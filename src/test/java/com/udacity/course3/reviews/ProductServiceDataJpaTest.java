package com.udacity.course3.reviews;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.util.DummyDataUtil;

/**
 * :INFO: FetchType.LAZY does not work without transactions. 
 * Otherwise getReviews() would be null. 
 * <p>
 *  see: 
 *  - https://stackoverflow.com/questions/57767530/join-fetch-query-not-fetching-lazy-collection-in-spring-datajpatest
 *  - https://github.com/zagyi/examples/blob/master/spring-data-jpa/src/test/java/com/zagyvaib/example/spring/data/jpa/basic/repository/EmployeeRepositoryTest.java
 *  - https://github.com/spring-projects/spring-batch/blob/master/spring-batch-infrastructure-tests/src/test/java/org/springframework/batch/retry/jms/SynchronousTests.java
 *  - https://stackoverflow.com/questions/28669280/spring-transaction-not-working-as-expected-in-junit-in-non-debug-mode
 * </p>
 * @author traal-devel
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductServiceDataJpaTest extends AbstractDataJpaTest {

  
  /* member variables */

  
  /* constructors */
  public ProductServiceDataJpaTest() {
    super();
  }

  
  /* methods */
  @Test
  public void testProductSaveWithReviews() {
    // :INFO: Use transaction to ensure that entity is written really to h2 database
    // so FetchType.LAZY (-> method getReview) can work. 
    // [START] - Transaction
    Product productRet = this.transactionTemplate.execute((status) -> {
      Product product = DummyDataUtil.generateDummyProduct("-1", "Test Description");
      Product productDB = this.productService.save(product);
      
      assertNotNull(productDB);
      assertNotNull(productDB.getId());
      
      Review review = DummyDataUtil.generateDummyReview(1).get(0);
      review.setProduct(productDB);
      Review reviewAdded = ProductServiceDataJpaTest.this.reviewRepository.save(review);
      
      assertNotNull(reviewAdded.getId());
      
      return productDB;
    });
    // [END] - Transaction. Use this pattern, so that getReviews() method 
    //         works correctly as a JUnit test-case.
    
    // :INFO: Flushes also does not work in this context. Entities are not written to database.
    // this.productRepository.flush();
    // this.reviewRepository.flush();
    
    // :INFO: Has to run in a transcation context too, to make 'mvn clean package' work.
    this.transactionTemplate.execute((status) -> {
      Product productDB = this.productRepository.findById(productRet.getId()).get();
      assertNotNull(productDB.getReviews());
      assertTrue(productDB.getReviews().size() == 1);
      return null;
    });
    
  }
  
  @Test
  public void testProductSave() {
    Product product = DummyDataUtil.generateDummyProduct("-1", "Test Description");
    Product productDB = this.productService.save(product);
    
    assertNotNull(productDB);
    assertNotNull(productDB.getId());
    assertNotNull(productDB.getCreatedTime());
    assertEquals(product.getName(), productDB.getName());
    assertEquals(product.getDescription(), productDB.getDescription());
    
  }
  
  @Test
  public void testProductListEmpty() {
    List<Product> productList = this.productService.list();
    assertNotNull(productList);
    assertTrue(productList.size() == 0);
  }
  
  @Test
  public void testProductSizeEqualsOne() {
    Product product = DummyDataUtil.generateDummyProduct("_1", "Test Description");
    this.productService.save(product);
    
    List<Product> productList = this.productService.list();
    assertNotNull(product);
    assertTrue(productList.size() == 1);
  }
  
  @Test
  public void testProductSizeEqualsFive() {
    int n = 5;
    long tmpCurrentSize = this.productService.count();
    List<Product> productList = DummyDataUtil.generateDummyProductList(n);
    for (Product product : productList) {
      this.productService.save(product);
    }
    
    List<Product> productDbList = this.productService.list();
    assertNotNull(productDbList);
    assertTrue(productDbList.size() == n + tmpCurrentSize);
  }
  
  @Test
  public void testProductFindById() {
    
    Product product = DummyDataUtil.generateDummyProduct("-1", "Test Description");
    Product productDB = this.productService.save(product);
    
    Product productFindById = this.productService.findById(productDB.getId());
    
    assertNotNull(productFindById);
    assertEquals(productFindById.getId(), productDB.getId());
    
  }
  
  @Test
  public void testCreateProductReviewComment() {
    
    // tansaction start.
    Product productRet = transactionTemplate.execute((status) -> {
      Product product = DummyDataUtil.generateDummyProduct("-1", "Test Description");
      Product productDB = this.productService.save(product);
      
      assertNotNull(productDB);
      assertNotNull(productDB.getId());
      
      Review review = DummyDataUtil.generateDummyReview(1).get(0);
      Review reviewDB = this.reviewService.addReview(productDB.getId(), review);
      
      assertNotNull(reviewDB);
      assertNotNull(reviewDB.getId());
      
      Comment comment = DummyDataUtil.generateDummyComment("_1");
      Comment commentDB = this.commentService.addComment(reviewDB.getId(), comment);
      
      assertNotNull(commentDB);
      assertNotNull(commentDB.getId());
      
      assertEquals(commentDB.getReview().getId(), reviewDB.getId());
      assertEquals(reviewDB.getProduct().getId(), productDB.getId());
      
      return productDB;
    });
    // transaction end, data is flushed. 
      
    // now we can read the new data marked as 'OneToMany' out of the database.
    transactionTemplate.execute((status) -> {
      Product productDB = this.productService.findById(productRet.getId());
      assertNotNull(productDB);
      assertNotNull(productDB.getReviews());
      
      return null;
    });
    
  }
  
}

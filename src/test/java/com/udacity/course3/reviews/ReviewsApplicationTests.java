package com.udacity.course3.reviews;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.support.TransactionTemplate;

import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.CommentRepository;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import com.udacity.course3.reviews.service.CommentService;
import com.udacity.course3.reviews.service.ProductService;
import com.udacity.course3.reviews.service.ReviewService;
import com.udacity.course3.reviews.util.DummyDataUtil;

/**
 * Reviews Application basic tests.
 * 
 * :INFO: Data JPA Test (h2 embedded database) are done in separate classes.
 * This class here uses the mysql database.
 * 
 * @author traal-devel
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class ReviewsApplicationTests {

  
  /* member variables */
  @Autowired 
  private ProductRepository           productRepository;
  
  @Autowired
  private ReviewRepository            reviewRepository;
  
  @Autowired
  private CommentRepository           commentRepository;
  
  @Autowired
  private PlatformTransactionManager  transactionManager;

  private TransactionTemplate         transactionTemplate;

  private ProductService              productService;
  private CommentService              commentService;
  private ReviewService               reviewService;
  
  
  /* constructors */
  public ReviewsApplicationTests() {
    super();
  }
  
  
  /* methods */
  @Before
  public void before() {
    this.productService = new ProductService(this.productRepository);
    this.reviewService = new ReviewService(
                                this.productRepository, this.reviewRepository);
    this.commentService = new CommentService(
                                this.reviewRepository, this.commentRepository);
    
    transactionTemplate = new TransactionTemplate(
                                  transactionManager, 
                                  new DefaultTransactionAttribute(
                                      TransactionDefinition.PROPAGATION_REQUIRES_NEW));
 
  }
  
//  @Test
//  public void methodAnnotations() {
//    // assert
//    AssertAnnotations.assertMethod(
//        Person.class, "getId", Id.class, GeneratedValue.class);
//    AssertAnnotations.assertMethod(
//        Person.class, "getFirstName", Column.class);
//    AssertAnnotations.assertMethod(
//        Person.class, "getLastName", Column.class);
//    AssertAnnotations.assertMethod(
//        Person.class, "getPhones", OneToMany.class);
//  }
  
  @Test
  public void testNotNull() {
    assertNotNull(this.productRepository);
    assertNotNull(this.reviewRepository);
    assertNotNull(this.commentRepository);
    assertNotNull(this.productService);
  }
  
  // :INFO: does not work without transaction. Otherwise getReviews() would be null. 
  // see: 
  // - https://stackoverflow.com/questions/57767530/join-fetch-query-not-fetching-lazy-collection-in-spring-datajpatest
  // - https://github.com/zagyi/examples/blob/master/spring-data-jpa/src/test/java/com/zagyvaib/example/spring/data/jpa/basic/repository/EmployeeRepositoryTest.java
  // - https://github.com/spring-projects/spring-batch/blob/master/spring-batch-infrastructure-tests/src/test/java/org/springframework/batch/retry/jms/SynchronousTests.java
  // - https://stackoverflow.com/questions/28669280/spring-transaction-not-working-as-expected-in-junit-in-non-debug-mode
  @Test
  public void testProductSaveWithReviews() {
    Product product = DummyDataUtil.generateDummyProduct("-1", "Test Description");
    Product productDB = this.productService.save(product);
   
    assertNotNull(productDB);
    assertNotNull(productDB.getId());
    
    Review review = DummyDataUtil.generateDummyReview(1).get(0);
    review.setProduct(productDB);
    Review reviewAdded =  ReviewsApplicationTests.this.reviewRepository.save(review);
    
    assertNotNull(reviewAdded.getId());

    productDB = ReviewsApplicationTests.this.productRepository.findById(productDB.getId()).get();
    assertNotNull(productDB.getReviews());
    assertTrue(productDB.getReviews().size() == 1);
    
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
  public void testAll() {
    
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
      
    // now we can read the new data out of the database.
    transactionTemplate.execute((status) -> {
      Product productDB = this.productService.findById(productRet.getId());
      assertNotNull(productDB);
      assertNotNull(productDB.getReviews());
      
      return null;
    });
    
  }
  
  
  
}
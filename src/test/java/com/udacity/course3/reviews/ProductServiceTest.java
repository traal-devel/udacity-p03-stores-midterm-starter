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
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import com.udacity.course3.reviews.service.ProductService;
import com.udacity.course3.reviews.util.DummyDataUtil;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductServiceTest {

  
  /* member variables */
  @Autowired 
  private ProductRepository productRepository;
  
  @Autowired
  private ReviewRepository  reviewRepository;
  
  @Autowired
  private PlatformTransactionManager transactionManager;

  private TransactionTemplate transactionTemplate;

  /* methods */
  
  private ProductService    productService;
  
  /* constructors */
  public ProductServiceTest() {
    super();
  }
  
  
  /* methods */
  @Before
  public void before() {
    this.productService = new ProductService(this.productRepository);
    
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
    assertNotNull(this.productService);
    assertNotNull(this.reviewRepository);
  }
  
  // :INFO: does not work without transaction. Otherwise getReviews() would be null. 
  // see: 
  // - https://stackoverflow.com/questions/57767530/join-fetch-query-not-fetching-lazy-collection-in-spring-datajpatest
  // - https://github.com/zagyi/examples/blob/master/spring-data-jpa/src/test/java/com/zagyvaib/example/spring/data/jpa/basic/repository/EmployeeRepositoryTest.java
  // - https://github.com/spring-projects/spring-batch/blob/master/spring-batch-infrastructure-tests/src/test/java/org/springframework/batch/retry/jms/SynchronousTests.java
  // - https://stackoverflow.com/questions/28669280/spring-transaction-not-working-as-expected-in-junit-in-non-debug-mode
  @Test
  public void testProductSaveWithReviews() {
    final Product productAdded =
        transactionTemplate.execute(new TransactionCallback<Product>() {
          @Override
          public Product doInTransaction(TransactionStatus status) {
            Product product = DummyDataUtil.generateDummyProduct("-1", "Test Description");
            return ProductServiceTest.this.productService.save(product);
          }
        });
    assertNotNull(productAdded.getId());
    
    final Review reviewAdded =
        transactionTemplate.execute(new TransactionCallback<Review>() {
          @Override
          public Review doInTransaction(TransactionStatus status) {
            Review review = DummyDataUtil.generateDummyReview(1).get(0);
            review.setProduct(productAdded);
            
            return ProductServiceTest.this.reviewRepository.save(review);
          }
        });
    assertNotNull(reviewAdded.getReviewId());

    Product productDB = this.productRepository.findById(productAdded.getId()).get();
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
    List<Product> productList = DummyDataUtil.generateDummyProductList(n);
    for (Product product : productList) {
      this.productService.save(product);
    }
    
    List<Product> productDbList = this.productService.list();
    assertNotNull(productDbList);
    assertTrue(productDbList.size() == n);
  }
  
  @Test
  public void testProductFindById() {
    
    Product product = DummyDataUtil.generateDummyProduct("-1", "Test Description");
    Product productDB = this.productService.save(product);
    
    Product productFindById = this.productService.findById(productDB.getId());
    
    assertNotNull(productFindById);
    assertEquals(productFindById.getId(), productDB.getId());
    
  }

}

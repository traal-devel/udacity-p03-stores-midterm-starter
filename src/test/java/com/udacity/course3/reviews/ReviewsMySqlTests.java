package com.udacity.course3.reviews;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.udacity.course3.reviews.service.ProductService;

/**
 * Reviews Application basic tests.
 * 
 * :INFO: Data JPA Test (h2 embedded database) are done in separate classes.
 * This class here uses the mysql database.
 * 
 * @author traal-devel
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ReviewsMySqlTests {

  
  /* member variables */
  @Autowired
  private ProductService                  productService;
  
  @Autowired
  private DtoConverter                    dtoConverter;

//  @Autowired
//  private ProductRepository               productRepository;
//
//  @Autowired
//  private ReviewRepository                reviewRepository;
//  
//  
//  @Autowired
//  private PlatformTransactionManager 
//                                          transactionManager;
//
//  private TransactionTemplate             transactionTemplate;

  
  /* methods */
  @Test
  public void contextLoads() {
    // Check if autowiring works.
    assertNotNull(this.productService);
    assertNotNull(this.dtoConverter);
  }
  
//  @Before
//  public void init() {
//    transactionTemplate = 
//        new TransactionTemplate(
//                transactionManager, 
//                new DefaultTransactionAttribute(
//                        TransactionDefinition.PROPAGATION_REQUIRES_NEW));
//  }
//	
//  @Test
//  public void testProductSaveWithReviews() {
//    
//    final Product productAdded =
//        transactionTemplate.execute(new TransactionCallback<Product>() {
//          @Override
//          public Product doInTransaction(TransactionStatus status) {
//            Product product = DummyDataUtil.generateDummyProduct("-1", "Test Description");
//            return ReviewsMySqlTests.this.productService.save(product);
//          }
//        });
//    assertNotNull(productAdded.getId());
//    
//    final Review reviewAdded =
//        transactionTemplate.execute(new TransactionCallback<Review>() {
//          @Override
//          public Review doInTransaction(TransactionStatus status) {
//            Review review = DummyDataUtil.generateDummyReview(1).get(0);
//            review.setProduct(productAdded);
//            
//            return ReviewsMySqlTests.this.reviewRepository.save(review);
//          }
//        });
//    assertNotNull(reviewAdded.getReviewId());
//
//    transactionTemplate.execute(new TransactionCallbackWithoutResult() {
//      @Override
//      protected void doInTransactionWithoutResult(TransactionStatus status) {
//        Product productDB = ReviewsMySqlTests.this.productRepository.findById(productAdded.getId()).get();
//        assertNotNull(productDB.getReviews());
//        assertTrue(productDB.getReviews().size() == 1);
//      }
//    });
//    
//  }
  
}
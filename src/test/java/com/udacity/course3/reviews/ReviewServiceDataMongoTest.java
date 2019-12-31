package com.udacity.course3.reviews;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.model.Review;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import com.udacity.course3.reviews.service.ReviewService;
import com.udacity.course3.reviews.util.DummyDataUtil;

@RunWith(SpringRunner.class)
@DataMongoTest
public class ReviewServiceDataMongoTest {

  
  /* member variables */
  @MockBean
  private ProductRepository productRepository;
  
  @Autowired
  private ReviewRepository  reviewRepository;
  
  @Autowired
  private ReviewService     reviewService;
  
  
  /* constructors */
  public ReviewServiceDataMongoTest() {
    super();
  }
  
  
  /* methods */
  @Before
  public void setup() {
    Product product = DummyDataUtil.generateDummyProduct("_1", "Product description");
    product.setId(1);
    
    given(productRepository.findById(any())).willReturn(Optional.of(product));
    given(productRepository.save(any())).willReturn(product);
    
    this.reviewRepository.deleteAll();
  }
  
  @Test
  public void testBasicNotNull() {
    assertNotNull(this.reviewRepository);
    assertNotNull(this.reviewService);
  }
    
  @Test
  public void testFindByProductIdEqualsZero() {
    List<Review> reviewList = this.reviewService.findByProductId(1);
    assertNotNull(reviewList);
    assertTrue("Size of list not correct. Should be 1 but is " + reviewList.size() ,
        reviewList.size() == 0);
    
  }
  
  @Test
  public void testFindByProductIdEqualsOne() {
    this.testAddReview();
    List<Review> reviewList = this.reviewService.findByProductId(1);
    assertNotNull(reviewList);
    assertTrue("Size of list not correct. Should be 1 but is " + reviewList.size() ,
        reviewList.size() == 1);
    
  }
  
  @Test
  public void testAddReview() {
    Integer productId = 1;
    Review reviewDummy = DummyDataUtil.generateDummyReview(1).get(0);
    Review reviewDB = this.reviewService.addReview(productId, reviewDummy);
    
    assertNotNull(reviewDB);
    assertNotNull(reviewDB.getId());
    assertEquals(reviewDummy.getAuthor(), reviewDB.getAuthor());
    assertEquals(reviewDummy.getBody(), reviewDB.getBody());
    assertEquals(reviewDummy.getTitle(), reviewDB.getTitle());

  }
  
  @TestConfiguration
  static class MongodbConfig {

    @Bean
    public ReviewService reviewService(
        ProductRepository productRepository,
        ReviewRepository reviewRepository
    ) {
        return new ReviewService(productRepository, reviewRepository);
    }
  }
}

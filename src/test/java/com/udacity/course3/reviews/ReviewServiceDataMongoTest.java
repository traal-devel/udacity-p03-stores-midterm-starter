package com.udacity.course3.reviews;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.test.context.junit4.SpringRunner;

import com.udacity.course3.reviews.data.entity.Product;
import com.udacity.course3.reviews.data.model.Review;
import com.udacity.course3.reviews.ex.ProductNotFoundException;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import com.udacity.course3.reviews.service.ReviewService;
import com.udacity.course3.reviews.util.DummyDataUtil;


@RunWith(SpringRunner.class)
@DataMongoTest
public class ReviewServiceDataMongoTest {

  
  /* constants */
  private static final Logger logger = 
                    LogManager.getLogger(ReviewServiceDataMongoTest.class);
  
  /* member variables */
  @MockBean
  private ProductRepository productRepository;
  
  @Autowired
  private ReviewRepository  reviewRepository;
  
  @Autowired
  private ReviewService     reviewService;
  
  @Autowired 
  private MongoTemplate     mongoTemplate;
  
  
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
    
  @Test(expected = ProductNotFoundException.class)
  public void testFindByProductIdEqualsZero() {
    this.reviewService.findByProductId(1);
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
  
  @Test
  public void testAverageRating() {
    Integer productId = 1;
    int iSize = 20;
    int sum = 0; 
    double average = 0;
    List<Review> reviewList = DummyDataUtil.generateDummyReview(iSize);
    
    
    for (Review review: reviewList) {
      Review reviewDB = this.reviewService.addReview(productId, review);
      assertNotNull(reviewDB);
      assertNotNull(reviewDB.getId());
      assertNotNull(reviewDB.getRating());
      
      sum += reviewDB.getRating();
    }
    
    average = (double)sum / iSize;
    logger.info("Average rating manually calculated: " + average);

    
    TypedAggregation<Review> agg = newAggregation(Review.class,
        group("productId")            
          .avg("rating").as("avgRating")
    );
    AggregationResults<Document> result = mongoTemplate.aggregate(agg, Document.class);
    List<Document> resultList = result.getMappedResults();
    double mongoAvgRating = (double)resultList.get(0).get("avgRating");
    logger.info("Average rating mongodb calculated: " + mongoAvgRating);
    
    assertTrue(average == mongoAvgRating);
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

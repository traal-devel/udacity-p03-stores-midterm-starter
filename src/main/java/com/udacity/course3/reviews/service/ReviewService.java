package com.udacity.course3.reviews.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.udacity.course3.reviews.data.model.Review;
import com.udacity.course3.reviews.ex.ProductNotFoundException;
import com.udacity.course3.reviews.ex.ReviewNotFoundException;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;

/**
 * Review Service implementation using the jpa repositories.
 * 
 * @author traal-devel
 */
@Service
public class ReviewService {

  
  /* member variables */
  @Autowired
  private ReviewRepository    reviewRepository;

  @Autowired
  private ProductRepository   productRepository;
  
  @Autowired
  private MongoTemplate       mongoTemplate;

  
  /* constructors */
  /**
   * Default constructor for ReviewService.
   */
  public ReviewService() {
    super();
  }

  
  /**
   * Constructor for ProductService.
   * <p>
   * Use for JUnit Tests and DataJpaTest annotation.
   * </p>
   * 
   * @param productRepository
   */
  public ReviewService(
      ProductRepository productRepository, 
      ReviewRepository reviewRepository
  ) {
    super();
    
    this.productRepository = productRepository;
    this.reviewRepository = reviewRepository;
  }
  
  /* methods */
  /**
   * Adds the given review to product specified with the parameter productId.
   * <p>
   * ProductNotFoundException thrown if product could not be found with given id.
   * </p>
   * 
   * @param productId Integer
   * @param review - Review to store.
   * @return Review - New review from the database.
   */
  public Review addReview(Integer productId, Review review) {
    return this.productRepository
               .findById(productId) 
               .map(product -> {
                 review.setProductId(productId);    
                 return this.reviewRepository.save(review);
               })
               .orElseThrow(ProductNotFoundException::new);
  }
  
  /**
   * Find one review by given parameter.
   * 
   * @param reviewId
   * @return Review - The actual review or ReviewNotFoundException.
   */
  public Review findById(ObjectId reviewId) {
    return this.reviewRepository
               .findById(reviewId)
               .orElseThrow(ReviewNotFoundException::new);
    
  }
  
  /**
   * Find all reviews by given parameter productId.
   * 
   * @param productId
   * @return List - list or ProductNotFountException
   */
  public List<Review> findByProductId(Integer productId) {
   
    return this.reviewRepository
               .findByProductId(productId)
               .filter(x -> !x.isEmpty())
               .orElseThrow(ProductNotFoundException::new);
  }
  
  
  /**
   * Calculates the average rating of the given product-id.
   * 
   * @param productId Integer
   * @return BigDecimal - Average rating or null if product not exists
   */
  public BigDecimal calcAvgRating(Integer productId) {
    
    TypedAggregation<Review> agg = 
        newAggregation(
            Review.class,
            match(
                Criteria.where("productId")
                        .is(productId)
            ),
            group("productId")       
              .avg("rating")
              .as("avgRating")
        );
    AggregationResults<Document> result = 
                              this.mongoTemplate.aggregate(agg, Document.class);
    List<Document> resultList = result.getMappedResults();
    BigDecimal avgRating = null;
    
    if (resultList != null && resultList.size() > 0) {
      avgRating = new BigDecimal(
                      (double)resultList.get(0).get("avgRating")
                  ).setScale(2, RoundingMode.HALF_UP);
    }
    
    return avgRating;
    
  }
  
}

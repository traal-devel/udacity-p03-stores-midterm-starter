package com.udacity.course3.reviews.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.udacity.course3.reviews.data.model.MongoReview;
import com.udacity.course3.reviews.ex.ProductNotFoundException;
import com.udacity.course3.reviews.ex.ReviewNotFoundException;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewMongoRepository;

/**
 * Review Service implementation using the mongo repositories.
 * 
 * @author traal-devel
 */
@Service
public class ReviewMongoService {

  
  /* member variables */
  @Autowired
  private ReviewMongoRepository   reviewRepository;

  @Autowired
  private ProductRepository       productRepository;
  
  @Autowired
  private MongoTemplate           mongoTemplate;

  
  /* constructors */
  /**
   * Default constructor for ReviewService.
   */
  public ReviewMongoService() {
    super();
  }

  
  /**
   * Constructor for ProductService.
   * <p>
   * Use for JUnit Tests and DataJpaTest annotation.
   * </p>
   * 
   * @param productRepository
   * @param reviewRepository
   */
  public ReviewMongoService(
      ProductRepository productRepository, 
      ReviewMongoRepository reviewRepository
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
   * @param reviewId Integer - Id of the review (currently mysql-database)
   * @param review - Review to store.
   * @return Review - New review from the database.
   */
  public MongoReview addReview(
      Integer productId, 
      Integer reviewId, 
      MongoReview review
  ) {
    
    return this.productRepository
               .findById(productId) 
               .map(product -> {
                 review.setId(reviewId);
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
  public MongoReview findById(
      Integer reviewId
  ) {
    
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
  public List<MongoReview> findByProductId(
      Integer productId
  ) {
   
    return this.reviewRepository
               .findByProductId(productId)
               .filter(x -> !x.isEmpty())
               .orElseThrow(ReviewNotFoundException::new);
    
  }
  
  
  /**
   * Calculates the average rating of the given product-id.
   * 
   * @param productId Integer
   * @return BigDecimal - Average rating or null if product not exists
   */
  public BigDecimal calcAvgRating(
      Integer productId
  ) {
    
    TypedAggregation<MongoReview> agg = 
        newAggregation(
            MongoReview.class,
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

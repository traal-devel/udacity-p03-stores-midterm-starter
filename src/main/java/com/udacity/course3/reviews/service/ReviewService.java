package com.udacity.course3.reviews.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.course3.reviews.data.dto.ReviewCreatingDTO;
import com.udacity.course3.reviews.data.dto.ReviewDTO;
import com.udacity.course3.reviews.data.entity.Review;
import com.udacity.course3.reviews.data.model.MongoReview;
import com.udacity.course3.reviews.utils.ObjectMapperUtils;

/**
 * Implementation of ReviewService.
 * <p>
 * Delegates the invocations to the right place.
 * </p>
 * 
 * @author traal-devel
 */
@Service
public class ReviewService {

  
  /* member variables */
  @Autowired
  private ReviewJpaService   jpaService;
  
  @Autowired
  private ReviewMongoService mongoService;
  
  
  /* constructors */
  public ReviewService() {
    super();
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
  // :TODO: Check if this works with mysql- and mongo-db together.
//  @Transactional
  public ReviewDTO addReview(
      Integer productId, 
      ReviewCreatingDTO reviewCreatingDTO
  ) {
    
    Review reviewDB = 
      this.jpaService
            .addReview(
                productId, 
                ObjectMapperUtils.map(reviewCreatingDTO, Review.class));

    if (reviewDB != null && reviewDB.getId() != null) {
      this.mongoService
            .addReview(
                productId,
                reviewDB.getId(),
                ObjectMapperUtils.map(reviewCreatingDTO, MongoReview.class));
                
            
    }
    
    return ObjectMapperUtils.map(reviewDB, ReviewDTO.class);
    
  }
  
  /**
   * Find one review by given parameter.
   * 
   * @param reviewId
   * @return Review - The actual review or ReviewNotFoundException.
   */
  public ReviewDTO findById(
      Integer reviewId
  ) {
    
    MongoReview review = this.mongoService.findById(reviewId);
    return ObjectMapperUtils.map(review, ReviewDTO.class);
        
  }
  
  /**
   * Find all reviews by given parameter productId.
   * 
   * @param productId
   * @return List - list or ProductNotFountException
   */
  public List<ReviewDTO> findByProductId(
      Integer productId
  ) {
    
    List<MongoReview> mongoList = this.mongoService.findByProductId(productId);
    return ObjectMapperUtils.mapAll(mongoList, ReviewDTO.class);
    
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
    
    return this.mongoService.calcAvgRating(productId);
    
  }

}

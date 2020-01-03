package com.udacity.course3.reviews.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.course3.reviews.data.dto.ReviewDTO;
import com.udacity.course3.reviews.data.entity.Review;
import com.udacity.course3.reviews.ex.ProductNotFoundException;
import com.udacity.course3.reviews.ex.ReviewNotFoundException;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import com.udacity.course3.reviews.utils.ObjectMapperUtils;

/**
 * Review Service implementation using the jpa repositories.
 * 
 * @author traal-devel
 */
@Service
public class ReviewJpaService {

  
  /* member variables */
  @Autowired
  private ReviewRepository    reviewRepository;

  @Autowired
  private ProductRepository   productRepository;

  
  /* constructors */
  /**
   * Default constructor for ReviewService.
   */
  public ReviewJpaService() {
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
  public ReviewJpaService (
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
  public Review addReview(
      Integer productId, 
      Review review
  ) {
  
    return this.productRepository
               .findById(productId) 
               .map(product -> {
                 review.setProduct(product);    
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
  public Review findById(
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
  public List<ReviewDTO> findByProductId(
      Integer productId
  ) {
  
    List<Review> reviewList = 
          this.reviewRepository
               .findByProductId(productId)
               .orElseThrow(ProductNotFoundException::new);
    
    return ObjectMapperUtils
              .mapAll(reviewList, ReviewDTO.class);
    
  
  }
  
}

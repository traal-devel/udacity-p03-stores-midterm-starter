package com.udacity.course3.reviews.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.course3.reviews.entity.Review;
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
  public ReviewService(ProductRepository productRepository) {
    super();
    
    this.productRepository = productRepository;
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
                 review.setProduct(product);    
                 return this.reviewRepository.save(review);
               })
               .orElseThrow(ProductNotFoundException::new);
  }
  
  public Review findById(Integer reviewId) {
    return this.reviewRepository
               .findById(reviewId)
               .orElseThrow(ReviewNotFoundException::new);
    
  }
  
}

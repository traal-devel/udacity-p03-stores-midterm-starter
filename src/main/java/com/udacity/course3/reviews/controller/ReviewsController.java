package com.udacity.course3.reviews.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.udacity.course3.reviews.data.dto.ReviewCreatingDTO;
import com.udacity.course3.reviews.data.dto.ReviewDTO;
import com.udacity.course3.reviews.service.ReviewService;
import com.udacity.course3.reviews.utils.ObjectMapperUtils;

/**
 * Spring REST controller for working with review entity.
 */
@RestController
@RequestMapping("/reviews")
public class ReviewsController {

  
  /* member variables */
  // [DONE] Wire JPA repositories here
  @Autowired
  private ReviewService   reviewService;

  
  /* constructors */
  /**
   * Default constructor.
   */
  public ReviewsController() {
    super();
  }

  
  /* methods */
  /**
   * Creates a review for a product.
   * <p>
   * 
   * :INFO: Encapsulated in ReviewService.
   * 
   * 1. [DONE] Add argument for review entity. Use {@link RequestBody} annotation.
   * 2. [DONE] Check for existence of product. :INFO: not necessary. 
   *           We will always receive a existing Product or an exception.  
   * 3. [DONE] If product not found, return NOT_FOUND.
   * 4. [DONE] If found, save review.
   * </p>
   *
   * @param productId The id of the product.
   * @return The created review or 404 if product id is not found [DONE].
   */
  @RequestMapping(
      value = "/products/{productId}", 
      method = RequestMethod.POST
  )
  public ResponseEntity<ReviewDTO> createReviewForProduct(
      @PathVariable("productId") Integer productId,
      @Valid @RequestBody ReviewCreatingDTO reviewCreatingDTO
  ) {
    
    ReviewDTO reviewDTO = 
                  this.reviewService.addReview(productId, reviewCreatingDTO);
    return ResponseEntity.ok(reviewDTO);
    
  }

  /**
   * Lists reviews by product [DONE].
   *
   * @param productId The id of the product.
   * @return The list of reviews.
   */
  @RequestMapping(
      value = "/products/{productId}", 
      method = RequestMethod.GET
  )
  public ResponseEntity<List<ReviewDTO>> listReviewsForProduct(
      @PathVariable("productId") Integer productId
  ) {
    
//    Product product = this.productService.findById(productId);
//    return ResponseEntity.ok(product.getReviews());
    
    List<ReviewDTO> reviewList = 
              ObjectMapperUtils.mapAll(
                  this.reviewService.findByProductId(productId), 
                  ReviewDTO.class
              );
    return ResponseEntity.ok(reviewList);
    
  }
}
package com.udacity.course3.reviews.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.course3.reviews.data.model.Comment;
import com.udacity.course3.reviews.ex.ReviewNotFoundException;
import com.udacity.course3.reviews.repository.ReviewRepository;

/**
 * Implementation of the comment service using jpa repositories.
 * 
 * @author traal-devel
 */
@Service
public class CommentService {

  
  /* member variables */
  @Autowired
  private ReviewRepository  reviewRepository;

  
  /* constructors */
  public CommentService() {
    super();
  }
  
  public CommentService(
      ReviewRepository reviewRepository
  ) {
    super();
    
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
  public Comment addComment(ObjectId reviewId, Comment comment) {
    return this.reviewRepository
               .findById(reviewId) 
               .map(review -> {
                 // :INFO: Set manually an ObjectId, so we can find a certain comment.
                 comment.setId(new ObjectId());
                 review.getComments().add(comment);
                 
                 this.reviewRepository.save(review);
                 return comment; 
               })
               .orElseThrow(ReviewNotFoundException::new);
  }
  
  /**
   * Finds all comments by using the given review-id.
   * 
   * @param reviewId Integer
   * @return List - the list with comments or ReviewNotFoundException 
   */
  public List<Comment> findByReviewId(ObjectId reviewId) {
    return this.reviewRepository
               .findById(reviewId)
               .map(review -> review.getComments())
               .orElseThrow(ReviewNotFoundException::new);
  }

}

package com.udacity.course3.reviews.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.ex.ReviewNotFoundException;
import com.udacity.course3.reviews.repository.CommentRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;

@Service
public class CommentService {

  
  /* member variables */
  @Autowired
  private CommentRepository commentRepository;
  
  @Autowired
  private ReviewRepository  reviewRepository;

  
  /* constructors */
  public CommentService() {
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
  public Comment addComment(Integer reviewId, Comment comment) {
    return this.reviewRepository
               .findById(reviewId) 
               .map(review -> {
                 comment.setReview(review);    
                 return this.commentRepository.save(comment);
               })
               .orElseThrow(ReviewNotFoundException::new);
  }

}

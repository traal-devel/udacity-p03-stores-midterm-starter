package com.udacity.course3.reviews.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.course3.reviews.data.entity.Comment;
import com.udacity.course3.reviews.ex.CommentNotFoundException;
import com.udacity.course3.reviews.ex.ReviewNotFoundException;
import com.udacity.course3.reviews.repository.CommentRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;

/**
 * Implementation of the comment service using jpa repositories.
 * 
 * @author traal-devel
 */
@Service
public class CommentJpaService {

  
  /* member variables */
  @Autowired
  private CommentRepository commentRepository;
  
  @Autowired
  private ReviewRepository  reviewRepository;

  
  /* constructors */
  public CommentJpaService() {
    super();
  }
  
  public CommentJpaService(
      ReviewRepository reviewRepository,
      CommentRepository commentRepository
  ) {
    
    super();
    
    this.commentRepository = commentRepository;
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
  public Comment addComment(
      Integer reviewId, 
      Comment comment
  ) {
    
    return this.reviewRepository
               .findById(reviewId) 
               .map(review -> {
                 comment.setReview(review);    
                 return this.commentRepository.save(comment);
               })
               .orElseThrow(ReviewNotFoundException::new);
    
  }
  
  /**
   * Finds all comments by using the given review-id.
   * 
   * @param reviewId Integer
   * @return List - the list with comments or ReviewNotFoundException 
   */
  public List<Comment> findByReviewId(
      Integer reviewId
  ) {
    
    return this.commentRepository
               .findByReviewId(reviewId)
               .orElseThrow(CommentNotFoundException::new);
    
  }

}

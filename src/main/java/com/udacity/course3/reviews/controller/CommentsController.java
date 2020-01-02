package com.udacity.course3.reviews.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.udacity.course3.reviews.data.dto.CommentCreatingDTO;
import com.udacity.course3.reviews.data.dto.CommentDTO;
import com.udacity.course3.reviews.data.model.Comment;
import com.udacity.course3.reviews.service.CommentService;
import com.udacity.course3.reviews.utils.ObjectMapperUtils;

/**
 * Spring REST controller for working with comment entity.
 */
@RestController
@RequestMapping("/comments")
public class CommentsController {

  
  /* member variables */
  // [DONE] Wire needed JPA repositories here
  @Autowired
  private CommentService  commentService;
  
  
  /* constructors */
  public CommentsController() {
    super();
  }
  
  
  /* methods */
  /**
   * Creates a comment for a review.
   * 
   * :INFO: Encapsulated in CommentService. 
   *
   * 1. [DONE] Add argument for comment entity. Use {@link RequestBody} annotation.
   * 2. [DONE] Check for existence of review.
   * 3. [DONE] If review not found, return NOT_FOUND.
   * 4. [DONE] If found, save comment.
   *
   * @param reviewId The id of the review.
   */
  @RequestMapping(
    value = "/reviews/{reviewId}", 
    method = RequestMethod.POST
  )
  public ResponseEntity<Comment> createCommentForReview(
      @PathVariable("reviewId") ObjectId reviewId,
      @RequestBody CommentCreatingDTO commentDTO
  ) {
    Comment comment = ObjectMapperUtils.map(commentDTO, Comment.class);
    Comment commentDB = this.commentService.addComment(reviewId, comment);
    return ResponseEntity.ok(commentDB);
    
  }

  /**
   * List comments for a review.
   *
   * 2. Check for existence of review.
   * 3. If review not found, return NOT_FOUND.
   * 4. If found, return list of comments.
   *
   * @param reviewId The id of the review.
   */
  @RequestMapping(
    value = "/reviews/{reviewId}", 
    method = RequestMethod.GET
  )
  public List<CommentDTO> listCommentsForReview(
      @PathVariable("reviewId") ObjectId reviewId
  ) {
    
    // Review review = this.reviewService.findById(reviewId); 
    // return review.getComments();
    
    // :INFO: Exception-handling encapsulated in CommentService.
    return ObjectMapperUtils.mapAll(
        this.commentService.findByReviewId(reviewId),
        CommentDTO.class
    );
    
  }
}
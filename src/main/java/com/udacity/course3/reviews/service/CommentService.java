package com.udacity.course3.reviews.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.course3.reviews.data.dto.CommentCreatingDTO;
import com.udacity.course3.reviews.data.dto.CommentDTO;
import com.udacity.course3.reviews.data.entity.Comment;
import com.udacity.course3.reviews.data.model.MongoComment;
import com.udacity.course3.reviews.ex.ReviewNotFoundException;
import com.udacity.course3.reviews.utils.ObjectMapperUtils;

/**
 * Implementation of the CommentService.
 * 
 * @author traal-devel
 */
@Service
public class CommentService {

  
  /* member variables */
  @Autowired
  private CommentJpaService     jpaService;
  
  @Autowired
  private CommentMongoService   mongoService;
  
  
  /* constructors */
  public CommentService() {
    super();
  }
  
  
  /* methods */
  /**
   * Adds the given comment to a review specified with by the parameter reviewId.
   * <p>
   * {@link ReviewNotFoundException} thrown if product could not be found 
   * with given id.
   * </p>
   * <p>
   * Use transaction
   * </p>
   * @param reviewId Integer
   * @param comment CommentCreatingDTO
   * @return Comment - New instance of comment from the database.
   */
//  :TODO: MongoDB 4.0 supports transactions. Test if this works with mysql together.
//  Sources:
//  - https://spring.io/blog/2018/06/28/hands-on-mongodb-4-0-transactions-with-spring-data
//  - https://www.baeldung.com/spring-data-mongodb-transactions
//  @Transactional
  public CommentDTO addComment(
      Integer reviewId, 
      CommentCreatingDTO comment
  ) {
    
    Comment commentDB = 
        this.jpaService
                .addComment(
                    reviewId, 
                    ObjectMapperUtils.map(comment, Comment.class));
    
    // :INFO: Try only to store the comment in the mongo db, if it could 
    // be stored in the mysql database.
    if (commentDB != null && commentDB.getId() != null) {
      this.mongoService
                .addComment(
                    reviewId, 
                    ObjectMapperUtils.map(comment, MongoComment.class));
      
    }
    
    return ObjectMapperUtils.map(commentDB, CommentDTO.class);
  }
  
  /**
   * Finds all comments by using the given review-id.
   * 
   * @param reviewId Integer
   * @return List - the list with comments or ReviewNotFoundException 
   */
  public List<CommentDTO> findByReviewId(
      Integer reviewId
  ) {
  
    List<Comment> commentList = this.jpaService.findByReviewId(reviewId); 
    return ObjectMapperUtils.mapAll(commentList, CommentDTO.class);
    
  }

}

package com.udacity.course3.reviews.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.udacity.course3.reviews.data.dto.CommentDTO;
import com.udacity.course3.reviews.data.model.MongoComment;
import com.udacity.course3.reviews.data.model.MongoReview;
import com.udacity.course3.reviews.repository.ReviewMongoRepository;
import com.udacity.course3.reviews.service.CommentMongoService;
import com.udacity.course3.reviews.util.DummyDataUtil;
import com.udacity.course3.reviews.utils.ObjectMapperUtils;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CommentServiceDataMongoTest extends AbstractDataMongoTest {

  
  /* member variables */
  @Autowired
  private ReviewMongoRepository    reviewRepository;

  @Autowired
  private CommentMongoService      commentService;
  
  private MongoReview              reviewDB;

  
  /* constructors */
  public CommentServiceDataMongoTest() {
    super();
  }

  
  /* methods */
  @Before
  public void before() {
    this.reviewRepository.deleteAll();
    
    MongoReview review = new MongoReview();
    review.setId(1);
    review.setAuthor("Test Author (1)");
    review.setBody("Test Body (1)");
    review.setCreatedTime(new Timestamp(System.currentTimeMillis()));
    review.setProductId(1);
    review.setTitle("Test Title (1)");
    
    this.reviewDB = this.reviewRepository.save(review);
  }
  
  @Test
  public void testReadComment() {
    Integer id = this.reviewDB.getId();
    this.testAddComment();
    List<MongoComment> commentList = this.commentService.findByReviewId(id);
    
    assertNotNull(commentList);
    assertTrue(commentList.size() == 1);
  }
  
  @Test
  public void testAddComment() {
    Integer reviewId = this.reviewDB.getId();
    CommentDTO comment = DummyDataUtil.generateDummyComment("_2");
    MongoComment commentDB = 
          this.commentService
                  .addComment(
                      reviewId, 
                      ObjectMapperUtils.map(comment, MongoComment.class));
    
    assertNotNull(commentDB);
    assertEquals(comment.getContent(), commentDB.getContent());
    assertEquals(comment.getName(), commentDB.getName());
  }
  
  
  @TestConfiguration
  static class MongodbConfig {

    @Bean
    public CommentMongoService commentService (
        ReviewMongoRepository reviewRepository
    ) {
        return new CommentMongoService(reviewRepository);
    }
  }
 
}

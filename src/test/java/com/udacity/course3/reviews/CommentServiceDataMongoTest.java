package com.udacity.course3.reviews;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.udacity.course3.reviews.data.model.Comment;
import com.udacity.course3.reviews.data.model.Review;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import com.udacity.course3.reviews.service.CommentService;
import com.udacity.course3.reviews.util.DummyDataUtil;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CommentServiceDataMongoTest {

  
  /* member variables */
  @MockBean
  private ProductRepository   productRepository;
  
  @Autowired
  private ReviewRepository    reviewRepository;

  @Autowired
  private CommentService      commentService;
  
  private Review              reviewDB;

  
  /* constructors */
  public CommentServiceDataMongoTest() {
    super();
  }

  
  /* methods */
  @Before
  public void before() {
    this.reviewRepository.deleteAll();
    
    Review review = new Review();
    review.setAuthor("Test Author (1)");
    review.setBody("Test Body (1)");
    review.setCreatedTime(new Timestamp(System.currentTimeMillis()));
    review.setProductId(1);
    review.setTitle("Test Title (1)");
    
    this.reviewDB = this.reviewRepository.save(review);
  }
  
  @Test
  public void testReadComment() {
    ObjectId id = this.reviewDB.getId();
    this.testAddComment();
    List<Comment> commentList = this.commentService.findByReviewId(id);
    
    assertNotNull(commentList);
    assertTrue(commentList.size() == 1);
  }
  
  @Test
  public void testAddComment() {
    ObjectId reviewId = this.reviewDB.getId();
    Comment comment = DummyDataUtil.generateDummyComment("_2");
    Comment commentDB = this.commentService.addComment(reviewId, comment);
    
    assertNotNull(commentDB);
    assertEquals(comment.getContent(), commentDB.getContent());
    assertEquals(comment.getName(), commentDB.getName());
  }
  
  
  @TestConfiguration
  static class MongodbConfig {

    @Bean
    public CommentService commentService (
        ReviewRepository reviewRepository
    ) {
        return new CommentService(reviewRepository);
    }
  }
 
}

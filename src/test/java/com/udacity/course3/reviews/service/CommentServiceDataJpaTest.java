package com.udacity.course3.reviews.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.udacity.course3.reviews.data.dto.CommentDTO;
import com.udacity.course3.reviews.data.entity.Comment;
import com.udacity.course3.reviews.util.DummyDataUtil;
import com.udacity.course3.reviews.utils.ObjectMapperUtils;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CommentServiceDataJpaTest extends AbstractDataJpaTest {

  
  /* member variables */

  
  /* constructors */
  public CommentServiceDataJpaTest() {
    super();
  }

  
  /* methods */
  @Before
  public void init() {
    // Generate test data.
    this.geneateTestTupel();
  }
  
  @Test
  public void testReadComment() {
    Integer reviewId = 1;
    List<Comment> commentList = this.commentService.findByReviewId(reviewId);
    
    assertNotNull(commentList);
    assertTrue(commentList.size() == 1);
  }
  
  @Test
  public void testAddComment() {
    this.transactionTemplate.execute((status) -> {
      Integer reviewId = 1;
      CommentDTO comment = DummyDataUtil.generateDummyComment("_2");
      Comment commentDB = 
          this.commentService
                .addComment(
                    reviewId, 
                    ObjectMapperUtils.map(comment, Comment.class));
      
      assertNotNull(commentDB);
      assertEquals(comment.getContent(), commentDB.getContent());
      assertEquals(comment.getName(), commentDB.getName());
      return null;
    });
  }
 
}

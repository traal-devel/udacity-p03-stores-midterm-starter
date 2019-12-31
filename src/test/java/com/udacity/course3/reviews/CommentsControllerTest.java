package com.udacity.course3.reviews;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.util.Arrays;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.udacity.course3.reviews.model.Comment;
import com.udacity.course3.reviews.service.CommentService;
import com.udacity.course3.reviews.util.DummyDataUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class CommentsControllerTest {

  
  /* member variables */
  @Autowired
  private MockMvc mvc;

  @Autowired
  private JacksonTester<Comment> jsonComment;

  @MockBean
  private CommentService  commentService;
  
  /* constructors */
  public CommentsControllerTest() {
    super();
  }

  /* methods */
  @Before
  public void setup() {
    Comment comment = DummyDataUtil.generateDummyComment("_1");
    comment.setId(new ObjectId());
    
    Comment comment2 = DummyDataUtil.generateDummyComment("_2");
    comment2.setId(new ObjectId());
    
    Comment comment3 = DummyDataUtil.generateDummyComment("_3");
    comment3.setId(new ObjectId());
    
    given(commentService.addComment(any(), any()))
        .willReturn(comment3);
    given(commentService.findByReviewId(any()))
        .willReturn(Arrays.asList(comment, comment2, comment3));
    
  }
  
  /**
   * Tests for successful creation of new car in the system
   * 
   * @throws Exception when car creation fails in the system
   */
  @Test
  public void testFindReviewById() throws Exception {
    ObjectId reviewId = new ObjectId();
    mvc.perform(
          get(new URI("/comments/reviews/" + reviewId.toString()))
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
        )
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$[0].id").isNotEmpty())
        .andExpect(jsonPath("$[1].id").isNotEmpty())
        .andExpect(jsonPath("$[2].id").isNotEmpty())
        ;
  }
  
  @Test
  public void testAddCommentToReview() throws Exception {
    Comment comment3 = DummyDataUtil.generateDummyComment("_3");
    
    ObjectId reviewId = new ObjectId();
    mvc.perform(
          post(new URI("/comments/reviews/" + reviewId.toString()))
            .content(jsonComment.write(comment3).getJson())
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").isNotEmpty())
        ;
  }

}

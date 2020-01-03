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

import com.udacity.course3.reviews.data.dto.CommentDTO;
import com.udacity.course3.reviews.service.CommentService;
import com.udacity.course3.reviews.util.DummyDataUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class CommentsControllerTest {

  
  /* member variables */
  @Autowired
  private MockMvc                     mvc;

  @Autowired
  private JacksonTester<CommentDTO>   jsonComment;

  @MockBean
  private CommentService              commentService;
  
  
  /* constructors */
  public CommentsControllerTest() {
    super();
  }

  
  /* methods */
  @Before
  public void setup() {
    CommentDTO comment = DummyDataUtil.generateDummyComment("_1");
    comment.setId(1);
    
    CommentDTO comment2 = DummyDataUtil.generateDummyComment("_2");
    comment2.setId(2);
    
    CommentDTO comment3 = DummyDataUtil.generateDummyComment("_3");
    comment3.setId(3);
    
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
    Integer reviewId = 1;
    mvc.perform(
          get(new URI("/comments/reviews/" + reviewId))
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
        )
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$[0].id", is(1)))
        .andExpect(jsonPath("$[1].id", is(2)))
        .andExpect(jsonPath("$[2].id", is(3)))
        ;
  }
  
  @Test
  public void testAddCommentToReview() throws Exception {
    CommentDTO comment3 = DummyDataUtil.generateDummyComment("_3");
    
    Integer reviewId = 1;
    mvc.perform(
          post(new URI("/comments/reviews/" + reviewId))
            .content(jsonComment.write(comment3).getJson())
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").isNotEmpty())
        ;
  }

}

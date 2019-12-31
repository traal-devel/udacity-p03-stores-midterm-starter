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
import java.util.List;

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

import com.udacity.course3.reviews.model.Review;
import com.udacity.course3.reviews.service.ReviewService;
import com.udacity.course3.reviews.util.DummyDataUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class ReviewControllerTest {

  
  /* member variables */
  @Autowired
  private MockMvc mvc;

  @Autowired
  private JacksonTester<Review> jsonReview;

  @MockBean
  private ReviewService  reviewService;
  
  
  /* constructors */
  public ReviewControllerTest() {
    super();
  }

  
  /* methods */
  @Before
  public void setup() {
    List<Review> reviewList = DummyDataUtil.generateDummyReview(1);
    Review firstReview = reviewList.get(0);
    firstReview.setId(new ObjectId());
    
    given(reviewService.addReview(any(), any())).willReturn(firstReview);
    given(reviewService.findByProductId(any())).willReturn(reviewList);
    
  }
  
  /**
   * Tests for successful creation of new car in the system
   * 
   * @throws Exception when car creation fails in the system
   */
  @Test
  public void testCreateReview() throws Exception {
    Integer productId = 1;
    Review review = DummyDataUtil.generateDummyReview(1).get(0);
    mvc.perform(
        post(new URI("/reviews/products/" + productId))
          .content(jsonReview.write(review).getJson())
          .contentType(MediaType.APPLICATION_JSON_UTF8)
          .accept(MediaType.APPLICATION_JSON_UTF8)
        )
        .andExpect(status().isOk())
        .andExpect(content().json("{}"))
        .andExpect(jsonPath("$.id").isNotEmpty())
    ;
  }
  
  @Test
  public void testGetProductList() throws Exception {
    Integer productId = 1;
    mvc.perform(
        get(new URI("/reviews/products/" + productId))
          .contentType(MediaType.APPLICATION_JSON_UTF8)
          .accept(MediaType.APPLICATION_JSON_UTF8)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$[0].id").isNotEmpty())
    ;
  }
  


}

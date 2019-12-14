package com.udacity.course3.reviews;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.udacity.course3.reviews.service.CommentService;
import com.udacity.course3.reviews.service.ProductService;
import com.udacity.course3.reviews.service.ReviewService;

/**
 * Reviews Application basic tests.
 * 
 * :INFO: Data JPA Test (h2 embedded database) are done in separate classes.
 * 
 * @author traal-devel
 */

@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class ReviewsApplicationTests {

  
  /* member variables */
  @Autowired
  private ProductService  productService;

  @Autowired
  private ReviewService   reviewService;
  
  @Autowired
  private CommentService  commentService;
  
  
  
  /* constructors */
  public ReviewsApplicationTests() {
    super();
  }
  
  
  /* methods */
  @Test
  public void contextLoads() {
    // :INFO: Data JPA Test (h2 embedded database) are done in separate classes.
    assertNotNull(this.productService);
    assertNotNull(this.reviewService);
    assertNotNull(this.commentService);
  }
  
  
}
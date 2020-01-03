package com.udacity.course3.reviews.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.udacity.course3.reviews.data.dto.CommentDTO;
import com.udacity.course3.reviews.data.dto.ProductDTO;
import com.udacity.course3.reviews.data.dto.ReviewDTO;

public class DummyDataUtil {

  
  /* member variables */

  
  /* constructors */
  private  DummyDataUtil() {
    super();
  }

  
  /* methods */
  public static CommentDTO generateDummyComment(String suffix) {
    CommentDTO comment = new CommentDTO();
    
    comment.setContent("TEST_CONTENT_" + suffix);
    comment.setName("TEST_NAME_" + suffix);
    comment.setCreatedTime(Timestamp.valueOf(LocalDateTime.now()));

    return comment;
  }
  
  public static List<ReviewDTO> generateDummyReview(int n) {
    List<ReviewDTO> reviewList = new ArrayList<>(n);
    
    for (int i=1; i<n+1; i++) {
      ReviewDTO review = new ReviewDTO();
      review.setAuthor("TEST_AUTHOR_" + i);
      review.setTitle("TEST_TITLE_" + i);
      review.setBody("TEST_BODY_" + i);
      review.setCreatedTime(Timestamp.valueOf(LocalDateTime.now()));
      review.setRating((int)(Math.random() * 5) + 1);
      reviewList.add(review);
    }
    
    return reviewList;
  }
  
  public static List<ProductDTO> generateDummyProductList(int n) {
    List<ProductDTO> productList = new ArrayList<>(n);
    
    for(int i=1; i<n+1; i++) {
      productList.add(DummyDataUtil.generateDummyProduct("_" + i, "Test Description " + i));
    }
    
    return productList;
  }
  
  public static ProductDTO generateDummyProduct(
      String suffix, 
      String description
  ) {
  
    ProductDTO product = new ProductDTO();
    
    product.setName("Product"  + suffix);
    product.setDescription(description);
    product.setCreatedTime(Timestamp.valueOf(LocalDateTime.now()));
    
    return product;
    
  }
}

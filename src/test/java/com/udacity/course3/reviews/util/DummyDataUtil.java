package com.udacity.course3.reviews.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;

public class DummyDataUtil {

  
  /* member variables */

  
  /* constructors */
  private  DummyDataUtil() {
    super();
  }

  
  /* methods */
  public static Comment generateDummyComment(String suffix) {
    Comment comment = new Comment();
    
    comment.setContent("TEST_CONTENT_" + suffix);
    comment.setName("TEST_NAME_" + suffix);
    comment.setCreatedTime(Timestamp.valueOf(LocalDateTime.now()));

    return comment;
  }
  
  public static List<Review> generateDummyReview(int n) {
    List<Review> reviewList = new ArrayList<>(n);
    
    for (int i=1; i<n+1; i++) {
      Review review = new Review();
      review.setAuthor("TEST_AUTHOR_" + i);
      review.setTitle("TEST_TITLE_" + i);
      review.setBody("TEST_BODY_" + i);
      review.setCreatedTime(Timestamp.valueOf(LocalDateTime.now()));
      reviewList.add(review);
    }
    
    return reviewList;
  }
  
  public static List<Product> generateDummyProductList(int n) {
    List<Product> productList = new ArrayList<>(n);
    
    for(int i=1; i<n+1; i++) {
      productList.add(DummyDataUtil.generateDummyProduct("_" + i, "Test Description " + i));
    }
    
    return productList;
  }
  
  public static Product generateDummyProduct(
      String suffix, 
      String description
  ) {
  
    Product product = new Product();
    
    product.setName("Product"  + suffix);
    product.setDescription(description);
    product.setCreatedTime(Timestamp.valueOf(LocalDateTime.now()));
    
    return product;
    
  }
}

package com.udacity.course3.reviews.data.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(
    collection = "reviews"
)
public class Review {

  
  /* member variables */
  @Id
  private ObjectId  id;
  
  @NotNull
  private String    title;
  
  @NotNull
  private String    body;
  
  @NotNull
  private String    author;
  
  private Date      createdTime;
  
  @Min(value = 1)
  @Max(value = 5)
  private Integer   rating;    
  
  // :INFO: This is the primary key of the mysql database. 
  @JsonIgnore
  private Integer   productId;
  
  // :INFO: we immediately initialise the comments, so we can avoid a NullPointerException
  @JsonIgnore
  private List<Comment> comments = new ArrayList<>();

  
  /* constructors */
  public Review() {
    super();
  }


  /* methods */
  public ObjectId getId() {
    return id;
  }

  public void setId(ObjectId reviewId) {
    this.id = reviewId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public Date getCreatedTime() {
    return createdTime;
  }

  public void setCreatedTime(Date createdTime) {
    this.createdTime = createdTime;
  }

  public List<Comment> getComments() {
    return comments;
  }

  public void setComments(List<Comment> comments) {
    this.comments = comments;
  }


  public Integer getProductId() {
    return productId;
  }


  public void setProductId(Integer product_id) {
    this.productId = product_id;
  }


  public Integer getRating() {
    return rating;
  }


  public void setRating(Integer rating) {
    this.rating = rating;
  }

}

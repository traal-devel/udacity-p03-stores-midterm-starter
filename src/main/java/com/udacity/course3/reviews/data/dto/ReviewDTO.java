package com.udacity.course3.reviews.data.dto;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;

/**
 * Review data transfer object implementation for return values.
 * 
 * @author traal-devel
 */
public class ReviewDTO {

  
  /* member variables */
  @NotNull
  private ObjectId  id;
  
  @NotNull
  private String    title;
  
  @NotNull
  private String    body;
  
  @NotNull
  private String    author;
  
  @NotNull
  private Date      createdTime;
  
  @Min(value = 1)
  @Max(value = 5)
  private Integer   rating;    
  
  
  /* constructors */
  public ReviewDTO() {
    super();
  }

  
  /* methods */
  public ObjectId getId() {
    return id;
  }

  public void setId(ObjectId id) {
    this.id = id;
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

  public Integer getRating() {
    return rating;
  }

  public void setRating(Integer rating) {
    this.rating = rating;
  }

}

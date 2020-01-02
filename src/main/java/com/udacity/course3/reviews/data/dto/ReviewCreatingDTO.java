package com.udacity.course3.reviews.data.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Review data transfer object implementation for creation use case.
 * 
 * @author traal-devel
 */
public class ReviewCreatingDTO {

  
  /* member variables */
  @NotNull
  private String    title;
  
  @NotNull
  private String    body;
  
  @NotNull
  private String    author;
  
  @JsonIgnore
  private Date      createdTime = Timestamp.valueOf(LocalDateTime.now());
  
  @Min(value = 1)
  @Max(value = 5)
  private Integer   rating;    
  
  
  /* constructors */
  public ReviewCreatingDTO() {
    super();
  }

  
  /* methods */
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

package com.udacity.course3.reviews.data.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CommentCreatingDTO {

  
  /* member variables */
  @NotNull
  private String    name;
  
  @NotNull
  private String    content;
  
  @JsonIgnore
  private Date      createdTime = Timestamp.valueOf(LocalDateTime.now());
  
  
  /* constructors */
  public CommentCreatingDTO() {
    super();
  }

  
  /* methods */
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Date getCreatedTime() {
    return createdTime;
  }

  public void setCreatedTime(Date createdTime) {
    this.createdTime = createdTime;
  }

}

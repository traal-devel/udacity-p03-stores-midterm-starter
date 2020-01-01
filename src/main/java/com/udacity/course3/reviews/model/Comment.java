package com.udacity.course3.reviews.model;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Comment {

  
  /* member variables */
  @Id
  private ObjectId  id;
  
  private String    name;
  
  private String    content;
  
  private Date      createdTime;
  
  
  /* constructors */
  public Comment() {
    super();
  }

  
  /* methods */
  public ObjectId getId() {
    return id;
  }

  public void setId(ObjectId id) {
    this.id = id;
  }

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

package com.udacity.course3.reviews.data.model;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

/**
 * Mongo DB model of comment stored with an instance of review
 * 
 * @author traal-devel
 */
public class MongoComment {

  
  /* member variables */
  @Id
  private ObjectId  id;
  
  private String    name;
  
  private String    content;
  
  private Date      createdTime;
  
  
  /* constructors */
  public MongoComment() {
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

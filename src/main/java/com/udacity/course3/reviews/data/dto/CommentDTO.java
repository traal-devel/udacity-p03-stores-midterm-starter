package com.udacity.course3.reviews.data.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;

/**
 * Comment data transfer object for return values.
 * 
 * @author traal-devel
 */
public class CommentDTO {

  
  /* member variables */
  @NotNull
  private ObjectId  id;
  
  @NotNull
  private String    name;
  
  @NotNull
  private String    content;
  
  @NotNull
  private Date      createdTime;

  
  /* constructors */
  public CommentDTO() {
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

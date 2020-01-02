package com.udacity.course3.reviews.data.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Product data transfer object for creating use case.
 * 
 * @author traal-devel
 */
public class ProductCreatingDTO {

  
  /* member variables */
  @NotNull
  private String        name;
  
  @NotNull
  private String        description;
  
  @JsonIgnore
  private Timestamp     createdTime = Timestamp.valueOf(LocalDateTime.now());

  
  /* constructors */
  public ProductCreatingDTO() {
    super();
  }


  /* methods */
  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public String getDescription() {
    return description;
  }


  public void setDescription(String description) {
    this.description = description;
  }


  public Timestamp getCreatedTime() {
    return createdTime;
  }


  public void setCreatedTime(Timestamp createdTime) {
    this.createdTime = createdTime;
  }

}

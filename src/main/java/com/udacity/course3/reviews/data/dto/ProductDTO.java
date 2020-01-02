package com.udacity.course3.reviews.data.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

/**
 * Product data transfer object for return values.
 * 
 * @author traal-devel
 */
public class ProductDTO {

  
  /* member variables */
  @NotNull
  private Integer       id;
  
  @NotNull
  private String        name;
  
  @NotNull  
  private String        description;
  
  @NotNull
  private Timestamp     createdTime;
  
  private BigDecimal    averageRating;

  
  /* constructors */
  public ProductDTO() {
    super();
  }


  /* methods */
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

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

  public BigDecimal getAverageRating() {
    return averageRating;
  }

  public void setAverageRating(BigDecimal averageRating) {
    this.averageRating = averageRating;
  }


}

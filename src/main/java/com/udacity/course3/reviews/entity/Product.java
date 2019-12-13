package com.udacity.course3.reviews.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Product Entity.
 * 
 * @author traal-devel
 */
@Entity
@Table(name="product")
public class Product {

  
  /* member variables */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="product_id")
  private Long id;
  
  @Column(name="name")
  @NotNull
  private String name;
  
  @Column(name="description")
  private String description;
  
  @Column(name = "created_time")
  private Timestamp createdTime;
  

  /* constructors */
  public Product() {
    super();
  }

  
  /* methods */
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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

}

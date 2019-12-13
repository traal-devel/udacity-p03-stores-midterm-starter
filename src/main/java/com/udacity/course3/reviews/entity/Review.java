package com.udacity.course3.reviews.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="review")
public class Review {

  
  /* member variables */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="review_id")
  private Integer reviewId;
  
  @Column(name="title")
  @NotNull
  private String title;
  
  @Column(name="body")
  @NotNull
  private String body;
  
  @Column(name="author")
  @NotNull
  private String author;
  
  @Column(name = "created_time")
  @NotNull
  private Timestamp createdTime;
  
  @ManyToOne
  @JoinColumn(name="product_id")
  private Product product;

  
  /* constructors */
  public Review() {
    super();
  }


  /* methods */
  public Integer getReviewId() {
    return reviewId;
  }

  public void setReviewId(Integer reviewId) {
    this.reviewId = reviewId;
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

  public Timestamp getCreatedTime() {
    return createdTime;
  }

  public void setCreatedTime(Timestamp createdTime) {
    this.createdTime = createdTime;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

}

package com.udacity.course3.reviews.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="review")
public class Review {

  
  /* member variables */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="review_id")
  private Integer id;
  
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
  private Timestamp createdTime;
  
  @ManyToOne
  @JoinColumn(name="product_id")
  @JsonIgnore
  private Product product;

  @OneToMany(
    mappedBy = "review", 
    fetch = FetchType.LAZY
  )
  @JsonIgnore
  private List<Comment> comments;
  
  /* constructors */
  public Review() {
    super();
  }


  /* methods */
  public Integer getId() {
    return id;
  }

  public void setId(Integer reviewId) {
    this.id = reviewId;
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

  public List<Comment> getComments() {
    return comments;
  }

  public void setComments(List<Comment> comments) {
    this.comments = comments;
  }

}

package com.udacity.course3.reviews.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.udacity.course3.reviews.data.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

  /* methods */
  @Query("SELECT r FROM Review r JOIN r.product p ON p.id = :productId ")
  Optional<List<Review>> findByProductId(
      @Param(value = "productId") Integer productId
  );

}

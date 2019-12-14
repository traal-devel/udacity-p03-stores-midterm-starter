package com.udacity.course3.reviews.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.udacity.course3.reviews.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

  /* methods */
  @Query("SELECT c FROM Comment c JOIN c.review r ON r.id = :reviewId ")
  Optional<List<Comment>> findByReviewId(
      @Param(value = "reviewId") Integer reviewId
  );
  
}

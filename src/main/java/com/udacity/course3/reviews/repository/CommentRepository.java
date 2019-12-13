package com.udacity.course3.reviews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udacity.course3.reviews.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

  /* methods */

}

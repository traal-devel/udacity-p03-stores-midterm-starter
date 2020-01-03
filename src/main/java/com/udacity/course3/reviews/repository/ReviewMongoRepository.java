package com.udacity.course3.reviews.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.udacity.course3.reviews.data.model.MongoReview;

@Repository
public interface ReviewMongoRepository extends MongoRepository<MongoReview, Integer> {

  /* methods */
  Optional<List<MongoReview>> findByProductId(Integer productId);

}

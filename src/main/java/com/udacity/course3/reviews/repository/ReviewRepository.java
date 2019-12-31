package com.udacity.course3.reviews.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.udacity.course3.reviews.model.Review;

@Repository
public interface ReviewRepository extends MongoRepository<Review, ObjectId> {

  /* methods */
  Optional<List<Review>> findByProductId(Integer productId);

}

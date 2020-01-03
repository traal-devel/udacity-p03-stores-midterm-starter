package com.udacity.course3.reviews.service;

import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.udacity.course3.reviews.repository.CommentRepository;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;

/**
 * Abstract Data Mongo Test Implementation.
 * <p>
 * Mock all jpa repository beans so we can use the annotation DataMongoTest.
 * Otherwise we will receive an exception at startup that the bean 
 * <tt>entityManagerFactory</tt> can not be initialised.
 * </p>
 * 
 * @author traal-devel
 */
@RunWith(SpringRunner.class)
@DataMongoTest
public abstract class AbstractDataMongoTest {

  
  /* member variables */
  @MockBean
  protected ProductRepository       productRepository;
  
  @MockBean
  protected CommentRepository       commentJpaRepository;
  
  @MockBean
  protected ReviewRepository        reviewJpaRepository;

  
  /* constructors */
  public AbstractDataMongoTest() {
    super();
  }
  
  /* methods */

}

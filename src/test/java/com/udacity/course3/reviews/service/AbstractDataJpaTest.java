package com.udacity.course3.reviews.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.support.TransactionTemplate;

import com.udacity.course3.reviews.data.dto.CommentDTO;
import com.udacity.course3.reviews.data.dto.ProductDTO;
import com.udacity.course3.reviews.data.dto.ReviewDTO;
import com.udacity.course3.reviews.data.entity.Comment;
import com.udacity.course3.reviews.data.entity.Review;
import com.udacity.course3.reviews.repository.CommentRepository;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import com.udacity.course3.reviews.service.CommentJpaService;
import com.udacity.course3.reviews.service.ProductService;
import com.udacity.course3.reviews.service.ReviewJpaService;
import com.udacity.course3.reviews.util.DummyDataUtil;
import com.udacity.course3.reviews.utils.ObjectMapperUtils;

/**
 * Abstract class used to reuse code for tests with 'DataJpaTest' annotation.
 * 
 * @author traal-devel
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public abstract class AbstractDataJpaTest {

  
  /* member variables */
  @Autowired 
  protected ProductRepository           productRepository;
  
  @Autowired
  protected ReviewRepository            reviewRepository;
  
  @Autowired
  protected CommentRepository           commentRepository;
  
  @Autowired
  protected PlatformTransactionManager  transactionManager;

  
  protected TransactionTemplate         transactionTemplate;

  protected ProductService              productService;
  protected CommentJpaService           commentService;
  protected ReviewJpaService            reviewService;
  

  /* constructors */
  public AbstractDataJpaTest() {
    super();
  }

  
  /* methods */
  @Before
  public void before() {
    // initialise the services correctly with the jpa repositories.
    // has to be done manually, because not the whole application context is
    // loaded when using 'DataJpaTest' annotation.
    this.productService = new ProductService(this.productRepository);
    this.reviewService = new ReviewJpaService(
                                this.productRepository, this.reviewRepository);
    this.commentService = new CommentJpaService(
                                this.reviewRepository, this.commentRepository);
    
    transactionTemplate = new TransactionTemplate(
                                  transactionManager, 
                                  new DefaultTransactionAttribute(
                                      TransactionDefinition.PROPAGATION_REQUIRES_NEW));
    
    this.commentRepository.deleteAll();
    this.reviewRepository.deleteAll();
    this.productRepository.deleteAll();
 
  }
  
  @Test
  public void testNotNull() {
    assertNotNull(this.productRepository);
  }
  
  
  protected void geneateTestTupel() {
    this.transactionTemplate.execute((status) -> {
      ProductDTO product = DummyDataUtil.generateDummyProduct("-1", "Test Description");
      ProductDTO productDB = this.productService.save(product);
      
      ReviewDTO reviewDTO = DummyDataUtil.generateDummyReview(1).get(0);
      Review reviewDB = 
                  this.reviewService
                        .addReview(
                            productDB.getId(), 
                            ObjectMapperUtils.map(reviewDTO, Review.class));
      
      CommentDTO commentDTO = DummyDataUtil.generateDummyComment("_1");
      this.commentService
            .addComment(
                 reviewDB.getId(), 
                 ObjectMapperUtils.map(commentDTO, Comment.class));
      
      return this.productService.save(product);
    });
  }

}

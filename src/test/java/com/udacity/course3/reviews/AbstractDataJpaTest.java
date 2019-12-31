package com.udacity.course3.reviews;

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

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.service.ProductService;
import com.udacity.course3.reviews.util.DummyDataUtil;

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
  protected PlatformTransactionManager  transactionManager;
  protected TransactionTemplate         transactionTemplate;

  
  protected ProductService              productService;

  

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
    
    transactionTemplate = new TransactionTemplate(
                                  transactionManager, 
                                  new DefaultTransactionAttribute(
                                      TransactionDefinition.PROPAGATION_REQUIRES_NEW));
    
    this.productRepository.deleteAll();
 
  }
  
  @Test
  public void testNotNull() {
    assertNotNull(this.productRepository);
  }
  
  
  protected void geneateTestTupel() {
    this.transactionTemplate.execute((status) -> {
      Product product = DummyDataUtil.generateDummyProduct("-1", "Test Description");
      
      return this.productService.save(product);
    });
  }

}

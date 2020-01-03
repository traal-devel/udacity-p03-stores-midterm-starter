package com.udacity.course3.reviews.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.udacity.course3.reviews.data.dto.ProductDTO;
import com.udacity.course3.reviews.util.DummyDataUtil;

/**
 * :INFO: FetchType.LAZY does not work without transactions. 
 * Otherwise getReviews() would be null. 
 * <p>
 *  see: 
 *  - https://stackoverflow.com/questions/57767530/join-fetch-query-not-fetching-lazy-collection-in-spring-datajpatest
 *  - https://github.com/zagyi/examples/blob/master/spring-data-jpa/src/test/java/com/zagyvaib/example/spring/data/jpa/basic/repository/EmployeeRepositoryTest.java
 *  - https://github.com/spring-projects/spring-batch/blob/master/spring-batch-infrastructure-tests/src/test/java/org/springframework/batch/retry/jms/SynchronousTests.java
 *  - https://stackoverflow.com/questions/28669280/spring-transaction-not-working-as-expected-in-junit-in-non-debug-mode
 * </p>
 * @author traal-devel
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductServiceDataJpaTest extends AbstractDataJpaTest {

  
  /* member variables */

  
  /* constructors */
  public ProductServiceDataJpaTest() {
    super();
  }

  
  /* methods */
  @Test
  public void testProductSaveWithReviews() {
    // :INFO: Use transaction to ensure that entity is written really to h2 database
    // so FetchType.LAZY (-> method getReview) can work. 
    // [START] - Transaction
    this.transactionTemplate.execute((status) -> {
      ProductDTO product = DummyDataUtil.generateDummyProduct("-1", "Test Description");
      ProductDTO productDB = this.productService.save(product);
      
      assertNotNull(productDB);
      assertNotNull(productDB.getId());
      
      return productDB;
    });
    // [END] - Transaction. Use this pattern, so that getReviews() method 
    //         works correctly as a JUnit test-case.
    
  }
  
  @Test
  public void testProductSave() {
    ProductDTO product = DummyDataUtil.generateDummyProduct("-1", "Test Description");
    ProductDTO productDB = this.productService.save(product);
    
    assertNotNull(productDB);
    assertNotNull(productDB.getId());
    assertNotNull(productDB.getCreatedTime());
    assertEquals(product.getName(), productDB.getName());
    assertEquals(product.getDescription(), productDB.getDescription());
    
  }
  
  @Test
  public void testProductListEmpty() {
    List<ProductDTO> productList = this.productService.list();
    assertNotNull(productList);
    assertTrue(productList.size() == 0);
  }
  
  @Test
  public void testProductSizeEqualsOne() {
    ProductDTO product = DummyDataUtil.generateDummyProduct("_1", "Test Description");
    this.productService.save(product);
    
    List<ProductDTO> productList = this.productService.list();
    assertNotNull(product);
    assertTrue(productList.size() == 1);
  }
  
  @Test
  public void testProductSizeEqualsFive() {
    int n = 5;
    long tmpCurrentSize = this.productService.count();
    List<ProductDTO> productList = DummyDataUtil.generateDummyProductList(n);
    for (ProductDTO product : productList) {
      this.productService.save(product);
    }
    
    List<ProductDTO> productDbList = this.productService.list();
    assertNotNull(productDbList);
    assertTrue(productDbList.size() == n + tmpCurrentSize);
  }
  
  @Test
  public void testProductFindById() {
    
    ProductDTO product = DummyDataUtil.generateDummyProduct("-1", "Test Description");
    ProductDTO productDB = this.productService.save(product);
    
    ProductDTO productFindById = this.productService.findById(productDB.getId());
    
    assertNotNull(productFindById);
    assertEquals(productFindById.getId(), productDB.getId());
    
  }
  
}

package com.udacity.course3.reviews;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.service.ProductService;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductServiceTest {

  
  /* member variables */
  @Autowired 
  private ProductRepository productRepository;
  
  private ProductService    productService;
  
  /* constructors */
  public ProductServiceTest() {
    super();
  }
  
  
  /* methods */
  @Before
  public void before() {
    this.productService = new ProductService(this.productRepository);
  }
  
  @Test
  public void testNotNull() {
    assertNotNull(this.productRepository);
    assertNotNull(this.productService);
  }
  
  @Test
  public void testProductSave() {
    
    Product product = this.generateDummyProduct("-", "Test Description");
    Product productDB = this.productService.save(product);
    
    assertNotNull(productDB);
    assertNotNull(productDB.getId());
    assertNotNull(productDB.getCreatedTime());
    assertEquals(product.getName(), productDB.getName());
    assertEquals(product.getDescription(), productDB.getDescription());
    
  }
  
  @Test
  public void testProductListEmpty() {
    List<Product> productList = this.productService.list();
    assertNotNull(productList);
    assertTrue(productList.size() == 0);
  }
  
  @Test
  public void testProductSizeEqualsOne() {
    Product product = this.generateDummyProduct("_1", "Test Description");
    this.productService.save(product);
    
    List<Product> productList = this.productService.list();
    assertNotNull(product);
    assertTrue(productList.size() == 1);
  }
  
  @Test
  public void testProductSizeEqualsFive() {
    int n = 5;
    List<Product> productList = this.generateDummyProductList(n);
    for (Product product : productList) {
      this.productService.save(product);
    }
    
    List<Product> productDbList = this.productService.list();
    assertNotNull(productDbList);
    assertTrue(productDbList.size() == n);
  }
  
  @Test
  public void testProductFindById() {
    
    Product product = this.generateDummyProduct("-", "Test Description");
    Product productDB = this.productService.save(product);
    
    Product productFindById = this.productService.findById(productDB.getId());
    
    assertNotNull(productFindById);
    assertEquals(productFindById.getId(), productDB.getId());
    
  }
  
  private List<Product> generateDummyProductList(int n) {
    List<Product> productList = new ArrayList<>(n);
    
    for(int i=1; i<n+1; i++) {
      productList.add(this.generateDummyProduct("_" + i, "Test Description " + i));
    }
    
    return productList;
  }
  
  private Product generateDummyProduct(
      String suffix, 
      String description
  ) {
  
    Product product = new Product();
    
    product.setName("Product"  + suffix);
    product.setDescription(description);
    product.setCreatedTime(Timestamp.valueOf(LocalDateTime.now()));
    
    return product;
    
  }

}

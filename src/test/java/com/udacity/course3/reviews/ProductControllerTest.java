package com.udacity.course3.reviews;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.service.ProductService;
import com.udacity.course3.reviews.util.DummyDataUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class ProductControllerTest {

  
  /* member variables */
  @Autowired
  private MockMvc mvc;

  @Autowired
  private JacksonTester<Product> jsonProdcut;

  @MockBean
  private ProductService  productService;
  
  /* constructors */
  public ProductControllerTest() {
    super();
  }

  /* methods */
  @Before
  public void setup() {
    Product product = DummyDataUtil.generateDummyProduct("_1", "Product description");
    product.setId(1);
    
    Product product2 = DummyDataUtil.generateDummyProduct("_2", "Product description");
    product2.setId(2);
    
    Product product3 = DummyDataUtil.generateDummyProduct("_3", "Product description");
    product3.setId(3);
    
    given(productService.findById(any())).willReturn(product);
    given(productService.save(any())).willReturn(product);
    given(productService.list()).willReturn(Arrays.asList(product, product2, product3));
    
  }
  
  /**
   * Tests for successful creation of new car in the system
   * 
   * @throws Exception when car creation fails in the system
   */
  @Test
  public void testFindProductById() throws Exception {
    Integer productId = 1;
    mvc.perform(
        get(new URI("/products/" + productId))
          .contentType(MediaType.APPLICATION_JSON_UTF8)
          .accept(MediaType.APPLICATION_JSON_UTF8)
      )
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
      .andExpect(content().json("{}"))
      .andExpect(jsonPath("$.id", is(1)));
    ;
  }
  
  @Test
  public void testGetProductList() throws Exception {
    mvc.perform(
        get(new URI("/products/"))
          .contentType(MediaType.APPLICATION_JSON_UTF8)
          .accept(MediaType.APPLICATION_JSON_UTF8)
      )
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").isArray())
      .andExpect(jsonPath("$[0].id", is(1)))
      .andExpect(jsonPath("$[1].id", is(2)))
      .andExpect(jsonPath("$[2].id", is(3)))
    ;
  }
  
  @Test
  public void testCreateProduct() throws Exception {
    Product product = DummyDataUtil.generateDummyProduct("_1", "Product description");
    mvc.perform(
        post(new URI("/products/"))
          .content(jsonProdcut.write(product).getJson())
          .contentType(MediaType.APPLICATION_JSON_UTF8)
          .accept(MediaType.APPLICATION_JSON_UTF8)
      )
      .andExpect(status().isCreated())
    ;
  }

}

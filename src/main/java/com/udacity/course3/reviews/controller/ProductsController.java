package com.udacity.course3.reviews.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.udacity.course3.reviews.data.dto.ProductCreatingDTO;
import com.udacity.course3.reviews.data.dto.ProductDTO;
import com.udacity.course3.reviews.data.entity.Product;
import com.udacity.course3.reviews.service.ProductService;
import com.udacity.course3.reviews.service.ReviewService;
import com.udacity.course3.reviews.utils.ObjectMapperUtils;

/**
 * Spring REST controller for working with product entity.
 */
@RestController
@RequestMapping("/products")
public class ProductsController {

  
  /* member variables */
  // DONE: Wire JPA repositories here
  @Autowired
  private ProductService  prodctService;
  
  @Autowired
  private ReviewService   reviewService;
  
  
  /* constructors */
  public ProductsController() {
    super();
  }
  
  /* methods */
  /**
   * Creates a product.
   *
   * 1. [DONE] Accept product as argument. Use {@link RequestBody} annotation.
   * 2. [DONE] Save product.
   */
  @RequestMapping(
    value = "/", 
    method = RequestMethod.POST
  )
  @ResponseStatus(HttpStatus.CREATED)
  public void createProduct(
      @Valid @RequestBody ProductCreatingDTO productDTO
  ) {
  
    Product product = ObjectMapperUtils.map(productDTO, Product.class);
    this.prodctService.save(product);

  }

  /**
   * [DONE] Finds a product by id.
   *
   * @param id The id of the product [DONE].
   * @return The product if found, or a 404 not found [DONE].
   */
  @RequestMapping(
      value = "/{id}",
      method = RequestMethod.GET
  )
  public ResponseEntity<ProductDTO> findById(
      @PathVariable("id") Integer productId
  ) {
    
    // :INFO: Exception-Handling encapsulated in findById Methode (ProductService).
    Product product = this.prodctService.findById(productId);
    ProductDTO productDTO = ObjectMapperUtils.map(product, ProductDTO.class);
    
    BigDecimal avgRating = this.reviewService.calcAvgRating(productId);
    productDTO.setAverageRating(avgRating);
    
    return ResponseEntity.ok(productDTO);
        
  }

  /**
   * [DONE] Lists all products.
   *
   * @return The list of products.
   */
  @RequestMapping(
      value = "/", 
      method = RequestMethod.GET
  )
  public List<ProductDTO> listProducts() {

    return ObjectMapperUtils.mapAll(
        this.prodctService.list(), 
        ProductDTO.class
    );
  
  }
  
}
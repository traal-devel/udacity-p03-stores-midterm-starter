package com.udacity.course3.reviews.controller;

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

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.service.ProductService;

/**
 * Spring REST controller for working with product entity.
 */
@RestController
@RequestMapping("/products")
public class ProductsController {

  
  /* member variables */
  // DONE: Wire JPA repositories here
  @Autowired
  private ProductService prodctService;
  
  
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
      @Valid @RequestBody Product product
  ) {
    
    this.prodctService.save(product);
    
  }

  /**
   * [DONE] Finds a product by id.
   *
   * @param id The id of the product [DONE].
   * @return The product if found, or a 404 not found [DONE].
   */
  @RequestMapping(value = "/{id}")
  public ResponseEntity<Product> findById(
      @PathVariable("id") Integer id
  ) {
    
    // :INFO: Exception-Handling encapsulated in findById Methode (ProductService).
    Product product = this.prodctService.findById(id);
    return ResponseEntity.ok(product);
        
  }

  /**
   * [DONE] Lists all products.
   *
   * @return The list of products.
   */
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public List<Product> listProducts() {
    return this.prodctService.list();
  }
  
}
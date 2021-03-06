package com.udacity.course3.reviews.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.course3.reviews.data.dto.ProductDTO;
import com.udacity.course3.reviews.data.entity.Product;
import com.udacity.course3.reviews.ex.ProductNotFoundException;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.utils.ObjectMapperUtils;

/**
 * Implementation of the product service using the jpa repository.
 * 
 * @author traal-devel
 */
@Service
public class ProductService {

  
  /* member variables */
  @Autowired
  private ProductRepository productRepository;

  
  /* constructors */
  public ProductService() {
    super();
  }
  
  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  
  /* methods */
  /**
   * Finds the product by the given id.
   * 
   * @param id Long
   * @return Product - Found product or else ProductNotFoundException 
   */
  public ProductDTO findById(
      Integer id
  ) {
    
     Product product =
         this.productRepository
            .findById(id)
            .orElseThrow(ProductNotFoundException::new);
     return ObjectMapperUtils.map(product, ProductDTO.class);
     
  }
  
  /**
   * Inserts or updates the given product.
   * 
   * @param product Product
   * @return Product - Product from the databae or else ProductNotFoundException
   */
  public ProductDTO save(
      ProductDTO productDTO
  ) {
    Product product = ObjectMapperUtils.map(productDTO, Product.class);
    Product productDB = null;
    
    if (product.getId() != null) {
      productDB = 
          this.productRepository.findById(product.getId())
              .map(productToBeUpdated -> {
                productToBeUpdated.setName(product.getName());
                productToBeUpdated.setDescription(product.getDescription());
                return productRepository.save(productToBeUpdated);
              }).orElseThrow(ProductNotFoundException::new);
      
      
    } else {
      productDB = productRepository.save(product);
    }

    return ObjectMapperUtils.map(productDB, ProductDTO.class);
    
  }

  /**
   * Returns the 
   * @return
   */
  public List<ProductDTO> list() {
    
    List<Product> productList = this.productRepository.findAll();
    return ObjectMapperUtils.mapAll(productList, ProductDTO.class);

  }
  
  public long count() {
    return this.productRepository.count();
  }

}

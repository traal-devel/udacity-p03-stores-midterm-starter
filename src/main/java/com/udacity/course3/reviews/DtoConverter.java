package com.udacity.course3.reviews;

import java.text.ParseException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.udacity.course3.reviews.dto.ProductDTO;
import com.udacity.course3.reviews.entity.Product;

/**
 * POC - DTO Util
 * 
 * Source:
 * - https://www.baeldung.com/entity-to-and-from-dto-for-a-java-spring-application
 * 
 * @author traal-devel
 */
@Component
public class DtoConverter {

  
  /* member variables */
  @Autowired
  private ModelMapper modelMapper;

  
  /* constructors */
  private DtoConverter() {
    super();
  }

  
  /* methods */
  public ProductDTO convertToDto(Product product) {
    ProductDTO postDto = modelMapper.map(product, ProductDTO.class);
   
    return postDto;
  }
  
  public Product convertToEntity(ProductDTO productDTO) throws ParseException {
    Product product = modelMapper.map(productDTO, Product.class);
  
    return product;
  }

}

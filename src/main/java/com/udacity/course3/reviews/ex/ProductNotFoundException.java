package com.udacity.course3.reviews.ex;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
  code = HttpStatus.NOT_FOUND, 
  reason = "Product not found"
)
public class ProductNotFoundException 
       extends RuntimeException {

  

  /* constants */
  /** Added by eclipse */
  private static final long serialVersionUID = -6036450267455361684L;

  
  /* member variables */

  
  /* constructors */
  public ProductNotFoundException() {
    super();
  }
  
  public ProductNotFoundException(String msg) {
    super(msg);
  }

  
  /* methods */

}

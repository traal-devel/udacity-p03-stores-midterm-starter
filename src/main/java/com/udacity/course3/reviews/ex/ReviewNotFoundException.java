package com.udacity.course3.reviews.ex;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
  code = HttpStatus.NOT_FOUND, 
  reason = "Review not found"
)
public class ReviewNotFoundException extends RuntimeException {

  
  /* constants */
  /** Added by eclipse */
  private static final long serialVersionUID = -1225689860176169014L;

  
  /* member variables */

  
  /* constructors */
  public ReviewNotFoundException() {
    super();
  }
  
  public ReviewNotFoundException(String msg) {
    super(msg);
  }

  
  /* methods */

}

package com.udacity.course3.reviews;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootApplication
@EnableJpaRepositories
public class ReviewsApplication {

  
  /* member variables */
  
  
  /* constructors */
  public ReviewsApplication() {
    super();
  }
  
  
  /* methods */
	public static void main(String[] args) {
		SpringApplication.run(ReviewsApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
    return new ModelMapper();
	}
	
//	@Bean
//	public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
//    ObjectMapper objectMapper = builder.createXmlMapper(false).build();
//    objectMapper.registerModule(new Jdk8Module());
//    objectMapper.registerModule(new JavaTimeModule());
//    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
////      objectMapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
//    return objectMapper;
//  }
	
}
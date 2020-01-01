package com.udacity.course3.reviews;

import org.bson.types.ObjectId;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

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
	
	/**
	 * Gets the MappingJackson2HttpMessageConverter.
	 * <p>
	 * Add a builder so (mongo-db) ObjectId is serialized to a String. 
	 * </p>
	 * 
	 * @return {@link MappingJackson2HttpMessageConverter}
	 */
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
	  Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
    builder.serializerByType(ObjectId.class, new ToStringSerializer());
    MappingJackson2HttpMessageConverter converter = 
        new MappingJackson2HttpMessageConverter(builder.build());
	 
	 return converter;
	}
	
}
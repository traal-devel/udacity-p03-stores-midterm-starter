package com.udacity.course3.reviews.utils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;

/**
 * ObjectMapperUtils by Andrew Nepogoda. 
 * 
 * <b>Proof of concept.</b>
 * 
 * <p>
 * Source:<br />
 * https://stackoverflow.com/questions/47929674/modelmapper-mapping-list-of-entites-to-list-of-dto-objects
 * </p>
 * <p> 
 * Usage:
 * <pre>
 * List<PostDTO> listOfPostDTO = ObjectMapperUtils.mapAll(listOfPosts, PostDTO.class);
 * </pre>
 * </p>
 */
public class ObjectMapperUtils {

  
  /* constants */
  private static ModelMapper modelMapper = new ModelMapper();

  
  /* static constructor */
  /**
   * Model mapper property setting are specified in the following block.
   * Default property matching strategy is set to Strict see {@link MatchingStrategies}
   * Custom mappings are added using {@link ModelMapper#addMappings(PropertyMap)}
   */
  static {
    modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
  }
  

  /* constructors */
  /**
   * Hide from public usage.
   */
  private ObjectMapperUtils() {
    super();
  }

  
  /* methods */
  /**
   * <p>Note: outClass object must have default constructor with no arguments</p>
   *
   * @param <D>      type of result object.
   * @param Object   type of source object to map from.
   * @param entity   entity that needs to be mapped.
   * @param outClass class of result object.
   * @return new object of <code>outClass</code> type.
   */
  public static <D> D map(
      final Object entity, 
      final Class<D> outClass
  ) {
    
    return modelMapper.map(entity, outClass);
    
  }

  /**
   * <p>Note: outClass object must have default constructor with no arguments</p>
   * 
   *
   * @param entityList list of entities that needs to be mapped
   * @param outCLass   class of result list element
   * @param <D>        type of objects in result list
   * @param <T>        type of entity in <code>entityList</code>
   * @return list of mapped object with <code><D></code> type. 
   */
  public static <D, T> List<D> mapAll(
      final Collection<T> entityList, 
      final Class<D> outCLass
  ) {
    
    // :INFO: Check for null. If null return null back.
    return entityList == null ? 
              null :
              entityList
                .stream()
                .map(entity -> map(entity, outCLass))
                .collect(Collectors.toList());
    
  }

  /**
   * Maps {@code source} to {@code destination}.
   *
   * @param source      object to map from
   * @param destination object to map to
   */
  public static <S, D> D map(
      final S source, 
      D destination
  ) {
    
    modelMapper.map(source, destination);
    return destination;
    
  }
  
}
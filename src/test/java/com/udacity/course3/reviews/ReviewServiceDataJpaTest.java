package com.udacity.course3.reviews;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.util.DummyDataUtil;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ReviewServiceDataJpaTest extends AbstractDataJpaTest {

  
  /* member variables */

  
  /* constructors */
  public ReviewServiceDataJpaTest() {
    super();
  }
  
  
  /* methods */
  @Before
  public void init() {
    // Generate test data.
    this.geneateTestTupel();
  }

  /**
   * :INFO: Transaction template is used so 'mvn clean package' can work. 
   */
  @Test
  public void testFindByProductId() {
    this.transactionTemplate.execute((status) -> {
      List<Review> reviewList = this.reviewService.findByProductId(1);
      assertNotNull(reviewList);
      assertTrue("Size of list not correct. Should be 1 but is " + reviewList.size() ,
          reviewList.size() == 1);
      
      return null;
    });
  }
  
  @Test
  public void testAddReview() {
    this.transactionTemplate.execute((status) -> {
      Integer productId = 1;
      Review reviewDummy = DummyDataUtil.generateDummyReview(1).get(0);
      Review reviewDB = this.reviewService.addReview(productId, reviewDummy);
      
      assertNotNull(reviewDB);
      assertNotNull(reviewDB.getId());
      assertEquals(reviewDummy.getAuthor(), reviewDB.getAuthor());
      assertEquals(reviewDummy.getBody(), reviewDB.getBody());
      assertEquals(reviewDummy.getTitle(), reviewDB.getTitle());

      return null;
    });
  }
  
}

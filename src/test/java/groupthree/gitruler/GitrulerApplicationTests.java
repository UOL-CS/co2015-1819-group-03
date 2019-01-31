package groupthree.gitruler;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * This class contains the JUnit tests that test the application.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GitrulerApplicationTests {

  @Test
  public void contextLoads() {
  }

  @Test
  public void assertsTrue() {
    assertTrue(true);
  }

}

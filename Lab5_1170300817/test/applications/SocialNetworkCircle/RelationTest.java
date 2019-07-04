package applications.SocialNetworkCircle;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import exception.sameLabelException;

public class RelationTest {
  @Test
  public void relationException1Test() {
    try {
      new relationKeeper("aaa", "bbb", 0.1);
      new relationKeeper("aaa", "bbb", 0.1);
    } catch (sameLabelException e) {
      assertTrue(e.getMessage().contains("边已经存在"));
    }
  }

  @Test
  public void relationException2Test() {
    try {
      new relationKeeper("aaa", "aaa", 0.1);
    } catch (sameLabelException e) {
      assertTrue(e.getMessage().contains("边无效"));
    }
  }

  @Test
  public void relationException3Test() {
    try {
      new relationKeeper("bbb", "aaa", 0.1);
    } catch (sameLabelException e) {
      assertTrue(e.getMessage().contains("边已经存在"));
    }
  }
}

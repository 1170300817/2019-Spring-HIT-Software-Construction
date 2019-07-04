package applications.SocialNetworkCircle;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import exception.illegalParameterException;
import exception.sameLabelException;

public class SocialNetCircularOrbitTest {
  // Testing strategy:
  // 补测一些子类方法
  SocialNetCircularOrbit orbit = new SocialNetCircularOrbit();

  @Test
  public void getIntimacyTest() throws sameLabelException, illegalParameterException {
    Person center = Person.getInstance("xxx", 20, "M");
    Person p1 = Person.getInstance("aa", 20, "M");
    Person p2 = Person.getInstance("bb", 20, "M");

    orbit.setCentralObject(center);
    orbit.addcentralRelation(p1, 0.5);
    orbit.addtrackRelation(p1, p2, 0.5);
    orbit.reArrange();
    assertTrue(orbit.getIntimacy(p1) == 0.75);
  }

}

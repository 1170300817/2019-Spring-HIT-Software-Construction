package phsicalObject;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class PhsicalObjectTest {
  // Testing strategy:
  // 覆盖所有重要方法，尽可能覆盖所有分支
  @Test
  public void ObjectTest() {
    PhysicalObject physicalObject = new PhysicalObject("aaa");
    assertEquals("aaa", physicalObject.getName());

  }
}

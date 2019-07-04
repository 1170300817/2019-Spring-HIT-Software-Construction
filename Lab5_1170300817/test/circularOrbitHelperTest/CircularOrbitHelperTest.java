
package circularOrbitHelperTest;

import centralObject.CentralObject;
import circularOrbit.CircularOrbit;
import circularOrbit.ConcreteCircularOrbit;
import circularOrbitHelper.CircularOrbitAPIs;
import difference.Difference;
import phsicalObject.PhysicalObject;
import track.Track;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class CircularOrbitHelperTest {
  // Testing strategy:
  // 覆盖所有方法，尽可能覆盖所有分支

  CircularOrbit<CentralObject, PhysicalObject> circular =
      new ConcreteCircularOrbit<CentralObject, PhysicalObject>();
  CentralObject center = new CentralObject("sun");
  Track t1 = new Track("track1", 100);
  Track t2 = new Track("track2", 200);
  Track t3 = new Track("track3", 300);
  PhysicalObject ob1 = new PhysicalObject("object1");
  PhysicalObject ob2 = new PhysicalObject("object2");
  PhysicalObject ob3 = new PhysicalObject("object3");

  @Test
  public void OrbitTestAPI1() {
    circular.setCentralObject(center);
    circular.addTrack(t1);
    circular.addTrack(t2);
    circular.addTrack(t3);
    circular.addObjectToTrack(t1, ob1);
    circular.addObjectToTrack(t2, ob2);
    circular.addObjectToTrack(t3, ob3);
    CircularOrbitAPIs.visualize(circular);

  }

  @Test
  public void OrbitTestAPI2() {
    CircularOrbit<CentralObject, PhysicalObject> circular1 =
        new ConcreteCircularOrbit<CentralObject, PhysicalObject>();
    circular.addTrack(t1);
    circular.addTrack(t2);
    circular.addTrack(t3);
    circular1.addTrack(t1);
    circular1.addTrack(t2);
    circular.addObjectToTrack(t1, ob1);
    circular.addObjectToTrack(t2, ob2);
    circular.addObjectToTrack(t3, ob3);
    circular1.addObjectToTrack(t1, ob1);
    circular1.addObjectToTrack(t2, ob3);
    Difference diffTest = CircularOrbitAPIs.getDifference(circular, circular1);
    assertTrue(diffTest.toString().contains("轨道数差异：1"));
    assertTrue(diffTest.toString().contains("轨道2的物体差异：[object2]-[object3]"));
    assertTrue(diffTest.toString().contains("轨道3的物体差异：[object3]-[]"));
  }


}

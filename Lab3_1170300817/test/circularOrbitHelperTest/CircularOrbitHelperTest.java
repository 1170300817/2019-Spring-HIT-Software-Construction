
package circularOrbitHelperTest;

import centralObject.CentralObject;
import circularOrbit.CircularOrbit;
import circularOrbit.ConcreteCircularOrbit;
import circularOrbitHelper.CircularOrbitAPIs;
import phsicalObject.PhysicalObject;
import track.Track;

import org.junit.Test;

public class CircularOrbitHelperTest {
	// Testing strategy:
	// 覆盖所有方法，尽可能覆盖所有分支
	
	CircularOrbit<CentralObject, PhysicalObject> circular = new ConcreteCircularOrbit<CentralObject, PhysicalObject>();
	CentralObject center = new CentralObject("sun");
	Track t1 = new Track("track1", 100);
	Track t2 = new Track("track2", 200);
	Track t3 = new Track("track3", 300);
	PhysicalObject ob1 = new PhysicalObject("object1");
	PhysicalObject ob2 = new PhysicalObject("object2");
	PhysicalObject ob3 = new PhysicalObject("object3");

	@Test
	public void OrbitTest1() {
		circular.setCentralObject(center);
		circular.addTrack(t1);
		circular.addTrack(t2);
		circular.addTrack(t3);
		CircularOrbitAPIs.visualize(circular);

	}

}
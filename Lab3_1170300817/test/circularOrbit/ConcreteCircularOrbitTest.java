package circularOrbit;

import centralObject.CentralObject;
import difference.Difference;
import phsicalObject.PhysicalObject;
import track.Track;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ConcreteCircularOrbitTest {

	// Testing strategy:
	// 覆盖所有重要方法，尽可能覆盖所有分支
	CircularOrbit<CentralObject, PhysicalObject> circular = new ConcreteCircularOrbit<CentralObject, PhysicalObject>();
	CentralObject center = new CentralObject("sun");
	Track t1 = new Track("track1", 100);
	Track t2 = new Track("track2", 200);
	Track t3 = new Track("track3", 300);
	Track t4 = new Track("track4", 400);
	PhysicalObject ob1 = new PhysicalObject("object1");
	PhysicalObject ob2 = new PhysicalObject("object2");
	PhysicalObject ob3 = new PhysicalObject("object3");
	PhysicalObject ob4 = new PhysicalObject("object4");
	PhysicalObject ob5 = new PhysicalObject("object5");

	@Test
	public void setCentralObjectTest() {
		circular.setCentralObject(center);
		assertEquals("sun", circular.getCentralObject().getName());
	}

	@Test
	public void addTrackTest() {
		assertTrue(circular.addTrack(t1));
		assertFalse(circular.addTrack(t1));
	}

	@Test
	public void removeTrackTest() {
		assertTrue(circular.addTrack(t1));
		assertTrue(circular.removeTrack(t1));
		assertFalse(circular.removeTrack(t1));
	}

	@Test
	public void getTrackNumTest() {
		circular.addTrack(t1);
		circular.addTrack(t2);
		circular.addTrack(t3);
		assertEquals((Integer) 3, circular.getTrackNum());
		circular.removeTrack(t3);
		assertEquals((Integer) 2, circular.getTrackNum());
	}

	@Test
	public void getObjectNumonTrackTest() {
		circular.setCentralObject(center);
		circular.addTrack(t1);
		circular.addTrack(t2);
		circular.addTrack(t3);
		circular.addObjectToTrack(t1, ob1);
		circular.addObjectToTrack(t2, ob2);
		circular.addObjectToTrack(t3, ob3);
		assertEquals((Integer) 1, circular.getObjectNumonTrack(t1));
		circular.removeTrack(t3);
		circular.addTrack(t3);
		assertEquals((Integer) 0, circular.getObjectNumonTrack(t3));
	}

	@Test
	public void addtrackRelationTest() {
		circular.addTrack(t1);
		circular.addTrack(t2);
		circular.addTrack(t3);
		circular.addTrack(t4);
		circular.addObjectToTrack(t1, ob1);
		circular.addObjectToTrack(t2, ob2);
		circular.addObjectToTrack(t3, ob3);
		circular.addObjectToTrack(t4, ob4);
		circular.addObjectToTrack(t1, ob5);
		circular.addtrackRelation(ob1, ob2, 1);
		circular.addtrackRelation(ob1, ob3, 1);
		circular.addtrackRelation(ob1, ob4, 1);
		circular.addtrackRelation(ob1, ob5, 1);
		assertTrue(circular.getTrackConnectedObject(ob1).contains(ob2));
		assertTrue(circular.getTrackConnectedObject(ob1).contains(ob3));
		assertTrue(circular.getTrackConnectedObject(ob1).contains(ob4));
		assertTrue(circular.getTrackConnectedObject(ob1).contains(ob5));
		assertEquals(1, circular.getLogicalDistance(ob1, ob2));
		assertEquals(1, circular.getLogicalDistance(ob1, ob3));
	}

	@Test
	public void addcentralRelationTest() {
		circular.setCentralObject(center);
		circular.addTrack(t1);

		circular.addObjectToTrack(t1, ob1);
		circular.addObjectToTrack(t1, ob2);
		circular.addObjectToTrack(t1, ob3);
		circular.addObjectToTrack(t1, ob4);

		circular.addcentralRelation(ob1, 1);
		circular.addcentralRelation(ob2, 1);
		circular.addcentralRelation(ob3, 1);
		circular.addcentralRelation(ob4, 1);
		assertTrue(circular.getCentralConnectedObject().contains(ob1));
		assertTrue(circular.getCentralConnectedObject().contains(ob2));
		assertTrue(circular.getCentralConnectedObject().contains(ob3));
		assertTrue(circular.getCentralConnectedObject().contains(ob4));
		circular.addcentralRelation(ob1, 0);
		circular.addcentralRelation(ob2, 0);
		assertTrue(!circular.getCentralConnectedObject().contains(ob1));
		assertTrue(!circular.getCentralConnectedObject().contains(ob2));
	}

	@Test
	public void getDifferenceTest() {
		CircularOrbit<CentralObject, PhysicalObject> circular1 = new ConcreteCircularOrbit<CentralObject, PhysicalObject>();

		circular.addTrack(t1);
		circular.addTrack(t2);
		circular.addTrack(t3);
		Track t4 = new Track("track4", 400);
		PhysicalObject ob4 = new PhysicalObject("object4");
		circular1.addTrack(t1);
		circular1.addTrack(t2);
		circular1.addTrack(t3);
		circular1.addTrack(t4);

		circular.addObjectToTrack(t1, ob1);
		circular.addObjectToTrack(t2, ob2);
		circular.addObjectToTrack(t3, ob3);

		circular1.addObjectToTrack(t1, ob1);
		circular1.addObjectToTrack(t2, ob2);
		circular1.addObjectToTrack(t3, ob3);
		circular1.addObjectToTrack(t4, ob4);
		Difference diffTest = circular.getDifference(circular1);

		System.out.println(diffTest.toString());
		// 观察验证

	}

	@Test
	public void getObjectDistributionEntropyTest() {
		CircularOrbit<CentralObject, PhysicalObject> circular1 = new ConcreteCircularOrbit<CentralObject, PhysicalObject>();
		CircularOrbit<CentralObject, PhysicalObject> circular2 = new ConcreteCircularOrbit<CentralObject, PhysicalObject>();

		circular.addTrack(t1);
		circular.addTrack(t2);
		circular.addTrack(t3);
		circular1.addTrack(t1);
		circular1.addTrack(t2);
		circular1.addTrack(t3);
		circular2.addTrack(t1);
		circular2.addTrack(t2);
		circular2.addTrack(t3);

		circular.addObjectToTrack(t1, ob1);
		circular.addObjectToTrack(t2, ob2);
		circular.addObjectToTrack(t3, ob3);

		circular1.addObjectToTrack(t1, ob1);
		circular1.addObjectToTrack(t1, ob2);
		circular1.addObjectToTrack(t1, ob3);

		circular2.addObjectToTrack(t1, ob1);
		circular2.addObjectToTrack(t1, ob2);
		circular2.addObjectToTrack(t2, ob3);

		assertTrue(circular.getObjectDistributionEntropy() > circular1.getObjectDistributionEntropy());
		assertTrue(circular.getObjectDistributionEntropy() > circular2.getObjectDistributionEntropy());
		assertTrue(circular2.getObjectDistributionEntropy() > circular1.getObjectDistributionEntropy());

	}

	@Test
	public void getObjectonTrack() {
		circular.setCentralObject(center);
		circular.addTrack(t1);
		circular.addTrack(t2);
		circular.addTrack(t3);
		circular.addObjectToTrack(t1, ob1);
		circular.addObjectToTrack(t1, ob2);
		circular.addObjectToTrack(t2, ob3);
		assertTrue(circular.getObjectonTrack(t1).contains(ob1));
		assertTrue(circular.getObjectonTrack(t1).contains(ob2));
		assertTrue(!circular.getObjectonTrack(t1).contains(ob3));
	}

	@Test
	public void contains() {
		circular.setCentralObject(center);
		circular.addTrack(t1);
		circular.addTrack(t2);
		circular.addTrack(t3);
		circular.addObjectToTrack(t1, ob1);
		circular.addObjectToTrack(t1, ob2);
		circular.addObjectToTrack(t2, ob3);
		assertTrue(circular.contains(ob1));
		assertTrue(circular.contains(ob2));
		assertTrue(circular.contains(ob3));
	}

	@Test
	public void getObjectTrackTest() {
		circular.setCentralObject(center);
		circular.addTrack(t1);
		circular.addTrack(t2);
		circular.addTrack(t3);
		circular.addObjectToTrack(t1, ob1);
		circular.addObjectToTrack(t1, ob2);
		circular.addObjectToTrack(t2, ob3);
		assertEquals(circular.getObjectTrack(ob1), t1);
		assertEquals(circular.getObjectTrack(ob2), t1);
		assertEquals(circular.getObjectTrack(ob3), t2);
	}
	

}

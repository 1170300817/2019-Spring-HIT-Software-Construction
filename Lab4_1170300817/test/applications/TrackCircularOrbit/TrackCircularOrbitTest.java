package applications.TrackCircularOrbit;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import applications.TrackGame.Athlete;
import applications.TrackGame.TrackCircularOrbit;
import exception.illegalParameterException;
import exception.sameLabelException;
import phsicalObject.PhysicalObject;
import track.Track;

public class TrackCircularOrbitTest {
	// Testing strategy:
	// 补测一些子类方法
	TrackCircularOrbit orbit = new TrackCircularOrbit();
	Track t1 = new Track("track1", 100);
	Track t2 = new Track("track2", 200);
	Track t3 = new Track("track3", 300);
	

	@Test
	public void toStringTest() throws sameLabelException, illegalParameterException {
		Athlete a1 = Athlete.getInstance("aa", 1, "AAA", 11, 1);
		Athlete a2 = Athlete.getInstance("bb", 2, "AAA", 11, 2);
		Athlete a3 = Athlete.getInstance("cc", 3, "AAA", 11, 3);
		orbit.addTrack(t1);
		orbit.addTrack(t2);
		orbit.addTrack(t3);
		orbit.addObjectToTrack(t1, a1);
		orbit.addObjectToTrack(t2, a2);
		orbit.addObjectToTrack(t3, a3);
		System.out.println(orbit.toString());
		assertTrue(orbit.toString().contains("track1:aa"));
		assertTrue(orbit.toString().contains("最好成绩:1.0"));
	}
	@Test
	public void checkRepTest() throws sameLabelException, illegalParameterException {
		Athlete a1 = Athlete.getInstance("dd", 1, "AAA", 11, 1);
		Athlete a2 = Athlete.getInstance("ee", 2, "AAA", 11, 2);
		Athlete a3 = Athlete.getInstance("ff", 3, "AAA", 11, 3);
		Track t4 = new Track("track4", 300);
		orbit.addTrack(t1);
		orbit.addTrack(t2);
		orbit.addTrack(t3);
		orbit.addTrack(t4);
		orbit.addObjectToTrack(t1, a1);
		orbit.addObjectToTrack(t2, a2);
		orbit.addObjectToTrack(t3, a3);
		assertTrue(orbit.checkRep());
	}

}

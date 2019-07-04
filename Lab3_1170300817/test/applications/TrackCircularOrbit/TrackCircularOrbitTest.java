package applications.TrackCircularOrbit;

import org.junit.Test;

import applications.TrackGame.Athlete;
import applications.TrackGame.TrackCircularOrbit;
import phsicalObject.PhysicalObject;
import track.Track;

public class TrackCircularOrbitTest {
	// Testing strategy:
	// 补测一些子类方法
	TrackCircularOrbit orbit = new TrackCircularOrbit();
	PhysicalObject center=new PhysicalObject(null);
	Track t1 = new Track("track1", 100);
	Track t2 = new Track("track2", 200);
	Track t3 = new Track("track3", 300);
	Athlete a1 = Athlete.getInstance("aa", 1, "AAA", 11, 1);
	Athlete a2 = Athlete.getInstance("aa", 1, "AAA", 11, 2);
	Athlete a3 = Athlete.getInstance("aa", 1, "AAA", 11, 3);

	@Test
	public void toStringTest() {
		orbit.setCentralObject(center);
		orbit.addTrack(t1);
		orbit.addTrack(t2);
		orbit.addTrack(t3);
		orbit.addObjectToTrack(t1, a1);
		orbit.addObjectToTrack(t2, a2);
		orbit.addObjectToTrack(t3, a3);
		System.out.println(orbit.toString());
	}

}

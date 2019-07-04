package applications.AtomStructure;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import track.Track;

public class AtomCircularOrbitTest {
	// Testing strategy:
	// 补测一些子类方法
	AtomCircularOrbit atomCircularOrbit=new AtomCircularOrbit();
	Track t1 = new Track("track1", 100);
	Track t2 = new Track("track2", 200);
	Track t3 = new Track("track3", 300);
	Particle e1=Particle.getElectron();
	Particle e2=Particle.getElectron();
	Particle e3=Particle.getElectron();
	@Test
	public void transitTest() {
		atomCircularOrbit.addTrack(t1);
		atomCircularOrbit.addTrack(t2);
		atomCircularOrbit.addTrack(t3);
		atomCircularOrbit.addObjectToTrack(t1, e1);
		atomCircularOrbit.addObjectToTrack(t2, e2);
		atomCircularOrbit.addObjectToTrack(t3, e3);
		atomCircularOrbit.transit(t1, t2);
		assertTrue(atomCircularOrbit.getObjectNumonTrack(t2)==2);
	}
	@Test
	public void removeElectronTest() {
		atomCircularOrbit.addTrack(t1);
		atomCircularOrbit.addTrack(t2);
		atomCircularOrbit.addTrack(t3);
		atomCircularOrbit.addObjectToTrack(t1, e1);
		atomCircularOrbit.addObjectToTrack(t2, e2);
		atomCircularOrbit.addObjectToTrack(t3, e3);
		atomCircularOrbit.removeElectron(t1);
		atomCircularOrbit.removeElectron(t2);
		assertTrue(atomCircularOrbit.getObjectNumonTrack(t1)==0);
		assertTrue(atomCircularOrbit.getObjectNumonTrack(t2)==0);
	}
	
	
}

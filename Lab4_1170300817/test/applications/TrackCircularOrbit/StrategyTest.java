package applications.TrackCircularOrbit;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import applications.TrackGame.Athlete;
import applications.TrackGame.TrackCircularOrbit;
import applications.TrackGame.TrackCircularOrbitBuilder;
import applications.TrackGame.Strategy.RandomStrategy;
import applications.TrackGame.Strategy.RecordStrategy;
import applications.TrackGame.Strategy.Strategy;
import exception.illegalParameterException;
import exception.sameLabelException;
import track.Track;

public class StrategyTest {
	@Test
	public void randomTest() throws sameLabelException, illegalParameterException {
		List<Athlete> athleteList = new LinkedList<>();
		  TrackCircularOrbitBuilder trackBuilder = new TrackCircularOrbitBuilder();

		Athlete a1 = Athlete.getInstance("HH", 1, "AAA", 11, 1.00);
		Athlete a2 = Athlete.getInstance("JJ", 2, "AAA", 11, 2.00);
		Athlete a3 = Athlete.getInstance("KK", 3, "AAA", 11, 3.00);
		Athlete a4 = Athlete.getInstance("LL", 4, "AAA", 11, 3.00);
		Athlete a5 = Athlete.getInstance("MM", 5, "AAA", 11, 3.00);
		Athlete a6 = Athlete.getInstance("NN", 6, "AAA", 11, 3.00);
		Athlete a7 = Athlete.getInstance("OO", 7, "AAA", 11, 3.00);
		Athlete a8 = Athlete.getInstance("PP", 8, "AAA", 11, 3.00);
		Athlete a9 = Athlete.getInstance("QQ", 9, "AAA", 11, 3.00);
		athleteList.add(a1);
		athleteList.add(a2);
		athleteList.add(a3);
		athleteList.add(a4);
		athleteList.add(a5);
		athleteList.add(a6);
		athleteList.add(a7);
		athleteList.add(a8);
		athleteList.add(a9);
		List<Track> trackList = new ArrayList<Track>();
		Strategy strategy1 = new RandomStrategy();
		int[] DefaultRadius = new int[8];
		for (int i = 0; i < 8; i++) {
			DefaultRadius[i] = 50 + 100 * i;
		}
		for (int i = 0; i < 5; i++) {
			trackList.add(new Track("tarck" + i, DefaultRadius[i]));
		}
		List<Map<Track, List<Athlete>>> arrangementMap = strategy1.Arrange(new ArrayList<>(athleteList), trackList);
		List<TrackCircularOrbit> trackOrbitList = new LinkedList<>();
		for (int i = 0; i < 2; i++) {
			Map<Track, List<Athlete>> currentMap = arrangementMap.get(i);
			trackBuilder.createCircularOrbit();
			trackBuilder.bulidTracks(trackList);
			trackBuilder.bulidPhysicalObjects(currentMap);
			TrackCircularOrbit newOrbit = (TrackCircularOrbit) trackBuilder.getConcreteCircularOrbit();
			trackOrbitList.add(newOrbit);
		}
//		System.out.println(trackOrbitList.get(0).toString());
//		System.out.println(trackOrbitList.get(1).toString());
		assertTrue(trackOrbitList.get(0).checkRep());
		assertTrue(trackOrbitList.get(1).checkRep());
	}
	@Test
	public void orderTest() throws sameLabelException, illegalParameterException {
		List<Athlete> athleteList = new LinkedList<>();
		  TrackCircularOrbitBuilder trackBuilder = new TrackCircularOrbitBuilder();

		Athlete a1 = Athlete.getInstance("HHa", 1, "AAA", 11, 1.00);
		Athlete a2 = Athlete.getInstance("JJa", 2, "AAA", 11, 2.00);
		Athlete a3 = Athlete.getInstance("KKa", 3, "AAA", 11, 3.00);
		Athlete a4 = Athlete.getInstance("LLa", 4, "AAA", 11, 3.10);
		Athlete a5 = Athlete.getInstance("MMa", 5, "AAA", 11, 3.20);
		Athlete a6 = Athlete.getInstance("NNa", 6, "AAA", 11, 3.30);
		Athlete a7 = Athlete.getInstance("OOa", 7, "AAA", 11, 3.40);
		Athlete a8 = Athlete.getInstance("PPa", 8, "AAA", 11, 3.50);
		Athlete a9 = Athlete.getInstance("QQa", 9, "AAA", 11, 3.60);
		athleteList.add(a1);
		athleteList.add(a2);
		athleteList.add(a3);
		athleteList.add(a4);
		athleteList.add(a5);
		athleteList.add(a6);
		athleteList.add(a7);
		athleteList.add(a8);
		athleteList.add(a9);
		List<Track> trackList = new ArrayList<Track>();
		Strategy strategy1 = new RecordStrategy();
		int[] DefaultRadius = new int[8];
		for (int i = 0; i < 8; i++) {
			DefaultRadius[i] = 50 + 100 * i;
		}
		for (int i = 0; i < 5; i++) {
			trackList.add(new Track("tarck" + i, DefaultRadius[i]));
		}
		List<Map<Track, List<Athlete>>> arrangementMap = strategy1.Arrange(new ArrayList<>(athleteList), trackList);
		List<TrackCircularOrbit> trackOrbitList = new LinkedList<>();
		for (int i = 0; i < 2; i++) {
			Map<Track, List<Athlete>> currentMap = arrangementMap.get(i);
			trackBuilder.createCircularOrbit();
			trackBuilder.bulidTracks(trackList);
			trackBuilder.bulidPhysicalObjects(currentMap);
			TrackCircularOrbit newOrbit = (TrackCircularOrbit) trackBuilder.getConcreteCircularOrbit();
			trackOrbitList.add(newOrbit);
		}
		
		assertTrue(trackOrbitList.get(0).checkRep());
		assertTrue(trackOrbitList.get(1).checkRep());
		
		
	}
}

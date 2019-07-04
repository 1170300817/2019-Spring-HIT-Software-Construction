package applications.TrackGame.Strategy;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import applications.TrackGame.Athlete;
import track.Track;

public class Relay4Strategy implements Strategy {

	@Override
	public List<Map<Track, List<Athlete>>> Arrange(List<Athlete> athleteList, List<Track> tracklist) {
		List<Map<Track, List<Athlete>>> arrangementMapList = new LinkedList<>();
		int OrbitNum = (int) ((athleteList.size()/4) / tracklist.size()) + 1;// OrbitµÄ¸öÊý

		for (int i = 0; i < OrbitNum; i++) {
			arrangementMapList.add(new HashMap<>());
			for (int j = 0; j < tracklist.size(); j++) {
				arrangementMapList.get(i).put(tracklist.get(j), new LinkedList<Athlete>());
			}
		}
		Random random = new Random();
		int i = 0;
		int j = 0;
		int n = 0;
		int num=0;
		for (i = 0; i < OrbitNum; i++) {
			j = 0;
			Map<Track, List<Athlete>> currentMap = arrangementMapList.get(i);
			while (j < tracklist.size() ) {
				while ( !athleteList.isEmpty()&&num<4) {
					n = random.nextInt(athleteList.size());
					currentMap.get(tracklist.get(j)).add(athleteList.get(n));
					athleteList.remove(n);
					num++;
				}
				j++;
				num=0;
			}
		}
//		System.out.println(arrangementMapList.size());
		return arrangementMapList;
	}
}

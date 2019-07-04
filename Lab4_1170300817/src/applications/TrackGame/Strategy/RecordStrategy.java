package applications.TrackGame.Strategy;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import applications.TrackGame.Athlete;
import track.Track;

public class RecordStrategy implements Strategy {
	/**
	 * 排序策略
	 * 
	 * @param runnerlist 运动员列表
	 * @param tracklist  赛道列表
	 * @return 排序策略Map
	 */
	@Override
	public List<Map<Track, List<Athlete>>> Arrange(List<Athlete> athleteList, List<Track> tracklist) {
		List<Map<Track, List<Athlete>>> arrangementMapList = new LinkedList<>();
		int OrbitNum = (int) (athleteList.size() / tracklist.size()) + 1;//Orbit的个数

		for (int i = 0; i < OrbitNum; i++) {
			arrangementMapList.add(new HashMap<>());
			for (int j=0;j<tracklist.size();j++) {
				arrangementMapList.get(i).put(tracklist.get(j), new LinkedList<Athlete>());
			}
		}
		Collections.sort(athleteList, new Comparator<Athlete>() {
			@Override
			public int compare(Athlete o1, Athlete o2) {
				if (o1.getBestRecord() - o2.getBestRecord() == 0) {
					return 0;
				} else if (o1.getBestRecord() - o2.getBestRecord() > 0) {
					return 1;
				} else {
					return -1;
				}
			}
		});
		int i = 0;
		int j = 0;
		for (i=0;i < OrbitNum; i++) {
			j=0;
			Map<Track, List<Athlete>> currentMap=arrangementMapList.get(i);
			while( j < tracklist.size()&&!athleteList.isEmpty()) {
				currentMap.get(tracklist.get(j)).add(athleteList.get(0));
				athleteList.remove(0);
				j++;
			}
		}
		return arrangementMapList;
	}

}

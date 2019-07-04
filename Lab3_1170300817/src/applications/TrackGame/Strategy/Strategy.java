package applications.TrackGame.Strategy;

import java.util.List;
import java.util.Map;

import applications.TrackGame.Athlete;
import track.Track;

public interface Strategy {
	/**
	 * 排序策略
	 * 
	 * @param runnerlist 运动员列表
	 * @param tracklist  赛道列表
	 * @return 排序策略Map
	 */
	public List<Map<Track, List<Athlete>>> Arrange(List<Athlete> runnerlist, List<Track> tracklist);
}

package applications.TrackGame;

import java.util.List;
import circularOrbit.ConcreteCircularOrbit;
import phsicalObject.PhysicalObject;
import track.Track;

public class TrackCircularOrbit extends ConcreteCircularOrbit<PhysicalObject, Athlete> {
	// Abstraction function:
	// TrackCircularOrbit是一个由多个Track，多个轨道物体和中心物体组成的对轨道结构的抽象数据型
	// OrbitMap抽象轨道和物体的一对多的关系
	// 所以AF是TrackCircularOrbit到真实的有运动员的轨道结构的映射

	// Representation invariant:
	// 轨道不能重名，不能有轨道具有相同半径

	// Safety from rep exposure:
	// 同父类
	// 在有必要的时候使用防御性拷贝
	public TrackCircularOrbit() {
		super();
	}

	/**
	 * 重写toString方法
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getCentralObject().getName() + ":\n");
		List<Track> trackList = this.getSortedTracks();
		for (int i = 0; i < trackList.size(); i++) {
			Track currentTrack = trackList.get(i);
			for (Athlete a : OrbitMap.get(currentTrack)) {
				sb.append(currentTrack.getName() + ":" + a.getName() + "\t号码：" + a.getIdNum() + "\t国籍："
						+ a.getNationality() + "\t年龄：" + a.getAge() + "\t最好成绩：" + a.getBestRecord() + "\n");
			}
		}
		return sb.toString();

	}

}

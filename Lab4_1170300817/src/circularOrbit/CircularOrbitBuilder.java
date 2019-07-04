package circularOrbit;

import java.util.List;
import java.util.Map;

import phsicalObject.PhysicalObject;
import track.Track;

public abstract class CircularOrbitBuilder<L, E extends PhysicalObject> {
	protected ConcreteCircularOrbit<L, E> concreteCircularOrbit;

	/**
	 * 返回构造完成的对象
	 * 
	 * @return ConcreteCircularOrbit
	 */
	public ConcreteCircularOrbit<L, E> getConcreteCircularOrbit() {
		return concreteCircularOrbit;
	}

	/**
	 * 子类实现时创建具体类型的子类
	 */
	public abstract void createCircularOrbit();

	/**
	 * 根据传入的trackList初始化concreteCircularOrbit
	 * 
	 * @param trackList
	 */
	public void bulidTracks(List<Track> trackList) {
		for (Track t : trackList) {
			concreteCircularOrbit.addTrack(t);
		}
	}

	/**
	 * 根据传入的centralObj和ObjectMap初始化concreteCircularOrbit
	 * 
	 * @param centralObj 传入的中心物体
	 * @param ObjectMap  传入的OrbitMap
	 */
	public void bulidPhysicalObjects(L centralObj, Map<Track, List<E>> ObjectMap) {
		concreteCircularOrbit.setCentralObject(centralObj);
		for (Map.Entry<Track, List<E>> e : ObjectMap.entrySet()) {
			for (E object : e.getValue()) {
				concreteCircularOrbit.addObjectToTrack(e.getKey(), object);
			}
		}
	}
	public void bulidPhysicalObjects(Map<Track, List<E>> ObjectMap) {
		for (Map.Entry<Track, List<E>> e : ObjectMap.entrySet()) {
			for (E object : e.getValue()) {
				concreteCircularOrbit.addObjectToTrack(e.getKey(), object);
			}
		}
	}

}

package applications.TrackGame;

import circularOrbit.CircularOrbitBuilder;
import phsicalObject.PhysicalObject;

public class TrackCircularOrbitBuilder extends CircularOrbitBuilder<PhysicalObject, Athlete> {

	/**
	 * 创建具体类型的子类
	 */
	public void createCircularOrbit(Integer gameType) {
		concreteCircularOrbit = new TrackCircularOrbit();
		concreteCircularOrbit.setCentralObject(new PhysicalObject(Integer.toString(gameType)));
	}
	/**
	 * 创建具体类型的子类
	 */
	@Override
	public void createCircularOrbit() {
		concreteCircularOrbit = new TrackCircularOrbit();
	}


}

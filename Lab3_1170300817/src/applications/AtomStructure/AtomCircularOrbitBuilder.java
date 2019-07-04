package applications.AtomStructure;

import circularOrbit.CircularOrbitBuilder;

public class AtomCircularOrbitBuilder extends CircularOrbitBuilder<Particle, Particle>{

	/**
	 * 重写createCircularOrbit，返回AtomCircularOrbit对象
	 */
	@Override
	public void createCircularOrbit() {
		concreteCircularOrbit = new AtomCircularOrbit();
		
	}

}

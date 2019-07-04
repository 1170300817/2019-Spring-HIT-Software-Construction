package circularOrbitHelper;

import circularOrbit.CircularOrbit;
import difference.Difference;

public class CircularOrbitAPIs {
	// Abstraction function:
	// 是一个circularOrbit的某些功能的调用者

	// Representation invariant:
	// circularOrbit不能是null

	// Safety from rep exposure:
	// 在有必要的时候使用防御性拷贝

	/**
	 * 可视化
	 * 
	 * @param c
	 */
	public static <L, E> void visualize(CircularOrbit<L, E> c) {
		c.drawpicture();
	}

	/**
	 * 计算信息熵
	 * 
	 * @param c 需要计算的Orbit
	 * @return 信息熵
	 */
	public static <L, E> double getObjectDistributionEntropy(CircularOrbit<L, E> c) {
		return c.getObjectDistributionEntropy();
	}

	/**
	 * 计算物体间逻辑距离
	 * 
	 * @param c需要计算的Orbit
	 * @param e1          物体1
	 * @param e2          物体2
	 * @return
	 */
	public static <L, E> int getLogicalDistance(CircularOrbit<L, E> c, E e1, E e2) {
		return c.getLogicalDistance(e1, e2);
	}

	/**
	 * 获得两系统间的不同
	 * 
	 * @param c1 需要比较的Orbit
	 * @param c2 需要比较的Orbit
	 * @return Difference对象记录不同之处
	 */
	public static <L, E> Difference getDifference(CircularOrbit<L, E> c1, CircularOrbit<L, E> c2) {
		return c1.getDifference(c2);
	}

}
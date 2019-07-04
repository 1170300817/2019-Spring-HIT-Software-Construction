package applications.AtomStructure;

import phsicalObject.PhysicalObject;

public class Particle extends PhysicalObject {
	// Abstraction function:
	// 所以AF是从一个记录着微粒名字的抽象数据型到现实微粒的映射

	// Representation invariant:
	// 名字都不能为空

	// Safety from rep exposure:
	// 同父类

	public Particle(String name) {
		super(name);
	}

	/**
	 * 构造电子
	 * 
	 * @return
	 */
	public static Particle getElectron() {
		Particle electon = new Particle("Electon");
		return electon;
	}

	/**
	 * 构造原子核
	 * 
	 * @param name
	 * @return
	 */
	public static Particle getNucleus(String name) {
		Particle Nucleus = new Particle(name);
		return Nucleus;
	}
}

package applications.SocialNetworkCircle;

import phsicalObject.PhysicalObject;

public class Person extends PhysicalObject {
	// Abstraction function:
	// 所以AF是从一个记录着名字，年龄，gender的抽象数据型到现实人际网络中的人的映射

	// Representation invariant:
	// 名字，年龄，gender都不能为空

	// Safety from rep exposure:
	// 同父类
	// 设置关键数据age,gender为private final防止更改
	private final int age;
	private final String gender;

	public Integer getAge() {
		return age;
	}

	public String getGender() {
		return gender;
	}

	/**
	 * @param name
	 * @param age
	 * @param gender
	 */
	public Person(String name, int age, String gender) {
		super(name);
		this.age = age;
		this.gender = gender;
	}

	/**
	 * 静态工厂方法
	 * 
	 * @param name   名字
	 * @param age    年龄
	 * @param gender 性别
	 * @return 实例
	 */
	public static Person getInstance(String name, int age, String gender) {
		Person p = new Person(name, age, gender);
		return p;
	}

}

package phsicalObject;

public class PhysicalObject {
	// Abstraction function:
	// 所以AF是从一个记录着名字的抽象数据型到现实运动员的映射

	// Representation invariant:
	// 名字不能为空

	// Safety from rep exposure:
	// 设置关键数据name为private final防止更改
	protected final String name;

	/**
	 * 获得名字
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**构造方法
	 * @param name 传入名字
	 */
	public PhysicalObject(String name) {
		this.name = name;
	}
}

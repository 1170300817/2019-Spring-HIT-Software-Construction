package centralObject;

import static org.junit.Assert.assertTrue;

public class CentralObject {
	// Abstraction function:
	// 所以AF是从一个记录了名字抽象数据型到现实轨道结构中心物体的映射

	// Representation invariant:
	// 名字不能为空

	// Safety from rep exposure:
	// 设置关键数据name为private final防止更改
	private final String name;

	/**
	 * 获得名字
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 构造方法
	 * 
	 * @param name
	 */
	public CentralObject(String name) {
		this.name = name;
		checkRep();
	}
	/**
	 * 检查合法性的方法
	 * 判断名字非空
	 */
	public void checkRep() {
		assertTrue(this.name!=null);
	}
}

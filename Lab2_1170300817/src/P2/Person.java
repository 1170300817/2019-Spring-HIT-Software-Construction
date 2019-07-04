package P2;

import java.util.ArrayList;

public class Person {
	private String name;// 保存名字
	private static ArrayList<String> personlist = new ArrayList<String>();

	/* 构造方法 */
	public Person(String nameString) {
		if (personlist.contains(nameString)) {
			System.out.println("名字重复");
//			System.exit(0);
		} else {
			this.name = nameString;
			personlist.add(nameString);
		}
	}

	/* 获取名字 */
	public String getName() {
		return name;
	}
}

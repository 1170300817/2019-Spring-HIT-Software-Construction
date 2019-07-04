package P3;

import java.util.ArrayList;
import java.util.List;

public class Person {
	private String name;//保存名字
	private List<Person> friendList;//保存朋友

	private static ArrayList<String> personlist = new ArrayList<String>();

	/* 构造方法 */
	public Person(String nameString) {
		if(personlist.contains(nameString))
		{
			System.out.println("名字重复");
			System.exit(0);
		}
		else {
			this.name = nameString;
			friendList = new ArrayList<>();
			personlist.add(nameString);
		}

	}
	/* 新增朋友 */
	public void addFriend(Person pb) {
		friendList.add(pb);
	}
	/* 获取名字 */
	public String getName() {
		return name;
	}
	/* 获取朋友列表 */
	public List<Person> getFriendList() {
		return this.friendList;
	}
}

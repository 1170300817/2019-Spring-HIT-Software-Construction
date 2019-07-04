package P3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class FriendshipGraph {
	private List<Person> people;
	private List<String> nameList;

	/* 构造方法 */
	public FriendshipGraph() {
		people = new ArrayList<>();
		nameList = new ArrayList<String>();
	}

	/* 增加新人 */
	public void addVertex(Person newPerson) {
		if (nameList.contains(newPerson.getName())) {
			System.out.println("名字重复1");
			System.exit(0);
		} else
			nameList.add(newPerson.getName());
		people.add(newPerson);
	}

	/* 增加新朋友 */
	public void addEdge(Person pa, Person pb) {
		pa.addFriend(pb);
	}

	/* 获取二人距离 */
	public int getDistance(Person Person1, Person Person2) {
		if (Person1 == Person2)
			return 0;
		Queue<Person> queue = new LinkedList<>();
		Map<Person, Integer> distantMap = new HashMap<>();
		queue.offer(Person1);
		distantMap.put(Person1, 0);
		while (!queue.isEmpty()) {
			Person topPerson = queue.poll();
			int nowDis = distantMap.get(topPerson);
			List<Person> friendList = topPerson.getFriendList();
			for (Person ps : friendList)
				if (!distantMap.containsKey(ps)) {
					distantMap.put(ps, nowDis + 1);
					queue.offer(ps);
					if (ps == Person2) {
						return distantMap.get(Person2);
					}
				}
		}
		return -1;
	}

	public static void main(String args[]) {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person ross = new Person("Rachel");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		graph.addEdge(rachel, ross);
		graph.addEdge(ross, rachel);
		graph.addEdge(ross, ben);
		graph.addEdge(ben, ross);
		System.out.println(graph.getDistance(rachel, ross));
		// should print 1
		System.out.println(graph.getDistance(rachel, ben));
		// should print 2
		System.out.println(graph.getDistance(rachel, rachel));
		// should print 0
		System.out.println(graph.getDistance(rachel, kramer));
		// should print -1

	}

}

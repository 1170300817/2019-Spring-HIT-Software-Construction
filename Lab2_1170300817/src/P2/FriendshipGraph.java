package P2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import P1.graph.ConcreteVerticesGraph;

public class FriendshipGraph {
	private final ConcreteVerticesGraph<Person> people;

	/**
	 * @return 返回ConcreteVerticesGraph图
	 */
	public ConcreteVerticesGraph<Person> getPeople() {
		return people;
	}

	/* 构造方法 */
	public FriendshipGraph() {
		people = new ConcreteVerticesGraph<>();
	}

	/* 增加新人 */
	public void addVertex(Person newPerson) {
		people.add(newPerson);
	}

	/* 增加新朋友 */
	public void addEdge(Person pa, Person pb) {
		people.set(pa, pb, 1);
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
			Set<Person> friendList = people.targets(topPerson).keySet();
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

//	public static void main(String args[]) {
//		FriendshipGraph graph = new FriendshipGraph();
//		Person rachel = new Person("Rachel");
//		Person ross = new Person("ross");
//		Person ben = new Person("Ben");
//		Person kramer = new Person("Kramer");
//		graph.addVertex(rachel);
//		graph.addVertex(ross);
//		graph.addVertex(ben);
//		graph.addVertex(kramer);
//		graph.addEdge(rachel, ross);
//		graph.addEdge(ross, rachel);
//		graph.addEdge(ross, ben);
//		graph.addEdge(ben, ross);
//		System.out.println(graph.getDistance(rachel, ross));
//		// should print 1
//		System.out.println(graph.getDistance(rachel, ben));
//		// should print 2
//		System.out.println(graph.getDistance(rachel, rachel));
//		// should print 0
//		System.out.println(graph.getDistance(rachel, kramer));
//		// should print -1
//	}
}

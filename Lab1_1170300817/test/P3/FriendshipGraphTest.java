package P3;

import static org.junit.Assert.assertEquals;



import org.junit.Test;



public class FriendshipGraphTest {

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	@Test
	public void testBasicFriendshipGraph() {
		FriendshipGraph graph = new FriendshipGraph();
		Person a = new Person("a");
		Person b = new Person("b");
		Person c = new Person("c");
		Person d = new Person("d");
		graph.addVertex(a);
		graph.addVertex(b);
		graph.addVertex(c);
		graph.addVertex(d);
		graph.addEdge(a, b);
		graph.addEdge(b, c);
		graph.addEdge(c, d);
		assertEquals("expected distance", 1, graph.getDistance(a, b));
		assertEquals("expected distance", 1, graph.getDistance(b, c));
		assertEquals("expected distance", 1, graph.getDistance(c, d));
		assertEquals("expected distance", 2, graph.getDistance(a, c));
		assertEquals("expected distance", 2, graph.getDistance(b, d));
		assertEquals("expected distance", 3, graph.getDistance(a, d));
		assertEquals("expected distance", -1, graph.getDistance(b, a));
	}

}

/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import java.util.Collections;
import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>
 * PS2 instructions: you MUST NOT add constructors, fields, or non-@Test methods
 * to this class, or change the spec of {@link #emptyInstance()}. Your tests
 * MUST only obtain Graph instances by calling emptyInstance(). Your tests MUST
 * NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
	/**
	 * Testing strategy
	 * 
	 * 对于add方法划分输入： vertex：已存入graph的点、还未存入graph的点
	 * 
	 * 对于set方法划分输入： source，target：已存入graph的点、还未存入graph的点 weight：0 ，非0
	 * 
	 * 对于remove方法划分输入： vertex：已存入graph的点、还未存入graph的点、与其他点有相连的点。
	 * 
	 * 对于vertices方法： graph：空图、非空图
	 * 
	 * 对于sources方法划分： target：有边连接的点、无边连接的点
	 * 
	 * 对于targets方法划分： source：有边连接的点、无边连接的点
	 * 
	 */
	/**
	 * Overridden by implementation-specific test classes.
	 * 
	 * @return a new empty graph of the particular implementation being tested
	 */
	public abstract Graph<String> emptyInstance();

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	@Test
	public void testInitialVerticesEmpty() {
		assertEquals("expected new graph to have no vertices", Collections.emptySet(), emptyInstance().vertices());
	}

	// 测试重复存入
	@Test
	public void testexistAdd() {
		Graph<String> graph = emptyInstance();
		graph.add("a");
		graph.add("a");
		assertTrue(graph.vertices().contains("a"));
	}

	// 测试add方法
	@Test
	public void testnotexistAdd() {
		Graph<String> graph = emptyInstance();
		graph.add("a");
		assertTrue(graph.vertices().contains("a"));
	}

	// 测试set方法，其中source和target未加入图中。附带测试vertices
	@Test
	public void testSet() {
		Graph<String> graph = emptyInstance();
		graph.set("a", "b", 4);
		assertTrue(graph.vertices().contains("a"));
		assertTrue(graph.vertices().contains("b"));
		graph.set("a", "c", 4);
		assertTrue(graph.vertices().contains("c"));
	}

	// 测试set方法，其中source和target未加入图中
	@Test
	public void testunSet() {
		Graph<String> graph = emptyInstance();
		graph.add("a");
		graph.add("b");
		graph.set("a", "b", 4);
		assertTrue(graph.vertices().contains("a"));
		assertTrue(graph.vertices().contains("b"));
		assertEquals((Integer) 4, Integer.valueOf(graph.targets("a").get("b")));
	}

	// 测试Remove方法,目标点无相连，附带测试vertices。
	@Test
	public void testunconnectedRemove() {
		Graph<String> graph = emptyInstance();
		graph.add("b");
		graph.remove("b");
		assertFalse(graph.vertices().contains("b"));
	}

	// 测试Remove方法,目标点有相连边，附带测试targets。
	
	@Test
	public void testconnectedRemove() {
		Graph<String> graph = emptyInstance();
		graph.set("a", "c", 4);
		graph.remove("c");
		assertFalse(graph.targets("a").keySet().contains("c"));
	}

	// 测试vertices方法,空图
	@Test
	public void testemptyVertices() {
		Graph<String> graph = emptyInstance();
		assertTrue(graph.vertices().isEmpty());
	}
	
}

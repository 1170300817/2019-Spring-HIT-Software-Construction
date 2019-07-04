/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {
	// Testing strategy
	// 对GraphPoet的文件输入进行划分：
	// 一行输入，多行输入，空文件
	// 对poem的选择过程进行划分：
	// 所有权都是1，需要比较选择权重大的
	// 对toString的输入划分：
	// 空文件，非空文件
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	// 空文件输入
	@Test
	public void testempty() throws IOException {
		final GraphPoet nimoy = new GraphPoet(new File("test\\P1\\poet\\empty.txt"));
		final String input = "To strange worlds";
		assertEquals(nimoy.poem(input), "To strange worlds");
	}

	// 单行输入
	@Test
	public void testSingleLine() throws IOException {
		final GraphPoet nimoy = new GraphPoet(new File("test\\P1\\poet\\characters.txt"));
		final String input = "a c e g i";
		assertEquals(nimoy.poem(input), "a b c d e f g h i");
	}

	// 多行输入
	@Test
	public void testMutiLine() throws IOException {
		final GraphPoet nimoy = new GraphPoet(new File("test\\P1\\poet\\Mutiline.txt"));
		final String input = "To strange worlds";
		assertEquals(nimoy.poem(input), "To explore strange new worlds");
	}
	// 测试需要选择的情况
		@Test
		public void testSelect() throws IOException {
			final GraphPoet nimoy = new GraphPoet(new File("test\\P1\\poet\\select.txt"));
			final String input = "a c";
			assertEquals(nimoy.poem(input), "a b c");
		}

	// 检测tostring非空文件
	@Test
	public void testtoStringempty() throws IOException {
		final GraphPoet nimoy = new GraphPoet(new File("test\\P1\\poet\\empty.txt"));
		System.out.println(nimoy.toString());
		assertEquals("", nimoy.toString());
	}

	// 检测tostring非空文件
	@Test
	public void testtoString() throws IOException {
		final GraphPoet nimoy = new GraphPoet(new File("test\\P1\\poet\\tostring.txt"));
		assertTrue(nimoy.toString().contains("a->b 权重为1"));
		assertTrue(nimoy.toString().contains("b->c 权重为1"));
	}

}

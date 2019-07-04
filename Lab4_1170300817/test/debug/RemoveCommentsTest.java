package debug;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

public class RemoveCommentsTest {
	@Test
	public void FindMedianSortedArraysTest1() {
		String[] a = {"/*Test program */", "int main()", "{ ", "  // variable declaration ", "int a, b, c;", "/* This is a test", "   multiline  ", "   comment for ", "   testing */", "a = b + c;", "}"};
		String[] b= {"int main()","{ ","  ","int a, b, c;","a = b + c;","}"};
		assertTrue(Arrays.asList(b).equals(RemoveComments.removeComments(a)));
		System.out.println(RemoveComments.removeComments(a));
		System.out.println(Arrays.asList(b));
	}
	@Test
	public void FindMedianSortedArraysTest2() {
		String[] a = {"a/*comment", "line", "more_comment*/b"};
		String[] b= {"ab"};
		assertTrue(Arrays.asList(b).equals(RemoveComments.removeComments(a)));
		System.out.println(RemoveComments.removeComments(a));
		System.out.println(Arrays.asList(b));
	}
}

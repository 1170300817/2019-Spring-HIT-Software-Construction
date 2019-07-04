package debug;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FindMedianSortedArraysTest {
	@Test
	public void FindMedianSortedArraysTest1() {
		int[] a = { 1, 3 };
		int[] b = { 2 };
		assertTrue(FindMedianSortedArrays.findMedianSortedArrays(a, b) == 2.0);
	}

	@Test
	public void FindMedianSortedArraysTest2() {
		int[] a = { 1, 2 };
		int[] b = { 3, 4 };
		assertTrue(FindMedianSortedArrays.findMedianSortedArrays(a, b) == 2.5);
	}

	@Test
	public void FindMedianSortedArraysTest3() {
		int[] a = { 1, 1, 1 };
		int[] b = { 5, 6, 7 };
		assertTrue(FindMedianSortedArrays.findMedianSortedArrays(a, b) == 3.0);
	}

	@Test
	public void FindMedianSortedArraysTest4() {
		int[] a= {1,1};
		int[] b= {1,2,3};
		assertTrue(FindMedianSortedArrays.findMedianSortedArrays(a, b)==1.0);
	}
}

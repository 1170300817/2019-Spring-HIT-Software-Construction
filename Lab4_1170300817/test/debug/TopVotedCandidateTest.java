package debug;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TopVotedCandidateTest {
	@Test
	public void TopVotedCandidateTest1() {
		int[] persons = { 0, 1, 1, 0, 0, 1, 0 };//投票给的人
		int[] times = { 0, 5, 10, 15, 20, 25, 30 };//投票的时间

		int[] input = { 3, 12, 25, 15, 24, 8 };//输入的时间点
		int[] output = { 0, 1, 1, 0, 0, 1 };//输出的获胜者
		TopVotedCandidate topVotedCandidate = new TopVotedCandidate(persons, times);
		for (int i = 0; i < 6; i++) {//循环检验
			assertEquals(topVotedCandidate.q(input[i]), output[i]);
		}
	}

}

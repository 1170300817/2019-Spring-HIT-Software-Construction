package P4.twitter;

import static org.junit.Assert.*;
import java.util.*;
import java.time.Instant;

import java.util.Arrays;

import java.util.Map;
import java.util.Set;

import org.junit.Test;


public class MySocialNetworkTest {

	/*
	 * TODO: your testing strategies for these methods should go here. See the
	 * ic03-testing exercise for examples of what a testing strategy comment looks
	 * like. Make sure you have partitions.
	 */
	private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
	private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
	private static final Instant d3 = Instant.parse("9999-02-17T14:00:00Z");
	private static final Instant d4 = Instant.parse("5959-02-17T16:00:00Z");
	private static final Instant d5 = Instant.parse("-1111-02-17T17:00:00Z");
	private static final Instant d6 = Instant.parse("2016-02-17T10:00:00Z");
	private static final Tweet tweet1 = new Tweet(1, "a", " #aaa #aa #a #bbb", d1);
	private static final Tweet tweet2 = new Tweet(2, "b", " #aaa #aa #a", d2);
	private static final Tweet tweet3 = new Tweet(3, "c", " #aaa #aa #ccc", d3);
	private static final Tweet tweet4 = new Tweet(4, "d", " #aaa ", d4);
	private static final Tweet tweet5 = new Tweet(5, "e", " #ccc #bbb #a", d5);
	private static final Tweet tweet6 = new Tweet(6, "f", " #eee ", d6);
	private static final Tweet tweet7 = new Tweet(7, "g", " #aaa #ccc", d6);

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}
	@Test
	public void testInfluencers() {
		Map<String, Set<String>> followsGraph = SocialNetwork
				.guessFollowsGraph(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5, tweet6, tweet7));
		List<String> influencers = SocialNetwork.influencers(followsGraph);

		assertEquals("expected same order", 0, influencers.indexOf("a"));
		assertEquals("expected same order", 1, influencers.indexOf("c"));
		assertEquals("expected same order", 2, influencers.indexOf("b"));

	}
}

/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import static org.junit.Assert.*;
import java.util.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Test;


public class SocialNetworkTest {

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
	private static final Tweet tweet1 = new Tweet(1, "a", " @a ", d1);
	private static final Tweet tweet2 = new Tweet(2, "b", " @a ", d2);
	private static final Tweet tweet3 = new Tweet(3, "c", " @a ", d3);
	private static final Tweet tweet4 = new Tweet(4, "d", " @a ", d4);
	private static final Tweet tweet5 = new Tweet(5, "e", " @c ", d5);
	private static final Tweet tweet6 = new Tweet(6, "f", " @e ", d6);
	private static final Tweet tweet7 = new Tweet(7, "g", " @e ", d6);

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	@Test
	public void testGuessFollowsGraphEmpty() {
		Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
		assertTrue("expected empty graph", followsGraph.isEmpty());
	}


	@Test
	public void testGuessFollowsGraph() {
		Map<String, Set<String>> followsGraph = SocialNetwork
				.guessFollowsGraph(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5, tweet6,tweet7));
		assertTrue("expected contain", followsGraph.containsKey("a"));
		assertTrue("expected contain", followsGraph.containsKey("b"));
		assertTrue("expected contain", followsGraph.containsKey("c"));
		assertTrue("expected contain", followsGraph.containsKey("d"));
		assertTrue("expected contain", followsGraph.containsKey("e"));
		assertTrue("expected contain", followsGraph.containsKey("f"));
		assertTrue("expected contain", followsGraph.containsKey("g"));
	}

	@Test
	public void testInfluencersEmpty() {
		Map<String, Set<String>> followsGraph = new HashMap<>();
		List<String> influencers = SocialNetwork.influencers(followsGraph);
		assertTrue("expected empty list", influencers.isEmpty());
	}

	@Test
	public void testInfluencers() {
		Map<String, Set<String>> followsGraph = SocialNetwork
				.guessFollowsGraph(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5, tweet6, tweet7));
		List<String> influencers = SocialNetwork.influencers(followsGraph);

		assertEquals("expected same order", 0, influencers.indexOf("a"));
		assertEquals("expected same order", 1, influencers.indexOf("e"));
		assertEquals("expected same order", 2, influencers.indexOf("c"));

	}

	/*
	 * Warning: all the tests you write here must be runnable against any
	 * SocialNetwork class that follows the spec. It will be run against several
	 * staff implementations of SocialNetwork, which will be done by overwriting
	 * (temporarily) your version of SocialNetwork with the staff's version. DO NOT
	 * strengthen the spec of SocialNetwork or its methods.
	 * 
	 * In particular, your test cases must not call helper methods of your own that
	 * you have put in SocialNetwork, because that means you're testing a stronger
	 * spec than SocialNetwork says. If you need such helper methods, define them in
	 * a different class. If you only need them in this test class, then keep them
	 * in this test class.
	 */

}

/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

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
	private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
	private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
	private static final Tweet tweet3 = new Tweet(99999, "bbitdiddle", "@test4:", d3);
	private static final Tweet tweet4 = new Tweet(7, "adadadadada", "iahsi@invalid", d4);
	private static final Tweet tweet5 = new Tweet(8, "sfsfsfsfsfsfs", "482 @aunsch wdwdw wdwd @test1", d5);
	private static final Tweet tweet6 = new Tweet(5, "regtbrtb", "*()_)_(&*^)+_)_+((*@abcd", d6);

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	@Test
	public void testFiveTweets() {
		Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5, tweet6));

		assertEquals("expected start", d5, timespan.getStart());
		assertEquals("expected end", d3, timespan.getEnd());
	}

	@Test
	public void testGetMentionedUsersNoMention() {

		Set<String> empty = Extract.getMentionedUsers(Arrays.asList(tweet1));
		assertTrue("expected empty set", empty.isEmpty());

		Set<String> mentionedUsers = Extract
				.getMentionedUsers(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5, tweet6));
		assertTrue("expected set containing 'test4'", mentionedUsers.contains("test4"));
		assertTrue("expected set containing 'test1'", mentionedUsers.contains("test1"));
		assertTrue("expected set containing 'aunsch'", mentionedUsers.contains("aunsch"));
	}

	/*
	 * Warning: all the tests you write here must be runnable against any Extract
	 * class that follows the spec. It will be run against several staff
	 * implementations of Extract, which will be done by overwriting (temporarily)
	 * your version of Extract with the staff's version. DO NOT strengthen the spec
	 * of Extract or its methods.
	 * 
	 * In particular, your test cases must not call helper methods of your own that
	 * you have put in Extract, because that means you're testing a stronger spec
	 * than Extract says. If you need such helper methods, define them in a
	 * different class. If you only need them in this test class, then keep them in
	 * this test class.
	 */

}

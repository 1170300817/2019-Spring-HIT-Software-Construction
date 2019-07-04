/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import java.time.Instant;
import java.util.regex.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

	/**
	 * Get the time period spanned by tweets.
	 * 
	 * @param tweets list of tweets with distinct ids, not modified by this method.
	 * @return a minimum-length time interval that contains the timestamp of every
	 *         tweet in the list.
	 */
	public static Timespan getTimespan(List<Tweet> tweets) {
		if (tweets.size() == 0) {
			return new Timespan(Instant.MAX, Instant.MAX);
		}
		Instant minTime = Instant.MAX, maxTime = Instant.MIN;
		for (Tweet t : tweets) {
			Instant currentTime = t.getTimestamp();
			if (currentTime.isBefore(minTime))
				minTime = currentTime;
			if (currentTime.isAfter(maxTime))
				maxTime = currentTime;
		}
		Timespan result = new Timespan(minTime, maxTime);
		return result;
	}

	/**
	 * Get usernames mentioned in a list of tweets.
	 * 
	 * @param tweets list of tweets with distinct ids, not modified by this method.
	 * @return the set of usernames who are mentioned in the text of the tweets. A
	 *         username-mention is "@" followed by a Twitter username (as defined by
	 *         Tweet.getAuthor()'s spec). The username-mention cannot be immediately
	 *         preceded or followed by any character valid in a Twitter username.
	 *         For this reason, an email address like bitdiddle@mit.edu does NOT
	 *         contain a mention of the username mit. Twitter usernames are
	 *         case-insensitive, and the returned set may include a username at most
	 *         once.
	 */
	public static Set<String> getMentionedUsers(List<Tweet> tweets) {
		Set<String> results = new HashSet<>();
		Pattern pattern = Pattern.compile("@([\\w.-]+)");
		for (Tweet t : tweets) {
			String text = t.getText();
			Matcher matcher = pattern.matcher(text.toLowerCase());
			while (matcher.find()) {//对匹配到的字符串检验，如果前面没有合法字符则满足要求
				String usernameString = null;
				int start = matcher.start();
				if (start > 0) {
					Character preCharacter = text.charAt(start - 1);
					if (preCharacter != '-' && preCharacter != '_' && !Character.isLetter(preCharacter)
							&& !Character.isDigit(preCharacter)) {
						usernameString =matcher.group(1);
					}
				}else {
					usernameString =matcher.group(1);			
				}
				if (usernameString!=null)
					results.add(usernameString);
			}
		}
		return results;
	}
}

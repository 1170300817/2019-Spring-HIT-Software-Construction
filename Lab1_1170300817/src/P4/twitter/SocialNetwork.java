/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;



/**
 * SocialNetwork provides methods that operate on a social network.
 * 
 * A social network is represented by a Map<String, Set<String>> where map[A] is
 * the set of people that person A follows on Twitter, and all people are
 * represented by their Twitter usernames. Users can't follow themselves. If A
 * doesn't follow anybody, then map[A] may be the empty set, or A may not even exist
 * as a key in the map; this is true even if A is followed by other people in the network.
 * Twitter usernames are not case sensitive, so "ernie" is the same as "ERNie".
 * A username should appear at most once as a key in the map or in any given
 * map[A] set.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class SocialNetwork {

    /**
     * Guess who might follow whom, from evidence found in tweets.
     * 
     * @param tweets
     *            a list of tweets providing the evidence, not modified by this
     *            method.
     * @return a social network (as defined above) in which Ernie follows Bert
     *         if and only if there is evidence for it in the given list of
     *         tweets.
     *         One kind of evidence that Ernie follows Bert is if Ernie
     *         @-mentions Bert in a tweet. This must be implemented. Other kinds
     *         of evidence may be used at the implementor's discretion.
     *         All the Twitter usernames in the returned social network must be
     *         either authors or @-mentions in the list of tweets.
     */
    public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {
    	Map<String, Set<String>> guessFollowMap = new HashMap<>();
    	Map<String, Set<String>> HashtagsMap = new HashMap<>();
    	for (Tweet t : tweets) {
            String Author = t.getAuthor().toLowerCase();
            Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(t));
            if (mentionedUsers.contains(Author)) {
            	mentionedUsers.remove(Author);
            }
            if (guessFollowMap.containsKey(Author)) {
            	guessFollowMap.get(Author).addAll(mentionedUsers);
            } else {
            	guessFollowMap.put(Author, mentionedUsers);
            }
            Set<String> hashtags = getHashtags(t.getText());
            if (HashtagsMap.containsKey(Author)) {
            	HashtagsMap.get(Author).addAll(hashtags);
            } else {
            	HashtagsMap.put(Author, hashtags);
            }  
        }
    	for (String user1 : HashtagsMap.keySet()) {
            for (String user2 : HashtagsMap.keySet()) {
                if (!user1.equals(user2)) {
                    Set<String> user1Hashtags = new HashSet<>(HashtagsMap.get(user1));
                    user1Hashtags.retainAll(HashtagsMap.get(user2));
                    if (user1Hashtags.size()>=2) {
                    	
                        if (guessFollowMap.containsKey(user1)) {
                        	guessFollowMap.get(user1).add(user2);
                        	
                        } else {
                        	guessFollowMap.put(user1, new HashSet<String>(Arrays.asList(user2)));
                        }                       
                    }
                }
            }
        }
        return guessFollowMap;
    }
    private static Set<String> getHashtags(String text){
    	Set<String> hashtags = new HashSet<>();
        String[] words = text.split("\\s+");
        for (String word : words) {
            if (Pattern.matches("#([A-Za-z0-9_-]+)", word)) {
                word = word.substring(1).toLowerCase();
                hashtags.add(word);
            }
        }
        return hashtags;
    }
    /**
     * Find the people in a social network who have the greatest influence, in
     * the sense that they have the most followers.
     * 
     * @param followsGraph
     *            a social network (as defined above)
     * @return a list of all distinct Twitter usernames in followsGraph, in
     *         descending order of follower count.
     */
    public static List<String> influencers(Map<String, Set<String>> followsGraph) {
    	List<String> influencersList = new ArrayList<String>();
    	Map<String, Integer> influencersMap = new HashMap<>();
    	for (Set<String> follows : followsGraph.values()) {
            for (String followedUser : follows) {
                followedUser = followedUser.toLowerCase();
                if (influencersMap.containsKey(followedUser)) {
                	influencersMap.put(followedUser, influencersMap.get(followedUser) + 1);
                } else {
                	influencersMap.put(followedUser, 1);
                }
            }
        }
    	for (String username : followsGraph.keySet()) {
    		username = username.toLowerCase();
    		if (!influencersMap.containsKey(username)) {
    			influencersMap.put(username.toLowerCase(), 0);
            }
    	}
    	while(!influencersMap.isEmpty()){
            int topFollowers = Collections.max(influencersMap.values());
            for (String username : influencersMap.keySet()){
                if(influencersMap.get(username).equals(topFollowers)){
                	influencersList.add(username);
                    influencersMap.remove(username);
                    break;
                }
            }
        }
		return influencersList;
    }
}

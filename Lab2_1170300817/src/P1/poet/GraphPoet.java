/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import P1.graph.ConcreteEdgesGraph;
import P1.graph.Graph;

/**
 * A graph-based poetry generator.
 * 
 * <p>
 * GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph. Vertices in the graph are words. Words are defined as
 * non-empty case-insensitive strings of non-space non-newline characters. They
 * are delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * 
 * <p>
 * For example, given this corpus:
 * 
 * <pre>
 *     Hello, HELLO, hello, goodbye!
 * </pre>
 * <p>
 * the graph would contain two edges:
 * <ul>
 * <li>("hello,") -> ("hello,") with weight 2
 * <li>("hello,") -> ("goodbye!") with weight 1
 * </ul>
 * <p>
 * where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>
 * Given an input string, GraphPoet generates a poem by attempting to insert a
 * bridge word between every adjacent pair of words in the input. The bridge
 * word between input words "w1" and "w2" will be some "b" such that w1 -> b ->
 * w2 is a two-edge-long path with maximum-weight weight among all the
 * two-edge-long paths from w1 to w2 in the affinity graph. If there are no such
 * paths, no bridge word is inserted. In the output poem, input words retain
 * their original case, while bridge words are lower case. The whitespace
 * between every word in the poem is a single space.
 * 
 * <p>
 * For example, given this corpus:
 * 
 * <pre>
 *     This is a test of the Mugar Omni Theater sound system.
 * </pre>
 * <p>
 * on this input:
 * 
 * <pre>
 *     Test the system.
 * </pre>
 * <p>
 * the output poem would be:
 * 
 * <pre>
 *     Test of the system.
 * </pre>
 * 
 * <p>
 * PS2 instructions: this is a required ADT class, and you MUST NOT weaken the
 * required specifications. However, you MAY strengthen the specifications and
 * you MAY add additional methods. You MUST use Graph in your rep, but otherwise
 * the implementation of this class is up to you.
 */
public class GraphPoet {

	private final Graph<String> graph = new ConcreteEdgesGraph<String>();
	
	// Abstraction function:
	// 是一个利用加权有向图寻找两点中间有无通路的方法，到实际给一句话添加中间词的问题的映射
	
	// Representation invariant:
	// 新加入的词必须是两点中间的bridge，weight必须大于0
	
	// Safety from rep exposure:
	// 设置graph为private final
	// 必要时使用防御性拷贝

	/**
	 * Create a new poet with the graph from corpus (as described above).
	 * 
	 * @param corpus text file from which to derive the poet's affinity graph
	 * @throws IOException if the corpus file cannot be found or read
	 */
	public GraphPoet(File corpus) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(corpus));
		List<String> list = new ArrayList<>();
		Map<String, Integer> map = new HashMap<>();
		String string;
		while ((string = in.readLine()) != null) {
			list.addAll(Arrays.asList(string.split(" ")));
		}
		in.close();
		for (int i = 0; i < list.size() - 1; i++) {
			String source = list.get(i).toLowerCase();
			String target = list.get(i + 1).toLowerCase();
			int preweight = 0;
			if (map.containsKey(source + target)) {
				preweight = map.get(source + target);
			}
			map.put(source + target, preweight + 1);
			graph.set(source, target, preweight + 1);
		}
		checkRep();
	}

	private void checkRep() {
		assert graph != null;
	}

	/**
	 * Generate a poem.
	 * 
	 * @param input string from which to create the poem
	 * @return poem (as described above)
	 */
	public String poem(String input) {
		StringBuilder SB = new StringBuilder();
		List<String> list = new ArrayList<String>(Arrays.asList(input.split(" ")));
		Map<String, Integer> sourceMap = new HashMap<>();
		Map<String, Integer> targetMap = new HashMap<>();
		for (int i = 0; i < list.size() - 1; i++) {
			SB.append(list.get(i)).append(" ");
			String sourceString = list.get(i).toLowerCase();
			String targetString = list.get(i + 1).toLowerCase();
			targetMap = graph.targets(sourceString);
			sourceMap = graph.sources(targetString);
			int maxWeight = 0;
			String bridgeWord = "";
			for (String string : targetMap.keySet()) {
				if (sourceMap.containsKey(string) && sourceMap.get(string) + targetMap.get(string) > maxWeight) {
					maxWeight = sourceMap.get(string) + targetMap.get(string);
					bridgeWord = string;
				}
			}
			if (maxWeight > 0) {
				SB.append(bridgeWord + " ");
			}
		}
		SB.append(list.get(list.size() - 1));
		return SB.toString();
	}
	@Override
	public String toString() {
		return graph.toString();
	}
}

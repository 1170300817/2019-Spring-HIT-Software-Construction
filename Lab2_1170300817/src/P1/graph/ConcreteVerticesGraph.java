/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>
 * PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph<L> implements Graph<L> {

	private final List<Vertex<L>> vertices = new ArrayList<>();
 
	// Abstraction function:
	// 用vertices抽象的图，到图结构的映射
	
	// Representation invariant:
	// vertices不能有重复点
	
	// Safety from rep exposure:
	// 设置vertices为private final
	// 必要时使用防御性拷贝

	/**
	 * 测试表示不变性
	 */
	private void checkRep() {
		assert vertices != null;
	}

	@Override
	public boolean add(L vertex) {
		for (Vertex<L> v : vertices) {
			if (v.getName().equals(vertex)) {
				return false;
			}
		}
		vertices.add(new Vertex<L>(vertex));
		checkRep();
		return true;
	}

	@Override
	public int set(L source, L target, int weight) {
		this.add(source);
		this.add(target);
		Iterator<Vertex<L>> it = vertices.iterator();
		while (it.hasNext()) {
			Vertex<L> v = it.next();
			if (v.getName().equals(source)) {
				v.setTarget(target, weight);
			}
			if (v.getName().equals(target)) {
				v.setSouce(source, weight);
			}
		}
		checkRep();
		return weight;
	}

	@Override
	public boolean remove(L vertex) {
		boolean flag = false;
		Iterator<Vertex<L>> it = vertices.iterator();
		while (it.hasNext()) {
			Vertex<L> v = it.next();
			if (v.getName() == vertex) {
				it.remove();
				flag = true;
			} else {
				if (v.getSources().containsKey(vertex)) {
					v.removeSource(vertex);
				}
				if (v.getTargets().containsKey(vertex)) {
					v.removeTarget(vertex);
				}
			}
		}
		checkRep();
		return flag;
	}

	@Override
	public Set<L> vertices() {
		Set<L> set = new HashSet<>();
		for (Vertex<L> v : vertices) {
			set.add(v.getName());
		}
		return set;
	}

	@Override
	public Map<L, Integer> sources(L target) {
		Map<L, Integer> map = new HashMap<>();
		for (Vertex<L> v : vertices) {
			if (v.getName() == target) {
				map = v.getSources();
				break;
			}
		}
		return new HashMap<L, Integer>(map);
	}

	@Override
	public Map<L, Integer> targets(L source) {
		Map<L, Integer> map = new HashMap<>();
		for (Vertex<L> v : vertices) {
			if (v.getName() == source) {
				map = v.getTargets();
				break;
			}
		}
		return new HashMap<L, Integer>(map);
	}

	@Override
	public String toString() {
		return String.format("This graph has %d vertices", this.vertices.size());
	}

}

/**
 * specification Mutable. This class is internal to the rep of
 * ConcreteVerticesGraph.
 * 
 * <p>
 * PS2 instructions: the specification and implementation of this class is up to
 * you.
 */
class Vertex<L> {
	private final L name;
	private final Map<L, Integer> sources;
	private final Map<L, Integer> targets;
	// Abstraction function:
	// 以name sources targets表示的数据类型，到一个有向图中顶点的映射

	// Representation invariant:
	// 各边weight的值应该永远大于0

	// Safety from rep exposure:
    // 将信息设置为private final，并添加getter方法
	/**
	 * 用名字创建新的点
	 * 
	 * @param name 点的名字
	 */
	public Vertex(L name) {
		this.name = name;
		sources = new HashMap<>();
		targets = new HashMap<>();
	}

	/**
	 * 检查表示不变性
	 */
	private void checkRep() {
		Set<L> keys1 = sources.keySet();
		if (keys1 != null) {
			Iterator<L> iterator = keys1.iterator();
			while (iterator.hasNext()) {
				L key = iterator.next();
				Integer value = sources.get(key);
				assertTrue(value > 0);
			}
		}
		Set<L> keys2 = targets.keySet();
		if (keys2 != null) {
			Iterator<L> iterator = keys2.iterator();
			while (iterator.hasNext()) {
				L key = iterator.next();
				Integer value = sources.get(key);
				assertTrue(value > 0);
			}
		}
	}

	/**
	 * @return 点的name
	 */
	public L getName() {
		return name;
	}

	/**
	 * @return 所有能到达此点的（点，边长）组成的HashMap
	 */
	public Map<L, Integer> getSources() {
		return new HashMap<L, Integer>(sources);
	}
	/**
	 * @return 所有此点能到达的（点，边长）组成的HashMap
	 */
	public Map<L, Integer> getTargets() {
		return new HashMap<L, Integer>(targets);
	}

	/**
	 * 如果weight=0，删去当前点的target，成功返回删去target的weight，不存在返回0
	 * 如果weight！=0，为当前点新增一个target，长度为weight，如果该点已存在，返回旧的weight，否则返回0
	 * @param target 边的终止点
	 * @param weight 边的长度
	 * @return 
	 */
	public int setTarget(L target, int weight) {
		Integer currentWeight = 0;
		if (weight == 0) {
			currentWeight = this.removeTarget(target);
		} else if (weight > 0) {
			currentWeight = targets.put(target, weight);
			currentWeight = (currentWeight == null )? 0 : currentWeight;
		}
		return currentWeight;
	}
	/**
	 * 如果weight=0，删去当前点的source，成功返回删去source的weight，不存在返回0
	 * 如果weight！=0，为当前点新增一个source，长度为weight，如果该点已存在，返回旧的weight，否则返回0
	 * @param source 边的起始点
	 * @param weight 边的长度
	 * @return 
	 */
	public int setSouce(L source, int weight) {
		Integer previousWeight = 0;
		if (weight == 0) {
			previousWeight = this.removeSource(source);
		} else if (weight > 0) {
			previousWeight = sources.put(source, weight);
			previousWeight = previousWeight == null ? 0 : previousWeight;
		}
		checkRep();
		return previousWeight;
	}

	/**
	 * @param source 删去的source
	 * @return 存在返回旧的weight，否则返回0
	 */
	public int removeSource(L source) {
		Integer weight = sources.remove(source);
		return weight == null ? 0 : weight;
	}

	/**
	 * @param target 删去的target
	 * @return 存在返回旧的weight，否则返回0
	 */
	public int removeTarget(L target) {
		Integer weight = targets.remove(target);
		return weight == null ? 0 : weight;
	}

	@Override
	public String toString() {
		return String.format("Vertex %s has %d sources and %d targets", this.getName().toString(),
				this.getSources().size(), this.getTargets().size());
	}
}

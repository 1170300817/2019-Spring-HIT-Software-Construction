package circularOrbit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import track.Track;

public class OrbitIterator<E extends Comparable<E>> implements Iterator<E> {
	private List<E> ObjectList;
	private int index;
	private int size;

	/**
	 * 构造方法，初始化 ObjectList作为OrbitIterator的迭代输出
	 * 
	 * @param orbitMap 迭代对象
	 */
	public OrbitIterator(Map<Track, List<E>> orbitMap) {
		index = 0;
		size = 0;
		for (Map.Entry<Track, List<E>> entry : orbitMap.entrySet()) {
			size += entry.getValue().size();
		}
		ObjectList = orbitMap.keySet().stream().sorted().map(orbitMap::get).reduce(new ArrayList<>(),
				(result, element) -> {
					Collections.sort(element);
					result.addAll(element);
					return result;
				});
	}

	/**
	 * 判断是否有下一个
	 * 
	 * @return 是返回true
	 */
	@Override
	public boolean hasNext() {
		return index < size;
	}

	/**
	 * 获取下一个迭代对象的方法
	 * 
	 * @return 下一个E对象
	 */
	@Override
	public E next() {
		return ObjectList.get(index++);
	}

}

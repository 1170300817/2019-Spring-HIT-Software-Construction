/**
 * 
 */
/**
 * @author Administrator
 *
 */
package difference;

import java.util.List;
import java.util.Set;

public class Difference {
	// Abstraction function:
	// trackNumDiff记录轨道数的差异，NumDiff列表记录轨道上物体数目差异，ObjectDiff记录物体差异
	// AF是从一个记录着两个Orbit各类差异数据的抽象数据型到现实两个Orbit真实差异的映射

	// Representation invariant:
	// NumDiff！=null ObjectDiff！=null

	// Safety from rep exposure:
	// 设置关键数据trackNumDiff，trackNumDiffNumDiff和ObjectDiff为private final防止更改

	private final Integer trackNumDiff;
	private final List<Integer> NumDiff;
	private final List<List<Set<String>>> ObjectDiff;

	/**
	 * 构造方法
	 * @param trackNumDiff
	 * @param numDiff
	 * @param ObjectDiff
	 */
	public Difference(Integer trackNumDiff, List<Integer> numDiff, List<List<Set<String>>> ObjectDiff) {
		this.trackNumDiff = trackNumDiff;
		this.NumDiff = numDiff;
		this.ObjectDiff = ObjectDiff;
	}

	/**
	 * 返回描述这个Difference对象的string
	 * @result 描述的String
	 */
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("轨道数差异：" + trackNumDiff + "\n");
		int num = 0;
		for (int i = 0; i < NumDiff.size(); i++) {
			num = i + 1;
			stringBuilder.append("轨道" + num + "的物体数目差异：" + NumDiff.get(i) + "\n");
		}
		for (int i = 0; i < ObjectDiff.size(); i++) {
			num = i + 1;
			stringBuilder
					.append("轨道" + num + "的物体差异：" + ObjectDiff.get(i).get(0) + "-" + ObjectDiff.get(i).get(1) + "\n");
		}
		return stringBuilder.toString();
	}
}
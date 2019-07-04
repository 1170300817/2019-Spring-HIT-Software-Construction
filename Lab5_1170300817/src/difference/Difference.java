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
  private final List<Integer> numDiff;
  private final List<List<Set<String>>> objectDiff;

  /**
   * 构造方法.
   * 
   * @param trackNumDiff 轨道数差异
   * @param numDiff 数目差异
   * @param objectDiff 物体差异
   */
  public Difference(Integer trackNumDiff, List<Integer> numDiff,
      List<List<Set<String>>> objectDiff) {
    this.trackNumDiff = trackNumDiff;
    this.numDiff = numDiff;
    this.objectDiff = objectDiff;
  }

  /**
   * 返回描述这个Difference对象的string.
   * 
   * @result 描述的String
   */
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("轨道数差异：" + trackNumDiff + "\n");
    int num = 0;
    for (int i = 0; i < numDiff.size(); i++) {
      num = i + 1;
      stringBuilder.append("轨道" + num + "的物体数目差异：" + numDiff.get(i) + "\n");
    }
    for (int i = 0; i < objectDiff.size(); i++) {
      num = i + 1;
      stringBuilder.append(
          "轨道" + num + "的物体差异：" + objectDiff.get(i).get(0) + "-" + objectDiff.get(i).get(1) + "\n");
    }
    return stringBuilder.toString();
  }
}

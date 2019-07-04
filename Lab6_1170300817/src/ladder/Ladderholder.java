package ladder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 梯子类，其实是一个梯子的集合，用一个List<Map<Integer, Integer>>来保存所有梯子和他们上面的信息，
 *  其中每一个Map<Integer, Integer>代表一个梯子，第一个Integer是位置，第二个是猴子的id
 */
public class Ladderholder {
  public final int h;
  public final int n;
  public final List<Map<Integer, Integer>> ladders;

  /**
   * 构造函数
   * 
   * @param h 梯子长度
   * @param n 梯子数量
   */
  public Ladderholder(int h, int n) {
    this.h = h;
    this.n = n;
    ladders = new ArrayList<Map<Integer, Integer>>();
    for (int i = 0; i < n; i++) {
      ladders.add(new HashMap<Integer, Integer>());
    }
  }

  public int getN() {
    return n;
  }

  public int getH() {
    return h;
  }
}

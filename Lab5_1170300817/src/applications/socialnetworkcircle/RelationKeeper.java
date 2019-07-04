package applications.socialnetworkcircle;

import exception.SameLabelException;
import java.util.HashSet;

// 读取文件时用的辅助类：因为读取文件读入的过程没法将读取到的人名马上与实例对应起来，所以构造relationKeeper保存每个关系的人名string。
public class RelationKeeper {
  private final String fromString;
  private final String toString;
  private final double weight;
  private static final HashSet<RelationKeeper> keeperSet = new HashSet<RelationKeeper>();

  public String getFromString() {
    return fromString;
  }

  public String getToString() {
    return toString;
  }

  public double getWeight() {
    return weight;
  }

  /**
   * 构造方法.
   * 
   * @param fromString 起始点
   * @param toString 终止点
   * @param weight 权
   * @throws SameLabelException 起止点一样报错
   */
  public RelationKeeper(String fromString, String toString, double weight)
      throws SameLabelException {
    super();
    this.fromString = fromString;
    this.toString = toString;
    this.weight = weight;
    if (fromString.equals(toString)) {
      throw new SameLabelException("从" + fromString + "到" + toString + "的边无效");
    }
    // for (RelationKeeper k : keeperList) {
    // if (k.getFromString().equals(toString) && k.getToString().equals(fromString)) {
    // throw new SameLabelException("从" + fromString + "到" + toString + "的边已经存在");
    // }
    // if (k.getFromString().equals(fromString) && k.getToString().equals(toString)) {
    // throw new SameLabelException("从" + fromString + "到" + toString + "的边已经存在");
    // }
    // }
    keeperSet.add(this);
  }
}

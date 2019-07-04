package track;

public class Track implements Comparable<Track> {
  // Abstraction function:
  // 所以AF是从一个记录着名字和半径的Track抽象数据型到现实轨道的映射

  // Representation invariant:
  // 轨道半径不能为0，名字不能为空

  // Safety from rep exposure:
  // 设置关键数据name，radius为private final防止更改
  // 在有必要的时候使用防御性拷贝
  private final String name;
  private final double radius;

  /**
   * 构造方法.
   * 
   * @param name 名字
   * @param radius 半径
   */
  public Track(String name, double radius) {
    super();
    this.name = name;
    this.radius = radius;
    checkRep();
  }

  /**
   * 获得轨道名字.
   * 
   * @return
   */
  public String getName() {
    return name;
  }

  /**
   * 获得轨道半径.
   * 
   * @return
   */
  public double getRadius() {
    return radius;
  }

  // private void checkRep() {
  // assert(this.radius>=0);
  //
  // }

  /**
   * 重写Comparable接口的compareTo方法.
   * 
   * @return 返回大小比较结果
   */
  @Override
  public int compareTo(Track o) {
    if (this.radius - o.getRadius() == 0) {
      return 0;
    } else if (this.radius - o.getRadius() > 0) {
      return 1;
    } else {
      return -1;
    }
  }

  /**
   * 检查名字非空，半径大于0.
   */
  public void checkRep() {
    assert (this.name != null);
    assert (this.radius >= 0);

  }
}

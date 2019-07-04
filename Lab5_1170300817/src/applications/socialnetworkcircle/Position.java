package applications.socialnetworkcircle;

public class Position {
  // 画图功能的辅助类，保存每个点在画板上的位置使用的抽象数据型

  private final int xcoordinate;
  private final int ycoordinate;

  public int getX() {
    return xcoordinate;
  }

  public int getY() {
    return ycoordinate;
  }

  public Position(int x, int y) {
    this.xcoordinate = x;
    this.ycoordinate = y;
  }
}

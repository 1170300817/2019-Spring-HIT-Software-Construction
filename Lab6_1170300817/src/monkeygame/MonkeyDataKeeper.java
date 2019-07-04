package monkeygame;

/**一个读入文件参数后保存的辅助类，只在v3使用
 * @author Administrator
 *
 */
public class MonkeyDataKeeper {
  public int bornTime;
  public int id;
  public String direction;
  public int velocity;

  public MonkeyDataKeeper(int bornTime, int id, String direction, int velocity) {
    this.bornTime = bornTime;
    this.id = id;
    this.direction = direction;
    this.velocity = velocity;
  }

}

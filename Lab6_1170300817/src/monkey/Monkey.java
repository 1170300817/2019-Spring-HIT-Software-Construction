package monkey;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.JTextArea;
import org.apache.log4j.Logger;
import ladder.Ladderholder;
import strategy.Strategy;

/**
 * 猴子类（线程），实现了Runnable接口，记录着猴子的各类数据 如出生时间bornTime， 消耗时间usedTime，id，方向，速度，策略等等的信息.
 * 主要方法就是run()，静态工厂方法，和在graphics上画图的方法paint。
 */
public class Monkey implements Runnable {

  public final int bornTime;
  public int usedTime = 0;
  public final int id;
  public final String direction;//
  public final int velocity;
  private boolean doned;// 当前回合有没有行动过了
  private static Logger logger = log.LoggerFactory.createLogger(Monkey.class);
  private final Strategy strategy;
  private int currentladderNum = 0;
  private int ladderLength;
  private int position = -1;// 在梯子上的位置
  private static Ladderholder ladders;
  private static Set<Integer> targetPool = new HashSet<>();
  // public static Map<Integer, Integer> reachMonkeyMap = new ConcurrentHashMap<Integer, Integer>();
  private JTextArea textArea;

  public Monkey(Strategy strategy, int bornTime, int id, String direction, int velocity,
      Ladderholder ladders, JTextArea textArea) {
    this.strategy = strategy;
    this.bornTime = bornTime;
    this.id = id;
    this.direction = direction;
    this.velocity = velocity;
    this.doned = false;
    Monkey.ladders = ladders;
    this.textArea = textArea;
  }

  public boolean isDoned() {
    return doned;
  }

  public void setDone() {
    this.doned = true;
  }

  public void setDoned() {
    this.doned = true;
  }

  public void setTargetPool(Set<Integer> targetPool1) {
    targetPool = targetPool1;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Monkey) {
      Monkey m = (Monkey) obj;
      return this.id == m.id;
    }
    return false;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("<生于:" + bornTime);
    sb.append(",ID:" + id);
    sb.append("方向:" + direction);
    sb.append("速度:" + velocity + ">");
    return sb.toString();
  }

  /**
   * 静态工厂方法
   * 
   * @param bornTime
   * @param id
   * @param direction
   * @param velocity
   * @return
   */
  public static Monkey generateMonkey(Strategy strategy, int bornTime, int id, String direction,
      int velocity, Ladderholder ladders, JTextArea textArea) {
    return new Monkey(strategy, bornTime, id, direction, velocity, ladders, textArea);
  }

  @Override
  public void run() {
    this.ladderLength = ladders.getH();
    logger.debug("monkey:" + this.toString() + " 开始运动");
    synchronized (textArea) {
      textArea.append("monkey:" + this.toString() + " 开始运动" + "\n");
    }
    // 一开始选择起始的梯子
    while (currentladderNum == 0) {
      synchronized (ladders) {
        usedTime++;
        try {
          ladders.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        currentladderNum = strategy.choose(ladders.ladders, 0);
        if (currentladderNum == 0) {
          logger.debug("猴子" + id + " 正在等待，离出生已经" + usedTime + "秒");
          synchronized (textArea) {
            textArea.append("猴子" + id + " 正在等待，离出生已经" + usedTime + "秒" + "\n");
          }
        } else {
          logger.debug("猴子" + id + "选择了梯子 " + currentladderNum);
          synchronized (textArea) {
            textArea.append("猴子" + id + "选择了梯子 " + currentladderNum + "\n");
          }

          if (this.direction.equals("L->R")) {
            position = 1;
            ladders.ladders.get(currentladderNum - 1).put(position, id);
          } else if (this.direction.equals("R->L")) {
            // 放在末端
            ladders.ladders.get(currentladderNum - 1).put(ladderLength, id);
            position = ladders.getH();
          }
        }
        // logger.debug("choose status:" + ladders.ladders.toString());
      }
    }
    // 后序的动作
    while (position != -2) {// 未到达
      synchronized (ladders) {
        try {
          ladders.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        if (this.direction.equals("L->R") && position == ladderLength) {
          // 到达最右边
          ladders.ladders.get(currentladderNum - 1).remove(Integer.valueOf(ladderLength));
          position = -2;
          MonkeyGenerator.reachMonkeyMap.put(this.id, usedTime);
          break;
        } else if (this.direction.equals("R->L") && position == 1) {
          // 到达最左边
          ladders.ladders.get(currentladderNum - 1).remove(Integer.valueOf(1));
          position = -2;
          MonkeyGenerator.reachMonkeyMap.put(this.id, usedTime);
          break;
        }

        int availableStep = 0;
        // 获得当前梯子的map
        Map<Integer, Integer> currentladderMap = ladders.ladders.get(currentladderNum - 1);
        int i = 0;
        if (this.direction.equals("L->R")) {
          for (i = position + 1; i <= position + this.velocity && i <= ladderLength; i++) {
            if (currentladderMap.containsKey(Integer.valueOf(i))) {
              // 前面已经有猴子
              break;
            }
          }
          availableStep = i - 1;
          currentladderMap.remove(Integer.valueOf(position));
          position = availableStep;
          logger.debug("猴子" + id + "向右走到了位置：" + availableStep + "离出生已经" + usedTime + "秒");
          synchronized (textArea) {
            textArea
                .append("猴子" + id + "向右走到了位置：" + availableStep + "离出生已经" + usedTime + "秒" + "\n");
          }
          usedTime++;
          // System.out.println(this.velocity);
          currentladderMap.put(Integer.valueOf(position), id);
          logger.debug("当前梯子状态:" + currentladderMap.toString());
          synchronized (textArea) {
            textArea.append("当前梯子状态:" + currentladderMap.toString() + "\n");
          }
        } else {
          for (i = position - 1; i >= position - this.velocity && i >= 1; i--) {
            if (currentladderMap.containsKey(Integer.valueOf(i))) {
              break;
            }
          }
          availableStep = i + 1;
          currentladderMap.remove(Integer.valueOf(position));
          position = availableStep;
          logger.debug("猴子" + id + "向左走到了位置：" + availableStep + "离出生已经" + usedTime + "秒");
          synchronized (textArea) {
            textArea
                .append("猴子" + id + "向左走到了位置：" + availableStep + "离出生已经" + usedTime + "秒" + "\n");
          }
          usedTime++;
          currentladderMap.put(Integer.valueOf(position), id);
          // logger.debug("status:" + currentladderMap.toString());
          logger.debug("当前梯子状态:" + currentladderMap.toString());
          synchronized (textArea) {
            textArea.append("当前梯子状态:" + currentladderMap.toString() + "\n");
          }
        }
      }
    }
    synchronized (targetPool) {
      this.setDone();
      targetPool.add(this.id);
    }
    logger.debug("猴子" + id + " 完成于梯子： " + currentladderNum + "耗时" + usedTime + "秒");
    synchronized (textArea) {
      textArea.append("猴子" + id + " 完成于梯子： " + currentladderNum + "耗时" + usedTime + "秒" + "\n");
    }
  }

  public void paint(Graphics2D graphics) {
    int radium = monkeyPanel.radium;
    int startPosition_x = monkeyPanel.LadderPosition_X;
    int startPosition_y =
        monkeyPanel.LadderPosition_Y + (currentladderNum - 1) * monkeyPanel.ladderGap;
    int rungGap = monkeyPanel.rungGap;
    // int ladderGap = monkeyPanel.ladderGap;
    switch (this.direction) {
      case "L->R":
        graphics.setColor(Color.GREEN);
        graphics.fillOval(startPosition_x + (position) * rungGap - radium,
            (int) (startPosition_y + 0.5 * rungGap - radium), 2 * radium, 2 * radium);
        break;
      case "R->L":
        graphics.setColor(Color.RED);
        graphics.fillOval(startPosition_x + (position) * rungGap - radium,
            (int) (startPosition_y + 0.5 * rungGap - radium), 2 * radium, 2 * radium);
        break;
      default:
        assert false;
    }

  }


}

package monkey;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import javax.swing.Timer;


/**
 * 猴子画板类，是JPanel的子类，动态展示猴子的运动过程
 * 
 * @author Administrator 主要方法：drawLadder画梯子，paintComponent(Graphics)在Graphics上画所有组件（梯子和猴子）
 */
public class monkeyPanel extends JPanel {
  private static final long serialVersionUID = 1L;
  public static final int LadderPosition_X = 30;// 开始画梯子的位置
  public static final int LadderPosition_Y = 30;// 开始画梯子的位置

  public static final int rungGap = 40; // 踏板间隔
  public static final int ladderGap = 50;// 梯子间隔

  public final int ladder_Length;
  public final int rung_num;// 踏板数目
  public final int ladder_num;// 踏板数目
  public static final int radium = 15;// 圆圈（猴子）的半径

  public monkeyPanel(int ladderCount_n, int rungCount_h) {
    this.ladder_num = ladderCount_n;
    this.rung_num = rungCount_h;
    this.ladder_Length = this.rung_num * rungGap + LadderPosition_X;
    Timer timer = new Timer(40, e -> repaint());
    timer.start();
  }

  private void drawLadder(Graphics g, int ladderNum) {
    int startPosition_x = LadderPosition_X;
    int startPosition_y = LadderPosition_Y + ladderNum * ladderGap;
    g.drawLine(startPosition_x, startPosition_y, startPosition_x + ladder_Length, startPosition_y);
    g.drawLine(startPosition_x, startPosition_y + rungGap, startPosition_x + ladder_Length,
        startPosition_y + rungGap);
    for (int i = 1; i <= rung_num; i++) {
      g.drawLine(startPosition_x + i * rungGap, startPosition_y, startPosition_x + i * rungGap,
          startPosition_y + rungGap);
    }
  }

  @Override
  protected synchronized void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();
    for (int i = 0; i < ladder_num; i++) {
      drawLadder(g2d, i);
    }
    synchronized (MonkeyGenerator.getMonkeySet()) {
      for (Monkey monkey : MonkeyGenerator.getMonkeySet()) {
        monkey.paint(g2d);
      }
    }
  }
}

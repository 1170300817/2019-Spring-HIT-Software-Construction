package monkey;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.JTextArea;
import org.apache.log4j.Logger;
import ladder.Ladderholder;
import monkeygame.MonkeyDataKeeper;
import strategy.Emptyfirststrategy;
import strategy.SameDirectionStrategy;
import strategy.Strategy;

/**
 * MonkeyGenerator类（线程），实现了Runnable接口，功能就是定时按要求产生一定数目的猴子 主要方法：run()，构造方法，还有计算公平性的方法getFairness。
 */
public class MonkeyGenerator implements Runnable {
  private static Logger logger = log.LoggerFactory.createLogger(Monkey.class);

  private int timeGap_t;
  private int monkeyCount_N;
  private int monkeyPerSecond_k;
  private int maxVelocity_MV;
  private JTextArea textArea;
  private JTextArea textArea_data1;
  private Ladderholder ladders;
  private int strategyFlag;
  private Map<Integer, Set<MonkeyDataKeeper>> moneyMap = null;
  // private Map<Integer, Monkey> monkeyMap =new
  public static Map<Integer, Integer> reachMonkeyMap = new ConcurrentHashMap<Integer, Integer>();
  private static Set<Monkey> monkeySet = new HashSet<Monkey>();

  public static Set<Monkey> getMonkeySet() {
    return monkeySet;
  }

  private int clock;

  public MonkeyGenerator(int ladderCount_n, int rungCount_h, int monkeyCount_N, int timeGap_t,
      int monkeyPerSecond_k, int maxVelocity_MV, JTextArea textArea_info, JTextArea textArea_data1,
      int strategyFlag) {
    this.timeGap_t = timeGap_t;
    this.monkeyCount_N = monkeyCount_N;
    this.monkeyPerSecond_k = monkeyPerSecond_k;
    this.maxVelocity_MV = maxVelocity_MV;
    ladders = new Ladderholder(rungCount_h, ladderCount_n);
    this.clock = 0;
    this.textArea = textArea_info;
    this.textArea_data1 = textArea_data1;
    this.strategyFlag = strategyFlag;
  }

  public MonkeyGenerator(int ladderCount_n, int rungCount_h, int monkeyCount_N,
      JTextArea textArea_info, JTextArea textArea_data1,
      Map<Integer, Set<MonkeyDataKeeper>> moneyMap) {
    this.monkeyCount_N = monkeyCount_N;
    ladders = new Ladderholder(rungCount_h, ladderCount_n);
    this.clock = 0;
    this.textArea = textArea_info;
    this.textArea_data1 = textArea_data1;
    this.moneyMap = moneyMap;
  }

  @Override
  public void run() {
    Random random = new Random();
    int randomStrategyNum = 0;
    int totalMonkey = 0;
    int randomDirectionNum;

    Strategy newStrategy = null;
    String directionString;
    int velocity;
    // int arriveCount = 0;
    while (reachMonkeyMap.size() < monkeyCount_N) {
      // 时钟++
      if (moneyMap == null) {
        if (clock % timeGap_t == 0) {
          for (int i = 0; i < monkeyPerSecond_k; i++) {
            if (strategyFlag == 0) {
              randomStrategyNum = random.nextInt(2);
            } else if (strategyFlag == 1) {
              randomStrategyNum = 0;
            } else if (strategyFlag == 2) {
              randomStrategyNum = 1;
            }
            randomDirectionNum = random.nextInt(2);
            velocity = random.nextInt(maxVelocity_MV) + 1;
            if (randomDirectionNum == 0) {
              directionString = "L->R";
            } else {
              directionString = "R->L";
            }
            if (randomStrategyNum == 0) {
              newStrategy = new Emptyfirststrategy();
            } else if (randomStrategyNum == 1) {
              newStrategy =
                  new SameDirectionStrategy(directionString, ladders.getH(), ladders.getN());
            }
            if (totalMonkey < monkeyCount_N) {
              Monkey newMonkey = Monkey.generateMonkey(newStrategy, clock, ++totalMonkey,
                  directionString, velocity, ladders, textArea);
              synchronized (monkeySet) {
                monkeySet.add(newMonkey);
              }
              (new Thread(newMonkey)).start();
            }
          }
        }
      } else {
        if (moneyMap.containsKey(clock)) {
          for (MonkeyDataKeeper MonkeyData : moneyMap.get(clock)) {
            Strategy newStrategy1 =
                new SameDirectionStrategy(MonkeyData.direction, ladders.getH(), ladders.getN());
            Monkey newMonkey = Monkey.generateMonkey(newStrategy1, clock, MonkeyData.id,
                MonkeyData.direction, MonkeyData.velocity, ladders, textArea);
            ++totalMonkey;
            synchronized (monkeySet) {
              monkeySet.add(newMonkey);
            }
            (new Thread(newMonkey)).start();
          }
        }
      }
      clock++;
      // System.out.println(reachMonkeyMap.size());
      // System.out.println("大N+" + monkeyCount_N);
      try {
        synchronized (ladders) {
          ladders.notifyAll();
        }
        Thread.sleep(100);
        logger.debug("=========================================" + clock
            + "=================================");
        synchronized (textArea) {
          textArea.append("===========" + clock + "============\n");
        }
        textArea_data1.setText("");
        double inoutRate = reachMonkeyMap.size() / Double.valueOf(clock);
        textArea_data1.append("吞吐量为：" + inoutRate);

      } catch (InterruptedException e) {
        e.printStackTrace();
      }

    }
  }

  public static double getFairness(JTextArea textArea) {
    double fairness = 0;
    for (Monkey m1 : monkeySet) {
      for (Monkey m2 : monkeySet) {
        if ((m1.bornTime - m2.bornTime)
            * (m1.bornTime + m1.usedTime - m2.bornTime - m2.usedTime) >= 0) {
          fairness += 0.5;
        } else {
          fairness -= 0.5;
        }
      }
    }
    int size = monkeySet.size();
    fairness = fairness / (size * (size - 1) / 2);
    textArea.append("公平性：" + fairness);
    return fairness;
  }

  // public static void main(String[] args) {
  // MonkeyGenerator generator = new MonkeyGenerator(1, 5, 3, 1, 1, 2);
  // (new Thread(generator)).start();
  // }

}

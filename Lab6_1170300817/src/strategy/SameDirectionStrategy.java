package strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 空优先+同向快速优先
 * 
 * @author Administrator
 *
 */
public class SameDirectionStrategy implements Strategy {
  private String directionString;
  private int h;
  private int n;
  private List<Integer> speedList = new ArrayList<Integer>();
  private List<Integer> positionList = new ArrayList<Integer>();
  private List<Integer> monkeyList = new ArrayList<Integer>();

  public SameDirectionStrategy(String directionString, int h, int n) {
    this.directionString = directionString;
    this.h = h;
    this.n = n;
    for (int i = 0; i < n; i++) {
      speedList.add(0);
    }
    for (int i = 0; i < n; i++) {
      positionList.add(-1);
      monkeyList.add(-1);
    }
  }

  @Override
  public int choose(List<Map<Integer, Integer>> ladders, int time) {
    int edge = 1;
    if (this.directionString.equals("R->L")) {
      edge = h;
    }
    for (int i = 0; i < ladders.size(); i++) {
      Map<Integer, Integer> currentLadder = ladders.get(i);
      if (currentLadder.containsKey(Integer.valueOf(edge))) {
        positionList.set(i, edge);
        monkeyList.set(i, currentLadder.get(edge));
        continue;
      }
      if (this.directionString.equals("L->R")) {
        if (currentLadder.isEmpty()) {
          return (i + 1);
        }

        for (int j = 1; j <= h; j++) {
          if (currentLadder.containsKey(j)) {
            int currentMonkeyNum = currentLadder.get(j);
            if (monkeyList.get(i).equals(currentMonkeyNum)) {
              int lastRung = positionList.get(i);
              positionList.set(i, j);
              int obSpeed = j - lastRung;
              speedList.set(i, obSpeed);
              break;
            } else {
              monkeyList.set(i, currentMonkeyNum);
              positionList.set(i, j);
              break;
            }
          }
        }
      } else {
        if (currentLadder.isEmpty()) {
          return (i + 1);
        }
        for (int j = h; j >= 1; j--) {
          if (currentLadder.containsKey(Integer.valueOf(j))) {
            int seeMonkey = currentLadder.get(Integer.valueOf(j));
            if (monkeyList.get(i).equals(Integer.valueOf(seeMonkey))) {
              int lastRung = positionList.get(i);
              positionList.set(i, Integer.valueOf(j));
              int obSpeed = lastRung - j;
              speedList.set(i, Integer.valueOf(obSpeed));
              break;
            } else {
              monkeyList.set(i, Integer.valueOf(seeMonkey));
              positionList.set(i, Integer.valueOf(j));
              speedList.set(i, Integer.valueOf(0));
              break;
            }
          }
        }
      }
    }
    int maxSpeed = 0;
    int maxIndex = -1;
    // 选择最大的
    for (int i = 0; i < n; i++) {
      if (speedList.get(i) > maxSpeed) {
        maxSpeed = speedList.get(i);
        maxIndex = i;
      }
    }
    if (maxIndex != -1) {
      return (maxIndex + 1);
    }
    return 0;
  }
}

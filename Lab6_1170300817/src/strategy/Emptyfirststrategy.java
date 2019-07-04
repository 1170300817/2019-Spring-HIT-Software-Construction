package strategy;

import java.util.List;
import java.util.Map;

public class Emptyfirststrategy implements Strategy {
  @Override
  public int choose(List<Map<Integer, Integer>> ladders, int time) {
    for (int i = 0; i < ladders.size(); i++) {
      Map<Integer, Integer> oneLadder = ladders.get(i);
      if (oneLadder.isEmpty()) {
        return (i + 1);
      }
    }
    return 0;
  }
}

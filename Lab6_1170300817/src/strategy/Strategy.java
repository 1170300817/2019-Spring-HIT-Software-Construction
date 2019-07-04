package strategy;

import java.util.List;
import java.util.Map;

public interface Strategy {
  public int choose(List<Map<Integer, Integer>> ladders, int time);

}

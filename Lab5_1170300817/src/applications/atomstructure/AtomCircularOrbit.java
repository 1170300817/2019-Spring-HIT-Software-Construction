package applications.atomstructure;

import circularorbit.ConcreteCircularOrbit;
import java.util.List;
import track.Track;

public class AtomCircularOrbit extends ConcreteCircularOrbit<Particle, Particle> {
  // Abstraction function:
  // AtomCircularOrbit是一个由多个Track，多个电子和一个中心微粒组成的对轨道结构的抽象
  // 所以AF是AtomCircularOrbit到真实的微粒轨道结构的映射

  // Representation invariant:
  // 轨道不能重名，不能有轨道具有相同半径

  // Safety from rep exposure:
  // 同父类
  // 在有必要的时候使用防御性拷贝

  public AtomCircularOrbit() {
    super();
  }


  /**
   * 实现跃迁：将一个轨道上的一个电子移到另一个轨道，没有电子返回false.
   * @param t1 起始轨道
   * @param t2 终止轨道
   * @return
   */
  public boolean transit(Track t1, Track t2) {
    if (orbitMap.get(t1).size() >= 1) {
      orbitMap.get(t2).add(Particle.getElectron());
      orbitMap.get(t1).remove(0);
      return true;
    } else {
      return false;
    }
  }

  /**
   * 从某条轨道删去一个电子，因为电子互相之间没有区别，所以只需一个轨道参数.
   * 
   * @param t 移除目标轨道
   * @return
   */
  public boolean removeElectron(Track t) {
    if (orbitMap.get(t).size() >= 1) {
      orbitMap.get(t).remove(0);
      return true;
    } else {
      return false;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    List<Track> trackList = this.getSortedTracks();
    for (int i = 0; i < trackList.size(); i++) {
      Track currentTrack = trackList.get(i);
      sb.append(currentTrack.getName() + "上有：" + orbitMap.get(currentTrack).size() + "个电子\n");
    }
    return sb.toString();
  }

}

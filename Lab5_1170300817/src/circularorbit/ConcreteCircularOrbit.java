package circularorbit;

import difference.Difference;
import exception.ObjectNoFoundException;
import java.awt.Color;
import java.awt.Graphics;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JPanel;
import phsicalobject.PhysicalObject;
import track.Track;

public class ConcreteCircularOrbit<L, E extends PhysicalObject> implements CircularOrbit<L, E> {
  // Abstraction function:
  // ConcreteCircularOrbit<L, E>是一个由多个Track，多个轨道物体和中心物体组成的对轨道结构的抽象
  // OrbitMap抽象轨道和物体的一对多的关系
  // 所以AF是CircularOrbit<L, E>到真实的轨道结构的映射

  // Representation invariant:
  // 轨道不能重名，不能有轨道具有相同半径

  // Safety from rep exposure:
  // 设置关键数据OrbitMap，centralRelationMap，trackRelationMap为protected final
  // 在有必要的时候使用防御性拷贝
  private L centralObject = null;
  protected final Map<Track, List<E>> orbitMap = new HashMap<>();
  protected final Map<E, Double> centralRelationMap = new HashMap<>();
  protected final Map<E, Map<E, Double>> trackRelationMap = new HashMap<>();

  /**
   * 设置中心物体.
   * 
   * @param centralObject 中心物体
   */
  public void setCentralObject(L centralObject) {
    this.centralObject = centralObject;
  }

  /**
   * 返回中心物体.
   * 
   * @return 中心物体
   */
  public L getCentralObject() {
    return centralObject;
  }

  /**
   * 增加一条轨道.
   * 
   * @param t 轨道
   * @return
   */
  public boolean addTrack(Track t) {
    if (orbitMap.containsKey(t)) {
      return false;
    } else {
      orbitMap.put(t, new ArrayList<E>());
      return true;
    }
  }

  /**
   * 删除一条轨道.
   * 
   * @param t 轨道
   * @return 成功返回true 否则false
   */
  public boolean removeTrack(Track t) {
    assert t != null;
    if (orbitMap.containsKey(t)) {
      orbitMap.remove(t);
      return true;
    } else {
      // System.err.println("想移除的轨道不存在");
      return false;
    }
  }

  /**
   * 返回轨道数目.
   * 
   * @return
   */
  @Override
  public Integer getTrackNum() {
    return orbitMap.keySet().size();
  }

  /**
   * 向轨道上增加物体.
   * 
   * @param t 目标轨道
   * @param object 增加的物体
   * @return
   */
  public boolean addObjectToTrack(Track t, E object) {
    assert t != null;
    assert object != null;
    return orbitMap.get(t).add(object);
  }

  /**
   * 删去一个轨道上的某个物体.
   * 
   * @param t 轨道
   * @param object 物体
   * @return 成功返回true 否则false
   */
  public boolean removeObjectOnTrack(Track t, E object) {
    assert t != null;
    assert object != null;
    return orbitMap.get(t).remove(object);


  }

  /**
   * 在两个轨道物体之间新增关系.
   * 
   * @param object1 物体
   * @param object2 物体
   * @param distance 距离
   * @return 成功返回true 否则false
   */
  public boolean addtrackRelation(E object1, E object2, double distance) {
    assert object1 != null;
    assert object2 != null;
    if (!trackRelationMap.containsKey(object1)) {
      trackRelationMap.put(object1, new HashMap<E, Double>());
    }
    if (!trackRelationMap.containsKey(object2)) {
      trackRelationMap.put(object2, new HashMap<E, Double>());
    }
    if (distance == 0) {
      trackRelationMap.get(object1).remove(object2);
      trackRelationMap.get(object2).remove(object1);
      return true;
    } else if (trackRelationMap.get(object1).containsKey(object2)) {
      return false;
    } else {
      trackRelationMap.get(object1).put(object2, distance);
      return true;
    }
  }

  /**
   * 在轨道物体和中心物体之间新增关系.
   * 
   * @param object 物体
   * @param distance 距离
   * @return 成功返回true 否则false
   */
  public boolean addcentralRelation(E object, double distance) {
    assert object != null;

    if (!trackRelationMap.containsKey(object)) {
      trackRelationMap.put(object, new HashMap<E, Double>());
    }
    if (distance == 0) {
      centralRelationMap.remove(object);
      return true;
    } else if (centralRelationMap.containsKey(object)) {
      return false;
    } else {
      centralRelationMap.put(object, distance);
      return true;
    }

  }

  /**
   * 计算轨道系统的信息熵.
   * 
   * @return 信息熵
   */
  public double getObjectDistributionEntropy() {
    double entropy = 0.0;
    int number = orbitMap.keySet().size();
    int i = 0;
    int sum = 0;

    List<Integer> p = new ArrayList<>();
    for (List<E> list : orbitMap.values()) {
      p.add(list.size());
      sum += list.size();
    }
    for (i = 0; i < number; i++) {
      if (p.get(i) != 0) {
        entropy -= (p.get(i) * 1.0 / sum) * Math.log(p.get(i) * 1.0 / sum) / Math.log(2);
      }
    }
    return entropy;
  }

  /**
   * 获得两物体间逻辑距离.
   * 
   * @param e1 物体
   * @param e2 物体
   * @return 逻辑距离
   */
  public int getLogicalDistance(E e1, E e2) {
    assert e1 != null;
    assert e2 != null;
    if (e1 == e2) {
      return 0;
    }

    Queue<E> queue = new LinkedList<>();
    Map<E, Integer> distantMap = new HashMap<>();
    queue.offer(e1);
    distantMap.put(e1, 0);
    while (!queue.isEmpty()) {
      E topObject = queue.poll();
      int nowDis = distantMap.get(topObject);
      Map<E, Double> friendList = trackRelationMap.get(topObject);
      for (Map.Entry<E, Double> entry : friendList.entrySet()) {
        if (!distantMap.containsKey(entry.getKey())) {
          distantMap.put(entry.getKey(), nowDis + 1);
          queue.offer(entry.getKey());
          if (entry.getKey() == e2) {
            return distantMap.get(e2);
          }
        }
      }
    }
    return Integer.MAX_VALUE;
  }

  /**
   * 比较当前orbit和目标orbit的不同.
   * 
   * @param that 目标orbit
   * @return 一个different对象，记录两个orbit的区别
   */
  public Difference getDifference(CircularOrbit<L, E> that) {
    Integer trackNumDiff = null;
    List<Integer> numDiff = new ArrayList<Integer>();
    List<List<Set<String>>> objectDiff = new ArrayList<>();

    trackNumDiff = this.getTrackNum() - that.getTrackNum();
    List<Track> sortedTracks1 = this.getSortedTracks();
    List<Track> sortedTracks2 = that.getSortedTracks();
    int i = 0;

    while (i < sortedTracks1.size() && i < sortedTracks2.size()) {

      objectDiff.add(new ArrayList<Set<String>>());
      objectDiff.get(i).add(new HashSet<String>());
      objectDiff.get(i).add(new HashSet<String>());

      numDiff.add(this.getObjectNumonTrack(sortedTracks1.get(i))
          - that.getObjectNumonTrack(sortedTracks2.get(i)));
      for (E currentObj : this.getObjectonTrack(sortedTracks1.get(i))) {
        if (!that.getObjectonTrack(sortedTracks2.get(i)).contains(currentObj)) {
          objectDiff.get(i).get(0).add(currentObj.getName());
        }
      }
      for (E currentObj : that.getObjectonTrack(sortedTracks2.get(i))) {
        if (!this.getObjectonTrack(sortedTracks1.get(i)).contains(currentObj)) {
          objectDiff.get(i).get(1).add(currentObj.getName());
        }
      }
      i++;
    }
    while (i < sortedTracks1.size()) {
      numDiff.add(this.getObjectNumonTrack(sortedTracks1.get(i)));

      objectDiff.add(new ArrayList<Set<String>>());
      objectDiff.get(i).add(new HashSet<String>());
      objectDiff.get(i).add(new HashSet<String>());

      for (E currentObj : this.getObjectonTrack(sortedTracks1.get(i))) {
        objectDiff.get(i).get(0).add(currentObj.getName());
      }
      i++;
    }
    while (i < sortedTracks2.size()) {
      numDiff.add(-that.getObjectNumonTrack(sortedTracks2.get(i)));

      objectDiff.add(new ArrayList<Set<String>>());
      objectDiff.get(i).add(new HashSet<String>());
      objectDiff.get(i).add(new HashSet<String>());

      for (E currentObj : that.getObjectonTrack(sortedTracks2.get(i))) {
        objectDiff.get(i).get(1).add(currentObj.getName());
      }
      i++;
    }

    Difference difference = new Difference(trackNumDiff, numDiff, objectDiff);
    return difference;
  }

  /**
   * 获得当前orbit包含的所有轨道按半径排列成的链表.
   * 
   * @return 半径有序的Track链表
   */
  public List<Track> getSortedTracks() {
    List<Track> listTracks = new ArrayList<>();
    for (Track t : orbitMap.keySet()) {
      listTracks.add(t);
    }
    Collections.sort(listTracks);
    return listTracks;
  }

  public Integer getObjectNumonTrack(Track t) {
    return orbitMap.get(t).size();
  }

  /**
   * 可视化方法.
   */
  @Override
  public void drawpicture() {
    JFrame frame = new JFrame();
    String title = "可视化";
    int width = 800;
    int height = 800;
    JPanel jpanel = new JPanel() {
      private static final long serialVersionUID = 1L;

      @Override
      public void paint(Graphics graphics) {
        super.paint(graphics);

        for (Track t : orbitMap.keySet()) {
          int radius = (int) t.getRadius();
          graphics.drawOval(400 - (int) (0.5 * radius), 400 - (int) (0.5 * radius), radius, radius);
          int objectNum = getObjectNumonTrack(t);
          double angle = objectNum == 0 ? 0 : 360.0 / (double) objectNum;
          int i = 1;
          for (PhysicalObject p : getObjectonTrack(t)) {
            int x = (int) (400 + 0.5 * radius * Math.cos(Math.PI * i * angle / 180));
            int y = (int) (400 + 0.5 * radius * Math.sin(Math.PI * i * angle / 180));
            graphics.setColor(Color.BLUE);
            graphics.drawOval(x - 5, y - 5, 10, 10);
            graphics.fillOval(x - 5, y - 5, 10, 10);
            graphics.drawString(p.getName(), x + 5, y);
            i++;
            graphics.setColor(Color.BLACK);
          }
        }
        graphics.setColor(Color.RED);
        graphics.drawOval(400 - 10, 400 - 10, 20, 20);
        graphics.fillOval(400 - 10, 400 - 10, 20, 20);
      }
    };
    frame.add(jpanel);
    frame.setTitle(title);
    frame.setSize(width, height);
    frame.setVisible(true);
  }

  /*
   * (non-Javadoc)
   * 
   * @see circularOrbit.CircularOrbit#getObjectonTrack(track.Track)
   */
  @Override
  public List<E> getObjectonTrack(Track t) {
    assert t != null;
    return orbitMap.get(t);
  }

  // public static void main(String[] args) {
  // CircularOrbit<CentralObject, PhysicalObject> circular = new
  // ConcreteCircularOrbit<CentralObject, PhysicalObject>();
  // CentralObject center = new CentralObject("sun");
  // Track t1 = new Track("track1", 100);
  // Track t2 = new Track("track2", 200);
  // Track t3 = new Track("track3", 300);
  // PhysicalObject ob1 = new PhysicalObject("object1");
  // PhysicalObject ob2 = new PhysicalObject("object2");
  // PhysicalObject ob3 = new PhysicalObject("object3");
  // PhysicalObject ob4 = new PhysicalObject("object4");
  // PhysicalObject ob5 = new PhysicalObject("object5");
  // PhysicalObject ob6 = new PhysicalObject("object6");
  // PhysicalObject ob7 = new PhysicalObject("object7");
  // PhysicalObject ob8 = new PhysicalObject("object8");
  // PhysicalObject ob9 = new PhysicalObject("object9");
  //
  // circular.setCentralObject(center);
  // circular.addTrack(t1);
  // circular.addTrack(t2);
  // circular.addTrack(t3);
  // circular.addObjectToTrack(t1, ob1);
  // circular.addObjectToTrack(t1, ob2);
  // circular.addObjectToTrack(t1, ob3);
  // circular.addObjectToTrack(t2, ob4);
  // circular.addObjectToTrack(t3, ob5);
  // circular.addObjectToTrack(t3, ob6);
  // circular.addObjectToTrack(t3, ob7);
  // circular.addObjectToTrack(t3, ob8);
  // circular.addObjectToTrack(t3, ob9);
  // circular.drawpicture();
  // }

  /**
   * 判断当前Orbit是否包含某个元素e.
   * 
   * @param e 元素e
   * @return 成功返回true 否则false
   */
  @Override
  public boolean contains(E e) {
    assert e != null;

    for (Track t : orbitMap.keySet()) {
      if (orbitMap.get(t).contains(e)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 返回某个元素e所在的Object对象.
   * 
   * @param e 某个元素
   * @return
   */
  @Override
  public Track getObjectTrack(E e) throws ObjectNoFoundException {
    assert e != null;

    for (Track t : orbitMap.keySet()) {
      if (orbitMap.get(t).contains(e)) {
        return t;
      }
    }
    throw new ObjectNoFoundException("没有找到该物体");
  }

  @Override
  public Track getObjectTrack(String name) throws ObjectNoFoundException {
    for (Track t : orbitMap.keySet()) {
      for (PhysicalObject p : orbitMap.get(t)) {
        if (p.getName().equals(name)) {
          return t;
        }
      }
    }
    throw new ObjectNoFoundException("没有找到名为" + name + "的物体");
  }

  /**
   * 返回与中心连接的物体.
   * 
   * @return 物体构成的集合
   */
  public Set<E> getCentralConnectedObject() {
    return new HashSet<>(centralRelationMap.keySet());

  }

  /**
   * 返回与某个轨道物体连接的所有物体.
   * 
   * @return 物体构成的集合
   */
  public Set<E> getTrackConnectedObject(E object) {
    assert object != null;
    return trackRelationMap.get(object).keySet();
  }

  /**
   * checkrep.
   * 
   * @return
   */
  public boolean checkRep() {
    assert centralObject != null;
    assert centralRelationMap != null;
    assert trackRelationMap != null;
    assert orbitMap != null;
    return true;

  }

}

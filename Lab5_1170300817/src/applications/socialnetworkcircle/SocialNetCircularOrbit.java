package applications.socialnetworkcircle;

import circularorbit.ConcreteCircularOrbit;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JPanel;
import track.Track;

public class SocialNetCircularOrbit extends ConcreteCircularOrbit<Person, Person> {
  // Abstraction function:
  // SocialNetCircularOrbit是一个由多个Track，多个朋友和中心用户组成的对人际关系轨道结构的抽象
  // 所以AF是SocialNetCircularOrbit到真实的人际关系层级轨道结构的映射

  // Representation invariant:
  // 轨道不能重名，不能有轨道具有相同半径

  // Safety from rep exposure:
  // 同父类
  // 在有必要的时候使用防御性拷贝
  public SocialNetCircularOrbit() {
    super();
  }

  /**
   * 重写toString方法.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(this.getCentralObject().getName() + "的关系网:\n");
    List<Track> trackList = this.getSortedTracks();
    for (int i = 0; i < trackList.size(); i++) {
      Track currentTrack = trackList.get(i);
      sb.append(currentTrack.getName() + "上有:");
      for (Person a : orbitMap.get(currentTrack)) {
        sb.append(a.getName() + "\t");
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  /**
   * 重写drawpicture方法，可视化可以画关系了.
   */
  @Override
  public void drawpicture() {
    JFrame frame = new JFrame();
    String title = "可视化";
    int width = 800;
    int height = 800;
    // frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    JPanel jpanel = new JPanel() {
      private static final long serialVersionUID = 1L;

      @Override
      public void paint(Graphics graphics) {
        super.paint(graphics);
        Map<Person, Position> positionMap = new HashMap<Person, Position>();

        for (Track t : orbitMap.keySet()) {
          int radius = (int) t.getRadius();
          graphics.drawOval(400 - (int) (0.5 * radius), 400 - (int) (0.5 * radius), radius, radius);
          int objectNum = getObjectNumonTrack(t);
          double angle = objectNum == 0 ? 0 : 360 / objectNum;
          int i = 1;
          for (Person p : getObjectonTrack(t)) {
            int x = (int) (400 + 0.5 * radius * Math.cos(Math.PI * i * angle / 180));
            int y = (int) (400 + 0.5 * radius * Math.sin(Math.PI * i * angle / 180));
            positionMap.put(p, new Position(x, y));
            // System.out.println(p.getName()+000+x+000+y);
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
        graphics.setColor(Color.GREEN);
        for (Person p : getCentralConnectedObject()) {
          graphics.drawLine(400, 400, positionMap.get(p).getX(), positionMap.get(p).getY());
        }
        for (Track t : getSortedTracks()) {
          for (Person start : getObjectonTrack(t)) {
            for (Person end : trackRelationMap.get(start).keySet()) {
              graphics.drawLine(positionMap.get(start).getX(), positionMap.get(start).getY(),
                  positionMap.get(end).getX(), positionMap.get(end).getY());
            }
          }
        }
      }
    };
    frame.add(jpanel);
    frame.setTitle(title);
    frame.setSize(width, height);
    frame.setVisible(true);
  }

  /**
   * 重整轨道结构.
   */
  public void reArrange() {
    Map<Track, List<Person>> currentOrbitMap = new HashMap<>();
    List<Track> trackList = new ArrayList<>();
    Set<Person> finishedPerson = new HashSet<>();
    Track track1 = new Track("track0", 50);
    currentOrbitMap.put(track1, new ArrayList<Person>());
    trackList.add(track1);
    for (Person p : this.getCentralConnectedObject()) {
      currentOrbitMap.get(track1).add(p);
      finishedPerson.add(p);
    }
    int i = 0;
    boolean flag = true;
    while (flag) {
      flag = false;
      i++;
      Track t = new Track("track" + i, 50 + 100 * i);
      Set<Person> temSet = new HashSet<>();
      for (Person p : finishedPerson) {
        if (this.getTrackConnectedObject(p).size() > 0) {
          for (Person peo : trackRelationMap.get(p).keySet()) {
            if (!finishedPerson.contains(peo)) {
              temSet.add(peo);
              flag = true;
            }
          }
        }
      }
      if (flag) {
        trackList.add(t);
        currentOrbitMap.put(t, new ArrayList<Person>());
        currentOrbitMap.get(t).addAll(temSet);
        finishedPerson.addAll(temSet);
      }
    }
    orbitMap.clear();
    for (Track t : trackList) {
      this.addTrack(t);
    }

    for (Map.Entry<Track, List<Person>> e : currentOrbitMap.entrySet()) {
      for (Person object : e.getValue()) {
        this.addObjectToTrack(e.getKey(), object);
      }
    }
  }

  /**
   * 计算亲密度.
   * 
   * @param p 轨道上的人
   * @return 亲密度
   */
  public double getIntimacy(Person p) {
    Track track = this.getSortedTracks().get(0);
    if (!orbitMap.get(track).contains(p)) {
      System.out.println("不存在");
      return 0;
    }
    Double intimacy = 0.0;
    Queue<Person> queue = new LinkedList<>();
    Map<Person, Double> intimacyMap = new HashMap<>();
    queue.offer(p);
    intimacyMap.put(p, centralRelationMap.get(p));
    while (!queue.isEmpty()) {
      Person topObject = queue.poll();
      Double nowDis = intimacyMap.get(topObject);
      intimacy += nowDis;
      Map<Person, Double> friendList = trackRelationMap.get(topObject);
      for (Map.Entry<Person, Double> entry : friendList.entrySet()) {
        if (!intimacyMap.containsKey(entry.getKey())) {
          intimacyMap.put(entry.getKey(), nowDis * entry.getValue());
          queue.offer(entry.getKey());
        }
      }
    }
    return intimacy;
  }

}



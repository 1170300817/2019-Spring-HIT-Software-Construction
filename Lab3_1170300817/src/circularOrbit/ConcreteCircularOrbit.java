package circularOrbit;

import static org.junit.Assert.assertTrue;
import java.awt.Color;
import java.awt.Graphics;
import java.util.*;
import java.lang.Math;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import difference.Difference;
import phsicalObject.PhysicalObject;
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
	private L centralObject;
	protected final Map<Track, List<E>> OrbitMap = new HashMap<>();
	protected final Map<E, Double> centralRelationMap = new HashMap<>();
	protected final Map<E, Map<E, Double>> trackRelationMap = new HashMap<>();

	/**
	 * 设置中心物体
	 * 
	 * @param centralObject
	 */
	public void setCentralObject(L centralObject) {
		this.centralObject = centralObject;
	}

	/**
	 * 返回中心物体
	 * 
	 * @return 中心物体
	 */
	public L getCentralObject() {
		return centralObject;
	}

	/**
	 * 增加一条轨道
	 * 
	 * @param t
	 * @return
	 */
	public boolean addTrack(Track t) {
		if (OrbitMap.containsKey(t)) {
			return false;
		} else {
			OrbitMap.put(t, new ArrayList<E>());
			return true;
		}
	}

	/**
	 * 删除一条轨道
	 * 
	 * @param t
	 * @return 成功返回true 否则false
	 */
	public boolean removeTrack(Track t) {
		if (OrbitMap.containsKey(t)) {
			OrbitMap.remove(t);
			return true;
		} else {
//			System.err.println("想移除的轨道不存在");
			return false;
		}
	}

	/**
	 * 返回轨道数目
	 * 
	 * @return
	 */
	@Override
	public Integer getTrackNum() {
		return OrbitMap.keySet().size();
	}

	/**
	 * 向轨道上增加物体
	 * 
	 * @param t      目标轨道
	 * @param object 增加的物体
	 * @return
	 */
	public boolean addObjectToTrack(Track t, E object) {
		return OrbitMap.get(t).add(object);
	}

	/**
	 * 删去一个轨道上的某个物体
	 * 
	 * @param t
	 * @param object
	 * @return 成功返回true 否则false
	 */
	public boolean removeObjectOnTrack(Track t, E object) {
		OrbitMap.get(t).remove(object);
		return false;

	}

	/**
	 * 在两个轨道物体之间新增关系
	 * 
	 * @param object1
	 * @param object2
	 * @param distance
	 * @return 成功返回true 否则false
	 */
	public boolean addtrackRelation(E object1, E object2, double distance) {
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
	 * 在轨道物体和中心物体之间新增关系
	 * 
	 * @param object
	 * @param distance
	 * @return 成功返回true 否则false
	 */
	public boolean addcentralRelation(E object, double distance) {

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
	 * 计算轨道系统的信息熵
	 * 
	 * @return 信息熵
	 */
	public double getObjectDistributionEntropy() {
		double Entropy = 0.0;
		int number = OrbitMap.keySet().size();
		int i = 0, sum = 0;

		List<Integer> P = new ArrayList<>();
		for (List<E> list : OrbitMap.values()) {
			P.add(list.size());
			sum += list.size();
		}
		for (i = 0; i < number; i++) {
			if (P.get(i) != 0) {
				Entropy -= (P.get(i) * 1.0 / sum) * Math.log(P.get(i) * 1.0 / sum) / Math.log(2);
			}
		}
		return Entropy;
	}

	/**
	 * 获得两物体间逻辑距离
	 * 
	 * @param e1
	 * @param e2
	 * @return 逻辑距离
	 */
	public int getLogicalDistance(E e1, E e2) {
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
			for (Map.Entry<E, Double> entry : friendList.entrySet())
				if (!distantMap.containsKey(entry.getKey())) {
					distantMap.put(entry.getKey(), nowDis + 1);
					queue.offer(entry.getKey());
					if (entry.getKey() == e2) {
						return distantMap.get(e2);
					}
				}
		}
		return 0;
	}

	/**
	 * 比较当前orbit和目标orbit的不同
	 * 
	 * @param c 目标orbit
	 * @return 一个different对象，记录两个orbit的区别
	 */
	public Difference getDifference(CircularOrbit<L, E> that) {
		Integer trackNumDiff = null;
		List<Integer> NumDiff = new ArrayList<Integer>();
		List<List<Set<String>>> ObjectDiff = new ArrayList<>();

		trackNumDiff = this.getTrackNum() - that.getTrackNum();
		List<Track> SortedTracks1 = this.getSortedTracks();
		List<Track> SortedTracks2 = that.getSortedTracks();
		int i = 0;

		while (i < SortedTracks1.size() && i < SortedTracks2.size()) {

			ObjectDiff.add(new ArrayList<Set<String>>());
			ObjectDiff.get(i).add(new HashSet<String>());
			ObjectDiff.get(i).add(new HashSet<String>());

			NumDiff.add(
					this.getObjectNumonTrack(SortedTracks1.get(i)) - that.getObjectNumonTrack(SortedTracks2.get(i)));
			for (E currentObj : this.getObjectonTrack(SortedTracks1.get(i))) {
				if (!that.getObjectonTrack(SortedTracks2.get(i)).contains(currentObj)) {
					ObjectDiff.get(i).get(0).add(currentObj.getName());
				}
			}
			for (E currentObj : that.getObjectonTrack(SortedTracks2.get(i))) {
				if (!this.getObjectonTrack(SortedTracks1.get(i)).contains(currentObj)) {
					ObjectDiff.get(i).get(1).add(currentObj.getName());
				}
			}
			i++;
		}
		while (i < SortedTracks1.size()) {
			NumDiff.add(this.getObjectNumonTrack(SortedTracks1.get(i)));

			ObjectDiff.add(new ArrayList<Set<String>>());
			ObjectDiff.get(i).add(new HashSet<String>());
			ObjectDiff.get(i).add(new HashSet<String>());

			for (E currentObj : this.getObjectonTrack(SortedTracks1.get(i))) {
				ObjectDiff.get(i).get(0).add(currentObj.getName());
			}
			i++;
		}
		while (i < SortedTracks2.size()) {
			NumDiff.add(-that.getObjectNumonTrack(SortedTracks2.get(i)));

			ObjectDiff.add(new ArrayList<Set<String>>());
			ObjectDiff.get(i).add(new HashSet<String>());
			ObjectDiff.get(i).add(new HashSet<String>());

			for (E currentObj : that.getObjectonTrack(SortedTracks2.get(i))) {
				ObjectDiff.get(i).get(1).add(currentObj.getName());
			}
			i++;
		}

		Difference difference = new Difference(trackNumDiff, NumDiff, ObjectDiff);

		return difference;
	}

	/**
	 * 获得当前orbit包含的所有轨道按半径排列成的链表
	 * 
	 * @return 半径有序的Track链表
	 */
	public List<Track> getSortedTracks() {
		List<Track> listTracks = new ArrayList<>();
		for (Track t : OrbitMap.keySet())
			listTracks.add(t);
		Collections.sort(listTracks);
		return listTracks;
	}

	public Integer getObjectNumonTrack(Track t) {
		return OrbitMap.get(t).size();
	}

	/**
	 * 可视化方法
	 */
	@Override
	public void drawpicture() {
		JFrame frame = new JFrame();
		String TITLE = "可视化";
		int WIDTH = 800;
		int HEIGHT = 800;
//		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		JPanel jpanel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			public void paint(Graphics graphics) {
				super.paint(graphics);
				
				for (Track t : OrbitMap.keySet()) {
					int R = (int) t.getRadius();
					graphics.drawOval(400 - (int) (0.5 * R), 400 - (int) (0.5 * R), R, R);
					int objectNum = getObjectNumonTrack(t);
					double angle = objectNum == 0 ? 0 : 360 / objectNum;
					int i = 1;
					for (PhysicalObject p : getObjectonTrack(t)) {
						int x = (int) (400 + 0.5 * R * Math.cos(Math.PI * i * angle / 180));
						int y = (int) (400 + 0.5 * R * Math.sin(Math.PI * i * angle / 180));
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
		frame.setTitle(TITLE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setVisible(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see circularOrbit.CircularOrbit#getObjectonTrack(track.Track)
	 */
	@Override
	public List<E> getObjectonTrack(Track t) {
		return OrbitMap.get(t);
	}

//	public static void main(String[] args) {
//		CircularOrbit<CentralObject, PhysicalObject> circular = new ConcreteCircularOrbit<CentralObject, PhysicalObject>();
//		CentralObject center = new CentralObject("sun");
//		Track t1 = new Track("track1", 100);
//		Track t2 = new Track("track2", 200);
//		Track t3 = new Track("track3", 300);
//		PhysicalObject ob1 = new PhysicalObject("object1");
//		PhysicalObject ob2 = new PhysicalObject("object2");
//		PhysicalObject ob3 = new PhysicalObject("object3");
//		PhysicalObject ob4 = new PhysicalObject("object4");
//		PhysicalObject ob5 = new PhysicalObject("object5");
//		PhysicalObject ob6 = new PhysicalObject("object6");
//		PhysicalObject ob7 = new PhysicalObject("object7");
//		PhysicalObject ob8 = new PhysicalObject("object8");
//		PhysicalObject ob9 = new PhysicalObject("object9");
//
//		circular.setCentralObject(center);
//		circular.addTrack(t1);
//		circular.addTrack(t2);
//		circular.addTrack(t3);
//		circular.addObjectToTrack(t1, ob1);
//		circular.addObjectToTrack(t1, ob2);
//		circular.addObjectToTrack(t1, ob3);
//		circular.addObjectToTrack(t2, ob4);
//		circular.addObjectToTrack(t3, ob5);
//		circular.addObjectToTrack(t3, ob6);
//		circular.addObjectToTrack(t3, ob7);
//		circular.addObjectToTrack(t3, ob8);
//		circular.addObjectToTrack(t3, ob9);
//		circular.drawpicture();
//	}

	/**
	 * 判断当前Orbit是否包含某个元素e
	 * 
	 * @param e
	 * @return 成功返回true 否则false
	 */
	@Override
	public boolean contains(E e) {
		for (Track t : OrbitMap.keySet()) {
			if (OrbitMap.get(t).contains(e)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 返回某个元素e所在的Object对象
	 * 
	 * @param e
	 * @return
	 */
	@Override
	public Track getObjectTrack(E e) {
		for (Track t : OrbitMap.keySet()) {
			if (OrbitMap.get(t).contains(e)) {
				return t;
			}
		}
		return null;
	}

	/**
	 * 返回与中心连接的物体
	 * 
	 * @return 物体构成的集合
	 */
	public Set<E> getCentralConnectedObject() {
		return centralRelationMap.keySet();

	}

	/**
	 * 返回与某个轨道物体连接的所有物体
	 * 
	 * @return 物体构成的集合
	 */
	public Set<E> getTrackConnectedObject(E object) {
		return trackRelationMap.get(object).keySet();
	}
	public void checkRep() {
		assert centralObject!=null;
		assert centralRelationMap!=null;
		assert trackRelationMap!=null;
		assert OrbitMap!=null;
		
	}

}
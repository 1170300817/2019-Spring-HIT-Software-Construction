package applications.TrackGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import applications.AtomStructure.AtomGame;
import applications.TrackGame.Strategy.RandomStrategy;
import applications.TrackGame.Strategy.RecordStrategy;
import applications.TrackGame.Strategy.Strategy;
import circularOrbitHelper.CircularOrbitAPIs;
import exception.illegalParameterException;
import exception.illegalTextGrammarException;
import exception.objectNoFoundException;
import exception.sameLabelException;
import logRecord.logKeeper;
import logRecord.loggerFactory;
import track.Track;

public class TrackGame {
	// Abstraction function:
	// 所以AF是一个保存着比赛各样数据和操作的抽象数据型到真实的比赛管理系统的映射

	// Representation invariant:
	// 轨道不能重名，不能有轨道具有相同半径
	// 不能有重号的运动员

	// Safety from rep exposure:
	// 设置关键数据athleteList，trackOrbitList，trackBuilder为private final
	// 在有必要的时候使用防御性拷贝
//	private Integer gameType;
	private Integer trackNum;
	private List<Athlete> athleteList = new LinkedList<>();
	private final List<TrackCircularOrbit> trackOrbitList = new LinkedList<>();
	private final TrackCircularOrbitBuilder trackBuilder = new TrackCircularOrbitBuilder();
	private static Logger LOGGER = loggerFactory.createLogger(AtomGame.class);
	private logKeeper LOGKEEPER;

	/**
	 * 菜单
	 */
	public void GameMenu() {
		System.out.println("1.\t读取文件");
		System.out.println("2.\t随机分配赛道");
		System.out.println("3.\t按成绩分配赛道");
		System.out.println("4.\t增加新赛道");
		System.out.println("5.\t增加新运动员");
		System.out.println("6.\t删除运动员");
		System.out.println("7.\t删除赛道");
		System.out.println("8.\t计算信息熵");
		System.out.println("9.\t交换两名选手的赛道和组别");
		System.out.println("10.\t可视化");
		System.out.println("11.\t显示分组信息");
		System.out.println("12.\t日志查询");
//		System.out.println("12.\t4人接力赛");
		System.out.println("end.\t 结束");
	}

	/**
	 * 游戏主体
	 * 
	 * @throws IOException
	 * @throws objectNoFoundException
	 */
	public void gameMain() throws IOException, objectNoFoundException {
		String inputString;
		String[] arguments;
		boolean exitflag = false;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			GameMenu();
			String input = reader.readLine();
			if (input != null) {
				input = input.trim();
			}else {
				continue;
			}
			switch (input) {
			case "1":// 读取文件
				try {
					System.out.println("输入需要读取的文件名：例如TrackGame.txt\n");
					inputString = reader.readLine().trim();
					athleteList = trackBuilder.createFromFile("src/txt/" + inputString);
//					athleteList=trackBuilder.createFromFile("src/txt/" + "TrackGame.txt");

				} catch (illegalTextGrammarException | sameLabelException | illegalParameterException e) {
					System.out.println(e.getMessage());
					System.out.println("请重新读取文件");
					break;
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				gameType = trackBuilder.getGameType();
				trackNum = trackBuilder.getTrackNum();
				System.out.println("文件读取成功");
				break;
			case "2":// 随机分配赛道
				Strategy strategy1 = new RandomStrategy();
				this.arrangeOrbit(strategy1);
				checkRep();
				arrangeOutout();
				System.out.println("分组完成\n");
				break;
			case "3":// 按成绩分配赛道
				Strategy strategy2 = new RecordStrategy();
				this.arrangeOrbit(strategy2);
				checkRep();
				arrangeOutout();
				System.out.println("分组完成\n");
				break;
			case "4":// 增加轨道数
				trackNum++;
				System.out.println("轨道数：" + trackNum + "\n");
				System.out.println("请重新分配方案\n");
				break;
			case "5":// 增加运动员
				System.out.println("输入需要增加的运动员的名字，id，国籍，年龄，最好成绩\n");
				inputString = reader.readLine().trim();
				arguments = inputString.split("\\s");
				String nameString = arguments[0];
				int idNum = Integer.valueOf(arguments[1]);
				String nationality = arguments[2];
				int age = Integer.valueOf(arguments[3]);
				double bestRecord = Double.valueOf(arguments[4]);
				Athlete a;
				try {
					a = Athlete.getInstance(nameString, idNum, nationality, age, bestRecord);
					athleteList.add(a);
				} catch (sameLabelException | illegalParameterException e) {
					System.out.println(e.getMessage());
				}
				System.out.println("增加成功\n");
				break;
			case "6":// 删除运动员
				System.out.println("输入需要删除的运动员的id\n");
				inputString = reader.readLine().trim();
				arguments = inputString.split("\\s");
				int id = Integer.valueOf(arguments[0]);
				Iterator<Athlete> iterator = athleteList.iterator();
				boolean flag1 = false;
				while (iterator.hasNext()) {
					if (iterator.next().getIdNum() == id) {
						iterator.remove();
						System.out.println("删除成功\n");
						flag1 = true;
					}
				}
				if (!flag1) {
					System.out.println("删除失败\n");
				}

				break;
			case "7":// 删除赛道
				trackNum--;
				System.out.println("轨道数-1");
				System.out.println("当前轨道数：" + trackNum);
				System.out.println("请重新分配方案");
				break;
			case "8":// 计算信息熵
				System.out.println("输入需要计算信息熵的轨道系统序号");
				System.out.println("输入范围为0——" + (trackOrbitList.size() - 1));
				inputString = reader.readLine().trim();
				arguments = inputString.split("\\s");
				int currentOrbit = Integer.valueOf(arguments[0]);
				System.out.println("信息熵为：" + trackOrbitList.get(currentOrbit).getObjectDistributionEntropy() + "\n");
				break;
			case "9":// 交换两名选手的赛道和组别
				System.out.println("输入两名需要交换的运动员的id\n");
				inputString = reader.readLine().trim();
				arguments = inputString.split("\\s");
				int id1 = Integer.valueOf(arguments[0]);
				int id2 = Integer.valueOf(arguments[1]);
				Athlete a1 = null;
				Athlete a2 = null;

				Iterator<Athlete> iterator1 = athleteList.iterator();

				while (iterator1.hasNext()) {
					Athlete tmp = iterator1.next();
					if (tmp.getIdNum() == id1) {
						a1 = tmp;
					}
					if (tmp.getIdNum() == id2) {
						a2 = tmp;
					}
				}
				Track t1 = null;
				Track t2 = null;
				TrackCircularOrbit o1 = null;
				TrackCircularOrbit o2 = null;
				if (a1 == null || a2 == null) {
					System.out.println("选手不存在");
					break;
				}
				for (TrackCircularOrbit Orb : trackOrbitList) {
					if (Orb.contains(a1)) {
						t1 = Orb.getObjectTrack(a1);
						o1 = Orb;
					}
					if (Orb.contains(a2)) {
						t2 = Orb.getObjectTrack(a2);
						o2 = Orb;
					}
				}
				o1.removeObjectOnTrack(t1, a1);
				o2.removeObjectOnTrack(t2, a2);
				o1.addObjectToTrack(t1, a2);
				o2.addObjectToTrack(t2, a1);
				System.out.println("交换成功");

				break;
			case "10":// 可视化
				System.out.println("输入需要显示的轨道系统序号");
				System.out.println("输入范围为0——" + (trackOrbitList.size() - 1));
				inputString = reader.readLine().trim();
				arguments = inputString.split("\\s");
				int currentOrbit1 = Integer.valueOf(arguments[0]);
				CircularOrbitAPIs.visualize(trackOrbitList.get(currentOrbit1));
				break;
			case "11":// 显示分组信息
				arrangeOutout();
				break;
			case "12":// 查询日志
				LOGKEEPER = new logKeeper();
				System.out.println("选择筛选条件：\n");
				System.out.println("1.\t时间段");
				System.out.println("2.\t按类型");
				System.out.println("3.\t按类");
				System.out.println("4.\t按方法");
				input = reader.readLine().trim();
				switch (input) {
				case "1":// 时间段
					System.out.println("请输入开始时间，格式参考：2019-05-19 02:05:14");
					inputString = reader.readLine().trim();
					final Instant time1 = Instant.parse(inputString.replace(' ', 'T') + "Z");
					System.out.println("请输入结束时间，格式参考：2019-05-19 02:05:14");
					inputString = reader.readLine().trim();
					final Instant time2 = Instant.parse(inputString.replace(' ', 'T') + "Z");
					System.out.println(
							LOGKEEPER.getFilterLogs(p -> p.getTime().isAfter(time1) && p.getTime().isBefore(time2)));
					System.out.println("查询完毕");
					break;
				case "2":// 按类型
					System.out.println("请输入类型：ERROR或DEBUG");
					inputString = reader.readLine().trim();
					final String input1 = new String(inputString);
					System.out.println(LOGKEEPER.getFilterLogs(p -> p.getLogType().equals(input1)));
					System.out.println("查询完毕");
					break;
				case "3":// 按类
					System.out.println("请输入类名");
					inputString = reader.readLine().trim();
					final String input2 = new String(inputString);
					System.out.println(LOGKEEPER.getFilterLogs(p -> p.getClassName().equals(input2)));
					System.out.println("查询完毕");
					break;
				case "4":// 按方法
					System.out.println("请输入方法名");
					inputString = reader.readLine().trim();
					final String input3 = new String(inputString);
					System.out.println(LOGKEEPER.getFilterLogs(p -> p.getMethodName().equals(input3)));
					System.out.println("查询完毕");
					break;
				default:
					LOGGER.debug("输入错误");
					break;
				}
				break;
//			case "12":// 4人接力
//				Strategy strategy3 = new Relay4Strategy();
//				this.arrangeOrbit(strategy3);
//				arrangeOutout();
//				System.out.println("分组完成\n");
//				break;
			case "end":// 结束游戏
				exitflag = true;
				break;
			default:
				System.out.println("输入错误");
				break;
			}
			if (exitflag)
				break;
		}

	}

	public void arrangeOrbit(Strategy strategy) {

		List<Track> trackList = new ArrayList<Track>();
		int[] DefaultRadius = new int[8];
		for (int i = 0; i < 8; i++) {
			DefaultRadius[i] = 50 + 100 * i;
		}
		for (int i = 0; i < trackNum; i++) {
			trackList.add(new Track("tarck" + i, DefaultRadius[i]));
		}
		List<Map<Track, List<Athlete>>> arrangementMap = strategy.Arrange(new ArrayList<>(athleteList), trackList);

		int OrbitNum = arrangementMap.size();

		trackOrbitList.clear();
		for (int i = 0; i < OrbitNum; i++) {
			Map<Track, List<Athlete>> currentMap = arrangementMap.get(i);
			trackBuilder.createCircularOrbit();
			trackBuilder.bulidTracks(trackList);
			trackBuilder.bulidPhysicalObjects(currentMap);
			TrackCircularOrbit newOrbit = (TrackCircularOrbit) trackBuilder.getConcreteCircularOrbit();
			trackOrbitList.add(newOrbit);
		}
	}

	public void arrangeOutout() {
		for (int i = 0; i < trackOrbitList.size(); i++) {
			TrackCircularOrbit CurrentOrbit = trackOrbitList.get(i);
			System.out.println(CurrentOrbit.toString());
		}
	}

	/**
	 * checkRep
	 */
	public void checkRep() {
		for (TrackCircularOrbit Orbit : trackOrbitList) {
			Orbit.checkRep();
		}
	}

	public static void main(String[] args) throws IOException, objectNoFoundException {
		new TrackGame().gameMain();
	}
}

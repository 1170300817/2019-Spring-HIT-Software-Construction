package applications.SocialNetworkCircle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import applications.AtomStructure.AtomGame;
import circularOrbitHelper.CircularOrbitAPIs;
import exception.illegalParameterException;
import exception.illegalTextGrammarException;
import exception.objectNoFoundException;
import exception.sameLabelException;
import exception.unDefinedPersonException;
import logRecord.logKeeper;
import logRecord.loggerFactory;
import track.Track;

public class SocialNetworkGame {
	// Abstraction function:
	// 所以AF是一个保存着人际关系数据和操作的抽象数据型到真实的人际关系层级管理器的映射

	// Representation invariant:
	// socialCircularOrbit!=null
	// centralUser!=null

	// Safety from rep exposure:
	// 设置关键数据personList，centralUser，socialCircularOrbit为private
	// 在有必要的时候使用防御性拷贝
	private Person centralUser;
	private List<Person> personList = new ArrayList<Person>();
	private SocialNetCircularOrbit socialCircularOrbit = null;
	private SocialNetCircularOrbitBuilder socialCircularOrbitBuilder = new SocialNetCircularOrbitBuilder();
	private static Logger LOGGER = loggerFactory.createLogger(AtomGame.class);
	private logKeeper LOGKEEPER;

	public int[] DefaultRadius = new int[10];

	public void GameMenu() {
		System.out.println("1.\t读取文件构造系统");
		System.out.println("2.\t可视化");
		System.out.println("3.\t查询好友位置");
		System.out.println("4.\t打印轨道结构");
		System.out.println("5.\t增加新的朋友");
		System.out.println("6.\t增加新的关系并重新整理");
		System.out.println("7.\t删除一个关系并重新整理");
		System.out.println("8.\t计算熵值");
		System.out.println("9.\t计算信息扩散度");
		System.out.println("10.\t计算两个用户之间的逻辑距离");
		System.out.println("11.\t日志查询");
		System.out.println("end.\t 结束");
	}

	public void gameMain() throws IOException {
		String inputString;
		String[] arguments;
		String name1;
		String name2;
		Person person1 = null;
		Person person2 = null;
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
					System.out.println("输入需要读取的文件名：例如SocialNetworkCircle.txt\n");
					inputString = reader.readLine().trim();
					personList = socialCircularOrbitBuilder.createFromFile("src/txt/" + inputString);
//					personList=socialCircularOrbitBuilder.createFromFile("src/txt/" + "SocialNetworkCircle.txt");

				} catch (illegalTextGrammarException | unDefinedPersonException | sameLabelException
						| illegalParameterException e) {
					LOGGER.error(e.getMessage());
					System.out.println("请重新读取文件");
					break;
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
				socialCircularOrbit = (SocialNetCircularOrbit) socialCircularOrbitBuilder.getConcreteCircularOrbit();
				centralUser = socialCircularOrbit.getCentralObject();
				LOGGER.debug("读取成功");
				break;
			case "2":// 可视化
				CircularOrbitAPIs.visualize(socialCircularOrbit);
				LOGGER.debug("可视化完成");
				break;
			case "3":// 查询好友位置
				System.out.println("输入需要查询的好友名字");
				inputString = reader.readLine().trim();
				arguments = inputString.split("\\s");
				String nameString = arguments[0];
				try {
					Track track = socialCircularOrbit.getObjectTrack(nameString);
					System.out.println("目标位于" + track.getName());
					LOGGER.debug("查询完成");
					break;
				} catch (objectNoFoundException e) {
					System.out.println("查询失败");
					LOGGER.error(e.getMessage());
					break;
				}
			case "4":// 打印轨道结构
				System.out.println(socialCircularOrbit.toString());
				LOGGER.debug("打印轨道结构");
				break;
			case "5":// 增加新的朋友
				System.out.println("增加新的朋友");
				System.out.println("输入名字 岁数 性别");
				inputString = reader.readLine().trim();
				arguments = inputString.split("\\s");
				try {
					person1 = Person.getInstance(arguments[0], Integer.valueOf(arguments[1]), arguments[2]);
				} catch (sameLabelException | NumberFormatException | illegalParameterException e) {
					System.out.println("增加失败");
					LOGGER.error(e.getMessage());
					break;
				}
				LOGGER.debug("增加成功\"");
				personList.add(person1);
				break;
			case "6":// 增加新的关系并重新整理
				System.out.println("增加新的关系");
				System.out.println("输入名字1 名字2 权重");
				inputString = reader.readLine().trim();
				arguments = inputString.split("\\s");
				name1 = arguments[0];
				name2 = arguments[1];
				Double weight = Double.valueOf(arguments[2]);
				Iterator<Person> iterator1 = personList.iterator();
				person1 = null;
				person2 = null;
				while (iterator1.hasNext()) {
					Person person = iterator1.next();
					if (person.getName().equals(name1)) {
						person1 = person;
					}
					if (person.getName().equals(name2)) {
						person2 = person;
					}
				}
				if (person1 == null || person2 == null) {
					LOGGER.error("不存在这个人");
					break;
				}
				if (person1.getName().equals(centralUser.getName())) {
					socialCircularOrbit.addcentralRelation(person2, weight);
					LOGGER.debug("新增关系成功");
				} else if (person2.getName().equals(centralUser.getName())) {
					socialCircularOrbit.addcentralRelation(person1, weight);
					LOGGER.debug("新增关系成功");
				} else {
					socialCircularOrbit.addtrackRelation(person1, person2, weight);
					socialCircularOrbit.addtrackRelation(person2, person1, weight);
					LOGGER.debug("新增关系成功");
				}
				socialCircularOrbit.reArrange();
				LOGGER.debug("重整关系图成功");
				break;
			case "7":// 删除一个关系并重新整理
				System.out.println("删除一个关系");
				System.out.println("输入名字1 名字2");
				inputString = reader.readLine().trim();
				arguments = inputString.split("\\s");
				name1 = arguments[0];
				name2 = arguments[1];
				Iterator<Person> iterator2 = personList.iterator();
				person1 = null;
				person2 = null;
				while (iterator2.hasNext()) {
					Person personxy = iterator2.next();
					if (personxy.getName().equals(name1)) {
						person1 = personxy;
					}
					if (personxy.getName().equals(name2)) {
						person2 = personxy;
					}
				}
				if (person1 == null || person2 == null) {
					LOGGER.error("不存在这个人");
					break;
				}
				if (person1.getName().equals(centralUser.getName())) {
					socialCircularOrbit.addcentralRelation(person2, 0);
					LOGGER.debug("删除关系成功");
				} else if (person2.getName().equals(centralUser.getName())) {
					socialCircularOrbit.addcentralRelation(person1, 0);
					LOGGER.debug("删除关系成功");
				} else {
					socialCircularOrbit.addtrackRelation(person1, person2, 0);
					socialCircularOrbit.addtrackRelation(person2, person1, 0);
					LOGGER.debug("删除关系成功");
				}
				LOGGER.debug("重整关系图成功");
				socialCircularOrbit.reArrange();
				break;
			case "8":// 计算熵值
				System.out.println("信息熵为：" + socialCircularOrbit.getObjectDistributionEntropy() + "\n");
				LOGGER.debug("计算信息熵完成");
				break;
			case "9":// 计算信息扩散度
				System.out.println("计算信息扩散度:输入名字");
				inputString = reader.readLine().trim();
				arguments = inputString.split("\\s");
				name1 = arguments[0];
				Iterator<Person> iterator9 = personList.iterator();
				person1 = null;
				while (iterator9.hasNext()) {
					Person personTmp = iterator9.next();
					if (personTmp.getName().equals(name1)) {
						person1 = personTmp;
					}
				}
				if (person1 == null) {
					System.out.println("不存在这个人");
					break;
				}
				System.out.println("信息扩散度为：" + socialCircularOrbit.getIntimacy(person1) + "\n");
				LOGGER.debug("计算信息扩散度完成");
				break;
			case "10":// 计算逻辑距离
				System.out.println("计算逻辑距离");
				System.out.println("输入名字1 名字2");
				inputString = reader.readLine().trim();
				arguments = inputString.split("\\s");
				name1 = arguments[0];
				name2 = arguments[1];
				Iterator<Person> iterator10 = personList.iterator();
				person1 = null;
				person2 = null;
				while (iterator10.hasNext()) {
					Person personxy = iterator10.next();
					if (personxy.getName().equals(name1)) {
						person1 = personxy;
					}
					if (personxy.getName().equals(name2)) {
						person2 = personxy;
					}
				}
				if (person1 == null || person2 == null) {
					System.out.println("不存在这个人");
					break;
				}
				System.out.println("距离：" + socialCircularOrbit.getLogicalDistance(person1, person2));
				LOGGER.debug("计算逻辑距离完成");
				break;
			case "11":// 查询日志
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
					final Instant time1 = Instant.parse(inputString.replace(' ','T')+"Z");
					System.out.println("请输入结束时间，格式参考：2019-05-19 02:05:14");
					inputString = reader.readLine().trim();
					final Instant time2 = Instant.parse(inputString.replace(' ','T')+"Z");
					System.out.println(LOGKEEPER.getFilterLogs(p -> p.getTime().isAfter(time1)&&p.getTime().isBefore(time2)));
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
			case "end":// 结束游戏
				exitflag = true;
				LOGGER.debug("游戏结束");
				break;
			default:
				System.out.println("输入错误");
				LOGGER.debug("输入错误");
				break;
			}
			if (exitflag)
				break;
		}
	}

	public static void main(String[] args) throws IOException {
		new SocialNetworkGame().gameMain();
	}
}

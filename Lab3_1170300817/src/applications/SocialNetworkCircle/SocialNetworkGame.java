package applications.SocialNetworkCircle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import circularOrbitHelper.CircularOrbitAPIs;
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
	private final List<Person> personList = new ArrayList<Person>();
	private final List<relationKeeper> keeperList = new ArrayList<relationKeeper>();
	private SocialNetCircularOrbit socialCircularOrbit = null;
	private  SocialNetCircularOrbitBuilder socialCircularOrbitBuilder = new SocialNetCircularOrbitBuilder();
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
		System.out.println("end.\t 结束");
	}

	public void gameMain() throws IOException {
		String inputString;
		String[] arguments;
		String name1;
		String name2;
		Person person1=null;
		Person person2=null;
		boolean exitflag = false;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			GameMenu();
			String input = reader.readLine().trim();
			switch (input) {
			case "1":// 读取文件
				BufferedReader in = new BufferedReader(new FileReader(new File("src/txt/SocialNetworkCircle_Larger.txt")));
				String fileline;
				String centralUserPatternString = "CentralUser\\s*::=\\s*<(\\w+),\\s*(\\d+),\\s*([MF])>";
				String friendPatternString = "Friend\\s*::=\\s*<(\\w+),\\s*(\\d+),\\s*([MF])>";
				String socialTiePatternString = "SocialTie\\s*::=\\s*<(\\w+),\\s*(\\w+),\\s*([01]\\.{1}\\d+)>";

				while ((fileline = in.readLine()) != null) {
					Matcher centralUserMatcher = Pattern.compile(centralUserPatternString).matcher(fileline);
					Matcher friendMatcher = Pattern.compile(friendPatternString).matcher(fileline);
					Matcher socialTieMatcher = Pattern.compile(socialTiePatternString).matcher(fileline);
					if (centralUserMatcher.find()) {
						String nameString = centralUserMatcher.group(1);
						int age = Integer.valueOf(centralUserMatcher.group(2));
						String gender = centralUserMatcher.group(3);
						centralUser = Person.getInstance(nameString, age, gender);
					} else if (friendMatcher.find()) {
						String nameString = friendMatcher.group(1);
						int age = Integer.valueOf(friendMatcher.group(2));
						String gender = friendMatcher.group(3);
						Person person = Person.getInstance(nameString, age, gender);
						personList.add(person);
						socialCircularOrbitBuilder.createCircularOrbit();
					} else if (socialTieMatcher.find()) {
						Double weight = Double.valueOf(socialTieMatcher.group(3));
						relationKeeper currKeeper = new relationKeeper(socialTieMatcher.group(1),
								socialTieMatcher.group(2), weight);
						keeperList.add(currKeeper);
					}
				}
				socialCircularOrbitBuilder.createCircularOrbit();
				assert (centralUser!=null):"中心用户丢失，构建失败";
				assert (personList.size()!=0):"用户丢失，构建失败";
				assert (keeperList.size()!=0):"关系丢失，构建失败";
				socialCircularOrbitBuilder.bulidRelations(centralUser, personList, keeperList);
				socialCircularOrbit = (SocialNetCircularOrbit) socialCircularOrbitBuilder.getConcreteCircularOrbit();
				System.out.println("读取成功");
				break;
			case "2":// 可视化
				CircularOrbitAPIs.visualize(socialCircularOrbit);
				break;
			case "3":// 查询好友位置
				System.out.println("输入需要查询的好友名字");
				inputString = reader.readLine().trim();
				arguments = inputString.split("\\s");
				String nameString = arguments[0];
				Iterator<Person> iterator = personList.iterator();
				Person p1 = null;
				while (iterator.hasNext()) {
					Person person = iterator.next();
					if (person.getName().equals(nameString)) {
						p1 = person;
					}
				}
				if (p1 == null) {
					System.out.println("不存在这个人");
					break;
				}
				Track track = socialCircularOrbit.getObjectTrack(p1);
				System.out.println("目标位于" + track.getName());
				break;
			case "4":// 打印轨道结构
				System.out.println(socialCircularOrbit.toString());
				break;
			case "5":// 增加新的朋友
				System.out.println("增加新的朋友");
				System.out.println("输入名字 岁数 性别");
				inputString = reader.readLine().trim();
				arguments = inputString.split("\\s");
				 person1 = Person.getInstance(arguments[0], Integer.valueOf(arguments[1]), arguments[2]);
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
					System.out.println("不存在这个人");
					break;
				}
				if (person1.getName().equals(centralUser.getName())) {
					socialCircularOrbit.addcentralRelation(person2, weight);
				} else if (person2.getName().equals(centralUser.getName())) {
					socialCircularOrbit.addcentralRelation(person1, weight);
				} else {
					socialCircularOrbit.addtrackRelation(person1, person2, weight);
					socialCircularOrbit.addtrackRelation(person2, person1, weight);
				}
				socialCircularOrbit.reArrange();
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
					System.out.println("不存在这个人");
					break;
				}
				if (person1.getName().equals(centralUser.getName())) {
					socialCircularOrbit.addcentralRelation(person2, 0);
				} else if (person2.getName().equals(centralUser.getName())) {
					socialCircularOrbit.addcentralRelation(person1, 0);
				} else {
					socialCircularOrbit.addtrackRelation(person1, person2, 0);
					socialCircularOrbit.addtrackRelation(person2, person1, 0);
				}
				
				socialCircularOrbit.reArrange();
				break;
			case "8"://计算熵值
				System.out.println("信息熵为：" + socialCircularOrbit.getObjectDistributionEntropy() + "\n");
				break;
			case "9"://计算信息扩散度
				System.out.println("计算信息扩散度:输入名字");
				inputString = reader.readLine().trim();
				arguments = inputString.split("\\s");
				 name1 = arguments[0];
				Iterator<Person> iterator9 = personList.iterator();
				 person1=null;
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
				break;
			case "10"://计算逻辑距离
				System.out.println("计算逻辑距离");
				System.out.println("输入名字1 名字2");
				inputString = reader.readLine().trim();
				arguments = inputString.split("\\s");
				name1 = arguments[0];
				name2= arguments[1];
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
				System.out.println("距离："+socialCircularOrbit.getLogicalDistance(person1, person2));
				break;
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

	public static void main(String[] args) throws IOException {
		new SocialNetworkGame().gameMain();
	}
}

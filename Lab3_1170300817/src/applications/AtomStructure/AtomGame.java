package applications.AtomStructure;

import static org.junit.jupiter.api.Assertions.assertAll;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import circularOrbitHelper.CircularOrbitAPIs;
import track.Track;

public class AtomGame {
	// Abstraction function:
	// AF是一个保存着原子轨道信息和电子核子信息的抽象数据型到真实的原子的映射

	// Representation invariant:
	// Nucleus!=null
	// trackNum!=null
	// atomCircularOrbit!=null

	// Safety from rep exposure:
	// 设置关键数据trackNum，Nucleus，atomCircularOrbit为private
	// 在有必要的时候使用防御性拷贝
	private Integer trackNum;
	private Particle Nucleus;
	private final TransitCareTaker transitCareTaker = new TransitCareTaker();
	private AtomCircularOrbit atomCircularOrbit=null;
	private final AtomCircularOrbitBuilder atomOrbitBuilder = new AtomCircularOrbitBuilder();
	public int[] DefaultRadius = new int[10];

	/**
	 * 菜单
	 */
	public void GameMenu() {
		System.out.println("1.\t读取文件构造系统");
		System.out.println("2.\t跃迁");
		System.out.println("3.\t回退");
		System.out.println("4.\t可视化");
		System.out.println("5.\t打印轨道结构");
		System.out.println("6.\t增加新轨道");
		System.out.println("7.\t增加新电子");
		System.out.println("8.\t删除电子");
		System.out.println("9.\t删除整条轨道");
		System.out.println("10.\t计算熵值");
		System.out.println("end.\t 结束");
	}

	/**
	 * 主体
	 * @throws IOException
	 */
	public void gameMain() throws IOException {
		String inputString;
		String[] arguments;
		boolean exitflag = false;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			GameMenu();
			String input = reader.readLine().trim();
			switch (input) {
			case "1":// 读取文件
				BufferedReader in = new BufferedReader(new FileReader(new File("src/txt/AtomicStructure.txt")));
				String fileline;
				String elementpattern = "ElementName\\s*::=\\s*([a-zA-Z]+)";
				String trackpattern = "NumberOfTracks\\s*::=\\s*(\\d+)";
				String electronpattern = "NumberOfElectron\\s*::=\\s*([\\d+\\/\\d+,;]+)";
				while ((fileline = in.readLine()) != null) {
					Matcher elementMatcher = Pattern.compile(elementpattern).matcher(fileline);
					Matcher trackMatcher = Pattern.compile(trackpattern).matcher(fileline);
					Matcher electronMatcher = Pattern.compile(electronpattern).matcher(fileline);
					if (elementMatcher.find()) {
						Nucleus = Particle.getNucleus(elementMatcher.group(1));
					} else if (trackMatcher.find()) {
						trackNum = Integer.valueOf(trackMatcher.group(1));
					} else if (electronMatcher.find()) {
						for (int i = 0; i < 10; i++) {
							DefaultRadius[i] = 50 + 100 * i;
						}
						List<Track> trackList = new ArrayList<Track>();
						Map<Track, List<Particle>> elementMap = new HashMap<Track, List<Particle>>();
						String[] NUm = electronMatcher.group(1).split(";");
						for (int i = 0; i < NUm.length; i++) {
							Track track = new Track("track" + i, DefaultRadius[i]);
							trackList.add(track);
							String[] value = NUm[i].split("/");
							int objNum = Integer.valueOf(value[1]);
							List<Particle> currentList = new LinkedList<Particle>();
							for (int j = 0; j < objNum; j++) {
								Particle p = Particle.getElectron();
								currentList.add(p);
							}
							elementMap.put(track, currentList);
							assert (currentList.size()!=0):"电子丢失，构建失败";
						}
						atomOrbitBuilder.createCircularOrbit();
						atomOrbitBuilder.bulidTracks(trackList);
						atomOrbitBuilder.bulidPhysicalObjects(Nucleus, elementMap);
						atomCircularOrbit = (AtomCircularOrbit) atomOrbitBuilder.getConcreteCircularOrbit();
					}
				}
				System.out.println(trackNum);
				assert (trackNum!=null):"轨道数丢失，构建失败";
				atomCircularOrbit.checkRep();
				System.out.println(atomCircularOrbit.toString());
				break;
			case "2":// 跃迁
				System.out.println("输入跃迁的起始轨道和终止轨道");
				System.out.println("输入范围为0——" + (trackNum - 1));
				inputString = reader.readLine().trim();
				arguments = inputString.split("\\s");
				int TrackNum1 = Integer.valueOf(arguments[0]);
				int TrackNum2 = Integer.valueOf(arguments[1]);
				Track t1 = atomCircularOrbit.getSortedTracks().get(TrackNum1);
				Track t2 = atomCircularOrbit.getSortedTracks().get(TrackNum2);
				if (atomCircularOrbit.transit(t1, t2)) {
					transitCareTaker.addMemento(new Memento(t1, t2));
					System.out.println("跃迁成功");
					System.out.println(atomCircularOrbit.toString());
				} else {
					System.out.println("跃迁失败");
				}
				break;
			case "3":// 回退
				Memento m = transitCareTaker.getMemento();
				Track fromTrack = m.getToTrack();
				Track toTrack = m.getFromTrack();
				if (atomCircularOrbit.transit(fromTrack, toTrack)) {
					System.out.println("回退成功");
					System.out.println(atomCircularOrbit.toString());
				} else {
					System.out.println("回退失败");
				}
				break;
			case "4":// 可视化
				CircularOrbitAPIs.visualize(atomCircularOrbit);
				break;
			case "5":// 文字输出
				System.out.println(atomCircularOrbit.toString());
				break;
			case "6":// 增加新轨道
				System.out.println("增加新轨道\n");
				trackNum++;
				atomCircularOrbit.addTrack(new Track("track" + (trackNum - 1), DefaultRadius[trackNum - 1]));
				System.out.println("轨道数：" + trackNum + "\n");
				System.out.println(atomCircularOrbit.toString());
				break;
			case "7":// 增加新电子
				System.out.println("输入需要增加电子的轨道\n");
				System.out.println("输入范围为0——" + (trackNum - 1));
				inputString = reader.readLine().trim();
				arguments = inputString.split("\\s");
				int trackIndex1 = Integer.valueOf(arguments[0]);
				Track t=atomCircularOrbit.getSortedTracks().get(trackIndex1);
				atomCircularOrbit.addObjectToTrack(t, Particle.getElectron());
				System.out.println(atomCircularOrbit.toString());
				System.out.println("增加成功");
				break;
			case "8":// 删除电子
				System.out.println("输入需要删除电子的轨道\n");
				System.out.println("输入范围为0——" + (trackNum - 1));
				inputString = reader.readLine().trim();
				arguments = inputString.split("\\s");
				Track track2=atomCircularOrbit.getSortedTracks().get(Integer.valueOf(arguments[0]));
				if (atomCircularOrbit.removeElectron(track2)) {
					System.out.println("删除成功");
				}
				else {
					System.out.println("删除失败");
				}
				System.out.println(atomCircularOrbit.toString());
				break;
			case "9":// 删除整条轨道
				System.out.println("输入需要删除的轨道\n");
				System.out.println("输入范围为0——" + (trackNum - 1));
				inputString = reader.readLine().trim();
				arguments = inputString.split("\\s");
				Track track3=atomCircularOrbit.getSortedTracks().get( Integer.valueOf(arguments[0]));
				if (atomCircularOrbit.removeTrack(track3)) {
					System.out.println("删除成功");
				}
				else {
					System.out.println("删除失败");
				}
				System.out.println(atomCircularOrbit.toString());
				trackNum--;
				break;
			case "10":// 计算熵值
				System.out.println("信息熵为：" + atomCircularOrbit.getObjectDistributionEntropy() + "\n");
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
		new AtomGame().gameMain();
	}
}

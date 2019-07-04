package applications.trackgamecircular;

import applications.atomstructure.AtomGame;
import applications.trackgamecircular.strategy.RandomStrategy;
import applications.trackgamecircular.strategy.RecordStrategy;
import applications.trackgamecircular.strategy.Strategy;
import circularorbithelper.CircularOrbitApis;
import exception.IllegalParameterException;
import exception.IllegalTextGrammarException;
import exception.ObjectNoFoundException;
import exception.SameLabelException;
import iostrategy.BufferedInputStrategy;
import iostrategy.BufferedOutputStrategy;
import iostrategy.InputStrategy;
import iostrategy.OutputStrategy;
import iostrategy.ReaderInputStrategy;
import iostrategy.StreamInputStrategy;
import iostrategy.StreamOutputStrategy;
import iostrategy.WriterOutputStrategy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import logrecord.LogKeeper;
import logrecord.LoggerFactory;
import org.apache.log4j.Logger;
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
  // private Integer gameType;
  private Integer trackNum;
  private Integer gameType;
  private Set<Athlete> athleteSet = new HashSet<>();
  private final List<TrackCircularOrbit> trackOrbitList = new LinkedList<>();
  private final TrackCircularOrbitBuilder trackBuilder = new TrackCircularOrbitBuilder();
  private static Logger LOGGER = LoggerFactory.createLogger(AtomGame.class);
  private LogKeeper logKeeper;

  /**
   * 菜单.
   */
  public void gameMenu() {
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
    System.out.println("13.\t文件输出");
    // System.out.println("12.\t4人接力赛");
    System.out.println("end.\t 结束");
  }

  /**
   * 游戏主体.
   * 
   * @throws IOException 文件读写错误
   * @throws ObjectNoFoundException 寻找失败错误
   */
  public void gameMain() throws IOException, ObjectNoFoundException {
    String inputString;
    String[] arguments;
    boolean exitflag = false;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    while (true) {
      gameMenu();
      String input = reader.readLine();
      if (input != null) {
        input = input.trim();
      } else {
        continue;
      }
      switch (input) {
        case "1":// 读取文件
          try {
            System.out.println("输入需要读取的文件名：例如src/txt/TrackGame.txt\n");
            String filePath = reader.readLine();
            System.out.println("选择读入策略：");
            System.out.println("1.\tBuffer");
            System.out.println("2.\tStream");
            System.out.println("3.\tReader");
            input = reader.readLine();
            InputStrategy strategy = null;
            switch (input) {
              case "1":// Buffer
                strategy = new BufferedInputStrategy(filePath);
                break;
              case "2":// Stream
                strategy = new StreamInputStrategy(filePath);
                break;
              case "3":// Reader
                strategy = new ReaderInputStrategy(filePath);
                break;
              default:  
                System.out.println("策略输入错误");
                break;
            }
            trackBuilder.createFromFile(strategy, athleteSet);
          } catch (IllegalTextGrammarException | SameLabelException | IllegalParameterException e) {
            System.out.println(e.getMessage());
            System.out.println("请重新读取文件");
            break;
          } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            System.out.println("请重新读取文件");
            break;
          }
          gameType = trackBuilder.getGameType();
          trackNum = trackBuilder.getTrackNum();
          System.out.println("文件读取成功");
          break;
        case "2":// 随机分配赛道
          Strategy strategy1 = new RandomStrategy();
          this.arrangeOrbit(strategy1);
          checkRep();
//          arrangeOutout();
          System.out.println("分组完成\n");
          break;
        case "3":// 按成绩分配赛道
          Strategy strategy2 = new RecordStrategy();
          this.arrangeOrbit(strategy2);
          checkRep();
//          arrangeOutout();
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
            athleteSet.add(a);
          } catch (SameLabelException | IllegalParameterException e) {
            System.out.println(e.getMessage());
          }
          System.out.println("增加成功\n");
          break;
        case "6":// 删除运动员
          System.out.println("输入需要删除的运动员的id\n");
          inputString = reader.readLine().trim();
          arguments = inputString.split("\\s");
          int id = Integer.valueOf(arguments[0]);
          Iterator<Athlete> iterator = athleteSet.iterator();
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
          System.out.println(
              "信息熵为：" + trackOrbitList.get(currentOrbit).getObjectDistributionEntropy() + "\n");
          break;
        case "9":// 交换两名选手的赛道和组别
          System.out.println("输入两名需要交换的运动员的id\n");
          inputString = reader.readLine().trim();
          arguments = inputString.split("\\s");
          int id1 = Integer.valueOf(arguments[0]);
          int id2 = Integer.valueOf(arguments[1]);
          Athlete a1 = null;
          Athlete a2 = null;

          Iterator<Athlete> iterator1 = athleteSet.iterator();

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
          for (TrackCircularOrbit orb : trackOrbitList) {
            if (orb.contains(a1)) {
              t1 = orb.getObjectTrack(a1);
              o1 = orb;
            }
            if (orb.contains(a2)) {
              t2 = orb.getObjectTrack(a2);
              o2 = orb;
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
          CircularOrbitApis.visualize(trackOrbitList.get(currentOrbit1));
          break;
        case "11":// 显示分组信息
          arrangeOutout();
          break;
        case "12":// 查询日志
          logKeeper = new LogKeeper();
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
              System.out.println(logKeeper
                  .getFilterLogs(p -> p.getTime().isAfter(time1) && p.getTime().isBefore(time2)));
              System.out.println("查询完毕");
              break;
            case "2":// 按类型
              System.out.println("请输入类型：ERROR或DEBUG");
              inputString = reader.readLine().trim();
              final String input1 = new String(inputString);
              System.out.println(logKeeper.getFilterLogs(p -> p.getLogType().equals(input1)));
              System.out.println("查询完毕");
              break;
            case "3":// 按类
              System.out.println("请输入类名");
              inputString = reader.readLine().trim();
              final String input2 = new String(inputString);
              System.out.println(logKeeper.getFilterLogs(p -> p.getClassName().equals(input2)));
              System.out.println("查询完毕");
              break;
            case "4":// 按方法
              System.out.println("请输入方法名");
              inputString = reader.readLine().trim();
              final String input3 = new String(inputString);
              System.out.println(logKeeper.getFilterLogs(p -> p.getMethodName().equals(input3)));
              System.out.println("查询完毕");
              break;
            default:
              LOGGER.debug("输入错误");
              break;
          }
          break;
        // case "12":// 4人接力
        // Strategy strategy3 = new Relay4Strategy();
        // this.arrangeOrbit(strategy3);
        // arrangeOutout();
        // System.out.println("分组完成\n");
        // break;
        case "13":// 文件输出
          System.out.println("选择输出策略：\n");
          System.out.println("1.\tBuffer");
          System.out.println("2.\tStream");
          System.out.println("3.\tWriter");
          input = reader.readLine();
          if (input != null) {
            input = input.trim();
          } else {
            continue;
          }
          String filePath = "fileoutput/TrackGame.txt";
          OutputStrategy strategy = null;
          switch (input) {
            case "1":// Buffer
              strategy = new BufferedOutputStrategy(filePath);
              break;
            case "2":// Stream
              strategy = new StreamOutputStrategy(filePath);
              break;
            case "3":// Writer
              strategy = new WriterOutputStrategy(filePath);
              break;
            default:
              System.out.println("输入错误");
              break;
          }
          long startTime1 = System.currentTimeMillis();
          fileOutPut(strategy);
          long endTime1 = System.currentTimeMillis();
          System.out.println("文件输出时间： " + (endTime1 - startTime1) + "ms");
          System.out.println("文件输出成功");
          break;
        case "end":// 结束游戏
          exitflag = true;
          break;
        default:
          System.out.println("输入错误");
          break;
      }
      if (exitflag) {
        break;
      }
    }

  }

  public void fileOutPut(OutputStrategy strategy) {
    long startTime1 = System.currentTimeMillis();
    try {
      strategy.write(String.format("Game ::= %d\n", gameType));
      strategy.write("NumOfTracks ::= " + trackNum + "\n");
      for (Athlete a : athleteSet) {
        strategy.write(String.format("Athlete ::= <%s,%d,%s,%d,%f>\n", a.getName(), a.getIdNum(),
            a.getNationality(), a.getAge(), a.getBestRecord()));
      }
      strategy.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    long endTime1 = System.currentTimeMillis();
    System.out.println("输出文件时间： " + (endTime1 - startTime1) + "ms");

  }

  /**
   * 重整轨道系统函数.
   * 
   * @param strategy 重整策略
   */
  public void arrangeOrbit(Strategy strategy) {
    List<Track> trackList = new ArrayList<Track>();
    int[] defaultRadius = new int[10];
    for (int i = 0; i < 10; i++) {
      defaultRadius[i] = 50 + 100 * i;
    }
    for (int i = 0; i < trackNum; i++) {
      trackList.add(new Track("tarck" + i, defaultRadius[i]));
    }
    List<Map<Track, List<Athlete>>> arrangementMap =
        strategy.arrange(new ArrayList<>(athleteSet), trackList);

    int orbitNum = arrangementMap.size();

    trackOrbitList.clear();
    for (int i = 0; i < orbitNum; i++) {
      Map<Track, List<Athlete>> currentMap = arrangementMap.get(i);
      trackBuilder.createCircularOrbit();
      trackBuilder.bulidTracks(trackList);
      trackBuilder.bulidPhysicalObjects(currentMap);
      TrackCircularOrbit newOrbit = (TrackCircularOrbit) trackBuilder.getConcreteCircularOrbit();
      trackOrbitList.add(newOrbit);
    }
  }

  /**
   * 批量输出函数.
   */
  public void arrangeOutout() {
    for (int i = 0; i < trackOrbitList.size(); i++) {
      TrackCircularOrbit currentOrbit = trackOrbitList.get(i);
      System.out.println(currentOrbit.toString());
    }
  }

  /**
   * checkRep函数.
   */
  public void checkRep() {
    for (TrackCircularOrbit orbit : trackOrbitList) {
      orbit.checkRep();
    }
  }

  public static void main(String[] args) throws IOException, ObjectNoFoundException {
    new TrackGame().gameMain();
  }
}

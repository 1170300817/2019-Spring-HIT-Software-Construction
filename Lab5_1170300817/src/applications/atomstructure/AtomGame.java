package applications.atomstructure;

import circularorbithelper.CircularOrbitApis;
import exception.IllegalTextGrammarException;
import exception.LogicalErrorException;
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
import java.util.List;
import logrecord.LogKeeper;
import logrecord.LoggerFactory;
import org.apache.log4j.Logger;
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
  private String elementName;
  private final TransitCareTaker transitCareTaker = new TransitCareTaker();
  private AtomCircularOrbit atomCircularOrbit = null;
  private final AtomCircularOrbitBuilder atomOrbitBuilder = new AtomCircularOrbitBuilder();
  private static Logger LOGGER = LoggerFactory.createLogger(AtomGame.class);
  private LogKeeper logKeeper;
  private final int[] defaultRadius = new int[10];

  /**
   * 菜单.
   */
  public void gameMenu() {
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
    System.out.println("11.\t日志查询");
    System.out.println("12.\t文件输出");
    System.out.println("end.\t 结束");
  }

  /**
   * 主体.
   * 
   * @throws IOException 读写错误
   */
  public void gameMain() throws IOException {
    for (int i = 0; i < 10; i++) {
      defaultRadius[i] = 50 + 100 * i;
    }
    String inputString;
    String[] arguments;
    boolean exitflag = false;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    while (true) {
      gameMenu();
      String input = reader.readLine();
      switch (input) {
        case "1":// 读取文件
          try {
            System.out.println("输入需要读取的文件名：例如src/txt/AtomicStructure.txt");
            String filePath = reader.readLine();
            StringBuilder nameBuilder = new StringBuilder();
            System.out.println("选择读入策略：");
            System.out.println("1.\tBuffer");
            System.out.println("2.\tStream");
            System.out.println("3.\tReader");
            input = reader.readLine();
            if (input != null) {
              input = input.trim();
            } else {
              continue;
            }
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
            atomOrbitBuilder.createFromFile(strategy, nameBuilder);
            elementName = nameBuilder.toString();
            atomCircularOrbit = (AtomCircularOrbit) atomOrbitBuilder.getConcreteCircularOrbit();
          } catch (IllegalTextGrammarException | IOException | NumberFormatException
              | LogicalErrorException e) {
            LOGGER.error(e.getMessage());
            System.out.println("请重新读取文件");
            break;
          }
          System.out.println(atomCircularOrbit.toString());
          trackNum = atomCircularOrbit.getTrackNum();
          LOGGER.debug("文件读取成功");
          break;
        case "2":// 跃迁
          System.out.println("输入跃迁的起始轨道和终止轨道");
          System.out.println("输入范围为0——" + (trackNum - 1));
          inputString = reader.readLine();
          arguments = inputString.split("\\s");
          int trackNum1 = Integer.valueOf(arguments[0]);
          int trackNum2 = Integer.valueOf(arguments[1]);
          Track t1 = atomCircularOrbit.getSortedTracks().get(trackNum1);
          Track t2 = atomCircularOrbit.getSortedTracks().get(trackNum2);
          if (atomCircularOrbit.transit(t1, t2)) {
            transitCareTaker.addMemento(new Memento(t1, t2));
            LOGGER.debug("跃迁成功:从" + t1.getName() + "到" + t2.getName());
            System.out.println(atomCircularOrbit.toString());
          } else {
            LOGGER.debug("跃迁失败");
          }
          break;
        case "3":// 回退
          Memento m = transitCareTaker.getMemento();
          Track fromTrack = m.getToTrack();
          Track toTrack = m.getFromTrack();
          if (atomCircularOrbit.transit(fromTrack, toTrack)) {
            LOGGER.debug("回退成功");
            System.out.println(atomCircularOrbit.toString());
          } else {
            LOGGER.debug("回退失败");
          }
          break;
        case "4":// 可视化
          LOGGER.debug("可视化完成");
          CircularOrbitApis.visualize(atomCircularOrbit);
          break;
        case "5":// 文字输出
          LOGGER.debug("文字输出完成");
          System.out.println(atomCircularOrbit.toString());
          break;
        case "6":// 增加新轨道
          System.out.println("增加新轨道\n");
          trackNum++;
          atomCircularOrbit
              .addTrack(new Track("track" + (trackNum - 1), defaultRadius[trackNum - 1]));
          System.out.println("轨道数：" + trackNum + "\n");
          LOGGER.debug("轨道增加成功");
          System.out.println(atomCircularOrbit.toString());
          break;
        case "7":// 增加新电子
          System.out.println("输入需要增加电子的轨道\n");
          System.out.println("输入范围为0——" + (trackNum - 1));
          inputString = reader.readLine();
          arguments = inputString.split("\\s");
          int trackIndex1 = Integer.valueOf(arguments[0]);
          Track t = atomCircularOrbit.getSortedTracks().get(trackIndex1);
          atomCircularOrbit.addObjectToTrack(t, Particle.getElectron());
          System.out.println(atomCircularOrbit.toString());
          LOGGER.debug("增加成功");
          break;
        case "8":// 删除电子
          System.out.println("输入需要删除电子的轨道\n");
          System.out.println("输入范围为0——" + (trackNum - 1));
          inputString = reader.readLine();
          arguments = inputString.split("\\s");
          Track track2 = atomCircularOrbit.getSortedTracks().get(Integer.valueOf(arguments[0]));
          if (atomCircularOrbit.removeElectron(track2)) {
            LOGGER.debug("删除成功");
          } else {
            LOGGER.debug("删除失败");
          }
          System.out.println(atomCircularOrbit.toString());
          break;
        case "9":// 删除整条轨道
          System.out.println("输入需要删除的轨道\n");
          System.out.println("输入范围为0——" + (trackNum - 1));
          inputString = reader.readLine();
          arguments = inputString.split("\\s");
          Track track3 = atomCircularOrbit.getSortedTracks().get(Integer.valueOf(arguments[0]));
          if (atomCircularOrbit.removeTrack(track3)) {
            LOGGER.debug("删除成功");
          } else {
            LOGGER.debug("删除失败");
          }
          System.out.println(atomCircularOrbit.toString());
          trackNum--;
          break;
        case "10":// 计算熵值
          System.out.println("信息熵为：" + atomCircularOrbit.getObjectDistributionEntropy() + "\n");
          LOGGER.debug("计算熵值完成");
          break;
        case "11":// 日志查询
          logKeeper = new LogKeeper();
          System.out.println("选择筛选条件：\n");
          System.out.println("1.\t时间段");
          System.out.println("2.\t按类型");
          System.out.println("3.\t按类");
          System.out.println("4.\t按方法");
          input = reader.readLine();
          if (input != null) {
            input = input.trim();
          } else {
            continue;
          }
          switch (input) {
            case "1":// 时间段
              System.out.println("请输入开始时间，格式参考：2019-05-19 02:05:14");
              inputString = reader.readLine();
              final Instant time1 = Instant.parse(inputString.replace(' ', 'T') + "Z");
              System.out.println("请输入结束时间，格式参考：2019-05-19 02:05:14");
              inputString = reader.readLine();
              final Instant time2 = Instant.parse(inputString.replace(' ', 'T') + "Z");
              System.out.println(logKeeper
                  .getFilterLogs(p -> p.getTime().isAfter(time1) && p.getTime().isBefore(time2)));
              System.out.println("查询完毕");
              break;
            case "2":// 按类型
              System.out.println("请输入类型：ERROR或DEBUG");
              inputString = reader.readLine();
              final String input1 = new String(inputString);
              System.out.println(logKeeper.getFilterLogs(p -> p.getLogType().equals(input1)));
              System.out.println("查询完毕");
              break;
            case "3":// 按类
              System.out.println("请输入类名");
              inputString = reader.readLine();
              final String input2 = new String(inputString);
              System.out.println(logKeeper.getFilterLogs(p -> p.getClassName().equals(input2)));
              System.out.println("查询完毕");
              break;
            case "4":// 按方法
              System.out.println("请输入方法名");
              inputString = reader.readLine();
              final String input3 = new String(inputString);
              System.out.println(logKeeper.getFilterLogs(p -> p.getMethodName().equals(input3)));
              System.out.println("查询完毕");
              break;
            default:
              LOGGER.debug("输入错误");
              break;
          }
          break;
        case "12":// 文件输出
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
          String filePath = "fileoutput/AtomicStructure.txt";
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
          LOGGER.debug("游戏结束");
          exitflag = true;
          break;
        default:
          LOGGER.debug("输入错误");
          break;
      }
      if (exitflag) {
        break;
      }
    }

  }

  public void fileOutPut(OutputStrategy strategy) {
    try {
      strategy.write("ElementName ::= " + elementName + "\n");
      strategy.write("NumberOfTracks ::= " + trackNum + "\n");
      StringBuilder NumberOfElectron = new StringBuilder("NumberOfElectron ::= ");
      List<Track> tracklist = atomCircularOrbit.getSortedTracks();
      int i = 0;
      for (i = 0; i < tracklist.size() - 1; i++) {
        int num = atomCircularOrbit.getObjectNumonTrack(tracklist.get(i));
        NumberOfElectron.append(i + 1);
        NumberOfElectron.append("/" + num + ";");
      }
      int num = atomCircularOrbit.getObjectNumonTrack(tracklist.get(i));
      NumberOfElectron.append(i + 1);
      NumberOfElectron.append("/" + num + "\n");
      strategy.write(NumberOfElectron.toString());
      strategy.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws IOException {
    new AtomGame().gameMain();
  }
}

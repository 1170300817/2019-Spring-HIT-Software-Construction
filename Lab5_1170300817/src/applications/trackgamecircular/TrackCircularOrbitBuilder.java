package applications.trackgamecircular;

import circularorbit.CircularOrbitBuilder;
import exception.IllegalParameterException;
import exception.IllegalTextGrammarException;
import exception.SameLabelException;
import iostrategy.InputStrategy;
import java.io.IOException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import phsicalobject.PhysicalObject;

public class TrackCircularOrbitBuilder extends CircularOrbitBuilder<PhysicalObject, Athlete> {
  public Integer gameType;
  public Integer trackNum;


  public Integer getGameType() {
    return gameType;
  }

  public Integer getTrackNum() {
    return trackNum;
  }

  /**
   * 创建具体类型的子类.
   */
  @Override
  public void createCircularOrbit() {
    concreteCircularOrbit = new TrackCircularOrbit();
  }

  /**
   * 读取文件构建轨道系统.
   * 
   * @param fileString filename
   * @return 返回人物列表
   * @throws IllegalTextGrammarException 文本语法错误
   * @throws NumberFormatException 数据格式错误
   * @throws IOException 文件读写错误
   * @throws SameLabelException 名字一样错误
   * @throws IllegalParameterException 参数非法错误
   */
  public void createFromFile(InputStrategy strategy, Set<Athlete> athleteSet)
      throws IllegalTextGrammarException, NumberFormatException, IOException, SameLabelException,
      IllegalParameterException {
    String fileline;
    String athletepattern =
        "Athlete\\s*::=\\s*<([a-zA-Z]+),(\\d+),([A-Z]{3}),(\\d+),(\\d{1,2}\\.\\d{2})>";
    String gamepattern = "Game\\s*::=\\s*(100|200|400)";
    String trackpattern = "NumOfTracks\\s*::=\\s*([4-9]|10)";
    long startTime = System.currentTimeMillis();

    while ((fileline = strategy.readLine()) != null) {
      Matcher athleteMatcher = Pattern.compile(athletepattern).matcher(fileline);
      Matcher gameMatcher = Pattern.compile(gamepattern).matcher(fileline);
      Matcher trackMatcher = Pattern.compile(trackpattern).matcher(fileline);
      if (athleteMatcher.find()) {
        String nameString = athleteMatcher.group(1);
        Integer idNum = Integer.valueOf(athleteMatcher.group(2));
        String nationality = athleteMatcher.group(3);
        int age = Integer.valueOf(athleteMatcher.group(4));
        double bestRecord = Double.valueOf(athleteMatcher.group(5));
        Athlete a;
        a = Athlete.getInstance(nameString, idNum, nationality, age, bestRecord);
        athleteSet.add(a);
      } else if (gameMatcher.find()) {
        gameType = Integer.valueOf(gameMatcher.group(1));
      } else if (trackMatcher.find()) {
        trackNum = Integer.valueOf(trackMatcher.group(1));
      } else {
        analyzeInput(fileline);
      }
    }
    long endTime = System.currentTimeMillis();
    System.out.println("读取文件时间： " + (endTime - startTime) + "ms");

    strategy.close();
  }

  /**
   * 分许错误输入的函数.
   * 
   * @param readLine 错误的文件行
   * @throws IllegalTextGrammarException 语法错误
   */
  public String analyzeInput(String readLine) throws IllegalTextGrammarException {
    String msgString = null;
    String[] arguments = readLine.trim().split("\\s");
    if (arguments[0].equals("Athlete")) {
      String[] parameter = arguments[2].split(",");
      if (parameter.length != 5) {
        throw new IllegalTextGrammarException(readLine + ":运动员参数缺失");
      }
      if (!Pattern.matches("(<[A-Za-z]+)", parameter[0])) {
        // System.out.println(111);
        throw new IllegalTextGrammarException(readLine + ":运动员名字错误");
      }
      if (!Pattern.matches("([A-Z]{3})", parameter[2])) {
        throw new IllegalTextGrammarException(readLine + ":运动员国籍错误");
      }
      if (!Pattern.matches("(\\d{1,2}\\.\\d{2}>)", parameter[4])) {
        throw new IllegalTextGrammarException(readLine + ":运动员最好成绩错误");
      }
    } else if (arguments[0].equals("Game")) {
      if (!Pattern.matches("(100|200|400)", arguments[2])) {
        throw new IllegalTextGrammarException(readLine + ":无效的比赛类型");
      }
    } else if (arguments[0].equals("NumOfTracks")) {
      if (!Pattern.matches("([4-9]|10)", arguments[2])) {
        throw new IllegalTextGrammarException(readLine + ":无效的轨道数目");
      }
    }
    return msgString;
  }
}

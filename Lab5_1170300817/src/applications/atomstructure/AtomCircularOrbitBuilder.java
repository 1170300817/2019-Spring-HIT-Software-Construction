package applications.atomstructure;

import circularorbit.CircularOrbitBuilder;
import exception.IllegalTextGrammarException;
import exception.LogicalErrorException;
import iostrategy.InputStrategy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import track.Track;

public class AtomCircularOrbitBuilder extends CircularOrbitBuilder<Particle, Particle> {

  /**
   * 从文件中新建轨道结构.
   * 
   * @param fileString 文件名
   * @throws IllegalTextGrammarException 语法错误
   * @throws NumberFormatException 数字转换错误
   * @throws IOException 读写错误
   * @throws LogicalErrorException 文件逻辑关系错误
   */
  public void createFromFile(InputStrategy strategy, StringBuilder elementName)
      throws IllegalTextGrammarException, NumberFormatException, IOException,
      LogicalErrorException {
    Particle nuclear = null;
    int[] defaultRadius = new int[10];
    for (int i = 0; i < 10; i++) {
      defaultRadius[i] = 50 + 100 * i;
    }
    Integer trackNum = null;
    createCircularOrbit();
    String fileline;
    String elementpattern = "ElementName\\s*::=\\s*([A-Z]{1}[a-z]{0,1})$";
    String trackpattern = "NumberOfTracks\\s*::=\\s*(\\d+)";
    String electronpattern = "NumberOfElectron\\s*::=\\s*((?:(?:\\d+\\/\\d+)|;)+)";
    while ((fileline = strategy.readLine()) != null) {
      Matcher elementMatcher = Pattern.compile(elementpattern).matcher(fileline);
      Matcher trackMatcher = Pattern.compile(trackpattern).matcher(fileline);
      Matcher electronMatcher = Pattern.compile(electronpattern).matcher(fileline);
      if (elementMatcher.find()) {
        elementName.append(elementMatcher.group(1));
        nuclear = Particle.getNucleus(elementMatcher.group(1));
      } else if (trackMatcher.find()) {
        trackNum = Integer.valueOf(trackMatcher.group(1));
      } else if (electronMatcher.find()) {
        List<Track> trackList = new ArrayList<Track>();
        Map<Track, Set<Particle>> elementMap = new HashMap<Track, Set<Particle>>();
        String[] num = electronMatcher.group(1).split(";");
        if (num.length != trackNum) {
          strategy.close();
          throw new LogicalErrorException(":轨道数前后不一致错误");
        }
        for (int i = 0; i < num.length; i++) {
          Track track = new Track("track" + i, defaultRadius[i]);
          trackList.add(track);
          String[] value = num[i].split("/");
          int objNum = Integer.valueOf(value[1]);
          Set<Particle> currentList = new HashSet<Particle>();
          for (int j = 0; j < objNum; j++) {
            Particle p = Particle.getElectron();
            currentList.add(p);
          }
          elementMap.put(track, currentList);
        }
        this.createCircularOrbit();
        this.bulidTracks(trackList);
        this.bulidPhysicalObjects(nuclear, elementMap);
      } else {
        analyzeInput(fileline);
      }
    }
    strategy.close();
  }

  /**
   * 分析文件读取错误原因.
   * 
   * @param readLine 错误的行
   * @throws IllegalTextGrammarException 语法错误
   */
  public void analyzeInput(String readLine) throws IllegalTextGrammarException {
    String[] arguments = readLine.trim().split("\\s");
    if (arguments[0].equals("ElementName")) {
      if (arguments.length != 3) {
        throw new IllegalTextGrammarException(readLine + ":元素名字参数缺失");
      }
      if (!Pattern.matches("\\s*([A-Z] {1}[a-z] {0,1})", arguments[2])) {
        throw new IllegalTextGrammarException(readLine + ":元素名字错误");
      }
    } else if (arguments[0].equals("NumberOfTracks")) {
      if (arguments.length != 3) {
        throw new IllegalTextGrammarException(readLine + ":轨道数参数缺失");
      }
      if (!Pattern.matches("\\s*(\\d+)", arguments[2])) {
        throw new IllegalTextGrammarException(readLine + ":轨道数错误");
      }
    } else if (arguments[0].equals("NumberOfElectron")) {
      if (!Pattern.matches("\\s*((?:(?:\\d+\\/\\d+)|;)+)", arguments[2])) {
        throw new IllegalTextGrammarException(readLine + ":轨道电子参数错误");
      }
    }
  }

  @Override
  public void createCircularOrbit() {
    concreteCircularOrbit = new AtomCircularOrbit();
  }

}

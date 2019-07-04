package applications.socialnetworkcircle;

import circularorbit.CircularOrbitBuilder;
import exception.IllegalParameterException;
import exception.IllegalTextGrammarException;
import exception.SameLabelException;
import exception.UnDefinedPersonException;
import iostrategy.InputStrategy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import track.Track;

public class SocialNetCircularOrbitBuilder extends CircularOrbitBuilder<Person, Person> {
  public List<Person> personList = new ArrayList<Person>();

  public SocialNetCircularOrbitBuilder() {
    concreteCircularOrbit = new SocialNetCircularOrbit();
  }

  /**
   * 创建具体类型的子类.
   */
  @Override
  public void createCircularOrbit() {
    concreteCircularOrbit = new SocialNetCircularOrbit();
  }

  /**
   * 从关系网络生成轨道结构.
   * 
   * @param centralObj 中心物体
   * @param personSet 人物列表
   * @param keeperSet 关系保存的列表
   * @throws UnDefinedPersonException 关系中出现没有定义的人
   */
  public void bulidRelations(Person centralObj, Set<Person> personSet,
      Set<RelationKeeper> keeperSet) throws UnDefinedPersonException {

    Map<Track, Set<Person>> orbitMap = new HashMap<>();
    concreteCircularOrbit.setCentralObject(centralObj);
    List<Track> trackList = new LinkedList<>();
    Map<String, Person> personNameSet = new HashMap<>();
    for (Person p : personSet) {
      personNameSet.put(p.getName(), p);
    }
    for (RelationKeeper keeper : keeperSet) {
      // Iterator<Person> iterator = personSet.iterator();
      Person p1 = personNameSet.get(keeper.getFromString());
      Person p2 = personNameSet.get(keeper.getToString());
      // while (iterator.hasNext()) {
      // Person person = iterator.next();
      // if (person.getName().equals(keeper.getFromString())) {
      // p1 = person;
      // }
      if (centralObj.getName().equals(keeper.getFromString())) {
        p1 = centralObj;
      }
      // if (person.getName().equals(keeper.getToString())) {
      // p2 = person;
      // }
      // }
      if (p1 == null) {
        throw new UnDefinedPersonException("关系中出现未定义的人:" + keeper.getFromString());
      }
      if (p2 == null) {
        throw new UnDefinedPersonException("关系中出现未定义的人:" + keeper.getToString());
      }
      if (p1.getName().equals(centralObj.getName())) {
        concreteCircularOrbit.addcentralRelation(p2, keeper.getWeight());
      } else {
        concreteCircularOrbit.addtrackRelation(p1, p2, keeper.getWeight());
        concreteCircularOrbit.addtrackRelation(p2, p1, keeper.getWeight());
      }
    }
//    System.out.println("aaa： " + System.currentTimeMillis() + "ms");

    Set<Person> finishedPerson = new HashSet<>();
    Track track1 = new Track("track0", 50);
    orbitMap.put(track1, new HashSet<Person>());
    trackList.add(track1);
    for (Person p : concreteCircularOrbit.getCentralConnectedObject()) {
      orbitMap.get(track1).add(p);
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
        if (concreteCircularOrbit.getTrackConnectedObject(p).size() > 0) {
          for (Person peo : concreteCircularOrbit.getTrackConnectedObject(p)) {
            if (!finishedPerson.contains(peo)) {
              temSet.add(peo);
              flag = true;
            }
          }
        }
      }
      if (flag) {
        trackList.add(t);
        orbitMap.put(t, new HashSet<Person>());
        orbitMap.get(t).addAll(temSet);
        finishedPerson.addAll(temSet);
      }
    }
    this.bulidTracks(trackList);
    this.bulidPhysicalObjects(centralObj, orbitMap);
  }

  /**
   * 读取文件构建轨道系统.
   * 
   * @param strategy
   * @param personSet
   * @param keeperSet 保存关系的
   * @throws IllegalTextGrammarException 文本语法错误
   * @throws SameLabelException 名字一样错误
   * @throws NumberFormatException 数据格式错误
   * @throws IOException 文件读写错误
   * @throws UnDefinedPersonException 关系中出现未定义的人报错
   * @throws IllegalParameterException 参数非法错误
   */
  public void createFromFile(InputStrategy strategy, Set<Person> personSet,
      Set<RelationKeeper> keeperSet) throws IllegalTextGrammarException, SameLabelException,
      NumberFormatException, IOException, UnDefinedPersonException, IllegalParameterException {
    Person centralUser = null;
    String fileline;
    String centralUserPatternString =
        "CentralUser\\s*::=\\s*<([A-Za-z0-9]+),\\s*(\\d+),\\s*([MF])>";
    String friendPatternString = "Friend\\s*::=\\s*<([A-Za-z0-9]+),\\s*(\\d+),\\s*([MF])>";
    String socialTiePatternString = "SocialTie\\s*::=\\s*<([A-Za-z0-9]+),+"
        + "\\s*([A-Za-z0-9]+),\\s*(0(?:\\.\\d{1,3})?|1(?:\\.0{1,3})?)>";
    this.createCircularOrbit();
    long startTime = System.currentTimeMillis();
    while ((fileline = strategy.readLine()) != null) {
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
        personSet.add(person);

      } else if (socialTieMatcher.find()) {
        Double weight = Double.valueOf(socialTieMatcher.group(3));
        RelationKeeper currKeeper =
            new RelationKeeper(socialTieMatcher.group(1), socialTieMatcher.group(2), weight);
        keeperSet.add(currKeeper);
      } else {
        analyzeInput(fileline);
      }
    }
    strategy.close();
    long endTime = System.currentTimeMillis();
    System.out.println("读取文件时间： " + (endTime - startTime) + "ms");
    long startTime1 = System.currentTimeMillis();
    this.bulidRelations(centralUser, new HashSet<>(personSet), new HashSet<>(keeperSet));
    long endTime1 = System.currentTimeMillis();
    System.out.println("构建关系时间： " + (endTime1 - startTime1) + "ms");

  }

  /**
   * 分许错误输入的函数.
   * 
   * @param readLine 错误的文件行
   * @throws IllegalTextGrammarException 语法错误
   */
  public void analyzeInput(String readLine) throws IllegalTextGrammarException {
    String[] arguments = readLine.trim().split("=");
    if (Pattern.matches("(CentralUser\\s*::\\s*)", arguments[0])
        || Pattern.matches("(Friend\\s*::\\s*)", arguments[0])) {
      String[] parameter = arguments[1].split(",");
      if (parameter.length != 3) {
        throw new IllegalTextGrammarException(readLine + ":人物参数缺失");
      }
      if (!Pattern.matches("(\\s*<[A-Za-z0-9]+)", parameter[0])) {
        throw new IllegalTextGrammarException(readLine + ":人物名字错误");
      }
      if (!Pattern.matches("(\\d+)", parameter[1])) {
        throw new IllegalTextGrammarException(readLine + ":人物年龄错误");
      }
      if (!Pattern.matches("([MF]>)", parameter[2])) {
        throw new IllegalTextGrammarException(readLine + ":人物性别错误");
      }
    } else if (Pattern.matches("(SocialTie\\s*::\\s*)", arguments[0])) {
      String[] parameter = arguments[1].split(",");
      if (parameter.length != 3) {
        throw new IllegalTextGrammarException(readLine + ":社交关系参数缺失");
      }
      if (!Pattern.matches("(\\s*<[A-Za-z0-9]+)", parameter[0])
          || !Pattern.matches("(\\s*[A-Za-z0-9]+)", parameter[1])) {
        throw new IllegalTextGrammarException(readLine + ":社交关系名字错误");
      }
      if (!Pattern.matches("(0(?:\\.\\d{1,3})?|1(?:\\.0{1,3})?>)", parameter[0])) {
        throw new IllegalTextGrammarException(readLine + ":社交关系亲密度错误");
      }
    }

  }
}

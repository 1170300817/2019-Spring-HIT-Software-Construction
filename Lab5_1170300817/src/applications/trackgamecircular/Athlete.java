package applications.trackgamecircular;

import exception.IllegalParameterException;
import exception.SameLabelException;
import java.util.HashMap;
import java.util.regex.Pattern;
import phsicalobject.PhysicalObject;

public class Athlete extends PhysicalObject implements Comparable<Athlete> {
  // Abstraction function:
  // 所以AF是从一个记录着名字，id号码，国籍，年龄，最好成绩的抽象数据型到现实运动员的映射

  // Representation invariant:
  // 名字，id号码，国籍，年龄，最好成绩都不能为空

  // Safety from rep exposure:
  // 设置关键数据name,idNum,nationality,age,bestRecord为private final防止更改
  private final Integer idNum;
  private final String nationality;
  private final Integer age;
  private final double bestRecord;
  private static final HashMap<String, Athlete> nameMap = new HashMap<>();

  public Integer getIdNum() {
    return idNum;
  }

  public double getBestRecord() {
    return bestRecord;
  }

  /**
   * 构造方法.
   * 
   * @param nameString 名字
   * @param idNum 号码
   * @param nationality 国籍
   * @param age 年龄
   * @param bestRecord 最好成绩
   */
  public Athlete(String nameString, Integer idNum, String nationality, Integer age,
      double bestRecord) {
    super(nameString);
    this.idNum = idNum;
    this.nationality = nationality;
    this.age = age;
    this.bestRecord = bestRecord;
  }

  public String getNationality() {
    return nationality;
  }

  public Integer getAge() {
    return age;
  }

  /**
   * 静态工厂方法.
   * 
   * @param nameString 名字
   * @param idNum id号码
   * @param nationality 国籍
   * @param age 年龄
   * @param bestRecord 最好成绩
   * @return Athlete实例
   * @throws SameLabelException 同名错误
   * @throws IllegalParameterException 参数不合法错误
   */
  public static Athlete getInstance(String nameString, Integer idNum, String nationality,
      Integer age, double bestRecord) throws SameLabelException, IllegalParameterException {

//    if (nameMap.containsKey(nameString)) {
//      throw new SameLabelException(nameString + "为名的运动员已经存在");
//    }
    Athlete athlete = new Athlete(nameString, idNum, nationality, age, bestRecord);
    nameMap.put(nameString, athlete);
    athlete.checkRep();
    return athlete;
  }

  /**
   * compareTo接口，以成绩排序.
   * 
   * @param o 运动员
   * 
   */

  @Override
  public int compareTo(Athlete o) {
    if (this.bestRecord - o.getBestRecord() == 0) {
      return 0;
    } else if (this.bestRecord - o.getBestRecord() > 0) {
      return 1;
    } else {
      return -1;
    }
  }

  /**
   * checkrep函数.
   * 
   * @throws IllegalParameterException 参数不合法错误
   */
  public void checkRep() throws IllegalParameterException {
    if (!Pattern.matches("[A-Za-z]+", this.name)) {
      throw new IllegalParameterException(this.name + ":运动员名字错误");
    }
    if (!Pattern.matches("([A-Z]{3})", nationality)) {
      throw new IllegalParameterException(nationality + ":运动员国籍错误");
    }
    if (!Pattern.matches("(\\d{1,2}\\.\\d{1,2})", String.valueOf(bestRecord))) {
      throw new IllegalParameterException(bestRecord + ":运动员最好成绩错误");
    }
  }
}

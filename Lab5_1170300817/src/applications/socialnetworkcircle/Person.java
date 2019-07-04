package applications.socialnetworkcircle;

import exception.IllegalParameterException;
import exception.SameLabelException;
import java.util.HashMap;
import java.util.regex.Pattern;
import phsicalobject.PhysicalObject;


public class Person extends PhysicalObject {
  // Abstraction function:
  // 所以AF是从一个记录着名字，年龄，gender的抽象数据型到现实人际网络中的人的映射

  // Representation invariant:
  // 名字，年龄，gender都不能为空

  // Safety from rep exposure:
  // 同父类
  // 设置关键数据age,gender为private final防止更改
  private final int age;
  private final String gender;
  private static final HashMap<String, Person> nameMap = new HashMap<>();

  public Integer getAge() {
    return age;
  }

  public String getGender() {
    return gender;
  }

  /**
   * 构造方法.
   * 
   * @param name 名字
   * @param age 年龄
   * @param gender 性别
   */
  public Person(String name, int age, String gender) {
    super(name);
    this.age = age;
    this.gender = gender;
  }

  /**
   * 静态工厂方法.
   * 
   * @param name 名字
   * @param age 年龄
   * @param gender 性别
   * @return 实例
   * @throws SameLabelException 名字重复报错
   * @throws IllegalParameterException 参数不和规定报错
   */
  public static Person getInstance(String name, int age, String gender)
      throws SameLabelException, IllegalParameterException {
    if (nameMap.containsKey(name)) {
      throw new SameLabelException(name + "为名的对象已经存在");
    }
    Person p = new Person(name, age, gender);
     p.checkRep();
    nameMap.put(name, p);
    return p;

  }

  /**
   * checkRep函数.
   * 
   * @throws IllegalParameterException 参数异常抛出
   */
  public void checkRep() throws IllegalParameterException {
    if (!Pattern.matches("(\\s*[A-Za-z0-9]+)", name)) {
      throw new IllegalParameterException(name + ":人物名字错误");
    }
    if (!Pattern.matches("([MF])", gender)) {
      throw new IllegalParameterException(gender + ":人物性别错误");
    }
    if (!Pattern.matches("(\\d{1,3})", String.valueOf(age))) {
      throw new IllegalParameterException(gender + ":人物年龄错误");
    }

  }
}

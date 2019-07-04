/**
 * 
 */
/**
 * @author Administrator
 *
 */
package applications.TrackGame;

import java.util.ArrayList;
import java.util.regex.Pattern;

import exception.illegalParameterException;
import exception.sameLabelException;
import phsicalObject.PhysicalObject;

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
	private static final ArrayList<String> nameList = new ArrayList<String>();

	public Integer getIdNum() {
		return idNum;
	}

	public double getBestRecord() {
		return bestRecord;
	}

	/**
	 * 构造方法
	 * 
	 * @param nameString
	 * @param idNum
	 * @param nationality
	 * @param age
	 * @param bestRecord
	 */
	public Athlete(String nameString, Integer idNum, String nationality, Integer age, double bestRecord) {
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
	 * 静态工厂方法
	 * 
	 * @param nameString  名字
	 * @param idNum       id号码
	 * @param nationality 国籍
	 * @param age         年龄
	 * @param bestRecord  最好成绩
	 * @return Athlete实例
	 * @throws sameLabelException
	 */
	public static Athlete getInstance(String nameString, Integer idNum, String nationality, Integer age,
			double bestRecord) throws sameLabelException, illegalParameterException {

		for (String s : nameList) {
			if (s.equals(nameString)) {
				throw new sameLabelException(nameString + "为名的对象已经存在");
			}
		}
		Athlete athlete = new Athlete(nameString, idNum, nationality, age, bestRecord);
		nameList.add(nameString);
		athlete.checkRep();
		return athlete;
	}

	/**
	 * 重写compareTo实现Comparable
	 * 
	 * @param 比较目标运动员
	 * @return 比较结果
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

	public void checkRep() throws illegalParameterException {
		if (!Pattern.matches("[A-Za-z]+", this.name)) {
			throw new illegalParameterException(this.name + ":运动员名字错误");
		}
		if (!Pattern.matches("([A-Z]{3})", nationality)) {
			throw new illegalParameterException(nationality + ":运动员国籍错误");
		}
		if (!Pattern.matches("(\\d{1,2}\\.\\d{1,2})", String.valueOf(bestRecord))) {
			throw new illegalParameterException(bestRecord + ":运动员最好成绩错误");
		}
	}
}
/**
 * 
 */
/**
 * @author Administrator
 *
 */
package applications.TrackGame;

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
	 */
	public static Athlete getInstance(String nameString, Integer idNum, String nationality, Integer age,
			double bestRecord) {
		Athlete athlete = new Athlete(nameString, idNum, nationality, age, bestRecord);
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
}
package applications.SocialNetworkCircle;

/**
 * @author Administrator 读取文件时用的辅助类：因为读取文件读入的过程没法将读取到的人名
 *         马上与实例对应起来，所以构造relationKeeper保存每个关系的人名string。
 */
public class relationKeeper {
	private final String fromString;
	private final String toString;
	private final double weight;

	public String getFromString() {
		return fromString;
	}

	public String getToString() {
		return toString;
	}

	public double getWeight() {
		return weight;
	}

	/**
	 * 构造方法
	 * 
	 * @param fromString
	 * @param toString
	 * @param weight
	 */
	public relationKeeper(String fromString, String toString, double weight) {
		super();
		this.fromString = fromString;
		this.toString = toString;
		this.weight = weight;
	}
}

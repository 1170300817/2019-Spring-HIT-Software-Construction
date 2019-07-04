package applications.SocialNetworkCircle;

import java.util.ArrayList;
import exception.sameLabelException;

/**
 * @author Administrator 读取文件时用的辅助类：因为读取文件读入的过程没法将读取到的人名
 *         马上与实例对应起来，所以构造relationKeeper保存每个关系的人名string。
 */
public class relationKeeper {
	private final String fromString;
	private final String toString;
	private final double weight;
	private static final ArrayList<relationKeeper> keeperList = new ArrayList<relationKeeper>();

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
	 * @throws sameLabelException
	 */
	public relationKeeper(String fromString, String toString, double weight) throws  sameLabelException{
		super();
		this.fromString = fromString;
		this.toString = toString;
		this.weight = weight;
		if (fromString.equals(toString)) {
			throw new sameLabelException("从" + fromString + "到" + toString + "的边无效");
		}
		for (relationKeeper k : keeperList) {
			if (k.getFromString().equals(toString) && k.getToString().equals(fromString)) {
				throw new sameLabelException("从" + fromString + "到" + toString + "的边已经存在");
			}
			if (k.getFromString().equals(fromString) && k.getToString().equals(toString)) {
				throw new sameLabelException("从" + fromString + "到" + toString + "的边已经存在");
			}
		}
		keeperList.add(this);
	}
}

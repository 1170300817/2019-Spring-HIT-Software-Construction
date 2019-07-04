package P3;

public class Position {

	private int Px;
	private int Py; // 保存坐标

	/**
	 * 构造方法
	 * @param px x坐标
	 * @param py y坐标
	 */
	public Position(int px, int py) {
		this.Px = px;
		this.Py = py;
	}

	/**
	 * @return 当前点x
	 */
	public int getPx() {
		return Px;
	}

	/**
	 * @return 当前点y
	 */
	public int getPy() {
		return Py;
	}

	/**
	 * 判断当前位置与目标位置是否相等
	 * @param a 比较对象
	 * @return 布尔值：相等与否
	 */
	public boolean equals(Position P) {
		if (P.Px == this.Px && P.Py == this.Py)
			return true;
		else
			return false;
	}
}

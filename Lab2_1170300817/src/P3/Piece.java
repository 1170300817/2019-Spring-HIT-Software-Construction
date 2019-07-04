package P3;

public class Piece {

	private final String pieceName; // 棋子名字
	private Position piecePosition;// 棋子位置
	private final int owner;// 棋子所有者

	/**
	 * 构造方法
	 * 
	 * @param pieceState 构造棋子的状态
	 * @param pieceName  构造棋子的姓名
	 * @param owner      所有者
	 */
	public Piece(String pieceName, int owner) {
		this.pieceName = pieceName;
		this.owner = owner;
	}

	/**
	 * @return 返回棋子的所有者
	 */
	public int getOwner() {
		return owner;
	}

	/**
	 * @return 返回棋子的名字
	 */
	public String getName() {
		return this.pieceName;
	}

	/**
	 * @return 返回棋子的位置
	 */
	public Position getPosition() {
		return this.piecePosition;
	}

	/**
	 * @param x 位置参数
	 * @param y 位置参数
	 */
	public void setPosition(Integer x, Integer y) {
		this.piecePosition = new Position(x, y);
	}

}

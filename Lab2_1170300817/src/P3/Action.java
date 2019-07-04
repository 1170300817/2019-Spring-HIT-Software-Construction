package P3;

import java.util.ArrayList;

public class Action {
	private final ArrayList<String> historys = new ArrayList<>();
	int historycounter = 0;
	// Abstraction function:
	// 是一个用抽象数据类型模拟的各种操作到真实下棋动作的抽象函数

	// Representation invariant:
	// 每个操作必须有操作选手，操作对象不为空，操作位置必须合法。

	// Safety from rep exposure:
	// 设置historys为private final
	// 在有必要的时候使用防御性拷贝
	/**
	 * 放置棋子
	 * 
	 * @param player 操作选手
	 * @param board  操作的棋盘
	 * @param piece  放置的棋子
	 * @return
	 */
	public boolean putPiece(Player player, Board board, Piece piece) {
		boolean flag = board.putPiece(piece);
		if (flag) {
			historys.add(historycounter + ". " + player.getPlayerName() + " add a " + piece.getName() + " piece to ("
					+ piece.getPosition().getPx() + "," + piece.getPosition().getPy() + ").");
			historycounter++;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 移除棋子
	 * 
	 * @param player 操作选手
	 * @param board  操作的棋盘
	 * @param piece  移除的棋子的位置
	 * @return
	 */
	public boolean removePiece(Player player, Board board, Position position) {
		boolean flag = board.removePiece(position);
		if (flag) {
			historys.add(historycounter + ". " + player.getPlayerName() + " remove the piece at (" + position.getPx()
					+ "," + position.getPy() + ").");
			historycounter++;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param board操作的棋盘
	 * @param position   检查的位置
	 * @return
	 */
	public String checkPos(Board board, Position position) {
		return board.checkPos(position);
	}

	/**
	 * @param board 操作的棋盘
	 * @return 返回说明双方棋子数目的字符串
	 */
	public String countPiece(Board board) {
		return board.countPiece();
	}

	/**
	 * 输出记录操作历史的数组
	 */
	public void printHistory() {
		for (String s : historys) {
			System.out.println(s);
		}
	}

	/**
	 * @param player 操作的玩家
	 * @param board  操作的棋盘
	 * @param p1     起始位置
	 * @param p2     终止位置
	 * @return
	 */
	public boolean move(Player player, Board board, Position p1, Position p2) {
		boolean flag = board.move(player, p1, p2);
		if (flag) {
			historys.add(historycounter + ". " + player.getPlayerName() + " move the piece at (" + p1.getPx() + ","
					+ p1.getPy() + ") to position (" + p2.getPx() + "," + p2.getPy() + ")");
			historycounter++;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param player操作的玩家
	 * @param board       操作的棋盘
	 * @param p1          起始位置
	 * @param p2          终止位置
	 * @return 布尔值 成功与否
	 */
	public boolean eat(Player player, Board board, Position p1, Position p2) {
		boolean flag = board.eat(player, p1, p2);
		if (flag) {
			historys.add(historycounter + ". " + player.getPlayerName() + " move the piece at (" + p1.getPx() + ","
					+ p1.getPy() + ") to position (" + p2.getPx() + "," + p2.getPy() + ")");
			historycounter++;
			return true;
		} else {
			return false;
		}
	}
}

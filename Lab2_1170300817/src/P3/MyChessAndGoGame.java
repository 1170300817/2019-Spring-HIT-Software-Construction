package P3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MyChessAndGoGame {

	public Game game;//执行操作的game实例

	/**
	 * 围棋的菜单
	 */
	public void goGameMenu() {
		System.out.println("1.\t落子（将尚未在棋盘上的一颗棋子放在棋盘上的指定位置）");
		System.out.println("2.\t提子（移除对手棋子）");
		System.out.println("3.\t查询某个位置的占用情况");
		System.out.println("4.\t计算两个玩家在棋盘上的棋子总数");
		System.out.println("5.\t跳过");
		System.out.println("end.\t 结束游戏");
	}

	/**
	 * 国际象棋菜单
	 */
	public void cheseGameMenu() {
		System.out.println("1.\t移动棋盘上的某个位置的棋子至新的位置");
		System.out.println("2.\t吃子（使用己方棋子吃掉对手棋子）");
		System.out.println("3.\t查询某个位置的占用情况");
		System.out.println("4.\t计算两个玩家分别在棋盘上的棋子总数");
		System.out.println("5.\t跳过");
		System.out.println("end.\t 结束游戏");
	}

	/**
	 * 游戏尚未明确类型时的客户端函数
	 */
	public void gameMain() {
		String gameType;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(System.in));
			while (true) {
				System.out.println("输入游戏类型:");
				System.out.println("a:国际象棋   b:围棋");
				gameType = reader.readLine().trim();
				if (gameType.equals("a") || gameType.equals("b")) {
					game = new Game(gameType);
					break;
				}
			}
			System.out.println("请输入选手A的名字");
			String playerAName = reader.readLine().trim();
			System.out.println("请输入选手B的名字");
			String playerBName = reader.readLine().trim();
			game.setNames(playerAName, playerBName);
			if (gameType.equals("a")) {
				System.out.println("国际象棋游戏开始!");
				cheseGame();
			} else if (gameType.equals("b")) {
				System.out.println("围棋游戏开始!");
				goGame();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new MyChessAndGoGame().gameMain();
	}

	/**
	 * 执行围棋操作
	 * 
	 * @throws IOException
	 */
	public void goGame() throws IOException {
		String inputString;
		String[] colorStrings = new String[2];
		colorStrings[0] = "white";
		colorStrings[1] = "black";
		BufferedReader reader = null;
		String[] arguments;
		int TURN = 0;// 出手顺序变量
		boolean exitflag = false;
		try {
			reader = new BufferedReader(new InputStreamReader(System.in));
			while (true) {
				System.out.println();
				System.out.println("it's " + game.getPlayer(TURN).getPlayerName() + "'s turn!");
				goGameMenu();
				String input = reader.readLine().trim();
				switch (input) {
				case "1":// 将尚未在棋盘上的一颗棋子放在棋盘上的指定位置
					System.out.println("你是选手" + game.getPlayer(TURN).getPlayerName() + "，执白，请输入坐标x y：（以空格隔开）");
					inputString = reader.readLine().trim();
					arguments = inputString.split("\\s");
					if (arguments.length == 2) {
						Piece newPiece = new Piece(colorStrings[TURN], TURN);
						Position P = new Position(Integer.valueOf(arguments[0]), Integer.valueOf(arguments[1]));
						if (game.addnewPiece(game.getPlayer(TURN), newPiece, P)) {
							TURN = (TURN + 1) % 2;
							System.out.println("Done");
						} else {
							continue;
						}
					} else {
						System.out.println("输入参数不为2个");
					}
					break;
				case "2":// 提子（移除对手棋子）
					System.out.println("输入棋子位置坐标x，y：（以空格隔开）");
					inputString = reader.readLine().trim();
					arguments = inputString.split("\\s");
					if (arguments.length == 2) {
						Position P = new Position(Integer.valueOf(arguments[0]), Integer.valueOf(arguments[1]));
						if (game.removePiece(game.getPlayer(TURN), P)) {
							TURN = (TURN + 1) % 2;
							System.out.println("Done");
						} else {
							continue;
						}
					} else {
						System.out.println("输入参数不为2个");
					}
					break;
				case "3":// 查询占用情况
					System.out.println("输入棋子位置坐标x，y：（以空格隔开）");
					inputString = reader.readLine().trim();
					arguments = inputString.split("\\s");
					if (arguments.length == 2) {
						Position P = new Position(Integer.valueOf(arguments[0]), Integer.valueOf(arguments[1]));
						System.out.println(game.checkPos(P));
					}
					break;
				case "4":// 计算两个玩家在棋盘上的棋子总数
					System.out.println(game.countPiece());
					break;
				case "5":// 计算两个玩家在棋盘上的棋子总数
					System.out.println("玩家跳过");
					TURN = (TURN + 1) % 2;
					break;
				case "end":// 结束游戏
					System.out.println("The game is over.");
					exitflag = true;
					break;
				default:
					System.out.println("输入错误");
					break;
				}
				if (exitflag)
					break;
			}
			System.out.println("是否查看操作历史？(输入y或n)");
			switch (reader.readLine().trim()) {
			case "y":
				game.printHistory();
				break;
			case "n":
				break;
			}
			System.out.println("。。。。。。。游戏结束。。。。。");
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 执行国际象棋的函数
	 * 
	 * @throws IOException
	 */
	public void cheseGame() throws IOException {
		String inputString;
		BufferedReader reader = null;
		String[] arguments;
		int TURN = 0;// 出手顺序变量
		boolean exitflag = false;
		try {
			reader = new BufferedReader(new InputStreamReader(System.in));
			while (true) {
				System.out.println();
				System.out.println("it's " + game.getPlayer(TURN).getPlayerName() + "'s turn!");
				cheseGameMenu();
				String input = reader.readLine().trim();
				switch (input) {
				case "1": // 移动棋盘上的某个位置的棋子至新的位置
					System.out.println("你是选手" + game.getPlayer(TURN).getPlayerName() + "，请输入坐标x1 y1 和坐标x2 y2：（以空格隔开）");
					inputString = reader.readLine().trim();
					arguments = inputString.split("\\s");
					if (arguments.length == 4) {
						Position P1 = new Position(Integer.valueOf(arguments[0]), Integer.valueOf(arguments[1]));
						Position P2 = new Position(Integer.valueOf(arguments[2]), Integer.valueOf(arguments[3]));
						if (game.move(game.getPlayer(TURN), P1, P2)) {
							TURN = (TURN + 1) % 2;
							System.out.println("Done");
						}
					} else {
						System.out.println("输入参数不为4个");
					}
					break;
				case "2":// 吃子（使用己方棋子吃掉对手棋子）
					System.out.println("你是选手" + game.getPlayer(TURN).getPlayerName() + "，请输入坐标x1 y1 和坐标x2 y2：（以空格隔开）");
					inputString = reader.readLine().trim();
					arguments = inputString.split("\\s");
					if (arguments.length == 4) {
						Position P1 = new Position(Integer.valueOf(arguments[0]), Integer.valueOf(arguments[1]));
						Position P2 = new Position(Integer.valueOf(arguments[2]), Integer.valueOf(arguments[3]));
						if (game.eat(game.getPlayer(TURN), P1, P2)) {
							TURN = (TURN + 1) % 2;
							System.out.println("Done");
						}
					} else {
						System.out.println("输入参数不为4个");
					}
					break;
				case "3":// 查询占用情况
					System.out.println("输入棋子位置坐标x，y：（以空格隔开）");
					inputString = reader.readLine().trim();
					arguments = inputString.split("\\s");
					if (arguments.length == 2) {
						Position P = new Position(Integer.valueOf(arguments[0]), Integer.valueOf(arguments[1]));

						System.out.println(game.checkPos(P));
					}
					break;
				case "4":// 计算两个玩家在棋盘上的棋子总数
					System.out.println(game.countPiece());
					break;
				case "5":// 计算两个玩家在棋盘上的棋子总数
					System.out.println("玩家跳过");
					TURN = (TURN + 1) % 2;
					break;
				case "end":// 结束游戏
					System.out.println("The game is over.");
					exitflag = true;
					break;
				default:
					System.out.println("输入错误");
					break;
				}
				if (exitflag)
					break;
			}
			System.out.println("是否查看操作历史？(输入y或n)");
			switch (reader.readLine().trim()) {
			case "y":
				game.printHistory();
				break;
			case "n":
				break;
			}
			System.out.println("。。。。。。。游戏结束。。。。。");
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

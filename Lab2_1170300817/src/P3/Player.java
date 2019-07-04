package P3;

public class Player {
	private String playerName; // 玩家姓名
	private Integer turn;// 玩家出手顺序

	/**
	 * @return 返回玩家出手顺序
	 */
	public Integer getPlayerTurn() {
		return turn;
	}

	/**
	 * @param turn 设置玩家出手顺序
	 */
	public void setPlayerTurn(Integer turn) {
		this.turn = turn;
	}

	/**
	 * @return 返回玩家名字
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * @param playerName 设置玩家名字
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
}

package P3;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BoardTest {
	// Testing strategy
	// 主要测试putPiece，removePiece，move，eat几个重点操作
	// 测试其面对各类异常（位置越界，位置无效，所有权非法）的表现
	@Test
	public void testputPiece() {
		Board B = new Board("b");
		Piece piece1 = new Piece("AAA", 1);
		Piece piece2 = new Piece("AAA", 1);
		piece1.setPosition(1, 1);
		piece2.setPosition(101, 101);
		assertTrue(B.putPiece(piece1));
		assertFalse(B.putPiece(piece1));
		assertFalse(B.putPiece(piece2));
	}

	@Test
	public void testremovePiece() {
		Board B = new Board("b");
		Piece piece1 = new Piece("AAA", 1);
		Position pa = new Position(1, 1);
		Position pb = new Position(2, 2);
		piece1.setPosition(1, 1);
		B.putPiece(piece1);
		assertTrue(B.removePiece(pa));
		assertFalse(B.removePiece(pb));
	}

	@Test
	public void testmove() {
		Game game = new Game("a");
		Board B = game.getGameBoard();
		Player paPlayer = new Player();
		paPlayer.setPlayerName("a");
		paPlayer.setPlayerTurn(0);
		Position pa = new Position(0, 0);
		Position pb = new Position(2, 2);
		Position pc = new Position(3, 3);
		Position pd = new Position(999, 999);
		assertTrue(B.move(paPlayer, pa, pb));
		assertTrue(B.move(paPlayer, pb, pc));
		assertFalse(B.move(paPlayer, pa, pb));
		assertFalse(B.move(paPlayer, pd, pb));

	}

	@Test
	public void testeat() {
		Game game = new Game("a");
		Board B = game.getGameBoard();
		Player paPlayer = new Player();
		paPlayer.setPlayerName("a");
		paPlayer.setPlayerTurn(0);
		Position pa = new Position(0, 0);
		Position pb = new Position(2, 2);
		Position pc = new Position(3, 3);
		Position pd = new Position(999, 999);
		assertTrue(B.move(paPlayer, pa, pb));
		assertTrue(B.move(paPlayer, pb, pc));
		assertFalse(B.move(paPlayer, pa, pb));
		assertFalse(B.move(paPlayer, pd, pb));
	}
}

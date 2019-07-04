package P3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class GameTest {
	// Testing strategy
	// TODO
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}
	public Game gametest =new Game("b");
	
	@Test
	public void testgettersetter() {
		gametest.setNames("pl1", "pl2");
		assertEquals("pl1", gametest.getPlayerA().getPlayerName());
		assertEquals("pl2", gametest.getPlayerB().getPlayerName());
	}
	@Test
	public void testaddnew() {
		Piece testPiece1=new Piece("black",0);
		Piece testPiece2=new Piece("white",1);
		Piece testPiece3=new Piece("white",1);
		Position P1 =new Position(1,1);
		Position P2 =new Position(2,1);
		Position P3 =new Position(3,1);
		gametest.addnewPiece(gametest.getPlayerA(), testPiece1, P1);
		gametest.addnewPiece(gametest.getPlayerB(), testPiece2, P2);
		gametest.addnewPiece(gametest.getPlayerB(), testPiece3, P3);
		assertTrue(gametest.getGameBoard().getBoardSet().contains(testPiece1));
		assertTrue(gametest.getGameBoard().getBoardSet().contains(testPiece2));
		assertTrue(gametest.getGameBoard().getBoardSet().contains(testPiece3));
	}
	@Test
	public void testremove() {

		
	}

}

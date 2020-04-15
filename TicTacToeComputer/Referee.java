/**
 * In this class, the referee will set the
 * X and O players as opponents, set the board,
 * and run the game by letting the X player start.
 * @author Toya Okeke UCID: 10152084 Lab No. 3 Exercise 4
 * 
 */
public class Referee {
	private Player xPlayer;
	private Player oPlayer;
	private Board board;
	
	/**
	 * Referee starts game by the setting the opponents,
	 * board, and letting the X player start
	 */
	public void runTheGame() {
		xPlayer.setOpponent(oPlayer);
		oPlayer.setOpponent(xPlayer);
		System.out.println("\nReferee started the game...\n");
		setBoard(board);
		xPlayer.play();
	}
	
	/**
	 * Sets and displays the board
	 * @param board The board
	 */
	public void setBoard(Board board) {
		this.board = board;
		this.board.display();
	}
	
	/**
	 * Sets the X player
	 * @param xPlayer X player
	 */
	public void setxPlayer(Player xPlayer) {
		this.xPlayer = xPlayer;
	}
	
	/**
	 * Sets the O player
	 * @param oPlayer O player
	 */
	public void setoPlayer(Player oPlayer) {
		this.oPlayer = oPlayer;
	}
}

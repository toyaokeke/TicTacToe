/**
 * This class construct the player with their name and mark, and lets them make
 * moves as long as the game has not ended
 * 
 * @author Toya Okeke UCID: 10152084 Lab No. 3 Exercise 4
 *
 */
abstract class Player implements Constants {
	protected String name;
	protected Board board;
	protected Player opponent;
	protected char mark;

	/**
	 * Constructor that assigns a name and mark to player
	 * 
	 * @param name Name of player
	 * @param mark Player's mark
	 */
	public Player(String name, char mark) {
		this.name = name;
		this.mark = mark;
	}

	/**
	 * Allows player to make a move as long as there is no winner and the board
	 * isn't full
	 */
	protected abstract void play();

	/**
	 * This method asks the user to choose a row and column to place their mark in
	 * and displays he board. If there is a winner, it ends the game. Otherwise, the
	 * opponent plays. The user is asked again to choose a cell is their entries have
	 * already been used or out of bounds.
	 */
	protected abstract void makeMove();


	/**
	 * Sets the player's opponent
	 * 
	 * @param opponent The opponent
	 */
	protected void setOpponent(Player opponent) {
		this.opponent = opponent;
	}

	/**
	 * Sets the board
	 * 
	 * @param theBoard The board
	 */
	protected void setBoard(Board theBoard) {
		board = theBoard;
	}
	
	protected void checkWhoWon() {
		if (board.checkWinner(mark) == 1) {
			System.out.println("THE GAME IS OVER: " + name + " is the winner!");
			System.out.println("Game Ended...");
			return;
		} else {
			opponent.play();
		}
	}
}

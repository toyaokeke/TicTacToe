import java.util.Scanner;

public class HumanPlayer extends Player{
	public HumanPlayer(String name, char mark) {
		super(name, mark);
	}
	
	protected  void play() {
		if (board.checkWinner(mark) == 0 && board.isFull() == true) { // if board is full and no winner
			System.out.println("THE GAME IS OVER: Tie Game!");
			System.out.println("Game Ended...");
		}

		else if (board.xWins() == false && board.oWins() == false && board.isFull() == false) { // if no winner and
																								// board not full
			makeMove();
		}
	}

	/**
	 * This method asks the user to choose a row and column to place their mark in
	 * and displays he board. If there is a winner, it ends the game. Otherwise, the
	 * opponent plays. The user is asked again to choose a cell is their entries have
	 * already been used or out of bounds.
	 */
	@Override
	protected void makeMove() {
		Scanner move = new Scanner(System.in);
		while (true) {
			System.out.print(name + ", what row should your next " + mark + " be placed in? ");
			int row = move.nextInt(); // player input their row
			System.out.print("\n" + name + ", what column should your next " + mark + " be placed in? ");
			int column = move.nextInt(); // player inputs their column
			if ((row >=0 && row <= 2) && (column >= 0 && column <= 2)) {
				if(board.getMark(row, column) == SPACE_CHAR) {
				board.addMark(row, column, mark);
				System.out.println();
				board.display();
				break;
				} else {
					System.out.println("\nThis cell has been used!\n");
				}
			} else {
				System.out.println("\nThe row/column does not exist!\n");
			} 
		}
		checkWhoWon();
//		if (board.checkWinner(mark) == 1) {
//			System.out.println("THE GAME IS OVER: " + name + " is the winner!");
//			System.out.println("Game Ended...");
//			return;
//		} else {
//			opponent.play();
//		}
	}

}

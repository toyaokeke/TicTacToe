
public class RandomPlayer extends Player {
	
	protected RandomGenerator rand;
	
	public RandomPlayer(String name, char mark, RandomGenerator r) {
		super (name, mark);
		rand = r;
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
	
	@Override
	protected void makeMove() {
		while (true) {
			int row = rand.discrete(0, 2);
			int column = rand.discrete(0, 2);
			if ((row >=0 && row <= 2) && (column >= 0 && column <= 2)) {
				if(board.getMark(row, column) == SPACE_CHAR) {
				board.addMark(row, column, mark);
				System.out.println();
				board.display();
				checkWhoWon();
				break;
				}
			}
		}
	}

}

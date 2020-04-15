
public class BlockingPlayer extends RandomPlayer {

	public BlockingPlayer(String name, char mark, RandomGenerator r) {
		super(name, mark, r);
	}

	@Override
	protected void makeMove() {
		boolean test = false;
		
		outerloop:
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {
				if (board.getMark(row, column) == SPACE_CHAR) {
					if (testForBlocking(row, column) == true) {
						test = true;
						board.addMark(row, column, mark);
						System.out.println();
						board.display();
						checkWhoWon();
						break outerloop;
					}
				}
			}
		}
		if (test == false) {
			super.makeMove();
		}
	}

	protected boolean testForBlocking(int row, int col) {
		boolean test;
		board.addMark(row, col, opponent.mark);

		if (board.checkWinner(opponent.mark) == 1) {
			test = true;
		} else {
			test = false;
		}

		board.removeMark(row, col);
		return test;
	}
}

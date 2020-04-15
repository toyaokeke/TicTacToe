
public class SmartPlayer extends BlockingPlayer {

	public SmartPlayer(String name, char mark, RandomGenerator r) {
		super(name, mark, r);
	}

	@Override
	protected void makeMove() {
		boolean test = false;
		
		outerloop:
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {
				if (board.getMark(row, column) == SPACE_CHAR) {
					if (testForWinning(row, column) == true) {
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

	protected boolean testForWinning(int row, int col) {
		boolean test;
		board.addMark(row, col, mark);

		if (board.checkWinner(mark) == 1) {
			test = true;
		} else {
			test = false;
		}

		board.removeMark(row, col);
		return test;
	}
}

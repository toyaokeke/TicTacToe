
//STUDENTS SHOULD ADD CLASS COMMENTS, METHOD COMMENTS, FIELD COMMENTS 
/**
 * This class generates the board for tic tac toe board. It also has methods for
 * checking the winner, checking when the board is full, and adding marks to the
 * board.
 */

public class Board implements Constants {
	private char theBoard[][];
	private int markCount;

	/**
	 * This constructor initializes the board with empty spaces and sets the number
	 * of spaces filled to zero
	 */
	public Board() {
		markCount = 0;
		theBoard = new char[3][];
		for (int i = 0; i < 3; i++) {
			theBoard[i] = new char[3];
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		}
	}

	/**
	 * Method gets the mark in a cell
	 * 
	 * @param row board row
	 * @param col board column
	 * @return Returns mark in that cell
	 */
	public char getMark(int row, int col) {
		return theBoard[row][col];
	}

	/**
	 * This is used when the board is full
	 * 
	 * @return Returns boolean of board being full
	 */
	public boolean isFull() {
		return markCount == 9;
	}

	/**
	 * Checks if X won
	 * 
	 * @return Returns true or false if X won
	 */
	public boolean xWins() {
		if (checkWinner(LETTER_X) == 1)
			return true;
		else
			return false;
	}

	/**
	 * Checks if O won
	 * 
	 * @return Returns true or false if O won
	 */
	public boolean oWins() {
		if (checkWinner(LETTER_X) == 1)
			return true;
		else
			return false;
	}

	/**
	 * Displays board for the players
	 */
	public void display() {
		displayColumnHeaders();
		addHyphens();
		for (int row = 0; row < 3; row++) {
			addSpaces();
			System.out.print("    row " + row + ' ');
			for (int col = 0; col < 3; col++)
				System.out.print("|  " + getMark(row, col) + "  ");
			System.out.println("|");
			addSpaces();
			addHyphens();
		}
	}

	/**
	 * Adds a player's marker to a cell
	 * 
	 * @param row  board row
	 * @param col  board column
	 * @param mark player's marker
	 */
	public void addMark(int row, int col, char mark) {

		theBoard[row][col] = mark;
		markCount++;
	}

	public void removeMark(int row, int col) {
		theBoard[row][col] = SPACE_CHAR;
		markCount--;
	}

	/**
	 * Clears the board and resets the mark count to 0
	 */
	public void clear() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		markCount = 0;
	}

	/**
	 * Checks if there is a winner for vertical, horizontal and diagonal lines
	 * 
	 * @param mark player's marker
	 * @return Returns 1 if there is a winner and 0 if there isn't
	 */
	int checkWinner(char mark) {
		int row, col;
		int result = 0;

		for (row = 0; result == 0 && row < 3; row++) {
			int row_result = 1;
			for (col = 0; row_result == 1 && col < 3; col++)
				if (theBoard[row][col] != mark)
					row_result = 0;
			if (row_result != 0)
				result = 1;
		}

		for (col = 0; result == 0 && col < 3; col++) {
			int col_result = 1;
			for (row = 0; col_result != 0 && row < 3; row++)
				if (theBoard[row][col] != mark)
					col_result = 0;
			if (col_result != 0)
				result = 1;
		}

		if (result == 0) {
			int diag1Result = 1;
			for (row = 0; diag1Result != 0 && row < 3; row++)
				if (theBoard[row][row] != mark)
					diag1Result = 0;
			if (diag1Result != 0)
				result = 1;
		}
		if (result == 0) {
			int diag2Result = 1;
			for (row = 0; diag2Result != 0 && row < 3; row++)
				if (theBoard[row][3 - 1 - row] != mark)
					diag2Result = 0;
			if (diag2Result != 0)
				result = 1;
		}
		return result;
	}

	/**
	 * Displays column headers
	 */
	void displayColumnHeaders() {
		System.out.print("          ");
		for (int j = 0; j < 3; j++)
			System.out.print("|col " + j);
		System.out.println();
	}

	/**
	 * prints horizontal lines for the board cells
	 */
	void addHyphens() {
		System.out.print("          ");
		for (int j = 0; j < 3; j++)
			System.out.print("+-----");
		System.out.println("+");
	}

	/**
	 * prints vertical lines for board cells
	 */
	void addSpaces() {
		System.out.print("          ");
		for (int j = 0; j < 3; j++)
			System.out.print("|     ");
		System.out.println("|");
	}
}

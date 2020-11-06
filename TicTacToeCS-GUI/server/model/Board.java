package server.model;

//STUDENTS SHOULD ADD CLASS COMMENTS, METHOD COMMENTS, FIELD COMMENTS 

public class Board implements Constants {
	private char theBoard[][];
	private int markCount;
	String boardString;
	private int id;

	/**
	 * Default constructor for Class Board. Initiates the board space and sets
	 * markCount to 0.
	 */
	public Board(int id) {
		markCount = 0;
		theBoard = new char[3][];
		for (int i = 0; i < 3; i++) {
			theBoard[i] = new char[3];
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		}
		this.id = id;
	}

	/**
	 * Gets the mark at the specified board location
	 * 
	 * @param row The row of the location
	 * @param col The column of the location
	 * @return The mark at the location
	 */
	public char getMark(int row, int col) {
		return theBoard[row][col];
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Checks if the board has been fully populated with player moves
	 * 
	 * @return True if board is full, false if not full.
	 */
	public boolean isFull() {
		return markCount == 9;
	}

	/**
	 * Checks if player x has won.
	 * 
	 * @return True if player x has won, false otherwise.
	 */
	public boolean xWins() {
		if (checkWinner(LETTER_X) == 1)
			return true;
		else
			return false;
	}

	/**
	 * Checks if player o has won.
	 * 
	 * @return True if player o has won, false otherwise.
	 */
	public boolean oWins() {
		if (checkWinner(LETTER_O) == 1)
			return true;
		else
			return false;
	}

	/**
	 * Displays the game board in console.
	 */
	public String display() {
		boardString = "";
		displayColumnHeaders();
		addHyphens();
		for (int row = 0; row < 3; row++) {
			addSpaces();
			// System.out.print(" row " + row + ' ');
			boardString += "    row " + row + ' ';
			for (int col = 0; col < 3; col++) {
				// System.out.print("| " + getMark(row, col) + " ");
				boardString += "|  " + getMark(row, col) + "  ";
			}
			// System.out.println("|");
			boardString += "|\n";
			addSpaces();
			addHyphens();
		}

		return boardString;
	}

	/**
	 * Add a mark on the board from a player. Increments markCount.
	 * 
	 * @param row  Row of the mark being added.
	 * @param col  Column of the mark being added.
	 * @param mark The player's mark.
	 */
	public void addMark(int row, int col, char mark) {

		theBoard[row][col] = mark;
		markCount++;
	}

	/**
	 * Clears the board of player marks. Resets markCount.
	 */
	public void clear() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		markCount = 0;
	}

	/**
	 * Checks if there is a row, column, or diagonal with 3 consecutive player
	 * marks.
	 * 
	 * @param mark The player mark to be checked.
	 * @return 1 if there are 3 consecutive marks, 0 otherwise.
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
	 * Displays column headers to the board.
	 */
	void displayColumnHeaders() {
		// System.out.print(" ");
		boardString += "          ";
		for (int j = 0; j < 3; j++) {
			// System.out.print("|col " + j);
			boardString += "|col " + j;
		}
		// System.out.println();
		boardString += "\n";
	}

	/**
	 * Displays board row divisions in console.
	 */
	void addHyphens() {
		// System.out.print(" ");
		boardString += "          ";
		for (int j = 0; j < 3; j++) {
			// System.out.print("+-----");
			boardString += "+-----";
		}
		// System.out.println("+");
		boardString += "+\n";
	}

	/**
	 * Displays board column divisions in console.
	 */
	void addSpaces() {
		// System.out.print(" ");
		boardString += "          ";
		for (int j = 0; j < 3; j++) {
			// System.out.print("| ");
			boardString += "|     ";
		}
		// System.out.println("|");
		boardString += "|\n";
	}

	public void updateBoard(String incomingBoard) {
		char[] updatedBoard = incomingBoard.toCharArray();
		markCount = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				theBoard[i][j] = updatedBoard[i * 3 + j];
				if (theBoard[i][j] != SPACE_CHAR)
					markCount++;
			}
		}
	}

	public String getBoardString() {
		String printString = "";
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				printString += Character.toString(theBoard[i][j]);
			}
		}
		return printString;
	}
}

package Ex4Files.ticTacToe_Server;

import java.io.*;

public class Player{

	private String name;
	private Board board;
	private Player opponent;
	private char mark;

	private PrintWriter socketOut;
	private BufferedReader socketIn;

	////////////////////////////////////////////////////////////////
	//GETTERS AND SETTERS
	/**
	 * Gets the name of the player.
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the mark of the player.
	 * @return the playerMark
	 */
	public char getMark() {
		return mark;
	}

	/**
	 * Sets the name of the player.
	 * @param name The name to set for the player.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the mark of the player.
	 * @param mark The mark to set for the player.
	 */
	public void setMark(char mark) {
		this.mark = mark;
	}

	////////////////////////////////////////////////////////////////
	//CONSTRUCTOR
	/**
	 * Constructs an object of class Player according to the specific
	 * name and mark received. Opponent and board will remain unassigned.
	 * @param name	The name to set for the player.
	 * @param mark	The mark to set for the player.
	 */
	public Player(String name, char mark, BufferedReader socketIn, PrintWriter socketOut) {
		this.name = name;
		this.mark = mark;
		this.socketIn = socketIn;
		this.socketOut = socketOut;
	}

	////////////////////////////////////////////////////////////////
	//INSTANCE METHODS
	/**
	 * Represents a move from the player, takes information entered by
	 * the player and displays it on the board, if valid. Then, passes 
	 * move to the opponent.
	 * @throws IOException When game could not poll for human input.
	 */
	public void play() {
		if(!gameActive()) {
			opponent.gameActive();
			System.out.println("Game Finished. Shutting down game...");
			endGame(false);
			opponent.endGame(false);
			return;
		}
		opponent.printWaitingMessage();
		try {
			makeMove();
		} catch (IOException e) {
			System.err.println("Unable to get input from client. Shutting down game...");
			opponent.endGame(true);
			e.printStackTrace();
			try {
				socketIn.close();
				socketOut.close();
			}
			catch (IOException e1) {
				e.printStackTrace();
			}
			return;
		}
		opponent.play();
	}

	private void displayBoard(boolean end) {
		if(!end)
			socketOut.println("The current board: ");
		else
			socketOut.println("Game ending board: ");
		socketOut.println(board.display());
	}
	
	private void printWaitingMessage() {
		socketOut.println("Waiting for " + opponent.name + " to make a move.");
	}

	private void endGame(boolean error) {
		if(!error) {
			displayBoard(true);
			socketOut.println("Shutting down game...");
		}
		else
			socketOut.println("Unable to get input from " + opponent.name + ". Shutting down game...");
		try {
			socketIn.close();
			socketOut.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Polls the current player for row and column to place the mark. Move will be
	 * successful if row and column numbers are within the board and the space not
	 * already populated.
	 * @throws IOException When game could not poll for human input.
	 */
	public void makeMove() throws IOException {
		int row, column;
		boolean moveComplete = false;
		while(!moveComplete) {
			//Poll for row and column information
			//			System.out.println(name + ", it's your move.");
			socketOut.println(name + ", it's your move.");
			displayBoard(false);
			//			System.out.println("Please enter the row number: ");
			socketOut.println("Please enter the row number: ");

			try {
				row = Integer.parseInt(socketIn.readLine());
			} catch (NumberFormatException e) {
				row = -1;
			}
			while(row > 2 || row < 0) { //Must be within board boundary
				//				System.out.println("Invalid row number, please try again: ");
				socketOut.println("Invalid row number, please try again: ");
				try {
					row = Integer.parseInt(socketIn.readLine());
				} catch (NumberFormatException e) {
					row = -1;
				}
			}
			//			System.out.println("Please enter the column number: ");
			socketOut.println("Please enter the column number: ");
			try {
				column = Integer.parseInt(socketIn.readLine());
			} catch (NumberFormatException e) {
				column = -1;
			}
			while(column > 2 || column < 0) { //Must be within board boundary
				//				System.out.println("Invalid column number, please try again: ");
				socketOut.println("Invalid column number, please try again: ");
				try {
					column = Integer.parseInt(socketIn.readLine());
				} catch (NumberFormatException e) {
					column = -1;
				}
			}
			//Check if board space has already been marked
			if(board.getMark(row, column) == ' ') {
				board.addMark(row, column, mark);
				moveComplete = true;
				System.out.println("Move successful, polling opponent.");
				socketOut.println("Move successful.");
				displayBoard(false);
			}
			else {
				System.out.println("Invalid move, board space already populated!");
				socketOut.println("Invalid move, board space already populated!");
			}
		}
	}

	/**
	 * Sets the opponent of the player.
	 * @param opponent	The opponent to set.
	 */
	public void setOpponent(Player opponent) {
		this.opponent = opponent;
	}

	/**
	 * Sets the board for the game.
	 * @param board	The board to set.
	 */
	public void setBoard(Board board) {
		this.board = board;
	}

	////////////////////////////////////////////////////////////////
	//HELPER METHODS
	/**
	 * Checks if game is still active by checking if player X or player O has
	 * won or if the game board is full. Displays appropriate message if game
	 * is over.
	 * @return	True if game is not over. False otherwise.
	 */
	private Boolean gameActive() {
		if(board.xWins()) {
			if(mark == 'X')
				socketOut.println(name + ", you win!");
			else
				socketOut.println(name + ", you lost!");
			return false;
		}
		else if(board.oWins()) {
			if(mark == 'O')
				socketOut.println(name + ", you win!");
			else
				socketOut.println(name + ", you lost!");
			return false;
		}
		else if(board.isFull()) {
			socketOut.println("Board is full. It's a tie!");
			return false;
		}
		return true;
	}
}

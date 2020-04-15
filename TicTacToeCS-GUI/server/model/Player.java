package Ex5Files.server.model;

import java.io.*;

public class Player{

	private Board board;
	private Player opponent;
	private char mark;
	private String name;

	private PrintWriter socketOut;
	private BufferedReader socketIn;

	////////////////////////////////////////////////////////////////
	//GETTERS AND SETTERS

	/**
	 * @return the playerMark
	 */
	public char getMark() {
		return mark;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
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
			System.out.println("[GAME " + board.getId() + "]: Game Finished. Shutting down game...");
			endGame(false);
			opponent.endGame(false);
			return;
		}
		opponent.printWaitingMessage();
		try {
			makeMove();
		} catch (IOException e) {
			System.err.println("[GAME " + board.getId() + "]: Unable to get input from client. Shutting down game...");
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
	
	private void printWaitingMessage() {
		socketOut.println("Waiting for opponent to make a move.");
	}

	private void endGame(boolean error) {
		if(!error) {
			socketOut.println("Game is over.");
			socketOut.println("SERVER: game over");
		}
		else {
			socketOut.println("Unable to get input from opponent. Shutting down game...");
			socketOut.println("SERVER: game over");
		}
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
		String incomingBoard;

		socketOut.println("It's your move.");
		socketOut.println("SERVER: move poll");
		incomingBoard = socketIn.readLine();
		board.updateBoard(incomingBoard);
		opponent.sendBoard();
		System.out.println("[GAME " + board.getId() + "]: Move successful, polling opponent.");
		socketOut.println("Move successful.");
	}

	private void sendBoard() {
		socketOut.println("SERVER: board update");
		socketOut.println(board.getBoardString());
	}
	/**
	 * Sets the opponent of the player.
	 * @param opponent	The opponent to set.
	 */
	public void setOpponent(Player opponent) {
		this.opponent = opponent;
		socketOut.println("Your opponent this game is: " + this.opponent.name + ".");
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
				socketOut.println("You win!");
			else
				socketOut.println("You lost to: " + opponent.name + ".");
			return false;
		}
		else if(board.oWins()) {
			if(mark == 'O')
				socketOut.println("You win!");
			else
				socketOut.println("You lost to: " + opponent.name + ".");
			return false;
		}
		else if(board.isFull()) {
			socketOut.println("Board is full. It's a tie!");
			return false;
		}
		return true;
	}
}

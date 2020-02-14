package Ex5Files.server.model;

import java.io.*;

//STUDENTS SHOULD ADD CLASS COMMENTS, METHOD COMMENTS, FIELD COMMENTS 


public class Game implements Constants, Runnable {

	private Board theBoard;
	private Referee theRef;

	private PrintWriter socketOut, socketOut2;
	private BufferedReader socketIn, socketIn2;
	
	private String[] playerNames;

	/**
	 * TODO: Edit javadoc
	 * Default constructor for class Game. initializes a blank game board.
	 */
	public Game(BufferedReader socketIn, PrintWriter socketOut, BufferedReader socketIn2, PrintWriter socketOut2, int id, String[] playerNames) {
		this.socketIn = socketIn;
		this.socketOut = socketOut;
		this.socketIn2 = socketIn2;
		this.socketOut2 = socketOut2;
		theBoard  = new Board(id);
		this.playerNames = playerNames;
	}

	/**
	 * Assigns a referee to the game, and starts the game.
	 * @param r The Referee to be assigned.
	 * @throws IOException When game could not poll for human input.
	 */
	public void appointReferee(Referee r) {
		theRef = r;
		theRef.runTheGame();
	}

	@Override
	public void run() {
		//Establish Referee and Player objects
		Referee theRef;
		Player xPlayer, oPlayer;
		printWelcomeMessage();

		xPlayer = new Player(playerNames[0], LETTER_X, socketIn, socketOut);
		xPlayer.setBoard(theBoard);

		oPlayer = new Player(playerNames[1], LETTER_O, socketIn2, socketOut2);
		oPlayer.setBoard(theBoard);

		theRef = new Referee();
		theRef.setoPlayer(oPlayer);
		theRef.setxPlayer(xPlayer);


		appointReferee(theRef);


	}

	//HELPER METHODS
	/**
	 * Prints a welcome message and displays the initial board.
	 */
	private void printWelcomeMessage() {
//		System.out.println("Welcome to tic-tac-toe. Here is your board:");
		socketOut.println("Welcome to tic-tac-toe!");
		socketOut2.println("Welcome to tic-tac-toe!");
	}
}

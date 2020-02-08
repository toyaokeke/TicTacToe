package Ex4Files.ticTacToe_Server;

import java.io.*;

//STUDENTS SHOULD ADD CLASS COMMENTS, METHOD COMMENTS, FIELD COMMENTS 


public class Game implements Constants, Runnable {

	private Board theBoard;
	private Referee theRef;

	private PrintWriter socketOut, socketOut2;
	private BufferedReader socketIn, socketIn2;

	/**
	 * TODO: Edit javadoc
	 * Default constructor for class Game. initializes a blank game board.
	 */
	public Game(BufferedReader socketIn, PrintWriter socketOut, BufferedReader socketIn2, PrintWriter socketOut2) {
		this.socketIn = socketIn;
		this.socketOut = socketOut;
		this.socketIn2 = socketIn2;
		this.socketOut2 = socketOut2;
		theBoard  = new Board();
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
		socketOut2.println("Waiting for opponent to enter name...");
		//Get details for player X, set that player to the game
		socketOut.println("Please enter your name: ");
		String name= null;
		try {
			name = socketIn.readLine();
			while (name == null) {
				socketOut.print("Please try again: ");
				name = socketIn.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		xPlayer = new Player(name, LETTER_X, socketIn, socketOut);
		xPlayer.setBoard(theBoard);
		socketOut.println("Waiting for opponent to enter name...");

		//Get details for player O, set that player to the game
		socketOut2.println("Please enter your name: ");
		try {
			name = socketIn2.readLine();
			while (name == null) {
				socketOut2.print("Please try again: ");
				name = socketIn2.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		oPlayer = new Player(name, LETTER_O, socketIn2, socketOut2);
		oPlayer.setBoard(theBoard);

		socketOut.println("Your opponent this time is: " + oPlayer.getName() + ".");
		socketOut2.println("Your opponent this time is: " + xPlayer.getName() + ".");	
		//Assign a referee to the game and to the players, then start the game
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

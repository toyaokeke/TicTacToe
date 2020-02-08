package server.model;

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

	//	public static void main(String[] args) throws IOException {
	//		//Establish Referee and Player objects
	//		Referee theRef;
	//		Player xPlayer, oPlayer;
	//		BufferedReader stdin;
	//		//Start a new game with a blank board
	//		Game theGame = new Game();
	//		stdin = new BufferedReader(new InputStreamReader(System.in));
	//
	//		//Get details for player X, set that player to the game
	//		System.out.print("\nPlease enter the name of the \'X\' player: ");
	//		String name= stdin.readLine();
	//		while (name == null) {
	//			System.out.print("Please try again: ");
	//			name = stdin.readLine();
	//		}
	//
	//		xPlayer = new Player(name, LETTER_X);
	//		xPlayer.setBoard(theGame.theBoard);
	//
	//		//Get details for player O, set that player to the game
	//		System.out.print("\nPlease enter the name of the \'O\' player: ");
	//		name = stdin.readLine();
	//		while (name == null) {
	//			System.out.print("Please try again: ");
	//			name = stdin.readLine();
	//		}
	//
	//		oPlayer = new Player(name, LETTER_O);
	//		oPlayer.setBoard(theGame.theBoard);
	//
	//		//Assign a referee to the game and to the players, then start the game
	//		theRef = new Referee();
	//		theRef.setBoard(theGame.theBoard);
	//		theRef.setoPlayer(oPlayer);
	//		theRef.setxPlayer(xPlayer);
	//
	//        theGame.appointReferee(theRef);
	//	}


	@Override
	public void run() {
		//Establish Referee and Player objects
		Referee theRef;
		Player xPlayer, oPlayer;
		printWelcomeMessage();

		xPlayer = new Player(LETTER_X, socketIn, socketOut);
		xPlayer.setBoard(theBoard);

		oPlayer = new Player(LETTER_O, socketIn2, socketOut2);
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

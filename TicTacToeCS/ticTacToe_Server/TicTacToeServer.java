package ticTacToe_Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TicTacToeServer {
	private ServerSocket serverSocket;
	private Socket socket, socket2;
	private PrintWriter socketOut, socketOut2;
	private BufferedReader socketIn, socketIn2;
	private final ExecutorService pool;
	int port;

	/**
	 * Creates a new server at a specified port and creates a CachedThreadPool
	 * @param port The port number on which game communications are to be setup.
	 */
	public TicTacToeServer(int port){
		pool = Executors.newCachedThreadPool();
		try{
			this.port = port;
			serverSocket = new ServerSocket(this.port);
			System.out.println("Server started.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Accepts connections to the server and starts a game once at least two players have connected
	 */
	public void acceptConnections(){
		try {
			while(true) {
				socket = serverSocket.accept();
				System.out.println("A player has connected.");
				System.out.println("Waiting for a second player to join...");
				socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				socketOut = new PrintWriter(socket.getOutputStream(), true);
				socketOut.println("Connected to server as the first player, waiting for second player to join...");
				
				socket2 = serverSocket.accept();
				System.out.println("A second player has connected.");
				System.out.println("Starting game...");
				socketIn2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
				socketOut2 = new PrintWriter(socket2.getOutputStream(), true);
				socketOut.println("Second player joined, starting game...");
				socketOut2.println("Connected to server as the second player, starting game...");
				
				startGame();
			}
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * Creates a new game for two players and add game to thread pool
	 */
	private void startGame() {
		Game game = new Game(socketIn, socketOut, socketIn2, socketOut2);
		pool.execute(game);
	}

	public static void main(String[] args) {
		TicTacToeServer demoServer = new TicTacToeServer(9999);
		demoServer.acceptConnections();
	}
}

package server.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import server.model.Game;

public class TicTacToeServer {
	private ServerSocket serverSocket;
	private Socket socket, socket2;
	private PrintWriter socketOut, socketOut2;
	private BufferedReader socketIn, socketIn2;
	private final ExecutorService pool;
	int port;

	public TicTacToeServer(int port) {
		pool = Executors.newCachedThreadPool();
		try {
			this.port = port;
			serverSocket = new ServerSocket(this.port);
			System.out.println("Server started.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Accepts player connections. Once two players are connected they are assigned
	 * a game ID before the game is executed. If there is an error connecting a
	 * player the cancelled and the connection to the server is closed
	 */
	public void acceptConnections() {
		int gameID = 1;
		String[] playerNames = new String[2];
		boolean playerConnected;
		while (true) {
			playerConnected = false;
			try {
				socket = serverSocket.accept();
				System.out.println("[GAME " + gameID + "]: A player has connected.");
				System.out.println("[GAME " + gameID + "]: Waiting for a second player to join...");
				socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				socketOut = new PrintWriter(socket.getOutputStream(), true);
				socketOut.println("SERVER: mark assign X");
				socketOut.println("SERVER: name poll");
				playerNames[0] = socketIn.readLine();
				System.out.println("[GAME " + gameID + "]: Player 1 name: " + playerNames[0] + ".");
				socketOut.println("Connected to server as the first player, waiting for second player to join...");

				playerConnected = true;

				socket2 = serverSocket.accept();
				System.out.println("[GAME " + gameID + "]: A second player has connected.");
				socketIn2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
				socketOut2 = new PrintWriter(socket2.getOutputStream(), true);
				socketOut2.println("SERVER: mark assign O");
				socketOut2.println("SERVER: name poll");
				playerNames[1] = socketIn2.readLine();
				System.out.println("[GAME " + gameID + "]: Player 2 name: " + playerNames[1] + ".");
				System.out.println("[GAME " + gameID + "]: Starting game...");
				socketOut.println("Second player joined, starting game...");
				socketOut2.println("Connected to server as the second player, starting game...");

				startGame(playerNames, gameID);
				gameID++;
			} catch (IOException e) {
				System.err.println("[GAME " + gameID + "]: Unable to connect to client. Cancelling game start.");
				e.printStackTrace();
				if (playerConnected) {
					socketOut.println("An unexpected error has occurred...");
					socketOut.println("SERVER: game over");
					try {
						socketIn.close();
						socketOut.close();
					} catch (IOException e1) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * Starts a game in the threadpool using the two player names and game ID
	 * 
	 * @param names names of player X and O
	 * @param id    game ID
	 */
	private void startGame(String[] names, int id) {
		Game game = new Game(socketIn, socketOut, socketIn2, socketOut2, id, names);
		pool.execute(game);
	}

	public static void main(String[] args) {
		TicTacToeServer demoServer = new TicTacToeServer(9999);
		demoServer.acceptConnections();
	}
}

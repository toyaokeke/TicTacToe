package Ex5Files.client.model;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.BufferedReader;

import Ex5Files.client.control.GameListener;

public class TicTacToeClient {
	private Socket socket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	private GameListener controller;

	/**
	 * Parameter constructor for the client model. Sets up the input and output streams for connection
	 * to the server.
	 * @param serverName The ip address of the server.
	 * @param port The port number for which the game communications are set up.
	 * @param controller The controller class which coordinates between the game model and client view.
	 */
	public TicTacToeClient(String serverName, int port, GameListener controller){
		try {
			socket = new Socket(serverName, port);
			// socket input stream
			socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			socketOut = new PrintWriter(socket.getOutputStream(), true);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.controller = controller;
	}
	
	/**
	 * A perpetually running method of the client model which receives communications from the server
	 * notifies the controller if user input is required.
	 */
	public void runningState() {
		if(socket == null) {
			System.out.println("Connection was not established successfully. Shutting down...");
			return;
		}
		
		while(true) {
			String incomingLine;
			try {
				incomingLine = socketIn.readLine();
			} catch (IOException e) {
				controller.appendMessage("An unexpected exception occurred. Shutting down...");
				controller.appendMessage("Please close game window.");
				System.err.println("An unexpected exception occurred. Shutting down...");
				e.printStackTrace();
				break;
			}
			
			if(incomingLine.startsWith("SERVER:")) {
				if(incomingLine.contains("move poll")) {
					controller.unlockGameBoard();
					while(controller.isActive()) {
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					socketOut.println(controller.getBoardString());
				}
				else if(incomingLine.contains("mark assign")) {
					char[] charArr = incomingLine.toCharArray();
					controller.setPlayerMark(charArr[charArr.length - 1]);
				}
				else if(incomingLine.contains("game over")) {
					controller.appendMessage("Please close game window");
					try {
						socketIn.close();
						socketOut.close();
					} catch(IOException e) {
						System.err.println("Could not close socket streams properly.");
						e.printStackTrace();
					}
					break;
				}
				else if(incomingLine.contains("board update")) {
					try {
						incomingLine = socketIn.readLine();
					} catch (IOException e) {
						System.err.println("An unexpected exception occurred. Shutting down...");
						e.printStackTrace();
						break;
					}
					controller.updateBoard(incomingLine);
				}
				else if(incomingLine.contains("name poll"))
					socketOut.println(controller.pollPlayerName());
			}
			else {
				System.out.println(incomingLine);
				controller.appendMessage(incomingLine);
			}
		}
	}
}

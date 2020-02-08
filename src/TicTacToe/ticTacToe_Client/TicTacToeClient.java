package Ex4Files.ticTacToe_Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TicTacToeClient {
	private Socket socket;
	private PrintWriter socketOut;
	private BufferedReader socketIn, stdIn;

	public TicTacToeClient(String serverName, int port){
		try {
			socket = new Socket(serverName, port);
			// keyboard input stream
			stdIn = new BufferedReader(new InputStreamReader(System.in));
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

	}
	
	public void runningState() {
		if(socket == null) {
			System.out.println("Connection was not established successfully. Shutting down...");
			return;
		}
		
		while(true) {
			String incomingLine;
			String userInput;
			try {
				incomingLine = socketIn.readLine();
			} catch (IOException e) {
				System.err.println("An unexpected exception occurred. Shutting down...");
				e.printStackTrace();
				break;
			}
			System.out.println(incomingLine);
			
			if(incomingLine.startsWith("Please enter") || 
					incomingLine.contains("try again")) {
				try {
					userInput = stdIn.readLine();
					socketOut.println(userInput);
				} catch (IOException e) {
					System.err.println("An unexpected exception occurred. Shutting down...");
					e.printStackTrace();
					break;
				}
			}
			
			if(incomingLine.contains("Shutting down game")) {
				try {
					stdIn.close();
					socketIn.close();
					socketOut.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}

	public static void main(String[] args) {
//		To run a Tic Tac Toe game on separate computers, please enter the IPv4 address of the server
		TicTacToeClient demoClient = new TicTacToeClient("localhost", 9999);
		demoClient.runningState();
	}
}

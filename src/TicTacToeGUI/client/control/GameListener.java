package Ex5Files.client.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Ex5Files.client.model.*;
import Ex5Files.client.view.GameWindow;

public class GameListener implements ActionListener, Constants {

	GameWindow gameWindow;
	boolean playerActive;
	
	public void setGameWindow(GameWindow gameWindow) {
		this.gameWindow = gameWindow;
	}
	@Override
	/**
	 * Responds to an ActionEvent triggered by pressing a button in the active player's window.
	 */
	public void actionPerformed(ActionEvent e) {
		if(playerActive) {
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					if(e.getSource() == gameWindow.getGameButtons()[i][j]) {
//						System.out.println(i + " " + j + gameWindow.player.getMark());
						if(gameWindow.getGameButtons()[i][j].getText().equals(SPACE_CHAR)) {
							gameWindow.setMark(i,j);
							lockGameBoard();
						}
//						player.makeMove(i, j);
					}
				}
			}	
		}
	}
	
	public void unlockGameBoard() {
		playerActive = true;
	}
	
	public void lockGameBoard() {
		playerActive = false;
	}

	public boolean isActive() {
		return playerActive;
	}
	
	public String getBoardString() {
		String printString = "";
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				printString += gameWindow.getGameButtons()[i][j].getText();
			}
		}
		return printString;
	}
	
	public void setPlayerMark(char charArr) {
		gameWindow.setPlayerChar(charArr);
	}
	
	public static void main(String[] args) {
//		To run a Tic Tac Toe game on separate computers, please enter the IPv4 address of the server
		GameListener gameListener = new GameListener();
		GameWindow gameWindow = new GameWindow(gameListener);
		gameListener.setGameWindow(gameWindow);
		
		TicTacToeClient demoClient = new TicTacToeClient("localhost", 9999, gameListener);
		demoClient.runningState();
	}
	
	public void appendMessage(String incomingLine) {
		gameWindow.appendTextArea(incomingLine);
	}
	public void updateBoard(String incomingLine) {
		char[] incomingBoard = incomingLine.toCharArray();
		gameWindow.updateBoard(incomingBoard);
	}
}
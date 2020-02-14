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
	
	/**
	 * Makes the game board responsive to a player's click action.
	 */
	public void unlockGameBoard() {
		playerActive = true;
	}
	
	/**
	 * Makes the game board unresponsive to a player's click action.
	 */
	public void lockGameBoard() {
		playerActive = false;
	}

	/**
	 * Checks if a player's board is listening.
	 * @return True if the board is listening. 
	 */
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
	
	/**
	 * Tells the GUI to update its player character portion.
	 * @param charArr The player character to set.
	 */
	public void setPlayerMark(char charArr) {
		gameWindow.setPlayerChar(charArr);
	}
	
	/**
	 * Appends a message to the GUI.
	 * @param incomingLine The new message to append.
	 */
	public void appendMessage(String incomingLine) {
		gameWindow.appendTextArea(incomingLine);
	}
	
	/**
	 * Updates the GUI board.
	 * @param incomingLine The board conditions flattened into a String.
	 */
	public void updateBoard(String incomingLine) {
		char[] incomingBoard = incomingLine.toCharArray();
		gameWindow.updateBoard(incomingBoard);
	}
	
	/**
	 * Tells the GUI to display a dialog box for the user to enter in the player name.
	 * @return The player name.
	 */
	public String pollPlayerName() {
		return gameWindow.pollPlayerNameDialog();
	}
	
	/**
	 * Tells the GUI to display a dialog box for the user to enter in the server address.
	 * @return The server address.
	 */
	private String getIP() {
		return gameWindow.showIPDialog();
	}
	
	public static void main(String[] args) {
		GameListener gameListener = new GameListener();
		GameWindow gameWindow = new GameWindow(gameListener);
		gameListener.setGameWindow(gameWindow);
		
		String serverAddress = gameListener.getIP();
		TicTacToeClient demoClient = new TicTacToeClient(serverAddress, 9999, gameListener);
		demoClient.runningState();
	}
}
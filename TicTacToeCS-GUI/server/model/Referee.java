package server.model;

import java.io.IOException;

public class Referee {

	private Player xPlayer, oPlayer;

	////////////////////////////////////////////////////////////////
	// INSTANCE METHODS

	/**
	 * Sets the 'O' Player to the referee.
	 * 
	 * @param oPlayer The player to set for 'O'.
	 */
	public void setoPlayer(Player oPlayer) {
		this.oPlayer = oPlayer;
	}

	/**
	 * Sets the 'X' Player to the referee.
	 * 
	 * @param xPlayer The player to set for 'X'.
	 */
	public void setxPlayer(Player xPlayer) {
		this.xPlayer = xPlayer;
	}

	/**
	 * Start the game by assigning the opponents of each player to one another,
	 * displaying the board, and polling the first player for first move.
	 * 
	 * @throws IOException When game could not poll for human input.
	 */
	public void runTheGame() {
		setOpponents();
		xPlayer.play();
	}

	////////////////////////////////////////////////////////////////
	// HELPER METHODS

	/**
	 * Sets the opponents of each player to each other.
	 */
	private void setOpponents() {
		xPlayer.setOpponent(oPlayer);
		oPlayer.setOpponent(xPlayer);
	}
}

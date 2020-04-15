package Ex5Files.client.view;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

import java.awt.*;

import Ex5Files.client.control.GameListener;
import Ex5Files.client.model.Constants;



public class GameWindow extends JFrame implements Constants {
	private JButton[][] gameButtons;
	JTextArea messageArea;
	JTextField playerChar;
	JTextField playerName;
	GameListener gameListener;

	/**
	 * Sets up the game window for both a client application.
	 */
	public GameWindow(GameListener gameListener) {
		super("Game Window");
		this.gameListener = gameListener;
		//Buttons for the game
		JPanel gamePanel = new JPanel();
		gamePanel.setLayout(new GridLayout(3,3));
		gameButtons = new JButton[3][3];
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				gameButtons[i][j] = new JButton();
				gameButtons[i][j].setPreferredSize(new Dimension(50,50));
				gameButtons[i][j].addActionListener(this.gameListener);
				gameButtons[i][j].setText(SPACE_CHAR);
				gamePanel.add(gameButtons[i][j]);
			}
		}
		add(gamePanel, BorderLayout.WEST);

		//The message box
		JPanel messagePanel = new JPanel();
		messagePanel.setLayout(new BorderLayout());
		messagePanel.add(new JLabel("Message Window: "), BorderLayout.NORTH);
		messageArea = new JTextArea();
		messageArea.setEditable(false);
		DefaultCaret caret = (DefaultCaret)messageArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		JScrollPane scrollMessage = new JScrollPane(messageArea);
		scrollMessage.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollMessage.setPreferredSize(new Dimension(200,100));
		messagePanel.add(scrollMessage, BorderLayout.CENTER);
		add(messagePanel, BorderLayout.CENTER);

		//Player information
		JPanel playerPanel = new JPanel();
		playerPanel.setLayout(new FlowLayout());
		playerPanel.add(new JLabel("Player: "));
		playerChar = new JTextField(3);
		playerChar.setEditable(false);
		playerPanel.add(playerChar);
		playerPanel.add(new JLabel("User Name: "));
		playerName = new JTextField(30);
		playerName.setEditable(false);
		playerPanel.add(playerName);
		playerPanel.setAlignmentY(LEFT_ALIGNMENT);

		add(playerPanel, BorderLayout.SOUTH);
		//Closing a player's window ends the game and closes the other player's window too
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * @return The game buttons.
	 */
	public JButton[][] getGameButtons() {
		return gameButtons;
	}

	/**
	 * @param gameButtons The game buttons to set.
	 */
	public void setGameButtons(JButton[][] gameButtons) {
		this.gameButtons = gameButtons;
	}

	/**
	 * Sets a player mark at the specified row and column.
	 * @param i The row for the mark.
	 * @param j The column for the mark.
	 */
	public void setMark(int i, int j) {
		gameButtons[i][j].setText(playerChar.getText());
	}


	/**
	 * Sets a player as player X or player O.
	 * @param charArr The player character to set.
	 */
	public void setPlayerChar(char charArr) {
		playerChar.setText(Character.toString(charArr));
	}

	/**
	 * Appends a message to the bottom of the message area.
	 * @param incomingLine The message to append.
	 */
	public void appendTextArea(String incomingLine) {
		messageArea.append(incomingLine + "\n");
	}

	/**
	 * Updates the board.
	 * @param incomingBoard The current board state represented in a flat char array.
	 */
	public void updateBoard(char[] incomingBoard) {
		if(incomingBoard.length != 9)
			return;

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				gameButtons[i][j].setText(Character.toString(incomingBoard[i * 3 + j]));
			}
		}
	}

	/**
	 * Displays a dialog for the player to enter their name.
	 * @return The player's name.
	 */
	public String pollPlayerNameDialog() {
		String name = JOptionPane.showInputDialog("Please enter your name:");
		if(name == null  || name.equals(""))
			name = "Player " + playerChar.getText();
		setPlayerName(name);
		return name;
	}

	/**
	 * Displays a dialog for the player to enter the server address (IP address).
	 * Will default to localhost if nothing is entered.
	 * @return The IP address of the server.
	 */
	public String showIPDialog() {
		String ipAddress = JOptionPane.showInputDialog("Please enter the server IP address, or leave it blank for 'localhost'.");
		if(ipAddress == null || ipAddress.equals(""))
			ipAddress = "localhost";

		return ipAddress;
	}

	/**
	 * Sets the player name in the textbox at the bottom right of the GUI window.
	 * @param name The player name to set.
	 */
	private void setPlayerName(String name) {
		playerName.setText(name);
	}
}

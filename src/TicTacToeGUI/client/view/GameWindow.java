package Ex5Files.client.view;

import javax.swing.*;

import Ex5Files.client.control.GameListener;
import Ex5Files.client.model.Constants;

import java.awt.*;
import java.awt.event.ActionListener;


public class GameWindow extends JFrame implements Constants {
	private JButton[][] gameButtons;
	JTextArea messageArea;
	JTextField playerChar;
	JTextField playerName;
	GameListener gameListener;
	
	/**
	 * Sets up the game window for both x and o players.
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
		playerName.setEditable(true);
		playerPanel.add(playerName);
		playerPanel.setAlignmentY(LEFT_ALIGNMENT);
		
		add(playerPanel, BorderLayout.SOUTH);
		//Closing a player's window ends the game and closes the other player's window too
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		pack();
	}


	public JButton[][] getGameButtons() {
		return gameButtons;
	}


	public void setGameButtons(JButton[][] gameButtons) {
		this.gameButtons = gameButtons;
	}

	public void setMark(int i, int j) {
		gameButtons[i][j].setText(playerChar.getText());
	}


	public void setPlayerChar(char charArr) {
		playerChar.setText(Character.toString(charArr));
	}

	public void appendTextArea(String incomingLine) {
		messageArea.append(incomingLine + "\n");
	}


	public void updateBoard(char[] incomingBoard) {
		if(incomingBoard.length != 9)
			return;
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				gameButtons[i][j].setText(Character.toString(incomingBoard[i * 3 + j]));
			}
		}
	}
}

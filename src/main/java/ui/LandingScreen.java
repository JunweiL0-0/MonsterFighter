package main.java.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import main.java.controller.GameController;


/**
 * A landingScreen for user to select difficulty and enter their player name.
 */
public class LandingScreen {
	private final GameController gc;
	private String playerName;
	private String difficulty;
	// swing components
	private JFrame landingFrame;
	private JButton confirmButton;
	private JLabel hintMessageLabel;


	/**
	 * LandingScreen's constructor. Launch the application.
	 *
	 * @param gc gameController
	 * @see GameController
	 */
	public LandingScreen (GameController gc) {
		this.gc = gc;

		initialize();
		show(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// *******************************************************************
		// *                       MONSTER FIGHTER                           *
		// *                      Enter Player Name                          *
		// *              ----------------------------------                 *
		// *              |       Enter Player Name        |                 *
		// *              ----------------------------------                 *
		// *                   Choose your difficulty!                       *
		// *                  Easy(Button) Hard(Button)                      *
		// *               Hint Message (Validate Player Name)               *
		// *                       ----------------                          *
		// *                       |  START GAME  |                          *
		// *                       ----------------                          *
		// *******************************************************************
		this.landingFrame = getLandingFrame();
		// MONSTER FIGHTER
		this.landingFrame.getContentPane().add(getTitleLabel());
		// Label
		this.landingFrame.getContentPane().add(getEnterNameLabel());
		// Enter Player Name
		this.landingFrame.getContentPane().add(getUserNameTextField());
		// Choose your difficulty
		this.landingFrame.getContentPane().add(getDiffLabel());
		// Easy, Hard Button
		JRadioButton easyButton = getEasyButton();
		JRadioButton hardButton = getHardButton();
		this.landingFrame.getContentPane().add(easyButton);
		this.landingFrame.getContentPane().add(hardButton);
		// Add Buttons to a buttonGroup to make a single selection
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(easyButton);
		buttonGroup.add(hardButton);
		// In order to enable/disable the confirmButton. We store the confirmButton into the variable.
		this.confirmButton = getConfirmButton();
		this.landingFrame.getContentPane().add(this.confirmButton);
		// Store the hintMessageLabel, so that we can change the label dynamically.
		this.hintMessageLabel = getHintMessageLabel();
		this.landingFrame.getContentPane().add(hintMessageLabel);

		// Default difficulty
		easyButton.setSelected(true);
		this.difficulty = easyButton.getText();
		// Bind the enter key to "START GAME" button
		this.landingFrame.getRootPane().setDefaultButton(confirmButton);
	}

	/**
	 * A function used to show and hide the landingScreen.
	 *
	 * @param visible a boolean value.
	 */
	public void show(Boolean visible) {
		this.landingFrame.setVisible(visible);
	}

	/**
	 * Enable the confirmButton on the landingScreen.
	 */
	private void enableConfirmButton() {
		this.confirmButton.setEnabled(true);
	}

	/**
	 * Disable the confirmButton on the landingScreen.
	 */
	private void disableConfirmButton() {
		this.confirmButton.setEnabled(false);
	}

	/**
	 * Set the text for hintMessageLabel
	 */
	private void setHintMessage(String hint) {
		this.hintMessageLabel.setText(hint);
	}

	private void closeAndDestoryCurrentScreen() {
		show(false);
		this.landingFrame.dispose();
	}

	/*
	Swing Components go here
	 */
	/**
	 * Return a new landingFrame
	 *
	 * @return a new landingFrame
	 */
	private JFrame getLandingFrame() {
		JFrame newLandingFrame = new JFrame("MONSTER FIGHTER");
		newLandingFrame.getContentPane().setPreferredSize(new Dimension(800,500));
		newLandingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		newLandingFrame.getContentPane().setBackground(Color.BLACK);
		newLandingFrame.setLayout(null);
		newLandingFrame.setResizable(false);
		newLandingFrame.pack();
		newLandingFrame.setLocationRelativeTo(null);

		return newLandingFrame;
	}

	/**
	 * Return the label which contains the title and this label will be shown on the top.
	 *
	 * @return a label which contains the title.
	 */
	private JLabel getTitleLabel() {
		// create titleLabel
		JLabel title = new JLabel("MONSTER FIGHTER",SwingConstants.CENTER);
		title.setBounds(20,20,760,80);
		title.setFont(new Font("Serif", Font.PLAIN, 60));
		title.setBackground(Color.BLACK);
		title.setForeground(Color.WHITE);

		return title;
	}

	/**
	 * Return the enterName label.
	 *
	 * @return a enterName label.
	 */
	private JLabel getEnterNameLabel() {
		// create label
		JLabel diffLabel = new JLabel("Enter Player Name", SwingConstants.CENTER);
		diffLabel.setBounds(200,145,400,30);
		diffLabel.setFont(new Font("Serif", Font.PLAIN, 18));
		diffLabel.setBackground(Color.BLACK);
		diffLabel.setForeground(Color.WHITE);

		return diffLabel;
	}

	/**
	 * Return a textField for the user to type in their userName
	 *
	 * @return a textField for typing userName
	 */
	private JTextField getUserNameTextField() {
		// create a textField
		JTextField userNameTextField = new JTextField(15);
		// remove border
		userNameTextField.setBorder(BorderFactory.createEmptyBorder());
		userNameTextField.setText("Enter Player Name");
		userNameTextField.setBounds(200, 175, 400, 50);
		userNameTextField.setHorizontalAlignment(JTextField.CENTER);
		userNameTextField.setColumns(10);
		// add listeners
		addKeyListener(userNameTextField);
		addFocusListener(userNameTextField);

		return userNameTextField;
	}

	/**
	 * Return the difficulty label.
	 *
	 * @return a difficulty label.
	 */
	private JLabel getDiffLabel() {
		// create label
		JLabel diffLabel = new JLabel("Choose your difficulty", SwingConstants.CENTER);
		diffLabel.setBounds(200,255,400,30);
		diffLabel.setFont(new Font("Serif", Font.PLAIN, 18));
		diffLabel.setBackground(Color.BLACK);
		diffLabel.setForeground(Color.WHITE);

		return diffLabel;
	}

	/**
	 * Return a Button whose text is "Easy"
	 *
	 * @return a Button whose text is "Easy"
	 */
	private JRadioButton getEasyButton() {
		// create Button
		JRadioButton easyButton = new JRadioButton("Easy");
		easyButton.setFont(new Font("Serif", Font.PLAIN, 20));
		easyButton.setForeground(Color.BLACK);
		easyButton.setBounds(300, 285, 100, 30);
		easyButton.setHorizontalAlignment(SwingConstants.CENTER);
		// actionListener
		addDiffButtonListener(easyButton);

		return easyButton;
	}

	/**
	 * Return a Button whose text is "Hard"
	 *
	 * @return a Button whose text is "Hard"
	 */
	private JRadioButton getHardButton() {
		// create Button
		JRadioButton hardButton = new JRadioButton("Hard");
		hardButton.setFont(new Font("Serif", Font.PLAIN, 20));
		hardButton.setForeground(Color.BLACK);
		hardButton.setBounds(400, 285, 100, 30);
		hardButton.setHorizontalAlignment(SwingConstants.CENTER);
		// actionListener
		addDiffButtonListener(hardButton);

		return hardButton;
	}

	/**
	 * This function will guide the player to enter a valid playerName.
	 *
	 * @return a JLabel
	 */
	private JLabel getHintMessageLabel() {
		// create label
		JLabel diffLabel = new JLabel("", SwingConstants.LEFT);
		diffLabel.setBounds(200,350,400,30);
		diffLabel.setFont(new Font("Serif", Font.PLAIN, 13));
		diffLabel.setBackground(Color.BLACK);
		diffLabel.setForeground(Color.red);

		return diffLabel;
	}

	/**
	 * Return the confirmButton
	 *
	 * @return a confirmButton
	 */
	private JButton getConfirmButton() {
		// create button
		JButton newConfirmButton = new JButton();
		newConfirmButton.setBounds(200, 380, 400, 50);
		// remove border
		newConfirmButton.setBorder(BorderFactory.createEmptyBorder());
		// setText via html so that we can see the text even the button is being disabled
		newConfirmButton.setText("<html><p style=\"color:red;font-size:20\">START GAME</p></html>");
		// as the player haven't entered the player name. We disable the confirm button.
		newConfirmButton.setEnabled(false);
		// confirmButton listener
		addConfirmButtonListener(newConfirmButton);

		return newConfirmButton;
	}

	/*
	Listeners go here
	 */
	/**
	 * ConfirmButton's listener. The ChooseMonsterScreen should be shown once this button being pressed.
	 *
	 * @param button confirm button on the landing screen
	 */
	private void addConfirmButtonListener(JButton button) {
		button.addActionListener(actionEvent -> switchToChooseMonsterScreen());
	}

	/**
	 * PlayerTextField's listener. This function will validate the input.
	 * The input should only contain alphabets and its length is between 3 and 15.
	 *
	 * @param textField a textField for the player's name.
	 */
	private void addKeyListener(JTextField textField) {
		textField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(!(Character.isAlphabetic(c) || (c==KeyEvent.VK_BACK_SPACE) || c==KeyEvent.VK_DELETE )) {
					e.consume();
					setHintMessage("You kidding me?! Only alphabet characters!!");
				}
				if (textField.getText().length() >= 15 ) {
					e.consume();
				}
				if (3 <= textField.getText().length() && textField.getText().length() <= 15) {
					enableConfirmButton();
					playerName = textField.getText();
					setHintMessage("Well done~ Let's start the game");
				} else {
					disableConfirmButton();
					setHintMessage("Keep typing! Your name should longer than three");
				}
			}
		});
	}

	/**
	 * FocusListener for the textField. PlaceHolder.
	 *
	 * @param textField a textField for entering player name.
	 */
	private void addFocusListener(JTextField textField) {
		textField.setForeground(Color.GRAY);
		textField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (textField.getText().equals("Enter Player Name")) {
					textField.setText("");
					textField.setForeground(Color.BLACK);
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (textField.getText().isEmpty()) {
					textField.setForeground(Color.GRAY);
					textField.setText("Enter Player Name");
				}
			}
		});
	}

	/**
	 * ActionListener for RadioButton. This function will auto set the difficulty level if the button is being selected.
	 * @param button A JRadioButton used for selecting different difficulty.
	 */
	private void addDiffButtonListener(JRadioButton button) {
		button.addActionListener(actionEvent -> {
			if (button.isSelected()) {
				difficulty = button.getText();
			}
		});
	}

	/*
	Functions used to interact with gameController
	 */
	/**
	 *  Hide landingScreen and show chooseMonsterScreen.
	 */
	private void switchToChooseMonsterScreen() {
		gc.launchChooseMonsterScreen();
		closeAndDestoryCurrentScreen();
	}
}

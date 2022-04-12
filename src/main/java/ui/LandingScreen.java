package main.java.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import main.java.controller.GameController;



public class LandingScreen {
	private final GameController gc;
	private JFrame landingFrame;


	/**
	 * LandingScreen's constructor. Launch the application.
	 * @param gc gameController
	 * @see GameController
	 */
	public LandingScreen (GameController gc) {
		this.gc = gc;
		initialize();

		this.landingFrame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// *******************************************************************
		// *                       MONSTER FIGHTER                           *
		// *              ----------------------------------                 *
		// *              |       Enter Player Name        |                 *
		// *              ----------------------------------                 *
		// *     Easy(CheckBox)    Medium(CheckBox)     Hard(CheckBox)       *
		// *                   Choose your difficulty!                       *
		// *                       ----------------                          *
		// *                       |    Confirm   |                          *
		// *                       ----------------                          *
		// *******************************************************************
		this.landingFrame = getLandingFrame();
		// MONSTER FIGHTER
		this.landingFrame.getContentPane().add(getTitleLabel());
		// Enter Player Name
		this.landingFrame.getContentPane().add(getUserNameTextField());
		// Easy, Medium, Hard CheckBox
		this.landingFrame.getContentPane().add(getEasyCheckBox());
		this.landingFrame.getContentPane().add(getMediumCheckBox());
		this.landingFrame.getContentPane().add(getHardCheckBox());
		// Choose your difficulty
		this.landingFrame.getContentPane().add(getDiffLabel());
		// Confirm button
		this.landingFrame.getContentPane().add(getConfirmButton());

//		ButtonGroup buttonGroup = new ButtonGroup();
//		buttonGroup.add(getEasyCheckBox());
//		buttonGroup.add(getMediumCheckBox());
//		buttonGroup.add(getHardCheckBox());
//		addPlayerName();
	}

	/*
	Swing Components go here
	 */
	/**
	 * Return a new landingFrame
	 * @return a new landingFrame
	 */
	public JFrame getLandingFrame() {
		JFrame newLandingFrame = new JFrame();
		newLandingFrame.setBounds(100, 100, 800, 500);
		newLandingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		newLandingFrame.setLocationRelativeTo(null);
		newLandingFrame.getContentPane().setBackground(Color.black);
		newLandingFrame.setLayout(null);
		newLandingFrame.setResizable(false);

		return newLandingFrame;
	}

	/**
	 * Return the label which contains the title and this label will be shown on the top.
	 * @return a label which contains the title.
	 */
	private JLabel getTitleLabel() {
		// create titleLabel
		JLabel title = new JLabel("MONSTER FIGHTER",SwingConstants.CENTER);
		title.setBounds(20,20,760,120);
		title.setFont(new Font("Serif", Font.PLAIN, 60));
		title.setBackground(Color.black);
		title.setForeground(Color.white);

		return title;
	}

	/**
	 * Return a checkBox whose text is "Easy"
	 * @return a checkBox whose text is "Easy"
	 */
	private JCheckBox getEasyCheckBox() {
		// create checkBox
		JCheckBox easyCheckBox = new JCheckBox("Easy");
		easyCheckBox.setFont(new Font("Serif", Font.PLAIN, 20));
		easyCheckBox.setForeground(Color.white);
		easyCheckBox.setBounds(240, 200, 85, 40);

		return easyCheckBox;
	}

	/**
	 * Return a checkBox whose text is "Medium"
	 * @return a checkBox whose text is "Medium"
	 */
	private JCheckBox getMediumCheckBox() {
		// create checkBox
		JCheckBox mediumCheckBox = new JCheckBox("Medium");
		mediumCheckBox.setFont(new Font("Serif", Font.PLAIN, 20));
		mediumCheckBox.setForeground(Color.white);
		mediumCheckBox.setBounds(340, 200, 120, 40);

		return mediumCheckBox;
	}

	/**
	 * Return a checkBox whose text is "Hard"
	 * @return a checkBox whose text is "Hard"
	 */
	private JCheckBox getHardCheckBox() {
		// create checkBox
		JCheckBox hardCheckBox = new JCheckBox("Hard");
		hardCheckBox.setFont(new Font("Serif", Font.PLAIN, 20));
		hardCheckBox.setForeground(Color.white);
		hardCheckBox.setBounds(470, 200, 90, 40);

		return hardCheckBox;
	}

	/**
	 * Return the difficulty label.
	 * @return a difficulty label.
	 */
	private JLabel getDiffLabel() {
		// create label
		JLabel diffLabel = new JLabel("Choose your difficulty", SwingConstants.CENTER);
		diffLabel.setBounds(295,180,200,120);
		diffLabel.setFont(new Font("Serif", Font.PLAIN, 15));
		diffLabel.setBackground(Color.black);
		diffLabel.setForeground(Color.white);

		return diffLabel;
	}

	/**
	 * Return the confirmButton
	 * @return a confirmButton
	 */
	private JButton getConfirmButton() {
		// create button
		JButton newConfirmButton = new JButton("Confirm");
		addConfirmButtonListener(newConfirmButton);
		newConfirmButton.setBounds(350,265,100,25);

		return newConfirmButton;
	}

	/**
	 * Return a textField for the user to type in their userName
	 * @return a textField for typing userName
	 */
	private JTextField getUserNameTextField() {
		// create a textField
		JTextField userNameTextField = new JTextField(15);
		userNameTextField.setText("Enter Player Name");
		userNameTextField.setBounds(200, 150, 400, 50);
		userNameTextField.setHorizontalAlignment(JTextField.CENTER);
		userNameTextField.setColumns(10);
		// add listeners
		addKeyListener(userNameTextField);

		return userNameTextField;
	}

	/*
	Listeners go here
	 */
	/**
	 * ConfirmButton's listener. The ChooseMonsterScreen should be shown once this button being pressed.
	 * @param button confirm button on the landing screen
	 */
	private void addConfirmButtonListener(JButton button) {
		button.addActionListener(actionEvent -> gc.launchChooseMonsterScreen());
	}

	/**
	 * PlayerTextField's listener. This function will validate the input.
	 * The input should only contain alphabets and its length is between 3 and 15.
	 * @param textField a textField for the player's name.
	 */
	private void addKeyListener(JTextField textField) {
		textField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(!(Character.isAlphabetic(c) || (c==KeyEvent.VK_BACK_SPACE) || c==KeyEvent.VK_DELETE )) {
					e.consume();
				}
				if (textField.getText().length() >= 15 )
					e.consume();
			}
		});
	}

//	private ButtonGroup getDifficultyButton() {
//		ButtonGroup difficultyGroup = new ButtonGroup();
//		difficultyGroup.add(getEasyCheckBox());
//		difficultyGroup.add(getMediumCheckBox());
//		difficultyGroup.add(getHardCheckBox());
//
//		return difficultyGroup;
//	}
}

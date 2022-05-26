package main.java.view;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

import main.java.controller.GameController;
import main.java.model.Monster;


/**
 * ChooseMonsterScreen. This screen will be shown after the user clicked the "START GAME" button on the LandingScreen.
 */
public class ChooseMonsterScreen {
	private final GameController gc;
	private ArrayList<Monster> availableMonsters;
	public ArrayList<Monster> selectedMonsters;
	private final ArrayList<JToggleButton> monsterButtons;
	// swing components
	private JFrame chooseFrame;
	private JButton confirmButton;
	private JLabel hintMessageLabel;


	/**
	 * Constructor for ChooseMonsterScreen.
	 *
	 * @param gc gameController
	 */
	public ChooseMonsterScreen(GameController gc) {
		this.gc = gc;
		this.availableMonsters = new ArrayList<>();
		this.selectedMonsters = new ArrayList<>();
		this.monsterButtons = new ArrayList<>();
		// get initMonsters
		this.availableMonsters = getInitMonsters();

		initialize();
		show(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// *******************************************************************
		// *                       CHOOSE MONSTER(S)                         * --> Title
		// *     ---------- ---------- ---------- ---------- ----------      * -
		// *     |MONSTER1| |MONSTER2| |MONSTER3| |MONSTER4| |MONSTER5|      *  \
		// *     ---------- ---------- ---------- ---------- ----------      *   \
		// *     ---------- ---------- ---------- ---------- ----------      *   / ButtonDetailPanel
		// *     | DETAIL | | DETAIL | | DETAIL | | DETAIL | | DETAIL |      *  /
		// *     ---------- ---------- ---------- ---------- ----------      * -
		// *               Hint Message (Validate Selections)                *
		// *                       ----------------                          *
		// *                       |   CONFIRM    |                          *
		// *                       ----------------                          *
		// *******************************************************************
		// add components to chooseFrame
		this.chooseFrame = getChooseFrame();
		this.chooseFrame.getContentPane().add(getTitle());
		this.chooseFrame.getContentPane().add(getButtonDetailPanel());
		// store the hintMessageLabel into a variable
		this.hintMessageLabel = getHintMessageLabel();
		this.chooseFrame.getContentPane().add(this.hintMessageLabel);
		// store the confirmButton into a variable
		this.confirmButton = getConfirmButton();
		this.chooseFrame.getContentPane().add(this.confirmButton);
		// go back button
		this.chooseFrame.getContentPane().add(getGoBackButton());
		// bind the enter key to "CONFIRM" button
		this.chooseFrame.getRootPane().setDefaultButton(confirmButton);
	}

	/* JFrame */
	/**
	 * Return a new chooseFrame.
	 *
	 * @return return a new chooseFrame.
	 */
	private JFrame getChooseFrame() {
		JFrame chooseFrame = new JFrame("MONSTER FIGHTER");
		chooseFrame.getContentPane().setPreferredSize(new Dimension(800,500));
		chooseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		chooseFrame.getContentPane().setBackground(Color.BLACK);
		chooseFrame.setLayout(null);
		chooseFrame.setResizable(false);
		chooseFrame.pack();
		// place the frame in the center of the screen
		chooseFrame.setLocationRelativeTo(null);

		return chooseFrame;
	}

	/* JLabel */
	/**
	 * Return a title for ChooseFrame.
	 *
	 * @return a title
	 */
	private JLabel getTitle() {
		// create title
		JLabel title = new JLabel("CHOOSE MONSTER(S)",SwingConstants.CENTER);
		title.setBounds(20,20,760,120);
		title.setFont(new Font("Serif", Font.PLAIN, 60));
		title.setBackground(Color.BLACK);
		title.setForeground(Color.WHITE);
		// return title
		return title;
	}

	/**
	 * Create and return the hintMessageLabel. This label will be shown just above the confirmBtn.
	 *
	 * @return a hintMessageLabel(JLabel)
	 */
	private JLabel getHintMessageLabel() {
		// create label
		JLabel diffLabel = new JLabel("", SwingConstants.LEFT);
		diffLabel.setBounds(200,350,400,30);
		diffLabel.setFont(new Font("Serif", Font.PLAIN, 13));
		diffLabel.setBackground(Color.BLACK);
		diffLabel.setForeground(Color.red);
		// return label
		return diffLabel;
	}

	/* JPanel */
	/**
	 * Return a buttonPanel which contains monsterButtons and monsterDetails.
	 *
	 * @return a buttonPanel which contains monsterButtons and monsterDetails.
	 */
	private JPanel getButtonDetailPanel() {
		// create panel
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setBounds(20,140,760,160);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER,5,0));
		// add buttons to the panel one by one
		for (int indexInList=0; indexInList<this.availableMonsters.size(); indexInList++) {
			JToggleButton button = getMonsterButton(indexInList);
			this.monsterButtons.add(button);
			panel.add(button);
		}
		// add details to the panel one by one
		for (int indexInList=0; indexInList<this.availableMonsters.size(); indexInList++) {
			panel.add(getMonsterDetail(indexInList));
		}

		return panel;
	}

	/* JToggleButton */
	/**
	 * This function will generate a monsterButton for a specific monster in the availableMonsters list.
	 * The button will have the monster's name as its text.
	 *
	 * @param indexInList an index used to locate a specific monster in the availableMonsters list.
	 * @return a monsterButton
	 */
	private JToggleButton getMonsterButton(int indexInList) {
		Monster monster = this.availableMonsters.get(indexInList);
		JToggleButton button = new JToggleButton();
		// resize the image
		Image monsterImage = monster.getImageIcon().getImage().getScaledInstance(100, 60, java.awt.Image.SCALE_SMOOTH);
		// create the imageIcon
		ImageIcon imageIcon = new ImageIcon(monsterImage);
		button.setIcon(imageIcon);
		button.setFont(new Font("Arial", Font.PLAIN, 10));
		button.setPreferredSize(new Dimension(144,100));
		button.setFocusable(false);
		button.setBackground(Color.BLACK);
		button.setForeground(Color.BLACK);
		// Listeners
		button.addActionListener(actionEvent -> validateSelection());
		return button;
	}

	/**
	 * Generate a monster's detail(JTextArea) by using the indexInList.
	 *
	 * @param indexInList an index used to locate a specific monster in the availableMonsters list.
	 * @return a detail(JTextArea) for a single monster
	 */
	private JTextArea getMonsterDetail(int indexInList) {
		Monster monster = this.availableMonsters.get(indexInList);
		JTextArea detail = new JTextArea();
		detail.setPreferredSize(new Dimension(144,100));
		detail.setForeground(Color.WHITE);
		detail.setBackground(Color.BLACK);
		detail.setBorder(null);
		detail.setText(constructMonsterDetail(monster));
		detail.setEditable(false);
		return detail;
	}

	/* JButton */
	/**
	 * Return the confirmButton
	 *
	 * @return a confirmButton
	 */
	private JButton getConfirmButton() {
		// create button
		JButton newConfirmButton = new JButton();
		newConfirmButton.setFocusable(false);
		newConfirmButton.setBounds(200, 380, 400, 50);
		// setText via html so that we can see the text even the button is being disabled
		newConfirmButton.setText("<html><p style=\"color:red;font-size:20\">CONFIRM</p></html>");
		// as the player haven't selected anything. We disable the confirm button.
		newConfirmButton.setEnabled(false);
		// confirmButton listener
		addConfirmButtonListener(newConfirmButton);
		// return btn
		return newConfirmButton;
	}

	/**
	 * Create a goBackBtn so the player can go back to the LandingScreen
	 * 
	 * @return a goBackBtn(JButton)
	 */
	private JButton getGoBackButton() {
		JButton goBackBtn = new JButton();
		goBackBtn.setText("Back");
		goBackBtn.setFont(new Font("Serif", Font.PLAIN, 15));
		goBackBtn.setForeground(Color.WHITE);
		goBackBtn.setBounds(20,380,70,50);
		goBackBtn.setBackground(Color.BLACK);
		goBackBtn.setFocusable(false);
		goBackBtn.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
		// event listener
		addGoBackButtonListener(goBackBtn);
		// return btn
		return goBackBtn;
	}

	/* Listener */
	/**
	 * Add an actionListener to the confirmButton.
	 * 
	 * @param button a confirmBtn
	 */
	private void addConfirmButtonListener(JButton button) {
		button.addActionListener(actionEvent -> switchToMainScreen());
	}

	/**
	 * Add an actionListener to the goBackButton.
	 * 
	 * @param btn a goBackBtn
	 */
	private void addGoBackButtonListener(JButton btn) {
		btn.addActionListener(actionEvent -> switchToLandingScreen());
	}

	/*
	Functions used to interact with gameController
	 */
	/**
	 * Get initMonsters from the gameController.
	 * 
	 * @return iniMonsters
	 */
	private ArrayList<Monster> getInitMonsters() {
		return this.gc.getInitMonsters();
	}

	/**
	 * Launch MainScreen and close this screen(ChooseMonsterScreen).
	 */
	private void switchToMainScreen() {
		storeSelectedMonsters();
		this.gc.launchMainScreen();
		closeAndDestroyChooseScreen();
	}

	/**
	 * Launch LandingScreen and close this screen(ChooseMonsterScreen).
	 */
	private void switchToLandingScreen() {
		this.gc.launchLandingScreen();
		closeAndDestroyChooseScreen();
	}

	/* Other functions */
	/**
	 * Show/hide chooseFrame.
	 *
	 * @param visible a boolean value used to toggle the visible status of the chooseFrame.
	 */
	public void show(Boolean visible) {
		this.chooseFrame.setVisible(visible);
	}

	/**
	 * Close and destroy current chooseScreen.
	 */
	private void closeAndDestroyChooseScreen() {
		show(false);
		this.chooseFrame.dispose();
	}

	/**
	 * Disable the confirmBtn on the chooseFrame.
	 */
	private void disableConfirmButton() {
		this.confirmButton.setEnabled(false);
	}

	/**
	 * Enable the confirmBtn on the chooseFrame.
	 */
	private void enableConfirmButton() {
		this.confirmButton.setEnabled(true);
	}

	/**
	 * Validate the selection of monsters and change the hint message.
	 */
	private void validateSelection() {
		int counter = 0;
		// loop through the list and count selected button
		for (JToggleButton b: this.monsterButtons) {
			if (b.isSelected()) {
				counter++;
			}
		}
		// enable/disable confirmButton
		if (counter <= 0) {
			disableConfirmButton();
			setHintMessageLabel("Select a monster!");
		} else if (counter <= 3) {
			enableConfirmButton();
			setHintMessageLabel("Cool! You are ready to fight!");
		} else {
			disableConfirmButton();
			setHintMessageLabel("?? No, maximum three monsters!");
		}
	}

	/**
	 * Set the hint message. This function will be called by the "validateSelection" function.
	 *
	 * @see ChooseMonsterScreen::validateSelection()
	 * @param hint a string(hint)
	 */
	private void setHintMessageLabel(String hint) {
		this.hintMessageLabel.setText(hint);
	}

	/**
	 * Construct the monster information for the monsterBtn.
	 *
	 * @param monster a monster instance
	 * @return a string represents the detail of the monster and will be shown on the monsterBtn.
	 */
	private String constructMonsterDetail(Monster monster) {
		return String.format("Name: %s\nMaxHealth: %d\nDamage: %d\nLevel: %d\n",
				monster.getName(), monster.getMaxHealth(), monster.getDamage(), monster.getLevel());
	}

	/**
	 * Store the selected monsters to the gameController.
	 */
	private void storeSelectedMonsters() {
		for (int i=0; i<this.monsterButtons.size(); i++) {
			if (this.monsterButtons.get(i).isSelected()) {
				Monster monster = this.availableMonsters.get(i);
				this.gc.addMonsterToPlayerTeam(monster);
			}
		}
	}
}

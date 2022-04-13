package main.java.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

import main.java.controller.GameController;
import main.java.model.Generator;
import main.java.model.Monster;


/**
 * ChooseMonsterScreen. This screen will be shown after the user clicked the "Confirm" button on the LandingScreen.
 */
public class ChooseMonsterScreen {
	private JFrame chooseFrame;
	private ArrayList<Monster> availableMonsters;
	private ArrayList<Monster> selectedMonsters;
	private ArrayList<JToggleButton> monsterButtons;
	private GameController gc;


	/**
	 * Constructor for ChooseMonsterScreen.
	 *
	 * @param gc gameController
	 */
	public ChooseMonsterScreen(GameController gc) {
		this.availableMonsters = new ArrayList<>();
		this.selectedMonsters = new ArrayList<>();
		this.monsterButtons = new ArrayList<>();
		this.gc = gc;
		// get initMonsters
		this.availableMonsters = getInitMonsters();

		initialize();
		this.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// *******************************************************************
		// *                      CHOOSE YOUR MONSTER!                       * --> Title
		// *     ---------- ---------- ---------- ---------- ----------      * -
		// *     |MONSTER1| |MONSTER2| |MONSTER3| |MONSTER4| |MONSTER5|      *  \
		// *     ---------- ---------- ---------- ---------- ----------      *   \
		// *     ---------- ---------- ---------- ---------- ----------      *   / ButtonDetailPanel
		// *     | DETAIL | | DETAIL | | DETAIL | | DETAIL | | DETAIL |      *  /
		// *     ---------- ---------- ---------- ---------- ----------      * -
		// *                                                                 *
		// *                                                                 *
		// *                                                                 *
		// *                                                                 *
		// *                                                                 *
		// *******************************************************************
		// add components to chooseFrame
		this.chooseFrame = getChooseFrame();
		this.chooseFrame.getContentPane().add(getTitle());
		this.chooseFrame.getContentPane().add(getButtonDetailPanel());
	}

//
//	private boolean isMaxSelections() {
//		return this.availableMonsters.size() == 3;
//	}
//
//	private void addMonsterToList(int selectedMonsterId) {
//		this.selectedMonsters.add(this.availableMonsters.get(selectedMonsterId));
//	}

	public void setVisible(Boolean visible) {
		this.chooseFrame.setVisible(visible);
	}

	/*
	Swing components
	 */
	/**
	 * Return a new chooseFrame
	 *
	 * @return return a new chooseFrame
	 */
	private JFrame getChooseFrame() {
		JFrame chooseFrame = new JFrame();
		chooseFrame.setBounds(100, 100, 800, 500);
		chooseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		chooseFrame.setLocationRelativeTo(null);
		chooseFrame.getContentPane().setBackground(Color.black);
		chooseFrame.setLayout(null);
		chooseFrame.setResizable(false);

		return chooseFrame;
	}

	/**
	 * Return a title for ChooseFrame
	 *
	 * @return a title
	 */
	private JLabel getTitle() {
		JLabel title = new JLabel("CHOOSE YOUR MONSTER!",SwingConstants.CENTER);
		title.setBounds(20,20,760,120);
		title.setFont(new Font("Serif", Font.PLAIN, 60));
		title.setBackground(Color.black);
		title.setForeground(Color.white);
		return title;
	}

	/**
	 * Return a buttonPanel which contains monsterButtons and monsterDetails.
	 *
	 * @return a buttonPanel which contains monsterButtons and monsterDetails.
	 */
	private JPanel getButtonDetailPanel() {
		// create panel
		JPanel panel = new JPanel();
		panel.setBackground(Color.black);
		panel.setBounds(20,150,760,250);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER,5,0));
		// add buttons to the panel
		for (int indexInList=0; indexInList<this.availableMonsters.size(); indexInList++) {
			JToggleButton button = getMonsterButton(indexInList);
			monsterButtons.add(button);
			panel.add(button);
		}
		// add details to the panel
		for (int indexInList=0; indexInList<this.availableMonsters.size(); indexInList++) {
			panel.add(getMonsterDetail(indexInList));
		}

		return panel;
	}

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
		button.setText(monster.getName());
		button.setFont(new Font("Arial", Font.PLAIN, 10));
		button.setPreferredSize(new Dimension(144,100));
		button.addActionListener(actionEvent -> {
			if (button.isSelected()) {
				System.out.println(button.getName());
			}
		});
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
//		detail.setBounds(0,370,800,150);
		detail.setForeground(Color.white);
		detail.setBackground(Color.black);
		detail.setBorder(null);
		detail.setText(monster.toString());
		return detail;
	}

	/*
	Listeners go here
	 */

	/*
	Functions used to interact with gameController
	 */
	/**
	 * Get initMonsters from the gameController.
	 * @return iniMonsters
	 */
	private ArrayList<Monster> getInitMonsters() {
		return this.gc.getInitMonsters();
	}
}

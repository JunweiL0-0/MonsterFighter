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

public class ChooseMonsterScreen {
	private JFrame chooseFrame;
	private ArrayList<Monster> availableMonsters;
	private ArrayList<Monster> selectedMonsters;
	private ArrayList<JToggleButton> monsterButtons;
	private GameController gc;

	//public Team teamMonsters;

	public ChooseMonsterScreen(GameController gc) {
		this.gc = gc;
		this.availableMonsters = new ArrayList<>();
		this.selectedMonsters = new ArrayList<>();
		this.monsterButtons = new ArrayList<>();

		initialize();
		this.setVisible(true);
	}

	private void initialize() {
		// get initMonsters
		this.availableMonsters = getInitMonsters();
		// add components to chooseFrame
		this.chooseFrame = getChooseFrame();
		this.chooseFrame.getContentPane().add(getTitle());
		this.chooseFrame.getContentPane().add(getButtonPanel());
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
	 *
	 * @return
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

	private JLabel getTitle() {
		JLabel title = new JLabel("Choose your Monsters!",SwingConstants.CENTER);
		title.setBounds(20,20,760,120);
		title.setFont(new Font("Serif", Font.PLAIN, 60));
		title.setBackground(Color.black);
		title.setForeground(Color.white);
		return title;
	}

	private JPanel getButtonPanel() {
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
	private ArrayList<Monster> getInitMonsters() {
		return this.gc.getInitMonsters();
	}
}

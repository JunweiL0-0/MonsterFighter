package main.java.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
<<<<<<< HEAD
=======
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
>>>>>>> 647707f7323d117b2f2de15e2fe3ba6b8805e07d

import javax.swing.*;

import main.java.controller.GameController;
import main.java.model.Generator;
import main.java.model.Monster;

public class ChooseMonsterScreen {
	private JFrame chooseFrame;
<<<<<<< HEAD
	
	private GameController gc;
	private Generator generateMonster;
	//public Team teamMonsters;
	
	
	
	public ChooseMonsterScreen(GameController gameContoller) {
		gc = gameContoller;
		initialize();
		chooseFrame.setVisible(true);
	}



=======
	private Map<Integer, Monster> availableMonsters;
	private ArrayList<Monster> selectedMonsters;
	private GameController gc;
	
	//public Team teamMonsters;
	
	public ChooseMonsterScreen(GameController gameContoller) {
		this.availableMonsters = new HashMap<>();
		this.selectedMonsters = new ArrayList<>();
		this.gc = gameContoller;
		
		this.availableMonsters.put(1, gc.generateMonster());
		this.availableMonsters.put(2, gc.generateMonster());
		this.availableMonsters.put(3, gc.generateMonster());
		this.availableMonsters.put(4, gc.generateMonster());
		this.availableMonsters.put(5, gc.generateMonster());
		

		initialize();
		chooseFrame.setVisible(true);
	}
	
>>>>>>> 647707f7323d117b2f2de15e2fe3ba6b8805e07d
	private void initialize() {
	chooseFrame = new JFrame();
	chooseFrame.setBounds(100, 100, 800, 500);
	chooseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	chooseFrame.setLocationRelativeTo(null);
	chooseFrame.getContentPane().setBackground(Color.black);
	chooseFrame.setLayout(null);
	chooseFrame.setResizable(false);
		
	JLabel title = new JLabel("Choose your Monsters!",SwingConstants.CENTER);
	title.setBounds(20,20,760,120);
	title.setFont(new Font("Serif", Font.PLAIN, 60));
	title.setBackground(Color.black);
	title.setForeground(Color.white);
	chooseFrame.getContentPane().add(title);
	
<<<<<<< HEAD
	
	
	
	JPanel panel = new JPanel();
	panel.setBackground(Color.black);
	panel.setBounds(0,150,800,100);
	panel.setLayout(new FlowLayout(FlowLayout.CENTER,25,0));
	chooseFrame.getContentPane().add(panel);
	
	JPanel details = new JPanel();
	details.setBackground(Color.black);
	details.setBounds(0,260,800,100);
	details.setLayout(new FlowLayout(FlowLayout.CENTER,22,0));
	chooseFrame.getContentPane().add(details);
	
	for (int i=1;i<=5;i++) {
		Monster monster1 = generateMonster.generateMonster();
		JToggleButton button = new JToggleButton(monster1.getName() + i);
		button.setPreferredSize(new Dimension(100,100));
		JTextArea monster = new JTextArea();
		monster.setPreferredSize(new Dimension(100,100));
		monster.setForeground(Color.white);
		monster.setBackground(Color.black);
		monster.setBorder(null);
		monster.setText("Details");
		details.add(monster);
		panel.add(button);
	}
	
	
	
	}
	
	
=======
	buttons();
	}
	
	private void buttons() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.black);
		panel.setBounds(0,150,800,250);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER,25,0));
		chooseFrame.getContentPane().add(panel);

		defaultButtons(panel);
		defaultDetails(panel);
	}
	
	private void defaultButtons(JPanel panel) {
		for (int i=1; i<=5; i++) {
			JToggleButton button = new JToggleButton();
			button.setFont(new Font("Arial", Font.PLAIN, 10));
			button.setPreferredSize(new Dimension(100,100));
			button.setName(Integer.toString(i));
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent actionEvent) {
					if (button.isSelected()) {
						System.out.println(button.getName());
					}
				}
			});
			button.setEnabled(!isMaxSelections());
			
			panel.add(button);
		}

	}
	
	private void defaultDetails(JPanel panel) {
		JPanel panelDetails = new JPanel();
		panelDetails.setBackground(Color.black);
		panelDetails.setBounds(0,260,800,100);
		panelDetails.setLayout(new FlowLayout(FlowLayout.CENTER,22,0));
		chooseFrame.getContentPane().add(panelDetails);
		
		for (int i=1; i<=5; i++) {
			Monster monster = this.availableMonsters.get(i);
			JTextArea details = new JTextArea();
			details.setPreferredSize(new Dimension(100,100));
			details.setBounds(0,370,800,100);
			details.setForeground(Color.white);
			details.setBackground(Color.black);
			details.setBorder(null);
			details.setText(monster.toString());
			
			panelDetails.add(details);
		}
		panel.add(panelDetails);
	}
	
	private boolean isMaxSelections() {
		return this.availableMonsters.size() == 3;
	}
	
	private void addMonsterToList(int selectedMonsterId) {
		this.selectedMonsters.add(this.availableMonsters.get(selectedMonsterId));
	}
>>>>>>> 647707f7323d117b2f2de15e2fe3ba6b8805e07d
}

package main.java.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import main.java.controller.GameController;
import main.java.model.Generator;
import main.java.model.Monster;

public class ChooseMonsterScreen {
	private JFrame chooseFrame;
	
	private GameController gc;
	private Generator generateMonster;
	//public Team teamMonsters;
	
	
	
	public ChooseMonsterScreen(GameController gameContoller) {
		gc = gameContoller;
		initialize();
		chooseFrame.setVisible(true);
	}



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
	
	
}

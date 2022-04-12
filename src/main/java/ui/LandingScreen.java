package main.java.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;


import main.java.controller.GameController;



public class LandingScreen {
	
	private GameController gc;
	private JFrame landingFrame;
	private JTextField userName;
	private JCheckBox easy,medium,hard;
	private JButton confirmNameDiff;
	
	/**
	 * Launch the application.
	 */
	public LandingScreen (GameController gc) {
		this.gc = gc;
		initialize();
		
		landingFrame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		landingFrame = new JFrame();
		landingFrame.setBounds(100, 100, 800, 500);
		landingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		landingFrame.setLocationRelativeTo(null);
		landingFrame.getContentPane().setBackground(Color.black);
		landingFrame.setLayout(null);
		landingFrame.setResizable(false);
		
		
		
		JLabel title = new JLabel("WELCOME",SwingConstants.CENTER);
		title.setBounds(20,20,760,120);
		title.setFont(new Font("Serif", Font.PLAIN, 60));
		title.setBackground(Color.black);
		title.setForeground(Color.white);
		landingFrame.getContentPane().add(title);
		
		JLabel diff = new JLabel("Choose your difficulty",SwingConstants.CENTER);
		diff.setBounds(295,180,200,120);
		diff.setFont(new Font("Serif", Font.PLAIN, 15));
		diff.setBackground(Color.black);
		diff.setForeground(Color.white);
		landingFrame.getContentPane().add(diff);
		
		
		easy = new JCheckBox("Easy");
		easy.setFont(new Font("Serif", Font.PLAIN, 20));
		easy.setForeground(Color.white);
		easy.setBounds(240, 200, 85, 40);
		landingFrame.getContentPane().add(easy);
		
		medium = new JCheckBox("Medium");
		medium.setFont(new Font("Serif", Font.PLAIN, 20));
		medium.setForeground(Color.white);
		medium.setBounds(340, 200, 120, 40);
		landingFrame.getContentPane().add(medium);
		
		hard = new JCheckBox("Hard");
		hard.setFont(new Font("Serif", Font.PLAIN, 20));
		hard.setForeground(Color.white);
		hard.setBounds(470, 200, 90, 40);
		landingFrame.getContentPane().add(hard);
		
		ButtonGroup difficultyGroup = new ButtonGroup();
		difficultyGroup.add(easy);
		difficultyGroup.add(medium);
		difficultyGroup.add(hard);
		
		
		confirmNameDiff = new JButton("Confirm");
		confirmNameDiff.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==confirmNameDiff) {
				gc.launchChooseMonsterScreen();
				}
			}
		});
		confirmNameDiff.setBounds(350,265,100,25);
		landingFrame.add(confirmNameDiff);
	
		addPlayerName();
		
		
	}
	private void addPlayerName() {
		userName = new JTextField(15);
		userName.setText("Enter Player Name");
		userName.setBounds(200, 150, 400, 50);
		userName.setHorizontalAlignment(JTextField.CENTER);
		landingFrame.getContentPane().add(userName);
		userName.setColumns(10);
		
		userName.addKeyListener(new KeyAdapter() {
	         public void keyTyped(KeyEvent e) {
	             char c = e.getKeyChar();
	             if(!(Character.isAlphabetic(c) || (c==KeyEvent.VK_BACK_SPACE) || c==KeyEvent.VK_DELETE )) {
	                 e.consume(); 
	             }
	             if (userName.getText().length() >= 15 ) 
	                 e.consume(); 
	         }
	      });
	}
	
	
	
	
}

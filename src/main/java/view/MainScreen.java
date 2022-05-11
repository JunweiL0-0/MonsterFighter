package main.java.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.*;

import main.java.controller.GameController;
import main.java.model.Monster;
import main.java.model.Team;

public class MainScreen {
	private JFrame mainFrame;
	private GameController gc;
	private JLabel goldPoints;
	private JPanel centerPanel[] = new JPanel[15];
	private JPanel teamPanel[] = new JPanel[4];
	private JPanel monsterPanel[] = new JPanel[4];
	private ButtonGroup topGroup;
	private ArrayList<Monster> selectedMonsters;
	private Team team;
	//private 
//	private ArrayList<Monster> monsterBag;
	
	
//	centerPanel[1]== main center panel
//	centerPanel[2]== bagPanel
//	centerPanel[3]== shopPanel
//	centerPanel[4]== settingsPanel
	

	public MainScreen(GameController gc) {
		this.gc = gc;
		this.team = gc.getTeam();
		this.selectedMonsters = gc.getSelectedMonster();
		initialize();

		show(true);
	}

	public void initialize() {
		this.mainFrame = getMainFrame();
		
		this.mainFrame.getContentPane().add(getTopPanel());
		
		this.mainFrame.getContentPane().add(getLeftPanel());

		this.mainFrame.getContentPane().add(getMainPanel());
		
		this.mainFrame.getContentPane().add(continueButton());
		
		this.mainFrame.getContentPane().add(getRightPanel());
		
		this.mainFrame.getContentPane().add(getBottomPanel());

		this.mainFrame.getContentPane().add(continueButton());
	}
	
	private JFrame getMainFrame() {
		JFrame newMainFrame = new JFrame("MONSTER FIGHTER");
		newMainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		newMainFrame.getContentPane().setPreferredSize(new Dimension(800,500));
		newMainFrame.getContentPane().setBackground(Color.black);
		newMainFrame.setLayout(null);
		newMainFrame.setResizable(false);
		
		newMainFrame.pack();
		newMainFrame.setLocationRelativeTo(null);
		
		return newMainFrame;
	}

	private JPanel getTopPanel() {
		
		JPanel newTopPanel = new JPanel();
		newTopPanel.setBounds(0,0,800,70);
		newTopPanel.setBackground(Color.black);
		newTopPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE,3));
		newTopPanel.setLayout(new GridLayout(1,5));

		JPanel topLeftPanel = new JPanel();
		topLeftPanel.setBackground(Color.BLACK);
		topLeftPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 3, Color.WHITE));
		topLeftPanel.setLayout(new GridLayout(2,1));
		
		JLabel playerName = new JLabel("", SwingConstants.CENTER);
		playerName.setText("Player: " + this.gc.getPlayerName());
		playerName.setFont(new Font("Serif", Font.PLAIN, 15));
		playerName.setForeground(Color.white);

		goldPoints = new JLabel("", SwingConstants.CENTER);
		goldPoints.setFont(new Font("Serif", Font.PLAIN, 15));
		goldPoints.setText("Gold: " + this.gc.getGold() + " Point: " + this.gc.getPoint());
		goldPoints.setForeground(Color.white);

		topLeftPanel.add(playerName);
		topLeftPanel.add(goldPoints);



		JLabel daysLeftLabel = new JLabel("",SwingConstants.CENTER);
		daysLeftLabel.setText(String.format("Days left: %d/%d", this.gc.getCurrentDay(), this.gc.getTotalDay()));
		daysLeftLabel.setFont(new Font("Serif",Font.PLAIN,15));
		daysLeftLabel.setForeground(Color.white);
		daysLeftLabel.setBackground(Color.BLACK);
		daysLeftLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.WHITE));

		
		JToggleButton bagButton = getBagButton();
		JToggleButton shopButton = getShopButton();
		//JToggleButton settingsButton = getSettingsButton();
		
		topGroup = new ButtonGroup();
		topGroup.add(bagButton);
		topGroup.add(shopButton);
		//topGroup.add(settingsButton);
		
		
		newTopPanel.add(topLeftPanel);
		newTopPanel.add(bagButton);
		newTopPanel.add(shopButton);
		//newTopPanel.add(settingsButton);
		newTopPanel.add(daysLeftLabel);
		
		return newTopPanel;
	}
	
	private JPanel getLeftPanel() {
			
		JPanel leftPanel = new JPanel();
		leftPanel.setBounds(0,70,120,430);
		leftPanel.setLayout(new GridLayout(4,1));
		leftPanel.setBackground(Color.black);
		leftPanel.setBorder(BorderFactory.createMatteBorder(0, 3, 3, 3, Color.WHITE));
	
		for (int i=0; i < team.getTeam().size(); i++) {
			teamPanel[i] = new JPanel();
			teamPanel[i].setLayout(new GridLayout(4,1));
			teamPanel[i].setBackground(Color.blue);
			teamPanel[i].setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.WHITE));
			
			JLabel monsterName = new JLabel();
			monsterName.setForeground(Color.white);
			monsterName.setText(team.getTeam().get(i).getName());//getName method for n index in selected monster in gc?
			monsterName.setFont(new Font("Serif", Font.PLAIN, 15));
			
			JLabel monsterHealth = new JLabel();
			monsterHealth.setForeground(Color.white);
			monsterHealth.setText("Health: "+selectedMonsters.get(i).getCurrentHealth());//getName method for n index in selected monster in gc?
			monsterHealth.setFont(new Font("Serif", Font.PLAIN, 15));
			
			
			JLabel monsterLevel = new JLabel();
			monsterLevel.setForeground(Color.white);
			monsterLevel.setText("Level: "+selectedMonsters.get(i).getRarity());//getName method for n index in selected monster in gc?
			monsterLevel.setFont(new Font("Serif", Font.PLAIN, 15));
			
			JLabel monsterDamage = new JLabel();
			monsterDamage.setForeground(Color.white);
			monsterDamage.setText("Damage: "+selectedMonsters.get(i).getDamage());//getName method for n index in selected monster in gc?
			monsterDamage.setFont(new Font("Serif", Font.PLAIN, 15));
			
			
			teamPanel[i].add(monsterName);
			teamPanel[i].add(monsterHealth);
			teamPanel[i].add(monsterLevel);
			teamPanel[i].add(monsterDamage);
			
			leftPanel.add(teamPanel[i]);
			if (i == 3) {	
				teamPanel[i].setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.WHITE));
			}

//		setSelectedMonsters();
		//initialize the selected monsters in the left Panels
//		for (int index=0; index <= selectedMonsters.size(); index++ ) {
//			JLabel monsterName = new JLabel();
//			monsterName.setForeground(Color.white);
//			monsterName.setText(gc.getSelectedMonster().get(i).getName());//getName method for n index in selected monster in gc?
//			monsterName.setFont(new Font("Serif", Font.PLAIN, 10));
//			teamPanel[i].add(monsterName);
//		}
		
		}


		return leftPanel;
	}

	private JPanel getRightPanel() {

		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(4,1));
		rightPanel.setBackground(Color.black);
		rightPanel.setBounds(680,60,120,440);
		rightPanel.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.WHITE));

		for (int i=0; i < 4; i++) {
			monsterPanel[i] = new JPanel();
			monsterPanel[i].setLayout(new GridLayout(4,1));
			monsterPanel[i].setBackground(Color.black);
			monsterPanel[i].setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.WHITE));

			JLabel monster = new JLabel("Gen Monster here");
			monster.setForeground(Color.white);

//			JButton button = new JButton();
//			button.setText("test");
			monsterPanel[i].add(monster);
//			monsterPanel[i].add(button);
			rightPanel.add(monsterPanel[i]);
			if (i == 3) {
				monsterPanel[i].setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.WHITE));
			}
		}

		return rightPanel;
	}

	private JPanel getBottomPanel() {
		JPanel startingPanel = new JPanel();
		startingPanel.setBackground(Color.black);
		startingPanel.setBounds(120,350,560,150);
		startingPanel.setLayout(new GridLayout(2,1));
		startingPanel.setBorder(BorderFactory.createMatteBorder(3, 0, 3, 0, Color.WHITE));

		JTextPane textPane = new JTextPane();
		textPane.setForeground(Color.white);
		textPane.setBackground(Color.BLACK);
		textPane.setFont(new Font("Serif", Font.PLAIN, 25));
		textPane.setText("Welcome Player!");
		textPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		textPane.setEditable(false);


		startingPanel.add(textPane);
		return startingPanel;
	}

	private JPanel getMainPanel() {
		centerPanel[1] = new JPanel();
		centerPanel[1].setLayout(null);
		centerPanel[1].setBackground(Color.BLACK);
		centerPanel[1].setBounds(120,70,560,280);
		
		JLabel mainPanel = new JLabel("MAIN",SwingConstants.CENTER);
		mainPanel.setFont(new Font("Serif", Font.PLAIN, 30));
		mainPanel.setBounds(230, 0, 100, 35);
		mainPanel.setForeground(Color.white);
		mainPanel.setBackground(Color.BLACK);
		mainPanel.setOpaque(true);
		
		centerPanel[1].add(mainPanel);
		
		return centerPanel[1];
	}

	private JButton continueButton() {
		JButton continueButton = new JButton();
		continueButton.setText("Continue");
		continueButton.setBounds(540, 455, 120, 25);
		continueButton.setBackground(Color.white);
		continueButton.setForeground(Color.BLACK);
		
		return continueButton;
	}

	private JToggleButton getBagButton() {
		JToggleButton bagButton = new JToggleButton();
		bagButton.setText("Bag");
		bagButton.setFont(new Font("Serif", Font.PLAIN, 25));
		bagButton.setForeground(Color.white);
		bagButton.setBackground(Color.BLACK);
		bagButton.setFocusable(false);
		bagButton.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 3, Color.WHITE));
		bagButton.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					centerPanel[2] = bagPanel();
					mainFrame.getContentPane().add(centerPanel[2]);
					centerPanel[2].setVisible(true);
					centerPanel[1].setVisible(false);
				
				}
				else if (e.getStateChange() == ItemEvent.DESELECTED) {
					centerPanel[2].setVisible(false);
					centerPanel[1].setVisible(true);
				}
				
			}
		});

		return bagButton;
	}


	private JToggleButton getShopButton() {
		JToggleButton shopButton = new JToggleButton();
		shopButton.setText("Shop");
		shopButton.setFont(new Font("Serif", Font.PLAIN, 25));
		shopButton.setForeground(Color.white);
		shopButton.setBackground(Color.BLACK);
		shopButton.setFocusable(false);
		shopButton.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 3, Color.WHITE));
		shopButton.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					centerPanel[3] = shopPanel();
					mainFrame.getContentPane().add(centerPanel[3]);
					centerPanel[3].setVisible(true);
					centerPanel[1].setVisible(false);
				
				}
				else if (e.getStateChange() == ItemEvent.DESELECTED) {
					centerPanel[3].setVisible(false);
					centerPanel[1].setVisible(true);
				}
			}
		});
		return shopButton;

	}


	private JToggleButton getSettingsButton() {
		JToggleButton settingsButton = new JToggleButton();
		settingsButton.setText("Settings");
		settingsButton.setFont(new Font("Serif", Font.PLAIN, 25));
		settingsButton.setForeground(Color.white);
		settingsButton.setBounds(0,0,120,70);
		settingsButton.setBackground(Color.BLACK);
		settingsButton.setFocusable(false);
		settingsButton.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 3, Color.WHITE));
		settingsButton.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					centerPanel[4] = settingsPanel();
					mainFrame.getContentPane().add(centerPanel[4]);
					centerPanel[4].setVisible(true);
					centerPanel[1].setVisible(false);
				
				}
				else if (e.getStateChange() == ItemEvent.DESELECTED) {
					centerPanel[4].setVisible(false);
					centerPanel[1].setVisible(true);
				}
			}
	});

		return settingsButton;
	}
	
	//return center bag panel
	private JPanel bagPanel() {
		JPanel bagPanel = new JPanel();
		bagPanel.setLayout(null);
		bagPanel.setBackground(Color.BLACK);
		bagPanel.setBounds(120,70,560,280);
		
		JLabel bagTitle = new JLabel("BAG",SwingConstants.CENTER);
		bagTitle.setFont(new Font("Serif", Font.PLAIN, 30));
		bagTitle.setBounds(230, 0, 100, 35);
		bagTitle.setForeground(Color.white);
		bagTitle.setBackground(Color.BLACK);
		bagTitle.setOpaque(true);
		
		bagPanel.add(bagTitle);
		bagPanel.add(backButton());

		return bagPanel;
	}

	//return the center shop panel
	private JPanel shopPanel() {
		JPanel shopPanel = new JPanel();
		shopPanel.setLayout(null);
		shopPanel.setBackground(Color.BLACK);
		shopPanel.setBounds(120,70,560,280);
		
		JLabel shopTitle = new JLabel("SHOP",SwingConstants.CENTER);
		shopTitle.setFont(new Font("Serif", Font.PLAIN, 30));
		shopTitle.setBounds(230, 0, 100, 35);
		shopTitle.setForeground(Color.white);
		shopTitle.setBackground(Color.BLACK);
		shopTitle.setOpaque(true);
		
		shopPanel.add(shopTitle);
		shopPanel.add(backButton());

		return shopPanel;
	}
	
	//return the center setting panel
	private JPanel settingsPanel() {
		JPanel settingsPanel = new JPanel();
		settingsPanel.setLayout(null);
		settingsPanel.setBackground(Color.BLACK);
		settingsPanel.setBounds(120,70,560,280);

		JLabel settingsTitle = new JLabel("SETTINGS",SwingConstants.CENTER);
		settingsTitle.setFont(new Font("Serif", Font.PLAIN, 30));
		settingsTitle.setBounds(205, 0, 150, 35);
		settingsTitle.setForeground(Color.white);
		settingsTitle.setBackground(Color.BLACK);
		settingsTitle.setOpaque(true);
		
		settingsPanel.add(settingsTitle);
		settingsPanel.add(backButton());
		
		return settingsPanel;
	}
	
	private JButton backButton() {
		JButton backButton = new JButton();
		backButton.setText("Back");
		backButton.setFont(new Font("Serif", Font.PLAIN, 15));
		backButton.setForeground(Color.white);
		backButton.setBounds(15,250,50,20);
		backButton.setBackground(Color.BLACK);
		backButton.setFocusable(false);
		backButton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == backButton) {
					topGroup.clearSelection();
				}
			}
		});
		
		return backButton;
		
		
	}
	
//	private ArrayList<Monster> MonsterBag(){
//		
//	}
	
//	private void setSelectedMonsters() {
//		int i = selectedMonsters.size();
//		for (Monster monster : selectedMonsters) {
//			JLabel monsterName = new JLabel();
//			monsterName.setForeground(Color.white);
//			monsterName.setText(monster.getName());//getName method for n index in selected monster in gc?
//			monsterName.setFont(new Font("Serif", Font.PLAIN, 10));
//			teamPanel[i].add(monsterName);
//			i++;
//		}
//		
//	}
	
	public void show(boolean val) {
		this.mainFrame.setVisible(val);
	}
}
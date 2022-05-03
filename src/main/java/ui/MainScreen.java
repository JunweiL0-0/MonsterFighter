package main.java.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;

import main.java.controller.GameController;

public class MainScreen {
	private JFrame mainFrame;
	private GameController gc;
	private JLabel goldPoints;
	private JPanel centerPanel[] = new JPanel[15];
	private JPanel teamPanel[] = new JPanel[4];
	private JPanel monsterPanel[] = new JPanel[4];

	//Need to only be able to press one togglebutton at a time

	public MainScreen(GameController gc) {
		this.gc = gc;
		initialize();

		mainFrame.setVisible(true);
	}

	public void initialize() {
		this.mainFrame = getMainFrame();
		
		this.mainFrame.getContentPane().add(getTopPanel());
		
		this.mainFrame.getContentPane().add(getLeftPanel());

		this.mainFrame.getContentPane().add(getCenterPanel());

		this.mainFrame.getContentPane().add(getRightPanel());
		
		this.mainFrame.getContentPane().add(getBottomPanel());

		this.mainFrame.getContentPane().add(continueButton());


//		this.mainFrame.getContentPane().add(continueButton());
	}
	
	private JFrame getMainFrame() {
		JFrame newMainFrame = new JFrame();
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
		playerName.setText("Get Name Function here");
		playerName.setFont(new Font("Serif", Font.PLAIN, 15));
		playerName.setForeground(Color.white);

		goldPoints = new JLabel("", SwingConstants.CENTER);
		goldPoints.setFont(new Font("Serif", Font.PLAIN, 15));
		goldPoints.setText("get curr gold and points func here");
		goldPoints.setForeground(Color.white);

		topLeftPanel.add(playerName);
		topLeftPanel.add(goldPoints);



		JLabel daysLeftLabel = new JLabel("",SwingConstants.CENTER);
		daysLeftLabel.setText("Days Left: 1/15");
		daysLeftLabel.setFont(new Font("Serif",Font.PLAIN,15));
		daysLeftLabel.setForeground(Color.white);
		daysLeftLabel.setBackground(Color.BLACK);
		daysLeftLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.WHITE));


		newTopPanel.add(topLeftPanel);
		newTopPanel.add(getBagButton());
		newTopPanel.add(getShopButton());
		newTopPanel.add(getSettingsButton());
		newTopPanel.add(daysLeftLabel);
		return newTopPanel;
	}
	
	private JPanel getLeftPanel() {
			
		JPanel leftPanel = new JPanel();
		leftPanel.setBounds(0,60,120,440);
		leftPanel.setLayout(new GridLayout(teamPanel.length,1));
		leftPanel.setBackground(Color.black);
		leftPanel.setBorder(BorderFactory.createMatteBorder(0, 3, 3, 3, Color.WHITE));

		for (int i=0; i < teamPanel.length; i++) {
			teamPanel[i] = new JPanel();
			teamPanel[i].setLayout(new GridLayout(4,1));
			teamPanel[i].setBackground(Color.black);
			teamPanel[i].setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.WHITE));

			JLabel monsterName = new JLabel();
			monsterName.setForeground(Color.white);
			monsterName.setText("Monster "+Integer.toString(i));//getName method for n index in selected monster in gc?

			teamPanel[i].add(monsterName);


			leftPanel.add(teamPanel[i]);
			if (i == 3) {
				teamPanel[i].setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.WHITE));
			}
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

	private JPanel getCenterPanel() {
		centerPanel[1] = new JPanel();
		centerPanel[1].setBackground(Color.black);
		centerPanel[1].setBounds(120,70,560,280);

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
		bagButton.setOpaque(true);
		bagButton.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 3, Color.WHITE));
		bagButton.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED) {
					bagButton.setBackground(Color.white);
					bagButton.setForeground(Color.BLACK);
					centerPanel[2] = bagPanel();
					mainFrame.getContentPane().add(centerPanel[2]);
					centerPanel[2].setVisible(true);
					centerPanel[2].setOpaque(true);
					centerPanel[1].setVisible(false);
				}
				else {
					bagButton.setForeground(Color.white);
					bagButton.setBackground(Color.BLACK);
					mainFrame.getContentPane().remove(centerPanel[2]);
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
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED) {
					shopButton.setBackground(Color.white);
					shopButton.setForeground(Color.BLACK);
					centerPanel[3] = shopPanel();
					mainFrame.getContentPane().add(centerPanel[3]);
					centerPanel[3].setVisible(true);
					centerPanel[3].setOpaque(true);
					centerPanel[1].setVisible(false);
				}
				else {
					shopButton.setForeground(Color.white);
					shopButton.setBackground(Color.BLACK);
					mainFrame.getContentPane().remove(centerPanel[3]);
					centerPanel[1].setVisible(true);
				}
			}
		});
		return shopButton;

	}


	public JToggleButton getSettingsButton() {
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
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED) {
					settingsButton.setBackground(Color.white);
					settingsButton.setForeground(Color.BLACK);
					centerPanel[4] = settingsPanel();
					mainFrame.getContentPane().add(centerPanel[4]);
					centerPanel[4].setVisible(true);
					centerPanel[4].setOpaque(true);
					centerPanel[1].setVisible(false);
				}
				else {
					settingsButton.setForeground(Color.white);
					settingsButton.setBackground(Color.BLACK);
					mainFrame.getContentPane().remove(centerPanel[4]);
					centerPanel[1].setVisible(true);
				}
			}
	});

		return settingsButton;
	}
	
	//return center bag panel
	public JPanel bagPanel() {
		JPanel bagPanel = new JPanel();
		bagPanel.setLayout(new GridLayout(1,4));
		bagPanel.setBackground(Color.GREEN);
		bagPanel.setBounds(120,70,560,280);
		
		JLabel monsters = new JLabel("Bag");//not showing
		monsters.setForeground(Color.white);
		monsters.setBackground(Color.WHITE);
		
		bagPanel.add(monsters);

		return bagPanel;
	}

	//return the center shop panel
	public JPanel shopPanel() {
		JPanel shopPanel = new JPanel();
		shopPanel.setLayout(new GridLayout(1,4));
		shopPanel.setBackground(Color.RED);
		shopPanel.setBounds(120,70,560,280);
		
		JLabel items = new JLabel("Shop");//not showing
		items.setForeground(Color.white);
		items.setBackground(Color.WHITE);

		shopPanel.add(items);

		return shopPanel;
	}
	
	//return the center setting panel
	public JPanel settingsPanel() {
		JPanel settingsPanel = new JPanel();
		settingsPanel.setLayout(new GridLayout(4,1));
		settingsPanel.setBackground(Color.YELLOW);
		settingsPanel.setBounds(120,70,560,280);

		JLabel items = new JLabel("Settings");//not showing
		items.setForeground(Color.white);
		items.setBackground(Color.WHITE);

		settingsPanel.add(items);

		return settingsPanel;
	}
}

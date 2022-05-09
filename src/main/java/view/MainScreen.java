package main.java.view;

import java.awt.*;
import java.awt.event.ItemEvent;
import javax.swing.*;
import main.java.controller.GameController;

/**
 * MainScreen. This is where we play the game.
 */
public class MainScreen {
	// game controller
	private final GameController gc;
	// components
	private final JPanel[] teamPanel = new JPanel[4];
	private final JPanel[] monsterPanel = new JPanel[4];
	private JFrame mainFrame;
	private ButtonGroup topGroup;
	// variable (Change dynamically)
	private JLabel goldPoints;
	private JPanel mainPanel;
	private JPanel bagPanel;
	private JPanel shopPanel;
	private JPanel settingsPanel;
	// enum
	private enum TopBtn {
		MAIN, BAG, SHOP, SETTINGS;
	}


	/**
	 * MainScreen's constructor. Initialize and show the mainScreen.
	 *
	 * @param gc gameController.
	 */
	public MainScreen(GameController gc) {
		this.gc = gc;
		initialize();
		// show mainScreen
		show(true);
	}

	/**
	 * Create mainFrame and add components to it.
	 */
	public void initialize() {
		this.mainFrame = getMainFrame();
		this.mainFrame.getContentPane().add(getTopPanel());
		this.mainFrame.getContentPane().add(getLeftPanel());
		this.mainFrame.getContentPane().add(getRightPanel());
		this.mainFrame.getContentPane().add(getContinueButton());
		this.mainFrame.getContentPane().add(getBottomPanel());
		// panels which will be shown in the center
		this.mainPanel = getMainPanel(); // visible by default
		this.bagPanel = getBagPanel(); // not visible by default
		this.shopPanel = getShopPanel(); // not visible by default
		this.settingsPanel = getSettingsPanel(); // not visible by default
		this.mainFrame.getContentPane().add(this.mainPanel);
		this.mainFrame.getContentPane().add(this.bagPanel);
		this.mainFrame.getContentPane().add(this.shopPanel);
		this.mainFrame.getContentPane().add(this.settingsPanel);
	}

	/*JFrame*/
	/**
	 * Create a new MainFrame
	 *
	 * @return a newly created MainFrame
	 */
	private JFrame getMainFrame() {
		// create a new frame
		JFrame newMainFrame = new JFrame("MONSTER FIGHTER");
		newMainFrame.getContentPane().setPreferredSize(new Dimension(800,500));
		newMainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		newMainFrame.getContentPane().setBackground(Color.BLACK);
		newMainFrame.setLayout(null);
		newMainFrame.setResizable(false);
		newMainFrame.pack();
		// place the frame in the center of the screen
		newMainFrame.setLocationRelativeTo(null);
		// return frame
		return newMainFrame;
	}

	/*JPanel*/
	/**
	 * Create and return a topPanel. This panel will be later added to the mainFrame
	 *
	 * @return a newly created topPanel.
	 */
	private JPanel getTopPanel() {
		// TopPanel
		//    TopLeftPanel     BagBtn    ShopBtn    SettingsBtn  DaysLeftLabel
		//   /            \       |           |           |           |
		// /                \     |           |           |           |
		// *******************************************************************
		// *    Player:      *          *           *           *            *
		// * Gold:  Points:  *   Bag    *    Shop   *  Settings * Days Left: *
		// *******************************************************************
		// create a new panel
		JPanel newTopPanel = new JPanel();
		newTopPanel.setBounds(0,0,800,70);
		newTopPanel.setBackground(Color.BLACK);
		newTopPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE,3));
		newTopPanel.setLayout(new GridLayout(1,5));
		// create buttons (Bag, Shop, Settings)
		JToggleButton bagButton = getBagButton();
		JToggleButton shopButton = getShopButton();
		JToggleButton settingsButton = getSettingsButton();
		// add them into a buttonGroup. Once selection at a time.
		topGroup = new ButtonGroup();
		topGroup.add(bagButton);
		topGroup.add(shopButton);
		topGroup.add(settingsButton);
		// add components to the panel
		newTopPanel.add(getTopLeftPanel());
		newTopPanel.add(bagButton);
		newTopPanel.add(shopButton);
		newTopPanel.add(settingsButton);
		newTopPanel.add(getDaysLeftLabel());
		// return panel
		return newTopPanel;
	}

	/**
	 * Create and return a topLeftPanel. This panel will be later added to the topPanel.
	 *
	 * @return a newly created topLeftPanel.
	 */
	private JPanel getTopLeftPanel() {
		// TopLeftPanel (topPanel component)
		// *******************
		// *    Player:      *  ----> PlayerNameLabel
		// * Gold:  Points:  *  ----> GoldPointsLabel
		// *******************
		JPanel topLeftPanel = new JPanel();
		topLeftPanel.setBackground(Color.BLACK);
		topLeftPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 3, Color.WHITE));
		topLeftPanel.setLayout(new GridLayout(2,1));
		// add components to the panel
		topLeftPanel.add(getPlayerNameLabel());
		topLeftPanel.add(getGoldPointsLabel());
		// return panel
		return topLeftPanel;
	}

	// TODO: Add drag and drop
	private JPanel getLeftPanel() {
		JPanel leftPanel = new JPanel();
		leftPanel.setBounds(0,70,120,430);
		leftPanel.setLayout(new GridLayout(teamPanel.length,1));
		leftPanel.setBackground(Color.BLACK);
		leftPanel.setBorder(BorderFactory.createMatteBorder(0, 3, 3, 3, Color.WHITE));

		for (int i=0; i < teamPanel.length; i++) {
			teamPanel[i] = new JPanel();
			teamPanel[i].setLayout(new GridLayout(4,1));
			teamPanel[i].setBackground(Color.BLACK);
			teamPanel[i].setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.WHITE));

			JLabel monsterName = new JLabel();
			monsterName.setForeground(Color.WHITE);
			monsterName.setText("Monster "+Integer.toString(i));//getName method for n index in selected monster in gc?

			teamPanel[i].add(monsterName);

			leftPanel.add(teamPanel[i]);
			if (i == 3) {
				teamPanel[i].setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.WHITE));
			}
		}
		return leftPanel;
	}

	// TODO: Add drag and drop
	private JPanel getRightPanel() {
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(4,1));
		rightPanel.setBackground(Color.BLACK);
		rightPanel.setBounds(680,70,120,430);
		rightPanel.setBorder(BorderFactory.createMatteBorder(0, 3, 3, 3, Color.WHITE));

		for (int i=0; i < 4; i++) {
			monsterPanel[i] = new JPanel();
			monsterPanel[i].setLayout(new GridLayout(4,1));
			monsterPanel[i].setBackground(Color.BLACK);
			monsterPanel[i].setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.WHITE));

			JLabel monster = new JLabel("Gen Monster here");
			monster.setForeground(Color.WHITE);

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

	/**
	 * Create and return the bottomPanel.
	 *
	 * @return a newly created bottomPanel.
	 */
	private JPanel getBottomPanel() {
		// startingPanel
		// *******************************************************************
		// * Welcome Player                                                  * ----> TextPane
		// *                                                                 *
		// *******************************************************************
		// create a startingPanel
		JPanel startingPanel = new JPanel();
		startingPanel.setBackground(Color.BLACK);
		startingPanel.setBounds(120,350,560,150);
		startingPanel.setLayout(new GridLayout(2,1));
		startingPanel.setBorder(BorderFactory.createMatteBorder(3, 0, 3, 0, Color.WHITE));
		// add components to the startingPanel
		startingPanel.add(getTextPane());
		// return the startingPanel
		return startingPanel;
	}

	/**
	 * Create and return a mainPanel. This panel is part of the centerPanel
	 *
	 * @return a newly created mainPanel
	 */
	private JPanel getMainPanel() {
		// MainPanel (centerPanel component)
		// create a JPanel
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.setBackground(Color.BLACK);
		mainPanel.setBounds(120,70,560,280);
		// create a JLabel
		JLabel mainPanelLabel = new JLabel("MAIN", SwingConstants.CENTER);
		mainPanelLabel.setFont(new Font("Serif", Font.PLAIN, 30));
		mainPanelLabel.setBounds(230, 0, 100, 35);
		mainPanelLabel.setForeground(Color.WHITE);
		mainPanelLabel.setBackground(Color.BLACK);
		mainPanelLabel.setOpaque(true);
		// add components to JPanel
		mainPanel.add(mainPanelLabel);
		// return panel
		return mainPanel;
	}

	/**
	 * Create and return a bagPanel. This panel will be stored in the variable "centerPanel".
	 *
	 * @return a bagPanel
	 */
	private JPanel getBagPanel() {
		// create a bagPanel
		JPanel bagPanel = new JPanel();
		bagPanel.setLayout(null);
		bagPanel.setBackground(Color.BLACK);
		bagPanel.setBounds(120,70,560,280);
		// add component
		bagPanel.add(getBagTitle());
		bagPanel.add(backButton());
		// set it to not visible (Default)
		bagPanel.setVisible(false);
		// return panel
		return bagPanel;
	}

	/**
	 * Create and return a settingsPanel. The settingsPanel will be shown in the middle of the mainFrame if "Visible" is true。
	 *
	 * @return a settingsPanel
	 */
	private JPanel getSettingsPanel() {
		// create a settingsPanel (This panel will be stored in a variable)
		JPanel settingsPanel = new JPanel();
		settingsPanel.setLayout(null);
		settingsPanel.setBackground(Color.BLACK);
		settingsPanel.setBounds(120,70,560,280);
		// add component
		settingsPanel.add(getSettingsTitle());
		settingsPanel.add(backButton());
		// set the panel to not visible (Default)
		settingsPanel.setVisible(false);
		// return settingsPanel
		return settingsPanel;
	}

	/**
	 * Create and return a shopPanel. The shopPanel will be shown in the middle of the mainFrame if "Visible" is true。
	 *
	 * @return a shopPanel
	 */
	private JPanel getShopPanel() {
		// create a shopPanel (This panel will be stored in a variable)
		JPanel shopPanel = new JPanel();
		shopPanel.setLayout(null);
		shopPanel.setBackground(Color.BLACK);
		shopPanel.setBounds(120,70,560,280);
		// add components
		shopPanel.add(getShopTitle());
		shopPanel.add(backButton());
		// set it to not visible (Default)
		shopPanel.setVisible(false);
		// return panel
		return shopPanel;
	}

	/* JTextPane */
	/**
	 * This function will create and return a textPane so that we can display information on the bottomPanel.
	 *
	 * @return a textPane(JTextPane)
	 */
	private JTextPane getTextPane() {
		// textPane (bottomPanel component)
		// textPane for displaying text
		JTextPane textPane = new JTextPane();
		textPane.setForeground(Color.WHITE);
		textPane.setBackground(Color.BLACK);
		textPane.setFont(new Font("Serif", Font.PLAIN, 25));
		textPane.setText("Welcome Player!");
		textPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		textPane.setEditable(false);
		// return statement
		return textPane;
	}

	/* JLabel */
	/**
	 * Create and return a playerNameLabel.
	 *
	 * @return a playerNameLabel(JLabel)
	 */
	private JLabel getPlayerNameLabel() {
		// playerName (TopLeftPanel/TopPanel component)
		JLabel playerNameLabel = new JLabel("", SwingConstants.CENTER);
		playerNameLabel.setText("Player: " + this.gc.getPlayerName());
		playerNameLabel.setFont(new Font("Serif", Font.PLAIN, 15));
		playerNameLabel.setForeground(Color.WHITE);
		return playerNameLabel;
	}

	/**
	 * Create and return a goldPointsLabel.
	 *
	 * @return a goldPointsLabel(JLabel)
	 */
	private JLabel getGoldPointsLabel() {
		// gold and point (TopLeftPanel/TopPanel component)
		goldPoints = new JLabel("", SwingConstants.CENTER);
		goldPoints.setFont(new Font("Serif", Font.PLAIN, 15));
		goldPoints.setText("Gold: " + this.gc.getGold() + " Point: " + this.gc.getPoint());
		goldPoints.setForeground(Color.WHITE);
		// return goldPoints label
		return goldPoints;
	}

	/**
	 * Create and return a daysLeftLabel.
	 *
	 * @return a daysLeftLabel(JLabel)
	 */
	private JLabel getDaysLeftLabel() {
		// daysLeftLabel (TopPanel component)
		JLabel daysLeftLabel = new JLabel("",SwingConstants.CENTER);
		daysLeftLabel.setText(String.format("Days left: %d/%d", this.gc.getCurrentDay(), this.gc.getTotalDay()));
		daysLeftLabel.setFont(new Font("Serif",Font.PLAIN,15));
		daysLeftLabel.setForeground(Color.WHITE);
		daysLeftLabel.setBackground(Color.BLACK);
		daysLeftLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.WHITE));
		// return label
		return daysLeftLabel;
	}

	/**
	 * This function will return the title of the bagPanel.
	 *
	 * @return a bagTitle
	 */
	private JLabel getBagTitle() {
		// bagTitle (BagPanel component)
		JLabel bagTitle = new JLabel("BAG",SwingConstants.CENTER);
		bagTitle.setFont(new Font("Serif", Font.PLAIN, 30));
		bagTitle.setBounds(230, 0, 100, 35);
		bagTitle.setForeground(Color.WHITE);
		bagTitle.setBackground(Color.BLACK);
		bagTitle.setOpaque(true);
		// return bagTitle
		return bagTitle;
	}

	/**
	 * This function will return the title of the settingsPanel.
	 *
	 * @return a settingsTitle(JLabel)
	 */
	private JLabel getSettingsTitle() {
		// settingsTitle (SettingsPanel component)
		JLabel settingsTitle = new JLabel("SETTINGS",SwingConstants.CENTER);
		settingsTitle.setFont(new Font("Serif", Font.PLAIN, 30));
		settingsTitle.setBounds(205, 0, 150, 35);
		settingsTitle.setForeground(Color.WHITE);
		settingsTitle.setBackground(Color.BLACK);
		settingsTitle.setOpaque(true);
		// return settingsTitle
		return settingsTitle;
	}

	/**
	 * This function will return the title of the shopPanel.
	 *
	 * @return a ShopTitle(JLabel)
	 */
	private JLabel getShopTitle() {
		// shopTitle (ShopPanel component)
		JLabel shopTitle = new JLabel("SHOP",SwingConstants.CENTER);
		shopTitle.setFont(new Font("Serif", Font.PLAIN, 30));
		shopTitle.setBounds(230, 0, 100, 35);
		shopTitle.setForeground(Color.WHITE);
		shopTitle.setBackground(Color.BLACK);
		shopTitle.setOpaque(true);
		// return title
		return shopTitle;
	}

	/* JButton */
	/**
	 * Create and return a ContinueBtn. This button will be part of the mainFrame components.
	 *
	 * @return a continueBtn(JButton)
	 */
	private JButton getContinueButton() {
		// **************
		// *  Continue  *
		// **************
		// continue button
		JButton continueButton = new JButton();
		continueButton.setText("Continue");
		continueButton.setBounds(540, 455, 120, 25);
		continueButton.setBackground(Color.WHITE);
		continueButton.setForeground(Color.BLACK);
		continueButton.setFocusable(false);
		// return continueBtn
		return continueButton;
	}

	/**
	 * Create and return a backBtn. Once the btn is being clicked. The mainPanel will be shown.
	 *
	 * @return a backBtn (JBtn)
	 */
	private JButton backButton() {
		// create a backBtn (BagPanel/ShopPanel/settingsPanel component)
		JButton backButton = new JButton();
		backButton.setText("Back");
		backButton.setFont(new Font("Serif", Font.PLAIN, 15));
		backButton.setForeground(Color.WHITE);
		backButton.setBounds(15,250,50,20);
		backButton.setBackground(Color.BLACK);
		backButton.setFocusable(false);
		backButton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
		// event listener
		addBackBtnListener(backButton);
		// return btn
		return backButton;
	}

	/* JToggleButton */
	/**
	 * Create and return the bagButton for the TopPanel
	 *
	 * @return a bagButton (JToggleBtn)
	 */
	private JToggleButton getBagButton() {
		// create a bagButton (TopPanel component)
		JToggleButton bagButton = new JToggleButton();
		bagButton.setText("Bag");
		bagButton.setFont(new Font("Serif", Font.PLAIN, 25));
		bagButton.setForeground(Color.WHITE);
		bagButton.setBackground(Color.BLACK);
		bagButton.setFocusable(false);
		bagButton.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 3, Color.WHITE));
		// bagBtn listener
		addBagBtnListener(bagButton);
		// return btn
		return bagButton;
	}

	/**
	 * Create and return the shopButton for the TopPanel
	 *
	 * @return a shopButton (JToggleBtn)
	 */
	private JToggleButton getShopButton() {
		// create a shopButton (TopPanel component)
		JToggleButton shopButton = new JToggleButton();
		shopButton.setText("Shop");
		shopButton.setFont(new Font("Serif", Font.PLAIN, 25));
		shopButton.setForeground(Color.WHITE);
		shopButton.setBackground(Color.BLACK);
		shopButton.setFocusable(false);
		shopButton.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 3, Color.WHITE));
		// shopButton listener
		addShopBtnListener(shopButton);
		// return btn
		return shopButton;
	}

	/**
	 * Create and return the settingsBtn for the TopPanel
	 *
	 * @return a settingsBtn (JToggleBtn)
	 */
	private JToggleButton getSettingsButton() {
		// create a settingsButton (TopPanel component)
		JToggleButton settingsButton = new JToggleButton();
		settingsButton.setText("Settings");
		settingsButton.setFont(new Font("Serif", Font.PLAIN, 25));
		settingsButton.setForeground(Color.WHITE);
		settingsButton.setBounds(0,0,120,70);
		settingsButton.setBackground(Color.BLACK);
		settingsButton.setFocusable(false);
		settingsButton.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 3, Color.WHITE));
		// event listener
		addSettingsBtnListener(settingsButton);
		return settingsButton;
	}

	/* Listeners */
	/**
	 * BackBtn listener. Clear all selected button and show mainPanel.
	 *
	 * @param b a JButton (From one of the top buttons)
	 */
	private void addBackBtnListener(JButton b) {
		b.addActionListener(e -> {
			// unSelected all buttons
			topGroup.clearSelection();
			// show mainPanel
			showCenterPanel(TopBtn.MAIN);
		});
	}

	/**
	 * BagBtn listener. Show bagPanel when button selected, show the mainPanel otherwise.
	 *
	 * @param b a JButton (From one of the top buttons)
	 */
	private void addBagBtnListener(JToggleButton b) {
		b.addItemListener(e -> {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				showCenterPanel(TopBtn.BAG);
			} else if (e.getStateChange() == ItemEvent.DESELECTED) {
				showCenterPanel(TopBtn.MAIN);
			}
		});
	}

	/**
	 * ShopBtn listener. Show shopPanel when button selected, show the mainPanel otherwise.
	 *
	 * @param b a JButton (From one of the top buttons)
	 */
	private void addShopBtnListener(JToggleButton b) {
		b.addItemListener(e -> {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				showCenterPanel(TopBtn.SHOP);
			} else if (e.getStateChange() == ItemEvent.DESELECTED) {
				showCenterPanel(TopBtn.MAIN);
			}
		});
	}

	/**
	 * SettingsBtn listener. Show settingsPanel when button selected, show the mainPanel otherwise.
	 *
	 * @param b a JButton (From one of the top buttons)
	 */
	private void addSettingsBtnListener(JToggleButton b) {
		b.addItemListener(e -> {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				showCenterPanel(TopBtn.SETTINGS);
			} else if (e.getStateChange() == ItemEvent.DESELECTED) {
				showCenterPanel(TopBtn.MAIN);
			}
		});
	}

	/*
	Other functions
	 */
	/**
	 * This function will hide all panels first and show the selected panel.
	 *
	 * @param TBtn an enum type represents the type of the button. (MAIN, BAG, SHOP, SETTINGS)
	 */
	private void showCenterPanel(TopBtn TBtn) {
		// hide all panels
		this.mainPanel.setVisible(false);
		this.bagPanel.setVisible(false);
		this.shopPanel.setVisible(false);
		this.settingsPanel.setVisible(false);
		// show one panel
		switch(TBtn) {
			case MAIN -> this.mainPanel.setVisible(true);
			case BAG -> this.bagPanel.setVisible(true);
			case SHOP -> this.shopPanel.setVisible(true);
			case SETTINGS -> this.settingsPanel.setVisible(true);
		}
	}

	/**
	 * Show or hide the mainFrame.
	 *
	 * @param val a boolean value used show/hide the mainFrame.
	 */
	public void show(boolean val) {
		this.mainFrame.setVisible(val);
	}
}

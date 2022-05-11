package main.java.view;

import java.awt.*;
import java.awt.event.ItemEvent;
import javax.swing.*;
import main.java.controller.GameController;
import main.java.model.Monster;
import main.java.utilities.Observable;
import main.java.utilities.Observer;

/**
 * MainScreen. This is where we play the game.
 */
public class MainScreen implements Observer {
	// game controller
	private final GameController gc;
	// components
	private final JPanel[] playerTeamPanel = new JPanel[4];
	private final JPanel[] monsterPanel = new JPanel[4];
	private JFrame mainFrame;
	private ButtonGroup topGroup;
	// variable (Toggle visibility)
	// centerPanel
	private JPanel centerMainPanel;
	private JPanel centerBagPanel;
	private JPanel centerShopPanel;
	private JPanel centerSettingsPanel;
	// bottomPanel
	private JPanel bottomMainPanel;
	private JPanel bottomBagPanel;
	private JPanel bottomShopPanel;
	private JPanel bottomSettingsPanel;
	// enum
	private enum TopBtn {
		MAIN, BAG, SHOP, SETTINGS;

		@Override
		public String toString() {
			return switch (this) {
				case MAIN -> "Main";
				case BAG -> "Bag";
				case SHOP -> "Shop";
				case SETTINGS -> "Settings";
			};
		}
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
		// *******************************************************************\
		// *    Player:      *          *           *           *            *  \ TopPanel
		// * Gold:  Points:  *   Bag    *    Shop   *  Settings * Days Left: *  /
		// *******************************************************************/
		// * LeftPanel  *                                       * RightPanel *
		// *            *                                       *            *
		// *            *     Main/Bag/Shop/Settings panel      *            *
		// *            *                                       *            *
		// *            *                                       *            *
		// *            *****************************************            *
		// *            *  Bottom Main/Bag/Shop/Settings panel  *            *
		// *            *                         (ContinueBtn) *            *
		// *******************************************************************
		this.mainFrame = getMainFrame();
		// Top, Left, Right
		this.mainFrame.getContentPane().add(getTopPanel());
		this.mainFrame.getContentPane().add(getLeftPanel());
		this.mainFrame.getContentPane().add(getRightPanel());
		// create panel and store them into variables
		// Center
		initCenterMainPanel(); // Visible by default
		initCenterBagPanel(); // Not visible until btn being pressed
		initCenterShopPanel(); // Not visible until btn being pressed
		initCenterSettingsPanel(); // Not visible until btn being pressed
		// Bottom
		initBottomMainPanel(); // Visible by default
		initBottomBagPanel(); // Not visible until btn being pressed
		initBottomShopPanel(); // Not visible until btn being pressed
		initBottomSettingsPanel(); // Not visible until btn being pressed
		// PlayerTeamPanel
		initPlayerTeamPanel1();
		// add panels
		this.mainFrame.getContentPane().add(this.centerMainPanel);
		this.mainFrame.getContentPane().add(this.centerBagPanel);
		this.mainFrame.getContentPane().add(this.centerShopPanel);
		this.mainFrame.getContentPane().add(this.centerSettingsPanel);
		this.mainFrame.getContentPane().add(this.bottomMainPanel);
		this.mainFrame.getContentPane().add(this.bottomBagPanel);
		this.mainFrame.getContentPane().add(this.bottomShopPanel);
		this.mainFrame.getContentPane().add(this.bottomSettingsPanel);
	}

	private void initPlayerTeamPanel1() {

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
		leftPanel.setLayout(new GridLayout(this.playerTeamPanel.length,1));
		leftPanel.setBackground(Color.BLACK);
		leftPanel.setBorder(BorderFactory.createMatteBorder(1, 3, 1, 3, Color.WHITE));

		for (int i=0; i < this.playerTeamPanel.length; i++) {
			JPanel panel = getNewPlayerTeamPanel();
			// add name label if we have monster in the team
			if (i < this.gc.getMonsterTeamMember().size()) {
				panel.add(getNameLabelForMonster(i));
			}
			this.playerTeamPanel[i] = panel;
			leftPanel.add(this.playerTeamPanel[i]);
		}
		return leftPanel;
	}

	private JPanel getNewPlayerTeamPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4,1));
		panel.setBackground(Color.BLACK);
		panel.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.WHITE));
		return panel;
	}

	private JLabel getNameLabelForMonster(int monsterIndex) {
		Monster monster = this.gc.getMonsterFromTeamByIndex(monsterIndex);
		JLabel nameLabel = new JLabel();
		nameLabel.setForeground(Color.WHITE);
		nameLabel.setText(monster.getName());
		return nameLabel;
	}

	// TODO: Add drag and drop
	private JPanel getRightPanel() {
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(4,1));
		rightPanel.setBackground(Color.BLACK);
		rightPanel.setBounds(680,70,120,430);
		rightPanel.setBorder(BorderFactory.createMatteBorder(1, 3, 1, 3, Color.WHITE));

		for (int i=0; i < 4; i++) {
			monsterPanel[i] = new JPanel();
			monsterPanel[i].setLayout(new GridLayout(4,1));
			monsterPanel[i].setBackground(Color.BLACK);
			monsterPanel[i].setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.WHITE));

			JLabel monster = new JLabel("Gen Monster here");
			monster.setForeground(Color.WHITE);

			monsterPanel[i].add(monster);
			rightPanel.add(monsterPanel[i]);
		}
		return rightPanel;
	}

	/**
	 * Create and return a new centerPanel. This is a panel template.
	 * <br>
	 * CenterPanel: (A panel which will be shown in the center of the mainFrame)
	 * @return a new CenterPanel(A panel which will be shown in the middle of the mainFrame)
	 */
	private JPanel getNewCenterPanel() {
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(null);
		centerPanel.setBackground(Color.BLACK);
		centerPanel.setBounds(120,70,560,280);
		return centerPanel;
	}

	/**
	 * Create and return the bottomPanel. This is a panel template.
	 * <br>
	 * BottomPanel: (A panel which will be shown at the bottom of the mainFrame)
	 * @return a newly created bottomPanel.
	 */
	private JPanel getNewBottomPanel() {
		// startingPanel
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(null);
		bottomPanel.setBackground(Color.BLACK);
		bottomPanel.setBounds(120,350,560,150);
		bottomPanel.setBorder(BorderFactory.createMatteBorder(3, 0, 3, 0, Color.WHITE));
		return bottomPanel;
	}

	/* JTextPane */
	/**
	 * This function will create and return a textPane so that we can display information on the bottomPanel.
	 *
	 * @return a textPane(JTextPane)
	 */
	private JTextPane getBottomMainTextPane() {
		// textPane (bottomMainPanel component)
		// textPane for displaying text
		JTextPane textPane = new JTextPane();
		textPane.setText("Welcome Player!");
		textPane.setForeground(Color.WHITE);
		textPane.setBackground(Color.BLACK);
		textPane.setFont(new Font("Serif", Font.PLAIN, 25));
		// as bottomPanel has border. We increase x and y by 3. Decrease Width and height accordingly.
		textPane.setBounds(3,3,554,112);
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
		JLabel goldPoints = new JLabel("", SwingConstants.CENTER);
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
	 * This function will return the title of the centerPanel.
	 *
	 * @return a title(JLabel)
	 */
	private JLabel getTitle(String title) {
		// title (BagPanel/ShopPanel/SettingsPanel/SellPanel/BuyPanel component)
		JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
		titleLabel.setFont(new Font("Serif", Font.PLAIN, 30));
		titleLabel.setBounds(230, 0, 100, 35);
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setBackground(Color.BLACK);
		titleLabel.setOpaque(true);
		// return titleLabel
		return titleLabel;
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
		continueButton.setBounds(420, 115, 120, 25);
		continueButton.setBackground(Color.WHITE);
		continueButton.setForeground(Color.BLACK);
		continueButton.setFocusable(false);
		// return continueBtn
		return continueButton;
	}

	/**
	 * Create and return a backBtn. MainPanel/BottomMainPanel will be shown once this btn in being clicked.
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

	/**
	 * Create and return a sellBtn for BottomShopPanel.
	 *
	 * @return a buyBtn
	 */
	private JButton getBuyBtn() {
		// create a buyBtn (BottomShopPanel component)
		JButton buyBtn = new JButton();
		buyBtn.setText("Buy");
		buyBtn.setFont(new Font("Arial", Font.PLAIN, 25));
		buyBtn.setBounds(45, 50, 210, 50);
		buyBtn.setBackground(Color.BLACK);
		buyBtn.setForeground(Color.WHITE);
		buyBtn.setFocusable(false);
		buyBtn.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
		// return
		return buyBtn;
	}

	/**
	 * Create and return a sellBtn for BottomShopPanel.
	 *
	 * @return a sellBtn.
	 */
	private JButton getSellBtn() {
		// create a sellBtn(BottomShopPanel component)
		JButton sellBtn = new JButton();
		sellBtn.setText("Sell");
		sellBtn.setFont(new Font("Arial", Font.PLAIN, 25));
		sellBtn.setBounds(305, 50, 210, 50);
		sellBtn.setBackground(Color.BLACK);
		sellBtn.setForeground(Color.WHITE);
		sellBtn.setFocusable(false);
		sellBtn.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
		// return
		return sellBtn;
	}

	/**
	 * Create and return a saveBtn for BottomSettingsPanel.
	 *
	 * @return a saveBtn(JButton)
	 */
	private JButton getSaveBtn() {
		// create a saveBtn (BottomSettingsPanel component)
		JButton saveBtn = new JButton();
		saveBtn.setText("Save");
		saveBtn.setFont(new Font("Arial", Font.PLAIN, 25));
		saveBtn.setBounds(45, 50, 210, 50);
		saveBtn.setBackground(Color.BLACK);
		saveBtn.setForeground(Color.WHITE);
		saveBtn.setFocusable(false);
		saveBtn.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
		// return
		return saveBtn;
	}

	/**
	 * Create and return a restartBtn for BottomSettingsPanel.
	 *
	 * @return a restartBtn(JButton)
	 */
	private JButton getRestartBtn() {
		// create a restartBtn (BottomSettingsPanel component)
		JButton restartBtn = new JButton();
		restartBtn.setText("Restart");
		restartBtn.setFont(new Font("Arial", Font.PLAIN, 25));
		restartBtn.setBounds(305, 50, 210, 50);
		restartBtn.setBackground(Color.BLACK);
		restartBtn.setForeground(Color.WHITE);
		restartBtn.setFocusable(false);
		restartBtn.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
		// return
		return restartBtn;
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
		addTopBtnListener(bagButton, TopBtn.BAG);
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
		addTopBtnListener(shopButton, TopBtn.SHOP);
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
		addTopBtnListener(settingsButton, TopBtn.SETTINGS);
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
			showCenterPanelOf(TopBtn.MAIN);
			showBottomPanelOf(TopBtn.MAIN);
		});
	}

	/**
	 * CenterBtn listener. Show panel when button selected, show the mainPanel otherwise.
	 *
	 * @param b a JButton (From one of the top buttons)
	 */
	private void addTopBtnListener(JToggleButton b, TopBtn tBtn) {
		b.addItemListener(e -> {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				showCenterPanelOf(tBtn);
				showBottomPanelOf(tBtn);
			} else if (e.getStateChange() == ItemEvent.DESELECTED) {
				showBottomPanelOf(TopBtn.MAIN);
				showBottomPanelOf(TopBtn.MAIN);
			}
		});
	}

	/*
	Other functions
	 */
	/**
	 * This function will hide all center panels first and show the selected panel.
	 *
	 * @param tBtn an enum type represents the type of the top button. (MAIN, BAG, SHOP, SETTINGS)
	 */
	private void showCenterPanelOf(TopBtn tBtn) {
		// hide all center panels
		this.centerMainPanel.setVisible(false);
		this.centerBagPanel.setVisible(false);
		this.centerShopPanel.setVisible(false);
		this.centerSettingsPanel.setVisible(false);
		// show one panel
		switch(tBtn) {
			case MAIN -> this.centerMainPanel.setVisible(true);
			case BAG -> this.centerBagPanel.setVisible(true);
			case SHOP -> this.centerShopPanel.setVisible(true);
			case SETTINGS -> this.centerSettingsPanel.setVisible(true);
		}
	}

	/**
	 * This function will hide all bottom panels first and show the selected panel.
	 *
	 * @param tBtn an enum type represents the type of the top button. (MAIN, BAG, SHOP, SETTINGS)
	 */
	private void showBottomPanelOf(TopBtn tBtn) {
		// hide all bottom panels
		this.bottomMainPanel.setVisible(false);
		this.bottomBagPanel.setVisible(false);
		this.bottomShopPanel.setVisible(false);
		this.bottomSettingsPanel.setVisible(false);
		// show one panel
		switch(tBtn) {
			case MAIN -> this.bottomMainPanel.setVisible(true);
			case BAG -> this.bottomBagPanel.setVisible(true);
			case SHOP -> this.bottomShopPanel.setVisible(true);
			case SETTINGS -> this.bottomSettingsPanel.setVisible(true);
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

	// observer
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("Receive new update");
	}

	/* initialization */
	/**
	 * Create a bottomMainPanel and store it into a variable.
	 * <br>
	 * This function will add components to the panel.
	 */
	private void initBottomMainPanel() {
		// *******************************************************************
		// * Welcome Player                                                  * ----> TextPane
		// *                                                                 *
		// *******************************************************************
		// create a bottomPanel
		this.bottomMainPanel = getNewBottomPanel();
		// add components to the startingPanel
		this.bottomMainPanel.add(getBottomMainTextPane());
		this.bottomMainPanel.add(getContinueButton());
	}

	/**
	 * Create a bottomBagPanel and store it into a variable
	 */
	private void initBottomBagPanel() {
		// bottomBagPanel
		this.bottomBagPanel = getNewBottomPanel();
		// set it to not visible (Default)
		this.bottomBagPanel.setVisible(false);
	}

	/**
	 * Create a bottomShopPanel and store it into a variable
	 * <br>
	 * This function will add components to the panel.
	 */
	private void initBottomShopPanel() {
		// bottomShopPanel
		this.bottomShopPanel = getNewBottomPanel();
		// add components to panel
		this.bottomShopPanel.add(getBuyBtn());
		this.bottomShopPanel.add(getSellBtn());
		// set it to not visible (Default)
		this.bottomShopPanel.setVisible(false);
	}

	/**
	 * Create a bottomSettingsPanel and store it into a variable
	 * <br>
	 * This function will add components to the panel.
	 */
	private void initBottomSettingsPanel() {
		// bottomSettingsPanel
		this.bottomSettingsPanel = getNewBottomPanel();
		// add components to panel
		this.bottomSettingsPanel.add(getSaveBtn());
		this.bottomSettingsPanel.add(getRestartBtn());
		// set it to not visible (Default)
		this.bottomSettingsPanel.setVisible(false);
	}

	/**
	 * Create a centerPanel(MainPanel in this case) and store the reference into a variable.
	 * <br>
	 * This function will add components to the panel.
	 */
	private void initCenterMainPanel() {
		// centerMainPanel
		this.centerMainPanel = getNewCenterPanel();
		// add components to panel
		this.centerMainPanel.add(getTitle("Main"));
	}

	/**
	 * Create a centerPanel(BagPanel in this case) and store the reference into a variable.
	 * <br>
	 * This function will add components to the panel.
	 */
	private void initCenterBagPanel() {
		// create a bagPanel
		this.centerBagPanel = getNewCenterPanel();
		// add component
		this.centerBagPanel.add(getTitle("Bag"));
		this.centerBagPanel.add(backButton());
		// set it to not visible (Default)
		this.centerBagPanel.setVisible(false);
	}

	/**
	 * Create a centerPanel(ShopPanel in this case) and store the reference into a variable.
	 * <br>
	 * This function will add components to the panel.
	 */
	private void initCenterShopPanel() {
		// create a shopPanel (This panel will be stored in a variable)
		this.centerShopPanel = getNewCenterPanel();
		// add component
		this.centerShopPanel.add(getTitle("Shop"));
		this.centerShopPanel.add(backButton());
		// set it to not visible (Default)
		this.centerShopPanel.setVisible(false);
	}

	/**
	 * Create a centerPanel(SettingsPanel in this case) and store the reference into a variable.
	 * <br>
	 * This function will add components to the panel.
	 */
	private void initCenterSettingsPanel() {
		// create a settingsPanel
		this.centerSettingsPanel = getNewCenterPanel();
		// add component
		this.centerSettingsPanel.add(getTitle("Settings"));
		this.centerSettingsPanel.add(backButton());
		// set it to not visible (Default)
		this.centerSettingsPanel.setVisible(false);
	}
}

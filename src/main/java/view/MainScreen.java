package main.java.view;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.HashMap;
import java.util.Map;
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
	private final JPanel[] enemyMonsterPanel = new JPanel[4];
	private JFrame mainFrame;
	private ButtonGroup topGroup;
	// variable (Toggle visibility)
	// centerPanel
	private final Map<CenterPanel, JPanel> centerPanelMap;
	// bottomPanel
	private final Map<BottomPanel, JPanel> bottomPanelMap;
	// enum
	private enum CenterPanel {
		MAIN, BAG, SHOP, SETTINGS
	}
	// enum
	private enum BottomPanel {
		MAIN, BAG, SHOP, SETTINGS
	}


	/**
	 * MainScreen's constructor. Initialize and show the mainScreen.
	 *
	 * @param gc gameController.
	 */
	public MainScreen(GameController gc) {
		centerPanelMap = new HashMap<>();
		bottomPanelMap = new HashMap<>();
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
		addCenterPanelToFrame(this.mainFrame);
		addBottomPanelToFrame(this.mainFrame);
	}

	private void addCenterPanelToFrame(JFrame frame) {
		// center
		JPanel centerMainPanel = getCenterMainPanel();
		JPanel centerBagPanel = getCenterBagPanel();
		JPanel centerShopPanel = getCenterShopPanel();
		JPanel centerSettingsPanel = getCenterSettingsPanel();
		// store them to map
		// center
		this.centerPanelMap.put(CenterPanel.MAIN, centerMainPanel);
		this.centerPanelMap.put(CenterPanel.BAG, centerBagPanel);
		this.centerPanelMap.put(CenterPanel.SHOP, centerShopPanel);
		this.centerPanelMap.put(CenterPanel.SETTINGS, centerSettingsPanel);
		// add center panel to frame
		for (JPanel panel : centerPanelMap.values()) {
			frame.getContentPane().add(panel);
		}

	}

	private void addBottomPanelToFrame(JFrame frame) {
		// bottom
		JPanel bottomMainPanel = getBottomMainPanel();
		JPanel bottomBagPanel = getBottomBagPanel();
		JPanel bottomShopPanel = getBottomShopPanel();
		JPanel bottomSettingsPanel = getBottomSettingsPanel();
		// store them to map
		// bottom
		this.bottomPanelMap.put(BottomPanel.MAIN, bottomMainPanel);
		this.bottomPanelMap.put(BottomPanel.BAG, bottomBagPanel);
		this.bottomPanelMap.put(BottomPanel.SHOP, bottomShopPanel);
		this.bottomPanelMap.put(BottomPanel.SETTINGS, bottomSettingsPanel);
		// add bottom panel to frame
		for (JPanel panel : bottomPanelMap.values()) {
			frame.getContentPane().add(panel);
		}
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
		newTopPanel.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.WHITE));
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

	/**
	 * Create and return the leftPanel. This panel will contain several playerTeamPanel.
	 * <br>
	 * This panel will be shown on the left side of the mainFrame.
	 *
	 * @return a leftPanel
	 */
	private JPanel getLeftPanel() {
		// create a leftPanel
		JPanel leftPanel = new JPanel();
		leftPanel.setBounds(0,70,120,430);
		leftPanel.setLayout(new GridLayout(this.playerTeamPanel.length,1));
		leftPanel.setBackground(Color.BLACK);
		leftPanel.setBorder(BorderFactory.createMatteBorder(1, 3, 1, 3, Color.WHITE));
		// add playerTeamPanel
		for (int i=0; i < this.playerTeamPanel.length; i++) {
			JPanel panel = getNewPlayerTeamPanel();
			// add name label if we have monster in the team
			if (i < this.gc.getMonsterTeamMember().size()) {
				panel.add(getNameLabelForMonster(i));
			}
			// store the reference of the panel into a list.
			this.playerTeamPanel[i] = panel;
			// add playerTeamPanel into leftPanel
			leftPanel.add(this.playerTeamPanel[i]);
		}
		return leftPanel;
	}

	/**
	 * Create and return a rightPanel. This panel will contain several enemyMonsterPanels.
	 * <br>
	 * This panel will be shown on the right side of the mainFrame.
	 *
	 * @return a rightPanel
	 */
	private JPanel getRightPanel() {
		// create a rightPanel
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(4,1));
		rightPanel.setBackground(Color.BLACK);
		rightPanel.setBounds(680,70,120,430);
		rightPanel.setBorder(BorderFactory.createMatteBorder(1, 3, 1, 3, Color.WHITE));
		// add enemyMonsterPanel
		for (int i=0; i < 4; i++) {
			JPanel panel = getNewEnemyMonsterPanel();
			// add label
			panel.add(getEnemyMonsterPanelLabel());
			// store reference in to a list
			this.enemyMonsterPanel[i] = panel;
			// add enemyMonsterPanel into the rightPanel
			rightPanel.add(this.enemyMonsterPanel[i]);
		}
		return rightPanel;
	}

	/**
	 * Create and return a new player team panel. This panel will be part of the left panel.
	 * <br>
	 * This is a template.
	 *
	 * @return a new player team panel
	 */
	private JPanel getNewPlayerTeamPanel() {
		// (leftPanel component)
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4,1));
		panel.setBackground(Color.BLACK);
		panel.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.WHITE));
		return panel;
	}

	/**
	 * Create and return a new enemy monster panel. This panel will be part of the right panel.
	 * <br>
	 * This is a template.
	 *
	 * @return a enemy monster panel.
	 */
	private JPanel getNewEnemyMonsterPanel() {
		// (rightPanel component)
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4,1));
		panel.setBackground(Color.BLACK);
		panel.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.WHITE));
		return panel;
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

	/**
	 * Create a bottomMainPanel and store it into a variable.
	 * <br>
	 * This function will add components to the panel.
	 */
	private JPanel getBottomMainPanel() {
		// *******************************************************************
		// * Welcome Player                                                  * ----> TextPane
		// *                                                                 *
		// *******************************************************************
		// create a bottomPanel
		JPanel bottomMainPanel = getNewBottomPanel();
		// add components to the startingPanel
		bottomMainPanel.add(getBottomMainTextPane());
		bottomMainPanel.add(getContinueButton());
		return bottomMainPanel;
	}

	/**
	 * Create a bottomBagPanel and store it into a variable
	 */
	private JPanel getBottomBagPanel() {
		// bottomBagPanel
		JPanel bottomBagPanel = getNewBottomPanel();
		// set it to not visible (Default)
		bottomBagPanel.setVisible(false);
		return bottomBagPanel;
	}

	/**
	 * Create a bottomShopPanel and store it into a variable
	 * <br>
	 * This function will add components to the panel.
	 */
	private JPanel getBottomShopPanel() {
		// bottomShopPanel
		JPanel bottomShopPanel = getNewBottomPanel();
		// add components to panel
		bottomShopPanel.add(getBuyBtn());
		bottomShopPanel.add(getSellBtn());
		// set it to not visible (Default)
		bottomShopPanel.setVisible(false);
		return bottomShopPanel;
	}

	/**
	 * Create a bottomSettingsPanel and store it into a variable
	 * <br>
	 * This function will add components to the panel.
	 */
	private JPanel getBottomSettingsPanel() {
		// bottomSettingsPanel
		JPanel bottomSettingsPanel = getNewBottomPanel();
		// add components to panel
		bottomSettingsPanel.add(getSaveBtn());
		bottomSettingsPanel.add(getRestartBtn());
		// set it to not visible (Default)
		bottomSettingsPanel.setVisible(false);
		return bottomSettingsPanel;
	}

	/**
	 * Create a centerPanel(MainPanel in this case) and store the reference into a variable.
	 * <br>
	 * This function will add components to the panel.
	 */
	private JPanel getCenterMainPanel() {
		// centerMainPanel
		JPanel centerMainPanel = getNewCenterPanel();
		// add components to panel
		centerMainPanel.add(getTitle("Main"));
		return centerMainPanel;
	}

	/**
	 * Create a centerPanel(BagPanel in this case) and store the reference into a variable.
	 * <br>
	 * This function will add components to the panel.
	 */
	private JPanel getCenterBagPanel() {
		// create a bagPanel
		JPanel centerBagPanel = getNewCenterPanel();
		// add component
		centerBagPanel.add(getTitle("Bag"));
		centerBagPanel.add(backButton());
		// set it to not visible (Default)
		centerBagPanel.setVisible(false);
		return centerBagPanel;
	}

	/**
	 * Create a centerPanel(ShopPanel in this case) and store the reference into a variable.
	 * <br>
	 * This function will add components to the panel.
	 */
	private JPanel getCenterShopPanel() {
		// create a shopPanel (This panel will be stored in a variable)
		JPanel centerShopPanel = getNewCenterPanel();
		// add component
		centerShopPanel.add(getTitle("Shop"));
		centerShopPanel.add(backButton());
		// set it to not visible (Default)
		centerShopPanel.setVisible(false);
		return centerShopPanel;
	}

	/**
	 * Create a centerPanel(SettingsPanel in this case) and store the reference into a variable.
	 * <br>
	 * This function will add components to the panel.
	 */
	private JPanel getCenterSettingsPanel() {
		// create a settingsPanel
		JPanel centerSettingsPanel = getNewCenterPanel();
		// add component
		centerSettingsPanel.add(getTitle("Settings"));
		centerSettingsPanel.add(backButton());
		// set it to not visible (Default)
		centerSettingsPanel.setVisible(false);
		return centerSettingsPanel;
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
	 * Create and return the name label for player monster.
	 *
	 * @param monsterIndex an index represent the location of the monster in the team.
	 * @return a JLabel with the monster's name on it.
	 */
	private JLabel getNameLabelForMonster(int monsterIndex) {
		// NameLabel (playerTeamPanel component)
		// get monster by index
		Monster monster = this.gc.getMonsterFromTeamByIndex(monsterIndex);
		// create the nameLabel
		JLabel nameLabel = new JLabel();
		nameLabel.setForeground(Color.WHITE);
		// get and set the monster's name
		nameLabel.setText(monster.getName());
		return nameLabel;
	}

	/**
	 * Create and return the Label for the enemy monster panel.
	 *
	 * @return a JLabel with the text on it.
	 */
	private JLabel getEnemyMonsterPanelLabel() {
		JLabel label = new JLabel("Gen Monster here");
		label.setForeground(Color.WHITE);
		return label;
	}

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
		bindBtnToPanel(bagButton, CenterPanel.BAG, BottomPanel.BAG);
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
		bindBtnToPanel(shopButton, CenterPanel.SHOP, BottomPanel.SHOP);
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
		bindBtnToPanel(settingsButton, CenterPanel.SETTINGS, BottomPanel.SETTINGS);
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
		});
	}

	/**
	 * CenterBtn listener. Show panel when button selected, show the mainPanel otherwise.
	 *
	 * @param b a JButton (From one of the top buttons)
	 */
	private void bindBtnToPanel(JToggleButton b, CenterPanel cP, BottomPanel bp) {
		b.addItemListener(e -> {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				showCenterPanel(cP);
				showBottomPanel(bp);
			} else if (e.getStateChange() == ItemEvent.DESELECTED) {
				showCenterPanel(CenterPanel.MAIN);
				showBottomPanel(BottomPanel.MAIN);
			}
		});
	}

	/*
	Other functions
	 */
	/**
	 * This function will hide all center panels first and show the selected panel.
	 *
	 * @param cP an enum value from this.CenterPanel (MAIN, BAG, SHOP, SETTINGS)
	 */
	private void showCenterPanel(CenterPanel cP) {
		// hide all centerPanel
		for (JPanel centerPanel : centerPanelMap.values()) {
			centerPanel.setVisible(false);
		}
		// show one panel
		centerPanelMap.get(cP).setVisible(true);
	}

	/**
	 * This function will hide all bottom panels first and show the selected panel.
	 *
	 * @param bP an enum value from this.BottomPanel (MAIN, BAG, SHOP, SETTINGS)
	 */
	private void showBottomPanel(BottomPanel bP) {
		// hide all bottom panels
		for (JPanel bottomPanel : bottomPanelMap.values()) {
			bottomPanel.setVisible(false);
		}
		// show one panel
		bottomPanelMap.get(bP).setVisible(true);
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
}

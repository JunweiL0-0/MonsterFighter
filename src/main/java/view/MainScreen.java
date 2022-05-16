package main.java.view;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import main.java.controller.GameController;
import main.java.model.Monster;
import main.java.model.Team;
import main.java.utilities.Observable;
import main.java.utilities.Observer;
import main.java.utilities.GamePanel;

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
	// PanelMap (Toggle visibility)
	// centerPanel
	private final Map<CenterPanel, JPanel> centerPanelMap;
	// bottomPanel
	private final Map<BottomPanel, JPanel> bottomPanelMap;
	// leftPanel, rightPanel
	private JPanel leftPanel;
	private JPanel rightPanel;
	// enum
	private enum CenterPanel {
		MAIN, BAG, SHOP, SETTINGS, BATTLE
	}
	// enum
	private enum BottomPanel {
		MAIN, BAG, SHOP, SETTINGS, BATTLE
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
		// subscribe the gameController
		this.gc.addObserver(this);
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
		// *            *  CenterPanel(Main/Bag/Shop/Settings)  *            *
		// *            *                                       *            *
		// *            *                                       *            *
		// *            *****************************************            *
		// *            *  BottomPanel(Main/Bag/Shop/Settings)  *            *
		// *            *                                       *            *
		// *******************************************************************
		this.mainFrame = getMainFrame();
		// Top, Left, Right
		this.leftPanel = getLeftPanel();
		this.rightPanel = getRightPanel();
		this.mainFrame.getContentPane().add(getTopPanel());
		this.mainFrame.getContentPane().add(this.leftPanel);
		this.mainFrame.getContentPane().add(this.rightPanel);
		// Center and bottom panel
		addCenterPanelToFrame(this.mainFrame);
		addBottomPanelToFrame(this.mainFrame);
	}

	/**
	 * This function will hide all center panels first and show the selected panel.
	 *
	 * @param cP an enum value from this.CenterPanel (MAIN, BAG, SHOP, SETTINGS)
	 */
	private void showCenterPanel(CenterPanel cP) {
		// Loop through the map and hide all centerPanel
		for (JPanel centerPanel : centerPanelMap.values()) {
			centerPanel.setVisible(false);
		}
		// show one panel
		centerPanelMap.get(cP).setVisible(true);
		centerPanelMap.get(cP).requestFocus();
	}

	/**
	 * This function will hide all bottom panels first and show the selected panel.
	 *
	 * @param bP an enum value from this.BottomPanel (MAIN, BAG, SHOP, SETTINGS)
	 */
	private void showBottomPanel(BottomPanel bP) {
		// Loop through the map and hide all centerPanel and hide all bottom panels
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
		if (((GameController)o).isEncounteredBattle()) {
			updateBottomMainPanel();
			updateRightPanel();
		}
		if (((GameController)o).isUpdateTeam()) {
			updateLeftPanel();
		}
		System.out.println("Receive new update");
	}

	public void updateBottomMainPanel() {
		JPanel bottomMainPanel = bottomPanelMap.get(BottomPanel.MAIN);
		bottomMainPanel.removeAll();
		JButton reorderBtn = getReorderBtn();
		JButton runBtn = getRunBtn();
		JButton fightBtn = getFightBtn();
		reorderBtn.setEnabled(this.gc.isAbleToReorderTeam());
		runBtn.setEnabled(this.gc.isAbleToStartFight());
		fightBtn.setEnabled(this.gc.isAbleToStartFight());
		// add components
		bottomMainPanel.add(reorderBtn);
		bottomMainPanel.add(runBtn);
		bottomMainPanel.add(fightBtn);
		// repaint
		bottomMainPanel.revalidate();
		bottomMainPanel.repaint();
	}

	private void updateRightPanel() {
		this.rightPanel.removeAll();
		Team enemyTeam = this.gc.getEnemyTeam();
		// add enemyMonsterPanel
		for (int i=0; i < enemyTeam.size(); i++) {
			Monster monster = enemyTeam.getMonsterByIndex(i);
			JPanel panel = getNewEnemyMonsterPanel();
			// add label
			panel.add(getMonsterOrderLabel(i));
			panel.add(autoResizeFont(getMonsterNameLabel(monster)));
			panel.add(getMonsterLevelLabel(monster));
			panel.add(getLabelWithMonsterImage(monster));
			panel.add(autoResizeFont(getMonsterHealthLabel()));
			panel.add(autoResizeFont(getMonsterDamageAndShieldLabel(monster)));
			panel.add(autoResizeFont(getExpLabel()));
			panel.add(getMonsterHealthBar(monster));
			panel.add(getMonsterExpBar(monster));
			// store reference in to a list
			this.enemyMonsterPanel[i] = panel;
			// add enemyMonsterPanel into the rightPanel
			this.rightPanel.add(this.enemyMonsterPanel[i]);
		}
		this.rightPanel.revalidate();
		this.rightPanel.repaint();
	}

	private void updateLeftPanel() {

	}

	/**
	 * Store centerPanel into this.CenterPanelMap and add the panel to the frame.
	 *
	 * @param frame a JFrame. (mainScreen in this case)
	 */
	private void addCenterPanelToFrame(JFrame frame) {
		// center
		// store them to map
		// center
		this.centerPanelMap.put(CenterPanel.MAIN, getCenterMainPanel());
		this.centerPanelMap.put(CenterPanel.BAG, getCenterBagPanel());
		this.centerPanelMap.put(CenterPanel.SHOP, getCenterShopPanel());
		this.centerPanelMap.put(CenterPanel.SETTINGS, getCenterSettingsPanel());
		this.centerPanelMap.put(CenterPanel.BATTLE, getCenterBattlePanel());
		// add center panel to frame
		for (JPanel panel : centerPanelMap.values()) {
			frame.getContentPane().add(panel);
		}
	}

	/**
	 * Store bottomPale into this.BottomPanel and add the panel to the frame.
	 *
	 * @param frame a JFrame. (mainScreen in this case)
	 */
	private void addBottomPanelToFrame(JFrame frame) {
		// bottom
		// store them to map
		// bottom
		this.bottomPanelMap.put(BottomPanel.MAIN, getBottomMainPanel());
		this.bottomPanelMap.put(BottomPanel.BAG, getBottomBagPanel());
		this.bottomPanelMap.put(BottomPanel.SHOP, getBottomShopPanel());
		this.bottomPanelMap.put(BottomPanel.SETTINGS, getBottomSettingsPanel());
		this.bottomPanelMap.put(BottomPanel.BATTLE, getBottomBattlePanel());
		// add bottom panel to frame
		for (JPanel panel : bottomPanelMap.values()) {
			frame.getContentPane().add(panel);
		}
	}

	//***************************************************************************************************************
	//
	//**********************   Functions below are used to create Swing components  *********************************
	//******************** They are seperated into different group for reading purpose ******************************
	//**  JFrame: 177, JPanel: 198, JTextPane: 520, JLabel: 541, JButton: 632, JToggleButton: 750, Listeners: 811  **
	//********************************** Warning: Lots of reading! **************************************************
	//
	//***************************************************************************************************************

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
		this.topGroup = new ButtonGroup();
		this.topGroup.add(bagButton);
		this.topGroup.add(shopButton);
		this.topGroup.add(settingsButton);
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
		// **************
		// * LeftPanel  *
		// *            *
		// *            *
		// *            *
		// *            *
		// *            *
		// *            *
		// *            *
		// **************
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
				Monster monster = this.gc.getMonsterFromTeamByIndex(i);
				panel.add(getMonsterOrderLabel(i));
				panel.add(autoResizeFont(getMonsterNameLabel(monster)));
				panel.add(getMonsterLevelLabel(monster));
				panel.add(getLabelWithMonsterImage(monster));
				panel.add(autoResizeFont(getMonsterHealthLabel()));
				panel.add(autoResizeFont(getMonsterDamageAndShieldLabel(monster)));
				panel.add(autoResizeFont(getExpLabel()));
				panel.add(getMonsterHealthBar(monster));
				panel.add(getMonsterExpBar(monster));
			}
			// store the reference of the panel into a list.
			this.playerTeamPanel[i] = panel;
			// add playerTeamPanel into leftPanel
			leftPanel.add(this.playerTeamPanel[i]);
		}
		return leftPanel;
	}

	private JLabel getMonsterOrderLabel(int orderNum) {
		JLabel order = new JLabel(""+Integer.toString(orderNum+1));
		order.setForeground(Color.white);
		order.setBounds(105, 2, 10, 10);
		return order;
	}

	private JLabel getLabelWithMonsterImage(Monster monster) {
		// resize the image
		Image image = monster.getImageIcon().getImage().getScaledInstance(50, 40, java.awt.Image.SCALE_SMOOTH);
		ImageIcon monsterImage = new ImageIcon(image);
		JLabel labelWithMonsterImg = new JLabel(monsterImage);
		//set bounds for label with monster Image
		labelWithMonsterImg.setBounds(0, 0, 50, 40);
		return labelWithMonsterImg;
	}

	private JLabel getMonsterNameLabel(Monster monster) {
		//add the monster name
		JLabel monsterName = getNameLabelForMonster(monster);
		monsterName.setBounds(53, 14, 57, 15);
		return monsterName;
	}

	private JLabel getMonsterLevelLabel(Monster monster) {
		//add the level on leftPanel
		JLabel levelLabel = new JLabel("LVL: "+monster.getLevel());
		levelLabel.setBounds(53, 30, 50, 10);
		levelLabel.setFont(new Font("Arial", Font.PLAIN, 10));
		levelLabel.setForeground(Color.white);
		return levelLabel;
	}

	private JLabel getMonsterHealthLabel() {
		JLabel health = new JLabel("HP");
		health.setBounds(6, 45, 40, 10);
		health.setForeground(Color.white);
		return health;
	}

	private JLabel getMonsterDamageAndShieldLabel(Monster monster) {
		JLabel damageAndShield = new JLabel("DMG: " + monster.getDamage() + "    SHD: N");
		damageAndShield.setBounds(2, 85, 128, 10);
		damageAndShield.setForeground(Color.white);
		return damageAndShield;
	}

	private JLabel getExpLabel() {
		JLabel exp = new JLabel("EXP");
		exp.setBounds(2, 65, 40, 10);
		exp.setForeground(Color.white);
		return exp;
	}

	/**
	 * Create and return a rightPanel. This panel will contain several enemyMonsterPanels.
	 * <br>
	 * This panel will be shown on the right side of the mainFrame.
	 *
	 * @return a rightPanel
	 */
	private JPanel getRightPanel() {
		// **************
		// * RightPanel *
		// *            *
		// *            *
		// *            *
		// *            *
		// *            *
		// *            *
		// *            *
		// **************
		// create a rightPanel
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(4,1));
		rightPanel.setBackground(Color.BLACK);
		rightPanel.setBounds(680,70,120,430);
		rightPanel.setBorder(BorderFactory.createMatteBorder(1, 3, 1, 3, Color.WHITE));
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
		panel.setLayout(null);
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
		// Template
		// (rightPanel component)
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.BLACK);
		panel.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.WHITE));
		return panel;
	}

	/**
	 * Create and return a new centerPanel.
	 * <br>
	 * CenterPanel: (A panel which will be shown in the center of the mainFrame)
	 * <br>
	 * This is a template.
	 *
	 * @return a new CenterPanel(A panel which will be shown in the middle of the mainFrame)
	 */
	private JPanel getNewCenterPanel() {
		// Template
		// This panel will be shown in the middle of the mainFrame
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
	 * <br>
	 * This is a template.
	 *
	 * @return a newly created bottomPanel.
	 */
	private JPanel getNewBottomPanel() {
		// Template
		// This panel will be shown in the bottom of the mainFrame
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
		// *                                                  ContinueBtn    * ----> ContinueBtn
		// *******************************************************************
		// create a bottomPanel
		JPanel bottomMainPanel = getNewBottomPanel();
		// add components to the startingPanel
		bottomMainPanel.add(getReorderBtn());
		bottomMainPanel.add(getRunBtn());
		bottomMainPanel.add(getFightBtn());
		return bottomMainPanel;
	}

	/**
	 * Create and return a bottomBagPanel. This function is using the bottom panel template function(getNewBottomPanel).
	 */
	private JPanel getBottomBagPanel() {
		// bottomBagPanel
		JPanel bottomBagPanel = getNewBottomPanel();
		// set it to not visible (Default)
		bottomBagPanel.setVisible(false);
		return bottomBagPanel;
	}

	/**
	 * Create a bottomShopPanel. This function is using the bottom panel template function(getNewBottomPanel).
	 * <br>
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
	 * Create a bottomSettingsPanel. This function is using the bottom panel template function(getNewBottomPanel).
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

	private JPanel getBottomFightConfirmPanel() {
		// bottomFightConfirmPanel
		JPanel bottomFightConfirmPanel = getNewBottomPanel();
		// add components to panel
		bottomFightConfirmPanel.add(getFightBtn());
		// set it to not visible (Default)
		bottomFightConfirmPanel.setVisible(false);
		return bottomFightConfirmPanel;
	}

	private JPanel getBottomBattlePanel() {
		// bottom
		JPanel bottomBattlePanel = getNewBottomPanel();
		// add components to panel
		bottomBattlePanel.add(getAttackBtn());
		// set it to not visible (Default)
		bottomBattlePanel.setVisible(false);
		return bottomBattlePanel;
	}

	/**
	 * Create a centerMainPanel. This function is using the bottom panel template function(getNewCenterPanel).
	 */
	private JPanel getCenterMainPanel() {
		// centerMainPanel
		JPanel centerMainPanel = new GamePanel(120,70 , this.gc);
		centerMainPanel.requestFocus();
		return centerMainPanel;
//		JPanel centerMainPanel = getNewCenterPanel();
//		// add components to panel
//		centerMainPanel.add(getCenterPanelTitle("Main"));
//		return centerMainPanel;
	}

	/**
	 * Create a centerBagPanel. This function is using the bottom panel template function(getNewCenterPanel)
	 */
	private JPanel getCenterBagPanel() {
		// create a bagPanel
		JPanel centerBagPanel = getNewCenterPanel();
		// add component
		centerBagPanel.add(getCenterPanelTitle("Bag"));
		centerBagPanel.add(getBackButton());
		// set it to not visible (Default)
		centerBagPanel.setVisible(false);
		return centerBagPanel;
	}

	/**
	 * Create a centerShopPanel. This function is using the bottom panel template function(getNewCenterPanel)
	 */
	private JPanel getCenterShopPanel() {
		// create a shopPanel (This panel will be stored in a variable)
		JPanel centerShopPanel = getNewCenterPanel();
		// add component
		centerShopPanel.add(getCenterPanelTitle("Shop"));
		centerShopPanel.add(getBackButton());
		// set it to not visible (Default)
		centerShopPanel.setVisible(false);
		return centerShopPanel;
	}

	/**
	 * Create a centerSettingsPanel. This function is using the bottom panel template function(getNewCenterPanel)
	 */
	private JPanel getCenterSettingsPanel() {
		// create a settingsPanel
		JPanel centerSettingsPanel = getNewCenterPanel();
		// add component
		centerSettingsPanel.add(getCenterPanelTitle("Settings"));
		centerSettingsPanel.add(getBackButton());
		// set it to not visible (Default)
		centerSettingsPanel.setVisible(false);
		return centerSettingsPanel;
	}

	private JPanel getCenterBattlePanel() {
		// create a centerBattlePanel
		JPanel centerBattlePanel = getNewCenterPanel();
		// add component
		centerBattlePanel.add(getCenterPanelTitle("Battle"));
		// set it to not visible (Default)
		centerBattlePanel.setVisible(false);
		return centerBattlePanel;
	}

	/* JTextPane */
	/**
	 * This function will create and return a textPane so that we can display information on the bottomMainPanel.
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
	 * @param monster a monster instance.
	 * @return a JLabel with the monster's name on it.
	 */
	private JLabel getNameLabelForMonster(Monster monster) {
		// NameLabel (playerTeamPanel component)
		// create the nameLabel
		JLabel nameLabel = new JLabel();
		nameLabel.setForeground(Color.WHITE);
		// get and set the monster's name
		nameLabel.setText(monster.getName());
		return nameLabel;
	}
	
	/* JProgressBar */
	/**
	 * Create and return the health bar for monster
	 * 
	 * @param monster a Monster instance
	 * @return a JProgressBar with the monsters max health
	 */
	private JProgressBar getMonsterHealthBar(Monster monster) {
		JProgressBar healthBar = new JProgressBar(0,monster.getMaxHealth());
		healthBar.setBounds(20,45,90,10);
		healthBar.setValue(monster.getMaxHealth());
		healthBar.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.white));
		healthBar.setForeground(Color.red);
		healthBar.setBackground(Color.black);
		healthBar.setString(Integer.toString(monster.getCurrentHealth()));
		
		return healthBar;
	}
	
	/*JProgressBar */
	/**
	 * Create and return the experience bar for player monster
	 * 
	 * @param monster a Monster instance
	 * @return a JProgressBar with the monsters experience bar
	 */
	private JProgressBar getMonsterExpBar(Monster monster) {
		JProgressBar expBar = new JProgressBar(0,100);
		expBar.setBounds(20,65,90,10);
		expBar.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.white));
		expBar.setForeground(Color.white);
		expBar.setBackground(Color.black);
		expBar.setString(Integer.toString(monster.getCurrentHealth()));
		
		return expBar;
	}
	
	/*JLabel */
	/**
	 * Resize the font for the monster name to fit in the JLabel
	 * 
	 * @param monsterName a JLabel instance for the monsters name
	 * @return a JLabel with the monsterName
	 */
	private JLabel autoResizeFont(JLabel monsterName) {
		Font monsterNameFont = monsterName.getFont();
		String monsterNameText = monsterName.getText();

		int stringWidth = monsterName.getFontMetrics(monsterNameFont).stringWidth(monsterNameText);
		int componentWidth = monsterName.getWidth();

		// Find out how much the font can grow in width.
		double widthRatio = (double)componentWidth / (double)stringWidth;

		int newFontSize = (int)(monsterNameFont.getSize() * widthRatio);
		int componentHeight = monsterName.getHeight();

		// Pick a new font size so it will not be larger than the height of monsterName.
		int fontSizeToUse = Math.min(newFontSize, componentHeight);

		// Set the monsterName's font size to the newly determined size.
		monsterName.setFont(new Font(monsterNameFont.getName(), Font.PLAIN, fontSizeToUse));
		return monsterName;
	}

	/**
	 * Create and return the Label for the enemy monster panel.
	 *
	 * @return a JLabel with the text on it.
	 */
	private JLabel getEnemyMonsterPanelLabel(String monsterName) {
		JLabel label = new JLabel(monsterName);
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
	 * @return a centerPanelTitle(JLabel)
	 */
	private JLabel getCenterPanelTitle(String title) {
		// title (centerBagPanel/centerShopPanel/centerSettingsPanel component)
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
	 * Create and return a ContinueBtn for bottomMainPanel
	 *
	 * @return a continueBtn(JButton)
	 */
	private JButton getContinueButton() {
		// **************
		// *  Continue  *
		// **************
		// continue button (bottomMainPanel component)
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
	private JButton getBackButton() {
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

	private JButton getFightBtn() {
		// create a fightBtn
		JButton fightBtn = new JButton();
		fightBtn.setText("Fight");
		fightBtn.setFont(new Font("Arial", Font.PLAIN, 25));
		fightBtn.setBounds(373, 50, 186, 50);
		fightBtn.setBackground(Color.BLACK);
		fightBtn.setForeground(Color.WHITE);
		fightBtn.setFocusable(false);
		fightBtn.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
		fightBtn.setEnabled(false);
		// listener
		addFightBtnListener(fightBtn);
		return fightBtn;
	}

	private JButton getRunBtn() {
		// create a runBtn
		JButton runBtn = new JButton();
		runBtn.setText("Run");
		runBtn.setFont(new Font("Arial", Font.PLAIN, 25));
		runBtn.setBounds(187, 50, 186, 50);
		runBtn.setBackground(Color.BLACK);
		runBtn.setForeground(Color.WHITE);
		runBtn.setFocusable(false);
		runBtn.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
		runBtn.setEnabled(false);
		// listener
//		addRunBtnListener(runBtn);
		return runBtn;
	}

	private JButton getReorderBtn() {
		// create a reorderBtn
		JButton reorderBtn = new JButton();
		reorderBtn.setText("Reorder");
		reorderBtn.setFont(new Font("Arial", Font.PLAIN, 25));
		reorderBtn.setBounds(1, 50, 186, 50);
		reorderBtn.setBackground(Color.BLACK);
		reorderBtn.setForeground(Color.WHITE);
		reorderBtn.setFocusable(false);
		if (!(this.gc.isAbleToReorderTeam())) {
			reorderBtn.setEnabled(false);
		}
		reorderBtn.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
		// listener
		return reorderBtn;
	}

	private JButton getAttackBtn() {
		JButton attackBtn = new JButton();
		attackBtn.setText("Attack");
		attackBtn.setFont(new Font("Arial", Font.PLAIN, 25));
		attackBtn.setBounds(305, 50, 210, 50);
		attackBtn.setBackground(Color.BLACK);
		attackBtn.setForeground(Color.WHITE);
		attackBtn.setFocusable(false);
		attackBtn.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
		// listener
		addAttackBtnListener(attackBtn);
		return attackBtn;
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
		bindToggleBtnToPanel(bagButton, CenterPanel.BAG, BottomPanel.BAG);
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
		bindToggleBtnToPanel(shopButton, CenterPanel.SHOP, BottomPanel.SHOP);
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
		bindToggleBtnToPanel(settingsButton, CenterPanel.SETTINGS, BottomPanel.SETTINGS);
		return settingsButton;
	}

	/* Listeners */
	/**
	 * BackBtn listener. Deselected all top buttons.
	 *
	 * @param b a backButton from one of the top buttons (JButton)
	 */
	private void addBackBtnListener(JButton b) {
		b.addActionListener(e -> {
			// unSelected all buttons
			this.topGroup.clearSelection();
		});
	}

	/**
	 * Top toggle button listener. Show bind center/bottom panel when button selected, show the mainPanel when deselected.
	 *
	 * @param b a JToggleBtn from one of the topBtn.
	 */
	private void bindToggleBtnToPanel(JToggleButton b, CenterPanel cP, BottomPanel bp) {
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

	private void addFightBtnListener(JButton b) {
		b.addActionListener(e -> {
			showCenterPanel(CenterPanel.BATTLE);
			showBottomPanel(BottomPanel.BATTLE);
		});
	}

	private void addAttackBtnListener(JButton b) {
		b.addActionListener(e -> {

		});
	}
	
//	private void changeOrderOfMonsters() {
//		
//	}
	
}

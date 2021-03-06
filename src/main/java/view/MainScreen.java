package main.java.view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import main.java.controller.GameController;
import main.java.model.GameItem;
import main.java.model.Medicine;
import main.java.model.Monster;
import main.java.model.Shield;
import main.java.model.Shop;
import main.java.model.Weapon;
import main.java.model.Team;
import main.java.utilities.Observable;
import main.java.utilities.Observer;
import main.java.utilities.GamePanel;

/**
 * MainScreen. This is where we play the game. MainScreen is observing the gameController.
 * Everytime when an update happens, MainScreen will be noticed and will be show the update on the screen.
 * MainScreen does not introduce a new thread.
 * Note: MainScreen used gamePanel(introduce new thread) as one of its component. Be careful when you are editing code.
 */
public class MainScreen implements Observer {
	// game controller
	private final GameController gc;
	private final Shop shop;
	// components
	private final JPanel[] playerTeamPanel = new JPanel[4];
	private final JPanel[] enemyMonsterPanel = new JPanel[4];
	private JLabel daysLeftLabel;
	private JLabel battleLeftLabel;
	private JLabel goldLabel;
	private JLabel pointLabel;
	private JFrame mainFrame;
	private ButtonGroup topGroup;
	private ButtonGroup shopButtonGroup;
	// values (Will be used for validation)
	private String newMonsterName;
	private int renameMonsterIndex;
	private int reorderingMonsterIndex1;
	private int reorderingMonsterIndex2;
	// validate input before button being activated
	private JButton reorderConfirmBtn;
	private JButton renameConfirmBtn;
	// PanelMap (Toggle visibility)
	// centerPanel, bottomPanel
	private final Map<CenterPanel, JPanel> centerPanelMap;
	private final Map<BottomPanel, JPanel> bottomPanelMap;
	// leftPanel, rightPanel
	private JPanel leftPanel;
	private JPanel rightPanel;
	// enum
	private enum CenterPanel {
		MAIN, BAG, SHOP, SETTINGS, BATTLE, BUY, SELL, WASTE, WIN, GAME_OVER, RENAME
	}
	// enum
	private enum BottomPanel {
		MAIN, BAG, SHOP, SETTINGS, BATTLE, BUY, SELL, REORDER, EXIST_BATTLE, GAME_OVER, RENAME
	}
	// for the shop refresh
	private ArrayList<JButton> monsterButtons;
	private ArrayList<JButton> weaponButtons;
	private ArrayList<JButton> shieldButtons;
	private ArrayList<JButton> medButtons;


	/**
	 * MainScreen's constructor. Initialize and show the mainScreen.
	 *
	 * @param gc gameController.
	 */
	public MainScreen(GameController gc) {
		this.newMonsterName = "";
		this.renameMonsterIndex = -1;
		this.reorderingMonsterIndex1 = -1;
		this.reorderingMonsterIndex2 = -1;
		this.centerPanelMap = new HashMap<>();
		this.bottomPanelMap = new HashMap<>();
		this.gc = gc;
		this.shop = this.gc.getShop();
		this.monsterButtons = new ArrayList<>();
		this.weaponButtons = new ArrayList<>();
		this.shieldButtons = new ArrayList<>();
		this.medButtons = new ArrayList<>();
		this.gc.getMonsterBag();
		this.gc.getWeaponBag();
		this.gc.getShieldBag();
		this.gc.getMedicineBag();
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

	private void closeAndDestroyCurrentScreen() {
		show(false);
		this.mainFrame.dispose();
	}

	// observer
	@Override
	public void update(Observable o, Object arg) {
		if (((GameController)o).isUpdateGold()) {
			updateGoldLabel();
		}
		if (((GameController)o).isUpdatePoint()) {
			updatePointLabel();
		}
		if (((GameController)o).isNextDay()) {
			updateBottomMainPanel();
			updateLeftPanel();
			updateRightPanel();
			updateGoldLabel();
			updatePointLabel();
			updateDaysLeftLabel();
			updateBattleLeftLabel();
			updateBuyPanel();
		}
		if (((GameController)o).isEncounteredBattle()) {
			updateBottomMainPanel();
			updateRightPanel();
		}
		if (((GameController)o).isUpdateTeam()) {
			updateLeftPanel();
			updateBottomMainPanel();
		}
		if (((GameController)o).isUpdateEnemyTeam()) {
			updateRightPanel();
		}
		if (((GameController)o).isBattleOccur()) {
			updateCenterBattlePanel();
		} else if (((GameController)o).isPlayerWon()) {
			showCenterPanel(CenterPanel.WIN);
			showBottomPanel(BottomPanel.EXIST_BATTLE);
			updateGoldLabel();
			updatePointLabel();
			updateBattleLeftLabel();
		} else if (((GameController)o).isEnemyWon()) {
			showCenterPanel(CenterPanel.WASTE);
			showBottomPanel(BottomPanel.EXIST_BATTLE);
		}
		if(((GameController)o).isRefreshAllPressed() || ((GameController)o).isNextDay()) {
			updateBuyPanel();
		}
		if (((GameController)o).isUpdateBag()) {
			updateBagPanel();
			updateSellPanel();
		}
		if (((GameController)o).isGameOver()) {
			updateCenterGameOverPanel();
			showCenterPanel(CenterPanel.GAME_OVER);
			showBottomPanel(BottomPanel.GAME_OVER);
			for (Enumeration<AbstractButton> buttons = topGroup.getElements(); buttons.hasMoreElements();) {
				AbstractButton button = buttons.nextElement();
				button.setEnabled(false);
			}
		}
	}

	private void updateCenterGameOverPanel() {
		JPanel gameOverPanel = centerPanelMap.get(CenterPanel.GAME_OVER);
		gameOverPanel.removeAll();

		gameOverPanel.setLayout(new GridLayout(4,1));
		gameOverPanel.setBackground(Color.BLACK);
		gameOverPanel.setBounds(120,70,560,280);

		gameOverPanel.add(getGameOverLabel());
		gameOverPanel.add(getFinalGoldLabel());
		gameOverPanel.add(getFinalPointLabel());

		gameOverPanel.revalidate();
		gameOverPanel.repaint();
	}

	private void updateBattleLeftLabel() {
		this.battleLeftLabel.setText("Battles left: " + this.gc.getTotalBattle());
	}

	private void updateGoldLabel() {
		this.goldLabel.setText("Gold: " + this.gc.getGold());
	}
	/**
	 * Updates the number of points on the top left panel.
	 */
	private void updatePointLabel() {
		this.pointLabel.setText("Point: " + this.gc.getPoint());
	}

	/**
	 * Updates the number of days left on the top right panel.
	 */
	private void updateDaysLeftLabel() {
		this.daysLeftLabel.setText("Days left: " + this.gc.getCurrentDay() + "/" + this.gc.getTotalDay());
	}

	/**
	 * Updates the sell panel when the observer is notified of a change.
	 */
	private void updateSellPanel() {
		JPanel sellPanel = centerPanelMap.get(CenterPanel.SELL);
		sellPanel.removeAll();
		sellPanel.add(getCenterPanelTitle("Sell Area"));
		//Add all players item here automatically
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEADING));
		panel.setPreferredSize(new Dimension(560,1000));
		panel.setBackground(Color.black);

		JPopupMenu popMenu = new JPopupMenu();

		JMenuItem[] menuItem = new JMenuItem[5];
		menuItem[0] = new JMenuItem("Sell");

		popMenu.add(menuItem[0]);
		// monster
		for (int i = 0; i< this.gc.getMedicineBag().size() ;i++ ) {
			Medicine item = this.gc.getMedicineBag().get(i);
			JTextArea detail = getItemDetails();
			detail.setText(constructMedicineRefundDetail(item));
			int finalI = i;
			detail.addMouseListener(new MouseListener() {
				public void mouseClicked(MouseEvent e) {
					if(SwingUtilities.isLeftMouseButton(e)) {
						gc.sellItem(item, finalI);
					}
				}
				public void mousePressed(MouseEvent e) {

				}
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
				}
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
				}
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
				}
			});
			panel.add(detail);
		}
		for (int i = 0; i< this.gc.getMonsterBag().size() ;i++ ) {
			Monster item = this.gc.getMonsterBag().get(i);
			JTextArea detail = getItemDetails();
			detail.setText(constructMonsterRefundDetail(item));
			int finalI = i;
			detail.addMouseListener(new MouseListener() {
				public void mouseClicked(MouseEvent e) {
					if(SwingUtilities.isLeftMouseButton(e)) {
						gc.sellItem(item, finalI);
					}
				}
				public void mousePressed(MouseEvent e) {

				}
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
				}
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
				}
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
				}
			});
			panel.add(detail);
		}
		for (int i = 0; i< this.gc.getWeaponBag().size() ;i++ ) {
			Weapon item = this.gc.getWeaponBag().get(i);
			JTextArea detail = getItemDetails();
			detail.setText(constructWeaponRefundDetail(item));
			int finalI = i;
			detail.addMouseListener(new MouseListener() {
				public void mouseClicked(MouseEvent e) {
					if(SwingUtilities.isLeftMouseButton(e)) {
						gc.sellItem(item, finalI);
					}
				}
				public void mousePressed(MouseEvent e) {

				}
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
				}
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
				}
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
				}

			});
			panel.add(detail);
		}
		for (int i = 0; i< this.gc.getShieldBag().size() ;i++ ) {
			Shield item = this.gc.getShieldBag().get(i);
			JTextArea detail = getItemDetails();
			detail.setText(constructShieldRefundDetail(item));
			int finalI = i;
			detail.addMouseListener(new MouseListener() {
				public void mouseClicked(MouseEvent e) {
					if(SwingUtilities.isLeftMouseButton(e)) {
						gc.sellItem(item, finalI);
					}
				}
				public void mousePressed(MouseEvent e) {

				}
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
				}
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
				}
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
				}

			});
			panel.add(detail);
		}
		JScrollPane bagScrollPane = new JScrollPane(panel,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		bagScrollPane.setBounds(0,41,560,240);
		sellPanel.add(bagScrollPane);
		sellPanel.add(getBackToShopBtn());

		sellPanel.revalidate();
		sellPanel.repaint();
	}
	/**
	 * Updates the bag panel when the observer is notified of a change.
	 */
	private void updateBagPanel() {
		JPanel bagPanel = centerPanelMap.get(CenterPanel.BAG);
		bagPanel.removeAll();
		// add component
		bagPanel.add(getBagTitle());
		//Add all players item here automatically
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEADING));
		panel.setPreferredSize(new Dimension(560,1000));
		panel.setBackground(Color.black);
		
		JPopupMenu popMenu = new JPopupMenu();
		
		JMenuItem[] menuItem = new JMenuItem[5];
		menuItem[0] = new JMenuItem("Use");
		
		
		popMenu.add(menuItem[0]);
		// monster
		for (int i = 0; i< this.gc.getMedicineBag().size() ;i++ ) {
			Medicine item = this.gc.getMedicineBag().get(i);
			JTextArea detail = getItemDetails();
			detail.setText(constructMedicineDetail(item));
			int finalI = i;
			detail.addMouseListener(new MouseListener() {
				public void mouseClicked(MouseEvent e) {
					if(SwingUtilities.isLeftMouseButton(e)) {
						if (gc.getPlayerTeamSize() <= 0) {
							JOptionPane.showMessageDialog(mainFrame, "Add a Monster first before clicking a Potion!","No Monster", JOptionPane.INFORMATION_MESSAGE );
						}else {
							gc.useItemForMonster(item, finalI);
						}
					}
				}
				public void mousePressed(MouseEvent e) {
					
				}
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
				}
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
				}
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
				}
				
			});
			panel.add(detail);
		}
		for (int i = 0; i< this.gc.getMonsterBag().size() ;i++ ) {
			Monster item = this.gc.getMonsterBag().get(i);
			JTextArea detail = getItemDetails();
			detail.setText(constructMonsterDetail(item));
			int finalI = i;
			detail.addMouseListener(new MouseListener() {
				public void mouseClicked(MouseEvent e) {
					if(SwingUtilities.isLeftMouseButton(e)) {
						if (gc.getPlayerTeamSize() == 4) {
							JOptionPane.showMessageDialog(mainFrame, "Remove a Monster first before adding a new one!","Slots are Full", JOptionPane.INFORMATION_MESSAGE );
						}else {
							gc.addBagMonsterToTeam(finalI);
						}
						
						
					}
				}
				public void mousePressed(MouseEvent e) {

				}
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
				}
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
				}
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
				}

			});
			panel.add(detail);
		}

		for (int i = 0; i< this.gc.getWeaponBag().size() ;i++ ) {
			Weapon item = this.gc.getWeaponBag().get(i);
			JTextArea detail = getItemDetails();
			detail.setText(constructWeaponDetail(item));
			int finalI = i;
			detail.addMouseListener(new MouseListener() {
				public void mouseClicked(MouseEvent e) {
					if(SwingUtilities.isLeftMouseButton(e)) {
						if (gc.getPlayerTeamSize() <= 0) {
							JOptionPane.showMessageDialog(mainFrame, "Add a Monster first before clicking a weapon!","No Monster", JOptionPane.INFORMATION_MESSAGE );
						}else {
							gc.useItemForMonster(item, finalI);
						}
						
					}
				}
				public void mousePressed(MouseEvent e) {

				}
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
				}
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
				}
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
				}

			});
			panel.add(detail);
		}
		for (int i = 0; i< this.gc.getShieldBag().size() ;i++ ) {
			Shield item = this.gc.getShieldBag().get(i);
			JTextArea detail = getItemDetails();
			detail.setText(constructShieldDetail(item));
			int finalI = i;
			detail.addMouseListener(new MouseListener() {
				public void mouseClicked(MouseEvent e) {
					if(SwingUtilities.isLeftMouseButton(e)) {
						if (gc.getPlayerTeamSize() <= 0) {
							JOptionPane.showMessageDialog(mainFrame, "Add a Monster first before clicking a Shield!","No Monster", JOptionPane.INFORMATION_MESSAGE );
						}else {
							gc.useItemForMonster(item, finalI);
						}
					}
				}
				public void mousePressed(MouseEvent e) {

				}
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
				}
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
				}
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
				}

			});
			panel.add(detail);
		}
		JScrollPane bagScrollPane = new JScrollPane(panel,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		bagScrollPane.setBounds(0,41,560,240);

		bagPanel.add(bagScrollPane);
		
		bagPanel.revalidate();
		bagPanel.repaint();
	}

	/**
	 * Updates the buy panel when the observer is notified of a change.
	 */
	public void updateBuyPanel() {
		JPanel buyPanel = centerPanelMap.get(CenterPanel.BUY);
		buyPanel.removeAll();
		this.monsterButtons = new ArrayList<>();
		this.weaponButtons = new ArrayList<>();
		this.shieldButtons = new ArrayList<>();
		this.medButtons = new ArrayList<>();
		// add component

		buyPanel.add(getBuyTitle());

		//panel to add to the JScrollPane
		JPanel panel = getBigCenterPanel();

//----------------------------------------------------------------------
		//monster label
		JLabel monsterLabel = getItemLabel();
		monsterLabel.setBounds(10, 0, 110, 40);
		monsterLabel.setText("Monsters");

		JPanel monsterPanel = getMonsterBuyPanel();

//----------------------------------------------------------------------
		//weapon label
		JLabel weaponLabel = getItemLabel();
		weaponLabel.setBounds(10, 180, 100, 40);
		weaponLabel.setText("Weapon");
		//refresh button for weapon

		JPanel weaponPanel = getWeaponBuyPanel();

//----------------------------------------------------------------------
		//shield label
		JLabel shieldLabel = getItemLabel();
		shieldLabel.setBounds(10, 360, 100, 40);
		shieldLabel.setText("Shield");

		JPanel shieldPanel = getShieldBuyPanel();


//----------------------------------------------------------------------
		//Potion label
		JLabel medLabel = getItemLabel();
		medLabel.setBounds(10, 540, 100, 40);
		medLabel.setText("Potions");

		JPanel medPanel = getMedBuyPanel();

//----------------------------------------------------------------------

		panel.add(monsterLabel);
		panel.add(weaponLabel);
		panel.add(shieldLabel);
		panel.add(medLabel);

		panel.add(weaponPanel);
		panel.add(monsterPanel);
		panel.add(shieldPanel);
		panel.add(medPanel);

		JScrollPane buyScrollPane = new JScrollPane(panel,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			   public void run() { 
			       buyScrollPane.getVerticalScrollBar().setValue(0);
			   }
			});
		buyScrollPane.setBounds(0,41,560,428);
		
		buyPanel.add(buyScrollPane);
		buyPanel.add(getCenterPanelTitle("Buy Area"));
		buyPanel.add(getBackToShopBtn());
		buyPanel.revalidate();
		buyPanel.repaint();
	}
	

	public void updateBottomMainPanel() {
		JPanel bottomMainPanel = bottomPanelMap.get(BottomPanel.MAIN);
		bottomMainPanel.removeAll();
		JButton reorderBtn = getReorderBtn();
		JButton fightBtn = getFightBtn();
		JButton renameBtn = getRenameMonsterBtn();
		reorderBtn.setEnabled(this.gc.isAbleToReorderTeam());
		fightBtn.setEnabled(this.gc.isAbleToStartFight());
		renameBtn.setEnabled(this.gc.isAbleToReorderTeam());
		// add components
		bottomMainPanel.add(reorderBtn);
		bottomMainPanel.add(fightBtn);
		bottomMainPanel.add(getNextDayBtn());
		bottomMainPanel.add(renameBtn);
		// repaint
		bottomMainPanel.revalidate();
		bottomMainPanel.repaint();
	}

	private void updateRightPanel() {
		this.rightPanel.removeAll();
		if (this.gc.getBattleIndex() != -1 && this.gc.battleLeft() > 0) {
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
		}
		this.rightPanel.revalidate();
		this.rightPanel.repaint();
	}

	private void updateLeftPanel() {
		this.leftPanel.removeAll();
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
				panel.add(getCurrentExpBarDetails(monster));
				panel.add(autoResizeFont(getMonsterDamageAndShieldLabel(monster)));
				panel.add(autoResizeFont(getExpLabel()));
				panel.add(getMonsterHealthBar(monster));
				panel.add(getMonsterExpBar(monster));
				panel.add(getRemoveButton(i));
			}
			// store the reference of the panel into a list.
			this.playerTeamPanel[i] = panel;
			// add playerTeamPanel into leftPanel
			this.leftPanel.add(this.playerTeamPanel[i]);
		}
		this.leftPanel.revalidate();
		this.leftPanel.repaint();
	}

	// update centerBattlePanel and show which monster is doing the fight
	private void updateCenterBattlePanel() {
		JPanel centerBattlePanel = centerPanelMap.get(CenterPanel.BATTLE);
		centerBattlePanel.removeAll();
		Monster playerMonster = this.gc.getPlayerTeamReadyMonster();
		Monster enemyMonster = this.gc.getEnemyTeamReadyMonster();
		// player's monster
		JPanel playerPanel = new JPanel();
		playerPanel.setLayout(null);
		playerPanel.setBounds(120,100,120,100);
		playerPanel.setBackground(Color.BLACK);
		playerPanel.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.WHITE));
		playerPanel.add(autoResizeFont(getMonsterNameLabel(playerMonster)));
		playerPanel.add(getMonsterLevelLabel(playerMonster));
		playerPanel.add(getLabelWithMonsterImage(playerMonster));
		playerPanel.add(autoResizeFont(getMonsterHealthLabel()));
		playerPanel.add(autoResizeFont(getMonsterDamageAndShieldLabel(playerMonster)));
		playerPanel.add(autoResizeFont(getExpLabel()));
		playerPanel.add(getMonsterHealthBar(playerMonster));
		playerPanel.add(getMonsterExpBar(playerMonster));
		JLabel attackLabel = new JLabel();
		if (this.gc.isPlayerTurnBattle()) {
			attackLabel.setText("Attack ->");
		} else {
			attackLabel.setText("<- Attack");
		}
		attackLabel.setForeground(Color.white);
		attackLabel.setBounds(250, 120, 70, 30);

		// enemy's monster
		JPanel enemyPanel = new JPanel();
		enemyPanel.setLayout(null);
		enemyPanel.setBounds(320,100,120,100);
		enemyPanel.setBackground(Color.BLACK);
		enemyPanel.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.WHITE));
		enemyPanel.add(autoResizeFont(getMonsterNameLabel(enemyMonster)));
		enemyPanel.add(getMonsterLevelLabel(enemyMonster));
		enemyPanel.add(getLabelWithMonsterImage(enemyMonster));
		enemyPanel.add(autoResizeFont(getMonsterHealthLabel()));
		enemyPanel.add(autoResizeFont(getMonsterDamageAndShieldLabel(enemyMonster)));
		enemyPanel.add(autoResizeFont(getExpLabel()));
		enemyPanel.add(getMonsterHealthBar(enemyMonster));
		enemyPanel.add(getMonsterExpBar(enemyMonster));
		// add panel to centerBattlePanel
		centerBattlePanel.add(enemyPanel);
		centerBattlePanel.add(attackLabel);
		centerBattlePanel.add(playerPanel);
		// revalidate and repaint the panel
		centerBattlePanel.revalidate();
		centerBattlePanel.repaint();
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
		this.centerPanelMap.put(CenterPanel.BUY, getCenterBuyPanel());
		this.centerPanelMap.put(CenterPanel.SELL, getCenterSellPanel());
		this.centerPanelMap.put(CenterPanel.WASTE, getCenterWastePanel());
		this.centerPanelMap.put(CenterPanel.WIN, getCenterWinPanel());
		this.centerPanelMap.put(CenterPanel.GAME_OVER, getCenterGameOverPanel());
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
		this.bottomPanelMap.put(BottomPanel.REORDER, getBottomReorderPanel());
		this.bottomPanelMap.put(BottomPanel.EXIST_BATTLE, getBottomExistBattlePanel());
		this.bottomPanelMap.put(BottomPanel.GAME_OVER, getBottomGameOverPanel());
		this.bottomPanelMap.put(BottomPanel.RENAME, getBottomRenamePanel());
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
		newTopPanel.add(getTopRightPanel());
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
		topLeftPanel.setLayout(new GridLayout(3,1));
		// add components to the panel
		topLeftPanel.add(getPlayerNameLabel());
		this.goldLabel = getGoldLabel();
		topLeftPanel.add(this.goldLabel);
		this.pointLabel = getPointLabel();
		topLeftPanel.add(this.pointLabel);
		// return panel
		return topLeftPanel;
	}

	private JPanel getTopRightPanel() {
		JPanel topRightPanel = new JPanel();
		topRightPanel.setBackground(Color.BLACK);
		topRightPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.WHITE));
		topRightPanel.setLayout(new GridLayout(2, 1));
		// add components to the panel
		this.daysLeftLabel = getDaysLeftLabel();
		topRightPanel.add(this.daysLeftLabel);
		this.battleLeftLabel = getBattleLeftLabel();
		topRightPanel.add(this.battleLeftLabel);
		return topRightPanel;
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
				panel.add(getCurrentHealthBarDetails(monster));
				panel.add(getCurrentExpBarDetails(monster));
				panel.add(autoResizeFont(getMonsterDamageAndShieldLabel(monster)));
				panel.add(autoResizeFont(getExpLabel()));
				panel.add(getMonsterHealthBar(monster));
				panel.add(getMonsterExpBar(monster));
				panel.add(getRemoveButton(i));
			}
			// store the reference of the panel into a list.
			this.playerTeamPanel[i] = panel;
			// add playerTeamPanel into leftPanel
			leftPanel.add(this.playerTeamPanel[i]);
		}
		return leftPanel;
	}
	
	public JButton getRemoveButton(int index) {
		JButton button = new JButton();
		button.setText("remove");
		button.setFont(new Font("Serif", Font.PLAIN, 10));
		button.setForeground(Color.WHITE);
		button.setBounds(60,80,50,17);
		button.setBackground(Color.BLACK);
		button.setFocusable(false);
		button.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == button) {
					gc.removeMonsterFromLeftPanel(index);

				}
			}
			
		});
		
		return button;
	}

	private JLabel getMonsterOrderLabel(int orderNum) {
		JLabel order = new JLabel(""+ (orderNum + 1));
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
		JLabel damageAndShield = new JLabel("DMG: " + monster.getDamage());
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
		// override the layout
		bottomMainPanel.setLayout(new GridLayout(2, 2));
		// add components to the startingPanel
		bottomMainPanel.add(getReorderBtn());
		bottomMainPanel.add(getFightBtn());
		bottomMainPanel.add(getNextDayBtn());
		bottomMainPanel.add(getRenameMonsterBtn());
		return bottomMainPanel;
	}

	/**
	 * Create and return a bottomBagPanel. This function is using the bottom panel template function(getNewBottomPanel).
	 */
	private JPanel getBottomBagPanel() {
		// bottomBagPanel
		JPanel bottomBagPanel = getNewBottomPanel();
		
		JButton bagInfo = new JButton();
		bagInfo.setText("Bag Info");
		bagInfo.setFont(new Font("Serif", Font.PLAIN, 30));
		bagInfo.setForeground(Color.WHITE);
		bagInfo.setBounds(200,47,150,50);
		bagInfo.setBackground(Color.BLACK);
		bagInfo.setFocusable(false);
		bagInfo.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
		bagInfo.addActionListener(e -> {
			if (e.getSource() == bagInfo) {
				JOptionPane.showMessageDialog(mainFrame, """
						Ensure that Slots are not full before adding to the main slots.
						Shield will increase the Max Health of a Monster randomly.
						Weapon will increase the damage of a random monster.
						Potion will recover the health of a Monster in your team.""","Bag Info", JOptionPane.INFORMATION_MESSAGE );
			}
		});
		bottomBagPanel.add(bagInfo);
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
		
		JTextPane text = new JTextPane();
		text.setText("Would you like to buy or sell your items?");
		text.setForeground(Color.white);
		text.setBackground(Color.black);
		text.setFont(new Font("Serif",Font.PLAIN,20));
		text.setBounds(20,15,450,40);
		text.setEditable(false);
		
		JToggleButton buyButton = getBuyBtn();
		JToggleButton sellButton = getSellBtn();
		
		shopButtonGroup = new ButtonGroup(); 
		
		shopButtonGroup.add(buyButton);
		shopButtonGroup.add(sellButton);
		
		bottomShopPanel.add(text);
		bottomShopPanel.add(buyButton);
		bottomShopPanel.add(sellButton);
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
//		bottomSettingsPanel.add(getSaveBtn());
		bottomSettingsPanel.add(getRestartBtn());
		// set it to not visible (Default)
		bottomSettingsPanel.setVisible(false);
		return bottomSettingsPanel;
	}

	private JPanel getBottomBattlePanel() {
		// bottom
		JPanel bottomBattlePanel = getNewBottomPanel();
		// add components to panel
		bottomBattlePanel.add(getAttackBtn());
		bottomBattlePanel.add(getExistBattleBtn());
		// set it to not visible (Default)
		bottomBattlePanel.setVisible(false);
		return bottomBattlePanel;
	}

	private JPanel getBottomReorderPanel() {
		// bottom
		JPanel bottomReorderPanel = getNewBottomPanel();
		// add components to panel
		// store the confirmBtn to toggle the status of the btn
		this.reorderConfirmBtn = getReorderConfirmBtn();
		bottomReorderPanel.add(getReorderMonster1TextField());
		bottomReorderPanel.add(getReorderSymbol());
		bottomReorderPanel.add(getReorderMonster2TextField());
		bottomReorderPanel.add(this.reorderConfirmBtn);
		bottomReorderPanel.add(getReorderCancelBtn());
		bottomReorderPanel.setVisible(false);
		return bottomReorderPanel;
	}

	private JPanel getBottomExistBattlePanel() {
		// bottom
		JPanel bottomExistBattlePanel = getNewBottomPanel();
		JButton btn = getExistBattleBtn();
		bottomExistBattlePanel.add(btn);
		bottomExistBattlePanel.setVisible(false);
		return bottomExistBattlePanel;
	}

	private JPanel getBottomGameOverPanel() {
		JPanel bottomGameOverPanel = getNewBottomPanel();
		bottomGameOverPanel.add(getReplayBtn());
		bottomGameOverPanel.add(getCloseBtn());
		bottomGameOverPanel.setVisible(false);
		return bottomGameOverPanel;
	}

	private JPanel getBottomRenamePanel() {
		JPanel bottomRenamePanel = getNewBottomPanel();
		this.renameConfirmBtn = getRenameConfirmBtn();
		JLabel renameMonsterIndexLabel = new JLabel("",SwingConstants.CENTER);
		renameMonsterIndexLabel.setBounds(0,20,130,15);
		renameMonsterIndexLabel.setForeground(Color.white);
		renameMonsterIndexLabel.setFont(new Font("Arial",Font.BOLD, 15));
		renameMonsterIndexLabel.setText("MonsterOrder#");

		JLabel newNameLabel = new JLabel("",SwingConstants.CENTER);
		newNameLabel.setBounds(130,20,120,15);
		newNameLabel.setForeground(Color.white);
		newNameLabel.setFont(new Font("Arial",Font.BOLD, 15));
		newNameLabel.setText("NewName");

		bottomRenamePanel.add(renameMonsterIndexLabel);
		bottomRenamePanel.add(newNameLabel);
		bottomRenamePanel.add(this.renameConfirmBtn);
		bottomRenamePanel.add(getRenameCancelBtn());
		bottomRenamePanel.add(getRenameMonsterIndexTextField());
		bottomRenamePanel.add(getNewMonsterNameTextField());
		bottomRenamePanel.setVisible(false);
		return bottomRenamePanel;
	}

	private JTextPane getReorderSymbol() {
		JTextPane symbol = new JTextPane();
		symbol.setText("<   >");
		symbol.setFont(new Font("Serif",Font.PLAIN,20));
		symbol.setForeground(Color.white);
		symbol.setBackground(Color.black);
		symbol.setBounds(80, 48, 50, 75);
		symbol.setEditable(false);
		return symbol;
	}

	private JTextField getReorderMonster1TextField() {
		// create a textField
		JTextField textField = new JTextField();
		// remove border
		textField.setBorder(BorderFactory.createEmptyBorder());
		textField.setBounds(20, 50, 50, 50);
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setColumns(1);
		textField.setBorder(null);
		// add listeners
		addReorderMonsterTextFieldKeyListener(textField);
		addReorderMonsterTextField1DocumentListener(textField);
		return textField;
	}

	private JTextField getNewMonsterNameTextField() {
		// create a textField
		JTextField textField = new JTextField();
		// remove border
		textField.setBorder(BorderFactory.createEmptyBorder());
		textField.setBounds(140, 50, 100, 50);
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setColumns(1);
		textField.setBorder(null);
		// listener
		addNewMonsterNameTextFieldListener(textField);
		return textField;
	}

	private JTextField getRenameMonsterIndexTextField() {
		// create a textField
		JTextField textField = new JTextField();
		// remove border
		textField.setBorder(BorderFactory.createEmptyBorder());
		textField.setBounds(30, 50, 50, 50);
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setColumns(1);
		textField.setBorder(null);
		// listener
		addRenameMonsterIndexTextFieldKeyListener(textField);
		addRenameMonsterIndexTextFieldDocumentListener(textField);
		return textField;
	}

	private JTextField getReorderMonster2TextField() {
		// create a textField
		JTextField textField = new JTextField();
		// remove border
		textField.setBorder(BorderFactory.createEmptyBorder());
		textField.setBounds(120, 50, 50, 50);
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setColumns(1);
		textField.setBorder(null);
		// add listeners
		addReorderMonsterTextFieldKeyListener(textField);
		addReorderMonsterTextField2DocumentListener(textField);
		return textField;
	}

	private void addReorderMonsterTextFieldKeyListener(JTextField textField) {
		textField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || c == KeyEvent.VK_DELETE)) {
					e.consume();
				}
				if (Character.getNumericValue(c) <= 0|| Character.getNumericValue(c) > gc.getMonsterTeamMember().size()) {
					e.consume();
				}
				if (textField.getText().length() >= 1) {
					e.consume();
				}
			}
		});
	}

	// listener for right textField
	private void addReorderMonsterTextField2DocumentListener(JTextField textField) {
		textField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
			}
			public void removeUpdate(DocumentEvent e) {
				reorderingMonsterIndex2 = -1;
				reorderConfirmBtn.setEnabled(false);
			}
			public void insertUpdate(DocumentEvent e) {
				int val = (Integer.parseInt(textField.getText()) - 1);
				if (reorderingMonsterIndex1 == -1) {
					reorderingMonsterIndex2 = val;
				}
				if ((reorderingMonsterIndex1 != -1) && (reorderingMonsterIndex1 != val)) {
					reorderingMonsterIndex2 = val;
					reorderConfirmBtn.setEnabled(true);
				}
			}
		});
	}

	// listener for left textField
	private void addReorderMonsterTextField1DocumentListener(JTextField textField) {
		textField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
			}
			public void removeUpdate(DocumentEvent e) {
				reorderingMonsterIndex1 = -1;
				reorderConfirmBtn.setEnabled(false);
			}
			public void insertUpdate(DocumentEvent e) {
				int val = (Integer.parseInt(textField.getText()) - 1);
				if (reorderingMonsterIndex2 == -1) {
					reorderingMonsterIndex1 = val;
				}
				if ((reorderingMonsterIndex2 != -1) && (reorderingMonsterIndex2 != val)) {
					reorderingMonsterIndex1 = val;
					reorderConfirmBtn.setEnabled(true);
				}
			}
		});
	}

	private void addNewMonsterNameTextFieldListener(JTextField textField) {
		textField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				newMonsterName = textField.getText();
				renameConfirmBtn.setEnabled(renameMonsterIndex != -1);
			}
			public void removeUpdate(DocumentEvent e) {
				newMonsterName = textField.getText();
				renameConfirmBtn.setEnabled(renameMonsterIndex != -1);
			}
			public void insertUpdate(DocumentEvent e) {
				newMonsterName = textField.getText();
				renameConfirmBtn.setEnabled(renameMonsterIndex != -1);
			}
		});
	}

	private void addRenameMonsterIndexTextFieldKeyListener(JTextField textField) {
		textField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || c == KeyEvent.VK_DELETE)) {
					e.consume();
				}
				if (Character.getNumericValue(c) <= 0|| Character.getNumericValue(c) > gc.getMonsterTeamMember().size()) {
					e.consume();
				}
				if (textField.getText().length() >= 1) {
					e.consume();
				}
			}
		});
	}

	private void addRenameMonsterIndexTextFieldDocumentListener(JTextField textField) {
		textField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				if (textField.getText().equals("")) {
					renameConfirmBtn.setEnabled(false);
					renameMonsterIndex = -1;
				} else {
					renameMonsterIndex = Integer.parseInt(textField.getText());
					renameConfirmBtn.setEnabled(!(newMonsterName.equals("")));
				}
			}
			public void removeUpdate(DocumentEvent e) {
				if (textField.getText().equals("")) {
					renameConfirmBtn.setEnabled(false);
					renameMonsterIndex = -1;
				} else {
					renameMonsterIndex = Integer.parseInt(textField.getText());
					renameConfirmBtn.setEnabled(!(newMonsterName.equals("")));
				}
			}
			public void insertUpdate(DocumentEvent e) {
				if (textField.getText().equals("")) {
					renameConfirmBtn.setEnabled(false);
					renameMonsterIndex = -1;
				} else {
					renameMonsterIndex = Integer.parseInt(textField.getText());
					renameConfirmBtn.setEnabled(!(newMonsterName.equals("")));
				}
			}
		});
	}

	private JPanel getCenterGameOverPanel() {
		JPanel gameOverPanel = new JPanel();
		gameOverPanel.setLayout(new GridLayout(4,1));
		gameOverPanel.setBackground(Color.BLACK);
		gameOverPanel.setBounds(120,70,560,280);

		gameOverPanel.add(getGameOverLabel());
		gameOverPanel.add(getFinalGoldLabel());
		gameOverPanel.add(getFinalPointLabel());

		gameOverPanel.setVisible(false);
		return gameOverPanel;
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
		centerBagPanel.add(getBagTitle());

		//Add all players item here automatically
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEADING));
		panel.setPreferredSize(new Dimension(560,1000));
		panel.setBackground(Color.black);

		JPopupMenu popMenu = new JPopupMenu();

		JMenuItem[] menuItem = new JMenuItem[5];
		menuItem[0] = new JMenuItem("Use");

		popMenu.add(menuItem[0]);
		JScrollPane bagScrollPane = new JScrollPane(panel,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		bagScrollPane.setBounds(0,41,560,240);

		centerBagPanel.add(bagScrollPane);
		return centerBagPanel;
	}

	/**
	 * Create a centerShopPanel. This function is using the bottom panel template function(getNewCenterPanel)
	 */
	private JPanel getCenterShopPanel() {
		// create a shopPanel (This panel will be stored in a variable)
		JPanel centerShopPanel = getNewCenterPanel();
		// add component
		JTextPane title = new JTextPane();
		title.setText("Monster Fighter \n        Shop");
		title.setFont(new Font("Serif",Font.PLAIN,30));
		title.setForeground(Color.white);
		title.setBackground(Color.black);
		title.setBounds(150, 75, 300, 100);
		title.setEditable(false);
		
		centerShopPanel.add(title);
		JButton backButton = getBackButton();
		backButton.setText("Exit Shop");
		backButton.setBounds(15,250,80,20);
		centerShopPanel.add(backButton);
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
		healthBar.setValue(monster.getCurrentHealth());
		healthBar.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.white));
		healthBar.setFont(new Font("Serif", Font.PLAIN, 10));
		healthBar.setForeground(Color.red);
		healthBar.setBackground(Color.black);
		// auto converting... Nice work Java..
		healthBar.setString(monster.getCurrentHealth() + "/" + monster.getMaxHealth());
		healthBar.setStringPainted(true);
		
		return healthBar;
	}
	
	private JLabel getCurrentHealthBarDetails(Monster monster) {
		JLabel currentHealth = new JLabel("",SwingConstants.CENTER);
		currentHealth.setBounds(20,46,90,9);
		currentHealth.setForeground(Color.white);
		currentHealth.setFont(new Font("Arial",Font.PLAIN, 9));
		currentHealth.setText(monster.getCurrentHealth()+"/"+monster.getMaxHealth());
		
		return currentHealth;
	}
	
	private JLabel getCurrentExpBarDetails(Monster monster) {
		JLabel currentExp = new JLabel("",SwingConstants.CENTER);
		currentExp.setBounds(20,66,90,9);
		currentExp.setForeground(Color.white);
		currentExp.setFont(new Font("Arial",Font.PLAIN, 9));
		currentExp.setText(monster.getExp() + "/" + monster.getMaxExp());
		
		return currentExp;
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
		expBar.setValue(monster.getExp());
		expBar.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.white));
		expBar.setForeground(Color.gray);
		expBar.setBackground(Color.black);
		
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
	 * Create and return a goldLabel.
	 *
	 * @return a goldLabel(JLabel)
	 */
	private JLabel getGoldLabel() {
		// gold label
		JLabel goldLabel = new JLabel("", SwingConstants.CENTER);
		// gold (TopLeftPanel/TopPanel component)
		goldLabel.setFont(new Font("Serif", Font.PLAIN, 15));
		goldLabel.setText("Gold: " + this.gc.getGold());
		goldLabel.setForeground(Color.WHITE);
		// return the goldLabel
		return goldLabel;
	}

	/**
	 * Create and return a pointLabel.
	 *
	 * @return a pointLabel(JLabel)
	 */
	private JLabel getPointLabel() {
		JLabel pointLabel = new JLabel("", SwingConstants.CENTER);
		// point (TopLeftPanel/TopPanel component)
		pointLabel.setFont(new Font("Serif", Font.PLAIN, 15));
		pointLabel.setText(" Point: " + this.gc.getPoint());
		pointLabel.setForeground(Color.WHITE);
		// return goldPoints label
		return pointLabel;
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

	private JLabel getGameOverLabel() {
		// daysLeftLabel (TopPanel component)
		JLabel gameOverLabel = new JLabel("",SwingConstants.CENTER);
		gameOverLabel.setText("Game Over");
		gameOverLabel.setFont(new Font("Serif",Font.PLAIN,30));
		gameOverLabel.setForeground(Color.WHITE);
		gameOverLabel.setBackground(Color.BLACK);
		gameOverLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.WHITE));
		// return label
		return gameOverLabel;
	}
	private JLabel getFinalGoldLabel() {
		// daysLeftLabel (TopPanel component)
		JLabel finalGoldLabel = new JLabel("",SwingConstants.CENTER);
		finalGoldLabel.setText("Player. Your final gold is: " + this.gc.getGold());
		finalGoldLabel.setFont(new Font("Serif",Font.PLAIN,15));
		finalGoldLabel.setForeground(Color.WHITE);
		finalGoldLabel.setBackground(Color.BLACK);
		finalGoldLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.WHITE));
		// return label
		return finalGoldLabel;
	}
	private JLabel getFinalPointLabel() {
		// daysLeftLabel (TopPanel component)
		JLabel finalPointLabel = new JLabel("",SwingConstants.CENTER);
		finalPointLabel.setText("Player. Your final point is: " + this.gc.getPoint());
		finalPointLabel.setFont(new Font("Serif",Font.PLAIN,15));
		finalPointLabel.setForeground(Color.WHITE);
		finalPointLabel.setBackground(Color.BLACK);
		finalPointLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.WHITE));
		// return label
		return finalPointLabel;
	}

	private JLabel getBattleLeftLabel() {
		// daysLeftLabel (TopPanel component)
		JLabel battleLeftLabel = new JLabel("",SwingConstants.CENTER);
		battleLeftLabel.setText(String.format("Battles left: %d", this.gc.getTotalBattle()));
		battleLeftLabel.setFont(new Font("Serif",Font.PLAIN,15));
		battleLeftLabel.setForeground(Color.WHITE);
		battleLeftLabel.setBackground(Color.BLACK);
		battleLeftLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.WHITE));
		// return label
		return battleLeftLabel;
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
		titleLabel.setBounds(205, 0, 150, 35);
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setBackground(Color.BLACK);
		titleLabel.setOpaque(true);
		// return titleLabel
		return titleLabel;
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
	private JToggleButton getBuyBtn() {
		// create a buyBtn (BottomShopPanel component)
		JToggleButton buyBtn = new JToggleButton();
		buyBtn.setText("Buy");
		buyBtn.setFont(new Font("Arial", Font.PLAIN, 25));
		buyBtn.setBounds(45, 65, 210, 50);
		buyBtn.setBackground(Color.BLACK);
		buyBtn.setForeground(Color.WHITE);
		buyBtn.setFocusable(false);
		buyBtn.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
		// event listener
		bindToggleButtonToShopPanel(buyBtn, CenterPanel.BUY);
		// return
		return buyBtn;
	}

	/**
	 * Create and return a sellBtn for BottomShopPanel.
	 *
	 * @return a sellBtn.
	 */
	private JToggleButton getSellBtn() {
		// create a sellBtn(BottomShopPanel component)
		JToggleButton sellBtn = new JToggleButton();
		sellBtn.setText("Sell");
		sellBtn.setFont(new Font("Arial", Font.PLAIN, 25));
		sellBtn.setBounds(305, 65, 210, 50);
		sellBtn.setBackground(Color.BLACK);
		sellBtn.setForeground(Color.WHITE);
		sellBtn.setFocusable(false);
		sellBtn.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
		// event listener
		bindToggleButtonToShopPanel(sellBtn, CenterPanel.SELL);
		// return
		return sellBtn;
	}

	private JButton getNextDayBtn() {
		// create a nextDayBtn
		JButton nextDayBtn = new JButton();
		nextDayBtn.setText("NextDay");
		nextDayBtn.setFont(new Font("Arial", Font.PLAIN, 25));
		nextDayBtn.setBounds(373, 50, 186, 50);
		nextDayBtn.setBackground(Color.BLACK);
		nextDayBtn.setForeground(Color.WHITE);
		nextDayBtn.setFocusable(false);
		nextDayBtn.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
		addNextDayBtnListener(nextDayBtn);
		return nextDayBtn;
	}

	private JButton getRenameMonsterBtn() {
		JButton renameMonsterBtn = new JButton();
		renameMonsterBtn.setText("RenameMonster");
		renameMonsterBtn.setFont(new Font("Arial", Font.PLAIN, 25));
		renameMonsterBtn.setBackground(Color.BLACK);
		renameMonsterBtn.setForeground(Color.WHITE);
		renameMonsterBtn.setFocusable(false);
		renameMonsterBtn.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
		// listener
		addRenameMonsterBtnListener(renameMonsterBtn);
		return renameMonsterBtn;
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
		restartBtn.setBounds(165, 50, 210, 50);
		restartBtn.setBackground(Color.BLACK);
		restartBtn.setForeground(Color.WHITE);
		restartBtn.setFocusable(false);
		restartBtn.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
		// listener
		restartBtn.addActionListener(e -> {
			closeAndDestroyCurrentScreen();
			gc.launchLandingScreen();
		});
		// return
		return restartBtn;
	}

	private JButton getRenameConfirmBtn() {
		// create a restartBtn (BottomSettingsPanel component)
		JButton renameConfirmBtn = new JButton();
		renameConfirmBtn.setText("Confirm");
		renameConfirmBtn.setFont(new Font("Arial", Font.PLAIN, 25));
		renameConfirmBtn.setBounds(390, 50, 100, 50);
		renameConfirmBtn.setBackground(Color.BLACK);
		renameConfirmBtn.setForeground(Color.WHITE);
		renameConfirmBtn.setEnabled(false);
		renameConfirmBtn.setFocusable(false);
		renameConfirmBtn.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
		// listener
		renameConfirmBtn.addActionListener(e -> {
			// -1 as the ui starts display the monster from one
			gc.renameTeamMonsterByIndex(renameMonsterIndex-1, newMonsterName);
		});
		// return
		return renameConfirmBtn;
	}

	private JButton getReplayBtn() {
		JButton replayBtn = new JButton();
		replayBtn.setText("Replay");
		replayBtn.setFont(new Font("Arial", Font.PLAIN, 25));
		replayBtn.setBounds(305, 50, 210, 50);
		replayBtn.setBackground(Color.BLACK);
		replayBtn.setForeground(Color.WHITE);
		replayBtn.setFocusable(false);
		replayBtn.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
		// listener
		addReplayBtnListener(replayBtn);
		// return
		return replayBtn;
	}

	private JButton getCloseBtn() {
		JButton closeBtn = new JButton();
		closeBtn.setText("Close");
		closeBtn.setFont(new Font("Arial", Font.PLAIN, 25));
		closeBtn.setBounds(45, 50, 210, 50);
		closeBtn.setBackground(Color.BLACK);
		closeBtn.setForeground(Color.WHITE);
		closeBtn.setFocusable(false);
		closeBtn.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
		// listener
		addCloseBtnListener(closeBtn);
		// return
		return closeBtn;
	}

	private JButton getFightBtn() {
		// create a fightBtn
		JButton fightBtn = new JButton();
		fightBtn.setText("Fight");
		fightBtn.setFont(new Font("Arial", Font.PLAIN, 25));
		fightBtn.setBounds(187, 50, 186, 50);
		fightBtn.setBackground(Color.BLACK);
		fightBtn.setForeground(Color.WHITE);
		fightBtn.setFocusable(false);
		fightBtn.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
		fightBtn.setEnabled(false);
		// listener
		addFightBtnListener(fightBtn);
		return fightBtn;
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
		addReorderBtnListener(reorderBtn);
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

	private JButton getReorderConfirmBtn() {
		JButton reorderBtn = new JButton();
		reorderBtn.setText("Confirm");
		reorderBtn.setFont(new Font("Arial", Font.PLAIN, 25));
		reorderBtn.setBounds(390, 50, 100, 50);
		reorderBtn.setBackground(Color.BLACK);
		reorderBtn.setForeground(Color.WHITE);
		reorderBtn.setFocusable(false);
		reorderBtn.setEnabled(false);
		reorderBtn.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
		// listener
		addReorderConfirmBtnListener(reorderBtn);
		return reorderBtn;
	}

	private JButton getExistBattleBtn() {
		JButton existBattleBtn = new JButton();
		existBattleBtn.setText("Exit");
		existBattleBtn.setFont(new Font("Arial", Font.PLAIN, 25));
		existBattleBtn.setBounds(45, 50, 210, 50);
		existBattleBtn.setBackground(Color.BLACK);
		existBattleBtn.setForeground(Color.WHITE);
		existBattleBtn.setFocusable(false);
		existBattleBtn.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
		// listener
		addExistBattleBtnListener(existBattleBtn);
		return existBattleBtn;
	}

	private JButton getReorderCancelBtn() {
		JButton cancelBtn = new JButton();
		cancelBtn.setText("Cancel");
		cancelBtn.setFont(new Font("Arial", Font.PLAIN, 25));
		cancelBtn.setBounds(280, 50, 100, 50);
		cancelBtn.setBackground(Color.BLACK);
		cancelBtn.setForeground(Color.WHITE);
		cancelBtn.setFocusable(false);
		cancelBtn.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
		// listener
		addReorderCancelBtnListener(cancelBtn);
		return cancelBtn;
	}

	private JButton getRenameCancelBtn() {
		JButton cancelBtn = new JButton();
		cancelBtn.setText("Cancel");
		cancelBtn.setFont(new Font("Arial", Font.PLAIN, 25));
		cancelBtn.setBounds(280, 50, 100, 50);
		cancelBtn.setBackground(Color.BLACK);
		cancelBtn.setForeground(Color.WHITE);
		cancelBtn.setFocusable(false);
		cancelBtn.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
		// listener
		addReorderCancelBtnListener(cancelBtn);
		return cancelBtn;
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
			}else if (e.getStateChange() == ItemEvent.DESELECTED) {
				showCenterPanel(CenterPanel.MAIN);
				showBottomPanel(BottomPanel.MAIN);
			}
		});
	}
	/**
	 *Shop's buy and sell toggle button listener. Shows the buy or sell area when button is selected and show the shop panel when deselected. 
	 *
	 * @param b a JtoggleBtn from one of the Shops buttons
	 */
	private void bindToggleButtonToShopPanel(JToggleButton b, CenterPanel cP) {
		b.addItemListener(e -> {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				for (Enumeration<AbstractButton> buttons = topGroup.getElements(); buttons.hasMoreElements();) {
		            AbstractButton button = buttons.nextElement();
		            button.setEnabled(false);
		        }
				showCenterPanel(cP);
			}else if (e.getStateChange() == ItemEvent.DESELECTED) {
				for (Enumeration<AbstractButton> buttons = topGroup.getElements(); buttons.hasMoreElements();) {
		            AbstractButton button = buttons.nextElement();
		            button.setEnabled(true);
		        }
				showCenterPanel(CenterPanel.SHOP);
				showBottomPanel(BottomPanel.SHOP);
			}
		});
	}
	
	private JPanel getCenterBuyPanel() {
		// Template
		// This panel will be shown in the middle of the mainFrame
		shop.refreshShop();
		JPanel buyPanel = getPanelForShop();
		// add component
		buyPanel.add(getBuyTitle());
		//panel to add to the JScrollPane
		JPanel panel = getBigCenterPanel();
		
//----------------------------------------------------------------------
		//monster label
		JLabel monsterLabel = getItemLabel();
		monsterLabel.setBounds(10, 0, 110, 40);
		monsterLabel.setText("Monsters");
		
		JPanel monsterPanel = getMonsterBuyPanel();
		
//----------------------------------------------------------------------
		//weapon label
		JLabel weaponLabel = getItemLabel();
		weaponLabel.setBounds(10, 180, 100, 40);
		weaponLabel.setText("Weapon");
		//refresh button for weapon
//		JButton refreshWeapons = getRefreshButton();
//		refreshWeapons.setBounds(110, 190 ,60,20);
		
		JPanel weaponPanel = getWeaponBuyPanel();
		
//----------------------------------------------------------------------
		//shield label
		JLabel shieldLabel = getItemLabel();
		shieldLabel.setBounds(10, 360, 100, 40);
		shieldLabel.setText("Shield");
		
		JPanel shieldPanel = getShieldBuyPanel();
		
		
//----------------------------------------------------------------------
		//Potion label
		JLabel medLabel = getItemLabel();
		medLabel.setBounds(10, 540, 100, 40);
		medLabel.setText("Potions");
		
		JPanel medPanel = getMedBuyPanel();
		
//----------------------------------------------------------------------

		panel.add(monsterLabel);
		panel.add(weaponLabel);
		panel.add(shieldLabel);
		panel.add(medLabel);

		panel.add(weaponPanel);
		panel.add(monsterPanel);
		panel.add(shieldPanel);
		panel.add(medPanel);
		

		JScrollPane buyScrollPane = new JScrollPane(panel,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			   public void run() { 
			       buyScrollPane.getVerticalScrollBar().setValue(0);
			   }
			});
		buyScrollPane.setBounds(0,41,560,428);
		
		buyPanel.add(buyScrollPane);
		buyPanel.add(getCenterPanelTitle("Buy Area"));
		buyPanel.add(getBackToShopBtn());
		
		// set it to not visible (Default)
		buyPanel.setVisible(false);
		return buyPanel;
	}

	private void addFightBtnListener(JButton b) {
		b.addActionListener(e -> {
			showCenterPanel(CenterPanel.BATTLE);
			showBottomPanel(BottomPanel.BATTLE);
			this.gc.enterBattle();
		});
	}

	private void addReplayBtnListener(JButton b) {
		b.addActionListener(e -> {
			closeAndDestroyCurrentScreen();
			this.gc.launchLandingScreen();
		});
	}

	private void addCloseBtnListener(JButton b) {
		b.addActionListener(e -> closeAndDestroyCurrentScreen());
	}

	private void addNextDayBtnListener(JButton b) {
		b.addActionListener(e -> this.gc.nextDay());
	}

	private void addRenameMonsterBtnListener(JButton b) {
		b.addActionListener(e -> showBottomPanel(BottomPanel.RENAME));
	}

	private void addAttackBtnListener(JButton b) {
		b.addActionListener(e -> this.gc.battle());
	}

	private void addReorderConfirmBtnListener(JButton b) {
		b.addActionListener(e -> this.gc.swapMonsterInPlayerTeam(this.reorderingMonsterIndex1, this.reorderingMonsterIndex2));
	}

	private void addExistBattleBtnListener(JButton b) {
		b.addActionListener(e -> {
			showCenterPanel(CenterPanel.MAIN);
			showBottomPanel(BottomPanel.MAIN);
			this.gc.existBattle();
		});
	}

	private void addReorderCancelBtnListener(JButton b) {
		b.addActionListener(e -> showBottomPanel(BottomPanel.MAIN));
	}
	
	
	private JPanel getCenterSellPanel() {
		JPanel sellPanel = getPanelForShop();
		// add component
		sellPanel.add(getCenterPanelTitle("Sell Area"));
		//Add all players item here automatically
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEADING));
		panel.setPreferredSize(new Dimension(560,1000));
		panel.setBackground(Color.black);

		JPopupMenu popMenu = new JPopupMenu();

		JMenuItem[] menuItem = new JMenuItem[5];
		menuItem[0] = new JMenuItem("Sell");

		popMenu.add(menuItem[0]);
		// monster
		for (int i = 0; i< this.gc.getMedicineBag().size() ;i++ ) {
			Medicine item = this.gc.getMedicineBag().get(i);
			JTextArea detail = getItemDetails();
			detail.setText(constructMedicineDetail(item));
			int finalI = i;
			detail.addMouseListener(new MouseListener() {
				public void mouseClicked(MouseEvent e) {
					if(SwingUtilities.isLeftMouseButton(e)) {
						gc.useItemForMonster(item, finalI);
					}
				}
				public void mousePressed(MouseEvent e) {

				}
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
				}
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
				}
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
				}
			});
			panel.add(detail);
		}
		for (int i = 0; i< this.gc.getMonsterBag().size() ;i++ ) {
			Monster item = this.gc.getMonsterBag().get(i);
			JTextArea detail = getItemDetails();
			detail.setText(constructMonsterDetail(item));
			int finalI = i;
			detail.addMouseListener(new MouseListener() {
				public void mouseClicked(MouseEvent e) {
					if(SwingUtilities.isLeftMouseButton(e)) {
						gc.addBagMonsterToTeam(finalI);
					}
				}
				public void mousePressed(MouseEvent e) {

				}
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
				}
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
				}
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
				}
			});
			panel.add(detail);
		}
		for (int i = 0; i< this.gc.getWeaponBag().size() ;i++ ) {
			Weapon item = this.gc.getWeaponBag().get(i);
			JTextArea detail = getItemDetails();
			detail.setText(constructWeaponDetail(item));
			int finalI = i;
			detail.addMouseListener(new MouseListener() {
				public void mouseClicked(MouseEvent e) {
					if(SwingUtilities.isLeftMouseButton(e)) {
						gc.useItemForMonster(item, finalI);
					}
				}
				public void mousePressed(MouseEvent e) {

				}
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
				}
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
				}
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
				}

			});
			panel.add(detail);
		}
		for (int i = 0; i< this.gc.getShieldBag().size() ;i++ ) {
			Shield item = this.gc.getShieldBag().get(i);
			JTextArea detail = getItemDetails();
			detail.setText(constructShieldDetail(item));
			int finalI = i;
			detail.addMouseListener(new MouseListener() {
				public void mouseClicked(MouseEvent e) {
					if(SwingUtilities.isLeftMouseButton(e)) {
						gc.useItemForMonster(item, finalI);
					}
				}
				public void mousePressed(MouseEvent e) {

				}
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
				}
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
				}
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
				}

			});
			panel.add(detail);
		}
		JScrollPane bagScrollPane = new JScrollPane(panel,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		bagScrollPane.setBounds(0,41,560,240);

		sellPanel.add(bagScrollPane);

		sellPanel.add(getBackToShopBtn());
		// set it to not visible (Default)
		sellPanel.setVisible(false);
		return sellPanel;	
	}

	private JPanel getCenterWastePanel() {
		JPanel wastePanel = getNewCenterPanel();
		wastePanel.add(getCenterPanelTitle("Waste"));
		return wastePanel;
	}
	private JPanel getCenterWinPanel() {
		JPanel winPanel = getNewCenterPanel();
		winPanel.add(getCenterPanelTitle("Win"));
		return winPanel;
	}

	
	private JButton getBackToShopBtn() {
		// create a backBtn (BagPanel/ShopPanel/settingsPanel component)
		JButton backToShopBtn = new JButton();
		backToShopBtn.setText("Back");
		backToShopBtn.setFont(new Font("Serif", Font.PLAIN, 15));
		backToShopBtn.setForeground(Color.WHITE);
		backToShopBtn.setBackground(Color.BLACK);
		backToShopBtn.setBounds(10,10,50,20);
		backToShopBtn.setFocusable(false);
		backToShopBtn.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
		
		addBackBtnListenerForShop(backToShopBtn);
		
		return backToShopBtn;
		
	}
	
	/**
	 * 
	 * @return JPanel for shop's buy and sell area.
	 */
	private JPanel getPanelForShop() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.black);
		panel.setBounds(120,70,560,429);
		panel.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.WHITE));
		
		return panel;
	}
	
	/**
	 * Listener for the back button in buy and sell area.
	 * it deselects all the buttons in shopButtonGroup.
	 */
	private void addBackBtnListenerForShop(JButton b) {
		b.addActionListener(e -> {
			// unSelected all buttons
			this.shopButtonGroup.clearSelection();
		});
	}

	/**
	 *
	 */
	private void addReorderBtnListener(JButton b) {
		b.addActionListener(e -> showBottomPanel(BottomPanel.REORDER));
	}
	
	
	private String constructMonsterDetail(Monster monster) {
		return String.format("%s\nHealth: %d\nDamage: %d\nLevel: %d\nPrice: "+monster.getPrice()+"\n       Monster",
				monster.getName(), monster.getMaxHealth(), monster.getDamage(), monster.getLevel());
	}
	
	private String constructWeaponDetail(Weapon weapon) {
		return String.format("%s\nRarity: %s\nDamage: %s\nPrice: "+weapon.getPrice()+"\n\n      Weapon",
				weapon.getName(), weapon.getRarityStr(), weapon.getDmg());
	}
	
	private String constructShieldDetail(Shield shield) {
		return String.format("%s\nRarity: %s\nShield: %s\nPrice: "+shield.getPrice()+"\n\n        Shield",
				shield.getName(), shield.getRarityStr(), shield.getShield());
	}

	private String constructMedicineDetail(Medicine meds) {
		return String.format("     %s\nEffect: +%s HP\nPrice: "+meds.getPrice()+"\n\n\n       Potion",
				meds.getName(), meds.getEffect());
	}

	private String constructMonsterRefundDetail(Monster monster) {
		return String.format("%s\nHealth: %d\nDamage: %d\nLevel: %d\nSell: "+monster.getRefundPrice()+" G\n       Monster",
				monster.getName(), monster.getMaxHealth(), monster.getDamage(), monster.getLevel());
	}

	private String constructWeaponRefundDetail(Weapon weapon) {
		return String.format("%s\nRarity: %s\nDamage: %s\nSell: "+weapon.getRefundPrice()+" G\n\n      Weapon",
				weapon.getName(), weapon.getRarityStr(), weapon.getDmg());
	}

	private String constructShieldRefundDetail(Shield shield) {
		return String.format("%s\nRarity: %s\nShield: %s\nSell: "+shield.getRefundPrice()+" G\n\n        Shield",
				shield.getName(), shield.getRarityStr(), shield.getShield());
	}

	private String constructMedicineRefundDetail(Medicine meds) {
		return String.format("     %s\nEffect: +%s HP\nSell: "+meds.getRefundPrice()+" G\n\n\n       Potion",
				meds.getName(), meds.getEffect());
	}
	
//-------------------------------//-------------------------------//-------------------------------//-------------------------------
//-------------------------------//-------------------------------//-------------------------------//-------------------------------
	private JPanel getBagTitle() {
		JPanel buyTitle = new JPanel();
		buyTitle.setLayout(null);
		buyTitle.setBackground(Color.black);
		buyTitle.setBounds(0, 0, 560, 40);
		buyTitle.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));
		
		JLabel titleLabel = new JLabel();
		titleLabel.setText("BAG");
		titleLabel.setForeground(Color.white);
		titleLabel.setBounds(250,0,100,40);
		
		
		JButton backButton = getBackButton();
		backButton.setText("Close");
		backButton.setBounds(10,10,80,20);
		
		buyTitle.add(backButton);
		buyTitle.add(titleLabel);
		
		return buyTitle;
	}
	
	private JPanel getBuyTitle() {
		JPanel buyTitle = new JPanel();
		buyTitle.setLayout(null);
		buyTitle.setBackground(Color.black);
		buyTitle.setBounds(0, 0, 560, 40);
		buyTitle.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));
		
		JLabel titleLabel = new JLabel();
		titleLabel.setText("BUY AREA");
		titleLabel.setForeground(Color.white);
		titleLabel.setBounds(250,0,100,40);
		
		//shows how the buyArea works
		JButton readMe = new JButton();
		readMe.setText("Read Me!");
		readMe.setBounds(100, 10, 100, 20);
		readMe.setForeground(Color.WHITE);
		readMe.setBackground(Color.BLACK);
		readMe.setFocusable(false);
		readMe.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
		readMe.addActionListener(e -> JOptionPane.showMessageDialog(mainFrame, "Refresh all = 50 Gold\n ", "Buy Area",JOptionPane.INFORMATION_MESSAGE ));
		
		
		//button to refresh the whole shop. cost 100gold
		JButton refreshAll = new JButton();
		refreshAll.setText("Refresh All");
		refreshAll.setBounds(450, 10, 100, 20);
		refreshAll.setForeground(Color.WHITE);
		refreshAll.setBackground(Color.BLACK);
		refreshAll.setFocusable(false);
		refreshAll.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
		refreshAll.addActionListener(e -> {
			  if (e.getSource() == refreshAll) {
				  if (gc.getGold() - 50 >= 0) {
					  int gold = gc.getGold() - 50;
					  gc.setGold(gold);
					  shop.refreshShop();
					  gc.isAbleToRefreshAll();
				  } else {
					  JOptionPane.showMessageDialog(mainFrame, "You currently do not have enough gold :(","Not Enough Gold!", JOptionPane.INFORMATION_MESSAGE );
				  }

			  }

		  });
		
		buyTitle.add(refreshAll);
		buyTitle.add(readMe);
		
		buyTitle.add(getBackToShopBtn());
		buyTitle.add(titleLabel);
		return buyTitle;
	}
	
	public JPanel getBigCenterPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(560,790));
		panel.setBackground(Color.black);
		
		return panel;
	}
	
	private JLabel getItemLabel() {
		JLabel itemLabel = new JLabel("",SwingConstants.CENTER);
		itemLabel.setFont(new Font("Arial", Font.BOLD, 20));
		itemLabel.setForeground(Color.white);
		
		return itemLabel;
	}
	
//	private JButton getRefreshButton() {
//		JButton refreshButtons = new JButton();
//		refreshButtons.setText("Refresh");
//		refreshButtons.setFont(new Font("Serif", Font.PLAIN, 15));
//		refreshButtons.setForeground(Color.WHITE);
//		refreshButtons.setBackground(Color.BLACK);
//		refreshButtons.setFocusable(false);
//		refreshButtons.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
//		
//		return refreshButtons;
//	}
	
	private JPanel getItemPanel() {

		JPanel itemPanel = new JPanel();
		itemPanel.setLayout(new FlowLayout());
		itemPanel.setBackground(Color.black);
		
		return itemPanel;
	}

	private JButton getBuyButtonsForBuyArea() {
		JButton button = new JButton();
		button.setText("BUY");
		button.setFont(new Font("Arial", Font.PLAIN, 10));
		button.setPreferredSize(new Dimension(100,20));
		button.setFocusable(false);
		return button;
	}

	private JTextArea getItemDetails() {
		
		JTextArea detail = new JTextArea();
		detail.setPreferredSize(new Dimension(100,100));
		detail.setForeground(Color.WHITE);
		detail.setBackground(Color.BLACK);
		detail.setBorder(null);
		detail.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
		
		detail.setEditable(false);
		return detail;
	}
	private JPanel getMonsterBuyPanel() {
		
		//get panel to put details
		JPanel monsterPanel = getItemPanel();
		monsterPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		monsterPanel.setBounds(10,40,525,140);
		
		// add details to the panel one by one
		for (int indexInList=0; indexInList<shop.getMonstersForSell().size(); indexInList++) {
			Monster monster = (Monster) shop.getMonstersForSell().get(indexInList);
			
			JTextArea monsterDetail = getItemDetails();
			monsterDetail.setText(constructMonsterDetail(monster));
			monsterPanel.add(monsterDetail);
		}
				
		for (int indexInList=0; indexInList<shop.getMonstersForSell().size(); indexInList++) {
			JButton monsterButton = getBuyButtonsForBuyArea();
			monsterButtons.add(monsterButton);
			monsterPanel.add(monsterButton);
		}
		for (int i = 0; i<shop.getMonstersForSell().size();i++) {
			JButton button = monsterButtons.get(i);
			
			final int innerI = (i);
			
			button.addActionListener(e -> {
				if (e.getSource() == button) {
					if (gc.getGold()-shop.getMonstersForSell().get(innerI).getPrice() >= 0) {
						gc.setGold(gc.getGold()-shop.getMonstersForSell().get(innerI).getPrice());
						gc.addItemToBag((Monster) shop.getMonstersForSell().get(innerI));
						button.setEnabled(false);
						button.setText("SOLD");
						updateBagPanel();
					}else {
						  JOptionPane.showMessageDialog(mainFrame, "You currently do not have enough gold :(","Not Enough Gold!", JOptionPane.INFORMATION_MESSAGE );
					}

				}
			});
		}
		
		return monsterPanel;
	}
	
	private JPanel getWeaponBuyPanel() {
		//get panel to put details
		JPanel weaponPanel = getItemPanel();
		weaponPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		weaponPanel.setBounds(10,220,525,140);
		// add detaindexInListls to the panel one by one
		for (int indexInList=0; indexInList<shop.getWeaponsForSell().size(); indexInList++) {
			GameItem weapon = shop.getWeaponsForSell().get(indexInList);
			
			JTextArea weaponDetail = getItemDetails();
			weaponDetail.setText(constructWeaponDetail((Weapon)weapon));
			weaponPanel.add(weaponDetail);
		}
						
		for (int indexInList=0; indexInList<shop.getWeaponsForSell().size(); indexInList++) {
			JButton weaponButton = getBuyButtonsForBuyArea();
			weaponButtons.add(weaponButton);
			weaponPanel.add(weaponButton);
		}	
		for (int i = 0; i<shop.getWeaponsForSell().size();i++) {
			JButton button = weaponButtons.get(i);
			
			final int innerI = (i);
			
			button.addActionListener(e -> {
				if (e.getSource() == button) {
					if (gc.getGold()-shop.getWeaponsForSell().get(innerI).getPrice() >= 0) {
						gc.setGold(gc.getGold()-shop.getWeaponsForSell().get(innerI).getPrice());
						gc.addItemToBag((Weapon) shop.getWeaponsForSell().get(innerI));
						button.setEnabled(false);
						button.setText("SOLD");
						updateBagPanel();
					}else {
						  JOptionPane.showMessageDialog(mainFrame, "You currently do not have enough gold :(","Not Enough Gold!", JOptionPane.INFORMATION_MESSAGE );

					}

				}
			});
		}
		return weaponPanel;
		
	}
	private JPanel getShieldBuyPanel() {
		//get panel to put details
		JPanel shieldPanel = getItemPanel();
		shieldPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		shieldPanel.setBounds(10,400,525,140);
		
		// add detaindexInListls to the panel one by one
		for (int indexInList=0; indexInList<shop.getShieldsForSell().size(); indexInList++) {
			GameItem shield = shop.getShieldsForSell().get(indexInList);
			
			JTextArea shieldDetail = getItemDetails();
			shieldDetail.setText(constructShieldDetail((Shield)shield));
			shieldPanel.add(shieldDetail);
		}
						
		for (int indexInList=0; indexInList<shop.getShieldsForSell().size(); indexInList++) {
			JButton shieldButton = getBuyButtonsForBuyArea();
			shieldButtons.add(shieldButton);
			shieldPanel.add(shieldButton);
		}	
		
		for (int i = 0; i<shop.getShieldsForSell().size();i++) {
			JButton button = shieldButtons.get(i);
			
			final Integer innerI = (i);
			
			button.addActionListener(e -> {
				if (e.getSource() == button) {
					if (gc.getGold()-shop.getShieldsForSell().get(innerI).getPrice() >= 0) {
						gc.setGold(gc.getGold()-shop.getShieldsForSell().get(innerI).getPrice());
						gc.addItemToBag((Shield) shop.getShieldsForSell().get(innerI));
						button.setEnabled(false);
						button.setText("SOLD");
						updateBagPanel();
					}else {
						  JOptionPane.showMessageDialog(mainFrame, "You currently do not have enough gold :(","Not Enough Gold!", JOptionPane.INFORMATION_MESSAGE );

					}
				}
			});
		}
		return shieldPanel;
	}
	
	private JPanel getMedBuyPanel() {
		//get panel to put details
		JPanel medPanel = getItemPanel();
		medPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		medPanel.setBounds(10,580,525,140);
		
		
		// add detaindexInListls to the panel one by one
		for (int indexInList=0; indexInList<shop.getMedsForSell().size(); indexInList++) {
			Medicine med = (Medicine) shop.getMedsForSell().get(indexInList);
			
			JTextArea medDetail = getItemDetails();
			medDetail.setText(constructMedicineDetail(med));
			medPanel.add(medDetail);
		}
						
		for (int indexInList=0; indexInList<shop.getMedsForSell().size(); indexInList++) {
			JButton medButton= getBuyButtonsForBuyArea();
			medButtons.add(medButton);
			medPanel.add(medButton);
			
		}	
		
		
		for (int i = 0; i<shop.getMedsForSell().size();i++) {
			JButton button = medButtons.get(i);
			
			final int innerI = (i);
			
			button.addActionListener(e -> {
				if (e.getSource() == button) {
					if (gc.getGold()-shop.getMedsForSell().get(innerI).getPrice() >= 0) {
						gc.setGold(gc.getGold()-shop.getMedsForSell().get(innerI).getPrice());
						gc.addItemToBag((Medicine)shop.getMedsForSell().get(innerI));
						button.setEnabled(false);
						button.setText("SOLD");
						updateBagPanel();
					}else {
						JOptionPane.showMessageDialog(mainFrame, "You currently do not have enough gold :(","Not Enough Gold!", JOptionPane.INFORMATION_MESSAGE );
					}

				}
			});
			
		}
		return medPanel;
	}

	

	
	
	
	
}

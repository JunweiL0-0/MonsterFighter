package main.java.ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.PopupMenu;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import main.java.controller.GameController;

public class MainScreen {
	private JFrame mainFrame;
	private GameController gc;
	
	public MainScreen(GameController gc) {
		this.gc = gc;
		initialize();
		mainFrame.setVisible(true);
		
	}
	public void initialize() {
		
		this.mainFrame = getMainFrame();
		
		this.mainFrame.getContentPane().add(getTopPanel());
		
		this.mainFrame.getContentPane().add(getWestPanel());
		
		this.mainFrame.getContentPane().add(getEastPanel());
		
		this.mainFrame.getContentPane().add(getTextArea());
		
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
		
		JTextArea newTextArea = new JTextArea();
		newTextArea.setText("Player Name: \nGold: ");
		newTextArea.setForeground(Color.white);
		newTextArea.setBounds(0,0,120,70);
		newTextArea.setBackground(Color.BLACK);
		newTextArea.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.WHITE));
		
		newTopPanel.add(newTextArea);
		return newTopPanel;
	}
	
	private JPanel getWestPanel() {
			
		JPanel newWestPanel = new JPanel();
		newWestPanel.setBounds(0,60,120,440);
		newWestPanel.setBackground(Color.black);
		newWestPanel.setBorder(BorderFactory.createMatteBorder(0, 3, 3, 3, Color.WHITE));
	
		return newWestPanel;
	}
	
	private JPanel getEastPanel() {
		
		JPanel newEastPanel = new JPanel();
		newEastPanel.setBackground(Color.black);
		newEastPanel.setBounds(680,60,120,440);
		newEastPanel.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.WHITE));
		
		return newEastPanel;
	}
	
	private JTextArea getTextArea() {
		JTextArea newTextArea = new JTextArea();
		newTextArea.setBounds(120,350,560,150);
		newTextArea.setBackground(Color.BLACK);
		newTextArea.setBorder(BorderFactory.createMatteBorder(3, 0, 3, 0, Color.WHITE));
		
		return newTextArea;
	}
	
}	

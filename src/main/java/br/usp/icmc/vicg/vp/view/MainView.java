package br.usp.icmc.vicg.vp.view;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class MainView extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private static final int PANELS_WIDTH = 700;
	private static final int RESULTS_WIDTH = 300;
	
	private static final int TOP_PANEL_HEIGHT= 50;
	private static final int MIDDLE_PANEL_HEIGHT= 400;
	private static final int BOTTOM_PANEL_HEIGHT= 150;
	
	private static final int MIN_SIZE = 100;
	private static final int MAX_SIZE = Short.MAX_VALUE;

	protected JPanel mainPanel;
	
	protected JPanel topPanel;
	protected JPanel middlePanel;
	protected JPanel bottomPanel;
	protected JPanel eastTopPanel;
	protected JPanel eastPanel;
	
	public MainView() {

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		this.setLayout(new GridLayout(1,1));
		
		initComponents();
		
		this.add(mainPanel);
		
		pack();
	}
	
	private void initComponents() {

		mainPanel = new JPanel();
		
		topPanel = new JPanel();
		middlePanel = new JPanel();
		bottomPanel = new JPanel();
		eastPanel = new JPanel();
		eastTopPanel = new JPanel();
		
		topPanel.setPreferredSize(new Dimension(PANELS_WIDTH, TOP_PANEL_HEIGHT));
		topPanel.setSize(new Dimension(PANELS_WIDTH, TOP_PANEL_HEIGHT));
		topPanel.setMinimumSize(new Dimension(MIN_SIZE, TOP_PANEL_HEIGHT));
		topPanel.setMaximumSize(new Dimension(MAX_SIZE, TOP_PANEL_HEIGHT));
		topPanel.setLayout(new GridLayout(1,1));
		topPanel.setBorder(BorderFactory.createEtchedBorder());
		
		middlePanel.setPreferredSize(new Dimension(PANELS_WIDTH, MIDDLE_PANEL_HEIGHT));
		middlePanel.setSize(new Dimension(PANELS_WIDTH, MIDDLE_PANEL_HEIGHT));
		middlePanel.setMinimumSize(new Dimension(MIN_SIZE, MIN_SIZE));
		middlePanel.setMaximumSize(new Dimension(MAX_SIZE, MAX_SIZE));
		middlePanel.setLayout(new GridLayout(1,1));
		middlePanel.setBorder(BorderFactory.createEtchedBorder());
		
		bottomPanel.setPreferredSize(new Dimension(PANELS_WIDTH, BOTTOM_PANEL_HEIGHT));
		bottomPanel.setSize(new Dimension(PANELS_WIDTH, BOTTOM_PANEL_HEIGHT));
		bottomPanel.setMinimumSize(new Dimension(MIN_SIZE, MIN_SIZE));
		bottomPanel.setMaximumSize(new Dimension(MAX_SIZE, MAX_SIZE));
		bottomPanel.setLayout(new GridLayout(1,1));
		bottomPanel.setBorder(BorderFactory.createEtchedBorder());
		
		eastPanel.setPreferredSize(new Dimension(RESULTS_WIDTH, MIN_SIZE));
		eastPanel.setSize(new Dimension(RESULTS_WIDTH, MIN_SIZE));
		eastPanel.setMinimumSize(new Dimension(MIN_SIZE, MIN_SIZE));
		eastPanel.setMaximumSize(new Dimension(RESULTS_WIDTH, MAX_SIZE));
		eastPanel.setLayout(new GridLayout(1,1));
		eastPanel.setBorder(BorderFactory.createEtchedBorder());
		
		eastTopPanel.setPreferredSize(new Dimension(RESULTS_WIDTH, TOP_PANEL_HEIGHT));
		eastTopPanel.setSize(new Dimension(RESULTS_WIDTH, TOP_PANEL_HEIGHT));
		eastTopPanel.setMinimumSize(new Dimension(MIN_SIZE, TOP_PANEL_HEIGHT));
		eastTopPanel.setMaximumSize(new Dimension(RESULTS_WIDTH, TOP_PANEL_HEIGHT));
		eastTopPanel.setLayout(new GridLayout(1,1));
		eastTopPanel.setBorder(BorderFactory.createEtchedBorder());
		
		GroupLayout layout = new GroupLayout(mainPanel);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(layout.createSequentialGroup().
				addGroup(layout.createParallelGroup().
					addComponent(topPanel).
					addComponent(middlePanel).
					addComponent(bottomPanel)).
				addGroup(layout.createParallelGroup().
					addComponent(eastTopPanel).
					addComponent(eastPanel)));
		
		layout.setVerticalGroup(layout.createParallelGroup().
				addGroup(layout.createSequentialGroup().
					addComponent(topPanel).
					addComponent(middlePanel).
					addComponent(bottomPanel)).
				addGroup(layout.createSequentialGroup().
					addComponent(eastTopPanel).
					addComponent(eastPanel)));
		
		mainPanel.setLayout(layout);
	}

	public JPanel getMainPanel() {
		return mainPanel; 
	}
	
	public JPanel getTopPanel() {
		return topPanel;
	}

	public JPanel getMiddlePanel() {
		return middlePanel;
	}

	public JPanel getBottomPanel() {
		return bottomPanel;
	}

	public JPanel getEastPanel() {
		return eastPanel;
	}

	public JPanel getEastTopPanel() {
		return eastTopPanel;
	}
}

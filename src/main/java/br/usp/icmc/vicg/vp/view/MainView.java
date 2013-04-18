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
		
		topPanel.setPreferredSize(new Dimension(ViewParameters.PANELS_WIDTH, ViewParameters.TOP_PANEL_HEIGHT));
		topPanel.setSize(new Dimension(ViewParameters.PANELS_WIDTH, ViewParameters.TOP_PANEL_HEIGHT));
		topPanel.setMinimumSize(new Dimension(ViewParameters.MIN_SIZE, ViewParameters.TOP_PANEL_HEIGHT));
		topPanel.setMaximumSize(new Dimension(ViewParameters.MAX_SIZE, ViewParameters.TOP_PANEL_HEIGHT));
		topPanel.setLayout(new GridLayout(1,1));
		topPanel.setBorder(BorderFactory.createEtchedBorder());
		
		middlePanel.setPreferredSize(new Dimension(ViewParameters.PANELS_WIDTH, ViewParameters.MIDDLE_PANEL_HEIGHT));
		middlePanel.setSize(new Dimension(ViewParameters.PANELS_WIDTH, ViewParameters.MIDDLE_PANEL_HEIGHT));
		middlePanel.setMinimumSize(new Dimension(ViewParameters.MIN_SIZE,ViewParameters. MIN_SIZE));
		middlePanel.setMaximumSize(new Dimension(ViewParameters.MAX_SIZE, ViewParameters.MAX_SIZE));
		middlePanel.setLayout(new GridLayout(1,1));
		middlePanel.setBorder(BorderFactory.createEtchedBorder());
		
		bottomPanel.setPreferredSize(new Dimension(ViewParameters.PANELS_WIDTH, ViewParameters.BOTTOM_PANEL_HEIGHT));
		bottomPanel.setSize(new Dimension(ViewParameters.PANELS_WIDTH, ViewParameters.BOTTOM_PANEL_HEIGHT));
		bottomPanel.setMinimumSize(new Dimension(ViewParameters.MIN_SIZE, ViewParameters.MIN_SIZE));
		bottomPanel.setMaximumSize(new Dimension(ViewParameters.MAX_SIZE, ViewParameters.MAX_SIZE));
		bottomPanel.setLayout(new GridLayout(1,1));
		bottomPanel.setBorder(BorderFactory.createEtchedBorder());
		
		eastPanel.setPreferredSize(new Dimension(ViewParameters.RESULTS_WIDTH, ViewParameters.MIN_SIZE));
		eastPanel.setSize(new Dimension(ViewParameters.RESULTS_WIDTH, ViewParameters.MIN_SIZE));
		eastPanel.setMinimumSize(new Dimension(ViewParameters.MIN_SIZE, ViewParameters.MIN_SIZE));
		eastPanel.setMaximumSize(new Dimension(ViewParameters.RESULTS_WIDTH, ViewParameters.MAX_SIZE));
		eastPanel.setLayout(new GridLayout(1,1));
		eastPanel.setBorder(BorderFactory.createEtchedBorder());
		
		eastTopPanel.setPreferredSize(new Dimension(ViewParameters.RESULTS_WIDTH, ViewParameters.TOP_PANEL_HEIGHT));
		eastTopPanel.setSize(new Dimension(ViewParameters.RESULTS_WIDTH, ViewParameters.TOP_PANEL_HEIGHT));
		eastTopPanel.setMinimumSize(new Dimension(ViewParameters.MIN_SIZE, ViewParameters.TOP_PANEL_HEIGHT));
		eastTopPanel.setMaximumSize(new Dimension(ViewParameters.RESULTS_WIDTH, ViewParameters.TOP_PANEL_HEIGHT));
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

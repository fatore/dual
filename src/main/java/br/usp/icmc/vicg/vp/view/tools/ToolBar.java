package br.usp.icmc.vicg.vp.view.tools;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JToolBar;

import br.usp.icmc.vicg.vp.controller.ControllerHandle;

public class ToolBar extends JPanel {

	private static final long serialVersionUID = 1L;

	private JToolBar toolBar;
	
	public ToolBar() {
		
		super();

		this.setLayout(new FlowLayout(FlowLayout.LEADING));
		
		// Holder for buttons
		this.toolBar = new JToolBar();
		toolBar.setFloatable(false);
		
		// Add buttons
		addSelectSubset();
		
		// Add tool bar to pane
		this.add(toolBar);
	}
	
	private void addSelectSubset() {

		ToolButton projectSubset = new ToolButton("Select Subset");
		
		projectSubset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				ControllerHandle.getInstance().reprojectSubset();
			}
		});

		toolBar.add(projectSubset);
	}
	
	@Override
	public void setBackground(Color color) {
		
		super.setBackground(color);
		
		if (toolBar != null) {

			for (Component c : toolBar.getComponents()) {
			
				c.setBackground(color);
			}
			toolBar.setBackground(color);
		}
	}
}

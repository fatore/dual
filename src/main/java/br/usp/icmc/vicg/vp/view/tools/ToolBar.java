package br.usp.icmc.vicg.vp.view.tools;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

import projection.model.ProjectionModel;
import projection.model.Scalar;
import br.usp.icmc.vicg.vp.controller.ControllerHandle;
import br.usp.icmc.vicg.vp.view.ViewParameters;

public class ToolBar extends JPanel {

	private static final long serialVersionUID = 1L;

	private JPanel toolBar;

	private JComboBox<Scalar> scalarComboBox;

	public ToolBar() {

		super();

		this.setLayout(new GridBagLayout());

		// Holder for elements
		toolBar = new JPanel();

		// Create elements
		JButton subsetButton = createSubsetButton();
		
		scalarComboBox = createScalarComboBox();
		toolBar.add(scalarComboBox);

		GroupLayout layout = new GroupLayout(toolBar);

		GroupLayout.ParallelGroup vGroup = layout.createParallelGroup();
		vGroup.addComponent(subsetButton).
		addComponent(scalarComboBox);
		layout.setVerticalGroup(vGroup);

		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
		hGroup.addComponent(subsetButton).
		addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).
		addComponent(scalarComboBox);
		layout.setHorizontalGroup(hGroup);

		toolBar.setLayout(layout);

		// Add tool bar to pane
		this.add(toolBar, new GridBagConstraints());
	}

	private ToolButton createSubsetButton() {

		ToolButton projectSubset = new ToolButton(
				"<html><center>Select<br/>Subset</center></html>",
				"Select items or (and) dimensions subset.");

		projectSubset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				ControllerHandle.getInstance().reprojectSubset();
			}
		});

		return projectSubset;
	}
	
	private JComboBox<Scalar> createScalarComboBox() {

		JComboBox<Scalar> scalarComboBox = new JComboBox<>();

		scalarComboBox.addItemListener(new ScalarComboBoxListener());
		scalarComboBox.setBackground(getBackground());
		scalarComboBox.setPreferredSize(
				new Dimension(ViewParameters.SCALAR_BOX_WIDTH, ViewParameters.SCALAR_BOX_HEIGHT));
		scalarComboBox.setMaximumSize(
				new Dimension(ViewParameters.SCALAR_BOX_WIDTH, ViewParameters.SCALAR_BOX_HEIGHT));
		scalarComboBox.setMinimumSize(
				new Dimension(ViewParameters.SCALAR_BOX_WIDTH, ViewParameters.SCALAR_BOX_HEIGHT));
		scalarComboBox.setFont(new Font(null, Font.PLAIN, 14));

		return scalarComboBox;
	}

	public void attach(ProjectionModel model) {

		DefaultComboBoxModel<Scalar> comboBoxModel = new DefaultComboBoxModel<>();
		for (Scalar s : model.getScalars()) {
			
			comboBoxModel.addElement(s);
		}
		scalarComboBox.setModel(comboBoxModel);
		scalarComboBox.setSelectedItem(model.getSelectedScalar());
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

	class ScalarComboBoxListener implements ItemListener {

		public void itemStateChanged(java.awt.event.ItemEvent evt) {

			Scalar scalar = (Scalar) scalarComboBox.getSelectedItem();
			ControllerHandle.getInstance().changeModelsScalar(scalar.getName());
		}
	}
}

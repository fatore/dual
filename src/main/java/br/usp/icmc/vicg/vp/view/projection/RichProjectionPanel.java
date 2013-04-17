package br.usp.icmc.vicg.vp.view.projection;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ItemListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

import projection.model.ProjectionModel;
import projection.model.Scalar;
import visualizationbasics.color.ColorScalePanel;
import visualizationbasics.view.selection.AbstractSelection;

public class RichProjectionPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static final int VERTICAL_GAP = 15;
	private static final int TITLE_FONT_SIZE = 12;
	
	private static final int COLOR_SCALE_WIDTH = 120;
	private static final int COLOR_SCALE_HEIGHT= 15;
	
	private static final int SCALAR_BOX_WIDTH = 60;
	private static final int SCALAR_BOX_HEIGHT= 16;
	
	private ProjectionPanel projectionPanel;
	private JLabel titleLabel;
	private ColorScalePanel colorscale;
	private JComboBox<String> scalarComboBox;
	private JPanel upperBar;
	
	public RichProjectionPanel(ProjectionModel model, String text) {
		
		projectionPanel = new ProjectionPanel(model);
		
		upperBar = new JPanel();
		
		titleLabel = new JLabel();
		titleLabel.setOpaque(true);
		titleLabel.setText(text);
		titleLabel.setFont(new Font(null, Font.BOLD, TITLE_FONT_SIZE));
		
		scalarComboBox = createScalarComboBox(model);
		
		colorscale = createColorScale(model);
		
		GroupLayout layout = new GroupLayout(upperBar);
		
		GroupLayout.ParallelGroup vGroup = layout.createParallelGroup();
		vGroup.addComponent(titleLabel).
		addComponent(scalarComboBox).
		addComponent(colorscale);
		layout.setVerticalGroup(vGroup);
		
		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
		hGroup.addComponent(titleLabel).
		addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).
		addComponent(colorscale).
		addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).
		addComponent(scalarComboBox);
		layout.setHorizontalGroup(hGroup);
		
		upperBar.setLayout(layout);
		
		this.setLayout(new BorderLayout(0, VERTICAL_GAP));
		
		this.add(upperBar, BorderLayout.PAGE_START);
		this.add(projectionPanel, BorderLayout.CENTER);
	}
	
	public ProjectionPanel getProjectionPanel() {
		
		return projectionPanel;
	}
	
	public void setSelection(AbstractSelection selection) {
		
		this.projectionPanel.setSelection(selection);
	}
	
	private ColorScalePanel createColorScale(ProjectionModel model) {
		
		ColorScalePanel colorscale = new ColorScalePanel(null);
		colorscale.setBackground(getBackground());
		colorscale.setPreferredSize(new Dimension(COLOR_SCALE_WIDTH, COLOR_SCALE_HEIGHT));
		colorscale.setMaximumSize(new Dimension(COLOR_SCALE_WIDTH, COLOR_SCALE_HEIGHT));
		colorscale.setMinimumSize(new Dimension(COLOR_SCALE_WIDTH, COLOR_SCALE_HEIGHT));
  		colorscale.setColorTable(model.getColorTable());
		
		return colorscale;
	}
	
	private JComboBox<String> createScalarComboBox(ProjectionModel model) {
		
		DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
		for (Scalar s : model.getScalars()) {
			
			comboBoxModel.addElement(s.getName());
        }
		JComboBox<String> scalarComboBox = new JComboBox<>(comboBoxModel);
		scalarComboBox.setSelectedItem(model.getSelectedScalar());
		scalarComboBox.addItemListener(new ScalarComboBoxListener());
		scalarComboBox.setBackground(getBackground());
		scalarComboBox.setPreferredSize(new Dimension(SCALAR_BOX_WIDTH, SCALAR_BOX_HEIGHT));
		scalarComboBox.setMaximumSize(new Dimension(SCALAR_BOX_WIDTH, SCALAR_BOX_HEIGHT));
		scalarComboBox.setMinimumSize(new Dimension(SCALAR_BOX_WIDTH, SCALAR_BOX_HEIGHT));
		scalarComboBox.setFont(new Font(null, Font.PLAIN, 12));
		
		return scalarComboBox;
	}
	
	class ScalarComboBoxListener implements ItemListener {
		
		public void itemStateChanged(java.awt.event.ItemEvent evt) {
        	
        	String name = (String) scalarComboBox.getSelectedItem();
        	((ProjectionModel) projectionPanel.getModel()).setSelectedScalar(
        			((ProjectionModel) projectionPanel.getModel()).getScalar(name));
        	
        	((ProjectionModel) projectionPanel.getModel()).setChanged();
        	((ProjectionModel) projectionPanel.getModel()).notifyObservers();
        }
	}
	
	@Override
	public final void setBackground(Color bg) {

		super.setBackground(bg);

		if (this.colorscale != null) {
			
			this.colorscale.setBackground(bg);
		}
		if (this.projectionPanel != null) {
			
			this.projectionPanel.setBackground(bg);
		}
		if (this.titleLabel != null) {
			
			this.titleLabel.setBackground(bg);
		}
		if (upperBar != null) {
			
			this.upperBar.setBackground(bg);
		}
	}
}

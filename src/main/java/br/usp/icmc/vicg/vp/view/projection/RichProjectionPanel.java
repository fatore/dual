package br.usp.icmc.vicg.vp.view.projection;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

import projection.model.ProjectionModel;
import visualizationbasics.color.ColorScalePanel;
import visualizationbasics.view.selection.AbstractSelection;
import br.usp.icmc.vicg.vp.view.ViewParameters;

public class RichProjectionPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private ProjectionPanel projectionPanel;
	private JLabel titleLabel;
	private ColorScalePanel colorscale;
	private JPanel upperBar;
	
	public RichProjectionPanel(ProjectionModel model, String text) {
		
		projectionPanel = new ProjectionPanel(model);
		
		upperBar = new JPanel();
		
		titleLabel = new JLabel();
		titleLabel.setOpaque(true);
		titleLabel.setText(text);
		titleLabel.setFont(new Font(null, Font.BOLD, ViewParameters.TITLE_FONT_SIZE));
		
		colorscale = createColorScale(model);
		
		GroupLayout layout = new GroupLayout(upperBar);
		
		GroupLayout.ParallelGroup vGroup = layout.createParallelGroup();
		vGroup.addComponent(titleLabel).
		addComponent(colorscale);
		layout.setVerticalGroup(vGroup);
		
		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
		hGroup.addComponent(titleLabel).
		addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).
		addComponent(colorscale);
		layout.setHorizontalGroup(hGroup);
		
		upperBar.setLayout(layout);
		
		this.setLayout(new BorderLayout(0, ViewParameters.VERTICAL_GAP));
		
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
		colorscale.setPreferredSize(
				new Dimension(ViewParameters.COLOR_SCALE_WIDTH, ViewParameters.COLOR_SCALE_HEIGHT));
		colorscale.setMaximumSize(
				new Dimension(ViewParameters.COLOR_SCALE_WIDTH, ViewParameters.COLOR_SCALE_HEIGHT));
		colorscale.setMinimumSize(
				new Dimension(ViewParameters.COLOR_SCALE_WIDTH, ViewParameters.COLOR_SCALE_HEIGHT));
  		colorscale.setColorTable(model.getColorTable());
		
		return colorscale;
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

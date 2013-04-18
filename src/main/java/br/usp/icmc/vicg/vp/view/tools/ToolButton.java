package br.usp.icmc.vicg.vp.view.tools;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;

import br.usp.icmc.vicg.vp.view.ViewParameters;

public class ToolButton extends JButton {

	private static final long serialVersionUID = 332794475342762251L;

	public ToolButton(String text, String toolTip) {

		this.setText(text);
		this.setToolTipText(toolTip);
		
		this.setMaximumSize(new Dimension(ViewParameters.MAX_SIZE, ViewParameters.BUTTONS_HEIGHT));
		this.setMinimumSize(new Dimension(0, ViewParameters.BUTTONS_HEIGHT));
		this.setFont(new Font(null, Font.BOLD, 12));
	}
}

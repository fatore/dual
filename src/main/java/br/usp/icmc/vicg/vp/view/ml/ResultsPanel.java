package br.usp.icmc.vicg.vp.view.ml;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import br.usp.icmc.vicg.vp.model.ml.Result;

public class ResultsPanel extends JPanel {

	private static final long serialVersionUID = 2783214242254050506L;

	private static final int TITLE_FONT_SIZE = 16;

	private Object[] colNames =  new Object[]{"Reduction Technique", "Acc.(%)",
			"Time(s)"};
	
	private JLabel title;
	private DefaultTableModel tableModel;

	public ResultsPanel() {

		this.setLayout(new BorderLayout());

		title = new JLabel("Classification Results", JLabel.CENTER);
		title.setVerticalTextPosition(JLabel.CENTER);
		title.setOpaque(true);
		title.setFont(new Font(null, Font.BOLD, TITLE_FONT_SIZE));
		
		tableModel = new DefaultTableModel(colNames ,0);
		
		JTable table = new JTable();
		table.setModel(tableModel);
		table.setAutoCreateRowSorter(true);
		table.getColumnModel().getColumn(0).setPreferredWidth(110);
		table.getColumnModel().getColumn(1).setPreferredWidth(10);
		table.getColumnModel().getColumn(2).setPreferredWidth(10);
		table.setFont(new Font(null, Font.PLAIN, 16));
		
		JTableHeader header = table.getTableHeader();
		header.setFont(new Font(null, Font.BOLD, 14));

		JScrollPane tableScrollPane = new JScrollPane(table);
		this.add(tableScrollPane);
	}
	
	public JLabel getTitle() {

		return title;
	}

	public void addRow(Result result) {

		if (result != null) {
			
			tableModel.addRow(new Object[]{
					result.getTechnique(), 
					result.getAccuracy(),
					result.getClassTime()
			});
		}
		else {
			
			tableModel.addRow(new Object[]{"-","-","-"});
		}
	}
}

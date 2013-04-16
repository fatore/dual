package br.usp.icmc.vicg.vp.controller;

import java.awt.Color;

import javax.swing.JPanel;

import br.usp.icmc.vicg.vp.model.data.DataMatrix;
import br.usp.icmc.vicg.vp.model.ml.ResultFactory;
import br.usp.icmc.vicg.vp.model.projection.DualProjections;
import br.usp.icmc.vicg.vp.model.projection.DualProjectionsFactory;
import br.usp.icmc.vicg.vp.model.tree.ContextVertex;
import br.usp.icmc.vicg.vp.model.tree.InteractionsTree;
import br.usp.icmc.vicg.vp.view.ml.ResultsPanel;
import br.usp.icmc.vicg.vp.view.projection.dual.DualProjectionsPanel;
import br.usp.icmc.vicg.vp.view.tools.ToolBar;
import br.usp.icmc.vicg.vp.view.tree.InteractionsTreePanel;

public class Controller {

	// Data
	private DataMatrix dataMatrix;

	// Models
	private InteractionsTree tree;

	// View Slots
	private JPanel dualPanelSlot;

	// Views
	private ToolBar toolBar;
	private InteractionsTreePanel treePanel;
	private ResultsPanel resultsPanel;

	public void init(JPanel toolbarSlot, JPanel dualPanelSlot,
			JPanel treePanelSlot, JPanel resultsPanelSlot, JPanel resultsTitleSlot) {

		// Tool bar
		toolBar = new ToolBar();
		toolbarSlot.add(toolBar);
		toolbarSlot.validate();

		// Dual panel
		this.dualPanelSlot = dualPanelSlot;

		// Tree panel
		this.treePanel = new InteractionsTreePanel();
		this.treePanel.setBackground(Color.WHITE);
		treePanelSlot.add(treePanel);
		treePanelSlot.validate();

		// Results panel
		this.resultsPanel = new ResultsPanel();
		resultsPanelSlot.add(resultsPanel);

		resultsTitleSlot.add(resultsPanel.getTitle());
	}

	public void attachData(DataMatrix dataMatrix) {

		this.dataMatrix = dataMatrix;

		tree = new InteractionsTree();
		treePanel.attach(tree);
		treePanel.revalidate();

		addVertexToTree(dataMatrix, false);

		initResults(dataMatrix);
	}

	private void initResults(DataMatrix dataMatrix) {

		try {
			resultsPanel.addRow(ResultFactory.getInstance(dataMatrix,"None"));

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void addVertexToTree(DataMatrix dataMatrix, boolean runClass) {
		
		int newVertexId = tree.getGraph().getNumVertices() + 1;
		
		if (runClass) {
			
			try {
				resultsPanel.addRow(ResultFactory.getInstance(
						dataMatrix,"Dual (" + newVertexId + ")"));
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		
		// Create projections
		DualProjections dualProjections = DualProjectionsFactory.getInstance(dataMatrix);

		// Create new vertex
		DualProjectionsPanel dualPanel = createDualPanel(dualProjections);
		ContextVertex newVertex = new ContextVertex(newVertexId,dualProjections,dualPanel);

		setCurrentProjection(newVertex);

		tree.addNewVertex(newVertex);
		treePanel.getComponent().layoutGraph();
		treePanel.revalidate();
	}

	private DualProjectionsPanel createDualPanel(DualProjections dualProjections) {

		DualProjectionsPanel dualPanel = new DualProjectionsPanel();
		dualPanel.setBackground(Color.WHITE);
		dualPanel.attach(dualProjections);

		return dualPanel;
	}

	private void setCurrentProjection(ContextVertex vertex) {

		dualPanelSlot.removeAll();
		dualPanelSlot.add(vertex.getDualPanel());
		dualPanelSlot.revalidate();
		dualPanelSlot.repaint();
	}

	public void changeContextToVertex(Integer value) {

		ContextVertex newCurrent = (ContextVertex) tree.getVertexAt(value - 1);
		setCurrentProjection(newCurrent);
		tree.setCurrentVertex(newCurrent, true);
	}

	public void reprojectSubset() {

		// Get current item model
		ContextVertex vertex = (ContextVertex) tree.getCurrentVertex();

		// Create new data matrix
		DataMatrix selectedData = dataMatrix.getDualSubset(vertex.getDualProjections());

		if (selectedData != null) {

			// Create and add new projections
			addVertexToTree(selectedData, true);
		}
		// Clear selection
		vertex.getDualPanel().clearSelections();
	}
}











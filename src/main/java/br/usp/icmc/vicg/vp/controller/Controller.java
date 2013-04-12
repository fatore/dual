package br.usp.icmc.vicg.vp.controller;

import java.awt.Color;

import javax.swing.JPanel;

import br.usp.icmc.vicg.vp.model.data.DataMatrix;
import br.usp.icmc.vicg.vp.model.projection.DualProjections;
import br.usp.icmc.vicg.vp.model.projection.DualProjectionsFactory;
import br.usp.icmc.vicg.vp.model.tree.ContextVertex;
import br.usp.icmc.vicg.vp.model.tree.InteractionsTree;
import br.usp.icmc.vicg.vp.view.projection.dual.DualProjectionsPanel;
import br.usp.icmc.vicg.vp.view.tools.ToolBar;
import br.usp.icmc.vicg.vp.view.tree.InteractionsTreePanel;

public class Controller {

	// Data
	private DataMatrix dataMatrix;

	// Models
	private InteractionsTree tree;

	// Views
	private ToolBar toolBar;
	private InteractionsTreePanel treePanel;

	// Dual Panel Slot
	private JPanel dualPanelSlot;

	private boolean useClass;

	public void initView(JPanel toolbarSlot, JPanel dualPanelSlot,
			JPanel treePanelSlot) {

		// Tool bar
		toolBar = new ToolBar();
		toolbarSlot.add(toolBar);
		toolbarSlot.validate();

		// Dual panel
		this.dualPanelSlot = dualPanelSlot;

		// Tree panel
		treePanel = new InteractionsTreePanel();
		treePanel.setBackground(Color.WHITE);
		treePanelSlot.add(treePanel);
		treePanelSlot.validate();
	}

	public void attachData(DataMatrix dataMatrix, boolean useClass) {

		this.dataMatrix = dataMatrix;
		this.useClass = useClass;

		tree = new InteractionsTree();
		treePanel.attach(tree);
		treePanel.revalidate();

		addVertexToTree(DualProjectionsFactory.getInstance(
				dataMatrix, useClass));
	}

	public void addVertexToTree(DualProjections dualProjections) {

		// Create new vertex
		DualProjectionsPanel dualPanel = createDualPanel(dualProjections);
		ContextVertex newVertex = new ContextVertex(tree.getGraph().getNumVertices() + 1,
				dualProjections, dualPanel);

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
		DataMatrix selectedData = dataMatrix.getDualSubset(
				vertex.getDualProjections());

		if (selectedData != null) {

			// Create and add new projections
			addVertexToTree((DualProjectionsFactory.getInstance(
					selectedData, useClass)));
		}
		// Clear selection
		vertex.getDualPanel().clearSelections();
	}
}











package br.usp.icmc.vicg.vp.controller;

import java.awt.Color;

import javax.swing.JPanel;

import projection.model.ProjectionModel;
import br.usp.icmc.vicg.vp.model.data.DataMatrix;
import br.usp.icmc.vicg.vp.model.ml.ResultFactory;
import br.usp.icmc.vicg.vp.model.projection.DualProjections;
import br.usp.icmc.vicg.vp.model.projection.DualProjectionsFactory;
import br.usp.icmc.vicg.vp.model.tree.AbstractVertex;
import br.usp.icmc.vicg.vp.model.tree.ContextVertex;
import br.usp.icmc.vicg.vp.model.tree.InteractionsTree;
import br.usp.icmc.vicg.vp.view.ml.ResultsPanel;
import br.usp.icmc.vicg.vp.view.projection.dual.DualProjectionsPanel;
import br.usp.icmc.vicg.vp.view.tools.ToolBar;
import br.usp.icmc.vicg.vp.view.tree.InteractionsTreePanel;

public class Controller {

	// Data
	private DataMatrix data;
	private DataMatrix tData;

	// Models
	private InteractionsTree tree;

	// View Slots
	private JPanel dualPanelSlot;

	// Views
	private ToolBar toolBar;
	private InteractionsTreePanel treePanel;
	private ResultsPanel resultsPanel;

	private String currentScalar;

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

		this.resultsPanel = new ResultsPanel();
		resultsPanelSlot.add(resultsPanel);
		resultsTitleSlot.add(resultsPanel.getTitle());
	}

	public void attachData(DataMatrix data, DataMatrix tData) {

		this.data = data;
		this.tData = tData;

		tree = new InteractionsTree();
		treePanel.attach(tree);
		treePanel.revalidate();

		addVertexToTree(data, tData, false);

		toolBar.attach(((ContextVertex) tree.getCurrentVertex()).getDualProjections().
				getItemsModel());

		initResults(data);
	}

	private void initResults(DataMatrix data) {

		try {
			// Results panel
			if (data.getClassIndex() != null) {

				resultsPanel.addRow(ResultFactory.getInstance(data,"None"));
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void addVertexToTree(DataMatrix data, DataMatrix tData, boolean classify) {

		int newVertexId = tree.getGraph().getNumVertices() + 1;

		if (classify && data.getClassIndex() != null) {

			try {

				resultsPanel.addRow(ResultFactory.getInstance(data,
						"Dual (" + newVertexId + ")"));
			} catch (Exception e) {

				e.printStackTrace();
			}
		}

		// Create projections
		DualProjections dualProjections = DualProjectionsFactory.getInstance(
				data, tData, currentScalar);

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

		// Get current vertex
		ContextVertex vertex = (ContextVertex) tree.getCurrentVertex();

		// Create new data matrix
		DataMatrix selectedData = DataMatrix.getSubset(
				data, vertex.getDualProjections().getItemsModel().getSelectedInstances());

		
		DataMatrix selectedTData = DataMatrix.getSubset(
				tData, vertex.getDualProjections().getDimensionsModel().getSelectedInstances());
		
		if (selectedData != null || selectedTData != null) {
			
			if (selectedData == null) {
				
				selectedData = DataMatrix.getSubset(
						data, vertex.getDualProjections().getItemsModel().getInstances());
			}
			if (selectedTData == null) {
				
				selectedTData = DataMatrix.getSubset(
						tData, vertex.getDualProjections().getDimensionsModel().getInstances());
			}
			
			selectedData.setClassIndex(data.getClassIndex());
			addVertexToTree(selectedData, selectedTData, true);
		}
		// Clear selection
		vertex.getDualPanel().clearSelections();
	}

	public void changeModelsScalar(String name) {

		this.currentScalar = name;

		for (AbstractVertex v : tree.getVertices()) {

			setModelScalar(((ContextVertex) v).getDualProjections().
					getDimensionsModel(), name);

			setModelScalar(((ContextVertex) v).getDualProjections().
					getItemsModel(), name);
		}
	}

	private void setModelScalar(ProjectionModel model, String name) {

		model.setSelectedScalar(model.getScalar(name));
		model.setChanged();
		model.notifyObservers();
	}
}











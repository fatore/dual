package br.usp.icmc.vicg.vp.view.tree;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;

import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import br.usp.icmc.vicg.vp.controller.ControllerHandle;
import br.usp.icmc.vicg.vp.model.tree.InteractionsTree;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxRectangle;

public class TreeComponent extends mxGraphComponent {

	private static final long serialVersionUID = -768829761119011630L;
	
	private static final float ZOOM_THRES = 1.25f;

	private mxHierarchicalLayout layout;

	public TreeComponent(InteractionsTree tree) {

		super(tree.getGraph());

		this.layout = new mxHierarchicalLayout(getGraph(),SwingConstants.WEST);

		this.getViewport().setOpaque(true);
		this.setEnabled(false);
		this.setBorder(null);

		this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		this.getGraphControl().addMouseListener(new ChangeContextListener());
	}

	public void fitToScreen() {

		mxRectangle graphBounds = getGraph().getGraphBounds();
		Rectangle visibleBounds = getViewport().getVisibleRect();

		float widthRatio = (float) (visibleBounds.getWidth() / 
				graphBounds.getWidth());

		float heigthRatio = (float) (visibleBounds.getHeight() / 
				graphBounds.getHeight());

		if (widthRatio > ZOOM_THRES && heigthRatio > ZOOM_THRES) {

			zoomActual();
		}
		else {

			float factor = Math.min(widthRatio, heigthRatio);
			zoom(factor);
		}
	}

	public void layoutGraph() {
		
		getGraph().getModel().beginUpdate();
		try {
			layout.execute(getGraph().getDefaultParent());
		} finally {

			getGraph().getModel().endUpdate();
		}
		fitToScreen();
	}

	class ChangeContextListener extends MouseAdapter {

		private void changeContextTo(Object cell) {
			
			if (cell != null) {
				
				if (cell instanceof mxCell) {

					if (((mxCell) cell).isVertex()) {

						Integer value = (Integer) getGraph().getModel().getValue(cell);
						ControllerHandle.getInstance().changeContextToVertex(value);
					}
				}
			}
		}
		
		@Override
		public void mouseReleased(java.awt.event.MouseEvent e) {
			
			mouseClicked(e);
		}
		
		@Override
		public void mouseClicked(java.awt.event.MouseEvent e) {

			if (e.getButton() == java.awt.event.MouseEvent.BUTTON1) {

				changeContextTo(getCellAt(e.getX(), e.getY()));
			}
		}
	}
}

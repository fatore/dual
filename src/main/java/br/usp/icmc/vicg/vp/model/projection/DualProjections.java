package br.usp.icmc.vicg.vp.model.projection;

import java.util.ArrayList;

import matrix.AbstractMatrix;
import projection.model.ProjectionModel;
import projection.model.ProjectionModel.InstanceType;
import visualizationbasics.color.ColorScaleFactory.ColorScaleType;

public class DualProjections  {

	private ProjectionModel itemsModel;
	private ProjectionModel dimensionsModel;
	
	public DualProjections(AbstractMatrix itemsProj, AbstractMatrix dimsProj,
			ArrayList<Float> itemsStress, ArrayList<Float> dimStress, String scalarName) {

		itemsModel = createProjectionModel(itemsProj, itemsStress, scalarName);
		dimensionsModel = createProjectionModel(dimsProj, dimStress, scalarName);
	}

	public ProjectionModel getItemsModel() {
		return itemsModel;
	}

	public ProjectionModel getDimensionsModel() {
		return dimensionsModel;
	}

	private ProjectionModel createProjectionModel(AbstractMatrix projection, 
			ArrayList<Float> stress, String scalarName) {

		ProjectionModel projModel = new ProjectionModel();
		projModel.addProjection(projection, InstanceType.CIRCLED_INSTANCE, 5);
		projModel.changeColorScaleType(ColorScaleType.ORANGE_TO_BLUESKY);
		
		projModel.addScalar("stress", stress);
		
		if (scalarName != null) {
			
			projModel.setSelectedScalar(projModel.getScalar(scalarName));
		}
		
		return projModel;
	}
}

package br.usp.icmc.vicg.vp.model.projection;

import java.util.ArrayList;

import matrix.AbstractMatrix;
import projection.model.ProjectionInstance;
import projection.model.ProjectionModel;
import projection.model.ProjectionModel.InstanceType;
import projection.model.Scalar;
import visualizationbasics.color.ColorScaleFactory.ColorScaleType;
import visualizationbasics.model.AbstractInstance;

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
		
		Scalar scalar = projModel.addScalar("stress");

		for (AbstractInstance pi : projModel.getInstances()) {

			((ProjectionInstance) pi).setScalarValue(scalar, stress.get(pi.getId()));
		}
		
		if (scalarName != null) {
			
			for (Scalar s : projModel.getScalars()) {

				if (s.getName().equals(scalarName)) {

					projModel.setSelectedScalar(s);
				}
			}
		}
		
		return projModel;
	}
}

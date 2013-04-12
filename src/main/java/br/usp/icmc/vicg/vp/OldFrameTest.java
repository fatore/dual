package br.usp.icmc.vicg.vp;

import matrix.AbstractMatrix;
import projection.model.ProjectionModel;
import projection.model.ProjectionModel.InstanceType;
import projection.technique.Projection;
import projection.technique.mds.ClassicalMDSProjection;
import projection.view.ProjectionFrameComp;
import br.usp.icmc.vicg.vp.model.data.DataLoader;
import br.usp.icmc.vicg.vp.model.data.DataSets;
import distance.DistanceMatrix;
import distance.dissimilarity.Euclidean;

public class OldFrameTest {

	public static void main2(String[] args) throws Exception {

		AbstractMatrix dataMatrix = DataLoader.loadData(DataSets.iris);

		// Create Distance Matrices
		DistanceMatrix elemDmat = new DistanceMatrix(dataMatrix, new Euclidean());

		// Project data
		Projection projTech = new ClassicalMDSProjection();
		AbstractMatrix itemsProj = projTech.project(elemDmat);

		ProjectionModel model = new ProjectionModel();
		model.addProjection(itemsProj, InstanceType.CIRCLED_INSTANCE);

		ProjectionFrameComp as = new ProjectionFrameComp();
		as.execute(new ProjectionFrameComp.Input(model));
	}
}

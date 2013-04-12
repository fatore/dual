package br.usp.icmc.vicg.vp.model.projection;

import java.io.IOException;

import matrix.AbstractMatrix;
import projection.technique.Projection;
import projection.technique.mds.ClassicalMDSProjection;
import br.usp.icmc.vicg.vp.model.data.DataMatrix;
import br.usp.icmc.vicg.vp.model.projection.distance.DimensionsDistanceMatrix;
import br.usp.icmc.vicg.vp.model.projection.distance.dissimilarity.MIC;
import distance.DistanceMatrix;
import distance.dissimilarity.Euclidean;

public class DualProjectionsFactory {

	public static DualProjections getInstance(DataMatrix dataMatrix) {

		// Create Transpose Matrix
		DataMatrix tMatrix = dataMatrix.getTranspose();
		
		// Create Distance Matrices
		DistanceMatrix elemDmat = new DistanceMatrix(dataMatrix, new Euclidean());
		DimensionsDistanceMatrix dimDmat = new DimensionsDistanceMatrix(tMatrix, new MIC());

		// Project data
		Projection projTech = new ClassicalMDSProjection();
		AbstractMatrix itemsProj = projTech.project(elemDmat);
		AbstractMatrix dimsProj = projTech.project(dimDmat);

		float[] elemStress = null;
		float[] dimStress = null;
		try {
			// Calculate Stress by Row
			elemStress = Stress.getStressByRow(elemDmat, itemsProj);
			dimStress = Stress.getStressByRow(dimDmat, dimsProj);
			
		}catch (IOException e) {
			
			System.err.println("ERROR: " + e.getMessage());
		}
		return new DualProjections(itemsProj, dimsProj, elemStress, dimStress);
	}
}

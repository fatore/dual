package br.usp.icmc.vicg.vp.model.projection;

import java.io.IOException;
import java.util.ArrayList;

import matrix.AbstractMatrix;
import projection.technique.Projection;
import projection.technique.mds.ClassicalMDSProjection;
import br.usp.icmc.vicg.vp.model.data.DataMatrix;
import br.usp.icmc.vicg.vp.model.projection.distance.DimensionsDistanceMatrix;
import br.usp.icmc.vicg.vp.model.projection.distance.dissimilarity.MIC;
import distance.DistanceMatrix;
import distance.dissimilarity.Euclidean;

public class DualProjectionsFactory {

	public static DualProjections getInstance(DataMatrix data,
			DataMatrix tData, String scalarName) {

		// Create Distance Matrices
		DistanceMatrix elemDmat = new DistanceMatrix(data, new Euclidean());
		DimensionsDistanceMatrix dimDmat = new DimensionsDistanceMatrix(tData, new MIC());

		// Project data
		Projection projTech = new ClassicalMDSProjection();
		AbstractMatrix itemsProj = projTech.project(elemDmat);
		AbstractMatrix dimsProj = projTech.project(dimDmat);

		ArrayList<Float> elemStress = null;
		ArrayList<Float> dimStress = null;
		try {
			// Calculate Stress by Row
			elemStress = Stress.getStressByRow(elemDmat, itemsProj);
			dimStress = Stress.getStressByRow(dimDmat, dimsProj);
			
		}catch (IOException e) {
			
			System.err.println("ERROR: " + e.getMessage());
		}
		return new DualProjections(itemsProj, dimsProj, elemStress, dimStress, scalarName);
	}
}

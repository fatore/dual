package br.usp.icmc.vicg.vp.model.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import matrix.AbstractVector;
import matrix.dense.DenseMatrix;
import matrix.dense.DenseVector;

public class DataMatrix extends DenseMatrix {

	private static final String separator = ",";

	private Integer labelIndex;
	private Integer classIndex;
	private String classLabel;
	private HashSet<Integer> ignoredCols;

	public DataMatrix() {

		super();

		this.labelIndex = null;
		this.classIndex = null;
		this.ignoredCols = new HashSet<Integer>();

	}

	public DataMatrix(DataSet dataset) {

		this.rows = new ArrayList<AbstractVector>();
		this.attributes = new ArrayList<String>();

		this.labelIndex = dataset.getLabelIndex();
		this.classIndex = dataset.getClassIndex();

		ArrayList<Integer> ignored = dataset.getIgnoreIndices();
		ignoredCols = new HashSet<>();
		if (ignored != null) {

			ignoredCols.addAll(ignored);
		}
	}

	public String getClassLabel() {
		return classLabel;
	}

	public Integer getClassIndex() {
		return classIndex;
	}

	public void setClassIndex(Integer classIndex) {
		this.classIndex = classIndex;
	}

	public void setClassLabel(String classLabel) {
		this.classLabel = classLabel;
	}

	public void setLabelIndex(Integer labelIndex) {
		this.labelIndex = labelIndex;
	}

	@Override
	public void load(String filename) throws IOException {}

	public void load(DataSet dataSet) throws IOException {

		String filename = dataSet.getFilename();

		BufferedReader in = null;

		try {

			in = new BufferedReader(new FileReader(filename));

			// Get first line
			String[] colNames = in.readLine().split(separator);

			// Set valid indices 
			ArrayList<Integer> validIndices = new ArrayList<>();
			for (int i = 0; i < colNames.length; i++) {

				if (!ignoredCols.contains(i)) {

					validIndices.add(i);
				}
			}

			// set number of columns 
			int nrdims = validIndices.size();

			// Read columns names
			for (Integer vi : validIndices) {

				this.attributes.add(colNames[vi].trim());
			}

			if (classIndex != null) {

				classLabel = colNames[classIndex].trim();
			}

			String line;

			// Read vectors
			while ((line = in.readLine()) != null && line.trim().length() > 0) {

				String[] values = line.split(separator);

				// Create vector
				float[] vector = new float[nrdims];

				// Fill vector
				int index = 0;
				for (Integer vi : validIndices) {

					vector[index++] = Float.parseFloat(values[vi]);
				}

				// Get id
				Integer id = this.getRowCount();

				// Get label
				String label; 
				if (labelIndex != null) {

					label = values[labelIndex];
				}
				else {

					label = id.toString();
				}

				// Get klass
				float klass;
				if (classIndex != null) {

					klass = Float.parseFloat(values[classIndex]);
				}
				else {

					klass = 0f;
				}

				// Add vector
				DenseVector newVector = new DenseVector(vector); 
				newVector.setId(id);
				newVector.setKlass(klass);

				this.addRow(newVector, label);
			}

		} catch (FileNotFoundException e) {
			throw new IOException("File " + filename + " does not exist!");
		} catch (IOException e) {
			throw new IOException(e.getMessage());
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ex) {
					Logger.getLogger(DataMatrix.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	}

	public DataMatrix getTranspose(boolean addClass) {

		float[][] points = this.toMatrix();
		int newRowLenght = points.length; 

		DataMatrix tMatrix = new DataMatrix();

		// Fill vector
		for (int i = 0; i < this.getDimensions(); i++) {

			float[] aux = new float[newRowLenght];

			for (int j = 0; j < points.length; j++) {

				aux[j] = points[j][i];
			}

			DenseVector newVector = new DenseVector(aux, tMatrix.getRowCount(), 0.0f);
			tMatrix.addRow(newVector, this.getAttributes().get(i));
		}

		if (addClass) {

			if (classIndex != null) {

				if (ignoredCols.contains(classIndex)) {

					float[] classCol = new float[newRowLenght];
					int i;
					for (i = 0; i < newRowLenght; i++) {

						classCol[i] = this.getRow(i).getKlass();        	
					}

					DenseVector newVector = new DenseVector(classCol, tMatrix.getRowCount(), 1.0f);
					tMatrix.addRow(newVector, classLabel);
				}
				else {

					tMatrix.getRow(classIndex).setKlass(1.0f);
				}
			}
		} else {

			if (classIndex != null) {

				if (!ignoredCols.contains(classIndex)) {

					tMatrix.getRow(classIndex).setKlass(1.0f);
				}
			}
		}
		return tMatrix;
	}

	public DataMatrix getItemsSubset(ArrayList<Integer> selRows, 
			ArrayList<Integer> selCols) {

		DataMatrix selectedData = new DataMatrix();

		// Check if class was selected
		boolean classSelected = false;
		for(Integer dimId : selCols) {

			if (dimId.equals(this.classIndex)) {

				classSelected = true;
				break;
			}
		}

		// Okay, now check if it was added or was already there
		boolean classAdded = classSelected && this.ignoredCols.contains(this.classIndex);
		
		int valuesSize = selCols.size();
		if (classAdded) valuesSize--;

		for(Integer rowId : selRows) {

			DenseVector row = (DenseVector) this.getRow(rowId);
			float[] values = new float[valuesSize];

			int pos = 0;
			for(Integer dimId : selCols) {

				// if class was added ignored it
				if (classAdded) {

					if (!dimId.equals(this.classIndex)) {

						values[pos++] = row.getValue(dimId);
					}
				}
				// else add anyway
				else {

					values[pos++] = row.getValue(dimId);
				}
			}

			DenseVector newRow = new DenseVector(values, row.getId(), row.getKlass());
			selectedData.addRow(newRow, this.getLabel(row.getId()));
		}
		for (Integer colId : selCols) {

			if (classAdded) {

				// if class was added ignored it
				if (!colId.equals(this.classIndex)) {

					selectedData.getAttributes().add(this.getAttributes().get(colId));
				}
			}
			// else add anyway
			else {

				selectedData.getAttributes().add(this.getAttributes().get(colId));
			}

		}

		return selectedData;
	}

	public DataMatrix getDimsSubset(ArrayList<Integer> selRows, 
			ArrayList<Integer> selCols) {

		DataMatrix selectedData = new DataMatrix();

		for(Integer rowId : selRows) {

			DenseVector row = (DenseVector) this.getRow(rowId);
			float[] values = new float[selCols.size()];

			int pos = 0;
			for(Integer dimId : selCols) {

				values[pos++] = row.getValue(dimId);
			}

			DenseVector newRow = new DenseVector(values, row.getId(), row.getKlass());
			selectedData.addRow(newRow, this.getLabel(row.getId()));
		}

		return selectedData;
	}
}








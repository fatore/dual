package br.usp.icmc.vicg.vp.model.data;

import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import matrix.AbstractVector;
import matrix.dense.DenseMatrix;
import matrix.dense.DenseVector;

public class DataMatrix extends DenseMatrix {

	private static final String separator = ",";

	private String filename;

	private Integer labelIndex;
	private Integer classIndex;
	private String classLabel;
	private Set<Integer> ignoreIndices;
	private boolean showClass;

	public DataMatrix() {

		super();
	}

	public DataMatrix(DataSet dataset) {

		this.rows = new ArrayList<AbstractVector>();
		this.attributes = new ArrayList<String>();

		this.filename = dataset.getFilename();
		this.labelIndex = dataset.getLabelIndex();
		this.classIndex = dataset.getClassIndex();

		ArrayList<Integer> ignored = dataset.getIgnoreIndices();
		this.ignoreIndices = new HashSet<Integer>();
		if (ignored != null) {

			this.ignoreIndices.addAll(ignored);
		}
		this.showClass = false;
	}

	public String getClassLabel() {
		return classLabel;
	}

	public void setClassIndex(Integer classIndex) {
		this.classIndex = classIndex;
	}

	public Set<Integer> getIgnoreIndices() {
		return ignoreIndices;
	}

	public boolean isShowClass() {
		return showClass;
	}

	public void setShowClass(boolean showClass) {
		this.showClass = showClass;
	}

	public void setClassLabel(String classLabel) {
		this.classLabel = classLabel;
	}

	public void setLabelIndex(Integer labelIndex) {
		this.labelIndex = labelIndex;
	}

	public void setIgnoreIndices(Set<Integer> ignoreIndices) {
		this.ignoreIndices = ignoreIndices;
	}

	@Override
	public void load(String filename) throws IOException {

		load();
	}

	private boolean isIndexValid(Integer index) {

		if (ignoreIndices.contains(index)) {

			return false;
		}
		return true;
	}

	public void load() throws IOException {

		BufferedReader in = null;

		try {

			in = new BufferedReader(new FileReader(filename));

			// Get first line
			String[] colNames = in.readLine().split(separator);

			// Set valid indices 
			ArrayList<Integer> validIndices = new ArrayList<>();
			for (int i = 0; i < colNames.length; i++) {

				if (isIndexValid(i)) {

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

	public DataMatrix getSubset(ArrayList<Integer> selItems, 
			ArrayList<Integer> selDims) {

		DataMatrix selectedData = new DataMatrix();
		
		boolean containsClass = false;
		for (Integer dimId : selDims) {
			
			if(dimId.equals(this.classIndex)) {
				
				containsClass = true;
			}
		}

		// If class is set but is ignored, then ignore
		boolean ignoredClass = this.classIndex != null && 
				this.ignoreIndices.contains(this.classIndex);

		int newSize = selDims.size();
		if (ignoredClass) newSize--;

		// For each selected row
		for (Integer itemId : selItems) {

			DenseVector row = (DenseVector) this.getRow(itemId);

			float[] values = new float[newSize];
			int i = 0;
			for (Integer dimId : selDims) {

				// if its not the class
				if (!dimId.equals(this.classIndex)) {

					values[i] = row.getValue(dimId);
					i++;
				} 
				// is the class
				else {

					// if class shouldn't be ignored
					if (!ignoredClass) {

						values[i] = row.getValue(dimId);
						i++;
					}
				}
			}

			DenseVector vector = new DenseVector(values, row.getId(), row.getKlass());
			selectedData.addRow(vector, this.getLabel(vector.getId()));
		}
		if (containsClass) {
			
			selectedData.setClassIndex(this.classIndex);
		}
		else {
			
			selectedData.setClassIndex(null);
		}
		for (Integer dimId : selDims) {

			if (ignoredClass) {

				// add only if its not class
				if (!dimId.equals(this.classIndex)) {

					selectedData.getAttributes().add(this.getAttributes().get(dimId));
				}
			}
		}
		return selectedData;
	}

	public DataMatrix getTranspose() {

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

		// Use class as a new row
		if (showClass && classIndex != null) {

			if (ignoreIndices.contains(classIndex)) {

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

		return tMatrix;
	}

	public Integer getClassIndex() {
		return classIndex;
	}
}








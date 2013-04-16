package br.usp.icmc.vicg.vp.model.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import matrix.AbstractVector;
import matrix.dense.DenseMatrix;
import matrix.dense.DenseVector;
import visualizationbasics.model.AbstractInstance;
import br.usp.icmc.vicg.vp.model.projection.DualProjections;

public class DataMatrix extends DenseMatrix {

	private static final String separator = ",";

	private String filename;

	private Integer labelIndex;
	private Integer classIndex;
	private String classLabel;
	private Set<Integer> ignoreIndices;
	private boolean useClass;

	public DataMatrix(boolean useClass) {

		super();
		
		this.useClass = useClass;
	}

	public DataMatrix(DataSet dataset) {

		this.rows = new ArrayList<AbstractVector>();
		this.attributes = new ArrayList<String>();

		this.filename = dataset.getFilename();
		this.labelIndex = dataset.getLabelIndex();
		this.classIndex = dataset.getClassIndex();
		this.useClass = dataset.isUseClass();

		Integer[] ignored = dataset.getIgnoreIndices();
		this.ignoreIndices = new HashSet<Integer>();
		if (ignored != null) {

			this.ignoreIndices.addAll(Arrays.asList(ignored));
		}
	}

	public String getClassLabel() {
		return classLabel;
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

	public DataMatrix getDualSubset(DualProjections dualProjections) {

		ArrayList<AbstractInstance> itemsSelected = dualProjections.
				getItemsModel().getSelectedInstances();

		ArrayList<AbstractInstance> dimsSelected = dualProjections.
				getDimensionsModel().getSelectedInstances();

		if (itemsSelected.isEmpty() && dimsSelected.isEmpty()) {

			return null;
		} 
		else if (itemsSelected.isEmpty()) {

			itemsSelected = dualProjections.getItemsModel().getInstances();
		}
		else if (dimsSelected.isEmpty()) {

			dimsSelected = dualProjections.getDimensionsModel().getInstances();
		}

		DataMatrix selectedData = new DataMatrix(useClass);

		// For each selected row
		for (AbstractInstance item : itemsSelected) {

			DenseVector v = (DenseVector) this.getRow(item.getId());
			ArrayList<Float> aux = new ArrayList<>();
			for (AbstractInstance dim : dimsSelected) {
				
				int desiredColumn = dim.getId();
				// Check if is not the class column
				if (desiredColumn < v.size()) {
					
					aux.add(v.getValue(dim.getId()));
				}
			}
			float[] values = new float[aux.size()];
			for (int i = 0; i < values.length; i++) {
				
				values[i] = aux.get(i);
			}
			if (values.length > 0 ) {
				
				DenseVector newVector = new DenseVector(values,v.getId(),v.getKlass());
				selectedData.addRow(newVector, this.getLabel(v.getId()));
			}
		}
		ArrayList<String> selAttributes = new ArrayList<>();
		for (AbstractInstance dim : dimsSelected) {
			
			int desiredColumn = dim.getId();
			// Check if is not the class column
			if (desiredColumn < dimensions) {
				
				selAttributes.add(this.getAttributes().get(dim.getId()));
			}
		}
		selectedData.setAttributes(selAttributes);

		if (selectedData.rows.size() > 0) {
			
			return selectedData;
		}
		else {
			
			return null;
		}
	}

	public DataMatrix getTranspose() {

		float[][] points = this.toMatrix();

		int newRowLenght = points.length; 

		DataMatrix tMatrix = new DataMatrix(useClass);

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
		if (useClass) {

			float[] classCol = new float[newRowLenght];

			int i;
			for (i = 0; i < newRowLenght; i++) {

				classCol[i] = this.getRow(i).getKlass();        	
			}

			DenseVector newVector = new DenseVector(classCol, tMatrix.getRowCount(), 1.0f);
			tMatrix.addRow(newVector, "Class");
		}
		return tMatrix;
	}
}








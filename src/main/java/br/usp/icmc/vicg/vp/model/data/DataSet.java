package br.usp.icmc.vicg.vp.model.data;

import java.util.ArrayList;
import java.util.Arrays;

public class DataSet {

	private String filename;
	private Integer labelIndex; 
	private Integer classIndex;
	private ArrayList<Integer> ignoredIndices;
	
	public DataSet(String filename, Integer labelIndex, Integer classIndex,
			Integer[] ignored) {
		
		this.filename = filename;
		this.labelIndex = labelIndex;
		this.classIndex = classIndex;
		this.ignoredIndices = new ArrayList<Integer>(Arrays.asList(ignored));
	}
	
	public String getFilename() {
		return filename;
	}

	public Integer getLabelIndex() {
		return labelIndex;
	}

	public Integer getClassIndex() {
		return classIndex;
	}

	public ArrayList<Integer> getIgnoreIndices() {
		
		return ignoredIndices;
	}
}

package br.usp.icmc.vicg.vp.model.ml;


public class Result {

	private String technique;
	private Double accuracy;
	private Float classTime;
	
	public Result(String technique, Double accuracy, Long elapsed) {
		
		this.technique = technique;
		this.accuracy = accuracy;
		
		this.classTime = elapsed / 1000F;
		System.out.println();
	}

	public String getTechnique() {
		return technique;
	}

	public Double getAccuracy() {
		return accuracy;
	}

	public Float getClassTime() {
		return classTime;
	}
}

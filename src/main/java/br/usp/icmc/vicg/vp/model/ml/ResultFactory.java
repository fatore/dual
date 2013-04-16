package br.usp.icmc.vicg.vp.model.ml;

import java.util.Random;

import matrix.AbstractVector;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;
import br.usp.icmc.vicg.vp.model.data.DataMatrix;

public class ResultFactory {

	public static Result getInstance(DataMatrix dataMatrix, String redTech) 
			throws Exception {

		// Convert data
		Instances data = loadData(dataMatrix);

		Random rand = new Random(1);   // create seeded number generator
		Instances randData = new Instances(data);   // create copy of original data
		randData.randomize(rand);

		Instances train = randData.trainCV(2, 0);
		Instances test = randData.testCV(2, 0);

		// Reduce dimensionality using selected technique
		if (redTech != null) {

		}

		// Classify data
		Classifier classifier = new NaiveBayes();
		long start = System.currentTimeMillis();  
		classifier.buildClassifier(train);
		long elapsedTime = System.currentTimeMillis() - start;

		// Evaluate classification
		Evaluation ev = new Evaluation(data);
		ev.evaluateModel(classifier, test);

		return new Result(redTech.toString(), ev.pctCorrect(), elapsedTime);
	}

	private static Instances loadData(DataMatrix dataMatrix) throws Exception {

		Instances data;

		FastVector atts = new FastVector();
		for (String at : dataMatrix.getAttributes()) {

			atts.addElement(new Attribute(at));
		}
		atts.addElement(new Attribute("Class"));

		// 2. create Instances object
		data = new Instances("WekaData", atts, 0);

		// 3. fill with data
		for (AbstractVector row : dataMatrix.getRows()) {

			double[] vals = new double[data.numAttributes()];
			int i;
			for (i = 0; i < vals.length - 1; i++) {

				vals[i] = row.getValue(i);
			}
			vals[i] = row.getKlass();
			data.add(new Instance(1.0, vals));
		}

		data.setClassIndex(data.numAttributes() - 1);

		NumericToNominal filter = new NumericToNominal();
		filter.setOptions(new String[]{"-R", ((Integer) data.numAttributes()).toString()});
		filter.setInputFormat(data);

		Instances filteredData = Filter.useFilter(data, filter);

		return filteredData;
	}

	public static void printConfusionMatrix(Evaluation ev) {

		double[][] cmMatrix = ev.confusionMatrix();

		for(int row_i=0; row_i<cmMatrix.length; row_i++){

			for(int col_i=0; col_i<cmMatrix.length; col_i++){

				System.out.print(cmMatrix[row_i][col_i]);
				System.out.print("|");
			}
			System.out.println();
		}
	}
}







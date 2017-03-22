package wekaProject;

import java.util.Random;

import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class InstancesTester {
	public String [] testResults (Instances test, 
			StringToWordVector filter, InfoGainAttributeEval attributeSelection, AbstractClassifier classifier) throws Exception{
		
			Instances data = test;
			data.setClassIndex(data.numAttributes() -1);

			String options = "Data filtered using StringToWordVector with options --->\n";
			String attributeSelectionInfo = "Attributes pruned using InfoGainAttributeEval with options --->\n";
			for (String option: attributeSelection.getOptions() ) {
				attributeSelectionInfo += option + "\n";
				
			}
			String classifierInfo = "Data classified using " + classifier.toString() + "\n";
			for (String option : filter.getOptions() ) {
				options += option + "\n";
			}
			System.out.println("Filtering...");
			filter.setInputFormat(data);
			
			Instances train = Filter.useFilter(data, filter);
	        
			System.out.println("Building classifier...");
			classifier.buildClassifier(train);
			Evaluation eval = new Evaluation(train);
			eval.crossValidateModel(classifier, train, 10, new Random(1));
			String classAttributes = train.classAttribute().toString(); 
			String[] result = {classAttributes, options, attributeSelectionInfo, classifierInfo, eval.toSummaryString("\n", true),
					//eval.fMeasure(1) + " " + eval.precision(1) + " " + eval.recall(1),
					eval.toMatrixString()};
			return result;
	}
}



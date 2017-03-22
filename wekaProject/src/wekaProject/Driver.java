package wekaProject;

import java.io.File;
import java.util.Enumeration;
import java.util.Random;
import java.util.Vector;

import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMO;
import weka.classifiers.meta.AttributeSelectedClassifier;
import weka.classifiers.trees.J48;
import weka.core.Capabilities;
import weka.core.Instances;
import weka.core.Option;

public class Driver {
	
	public static void main(String[] args) throws Exception{
		
		File master = new File(args[0]);
		File[] masterList = master.listFiles((dir, name) -> !name.equals(".DS_Store"));
		InstancesTester test = new InstancesTester();
		TextDirectoryToArff data = new TextDirectoryToArff();
		StringToWordVector filter = new StringToWordVector();
		InfoGainAttributeEval attributeSelection = new InfoGainAttributeEval();
		
		AttributeSelectedClassifier aSC = new AttributeSelectedClassifier();
		String [] o = {"-E", "weka.attributeSelection.InfoGainAttributeEval", "W", "weka.classifiers.functions.SMO"};
		//aSC.setOptions(o);
		NaiveBayes nB = new NaiveBayes();
		J48 j48 = new J48();
		SMO smo = new SMO();
		AbstractClassifier ClassifiersToRun [] = {smo, nB, aSC};
		
		for (File dir : masterList) {	
			for (AbstractClassifier c : ClassifiersToRun) {
				String [] out = test.testResults(data.convert(dir), filter, attributeSelection, c);
				for (String result : out) {
					System.out.println(result);
				}
			}
		}	
	}
}
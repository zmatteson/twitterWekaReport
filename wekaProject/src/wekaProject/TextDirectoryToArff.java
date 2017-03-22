package wekaProject;

import java.io.*;

import weka.core.Instances;
import weka.core.converters.*;

public class TextDirectoryToArff {

	public Instances convert(File dir) throws Exception{
		
		TextDirectoryLoader textToArff = new TextDirectoryLoader();
		textToArff.setDirectory(dir);
		textToArff.setSource(dir);
				
		return new Instances (textToArff.getDataSet());
		
	}
}

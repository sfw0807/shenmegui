package com.dc.esb.servicegov.refactoring.resource.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AbstractSOPMakeFileGenerater {
	
	public static final String OJECT_PRIFIX = "O";
	public static final String FIELD_PRIFIX_STRING = "F";
	public static final String REQUEST = "request";
	public static final String RESPONSE = "response";
	public static final String FIELD_IDENTIFY = "FLD";
	public static final String GRD_IDENTIFY = "GRD";
	public static final String INTERFACE_TYPE = "type";
	public static final String INTERFACE_LENGTH = "length";
	public static final String INTERFACE_SCALE = "scale";
	public static final String MAPFILE_FORM_LABEL = "WINDOW";
	public static final String MAPFILE_FIELD_LABEL= "PRINTER";
	

	public void generate(String resource) throws Exception {
		
	}
	
	protected void writeContentToFile(String content, File targetFile) throws IOException{
		if(null != targetFile){
			FileWriter fWriter = new FileWriter(targetFile);
			fWriter.append(content);
			fWriter.flush();
			fWriter.close();
		}
	}
}

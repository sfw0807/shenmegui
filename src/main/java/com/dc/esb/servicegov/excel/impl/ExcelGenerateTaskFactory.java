package com.dc.esb.servicegov.excel.impl;

import static com.dc.esb.servicegov.excel.support.Constants.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dc.esb.servicegov.exception.TypeException;

public class ExcelGenerateTaskFactory  {
	
	private static final Log log = LogFactory.getLog(ExcelGenerateTaskFactory.class);
	
	private static ExcelGenerateTaskFactory instance = new ExcelGenerateTaskFactory();
	
	private ExcelGenerateTaskFactory(){
		
	}
	
	public static ExcelGenerateTaskFactory getInstance(){
		return instance;
	}
	
	public ExcelGenerateTask factory(String type) throws TypeException {
		if(MAPPING_FILE_TYPE.equals(type)){
			return new MappingGeneraterTask();
		}else if(SERVICE_FILE_TYPE.equals(type)){
			return new ServiceExcelGeneraterTask();
		}else if(INTERFACE_FILE_TYPE.equals(type)){
			return new InterfaceGeneraterTask();
		}else{
			String errorMsg = "暂时不支持类型为["+type+"]的文档导出！";
			log.error(errorMsg);
			throw new TypeException(errorMsg);
		}
	}

}

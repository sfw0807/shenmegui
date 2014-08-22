package com.dc.esb.servicegov.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class MappingExcelGenerator {

	private static final Log log = LogFactory.getLog(MappingExcelGenerator.class);
	@Autowired
	private ServiceManagerImpl serviceManager;
	@Autowired
	private MetadataManagerImpl metadataManager;
}

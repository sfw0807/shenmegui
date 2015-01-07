package com.dc.esb.servicegov.excel.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.dc.esb.servicegov.entity.SDANode;
import com.dc.esb.servicegov.service.impl.InterfaceManager;
import com.dc.esb.servicegov.service.impl.MetadataManagerImpl;
import com.dc.esb.servicegov.service.impl.ServiceManagerImpl;
import com.dc.esb.servicegov.service.impl.SystemManager;
import com.dc.esb.servicegov.vo.RelationVo;

public interface ExcelGenerateTask extends Runnable {

	public void initManager(ServiceManagerImpl serviceManager, SystemManager systemManager,
			InterfaceManager interfaceManager, MetadataManagerImpl metadataManager);
	
	public void setStyle1(CellStyle s1,CellStyle s2,CellStyle s3,CellStyle s4);
	
	public void setStyle2(CellStyle s1,CellStyle s2,CellStyle s3,CellStyle s4);
	
	public void setStyle3(CellStyle s1,CellStyle s2,CellStyle s3,CellStyle s4);
	
	public void init(RelationVo r,Workbook wb,Sheet sheet,CountDownLatch countDown,List<Map<String, SDANode>> lstStructName);
}

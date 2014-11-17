package com.dc.esb.servicegov.refactoring.excel.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.dc.esb.servicegov.refactoring.entity.System;
import com.dc.esb.servicegov.refactoring.excel.support.MappingExcelUtils;
import com.dc.esb.servicegov.exception.DataException;
import com.dc.esb.servicegov.refactoring.entity.IDA;
import com.dc.esb.servicegov.refactoring.entity.Interface;
import com.dc.esb.servicegov.refactoring.entity.Operation;
import com.dc.esb.servicegov.refactoring.entity.SDA;
import com.dc.esb.servicegov.refactoring.entity.Service;
import com.dc.esb.servicegov.refactoring.entity.SvcAsmRelateView;
import com.dc.esb.servicegov.refactoring.service.impl.InterfaceManagerImpl;
import com.dc.esb.servicegov.refactoring.service.impl.ServiceManagerImpl;
import com.dc.esb.servicegov.refactoring.service.impl.SystemManagerImpl;
import com.dc.esb.servicegov.refactoring.vo.SDA4I;
import com.dc.esb.servicegov.refactoring.vo.SDAVO;
import com.dc.esb.servicegov.vo.MappingExcelIndexVo;
import com.dc.esb.servicegov.vo.MetadataViewBean;
import com.dc.esb.servicegov.vo.RelationVo;

public class MappingGeneraterTask implements  ExcelGenerateTask{
	private static final Log log = LogFactory.getLog(MappingGeneraterTask.class);
	
	private ServiceManagerImpl serviceManager;
	private SystemManagerImpl systemManager;
	private InterfaceManagerImpl interfaceManager;
	
	private RelationVo r;
	private CountDownLatch countDown;
	//记录"输出"所在的行索引
	private int outputIndex=0;
	
	//记录"输入"所在的行索引
	private int inputIndex=0;
	
	//记录最后一行的行索引
	private int lastIndex=0;
	private List<Map<String, SDA>> lstStructName = new ArrayList<Map<String, SDA>>();
	// 全局的行
	int i = 6;
	// 当前操作SHEET 
	private Sheet sheet2;
	// 全局WORKBOOK
	private Workbook wb;
	// KEY 为XPATH 映射左右节点
	private Map<String, IDA> sda4Imap = new HashMap<String, IDA>();
	
	Hyperlink href = new HSSFHyperlink(Hyperlink.LINK_DOCUMENT);
	
	private CellStyle arrayCellStyle;
	private CellStyle cellStyleLightYellow;
	private CellStyle cellStyleLightGreen;
	private CellStyle cellStyleMaroon;
	
	private CellStyle titleCellStyle;
	private CellStyle bodyCellStyle;
	
	private CellStyle headCellStyle;
	private CellStyle cellStyle;
	private CellStyle cellStyle4r;
	private CellStyle headCenterCellStyle;
	private CellStyle greyCellStyle;
	private CellStyle arrayCellStyle4r;
	
	public MappingGeneraterTask() {
		
	}
	
	public MappingGeneraterTask(RelationVo r,Workbook wb,Sheet sheet,CountDownLatch countDown){
		this.r = r;
		this.wb = wb;
		this.countDown = countDown;
		this.sheet2 = sheet;
	}
	
	public void setStyle1(CellStyle s1,CellStyle s2,CellStyle s3,CellStyle s4){
		this.arrayCellStyle = s1;
		this.cellStyleLightYellow = s2;
		this.cellStyleLightGreen = s3;
		this.cellStyleMaroon = s4;
	}
	
	public void setStyle2(CellStyle s1,CellStyle s2,CellStyle s3,CellStyle s4){
		this.titleCellStyle = s1;
		this.bodyCellStyle = s2;
		this.greyCellStyle = s3;
		this.arrayCellStyle4r = s4;
	}
	
	public void setStyle3(CellStyle s1,CellStyle s2,CellStyle s3,CellStyle s4){
		this.headCellStyle = s1;
		this.cellStyle4r = s2;
		this.cellStyle = s3;
		this.headCenterCellStyle = s4;
	}
	
	public void run() {
		try {
			long start = java.lang.System.currentTimeMillis();
			i = 6;
			reset();
			List<Service> lstService = serviceManager.getServicesById(r
					.getServiceId()); // InstInfoDtlQry S120030021
			SDAVO sda = null;
			if (lstService.size() == 0) {
				log.info("接口 " + r.getInterfaceId() + " 的服务ID为空");
				return;
			}
			sda = serviceManager.getSDAofRelation(r);
			SDA4I sda4I = serviceManager.getSDA4IofInterfaceId(r
					.getInterfaceId());
			String interfaceId = r.getInterfaceId();
			// 有问题的服务 待处理
			if (interfaceId.equals("FB52")) {
				countDown.countDown();
				return;
			}

			MappingExcelIndexVo mVo = createMappingExcelIndexVo(r);
			printIndexInfo(r, mVo);

			// sheet2 = synCreateSheet(wb, interfaceId);

			printHeader(r, sheet2, mVo);
			renderSDA4I(sda4I);
			
			renderSDA(sda);
			try{
				setSheet2TextFont(sheet2);
			}catch(Exception e){
				log.error(e,e);
			}
			
			MappingExcelUtils.addLstStructName(lstStructName);
			
			long end = java.lang.System.currentTimeMillis();
			log.error("生成单个sheet:[" + sheet2.getSheetName() + "]耗时:"
					+ (end - start) + "ms");
			countDown.countDown();
		} catch (DataException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 设置sheet2(当前操作SHEET)文本字体和颜色 合并单元格
	 */
	private void setSheet2TextFont(Sheet sheet){
		
		Iterator<Row> iterator=sheet.iterator();
		//处理其他行
		Row row = null;
		Hyperlink href = new HSSFHyperlink(Hyperlink.LINK_DOCUMENT);
		href.setAddress("#INDEX!A1");
		MappingExcelUtils.fillCell(0, 10, "返回", sheet, cellStyle);
		sheet.getRow(0).getCell(10).setHyperlink(href);
		while (iterator.hasNext()) {
			row = iterator.next();
			if (row.getRowNum() == i) {
				break ;
			}
			// 填充左侧空格
			for (int som=0;som<5;som++) {
				if (row.getCell(som) == null) {
					MappingExcelUtils.fillCell(row, som, "", cellStyle);
				}
			}
			row.setHeightInPoints((short) 20);
		}
		for(int i=0;i<=4;i++){
		    sheet.getRow(4).getCell(i).setCellStyle(cellStyleLightYellow);
		    sheet.getRow(5).getCell(i).setCellStyle(cellStyleLightYellow);
		}
		for(int i=6;i<=9;i++){
			sheet.getRow(4).getCell(i).setCellStyle(cellStyleLightYellow);
			sheet.getRow(5).getCell(i).setCellStyle(cellStyleLightYellow);
		}
		lastIndex = i-1;
		for(int i=0;i<=lastIndex;i++){
			if(i!=inputIndex&&i!=outputIndex){
				Row rowToRender = sheet.getRow(i);
				Cell cellToRender =rowToRender.getCell(5);
				cellToRender.setCellStyle(cellStyleMaroon);
			}
		}
		
		for(int i=0;i<10;i++){
			sheet.getRow(inputIndex).getCell(i).setCellStyle(cellStyleLightGreen);
			sheet.getRow(outputIndex).getCell(i).setCellStyle(cellStyleLightGreen);
		}
		
		//合并单元格
		sheet.addMergedRegion(new CellRangeAddress(0,0,1,4));
		sheet.addMergedRegion(new CellRangeAddress(0,0,7,9));
		sheet.addMergedRegion(new CellRangeAddress(1,1,1,4));
		sheet.addMergedRegion(new CellRangeAddress(1,1,7,9));
		sheet.addMergedRegion(new CellRangeAddress(2,2,1,4));
		sheet.addMergedRegion(new CellRangeAddress(2,2,7,9));
		sheet.addMergedRegion(new CellRangeAddress(3,3,1,4));
		sheet.addMergedRegion(new CellRangeAddress(3,3,7,9));
		sheet.addMergedRegion(new CellRangeAddress(4,4,0,4));
		sheet.addMergedRegion(new CellRangeAddress(4,4,6,9));
		sheet.addMergedRegion(new CellRangeAddress(0,5,5,5));
		sheet.addMergedRegion(new CellRangeAddress(7,outputIndex-1,5,5));
		if (outputIndex+1 <= i-1)
			sheet.addMergedRegion(new CellRangeAddress(outputIndex+1,i-1,5,5));
		
		// 设置列宽度
		sheet.setColumnWidth(0, 15*256);
		sheet.setColumnWidth(1, 18*256);
		sheet.setColumnWidth(2, 16*256);
		sheet.setColumnWidth(3, 14*256);
		sheet.setColumnWidth(4, 17*256);
		sheet.setColumnWidth(5, 2*256);
		sheet.setColumnWidth(6, 14*256);
		sheet.setColumnWidth(7, 16*256);
		sheet.setColumnWidth(8, 17*256);
		sheet.setColumnWidth(9, 18*256);
	}
	

	/**
	 * reset 全局变量
	 */
	private void reset() {
		this.inputIndex = 0;
		this.outputIndex = 0;
		this.lstStructName = new ArrayList<Map<String, SDA>>();
		this.lastIndex = 0;
	}
	
	/**
	 * 生成 MappingExcelIndexVo
	 * @param ServiceInvokeRelation r
	 * @return
	 */
	private MappingExcelIndexVo createMappingExcelIndexVo(
			RelationVo r) {
		MappingExcelIndexVo mVo = new MappingExcelIndexVo();
		String interfaceId = r.getInterfaceId();
		String operationId = r.getOperationId();
		String serviceId = r.getServiceId();
		String consumerSysAB = r.getConsumerSystemAb();
		String providerId = r.getProviderSystemId();
		System providerSys = systemManager.getSystemById(providerId);
		String providerSysAB = providerSys.getSystemAb();
		List<Interface> interfaces = interfaceManager.getInterfacesById(interfaceId);
		List<Service> services = serviceManager.getServicesById(serviceId);
		List<Operation> operations = serviceManager.getOperationById(operationId);
		Interface interfaceInfo = new Interface();
		// Relation表中有 接口表中没有interfaces.get(0)会有NullPointer
		if (interfaces.size()>0)
			interfaceInfo = interfaces.get(0);
		Service service = services.get(0);
		Service operation = services.get(0);
		mVo.setInterfaceId(interfaceId);
		mVo.setProviderSysId(providerId);
		mVo.setInterfaceName(interfaceInfo.getInterfaceName());
		mVo.setServiceId(serviceId);
		mVo.setServiceName(service.getServiceName());
		mVo.setServiceRemark(service.getServiceRemark());
		mVo.setOperationRemark(operations.get(0).getRemark());
		mVo.setOperationId(operationId);
		mVo.setOperationName(operation.getServiceName());
		mVo.setConsumerSysAb(consumerSysAB);
		mVo.setProviderSysAb(providerSysAB);
		// 根据INTERFACE_ID找到SVC_ASM_RELATE_VIEW中的记录对应消息类型
		SvcAsmRelateView view = serviceManager.getRelationViewByInterfaceId(interfaceId);
		mVo.setType(view.getDirection());
		mVo.setMsgType(view.getProvideMsgType());
		return mVo;
	}
	
	/**
	 * CREATE & PAINT INDEX
	 * @param ServiceInvokeRelation r
	 * @param MappingExcelIndexVo mVo
	 */
	private  void printIndexInfo(RelationVo r, MappingExcelIndexVo mVo) {
		synchronized(getClass()){
			Sheet indexSheet = wb.getSheet("INDEX");
			if (null == indexSheet) {
				indexSheet = wb.createSheet("INDEX");
				printSheetIndexLabel(indexSheet);
			}
			printSheetIndexData(mVo, indexSheet);
		}
	}
	/**
	 * 设置INDEX页的标题行
	 * @param indexSheet
	 */
	private void printSheetIndexLabel(Sheet indexSheet) {
		// 处理Index页
		Row titleRow = indexSheet.createRow(0);
		titleRow.setHeightInPoints((short) 22.5);
		MappingExcelUtils.fillCell(titleRow, 0, "交易代码", titleCellStyle);
		MappingExcelUtils.fillCell(titleRow, 1, "交易名称", titleCellStyle);
		MappingExcelUtils.fillCell(titleRow, 2, "服务名称", titleCellStyle);
		MappingExcelUtils.fillCell(titleRow, 3, "服务操作ID", titleCellStyle);
		MappingExcelUtils.fillCell(titleRow, 4, "服务操作名称", titleCellStyle);
		MappingExcelUtils.fillCell(titleRow, 5, "调用方", titleCellStyle);
		MappingExcelUtils.fillCell(titleRow, 6, "服务操作提供系统", titleCellStyle);
		MappingExcelUtils.fillCell(titleRow, 7, "接口方向", titleCellStyle);
		MappingExcelUtils.fillCell(titleRow, 8, "接口提供系统ID", titleCellStyle);
		MappingExcelUtils.fillCell(titleRow, 9, "报文类型", titleCellStyle);
		indexSheet.setColumnWidth(0, 23*256);
		indexSheet.setColumnWidth(1, 23*256);
		indexSheet.setColumnWidth(2, 23*256);
		indexSheet.setColumnWidth(3, 23*256);
		indexSheet.setColumnWidth(4, 23*256);
		indexSheet.setColumnWidth(6, 22*256);
		indexSheet.setColumnWidth(9, 22*256);
	}
	
	/**
	 * INDEX页追加一条数据
	 * @param mVo
	 * @param indexSheet
	 */
	private void printSheetIndexData(MappingExcelIndexVo mVo, Sheet indexSheet) {
		int lastRow = indexSheet.getLastRowNum();
		Row rowAdded = indexSheet.createRow(lastRow + 1);
		rowAdded.setHeightInPoints((short) 22.5);
		MappingExcelUtils.fillCell(rowAdded, 0, mVo.getInterfaceId(), bodyCellStyle);
		href.setAddress("#" + mVo.getInterfaceId() + "!A1");
		rowAdded.getCell(0).setHyperlink(href);
		MappingExcelUtils.fillCell(rowAdded, 1, mVo.getInterfaceName(), bodyCellStyle);
		MappingExcelUtils.fillCell(rowAdded, 2, mVo.getServiceName() + "(" + mVo.getServiceId()
				+ ")", bodyCellStyle);
		MappingExcelUtils.fillCell(rowAdded, 3, mVo.getOperationId(), bodyCellStyle);
		MappingExcelUtils.fillCell(rowAdded, 4, mVo.getOperationName(), bodyCellStyle);
		MappingExcelUtils.fillCell(rowAdded, 5, mVo.getConsumerSysAb(), bodyCellStyle);
		MappingExcelUtils.fillCell(rowAdded, 6, mVo.getProviderSysAb(), bodyCellStyle);
		MappingExcelUtils.fillCell(rowAdded, 7, "0".equals(mVo.getType()) ? "Consumer"
				: "Provider", bodyCellStyle);
		MappingExcelUtils.fillCell(rowAdded, 8, mVo.getProviderSysId(), bodyCellStyle);
		MappingExcelUtils.fillCell(rowAdded, 9, mVo.getMsgType(), bodyCellStyle);
	}
	
	/**
	 * PAINT SHEET2 HEADER
	 * @param ServiceInvokeRelation r
	 * @param Sheet contentSheet
	 * @param MappingExcelIndexVo mVo
	 */
	private void printHeader(RelationVo r,
			Sheet contentSheet, MappingExcelIndexVo mVo) {
		MappingExcelUtils.fillCell(0, 0, "交易码", contentSheet, headCellStyle);
		MappingExcelUtils.fillCell(0, 1, mVo.getInterfaceId(), contentSheet, cellStyle);
		MappingExcelUtils.fillCell(0, 6, "服务名称", contentSheet, headCellStyle);
		MappingExcelUtils.fillCell(0, 7, mVo.getServiceName() + "(" + mVo.getServiceId() + ")",
				contentSheet, cellStyle);
		MappingExcelUtils.fillCell(1, 0, "交易名称", contentSheet, headCellStyle);
		MappingExcelUtils.fillCell(1, 1, mVo.getInterfaceName(), contentSheet, cellStyle);
		MappingExcelUtils.fillCell(1, 6, "服务操作名称", contentSheet, headCellStyle);
		MappingExcelUtils.fillCell(1, 7, mVo.getOperationId() + mVo.getOperationName(),
				contentSheet, cellStyle);
		MappingExcelUtils.fillCell(2, 6, "服务描述", contentSheet, headCellStyle);
		MappingExcelUtils.fillCell(2, 7, mVo.getServiceRemark(), contentSheet, cellStyle);
		MappingExcelUtils.fillCell(3, 6, "服务操作描述", contentSheet, headCellStyle);
		MappingExcelUtils.fillCell(3, 7, mVo.getOperationRemark(), contentSheet, cellStyle);
		MappingExcelUtils.fillCell(4, 0, "原始接口", contentSheet, headCenterCellStyle);
		MappingExcelUtils.fillCell(4, 6, "SPDBSD", contentSheet, headCenterCellStyle);

		MappingExcelUtils.fillCell(5, 0, "英文名称", contentSheet, headCellStyle);
		MappingExcelUtils.fillCell(5, 1, "中文名称", contentSheet, headCellStyle);
		MappingExcelUtils.fillCell(5, 2, "数据类型", contentSheet, headCellStyle);
		MappingExcelUtils.fillCell(5, 3, "是否必输", contentSheet, headCellStyle);
		MappingExcelUtils.fillCell(5, 4, "备注", contentSheet, headCellStyle);
		MappingExcelUtils.fillCell(5, 6, "英文名称", contentSheet, headCellStyle);
		MappingExcelUtils.fillCell(5, 7, "数据类型", contentSheet, headCellStyle);
		MappingExcelUtils.fillCell(5, 8, "中文名称", contentSheet, headCellStyle);
		MappingExcelUtils.fillCell(5, 9, "备注", contentSheet, headCellStyle);
		
		// 初始化表头HEADER中的空格子
		for (int i=0;i<6;i++) {
			for (int j=0;j<10;j++) {
				if (contentSheet.getRow(i).getCell(j) == null) {
					MappingExcelUtils.fillCell(i, j, "", contentSheet, cellStyle);
				}
			}
		}
	}
	
	/**
	 * 递归 SDA
	 * @param sda
	 */
	public void renderSDA(SDAVO sda) {
		SDA node = sda.getValue();
		
		paintSDANode(node, sda.getXpath());
		
		if (sda.getChildNode() != null) {
			List<SDAVO> childSda = sda.getChildNode();
			for (SDAVO a : childSda) {
				renderSDA(a);
			}
		}
		
		String type = node.getType();
		String mid = node.getMetadataId();
		String alias = "";
		if (!"".equals(mid) && null != mid) {
			if (serviceManager.getMetadataAttrById(mid) != null) {
				alias = serviceManager.getMetadataAttrById(mid).getElementName();
			} else {
				log.info("METADATA_STRUCTS_ATTR表" + mid + "属性为找到");
			}
		}
		boolean paintEnd = true;
		
		// 不知道这段是干啥的了
//		if (!"".equals(mid) && null != mid ) {
//			paintEnd = metadataManager.IsPaintNodeEnd(mid);
//		}
		
		if (paintEnd) {
		
			// PAINT ARRAY END
			if (type.equalsIgnoreCase("array")) {
				Row temprow = sheet2.createRow(i);
				i++;
				MappingExcelUtils.fillCell(temprow, 5, "", arrayCellStyle);
				MappingExcelUtils.fillCell(temprow, 6, node.getStructId(), arrayCellStyle);
				MappingExcelUtils.fillCell(temprow, 7, "Array", arrayCellStyle);
				MappingExcelUtils.fillCell(temprow, 8, alias, arrayCellStyle);
				MappingExcelUtils.fillCell(temprow, 9, "End", arrayCellStyle);
				
				// 非SOAP
				if (!r.getType().equalsIgnoreCase("SOAP")) {
					IDA s = null;
					s = sda4Imap.get(sda.getXpath());
					if (s != null) {
						
						MappingExcelUtils.fillCell(temprow, 0, s.getStructName(), arrayCellStyle);
						MappingExcelUtils.fillCell(temprow, 1, s.getStructAlias(), arrayCellStyle);
						MappingExcelUtils.fillCell(temprow, 2, "Array", arrayCellStyle);
						MappingExcelUtils.fillCell(temprow, 3, s.getRequired(), arrayCellStyle4r);
						MappingExcelUtils.fillCell(temprow, 4, "End", arrayCellStyle);
					} else {
						// 画灰色格子
						paintSDA4INoMapping(temprow);
					}
				} else {
						paintSDA4IEmptyArray(temprow);
				}
			} else if (type.equalsIgnoreCase("struct") || (type.equals("") && node.getRemark().equalsIgnoreCase("Start"))) {
				
				Row temprow = sheet2.createRow(i);
				i++;
				MappingExcelUtils.fillCell(temprow, 5, "", arrayCellStyle);
				MappingExcelUtils.fillCell(temprow, 6, node.getStructId(), arrayCellStyle);
				MappingExcelUtils.fillCell(temprow, 7, "Struct", arrayCellStyle);
				MappingExcelUtils.fillCell(temprow, 8, alias, arrayCellStyle);
				MappingExcelUtils.fillCell(temprow, 9, "End", arrayCellStyle);
				
				if (!r.getType().equalsIgnoreCase("SOAP")) {
					IDA s = null;
					s = sda4Imap.get(sda.getXpath());
					if (s != null) {
						
						MappingExcelUtils.fillCell(temprow, 0, s.getStructName(), arrayCellStyle);
						MappingExcelUtils.fillCell(temprow, 1, s.getStructAlias(), arrayCellStyle);
						MappingExcelUtils.fillCell(temprow, 2, "Struct", arrayCellStyle);
						MappingExcelUtils.fillCell(temprow, 3, s.getRequired(), arrayCellStyle4r);
						MappingExcelUtils.fillCell(temprow, 4, "End", arrayCellStyle);
					} else {
						// 画灰色格子
						paintSDA4INoMapping(temprow);
					}
				} else {
					paintSDA4IEmptyArray(temprow);
				}
			}
		}
	}
	
	/**
	 * 初始化 sda4Imap<xpath, SDA4INode>
	 * @param sda
	 */
	public void renderSDA4I(SDA4I sda) {
		IDA node = sda.getValue();
		sda4Imap.put(sda.getXpath(), node);
		if (sda.getChildNode() != null) {
			List<SDA4I> childSda = sda.getChildNode();
			for (SDA4I a : childSda) {
				renderSDA4I(a);
			}
		}
	}
	/**
	 * 
	 * @param SDANode n
	 * @param xpath
	 */
	public void paintSDANode(SDA n, String xpath) {
		Row temprow=sheet2.createRow(i);
		String structName = n.getStructId().trim();
		String type = n.getType();
		String metadataId = n.getMetadataId().trim();
		String alias = "";
		if (structName.equals("SvcBody")) {
			return ;
		} else if (structName.equals("request")) {
			temprow=sheet2.createRow(i);
			temprow.createCell(0).setCellValue("输入");
			temprow.createCell(1).setCellValue("");
			temprow.createCell(2).setCellValue("");
			temprow.createCell(3).setCellValue("");
			temprow.createCell(4).setCellValue("");
			temprow.createCell(5).setCellValue("");
			temprow.createCell(6).setCellValue("");
			temprow.createCell(7).setCellValue("");
			temprow.createCell(8).setCellValue("");
			temprow.createCell(9).setCellValue("");
			sheet2.addMergedRegion(new CellRangeAddress(i,i,0,9));
			inputIndex=i;
			//转到下一行
			i++;
			return ;
		} else if (structName.equals("response")) {
			temprow=sheet2.createRow(i);
			temprow.createCell(0).setCellValue("输出");
			temprow.createCell(1).setCellValue("");
			temprow.createCell(2).setCellValue("");
			temprow.createCell(3).setCellValue("");
			temprow.createCell(4).setCellValue("");
			temprow.createCell(5).setCellValue("");
			temprow.createCell(6).setCellValue("");
			temprow.createCell(7).setCellValue("");
			temprow.createCell(8).setCellValue("");
			temprow.createCell(9).setCellValue("");
			sheet2.addMergedRegion(new CellRangeAddress(i,i,0,9));
			outputIndex=i;
			i++;
			return ;
		} 
		
//		if (serviceManager.getMetadataAttrById(structName) != null) {
//			alias = serviceManager.getMetadataAttrById(structName).getElementName();
//		} else {
//			log.info("METADATA_STRUCTS_ATTR表" + structName + "属性为找到");
//		}
		
		
		if (type.equalsIgnoreCase("array")) {
			try {

				// 画出Array开始节点
				MappingExcelUtils.fillCell(temprow, 5, "", arrayCellStyle);
				MappingExcelUtils.fillCell(temprow, 6, n.getStructId(), arrayCellStyle);
				MappingExcelUtils.fillCell(temprow, 7, "Array", arrayCellStyle);
				MappingExcelUtils.fillCell(temprow, 8, alias, arrayCellStyle);
				MappingExcelUtils.fillCell(temprow, 9, n.getRemark(), arrayCellStyle);
				
				if (!r.getType().equalsIgnoreCase("SOAP")) {
					IDA s = null;
					// 找对应的接口节点
					s = sda4Imap.get(xpath);
					
					if (s != null) {
						
						MappingExcelUtils.fillCell(temprow, 0, s.getStructName(), arrayCellStyle);
						MappingExcelUtils.fillCell(temprow, 1, s.getStructAlias(), arrayCellStyle);
						MappingExcelUtils.fillCell(temprow, 2, "Array", arrayCellStyle);
						MappingExcelUtils.fillCell(temprow, 3, s.getRequired(), arrayCellStyle4r);
						MappingExcelUtils.fillCell(temprow, 4, s.getRemark(), arrayCellStyle);
					} else {
						paintSDA4INoMapping(temprow);
					}
				} else {
					MappingExcelUtils.fillCell(temprow, 0, "", arrayCellStyle);
					MappingExcelUtils.fillCell(temprow, 1, "", arrayCellStyle);
					MappingExcelUtils.fillCell(temprow, 2, "", arrayCellStyle);
					MappingExcelUtils.fillCell(temprow, 3, n.getRequired(), arrayCellStyle4r);
					MappingExcelUtils.fillCell(temprow, 4, "", arrayCellStyle);
				}
				i++;
				return ;
			} catch (Exception e) {
				e.printStackTrace();
				log.info("Exception at paint SDANode, metadataId:" + metadataId);
			}
		  // type为空remak为Start的 是struct节点 - -#  
		} else if (type.equalsIgnoreCase("struct") || (type.equals("") && n.getRemark().equalsIgnoreCase("Start"))) {
			Map<String, SDA> map = new HashMap<String, SDA>();
			map.put(n.getStructId(), n);
			// remak为Start的 不画struct sheet
			if (!n.getRemark().equalsIgnoreCase("Start")) {
				lstStructName.add(map);
			}
			MappingExcelUtils.fillCell(temprow, 5, "", arrayCellStyle);
			MappingExcelUtils.fillCell(temprow, 6, n.getStructId(), arrayCellStyle);
			MappingExcelUtils.fillCell(temprow, 7, "Struct", arrayCellStyle);
			MappingExcelUtils.fillCell(temprow, 8, alias, arrayCellStyle);
			MappingExcelUtils.fillCell(temprow, 9, n.getRemark(), arrayCellStyle);
			if (!r.getType().equalsIgnoreCase("SOAP")) {
				IDA s = null;
				s = sda4Imap.get(xpath);
				if (s != null) {
					MappingExcelUtils.fillCell(temprow, 0, s.getStructName(), arrayCellStyle);
					MappingExcelUtils.fillCell(temprow, 1, s.getStructAlias(), arrayCellStyle);
					MappingExcelUtils.fillCell(temprow, 2, "Struct", arrayCellStyle);
					MappingExcelUtils.fillCell(temprow, 3, s.getRequired(), arrayCellStyle4r);
					MappingExcelUtils.fillCell(temprow, 4, s.getRemark(), arrayCellStyle);
				} else {
					paintSDA4INoMapping(temprow);
				}
			} else {
				MappingExcelUtils.fillCell(temprow, 0, "", arrayCellStyle);
				MappingExcelUtils.fillCell(temprow, 1, "", arrayCellStyle);
				MappingExcelUtils.fillCell(temprow, 2, "", arrayCellStyle);
				MappingExcelUtils.fillCell(temprow, 3, n.getRequired(), arrayCellStyle4r);
				MappingExcelUtils.fillCell(temprow, 4, "", arrayCellStyle);
			}
			i++;
			return ;
		}
		try {
			// Normal painting
			String mmid = n.getMetadataId().trim();
			if ("".equals(mmid) || null == mmid)
				return ;
			MetadataViewBean metadata = serviceManager.getMetadataById(mmid);
			metadata.setType(type);
			MappingExcelUtils.fillCell(temprow, 3, n.getRequired(), cellStyle4r);
			MappingExcelUtils.fillCell(temprow, 5, "", cellStyle);
			MappingExcelUtils.fillCell(temprow, 6, n.getStructId(), cellStyle);
			if (metadata != null) {
				MappingExcelUtils.fillCell(temprow, 7, metadata.getTypeLengthAndScale(), cellStyle);
			}
			MappingExcelUtils.fillCell(temprow, 8, metadata.getMetadataName(), cellStyle);
			MappingExcelUtils.fillCell(temprow, 9, n.getRemark(), cellStyle);
			// SOAP类型不画左侧（除是否必输）
			if (!r.getType().equalsIgnoreCase("SOAP")) {
				// paint SDA4I
				IDA s = null;
				s = sda4Imap.get(xpath);
				if (s != null) {
					paintSDANode4I2(s);
				} else {
					paintSDA4INoMapping(temprow);
				}
			}
			i++;
			
		} catch (DataException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 写入左侧接口数据
	 * @param SDANode4I n
	 */
	public void paintSDANode4I2(IDA n) {
		Row temprow=sheet2.getRow(i);
		String structName = n.getStructName();
		if (structName.equals("SvcBody")||structName.equals("request")||structName.equals("response")) 
			return ;
		if (temprow == null )
			temprow = sheet2.createRow(i);
//			MetadataViewBean metadata = serviceManager.getMetadataById(n.getMetadataId().trim());
			MappingExcelUtils.fillCell(temprow, 0, structName, cellStyle);
			MappingExcelUtils.fillCell(temprow, 1, n.getStructAlias(), cellStyle);
			MappingExcelUtils.fillCell(temprow, 2, n.getTypeLengthAndScale()
					, cellStyle);
			// 在paintNode normal 中写入类型
//			MappingExcelUtils.fillCell(temprow, 3, n.getRequired(), cellStyle4r);
			MappingExcelUtils.fillCell(temprow, 4, n.getRemark(), cellStyle);
	}

	/**
	 * 左侧接口无映射
	 * @param temprow
	 */
	private void paintSDA4INoMapping(Row temprow) {
		MappingExcelUtils.fillCell(temprow, 0, "", greyCellStyle);
		MappingExcelUtils.fillCell(temprow, 1, "", greyCellStyle);
		MappingExcelUtils.fillCell(temprow, 2, "", greyCellStyle);
		MappingExcelUtils.fillCell(temprow, 3, "", greyCellStyle);
		MappingExcelUtils.fillCell(temprow, 4, "不映射", greyCellStyle);
	}
	
	/**
	 * paintSDA4IEmptyArray
	 * @param temprow
	 */
	private void paintSDA4IEmptyArray(Row temprow) {
		MappingExcelUtils.fillCell(temprow, 0, "", arrayCellStyle);
		MappingExcelUtils.fillCell(temprow, 1, "", arrayCellStyle);
		MappingExcelUtils.fillCell(temprow, 2, "", arrayCellStyle);
		MappingExcelUtils.fillCell(temprow, 3, "", arrayCellStyle);
		MappingExcelUtils.fillCell(temprow, 4, "", arrayCellStyle);
	}
	
	@Override
	public void initManager(
			com.dc.esb.servicegov.refactoring.service.impl.ServiceManagerImpl serviceManager,
			SystemManagerImpl systemManager,
			InterfaceManagerImpl interfaceManager,
			com.dc.esb.servicegov.refactoring.service.impl.MetadataManagerImpl metadataManager) {
		
		this.serviceManager = serviceManager ;
		this.systemManager = systemManager;
		this.interfaceManager = interfaceManager;
	}

	@Override
	public void init(RelationVo r, Workbook wb, Sheet sheet,
			CountDownLatch countDown, List<Map<String, SDA>> lstStructName) {
		
		this.r = r;
		this.wb = wb;
		this.sheet2 = sheet;
		this.countDown = countDown;
		this.lstStructName = lstStructName;
	}

}

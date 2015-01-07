package com.dc.esb.servicegov.excel;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import com.dc.esb.servicegov.excel.impl.ESBMappingExcelGenerator;

@Component
public class ESBMetadataStructsGenerator {

	//包含的元数据结构个数
	private int structCount=0;

	private ESBMappingExcelGenerator mappingExcelGenerator = new ESBMappingExcelGenerator();
//	
//	MetadataStructToDB metadataStructToDB = new MetadataStructToDB();
//	private List<Map> allStructsList=metadataStructToDB.searchAllMDTStructs();
//	
	//生成结构体的sheet页
//	protected void generateStructsSheets(Workbook wb,String operationId){
//		List<Map> list=	mappingExcelGenerator.getStructsInfoByType(operationId);
//		if(list!=null&&list.size()>0){
//			//判断Index1的sheet页面是否存在
//			Sheet structSheet;
//			if (wb.getNumberOfSheets() <= 1) {
//				structSheet = wb.createSheet("INDEX1");
//			} else {
//				if(wb.getSheet("INDEX1")!=null){
//					structSheet=wb.getSheet("INDEX1");
//				}else{
//					structSheet = wb.createSheet("INDEX1");
//				}
//			}
//			this.createAndFillFirstRow(structSheet);
//			this.createAndFillOthersRow(structSheet, list);
//			this.setIndexStyle(structSheet, wb);
//			this.setIndexOthersStyle(structSheet, wb);
//			// 创建其他的sheet页
//			for(Map map:list){
//				if(map!=null){
//					String structId=map.get("METADATAID").toString();
//					String structName=setValue(map.get("STRUCTALIAS").toString());
//					// 判断是否存在相同的sheet
//					boolean flag=false;
//					for(int i=0;i<wb.getNumberOfSheets();i++){
//						if(structId.equals(wb.getSheetName(i))){
//							flag=true;
//						}
//					}
//					//存在则跳出,不记录
//					if(flag==true){
//						break;
//					}
//					Sheet metadataSheet=wb.createSheet(structId);
//					
//					//获取当前的结构包含的所有元数据信息
//					List<Map> metadataList=this.getStructByStructID(structId);
//					
//					//创建开始
//					this.createMetadataHeadInfo(metadataSheet, structId, structName);
//					this.createBlankRow(metadataSheet, wb, 4);
//					this.createMetadataDetailInfo(metadataSheet, metadataList);
//					this.createBlankRow(metadataSheet, wb, 5 + metadataList.size());
//					this.setMetadataDetailStyle(metadataSheet, wb , metadataList.size());
//				}
//			}
//			
//		}
//	}
}
	
	
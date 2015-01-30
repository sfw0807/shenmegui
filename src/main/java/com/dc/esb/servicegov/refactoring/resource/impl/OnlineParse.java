package com.dc.esb.servicegov.refactoring.resource.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.refactoring.dao.impl.InterfaceDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.InvokeInfoDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.OperationDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.PublishInfoDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.SLADAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.ServiceDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.SystemDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.TransStateDAOImpl;
import com.dc.esb.servicegov.refactoring.entity.Interface;
import com.dc.esb.servicegov.refactoring.entity.InvokeInfo;
import com.dc.esb.servicegov.refactoring.entity.Operation;
import com.dc.esb.servicegov.refactoring.entity.PublishInfo;
import com.dc.esb.servicegov.refactoring.entity.SLA;
import com.dc.esb.servicegov.refactoring.entity.TransState;
import com.dc.esb.servicegov.refactoring.util.ExcelTool;
import com.dc.esb.servicegov.refactoring.util.Utils;


@Service
@Transactional(rollbackFor=Exception.class)
public class OnlineParse{
	private Log log = LogFactory.getLog(OnlineParse.class);
	ExcelTool excelTool = ExcelTool.getInstance();
	@Autowired
	private InvokeInfoDAOImpl invokeInfoDAO;
	@Autowired
	private SystemDAOImpl systemDAO;
	@Autowired
	private PublishInfoDAOImpl publishDAO;
	@Autowired
	private TransStateDAOImpl tranStateDAO;
	@Autowired
	private OperationDAOImpl operationDAO;
	@Autowired
	private ServiceDAOImpl serviceDAO;
	@Autowired
	private InterfaceDAOImpl interfaceDAO;
	@Autowired
	private SLADAOImpl slaDAO;
	private List<SLA> list = null;
	//解析上线服务总表
	public String parse(Sheet onlineSheet) {
		list = new ArrayList<SLA>();
		log.error("第一行："+onlineSheet.getRow(0).getCell(1));
		int validCellNum = onlineSheet.getRow(0).getLastCellNum();
		int rowNum = 0;
		int succCount = 0;
		boolean rowParseFlag = true;
		//校验格式是否相符合
		if(validCellNum==19){
			rowNum = onlineSheet.getLastRowNum();
			for(int i=1;i<=rowNum;i++){
				Row row = onlineSheet.getRow(i);
				
				String consumeInfo = excelTool.getCellContent(row.getCell(1)).trim();
				String provideSys = excelTool.getCellContent(row.getCell(3)).trim();

				//serviceId处理
				String serviceInfo = excelTool.getCellContent(row.getCell(6)).trim();
				serviceInfo=serviceInfo.replace("（", "(");
				serviceInfo=serviceInfo.replace("）", ")");
				String serviceId = serviceInfo.substring(serviceInfo.indexOf("(") + 1,serviceInfo.indexOf(")"));
				
				String operationId = excelTool.getCellContent(row.getCell(7	)).trim();
				
				String interfaceId = excelTool.getCellContent(row.getCell(4)).trim();
				if("".equals(interfaceId) || null==interfaceId){
					interfaceId = serviceId+operationId;
				}
				//报文类型处理
				String msgInfo = excelTool.getCellContent(row.getCell(9)).trim();
				msgInfo=msgInfo.replace("非标", "");
				msgInfo=msgInfo.replace("（", "(");
				msgInfo=msgInfo.replace("）", ")");
				msgInfo=msgInfo.replace("(", "");
				msgInfo=msgInfo.replace(")", "");
				msgInfo=msgInfo.replace("->", "-");
				String consumeMsgType = msgInfo.split("-")[0];
				String provideMsgType = msgInfo.split("-")[1];
				
				String state = excelTool.getCellContent(row.getCell(10)).trim();
				String version = excelTool.getCellContent(row.getCell(11)).trim();
				String firstOnlineDate = excelTool.getCellContent(row.getCell(12)).trim();
				String OnlineDateInfo = excelTool.getCellContent(row.getCell(13)).trim();
				String[] onlineDateArr = OnlineDateInfo.split("、");
				String currentCount = excelTool.getCellContent(row.getCell(14));
				String timeOut = excelTool.getCellContent(row.getCell(15));
				String averageTime = excelTool.getCellContent(row.getCell(17));
				String successRate = row.getCell(18).toString();
				if(successRate != null && !"".equals(successRate)){
					successRate = Float.parseFloat(successRate)*100 + "%";
				}
				// 修改或保存服务操作的SLA
				if(slaDAO.delSlaByOperationIdAndServiceId(serviceId, operationId)){
					list.add(new SLA(serviceId,operationId,Utils.currentCount,currentCount));
					list.add(new SLA(serviceId,operationId,Utils.timeOut,timeOut));
					list.add(new SLA(serviceId,operationId,Utils.averageTime,averageTime));
					list.add(new SLA(serviceId,operationId,Utils.successRate,successRate));
				}
				if(!"".equals(consumeInfo) && null!=consumeInfo){
					String[] consumeAbArr = consumeInfo.split("、");
					for (int j = 0; j < consumeAbArr.length; j++) {
						String consumeAb = consumeAbArr[j].trim();
						String consumeId = systemDAO.getSystemIdByAb(consumeAb);
						String provideId = systemDAO.getSystemIdByAb(provideSys);
						Map<String,String> paramMap = new HashMap<String,String>();
						if(consumeId!=null&&!"".equals(consumeId)){
							paramMap.put("consumeSysId", consumeId);
						}
						if(provideId!=null&&!"".equals(provideId)){
							paramMap.put("provideSysId", provideId);
						}
						if(interfaceId!=null&&!"".equals(interfaceId)){
							paramMap.put("ecode", interfaceId);
						}
						if(serviceId!=null&&!"".equals(serviceId)){
							paramMap.put("serviceId", serviceId);
						}
						if(operationId!=null&&!"".equals(operationId)){
							paramMap.put("operationId", operationId);
						}
						if(consumeMsgType!=null&&!"".equals(consumeMsgType)){
							paramMap.put("consumeMsgType", consumeMsgType);
						}
						if(provideMsgType!=null&&!"".equals(provideMsgType)){
							paramMap.put("provideMsgType", provideMsgType);
						}
						List<InvokeInfo> invokeList = invokeInfoDAO.findBy(paramMap);
						if(invokeList.size()==1){
							int irId = invokeList.get(0).getId();
							List<PublishInfo> publishInfoList = publishDAO.findBy("iRid", irId);
							publishDAO.delete(publishInfoList);
							if("下线".equals(state)){
								TransState transState = tranStateDAO.findUniqueBy("id", irId);
								transState.setVersionState(state);
								tranStateDAO.save(transState);
								Operation operation = operationDAO.getOperation(operationId, serviceId);
								operation.setVersion(Utils.modifyOnlineNo(version.substring(1),operation.getVersion()));
								operation.setState(state);
								operationDAO.save(operation);
								continue;
							}
							PublishInfo firstpublishInfo = new PublishInfo();
							firstpublishInfo.setId(publishDAO.getMaxId()+1);
							firstpublishInfo.setIRid(irId);
							firstpublishInfo.setOnlineDate(firstOnlineDate);
							firstpublishInfo.setOperationVersion(1+"");
							firstpublishInfo.setServiceVersion(1+"");
							firstpublishInfo.setInerfaceVersion(1+"");
							publishDAO.save(firstpublishInfo);
							for (int k = 0; k < onlineDateArr.length; k++) {
								if(onlineDateArr[k].trim()!=null&&!"".equals(onlineDateArr[k].trim())){
									log.error("OnlineDate:"+onlineDateArr[k].trim()+"版本号："+(k+2));
									//insert publishinfo
									PublishInfo publishInfo = new PublishInfo();
									publishInfo.setId(publishDAO.getMaxId()+1);
									publishInfo.setIRid(irId);
									publishInfo.setOnlineDate(onlineDateArr[k].trim());
									publishInfo.setOperationVersion((k+2)+"");
									publishInfo.setServiceVersion((k+2)+"");
									publishInfo.setInerfaceVersion((k+2)+"");
									publishDAO.save(publishInfo);
								}
							}
							//update transstate
							TransState transState = tranStateDAO.findUniqueBy("id", irId);
							transState.setVersionState(state);
							tranStateDAO.save(transState);
							//update operation、service、interface
							Operation operation = operationDAO.getOperation(operationId, serviceId);
							if(operation == null){
								log.info("操作 [" + operationId + "]不存在!");
							}
							operation.setVersion(Utils.modifyOnlineNo(version.substring(1),operation.getVersion()));
							operation.setState(state);
							operationDAO.save(operation);
							com.dc.esb.servicegov.refactoring.entity.Service service = serviceDAO.findUniqueBy("serviceId", serviceId);
							service.setVersion(Utils.modifyOnlineNo(version.substring(1),service.getVersion()));
							service.setState(state);
							serviceDAO.save(service);
							Interface interfaceInfo = interfaceDAO.findUniqueBy("interfaceId", interfaceId);
							interfaceInfo.setState(state);
							interfaceDAO.save(interfaceInfo);
							log.error("第"+i+"行,当调用方系统为："+consumeId+"时,解析成功");
						}else{
							log.error("第"+i+"行,当调用方系统为："+consumeId+
									"时,解析失败,原因:根据此行文档数据interafce:"+
									interfaceId+",serviceId:"+serviceId+",operationId:"+
									operationId+",consumeMsgType:"+consumeMsgType+",provideMsgType:"+
									provideMsgType+",psys:"+provideId+",csys:"+consumeId+",查询到"+
									invokeList.size()+"条ir信息");
							rowParseFlag = false;
							continue;
						}
					}
				}
				if(rowParseFlag){
					succCount++;
				}
			}
		}else{
			log.error("文档格式不符,请检查列数");
		}
		List<SLA> duplicateList = this.DuplicateInvokeList(list);
		// 插入sda
		if (slaDAO.delSlaList(duplicateList)) {
			slaDAO.batchInsertSla(duplicateList);
		}
		return "共"+rowNum+"条记录,成功："+succCount+"条,失败："+(rowNum-succCount)+"条";
	}
	
	/**
	 * 去重复元素
	 * @param targetList
	 * @param sourceList
	 * @return
	 */
	public List<SLA> DuplicateInvokeList(List<SLA> sourceList) {
		List<SLA> targetList = new ArrayList<SLA>();
		for (SLA source : sourceList) {
			boolean flag = false;
			for (SLA target : targetList) {
				if (target.equals(source)) {
					flag = true;
					break;
				}
			}

			if (!flag) {
				targetList.add(source);
			}
		}
		return targetList;
	}
}

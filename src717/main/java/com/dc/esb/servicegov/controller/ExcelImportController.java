package com.dc.esb.servicegov.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletResponse;

import com.dc.esb.servicegov.entity.*;
import com.dc.esb.servicegov.service.MetadataService;
import com.dc.esb.servicegov.service.impl.LogInfoServiceImpl;
import com.dc.esb.servicegov.service.impl.MetadataServiceImpl;
import com.dc.esb.servicegov.util.GlobalImport;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dc.esb.servicegov.service.ExcelImportService;
import com.dc.esb.servicegov.util.ExcelTool;

@Controller
@RequestMapping("/excelHelper")
public class ExcelImportController {

	protected Log logger = LogFactory.getLog(getClass());

	@Autowired
	ExcelImportService excelImportService;

	@Autowired
	MetadataServiceImpl metadataService;

	@Autowired
	LogInfoServiceImpl logInfoService;
	/**
	 * Excel 2003
	 */
	private final static String XLS = "xls";
	/**
	 * Excel 2007
	 */
	private final static String XLSX = "xlsx";

	private static int readline = 0;

	@RequestMapping(method = RequestMethod.POST, value = "/fieldimport")
	public void importFieldMapping(@RequestParam("file")
									   MultipartFile file, Model model, HttpServletResponse response, @RequestParam("select")
									   String operateFlag) {

		response.setContentType("text/html");
		response.setCharacterEncoding("GB2312");

		logger.info("覆盖标识: " + operateFlag);
		if ("Y".equals(operateFlag)) {
			GlobalImport.operateFlag = true;
		} else {
			GlobalImport.operateFlag = false;
		}
		//List<Object> list = new LinkedList<Object>();

		Workbook workbook = null;
		String extensionName = FilenameUtils.getExtension(file
				.getOriginalFilename());

		InputStream is;
		java.io.PrintWriter writer = null;
		StringBuffer msg = new StringBuffer();
		try {
			writer = response.getWriter();
			is = file.getInputStream();
			if (extensionName.toLowerCase().equals(XLS)) {
				workbook = new HSSFWorkbook(is);
			} else if (extensionName.toLowerCase().equals(XLSX)) {
				workbook = new XSSFWorkbook(is);
			}else{
				writer.println("<script language=\"javascript\">");
				writer.println("alert('导入失败,文件类型不支持!');");
				writer.println("window.location='/jsp/sysadmin/fieldmapping_import.jsp'");
				writer.println("</script>");
				writer.flush();
				writer.close();
				return;

			}

			// 读取交易索引Sheet页
			Sheet indexSheet = workbook.getSheet("INDEX");
			if (indexSheet == null) {

				logger.error("缺少INDEX sheet页");
				logInfoService.saveLog("缺少INDEX sheet页","导入");
				writer.println("<script language=\"javascript\">");
				writer.println("alert('缺少INDEX sheet页!');");
				writer.println("window.location='/jsp/sysadmin/fieldmapping_import.jsp'");
				writer.println("</script>");
				writer.flush();
				writer.close();
				return ;
			}

			int endRow = indexSheet.getLastRowNum();
			// 从第一行开始读，获取所有交易行
			for (int i = 1; i <= endRow; i++) {
				Row row = indexSheet.getRow(i);
				// 读取每一行第一列，获取每个交易sheet名称
				String sheetName = ExcelTool.getInstance().getCellContent(
						row.getCell(0));

				//接口消费方
				String cusumerSystem = ExcelTool.getInstance().getCellContent(
						row.getCell(5));

				//接口提供方
				String providerSystem = ExcelTool.getInstance().getCellContent(
						row.getCell(6));

				//接口方向
				String interfacepoint = ExcelTool.getInstance().getCellContent(
						row.getCell(7));

				String interfaceHead = ExcelTool.getInstance().getCellContent(
						row.getCell(18));
				String systemId = cusumerSystem;
				if ("Provider".equalsIgnoreCase(interfacepoint)) {
					systemId = providerSystem;
				}

				//判断系统是否存在
				boolean exists = excelImportService.existSystem(systemId);
				if (!exists) {
					logger.error("交易[" + sheetName + "]," + systemId + "系统不存在");
					logInfoService.saveLog("交易[" + sheetName + "]," + systemId + "系统不存在","导入");
					msg.append("交易[" + sheetName + "]," + systemId + "系统不存在，");
					continue;
				}
				if (sheetName != null && !"".equals(sheetName)) {
					// 读取每个交易sheet页
					logger.debug("开始获取" + sheetName
							+ "交易信息=========================");

					Sheet sheet = workbook.getSheet(sheetName);

					//获取交易、服务、场景信息
					Map<String, Object> infoMap = getInterfaceAndServiceInfo(sheet);
					//list.add(infoMap);

					//获取接口、服务 输入 参数
					Map<String, Object> inputMap = getInputArg(sheet);

					//获取接口、服务 输出 参数
					Map<String, Object> outMap = getOutputArg(sheet);



					Map<String,Object> headMap = null;
					if (interfaceHead != null && !"".equals(interfaceHead)) {
						if(GlobalImport.headMap.get(interfaceHead)==null){

							Sheet headSheet = workbook.getSheet(interfaceHead);
							if (headSheet == null) {
								logger.info("交易[" + sheetName + "]，没找到业务报文头[" + interfaceHead + "]");
							} else {
								headMap = getInterfaceHead(headSheet);
								if(headMap!=null){
									headMap.put("headName", interfaceHead);
								}

							}
						}else{
							headMap = new HashMap<String, Object>();
							headMap.put("headName", interfaceHead);
						}
					}

					if(infoMap==null || inputMap == null || outMap == null || (headMap == null && (interfaceHead != null && !"".equals(interfaceHead)))){
						msg.append(sheetName+"导入失败，");
						continue;
					}

					Map<String, String> publicMap = new HashMap<String, String>();
					publicMap.put("providerSystem", providerSystem);
					publicMap.put("cusumerSystem", cusumerSystem);
					publicMap.put("interfacepoint", interfacepoint);

					logger.info("===========交易[" + sheetName + "],开始导入字段映射信息=============");
					long time = java.lang.System.currentTimeMillis();
					boolean result = excelImportService.executeImport(infoMap, inputMap, outMap, publicMap,headMap);

					if (!result) {
						logger.info("===========交易[" + sheetName + "],导入失败=============");
						continue;
					}
					long useTime = java.lang.System.currentTimeMillis() - time;
					logger.info("===========交易[" + sheetName + "],导入完成，耗时" + useTime + "ms=============");


				} else {
					logger.error("第" + i + "行，交易代码为空。");
					logInfoService.saveLog("第" + i + "行，交易代码为空。","导入");
				}

			}
			writer.println("<script language=\"javascript\">");

			if(msg.length()==0){
				writer.println("alert('导入成功!');");
			}else {
				writer.println("alert('"+msg.toString()+",请查看日志！');");
			}
		} catch (IOException e) {

			logger.error("导入出现异常:异常信息：" + e.getMessage());
			logInfoService.saveLog("导入出现异常:异常信息：" + e.getMessage(),"导入");
			writer.println("alert('导入失败，请查看日志!');");
		}

		GlobalImport.headMap.clear();//清空本次导入的业务报文头
		writer.println("window.location='/jsp/sysadmin/fieldmapping_import.jsp'");
		writer.println("</script>");
		writer.flush();
		writer.close();
	}

	private Map<String,Object> getInterfaceHead(Sheet sheet) {
		boolean flag = true;
		StringBuffer msg = new StringBuffer();
		Map<String,Object> resMap = new HashMap<String, Object>();

		ExcelTool tools = ExcelTool.getInstance();
		int start = sheet.getFirstRowNum();
		int end = sheet.getLastRowNum();
		int inputIndex = 0;
		int outIndex = 0;
		for (int i = 0; i <= end; i++) {
			Row sheetRow = sheet.getRow(i);
			Cell cellObj = sheetRow.getCell(0);
			if (cellObj != null) {
				String cell = tools.getCellContent(cellObj);
				if ("输入".equals(cell)) {
					inputIndex = i + 1;
				} else if ("输出".equals(cell)) {
					outIndex = i + 1;
					break;
				}
			}
		}

		int order = 0;
		List<Ida> input = new ArrayList<Ida>();
		for (int i = inputIndex; i < outIndex-1; i++) {
			Ida ida = new Ida();
			Row sheetRow = sheet.getRow(i);
			Cell cellObj = sheetRow.getCell(0);
			if (cellObj != null) {
				String cell = tools.getCellContent(cellObj);
				ida.setStructName(isNull(cell));
				if("".equals(isNull(cell))){
					continue;
				}
			}

			cellObj = sheetRow.getCell(1);
			if (cellObj != null) {
				String cell = tools.getCellContent(cellObj);
				ida.setStructAlias(isNull(cell));
			}

			cellObj = sheetRow.getCell(2);
			if (cellObj != null) {
				String cell = tools.getCellContent(cellObj);
				ida.setType(isNull(cell));
			}

			cellObj = sheetRow.getCell(3);
			if (cellObj != null) {
				String cell = tools.getCellContent(cellObj);
				ida.setLength(isNull(cell));
			}
			cellObj = sheetRow.getCell(4);
			if (cellObj != null) {
				String cell = tools.getCellContent(cellObj);
				ida.setRequired(isNull(cell));
			}

			cellObj = sheetRow.getCell(5);
			if (cellObj != null) {
				String cell = tools.getCellContent(cellObj);
				ida.setRemark(isNull(cell));
			}

			cellObj = sheetRow.getCell(7);
			if (cellObj != null) {
				String cell = tools.getCellContent(cellObj);
				ida.setMetadataId(isNull(cell));
			}
			input.add(ida);
			ida.setSeq(order);
			order++;
		}

		order = 0;
		List<Ida> output = new ArrayList<Ida>();
		for (int j = outIndex; j < end; j++) {
			Ida ida = new Ida();
			Row sheetRow = sheet.getRow(j);
			Cell cellObj = sheetRow.getCell(0);
			if (cellObj != null) {
				String cell = tools.getCellContent(cellObj);
				ida.setStructName(isNull(cell));
				if("".equals(isNull(cell))){
					continue;
				}
			}

			cellObj = sheetRow.getCell(1);
			if (cellObj != null) {
				String cell = tools.getCellContent(cellObj);
				ida.setStructAlias(isNull(cell));
			}

			cellObj = sheetRow.getCell(2);
			if (cellObj != null) {
				String cell = tools.getCellContent(cellObj);
				ida.setType(isNull(cell));
			}

			cellObj = sheetRow.getCell(3);
			if (cellObj != null) {
				String cell = tools.getCellContent(cellObj);
				ida.setLength(isNull(cell));
			}
			cellObj = sheetRow.getCell(4);
			if (cellObj != null) {
				String cell = tools.getCellContent(cellObj);
				ida.setRequired(isNull(cell));
			}

			cellObj = sheetRow.getCell(5);
			if (cellObj != null) {
				String cell = tools.getCellContent(cellObj);
				ida.setRemark(isNull(cell));
			}

			cellObj = sheetRow.getCell(6);
			if (cellObj != null) {
				String cell = tools.getCellContent(cellObj);
				ida.setMetadataId(isNull(cell));
				if(cell!=null&&!"".equals(cell)){
					Metadata metadata = metadataService.findUniqueBy("metadataId",cell);
					if(metadata==null){

						logger.error(sheet.getSheetName()+"页,元数据["+cell+"]未配置，导入失败...");
						//logInfoService.saveLog(sheet.getSheetName()+"页,元数据["+cell+"]未配置，导入失败...","导入");
						msg.append(cell).append(",");
						flag = false;
						//return null;
					}
				}
			}
			output.add(ida);
			ida.setSeq(order);
			order++;
		}

		if(!flag){
			logInfoService.saveLog(sheet.getSheetName()+"页,元数据["+msg.toString()+"]未配置，导入失败...","导入报文头");
			return null;
		}
		resMap.put("input",input);
		resMap.put("output",output);

		return resMap;
	}


	private Map<String, Object> getInputArg(Sheet sheet) {
		boolean flag = true;
		StringBuffer msg = new StringBuffer();
		List<Ida> idas = new ArrayList<Ida>();
		List<SDA> sdas = new ArrayList<SDA>();
		ExcelTool tools = ExcelTool.getInstance();
		int start = readline;
		int end = sheet.getLastRowNum();

		int order = 0;
		for (int j = start; j <= end; j++) {
			Ida ida = new Ida();
			SDA sda = new SDA();
			Row sheetRow = sheet.getRow(j);

			Cell cellObj = sheetRow.getCell(0);

			if (cellObj != null) {
				String cell = tools.getCellContent(cellObj);
				if ("输出".equals(cell)) {
					readline = j++;
					break;
				}

				ida.setStructName(isNull(cell));
			}
			cellObj = sheetRow.getCell(1);
			if (cellObj != null) {
				String cell = tools.getCellContent(cellObj);
				ida.setStructAlias(isNull(cell));
			}
			cellObj = sheetRow.getCell(2);
			if (cellObj != null) {
				String cell = tools.getCellContent(cellObj);
				ida.setType(isNull(cell));
			}

			cellObj = sheetRow.getCell(3);
			if (cellObj != null) {
				String cell = tools.getCellContent(cellObj);
				ida.setLength(isNull(cell));
			}

			cellObj = sheetRow.getCell(4);
			if (cellObj != null) {
				String cell = tools.getCellContent(cellObj);
				ida.setRequired(isNull(cell));
			}

			cellObj = sheetRow.getCell(5);
			if (cellObj != null) {
				String cell = tools.getCellContent(cellObj);
				String remark = isNull(cell);
				ida.setRemark(remark);
			}

			cellObj = sheetRow.getCell(7);
			if (cellObj != null) {
				String cell = tools.getCellContent(cellObj);

				if(!"start".equalsIgnoreCase(ida.getRemark())) {
					ida.setMetadataId(isNull(cell));

				}
				sda.setMetadataId(isNull(cell));
				sda.setStructName(isNull(cell));

			}
			ida.setSeq(order);

			cellObj = sheetRow.getCell(8);
			if (cellObj != null) {
				String cell = tools.getCellContent(cellObj);
				sda.setStructAlias(isNull(cell));
			}

			cellObj = sheetRow.getCell(9);
			if (cellObj != null) {
				String cell = tools.getCellContent(cellObj);
				sda.setType(isNull(cell));
			}

			cellObj = sheetRow.getCell(10);
			if (cellObj != null) {
				String cell = tools.getCellContent(cellObj);
				sda.setLength(isNull(cell));
			}
			cellObj = sheetRow.getCell(12);
			if (cellObj != null) {
				String cell = tools.getCellContent(cellObj);
				sda.setRequired(isNull(cell));
			}
			cellObj = sheetRow.getCell(13);
			if (cellObj != null) {
				String cell = tools.getCellContent(cellObj);
				String remark = isNull(cell);
				if("start".equalsIgnoreCase(remark)) {
					sda.setMetadataId("");
				}
				sda.setRemark(remark);
			}

			if(ida.getMetadataId()!=null&&!"".equals(ida.getMetadataId()) && !"start".equalsIgnoreCase(sda.getRemark()) && !"end".equalsIgnoreCase(sda.getRemark())){
				Metadata metadata = metadataService.findUniqueBy("metadataId", sda.getMetadataId());
				if(metadata==null){
					logger.error(sheet.getSheetName()+"页,元数据["+ida.getMetadataId()+"]未配置，导入失败...");
					msg.append(ida.getMetadataId()).append(",");
					flag = false;
				}
			}
			sda.setSeq(order);

			idas.add(ida);
			sdas.add(sda);
			order++;

		}

		if(!flag){
			logInfoService.saveLog(sheet.getSheetName()+"页,元数据["+msg.toString()+"]未配置，导入失败...","导入(输入)");
			return null;
		}
		Map<String, Object> resMap = new HashMap<String, Object>();

		resMap.put("idas", idas);
		resMap.put("sdas", sdas);

		return resMap;
	}

	private Map<String, Object> getOutputArg(Sheet sheet) {
		boolean flag = true;
		StringBuffer msg = new StringBuffer();
		List<Ida> idas = new ArrayList<Ida>();
		List<SDA> sdas = new ArrayList<SDA>();
		ExcelTool tools = ExcelTool.getInstance();
		int start = readline;
		int end = sheet.getLastRowNum();
		int order = 0;
		for (int j = start; j <= end; j++) {
			Ida ida = new Ida();
			SDA sda = new SDA();
			Row sheetRow = sheet.getRow(j);

			Cell cellObj = sheetRow.getCell(0);

			if (cellObj != null) {
				String cell = tools.getCellContent(cellObj);
				if ("输出".equals(cell)) {
					continue;
				}
				ida.setStructName(isNull(cell));
			}
			cellObj = sheetRow.getCell(1);
			if (cellObj != null) {
				String cell = tools.getCellContent(cellObj);
				ida.setStructAlias(isNull(cell));
			}
			cellObj = sheetRow.getCell(2);
			if (cellObj != null) {
				String cell = tools.getCellContent(cellObj);
				ida.setType(isNull(cell));
			}

			cellObj = sheetRow.getCell(3);
			if (cellObj != null) {
				String cell = tools.getCellContent(cellObj);
				ida.setLength(isNull(cell));
			}

			cellObj = sheetRow.getCell(4);
			if (cellObj != null) {
				String cell = tools.getCellContent(cellObj);
				ida.setRequired(isNull(cell));
			}
			cellObj = sheetRow.getCell(5);
			if (cellObj != null) {
				String cell = tools.getCellContent(cellObj);
				String remark = isNull(cell);
				ida.setRemark(remark);
			}

			cellObj = sheetRow.getCell(7);
			if (cellObj != null) {
				String cell = tools.getCellContent(cellObj);
				if(!"start".equalsIgnoreCase(ida.getRemark())) {
					ida.setMetadataId(isNull(cell));

				}
				sda.setStructName(isNull(cell));
				sda.setMetadataId(isNull(cell));
			}
			ida.setSeq(order);

			cellObj = sheetRow.getCell(8);
			if (cellObj != null) {
				String cell = tools.getCellContent(cellObj);
				sda.setStructAlias(isNull(cell));
			}

			cellObj = sheetRow.getCell(9);
			if (cellObj != null) {
				String cell = tools.getCellContent(cellObj);
				sda.setType(isNull(cell));
			}

			cellObj = sheetRow.getCell(10);
			if (cellObj != null) {
				String cell = tools.getCellContent(cellObj);
				sda.setLength(isNull(cell));
			}
			cellObj = sheetRow.getCell(12);
			if (cellObj != null) {
				String cell = tools.getCellContent(cellObj);
				sda.setRequired(isNull(cell));
			}
			cellObj = sheetRow.getCell(13);
			if (cellObj != null) {
				String cell = tools.getCellContent(cellObj);
				String remark = isNull(cell);
				if("start".equalsIgnoreCase(cell)) {
					sda.setMetadataId("");
				}
				sda.setRemark(remark);
			}

			if(ida.getMetadataId()!=null&&!"".equals(ida.getMetadataId()) && !"start".equalsIgnoreCase(sda.getRemark()) && !"end".equalsIgnoreCase(sda.getRemark())){
				Metadata metadata = metadataService.findUniqueBy("metadataId", sda.getMetadataId());
				if(metadata==null){
					logger.error(sheet.getSheetName()+"页,元数据["+ida.getMetadataId()+"]未配置，导入失败...");
					msg.append(ida.getMetadataId()).append(",");
					flag = false;
				}
			}
//e

			sda.setSeq(order);

			idas.add(ida);
			sdas.add(sda);
			order++;

		}

		if(!flag){
			logInfoService.saveLog(sheet.getSheetName()+"页,元数据["+msg.toString()+"]未配置，导入失败...","导入(输出)");
			return null;
		}

		Map<String, Object> resMap = new HashMap<String, Object>();

		resMap.put("idas", idas);
		resMap.put("sdas", sdas);

		return resMap;
	}

	/**
	 * 获取交易、服务、场景信息
	 * @return
	 */
	private Map<String, Object> getInterfaceAndServiceInfo(Sheet tranSheet) {
		boolean flag = true;
		// 读取每个sheet页交易信息与服务信息
		int start = tranSheet.getFirstRowNum();
		int end = tranSheet.getLastRowNum();
		Interface inter = new Interface();
		Service service = new Service();
		Operation oper = new Operation();
		for (int j = start; j <= end; j++) {
			Row sheetRow = tranSheet.getRow(j);
			String tranCode = "";
			String tranName = "";
			String serviceName = "";
			String serviceId = "";
			String operId = "";
			String operName = "";
			String serviceDesc = "";
			String operDesc = "";
			int cellStart = sheetRow.getFirstCellNum();
			int cellEnd = sheetRow.getLastCellNum();

			for (int k = cellStart; k < cellEnd; k++) {

				Cell cellObj = sheetRow.getCell(k);
				if (cellObj != null) {

					String cell = ExcelTool.getInstance().getCellContent(
							cellObj);
					if ("交易码".equals(cell)) {
						tranCode = sheetRow.getCell(k + 1).getStringCellValue();
						if (tranCode == null || "".equals(tranCode)) {
							logger.error(tranSheet.getSheetName()
									+ "sheet页，交易码为空");
							logInfoService.saveLog(tranSheet.getSheetName()
									+ "sheet页，交易码为空","导入");
							flag = false;
						}
						inter.setEcode(tranCode);
					} else if ("服务名称".equals(cell)) {
						serviceName = sheetRow.getCell(k + 1)
								.getStringCellValue();
						if (serviceName == null || "".equals(serviceName)) {
							logger.error(tranSheet.getSheetName()
									+ "sheet页，服务名称为空");
							logInfoService.saveLog(tranSheet.getSheetName()
									+ "sheet页，服务名称为空","导入");
							flag = false;
						}

						try{
							String[] req = getContext(serviceName);
							serviceName = req[0];
							serviceId = req[1];
							service.setServiceName(serviceName);
							service.setServiceId(serviceId);
						}catch (Exception e){
							logger.error("服务名称格式不正确，格式应为为：服务名称(服务ID)");
							logInfoService.saveLog(tranSheet.getSheetName()
									+ "sheet页，服务名称格式不正确，格式应为为：服务名称(服务ID)","导入");
							flag = false;
						}

						break;
					} else if ("交易名称".equals(cell)) {
						tranName = sheetRow.getCell(k + 1).getStringCellValue();
						if (tranName == null || "".equals(tranName)) {
							logger.error(tranSheet.getSheetName()
									+ "sheet页，交易名称为空");
							logInfoService.saveLog(tranSheet.getSheetName()
									+ "sheet页，交易名称为空","导入");
							flag = false;
						}
						inter.setInterfaceName(tranName);
					} else if ("服务操作名称".equals(cell)) {
						operName = sheetRow.getCell(k + 1).getStringCellValue();
						if (operName == null || "".equals(operName)) {
							logger.error(tranSheet.getSheetName()
									+ "sheet页，服务操作名称为空");
							logInfoService.saveLog(tranSheet.getSheetName()
									+ "sheet页，服务操作名称为空","导入");
							flag = false;
						}

						try{
							String[] req = getContext(operName);
							operName = req[0];
							operId = req[1];
							oper.setOperationId(operId);
							oper.setOperationName(operName);
						}catch (Exception e){
							logger.error("服务操作名称格式不正确，格式应为为：服务操作名称(操作ID)");
							logInfoService.saveLog(tranSheet.getSheetName()
									+ "sheet页，服务操作名称格式不正确，格式应为为：服务操作名称(操作ID)","导入");
							flag = false;
						}
						break;
					} else if ("服务描述".equals(cell)) {
						serviceDesc = sheetRow.getCell(k + 1)
								.getStringCellValue();
						if (serviceDesc == null || "".equals(serviceDesc)) {
							logger.error(tranSheet.getSheetName()
									+ "sheet页，服务描述为空");
							logInfoService.saveLog(tranSheet.getSheetName()
									+ "sheet页，服务描述为空","导入");
							flag = false;
						}
						service.setDesc(serviceDesc);
						break;
					} else if ("服务操作描述".equals(cell)) {
						operDesc = sheetRow.getCell(k + 1).getStringCellValue();
						if (operDesc == null || "".equals(operDesc)) {
							logger.error(tranSheet.getSheetName()
									+ "sheet页，服务操作描述为空");
							logInfoService.saveLog(tranSheet.getSheetName()
									+ "sheet页，服务操作描述为空","导入");
							flag = false;
						}
						oper.setOperationDesc(operDesc);
						break;
					}

					else if ("原始接口".equals(cell)) {
						// 将表头跳过,获取接口字段信息
						readline = j += 3;
						break;
					}
				}
			}
		}

		//信息不正确返回空
		if(!flag){
			return null;
		}
		Map<String, Object> resMap = new HashMap<String, Object>();

		resMap.put("interface", inter);
		resMap.put("service", service);
		resMap.put("operation", oper);

		return resMap;
	}

	private String[] getContext(String text) {

		text = text.replaceAll("（", "(").replaceAll("）", ")");
		String[] str = text.split("[()]+");
		return str;
	}

	private String isNull(String text){
		if(text == null){
			return "";
		}
		text = text.replaceAll("（","(").replaceAll("）", ")");
		return text;
	}
}

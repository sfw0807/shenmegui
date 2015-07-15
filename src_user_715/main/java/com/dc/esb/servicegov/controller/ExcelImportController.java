package com.dc.esb.servicegov.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.dc.esb.servicegov.util.GlobalImport;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dc.esb.servicegov.entity.Ida;
import com.dc.esb.servicegov.entity.Interface;
import com.dc.esb.servicegov.entity.Operation;
import com.dc.esb.servicegov.entity.SDA;
import com.dc.esb.servicegov.entity.Service;
import com.dc.esb.servicegov.service.ExcelImportService;
import com.dc.esb.servicegov.util.ExcelTool;

@Controller
@RequestMapping("/excelHelper")
public class ExcelImportController {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	ExcelImportService excelImportService ;
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
		List<Object> list = new LinkedList<Object>();

		Workbook workbook = null;
		String extensionName = FilenameUtils.getExtension(file
				.getOriginalFilename());

		InputStream is;
		try {
			java.io.PrintWriter writer = response.getWriter();
			is = file.getInputStream();
			if (extensionName.toLowerCase().equals(XLS)) {
				workbook = new HSSFWorkbook(is);
			} else if (extensionName.toLowerCase().equals(XLSX)) {
				workbook = new XSSFWorkbook(is);
			}

			// 读取交易索引Sheet页
			Sheet indexSheet = workbook.getSheet("INDEX");
			if (indexSheet == null) {

				logger.error("缺少INDEX sheet页");
				return;
			}

			int endRow = indexSheet.getLastRowNum();
			// 从第一行开始读，获取所有交易行
			for (int i = 1; i <= endRow; i++) {
				Row row = indexSheet.getRow(i);
				// 读取每一行第一列，获取每个交易sheet名称
				String sheetName = ExcelTool.getInstance().getCellContent(
						row.getCell(0));

				//接口提供方
				String providerSystem = ExcelTool.getInstance().getCellContent(
						row.getCell(5));
				//接口消费方
				String cusumerSystem = ExcelTool.getInstance().getCellContent(
						row.getCell(6));
				//判断系统是否存在
				boolean exists = excelImportService.existSystem(providerSystem,cusumerSystem);
				if(!exists){
					logger.error("交易["+sheetName+"],接口提供方或消费方系统不存在");
					continue;
				}
				if (sheetName != null && !"".equals(sheetName)) {
					// 读取每个交易sheet页
					logger.debug("开始获取" + sheetName
							+ "交易信息=========================");

					Sheet sheet = workbook.getSheet(sheetName);

					//获取交易、服务、场景信息
					Map<String, Object> infoMap = getInterfaceAndServiceInfo(sheet);
					list.add(infoMap);

					//获取接口、服务 输入 参数
					Map<String, Object> inputMap = getInputArg(sheet);

					//获取接口、服务 输出 参数
					Map<String, Object> outMap = getOutputArg(sheet);

					Map<String,String> publicMap = new HashMap<String, String>();
					publicMap.put("providerSystem",providerSystem);
					publicMap.put("cusumerSystem",cusumerSystem);

					logger.info("===========交易[" + sheetName+"],开始导入字段映射信息=============");
					long time = System.currentTimeMillis();
					boolean result = excelImportService.executeImport(infoMap, inputMap, outMap,publicMap);
					if(!result){
						logger.info("===========交易[" + sheetName+"],导入失败=============");
						continue;
					}
					long useTime = System.currentTimeMillis() - time;
					logger.info("===========交易[" + sheetName+"],导入完成，耗时"+useTime+"ms=============");
					writer.println("<script language=\"javascript\">");
					if (true) {
						writer.println("alert('导入成功!');");
					} else {
						writer.println("alert('导入失败，请查看日志!');");
					}

				} else {
					logger.error("第" + i + "行，交易代码为空。");
				}

			}

		} catch (IOException e) {
			logger.error("导入出现异常:异常信息："+e.getMessage());
		}
	}

	private Map<String, Object> getInputArg(Sheet sheet) {
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

			cellObj = sheetRow.getCell(7);
			if (cellObj != null) {
				String cell = tools.getCellContent(cellObj);
				ida.setMetadataId(isNull(cell));

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
				if("".equalsIgnoreCase("start")){

				}

				sda.setRemark(remark);
			}
			sda.setSeq(order);

			idas.add(ida);
			sdas.add(sda);
			order++;

		}

		Map<String, Object> resMap = new HashMap<String, Object>();

		resMap.put("idas", idas);
		resMap.put("sdas", sdas);

		return resMap;
	}

	private Map<String, Object> getOutputArg(Sheet sheet) {
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

			cellObj = sheetRow.getCell(7);
			if (cellObj != null) {
				String cell = tools.getCellContent(cellObj);
				ida.setMetadataId(isNull(cell));
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
				sda.setRemark(isNull(cell));
			}
			sda.setSeq(order);

			idas.add(ida);
			sdas.add(sda);
			order++;

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
						}
						inter.setEcode(tranCode);
					} else if ("服务名称".equals(cell)) {
						serviceName = sheetRow.getCell(k + 1)
								.getStringCellValue();
						if (serviceName == null || "".equals(serviceName)) {
							logger.error(tranSheet.getSheetName()
									+ "sheet页，服务名称为空");
						}

						String[] req = getContext(serviceName);
						serviceName = req[0];
						serviceId = req[1];
						service.setServiceName(serviceName);
						service.setServiceId(serviceId);
						break;
					} else if ("交易名称".equals(cell)) {
						tranName = sheetRow.getCell(k + 1).getStringCellValue();
						if (tranName == null || "".equals(tranName)) {
							logger.error(tranSheet.getSheetName()
									+ "sheet页，交易名称为空");
						}
						inter.setInterfaceName(tranName);
					} else if ("服务操作名称".equals(cell)) {
						operName = sheetRow.getCell(k + 1).getStringCellValue();
						if (operName == null || "".equals(operName)) {
							logger.error(tranSheet.getSheetName()
									+ "sheet页，服务操作名称为空");
						}
						String[] req = getContext(operName);
						operId = req[0];
						operName = req[1];
						oper.setOperationId(operId);
						oper.setOperationName(operName);
						break;
					} else if ("服务描述".equals(cell)) {
						serviceDesc = sheetRow.getCell(k + 1)
								.getStringCellValue();
						if (serviceDesc == null || "".equals(serviceDesc)) {
							logger.error(tranSheet.getSheetName()
									+ "sheet页，服务描述为空");
						}
						service.setDesc(serviceDesc);
						break;
					} else if ("服务操作描述".equals(cell)) {
						operDesc = sheetRow.getCell(k + 1).getStringCellValue();
						if (operDesc == null || "".equals(operDesc)) {
							logger.error(tranSheet.getSheetName()
									+ "sheet页，服务操作描述为空");
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

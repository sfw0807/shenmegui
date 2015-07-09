package com.dc.esb.servicegov.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dc.esb.servicegov.dao.support.Page;
import com.dc.esb.servicegov.dao.support.SearchCondition;
import com.dc.esb.servicegov.entity.EnumElementMap;
import com.dc.esb.servicegov.entity.EnumElements;
import com.dc.esb.servicegov.entity.MasterSlaveEnumMap;
import com.dc.esb.servicegov.entity.SGEnum;
import com.dc.esb.servicegov.service.EnumService;
import com.dc.esb.servicegov.util.DateUtils;

@Controller
@RequestMapping("/enum")
public class EnumController {
	
	@Autowired
	private EnumService enumService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/addEnum", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean addEnum(@RequestBody SGEnum anEnum) {
		anEnum.setOptDate(DateUtils.format(new Date()));
		System.out.println(anEnum.getOptDate());
		enumService.insertEnum(anEnum);
		return true;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/addSlaveEnum/{masterId}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean addSlaveEnum(@RequestBody SGEnum anEnum,@PathVariable(value = "masterId") String masterId) {
		anEnum.setOptDate(DateUtils.format(new Date()));
		enumService.insertEnum(anEnum);
		String slaveId = anEnum.getId();
		MasterSlaveEnumMap mapping = new MasterSlaveEnumMap();
		mapping.setMasterId(masterId);
		mapping.setSlaveId(slaveId);
		enumService.addMasterSlaveEnumMap(mapping);
		return true;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/searchEnum", headers = "Accept=application/json")
    public
    @ResponseBody
    List<SGEnum> searchEnum(@RequestBody HashMap<String, String> map) {
		List<SGEnum> list = enumService.getEnumByParams(map);
		return list;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/getAll", headers = "Accept=application/json")
	public
	@ResponseBody
	HashMap<String, Object> getAll(HttpServletRequest req){
		int pageNum = Integer.parseInt(req.getParameter("page"));
		int rows = Integer.parseInt(req.getParameter("rows"));
		String name = req.getParameter("name");
		String isStandard = req.getParameter("isStandard");
		String isMaster = req.getParameter("isMaster");
		String dataSource = req.getParameter("dataSource");
		String status = req.getParameter("status");
		String version = req.getParameter("version");
		String remark = req.getParameter("remark");
		
		StringBuffer hql = new StringBuffer("select sge from SGEnum sge");
		hql.append(" where 1=1 and isMaster='1'");
		List<SearchCondition> searchConds =new ArrayList<SearchCondition>();
		if(name!=null && !name.equals("")){
			hql.append(" and name like ?");
			searchConds.add(new SearchCondition("name", "%"+name+"%"));
		}
		if(isStandard!=null && !isStandard.equals("")){
			hql.append(" and isStandard like ?");
			searchConds.add(new SearchCondition("isStandard", "%"+isStandard+"%"));
		}
		if(isMaster!=null && !isMaster.equals("")){
			hql.append(" and isMaster like ?");
			searchConds.add(new SearchCondition("isMaster", "%"+isMaster+"%"));
		}
		if(dataSource!=null && !dataSource.equals("")){
			hql.append(" and dataSource like ?");
			searchConds.add(new SearchCondition("dataSource", "%"+dataSource+"%"));
		}
		if(status!=null && !status.equals("")){
			hql.append(" and status like ?");
			searchConds.add(new SearchCondition("status", "%"+status+"%"));
		}
		if(version!=null && !version.equals("")){
			hql.append(" and version like ?");
			searchConds.add(new SearchCondition("version", "%"+version+"%"));
		}
		if(remark!=null && !remark.equals("")){
			hql.append(" and remark like ?");
			searchConds.add(new SearchCondition("remark", "%"+remark+"%"));
		}
		return enumService.getAllEnum(hql.toString(),searchConds,pageNum,rows);
		
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getMasterElements/{masterId}", headers = "Accept=application/json")
	public
	@ResponseBody
	List<EnumElements> getMasterElements(@PathVariable(value="masterId") String masterId){
		return enumService.getElementsByEnumId(masterId);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getByEnumId/{id}/{isMaster}/{aSwitch}", headers = "Accept=application/json")
	public
	@ResponseBody
	ModelAndView getByEnumId(@PathVariable(value = "id") String id,@PathVariable(value = "isMaster") String isMaster
			,@PathVariable(value = "aSwitch") String aSwitch){
		SGEnum master = enumService.getByEnumId(id);
		List<SGEnum> slave = enumService.getSlaveByEnumId(id);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("master", master);
		modelAndView.addObject("slave", slave);
		//公共代码
		if(aSwitch.equals("1")){
			modelAndView.setViewName("SGEnum/maintainGGEnum");
		}else if(isMaster.equals("1")){
			modelAndView.setViewName("SGEnum/maintainEnum");
		}else if(isMaster.equals("0")){
			modelAndView.setViewName("SGEnum/maintainSlaveEnum");
		}
		return modelAndView;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getElementMapping/{masterId}/{slaveId}", headers = "Accept=application/json")
	public
	@ResponseBody
	List getElementMapping(@PathVariable(value = "masterId") String masterId,@PathVariable(value = "slaveId") String slaveId){
		StringBuffer sql = new StringBuffer();
				
		sql.append("SELECT m.element_name as masterName,s.element_name slaveName,m.element_id as masterId,s.element_id as slaveId");
		sql.append(",mp.direction as direction,mp.mapping_relation as mappingRelation");
		sql.append(" FROM ENUM_ELEMENTS m left join");
		sql.append(" (select * from ENUM_ELEMENT_MAP t1 where t1.slave_element_id");
		sql.append(" in(select t2.element_id from ENUM_ELEMENTS t2 where t2.enum_id='"+slaveId+"')) mp");
		sql.append(" ON m.element_id = mp.master_element_id");
		sql.append(" left join ENUM_ELEMENTS s");
		sql.append(" ON mp.slave_element_id = s.element_id");
		sql.append(" WHERE m.enum_id='"+masterId+"'");
		
		List list = enumService.getElementsMapping(sql);
		return list;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getMappingBySlaveId/{id}", headers = "Accept=application/json")
	public
	@ResponseBody
	ModelAndView getMappingBySlaveId(@PathVariable(value = "id") String id){
		SGEnum slave = enumService.getByEnumId(id);
		SGEnum master = enumService.getMasterBySlaveId(slave.getId());
		List<EnumElements> masterElements = enumService.getElementsByEnumId(master.getId());
		List<EnumElements> slaveElements = enumService.getElementsByEnumId(slave.getId());
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT m.element_name as masterName,s.element_name slaveName");
		sql.append(" FROM ENUM_ELEMENTS m left join ENUM_ELEMENT_MAP mp");
		sql.append(" ON m.element_id = mp.master_element_id");
		sql.append(" left join ENUM_ELEMENTS s");
		sql.append(" ON mp.slave_element_id = s.element_id");
		sql.append(" WHERE m.enum_id='"+master.getId().toString()+"'");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("master", master);
		modelAndView.addObject("slave", slave);
		modelAndView.addObject("masterElements", masterElements);
		modelAndView.addObject("slaveElements", slaveElements);
//		modelAndView.addObject("mappingData", hashMap);
		modelAndView.setViewName("SGEnum/elementMapping");
		return modelAndView;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getSlaveByMasterId/{id}", headers = "Accept=application/json")
	public
	@ResponseBody
	List<SGEnum> getSlaveByMasterId(@PathVariable(value = "id") String id){
		List<SGEnum> slave = enumService.getSlaveByEnumId(id);
		return slave;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getElementByMasterId/{id}", headers = "Accept=application/json")
	public
	@ResponseBody
	List<EnumElements> getElementByMasterId(@PathVariable(value = "id") String id){
		List<EnumElements> EnumElements = enumService.getElementsByEnumId(id);
		return EnumElements;
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteEnum/{id}", headers = "Accept=application/json")
	public
	@ResponseBody
	boolean deleteEnumById(@PathVariable(value = "id") String id){
		return enumService.deleteEnumById(id);
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/saveEnum", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean saveEnum(@RequestBody SGEnum anEnum) {
		return enumService.updateEnum(anEnum);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/setElementMapping", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean setElementMapping(@RequestBody EnumElementMap elementMap) {
		enumService.addEnumElementMap(elementMap);
		return true;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/saveElementMapping", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean saveElementMapping(@RequestBody List list) {
		for (int i = 0; i < list.size(); i++) {
			LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) list.get(i);
			Set<String> keySet = map.keySet();
			EnumElementMap elementMap = new EnumElementMap();
			elementMap.setDirection(map.get("DIRECTION"));
			elementMap.setMappingRelation(map.get("MAPPINGRELATION"));
			elementMap.setMasterElementId(map.get("MASTERID"));
			//因为前端combobox值，所以SLAVENAME
			elementMap.setSlaveElementId(map.get("SLAVENAME"));
			if(map.get("SLAVEID")!=null){
				//删掉旧mapping
				enumService.deleteElementsMappingByPK(map.get("MASTERID"),map.get("SLAVEID"));
			}
			enumService.addEnumElementMap(elementMap);
		}
		return true;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/addElement", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean addElement(@RequestBody EnumElements elements) {
		elements.setOptDate(DateUtils.format(new Date()));
		return enumService.addElement(elements);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteEnumElements", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean deleteEnumElements(@RequestBody List list) {
		List<String> ids = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) list.get(i);
			ids.add(map.get("elementId"));
		}
		enumService.deleteEnumElementsByIds(ids);
		return true;
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteElementsMapping", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean deleteElementsMapping(@RequestBody List list) {
		List<String> ids = new ArrayList<String>();
		List<EnumElementMap> mappingList = new ArrayList<EnumElementMap>();
		for (int i = 0; i < list.size(); i++) {
			LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) list.get(i);
			EnumElementMap elementMap = new EnumElementMap();
			elementMap.setMasterElementId(map.get("MASTERID"));
			elementMap.setSlaveElementId(map.get("SLAVEID"));
			mappingList.add(elementMap);
		}
		enumService.deleteElementsMapping(mappingList);
		return true;
	}
	
}

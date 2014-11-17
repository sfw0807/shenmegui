package com.dc.esb.servicegov.refactoring.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dc.esb.servicegov.refactoring.entity.Function;
import com.dc.esb.servicegov.refactoring.entity.Role;
import com.dc.esb.servicegov.refactoring.service.FunctionManager;
import com.dc.esb.servicegov.refactoring.service.RoleFunctionRelateManager;
import com.dc.esb.servicegov.refactoring.vo.FunctionTreeVO;
import com.dc.esb.servicegov.refactoring.vo.FunctionVO;

@Controller
@RequestMapping("/function")
public class FunctionController {
	private Log log = LogFactory.getLog(FunctionController.class);
	@Autowired
	private FunctionManager functionManager;
	@Autowired
	private RoleFunctionRelateManager rolefuncrelatManager;
	@RequestMapping(method = RequestMethod.GET, value = "/list", headers = "Accept=application/json")
	public @ResponseBody List<FunctionVO> getAll() {
		List<Function> functionList = functionManager.getAll();
		List<FunctionVO> functionVOList = new ArrayList<FunctionVO>();
		for (Function function:functionList) {
			FunctionVO functionVO = new FunctionVO();
			functionVO.setId(function.getId()+"");
			functionVO.setName(function.getName());
			functionVO.setRemark(function.getRemark());
			functionVO.setParent(functionManager.getFunctionNameById(function.getParentId()));
			functionVO.setUrl(function.getUrl());
			functionVOList.add(functionVO);
		}
		return functionVOList;
	}
	@RequestMapping(method = RequestMethod.GET, value = "/listtreevo/{roleId}", headers = "Accept=application/json")
	public @ResponseBody List<FunctionTreeVO> getAllFunction(@PathVariable String roleId) {
		List<Function> funcListByRole = rolefuncrelatManager.getFunctionByRole(roleId);
		List<Function> functionList = functionManager.getAll();
		List<FunctionTreeVO> funcTreeVOList = new ArrayList<FunctionTreeVO>();
		for(Function function:functionList){
			FunctionTreeVO functionTreeVO = new FunctionTreeVO();
			functionTreeVO.setId(function.getId()+"");
			functionTreeVO.setName(function.getName());
			functionTreeVO.setUrl(function.getUrl());
			functionTreeVO.setParentId(function.getParentId());
			functionTreeVO.setRemark(function.getRemark());
			functionTreeVO.setIsChecked(funcListByRole.contains(function));
			funcTreeVOList.add(functionTreeVO);
		}
		return funcTreeVOList;
	}
	@RequestMapping(method = RequestMethod.POST, value = "/add", headers = "Accept=application/json")
	public @ResponseBody boolean saveFunction(@RequestBody String[] param) {
		Function function = new Function();
		function.setId(functionManager.getMaxId()+1);
		function.setName(param[0]);
		function.setParentId(param[2]);
		function.setUrl(param[1]);
		function.setRemark(param[3]);
		return functionManager.save(function);
	}
	@RequestMapping(method = RequestMethod.GET, value = "/delete/{functionId}", headers = "Accept=application/json")
	public @ResponseBody boolean deleteFunction(@PathVariable String functionId) {
		Function function = functionManager.getFunctionById(functionId);
		return functionManager.delete(function);
	}
}

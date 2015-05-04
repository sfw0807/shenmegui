package com.dc.esb.servicegov.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.dao.impl.FunctionDAOImpl;
import com.dc.esb.servicegov.dao.impl.RoleFunctionRelateDAOImpl;
import com.dc.esb.servicegov.entity.Function;
import com.dc.esb.servicegov.entity.RoleFunctionRelate;
import com.dc.esb.servicegov.service.RoleFunctionRelateManager;

@Service
@Transactional
public class RoleFunctionRelateManagerImpl implements RoleFunctionRelateManager{
	private Log log = LogFactory.getLog(RoleFunctionRelateManagerImpl.class);
	@Autowired
	private RoleFunctionRelateDAOImpl roleFunctionRelateDAO;
	@Autowired
	private FunctionDAOImpl functionDAO;
	public List<Function> getFunctionByRole(String roleId) {
		List<Function> functionList = new ArrayList<Function>();
		List<RoleFunctionRelate> relateList = roleFunctionRelateDAO.findBy("roleId",  Integer.parseInt(roleId));
		for(RoleFunctionRelate roleFunctionRelate:relateList){
			Function function = functionDAO.findUniqueBy("id", roleFunctionRelate.getFunctionId());
			functionList.add(function);
		}
		return functionList;
	}
	
}

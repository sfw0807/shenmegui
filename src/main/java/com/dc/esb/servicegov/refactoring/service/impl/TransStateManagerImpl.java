package com.dc.esb.servicegov.refactoring.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.refactoring.dao.impl.TransStateDAOImpl;
import com.dc.esb.servicegov.refactoring.entity.TransState;
import com.dc.esb.servicegov.refactoring.service.TransStateManager;

@Component
@Transactional(rollbackFor = Exception.class)
public class TransStateManagerImpl implements TransStateManager {	
	private Log log = LogFactory.getLog(getClass());
	@Autowired
	private TransStateDAOImpl transStateDAOImpl;
	@Override
	public boolean updateState(int id,String state) {
		boolean flag = false;
		try{
			Map<String,String> paramMap = new HashMap<String,String>();
			paramMap.put("id", id+"");
			TransState transState = transStateDAOImpl.findUniqueBy("id",id);
			transState.setVersionState(state);	
			flag = true;
		}catch(Exception e){
			log.error(e, e);
		}
		return flag;
	}
	
}

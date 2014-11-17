package com.dc.esb.servicegov.refactoring.dao.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dc.esb.servicegov.dao.impl.HibernateDAO;
import com.dc.esb.servicegov.refactoring.entity.DefaultInteface;

@Repository
public class DefaultIntefaceDAOImpl extends HibernateDAO<DefaultInteface, String> {

	public List<DefaultInteface> getDefaultInterfaceByServiceId(String serviceId,String type){
		    Map<String,String> params = new HashMap<String,String>();
		    params.put("serviceId", serviceId);
		    params.put("interfaceType", type);
	        return this.findBy(params);
	}
}

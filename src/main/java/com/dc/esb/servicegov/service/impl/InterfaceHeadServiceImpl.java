package com.dc.esb.servicegov.service.impl;

import java.util.List;

import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.entity.Ida;
import com.dc.esb.servicegov.service.IdaService;
import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.dao.impl.InterfaceHeadDAOImpl;
import com.dc.esb.servicegov.entity.InterfaceHead;
import com.dc.esb.servicegov.service.InterfaceHeadService;
@Service
@Transactional(propagation=Propagation.REQUIRED , rollbackFor = Exception.class)
public class InterfaceHeadServiceImpl extends AbstractBaseService<InterfaceHead, String> implements InterfaceHeadService{

	@Autowired 
	private InterfaceHeadDAOImpl interfaceHeadDAO;
	@Autowired
	private IdaService idaService;
	
	@Override
	public HibernateDAO<InterfaceHead, String> getDAO() {
		return interfaceHeadDAO;
	}

	@Override
	public void initHDA(InterfaceHead interfaceHead){
		Ida ida = new Ida();
		ida.setHeadId(interfaceHead.getHeadId());
		ida.set_parentId(null);
		ida.setStructName("root");
		ida.setStructAlias("根节点");
		idaService.save(ida);
		String parentId = ida.getId();

		ida = new Ida();
		ida.setHeadId(interfaceHead.getHeadId());
		ida.set_parentId(parentId);
		ida.setStructName("request");
		ida.setStructAlias("请求头");
		idaService.save(ida);

		ida = new Ida();
		ida.setHeadId(interfaceHead.getHeadId());
		ida.set_parentId(parentId);
		ida.setStructName("response");
		ida.setStructAlias("响应头");
		idaService.save(ida);
	}

}

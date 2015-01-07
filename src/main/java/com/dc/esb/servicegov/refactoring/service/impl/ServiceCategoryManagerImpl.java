package com.dc.esb.servicegov.refactoring.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.refactoring.dao.impl.ServiceCategoryDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.ServiceDAOImpl;
import com.dc.esb.servicegov.refactoring.entity.ServiceCategory;
import com.dc.esb.servicegov.refactoring.service.ServiceCategoryManager;
import com.dc.esb.servicegov.refactoring.vo.ServiceTotalVO;

@Service
@Transactional
public class ServiceCategoryManagerImpl implements ServiceCategoryManager {
	@SuppressWarnings("unused")
	private Log log = LogFactory.getLog(getClass());

	@Autowired
	private ServiceCategoryDAOImpl categoryDAO;
	@Autowired
	private ServiceDAOImpl serviceDAO;
	
	@Override
	public List<ServiceCategory> getAllCategoryInfo() {
		// TODO Auto-generated method stub
		
		return categoryDAO.getAllCategoryInfo();
	}
	@Override
	public void delServiceCategory(String id) {
		// TODO Auto-generated method stub
		categoryDAO.delete(id);
	}
	@Override
	public List<ServiceCategory> getFirstLevelInfo() {
		// TODO Auto-generated method stub
		return categoryDAO.getFirstLevelInfo();
	}
	@Override
	public List<ServiceCategory> getSecondLevelInfo() {
		// TODO Auto-generated method stub
		return categoryDAO.getSecondLevelInfo();
	}
	@Override
	public ServiceCategory getServiceCategoryById(String id) {
		// TODO Auto-generated method stub
		ServiceCategory serviceCategory = categoryDAO.findUniqueBy("categoryId", id);
		return serviceCategory;
	}
	@Override
	public void insertOrUpdate(ServiceCategory serviceCategory) {
		// TODO Auto-generated method stub
		categoryDAO.save(serviceCategory);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	/**
	 * 检查分组是否已经被使用
	 */
	public boolean checkIsUsed(String id) {
		// TODO Auto-generated method stub
		List list = serviceDAO.findBy("categoryId", id);
		if(list != null && list.size()>0){
			return true;
		}
		return false;
	}
	
	/**
	 * 检查是否是父分组
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean checkIsFather(String id) {
		// TODO Auto-generated method stub
		List list = categoryDAO.findBy("parentId", id);
		if(list !=null && list.size()>0){
			return true;
		}
		return false;
	}
	/**
	 * 获取所有服务分组统计信息
	 */
	@Override
	public List<ServiceTotalVO> getServiceTotalInfos() {
		// TODO Auto-generated method stub
		List<ServiceCategory> firstList = categoryDAO.getFirstLevelInfo();
		List<ServiceCategory> secondList = null;
		List<ServiceTotalVO> returnList = new ArrayList<ServiceTotalVO>();
		for(ServiceCategory scFirst: firstList){
			// 忽略MSMGroup分组
			if("MSMGroup".equals(scFirst.getParentId())){
				secondList = categoryDAO.getSecondCategoryByFirstId(scFirst.getCategoryId());
				for(ServiceCategory scSecond: secondList){
					ServiceTotalVO vo = new ServiceTotalVO();
					vo.setFirstCategory(scFirst.getCategoryName());
					vo.setSecondCategory(scSecond.getCategoryName());
					vo.setServiceCount(categoryDAO.countOfServiceBySecondCategoryId(scSecond.getCategoryId()));
					vo.setServiceTotal(categoryDAO.countOfServiceByFirstCategoryId(scFirst.getCategoryId()));;
					vo.setOperationCount(categoryDAO.countOfOperationBySecondCategoryId(scSecond.getCategoryId()));
					vo.setOperationTotal(categoryDAO.countOfOperationByFirstCategoryId(scFirst.getCategoryId()));
					returnList.add(vo);
				}
			}
		}
		return returnList;
	}
}

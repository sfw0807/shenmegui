package com.dc.esb.servicegov.refactoring.service;

import java.util.List;

import com.dc.esb.servicegov.refactoring.entity.ServiceCategory;
import com.dc.esb.servicegov.refactoring.vo.ServiceTotalVO;

public interface ServiceCategoryManager {
	
	public List<ServiceCategory> getAllCategoryInfo();
	public ServiceCategory getServiceCategoryById(String id);
	public void insertOrUpdate(ServiceCategory serviceCategory);
	public void delServiceCategory(String id);
	// 获取一级分组信息
	public List<ServiceCategory> getFirstLevelInfo();
	// 获取二级分组信息
	public List<ServiceCategory> getSecondLevelInfo();
	// 判断分组是否已被使用
	public boolean checkIsUsed(String id);
	// 判断该分组是否是父分组
	public boolean checkIsFather(String id);
	// 获取分组服务统计信息
	public List<ServiceTotalVO> getServiceTotalInfos();
}

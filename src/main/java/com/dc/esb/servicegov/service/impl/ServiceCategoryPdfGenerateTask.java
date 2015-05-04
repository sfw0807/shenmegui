package com.dc.esb.servicegov.service.impl;

import java.util.List;

import com.dc.esb.servicegov.entity.ServiceCategory;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-5-29
 * Time: 下午6:23
 */
public class ServiceCategoryPdfGenerateTask extends Thread {
	private ServiceCategoryManagerImpl serviceCategoryManager;
    private ServiceCategoryPdfGenerator serviceCategoryPdfGenerator;
    private String categoryId;
	public ServiceCategoryPdfGenerateTask(String categoryId,ServiceCategoryManagerImpl serviceCategoryManager,
			ServiceCategoryPdfGenerator serviceCategoryPdfGenerator) {
		this.categoryId = categoryId;
		this.serviceCategoryManager = serviceCategoryManager;
		this.serviceCategoryPdfGenerator = serviceCategoryPdfGenerator;
	}
	public void run() {
        List<ServiceCategory> serviceCategorys = serviceCategoryManager.getSubServiceCategoryByParentId(categoryId);
        try {
			serviceCategoryPdfGenerator.generate(serviceCategorys);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

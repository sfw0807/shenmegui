package com.dc.esb.servicegov.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dc.esb.servicegov.entity.Service;
import com.dc.esb.servicegov.entity.ServiceCategory;
import com.dc.esb.servicegov.service.impl.ServiceCategoryServiceImpl;
import com.dc.esb.servicegov.service.impl.ServiceServiceImpl;
import com.dc.esb.servicegov.vo.ServiceTreeViewBean;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/service")
public class ServiceController {
	
	@Autowired
	private ServiceServiceImpl serviceServiceImpl;
	@Autowired
	private ServiceCategoryServiceImpl serviceCategoryServiceImpl;
	
	@RequestMapping(method = RequestMethod.GET, value = "/getTree", headers = "Accept=application/json")
    public
    @ResponseBody
    List<ServiceTreeViewBean> getAll() {
		
		List<ServiceCategory> serviceCategoryList = serviceCategoryServiceImpl.getAll();
		List<Service> serviceList = serviceServiceImpl.getAll();
		List<ServiceTreeViewBean> treeBeans = new ArrayList<ServiceTreeViewBean>();
		//服务类
		List<ServiceTreeViewBean> parents = new ArrayList<ServiceTreeViewBean>();
		for (ServiceCategory serviceCategory : serviceCategoryList) {
			ServiceTreeViewBean treeViewBean = new ServiceTreeViewBean();
			treeViewBean.setId(serviceCategory.getCategoryId());
			treeViewBean.setText(serviceCategory.getCategoryName());
			treeViewBean.setType("serviceCategory");
			treeViewBean.setServiceCategory(serviceCategory);
			treeBeans.add(treeViewBean);
			//父节点
			if(serviceCategory.getParentId()==null){
				parents.add(treeViewBean);
			}
		}
		//服务
		for (Service service : serviceList) {
			for (ServiceTreeViewBean per : treeBeans) {
				if(per.getId().equals(service.getCategoryId())){
					ServiceTreeViewBean treeViewBean = new ServiceTreeViewBean();
					treeViewBean.setId(service.getServiceId());
					treeViewBean.setText(service.getServiceName());
					treeViewBean.setType("service");
					treeViewBean.setService(service);
					List<ServiceTreeViewBean> children = per.getChildren();
					children.add(treeViewBean);
					per.setChildren(children);
					
				}
			}
		}
		for (ServiceCategory serviceCategory : serviceCategoryList) {
			//子节点
			if(serviceCategory.getParentId()!=null){
				ServiceTreeViewBean treeViewBean = null;
				for (ServiceTreeViewBean per : treeBeans) {
					if(per.getId().equals(serviceCategory.getCategoryId())){
						treeViewBean = per;
					}
				}
				//找到父节点
				for (ServiceTreeViewBean serviceTreeViewBean : parents) {
					if(serviceTreeViewBean.getId().equals(serviceCategory.getParentId())){
						List<ServiceTreeViewBean> children = serviceTreeViewBean.getChildren();
						children.add(treeViewBean);
						serviceTreeViewBean.setChildren(children);
					}
				}
			}
		}
		return parents;
    }
	
	@RequestMapping(method = RequestMethod.POST, value = "/addService", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean addService(@RequestBody Service service) {
		serviceServiceImpl.save(service);
		return true;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/editService", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean editService(@RequestBody Service service) {
		serviceServiceImpl.save(service);
		return true;
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteService/{Id}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean deleteService(@PathVariable String Id) {
		serviceServiceImpl.deleteById(Id);
		return true;
    }
	
	@RequestMapping(method = RequestMethod.POST, value = "/addServiceCategory", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean addServiceCategory(@RequestBody ServiceCategory serviceCategory) {
		serviceCategoryServiceImpl.save(serviceCategory);
		return true;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/editServiceCategory", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean editService(@RequestBody ServiceCategory serviceCategory) {
		serviceCategoryServiceImpl.save(serviceCategory);
		return true;
	}

	/**
	 * 根据服务id跳转到服务基本信息页面
	 */
	@RequestMapping("/serviceGrid")
	public ModelAndView serviceGrid(String serviceId){
		ModelAndView mv = new ModelAndView("service/serviceGrid");
		Service entity = serviceServiceImpl.getUniqueByServiceId(serviceId);
		if(entity != null){
			mv.addObject("entity", entity);
		}
		return mv;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteServiceCategory/{Id}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean deleteServiceCategory(@PathVariable String Id) {
		serviceCategoryServiceImpl.deleteById(Id);
		return true;
    }
}

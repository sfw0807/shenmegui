package com.dc.esb.servicegov.controller;

import com.dc.esb.servicegov.entity.Service;
import com.dc.esb.servicegov.entity.ServiceCategory;
import com.dc.esb.servicegov.service.impl.ServiceCategoryServiceImpl;
import com.dc.esb.servicegov.service.impl.ServiceServiceImpl;
import com.dc.esb.servicegov.util.TreeNode;
import com.dc.esb.servicegov.vo.ServiceTreeViewBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/service")
public class ServiceController {

    private static final Log log = LogFactory.getLog(ServiceController.class);

    @Autowired
    private ServiceServiceImpl serviceServiceImpl;
    @Autowired
    private ServiceCategoryServiceImpl serviceCategoryServiceImpl;

    @RequiresPermissions({"service-get"})
    @RequestMapping(method = RequestMethod.GET, value = "/getTree", headers = "Accept=application/json")
    public
    @ResponseBody
    List<ServiceTreeViewBean> getAllTreeBean() {
        List<ServiceCategory> serviceCategoryList = serviceCategoryServiceImpl.getAll();
        List<Service> serviceList = serviceServiceImpl.getAll();
        return getTreeJson(serviceCategoryList, serviceList);
    }

    @RequiresPermissions({"service-add"})
    @RequestMapping(method = RequestMethod.POST, value = "/addService", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean addService(@RequestBody Service service) {
        serviceServiceImpl.save(service);
        return true;
    }

    @RequiresPermissions({"service-update"})
    @RequestMapping(method = RequestMethod.POST, value = "/editService", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean editService(@RequestBody Service service) {
        serviceServiceImpl.save(service);
        return true;
    }

    @RequiresPermissions({"service-delete"})
    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteService/{Id}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean deleteService(@PathVariable String Id) {
        serviceServiceImpl.deleteById(Id);
        return true;
    }

    @RequiresPermissions({"service-add"})
    @RequestMapping(method = RequestMethod.POST, value = "/addServiceCategory", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean addServiceCategory(@RequestBody ServiceCategory serviceCategory) {
        serviceCategoryServiceImpl.save(serviceCategory);
        return true;
    }

    @RequiresPermissions({"service-update"})
    @RequestMapping(method = RequestMethod.POST, value = "/editServiceCategory", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean editService(@RequestBody ServiceCategory serviceCategory) {
        serviceCategoryServiceImpl.save(serviceCategory);
        return true;
    }

    @RequiresPermissions({"service-delete"})
    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteServiceCategory/{Id}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean deleteServiceCategory(@PathVariable String Id) {
        serviceCategoryServiceImpl.deleteById(Id);
        return true;
    }

    @RequiresPermissions({"service-get"})
    @RequestMapping(method = RequestMethod.GET, value = "/searchService/{serviceName}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<ServiceTreeViewBean> searchService(@PathVariable String serviceName) {
        List<ServiceTreeViewBean> TreeViewBeans = new ArrayList<ServiceTreeViewBean>();
        Map<String, String> params = new HashMap<String, String>();
        params.put("serviceName", serviceName);
        List<Service> serviceList = serviceServiceImpl.findLike(params);
        List<String> categoryIdList = new ArrayList<String>();
        for (Service service : serviceList) {
            categoryIdList.add(service.getCategoryId());
        }
        if (categoryIdList.size() != 0) {
            List<ServiceCategory> serviceCategoryList = serviceCategoryServiceImpl.getCategoryHierarchyByLeafIds(categoryIdList);
            TreeViewBeans = getTreeJson(serviceCategoryList, serviceList);
        }
        return TreeViewBeans;

    }

    @RequiresPermissions({"service-get"})
    @RequestMapping(method = RequestMethod.GET, value = "/searchService/processId/{processId}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<ServiceTreeViewBean> searchServiceByProcessId(@PathVariable("processId") String processId) {
        List<ServiceTreeViewBean> TreeViewBeans = new ArrayList<ServiceTreeViewBean>();
        Map<String, String> params = new HashMap<String, String>();
        params.put("processId", processId);
        List<Service> serviceList = serviceServiceImpl.findLike(params);
        List<String> categoryIdList = new ArrayList<String>();
        for (Service service : serviceList) {
            categoryIdList.add(service.getCategoryId());
        }
        if (categoryIdList.size() != 0) {
            List<ServiceCategory> serviceCategoryList = serviceCategoryServiceImpl.getCategoryHierarchyByLeafIds(categoryIdList);
            TreeViewBeans = getTreeJson(serviceCategoryList, serviceList);
        }
        return TreeViewBeans;

    }

    private List<ServiceTreeViewBean> getTreeJson(List<ServiceCategory> serviceCategoryList, List<Service> serviceList) {
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
            if (serviceCategory.getParentId() == null) {
                parents.add(treeViewBean);
            }
        }
        //服务
        for (Service service : serviceList) {
            for (ServiceTreeViewBean per : treeBeans) {
                if (per.getId().equals(service.getCategoryId())) {
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
            if (serviceCategory.getParentId() != null) {
                ServiceTreeViewBean treeViewBean = null;
                for (ServiceTreeViewBean per : treeBeans) {
                    if (per.getId().equals(serviceCategory.getCategoryId())) {
                        treeViewBean = per;
                    }
                }
                //找到父节点
                for (ServiceTreeViewBean serviceTreeViewBean : parents) {
                    if (serviceTreeViewBean.getId().equals(serviceCategory.getParentId())) {
                        List<ServiceTreeViewBean> children = serviceTreeViewBean.getChildren();
                        children.add(treeViewBean);
                        serviceTreeViewBean.setChildren(children);
                    }
                }
            }
        }
        ServiceTreeViewBean root = new ServiceTreeViewBean();
        root.setId("root");
        root.setChildren(parents);
        root.setText("服务类");
        root.setState("close");
        root.setType("root");

        List<ServiceTreeViewBean> list = new ArrayList<ServiceTreeViewBean>();
        list.add(root);
        return list;
    }

    //根据服务id跳转到服务基本信息页面
    @RequiresPermissions({"service-get"})
    @RequestMapping("/serviceGrid")
    public ModelAndView serviceGrid(String serviceId) {
        ModelAndView mv = new ModelAndView("service/serviceGrid");
        Service entity = serviceServiceImpl.getUniqueByServiceId(serviceId);
        if (entity != null) {
            mv.addObject("entity", entity);
        }
        return mv;
    }

    @RequiresPermissions({"service-get"})
    @RequestMapping("/serviceAppandForm/process/{processId}")
    public ModelAndView serviceAppandForm(@PathVariable("processId") String processId) {
        ModelAndView mv = new ModelAndView("service/serviceGrid");
        mv.addObject(processId);
        return mv;
    }

    @RequiresPermissions({"service-get"})
    @RequestMapping("serviceTree")
    public List<TreeNode> serviceTree() {
        return serviceServiceImpl.genderServiceTree();
    }

    @RequiresPermissions({"service-get"})
    @RequestMapping(method = RequestMethod.GET, value = "/getAll", headers = "Accept=application/json")
    public
    @ResponseBody
    List<Service> getAll() {
        return serviceServiceImpl.getAll();
    }

    @RequiresPermissions({"service-get"})
    @RequestMapping("serviceCategorys")
    public
    @ResponseBody
    List<ServiceCategory> serviceCategorys() {
        String hql = " from " + ServiceCategory.class.getName() + " where parentId is null";
        List<ServiceCategory> list = serviceCategoryServiceImpl.find(hql);
        return list;
}

    @ExceptionHandler({UnauthenticatedException.class, UnauthorizedException.class})
    public String processUnauthorizedException() {
        return "403";
    }

}

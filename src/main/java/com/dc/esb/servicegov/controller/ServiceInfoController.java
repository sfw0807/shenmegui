package com.dc.esb.servicegov.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dc.esb.servicegov.entity.Operation;
import com.dc.esb.servicegov.entity.Service;
import com.dc.esb.servicegov.entity.ServiceCategory;
import com.dc.esb.servicegov.service.impl.ServiceCategoryManagerImpl;
import com.dc.esb.servicegov.service.impl.ServiceManagerImpl;
import com.dc.esb.servicegov.vo.ServiceNode;

@Controller
@RequestMapping("/serviceInfo")
public class ServiceInfoController {
    @SuppressWarnings("unused")
    private Log log = LogFactory.getLog(getClass());
    @Autowired
    private ServiceManagerImpl serviceManager;
    @Autowired
    private ServiceCategoryManagerImpl ServiceCategoryManager;

    @RequestMapping(method = RequestMethod.GET, value = "/list", headers = "Accept=application/json")
    public
    @ResponseBody
    List<Service> getAllServiceInfo() {

        return serviceManager.getAllServices();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/servicelist", headers = "Accept=application/json")
    public
    @ResponseBody
    List<Service> getAllServiceOrderbyServiceId() {
        return serviceManager.getAllServiceOrderByServiceId();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/auditList", headers = "Accept=application/json")
    public
    @ResponseBody
    List<Service> getAuditService() {

        return serviceManager.getAuditServices();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getServiceById/{id}", headers = "Accept=application/json")
    public
    @ResponseBody
    Service getServiceById
            (@PathVariable String id) {

        return serviceManager.getServiceById(id);
    }

    /**
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/insertOrUpdate", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean insertOrUpdate(HttpServletRequest request,
                           HttpServletResponse response, @RequestBody Service service) {
        boolean flag = false;
        try {
            String version = service.getVersion();
            service.setVersion(getNewVersion(version));
            service.setUpdateTime(getNowTime());

            flag = serviceManager.insertOrUpdateService(service);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    /***
     *
     *获取当前时间
     *
     */
    public String getNowTime()
    {

        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String updateTime=format.format(date);

        return updateTime;
    }


    /**
     * 版本号+1
     *
     */
    public String getNewVersion(String version)
    {
        String[] vs = version.split("\\.");
        String temp = "";
        for (int i = 0; i < vs.length; i++) {
            temp += vs[i];
        }

        int versionNum = Integer.parseInt(temp);
        versionNum++;

        String versionString = Integer.toString(versionNum);
        int versionLength = versionString.length();

        char[] aa= new char[versionString.length()];
        aa = versionString.toCharArray();

        String tempVersion = "";
        for(int i = 0; i < versionLength; i++)
        {
            if(i == (versionLength - 2))
            {
                tempVersion += "." + aa[i] + ".";
            }else
            {
                tempVersion += aa[i];
            }

        }


        return tempVersion;
    }


    /**
     * 根据ID删除服务
     *
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/delByServiceId/{id}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean delete(@PathVariable String id) {
        boolean flag = true;
        try {
            if (id.contains(",")) {
                String[] idArr = id.split(",");
                int i = 0;
                while (i < idArr.length) {
                    serviceManager.delServiceById(idArr[i]);
                    i++;
                }
            } else {
                serviceManager.delServiceById(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    /**
     * 根据ID获取所有审核通过的操作信息
     *
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getOperationsById/{id}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<Operation> getOperationsById(@PathVariable String id) {
        return serviceManager.getOperationsByServiceId(id);
    }

    /**
     * check service has operation?
     *
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/checkExistOperation/{id}", headers = "Accept=application/json")
    public
    @ResponseBody
    String checkExistOperation(@PathVariable String id) {
        String idArr[] = id.split(",");
        for (String serid : idArr) {
            List<Operation> list = serviceManager.getOperationsByServiceId(serid);
            if (list != null && list.size() > 0) {
                return serid;
            }
        }
        return null;
    }

    /**
     * 发布服务
     *
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/deploy/{id}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean deploy(@PathVariable String id) {
        boolean flag = true;
        try {
            if (id.contains(",")) {
                String[] idArr = id.split(",");
                int i = 0;
                while (i < idArr.length) {
                    serviceManager.deployService(idArr[i]);
                    i++;
                }
            } else {
                serviceManager.deployService(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    /**
     * 重定义服务
     *
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/redef/{id}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean redef(@PathVariable String id) {
        boolean flag = true;
        try {
            if (id.contains(",")) {
                String[] idArr = id.split(",");
                int i = 0;
                while (i < idArr.length) {
                    serviceManager.redefService(idArr[i]);
                    i++;
                }
            } else {
                serviceManager.redefService(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    /**
     * 上线服务
     *
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/publish/{id}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean publish(@PathVariable String id) {
        boolean flag = true;
        try {
            if (id.contains(",")) {
                String[] idArr = id.split(",");
                int i = 0;
                while (i < idArr.length) {
                    serviceManager.publishService(idArr[i]);
                    i++;
                }
            } else {
                serviceManager.publishService(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/AllService", headers = "Accept=application/json")
    public
    @ResponseBody
    List<ServiceNode> getAllServiceNode() {
        List<ServiceNode> serviceNodeList = new ArrayList<ServiceNode>();
        ServiceNode serviceNode = new ServiceNode();
        serviceNode.setNodeId("MSMGroup");
        serviceNode.setNodeName("金融服务库");
        serviceNode.setNodeValue("金融服务库");
        serviceNode.setParentNodeId("/");
        serviceNodeList.add(serviceNode);
        List<ServiceCategory> firstServiceCategoryList = ServiceCategoryManager.getFirstLevelInfo();
        for (ServiceCategory serviceCategory : firstServiceCategoryList) {
            if (!"MSMGroup".equals(serviceCategory.getCategoryId())) {
                ServiceNode firstNode = new ServiceNode();
                firstNode.setNodeId(serviceCategory.getCategoryId());
                firstNode.setNodeName(serviceCategory.getCategoryId());
                firstNode.setNodeValue(serviceCategory.getCategoryName());
                firstNode.setParentNodeId(serviceCategory.getParentId());
                serviceNodeList.add(firstNode);
            }
        }
        List<ServiceCategory> secondServiceCategoryList = ServiceCategoryManager.getSecondLevelInfo();
        for (ServiceCategory serviceCategory : secondServiceCategoryList) {
            ServiceNode secondNode = new ServiceNode();
            secondNode.setNodeId(serviceCategory.getCategoryId());
            secondNode.setNodeName(serviceCategory.getCategoryId());
            secondNode.setNodeValue(serviceCategory.getCategoryName());
            secondNode.setParentNodeId(serviceCategory.getParentId());
            serviceNodeList.add(secondNode);
            List<Service> servieList = serviceManager.getServicesByCategory(serviceCategory.getCategoryId());
            for (Service service : servieList) {
                ServiceNode thirdNode = new ServiceNode();
                thirdNode.setNodeId(service.getServiceId());
                thirdNode.setNodeName(service.getServiceId());
                thirdNode.setNodeValue(service.getServiceName());
                thirdNode.setParentNodeId(serviceCategory.getCategoryId());
                serviceNodeList.add(thirdNode);
            }
        }
        return serviceNodeList;
    }

    /**
     * 服务审核
     *
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/audit", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean audit(@RequestBody String[] params) {
        try {
            for (String param : params) {
                String[] arr = param.split(",");
                String serviceId = arr[0];
                String auditState = arr[1];
                serviceManager.auditService(serviceId, auditState);
            }
        } catch (Exception e) {
            log.error("审核服务出现错误!");
            return false;
        }
        return true;
    }

    /**
     * 提交审核
     *
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/submit", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean submit(@RequestBody String[] params) {
        try {
            for (String param : params) {
                serviceManager.submitService(param);
            }
        } catch (Exception e) {
            log.error("审核服务出现错误!");
            return false;
        }
        return true;
    }
}

package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.entity.Service;
import com.dc.esb.servicegov.entity.ServiceCategory;
import com.dc.esb.servicegov.exception.DataException;
import com.dc.esb.servicegov.vo.TreeNode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Vincent Fan
 * Date: 14-7-2
 * Time: 下午5:07
 */
@Component
public class TreeMenuManagerImpl {

    private static final Log log = LogFactory.getLog(TreeMenuManagerImpl.class);

    @Autowired
    private ServiceCategoryManagerImpl serviceCategoryManager;
    @Autowired
    private ServiceManagerImpl serviceManager;

    @Transactional
    public List<TreeNode> getMenuTreeNodes() throws DataException {
        List<TreeNode> menuTree =  new ArrayList<TreeNode>();
        List<ServiceCategory> categories = serviceCategoryManager.getTopCategories();
        for(ServiceCategory topCategory : categories){
            if(log.isDebugEnabled()){
                log.debug("add node of top category ["+topCategory.getGroupName()+"]");
            }
            String topCategoryName = topCategory.getGroupName();
            TreeNode treeNode = new TreeNode();
            treeNode.setTitle(topCategoryName);
            treeNode.setFolder("true");
            List<ServiceCategory> subCategories = serviceCategoryManager.getSubCategories(topCategory);
            if(null != subCategories && subCategories.size() > 0){
                List<TreeNode> subCategoryTreeNodes = new ArrayList<TreeNode>();
                for(ServiceCategory subCategory : subCategories){
                    if(log.isDebugEnabled()){
                        log.debug("add node of sub category ["+subCategory.getGroupName()+"]");
                    }
                    TreeNode subCategoryTreeNode = new TreeNode();
                    subCategoryTreeNode.setTitle(subCategory.getGroupName());
                    subCategoryTreeNode.setFolder("true");
                    List<Service> operations = serviceCategoryManager.getServicesByCategory(subCategory.getGroupId());
                    if(null != operations && operations.size() > 0){
                        List<Service> services = new ArrayList<Service>();
                        for(Service operation: operations){
                            List<Service> tmpservices = serviceManager.getServiceOfOperation(operation);
                            services.removeAll(tmpservices);
                            services.addAll(tmpservices);
                        }
                        List<TreeNode> serviceTreeNodes = new ArrayList<TreeNode>();
                        for(Service service : services){
                            if(log.isDebugEnabled()){
                                log.debug("add node of service ["+service.getServiceId()+"]");
                            }
                            TreeNode serviceTreeNode = new TreeNode();
                            String serviceId = service.getServiceId();
                            String serviceName = service.getServiceName();
                            String title = serviceId + serviceName;
                            serviceTreeNode.setTitle(title);
//                            List<Service> operationsOfService = serviceManager.getOpertions(serviceId);
//                            if(null != operationsOfService && operationsOfService.size() > 0){
//                                List<TreeNode> operationsTreeNodes = new ArrayList<TreeNode>();
//                                for(Service operationOfService : operationsOfService){
//                                    if(log.isDebugEnabled()){
//                                        log.debug("add node of operation ["+operationOfService.getServiceId()+"]");
//                                    }
//                                    String operationId = operationOfService.getServiceId();
//                                    String operationName = operationOfService.getServiceName();
//                                    String title = operationId + operationName;
//                                    TreeNode operationTreeNode = new TreeNode();
//                                    operationTreeNode.setTitle(title);
//                                    operationsTreeNodes.add(operationTreeNode);
//                                }
//                                serviceTreeNode.setChildren(operationsTreeNodes);
//                            }
                            serviceTreeNodes.add(serviceTreeNode);
                        }
                        subCategoryTreeNode.setChildren(serviceTreeNodes);
                    }
                    subCategoryTreeNodes.add(subCategoryTreeNode);
                }
                treeNode.setChildren(subCategoryTreeNodes);
            }
            menuTree.add(treeNode);
        }
        return menuTree;
    }
}

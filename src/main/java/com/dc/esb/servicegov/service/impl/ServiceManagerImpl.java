package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.dao.impl.*;
import com.dc.esb.servicegov.entity.*;
import com.dc.esb.servicegov.entity.System;
import com.dc.esb.servicegov.exception.DataException;
import com.dc.esb.servicegov.vo.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created with IntelliJ IDEA. User: Administrator Date: 14-5-27 Time: 上午11:23
 * @author Vincent Fan
 */
@org.springframework.stereotype.Service
@Transactional
public class ServiceManagerImpl {

    private static final Log log = LogFactory.getLog(ServiceManagerImpl.class);
    @Autowired
    private ServiceDAOImpl serviceDAO;
    @Autowired
    private SDANodeDAOImpl sdaNodeDAO;
    @Autowired
    private SDANode4IDAOImpl sdaNode4IDAO;
    @Autowired
    private SDANodePropertyDAOImpl sdaNodePropertyDAO;
    @Autowired
    private MetadataDAOImpl metadataDAO;
    @Autowired
    private MetadataAttributeDAOImpl metadataAttrDAO;
    @Autowired
    private ServiceExtendInfoDAOImpl serviceExtendInfoDAO;
    @Autowired
    private ServiceInvokeRelationDAOImpl serviceInvokeRelationDAO;
    @Autowired
    private SystemDAOImpl systemDAO;
    @Autowired
    private RemainingServiceDAOImpl remainingServiceDAO;
    @Autowired
    private InterfaceDAOImpl interfaceDAO;
    @Autowired
    private InterfaceExtendInfoDAO interfaceExtendDAO;
    @Autowired
    private ServiceSlaDAOImpl serviceSlaDAO;
    @Autowired
    private ServiceOlaDAOImpl serviceOlaDAO;
    @Autowired
    private RelationViewDAOImpl relationDAO;

    public List<RelationView> getRelationViewList() {
        return relationDAO.getAll();
    }

    public List<ServiceSLA> getServiceSlaById(String id) {
        return serviceSlaDAO.findBy("relationId", id);
    }

    public List<ServiceOLA> getServiceOlaById(String id) {
        return serviceOlaDAO.findBy("relationId", id);
    }

    public List<ServiceExtendInfo> getServiceExtendInfoByOperationId(String id) {
        return serviceExtendInfoDAO.findBy("relationId", id);
    }

    public boolean isConsumerSysLinked(String id) {
        Criterion[] criterions = new Criterion[3];
        criterions[0] = Restrictions.eq("consumerSystemAb", id);
        criterions[1] = Restrictions.ne("operationId", "");
        criterions[2] = Restrictions.ne("interfaceId", "");
        if (serviceInvokeRelationDAO.find(criterions).size() > 0)
            return true;
        else
            return false;
    }

    public boolean isProviderSysLinked(String id) {
        Criterion[] criterions = new Criterion[3];
        criterions[0] = Restrictions.eq("providerSystemId", id);
        criterions[1] = Restrictions.ne("operationId", "");
        criterions[2] = Restrictions.ne("interfaceId", "");
        if (serviceInvokeRelationDAO.find(criterions).size() > 0)
            return true;
        else
            return false;
    }

    /**
     * 得到Interface INFO
     *
     * @param interfaceId
     * @return
     */
    public InterfaceVo getInterfaceInfo(String interfaceId) {

        Interface inter = null;
        Criterion criterion = Restrictions.eq("interfaceId", interfaceId);
        inter = interfaceDAO.findUnique(criterion);
        InterfaceVo vo = new InterfaceVo(inter);
        ServiceInvokeRelation relation = serviceInvokeRelationDAO.findBy(
                "interfaceId", interfaceId).get(0);
        vo.setConsumerSys(relation.getConsumerSystemAb());
        vo.setProviderSys(relation.getProviderSystemId());
        return vo;
    }

    /**
     * @param relationId
     * @return
     */
    public List<InterfaceExtendInfo> getInterfaceExtendInfo(String relationId) {
        return interfaceExtendDAO.findBy("relationId", relationId);
    }

    /**
     * 得到所有系统
     *
     * @return
     */
    public List<System> getAllSys() {
        return systemDAO.getAll("systemId", true);
    }

    @Transactional
    public List<RelationVo> getInvokerRelationByInterfaceIds(
            String[] ids) {

        Criterion criterion = Restrictions.in("interfaceId", ids);
        List<ServiceInvokeRelation> list = serviceInvokeRelationDAO.find(criterion);
        List<RelationVo> listVO = new ArrayList<RelationVo>();

        Map<String, RelationVo> map = new HashMap<String, RelationVo>();
        Iterator<ServiceInvokeRelation> it = list.iterator();
        while (it.hasNext()) {
            ServiceInvokeRelation r = it.next();
            RelationVo vo = new RelationVo(r);
            String ecode = vo.getInterfaceId();
            if (map.containsKey(ecode)) {
                RelationVo o = map.get(ecode);
                o.setConsumerSystemAb(o.getConsumerSystemAb()
                        + "、" + vo.getConsumerSystemAb());
            } else {
                map.put(ecode, vo);
            }
        }
        for (Map.Entry<String, RelationVo> e : map.entrySet()) {
            listVO.add(e.getValue());
        }
        Iterator<RelationVo> ito = listVO.iterator();
        while (ito.hasNext()) {
            RelationVo r = ito.next();
            if (r.getPassbySys().equals("ZHIPP")) {
                r.setConsumerSystemAb("ZHIPP(" + r.getConsumerSystemAb() + ")");
            }
        }
        return listVO;
    }

    /**
     * @param id
     * @return
     */
    public List<RelationVo> getServiceInvokeRelationByProvider(
            String id) {
        List<ServiceInvokeRelation> list = new ArrayList<ServiceInvokeRelation>();
        List<RelationVo> listVO = new ArrayList<RelationVo>();

        Map<String, RelationVo> map = new HashMap<String, RelationVo>();
        list = serviceInvokeRelationDAO.findBy("providerSystemId", id);
        Iterator<ServiceInvokeRelation> it = list.iterator();
        while (it.hasNext()) {
            ServiceInvokeRelation r = it.next();
            RelationVo vo = new RelationVo(r);
            String ecode = vo.getInterfaceId();
            if (map.containsKey(ecode)) {
                RelationVo o = map.get(ecode);
                o.setConsumerSystemAb(o.getConsumerSystemAb()
                        + "、" + vo.getConsumerSystemAb());
            } else {
                map.put(ecode, vo);
            }
        }
        for (Map.Entry<String, RelationVo> e : map.entrySet()) {
            listVO.add(e.getValue());
        }
        Iterator<RelationVo> ito = listVO.iterator();
        while (ito.hasNext()) {
            RelationVo r = ito.next();
            if (r.getPassbySys().equals("ZHIPP")) {
                r.setConsumerSystemAb("ZHIPP(" + r.getConsumerSystemAb() + ")");
            }
        }
        return listVO;
    }

    /**
     * @param id
     * @return
     */
    public List<RelationVo> getServiceInvokeRelationByConsumer(
            String id) {
        List<ServiceInvokeRelation> list = new ArrayList<ServiceInvokeRelation>();
        List<RelationVo> listVO = new ArrayList<RelationVo>();
        Map<String, RelationVo> map = new HashMap<String, RelationVo>();
        list = serviceInvokeRelationDAO.findBy("consumerSystemAb", id);
        Iterator<ServiceInvokeRelation> it = list.iterator();
        while (it.hasNext()) {
            ServiceInvokeRelation r = it.next();
            RelationVo vo = new RelationVo(r);
            String ecode = vo.getInterfaceId();
            if (map.containsKey(ecode)) {
                RelationVo o = map.get(ecode);
                o.setConsumerSystemAb(o.getConsumerSystemAb()
                        + "、" + vo.getConsumerSystemAb());
            } else {
                map.put(ecode, vo);
            }
        }
        for (Map.Entry<String, RelationVo> e : map.entrySet()) {
            listVO.add(e.getValue());
        }
        Iterator<RelationVo> ito = listVO.iterator();
        while (ito.hasNext()) {
            RelationVo r = ito.next();
            if (r.getPassbySys().equals("ZHIPP")) {
                r.setConsumerSystemAb("ZHIPP(" + r.getConsumerSystemAb() + ")");
            }
        }
        return listVO;
    }

    @Transactional
    public List<Service> getServiceById(String serviceId) {
        List<Service> service = null;
        if (null != serviceId) {
            service = serviceDAO.findBy("serviceId", serviceId);
        }
        return service;
    }

    @Transactional
    public List<Service> getAllServices() {
        return serviceDAO.findBy("type", "服务");
    }

    @Transactional
    public List<Service> getAllOperation() {
        return serviceDAO.findBy("type", "场景");
    }

    @Transactional
    public List<ServiceInvokeVo> getServiceInvokeInfo(String serviceId) {
        List<ServiceInvokeVo> serviceInvokeVos = null;
        List<ServiceInvokeRelation> serviceInvokeRelations = serviceInvokeRelationDAO
                .findBy("serviceId", serviceId);
        if (null != serviceInvokeRelations) {
            serviceInvokeVos = new ArrayList<ServiceInvokeVo>();
            for (ServiceInvokeRelation serviceInvokeRelation : serviceInvokeRelations) {
                ServiceInvokeVo serviceInvokeVo = new ServiceInvokeVo(
                        serviceInvokeRelation);
                String serviceConsumerAb = serviceInvokeRelation
                        .getConsumerSystemAb();
                String serviceProviderId = serviceInvokeRelation
                        .getProviderSystemId();
                String servicePassBySysAb = serviceInvokeRelation
                        .getPassbySys();
                System serviceConsumer = systemDAO.findUniqueBy(
                        "systemAbbreviation", serviceConsumerAb);
                System serviceProvider = systemDAO.findUniqueBy("systemId",
                        serviceProviderId);
                System servicePassBySys = systemDAO.findUniqueBy(
                        "systemAbbreviation", servicePassBySysAb);
                String serviceConsumerName = serviceConsumer != null ? serviceConsumer
                        .getSystemName()
                        : null;
                String serviceProviderName = serviceProvider != null ? serviceProvider
                        .getSystemName()
                        : null;
                String servicePassBySysName = servicePassBySys != null ? servicePassBySys
                        .getSystemName()
                        : null;
                serviceInvokeVo.setConsumerName(serviceConsumerName);
                serviceInvokeVo.setProviderName(serviceProviderName);
                serviceInvokeVo.setPassBySysName(servicePassBySysName);
                serviceInvokeVos.add(serviceInvokeVo);
            }
        }
        return serviceInvokeVos;
    }

    @Transactional
    public SDA getSDAofService(Service service) throws DataException {
        SDA root = null;
        if (null != service) {
            Map<String, String> params = new HashMap<String, String>();
            params.put("serviceId", service.getServiceId().trim());
            List<SDANode> nodes = sdaNodeDAO.findBy(params, "structIndex");
            // 获取node节点的属性
            Map<String, SDA> sdaMap = new HashMap<String, SDA>(nodes.size());
            String tmpPath = "/";
            for (SDANode sdaNode : nodes) {
                SDA sda = new SDA();
                // List<SDANodeProperty> nodeProperties =
                // sdaNodePropertyDAO.findBy("structId",
                // sdaNode.getResourceId());
                sda.setValue(sdaNode);
                // sda.setProperties(nodeProperties);
                sdaMap.put(sdaNode.getResourceId(), sda);
                String parentResourceId = sdaNode.getParentResourceId();
                if ("/".equalsIgnoreCase(parentResourceId)) {
                    root = sda;
                    sda.setXpath("/");
                }
                String metadataId = sda.getValue().getMetadataId();
                String structName = sda.getValue().getStructName();
                SDA parentSDA = sdaMap.get(parentResourceId);

                if (null != parentSDA) {
                    parentSDA.addChild(sda);
                    sda.setXpath(tmpPath + "/" + metadataId);
                    if ("request".equalsIgnoreCase(structName)) {
                        tmpPath = "/request";
                    }
                    if ("response".equalsIgnoreCase(structName)) {
                        tmpPath = "/response";
                    }
                    // if (!parentSDA.getXpath().equals("/")) {
                    // sda.setXpath(parentSDA.getXpath() + "/" +
                    // sda.getValue().getMetadataId());
                    // } else {
                    // sda.setXpath("/" + sda.getValue().getMetadataId());
                    // }
                }
            }
            sdaMap = null;
        }
        return root;
    }

    /**
     * @param interfaceId
     * @return
     * @throws DataException
     */
    @Transactional
    public SDA4I getSDA4IofInterfaceId(String interfaceId) throws DataException {
        SDA4I root = null;
        if (null != interfaceId) {
            root = new SDA4I();
            Map<String, String> params = new HashMap<String, String>();
            // if (log.isDebugEnabled()) {
            // log.debug("查找根节点，服务id为[" + service.getServiceId() + "],
            // 父节点为[/]");
            // }
            params.put("interfaceId", interfaceId);
            // log.info("interfaceId:"+interfaceId);
            // params.put("parentResourceId", "/");
            List<SDANode4I> nodes = sdaNode4IDAO.findBy(params, "structIndex");
            Map<String, SDA4I> sdaMap = new HashMap<String, SDA4I>(nodes.size());
            String tmpPath = "/";
            for (SDANode4I sdaNode : nodes) {
                SDA4I sda = new SDA4I();
                sda.setValue(sdaNode);
                sdaMap.put(sdaNode.getResourceId(), sda);
                String parentResourceId = sdaNode.getParentResourceId();
                if ("/".equalsIgnoreCase(parentResourceId)) {
                    root = sda;
                    sda.setXpath("/");
                }
                String structName = sda.getValue().getStructName();
                String metadataId = sda.getValue().getMetadataId();
                SDA4I parentSDA = sdaMap.get(parentResourceId);
                sda.setXpath(tmpPath + "/" + metadataId);
                if (null != parentSDA) {
                    parentSDA.addChild(sda);
                    if ("request".equalsIgnoreCase(structName)) {
                        tmpPath = "/request";
                    }
                    if ("response".equalsIgnoreCase(structName)) {
                        tmpPath = "/response";
                    }
                    // if (!parentSDA.getXpath().equals("/")) {
                    // sda.setXpath(parentSDA.getXpath() + "/" +
                    // sda.getValue().getMetadataId());
                    // } else {
                    // sda.setXpath("/" + sda.getValue().getMetadataId());
                    // }
                }
            }
        }
        return root;
    }

    @Transactional
    public SDA4I getSDA4IVOInterfaceId(String interfaceId) throws DataException {

        SDA4I sda = null;
        if (null != interfaceId) {
            sda = new SDA4I();
            Map<String, String> params = new HashMap<String, String>();
            // if (log.isDebugEnabled()) {
            // log.debug("查找根节点，服务id为[" + service.getServiceId() + "],
            // 父节点为[/]");
            // }
            params.put("interfaceId", interfaceId);
            params.put("parentResourceId", "/");
            List<SDANode4I> nodes = sdaNode4IDAO.findBy(params, "structIndex");
            if (nodes.size() > 1) {
                String errorMsg = "一个sda只能有一个root节点";
                log.error(errorMsg);
                throw new DataException(errorMsg);
            }
            // 获取node节点
            SDANode4I node = nodes.get(0);
            if (log.isDebugEnabled()) {
                log.debug("获取到根节点[" + node + "]");
            }
            // 获取node节点的属性
            List<SDANodeProperty> nodeProperties = sdaNodePropertyDAO.findBy(
                    "structId", node.getResourceId());
            sda.setValue(node);
            sda.setProperties(nodeProperties);
            List<SDA4I> childSDA = getChildSDANodes4I(sda);
            sda.setChildNode(childSDA);
        }
        return sda;
    }

    public List<SDA> getChildSDANodes(SDA sda) throws DataException {
        List<SDA> childSdas = null;
        if (null != sda) {
            SDANode sdaNode = sda.getValue();
            if (null == sdaNode) {
                String errorMsg = "sda的数据节点为空!";
                log.error(errorMsg);
                throw new DataException(errorMsg);
            }
            if (sdaNode.getParentResourceId().equals("/")) {
                sda.setXpath("/" + sdaNode.getMetadataId());
            }
            String nodeResourceId = sdaNode.getResourceId();
            // 获取所有的数据子节点
            Map<String, String> params = new HashMap<String, String>();
            params.put("parentResourceId", nodeResourceId);
            List<SDANode> childNodes = sdaNodeDAO.findBy(params, "structIndex");
            if (null != childNodes && childNodes.size() > 0) {
                childSdas = new ArrayList<SDA>();
            }
            for (SDANode childNode : childNodes) {
                // 获取子节点的属性信息
                List<SDANodeProperty> childNodeProperties = sdaNodePropertyDAO
                        .findBy("structId", childNode.getResourceId());
                SDA childSDA = new SDA();
                childSDA.setValue(childNode);
                childSDA.setProperties(childNodeProperties);
                if (childNode.getParentResourceId().equals("/")) {
                    sda.setXpath("/");
                }
                if (!sda.getXpath().equals("/")) {
                    childSDA.setXpath(sda.getXpath() + "/"
                            + childNode.getMetadataId());
                } else {
                    childSDA.setXpath("/" + childNode.getMetadataId());
                }
                // 递归获取节点自己的子节点
                List<SDA> grandChildNodes = getChildSDANodes(childSDA);
                if (null != grandChildNodes) {
                    childSDA.setChildNode(grandChildNodes);
                }
                childSdas.add(childSDA);
            }
        }
        return childSdas;
    }

//	public List<SDA> getChildNodesOfSda(SDA sda) {
//		SDANode value = sda.getValue();
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("parentResourceId", nodeResourceId);
//		List<SDANode> childNodes = sdaNodeDAO.findBy(params, "structIndex");
//	}
//	

    @Transactional
    public List<SDANode> getRelateChildSDANodes(SDANode sdaNode)
            throws DataException {
        List<SDANode> childNodes = null;
        if (null != sdaNode) {
            String nodeResourceId = sdaNode.getResourceId();
            // 获取所有的数据子节点
            Map<String, String> params = new HashMap<String, String>();
            params.put("parentResourceId", nodeResourceId);
            childNodes = sdaNodeDAO.findBy(params, "structIndex");
        }
        return childNodes;
    }

    @Transactional
    public List<SDA4I> getChildSDANodes4I(SDA4I sda) throws DataException {
        List<SDA4I> childSdas = null;
        if (null != sda) {
            SDANode4I sdaNode = sda.getValue();
            if (null == sdaNode) {
                String errorMsg = "sda的数据节点为空!";
                log.error(errorMsg);
                throw new DataException(errorMsg);
            }
            // if (sdaNode.getParentResourceId().equals("/")) {
            // sda.setXpath("/" + sdaNode.getMetadataId());
            // }
            String nodeResourceId = sdaNode.getResourceId();
            // 获取所有的数据子节点
            Map<String, String> params = new HashMap<String, String>();
            params.put("parentResourceId", nodeResourceId);
            List<SDANode4I> childNodes = sdaNode4IDAO.findBy(params,
                    "structIndex");
            if (null != childNodes && childNodes.size() > 0) {
                childSdas = new ArrayList<SDA4I>();
            }
            for (SDANode4I childNode : childNodes) {
                // 获取子节点的属性信息
                List<SDANodeProperty> childNodeProperties = sdaNodePropertyDAO
                        .findBy("structId", childNode.getResourceId());
                SDA4I childSDA = new SDA4I();
                childSDA.setValue(childNode);
                childSDA.setProperties(childNodeProperties);
                // if (!sda.getXpath().equals("/")) {
                // childSDA.setXpath(sda.getXpath() + "/" +
                // childNode.getMetadataId());
                // } else {
                // childSDA.setXpath("/" + childNode.getMetadataId());
                // }
                // 递归获取节点自己的子节点
                List<SDA4I> grandChildNodes = getChildSDANodes4I(childSDA);
                if (null != grandChildNodes) {
                    childSDA.setChildNode(grandChildNodes);
                }
                childSdas.add(childSDA);
            }
        }
        return childSdas;
    }

    @Transactional
    public List<Service> getOpertions(String serviceId) throws DataException {
        List<Service> operations = null;
        List<ServiceExtendInfo> serviceExtendInfos = serviceExtendInfoDAO
                .findBy("superServiceId", serviceId);
        if (null != serviceExtendInfos) {
            operations = new ArrayList<Service>();
            for (ServiceExtendInfo serviceExtendInfo : serviceExtendInfos) {
                String operationRelationId = serviceExtendInfo.getRelationId();
                if (null != operationRelationId) {
                    List<Service> services = serviceDAO.findBy("resourceId",
                            operationRelationId);
                    if (null == services) {
                        String errorMsg = "服务[" + serviceId + "]的操作["
                                + operationRelationId + "]不存在";
                        log.error(errorMsg);
                        throw new DataException((errorMsg));
                    }
                    if (services.size() == 0) {
                        String errorMsg = "服务[" + serviceId + "]的操作["
                                + operationRelationId + "]不存在";
                        log.error(errorMsg);
                        throw new DataException(errorMsg);
                    }
                    if (services.size() > 1) {
                        String errorMsg = "服务[" + serviceId + "]的中存在重复的操作";
                        log.error(errorMsg);
                        throw new DataException(errorMsg);
                    }
                    operations.add(services.get(0));
                }
            }
        }
        return operations;
    }

    @Transactional
    public List<Service> getServiceOfOperation(Service operation) {
        List<Service> superServices = null;
        if (null != operation) {
            superServices = new ArrayList<Service>();
            String operationResourceId = operation.getResourceId();
            List<ServiceExtendInfo> serviceExtendInfos = serviceExtendInfoDAO
                    .findBy("relationId", operationResourceId);
            for (ServiceExtendInfo serviceExtendInfo : serviceExtendInfos) {
                String serviceId = serviceExtendInfo.getSuperServiceId();
                List<Service> services = serviceDAO.findBy("serviceId",
                        serviceId);
                superServices.addAll(services);
            }
        }
        return superServices;
    }

    @Transactional
    public List<Service> getServiceByOperationId(String operationId) {
        List<Service> services = null;
        List<ServiceInvokeRelation> serviceInvokeRelations = serviceInvokeRelationDAO
                .findBy("operationId", operationId);
        if (null != serviceInvokeRelations) {
            List<String> serviceIds = new ArrayList<String>();
            services = new ArrayList<Service>();
            for (ServiceInvokeRelation serviceInvokeRelation : serviceInvokeRelations) {
                String serviceId = serviceInvokeRelation.getServiceId();
                serviceIds.remove(serviceId);
                serviceIds.add(serviceId);
            }
            for (String serviceId : serviceIds) {
                Service service = serviceDAO.findUniqueBy("serviceId",
                        serviceId);
                services.add(service);
            }
        }
        return services;
    }

    public Service getServiceByResourceId(String resourceId) {
        return serviceDAO.findUniqueBy("resourceId", resourceId);
    }

    @Transactional
    public List<Service> getParentService(Service service) {
        List<ServiceExtendInfo> serviceExtendInfos = serviceExtendInfoDAO
                .findBy("relationId", service.getResourceId());
        ServiceExtendInfo serviceExtendInfo = serviceExtendInfos.get(0);
        String serviceId = serviceExtendInfo.getSuperServiceId();
        return getServiceById(serviceId);
    }

    @Transactional
    public List<RemainingService> getRemainingServiceByServiceId(
            String serviceId) {
        return remainingServiceDAO.findBy("serviceId", serviceId);
    }

    @Transactional
    public List<ServiceInvokeRelation> getInvokerRelationByInterfaceId(
            String interfaceid) {
        return serviceInvokeRelationDAO.findBy("interfaceId", interfaceid);
    }

    /**
     * 获得Excel导出左侧interface metadataviewbean
     *
     * @param id
     * @return
     * @throws DataException
     */
    public MetadataViewBean getMetadataById(String id) throws DataException {
        MetadataViewBean metadataViewBean = null;
        List<Metadata> metadatas = metadataDAO.findBy("metadataId", id);
        if (null == metadatas) {
            String errorMsg = "元数据[" + id + "]不存在！";
            log.error(errorMsg);
            throw new DataException(errorMsg);
        }
        if (metadatas.size() == 0) {
            String errorMsg = "元数据[" + id + "]不存在！";
            log.error(errorMsg);
            throw new DataException(errorMsg);
        }
        if (metadatas.size() > 1) {
            String errorMsg = "元数据[" + id + "]存在重复项！";
            log.error(errorMsg);
            throw new DataException(errorMsg);
        }

        Metadata metadata = metadatas.get(0);
        String metadataId = metadata.getMetadataId();
        List<SDANode4I> lstSDANode4I = sdaNode4IDAO.findBy("metadataId", metadataId);
        if (lstSDANode4I.size() == 0)
            return null;
        SDANode4I node = lstSDANode4I.get(0);
        metadataViewBean = new MetadataViewBean();
        metadataViewBean.setMetadataId(metadataId);
        metadataViewBean.setMetadataName(metadata.getMetadataName());
        metadataViewBean.setType(node.getType());
        metadataViewBean.setLength(node.getLength());

        return metadataViewBean;
    }

    /**
     * @param id
     * @return
     * @throws DataException
     */
    public MetadataViewBean getServiceMetadataInfo(String id) throws DataException {
        MetadataViewBean metadataViewBean = new MetadataViewBean();
        List<MetadataAttribute> lstMetadataAttr = metadataAttrDAO.findBy("metadataId", id);
        for (MetadataAttribute attr : lstMetadataAttr) {
            if (attr.getAttributeId().equals("length")) {
                metadataViewBean.setLength(attr.getAttributeValue());
            } else if (attr.getAttributeId().equals("scale")) {
                metadataViewBean.setScale(attr.getAttributeValue());
            } else if (attr.getAttributeId().equals("type")) {
                metadataViewBean.setType(attr.getAttributeValue());
            }
        }
        return metadataViewBean;
    }
}

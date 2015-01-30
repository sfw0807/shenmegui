package com.dc.esb.servicegov.refactoring.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.exception.DataException;
import com.dc.esb.servicegov.refactoring.dao.impl.HeadSDADAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.IdaDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.InvokeInfoDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.MetadataDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.MetadataStructsAttrDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.OperationDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.RemainingServiceDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.SDADAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.ServiceDAOImpl;
import com.dc.esb.servicegov.refactoring.dao.impl.SvcAsmRelateViewDAOImpl;
import com.dc.esb.servicegov.refactoring.entity.HeadSDA;
import com.dc.esb.servicegov.refactoring.entity.IDA;
import com.dc.esb.servicegov.refactoring.entity.Metadata;
import com.dc.esb.servicegov.refactoring.entity.MetadataStructsAttr;
import com.dc.esb.servicegov.refactoring.entity.Operation;
import com.dc.esb.servicegov.refactoring.entity.RemainingService;
import com.dc.esb.servicegov.refactoring.entity.SDA;
import com.dc.esb.servicegov.refactoring.entity.SvcAsmRelateView;
import com.dc.esb.servicegov.refactoring.service.ServiceManager;
import com.dc.esb.servicegov.refactoring.util.AuditUtil;
import com.dc.esb.servicegov.refactoring.util.ServiceStateUtils;
import com.dc.esb.servicegov.refactoring.vo.HeadSDAVO;
import com.dc.esb.servicegov.refactoring.vo.SDA4I;
import com.dc.esb.servicegov.refactoring.vo.SDAVO;
import com.dc.esb.servicegov.vo.MetadataViewBean;
import com.dc.esb.servicegov.vo.RelationNewVO;
import com.dc.esb.servicegov.vo.RelationVo;

@Service
@Transactional
public class ServiceManagerImpl implements ServiceManager {
	private Log log = LogFactory.getLog(getClass());
	@Autowired
	private ServiceDAOImpl serviceDAO;
	@Autowired
	private OperationDAOImpl operationDAO;
	@Autowired
	private RemainingServiceDAOImpl remainingServiceDAO;
	@Autowired
	private HeadSDADAOImpl headSDADAO;
	@Autowired
	private InvokeInfoDAOImpl invokeDAO;
	@Autowired
	private SvcAsmRelateViewDAOImpl svcAsmRelateViewDAOImpl;
	@Autowired
	private IdaDAOImpl idaDAO;
	@Autowired
	private SDADAOImpl sdaDAO;
	@Autowired
	private MetadataDAOImpl metadataDAO;
	@Autowired
	private MetadataStructsAttrDAOImpl metadataAttrDAO;

	public List<RelationNewVO> getRelationViewListNew() {
		List<RelationNewVO> lstVO = new ArrayList<RelationNewVO>();
		Map<String, RelationNewVO> map = new HashMap<String, RelationNewVO>();
		List<SvcAsmRelateView> lst = svcAsmRelateViewDAOImpl.getAll();
		Iterator<SvcAsmRelateView> it = lst.iterator();
		while(it.hasNext()) {
			SvcAsmRelateView r = it.next();
			RelationNewVO vo = new RelationNewVO(r);
			String ecode = vo.getEcode();
			if (map.containsKey(ecode)) {
				RelationNewVO o = map.get(ecode);
				if (!o.getConsumerSys().contains(vo.getConsumerSys())) {
					o.setConsumerSys(o.getConsumerSys() 
							+ "、" + vo.getConsumerSys());
				}
				if (vo.getSourceSys()!=null &&!o.getSourceSys().contains(vo.getSourceSys())) {
					if (o.getSourceSys().equals("")) {
						o.setSourceSys(vo.getSourceSys());
					} else {
						o.setSourceSys(o.getSourceSys()+"、"+ vo.getSourceSys());
					}
				}
				
			} else {
				map.put(ecode, vo);
			}
		}
		
		for (Map.Entry<String, RelationNewVO> e : map.entrySet()) {
			lstVO.add(e.getValue());
		}
		return lstVO;
	}
	
	public MetadataStructsAttr getMetadataAttrById(String mid) {
		List<MetadataStructsAttr> lst = metadataAttrDAO.findBy("structId", mid);
		if (lst.size() == 0) {
			return null;
		}
		return metadataAttrDAO.findBy("structId", mid).get(0);
	}
	
	public List<com.dc.esb.servicegov.refactoring.entity.Service> getAllServices() {
		List<com.dc.esb.servicegov.refactoring.entity.Service> services = new ArrayList<com.dc.esb.servicegov.refactoring.entity.Service>();
		if (log.isInfoEnabled()) {
			log.info("获取全部服务信息...");
		}
		try {
			services = serviceDAO.getAll();
		} catch (Exception e) {
			log.error("获取全部服务信息失败", e);
		}
		return services;
	}

	@Transactional
	public SDAVO getSDAofRelation(RelationVo relation) throws DataException {
		SDAVO root = null;
		if (null != relation) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("serviceId", relation.getServiceId().trim());
			params.put("operationId", relation.getOperationId().trim());
			List<SDA> nodes = sdaDAO.findBy(params, "seq");
			// 获取node节点的属性
			Map<String, SDAVO> sdaMap = new HashMap<String, SDAVO>(nodes.size());
			String tmpPath = "/";
			for (SDA sdaNode : nodes) {
				SDAVO sda = new SDAVO();
				sda.setValue(sdaNode);
				// sda.setProperties(nodeProperties);
				sdaMap.put(sdaNode.getId(), sda);
				String parentResourceId = sdaNode.getParentId();
				if ("/".equalsIgnoreCase(parentResourceId)) {
					root = sda;
					sda.setXpath("/");
				}
				String metadataId = sda.getValue().getMetadataId();
				String structName = sda.getValue().getStructId();
				SDAVO parentSDA = sdaMap.get(parentResourceId);

				if (null != parentSDA) {
					parentSDA.addChild(sda);
					sda.setXpath(tmpPath + "/" + metadataId);
					if ("request".equalsIgnoreCase(structName)) {
						tmpPath = "/request";
					}
					if ("response".equalsIgnoreCase(structName)) {
						tmpPath = "/response";
					}
				}
			}
			sdaMap = null;
		}
		return root;
	}
	
	/**
	 * 获得Excel导出左侧interface metadataviewbean
	 * 
	 * @param id
	 * @return
	 * @throws com.dc.esb.servicegov.exception.DataException
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
		metadataViewBean = new MetadataViewBean();
		metadataViewBean.setMetadataId(metadataId);
		metadataViewBean.setMetadataName(metadata.getName());
		metadataViewBean.setType(metadata.getType());
		metadataViewBean.setLength(metadata.getLength());
		metadataViewBean.setScale(metadata.getScale());

		return metadataViewBean;
	}
	
	public Metadata getMetadataByMid(String id) {
		List<Metadata> lstMetadata = metadataDAO.findBy("metadataId", id);
		if (lstMetadata != null) {
			return lstMetadata.get(0);
		}
		return null;
	}
	
	public List<Operation> getOperationById(String id) {
		return operationDAO.findBy("operationId", id);
	}
	
	@Transactional
	public List<com.dc.esb.servicegov.refactoring.entity.Service> getServicesById(String serviceId) {
		List<com.dc.esb.servicegov.refactoring.entity.Service> service = null;
		if (null != serviceId) {
			service = serviceDAO.findBy("serviceId", serviceId);
		}
		return service;
	}
	
	/**
	 * 
	 * @param interfaceId
	 * @return
	 * @throws com.dc.esb.servicegov.exception.DataException
	 */
	@Transactional
	public SDA4I getSDA4IofInterfaceId(String interfaceId) throws DataException {
		SDA4I root = null;
		if (null != interfaceId) {
			root = new SDA4I();
			Map<String, String> params = new HashMap<String, String>();
			params.put("interfaceId", interfaceId);
			List<IDA> nodes = idaDAO.findBy(params, "seq");
			Map<String, SDA4I> sdaMap = new HashMap<String, SDA4I>(nodes.size());
			String tmpPath = "/";
			for (IDA sdaNode : nodes) {
				SDA4I sda = new SDA4I();
				sda.setValue(sdaNode);
				sdaMap.put(sdaNode.getId(), sda);
				String parentResourceId = sdaNode.getParentId();
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
				}
			}
		}
		return root;
	}
	
	public SvcAsmRelateView getRelationViewByInterfaceId(String id) {
		Criterion criterion = Restrictions.eq("interfaceId", id);
		SvcAsmRelateView view = svcAsmRelateViewDAOImpl.find(criterion).get(0);
		return view;
	}
	
	@Transactional
	public List<RelationVo> getInvokerRelationByInterfaceIds(
			String[]ids) {
		
		Criterion criterion = Restrictions.in("interfaceId", ids);
		
		List<SvcAsmRelateView> list = svcAsmRelateViewDAOImpl.find(criterion);
		
		List<RelationVo> listVO = new ArrayList<RelationVo>();
		
		Map<String, RelationVo> map = new HashMap<String, RelationVo>();
		Iterator<SvcAsmRelateView> it = list.iterator();
		while(it.hasNext()) {
			SvcAsmRelateView r = it.next();
			RelationVo vo = new RelationVo(r);
			String ecode = vo.getInterfaceId();
			if (map.containsKey(ecode)) {
				RelationVo o = map.get(ecode);
				// 调用方不相同合并多个调用方
				if (!o.getConsumerSystemAb().equals(vo.getConsumerSystemAb())) {
					o.setConsumerSystemAb(o.getConsumerSystemAb() 
							+ "、" + vo.getConsumerSystemAb());
				}
				if (!CollectionUtils.isEqualCollection(o.getMsgConvert(), vo.getMsgConvert())) {
					o.getMsgConvert().addAll(vo.getMsgConvert());
				}
			} else {
				map.put(ecode, vo);
			}
		}
		for (Map.Entry<String, RelationVo> e : map.entrySet()) {
			listVO.add(e.getValue());
		}
		Iterator<RelationVo> ito = listVO.iterator();
		while(ito.hasNext()) {
			RelationVo r = ito.next();
			if (r.getPassbySys() != null && r.getPassbySys().equals("ZHIPP")) {
				if (r.getConsumerSystemAb() != null) {
					r.setConsumerSystemAb("ZHIPP(" + r.getConsumerSystemAb() + ")");
				} else {
					r.setConsumerSystemAb("ZHIPP");
				}
			}
		}
		return listVO;
	}
	
	/**
	 * 
	 * @param system
	 *            id
	 * @return
	 */
	public List<RelationVo> getServiceInvokeRelationByProvider(
			String id) {
		List<SvcAsmRelateView> list = new ArrayList<SvcAsmRelateView>();
		List<RelationVo> listVO = new ArrayList<RelationVo>();
		
		Map<String, RelationVo> map = new HashMap<String, RelationVo>();
		list = svcAsmRelateViewDAOImpl.findBy("prdSysID", id);
		Iterator<SvcAsmRelateView> it = list.iterator();
		while(it.hasNext()) {
			SvcAsmRelateView r = it.next();
			RelationVo vo = new RelationVo(r);
			String ecode = vo.getInterfaceId();
			if (map.containsKey(ecode)) {
				RelationVo o = map.get(ecode);
				o.setConsumerSystemAb(o.getConsumerSystemAb() 
						+ "、" + vo.getConsumerSystemAb());
				if (!CollectionUtils.isEqualCollection(o.getMsgConvert(), vo.getMsgConvert())) {
					o.getMsgConvert().addAll(vo.getMsgConvert());
				}
			} else {
				map.put(ecode, vo);
			}
		}
		for (Map.Entry<String, RelationVo> e : map.entrySet()) {
			listVO.add(e.getValue());
		}
		Iterator<RelationVo> ito = listVO.iterator();
		while(ito.hasNext()) {
			RelationVo r = ito.next();
			if (r.getPassbySys().equals("ZHIPP")) {
				r.setConsumerSystemAb("ZHIPP(" + r.getConsumerSystemAb() + ")");
			}
		}
		return listVO;
	}
	
	/**
	 * 
	 * @param system
	 *            id
	 * @return
	 */
	public List<RelationVo> getServiceInvokeRelationByConsumer(
			String id) {
		List<SvcAsmRelateView> list = new ArrayList<SvcAsmRelateView>();
		List<RelationVo> listVO = new ArrayList<RelationVo>();
		Map<String, RelationVo> map = new HashMap<String, RelationVo>();
		list = svcAsmRelateViewDAOImpl.findBy("csmSysId", id);
		Iterator<SvcAsmRelateView> it = list.iterator();
		while(it.hasNext()) {
			SvcAsmRelateView r = it.next();
			RelationVo vo = new RelationVo(r);
			String ecode = vo.getInterfaceId();
			if (map.containsKey(ecode)) {
				RelationVo o = map.get(ecode);
				o.setConsumerSystemAb(o.getConsumerSystemAb() 
						+ "、" + vo.getConsumerSystemAb());
				if (!CollectionUtils.isEqualCollection(o.getMsgConvert(), vo.getMsgConvert())) {
					o.getMsgConvert().addAll(vo.getMsgConvert());
				}
			} else {
				map.put(ecode, vo);
			}
		}
		for (Map.Entry<String, RelationVo> e : map.entrySet()) {
			listVO.add(e.getValue());
		}
		Iterator<RelationVo> ito = listVO.iterator();
		while(ito.hasNext()) {
			RelationVo r = ito.next();
			if (r.getPassbySys().equals("ZHIPP")) {
				r.setConsumerSystemAb("ZHIPP(" + r.getConsumerSystemAb() + ")");
			}
		}
		return listVO;
	}
	
	public boolean isConsumerSysLinked(String id) {
		Criterion[] criterions = new Criterion[3];
		criterions[0] = Restrictions.eq("consumeSysId", id);
		criterions[1] = Restrictions.ne("operationId", "");
		criterions[2] = Restrictions.ne("ecode", "");
		if (invokeDAO.find(criterions).size() > 0)
			return true;
		else
			return false;
	}

	public boolean isProviderSysLinked(String id) {
		Criterion[] criterions = new Criterion[3];
		criterions[0] = Restrictions.eq("provideSysId", id);
		criterions[1] = Restrictions.ne("operationId", "");
		criterions[2] = Restrictions.ne("ecode", "");
		if (invokeDAO.find(criterions).size() > 0)
			return true;
		else
			return false;
	}
	
	@Override
	public List<com.dc.esb.servicegov.refactoring.entity.Service> getServicesByCategory(
			String categoryId) {
		List<com.dc.esb.servicegov.refactoring.entity.Service> services = new ArrayList<com.dc.esb.servicegov.refactoring.entity.Service>();
		if (log.isInfoEnabled()) {
			log.info("获取服务分类为[" + categoryId + "]的服务...");
		}
		try {
			Map<String,String> map = new HashMap<String,String>();
			map.put("categoryId", categoryId);
			map.put("auditState", AuditUtil.passed);
			services = serviceDAO.findBy(map);
//			services = serviceDAO.findBy("categoryId", categoryId);
		} catch (Exception e) {
			log.error("获取全部审核通过的服务信息失败", e);
		}
		return services;
	}

	@Override
	public com.dc.esb.servicegov.refactoring.entity.Service getServiceById(
			String id) {
		if (log.isInfoEnabled()) {
			log.info("获取服务ID为[" + id + "]的服务...");
		}
		return serviceDAO.findUniqueBy("serviceId", id);
	}

	/**
	 * 删除服务 （删除服务、操作、以及操作涉及的SDA、SLA、OLA InvokeRelation表）
	 */
	@Override
	public boolean delServiceById(String id) {
		// TODO Auto-generated method stub
		boolean flag = true;
		if (log.isInfoEnabled()) {
			log.info("删除服务ID为[" + id + "]的服务...");
		}
		try {
			serviceDAO.delete(id);
		} catch (Exception e) {
			log.error("delete failed!", e);
			flag = false;
		}
		return flag;
	}

	@Override
	public boolean insertOrUpdateService(
			com.dc.esb.servicegov.refactoring.entity.Service service) {
		// TODO Auto-generated method stub
		boolean flag = true;
		if (log.isInfoEnabled()) {
			log.info("新增或修改服务ID为[" + service.getServiceId() + "]的服务...");
		}
		try {
			serviceDAO.save(service);
		} catch (Exception e) {
			log.error("delete failed!", e);
			flag = false;
		}
		return flag;
	}

	/**
	 * 查看某一个服务下的所有操作信息
	 * 
	 * @param id
	 */
	@Override
	public List<Operation> getOperationsByServiceId(String id) {
		if (log.isInfoEnabled()) {
			log.info("正在查找服务["+id+"]下的所有操作");
		}
		List<Operation> list = operationDAO.findBy("serviceId", id);
		if (list == null) {
			list = new ArrayList<Operation>();
		}
		return list;
	}

	/**
	 * 发布服务
	 */
	@Override
	public boolean deployService(String id) {
		// TODO Auto-generated method stub\
		if (log.isInfoEnabled()) {
			log.info("deploy service...");
		}
		boolean flag = true;
		try {
			// 发布服务
			com.dc.esb.servicegov.refactoring.entity.Service service = this
					.getServiceById(id);
			service.setState(ServiceStateUtils.DEVELOP);
			serviceDAO.save(service);
			// 发布服务下所有操作
			List<Operation> operationList = getOperationsByServiceId(id);
			for (Operation operation : operationList) {
				operationDAO.deployOperation(operation.getOperationId(),
						operation.getServiceId());
			}
		} catch (Exception e) {
			log.error("deploy service failed!", e);
			flag = false;
		}
		return flag;
	}

	/**
	 * 上线服务
	 */
	@Override
	public boolean publishService(String id) {
		if (log.isInfoEnabled()) {
			log.info("publish service...");
		}
		boolean flag = true;
		try {
			// 上线服务
			com.dc.esb.servicegov.refactoring.entity.Service service = this
					.getServiceById(id);
			service.setState(ServiceStateUtils.PUBLISH);
			String version = service.getVersion();
			String[] num = version.split("\\.");
			num[2] = String.valueOf(Integer.parseInt(num[2]) + 1);
			version = num[0] + "." + num[1] + "." + num[2];
			service.setVersion(version);
			serviceDAO.save(service);
			// 上线服务下所有操作
			List<Operation> operationList = getOperationsByServiceId(id);
			for (Operation operation : operationList) {
				operationDAO.publishOperation(operation.getOperationId(),
						operation.getServiceId());
			}
		} catch (Exception e) {
			log.error("publish service failed!", e);
			flag = false;
		}
		return flag;
	}

	/**
	 * 重定义服务
	 */
	@Override
	public boolean redefService(String id) {
		if (log.isInfoEnabled()) {
			log.info("redef service...");
		}
		boolean flag = true;
		try {
			// 发布服务
			com.dc.esb.servicegov.refactoring.entity.Service service = this
					.getServiceById(id);
			service.setState(ServiceStateUtils.DEFINITION);
			String version = service.getVersion();
			String[] num = version.split("\\.");
			num[1] = String.valueOf(Integer.parseInt(num[1]) + 1);
			version = num[0] + "." + num[1] + ".0";
			service.setVersion(version);
			serviceDAO.save(service);
			// 发布服务下所有操作
			List<Operation> operationList = getOperationsByServiceId(id);
			for (Operation operation : operationList) {
				operationDAO.redefOperation(operation.getOperationId(),
						operation.getServiceId());
			}
		} catch (Exception e) {
			log.error("redef service failed!", e);
			flag = false;
		}
		return flag;
	}

	public List<RemainingService> getRemainingServiceByServiceId(String id) {
		return remainingServiceDAO.findBy("serviceId", id);
	}
	public boolean checkSdaExists(String operationId,String serviceId){
		boolean flag = true;
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("operationId", operationId);
		paramMap.put("serviceId", serviceId);
		List<SDA> list = sdaDAO.findBy(paramMap);
		if(list == null || list.size() <= 0){
			flag = false;
		}
		log.error("operationId:"+operationId+",serviceId:"+serviceId+",是否有sda"+flag);
		return flag;
	}

	public HeadSDAVO getHeadSDAofService(String serviceId) {
		HeadSDAVO root = null;
		if (null != serviceId) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("headId", serviceId);
			List<HeadSDA> nodes = headSDADAO.findBy(params, "structIndex");
			// 获取node节点的属性
			Map<String, HeadSDAVO> sdaMap = new HashMap<String, HeadSDAVO>(nodes.size());
			String tmpPath = "/";
			for (HeadSDA sdaNode : nodes) {
				HeadSDAVO sda = new HeadSDAVO();
				// List<SDANodeProperty> nodeProperties =
				// sdaNodePropertyDAO.findBy("structId",
				// sdaNode.getResourceId());
				sda.setValue(sdaNode);
				// sda.setProperties(nodeProperties);
				sdaMap.put(sdaNode.getId(), sda);
				String parentResourceId = sdaNode.getParentId();
				if ("/".equalsIgnoreCase(parentResourceId)) {
					root = sda;
					sda.setXpath("/");
				}
				String metadataId = sda.getValue().getStructName();
				String structName = sda.getValue().getStructName();
				HeadSDAVO parentSDA = sdaMap.get(parentResourceId);

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

	public SDAVO getSDAofService(Operation operationDO) {
		SDAVO root = null;
		if (null != operationDO.getOperationId()) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("operationId", operationDO.getOperationId());
			params.put("serviceId", operationDO.getServiceId());
			List<SDA> nodes = sdaDAO.findBy(params, "seq");
			// 获取node节点的属性
			Map<String, SDAVO> sdaMap = new HashMap<String, SDAVO>(nodes.size());
			String tmpPath = "/";
			for (SDA sdaNode : nodes) {
				SDAVO sda = new SDAVO();
				// List<SDANodeProperty> nodeProperties =
				// sdaNodePropertyDAO.findBy("structId",
				// sdaNode.getResourceId());
				sda.setValue(sdaNode);
				// sda.setProperties(nodeProperties);
				sdaMap.put(sdaNode.getId(), sda);
				String parentResourceId = sdaNode.getParentId();
				if ("/".equalsIgnoreCase(parentResourceId)) {
					root = sda;
					sda.setXpath("/");
				}
				String metadataId = sda.getValue().getStructId();
				String structName = sda.getValue().getStructId();
				SDAVO parentSDA = sdaMap.get(parentResourceId);

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
	 * get 待审核 audit serviceList
	 */
	@Override
	public List<com.dc.esb.servicegov.refactoring.entity.Service> getAuditServices() {
		return serviceDAO.findBy("auditState", "1");
	}

	@Override
	public boolean auditService(String serviceId, String auditState) {
		// TODO Auto-generated method stub
		com.dc.esb.servicegov.refactoring.entity.Service service = this.getServiceById(serviceId);
		if(service == null){
			log.error("审核的服务不存在!");
			return false;
		}
		else{
			service.setAuditState(auditState);
		    serviceDAO.save(service);
		}
		return true;
	}
	@Override
	public boolean submitService(String serviceId) {
		// TODO Auto-generated method stub
		com.dc.esb.servicegov.refactoring.entity.Service service = this.getServiceById(serviceId);
		if(service == null){
			log.error("审核的服务不存在!");
			return false;
		}
		else{
			service.setAuditState(AuditUtil.submit);
		    serviceDAO.save(service);
		}
		return true;
	}
	public List<com.dc.esb.servicegov.refactoring.entity.Service> getAllServiceOrderByServiceId() {
		List<com.dc.esb.servicegov.refactoring.entity.Service> services = new ArrayList<com.dc.esb.servicegov.refactoring.entity.Service>();
		try {
			services = serviceDAO.getAll("serviceId", true);
		} catch (Exception e) {
			log.error("获取全部服务信息失败", e);
		}
		return services;
	}

	@Override
	public boolean checkServicePassed(String serviceId) {
		// TODO Auto-generated method stub
		com.dc.esb.servicegov.refactoring.entity.Service service = serviceDAO.findUniqueBy("serviceId", serviceId);
		if(AuditUtil.passed.equals(service.getAuditState())){
			return true;
		}
		return false;
	}
}

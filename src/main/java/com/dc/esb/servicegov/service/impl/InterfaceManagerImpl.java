package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.dao.impl.IdaDAOImpl;
import com.dc.esb.servicegov.dao.impl.InterfaceDAOImpl;
import com.dc.esb.servicegov.dao.impl.InvokeInfoDAOImpl;
import com.dc.esb.servicegov.dao.impl.TransStateDAOImpl;
import com.dc.esb.servicegov.entity.IDA;
import com.dc.esb.servicegov.entity.Interface;
import com.dc.esb.servicegov.entity.InvokeInfo;
import com.dc.esb.servicegov.entity.TransState;
import com.dc.esb.servicegov.exception.DataException;
import com.dc.esb.servicegov.util.UUIDUtil;
import com.dc.esb.servicegov.vo.IDAVO;
import com.dc.esb.servicegov.vo.InterfaceListVO;
import com.dc.esb.servicegov.vo.InterfaceVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author G
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class InterfaceManagerImpl {
    private Log log = LogFactory.getLog(InterfaceManagerImpl.class);
    @Autowired
    private InterfaceDAOImpl interfaceDAOImpl;
    @Autowired
    private InvokeInfoDAOImpl invokeDAOImpl;
    @Autowired
    private IdaDAOImpl idaDAOImpl;
    @Autowired
    private TransStateDAOImpl transStateDAOImpl;

    @Transactional
    public List<Interface> getInterfacesById(String interfaceId) {
        return interfaceDAOImpl.findBy("interfaceId", interfaceId);
    }

    /**
     * @return
     */
    public List<InterfaceListVO> getInterfaceManagementInfo() {
        List<InterfaceListVO> lst = interfaceDAOImpl.getInterfaceVOList();
        List<InterfaceListVO> result = new ArrayList<InterfaceListVO>();
        simplizeInterfaceList(lst, result);
        return result;
    }

    /**
     * 合并不同COMSUMER记录
     *
     * @param lst
     * @param result
     */
    private void simplizeInterfaceList(List<InterfaceListVO> lst,
                                       List<InterfaceListVO> result) {
        Map<String, InterfaceListVO> map = new HashMap<String, InterfaceListVO>();
        for (InterfaceListVO vo : lst) {
            String key = vo.getECODE() + vo.getPROVIDER_SYSAB();
            if (map.get(key) != null) {
                InterfaceListVO obj = map.get(key);
                obj.setCONSUMER_SYSAB(obj.getCONSUMER_SYSAB() + "、" + vo.getCONSUMER_SYSAB());
                obj.setCONSUMER_SYSNAME(obj.getCONSUMER_SYSNAME() + "、" + vo.getCONSUMER_SYSNAME());
            } else {
                map.put(key, vo);
            }
        }
        Iterator<Map.Entry<String, InterfaceListVO>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, InterfaceListVO> vv = it.next();
            result.add(vv.getValue());
        }
    }

    /**
     * @param ecode
     * @return
     */
    public InterfaceListVO getInterfaceVO(String ecode) {
        List<InterfaceListVO> lst = interfaceDAOImpl.getInterfaceVOByEcode(ecode);
        List<InterfaceListVO> result = new ArrayList<InterfaceListVO>();
        simplizeInterfaceList(lst, result);
        return result.get(0);
    }


    /**
     * @param vo
     */
    public void updateInterfaceInfo(InterfaceVO vo) {
        Interface i = new Interface(vo);
        interfaceDAOImpl.save(i);

        InvokeInfo invoke = new InvokeInfo(vo);
        invoke.setOperationId(vo.getOperationId());

        Criterion[] cs = new Criterion[5];
        cs[0] = Restrictions.eq("consumeSysId", vo.getConsumerSysId());
        cs[1] = Restrictions.eq("ecode", vo.getEcode());
        cs[2] = Restrictions.eq("operationId", vo.getOperationId());
        cs[3] = Restrictions.eq("provideSysId", vo.getProviderSysId());
        cs[4] = Restrictions.eq("serviceId", vo.getServiceId());

        InvokeInfo info = invokeDAOImpl.findUnique(cs);



        // invoke_relation 不存在 新增
//        if (info == null) {
            Map<String, String> params = new HashMap<String, String>();
            params.put("ecode", vo.getEcode());
            List<InvokeInfo> infos = invokeDAOImpl.findBy(params);
            for(InvokeInfo invokeInfo : infos){
                invokeDAOImpl.delete(invokeInfo);
            }
            Query query = invokeDAOImpl.getSession().createSQLQuery("select max(id) from" +
                    " INVOKE_RELATION");
            BigDecimal tmp = (BigDecimal)query.list().get(0);
            Integer max = Integer.parseInt(String.valueOf(tmp));
            invoke.setId(max + 1);
            invokeDAOImpl.save(invoke);
//        } else { // modify
//            invokeDAOImpl.getSession().refresh(info);
//            invokeDAOImpl.save(info);
//        }
    }

    /**
     * @param vo
     */
    public void insertInterfaceInfo(InterfaceVO vo) {
        Interface i = new Interface(vo);
        interfaceDAOImpl.save(i);

        Query query = invokeDAOImpl.getSession().createSQLQuery("select max(id) from" +
                " INVOKE_RELATION");
        BigDecimal tmp = (BigDecimal) query.list().get(0);

        Integer max = Integer.parseInt(String.valueOf(tmp));

        InvokeInfo invoke = new InvokeInfo(vo);
        invoke.setId(max + 1);
        invoke.setOperationId(vo.getOperationId());
        invokeDAOImpl.save(invoke);

        String interfaceId = vo.getInterfaceId();
        IDA root = new IDA("root", interfaceId, null);
        IDA request = new IDA("request", interfaceId, root);
        IDA response = new IDA("response", interfaceId, root);

        idaDAOImpl.save(root, null);
        idaDAOImpl.save(request, root);
        idaDAOImpl.save(response, root);

        TransState transState = new TransState();
        transState.setId(max + 1);
        transState.setVersionState("服务定义");
        transStateDAOImpl.save(transState);
    }

    /**
     * @param params
     * @return
     */
    public boolean deleteInterfaceInfos(String params) {
        String[] ecodes = params.split(",");
        for (String ecode : ecodes) {
            List<InvokeInfo> invokes = invokeDAOImpl.findBy("ecode", ecode);
            Interface inter = interfaceDAOImpl.findUniqueBy("ecode", ecode);
            // delete idas
            idaDAOImpl.deleteInterfaceIDAs(inter);
            // delete interface
            interfaceDAOImpl.delete(inter);
            // delete invoke_relation
            invokeDAOImpl.delete(invokes);
        }
        return true;
    }

    /**
     * @param ecode
     * @return
     */
    public Interface getInterfaceByEcode(String ecode) {
        return interfaceDAOImpl.get(ecode);
    }

    /**
     * @param ecode
     * @return
     */
    public List<InvokeInfo> getInvokeRelationByEcode(String ecode) {
        return invokeDAOImpl.findBy("ecode", ecode);
    }

    public void addInterfaceManagement() {

    }

    /**
     * @param vo
     */
    public void updateInterfaceManagement(InterfaceListVO vo) {
        String ecode = vo.getECODE();
        Interface i = getInterfaceByEcode(ecode);
        i.setInterfaceName(vo.getINTERFACE_NAME());
        i.setThrough(vo.getTHROUGH());
        interfaceDAOImpl.save(i);
        List<InvokeInfo> lstII = getInvokeRelationByEcode(ecode);
        for (InvokeInfo f : lstII) {
            f.setOperationId(vo.getOPERATION_ID());
            invokeDAOImpl.save(f);
        }
    }

    /**
     * DELETE METHOD
     *
     * @param params
     * @return
     */
    public String deleteIDAs(String params) {
        String[] ids = params.split(",");
        for (String id : ids) {
            if (idaDAOImpl.findBy("parentId", id).size() > 0) {
                return "failed";
            } else {
                idaDAOImpl.delete(id);
            }
        }
        return "success";
    }

    public IDAVO getIDATreeOfInterfaceId(String interfaceId) throws DataException {
        IDAVO root = null;
        if (null != interfaceId) {
            Map<String, String> params = new HashMap<String, String>();
            params.put("interfaceId", interfaceId);
            List<IDA> nodes = idaDAOImpl.findBy(params, "seq");
            Map<String, IDAVO> sdaMap = new HashMap<String, IDAVO>(nodes.size());
            String tmpPath = "/";
            for (IDA sdaNode : nodes) {
                IDAVO idaVO = new IDAVO();
                idaVO.setValue(sdaNode);
                sdaMap.put(sdaNode.getId(), idaVO);
                String parentId = sdaNode.getParentId();
                if ("/".equalsIgnoreCase(parentId)) {
                    root = idaVO;
                    idaVO.setXpath("/");
                }
                String structName = sdaNode.getStructName();
                String metadataId = sdaNode.getMetadataId();
                IDAVO parentSDAVO = sdaMap.get(parentId);
                idaVO.setXpath(tmpPath + "/" + metadataId);
                if (null != parentSDAVO) {
                    parentSDAVO.addChild(idaVO);
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

    public void saveIDA(Map<String, String> map) {

        String id = map.get("id");
        String parentId = map.get("parentId");
        String operation = map.get("operation");
        String seq = map.get("seq");
        String structName = map.get("structName");
        String structAlias = map.get("structAlias");
        String metadataId = map.get("metadataId");
        String type = map.get("type");
        String required = map.get("required");
        String remark = map.get("remark");

        IDA parent = idaDAOImpl.findUniqueBy("id", parentId);
        String interfaceId = parent.getInterfaceId();

        IDA ida = new IDA();
        ida.setStructAlias(structAlias);
        ida.setType(type);
        ida.setRequired(required);
        ida.setParentId(parentId);
        ida.setRemark(remark);
        ida.setSeq(Integer.parseInt(seq));
        ida.setMetadataId(metadataId);
        ida.setInterfaceId(interfaceId);
        ida.setStructName(structName);
        if (operation.equals("edit")) {
            ida.setId(id);
        } else if ("add".equals(operation)) {
            ida.setId(UUIDUtil.getUUID());
            ida.setParentId(parentId);
        }
        idaDAOImpl.save(ida, parent);
    }

    public boolean saveIDA(List<IDA> idas){
        try{
            for(IDA ida : idas){
                System.out.println(ida.getId());
                if("".equals(ida.getId())){
                    ida.setId(UUIDUtil.getUUID());
                }
                idaDAOImpl.save(ida);
            }
            return true;
        }catch(Exception e){
            log.error(e,e);
        }
        return false;
    }


    public boolean deleteIDAsByInterfaceId(String interfaceId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("interfaceId", interfaceId);
        List<IDA> idas = idaDAOImpl.findBy(params);
        try {
            for (IDA ida : idas) {
                idaDAOImpl.delete(ida);
            }
            return true;
        } catch (Exception e) {
            log.error(e, e);
        }
        return false;
    }

}

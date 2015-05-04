package com.dc.esb.servicegov.controller;

import com.dc.esb.servicegov.entity.OLA;
import com.dc.esb.servicegov.entity.Operation;
import com.dc.esb.servicegov.entity.SDA;
import com.dc.esb.servicegov.entity.SLA;
import com.dc.esb.servicegov.service.*;
import com.dc.esb.servicegov.vo.OperationInfoVO;
import com.dc.esb.servicegov.vo.SDAVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/operationInfo")
public class OperationInfoController {
    private static final Log log = LogFactory.getLog(OperationInfoController.class);
    @Autowired
    private OperationManager operationManager;
    @Autowired
    private SDAManager sdaManager;
    @Autowired
    private SLAManager slaManager;
    @Autowired
    private OLAManager olaManager;
    @Autowired
    private InvokeInfoManager invokeManager;

    List<SDA> sdaList = new ArrayList<SDA>();

    private String indentSpace = "";


    /**
     * 获取所有场景
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/alloperations", headers = "Accept=application/json")
    public
    @ResponseBody
    List<OperationInfoVO> getAllOperationInfo() {
        List<OperationInfoVO> operationInfoVOInfos = new ArrayList<OperationInfoVO>();
        operationInfoVOInfos = operationManager.getAllOperations();
        return operationInfoVOInfos;
    }

    /**
     * 获取所有待审核的场景
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/auditList", headers = "Accept=application/json")
    public
    @ResponseBody
    List<Operation> getAuditOperation() {
        return operationManager.getAuditOperation();
    }

    /**
     * 根据服务Id和场景ID获取场景
     *
     * @param serviceId
     * @param operationId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getOperation/{serviceId}/{operationId}", headers = "Accept=application/json")
    public
    @ResponseBody
    Operation getOperation(@PathVariable("serviceId") String serviceId, @PathVariable("operationId") String operationId) {
        return operationManager.getOperation(operationId, serviceId);
    }

    /**
     * 根据服务Id和场景Id获取场景SDA
     *
     * @param serviceId
     * @param operationId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getSDAInfoByOperationId/{serviceId}/{operationId}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<SDA> getSDAInfoByOperationId(@PathVariable("serviceId") String serviceId, @PathVariable("operationId") String operationId) {
        try {
            sdaList = new ArrayList<SDA>();
            SDAVO sdaVO = operationManager.getSDAByOperation(operationId, serviceId);
            renderSDAVO(sdaVO);
        } catch (Exception e) {
            log.error("获取场景失败", e);
        }
        return sdaList;
    }

    /**
     * 根据服务Id和场景ID获取SLA
     *
     * @param serviceId
     * @param operationId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getSLAByOperationId/{serviceId}/{operationId}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<SLA> getSLAByOperationId(@PathVariable("serviceId") String serviceId, @PathVariable("operationId") String operationId) {
        return operationManager.getSLAByOperationId(operationId, serviceId);
    }

    /**
     * 根据服务ID和场景ID获取OLA
     *
     * @param serviceId
     * @param operationId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getOLAByOperationId/{serviceId}/{operationId}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<OLA> getOLAByOperationId(@PathVariable("serviceId") String serviceId, @PathVariable("operationId") String operationId) {
        return operationManager.getOLAByOperationId(operationId, serviceId);
    }

    /**
     * 根据服务ID和场景ID删除场景
     *
     * @param serviceId
     * @param operationId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/deleteOperation/{serviceId}/{operationId}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean deleteOperation(@PathVariable("serviceId") String serviceId, @PathVariable("operationId") String operationId) {
        boolean sdaSuccess = sdaManager.deleteSDA(operationId, serviceId);
        boolean slaSuccess = slaManager.deleteSLA(operationId, serviceId);
        boolean olaSuccess = olaManager.deleteOLA(operationId, serviceId);
        boolean invokeSuccess = invokeManager.deleteInvokeInfo(operationId, serviceId);
        boolean operationSuccess = operationManager.deleteOperation(operationId, serviceId);
        boolean isSuccess = sdaSuccess && slaSuccess && olaSuccess && operationSuccess && invokeSuccess;
        return isSuccess;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/addOperation", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean addOperation(@RequestBody Operation operation) {
        boolean operationSuccess = operationManager.saveOperation(operation);
        String operationId = operation.getOperationId();
        String serviceId = operation.getServiceId();
        boolean flag = false;
        if (operationSuccess) {
            flag = initSDA(operationId, serviceId);
        }
        return operationSuccess && flag;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/addSDA", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean addSDA(@RequestBody SDA[] sdaArray) {
        String operationId = sdaArray[0].getOperationId();
        String serviceId = sdaArray[0].getServiceId();
        boolean updateFlag = sdaManager.updateOperationVersion(operationId, serviceId);
        if (updateFlag) {
            boolean isDeleteSuccess = sdaManager.deleteSDA(operationId, serviceId);
            List<SDA> sdaList = new ArrayList<SDA>();
            for (int i = 0; i < sdaArray.length; i++) {
                sdaArray[i].setStructId(sdaArray[i].getStructId()
                        .substring(sdaArray[i].getStructId().lastIndexOf("|--") + 3));
                sdaList.add(sdaArray[i]);
            }
            if (isDeleteSuccess) {
                boolean sdaSuccess = sdaManager.saveSDA(sdaList);
                log.error("sda save is success :" + sdaSuccess);
                return sdaSuccess;
            } else {
                return false;
            }
        } else {
            log.error("error in update operation version ");
            return false;
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "/addSLA", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean addSLA(@RequestBody SLA[] slaArray) {
        String serviceId = slaArray[0].getServiceId();
        String operationId = slaArray[0].getOperationId();
        boolean deleteFlag = slaManager.deleteSLA(operationId, serviceId);
        List<SLA> slaList = new ArrayList<SLA>();
        for (int i = 0; i < slaArray.length; i++) {
            slaList.add(slaArray[i]);
        }
        if (deleteFlag) {
            boolean saveFlag = slaManager.saveSLA(slaList);
            return saveFlag;
        } else {
            return false;
        }
    }

    /**
     * 根据服务ID和场景ID删除SLA
     *
     * @param serviceId
     * @param operationId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/deleteSLA/{serviceId}/{operationId}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean deleteSLA(@PathVariable("serviceId") String serviceId, @PathVariable("operationId") String operationId) {
        boolean deleteFlag = slaManager.deleteSLA(operationId, serviceId);
        return deleteFlag;
    }

    /**
     * 根据服务ID和场景ID删除OLA
     *
     * @param serviceId
     * @param operationId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/deleteOLA/{serviceId}/{operationId}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean deleteOLA(@PathVariable("serviceId") String serviceId, @PathVariable("operationId") String operationId) {
        boolean deleteFlag = olaManager.deleteOLA(operationId, serviceId);
        return deleteFlag;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/addOLA", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean addOLA(@RequestBody OLA[] olaArray) {
        String operationId = olaArray[0].getOperationId();
        String serviceId = olaArray[0].getServiceId();
        boolean isDeleteSuccess = olaManager.deleteOLA(operationId, serviceId);
        List<OLA> olaList = new ArrayList<OLA>();
        for (int i = 0; i < olaArray.length; i++) {
            olaList.add(olaArray[i]);
        }
        if (isDeleteSuccess) {
            boolean slaSuccess = olaManager.saveOLA(olaList);
            return slaSuccess;
        } else {
            return false;
        }

    }

    public boolean initSDA(String operationId, String serviceId) {
        int sdaNum = operationManager.getSDAByOperationId(operationId, serviceId).size();
        List<SDA> sdaList = new ArrayList<SDA>();
        if (sdaNum <= 0) {
            SDA rootSda = new SDA();
            String rootId = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
            rootSda.setId(rootId);
            rootSda.setStructId("");
            rootSda.setMetadataId("");
            rootSda.setType("");
            rootSda.setSeq(1);
            rootSda.setStructId(operationId);
            rootSda.setOperationId(operationId);
            rootSda.setServiceId(serviceId);
            rootSda.setParentId("/");
            sdaList.add(rootSda);

            SDA requestSda = new SDA();
            String requestId = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
            requestSda.setId(requestId);
            requestSda.setSeq(2);
            requestSda.setStructId("");
            requestSda.setMetadataId("");
            requestSda.setType("");
            requestSda.setStructId("request");
            requestSda.setOperationId(operationId);
            requestSda.setServiceId(serviceId);
            requestSda.setParentId(rootId);
            sdaList.add(requestSda);

            SDA svcBodyRequestSda = new SDA();
            String svcBodyRequestId = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
            svcBodyRequestSda.setId(svcBodyRequestId);
            svcBodyRequestSda.setSeq(3);
            svcBodyRequestSda.setStructId("");
            svcBodyRequestSda.setMetadataId("");
            svcBodyRequestSda.setType("");
            svcBodyRequestSda.setStructId("SvcBody");
            svcBodyRequestSda.setOperationId(operationId);
            svcBodyRequestSda.setServiceId(serviceId);
            svcBodyRequestSda.setParentId(requestId);
            sdaList.add(svcBodyRequestSda);

            SDA responseSda = new SDA();
            String responseId = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
            responseSda.setId(responseId);
            responseSda.setSeq(4);
            responseSda.setStructId("");
            responseSda.setMetadataId("");
            responseSda.setType("");
            responseSda.setStructId("response");
            responseSda.setOperationId(operationId);
            responseSda.setServiceId(serviceId);
            responseSda.setParentId(rootId);
            sdaList.add(responseSda);

            SDA svcBodyResponseSda = new SDA();
            String svcBodyResponseId = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
            svcBodyResponseSda.setId(svcBodyResponseId);
            svcBodyResponseSda.setSeq(5);
            svcBodyResponseSda.setStructId("");
            svcBodyResponseSda.setMetadataId("");
            svcBodyResponseSda.setType("");
            svcBodyResponseSda.setStructId("SvcBody");
            svcBodyResponseSda.setOperationId(operationId);
            svcBodyResponseSda.setServiceId(serviceId);
            svcBodyResponseSda.setParentId(responseId);
            sdaList.add(svcBodyResponseSda);
        }
        return sdaManager.saveSDA(sdaList);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/deployOperation/{operationandservice}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean deployOperation(@PathVariable String operationandservice) {
        log.error("*************************************" + operationandservice);
        String operationId = operationandservice.substring(10);
        String serviceId = operationandservice.substring(0, 10);
        return operationManager.deployOperation(operationId, serviceId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/redefOperation/{operationandservice}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean redefOperation(@PathVariable String operationandservice) {
        String operationId = operationandservice.substring(10);
        String serviceId = operationandservice.substring(0, 10);
        return operationManager.redefOperation(operationId, serviceId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/publishOperation/{operationandservice}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean publishOperation(@PathVariable String operationandservice) {
        log.info("开始发布服务！");
        String operationId = operationandservice.substring(10);
        String serviceId = operationandservice.substring(0, 10);
        return operationManager.publishOperation(operationId, serviceId);
    }

    /**
     * 操作审核
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
                String operationId = arr[0];
                String serviceId = arr[1];
                String auditState = arr[2];
                operationManager.auditOperation(operationId, serviceId,
                        auditState);
            }
        } catch (Exception e) {
            log.error("审核操作出现错误!");
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
                String[] arr = param.split(",");
                String operationId = arr[0];
                String serviceId = arr[1];
                operationManager.submitOperation(operationId, serviceId);
            }
        } catch (Exception e) {
            log.error("审核操作出现错误!");
            return false;
        }
        return true;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getSDAByOperationService/{serviceId}/{operationId}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<SDA> getSDAByOperationService(@PathVariable("serviceId") String serviceId, @PathVariable("operationId") String operationId) {
        List<SDA> list = null;
        try {
            sdaList = new ArrayList<SDA>();
            list = operationManager.getSDAByOperationId(operationId, serviceId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/randomUUID", headers = "Accept=application/json")
    public
    @ResponseBody
    UUIDElement randomUUID() {
        UUIDElement uuidElement = new UUIDElement();
        uuidElement.setValue(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
        return uuidElement;
    }

    private static class UUIDElement {
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }


    @RequestMapping(method = RequestMethod.POST, value = "/addSDANew", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean addSDANew(@RequestBody SDA[] sdaArray) {
        String operationId = sdaArray[0].getOperationId();
        String serviceId = sdaArray[0].getServiceId();
        boolean updateFlag = sdaManager.updateOperationVersion(operationId, serviceId);
        if (updateFlag) {
            boolean isDeleteSuccess = sdaManager.deleteSDA(operationId, serviceId);
            List<SDA> sdaList = new ArrayList<SDA>();
            for (int i = 0; i < sdaArray.length; i++) {
                sdaList.add(sdaArray[i]);
            }
            if (isDeleteSuccess) {
                boolean sdaSuccess = sdaManager.saveSDA(sdaList);
                log.error("sda save is success :" + sdaSuccess);
                return sdaSuccess;
            } else {
                return false;
            }
        } else {
            log.error("error in update operation version ");
            return false;
        }

    }

    private void renderSDAVO(SDAVO sda) {
        SDA node = sda.getValue();
        node.setStructId("|--" + node.getStructId());
        node.setStructId(this.indentSpace + node.getStructId());
        sdaList.add(node);
        if (sda.getChildNode() != null) {
            List<SDAVO> childSda = sda.getChildNode();
            String tmpIndent = this.indentSpace;
            this.indentSpace += "&nbsp;&nbsp;&nbsp;&nbsp;";
            for (SDAVO a : childSda) {
                renderSDAVO(a);
            }
            this.indentSpace = tmpIndent;
        }
    }
}
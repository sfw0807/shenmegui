package com.dc.esb.servicegov.controller;

import com.dc.esb.servicegov.dao.impl.SDAHistoryDAOImpl;
import com.dc.esb.servicegov.entity.OperationHistory;
import com.dc.esb.servicegov.entity.SDAHistory;
import com.dc.esb.servicegov.entity.SLAHistory;
import com.dc.esb.servicegov.service.*;
import com.dc.esb.servicegov.vo.SDAHistoryVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/operationHistory")
public class OperationHistoryController {
    private static final Log log = LogFactory.getLog(OperationHistoryController.class);
    @Autowired
    private OperationHistoryManager operationManager;
    List<SDAHistory> sdaList = new ArrayList<SDAHistory>();
    private String indentSpace = "";
    @Autowired
    private OperationManager opManager;
    @Autowired
    private SDAManager sdaManager;
    @Autowired
    private SLAManager slaManager;
    @Autowired
    private OLAManager olaManager;
    @Autowired
    private SDAHistoryDAOImpl sdaHistoryDao;

    @RequestMapping(method = RequestMethod.GET, value = "/allHistory/{serviceId}/{operationId}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<OperationHistory> getAllHistoryVersion(@PathVariable(value="serviceId") String serviceId, @PathVariable(value = "operationId") String operationId) {
        return operationManager.getAllHistoryOperation(operationId, serviceId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/getOperation", headers = "Accept=application/json")
    public
    @ResponseBody
    OperationHistory getOperation(@RequestBody String[] params) {
        return operationManager.getOperation(params[0], params[1], params[2]).get(0);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/getSDA", headers = "Accept=application/json")
    public
    @ResponseBody
    List<SDAHistory> getSDAInfoByOperationId(@RequestBody String[] params) {
        try {
            sdaList = new ArrayList<SDAHistory>();
            SDAHistoryVO sdaVO = operationManager.getSDA(params[0], params[1], params[2]);
            renderSDAVO(sdaVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sdaList;
    }

    private void renderSDAVO(SDAHistoryVO sda) {
        SDAHistory node = sda.getValue();
        node.setStructId("|--" + node.getStructId());
        node.setStructId(this.indentSpace + node.getStructId());
        sdaList.add(node);
        if (sda.getChildNode() != null) {
            List<SDAHistoryVO> childSda = sda.getChildNode();
            String tmpIndent = this.indentSpace;
            this.indentSpace += "&nbsp;&nbsp;&nbsp;&nbsp;";
            for (SDAHistoryVO a : childSda) {
                renderSDAVO(a);
            }
            this.indentSpace = tmpIndent;
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/getSLA", headers = "Accept=application/json")
    public
    @ResponseBody
    List<SLAHistory> getSLA(@RequestBody String[] params) {
        return operationManager.getSLA(params[0], params[1], params[2]);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/getOLA", headers = "Accept=application/json")
    public
    @ResponseBody
    List<SLAHistory> getOLA(@RequestBody String[] params) {
        return operationManager.getSLA(params[0], params[1], params[2]);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/backOperation", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean backOperation(@RequestBody String[] params) {
        boolean sdaFlag = sdaManager.deleteSDA(params[0], params[2]);
        boolean slaFlag = slaManager.deleteSLA(params[0], params[2]);
        boolean olaFlag = olaManager.deleteOLA(params[0], params[2]);
        boolean operationFlag = opManager.deleteOperation(params[0], params[2]);
        boolean flag = operationFlag && sdaFlag && slaFlag && olaFlag;
        if (flag) {
            return operationManager.backOperation(params[0], params[1], params[2]);
        } else {
            return false;
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/getSDANew", headers = "Accept=application/json")
    public
    @ResponseBody
    List<SDAHistory> getSDAInfoNew(@RequestBody String[] params) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("operationId", params[0]);
        paramMap.put("operationVersion", params[2]);
        paramMap.put("serviceId", params[1]);
        List<SDAHistory> nodes = sdaHistoryDao.findBy(paramMap, "seq");
        return nodes;
    }
}

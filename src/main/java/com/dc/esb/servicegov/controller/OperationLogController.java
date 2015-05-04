package com.dc.esb.servicegov.controller;

import com.dc.esb.servicegov.entity.LogInfo;
import com.dc.esb.servicegov.service.LogManager;
import com.dc.esb.servicegov.vo.LogInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/logquery")
public class OperationLogController {

    @Autowired
    private LogManager logManager;

    @RequestMapping(method = RequestMethod.GET, value = "/getLogList", headers = "Accept=application/json")
    public
    @ResponseBody
    List<LogInfoVO> getInterfaceManagermentInfo() {
        List<LogInfo> list = logManager.getLogList();
        List<LogInfoVO> voList = new ArrayList<LogInfoVO>();
        if (list != null && list.size() > 0) {
            for (LogInfo logInfo : list) {
                LogInfoVO vo = new LogInfoVO(logInfo);
                voList.add(vo);
            }
        }
        return voList;
    }
}

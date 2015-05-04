package com.dc.esb.servicegov.controller;


import com.dc.esb.servicegov.service.impl.PublishViewManagerImpl;
import com.dc.esb.servicegov.vo.PublishTotalVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/publishView")
public class PublishViewController {

    @SuppressWarnings("unused")
    private Log log = LogFactory.getLog(getClass());
    @Autowired
    private PublishViewManagerImpl publishViewManager;


    /**
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/total/{onlineDate}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<PublishTotalVO> insertOrUpdate(HttpServletRequest request,
                                        HttpServletResponse response, @PathVariable String onlineDate) {
        if ("all".equals(onlineDate)) {
            onlineDate = "";
        } else {
            onlineDate = onlineDate.replace("-", "");
        }
        List<PublishTotalVO> list = new ArrayList<PublishTotalVO>();
        try {
            PublishTotalVO vo = new PublishTotalVO();
            vo.setCountOfConsumerSys(publishViewManager.countOfConsumerSys(onlineDate));
            vo.setCountOfDeletedService(publishViewManager.countOfDeletedService(onlineDate));
            vo.setCountOfModifyTimes(publishViewManager.countOfModifyTimes(onlineDate));
            vo.setCountOfOffLine(publishViewManager.countOfOffLine(onlineDate));
            vo.setCountOfOperation(publishViewManager.countOfOperation(onlineDate));
            vo.setCountOfProviderSys(publishViewManager.countOfProviderSys(onlineDate));
            vo.setCountOfPublishTimes(publishViewManager.countOfPublishTimes(onlineDate));
            vo.setCountOfService(publishViewManager.countOfService(onlineDate));
            list.add(vo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}

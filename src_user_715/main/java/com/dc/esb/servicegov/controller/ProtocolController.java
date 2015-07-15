package com.dc.esb.servicegov.controller;

import com.dc.esb.servicegov.dao.support.Page;
import com.dc.esb.servicegov.dao.support.SearchCondition;
import com.dc.esb.servicegov.entity.Protocol;
import com.dc.esb.servicegov.service.ProtocolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Administrator on 2015/7/3.
 */

@Controller
@RequestMapping("/protocol")
public class ProtocolController {

    @Autowired
    ProtocolService protocolService;


    @RequestMapping(method = RequestMethod.POST, value = "/getAll", headers = "Accept=application/json")
    public @ResponseBody
    Map<String,Object> getAll(HttpServletRequest req) {

        String starpage = req.getParameter("page");
        String rows = req.getParameter("rows");

        List<SearchCondition> searchConds = new ArrayList<SearchCondition>();

        StringBuffer hql = new StringBuffer("FROM Protocol t1 WHERE 1=1");

        String protocolName = req.getParameter("protocolName");
        String encoding = req.getParameter("encoding");
        String msgType = req.getParameter("msgType");
        String remark = req.getParameter("remark");
        if(protocolName!=null&&!"".equals(protocolName)){
            hql.append(" AND t1.protocolName like ?");
            SearchCondition search = new SearchCondition();
            search.setFieldValue("%"+protocolName+"%");
            searchConds.add(search);
        }
        if(encoding!=null&&!"".equals(encoding)){
            hql.append(" AND t1.encoding = ?");
            SearchCondition search = new SearchCondition();
            search.setFieldValue(encoding);
            searchConds.add(search);
        }
        if(msgType!=null&&!"".equals(msgType)){
            hql.append(" AND t1.msgType = ?");
            SearchCondition search = new SearchCondition();
            search.setFieldValue(msgType);
            searchConds.add(search);
        }
        if(remark!=null&&!"".equals(remark)){
            hql.append(" AND t1.remark like ?");
            SearchCondition search = new SearchCondition();
            search.setFieldValue("%"+remark+"%");
            searchConds.add(search);
        }

        SearchCondition searchCond = new SearchCondition();

        Page page = protocolService.findPage(hql.toString(), Integer.parseInt(rows), searchConds);
        page.setPage(Integer.parseInt(starpage));

        List<Protocol> protocols = protocolService.findBy(hql.toString(),page,searchConds);
        //List<Protocol> resList = new ArrayList<Protocol>();

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("total", page.getResultCount());
        map.put("rows", protocols);
        return map;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add", headers = "Accept=application/json")
    public @ResponseBody
        boolean add(@RequestBody Protocol protocol) {
        if(protocol.getMsgTemplateId()==null || "".equals(protocol.getMsgTemplateId())) {
            String msgtemplateId = UUID.randomUUID().toString();
            protocol.setMsgTemplateId(msgtemplateId);
            protocol.getMsgTemplate().setTemplateId(msgtemplateId);
            protocol.getMsgTemplate().setTemplateName(protocol.getProtocolName() + "协议模板");
        }
        protocolService.save(protocol);
        return true;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/delete/{protocolId}", headers = "Accept=application/json")
    public @ResponseBody
    boolean delete (@PathVariable String protocolId) {
        protocolService.deleteById(protocolId);
        return true;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/edit/{protocolId}", headers = "Accept=application/json")
    public ModelAndView getSystem(@PathVariable
                                  String protocolId) {

      Protocol protocol = protocolService.getById(protocolId);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("protocol", protocol);
        modelAndView.setViewName("sysadmin/protocol_edit");
        return modelAndView;
    }

}

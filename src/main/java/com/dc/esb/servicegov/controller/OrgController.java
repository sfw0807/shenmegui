package com.dc.esb.servicegov.controller;

import com.dc.esb.servicegov.entity.Organization;
import com.dc.esb.servicegov.service.OrgManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/org")
public class OrgController {
    private Log log = LogFactory.getLog(OrgController.class);
    @Autowired
    private OrgManager orgManager;

    @RequestMapping(method = RequestMethod.GET, value = "/list", headers = "Accept=application/json")
    public
    @ResponseBody
    List<Organization> getAll() {
        List<Organization> orgList = orgManager.getAll();
//		for (Function function:functionList) {
//			FunctionVO functionVO = new FunctionVO();
//			functionVO.setId(function.getId());
//			functionVO.setName(function.getName());
//			functionVO.setRemark(function.getRemark());
//			functionVO.setParent(functionManager.getFunctionNameById(function.getParentId()));
//			functionVO.setUrl(function.getUrl());
//			functionVOList.add(functionVO);
//		}
        return orgList;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean saveOrg(@RequestBody String[] param) {
        Organization org = new Organization();
        org.setOrgId(param[0]);
        org.setOrgName(param[2]);
        org.setOrgAB(param[1]);
        org.setOrgStatus(param[3]);
        return orgManager.save(org);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/delete/{orgId}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean deleteOrg(@PathVariable String orgId) {
        Organization org = orgManager.getOrgById(orgId);
        return orgManager.delete(org);
    }
}

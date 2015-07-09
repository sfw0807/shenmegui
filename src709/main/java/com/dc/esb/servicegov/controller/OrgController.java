package com.dc.esb.servicegov.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.dc.esb.servicegov.entity.Organization;
import com.dc.esb.servicegov.service.impl.OrgServiceImpl;

@Controller
@RequestMapping("/org")
public class OrgController {
	@Autowired
	private OrgServiceImpl orgServiceImpl;
	@RequestMapping(method = RequestMethod.POST, value = "/add", headers = "Accept=application/json")
	public @ResponseBody
	boolean add(@RequestBody Organization org) {
		orgServiceImpl.save(org);
		return true;
	}
	
    @RequestMapping(method = RequestMethod.GET, value = "/getAll", headers = "Accept=application/json")
    public
    @ResponseBody
    List<Organization> getAll() {
        List<Organization> org = orgServiceImpl.getAll();
        return org;
    }
    
    
}

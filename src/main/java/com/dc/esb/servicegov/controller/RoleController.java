package com.dc.esb.servicegov.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.dc.esb.servicegov.entity.Role;
import com.dc.esb.servicegov.service.impl.RoleServiceImpl;

@Controller
@RequestMapping("/role")
public class RoleController {
	@Autowired
	private RoleServiceImpl roleServiceImpl;
	@RequestMapping(method = RequestMethod.POST, value = "/add", headers = "Accept=application/json")
	public @ResponseBody
	boolean add(@RequestBody Role role) {
		roleServiceImpl.save(role);
		return true;
	}
	
    @RequestMapping(method = RequestMethod.GET, value = "/getAll", headers = "Accept=application/json")
    public
    @ResponseBody
    List<Role> getAll() {
        List<Role> role = roleServiceImpl.getAll();
        for(Role r :role){
        	r.setUserRoleRelations(null);
        }
        return role;
    }
    
    
}

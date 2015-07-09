package com.dc.esb.servicegov.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dc.esb.servicegov.dao.support.Page;
import com.dc.esb.servicegov.dao.support.SearchCondition;
import com.dc.esb.servicegov.entity.SGUser;
import com.dc.esb.servicegov.entity.UserRoleRelation;
import com.dc.esb.servicegov.service.impl.UserServiceImpl;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserServiceImpl userServiceImpl;
	@RequestMapping(method = RequestMethod.POST, value = "/add", headers = "Accept=application/json")
	public @ResponseBody
	boolean add(@RequestBody SGUser SGUser) {
		userServiceImpl.insert(SGUser);
		return true;
	}
	
    @RequestMapping(method = RequestMethod.GET, value = "/getAll", headers = "Accept=application/json")
    public @ResponseBody
	Map<String,Object> getAll(String operationId,HttpServletRequest req) {
		String starpage = req.getParameter("page");
	    String rows = req.getParameter("rows");
	    StringBuffer hql = new StringBuffer("select user from User user");
	    List<SearchCondition> searchConds = new ArrayList<SearchCondition>();
	    Page page = userServiceImpl.findPage(hql.toString(), Integer.parseInt(rows), searchConds);
        page.setPage(Integer.parseInt(starpage));
        List<SGUser> SGUser = userServiceImpl.findBy(hql.toString(),page,searchConds);
		Map<String,Object> resMap = new HashMap<String,Object>();
		resMap.put("total", page.getResultCount());
		resMap.put("rows", SGUser);
		 for(SGUser u : SGUser){
	        	u.setUserRoleRelations(null);
	        }
		return resMap;
	}
    
    @RequestMapping(method = RequestMethod.GET, value = "/get/id/{id}/name/{name}/orgId/{orgId}/startdate/{startdate}/lastdate/{lastdate}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<SGUser> getByName(@PathVariable(value = "id") String id, @PathVariable(value = "name") String name, @PathVariable(value = "orgId") String orgId, @PathVariable(value = "startdate") String startdate, @PathVariable(value = "lastdate") String lastdate) {
        Map<String, String> params = new HashMap<String, String>();
        if (!"itisanuniquevaluethatneverbeexisted".equals(id))
            params.put("id", id);
        if (!"itisanuniquevaluethatneverbeexisted".equals(name))
            params.put("name", name);
        if (!"itisanuniquevaluethatneverbeexisted".equals(orgId))
            params.put("orgId", orgId);
        if (!"itisanuniquevaluethatneverbeexisted".equals(lastdate))
            params.put("orgId", lastdate);
        if (!"itisanuniquevaluethatneverbeexisted".equals(startdate))
            params.put("startdate", startdate);
        List<SGUser> SGUser = userServiceImpl.findBy(params);
        for(SGUser u : SGUser){
        	u.setUserRoleRelations(null);
        }
        return SGUser;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/{id}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean delete(@PathVariable String id) {
    	userServiceImpl.delete(id);
        return true;
    }
    @RequestMapping(method = RequestMethod.GET, value = "/getById/{id}", headers = "Accept=application/json")
    public
    ModelAndView getById(
            @PathVariable String id) {
    	SGUser SGUser =  userServiceImpl.getById(id);
    	ModelAndView model = new ModelAndView();
    	model.addObject("user", SGUser);
    	model.setViewName("user/userEdit");
    	
    	List<UserRoleRelation> rs =  SGUser.getUserRoleRelations();
    	String roleId = "";
    	for(UserRoleRelation us :rs)
    	{
    		roleId +=us.getRole().getId()+",";		  	
    	}
    	roleId.substring(0,roleId.length()-1);
    	model.addObject("roleId", roleId);
    	//model.setViewName(viewName)
        return model;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/modify", headers = "Accept=application/json")
	public @ResponseBody
	boolean modify(@RequestBody SGUser SGUser) {
    	userServiceImpl.update(SGUser);
		return true;
	}
    
    @RequestMapping(method = RequestMethod.GET, value = "/getByPW/{id}", headers = "Accept=application/json")
    public
    ModelAndView getByPW(
            @PathVariable String id) {
    	SGUser SGUser =  userServiceImpl.getById(id);
    	ModelAndView model = new ModelAndView();
    	model.addObject("user", SGUser);
    	model.setViewName("user/passWord");
        return model;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/passWord", headers = "Accept=application/json")
	public @ResponseBody
	boolean passWord(@RequestBody SGUser SGUser) {
    	userServiceImpl.passWord(SGUser);
		return true;
	}
}

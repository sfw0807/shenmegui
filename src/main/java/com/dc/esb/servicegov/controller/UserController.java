package com.dc.esb.servicegov.controller;

import com.dc.esb.servicegov.dao.support.Page;
import com.dc.esb.servicegov.dao.support.SearchCondition;
import com.dc.esb.servicegov.entity.SGUser;
import com.dc.esb.servicegov.entity.UserRoleRelation;
import com.dc.esb.servicegov.service.impl.RoleServiceImpl;
import com.dc.esb.servicegov.service.impl.UserRoleRelationServiceImpl;
import com.dc.esb.servicegov.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private UserRoleRelationServiceImpl userRoleRelationService;
    @Autowired
    private RoleServiceImpl roleService;

    @RequestMapping(method = RequestMethod.POST, value = "/add", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean add(@RequestBody SGUser user) {
        userServiceImpl.save(user);
        return true;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/assignRoles", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean add(@RequestBody UserRoleRelation[] userRoleRelations) {
        for(UserRoleRelation userRoleRelation : userRoleRelations){
            userRoleRelationService.save(userRoleRelation);
        }
        return true;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAll", headers = "Accept=application/json")
    public
    @ResponseBody
    Map<String, Object> getAll(String operationId, HttpServletRequest req) {
        String starpage = req.getParameter("page");
        String rows = req.getParameter("rows");
        StringBuffer hql = new StringBuffer("select user from SGUser user");
        List<SearchCondition> searchConds = new ArrayList<SearchCondition>();
        Page page = userServiceImpl.findPage(hql.toString(), Integer.parseInt(rows), searchConds);
        page.setPage(Integer.parseInt(starpage));
        List<SGUser> SGUser = userServiceImpl.findBy(hql.toString(), page, searchConds);
        Map<String, Object> resMap = new HashMap<String, Object>();
        resMap.put("total", page.getResultCount());
        resMap.put("rows", SGUser);
        return resMap;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/query", headers = "Accept=application/json")
    public
    @ResponseBody
    List<SGUser> getByName( @RequestBody Map<String, String> params) {
        return userServiceImpl.findLikeAnyWhere(params);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/{id}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean delete(@PathVariable String id) {
        userServiceImpl.deleteById(id);
        return true;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getById/{id}", headers = "Accept=application/json")
    public ModelAndView getById(
            @PathVariable String id) {
        SGUser user = userServiceImpl.getById(id);
        ModelAndView model = new ModelAndView();
        model.addObject("user", user);
        model.setViewName("user/userEdit");

        List<UserRoleRelation> rs = userRoleRelationService.findBy("userId", user.getId());
        String roleId = "";
        for (UserRoleRelation us : rs) {
            roleId += us.getRoleId() + ",";
        }
        roleId.substring(0, roleId.length() - 1);
        model.addObject("roleId", roleId);
        return model;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/modify", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean modify(@RequestBody SGUser SGUser) {
        userServiceImpl.update(SGUser);
        return true;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getByPW/{id}", headers = "Accept=application/json")
    public ModelAndView getByPW(
            @PathVariable String id) {
        SGUser SGUser = userServiceImpl.getById(id);
        ModelAndView model = new ModelAndView();
        model.addObject("user", SGUser);
        model.setViewName("user/passWord");
        return model;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/passWord", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean passWord(@RequestBody SGUser SGUser) {
        userServiceImpl.save(SGUser);
        return true;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/checkUnique/userId/{userId}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean passWord(@PathVariable("userId") String userId) {
        SGUser user = userServiceImpl.getById(userId);
        if(null != user){

        }
        return true;
    }
}

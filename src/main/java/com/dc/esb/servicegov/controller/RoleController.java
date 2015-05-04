package com.dc.esb.servicegov.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dc.esb.servicegov.entity.Role;
import com.dc.esb.servicegov.entity.RoleFunctionRelate;
import com.dc.esb.servicegov.service.RoleManager;
import com.dc.esb.servicegov.vo.RoleVO;

@Controller
@RequestMapping("/role")
public class RoleController {
    private Log log = LogFactory.getLog(RoleController.class);
    @Autowired
    private RoleManager roleManager;

    @RequestMapping(method = RequestMethod.GET, value = "/list", headers = "Accept=application/json")
    public
    @ResponseBody
    List<RoleVO> getAll() {
        List<Role> roleList = roleManager.getAll();
        List<RoleVO> roleVOList = new ArrayList<RoleVO>();
        for (Role role : roleList) {
            RoleVO roleVo = new RoleVO();
            roleVo.setId(role.getId() + "");
            roleVo.setName(role.getName());
            roleVo.setRemark(role.getRemark());
            roleVOList.add(roleVo);
        }
        return roleVOList;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean saveRole(@RequestBody String[] param) {
        Role role = new Role();
        role.setId(roleManager.getMaxId() + 1);
        role.setName(param[0]);
        role.setRemark(param[1]);
        return roleManager.saveRole(role);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/delete/{param}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean saveRole(@PathVariable String param) {
        Role role = roleManager.getRoleById(param);
        return roleManager.deleteRole(role);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/assign", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean assignFunction(@RequestBody String[] param) {
        boolean saveFlag = false;
        String roleId = param[0];
        boolean deleteFlag = roleManager.deleteRoleFunctionRelate(roleId);
        Map<String, String> uniqueMap = new HashMap<String, String>();
        List<String> idList = new ArrayList<String>();
        if (deleteFlag) {
            for (int i = 1; i < param.length; i++) {
                if (!idList.contains(param[i])) {
                    idList.add(param[i]);
                }
            }
            for (String funcId : idList) {
                if (!"0".equals(funcId)) {
                    RoleFunctionRelate relat = new RoleFunctionRelate();
                    relat.setRoleId(Integer.parseInt(roleId));
                    relat.setFunctionId(Integer.parseInt(funcId));
                    roleManager.saveRoleFunctionRelate(relat);
                }
            }
            saveFlag = true;
        } else {
            log.error("删除旧的角色列表失败");
        }
        return saveFlag;
    }
}

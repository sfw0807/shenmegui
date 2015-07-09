package com.dc.esb.servicegov.controller;

import com.dc.esb.servicegov.entity.*;
import com.dc.esb.servicegov.service.impl.SystemServiceImpl;
import com.dc.esb.servicegov.util.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vincentfxz on 15/7/9.
 */
@Controller
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private SystemServiceImpl systemService;

    @RequestMapping(method = RequestMethod.GET, value = "/getTree", headers = "Accept=application/json")
    public
    @ResponseBody
    List<TreeNode> getAllTreeBean() {
        List<TreeNode> systemTree = new ArrayList<TreeNode>();
        List<com.dc.esb.servicegov.entity.System> systems = systemService.getAll();
        for(com.dc.esb.servicegov.entity.System system : systems){
            TreeNode systemTreeNode = new TreeNode();
            systemTreeNode.setId(system.getSystemId());
            systemTreeNode.setText(system.getSystemChineseName());
            systemTree.add(systemTreeNode);
        }
        return systemTree;
    }
}

package com.dc.esb.servicegov.controller;

import com.dc.esb.servicegov.exception.DataException;
import com.dc.esb.servicegov.service.impl.TreeMenuManagerImpl;
import com.dc.esb.servicegov.vo.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Vincent Fan
 * Date: 14-7-2
 * Time: 下午5:03
 */
@Controller
@RequestMapping("/menu")
public class TreeController {

    @Autowired
    private TreeMenuManagerImpl treeMenuManager;

    @RequestMapping(method = RequestMethod.GET, value = "/tree", headers = "Accept=application/json")
    private
    @ResponseBody
    List<TreeNode> getMenuTree() throws DataException {
        List<TreeNode> treeNodes = treeMenuManager.getMenuTreeNodes();
        return treeNodes;
    }
}

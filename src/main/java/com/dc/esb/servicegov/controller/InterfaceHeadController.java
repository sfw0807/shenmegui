package com.dc.esb.servicegov.controller;

import com.dc.esb.servicegov.entity.Ida;
import com.dc.esb.servicegov.entity.InterfaceHead;
import com.dc.esb.servicegov.service.IdaService;
import com.dc.esb.servicegov.service.InterfaceHeadService;
import com.dc.esb.servicegov.util.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/interfaceHead")
public class InterfaceHeadController {

    @Autowired
    private InterfaceHeadService interfaceHeadService;

    @Autowired
    private IdaService idaService;

    @RequestMapping(method = RequestMethod.GET, value = "/getAll", headers = "Accept=application/json")
    public
    @ResponseBody
    List<TreeNode> getAll() {
        List<InterfaceHead> heads = interfaceHeadService.getAll();

        List<TreeNode> trees = new ArrayList<TreeNode>();
        TreeNode root = new TreeNode();
        root.setId("root");
        root.setText("报文头管理");
        for (InterfaceHead head : heads) {
            TreeNode tree = new TreeNode();
            tree.setId(head.getHeadId());
            tree.setText(head.getHeadName());
            trees.add(tree);
        }
        root.setChildren(trees);
        List<TreeNode> rootList = new ArrayList<TreeNode>();
        rootList.add(root);
        return rootList;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean save(@RequestBody
                 InterfaceHead head) {
        boolean isAdd = false || (head.getHeadId() == null || "".equals(head.getHeadId()));
        interfaceHeadService.save(head);
        //添加报文，自动生成固定报文头<root><request><response>
        if (isAdd) {
            interfaceHeadService.initHDA(head);
        }
        return true;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/edit/{headId}", headers = "Accept=application/json")
    public ModelAndView getInterfaceHead(@PathVariable
                                         String headId) {
        InterfaceHead head = interfaceHeadService.getById(headId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("head", head);
        modelAndView.setViewName("sysadmin/header_edit");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/delete/{headId}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean delete(@PathVariable
                   String headId) {
        interfaceHeadService.deleteById(headId);
        Map<String, String> params = new HashMap<String, String>();
        params.put("headId", headId);
        List<Ida> idas = idaService.findBy(params);
        for (Ida ida : idas) {
            idaService.deleteById(ida.getId());
        }
        return true;
    }
}

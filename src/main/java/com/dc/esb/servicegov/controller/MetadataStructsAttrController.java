package com.dc.esb.servicegov.controller;

import com.dc.esb.servicegov.entity.MetadataStructsAttr;
import com.dc.esb.servicegov.service.impl.MetadataStructsAttrManagerImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/mdtStructsAttr")
public class MetadataStructsAttrController {
    private Log log = LogFactory.getLog(MetadataStructsAttrController.class);

    @Autowired
    private MetadataStructsAttrManagerImpl metadataStructsAttrManager;

    @RequestMapping(method = RequestMethod.GET, value = "/list", headers = "Accept=application/json")
    public
    @ResponseBody
    List<MetadataStructsAttr> getAll() {

        return metadataStructsAttrManager.getAll();
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.POST, value = "/update", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean update(@RequestBody
                   MetadataStructsAttr[] msaArr) {
        boolean flag = true;
        try {
            // for IE 8.0 above
            for (MetadataStructsAttr mdtAttr : msaArr) {
                mdtAttr.setIsRequired(("null".equals(mdtAttr.getIsRequired()) || mdtAttr.getIsRequired() == null) ? "" : mdtAttr.getIsRequired());
                mdtAttr.setRemark(("null".equals(mdtAttr.getRemark()) || mdtAttr.getRemark() == null) ? "" : mdtAttr.getRemark());
            }
            if (msaArr != null && msaArr.length > 0) {
                // 删除所有属性
                metadataStructsAttrManager.delByStructId(msaArr[0]
                        .getStructId());
                // 批量插入或更新元数据结构属性信息
                metadataStructsAttrManager.batchInsertOrUpdate(msaArr);
            }
        } catch (Exception e) {
            log.error("update MetadataStructsfailed!", e);
            flag = false;
        }
        return flag;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.POST, value = "/insert", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean insert(@RequestBody
                   MetadataStructsAttr[] msaArr) {
        boolean flag = true;
        try {
            if (msaArr != null && msaArr.length > 0) {
                // 批量插入或更新元数据结构属性信息
                metadataStructsAttrManager.batchInsertOrUpdate(msaArr);
            }
        } catch (Exception e) {
            log.error("update MetadataStructsfailed!", e);
            flag = false;
        }
        return flag;
    }

}
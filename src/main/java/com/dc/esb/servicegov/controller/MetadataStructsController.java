package com.dc.esb.servicegov.controller;

import com.dc.esb.servicegov.entity.MetadataStructs;
import com.dc.esb.servicegov.entity.MetadataStructsAttr;
import com.dc.esb.servicegov.service.impl.MetadataStructsManagerImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/mdtStructs")
public class MetadataStructsController {
    private Log log = LogFactory.getLog(MetadataStructsController.class);

    @Autowired
    private MetadataStructsManagerImpl metadataStructsManager;

    @RequestMapping(method = RequestMethod.GET, value = "/list", headers = "Accept=application/json")
    public
    @ResponseBody
    List<MetadataStructs> getAll() {

        return metadataStructsManager.getAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/delete/{id}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean delete(@PathVariable
                   String id) {
        boolean flag = true;
        try {
            if (id.contains(",")) {
                String[] idArr = id.split(",");
                int i = 0;
                while (i < idArr.length) {
                    metadataStructsManager.delById(idArr[i]);
                    i++;
                }
            } else {
                metadataStructsManager.delById(id);
            }
        } catch (Exception e) {
            log.error("delete metadataStructs" + id + "failed!", e);
            flag = false;
        }
        return flag;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/insert", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean insert(@RequestBody
                   MetadataStructs metadataStructs) {
        boolean flag = true;
        try {
            metadataStructsManager.insertOrUpdate(metadataStructs);
        } catch (Exception e) {
            log.error("insert MetadataStructs" + metadataStructs.getStructId() + "failed!", e);
            flag = false;
        }
        return flag;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/update", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean update(@RequestBody
                   MetadataStructs metadataStructs) {
        boolean flag = true;
        try {
            metadataStructsManager.insertOrUpdate(metadataStructs);
        } catch (Exception e) {
            log.error("update MetadataStructs" + metadataStructs.getStructId() + "failed!", e);
            flag = false;
        }
        return flag;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getMetadataStructsById/{id}", headers = "Accept=application/json")
    public
    @ResponseBody
    MetadataStructs getMetadataStructsById(@PathVariable
                                           String id) {
        MetadataStructs metadataStructs = new MetadataStructs();
        try {
            metadataStructs = metadataStructsManager.getMetadataStructsById(id);
        } catch (Exception e) {
            log.error("get system" + metadataStructs.getStructId() + "failed!", e);
        }
        return metadataStructs;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/checkIsUsed/{id}", headers = "Accept=application/json")
    public
    @ResponseBody
    String checkIsUsed(@PathVariable
                       String id) {
        if (id.contains(",")) {
            String[] idArr = id.split(",");
            int i = 0;
            while (i < idArr.length) {
                if (metadataStructsManager.isUsed(idArr[i])) {
                    return idArr[i];
                }
                i++;
            }
        } else {
            if (metadataStructsManager.isUsed(id)) {
                return id;
            }
        }
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getMdtStructsAttrByStructId/{id}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<MetadataStructsAttr> getMdtStructsAttrByStructId(@PathVariable
                                                          String id) {
        return metadataStructsManager.getMdtStructsAttrByStructId(id);
    }
}

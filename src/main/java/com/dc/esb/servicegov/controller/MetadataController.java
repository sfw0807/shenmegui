package com.dc.esb.servicegov.controller;

import com.dc.esb.servicegov.entity.Metadata;
import com.dc.esb.servicegov.service.impl.MetadataManagerImpl;
import com.dc.esb.servicegov.vo.MetadataUsedInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/metadata")
public class MetadataController {
    private Log log = LogFactory.getLog(MetadataController.class);

    @Autowired
    private MetadataManagerImpl metadataManager;

    /**
     * get all metadata list
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/list", headers = "Accept=application/json")
    public
    @ResponseBody
    List<Metadata> getAllMetadataList() {

        return metadataManager.getAllMetadata();
    }

    /**
     * check metadata exists
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/exist/{id}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean getMetadataById(HttpServletRequest request,
                            HttpServletResponse response, @PathVariable
    String id) {
        boolean flag = false;
        try {
            Metadata metadata = metadataManager.getMetadataById(id);
            if (metadata != null) {
                flag = true;
            }

        } catch (Exception e) {
            log.error("delete metadata failed!" + e);
        }
        return flag;
    }

    /**
     * delete metadata List
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/delete/{id}", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean deleteByMetadataIds(HttpServletRequest request,
                                HttpServletResponse response, @PathVariable
    String id) {
        boolean flag = true;
        try {
            if (id.contains(",")) {
                String[] idArr = id.split(",");
                int i = 0;
                while (i < idArr.length) {
                    metadataManager.delById(idArr[i]);
                    i++;
                }
            } else {
                metadataManager.delById(id);
            }
        } catch (Exception e) {
            log.error("delete metadata failed!" + e);
            flag = false;
        }
        return flag;
    }

    /**
     * Insert List
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/insert", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean insertByMetadata(HttpServletRequest request,
                             HttpServletResponse response, @RequestBody Metadata metadata) {
        boolean flag = true;
        try {
            metadataManager.insert(metadata);
        } catch (Exception e) {
            flag = false;
            log.error("insert metadata failed!" + e);
        }
        return flag;
    }

    /**
     * Update List
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/update", headers = "Accept=application/json")
    public
    @ResponseBody
    boolean updateByMetadataIds(HttpServletRequest request,
                                HttpServletResponse response, @RequestBody Metadata metadata) {
        boolean flag = true;
        try {

            metadata.setUpdateTime(getNowTime());
            metadataManager.updateEntity(metadata);
        } catch (Exception e) {
            flag = false;
            log.error("update metadata failed!" + e);
        }
        return flag;
    }

    /***
     *
     *获取当前时间
     *
     */
    public String getNowTime()
    {

        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String updateTime=format.format(date);

        return updateTime;
    }


    /**
     * mdtused List
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/mdtused/{id}", headers = "Accept=application/json")
    public
    @ResponseBody
    List<MetadataUsedInfo> getMdtUsedInfo(HttpServletRequest request,
                                          HttpServletResponse response, @PathVariable
    String id) {
        List<MetadataUsedInfo> list = null;
        try {
            list = metadataManager.getUsedInfoByMetadataId(id);
        } catch (Exception e) {
            log.error("get used metadata failed!" + e);
        }
        return list;
    }

    /**
     * check metadata is or nor used
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/checkIsUsed/{id}", headers = "Accept=application/json")
    public
    @ResponseBody
    String deleteByMetadataIds(@PathVariable
                               String id) {
        List<MetadataUsedInfo> list = null;
        try {
            if (id.contains(",")) {
                String[] idArr = id.split(",");
                int i = 0;
                while (i < idArr.length) {
                    list = metadataManager.getUsedInfoByMetadataId(idArr[i]);
                    if (list != null && list.size() > 0) {
                        return idArr[i];
                    }
                    i++;
                }
            } else {
                list = metadataManager.getUsedInfoByMetadataId(id);
                if (list != null && list.size() > 0) {
                    return id;
                }
            }
        } catch (Exception e) {
            log.error("delete metadata failed!" + e);
        }
        return null;
    }
}

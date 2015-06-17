package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.dao.impl.ConFilePathDAOImpl;
import com.dc.esb.servicegov.entity.ConFilePath;
import com.dc.esb.servicegov.service.ConFilePathManager;
import com.dc.esb.servicegov.vo.ConFilePathVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2015/5/13.
 */
@Service
@Transactional
public class ConFilePathManagerImpl implements ConFilePathManager {
    private Log log = LogFactory.getLog(super.getClass());


    @Autowired
    private ConFilePathDAOImpl conFilePathDAOImpl;


    /**
     * 获取导出配置文件的路径所有列表
     * */
    @Override
    public  List<ConFilePathVO> getAllConFilePathInfo() {
        if (this.log.isInfoEnabled())
            this.log.info("获取全部导出配置文件路径的接口信息...");

        List<ConFilePathVO> returnList = new ArrayList<ConFilePathVO>();
        List<ConFilePath> infos = conFilePathDAOImpl.getAll();
        if (infos != null && infos.size() > 0) {
            for (ConFilePath con : infos) {
                ConFilePathVO vo = new ConFilePathVO();
                vo.setName(con.getName());
                vo.setFilePath(con.getFilePath());
                vo.setUsername(con.getUsername());
                vo.setPassword(con.getPassword());
                returnList.add(vo);
            }

        }
        return returnList;
    }


    @Override
    public  List<ConFilePathVO> getPublishConFilePathInfo() {
        if (this.log.isInfoEnabled())
            this.log.info("获取导出配置文件路径的接口信息...");

        List<ConFilePathVO> returnList = new ArrayList<ConFilePathVO>();
        List<ConFilePath> infos = conFilePathDAOImpl.getAll();
        if (infos != null && infos.size() > 0) {
            for (ConFilePath con : infos) {
                ConFilePathVO vo = new ConFilePathVO();
                vo.setName(con.getName());
                vo.setFilePath(con.getFilePath());
                returnList.add(vo);
            }

        }
        return returnList;
    }

    public ConFilePath getMachineInfoByName(String name){
        return conFilePathDAOImpl.get(name);
    }





}

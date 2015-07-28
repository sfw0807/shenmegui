package com.dc.esb.servicegov.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.service.support.AbstractBaseService;
import com.dc.esb.servicegov.service.support.Constants;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.dao.impl.BaseLineDAOImpl;
import com.dc.esb.servicegov.entity.BaseLine;
import com.dc.esb.servicegov.entity.BaseLineVersionHisMapping;
import com.dc.esb.servicegov.util.DateUtils;

@Service
@Transactional
public class BaseLineServiceImpl extends AbstractBaseService<BaseLine, String> {
    @Autowired
    private BaseLineDAOImpl baseLineDAO;
    @Autowired
    private VersionServiceImpl versionServiceImpl;
    @Autowired
    private VersionHisServiceImpl versionHisServiceImpl;
    @Autowired
    private OperationServiceImpl operationServiceImpl;
    @Autowired
    private OperationHisServiceImpl operationHisServiceImpl;
    @Autowired
    private BVServiceImpl bvServiceImpl;
    @Autowired
    private ServiceInvokeServiceImpl serviceInvokeServiceImpl;

    // 查询已通过审核但未发布的场景列表

    /**
     *  TODO 这个方法未什么在这里
     * @return
     */
    public List<?> operationList() {
        return operationServiceImpl.getReleased();
    }


    public boolean release(HttpServletRequest req, String code, String blDesc, String versionHisIds) {
        //新建基线
        BaseLine bl = new BaseLine();
        bl.setBaseId(UUID.randomUUID().toString());
        bl.setCode(code);
        bl.setBlDesc(blDesc);
        bl.setOptDate(DateUtils.format(new Date()));
        String versionId = versionServiceImpl.addVersion(Constants.Version.TARGET_TYPE_BASELINE, bl.getBaseId(), Constants.Version.TARGET_TYPE_BASELINE);
        bl.setVersionId(versionId);
        baseLineDAO.save(bl);

        //保存基线版本关系
        if(!StringUtils.isEmpty(versionHisIds)){
        	  String[] vids = versionHisIds.split("\\,");
              for (String versionHisId : vids) {
                  BaseLineVersionHisMapping bvm = new BaseLineVersionHisMapping();
                  bvm.setBaseLineId(bl.getBaseId());
                  bvm.setVersionHisId(versionHisId);
                  bvServiceImpl.save(bvm);
              }
              
              versionHisServiceImpl.updateVerionHis(Constants.Version.TARGET_TYPE_BASELINE, vids);
        }
        
      return true;
    }

    public List<BaseLine> getBaseLine(String code, String blDesc) {
        
        return baseLineDAO.getBaseLine(code, blDesc);
    }

    public List<?> getBLOperationHiss(String baseId) {
        return operationHisServiceImpl.getBLOperationHiss(baseId);
    }

    @Override
    public HibernateDAO<BaseLine, String> getDAO() {
        return baseLineDAO;
    }
}

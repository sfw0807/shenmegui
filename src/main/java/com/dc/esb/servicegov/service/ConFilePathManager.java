package com.dc.esb.servicegov.service;

import com.dc.esb.servicegov.vo.ConFilePathVO;

import java.util.List;


/**
 * Created by Administrator on 2015/5/13.
 */
public abstract interface ConFilePathManager {

    public abstract List<ConFilePathVO> getAllConFilePathInfo();
    public abstract List<ConFilePathVO> getPublishConFilePathInfo();
}

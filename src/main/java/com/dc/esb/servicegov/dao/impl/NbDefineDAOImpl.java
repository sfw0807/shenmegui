package com.dc.esb.servicegov.dao.impl;

import com.dc.esb.servicegov.entity.NbDefine;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2015/5/6.
 */
@Repository
public class NbDefineDAOImpl extends HibernateDAO<NbDefine, String>{

    private Log log = LogFactory.getLog(NbDefineDAOImpl.class);



}

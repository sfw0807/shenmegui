package com.dc.esb.servicegov.dao.impl;

import com.dc.esb.servicegov.entity.ConFilePath;
import com.dc.esb.servicegov.vo.ConFilePathVO;
import com.dc.esb.servicegov.vo.InterfaceListVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2015/5/13.
 */
@Repository
public class ConFilePathDAOImpl extends HibernateDAO<ConFilePath, String>{

    private Log log = LogFactory.getLog(ConFilePathDAOImpl.class);


    /**
     * 获取name获取文件绝对路径
     *
     * @param interfaceId
     * @return
     */
    public List<ConFilePath> getFilePath(String interfaceId) {
//        String sql = "SELECT * FROM CON_FILE_PATH WHERE name='"+interfaceId+"'";
//        Query query = super.getSession().createSQLQuery(sql);
//        query.setResultTransformer(Transformers.aliasToBean(ConFilePathVO.class));
//        return query.list();

      //  String hql = "from ProtocolInfo where sysId = :sysId";
        String hql = "from CON_FILE_PATH where name = :interfaceId";
        Query query = getSession().createQuery(hql);
        query.setString("interfaceId", interfaceId);
        return query.list();
    }
}

package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.dao.impl.InvokeConnectionDAOImpl;
import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.entity.InvokeConnection;
import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vincentfxz on 15/7/13.
 */
public class InvokeConnectionServiceImpl extends AbstractBaseService<InvokeConnection, String> {

    @Autowired
    private InvokeConnectionDAOImpl invokeConnectionDAO;

    @Override
    public HibernateDAO<InvokeConnection, String> getDAO() {
        return invokeConnectionDAO;
    }

    /**
     * TODO 注意环形递归
     * 根据交易链路的某个起点 查询交易链路
     *
     * @param sourceId
     * @return
     */
    public List<InvokeConnection> getConnectionsStartWith(String sourceId) {
        List<InvokeConnection> connections = new ArrayList<InvokeConnection>();
        List<InvokeConnection> startConnections = getDAO().findBy("sourceId", sourceId);
        connections.addAll(startConnections);
        for (InvokeConnection invokeConnection : startConnections) {
            String targetId = invokeConnection.getTargetId();
            if (null != targetId) {
                connections.addAll(getConnectionsStartWith(targetId));
            }
        }
        return connections;
    }
}

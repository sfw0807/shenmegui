package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.dao.impl.InvokeConnectionDAOImpl;
import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.entity.InvokeConnection;
import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vincentfxz on 15/7/13.
 */
@Service
@Transactional
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

    /**
     * TODO 注意环形递归
     * 根据交易链路的某个子节点 查询交易链路血缘分析
     *
     * @param sourceId
     * @return
     */
    public List<InvokeConnection> getConnectionsEndWith(String targetId) {
        List<InvokeConnection> connections = new ArrayList<InvokeConnection>();
        List<InvokeConnection> endConnections = getDAO().findBy("targetId", targetId);
        connections.addAll(endConnections);
        for (InvokeConnection invokeConnection : endConnections) {
            String sourceId = invokeConnection.getSourceId();
            if (null != sourceId) {
                connections.addAll(getConnectionsEndWith(sourceId));
            }
        }
        return connections;
    }
}

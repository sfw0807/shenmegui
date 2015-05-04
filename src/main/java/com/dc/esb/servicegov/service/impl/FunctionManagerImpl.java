package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.dao.impl.FunctionDAOImpl;
import com.dc.esb.servicegov.entity.Function;
import com.dc.esb.servicegov.service.FunctionManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FunctionManagerImpl implements FunctionManager {
    private Log log = LogFactory.getLog(FunctionManagerImpl.class);
    @Autowired
    private FunctionDAOImpl functionDAO;

    public List<Function> getAll() {
        return functionDAO.getAll();
    }

    public String getFunctionNameById(String id) {
        Function function = functionDAO.findUniqueBy("id", Integer.parseInt(id));
        if (function != null) {
            return function.getName();
        } else {
            return "";
        }

    }

    public Integer getMaxId() {
        return functionDAO.getMaxId();
    }

    public boolean save(Function function) {
        boolean flag = false;
        try {
            functionDAO.save(function);
            flag = true;
        } catch (Exception e) {
            log.error(e, e);
        }
        return flag;
    }

    public Function getFunctionById(String functionId) {
        return functionDAO.findUniqueBy("id", Integer.parseInt(functionId));
    }

    public boolean delete(Function function) {
        boolean flag = false;
        try {
            functionDAO.delete(function);
            flag = true;
        } catch (Exception e) {
            log.error(e, e);
        }
        return flag;
    }

}
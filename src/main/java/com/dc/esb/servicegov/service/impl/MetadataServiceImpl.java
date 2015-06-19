package com.dc.esb.servicegov.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dc.esb.servicegov.dao.impl.MetadataDAOImpl;
import com.dc.esb.servicegov.entity.Metadata;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MetadataServiceImpl {
    @Autowired
    private MetadataDAOImpl metadataDAOImpl;

    public List<Metadata> getAllMetadata() {
        return metadataDAOImpl.getAll();
    }

    public List<Metadata> getByMetadataId(String metadataId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("metadataId", metadataId);
        return metadataDAOImpl.findBy(params);
    }

    public List<Metadata> getByMetadataName(String metadataName) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("metadataName", metadataName);
        return metadataDAOImpl.findBy(params);
    }

    public List<Metadata> getByChineseName(String chineseName) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("chineseName", chineseName);
        return metadataDAOImpl.findBy(params);
    }

    public List<Metadata> getByCategoryWordId(String categoryWordId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("categoryWordId", categoryWordId);
        return metadataDAOImpl.findBy(params);
    }

    public List<Metadata> getByRemark(String remark) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("remark", remark);
        return metadataDAOImpl.findBy(params);
    }

    public List<Metadata> getByType(String type) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("type", type);
        return metadataDAOImpl.findBy(params);
    }

    public List<Metadata> getByLength(String length) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("length", length);
        return metadataDAOImpl.findBy(params);
    }

    public List<Metadata> getByScale(String scale) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("scale", scale);
        return metadataDAOImpl.findBy(params);
    }

    public List<Metadata> getByEnumId(String enumId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("enumId", enumId);
        return metadataDAOImpl.findBy(params);
    }

    public List<Metadata> getByMetadataAlias(String metadataAlias) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("metadataAlias", metadataAlias);
        return metadataDAOImpl.findBy(params);

    }

    public List<Metadata> getByBussDefine(String bussDefine) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("bussDefine", bussDefine);
        return metadataDAOImpl.findBy(params);
    }

    public List<Metadata> getByBussRule(String bussRule) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("bussRule", bussRule);
        return metadataDAOImpl.findBy(params);
    }

    public List<Metadata> getByDataSource(String dataSource) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("dataSource", dataSource);
        return metadataDAOImpl.findBy(params);
    }

    public List<Metadata> getByTemplateId(String templateId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("templateId", templateId);
        return metadataDAOImpl.findBy(params);
    }

    public List<Metadata> getByStatus(String status) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("status", status);
        return metadataDAOImpl.findBy(params);
    }

    public List<Metadata> getByVersion(String version) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("version", version);
        return metadataDAOImpl.findBy(params);
    }

    public List<Metadata> getByPotUser(String potUser) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("potUser", potUser);
        return metadataDAOImpl.findBy(params);
    }

    public List<Metadata> getByPotDate(String potDate) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("potDate", potDate);
        return metadataDAOImpl.findBy(params);
    }

    public List<Metadata> getByAuditUser(String auditUser) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("auditUser", auditUser);
        return metadataDAOImpl.findBy(params);
    }

    public List<Metadata> getByAuditDate(String auditDate) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("auditDate", auditDate);
        return metadataDAOImpl.findBy(params);
    }

    public boolean addMetadata(Metadata metadata) {
        metadataDAOImpl.save(metadata);
        return true;
    }

    public boolean modifyMetadata(Metadata metadata) {
        metadataDAOImpl.save(metadata);
        return true;
    }

    public void deleteMetadata(String metadataId) {
        metadataDAOImpl.delete(metadataId);
    }

}

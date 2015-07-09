package com.dc.esb.servicegov.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dc.esb.servicegov.dao.impl.CategoryWordDAOImpl;
import com.dc.esb.servicegov.dao.support.HibernateDAO;
import com.dc.esb.servicegov.service.support.AbstractBaseService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.dao.impl.MetadataDAOImpl;
import com.dc.esb.servicegov.entity.CategoryWord;
import com.dc.esb.servicegov.entity.Metadata;
import com.dc.esb.servicegov.util.DateUtils;

@Service
@Transactional
public class MetadataServiceImpl extends AbstractBaseService<Metadata,String>{
    @Autowired
    private MetadataDAOImpl metadataDAOImpl;
    @Autowired
    private CategoryWordDAOImpl categoryWordDAO;

    public List<Metadata> getAllMetadata() {
    	List<Metadata> list = metadataDAOImpl.getAll();
        return list;
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
		metadata.setOptDate(DateUtils.format(new Date()));
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
    
    public void deleteMetadatas(String metadataIds){
    	String[] ids = metadataIds.split("\\,");
    	if(ids != null && ids.length > 0){
    		for(String metadataId : ids){
    			deleteMetadata(metadataId);
    		}
    	}
    }

    public List<Metadata> queryByCondition(Map<String, String[]> values){
    	String hql = " from Metadata a where 1=1 ";
    	if(values != null && values.size() > 0){
    		for(String key:values.keySet()){
    			if(key.equals("metadataName") && values.get(key) != null && values.get(key).length > 0 ){
    				if(StringUtils.isNotEmpty(values.get(key)[0])){
    					hql += " and a.metadataName like '%" + values.get(key)[0] + "%' ";
    				}
    			}
    			if(key.equals("chineseName") && values.get(key) != null && values.get(key).length > 0 ){
    				if(StringUtils.isNotEmpty(values.get(key)[0])){
    					hql += " and a.chineseName like '%" + values.get(key)[0] + "%' ";
    				}
    			}
    			if(key.equals("metadataId") && values.get(key) != null && values.get(key).length > 0 ){
    				if(StringUtils.isNotEmpty(values.get(key)[0])){
    					hql += " and a.metadataId like '%" + values.get(key)[0] + "%' ";
    				}
    			}
    			if(key.equals("metadataAlias") && values.get(key) != null && values.get(key).length > 0 ){
    				if(StringUtils.isNotEmpty(values.get(key)[0])){
    					hql += " and a.metadataAlias like '%" + values.get(key)[0] + "%' ";
    				}
    			}
    			if(key.equals("status") && values.get(key) != null && values.get(key).length > 0 ){
    				if(StringUtils.isNotEmpty(values.get(key)[0])){
    					hql += " and a.status like '%" + values.get(key)[0] + "%' ";
    				}
    			}
    			if(key.equals("version") && values.get(key) != null && values.get(key).length > 0 ){
    				if(StringUtils.isNotEmpty(values.get(key)[0])){
    					hql += " and a.version like '%" + values.get(key)[0] + "%' ";
    				}
    			}
    			if(key.equals("startDate") && values.get(key) != null && values.get(key).length > 0 ){
    				if(StringUtils.isNotEmpty(values.get(key)[0])){
    					hql += " and a.optDate >'" + values.get(key)[0] + "' ";
    				}
    			}
    			if(key.equals("endDate") && values.get(key) != null && values.get(key).length > 0 ){
    				if(StringUtils.isNotEmpty(values.get(key)[0])){
    					hql += " and a.optDate <'" + values.get(key)[0] + "' ";
    				}
    			}
    			
    			if(key.equals("categoryWordId") && values.get(key) != null && values.get(key).length > 0 ){
    				if(StringUtils.isNotEmpty(values.get(key)[0])){
    					hql += " and a.categoryWordId ='" + values.get(key)[0] + "' ";
    				}
    			}
    		}
    	}
    	return metadataDAOImpl.find(hql);
    }
    
    public boolean uniqueValid(String metadataId){
    	List<Metadata> list = this.getByMetadataId(metadataId);
    	if(list != null && list.size() > 0){
    		return false;
    	}
    	return true;
    }
    
    public List<CategoryWord> categoryWord(){
    	return categoryWordDAO.getAll();
    }

    @Override
    public HibernateDAO<Metadata, String> getDAO() {
        return metadataDAOImpl;
    }
}

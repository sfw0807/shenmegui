package com.dc.esb.servicegov.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dc.esb.servicegov.dao.impl.MetaStructNodeDAOImpl;
import com.dc.esb.servicegov.dao.impl.MetadataAttributeDAOImpl;
import com.dc.esb.servicegov.dao.impl.MetadataDAOImpl;
import com.dc.esb.servicegov.entity.MetaStructNode;
import com.dc.esb.servicegov.entity.Metadata;
import com.dc.esb.servicegov.entity.MetadataAttribute;
import com.dc.esb.servicegov.exception.DataException;
import com.dc.esb.servicegov.vo.MetadataViewBean;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-5-29
 * Time: 下午2:03
 */
@Component
@Transactional
public class MetadataManagerImpl {

    private static final Log log = LogFactory.getLog(MetadataManagerImpl.class);

    @Autowired
    private MetadataDAOImpl metadataDAO;
    @Autowired
    private MetadataAttributeDAOImpl metadataAttributeDAO;
    @Autowired
    private MetaStructNodeDAOImpl metaStructNodeDAO;
    
    public boolean IsPaintNodeEnd(String mid) {
    	List<MetaStructNode> l = new ArrayList<MetaStructNode>();
    	l = metaStructNodeDAO.findBy("structId", mid);
    	if (l.size() == 0)
    		return true;
    	return false;
    }
    
    public Metadata getMetadataByMid(String mid){
    	List<Metadata> list = metadataDAO.findBy("metadataId", mid);
    	if (list != null) {
    		return metadataDAO.findBy("metadataId", mid).get(0);
    	}
    	return null;
    }
    
    /**
     * 
     * @param structId
     * @return
     */
    public List<MetaStructNode> getMetaStructById(String structId) {
    	return metaStructNodeDAO.findBy("structId", structId);
    }
    
    public MetadataViewBean getMetadataById(String id) throws DataException {
        MetadataViewBean metadataViewBean = null;
        List<Metadata> metadatas = metadataDAO.findBy("metadataId", id);
        if (null == metadatas) {
            String errorMsg = "元数据[" + id + "]不存在！";
            log.error(errorMsg);
            throw new DataException(errorMsg);
        }
        if (metadatas.size() == 0) {
            String errorMsg = "元数据[" + id + "]不存在！";
            log.error(errorMsg);
            throw new DataException(errorMsg);
        }
        if (metadatas.size() > 1) {
            String errorMsg = "元数据[" + id + "]存在重复项！";
            log.error(errorMsg);
            throw new DataException(errorMsg);
        }
        Metadata metadata = metadatas.get(0);
        String metadataId = metadata.getMetadataId();
        metadataViewBean = new MetadataViewBean();
        metadataViewBean.setMetadataId(metadata.getMetadataId());
        metadataViewBean.setMetadataName(metadata.getMetadataName());
        List<MetadataAttribute> metadataAttributes = getMetadataAttributesById(metadataId);
        if (null != metadataAttributes) {
            for (MetadataAttribute metadataAttribute : metadataAttributes) {
                if ("type".equalsIgnoreCase(metadataAttribute.getAttributeId())) {
                    metadataViewBean.setType(metadataAttribute.getAttributeValue());
                }
                if ("length".equalsIgnoreCase(metadataAttribute.getAttributeId())) {
                    metadataViewBean.setLength(metadataAttribute.getAttributeValue());
                }
                if ("scale".equalsIgnoreCase(metadataAttribute.getAttributeId())) {
                    metadataViewBean.setScale(metadataAttribute.getAttributeValue());
                }
            }
        }
        return metadataViewBean;
    }

    @Transactional
    public List<MetadataAttribute> getMetadataAttributesById(String metadataId) {
        List<MetadataAttribute> metadataAttributes = null;
        if (null != metadataId) {
            metadataAttributes = metadataAttributeDAO.findBy("metadataId", metadataId);
        }
        return metadataAttributes;
    }
}

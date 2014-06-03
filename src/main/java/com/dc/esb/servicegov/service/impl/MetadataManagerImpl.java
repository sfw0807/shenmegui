package com.dc.esb.servicegov.service.impl;

import com.dc.esb.servicegov.dao.impl.MetadataAttributeDAOImpl;
import com.dc.esb.servicegov.dao.impl.MetadataDAOImpl;
import com.dc.esb.servicegov.entity.Metadata;
import com.dc.esb.servicegov.entity.MetadataAttribute;
import com.dc.esb.servicegov.exception.DataException;
import com.dc.esb.servicegov.vo.MetadataViewBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-5-29
 * Time: 下午2:03
 */
@Component
public class MetadataManagerImpl {

    private static final Log log = LogFactory.getLog(MetadataManagerImpl.class);

    @Autowired
    private MetadataDAOImpl metadataDAO;
    @Autowired
    private MetadataAttributeDAOImpl metadataAttributeDAO;

    public MetadataViewBean getMetadataById(String id) throws DataException {
        MetadataViewBean metadataViewBean = null;
        List<Metadata> metadatas = metadataDAO.findBy("metadataId", id);
        if(null == metadatas){
            String errorMsg = "元数据["+id+"]不存在！";
            log.error(errorMsg);
            throw new DataException(errorMsg);
        }
        if(metadatas.size() == 0){
            String errorMsg = "元数据["+id+"]不存在！";
            log.error(errorMsg);
            throw new DataException(errorMsg);
        }
        if(metadatas.size() > 1){
            String errorMsg ="元数据["+id+"]存在重复项！";
            log.error(errorMsg);
            throw new DataException(errorMsg);
        }
        Metadata metadata = metadatas.get(0);
        String metadataId = metadata.getMetadataId();
        metadataViewBean = new MetadataViewBean();
        metadataViewBean.setMetadataId(metadata.getMetadataId());
        metadataViewBean.setMetadataName(metadata.getMetadataName());
        List<MetadataAttribute> metadataAttributes = getMetadataAttributesById(metadataId);
        if(null != metadataAttributes){
            for(MetadataAttribute metadataAttribute : metadataAttributes){
                if("type".equalsIgnoreCase(metadataAttribute.getAttributeId())){
                    metadataViewBean.setType(metadataAttribute.getAttributeValue());
                }
                if("length".equalsIgnoreCase(metadataAttribute.getAttributeId())){
                    metadataViewBean.setLength(metadataAttribute.getAttributeValue());
                }
                if("scale".equalsIgnoreCase(metadataAttribute.getAttributeId())){
                    metadataViewBean.setScale(metadataAttribute.getAttributeValue());
                }
            }
        }
        return metadataViewBean;
    }

    public List<MetadataAttribute> getMetadataAttributesById(String metadataId){
        List<MetadataAttribute> metadataAttributes = null;
        if(null != metadataId){
            metadataAttributes = metadataAttributeDAO.findBy("metadataId", metadataId);
        }
        return metadataAttributes;
    }
}

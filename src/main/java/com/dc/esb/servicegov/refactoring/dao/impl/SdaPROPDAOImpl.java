package com.dc.esb.servicegov.refactoring.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.dc.esb.servicegov.dao.impl.HibernateDAO;
import com.dc.esb.servicegov.refactoring.entity.SdaPROP;
import com.dc.esb.servicegov.refactoring.resource.metadataNode.Attr;

@Repository
public class SdaPROPDAOImpl extends HibernateDAO<SdaPROP, String> {

	/**
	 * 根据sdaId返回sdaPROP的MAP结果
	 * @param sdaId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Attr> getsdaPropMapBySdaId(String sdaId){
		List<Attr> returnList = new ArrayList<Attr>();
		String hql = "from SdaPROP where sdaId = :sdaId";
		Query query = getSession().createQuery(hql);
		List list = query.setString("sdaId", sdaId).list();
		if(list != null && list.size() > 0){
			SdaPROP sdaP = (SdaPROP)list.get(0);
			Attr attr = new Attr();
			attr.setResourceid(sdaP.getId());
			attr.setStructId(sdaP.getSdaId());
			attr.setPropertyName(sdaP.getName());
			attr.setPropertyValue(sdaP.getValue());
			attr.setPropertyIndex(sdaP.getSeq());
			attr.setRemark(sdaP.getRemark());
			returnList.add(attr);
		}
		return returnList;
	}
}

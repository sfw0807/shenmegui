package com.dc.esb.servicegov.service;

import java.util.List;

import com.dc.esb.servicegov.entity.OLA;

public interface OLAManager {
	public abstract boolean deleteOLA(String operationId, String serviceId);
	public abstract boolean saveOLA(List<OLA> olaList);
}

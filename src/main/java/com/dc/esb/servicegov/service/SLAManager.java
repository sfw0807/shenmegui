package com.dc.esb.servicegov.service;

import java.util.List;

import com.dc.esb.servicegov.entity.SLA;

public interface SLAManager {
	public abstract boolean deleteSLA(String operationId, String serviceId);
	public abstract boolean saveSLA(List<SLA> slaList);
}

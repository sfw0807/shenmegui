package com.dc.esb.servicegov.service;


public interface PublishViewManager {

	public Integer countOfPublishTimes(String onlineDate);
	public Integer countOfProviderSys(String onlineDate);
	public Integer countOfConsumerSys(String onlineDate);
	public Integer countOfService(String onlineDate);
	public Integer countOfOperation(String onlineDate);
	public Integer countOfOffLine(String onlineDate);
	public Integer countOfModifyTimes(String onlineDate);
	public Integer countOfDeletedService(String onlineDate);
}

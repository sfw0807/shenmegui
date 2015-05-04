package com.dc.esb.servicegov.resource;

import java.io.File;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.dc.esb.servicegov.resource.impl.XMPassedInterfaceDataFromDB;

public interface IExportMapFileTask extends Runnable {
	
	public List<File> generate();
	
	public void init(String interfaceName, List<File> files, XMPassedInterfaceDataFromDB db, CountDownLatch countDown);

}

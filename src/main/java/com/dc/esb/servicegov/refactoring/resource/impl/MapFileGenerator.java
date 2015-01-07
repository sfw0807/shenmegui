package com.dc.esb.servicegov.refactoring.resource.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dc.esb.servicegov.refactoring.resource.impl.ExportMapFileTask;
import com.dc.esb.servicegov.refactoring.resource.IExportMapFileTask;

@Service
public class MapFileGenerator {

	Log log = LogFactory.getLog(MapFileGenerator.class);
	
	CountDownLatch countDown = null;
	
	@Autowired
	private XMPassedInterfaceDataFromDB db;
	
	public List<File> generate(String[] interfaces) {
		List<File> mapFiles = Collections.synchronizedList(new LinkedList<File>());
		List<IExportMapFileTask> taskLst = new ArrayList<IExportMapFileTask>();
		countDown = new CountDownLatch(interfaces.length);
		for (String id : interfaces) {
			IExportMapFileTask task = new ExportMapFileTask();
			task.init(id, mapFiles, db, countDown);
			taskLst.add(task);
		}
		ExecutorService executor = Executors.newFixedThreadPool(20);
		for (IExportMapFileTask t : taskLst) {
			executor.execute(t);
		}
		try {
			countDown.await(60 * 5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			log.error("MapFileGenerator countDown : " + countDown.getCount());
			e.printStackTrace();
		}
		return mapFiles;
	}
	
}

package com.dc.esb.servicegov.resource;

import java.io.File;
import java.util.List;

import com.dc.esb.servicegov.entity.InvokeInfo;


public interface IExport {

	public File[] exportConfigFiles(List<InvokeInfo> list);
}

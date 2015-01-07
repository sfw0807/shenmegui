package com.dc.esb.servicegov.refactoring.resource;

import java.io.File;
import java.util.List;

import com.dc.esb.servicegov.refactoring.entity.InvokeInfo;


public interface IExport {

	public File[] exportConfigFiles(List<InvokeInfo> list);
}

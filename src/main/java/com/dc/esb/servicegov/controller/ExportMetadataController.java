package com.dc.esb.servicegov.controller;


import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dc.esb.servicegov.export.impl.MetadataConfigGenerator;
@Controller
@RequestMapping("/exportMetadata")
public class ExportMetadataController {
	@SuppressWarnings("unused")
	private Log log = LogFactory.getLog(ExportMetadataController.class);
	@Autowired
	private MetadataConfigGenerator metadataConfigGenerator;
	@RequestMapping(method = RequestMethod.GET, value = "/export")
	public @ResponseBody
	boolean exportMetadata(HttpServletRequest request, HttpServletResponse response){
		//生成本地文件
		File file = metadataConfigGenerator.generate();
		//读取本地文件内容
		int length = (int) file.length();
		DataInputStream dataIn = null;
		OutputStream out = null;
		try {
			dataIn = new DataInputStream(new FileInputStream(file));
			byte[] buffer = new byte[length];
			dataIn.readFully(buffer);
			//修改HTTP头
			response.setContentType("application/octet-stream");		
            response.addHeader("Content-Disposition",
                    "attachment;filename="
                            + new String(file.getName().getBytes(
                            "gbk"), "iso-8859-1"));
          //把本地文件的内容写入Http输出流
			out = response.getOutputStream();
			out.write(buffer);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null != dataIn){
				try {
					dataIn.close();
				} catch (IOException e) {
					log.error(e, e);
				}
				try {
					out.close();
				} catch (IOException e) {
					log.error(e, e);
				}
			}
		}
		//删除本地文件
		return true;
	}
}


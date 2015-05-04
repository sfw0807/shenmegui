package com.dc.esb.servicegov.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileUtil {

	private static Log log = LogFactory.getLog(FileUtil.class);

	/**
	 * 功能:将File响应给用户
	 * 
	 * @param file
	 * @param response
	 * @throws RuntimeException
	 */
	public static File downloadFile(File[] files, String zipName,
			HttpServletResponse response) throws RuntimeException {
			if (!zipName.endsWith(".zip")) {
				zipName += ".zip";
			}
			File zipFile = new File(zipName);
			ZIPUtils zipUtils = new ZIPUtils();
			zipUtils.doZip(files, zipFile.getAbsolutePath());
			InputStream in = null;
			OutputStream out = null;

			try {
				response.setContentType("application/zip");
				response.addHeader("Content-Disposition",
						"attachment;filename="
								+ new String(zipFile.getName().getBytes("gbk"),
										"iso-8859-1"));
				in = new BufferedInputStream(new FileInputStream(zipFile));
				out = new BufferedOutputStream(response.getOutputStream());
				long fileLength = zipFile.length();
				byte[] cache = null;
				if (fileLength > Integer.MAX_VALUE) {
					cache = new byte[Integer.MAX_VALUE];
				} else {
					cache = new byte[(int) fileLength];
				}
				int i = 0;
				while ((i = in.read(cache)) > 0) {
					out.write(cache, 0, i);
				}
				out.flush();
			} catch (Exception e) {
				log.error(e, e);
			} finally {
				if (null != in) {
					try {
						in.close();
					} catch (IOException e) {
						log.error(e, e);
					}
				}
				if (null != out) {
					try {
						in.close();
					} catch (IOException e) {
						log.error(e, e);
					}

				}
			}
			return zipFile;
	}

	/**
	 * 合并文件存储到Map对象
	 * 
	 * @param fileMap
	 * @param files
	 */
	public static void mergeFile(Map<String, File> fileMap, List<File> files) {
		if (null != fileMap && null != files) {
			for (File file : files) {
				if (!fileMap.containsKey(file.getAbsoluteFile())) {
					log.info("[SERVICE DEF GENERATER]: file ["
							+ file.getAbsolutePath()
							+ "] has been put into merge list");
					fileMap.put(file.getAbsolutePath(), file);
				}
			}
		}
	}

	/**
	 * 从Map中取出文件列表
	 * 
	 * @param fileMap
	 * @return
	 */
	@SuppressWarnings("unused")
	public static File[] filesFromMap(Map<String, File> fileMap) {
		File[] files = null;
		if (null != fileMap) {
			int fileCount = fileMap.size();
			log.info("[SERVICE DEF GENERATER]: [" + fileCount
					+ "] files generated");
			if (fileCount > 0) {
				files = new File[fileCount];
				int i = 0;
				for (Map.Entry<String, File> entry : fileMap.entrySet()) {
					files[i] = entry.getValue();
					i++;
				}
			}
		}
		return files;
	}

	/**
	 * 删除文件
	 * 
	 * @param file
	 * @return
	 */
	public static boolean deleteFile(File file) {
		boolean deleteResult = true;
		if (file.isDirectory()) {
			File[] subFiles = file.listFiles();
			if (null != subFiles) {
				for (File subFile : subFiles) {
					deleteResult = deleteFile(subFile);
				}
			}
		}
		log.info("开始删除临时文件[" + file.getAbsolutePath() + "]");
		deleteResult = file.delete();
		if (!deleteResult) {
			log.error("删除临时文件[" + file.getAbsolutePath() + "]失败！");
		}
		return deleteResult;
	}

	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}

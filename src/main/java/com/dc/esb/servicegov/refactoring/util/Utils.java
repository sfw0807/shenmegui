package com.dc.esb.servicegov.refactoring.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

	public final static String currentCount = "并发数";
	public final static String timeOut = "超时时间";
	public final static String averageTime = "平均响应时间";
	public final static String successRate = "业务成功率";
	
	public static String getTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return sdf.format(new Date());
	}

	/**
	 * get data type length scale
	 * 
	 * @param value
	 * @return
	 */
	public static String getDataType(String value) {
		String type = null;
		if (value != null && !"".equals(value)) {
			if (value.contains("(")) {
				type = value.substring(0, value.indexOf("(")).toLowerCase();
			} else {
				type = value;
			}
		}
		return type;
	}

	public static String getDataLength(String value) {
		String length = null;
		if (value != null && !"".equals(value)) {
			if (value.indexOf("(") > 0) {
				if (value.indexOf(",") > 0) {
					length = value.substring(value.indexOf("(") + 1, value
							.indexOf(","));
				} else {
					length = value.substring(value.indexOf("(") + 1, value
							.length() - 1);
				}
			}
		}
		return length;
	}

	public static String getDataScale(String value) {
		String scale = null;
		if (value != null && !"".equals(value)) {
			if (value.indexOf(",") > 0) {
				scale = value.substring(value.indexOf(",") + 1,
						value.length() - 1);
			}
		}
		return scale;
	}

	public static String getDataLengthFromLength(String lengthValue){
		String length = "";
		if (lengthValue.indexOf(",") > 0) {
			length = lengthValue.substring(0, lengthValue
					.indexOf(","));
		} else {
			length = lengthValue;
		}
		return length;
	}

	public static String getDataScaleFromLength(String lengthValue){
		String scale = "";
		if (lengthValue.indexOf(",") > 0) {
			scale = lengthValue.substring(lengthValue.indexOf(",") + 1,
					lengthValue.length());
		}
		return scale;
	}


	// 基础版本号，修改
	public static String modifyversionno(String versionno) {
		String[] num = versionno.split("\\.");
		num[2] = (Integer.parseInt(num[2]) + 1) + "";
		versionno = num[0] + "." + num[1] + "." + num[2];
		return versionno;
	}
	
	// 基础版本号，修改
	public static String modifyOnlineNo(String newOnlineVersion, String versionno) {
		String[] num = versionno.split("\\.");
		versionno = newOnlineVersion + "." + num[1] + "." + num[2];
		return versionno;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// System.out.println(Utils.getTime());
	}

}

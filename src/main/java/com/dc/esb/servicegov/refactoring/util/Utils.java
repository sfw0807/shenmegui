package com.dc.esb.servicegov.refactoring.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

	public static String getTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return sdf.format(new Date());
	}

	/**
	 * get  data type length scale
	 * @param value
	 * @return
	 */
	public static String getDataType(String value) {
		String type = null;
		if (value != null && !"".equals(value)) {
			type = value.substring(0, value.indexOf("(")).toLowerCase();
		}
		return type;
	}

	public static String getDataLength(String value) {
		String length = null;
		if (value != null && !"".equals(value)) {
			if (value.indexOf(",") > 0) {
				length = value.substring(value.indexOf("(") + 1, value
						.indexOf(","));
			} else {
				length = value.substring(value.indexOf("(") + 1,
						value.length() - 1);
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
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// System.out.println(Utils.getTime());
	}

}

package com.dc.esb.servicegov.util;

/**
 * Created with IntelliJ IDEA. User: Administrator Date: 14-5-28 Time: 下午3:55
 */
public class ServiceStateUtils {

	public static final String DEFINITION = "服务定义";
	public static final String DEVELOP = "开发";
	public static final String UNION = "联调测试";
	public static final String SIT = "sit测试";
	public static final String UAT = "uat测试";
	public static final String PREFORPUBLISH = "投产验证";
	public static final String PUBLISH = "上线";
	public static final String OFFLINE = "下线";

	public static String getServiceState(int id) {
		String state = "";
		switch (id) {
		case 0:
			state = DEFINITION;
			break;
		case 1:
			state = DEVELOP;
			break;
		case 2:
			state = UNION;
			break;
		case 3:
			state = SIT;
			break;
		case 4:
			state = UAT;
			break;
		case 5:
			state = PREFORPUBLISH;
			break;
		case 6:
			state = PUBLISH;
			break;
		case 7:
			state = OFFLINE;
			break;
		}
		return state;
	}
}

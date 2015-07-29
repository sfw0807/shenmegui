package com.dc.esb.servicegov.service.support;

/**
 * Created by vincentfxz on 15/7/12.
 */
public class Constants {
    public static final String STATE_PASS = "1";
    public static final String STATE_UNPASS = "2";
    
    public static final String DELTED_FALSE = "0";//0:未删除，1：已删除
    public static final String DELTED_TRUE = "1";
    
    public static final String INVOKE_TYPE_CONSUMER = "1";//接口映射类型，1：消费者，0：提供者
    public static final String INVOKE_TYPE_PROVIDER = "0";

	public static final String EXCEL_TEMPLATE_SERVICE =Constants.class.getResource("/").getPath() +"/template/excel_service_template.xls";
	public static final String EXCEL_TEMPLATE_SERVICE_VIEW =Constants.class.getResource("/").getPath() +"/template/excel_service_view_template.xls";

    public static class Operation{
    	public static final String OPT_STATE_UNAUDIT = "0";  //0：未审核， 1：审核通过，2：审核不通过
    	public static final String OPT_STATE_PASS = "1";
    	public static final String OPT_STATE_UNPASS = "2";

    	
    }
    
    public static class Version{
    	public static final String TYPE_ELSE = "0";  //0：非基线，1：基线
    	public static final String TYPE_BASELINE = "1";
    	
    	public static final String STATE_USING = "0";//0:生效，1：废弃
    	public static final String STATE_DROPPED = "1";
    	
    	public static final String TARGET_TYPE_BASELINE = "0";//基线
    	public static final String TARGET_TYPE_OPERATION = "1";//场景
    	public static final String TARGET_TYPE_PC = "2";//公共代码
    	
    	public static final String OPT_TYPE_ADD = "0"; //操作类型，0：新增，1：修改，2：删除,3:发布
    	public static final String OPT_TYPE_EDIT = "1"; 
    	public static final String OPT_TYPE_DELETE = "2"; 
    	public static final String OPT_TYPE_RELEASE = "3"; 
    }

	public static class Metadata{
		public static final String STATUS_UNAUDIT = "未审核";
		public static final String STATUS_FORMAL =  "正式";
	}

}

<%@ page contentType="text/json; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%
	String mid = request.getParameter("mid");
%>
<%
	if(mid.equals("1.2")){
%>
{"success":true,"url":"/jsp/version/versionRelease.jsp","title":"版本发布"}
<%
	}
	
%>
<%
	if(mid.equals("1.3")){
%>
{"success":true,"url":"/jsp/version/versionHis.jsp","title":"版本历史"}
<%
	}
	
%>
<%
	if(mid.equals("1.4")){
%>
{"success":true,"url":"/jsp/version/releaseHis.jsp","title":"版本公告"}
<%
	}
	
%>
<%
	if(mid.equals("1.5")){
%>

{"success":true,"url":"/jsp/version/baseLineMake.jsp","title":"基线制作"}
<%
	}

%>
<%
	if(mid.equals("1.6")){
%>
{"success":true,"url":"/jsp/version/baseLineHis.jsp","title":"基线历史"}
<%
	}

%>
<%
	if(mid.equals("2.3")){
%>
{"success":true,"url":"/dataTemplate/grid4.jsp","title":"任务管理"}
<%
	}
	
%>
<%
	if(mid.equals("2.4")){
%>
{"success":true,"url":"/jsp/task/mytask.jsp","title":"我的任务"}
<%
	}
	
%>
<%
	if(mid.equals("3.2")){
%>
{"success":true,"url":"/dataTemplate/grid5.jsp","title":"英文单词及缩写管理"}
<%
	}
	
%>
<%
	if(mid.equals("3.3")){
%>
{"success":true,"url":"/dataTemplate/grid6.jsp","title":"类别词管理"}
<%
	}
	
%>
<%
	if(mid.equals("3.4")){
%>
{"success":true,"url":"/dataTemplate/grid7.jsp","title":"元数据管理"}
<%
	}
	
%>
<%
	if(mid.equals("3.6")){
%>
{"success":true,"url":"/dataTemplate/words/gonggong.html","title":"公共代码管理"}
<%
	}

%>

<%
	if(mid.equals("9.2")){
%>
{"success":true,"url":"/jsp/sysadmin/fieldmapping_import.jsp","title":"字段映射导入"}
<%
	}
	
%>
<%
	if(mid.equals("4.2")){
%>
{"success":true,"url":"/jsp/user/userMaintain.jsp","title":"用户维护"}
<%
	}

%><%
	if(mid.equals("9.4")){
%>
{"success":true,"url":"/jsp/sysadmin/file_list.jsp","title":"文件管理"}
<%
	}

%><%
	if(mid.equals("3.7")){
%>
{"success":true,"url":"/dataTemplate/fieldmapping_import.jsp","title":"元数据导入"}
<%
	}

%>
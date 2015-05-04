<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String ctx = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ include file="/jsp/includes/header.jsp" %>
<%@ include file="/jsp/includes/jquery.jsp" %>
<%@ include file="/jsp/includes/ligerUI.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>服务统计表</title>
    <script src="<%=path %>/js/serviceTotal.js"></script>
</head>
<body>
 
  <div id="maingrid" style="margin:10px;"></div>
  <div style="display:none;">
  
</div>
 
</body>
</html>





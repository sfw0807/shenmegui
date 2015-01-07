<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>服务统计表</title>
    <link href="<%=path %>/js/ligerUI/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="<%=path %>/js/ligerUI/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
    <script src="<%=path %>/js/ligerUI/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
    <script src="<%=path %>/js/ligerUI/lib/ligerUI/js/ligerui.min.js" type="text/javascript"></script>
    <link href="<%=path %>/js/ligerUI/lib/ligerUI/skins/Tab/css/tab.css" rel="stylesheet" />
    <link href="<%=path %>/js/ligerUI/lib/ligerUI/skins/Tab/css/grid.css" rel="stylesheet" />
    <script src="<%=path %>/js/ligerUI/lib/ligerUI/js/plugins/ligerGrid.js"></script>
    <script src="<%=path %>/js/serviceTotal.js"></script>
</head>
<body>
 
  <div id="maingrid" style="margin:10px;"></div>
  <div style="display:none;">
  
</div>
 
</body>
</html>





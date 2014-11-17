<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>资源导入</title>

		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="this is my page">
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">

		<link rel="stylesheet"
			href="<%=path %>/themes/smoothness/jquery-ui-1.8.4.custom.css" />
		<link rel="stylesheet" href="<%=path %>/css/index.css" />
		<script src="<%=path %>/js/jquery-ui/js/jquery-1.10.2.js"></script>
	    <script src="<%=path %>/js/jquery.ui.core.js"></script>
	    <script src="<%=path %>/js/jquery.ui.widget.js"></script>
	    <script src="<%=path %>/js/jquery.ui.button.js"></script>
	    <script src="<%=path %>/js/jquery.ui.position.js"></script>
	    <script src="<%=path %>/js/jquery.ui.autocomplete.js"></script>
       <script src="<%=path %>/js/jquery-ui-tabs.js"></script>
       <script src="<%=path %>/js/jquery.ui.button.js"></script>
       <script src='<%=path %>/js/jquery.datatables/js/jquery.dataTables.js' type="text/javascript"></script>
		<link rel='stylesheet' type='text/css'
			href='<%=path %>/js/jquery.datatables/css/jquery.dataTables.css' />
		<script src="<%=path %>/js/layout.js" type="text/javascript"></script>
		<script src="<%=path %>/js/resourceImport.js" type="text/javascript"></script>
		<script src="<%=path %>/js/json/json2.js" type="text/javascript"></script>
		<style>
			.ui-menu {
				position: absolute;
				width: 100px;
			}
			
			.demo-description {
				clear: both;
				padding: 12px;
				font-size: 1.3em;
				line-height: 1.4em;
			}
			
			.ui-draggable,.ui-droppable {
				background-position: top;
			}
		</style>

	</head>
	<body>
		<div id="tabs" style="width: 100%">
			<ul>
				<li id='tab0'>
					<a href='#tabs-0'>映射文档导入</a>
				</li>
				<li id='tab1'>
					<a href='#tabs-1'>上线清单导入</a>
				</li>
			</ul>
			
			<div id="tabs-0">
			<div class="ui-widget-header" style="margin-bottom: 0.1em;padding:0.1em;">
			<form action="<%=path %>/import/mapping" method="post" enctype="multipart/form-data" id="mapping">
			  请选择导入的映射文档：
			  <input  style ="width:14%;" type = "file" name="file" id="fileUpload" />
			  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			  <input  style ="width:4%;" type = "submit" value="导入" id="import" />
			</form>
			</div>
	       </div>
	       
						
			<div id="tabs-1">
			<div class="ui-widget-header" style="margin-bottom: 0.1em;padding:0.1em;">
			<form action="<%=path %>/import/online" method="post" enctype="multipart/form-data" id="online" >
			  请选择导入的上线清单：
			  <input  style ="width:14%;" type = "file" name="file" id="onlinefileUpload" />
			  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			  <input  style ="width:4%;" type = "submit" value="导入" id="onlineimport" />
			</form>
			</div>
	       </div>      
		</div>
	</body>
</html>




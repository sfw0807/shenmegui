<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include  file= "/jsp/header/header.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>操作管理</title>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="this is my page">
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge"/>

		<link rel="stylesheet" href="<%=path %>/themes/smoothness/jquery-ui-1.8.4.custom.css" />
		<link rel="stylesheet" href="<%=path %>/css/demos.css" />
	    <script src="<%=path %>/js/jquery.ui.mouse.js"></script>
	    <script src="<%=path %>/js/jquery.ui.button.js"></script>
	    <script src="<%=path %>/js/jquery.ui.position.js"></script>
	    <script src="<%=path %>/js/jquery.ui.draggable.js"></script>
	    <script src="<%=path %>/js/jquery.ui.resizable.js"></script>
	    <script src="<%=path %>/js/jquery.ui.dialog.js"></script>
        <script src="<%=path %>/js/jquery-ui-tabs.js"></script>
        <script src="<%=path %>/js/combo-box.js"></script>
        <script src='<%=path %>/js/jquery.datatables/js/jquery.dataTables.js' type="text/javascript"></script>
		<link rel='stylesheet' type='text/css' href='<%=path %>/js/jquery.datatables/css/jquery.dataTables.css' />
		<script src="<%=path%>/js/jquery.fileDownload.js" type="text/javascript"></script>
		<script src="<%=path%>/js/layout.js" type="text/javascript"></script>
		<script src="<%=path%>/assets/scene/js/operationAllManager.js" type="text/javascript"></script>
		<script src="<%=path%>/assets/scene/js/operationAll.js" type="text/javascript"></script>
		<link rel="stylesheet" href="<%=path %>/css/index.css" />
		<link rel="stylesheet" href="<%=path %>/assets/scene/css/scene.css" />
	</head>
	<body>
    </div>
		<div id="tabs" style="width: 100%;">
			<ul>
				<li id='tab0'>
					<a href='#tabs-0'>操作管理</a>
				</li>
			</ul>
			
			<div id="tabs-0">
			<div class="ui-widget-header" style="margin-bottom: 0.5em;padding: 0.2em;">
				<input type = "button" value="新增" id="addOperation" />
				<input type = "button" value="删除" id="deleteOperation" />
				<input type = "button" value="发布" id="deployOperation" />
				<input type = "button" value="重定义" id="redefOperation" />
				<input type = "button" value="上线" id="publishOperation" />
				<input type = "button" value="提交审核" id="submit" />
                <input type='button' id='checkAll' value='全选'></input>
                <input type='button' id='toggleAll' value='反选'></input>
			</div>
			<table cellpadding="0" cellspacing="0" border="0" class="display" id="operationTable">
					<tfoot>
						<tr>
							<th>
								<input type="text" name="operationId" id="operationId" value="操作ID"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="operationName" id="operationName" value="操作名称"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="serviceId" id="serviceId" value="服务ID"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="serviceName" id="serviceName" value="服务名称"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="state" id="state" value="操作状态"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="version" id="version" value="开发版本"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="publishVersion" id="publishVersion" value="上线版本"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="publishDate" id="publishDate" value="上线日期"
									class="search_init" />
							</th>
							<th>
								<input type="hidden" name="action" id="action" value="动作"
									class="search_init" />
							</th>
							<th>
								<input type="hidden" name="history" id="history" value="历史"
									class="search_init" />
							</th>
							<th>
								<input type="hidden" name="invokeInfo" id="invokeInfo" value="调用情况"
									class="search_init" />
							</th>
						</tr>
					</tfoot>
				</table>
			</div>
		</div>
	</body>
</html>


<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>元数据调用情况详细</title>

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
		<script src="<%=path %>/js/jquery.fileDownload.js" type="text/javascript"></script>
		<script src="<%=path %>/js/layout.js" type="text/javascript"></script>
		<script src="<%=path %>/js/metadataManager.js" type="text/javascript"></script>
		<script src="<%=path %>/js/mdtUsed.js" type="text/javascript"></script>
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
					<a href='#tabs-0'>元数据调用情况详细</a>
				</li>
			</ul>
			
			<div id="tabs-0">
	        <table cellpadding="0" cellspacing="0" border="0" class="display"
					id="mdtUsedTable">
					<tfoot>
						<tr>
							<th>
								<input type="text" name="metadataId" id="metadataId" value="元数据ID"
									class="search_init" />
							</th>
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
								<input type="text" name="prdSysAB" id="prdSysAB" value="提供系统简称"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="prdSysName" id="prdSysName" value="提供系统名称"
									class="search_init" />
							</th>
						</tr>
					</tfoot>
				</table>
			</div>
		</div>
	</body>
</html>




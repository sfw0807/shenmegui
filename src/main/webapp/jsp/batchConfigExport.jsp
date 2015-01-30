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
		<title>批量导出配置</title>

		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="this is my page">
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">

		<link rel="stylesheet" href="<%=path %>/themes/smoothness/jquery-ui-1.8.4.custom.css" />
		<link rel="stylesheet" href="<%=path %>/css/index.css" />
		<link rel="stylesheet" href="<%=path %>/css/demos.css" />
		<script src="<%=path %>/js/jquery-ui/js/jquery-1.10.2.js"></script>
		<script src="<%=path %>/js/jquery-1.8.2.js"></script>
		<script src="<%=path %>/js/jquery.bgiframe-2.1.2.js"></script>
	    <script src="<%=path %>/js/jquery.ui.core.js"></script>
	    <script src="<%=path %>/js/jquery.ui.widget.js"></script>
	    <script src="<%=path %>/js/jquery.ui.mouse.js"></script>
	    <script src="<%=path %>/js/jquery.ui.button.js"></script>
	    <script src="<%=path %>/js/jquery.ui.position.js"></script>
	    <script src="<%=path %>/js/jquery.ui.autocomplete.js"></script>
	    <script src="<%=path %>/js/jquery.ui.draggable.js"></script>
	    <script src="<%=path %>/js/jquery.ui.resizable.js"></script>
	    <script src="<%=path %>/js/jquery.ui.dialog.js"></script>
	    <script src="<%=path %>/js/jquery.effects.core.js"></script>
        <script src="<%=path %>/js/jquery-ui-tabs.js"></script>
        <script src="<%=path %>/js/combo-box.js"></script>
        <script src='<%=path %>/js/jquery.datatables/js/jquery.dataTables.js' type="text/javascript"></script>
		<link rel='stylesheet' type='text/css' href='<%=path %>/js/jquery.datatables/css/jquery.dataTables.css' />
		<script src="<%=path%>/js/jquery.fileDownload.js"
			type="text/javascript"></script>
		<script src="<%=path%>/js/layout.js" type="text/javascript"></script>
		<script src="<%=path%>/js/batchExportManager.js" type="text/javascript"></script>
		<script src="<%=path%>/js/batchExport.js" type="text/javascript"></script>
		<script src="<%=path%>/js/json/json2.js" type="text/javascript"></script>
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
					<a href='#tabs-0'>批量导出配置</a>
				</li>
			</ul>

			<div id="tabs-0">
				<div class="ui-widget-header"
					style="margin-bottom: 0.5em; padding: 0.5em;">
					<textarea rows="8" cols="50" id="area"></textarea>
					<label style="color: blue; font: 14px;">
						备注：交易码之间以英文逗号(,)分隔
					</label>
					<br />
					<input  type="submit" value="查询可选导出数据" id="search" />
					<input style="width: 10%;" type="submit" value="导出" id="export" />
				</div>
				<table cellpadding="0" cellspacing="0" border="0" class="display"
					id="batchExportTable" >
					<tfoot>
						<tr>
							<th>
								<input type="text" name="serviceId" id="serviceId"
									value="服务ID" class="search_init" />
							</th>
							<th>
								<input type="text" name="operationId" id="operationId"
									value="操作ID" class="search_init" />
							</th>
							<th>
								<input type="text" name="interfaceId" id="interfaceId"
									value="交易代码" class="search_init" />
							</th>
							<th>
								<input type="text" name="provideSys" id="provideSys"
									value="提供方" class="search_init" />
							</th>
							<th>
								<input type="text" name="consumeMsgType" id="consumeMsgType"
									value="调用报文类型" class="search_init" />
							</th>
							<th>
								<input type="text" name="provideMsgType" id="provideMsgType"
									value="提供报文类型" class="search_init" />
							</th>
							<th>
								<input type="text" name="through" id="through" value="是否穿透"
									class="search_init" />
							</th>
						</tr>
					</tfoot>
				</table>
			</div>
		</div>
	</body>
</html>




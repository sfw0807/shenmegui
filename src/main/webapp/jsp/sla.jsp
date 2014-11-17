<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>服务SLA信息查询</title>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="this is my page">
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge"/>

		<link rel="stylesheet"
			href="<%=path%>/themes/smoothness/jquery-ui-1.8.4.custom.css" />
		<link rel="stylesheet" href="<%=path%>/css/index.css" />
		<script src="<%=path%>/js/jquery-ui/js/jquery-1.10.2.js"></script>
	    <script src="<%=path%>/js/jquery.ui.core.js"></script>
	    <script src="<%=path%>/js/jquery.ui.widget.js"></script>
	    <script src="<%=path%>/js/jquery.ui.button.js"></script>
	    <script src="<%=path%>/js/jquery.ui.position.js"></script>
	    <script src="<%=path%>/js/jquery.ui.autocomplete.js"></script>
       <script src="<%=path%>/js/jquery-ui-tabs.js"></script>
       <script src="<%=path%>/js/jquery.ui.button.js"></script>
       <script src='<%=path%>/js/jquery.datatables/js/jquery.dataTables.js' type="text/javascript"></script>
		<link rel='stylesheet' type='text/css'
			href='<%=path%>/js/jquery.datatables/css/jquery.dataTables.css' />
		<script src="<%=path%>/js/jquery.fileDownload.js" type="text/javascript"></script>
		<script src="<%=path%>/js/layout.js" type="text/javascript"></script>
		<script src="<%=path%>/js/slaManager.js" type="text/javascript"></script>
		<script src="<%=path%>/js/sla.js" type="text/javascript"></script>
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
					<a href='#tabs-0'>服务SLA信息查询</a>
				</li>
			</ul>
			
			<div id="tabs-0">
			<div class="ui-widget-header" style="margin-bottom: 0.5em;padding: 0.2em;">
				<input  type = "button" value="导出" id="export" />
			</div>
			<!-- <table width="100%" cellpadding="0" cellspacing="0" border="0" class="display" id="exportTable">
	   		   <tr width="60%">
	  			  <td width="15%">
		  			 
	  			  </td>
  			   </tr>
	        </table> -->
			<table cellpadding="0" cellspacing="0" border="0" class="display" id="slaTable">
					<tfoot>
						<tr>
							<th>
								<input type="text" name="serviceInfo" id="serviceInfo" value="服务ID/名称"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="operationInfo" id="operationInfo" value="操作ID/名称"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="interfaceInfo" id="interfaceInfo" value="交易代码/名称"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="provideSysInfo" id="provideSysInfo" value="提供方系统简称/名称"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="successRate" id="successRate" value="业务成功率"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="timeOut" id="timeOut" value="超时时间"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="currentCount" id="currentCount" value="并发数"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="averageTime" id="averageTime" value="平均响应时间"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="field" id="field" value="备注"
									class="search_init" />
							</th>
						</tr>
					</tfoot>
				</table>
			</div>
		</div>
	</body>
</html>


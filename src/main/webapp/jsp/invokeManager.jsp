<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>调用关系管理</title>
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
		<script src="<%=path%>/js/invokeManager.js" type="text/javascript"></script>
		<script src="<%=path%>/js/invoke.js" type="text/javascript"></script>
		<script src="<%=path%>/js/json/json2.js" type="text/javascript"></script>
		<style>
			a:link{
				text-decoration:none;
				color:blue;
			}
			a:visited{
				text-decoration:underline;
				color:blue;
			}
			a:hover{
				text-decoration:underline;
				color:blue;
			}
			a:active{
				text-decoration:underline;
				color:blue;
			}
		</style>
	</head>
	<body>
		<div id="tabs" style="width: 100%">
			<ul>
				<li id='tab0'>
					<a href='#tabs-0'>调用关系管理</a>
				</li>
			</ul>
			
			<div id="tabs-0">
			<!--
			<div class="ui-widget-header" style="margin-bottom: 0.5em;padding: 0.2em;">
				<input type = "button" value="新增" id="addOperation" />
				<input type = "button" value="删除" id="deleteOperation" />
				<input type = "button" value="发布" id="deployOperation" />
				<input type = "button" value="重定义" id="redefOperation" />
				<input type = "button" value="上线" id="publishOperation" />
			</div>
			-->
			<table cellpadding="0" cellspacing="0" border="0" class="display" id="invokeTable">
					<tfoot>
						<tr>
							<th>
								<input type="text" name="operationInfo" id="operationInfo" value="操作ID/操作名称"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="serviceInfo" id="serviceInfo" value="服务ID/服务名称"
									class="search_init" />
							</th>						
							<th>
								<input type="text" name="interfaceInfo" id="interfaceInfo" value="接口ID/接口名称"
									class="search_init" />
							</th>							
							<th>
								<input type="text" name="provideMsgType" id="provideMsgType" value="提供方报文类型"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="consumeMsgType" id="consumeMsgType" value="调用方报文类型"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="through" id="through" value="是否穿透"
									class="search_init" />
							</th>

							<th>
								<input type="text" name="state" id="state" value="版本状态"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="onlineVersion" id="onlineVersion" value="上线版本"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="onlineDate" id="onlineDate" value="上线日期"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="remark" id="remark" value="备注"
									class="search_init" />
							</th>	
							<th>
								<input type="text" name="provider" id="provider" value="提供方"
									class="search_init" />
							</th>	
							<th>
								<input type="text" name="consumer" id="consumer" value="调用方"
									class="search_init" />
							</th>	
							<th>
								<input type="text" name="action" id="action" value="动作"
									class="search_init" />
							</th>																											
						</tr>
					</tfoot>
				</table>
			</div>
		</div>
	</body>
</html>


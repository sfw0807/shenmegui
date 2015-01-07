<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>用户管理</title>
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
		<script src="<%=path%>/js/userAllManager.js" type="text/javascript"></script>
		<script src="<%=path%>/js/userAll.js" type="text/javascript"></script>
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
					<a href='#tabs-0'>用户管理</a>
				</li>
			</ul>
			
			<div id="tabs-0">
			<div class="ui-widget-header" style="margin-bottom: 0.5em;padding: 0.2em;">
				<input type = "button" value="新增" id="addUser" />
				<input type = "button" value="删除" id="deleteUser" />
			</div>
			<table cellpadding="0" cellspacing="0" border="0" class="display" id="userTable">
					<tfoot>
						<tr>
							<th>
								<input type="text" name="operationId" id="operationId" value="用户ID"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="operationName" id="operationName" value="用户名"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="serviceId" id="serviceId" value="角色"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="serviceName" id="serviceName" value="机构"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="state" id="state" value="上次登录时间"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="version" id="version" value="用户状态"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="publishVersion" id="publishVersion" value="备注"
									class="search_init" />
							</th>
							<th>
								<input type="hidden" name="publishVersion" id="publishVersion" value=""
									class="search_init" />
							</th>							
						</tr>
					</tfoot>
				</table>
			</div>
		</div>
	</body>
</html>


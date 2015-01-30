<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html >
<html >
	<head>
		<title>上线操作详细信息(按日期)</title>

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
		<script src="<%=path %>/js/layout.js" type="text/javascript"></script>
		<script src="<%=path %>/js/publishInfoManager.js" type="text/javascript"></script>
		<script src="<%=path %>/js/publishOperations.js" type="text/javascript"></script>
		<script src="<%=path %>/js/json/json2.js" type="text/javascript"></script>
		<style>
		   .ui-combobox {
		      position: relative;
		      display: inline-block;
	          }
	       .ui-combobox-toggle {
		      position: absolute;
		      top: 0;
		      bottom: 0;
		      margin-left: -1px;
		      padding: 0;
		      /* adjust styles for IE 6/7 */
		      *height: 1.7em;
		      *top: 0.1em;
	          }
	       .ui-combobox-input {
		     margin: 0;
		     padding: 0.3em;
	          }
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
	        <table cellpadding="0" cellspacing="0" border="0" class="display"
					id="publishOperationsTable">
					<tfoot>
						<tr>
						    <th>
								<input type="text" name="operationId" id="operationId" value="操作Id"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="operationName" id="operationName" value="操作名称"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="serviceId" id="serviceId" value="服务Id"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="serviceName" id="serviceName" value="服务名称"
									class="search_init" />
							</th>
						</tr>
					</tfoot>
				</table>
	</body>
</html>




<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String ctx = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%@ include file="/jsp/includes/header.jsp" %>
<%@ include file="/jsp/includes/jquery.jsp" %>
<%@ include file="/jsp/includes/jquery-ui.jsp" %>
<%@ include file="/jsp/includes/jquery-plugins.jsp" %>
<%@ include file="/jsp/includes/datatable.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>服务操作列表</title>

		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="this is my page">
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">

		<link rel="stylesheet" href="<%=ctx %>/css/index.css" />
		<link rel="stylesheet" href="<%=ctx %>/css/demos.css" />
		<script src="<%=ctx %>/js/layout.js" type="text/javascript"></script>
		<script src="<%=ctx %>/assets/service/js/serviceInfoManager.js" type="text/javascript"></script>
		<script src="<%=ctx %>/js/operationsByServiceId.js" type="text/javascript"></script>
		<script src="<%=ctx %>/js/json/json2.js" type="text/javascript"></script>
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
			#dialog-form label input select{ display:block; }
			#dialog-form select{ margin-bottom:12px; width:95%; padding: .4em; }
		    input.text { margin-bottom:12px; width:95%; padding: .4em; }
		    fieldset { padding:0; border:0; margin-top:25px; }
	  </style>
        
        
	</head>
	<body>
		<div id="tabs" style="width: 100%">
			<ul>
				<li id='tab0'>
					<a href='#tabs-0'>服务操作列表</a>
				</li>
			</ul>
			
			<div id="tabs-0">
	        <table cellpadding="0" cellspacing="0" border="0" class="display"
					id="operationsByServiceIdTable">
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
								<input type="text" name="remark" id="remark" value="操作描述"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="version" id="version" value="版本号" 
								class="search_init" />
							</th>
							<th>
								<input type="text" name="state" id="state" value="版本状态"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="modifyUser" id="modifyUser" value="操作用户"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="updateTime" id="updateTime" value="更新时间"
									class="search_init" />
							</th>
						</tr>
					</tfoot>
				</table>
			</div>
		</div>
	</body>
</html>





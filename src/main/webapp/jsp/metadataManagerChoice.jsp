<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String ctx = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ include file="/jsp/includes/jquery.jsp" %>
<%@ include file="/jsp/includes/jquery-ui.jsp" %>
<%@ include file="/jsp/includes/jquery-plugins.jsp" %>
<%@ include file="/jsp/includes/datatable.jsp" %>

<!DOCTYPE html >
<html>
	<head>
		<title>服务元数据管理</title>

		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="this is my page">
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">

		<link rel="stylesheet" href="<%=path %>/css/index.css" />
		<link rel="stylesheet" href="<%=path %>/css/demos.css" />
		<script src="<%=path %>/js/layout.js" type="text/javascript"></script>
		<script src="<%=path %>/js/metadataManager.js" type="text/javascript"></script>
		<script src="<%=path %>/js/metadata.js" type="text/javascript"></script>
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
			#dialog-form label input { display:block; }
		    input.text { margin-bottom:12px; width:95%; padding: .4em; }
		    fieldset { padding:0; border:0; margin-top:25px; }
		    .ui-dialog .ui-state-error { padding: .3em; }
		    .validateTips { border: 0px solid transparent; padding: 0.3em; }
	  </style>
        
        
	</head>
	<body>
		<div id="tabs" style="width: 100%">
			<ul>
				<li id='tab0'>
					<a href='#tabs-0'>服务元数据管理</a>
				</li>
			</ul>
			
			<div id="tabs-0">
			<div class="ui-widget-header" style="margin-bottom: 0.5em;padding:0.5em;">
			    <button id="choice">确认选取</button>
			</div>
			<div id="dialog-form" style="display:none;">
	               <p class="validateTips"></p>
	            <form>
	              <fieldset>
		          <label>元数据ID</label>
		          <input type="text" name="form_metadataId" id="form_metadataId" class="text ui-widget-content ui-corner-all" />
		          <label>元数据名称</label>
		          <input type="text" name="form_name" id="form_name" class="text ui-widget-content ui-corner-all" />
		          <label>元数据描述</label>
		          <input type="text" name="form_remark" id="form_remark" class="text ui-widget-content ui-corner-all" />
		          <label>类型</label>
		          <input type="text" name="form_type" id="form_type" class="text ui-widget-content ui-corner-all" />
		          <label>长度</label>
		          <input type="text" name="form_length" id="form_length" class="text ui-widget-content ui-corner-all" />
		          <label>精度</label>
		          <input type="text" name="form_scale" id="form_scale" class="text ui-widget-content ui-corner-all" />
	              </fieldset>
	           </form>
            </div>
	        <table cellpadding="0" cellspacing="0" border="0" class="display"
					id="metadataTable">
					<tfoot>
						<tr>
							<th >
								<input type="text" name="metadataId" id="metadataId" value="元数据ID"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="name" id="name" value="元数据名称"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="remark" id="remark" value="元数据描述"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="type" id="type" value="类型"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="length" id="length" value="长度" 
								class="search_init" />
							</th>
							<th>
								<input type="text" name="scale" id="scale" value="精度"
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




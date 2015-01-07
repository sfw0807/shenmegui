<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html >
<html >
	<head>
		<title>服务管理</title>

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
		<script src="<%=path %>/js/jquery.fileDownload.js" type="text/javascript"></script>
		<script src="<%=path %>/js/layout.js" type="text/javascript"></script>
		<script src="<%=path %>/js/serviceInfoManager.js" type="text/javascript"></script>
		<script src="<%=path %>/js/serviceInfo.js" type="text/javascript"></script>
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
			#dialog-form label input select{ display:block; }
			#dialog-form select{ margin-bottom:12px; width:95%; padding: .4em; }
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
					<a href='#tabs-0'>服务管理</a>
				</li>
			</ul>
			
			<div id="tabs-0">
			<div class="ui-widget-header" style="margin-bottom: 0.5em;padding:0.5em;">
			    <button id="add">新增</button>
			    <button id="del">删除</button>
			    <button id="modify">修改</button>
			    <button id="deploy">发布</button>
			    <button id="redef">重定义</button>
			    <button id="publish">上线</button>
			    <button id="operationsInfo">操作</button>
			</div>
			<div id="dialog-form" style="display:none;">
	               <p class="validateTips"></p>
	            <form>
	              <fieldset>
		          <label for="form_serviceId">服务ID</label>
		          <input type="text"  id="form_serviceId" class="text ui-widget-content ui-corner-all" />
		          <label for="form_serviceName">服务名称</label>
		          <input type="text"  id="form_serviceName" class="text ui-widget-content ui-corner-all" />
		          <label for="form_serviceRemark">服务描述</label>
		          <input type="text"  id="form_serviceRemark" class="text ui-widget-content ui-corner-all" />
		          <label for="form_categoryId">服务分组</label>
		          <br>
		          <select id="form_categoryId" class="text ui-widget-content ui-corner-all">
		          </select>
		          <br>
		          <br>
		          <label for="form_version" style="margin-top: 4em;">版本号</label>
		          <input type="text"  id="form_version" class="text ui-widget-content ui-corner-all" />
		          <label for="form_state">版本状态</label>
		          <br> 
		          <select id="form_state" class="text ui-widget-content ui-corner-all" style="margin-bottom: 12px;">
		             <option value="服务定义" selected>服务定义</option>
		             <option value="开发" >开发</option>
		             <option value="联调测试" >联调测试</option>
		             <option value="sit测试" >sit测试</option>
		             <option value="uat测试" >uat测试</option>
		             <option value="投产验证" >投产验证</option>
		             <option value="上线" >上线</option>
		          </select>
		          <br>
	              </fieldset>
	           </form>
            </div>
	        <table cellpadding="0" cellspacing="0" border="0" class="display"
					id="serviceInfoTable">
					<tfoot>
						<tr>
							<th>
								<input type="text" name="serviceId" id="serviceId" value="服务Id"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="serviceName" id="serviceName" value="服务名称"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="serviceRemark" id="serviceRemark" value="服务描述"
									class="search_init" />
							</th>
							<th>
								<input type="text" name=categoryId id="categoryId" value="分组Id"
									class="search_init" />
							</th>
							<th>
								<input type="text" name=categoryName id="categoryName" value="分组名称"
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




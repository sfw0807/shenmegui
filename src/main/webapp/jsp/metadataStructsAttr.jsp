<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html >
<html >
	<head>
		<title>元数据结构属性管理</title>

		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="this is my page">
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">

		<link rel="stylesheet" href="<%=path %>/themes/smoothness/jquery-ui-1.8.4.custom.css" />
		<link rel="stylesheet" href="<%=path %>/js/jquery-ui/development-bundle/themes/base/jquery.ui.all.css" />
		<link rel="stylesheet" href="<%=path %>/css/index.css" /
		<script src="<%=path %>/js/jquery-ui/js/jquery-1.10.2.js"></script>
		<script src="<%=path %>/js/jquery-1.8.2.js"></script>
		<script src="<%=path %>/js/jquery.bgiframe-2.1.2.js"></script>
	    <script src="<%=path %>/js/jquery.ui.core.js"></script>
	    <script src="<%=path %>/js/jquery.ui.widget.js"></script>
	    <script src="<%=path %>/js/jquery.ui.datepicker.js"></script>
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
		<script src="<%=path %>/js/metadataStructsManager.js" type="text/javascript"></script>
		<script src="<%=path %>/js/metadataStructsAttr.js" type="text/javascript"></script>
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
					<a href='#tabs-0'>元数据结构属性管理</a>
				</li>
			</ul>
			
			<div id="tabs-0">
			<div class="ui-widget-header" style="margin-bottom: 0.5em;padding:0.5em;">
			    <label>元数据结构Id</label>
			    <input type='Text' value='' id='sId'/>
			    <label>元数据结构名称</label>
			    <input type='Text' value='' id='sName'/>
			    <label>描述</label>
			    <input type='Text' value='' id='sRemark'/>
			    <button id="save">保存</button>
			</div>
			<div class="ui-widget-header" style="margin-bottom: 0.0em;">
			    <button id="add">添加</button>
			    <button id="del">删除</button>
			    <button id="edit">编辑</button>
			</div>
	        <table cellpadding="0" cellspacing="0" border="0" class="display"
					id="metadataStructsAttrTable">
					<!--  
					<tfoot>
						<tr>
							<th>
								<input type="text" name="structId" id="structId" value="元数据结构Id"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="elementID" id="elementID" value="原始元数据Id"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="elementName" id="elementName" value="原始元数据名称"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="remark" id="remark" value="描述"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="isRequired" id="isRequired" value="是否必输"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="metadataId" id="metadataId" value="元数据ID"
									class="search_init" />
							</th>
						</tr>
					</tfoot>
					-->
				</table>
			</div>
		</div>
	</body>
</html>




<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html >
<html >
	<head>
		<title>接入系统新增/修改</title>

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
		<script src="<%=path %>/js/systemInfoManager.js" type="text/javascript"></script>
		<script src="<%=path %>/js/systemInfoAttr.js" type="text/javascript"></script>
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
      <script>
       $(function(){
           $("#firstTime").datepicker({
		          "changeMonth":true,
		          "changeYear":true,
		          "dateFormat":"yy-mm-dd",
		        });
       })
      </script>
	</head>
	<body>
		<div id="tabs" style="width: 100%">
			<ul>
				<li id='tab0'>
					<a href='#tabs-0'>接入系统新增/修改</a>
				</li>
			</ul>
			
			<div id="tabs-0">
			<div class="ui-widget-header" style="margin-bottom: 0.5em;padding:0.5em;">
			    <label>系统Id</label>
			    <input type='Text' value='' id='sysId'/>
			    <label>系统简称</label>
			    <input type='Text' value='' id='sysAb'/>
			    <label>系统名称</label>
			    <input type='Text' value='' id='sysName'/>
			    <label>系统描述</label>
			    <input type='Text' value='' id='remark'/>
			    <br>
			    <label>首次上线时间</label>
			    <input type='Text' value='' id='firstTime'/>
			    <button id="save">保存</button>
			</div>
			<div class="ui-widget-header" style="margin-bottom: 0.0em;">
			    <button id="add">添加</button>
			    <button id="del">删除</button>
			    <button id="edit">编辑</button>
			</div>
	        <table cellpadding="0" cellspacing="0" border="0" class="display"
					id="protocolInfoTable">
				</table>
			</div>
		</div>
	</body>
</html>





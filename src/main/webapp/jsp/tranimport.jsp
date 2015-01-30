<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>存量交易导入</title>

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
		<script src="<%=path %>/js/resourceImport.js" type="text/javascript"></script>
		<script src="<%=path %>/js/json/json2.js" type="text/javascript"></script>
		<script type="text/javascript">
		    $(function(){
			    $('#pfileUpload').change(function(){
			      var fileName = $('#pfileUpload').val();
			      if(fileName == ""){
			          $('#pimport').attr("disabled",true);
			      }
			      else{
			          $('#pimport').attr("disabled",false);
			      }
			    });
			    $('#cfileUpload').change(function(){
			      var fileName = $('#cfileUpload').val();
			      if(fileName == ""){
			          $('#cimport').attr("disabled",true);
			      }
			      else{
			          $('#cimport').attr("disabled",false);
			      }
			    });
		    })
		</script>
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
					<a href='#tabs-0'>提供方导入</a>
				</li>
				<li id='tab2'>
					<a href='#tabs-1'>调用方导入</a>
				</li>
			</ul>
			
			<div id="tabs-0">
			<div class="ui-widget-header" style="margin-bottom: 0.1em;padding:0.1em;">
			<form action="<%=path %>/tranlink/provider" method="post" enctype="multipart/form-data" id="provider">
			  请选择导入的提供方交易文件：
			  <input  style ="width:14%;" type = "file" name="file" id="pfileUpload" />
			  <input  style ="width:4%;" type = "submit" value="导入" id="pimport" disabled="true"/>
			</form>
			</div>
	       </div>
	       
	       <div id="tabs-1">
			<div class="ui-widget-header" style="margin-bottom: 0.1em;padding:0.1em;">
			<form action="<%=path %>/tranlink/consumer" method="post" enctype="multipart/form-data" id="consumer" >
			  请选择导入的调用方交易文件：
			  <input  style ="width:14%;" type = "file" name="file" id="cfileUpload" />
			  <input  style ="width:4%;" type = "submit" value="导入" id="cimport" disabled="true" />
			</form>
			</div>
	       </div>  
						
   
		</div>
	</body>
</html>




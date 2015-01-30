<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>PDF导出</title>
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
		<link rel="stylesheet"
			href="<%=path %>/themes/smoothness/jquery-ui-1.8.4.custom.css" />
		<link rel="stylesheet" href="<%=path %>/css/index.css" />
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
		<link rel='stylesheet' type='text/css'
			href='<%=path %>/js/jquery.datatables/css/jquery.dataTables.css' />
		<script src="<%=path %>/js/jquery.fileDownload.js" type="text/javascript"></script>
		<script src="<%=path %>/js/layout.js" type="text/javascript"></script>
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
	
	        .ui-draggable, .ui-droppable {
	            background-position: top;
	        }
	    </style>
	    <script type="text/javascript" language="JavaScript">
	    $(function(){
	    	$('#tabs').tabs(); 
	    	//所有系统信息的下拉框
			function initFirst() {
		          $.ajax({
		            url: '<%=path%>/serviceCategory/first',
		            type: 'GET',
		            success: function(result) {
		                initSelectF(result);
		            }
		          });
		    }
		    function initSelectF(result) {
				for (var i=0;i<result.length;i++)
					if(result[i].categoryId!="MSMGroup"){
						$('#fcategory').append("<option value='"+result[i].categoryId+"'>"+result[i].categoryId+":"+result[i].categoryName+"</option>");
					}
			}
			initFirst();
			function initSecond() {
		          $.ajax({
		            url: '<%=path%>/serviceCategory/second',
		            type: 'GET',
		            success: function(result) {
		                initSelectS(result);
		            }
		          });
		    }
		    function initSelectS(result) {
				for (var i=0;i<result.length;i++)
					$('#scategory').append("<option value='"+result[i].categoryId+"'>"+result[i].categoryId+":"+result[i].categoryName+"</option>");
			}
			initSecond();
			function initService() {
		          $.ajax({
		            url: '<%=path%>/serviceInfo/servicelist',
		            type: 'GET',
		            success: function(result) {
		                initSelectService(result);
		            }
		          });
		    }
		    function initSelectService(result) {
				for (var i=0;i<result.length;i++)
					$('#service').append("<option value='"+result[i].serviceId+"'>"+result[i].serviceId+":"+result[i].serviceName+"</option>");
				//$('#service').combobox();
			}
			initService();
			$("#exportFCategoryPdf").button().click(function(){
				var param = $('#fcategory').val();
				$.fileDownload(encodeURI(encodeURI("../pdfExport/categorypdf/" + param)), {});
			});
			$("#exportSCategoryPdf").button().click(function(){
				var param = $('#scategory').val();
				$.fileDownload(encodeURI(encodeURI("../pdfExport/servicepdf/" + param)), {});
			});
			$("#exportServicePdf").button().click(function(){
				var param = $('#service').val();
				$.fileDownload(encodeURI(encodeURI("../pdfExport/operationpdf/" + param)), {});
			});
			$("#exportAllPdf").button().click(function(){
				$.fileDownload(encodeURI(encodeURI("../pdfExport/allpdf")), {});
			});
	    })
	    </script>	    
  </head>
  
  <div id="tabs" style="width:100%">
			<ul>
				<li id='tab0'><a  href='#tabs-0'>一级服务分类PDF导出</a></li>
				<li id='tab1'><a  href='#tabs-1'>二级服务分类PDF导出</a></li>
				<li id='tab2'><a  href='#tabs-2'>服务PDF导出</a></li>
				<li id='tab3'><a  href='#tabs-3'>全量导出</a></li>
			</ul>
			<div id="tabs-0">
				<div class="ui-widget-header" style="margin-bottom: 0.5em;padding: 0.2em;">
					<input  type = "button" value="导出" id="exportFCategoryPdf" />
				</div>
						一级服务分类:<select id="fcategory"></select><br>				
			</div>
			<div id="tabs-1">
				<div class="ui-widget-header" style="margin-bottom: 0.5em;padding: 0.2em;">
					<input  type = "button" value="导出" id="exportSCategoryPdf" />
				</div>
						二级服务分类:<select id="scategory"></select><br>				
			</div>			
			<div id="tabs-2">
				<div class="ui-widget-header" style="margin-bottom: 0.5em;padding: 0.2em;">
					<input  type = "button" value="导出" id="exportServicePdf" />
				</div>
						服务:<select id="service"></select><br>				
			</div>
			<div id="tabs-3">
				<div class="ui-widget-header" style="margin-bottom: 0.5em;padding: 0.2em;">
					<input  type = "button" value="导出" id="exportAllPdf" />
				</div>
			</div>						
		</div>
	</body>
</html>


<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>新增提供方交易</title>
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <!--<link rel="stylesheet" type="text/css" href="./styles.css">--><!--
		
		<link rel="stylesheet" href="/themes/smoothness/jquery-ui-1.8.4.custom.css" />
		<link rel="stylesheet" href="/css/index.css" />
		<script src="/js/jquery-ui/js/jquery-1.10.2.js"></script>
		<script src="/js/jquery-1.8.2.js"></script>	
		<script src="/js/jquery-ui-tabs.js"></script>
		<script src='/js/jquery.datatables/js/jquery.dataTables.js' type="text/javascript"></script>
		<link rel='stylesheet' type='text/css' href='/js/jquery.datatables/css/jquery.dataTables.css' />
		
		<script src="/js/jquery.ui.core.js"></script>
		<script src="/js/jquery.ui.widget.js"></script>
		<script src="/js/jquery.ui.datepicker.js"></script>
		-->
		<link rel="stylesheet" href="<%=path%>/themes/smoothness/jquery-ui-1.8.4.custom.css" />
		<link rel="stylesheet" href="<%=path%>/js/jquery-ui/development-bundle/themes/base/jquery.ui.all.css" />
		<link rel="stylesheet" href="<%=path%>/css/index.css" />
		
		<script src="<%=path%>/js/jquery-ui/js/jquery-1.10.2.js"></script>
		<script src="<%=path%>/js/jquery-1.8.2.js"></script>
		<script src="<%=path%>/js/jquery-ui-tabs.js"></script>
		<script src="<%=path%>/js/jquery.datatables/js/jquery.dataTables.js" type="text/javascript"></script>
		<link rel='stylesheet' type='text/css' href='<%=path%>/js/jquery.datatables/css/jquery.dataTables.css' />
		
		<script src="<%=path%>/js/jquery.ui.core.js"></script>
		<script src="<%=path%>/js/jquery.ui.widget.js"></script>
		<script src="<%=path%>/js/jquery.ui.datepicker.js"></script>
		<script src="<%=path%>/js/layout.js" type="text/javascript"></script>
		<script src="<%=path%>/js/addOperation.js" type="text/javascript"></script>
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
	    	var tranManager = {
	    		add: function(param) {
			        $.ajax({
			            "type": "POST",
			            "contentType": "application/json; charset=utf-8",
			            "url": "../tranlink/saveTranprovider",
			            "dataType": "json",
			            "data":JSON.stringify(param),
			            "success": function(result) {
			            	if(result){
			            		alert("新增成功");
			            		window.location.href="tranlink.jsp";
			            	}else{
			            		alert("新增失败");
			            	}
			            }
			        });
			    }
	    	}
	    	$(function() {
				$("#save").click(function(){
					var trancode = $("#trancode").val();
					var tranname = $("#tranname").val();
					var provider = $("#provider").val();
					var providerMsgType = $("#providerMsgType").val();
					var charger = $("#charger").val();
					var remark = $("#remark").val();
					var param = [trancode,tranname,provider,providerMsgType,charger,remark];
					tranManager.add(param);
				});
	    	});
	    </script>	    
  </head>
  
  <div id="tabs" style="width:100%">
			<ul>
				<li id='tab0'><a  href='#tabs-0'>新增提供方交易</a></li>
			</ul>
			<div id="tabs-0">
				<div class="ui-widget-header" style="margin-bottom: 0.5em;padding: 0.2em;">
					<input  type = "button" value="保存" id="save" />
				</div>
			<label style="width: 16%">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;交易码:
			</label>
			<input type="text" id="trancode" value="" style="width: 12%"
				 />
			<br>
			<label style="width: 16%">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;交易名称:
			</label>
			<input type="text" id="tranname" value="" style="width: 12%"
				 />
			<br>
			<label style="width: 16%">
				提供系统名称:
			</label>
			<input type="text" id="provider" value="" style="width: 12%"
				 />
			<br>
			<label style="width: 16%">
				提供报文类型:
			</label>
			<input type="text" id="providerMsgType" value="" style="width: 12%;"
				 />
			<br>
			<label style="width: 16%">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;负责人:
			</label>
			<input type="text" id="charger" value="" style="width: 12%;"
				 />
			<br>
			<label style="width: 16%">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;备注:
			</label>
			<input type="text" id="remark" value="" style="width: 12%;"
				 />
			<br>
			</div>
		</div>
	</body>
</html>


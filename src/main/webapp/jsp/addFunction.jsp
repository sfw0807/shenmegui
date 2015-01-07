<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>新增权限</title>
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
	    	var functionManager = {
	    		add: function(param) {
			        $.ajax({
			            "type": "POST",
			            "contentType": "application/json; charset=utf-8",
			            "url": "../function/add",
			            "dataType": "json",
			            "data":JSON.stringify(param),
			            "success": function(result) {
			            	if(result){
			            		alert("新增成功");
			            		window.location.href="functionManager.jsp";
			            	}else{
			            		alert("新增失败");
			            	}
			            }
			        });
			    }
	    	}
	    	$(function() {
	    		//所有角色信息的下拉框
				function initFunctionInfo() {
			          $.ajax({
			            url: '<%=path%>/function/list',
			            type: 'GET',
			            success: function(result) {
			                initFunctionInfoSelect(result);
			            }
			          });
			    }
			    function initFunctionInfoSelect(result) {
			    	$("#parentfunc").append("<option value='0' selected='true'>请选择权限</option>");
					for (var i=0;i<result.length;i++)
						$("#parentfunc").append("<option value='"+result[i].id+"'>"+result[i].name+"</option>");
				}
				initFunctionInfo();
				$("#saveFunction").click(function(){
					var funcname = $("#funcname").val();
					var url = $("#url").val();
					var parentfunc = $("#parentfunc").val();
					var remark = $("#remark").val();
					var param = [funcname,url,parentfunc,remark];
					functionManager.add(param);
				});
	    	});
	    </script>	    
  </head>
  
  <div id="tabs" style="width:100%">
			<ul>
				<li id='tab0'><a  href='#tabs-0'>新增权限</a></li>
			</ul>
			<div id="tabs-0">
				<div class="ui-widget-header" style="margin-bottom: 0.5em;padding: 0.2em;">
					<input  type = "button" value="保存" id="saveFunction" />
				</div>
				<table style="width:50%" id="userTable">
					<tr>
						<td><label style="width:19%">&nbsp;&nbsp;&nbsp;权限名称<font color="red">*</font>:</label><input type="text" id="funcname"  style="width:70%"  /></td>
						<td><label style="width:16%">&nbsp;访问路径<font color="red">*</font>:</label><input type="text" id="url" value="" style="width:70%"  /></td>
					</tr>	
					<tr>
						
						<td><label style="width:19%">&nbsp;&nbsp;&nbsp;父级权限<font color="red">*</font>:</label><select id="parentfunc"/></select>					
					</tr>	
					<tr>
						<td><label style="width:16%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;备注信息:</label><input type="text" id="remark" value="" style="width:70%"  /></td>
						<td></td>		
					</tr>
				</table>
			</div>
		</div>
	</body>
</html>


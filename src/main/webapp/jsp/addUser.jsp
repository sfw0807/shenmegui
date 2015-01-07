<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>addopertation.jsp</title>
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
	    	var userManager = {
	    		add: function(param) {
			        $.ajax({
			            "type": "POST",
			            "contentType": "application/json; charset=utf-8",
			            "url": "../user/add",
			            "dataType": "json",
			            "data":JSON.stringify(param),
			            "success": function(result) {
			            	if(result){
			            		alert("新增成功");
			            		window.location.href="userManager.jsp";
			            	}else{
			            		alert("新增失败");
			            	}
			            }
			        });
			    }
	    	}
	    	$(function() {
	    		//所有角色信息的下拉框
				function initRoleInfo() {
			          $.ajax({
			            url: '<%=path%>/role/list',
			            type: 'GET',
			            success: function(result) {
			                initRoleInfoSelect(result);
			            }
			          });
			    }
			    function initRoleInfoSelect(result) {
			    	$('#role').append("<option value='0' selected='true'>请选择角色</option>");
					for (var i=0;i<result.length;i++)
						$('#role').append("<option value='"+result[i].id+"'>"+result[i].name+"</option>");
				}
				initRoleInfo();
	    		//所有机构信息的下拉框
				function initOrgInfo() {
			          $.ajax({
			            url: '<%=path%>/org/list',
			            type: 'GET',
			            success: function(result) {
			                initOrgInfoSelect(result);
			            }
			          });
			    }
			    function initOrgInfoSelect(result) {
			    	$('#org').append("<option value='0' selected='true'>请选择机构</option>");
					for (var i=0;i<result.length;i++)
						$('#org').append("<option value='"+result[i].orgId+"'>"+result[i].orgAB+":"+result[i].orgName+"</option>");
				}
				initOrgInfo();
				$("#saveUser").click(function(){
					var username = $("#username").val();
					var initpwd = $("#initpwd").val();
					var role = $("#role").val();
					var org = $("#org").val();
					var status = $("#status").val();
					var remark = $("#remark").val();
					var param = [username,initpwd,role,org,status,remark];
					userManager.add(param);
				});
	    	});
	    </script>	    
  </head>
  
  <div id="tabs" style="width:100%">
			<ul>
				<li id='tab0'><a  href='#tabs-0'>新增用户</a></li>
			</ul>
			<div id="tabs-0">
				<div class="ui-widget-header" style="margin-bottom: 0.5em;padding: 0.2em;">
					<input  type = "button" value="保存" id="saveUser" name="saveUser"/>
				</div>
				<table style="width:50%" id="userTable">
					<tr>
						<td><label style="width:16%">&nbsp;&nbsp;&nbsp;&nbsp;用户名<font color="red">*</font>:</label><input type="text" id="username" value="" style="width:70%"  /></td>
						<td><label style="width:19%">&nbsp;&nbsp;&nbsp;初始密码<font color="red">*</font>:</label><input type="text" id="initpwd" value="111111" style="width:70%"  /></td>
					</tr>	
					<tr>
						<td><label style="width:16%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;角色<font color="red">*</font>:</label><select id="role"></select></td>
						<td>
						<label style="width:19%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 机构<font color="red">*</font>:</label>
						<select id="org"></select>					
						</td>
					</tr>	
					<tr>
						<td><label style="width:16%"> &nbsp;用户状态<font color="red">*</font>:</label>
							<select id="status">
								<option value="1">正常</option>
								<option value="0">无效</option>
							</select>	
						</td>
						<td><label style="width:19%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;备注信息:</label><input type="text" id="remark" value="" style="width:70%" value="1.0.0" /></td>
					</tr>
				</table>
			</div>
		</div>
	</body>
</html>


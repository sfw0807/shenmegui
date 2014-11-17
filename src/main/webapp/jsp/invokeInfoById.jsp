<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>invokeInfoById.jsp</title>
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
		<script src='<%=path%>/js/jquery.datatables/js/jquery.dataTables.js' type="text/javascript"></script>
		<link rel='stylesheet' type='text/css' href='<%=path%>/js/jquery.datatables/css/jquery.dataTables.css' />
		
		<script src="<%=path%>/js/jquery.ui.core.js"></script>
		<script src="<%=path%>/js/jquery.ui.widget.js"></script>
		<script src="<%=path%>/js/jquery.ui.datepicker.js"></script>
		<script src="<%=path%>/js/invokeInfoByIdManager.js" type="text/javascript"></script>
		<script src="<%=path%>/js/layout.js" type="text/javascript"></script>
		<script src="<%=path%>/js/invokeInfoById.js" type="text/javascript"></script>
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
  </head>
  
  <div id="tabs" style="width:100%">
			<ul>
				<li id='tab0'><a  href='#tabs-0'>基本定义</a></li>
			</ul>
			<div id="tabs-0">
				<div class="ui-widget-header" style="margin-bottom: 0.5em;padding: 0.2em;">
					<input  type = "button" value="保存" id="saveInvokeInfo" />
				</div>
				<table style="width:50%" id="invokeInfoTable">
					<tr>
						<td><label style="width:16%">操作ID:</label><input type="text" id="operationId" value="" style="width:80%" disabled="true"/></td>
						<td><label style="width:19%">服务ID:</label><input type="text" id="serviceId" value="" style="width:80%" disabled="true" /></td>
					</tr>	
					<tr>
						<td><label style="width:16%">接口ID:</label><input type="text" id="interfaceId" value="" style="width:80%" disabled="true"/></td>
						<td><label style="width:16%">&nbsp;&nbsp;&nbsp;&nbsp;穿透:</label><input type="text" id="through" value="" style="width:79.7%" disabled="true"/></td>
					</tr>	
					<tr>
						<td ><label style="width:16%">提供方报文类型:</label><input type="text" id="provideMsgType" value="" style="width:66.4%" disabled="true"/></td>
						<td ><label style="width:16%">调用方报文类型:</label><input type="text" id="consumeMsgType" value="" style="width:67%;" disabled="true" /></td>
					</tr>
					<tr>
						<td colspan="2"><label style="width:16%">上线版本:</label><input type="text" id="onlineVersion" value="" style="width:38.3%;" disabled="true" /></td>
					</tr>						
					<tr>
						<td colspan="2"><label style="width:16%">上线时间:</label><input type="text" id="onlineDate" value="" style="width:38.3%;" disabled="true" /></td>
					</tr>					
					<tr>
						<td colspan="2"><label style="width:16%">版本状态:</label>						
							<select id="state" class="text ui-widget-content ui-corner-all" >
                             <option value="服务定义">服务定义</option>
                             <option value="开发" >开发</option>
                             <option value="联调测试" >联调测试</option>
                             <option value="sit测试" >sit测试</option>
                             <option value="uat测试" >uat测试</option>
                             <option value="投产验证" >投产验证</option>
                             <option value="上线" >上线</option>
                         	</select>	
                        </td>
					</tr>	

				</table>
			</div>			
		</div>
	</body>
</html>


<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>sda详细信息</title><link rel="stylesheet" type="text/css"
	href="/resources/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/resources/themes/icon.css">
<link href="/resources/css/ui.css" rel="stylesheet" type="text/css">
    
    <script type="text/javascript" src="resources/js/jquery.min.js"></script>
<script type="text/javascript" src="resources/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/jsp/service/operation/operation.js"></script>
</head>
<body >
<fieldset>
 <legend>基本信息</legend>
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
     <th>服务</th>
    <td >
    	<input class="easyui-textbox" value="${service.serviceName }" />&nbsp;&nbsp;
    	<a iconcls="icon-search"  class="easyui-linkbutton l-btn l-btn-small" onclick="choseService('dlg')"  href="javascript:void(0)" >选择服务</a>
    	</td>
     </tr>
</table>


</fieldset>

<fieldset>
 <legend>未审核场景列表</legend>
 	  <table id="operationAuditList" class="easyui-datagrid"
		data-options="
				rownumbers:true,
				singleSelect:false,
				url:'/operation/getAudits/${service.serviceId }',
				method:'get',
				pagination:true,
				toolbar:'#tb',
				pageSize:10"
		style="height:370px; width:auto;">
		<thead>
			<tr>
				<th data-options="field:'operationId',checkbox:true"></th>
				<th data-options="field:'operationName'">场景名称</th>
				<th data-options="field:'operationDesc'">功能描述</th>
				<th data-options="field:'version'">版本号</th>
				<th data-options="field:'optDate'">更新时间</th>
				<th data-options="field:'optUser'">更新用户</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto">
    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><a href="javascript:void(0)" onclick="auditUnPass('operationAuditList');" class="easyui-linkbutton" iconCls="icon-cancel" plain="true">审核不通过</a>&nbsp;&nbsp;
	    <a href="javascript:void(0)" onclick="auditPass('operationAuditList');" class="easyui-linkbutton" iconCls="icon-ok" plain="true">审核通过</a>
    </td>
    </tr>
    </table>
    </div>
</fieldset>


    </div>
  <div id="dlg" class="easyui-dialog"
		style="width:400px;height:280px;padding:10px 20px" closed="true"
		resizable="true"></div>
  
  </body>
</html>

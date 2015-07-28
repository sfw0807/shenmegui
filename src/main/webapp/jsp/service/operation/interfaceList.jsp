<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  </head>
  
  <body>
   <table id="intefaceList" class="easyui-datagrid"
		data-options="
				rownumbers:true,
				singleSelect:true,
				url:'/serviceLink/getInterface?systemId=${param.systemId}',
				method:'get',
				toolbar:'#tb',
				pagination:true,
				pageSize:10"
		style="height:370px; width:auto;">
		<thead>
			<tr>
				<th data-options="field:'invokeId',checkbox:true"></th>
				<th data-options="field:'systemId', width:50">系统id</th>
				<th data-options="field:'systeChineseName', width:100" formatter='ff.systemChineseName'>系统名称</th>
				<th data-options="field:'interfaceId', width:50">接口id</th>
				<th data-options="field:'inter.interfaceName', width:100" formatter='ff.interfaceName'>接口名称</th>
				<th data-options="field:'remark', width:50">备注</th>
			</tr>
		</thead>
	</table>
	
	<div id="tb" style="padding:5px;height:auto">
    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><a href="javascript:void(0)" onclick="$('#opDlg').dialog('close');" class="easyui-linkbutton" iconCls="icon-cancel" plain="true">取消</a>&nbsp;&nbsp;
	    <a href="javascript:void(0)" onclick="selectInterface('${param.newListId}', '${param.type }');" class="easyui-linkbutton" iconCls="icon-ok" plain="true">确定</a>
    </td>
    </tr>
    </table>
    </div>
	
  </body>
</html>

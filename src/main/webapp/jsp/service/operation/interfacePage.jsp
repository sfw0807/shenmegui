<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>列表页</title>
<link rel="stylesheet" type="text/css"
	href="/resources/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/resources/themes/icon.css">
<link href="/resources/css/css.css" rel="stylesheet" type="text/css">
</head>

<body>
	<fieldset>
		<div>
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th>服务代码</th>
					<td><input class="easyui-textbox" type="text" name="1" id="1"
						value="${service.serviceId }" disabled="disabled">
					</td>
					<th>服务名称</th>
					<td><input class="easyui-textbox" type="text" name="2" id="2"
						value="${service.serviceName }" disabled="disabled">
					</td>
					<th>场景号</th>
					<td><input class="easyui-textbox" type="text" name="3" id="3"
						value="${operation.operationId }" disabled="disabled">
					</td>
					<th>场景名称</th>
					<td><input class="easyui-textbox" type="text" name="4" id="4"
						value="${operation.operationName }" disabled="disabled">
					</td>
				</tr>
				<tr>
					<th>应用系统</th>
					<td><input id="systemList" type="text" /></td>
				</tr>
				<tr>
					<th>映射关系列表</th>
					<td></td>
				</tr>
			</table>
		</div>
		<div>
			<table id="invokeList" class="easyui-datagrid"
				data-options="	rownumbers:true,
								singleSelect:true,
								url:'/operation/getInterfaceByOSS?operationId=${operation.operationId }&serviceId=${service.serviceId }',
								method:'get',
								pagination:true,
								pageSize:10"
				style="height:200px; width:100%;">
				<thead>
					<tr>
						<th data-options="field:'invokeId',checkbox:true"></th>
						<th data-options="field:'systemId', width:50">系统id</th>
						<th data-options="field:'systeChineseName', width:150"
							formatter='ff.systemChineseName'>系统名称</th>
						<th data-options="field:'isStandard', width:50"
							formatter='ff.isStandardText'>标准</th>
						<th data-options="field:'interfaceId', width:50">接口id</th>
						<th data-options="field:'inter.interfaceName', width:150"
							formatter='ff.interfaceName'>接口名称</th>
						<th data-options="field:'type', width:50"
							formatter='ff.typeText'>类型</th>
						<th data-options="field:'desc', width:100">描述</th>
						<th data-options="field:'remark', width:100">备注</th>
					</tr>
				</thead>
			</table>

		</div>

	</fieldset>
	<fieldset>
		<table width="100%">
			<tr>
				<td width="50%">
					接口需求
				</td>
				<td width="50%">
					映射结果
				</td>
			</tr>
			<tr>
				<td width="45%">
					<table title="sda" class="easyui-treegrid" id="tg" style=" width:auto;"
			data-options="
				iconCls: 'icon-ok',
				rownumbers: true,
				animate: true,
				fitColumns: true,
				url: '/sda/sdaTree?serviceId=${service.serviceId }&operationId='+encodeURI(encodeURI('${operation.operationId }')),
				method: 'get',
				idField: 'id',
				treeField: 'text'
				"
                >
		<thead>
			<tr>
				<th data-options="field:'text',width:180,editor:'text'">字段名</th>
				<th data-options="field:'append1',width:60,align:'right',editor:'text'">中文名称</th>
				<th data-options="field:'append2',width:80,editor:'text'">功能描述</th>
				<th data-options="field:'append3',width:80,editor:'text'">备注</th>
			</tr>
		</thead>
	</table>
				</td>
				<td>
				
				</td>
			</tr>
		</table>
	</fieldset>
	<script type="text/javascript" src="/resources/js/jquery.min.js"></script>
	<script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="/jsp/service/operation/operation.js"></script>
	<script type="text/javascript">
		$("#systemList").combobox({
			data : ${systemList},
			valueField : 'systemId',
			textField : 'systemChineseName',
			onChange:function(newValue, oldValue){
						this.value=newValue;
						$("#invokeList").datagrid({
							url: "/operation/getInterfaceByOSS?operationId=${operation.operationId }&serviceId=${service.serviceId }&systemId=" + newValue
						});
						
					}
		});
	</script>

</body>
</html>

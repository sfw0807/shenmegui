<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>列表页</title>
<link rel="stylesheet" type="text/css"
	href="/resources/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css"
		href="/resources/themes/icon.css">
		<link href="/resources/css/css.css" rel="stylesheet" type="text/css">
			<script type="text/javascript" src="/resources/js/jquery.min.js"></script>
	<script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="/jsp/version/version.js"></script>
	<script type="text/javascript">
		var toolbar = [ {
			text : '移出',
			iconCls : 'icon-cancel',
			handler : function() {
				var rows = $('#operationList').datagrid("getSelections");	//获取你选择的所有行
		      //循环所选的行
		      	for(var i =0;i<rows.length;i++){    
		            var index = $('#operationList').datagrid('getRowIndex',rows[i]);//获取某行的行号
		            $('#operationList').datagrid('deleteRow',index);	//通过行号移除该行
		        }
			}
		} ];
		var toolbar2 = [ {
			text : '移出',
			iconCls : 'icon-cancel',
			handler : function() {
				var rows = $('#pcList').datagrid("getSelections");	//获取你选择的所有行
		      //循环所选的行
		      	for(var i =0;i<rows.length;i++){    
		            var index = $('#pcList').datagrid('getRowIndex',rows[i]);//获取某行的行号
		            $('#pcList').datagrid('deleteRow',index);	//通过行号移除该行
		        }
			}
		} ];
		function releaseBatch() {
			if(!$("#baseForm").form('validate')){
				return false;
			}
			var opItems = $('#operationList').datagrid('getChecked');
		//	var pcItems = $('#pcList').datagrid('getChecked');
			var operations = new Array();
			$.each(opItems, function(index, item) {
				var opertion = {};
				opertion.serviceId = item.serviceId;
				opertion.operationId = item.operationId;
				opertion.operationDesc =  $("#desc").textbox("getValue");
			//	opertion.operationRemark =  $("#code").textbox("getValue");
				operations.push(opertion);
			});
			$.ajax({
				type: "post",
				async: false,
				contentType:"application/json; charset=utf-8",
				url: "/operation/releaseBatch",
				dataType: "json",
				data: JSON.stringify(operations),
				success: function(data){
					alert("操作成功");
					 $('#operationList').datagrid('reload');
				}
			});
		}
	</script>
</head>

<body>
	<fieldset>
		<legend>版本信息</legend>
		<form id="baseForm">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<!-- 
					<th>发布版本号</th>
					<td><label for="textfield"></label> <input id="code"
						class="easyui-textbox" type="text" name="code"
						data-options="required:true">
					</td>
					 -->
					<th>版本描述</th>
					<td><input class="easyui-textbox" type="text" id="desc" name="desc"
						data-options="required:true">
					</td>
					<td><a href="javascript:void(0)" onclick="releaseBatch()" class="easyui-linkbutton" plain="true"
						iconCls="icon-save">发布</a>
					</td>
				</tr>
		</table>
		</form>
	</fieldset>
	<table id="operationList" class="easyui-datagrid" title="工作项发布清单(服务定义)"
		data-options="
				rownumbers:true,
				singleSelect:false,
				url:'/baseLine/operationList',
				method:'get',
				toolbar:toolbar,
				pagination:true,
				pageSize:10"
		style="height:365px; width:auto;">
		<thead>
			<tr>
				<th data-options="field:'productid',checkbox:true"></th>
				<th data-options="field:'service.serviceId'" formatter='service.serviceId'>服务代码
				</th>
				<th data-options="field:'service.serviceName'"
					formatter='service.serviceName'>服务名称</th>
				<th data-options="field:'operationId',align:'right'">服务场景</th>
				<th data-options="field:'operationName',width:80,align:'right'">服务场景名称</th>
				<th data-options="field:'desc',width:80,align:'right'">场景描述</th>
				<th data-options="field:'version.optType'" formatter="version.optType">修订类型</th>
				<th data-options="field:'version.code'" formatter="version.code">版本号</th>
			</tr>
		</thead>
	</table>
	<table id="pcList" class="easyui-datagrid" title="工作项发布清单(公共代码)"
		data-options="
			rownumbers:true,
			singleSelect:false,
			url:'datagrid_data1.json',
			method:'get',
			toolbar:toolbar2,pagination:true,
				pageSize:10"
		style="height:365px; width:auto;">
		<thead>
			<tr>
				<th data-options="field:'productid',checkbox:true"></th>


				<th data-options="field:'itemid'">服务代码</th>
				<th data-options="field:'status'">服务名称</th>
				<th data-options="field:'listprice',align:'right'">服务场景</th>
				<th data-options="field:'unitcost',width:80,align:'right'">服务场景名称
				</th>
				<th data-options="field:'attr1'">消费方</th>
				<th data-options="field:'status',width:60,align:'center'">交易码</th>
				<th data-options="field:'attr1'">交易名称</th>
				<th data-options="field:'attr1'">提供方</th>
				<th data-options="field:'attr1'">修订类型</th>
				<th data-options="field:''"">版本号</th>
			</tr>
		</thead>
	</table>
	
</body>
</html>
<%@ page contentType="text/html; charset=utf-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>列表页</title>
<link rel="stylesheet" type="text/css"
	href="/resources/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/resources/themes/icon.css">
<link href="/resources/css/ui.css" rel="stylesheet" type="text/css">
</head>

<body>
	<fieldset>
		<legend>枚举映射</legend>
		<table border="0" cellspacing="0" cellpadding="0">
			<tr>
				<th>主代码名称</th>
				<td>
					<input class="easyui-text" readonly="true" value="${master.name}" style="width:140px" type="text" name="name" id="masterName"/>
				</td>
				<th>从代码名称</th>
				<td>
					<input class="easyui-text" readonly="true" value="${slave.name}" style="width:140px" type="text" name="name" id="slaveName"/>
				</td>
			</tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<!-- <tr>
				<th>主代码枚举名称</th>
				<td>
					<input class="easyui-combobox" panelHeight="auto" style="width:140px" type="text" name="name" id="masterElementName" editable="false"/>
				</td>
				<th>从代码枚举名称</th>
				<td>
					<input class="easyui-combobox" panelHeight="auto" style="width:140px" type="text" name="name" id="slaveElementName" editable="false"/>
				</td>
			</tr>
			<td><a href="#" id="btn" class="easyui-linkbutton" iconCls="icon-qxfp">设置映射关系</a></td> -->
		</table>
	</fieldset>
	<table title="枚举映射" id="mappingdatagrid" style="height:330px; width:auto;">
	</table>
	<div id="w" class="easyui-window" title=""
		data-options="modal:true,closed:true,iconCls:'icon-add'"
		style="width:500px;height:200px;padding:10px;"></div>

	<script type="text/javascript" src="/resources/js/jquery.min.js"></script>
	<script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="/resources/js/ui.js"></script>
	<script type="text/javascript"
		src="/assets/enumManager/js/enumManager.js"></script>
	<script type="text/javascript">
		var editedRows = [];
		$(function(){
			var url = "/enum/getElementMapping/"+"${master.id}"+"/"+"${slave.id}";
			$('#mappingdatagrid').datagrid({
				//rownumbers:true,
				singleSelect:false,
				collapsible:true,
				url:url,
				method:'get',
				toolbar:toolbar,
				pagination:true,
				pageSize:10,
				columns:[[
			        {field:'productid',checkbox:true},
			        {field:'MASTERNAME',title:'主代码枚举名称'},
					{field:'SLAVENAME',title:'从代码枚举名称',required : true,
						editor:{  
			                type:'combobox',
			                options:{  
			                	url : '/enum/getMasterElements/'+"${slave.id}",
								method : 'get',
								valueField : 'elementId',
								textField : 'elementName',
								panelHeight : 'auto'
			                }
			            }
			        },
					{field:'DIRECTION',title:'映射方向',editor:'text'},
					{field:'MAPPINGRELATION',title:'映射关系',editor:'text'}
			    ]],
			    onDblClickCell: function(index,field,value){
					$(this).datagrid('beginEdit', index);
					var ed = $(this).datagrid('getEditor', {index:index,field:field});
					$(ed.target).focus();
				},
				onBeginEdit : function(index,row){
					editedRows.push(index);
				},
			    onLoadSuccess : function (data){
			    	
			    }
				
			});
			
			
			$('#masterElementName').combobox({
				url : '/enum/getMasterElements/'+"${master.id}",
				method : 'get',
				valueField : 'elementId',
				textField : 'elementName'
			});
			
			$('#slaveElementName').combobox({
				url : '/enum/getMasterElements/'+"${slave.id}",
				method : 'get',
				valueField : 'elementId',
				textField : 'elementName'
			});
		});
		var toolbar = [ {
			text : '删除映射关系',
			iconCls : 'icon-remove',
			handler : function() {
				var selectData = $('#mappingdatagrid').datagrid('getSelections');
				if (selectData.length == 0) {
					alert("请先选择一条记录");
					return;
				}
				if(confirm('确定删除吗 ？')){
					enumManager.deleteElementsMapping(selectData, function(result){
						if(result){
							$('#mappingdatagrid').datagrid('reload');
						}
					});
				}
			}
		},{
			text : '保存映射关系',
			iconCls : 'icon-remove',
			handler : function() {
				for ( var per in editedRows) {
					$("#mappingdatagrid").datagrid('endEdit', editedRows[per]); 
				}
				var editData = $("#mappingdatagrid").datagrid('getChanges');
				enumManager.saveElementMapping(editData,function(result){
					if(result){
						$('#mappingdatagrid').datagrid('reload');
					}
				});
				editedRows = [];
			}
		} ];
	</script>
</body>
</html>
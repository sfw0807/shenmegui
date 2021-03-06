
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<table id="olaTemplateTable" style="height:auto; width:auto;"
	title="">
	<thead>
		<tr>
			<th data-options="field:'productid',checkbox:true"></th>
			<th field="olaTemplateId" width="100" editor="text" id="idText"
				data-options="hidden:true">ID</th>
			<th field="templateName" width="100" editor="text" align="center">OLA模板名称</th>
			<th field="desc" width="150" align="center" editor="text">描 述</th>
		</tr>
	</thead>
</table>
<div id="w" class="easyui-window" title=""
	data-options="modal:true,closed:true,iconCls:'icon-add'"
	style="width:500px;height:200px;padding:10px;"></div>
<script type="text/javascript" src="/assets/olaTemplate/js/olaTemplateManager.js"></script>
<script type="text/javascript" src="/assets/ola/js/olaManager.js"></script>
<script type="text/javascript">
	var olaTemplatetoolbar = [
			{
				text : '新增',
				iconCls : 'icon-add',
				handler : function() {
					$('#olaTemplateTable').edatagrid('addRow');
					}
			},{
				text : '编辑',
				iconCls : 'icon-edit',
				handler : function() {
				
				var row = $('#olaTemplateTable').edatagrid("getSelected");
					var content = '<iframe scrolling="auto" frameborder="0"  src="/jsp/service/ola/olaTemplate.jsp?olaTemplateId='+row.olaTemplateId+'&templateName='+row.templateName+'&serviceId='+serviceId+'&operationId='+operationId+'" style="width:100%;height:100%;"></iframe>';
// 					alert(content);
					parent.parent.parent.addTab('OLA模板', content);
					}
			},
			{
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
					var row = $('#olaTemplateTable').edatagrid('getSelected');
					var rowIndex = $('#olaTemplateTable').datagrid(
							'getRowIndex', row);
					$('#olaTemplateTable').edatagrid('deleteRow', rowIndex);
				}
			},{
			text : ' 保存',
			iconCls : 'icon-save',
			handler : function() {
			for ( var per in editedRows) {
				$("#olaTemplateTable").datagrid('endEdit', editedRows[per]);
			}
			var editData = $("#olaTemplateTable").datagrid('getChanges');
			var deleteData = $("#olaTemplateTable").datagrid('getChanges','deleted');	
					
				olaTemplateManager.add(editData,function(result){
					if(result){
						$('#olaTemplateTable').datagrid('reload');
					}
				});
				console.log(deleteData);
				if(deleteData.length > 0){
					olaTemplateManager.deleteByEntity(deleteData,function(result){
						if(result){
							$('#olaTemplateTable').datagrid('reload');
						}
					})
				}
				editedRows = [];

			}
		}, {
				text : '选择',
				iconCls : 'icon-qxfp',
				handler : function() {
					var serviceId = "${param.serviceId}";
					var operationId = "${param.operationId}";
					var info = $('#olaTemplateTable').edatagrid('getSelected');
					if(info.olaTemplateId){
					olaManager.getByParams(info.olaTemplateId,function(result){
						$('#ola').edatagrid('loadData',result);
						olaManager.deleteByAll(serviceId,operationId, function(result1){
						if(result1){
						olaManager.addList(result,function(result2){
							if(result2){alert("导入成功");}else{alert("导入失败");}
						   });
						  }
					   });
					});
					$('#w').window('close');
					}
				}
			} ];
			
	var editedRows = [];
	$(function() {

		$('#olaTemplateTable').edatagrid({
			rownumbers:true,
				singleSelect:true,
				url:"/olaTemplate/getAll/",
				method:'get',
				toolbar:olaTemplatetoolbar,
				onBeginEdit : function(index,row){
					editedRows.push(index);
				}
		});
	});
</script>
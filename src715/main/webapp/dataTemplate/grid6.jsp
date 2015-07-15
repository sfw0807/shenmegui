<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>列表页</title>
<link rel="stylesheet" type="text/css" href="/resources/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/resources/themes/icon.css">
<link href="/resources/css/ui.css" rel="stylesheet" type="text/css">
</head>

<body>
<fieldset>
 <legend>条件过滤</legend>
 <table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th>类别词中文名称</th>
	    <td>
	    	<input class="easyui-textbox" id="chineseWord"/>
	    </td>
    <th>类别词英文名称</th>
    	<td>
    		<input class="easyui-textbox" type="text" id="englishWord" >
    	</td>
  </tr>
  <tr>
	<th>类别词英文缩写</th>
	    <td>
	    	<input class="easyui-textbox" id="esglisgAb"/>
	    </td>
	<th>备注</th>
	    <td>
	    	<input class="easyui-textbox" id="remark"/>
	    </td>
	    <td>
	   		<button href="#" onclick="search()" class="easyui-linkbutton" plain="true" iconCls="icon-search">过滤</button>
	    </td>
  </tr>
</table>

</fieldset><table id="tt" style="height:370px; width:auto;"
			title="所有类别词">
		<thead>
			<tr>
				<th field="chineseWord" width="100" editor="{type:'validatebox',options:{required:true}}">类别词中文名称</th>
				<th field="englishWord" width="100" editor="text">类别词英文名称</th>
				<th field="esglisgAb" width="100" align="right" editor="{type:'text',options:{}}">类别词英文缩写</th>
				<!-- <th field="esglisgab" width="100" align="right" editor="{type:'numberbox',options:{precision:1}}">类别词英文缩写</th> -->
				<th field="remark" width="100" align="right" editor="text">备注</th>
				<th field="optUser" width="150" editor="text">修订人</th>
				<th field="optDate" width="150" editor="text">修订时间</th>
			</tr>
		</thead>
	</table>
<div id="w" class="easyui-window" title="" data-options="modal:true,closed:true,iconCls:'icon-add'" style="width:500px;height:200px;padding:10px;">
		
</div>
<script type="text/javascript" src="/resources/js/jquery.min.js"></script> 
<script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/resources/js/jquery.edatagrid.js"></script>
<script type="text/javascript" src="/resources/js/ui.js"></script>
<script type="text/javascript" src="/assets/categoryWord/js/categoryWordManager.js"> </script>
<script type="text/javascript">
		var toolbar = [{
			text:'新增',
			iconCls:'icon-add',
			handler:function(){
				$('#tt').edatagrid('addRow');
			}
		},
		{
			text:'删除',
			iconCls:'icon-remove',
			handler:function(){
				var row = $('#tt').edatagrid('getSelected');
				var rowIndex = $('#tt').edatagrid('getRowIndex', row);
				$('#tt').edatagrid('deleteRow', rowIndex);
			}
		},
		{
			text:'保存',
			iconCls:'icon-save',
			handler:function(){
				for ( var per in editedRows) {
					$("#tt").datagrid('endEdit', editedRows[per]);
				}

				var editData = $("#tt").datagrid('getChanges');
				console.log(editData);
				categoryWordManager.saveCategoryWord(editData,function(result){
					if(result){
						$('#tt').datagrid('reload');
					}
				});
				var deleteData = $("#tt").datagrid('getChanges','deleted');
				if(deleteData.length > 0){
					categoryWordManager.deleteCategoryWord(deleteData,function(result){
						if(result){
							$('#tt').datagrid('reload');
						}
					})
				}
				editedRows = [];


				/*//新增
				var insetDatas = $('#tt').edatagrid('getChanges','inserted');
				console.log(insetDatas);
				for(var i = 0; i < insetDatas.length; i++){
					var changeData = insetDatas[i];
					if(changeData.isNewRecord){
						var data = {};
						data.chineseWord = changeData.chineseWord;
						data.englishWord = changeData.englishWord;
						data.esglisgAb = changeData.esglisgAb;
						data.remark = changeData.remark;
						data.potUser = changeData.potUser;
						data.potDate = changeData.potDate;
						categoryWordManager.add(data,function(result){
							if(result){
								alert("保存成功");
							}else{
								alert("保存失败");
							}
						});
					}
				}
				//删除
				var deleteDatas = $('#tt').edatagrid('getChanges','deleted');
				for(var i = 0; i < deleteDatas.length; i++){
					var deleteData = deleteDatas[i];
					categoryWordManager.deleteRow(deleteData.id,function(result){
						if(result){
							alert("保存成功");
						}else{
							alert("保存失败");
						}
					});
				}
				//修改
				var updateDatas = $('#tt').edatagrid('getChanges','updated');
				for(var i = 0; i < updateDatas.length; i++){
					var updateData = updateDatas[i];
					categoryWordManager.modify(updateData,function(result){
						if(result){
							alert("保存成功");
						}else{
							alert("保存失败");
						}
					});
				}*/
			}
		}];
		var editedRows = [];
		$(function(){
			$('#tt').edatagrid({
				rownumbers:true,
				singleSelect:true,
				url:'/categoryWord/getAll',
				method:'get',
				toolbar:toolbar,
				pagination:true,
				pageSize:10,
				onBeginEdit : function(index,row){
					editedRows.push(index);
				}
			});
		});
		function search(){
			var  param = {};
			param.englishWord = $('#englishWord').val() ? $('#englishWord').val() : "isNull";
			param.chineseWord = $('#chineseWord').val() ? $('#chineseWord').val() : "isNull";
			param.esglisgAb = $('#esglisgAb').val() ? $('#esglisgAb').val() : "isNull";
			param.remark = $('#remark').val() ? $('#remark').val() : "isNull";
			categoryWordManager.getByParams(param,function(result){
				$('#tt').edatagrid('loadData',result);
			});
		}
	</script> 

</body>
</html>
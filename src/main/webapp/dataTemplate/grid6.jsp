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
    <th>关键字</th>
    <td>
      <input class="easyui-textbox" type="text" name="name" ></td>
   
    <td><a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search">过滤</a></td>

  </tr>
</table>

</fieldset><table id="tt" style="height:370px; width:auto;"
			title="所有类别词"
			
			 data-options="rownumbers:true,singleSelect:true,url:'datagrid_data1.json',method:'get',toolbar:toolbar,pagination:true,
				pageSize:10">
		<thead>
			<tr>
				<th field="itemid" width="100" editor="{type:'validatebox',options:{required:true}}">Item ID</th>
				<th field="productid" width="100" editor="text">Product ID</th>
				<th field="listprice" width="100" align="right" editor="{type:'numberbox',options:{precision:1}}">List Price</th>
				<th field="unitcost" width="100" align="right" editor="numberbox">Unit Cost</th>
				<th field="attr1" width="150" editor="text">Attribute</th>
			</tr>
		</thead>
	</table>
<div id="w" class="easyui-window" title="" data-options="modal:true,closed:true,iconCls:'icon-add'" style="width:500px;height:200px;padding:10px;">
		
</div>
<script type="text/javascript" src="/resources/js/jquery.min.js"></script> 
<script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/resources/js/jquery.edatagrid.js"></script>

<script type="text/javascript" src="/resources/js/ui.js"></script>
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
				$('#tt').edatagrid('destroyRow')
			}
		}];
		$(function(){
			$('#tt').edatagrid({
				autoSave:true,
				saveUrl: '/',
				updateUrl: '/',
				destroyUrl: '/'
			});
		});
	</script> 

</body>
</html>
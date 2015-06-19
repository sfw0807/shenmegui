<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>列表页</title>
<link rel="stylesheet" type="text/css" href="/resources/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/resources/themes/icon.css">
<link href="/resources/css/css.css" rel="stylesheet" type="text/css">
</head>

<body>
<fieldset>
 <legend>条件搜索</legend>
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
      <th>元数据名称</th>
    <td> <input class="easyui-textbox" type="text" name="name" ></td>
   
   <th>中文名称</th>
    <td> <input class="easyui-textbox" type="text" name="name" >
      </td>
   <th>英文名称</th>
    <td> <input class="easyui-textbox" type="text" name="name" ></td>
  </tr>
  <tr>
     <th>类别词</th>
    <td><select class="easyui-combobox" panelHeight="auto" style="width:155px">
				<option value="java">节点1</option>
				<option value="c">节点2</option>
				<option value="basic">节点3</option>
				<option value="perl">节点4</option>
			</select></td>
    <th>基线版本号</th>
    <td> <input class="easyui-textbox" type="text" name="name" ></td>
     <th> 创建人</th>
    <td> <input class="easyui-textbox" type="text" name="name" ></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td align="right"><a href="#" class="easyui-linkbutton"  iconCls="icon-search">搜索</a></td>
  </tr>
</table>


</fieldset>
<table class="easyui-datagrid" title="元数据管理" 
			data-options="rownumbers:true,singleSelect:false,url:'datagrid_data1.json',method:'get',toolbar:toolbar,pagination:true,
				pageSize:10" style="height:370px; width:auto;">
  <thead>
     <tr>
       <th > </th>

    
      <th colspan="2">说明</th>
      <th colspan="3"  >综合</th>
      <th >字段5 </th>
      <th colspan="2" > 示例</th>
      <th >字段8 </th>
      <th >字段9 </th>
    </tr>
    <tr>
          <th data-options="field:'productid',checkbox:true"> </th>

    
      <th data-options="field:'itemid'">字段1 </th>
      <th data-options="field:'status'">字段2 </th>
      <th data-options="field:'listprice',align:'right'">字段3 </th>
      <th data-options="field:'unitcost',align:'right'">字段4 </th>
      <th data-options="field:'attr1'">消费方 </th>
      <th data-options="field:'status',align:'center'">字段5 </th>
      <th data-options="field:'attr1'"> 字段6 </th>
      <th data-options="field:'attr1'"> 字段7 </th>
      <th data-options="field:'attr1'">字段8 </th>
      <th data-options="field:'attr1'">字段9 </th>
    </tr>
  </thead>
</table>
<div id="w" class="easyui-window" title="" data-options="modal:true,closed:true,iconCls:'icon-add'" style="width:500px;height:200px;padding:10px;">
		
</div>
<script type="text/javascript">
		var toolbar = [{
			text:'新增',
			iconCls:'icon-add',
			handler:function(){
			    
				uiinit.win({
					w:500,
					iconCls:'icon-add',
					title:"新增元数据",
					url : "/dataTemplate/formTemplate/form.html"
				});	
			}
		},{
			text:'修改',
			iconCls:'icon-edit',
			handler:function(){
				uiinit.win({
					w:500,
					iconCls:'icon-edit',
					title:"修改元数据",
					url : "/dataTemplate/formTemplate/form.html"
				});
				}
		},{
			text:'删除',
			iconCls:'icon-remove',
			handler:function(){}
		},'-',
		{
			text:'关联服务场景',
			iconCls:'icon-cfp',
			handler:function(){
				uiinit.win({
					w:500,
					iconCls:'icon-cfp',
					title:"关联服务场景",
					url : "/dataTemplate/words/tree.html"
				});
			}
		},
		{
			text:'公共代码',
			iconCls:'icon-cfp',
			handler:function(){alert('移出')}
		},'-',
		{
			text:'导入',
			iconCls:'icon-cfp',
			handler:function(){alert('导入')}
		},
		{
			text:'导出',
			iconCls:'icon-save',
			handler:function(){alert('导出')}
		},
		{
			text:'检出',
			iconCls:'icon-qxfp',
			handler:function(){
				
			}
		},
		{
			text:'提交任务',
			iconCls:'icon-qxfp',
			handler:function(){
				
			}
		}];
	</script> 
<script type="text/javascript" src="/resources/js/jquery.min.js"></script> 
<script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/resources/js/ui.js"></script>
<script type="text/javascript">
	$(function(){
		$(".datagrid-cell-group").width("auto")
		})
</script>
</body>
</html>
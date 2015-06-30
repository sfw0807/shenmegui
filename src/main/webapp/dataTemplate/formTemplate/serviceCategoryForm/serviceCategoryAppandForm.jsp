
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript">
	function toSave(){
		var serviceCategory = {};
		serviceCategory.categoryName = $('#categoryName').val();
		serviceCategory.desc = $('#discription').val();
		serviceCategory.parentId = $('#parentId').val();
		serviceCategory.remark = $('#remark').val();
		addServiceCategory(serviceCategory,function(result){
			$('#w').window('close');
			$('.mxservicetree').tree('reload');
		});
	}
	$(function(){
		var node = $('.mxservicetree').tree('getSelected');
		$('#parentId').val(node.serviceCategory.parentId);
	});
</script>
<form class="formui">
<table border="0" cellspacing="0" cellpadding="0">

  <tr>
    <th>服务分类名</th>
    <td><input class="easyui-textbox" type="text" id="categoryName" ></td>
  </tr>
  <tr>
    <th>描述</th>
    <td><input class="easyui-textbox" type="text" id="discription" ></td>
  </tr>
  <tr>
    <th>服务类父类</th>
    <td><input class="easyui-textbox" type="text" id="parentId" ></td>
  </tr>
  <tr>
    <th>remark</th>
    <td><input class="easyui-textbox" type="text" id="remark" ></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td class="win-bbar"><a href="#" class="easyui-linkbutton"  iconCls="icon-cancel" onClick="$('#w').window('close')">取消</a><a href="#" class="easyui-linkbutton" onClick="toSave()" iconCls="icon-save">保存</a></td>
  </tr>
</table>
</form>
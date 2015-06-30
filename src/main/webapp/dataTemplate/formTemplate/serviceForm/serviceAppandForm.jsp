
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript">
	function toSave(){
		var service = {};
		service.serviceName = $('#serviceName').val();
		service.desc = $('#discription').val();
		var node = $('.mxservicetree').tree('getSelected');
		service.categoryId = $('#serviceCategory').val();
		service.version = $('#version').val();
		service.state = $('#state').val();
		addService(service,function(result){
			if(result){
				alert("新增成功");
			}
		});
	}
</script>
<form class="formui">
<table border="0" cellspacing="0" cellpadding="0">

  <tr>
    <th>服务名</th>
    <td><input class="easyui-textbox" type="text" id="serviceName" ></td>
  </tr>
  <tr>
    <th>描述</th>
    <td><input class="easyui-textbox" type="text" id="discription" ></td>
  </tr>
  <tr>
    <th>服务分类</th>
    <td><input class="easyui-textbox" type="text" id="serviceCategory" ></td>
  </tr>
<tr>
    <th>version</th>
    <td><input class="easyui-textbox" type="text" id="version" ></td>
  </tr>
  <tr>
    <th>state</th>
    <td><input class="easyui-textbox" type="text" id="state" ></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td class="win-bbar"><a href="#" class="easyui-linkbutton"  iconCls="icon-cancel" onClick="$('#w').window('close')">取消</a><a href="#" class="easyui-linkbutton" onClick="toSave()" iconCls="icon-save">保存</a></td>
  </tr>
</table>
</form>
<%@ page contentType="text/html; charset=utf-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<form class="formui">
	<table border="0" cellspacing="0" cellpadding="0">

		<tr>
			<th>
				交易码
			</th>
			<td>
				<input class="easyui-textbox" type="text" id="ecode">
			</td>
		</tr>
		<tr>
			<th>
				交易名称
			</th>
			<td>
				<input class="easyui-textbox" type="text" id="interfaceName">
			</td>
		</tr>
		<tr>
			<th>
				接口功能描述
			</th>
			<td>
				<input class="easyui-textbox" type="text" id="desc">
			</td>
		</tr>
		<tr>
			<th>
				备注说明
			</th>
			<td>
				<input class="easyui-textbox" type="text" id="remark">
			</td>
		</tr>


		<tr>
			<td>
				&nbsp;
			</td>
			<td class="win-bbar">
				<a href="#" class="easyui-linkbutton" iconCls="icon-cancel"
					onClick="$('#w').window('close')">取消</a><a href="#"
					class="easyui-linkbutton" onclick="save()" iconCls="icon-save">保存</a>
			</td>
		</tr>
	</table>
</form>

<script type="text/javascript">

	function save(){
		var ecode = $("#ecode").val();
		var interfaceName = $("#interfaceName").val();
		var desc = $("#desc").val();
		var remark = $("#remark").val();
		var node = $('.msinterfacetree').tree("getSelected");
		
		var systemId = node.id;
		var data = {};
		data.ecode = ecode;
		data.interfaceName = interfaceName;
		data.desc = desc;
		data.remark = remark;
		
		var serviceInvoke = {};
		serviceInvoke.systemId = systemId;
		data.serviceInvoke = serviceInvoke;
		
	
		interfaceManager.add(data,function(result){
			if(result){
				$('#w').window('close');
				$('.msinterfacetree').tree("reload");
			}else{
				alert("保存失败");
			}
        });
	}

</script>


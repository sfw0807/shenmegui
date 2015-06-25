<%@ page contentType="text/html; charset=utf-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<form class="formui">
	<table border="0" cellspacing="0" cellpadding="0">
		<tr>
			<th>
				报文头名称
			</th>
			<td>
				<input class="easyui-textbox" type="text" id="headName" value="${head.headName}">
			</td>
		</tr>
		<tr>
			<th>
				报文头备注
			</th>
			<td>
				<input class="easyui-textbox" type="text" id="headRemark" value="${head.headRemark}">
			</td>
		</tr>
		<tr>
			<th>
				报文头描述
			</th>
			<td>
				<input class="easyui-textbox" type="text" id="headDesc" value="${head.headDesc}">
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
		 <input type="hidden" id="headId" value="${head.headId}">
</form>

<script type="text/javascript">

	function save(){
		var headId = $("#headId").val();
		var headName = $("#headName").val();
		var headRemark = $("#headRemark").val();
		var headDesc = $("#headDesc").val();
		
		var data = {};
		data.headId = headId;
		data.headName = headName;
		data.headRemark = headRemark;
		data.headDesc = headDesc;
	
		sysManager.add(data,function(result){
			if(result){
				$('#w').window('close');
				$('.mxsysadmintree').tree("reload");
			}else{
				alert("保存失败");
			}
        });
	}

</script>



<%@ page contentType="text/html; charset=utf-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<form class="formui">
	<table border="0" cellspacing="0" cellpadding="0">

		<tr>
			<th>
				系統ID
			</th>
			<td>
				<input class="easyui-textbox" type="text" id="systemIdText">
			</td>
		</tr>
		<tr>
			<th>
				系統简称
			</th>
			<td>
				<input class="easyui-textbox" type="text" id="systemAbText">
			</td>
		</tr>
		<tr>
			<th>
				系統名称
			</th>
			<td>
				<input class="easyui-textbox" type="text" id="systemChineseNameText">
			</td>
		</tr>
		<!--
		<tr>
			<th>
				系統协议
			</th>
			<td>
				<select class="easyui-combobox" id="protocolIdText" style="width:155px" panelHeight="auto" data-options="editable:false">
				</select>
			</td>
		</tr>
		-->
		<tr>
			<th>
				联系人一
			</th>
			<td>
				<input class="easyui-textbox" type="text" id="principal1Text">
			</td>
		</tr>
		<tr>
			<th>
				联系人二
			</th>
			<td>
				<input class="easyui-textbox" type="text" id="principal2Text">
			</td>
		</tr>

        <tr>
			<th>
				工作范围
			</th>
			<td>
				<input class="easyui-textbox" type="text" id="workRangeText">
			</td>
		</tr>
		<tr>
			<th>
				备注说明
			</th>
			<td>
				<input class="easyui-textbox" type="text" id="featureDescText">
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
	$(document).ready(function (){

		    $('#protocolIdText').combobox({
                url:'/system/getProtocolAll',
                method:'get',
                mode:'remote',
                valueField:'id',
                textField:'text'
            });


	});


	function save(){

        var systemId = $("#systemIdText").val();
        if(systemId==null || systemId == ''){
			alert("请填写系统ID");
			return;
		}
		var systemAb = $("#systemAbText").val();
		var systemChineseName = $("#systemChineseNameText").val();
		/*var protocolId = $("#protocolIdText").combobox('getValue');
		if(protocolId==null || protocolId == ''){
			alert("请选择协议类型");
			return;
		}
		*/
		var principal1 = $("#principal1Text").val();
		var principal2 = $("#principal2Text").val();
		var workRange = $("#workRangeText").val();
		var featureDesc = $("#featureDescText").val();
		var data = {};

		data.systemId = systemId;
		data.systemAb = systemAb;
		data.systemChineseName = systemChineseName;
		data.principal1 = principal1;
		data.principal2 = principal2;
		data.workRange = workRange;
		data.featureDesc = featureDesc;

		//var protocolData = {};

		//protocolData.protocolId = protocolId;
		//protocolData.systemId = systemId;
		//data.systemProtocol = protocolData;

		sysManager.addSystem(data,function(result){
			if(result){
				$('#w').window('close');
				parent.parent.$('.msinterfacetree').tree("reload");
				$('#tg').datagrid("reload");
			}else{
				alert("保存失败");
			}
		});


	}

</script>



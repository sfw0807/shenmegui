<%@ page contentType="text/html; charset=utf-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<form class="formui">
	<table border="0" cellspacing="0" cellpadding="0">

		<tr>
			<th>
				协议名称
			</th>
			<td>
				<input class="easyui-textbox" type="text" id="protocolNameText" value="${protocol.protocolName}">
			</td>
		</tr>
		<tr>
			<th>
				消息类型
			</th>
			<td>
				<input class="easyui-textbox" type="text" id="msgTypeText" value="${protocol.msgType}">
			</td>
		</tr>
		<tr>
			<th>
				协议编码
			</th>
			<td>
				<input class="easyui-textbox" type="text" id="encodingText" value="${protocol.encoding}">
			</td>
		</tr>
		<tr>
			<th>
				超时时间
			</th>
			<td>
				<input class="easyui-textbox" type="text" id="timeoutText" value="${protocol.timeout}">
			</td>
		</tr>
		<tr>
			<th>
				错误代码
			</th>
			<td>
				<input class="easyui-textbox" type="text" id="errorCodeText" value="${protocol.errorCode}">
			</td>
		</tr>

        <tr>
			<th>
				成功代码
			</th>
			<td>
				<input class="easyui-textbox" type="text" id="succCodeText" value="${protocol.succCode}">
			</td>
		</tr>
		<tr>
			<th>
				备注说明
			</th>
			<td>
				<input class="easyui-textbox" type="text" id="remarkText" value="${protocol.remark}">
			</td>
		</tr>
		<tr>
			<th>
				消息模板
			</th>
			<td>
				<input class="easyui-textbox" type="text" id="templateContent" value="${protocol.msgTemplate.templateContent}">
			</td>
		</tr>
		<tr>
			<th>
				生成类
			</th>
			<td>
				<input class="easyui-textbox" type="text" id="generatorIdText" value="${protocol.generatorId}">
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
	<input  type="hidden" id="protocolIdText" value="${protocol.protocolId}" readOnly>
	<input  type="hidden" id="msgTemplateIdText" value="${protocol.msgTemplateId}" readOnly>
</form>

<script type="text/javascript">

	function save(){

		var protocolId = $("#protocolIdText").val();
        var protocolName = $("#protocolNameText").val();
		var msgType = $("#msgTypeText").val();
		var encoding = $("#encodingText").val();
		var timeout = $("#timeoutText").val();
		var errorCode = $("#errorCodeText").val();
		var remark = $("#remarkText").val();
		var succCode = $("#succCodeText").val();
		var templateContent = $("#templateContent").val();
		var msgTemplateId = $("#msgTemplateIdText").val();
		var generatorId = $("#generatorIdText").val();
		var data = {};

		data.protocolId = protocolId;
		data.protocolName = protocolName;
		data.msgType = msgType;
		data.encoding = encoding;
		data.timeout = timeout;
		data.errorCode = errorCode;
		data.remark = remark;
		data.succCode = succCode;
		data.msgTemplateId = msgTemplateId;
		data.generatorId = generatorId;

		var msgTemplate = {};

		msgTemplate.templateContent = templateContent;
		msgTemplate.templateId = msgTemplateId
		data.msgTemplate = msgTemplate;
		sysManager.addProtocol(data,function(result){
			if(result){
				$('#w').window('close');
				$('#tg').datagrid("reload");
			}else{
				alert("保存失败");
			}
		});


	}

</script>



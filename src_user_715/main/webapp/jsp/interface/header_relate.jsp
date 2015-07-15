<%@ page contentType="text/html; charset=utf-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<form class="formui">
	<table border="0" cellspacing="0" cellpadding="0">
		<tr>
			<th>
				接口Id
			</th>
			<td>
				<input class="easyui-textbox" type="text" value="${param.interfaceId}" readOnly>
			</td>
		</tr>
		<tr  style="height:50px">
			<th>
				报文头
			</th>
			<td>
				<select class="easyui-combobox" id="headerRelate" style="width:155px" panelHeight="auto" data-options="editable:false,multiple:true">
				</select>
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

		    $('#headerRelate').combobox({
                url:'/interface/getHeadAll',
				method:'get',
				mode:'remote',
				valueField:'id',
				textField:'text',
                onLoadSuccess:function(){
                	 $.ajax({
							type: "GET",
							contentType: "application/json; charset=utf-8",
							url: "/interface/getChecked/${param.interfaceId}",
							dataType: "json",
							success: function(result) {
								 $('#headerRelate').combobox("setValues",result);
							}
						});

				}
            });
	});


	function save(){

        var systemId = "${param.interfaceId}";
        var headId = $("#headerRelate").combobox('getValues');

		$.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: "/interface/headRelate/${param.interfaceId}/"+headId,
			dataType: "json",
			success: function(result) {
				 //alert(result);
				 if(true){
				 	alert("关联成功");
				 	$('#w').window('close');
				 	$('#tg').datagrid("reload");
				 }else{

				 }
				 //$('#protocolIdRelate').combobox("setValues",result);
			}
		});


	}

</script>



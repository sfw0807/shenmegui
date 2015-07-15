<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>列表页</title>
<link rel="stylesheet" type="text/css" href="/resources/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/resources/themes/icon.css">
<link href="/resources/css/ui.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/resources/js/jquery.min.js"></script> 
<script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/resources/js/ui.js"></script>
<script type="text/javascript" src="/resources/js/easyui-lang-zh_CN.js"></script>

<script type="text/javascript" src="/jsp/service/operation/operation.js"></script>
<script type="text/javascript">
	var invokes = new Array();
    
    $(document).ready(function(){
    	$.extend($.fn.validatebox.defaults.rules,{  
        unique: {  
            validator : function(value, param){ 
            var result;
             $.ajax({
		         type: "get",
		         async: false,
		         url: "/operation/uniqueValid",
		         dataType: "json",
		         data: {"operationId":value, "serviceId": "${service.serviceId }"},
		         success: function(data){
		         	result = data;
		            }
			 	});
			 return result;
            },  
            message: '已存在相同场景编号'  
        }  
    });
    	loadSystem("systemList1", ${systemList}, "systemId", "systemChineseName");
    	loadSystem("systemList2", ${systemList}, "systemId", "systemChineseName");
	});
   
   function selectInterface2(listId, type){
	var checkedItems = $('#intefaceList').datagrid('getChecked');
    if (checkedItems != null && checkedItems.length > 0) {
        $.each(checkedItems, function (index, item) {
        	console.log(item);
            var exsit = $("#" + listId + " option[value='__invoke__" + item.invokeId + "']");
            if (exsit.length > 0) {
                alert("接口已经被选中！");
            } else {
                $("#" + listId).append("<option value='__invoke__" + item.invokeId + "'>" + item.system.systemChineseName + "::" + item.inter.interfaceName + "</option>");
            	addInvoke(item, type);
            }

        });
    }
    $('#opDlg').dialog('close');
}

function selectex2(oldDataUi, newDataUi, type) {
    var oldData = ""

    $('#' + oldDataUi + ' option:selected').each(function () {
        var value = $(this).val();
        $("#" + oldDataUi + " option[value=" + value + "]").remove();
        
        addSystem(value, type);
//			$("#"+newDataUi).append("<option value='"+$(this).val()+"'>"+this.text+"</option>");
    });
}

//从系统选择消费者接口
function chooseInterface2(oldListId, newListId, type) {
    $('#' + oldListId + ' option:selected').each(function () {
        var value = $(this).val();
        var text = this.text;
        $.ajax({
            type: "get",
            async: false,
            contentType: "application/json; charset=utf-8",
            url: "/operation/judgeInterface",
            dataType: "json",
            data: {"systemId": value},
            success: function (data) {
                //如果系统没有接口，直接转移
                if (!data) {
                    var exsit = $("#" + newListId + " option[value='" + value + "']");
                    console.log(length);
                    if (exsit.length > 0) {
                        alert("应经被选中！");
                    } else {
                        $("#" + newListId).append("<option value='" + value + "'>" + text + "</option>");
                        addSystem(value, type)
                    }

                } else {
                    //如果有接口弹出接口选择页面
                    $('#opDlg').dialog({
                        title: '接口选择',
                        width: 700,
                        closed: false,
                        cache: false,
                        href: '/jsp/service/operation/interfaceList.jsp?systemId=' + value + "&newListId=" + newListId+"&type=?"+type,
                        modal: true
                    });
                }

            }
        });
    });

}

function addInvoke(item, type){
	var serviceId = $("#serviceId").attr("value");
  	var operationId = $("#operationId").textbox("getValue");
  	console.log(serviceId);
	var serviceInvoke ={
			"invokeId":item.invokeId,
			"systemId":item.systemId,
			"isStandard":item.isStandard, 
			"serviceId":serviceId, 
			"operationId":operationId,
			"interfaceId":item.interfaceId, 
			"type":type, 
			"desc":item.desc ,
			"remark":item.remark ,
			"protocolId":item.protocolId,
	}
	invokes.push(serviceInvoke);
}
function addSystem(systemId, type){
  	var serviceId = $("#serviceId").attr("value");
  	var operationId = $("#operationId").textbox("getValue");
	var serviceInvoke ={
			"systemId":item.systemId,
			"serviceId":serviceId, 
			"operationId":operationId,
			"type":type, 
	}
	invokes.push(serviceInvoke);
}
function selectInterface2(listId, type) {
    var checkedItems = $('#intefaceList').datagrid('getChecked');
    if (checkedItems != null && checkedItems.length > 0) {
        $.each(checkedItems, function (index, item) {
        	console.log(item);
            var exsit = $("#" + listId + " option[value='__invoke__" + item.invokeId + "']");
            if (exsit.length > 0) {
                alert("该接口已经被选中！");
            } else {
                $("#" + listId).append("<option value='__invoke__" + item.invokeId + "'>" + item.system.systemChineseName + "::" + item.inter.interfaceName + "</option>");
            	addInvoke(item, type)
            }

        });
    }
    $('#opDlg').dialog('close');
}

function save2(formId, operation) {
    if (!$("#" + formId).form('validate')) {
        return false;
    }
    var params = $("#" + formId).serialize();
    params = decodeURIComponent(params, true);

    var urlPath;
    if (operation == 0) {
        urlPath = "/operation/add";
    }
    if (operation == 1) {
        urlPath = "/operation/edit";
    }
    $.ajax({
        type: "post",
        async: false,
        url: urlPath,
        dataType: "json",
        data: params,
        success: function (data) {
            if (data == true) {
				afterAdd();
            } else {
                alert("保存出现异常 ，操作失败！");
            }

        }
    });
}

function afterAdd(){
	console.log(invokes);
	 		$.ajax({
                    type: "post",
                    async: false,
                    contentType: "application/json; charset=utf-8",
                    url: "/serviceLink/saveBatch",
                    dataType: "json",
                    data: JSON.stringify(invokes),
                    success: function (data) {
                        alert("操作成功 ！");
                        //清空页面数据
                        clean();
                        //刷新查询列表
                        parent.serviceInfo.reloadData();
                    }
                });
}
</script>

</head>

<body >
<form class="formui" id="operationForm">
 <div  class="easyui-panel" title="基本信息" style="width:100%;height:auto;padding:10px;">
 <input id="serviceId" name="serviceId" type="hidden"  value="${service.serviceId }" />
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
      <th>服务代码</th>
    <td> <input class="easyui-textbox" disabled="disabled"  type="text" value="${service.serviceId }" /></td>
   
  <th>服务名称</th>
    <td> <input class="easyui-textbox" disabled="disabled"  type="text" name="serviceName" value="${service.serviceName }" />
    </td>
  </tr>
  <tr>
     <th>场景号</th>
    <td><input id="operationId" name="operationId" class="easyui-textbox"  type="text" data-options="required:true, validType:'unique'" /></td>
    <th>场景名称</th>
    <td><input id="operationName" name="operationName" class="easyui-textbox"  type="text" /></td>
    </tr>
  <tr>
     <th>功能描述</th>
    <td colspan="3"><input id="operationDesc" name="operationDesc"  class="easyui-textbox"  style="width:100%" type="text" /></td>
    </tr>
  <tr>
      <th>场景关键词</th>
    <td> <input class="easyui-textbox" disabled="disabled"  type="text" name="" /></td>
   
  <th>状态</th>
    <td>未审核
    	<input type="hidden" value="0" name="state" />
    </td>
  </tr>
   <tr>
      <th>使用范围</th>
    <td> <input class="easyui-textbox"  disabled="disabled" type="text" name="" /></td>
   
  <th>备注</th>
    <td> <input id="operationRemark" name="operationRemark" class="easyui-textbox"  type="text" />
    </td>
  </tr>
</table>


</div>
  <div style="margin-top:10px;"></div>

<div id="p" class="easyui-panel" title="服务消费方应用管理" style="width:100%;height:auto;padding:10px;">
<table border="0" cellspacing="0" cellpadding="0" style="width:auto;">
  <tr>
      <th align="center">已经应用</th>
    <td width="50" align="center">&nbsp;</td>
    <th align="center">应用系统列表</th>
  </tr>
  
  <tr>
    <th align="center"><select name="select2" id="consumer" size="10" multiple  style="width:155px;height:160px" panelHeight="auto">
    </select></th>
    <td align="center" valign="middle"><a href="#" class="easyui-linkbutton"  iconCls="icon-select-add" onClick="selectex('consumer','systemList1')"></a><br>
<br>
<br>
<a href="#" class="easyui-linkbutton"  iconCls="icon-select-remove" onClick="chooseInterface('systemList1','consumer')"></a></td>
   <td align="center">
    <select name="select" id="systemList1" size="10" multiple  style="width:155px;height:160px" panelHeight="auto"/>
    </select>
    </td>
  </tr>
  </table>
  </div>
  <div style="margin-top:10px;"></div>
 <div  class="easyui-panel" title="服务提供方应用管理" style="width:100%;height:auto;padding:10px;">
<table border="0" cellspacing="0" cellpadding="0" style="width:auto;">
  <tr>
      <th align="center">已经应用</th>
    <td width="50" align="center">&nbsp;</td>
    <th align="center">应用系统列表</th>
  </tr>
  
  <tr>
    <th align="center"><select name="select2" id="provider" size="10" multiple  style="width:155px;height:160px" panelHeight="auto">
    </select></th>
    <td align="center" valign="middle"><a href="#" class="easyui-linkbutton"  iconCls="icon-select-add" onClick="selectex('provider','systemList2')"></a><br>
<br>
<br>
<a href="#" class="easyui-linkbutton"  iconCls="icon-select-remove" onClick="chooseInterface('systemList2','provider')"></a></td>
    <td align="center"><select name="select" id="systemList2" size="10" multiple  style="width:155px;height:160px" panelHeight="auto">
     
    </select></td>
  </tr>
  </table>
  </div>
    <div style="margin-top:10px;"></div>

  <div class="win-bbar" style="text-align:center"><a href="#" class="easyui-linkbutton"  iconCls="icon-cancel" onClick="clean()">取消</a><a href="#" onclick="save('operationForm',0)" class="easyui-linkbutton"  iconCls="icon-save">保存</a></div>
   <div id="opDlg" class="easyui-dialog"
		style="width:400px;height:280px;padding:10px 20px" closed="true"
		resizable="true"></div>
  </form>
</body>
</html>

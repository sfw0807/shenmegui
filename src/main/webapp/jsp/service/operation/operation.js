var ff = {
    interfaceName: function (value, row, index) {
        try {
            return row.inter.interfaceName;
        } catch (exception) {
        }
    },
    systemChineseName: function (value, row, index) {
        try {
            return row.system.systemChineseName;
        } catch (exception) {
        }
    },
    isStandardText: function (value, row, index) {
        if ("0" == value) {
            return "是";
        } else {
            return "否";
        }
    },
    typeText: function (value, row, index) {
        if ("0" == value) {
            return "消费者";
        }
        if ("1" == value) {
            return "提供者";
        }
    },
    version:function(value, row, index){
 		try {
			return row.version.code
		} catch (exception) {
		}
 	}
}

function save(formId, operation) {
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
                var consumerList = new Array();
                var consumerOptions = $('#consumer option');
                for (var i = 0; i < consumerOptions.length; i++) {
                    // 添加到数组里
                    consumerList.push(consumerOptions.eq(i).val());
                }
                var providerList = new Array();
                var providerOptions = $('#provider option');
                for (var i = 0; i < providerOptions.length; i++) {
                    // 添加到数组里
                    providerList.push(providerOptions.eq(i).val());
                }

                var serviceId = $("#serviceId").attr("value");
                var operationId = $("#operationId").textbox("getValue");
                console.log(serviceId);
                $.ajax({
                    type: "post",
                    async: false,
                    url: "/operation/afterAdd",
                    dataType: "json",
                    data: {
                        "serviceId": serviceId,
                        "operationId": operationId,
                        "consumerStr": consumerList.join(","),
                        "providerStr": providerList.join(",")
                    },
                    success: function (data) {
                        alert("操作成功 ！");
                        //清空页面数据
                        clean();
                        //刷新查询列表
                        parent.serviceInfo.reloadData();
                    }
                });

            } else {
                alert("保存出现异常 ，操作失败！");
            }

        }
    });
}

function clean() {
    $("#operationId").textbox("setValue", "");
    $("#operationName").textbox("setValue", "");
    $("#operationDesc").textbox("setValue", "");
    $("#operationRemark").textbox("setValue", "");
    $("#state").combobox("setValue", "");

    //标签切换
    parent.$('#subtab').tabs('select', '服务基本信息');
}

function choseService(id) {
    $('#' + id).dialog({
        title: '选择服务',
        width: 500,
        closed: false,
        cache: false,
        href: '/jsp/service/serviceTreePage.jsp',
        modal: true
    });
}

/*
 * 弹出服务窗口后选中某一个服务，确认方法
 * 获取选中数据的serviceId
 * 关闭对话框
 * 改变table的url
 * 刷新table
 */
function selectService(listId, dialogId, url) {
    $('#dlg').dialog('close');
               var node = $("#serviceTree").tree("getSelected");
               $("#operationAuditList").datagrid({
                url:'/operation/getAudits/'+ node.service.serviceId
               });
}

//审核通过方法
function auditPass(listId) {
    var checkedItems = $('#' + listId).datagrid('getChecked');
    if (checkedItems != null && checkedItems.length > 0) {
        var ids = [];
        $.each(checkedItems, function (index, item) {
            ids.push(item.operationId);
        });
        $.ajax({
            type: "post",
            async: false,
            contentType: "application/json; charset=utf-8",
            url: "/operation/auditPass",
            dataType: "json",
            data: JSON.stringify(ids),
            success: function (data) {
                alert("操作成功");
                $('#' + listId).datagrid('reload');
            }
        });
    }
}

//审核不通过方法
function auditUnPass(listId) {
    var checkedItems = $('#' + listId).datagrid('getChecked');
    if (checkedItems != null && checkedItems.length > 0) {
        var ids = [];
        $.each(checkedItems, function (index, item) {
            ids.push(item.operationId);
        });
        $.ajax({
            type: "post",
            async: false,
            contentType: "application/json; charset=utf-8",
            url: "/operation/auditUnPass",
            dataType: "json",
            data: JSON.stringify(ids),
            success: function (data) {
                alert("操作成功");
                $('#' + listId).datagrid('reload');
            }
        });
    }
}
//加载系统列表
function loadSystem(id, items, valueField, textField) {
    if (items.length > 0) {
        $.each(items, function (index, item) {
            $("#" + id).append("<option value='" + item[valueField] + "'>" + item[textField] + "</option>");
        });
    }
}
//加载已选系统-接口列表
function loadSelect(id, items) {
    if (items.length > 0) {
        $.each(items, function (index, item) {
            console.log(item);
            var text = item["system"]["systemChineseName"];
            if (null != item["inter"]) {
                text = text + "::" + item["inter"]["interfaceName"];
            }
            $("#" + id).append("<option value='__invoke__" + item["invokeId"] + "'>" + text + "</option>");
        });
    }
}
//从系统选择消费者接口
function chooseInterface(oldListId, newListId) {
    $('#' + oldListId + ' option:selected').each(function () {
        var value = $(this).val();
        var text = this.text;
//		console.log(value);
        $.ajax({
            type: "get",
            async: false,
            contentType: "application/json; charset=utf-8",
            url: "/operation/judgeInterface",
            dataType: "json",
            data: {"systemId": $(this).val()},
            success: function (data) {
                //如果系统没有接口，直接转移
                if (!data) {
//		     		$("#"+oldListId+" option[value="+value+"]").remove();
                    var exsit = $("#" + newListId + " option[value='" + value + "']");
                    console.log(length);
                    if (exsit.length > 0) {
                        alert("应经被选中！");
                    } else {
                        $("#" + newListId).append("<option value='" + value + "'>" + text + "</option>");
                    }

                } else {
                    //如果有接口弹出接口选择页面
                    $('#opDlg').dialog({
                        title: '接口选择',
                        width: 700,
                        closed: false,
                        cache: false,
                        href: '/jsp/service/operation/interfaceList.jsp?systemId=' + value + "&newListId=" + newListId,
                        modal: true
                    });
                }

            }
        });
    });

}

function selectex(oldDataUi, newDataUi) {
    var oldData = ""

    $('#' + oldDataUi + ' option:selected').each(function () {
        var value = $(this).val();
        $("#" + oldDataUi + " option[value=" + value + "]").remove();
//			$("#"+newDataUi).append("<option value='"+$(this).val()+"'>"+this.text+"</option>");
    });
}

function selectInterface(listId) {
    var checkedItems = $('#intefaceList').datagrid('getChecked');
    if (checkedItems != null && checkedItems.length > 0) {
        $.each(checkedItems, function (index, item) {
        	console.log(item);
            var exsit = $("#" + listId + " option[value='__invoke__" + item.invokeId + "']");
            if (exsit.length > 0) {
                alert("该接口已经被选中！");
            } else {
                $("#" + listId).append("<option value='__invoke__" + item.invokeId + "'>" + item.system.systemChineseName + "::" + item.inter.interfaceName + "</option>");
            }

        });
    }
    $('#opDlg').dialog('close');
}
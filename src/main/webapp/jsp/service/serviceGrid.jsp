<%@ page language="java" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>列表页</title>
    <link rel="stylesheet" type="text/css"
          href="/resources/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="/resources/themes/icon.css">
    <link href="/resources/css/ui.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/resources/js/jquery.min.js"></script>
    <script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/resources/js/ui.js"></script>
    <script type="text/javascript" src="/resources/js/urlencode.js"></script>
</head>

<body>
<fieldset>
    <legend>服务基本信息</legend>
    <table border="0" cellspacing="0" cellpadding="0">
        <tr>
            <th>服务一级目录</th>
            <td><input class="easyui-textbox" disabled="disabled"
                       type="text" name="name">
            </td>
            <th>服务二级目录</th>
            <td><input class="easyui-textbox" disabled="disabled"
                       type="text" name="name"></td>
            <th>服务三级目录</th>
            <td><input class="easyui-textbox" disabled="disabled"
                       type="text" name="name">
            </td>
        </tr>
        <tr>
            <th>服务代码</th>
            <td><input class="easyui-textbox" disabled="disabled"
                       type="text" name="serviceId" value="${entity.serviceId }">
            </td>
            <th>服务名称</th>
            <td><input class="easyui-textbox" disabled="disabled"
                       type="text" name="serviceName" value="${entity.serviceName }">
            </td>
            <th>服务功能描述</th>
            <td><input class="easyui-textbox" disabled="disabled"
                       type="text" name="desc" value="${entity.desc }">
            </td>
        </tr>
        <tr>
            <th>服务关键词</th>
            <td><input class="easyui-textbox" disabled="disabled"
                       type="text" name="remark" value="${entity.remark }">
            </td>
            <th>&nbsp;</th>
            <td>&nbsp;</td>
            <th>&nbsp;</th>
            <td>&nbsp;</td>
        </tr>
    </table>


</fieldset>
<table id="operationList" class="easyui-datagrid" title="场景明细"
       data-options="rownumbers:true,singleSelect:false,url:'/operation/getOperationByServiceId/${entity.serviceId }',method:'get',toolbar:toolbar,pagination:true,
				pageSize:10"
       style="height:370px; width:auto;">
    <thead>
    <tr>
        <th data-options="field:'',checkbox:true"></th>


        <th data-options="field:'operationId'">服务场景</th>
        <th data-options="field:'operationName'">场景名称</th>
        <th data-options="field:'operationDesc'">功能描述</th>
        <th data-options="field:'operationRemark'">关键字</th>
        <th data-options="field:'headId'">适用范围说明</th>
        <th data-options="field:'version'">版本号</th>
        <th data-options="field:'optDate'">更新时间</th>
        <th data-options="field:'optUser'">更新用户</th>
        <th data-options="field:'state'">状态</th>
    </tr>
    </thead>
</table>
<div id="w" class="easyui-window" title=""
     data-options="modal:true,closed:true,iconCls:'icon-add'"
     style="width:500px;height:200px;padding:10px;"></div>
<script type="text/javascript">
    function selectTab(title, content) {
        var exsit = parent.$('#subtab').tabs('getTab', title);
        if (exsit == null) {
            parent.$('#subtab').tabs('add', {
                title: title,
                content: content
            });
        } else {
            parent.$('#subtab').tabs('update', {
                tab: exsit,
                options: {
                    content: content
                }
            });
        }
    }

    function reloadData() {
        $("#operationList").datagrid('reload');
    }

    function getSelected() {
        var checkedItems = $("#operationList").datagrid('getChecked');
        if (checkedItems != null && checkedItems.length > 0) {
            return checkedItems[0].operationId;
        }
        return null;
    }

    var toolbar = [{
        text: '新增',
        iconCls: 'icon-add',
        handler: function () {
            var opeAddContent = ' <iframe scrolling="auto" frameborder="0"  src="/operation/addPage/${entity.serviceId }"  style="width:100%;height:100%;"></iframe>'
            selectTab('服务场景', opeAddContent);
            parent.k++;
            parent.$('#subtab').tabs('select', '服务场景');

        }
    }, {
        text: '修改',
        iconCls: 'icon-edit',
        handler: function () {
            var checkedItems = $('#operationList').datagrid('getChecked');
            var checkedItem;
            if (checkedItems != null && checkedItems.length > 0) {
                if (checkedItems.length > 1) {
                    alert("请只选中一行要修改的数据！");
                    return false;
                }
                else {
                    var urlPath = "/operation/editPage?serviceId=${entity.serviceId }&operationId=" + checkedItems[0].operationId;
                    var opeEditContent = ' <iframe scrolling="auto" frameborder="0"  src="' + encodeURI(encodeURI(urlPath)) + '" style="width:100%;height:100%;"></iframe>'
                    selectTab('服务场景', opeEditContent);
                    parent.k++;

                    parent.$('#subtab').tabs('select', '服务场景');
                }
            }
            else {
                alert("请选中要修改的数据！");
            }

        }
    }, {
        text: '删除',
        iconCls: 'icon-remove',
        handler: function () {
            var checkedItems = $('#operationList').datagrid('getChecked');
            if (checkedItems != null && checkedItems.length > 0) {
                if (confirm("确定要删除已选中的" + checkedItems.length + "项吗？一旦删除无法恢复！")) {
                    var ids = [];
                    $.each(checkedItems, function (index, item) {
                        ids.push(item.operationId);
                    });
                    $.ajax({
                        type: "post",
                        async: false,
                        url: "/operation/deletes",
                        dataType: "json",
                        data: {"operationIds": ids.join(",")},
                        success: function (data) {
                            alert("操作成功");
                            $('#operationList').datagrid('reload');
                        }
                    });
                }
            } else {
                alert("没有选中项！");
            }
        }
    }, '-',
        {
            text: '场景明细',
            iconCls: 'icon-qxfp',
            handler: function () {
                var checkedItems = $('#operationList').datagrid('getChecked');
                if (checkedItems != null && checkedItems.length > 0) {
                    var urlPath = "/operation/detailPage?serviceId=${entity.serviceId }&operationId=" + checkedItems[0].operationId;
                    var opeDetailContent = ' <iframe scrolling="auto" frameborder="0"  src="' + encodeURI(encodeURI(urlPath)) + '" style="width:100%;height:100%;"></iframe>'
                    selectTab('服务场景', opeDetailContent);
                    parent.k++;

                    parent.$('#subtab').tabs('select', '服务场景');
                }
                else {
                    alert("请只选中场景后再查看！");
                }
            }
        },
        {
            text: '历史版本',
            iconCls: 'icon-qxfp',
            handler: function () {
                var urlPath = "/operationHis/hisPage?serviceId=${entity.serviceId }";
                var checkedItems = $('#operationList').datagrid('getChecked');
                if (checkedItems != null && checkedItems.length > 0) {
                    urlPath += "&operationId=" + checkedItems[0].operationId;
                }
                var opeHisContent = ' <iframe scrolling="auto" frameborder="0"  src="' + encodeURI(encodeURI(urlPath)) + '" style="width:100%;height:100%;"></iframe>'

                parent.parent.addTab('历史场景', opeHisContent);
            }
        },
        {
            text: '发布版本',
            iconCls: 'icon-qxfp',
            handler: function () {
                var checkedItems = $('#operationList').datagrid('getChecked');
                if (checkedItems != null && checkedItems.length > 0) {
                    if (checkedItems.length > 1) {
                        alert("请只选中一个要发布的场景！");
                        return false;
                    }
                    else {
                        if (confirm("确认发布场景：" + checkedItems[0].operationName + "吗？")) {
                            var urlPath = "/operation/release?serviceId=${entity.serviceId }&operationId=" + checkedItems[0].operationId;
                            var opeReleaseContent = ' <iframe scrolling="auto" frameborder="0"  src="' + encodeURI(encodeURI(urlPath)) + '" style="width:100%;height:100%;"></iframe>'
                            selectTab('服务场景', opeReleaseContent);
                            parent.k++;
                            parent.$('#subtab').tabs('select', '服务场景');
                        }

                    }
                }
                else {
                    alert("请选中要发布的场景！");
                }
            }
        }];
</script>

</body>
</html>
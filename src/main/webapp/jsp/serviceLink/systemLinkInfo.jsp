<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>列表页</title>
    <link rel="stylesheet" type="text/css" href="/resources/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="/resources/themes/icon.css">
    <link href="/resources/css/ui.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/resources/js/jquery.min.js"></script>
    <script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/resources/js/ui.js"></script>
</head>

<body>
<fieldset>
    <legend>条件搜索</legend>
    <table border="0" cellspacing="0" cellpadding="0">
        <tr>
            <th>接口ID</th>
            <td><input class="easyui-textbox" type="text" name="interfaceId"></td>
            <th>接口名称</th>
            <td><input class="easyui-textbox" type="text" name="interfaceName"></td>
            <th>服务代码</th>
            <td><input class="easyui-textbox" type="text" name="serviceId"></td>
            <th>服务名称</th>
            <td><input class="easyui-textbox" type="text" name="serviceName"></td>
        </tr>
        <tr>
            <th>交易属性标识</th>
            <td><input class="easyui-textbox" type="text" name="attribute"></td>
            <th>节点状态</th>
            <td><input class="easyui-textbox" type="text" name="status"></td>
            <th>版本号</th>
            <td><input class="easyui-textbox" type="text" name="status"></td>
            <th></th>
            <td align="right"><a href="#" class="easyui-linkbutton" iconCls="icon-search">搜索</a></td>
        </tr>
    </table>


</fieldset>
<table class="easyui-datagrid" title="交易节点" id="invokeLinkeTable"
       data-options="rownumbers:true,singleSelect:false,url:'/serviceLink/getServiceLink/system/<%=request.getParameter("systemId") %>',method:'get',toolbar:toolbar,pagination:true,
				pageSize:10" style="height:370px; width:auto;">
    <thead>
    <tr>
        <th data-options="field:'productid',checkbox:true"></th>
        <th data-options="field:'interfaceId'">交易码</th>
        <th data-options="field:'interfaceName'">交易名称</th>
        <th data-options="field:'serviceId'">服务码</th>
        <th data-options="field:'serviceName'">服务名称</th>
        <th data-options="field:'nodeType'">节点类型</th>
        <th data-options="field:'status'">状态</th>
    </tr>
    </thead>
</table>
<div id="w" class="easyui-window" title="" data-options="modal:true,closed:true,iconCls:'icon-add'"
     style="width:500px;height:200px;padding:10px;">

</div>
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
    var toolbar = [{
        text: '新增',
        iconCls: 'icon-add',
        handler: function () {
            var content = '<iframe scrolling="auto" frameborder="0"  src="/dataTemplate/syslink/grid3.html" style="width:100%;height:100%;"></iframe>';

            parent.uiinit.subtab.add({
                title: '交易链路维护',
                content: content
            })


            parent.k = 0;
            parent.j = 0;
            parent.m = 0;


            parent.$('#subtab').tabs('select', '交易链路维护')

        }
    }, {
        text: '修改',
        iconCls: 'icon-edit',
        handler: function () {
            var content = '<iframe scrolling="auto" frameborder="0"  src="/dataTemplate/syslink/grid3.html" style="width:100%;height:100%;"></iframe>';

            parent.uiinit.subtab.add({
                title: '交易链路维护',
                content: content
            })


            parent.k = 0;
            parent.j = 0;
            parent.m = 0;


            parent.$('#subtab').tabs('select', '交易链路维护')

        }
    }, {
        text: '删除',
        iconCls: 'icon-remove',
        handler: function () {
        }
    }, '-',

        {
            text: '预览',
            iconCls: 'icon-qxfp',
            handler: function () {
                var checkedItems = $('#invokeLinkeTable').datagrid('getChecked');
                var checkedItem;
                if (checkedItems != null && checkedItems.length > 0) {
                    if (checkedItems.length > 1) {
                        alert("请选择一个节点进行预览！");
                        return false;
                    }
                    else {
                        checkedItem = checkedItems[0];
                        var content = '<iframe scrolling="auto" frameborder="0"  src="/jsp/serviceLink/previewLink.jsp?sourceId='+checkedItem.interfaceId+'" style="width:100%;height:100%;"></iframe>';
                        selectTab('预览', content);
                        selectTab('预览', content);
                    }
                }
                else {
                    alert("请选中要预览的节点！");
                }


            }
        },
        {
            text: '血缘分析',
            iconCls: 'icon-qxfp',
            handler: function () {
                var content = '<iframe scrolling="auto" frameborder="0"  src="/dataTemplate/syslink/t5.html" style="width:100%;height:100%;"></iframe>';

                parent.uiinit.subtab.add({
                    title: '血缘分析',
                    content: content
                })


            }
        }];
</script>

</body>
</html>
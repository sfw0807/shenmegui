<%@ page contentType="text/html; charset=utf-8" language="java"
         errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>列表页</title>
    <link rel="stylesheet" type="text/css"
          href="/resources/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css"
          href="/resources/themes/icon.css">
    <link href="/resources/css/css.css" rel="stylesheet" type="text/css">
</head>

<body>
<fieldset>
    <legend>条件搜索</legend>
    <table border="0" cellspacing="0" cellpadding="0">
        <tr>
            <th>角色代码</th>
            <td><input class="easyui-textbox" type="text" name="Id" id="Id">
            </td>
            <th>角色名称</th>
            <td><input class="easyui-textbox" type="text" name="Name" id="Name">
            </td>
            <th>角色描述</th>
            <td><input class="easyui-textbox" type="text" name="Remark" id="Remark">
            </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td align="right"><a href="#" class="easyui-linkbutton"
                                 iconCls="icon-search" id="search">搜索角色</a>
            </td>
        </tr>
    </table>


</fieldset>

<table id="tt" style="height:370px; width:auto;" title="所有角色">
    <thead>
    <tr>
        <th data-options="field:'role',checkbox:true"></th>
        <th field="id" width="130px" type="text" align="center">角色代码</th>
        <th field="name" width="130px" align="center">角色名称</th>
        <th field="remark" width="130px" align="center">备 注</th>
    </tr>
    </thead>
</table>

<div id="w" class="easyui-window" title=""
     data-options="modal:true,closed:true,iconCls:'icon-add'"
     style="width:500px;height:200px;padding:10px;"></div>
<script type="text/javascript" src="/resources/js/jquery.min.js"></script>
<script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/resources/js/jquery.edatagrid.js"></script>
<script type="text/javascript" src="/resources/js/ui.js"></script>
<script type="text/javascript" src="/plugin/json/json2.js"></script>
<script type="text/javascript" src="/js/role/roleManager.js"></script>
<script type="text/javascript">

    var toolbar = [{
        text: '新增',
        iconCls: 'icon-add',
        handler: function () {
            uiinit.win({
                w: 370,
                iconCls: 'icon-add',
                title: "新增角色",
                url: "roleAdd.jsp"
            })
        }
    },
        {
            text: '修改',
            iconCls: 'icon-edit',
            handler: function () {
                var row = $('#tt').edatagrid('getSelected');
                uiinit.win({
                    w: 370,
                    iconCls: 'icon-edit',
                    title: "修改 角色",
                    url: "/role/getById/" + row.id
                })
            }
        },
        {
            text: '删除',
            iconCls: 'icon-remove',
            handler: function () {
                var row = $('#tt').edatagrid('getSelected');
                var rowIndex = $('#tt').edatagrid('getRowIndex', row);
                roleManager.deleteById(row.id, function (result) {
                    if (result) {
                        alert("删除成功");
                    } else {
                        alert("删除失败");
                    }
                });

                $('#tt').edatagrid('deleteRow', rowIndex);
            }
        },
        {
            text: '权限分配',
            iconCls: 'icon-qxfp',
            handler: function () {
                var row = $('#tt').datagrid('getSelected');
                uiinit.win({
                    w: 400,
                    iconCls: 'icon-qxfp',
                    title: "权限分配",
                    url: "permission.jsp?id=" + row.id
                })
            }
        }, {
            text: '权限修改',
            iconCls: 'icon-qxfp',
            handler: function () {
                var row = $('#tt').datagrid('getSelected');
                uiinit.win({
                    w: 400,
                    iconCls: 'icon-qxfp',
                    title: "权限分配",
                    url: "permissionEdit.jsp?id=" + row.id
                })
            }
        }

    ];
    $(function () {
        $('#tt').datagrid({
            rownumbers: true,
            singleSelect: true,
            url: '/role/getAll',
            method: 'get',
            toolbar: toolbar,
            pagination: true,
            pageSize: 10
        });
        $('#search').click(function () {
            var param = {};
            param.id = $('#Id').val() ? $('#Id').val() : "itisanuniquevaluethatneverbeexisted";
            param.name = $('#Name').val() ? $('#Name').val() : "itisanuniquevaluethatneverbeexisted";
            param.remark = $('#Remark').val() ? $('#Remark').val() : "itisanuniquevaluethatneverbeexisted";
            roleManager.getByParams(param, function (result) {
                $('#tt').edatagrid('loadData', result);
            });
        });
    });

</script>

</body>
</html>
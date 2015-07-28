<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>列表页</title>
    <link rel="stylesheet" type="text/css" href="/resources/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="/resources/themes/icon.css">
    <link href="/resources/css/css.css" rel="stylesheet" type="text/css">
</head>

<body>
<fieldset>
    <legend>条件搜索</legend>
    <table border="0" cellspacing="0" cellpadding="0">
        <tr>
            <th>元数据名称</th>
            <td><input class="easyui-textbox" type="text" style="width:100px" name="metadataId" id="metadataId"></td>
            <th>中文名称</th>
            <td><input class="easyui-textbox" type="text" style="width:100px" name="chineseName" id="chineseName">
            </td>
            <th>英文名称</th>
            <td><input class="easyui-textbox" type="text" style="width:100px" name="metadataName" id="metadataName">
            </td>
            <th style="text-align:right">别名</th>
            <td><input class="easyui-textbox" type="text" style="width:100px" name="metadataAlias" id="metadataAlias">
            </td>
            <th style="text-align:right">状态</th>
            <td><select class="easyui-combobox" panelHeight="auto" style="width:100px" name="status" id="status">
                <option value="未审核">未审核</option>
                <option value="正式">正式</option>
            </select></td>
        </tr>
        <tr>
            <th>类别词</th>
            <td><input type="text" name="categoryWordId" id="categoryWordId"
                       class="easyui-combobox"
                       data-options="
						url:'/metadata/categoryWord',
				 		 method:'get',
				 		 valueField: 'id',
				 		 textField: 'chineseWord',
				 		 onChange:function(newValue, oldValue){
							this.value=newValue;
						}
					"
                       style="width: 100px; "/></td>
            <th>基线版本号</th>
            <td><input class="easyui-textbox" style="width:100px" type="text" name="version" id="version"></td>
            <th> 创建人</th>
            <td><input class="easyui-textbox" style="width:100px" type="text" name="name"></td>
            <th> 创建起始日期</th>
            <td><input class="easyui-datebox" style="width:100px" type="text" name="startDate" id="startDate"></td>
            <th> 创建结束日期</th>
            <td><input class="easyui-datebox" style="width:100px" type="text" name="endDate" id="endDate"></td>
            <td align="right"><a href="#" id="queryMetadataBtn" class="easyui-linkbutton" iconCls="icon-search">搜索</a></td>
        </tr>
    </table>
</fieldset>
<table id="metadataList" class="easyui-datagrid" title="元数据管理"
       data-options="rownumbers:true,singleSelect:false,url:'/metadata/list',method:'get',toolbar:toolbar,pagination:true,
				pageSize:10,fitColumns:'false'" style="height:370px; width:auto;">
    <thead>
    <tr>
        <th data-options="field:'',checkbox:true"></th>
        <th data-options="field:'metadataId'">元数据名称</th>
        <th data-options="field:'chineseName'">中文名称</th>
        <th data-options="field:'metadataName'">英文名称</th>
        <th data-options="field:'categoryWordId'">类别词</th>
        <th data-options="field:'type'">类型</th>
        <th data-options="field:'length'">长度</th>
        <th data-options="field:'scale'">精度</th>
        <td data-options="field:'dataCategory'">数据项分类</td>
        <td data-options="field:'buzzCategory'">业务项分类</td>
        <th data-options="field:'bussDefine'">业务定义</th>
        <th data-options="field:'bussRule'">业务规则</th>
        <th data-options="field:'dataSource'">数据来源</th>
        <th data-options="field:'status'">状态</th>
        <th data-options="field:'version'">版本号</th>
        <th data-options="field:'optUser'">创建人</th>
        <th data-options="field:'optDate'">创建时间</th>
        <th data-options="field:'  '">审核人</th>
        <th data-options="field:'  '">审核时间</th>
    </tr>
    </thead>
</table>
<div id="w" class="easyui-window" title="" data-options="modal:true,closed:true,iconCls:'icon-add'"
     style="width:500px;height:200px;padding:10px;">

</div>
<script type="text/javascript">
    var toolbar = [{
        text: '新增',
        iconCls: 'icon-add',
        handler: function () {
            uiinit.win({
                w: 500,
                iconCls: 'icon-add',
                title: "新增元数据",
                url: "/assets/metadata/add.jsp"
            });
        }
    }, {
        text: '修改',
        iconCls: 'icon-edit',
        handler: function () {
            var checkedItems = $('#metadataList').datagrid('getChecked');
            var checkedItem;
            if (checkedItems != null && checkedItems.length > 0) {
                if (checkedItems.length > 1) {
                    alert("请只选中一行要修改的数据！");
                    return false;
                }
                else {
                    checkedItem = checkedItems[0];
                    uiinit.win({
                        w: 500,
                        iconCls: 'icon-edit',
                        title: "修改元数据",
                        url: "/metadata/editPage?metadataId=" + checkedItem.metadataId
                    });
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
            deleteObj();
        }
    }, '-',
        {
            text: '关联服务场景',
            iconCls: 'icon-cfp',
            handler: function () {
                uiinit.win({
                    w: 500,
                    iconCls: 'icon-cfp',
                    title: "关联服务场景",
                    url: "/dataTemplate/words/tree.html"
                });
            }
        },
        {
            text: '公共代码',
            iconCls: 'icon-cfp',
            handler: function () {
                alert('移出')
            }
        }, '-',
        {
            text: '导入',
            iconCls: 'icon-cfp',
            handler: function () {
                uiinit.win({
                    w: 500,
                    iconCls: 'icon-cfp',
                    title: "关联服务场景",
                    url: "/jsp/metadata/importMetadata.jsp"
                });
            }
        },
        {
            text: '导出',
            iconCls: 'icon-save',
            handler: function () {
                $(function() {
                    $.fileDownload("/metadata/export", {
                    });
                });
            }
        },
        {
            text: '检出',
            iconCls: 'icon-qxfp',
            handler: function () {

            }
        },
        {
            text: '提交任务',
            iconCls: 'icon-qxfp',
            handler: function () {

            }
        }];
</script>

<script type="text/javascript" src="/resources/js/jquery.min.js"></script>
<script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/resources/js/ui.js"></script>
<script type="text/javascript" src="/assets/metadata/metadataManager.js"></script>
<script type="text/javascript" src="/assets/metadata/metadata.js"></script>
<scirpt type="text/javascript" src="/resources/js/jquery.fileupload.js"></scirpt>
<script type="text/javascript" src="/resources/js/jquery.fileDownload.js"></script>



<script type="text/javascript">
    $(function () {
        $(".datagrid-cell-group").width("auto");
    })
</script>
</body>
</html>
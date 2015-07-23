<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
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
    <link href="/plugin/aehlke-tag-it/css/jquery.tagit.css" rel="stylesheet" type="text/css">
    <link href="/plugin/aehlke-tag-it/css/tagit.ui-zendesk.css" rel="stylesheet" type="text/css">
    <link href="/resources/css/ui.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/resources/js/jquery.min.js"></script>
    <script src="/newui/plugins/jQueryUI/jquery-ui.js" type="text/javascript" charset="utf-8"></script>
    <script src="/plugin/aehlke-tag-it/js/tag-it.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/resources/js/ui.js"></script>
    <script type="text/javascript" src="/js/service/export.js"></script>
    <script type="text/javascript" src="/resources/js/jquery.fileDownload.js"></script>
    <script type="text/javascript" src="/assets/tag/tagManager.js"></script>

</head>

<body>
<fieldset>
    <legend>条件搜索</legend>
    <table border="0" cellspacing="0" cellpadding="0">
        <!--
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
         -->
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
            <th>服务备注</th>
            <td><input class="easyui-textbox" disabled="disabled"
                       type="text" name="remark" value="${entity.remark }">
            </td>
            <th>服务标签</th>
            <td>
                <ul id="tags"></ul>
            </td>
            <th>
                <a href="#" id="saveTagBtn" class="easyui-linkbutton" iconCls="icon-save" style="margin-left:1em">保存</a>
            </th>
            <td>&nbsp;</td>
        </tr>
    </table>


</fieldset>
<table id="operationList" class="easyui-datagrid" title="场景明细"
       data-options="
			rownumbers:true,
			singleSelect:false,
			url:'/operation/getOperationByServiceId/${entity.serviceId }',
			method:'get',toolbar:toolbar,
			pagination:true,
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
        <th data-options="field:' '" formatter='formatter.version'>版本号</th>
        <th data-options="field:'optDate'">更新时间</th>
        <th data-options="field:'optUser'">更新用户</th>
        <th data-options="field:'state'" formatter='formatter.operationState'>状态</th>
    </tr>
    </thead>
</table>
<div id="w" class="easyui-window" title=""
     data-options="modal:true,closed:true,iconCls:'icon-add'"
     style="width:500px;height:200px;padding:10px;"></div>
<div id="opDialog" class="easyui-dialog"
     style="width:400px;height:280px;padding:10px 20px" closed="true"
     resizable="true"></div>
</body>
<script type="text/javascript">
    var formatter = {
        operationState: function (value, row, index) {
            if (value == 0) {
                return "<font color=''>待审核</font>";
            }
            if (value == 1) {
                return "<font color='green'>审核通过</font>";
            }
            if (value == 2) {
                return "<font color='red'>审核未通过</font>";
            }
        },
        version: function (value, row, index) {
            try {
                return row.version.code
            } catch (exception) {
            }
        }
    };
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
            //var sdaAddContent = ' <iframe scrolling="auto" frameborder="0"  src="/sda/sdaPPage?serviceId=${entity.serviceId }"  style="width:100%;height:100%;"></iframe>'
            //selectTab('服务接口SDO', '');

            //selectTab('服务SLA', '');
            //selectTab('服务OLA', '');
            //selectTab('服务接口隐射', '');
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
                        var operationPK = {};
                        operationPK.serviceId = "${entity.serviceId }";
                        operationPK.operationId = item.operationId;
                        ids.push(operationPK);
                    });
                    $.ajax({
                        type: "post",
                        async: false,
                        contentType: "application/json; charset=utf-8",
                        url: "/operation/deletes",
                        dataType: "json",
                        data: JSON.stringify(ids),
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
                        if (checkedItems[0].state == "1") {
                            var urlPath = "/jsp/service/operation/release.jsp?operationName=" + checkedItems[0].operationName + "&versionCode=" + checkedItems[0].version.code + "&operationId=" + checkedItems[0].operationId;
                            $('#opDialog').dialog({
                                title: 'SDAHis',
                                width: 500,
                                closed: false,
                                cache: false,
                                href: urlPath,
                                modal: true
                            });
                        }
                        else {
                            alert("请先通过审核再发布!")
                            return false;
                        }

                    }
                }
                else {
                    alert("请选中要发布的场景！");
                }
            }
        },
        {
            text: '审核',
            iconCls: 'icon-audit',
            handler: function () {
                var urlPath = "/operation/auditPage?serviceId=${entity.serviceId }";
                var checkedItems = $('#operationList').datagrid('getChecked');
                if (checkedItems != null && checkedItems.length > 0) {
                    urlPath += "&operationId=" + checkedItems[0].operationId;
                }
                var opeAuditContent = ' <iframe scrolling="auto" frameborder="0"  src="' + urlPath + '" style="width:100%;height:100%;"></iframe>'

                parent.parent.$('#mainContentTabs').tabs('add', {
                    title: '服务场景审核',
                    content: opeAuditContent,
                    closable: true
                });
            }
        },
        {
			text:'导出EXCEL',
			iconCls:'icon-excel-export',
            handler: function () {
                var form=$("<form>");//定义一个form表单
                form.attr("style","display:none");
                form.attr("target","");
                form.attr("method","post");
                form.attr("action","/excelExporter/exportService");
                var input1=$("<input>");
                input1.attr("type","hidden");
                input1.attr("name","serviceId");
                input1.attr("value","${entity.serviceId }");
                var input2=$("<input>");
                input2.attr("type","hidden");
                input2.attr("name","type");
                input2.attr("value","SERVICE");

                $("body").append(form);//将表单放置在web中
                form.append(input1);
                form.append(input2);

                form.submit();//表单提交

            }
        }
    ];

    function releaseOp(desc, operationId) {
        $('#opDialog').dialog('close');
        var urlPath = "/operation/release?serviceId=${entity.serviceId }&operationId=" + operationId + "&versionDesc=" + desc;
        console.log(urlPath);
        var opeReleaseContent = ' <iframe scrolling="auto" frameborder="0"  src="' + encodeURI(encodeURI(urlPath)) + '" style="width:100%;height:100%;"></iframe>'
        selectTab('服务场景', opeReleaseContent);
        parent.k++;
        parent.$('#subtab').tabs('select', '服务场景');

    }

    $(function () {
        var serviceId = "${entity.serviceId}";
        /**
         *  初始化接口标签
         * @param result
         */
        var initTags = function initTags(result){
            result.forEach(function(tag){
                console.log(tag);
                $("#tags").append("<li>" + tag.tagName + "</li>");
            });
            $("#tags").tagit();

        };
        tagManager.getTagForService(serviceId,initTags);

        $("#saveTagBtn").click(function () {
            var tagNames = $("#tags").tagit("assignedTags");
            var tags = [];
            tagNames.forEach(function (tagName){
                var tagToAdd = {};
                tagToAdd.tagName = tagName;
                tags.push(tagToAdd);
            });
            tagManager.addTagForService(serviceId, tags, function (){
                alert("标签保存成功");
            });
        });

    });


</script>

</body>
</html>


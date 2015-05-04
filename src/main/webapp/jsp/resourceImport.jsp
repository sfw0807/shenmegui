<%@ page language="java" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String ctx = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ include file="/jsp/includes/header.jsp" %>
<%@ include file="/jsp/includes/jquery.jsp" %>
<%@ include file="/jsp/includes/jquery-ui.jsp" %>
<%@ include file="/jsp/includes/jquery-plugins.jsp" %>
<%@ include file="/jsp/includes/datatable.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>资源导入</title>

    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="<%=ctx %>/css/index.css"/>
    <link rel="stylesheet" href="<%=ctx %>/css/demos.css"/>
    <script src="<%=ctx %>/js/layout.js" type="text/javascript"></script>
    <script src="<%=ctx %>/js/resourceImport.js" type="text/javascript"></script>
    <script src="<%=ctx %>/js/json/json2.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function () {
            $('#fileUpload').change(function () {
                var fileName = $('#fileUpload').val();
                if (fileName == "") {
                    $('#import').attr("disabled", true);
                }
                else {
                    $('#import').attr("disabled", false);
                }
            });
            $('#onlinefileUpload').change(function () {
                var fileName = $('#onlinefileUpload').val();
                if (fileName == "") {
                    $('#onlineimport').attr("disabled", true);
                }
                else {
                    $('#onlineimport').attr("disabled", false);
                }
            });
            $('#metadatafileUpload').change(function () {
                var fileName = $('#metadatafileUpload').val();
                if (fileName == "") {
                    $('#metadataImport').attr("disabled", true);
                }
                else {
                    $('#metadataImport').attr("disabled", false);
                }
            });
        })
    </script>
    <style>
        .ui-menu {
            position: absolute;
            width: 100px;
        }

        .demo-description {
            clear: both;
            padding: 12px;
            font-size: 1.3em;
            line-height: 1.4em;
        }

        .ui-draggable, .ui-droppable {
            background-position: top;
        }
    </style>

</head>
<body>
<div id="tabs" style="width: 100%">
    <ul>
        <li id='tab0'>
            <a href='#tabs-0'>映射文档导入</a>
        </li>
        <li id='tab2'>
            <a href='#tabs-2'>元数据导入</a>
        </li>
        <li id='tab1'>
            <a href='#tabs-1'>上线清单导入</a>
        </li>
    </ul>

    <div id="tabs-0">
        <div class="ui-widget-header" style="margin-bottom: 0.1em;padding:0.1em;">
            <form action="<%=path %>/import/mapping" method="post" enctype="multipart/form-data" id="mapping">
                请选择导入的映射文档：
                <input style="width:14%;" type="file" name="file" id="fileUpload"/>
                <br/>
                是否覆盖导入：
                <select id="select" name="select">
                    <option value="Y">是</option>
                    <option value="N" selected>否</option>
                </select>
                <br/>
                <input style="width:4%;" type="submit" value="导入" id="import" disabled="true"/>
            </form>
        </div>
    </div>

    <div id="tabs-2">
        <div class="ui-widget-header" style="margin-bottom: 0.1em;padding:0.1em;">
            <form action="<%=path %>/import/metadata" method="post" enctype="multipart/form-data" id="metadata">
                请选择导入的元数据文件：
                <input style="width:14%;" type="file" name="file" id="metadatafileUpload"/>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input style="width:4%;" type="submit" value="导入" id="metadataImport" disabled="true"/>
            </form>
        </div>
    </div>

    <div id="tabs-1">
        <div class="ui-widget-header" style="margin-bottom: 0.1em;padding:0.1em;">
            <form action="<%=path %>/import/online" method="post" enctype="multipart/form-data" id="online">
                请选择导入的上线清单文件：
                <input style="width:14%;" type="file" name="file" id="onlinefileUpload"/>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input style="width:4%;" type="submit" value="导入" id="onlineimport" disabled="true"/>
            </form>
        </div>
    </div>
</div>
</body>
</html>




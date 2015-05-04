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
    <title>接口信息管理</title>

    <meta http-equiv="content-type" content="text/html; charset=UTF-8">

    <link rel="stylesheet" type="text/css" href="../js/multi-select/css/jquery.multiselect.css"/>
    <link rel="stylesheet" type="text/css" href="../js/multi-select/css/jquery.multiselect.filter.css"/>
    <%--<link rel="stylesheet" type="text/css" href="../js/jquery-ui/css/redmond/jquery.ui.theme.css"/>--%>
    <%--<link rel="stylesheet" type="text/css" href="../js/jquery-ui/css/redmond/jquery-ui.css"/>--%>
    <%--<link rel="stylesheet" href="../themes/smoothness/jquery-ui-1.8.4.custom.css"/>--%>
    <link rel="stylesheet" href="../css/index.css"/>
    <%--<link rel="stylesheet" type="text/css" href="../js/jquery.datatables/css/jquery.dataTables.css"/>--%>
    <%--<script src="../js/jquery-ui/js/jquery-1.10.2.js"></script>--%>
    <%--<script src="../js/jquery.datatables/js/jquery.dataTables.js" type="text/javascript"></script>--%>
    <script src="../assets/interface/js/interfaceManager.js" type="text/javascript"></script>
    <script src="../js/layout.js" type="text/javascript"></script>
    <script src="../assets/interface/js/interface.js" type="text/javascript"></script>
    <%--<script src="../js/jquery.ui.widget.js"></script>--%>
    <%--<script src="../js/jquery-ui.min.js" type="text/javascript"></script>--%>
    <%--<script src="../js/combo-box.js" type="text/javascript"></script>--%>
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

        .ui-combobox {
            position: relative;
            display: inline-block;
        }

        .ui-combobox-toggle {
            position: absolute;
            top: 0;
            bottom: 0;
            margin-left: -1px;
            padding: 0;
            /* adjust styles for IE 6/7 */
            *height: 1.5em;
            *top: 0.1em;
        }

        .ui-combobox-input {
            margin: 0;
            padding: 0.3em;
            *weight: 15em
        }
    </style>

</head>

<body>
<br>
<div class="ui-widget-header" style="margin-bottom:0.5em;padding:0.2em;">
    <!-- <input type='button' id='query' value='查询'></input> -->
    <input type='button' id='add' value='新增'></input>
    <input type='button' id='edit' value='编辑'></input>
    <input type='button' id='delete' value='删除'></input>
    <input type='button' id='reset' value='重置'></input>
</div>

<table cellpadding="0" cellspacing="0" border="0" class="display" id="interfaceManagementTable">
    <tfoot>
    <tr>
        <th><input type="text" name="ecode" value="接口ID" class="search_init"/></th>
        <th><input type="text" name="interface_NAME" value="接口名称" class="search_init"/></th>
        <th><input type="text" name="operation_ID" value="操作ID" class="search_init"/></th>
        <th><input type="text" name="service_ID" value="服务ID" class="search_init"/></th>
        <th><input type="text" name="consumer_SYSAB" value="调用系统" class="search_init"/></th>
        <th><input type="text" name="consumer_SYSNAME" value="调用系统名称" class="search_init"/></th>
        <th><input type="text" name="provider_SYSAB" value="提供系统" class="search_init"/></th>
        <th><input type="text" name="provider_SYSNAME" value="提供系统名称" class="search_init"/></th>
    </tr>
    </tfoot>
</table>
</body>
</html>


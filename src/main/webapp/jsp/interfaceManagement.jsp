<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/jsp/header/header.jsp" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>接口信息管理</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="<%=path%>/js/multi-select/css/jquery.multiselect.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/js/multi-select/css/jquery.multiselect.filter.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/js/jquery-ui/css/redmond/jquery.ui.theme.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/js/jquery-ui/css/redmond/jquery-ui.css"/>
    <link rel="stylesheet" href="<%=path%>/themes/smoothness/jquery-ui-1.8.4.custom.css"/>
    <link rel="stylesheet" href="<%=path%>/css/index.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/js/jquery.datatables/css/jquery.dataTables.css"/>
    <script src="<%=path%>/js/jquery.datatables/js/jquery.dataTables.js" type="text/javascript"></script>
    <script src="<%=path%>/assets/interface/js/interfaceManager.js" type="text/javascript"></script>
    <script src="<%=path%>/js/layout.js" type="text/javascript"></script>
    <script src="<%=path%>/assets/interface/js/interface.js" type="text/javascript"></script>
    <script src="<%=path%>/js/jquery-ui.min.js" type="text/javascript"></script>
    <script src="<%=path%>/js/combo-box.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="<%=path%>/assets/interface/css/interface.css"/>
</head>

<body>
<div id="tabs" style="width: 100%">
    <ul>
        <li id='tab0'>
            <a href='#tabs-0'>服务管理</a>
        </li>
    </ul>
    <div id="tabs-0">
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
    </div>
</div>

</body>
</html>

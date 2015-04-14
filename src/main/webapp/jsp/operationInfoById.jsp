<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ include file="/jsp/header/header.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>编辑场景</title>
    <meta http-equiv="keywords" content="scene">
    <meta http-equiv="description" content="modify scene">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="<%=path%>/themes/smoothness/jquery-ui-1.8.4.custom.css"/>
    <link rel="stylesheet" href="<%=path%>/js/jquery-ui/development-bundle/themes/base/jquery.ui.all.css"/>
    <link rel="stylesheet" href="<%=path%>/css/index.css"/>
    <script src="<%=path%>/js/jquery-ui-tabs.js"></script>
    <script src='<%=path%>/js/jquery.datatables/js/jquery.dataTables.js' type="text/javascript"></script>
    <link rel='stylesheet' type='text/css' href='<%=path%>/js/jquery.datatables/css/jquery.dataTables.css'/>
    <script src="<%=path%>/js/jquery.ui.core.js"></script>
    <script src="<%=path%>/js/jquery.ui.widget.js"></script>
    <script src="<%=path%>/js/jquery.ui.datepicker.js"></script>
    <script src="<%=path%>/assets/scene/js/operationManager.js" type="text/javascript"></script>
    <script src="<%=path%>/assets/scene/js/editOperation.js" type="text/javascript"></script>
    <script src="<%=path%>/js/layout.js" type="text/javascript"></script>
    <link rel='stylesheet' type='text/css' href='<%=path%>/js/jquery.datatables/css/jquery.dataTables.css'/>
</head>

<div id="tabs" style="width:100%">
    <ul>
        <li id='tab0'><a href='#tabs-0'>基本定义</a></li>
        <li id='tab1'><a href='#tabs-1'>SDA</a></li>
        <li id='tab2'><a href='#tabs-2'>SLA</a></li>
        <li id='tab3'><a href='#tabs-3'>OLA</a></li>
    </ul>
    <div id="tabs-0">
        <div class="ui-widget-header" style="margin-bottom: 0.5em;padding: 0.2em;">
            <input type="button" value="保存" id="saveOperationDef"
                   class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/>
        </div>
        <label style="width:8%">操作编号:</label><input type="text" id="operationId" value="" style="width:20%"
                                                    disabled="true"/><br>
        <label style="width:8%">服务编号:</label><input type="text" id="serviceId" value="" style="width:20%"
                                                    disabled="true"/><br>
        <label style="width:8%">操作名称:</label><input type="text" id="operationName" value="" style="width:20%"/><br>
        <label style="width:8%">操作状态:</label>
        <select id="state" class="text ui-widget-content ui-corner-all">
            <option value="服务定义">服务定义</option>
            <option value="开发">开发</option>
            <option value="联调测试">联调测试</option>
            <option value="sit测试">sit测试</option>
            <option value="uat测试">uat测试</option>
            <option value="投产验证">投产验证</option>
            <option value="上线">上线</option>
        </select><br>
        <label style="width:8%">操作描述:</label><input type="text" id="operationRemark" value=""
                                                    style="width:20%;position:relative;z-index:10000"/><br>
        <label style="width:8%">开发版本:</label><input type="text" id="version" value="" style="width:20%"
                                                    disabled="true"/>
    </div>

    <div id="tabs-1">
        <div class="ui-widget-header" style="margin-bottom: 0.5em;padding: 0.2em;">
            <input type="button" value="查看SDA" id="seesda" class="ui-button ui-widget ui-state-default ui-corner-all"
                   role="button" aria-disabled="false"/>
        </div>
    </div>

    <div id="tabs-2">
        <div class="ui-widget-header" style="margin-bottom: 0.5em;padding: 0.2em;">
            <input type="button" value="编辑" id="editOperationSLA"
                   class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/>
            <input type="button" value="添加" id="addOperationSLA"
                   class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/>
            <input type="button" value="删除" id="deleteOperationSLA"
                   class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/>
            <input type="button" value="保存" id="saveOperationSLA"
                   class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/>
        </div>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="operationSlaTable">
        </table>
    </div>

    <div id="tabs-3">
        <div class="ui-widget-header" style="margin-bottom: 0.5em;padding: 0.2em;">
            <input type="button" value="编辑" id="editOperationOLA"
                   class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/>
            <input type="button" value="添加" id="addOperationOLA"
                   class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/>
            <input type="button" value="删除" id="deleteOperationOLA"
                   class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/>
            <input type="button" value="保存" id="saveOperationOLA"
                   class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false"/>
        </div>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="operationOlaTable">
        </table>
    </div>
</div>
</body>
</html>


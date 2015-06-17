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
    <title>操作管理</title>
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>

    <script src="<%=ctx%>/js/layout.js" type="text/javascript"></script>
    <script src="<%=ctx%>/assets/scene/js/operationAllManager.js" type="text/javascript"></script>
    <script src="<%=ctx%>/assets/scene/js/operationAll.js" type="text/javascript"></script>
    <script src="<%=ctx%>/plugins/bootstrap/js/bootstrap-modal.js"></script>

    <link rel="stylesheet" href="<%=ctx %>/css/index.css"/>
    <link rel="stylesheet" href="<%=ctx %>/assets/scene/css/scene.css"/>
    <style>
        fieldset { padding:0; border:0; margin-top:25px; }
        h1 { font-size: 1.2em; margin: .6em 0; }
        div#users-contain { width: 350px; margin: 20px 0; }
        div#users-contain table { margin: 1em 0; border-collapse: collapse; width: 100%; }
        div#users-contain table td, div#users-contain table th { border: 1px solid #eee; padding: .6em 10px; text-align: left; }
        .ui-dialog .ui-state-error { padding: .3em; }
        .validateTips { border: 1px solid transparent; padding: 0.3em; }
    </style>
</head>
<body>
</div>
<div id="tabs" style="width: 100%;">
    <ul>
        <li id='tab0'>
            <a href='#tabs-0'>操作管理</a>
        </li>
    </ul>

    <div id="tabs-0">
        <div class="ui-widget-header" style="margin-bottom: 0.5em;padding: 0.2em;">
            <input type="button" value="新增" id="addOperation"/>
            <input type="button" value="删除" id="deleteOperation"/>
            <input type="button" value="发布" id="deployOperation"/>
            <input type="button" value="重定义" id="redefOperation"/>
            <input type="button" value="上线" id="publishOperation"/>
            <input type="button" value="提交审核" id="submit"/>
            <input type="button" value="导出配置" id="exportConfigBtn"/>
            <input type='button' id='checkAll' value='全选'></input>
            <input type='button' id='toggleAll' value='反选'></input>
        </div>
        <div id="configExportDialog" title="配置选项">
            <p class="validateTips">所有项必输</p>
            <form>
                <fieldset>
                    <label for="consumerInterface">消费方接口</label>
                    <input type="text" name="consumerInterface" id="consumerInterface"
                           class="text ui-widget-content ui-corner-all">
                    <label for="consumerProtocal">消费方协议</label>
                    <input type="text" name="consumerProtocal" id="consumerProtocal"
                           class="text ui-widget-content ui-corner-all">
                    <label for="providerInterface">提供方接口</label>
                    <input type="text" name="providerInterface" id="providerInterface"
                           class="text ui-widget-content ui-corner-all">
                    <label for="providerProtocal">提供方协议</label>
                    <input type="text" name="providerProtocal" id="providerProtocal"
                           class="text ui-widget-content ui-corner-all">
                    <input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
                </fieldset>
            </form>
        </div>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="operationTable">
            <tfoot>
            <tr>
                <th>
                    <input type="text" name="operationId" id="operationId" value="操作ID"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="operationName" id="operationName" value="操作名称"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="serviceId" id="serviceId" value="服务ID"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="serviceName" id="serviceName" value="服务名称"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="state" id="state" value="操作状态"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="version" id="version" value="开发版本"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="publishVersion" id="publishVersion" value="上线版本"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="publishDate" id="publishDate" value="上线日期"
                           class="search_init"/>
                </th>
                <th>
                    <input type="hidden" name="action" id="action" value="动作"
                           class="search_init"/>
                </th>
                <th>
                    <input type="hidden" name="history" id="history" value="历史"
                           class="search_init"/>
                </th>
                <th>
                    <input type="hidden" name="invokeInfo" id="invokeInfo" value="调用情况"
                           class="search_init"/>
                </th>
            </tr>
            </tfoot>
        </table>
    </div>
</div>
</body>
</html>


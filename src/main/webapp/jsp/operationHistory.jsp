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
    <title>操作历史版本管理</title>
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>

    <link rel="stylesheet" href="<%=ctx%>/css/index.css"/>
    <script src="<%=ctx%>/js/layout.js" type="text/javascript"></script>
    <script src="<%=ctx%>/js/json/json2.js" type="text/javascript"></script>
    <script src="<%=ctx%>/js/operationHistoryAllManager.js" type="text/javascript"></script>
    <script src="<%=ctx%>/js/operationHistoryAll.js" type="text/javascript"></script>

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

        a:link {
            text-decoration: none;
            color: blue;
        }

        a:visited {
            text-decoration: underline;
            color: blue;
        }

        a:hover {
            text-decoration: underline;
            color: blue;
        }

        a:active {
            text-decoration: underline;
            color: blue;
        }
    </style>

</head>
<body>
<div id="tabs" style="width: 100%">
    <ul>
        <li id='tab0'>
            <a href='#tabs-0'>操作历史版本管理</a>
        </li>
    </ul>

    <div id="tabs-0">
        <div class="ui-widget-header" style="margin-bottom: 0.5em;padding: 0.2em;">
            <input type="button" value="还原" id="backOperation"/>
        </div>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="operationHistoryTable">
            <tfoot>
            <tr>
                <th>
                    <input type="text" name="operationId" id="operationId" value="操作ID"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="serviceId" id="serviceId" value="操作名称"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="operationName" id="operationName" value="操作版本"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="operationVersion" id="operationVersion" value="操作状态"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="operationState" id="operationState" value="服务ID"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="serviceVersion" id="serviceVersion" value="服务版本"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="remark" id="remark" value="备注"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="action" id="action" value="详细信息"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="modifyUser" id="modifyUser" value="操作用户"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="updateTime" id="updateTime" value="更新时间"
                           class="search_init"/>
                </th>
            </tr>
            </tfoot>
        </table>
    </div>
</div>
</body>
</html>

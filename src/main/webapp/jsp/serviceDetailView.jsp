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
    <title>服务详细信息查询</title>

    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="<%= path%>/css/index.css"/>
    <script src="<%= path%>/js/layout.js" type="text/javascript"></script>
    <script src="<%= path%>/js/serviceDetailManager.js" type="text/javascript"></script>
    <script src="<%= path%>/js/serviceDetail.js" type="text/javascript"></script>
    <script src="<%= path%>/js/json/json2.js" type="text/javascript"></script>
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
            <a href='#tabs-0'>服务详细信息查询</a>
        </li>
    </ul>

    <div id="tabs-0">
        <div class="ui-widget-header" style="margin-bottom: 0.5em;padding: 0.2em;">
            <input type="button" value="导出" id="export"/>
        </div>

        <table cellpadding="0" cellspacing="0" border="0" class="display"
               id="serviceDetailTable">
            <tfoot>
            <tr>
                <th>
                    <input type="text" name="serviceInfo" id="serviceInfo" value="服务ID/名称"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="operationInfo" id="operationInfo" value="操作ID/名称"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="interfaceInfo" id="interfaceInfo" value="交易代码/名称"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="passbySysInfo" id="passbySysInfo" value="经由系统简称/名称"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="provideSysInfo" id="provideSysInfo" value="提供方系统简称/名称"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="provideMsgType" id="provideMsgType" value="提供报文类型"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="through" id="through" value="是否穿透"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="state" id="state" value="交易状态"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="modifyTimes" id="modifyTimes" value="修订次数"
                           class="search_init" style="display:none"/>
                </th>
                <th>
                    <input type="text" name="onlineDate" id="onlineDate" value="上线日期"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="onlineVersion" id="onlineVersion" value="上线版本"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="field" id="field" value="备注"
                           class="search_init" style="display:none"/>
                </th>
            </tr>
            </tfoot>
        </table>
    </div>
</div>
</body>
</html>



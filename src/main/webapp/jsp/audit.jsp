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
    <title>服务审核</title>

    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">

    <link rel="stylesheet" href="<%=path %>/css/index.css"/>
    <link rel="stylesheet" href="<%=path %>/css/demos.css"/>
    <script src="<%=path%>/js/layout.js" type="text/javascript"></script>
    <script src="<%=path%>/js/auditManager.js" type="text/javascript"></script>
    <script src="<%=path%>/js/audit.js" type="text/javascript"></script>
    <script src="<%=path%>/js/json/json2.js" type="text/javascript"></script>
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
            <a href='#tabs-0'>服务审核</a>
        </li>
        <li id='tab1'>
            <a href='#tabs-1'>操作审核</a>
        </li>
    </ul>

    <div id="tabs-0">
        <div class="ui-widget-header"
             style="margin-bottom: 0.5em; padding: 0.5em;">
            <button id="servicePassed">
                通过
            </button>
            <button id="serviceRefused">
                拒绝
            </button>
            <input type='button' id='checkAllService' value='全选'></input>
            <input type='button' id='toggleAllService' value='反选'></input>
        </div>
        <%--<div class="ui-widget-header" style="margin-bottom:0.5em;">--%>
            <%--<input type='button' id='checkAllService' value='全选'></input>--%>
            <%--<input type='button' id='toggleAllService' value='反选'></input>--%>
        <%--</div>--%>
        <table cellpadding="0" cellspacing="0" border="0" class="display"
               id="serviceInfoTable">
            <tfoot>
            <tr>
                <th>
                    <input type="text" name="serviceId" id="serviceId" value="服务Id"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="serviceName" id="serviceName"
                           value="服务名称" class="search_init"/>
                </th>
                <th>
                    <input type="text" name="version" id="version" value="开发版本"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="auditState" id="auditState" value="审核状态"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="modifyUser" id="modifyUser"
                           value="操作用户" class="search_init"/>
                </th>
            </tr>
            </tfoot>
        </table>
    </div>
    <div id="tabs-1">
        <div class="ui-widget-header"
             style="margin-bottom: 0.5em; padding: 0.5em;">
            <button id="operationPassed">
                通过
            </button>
            <button id="operationRefused">
                拒绝
            </button>
            <input type='button' id='checkAllOperation' value='全选'></input>
            <input type='button' id='toggleAllOperation' value='反选'></input>
        </div>
        <%--<div class="ui-widget-header" style="margin-bottom:0.5em;">--%>
            <%--<input type='button' id='checkAllOperation' value='全选'></input>--%>
            <%--<input type='button' id='toggleAllOperation' value='反选'></input>--%>
        <%--</div>--%>
        <table cellpadding="0" cellspacing="0" border="0" class="display"
               id="operationTable">
            <tfoot>
            <tr>
                <th>
                    <input type="text" name="operationId" id="operationId"
                           value="操作ID" class="search_init"/>
                </th>
                <th>
                    <input type="text" name="operationName" id="operationName"
                           value="操作名称" class="search_init"/>
                </th>
                <th>
                    <input type="text" name="serviceId" id="serviceId" value="服务ID"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="serviceName" id="serviceName"
                           value="服务名称" class="search_init"/>
                </th>
                <th>
                    <input type="text" name="version" id="version" value="开发版本"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="auditState" id="auditState" value="审核状态"
                           class="search_init"/>
                </th>
            </tr>
            </tfoot>
        </table>
    </div>
</div>
</body>
</html>




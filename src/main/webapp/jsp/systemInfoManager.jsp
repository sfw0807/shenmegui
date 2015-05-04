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
<!DOCTYPE html >
<html>
<head>
    <title>接入系统管理</title>

    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="<%=ctx %>/css/index.css"/>
    <script src="<%=ctx %>/js/layout.js" type="text/javascript"></script>
    <script src="<%=ctx %>/js/systemInfoManager.js" type="text/javascript"></script>
    <script src="<%=ctx %>/js/systemInfo.js" type="text/javascript"></script>
    <script src="<%=ctx %>/js/json/json2.js" type="text/javascript"></script>
    <style>
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
            *height: 1.7em;
            *top: 0.1em;
        }

        .ui-combobox-input {
            margin: 0;
            padding: 0.3em;
        }

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

        #dialog-form label input select {
            display: block;
        }

        #dialog-form select {
            margin-bottom: 12px;
            width: 95%;
            padding: .4em;
        }

        input.text {
            margin-bottom: 12px;
            width: 95%;
            padding: .4em;
        }

        fieldset {
            padding: 0;
            border: 0;
            margin-top: 25px;
        }

        .ui-dialog .ui-state-error {
            padding: .3em;
        }

        .validateTips {
            border: 0px solid transparent;
            padding: 0.3em;
        }
    </style>
</head>
<body>
<div id="tabs" style="width: 100%">
    <ul>
        <li id='tab0'>
            <a href='#tabs-0'>接入系统管理</a>
        </li>
    </ul>

    <div id="tabs-0">
        <div class="ui-widget-header" style="margin-bottom: 0.5em;padding:0.5em;">
            <button id="add">新增</button>
            <button id="del">删除</button>
            <button id="modify">修改</button>
        </div>
        <table cellpadding="0" cellspacing="0" border="0" class="display"
               id="systemInfoTable">
            <tfoot>
            <tr>
                <th>
                    <input type="text" name="systemId" id="systemId" value="系统Id"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="systemAb" id="systemAb" value="系统简称"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="systemName" id="systemName" value="系统名称"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name=remark id="remark" value="系统描述"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name=firstPublishDate id="firstPublishDate" value="首次上线日期(提供方)"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name=secondPublishDate id="secondPublishDate" value="首次上线日期(调用方)"
                           class="search_init"/>
                </th>
            </tr>
            </tfoot>
        </table>
    </div>
</div>
</body>
</html>




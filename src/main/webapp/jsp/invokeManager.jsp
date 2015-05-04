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
    <title>调用关系管理</title>
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <script src="<%=ctx%>/js/layout.js" type="text/javascript"></script>
    <script src="<%=ctx%>/js/invokeManager.js" type="text/javascript"></script>
    <script src="<%=ctx%>/js/invoke.js" type="text/javascript"></script>
    <script src="<%=ctx%>/js/json/json2.js" type="text/javascript"></script>
    <style>
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
            <a href='#tabs-0'>调用关系管理</a>
        </li>
    </ul>

    <div id="tabs-0">
        <div class="ui-widget-header" style="margin-bottom:0.5em;padding:0.2em;">
            <input type='button' id='del' value='删除'></input>
            <input type='button' id='addConsumer' value='新增调用方'></input>
            <input type='button' id='modifyState' value='修改状态'></input>
        </div>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="invokeTable">
            <tfoot>
            <tr>
                <th>
                    <input type="text" name="operationInfo" id="operationInfo" value="操作ID/操作名称"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="serviceInfo" id="serviceInfo" value="服务ID/服务名称"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="interfaceInfo" id="interfaceInfo" value="接口ID/接口名称"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="consumeSysInfo" id="consumeSysInfo" value="源系统简称/名称"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="passbySysInfo" id="passbySysInfo" value="调用方系统简称/名称"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="provideSysInfo" id="provideSysInfo" value="提供方系统简称/名称"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="provideMsgType" id="provideMsgType" value="提供方报文类型"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="consumeMsgType" id="consumeMsgType" value="调用方报文类型"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="direction" id="direction" value="接口方向"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="through" id="through" value="是否穿透"
                           class="search_init"/>
                </th>

                <th>
                    <input type="text" name="state" id="state" value="版本状态"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="onlineVersion" id="onlineVersion" value="上线版本"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="onlineDate" id="onlineDate" value="上线日期"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="remark" id="remark" value="备注"
                           class="search_init"/>
                </th>
            </tr>
            </tfoot>
        </table>
    </div>
</div>
</body>
</html>


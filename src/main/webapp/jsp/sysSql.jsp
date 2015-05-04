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
    <title>系统级别sql导出</title>
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">

    <link rel="stylesheet" href="<%=ctx %>/css/index.css"/>
    <script src="<%=ctx %>/js/layout.js" type="text/javascript"></script>
    <script src="<%=ctx %>/js/json/json2.js" type="text/javascript"></script>
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
    <script type="text/javascript" language="JavaScript">
        $(function () {
            $('#tabs').tabs();
            //所有系统信息的下拉框
            function initSystemInfo() {
                $.ajax({
                    url: '<%=path%>/serviceDevInfo/getAllSystem',
                    type: 'GET',
                    success: function (result) {
                        initSelect(result);
                    }
                });
            }

            function initSelect(result) {
                $('#psys').append("<option value='0' selected='true'>请选择系统</option>");
                $('#csys').append("<option value='0' selected='true'>请选择系统</option>");
                for (var i = 0; i < result.length; i++)
                    $('#psys').append("<option value='" + result[i].systemId + "'>" + result[i].systemAb + ":" + result[i].systemName + "</option>");
                for (var i = 0; i < result.length; i++)
                    $('#csys').append("<option value='" + result[i].systemId + "'>" + result[i].systemAb + ":" + result[i].systemName + "</option>");
            }

            initSystemInfo();
            $("#exportSysSql").button().click(function () {
                var systype = $("input:radio:checked").val();
                var psysid = $("#psys").val();
                var psysname = $("#psys :selected").text();
                var psysab = psysname.split(":")[0];
                psysname = psysname.split(":")[1];
                var padapter = $("#padapter").val();
                var pmsg = $("#pmsg").val();
                var csysid = $("#csys").val();
                var csysname = $("#csys :selected").text();
                var csysab = csysname.split(":")[0];
                csysname = csysname.split(":")[1];
                if (systype == "0") {
                    if (psysid == "0") {
                        alert("请选择提供方系统");
                        return;
                    } else if (padapter == "0") {
                        alert("请选择提供方协议类型");
                        return;
                    } else if (pmsg == "0") {
                        alert("请选择提供方报文类型");
                        return;
                    }
                } else if (systype == "1") {
                    if (csysid == "0") {
                        alert("请选择调用方系统");
                        return;
                    }
                } else {
                    if (psysid == "0") {
                        alert("请选择提供方系统");
                        return;
                    } else if (padapter == "0") {
                        alert("请选择提供方协议类型");
                        return;
                    } else if (pmsg == "0") {
                        alert("请选择提供方报文类型");
                        return;
                    }
                    if (csysid == "0") {
                        alert("请选择调用方系统");
                        return;
                    }
                }
                var param = systype + "|" + psysid + "|" + psysab + "|" + psysname + "|" + padapter + "|" + pmsg + "|" + csysid + "|" + csysab + "|" + csysname;
                $.fileDownload(encodeURI(encodeURI("../export/syssql/" + param)), {});
            });
        })
    </script>
</head>

<div id="tabs" style="width:100%">
    <ul>
        <li id='tab0'><a href='#tabs-0'>系统级别sql导出</a></li>
    </ul>
    <div id="tabs-0">
        <div class="ui-widget-header" style="margin-bottom: 0.5em;padding: 0.2em;">
            <input type="button" value="导出" id="exportSysSql"/>
        </div>
        新增系统类型:<input type="radio" name="addtype" value="0" checked/>提供方<input type="radio" name="addtype" value="1"/>调用方<input
            type="radio" name="addtype" value="2"/>提供方和调用方<br>
        提供方系统名称:<select id="psys"></select><br>
        提供方协议类型:<select id="padapter">
        <option value="0">请选择</option>
        <option value="IbmQServiceConnector">IbmQServiceConnector</option>
        <option value="IbmQChannelConnector">IbmQChannelConnector</option>
        <option value="UDPServiceConnector">UDPServiceConnector</option>
        <option value="UDPChannelConnector">UDPChannelConnector</option>
        <option value="CICSConnector">CICSConnector</option>
        <option value="IDPWSServiceConnector">IDPWSServiceConnector</option>
        <option value="EJBChannelConnector">EJBChannelConnector</option>
        <option value="EJBServiceConnector">EJBServiceConnector</option>
        <option value="HTTPChannelConnector">HTTPChannelConnector</option>
        <option value="HTTPServiceConnector">HTTPServiceConnector</option>
        <option value="JMSChannelConnector">JMSChannelConnector</option>
        <option value="JMSServiceConnector">JMSServiceConnector</option>
        <option value="RESTChannelConnector">RESTChannelConnector</option>
        <option value="RESTServiceConnector">RESTServiceConnector</option>
        <option value="TCPChannelConnector">TCPChannelConnector</option>
        <option value="TCPServiceConnector">TCPServiceConnector</option>
        <option value="WSChannelConnector">WSChannelConnector</option>
        <option value="WSServiceConnector">WSServiceConnector</option>
    </select><br>
        提供方报文类型:
        <select id="pmsg">
            <option value="0">请选择</option>
            <option value="SOAP">SOAP</option>
            <option value="other">其他</option>
        </select><br>
        调用方系统名称:<select id="csys"></select>
    </div>
</div>
</body>
</html>


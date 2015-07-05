<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>列表页</title>
    <link rel="stylesheet" type="text/css"
          href="/resources/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css"
          href="/resources/themes/icon.css">
    <link href="/resources/css/css.css" rel="stylesheet" type="text/css">
</head>

<body>
<fieldset>
    <table border="0" cellspacing="0" cellpadding="0">
        <tr>
            <th>服务代码</th>
            <td><input class="easyui-textbox" type="text" name="1" id="1" value="${service.serviceId }"
                       disabled="disabled">
            </td>
            <th>服务名称</th>
            <td><input class="easyui-textbox" type="text" name="2" id="2" value="${service.serviceName }"
                       disabled="disabled">
            </td>
            <th>场景号</th>
            <td><input class="easyui-textbox" type="text" name="3" id="3" value="${operation.operationId }"
                       disabled="disabled">
            </td>
            <th>场景名称</th>
            <td><input class="easyui-textbox" type="text" name="4" id="4" value="${operation.operationName }"
                       disabled="disabled">
            </td>
        </tr>
    </table>


</fieldset>
<table id="sla" style="height:370px; width:auto;" title="服务SLA"
       data-options="rownumbers:true,singleSelect:true,url:slaUrl,method:'get',toolbar:slatoolbar,pagination:true,
				pageSize:10">
    <thead>
    <tr>
        <th field="slaId" width="100" editor="text"
            data-options="hidden:true">ID
        </th>
        <th field="slaName" width="100" editor="text" align="center">SLA指标</th>
        <th field="slaValue" width="150" align="center" editor="text">取值范围</th>
        <th field="slaDesc" width="150" align="center" editor="text">描
            述
        </th>
        <th field="slaRemark" width="150" align="center" editor="text">备
            注
        </th>
    </tr>
    </thead>
</table>

<div id="w" class="easyui-window" title=""
     data-options="modal:true,closed:true,iconCls:'icon-add'"
     style="width:500px;height:200px;padding:10px;"></div>
<script type="text/javascript" src="/resources/js/jquery.min.js"></script>
<script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/resources/js/jquery.edatagrid.js"></script>
<script type="text/javascript" src="/resources/js/ui.js"></script>
<script type="text/javascript" src="/plugin/json/json2.js"></script>
<script type="text/javascript" src="/assets/sla/js/slaManager.js"></script>
<script type="text/javascript" src="<%=basePath %>/assets/sla/js/slaPage.js"></script>
</body>
</html>

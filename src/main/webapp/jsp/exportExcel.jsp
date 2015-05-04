<%@ page language="java" pageEncoding="UTF-8" %>
<%
  String path = request.getContextPath();
  String ctx = request.getScheme() + "://"
          + request.getServerName() + ":" + request.getServerPort()
          + path + "/";
%>
<%@ include file="/jsp/includes/header.jsp" %>
<%@ include file="/jsp/includes/jquery.jsp" %>
<%@ include file="/jsp/includes/jquery-ui.jsp" %>
<%@ include file="/jsp/includes/jquery-plugins.jsp" %>
<%@ include file="/jsp/includes/datatable.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <title>映射文档导出</title>

  <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
  <meta http-equiv="description" content="this is my page">
  <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  <link rel="stylesheet" href="<%=ctx%>/css/index.css" />
  <link rel="stylesheet" href="<%=ctx%>/css/demos.css" />
  <script src="<%=ctx%>/assets/interface/js/interfaceManager.js" type="text/javascript"></script>
  <script src="<%=ctx%>/js/export-excel.js" type="text/javascript"></script>
  <script src="<%=ctx%>/js/jquery.fileDownload.js" type="text/javascript"></script>
  <script src="<%=ctx%>/js/layout.js" type="text/javascript"></script>

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
      *height: 2em;
      *top: 0.1em;
    }
    .ui-combobox-input {
      margin: 0;
      padding: 0.3em;
      weight:15em;
      height:2em;
    }
  </style>
  <script language="javascript">


  </script>


</head>

<body>

<div id="tabs" style="width:100%">
  <ul>
    <li id='tab0'><a  href='#tabs-0'>按接口导出</a></li>
    <li id='tab1'><a  href='#tabs-1'>按系统导出</a></li>
  </ul>
  <div id="tabs-0">
    <div id="service_main">
      <div class="ui-widget-header" style="margin-bottom:0.5em;padding:0.2em;width:100%">
        导出条件
      </div>
      <div class="ui-widget-header" style="margin-bottom:0.5em;padding:0.2em;width:100%">
        <table width="100%" cellpadding="0" cellspacing="0" border="0" class="display" id="serviceTable">
          <tr width="60%">
            <td width="20%">
              <label id='doctypeLabel'>文档类型:</label>&nbsp;
              <select id="doctype" style='width:168px'>
                <option value="MAPPING" >映射文档</option>
                <option value="INTERFACE" >接口文档</option>
                <option value="SERVICE" >服务文档</option>
              </select>
            </td>
            <td width="21%">&nbsp;&nbsp;
              <label id='operateIdLabel'>操作ID:&nbsp;</label><input type="text" id="operateId" value="" class="search_init" style='width:168px'/>
            </td>
            <td width="21%">
              <label id='ecodeLabel'>交易码:&nbsp;&nbsp;&nbsp;&nbsp;
              </label><input type="text" id="ecode" value="" class="search_init" style='width:168px'/>
            </td>
            <td width="15%">
              <input  type = "button" value="导出" id="export" />
              <input  type = "button" value="重置" id="reset" />
            </td>
            <td width="14%"></td>
            <td width="15%"></td>
          </tr>
        </table>
      </div>
      <table cellpadding="0" cellspacing="0" border="0" class="display" id="interfaceTable">
        <tfoot>
        <tr>
          <th><input type="text" name="ecode" value="接口ID" class="search_init"/></th>
          <th><input type="text" name="interfaceName" value="接口名称" class="search_init"/></th>
          <th><input type="text" name="serviceId" value="服务ID" class="search_init"/></th>
          <th><input type="text" name="serviceName" value="服务名称" class="search_init"/></th>
          <th><input type="text" name="operationId" value="操作ID" class="search_init"/></th>
          <th><input type="text" name="operationName" value="操作名称" class="search_init"/></th>
          <th><input type="text" name="sourceSys" value="源系统" class="search_init"/></th>
          <th><input type="text" name="consumeSys" value="调用方" class="search_init"/></th>
          <th><input type="text" name="providerSysAb" value="提供系统" class="search_init"/></th>
          <th><input type="text" name="versionSt" value="状态" class="search_init"/></th>
          <th><input type="text" name="productNo" value="上线版本" class="search_init"/></th>
          <th><input type="text" name="versionNo" value="开发版本" class="search_init"/></th>
          <th><input type="text" name="onLineDate" value="上线日期" class="search_init"/></th>

        </tr>
        </tfoot>
      </table>
    </div>
  </div>
  <div id="tabs-1">
    <div id="service_main">
      <div class="ui-widget-header" style="margin-bottom:0.5em;padding:0.2em;width:100%">
        导出条件
      </div>
      <div class="ui-widget-header" style="margin-bottom:0.5em;padding:0.2em;width:100%">
        <table width="100%" cellpadding="0" cellspacing="0" border="0" class="display" id="serviceTable">
          <tr width="60%">
            <td width="20%">
              <!--
                  <label>导出类型:</label>
                  <select id="exportType" style='width:168px'>
                      <option value="multi" >导出选中接口</option>
                      <option value="system" >按系统导出</option>
                  </select>
               -->
              <label id='systemTypeLabel'>接口类型:</label>
              <select id="systemType" style='width:168px'>
                <option value="consumer" >调用方</option>
                <option value="provider" >提供方</option>
                <option value="cp" >调用方或提供方</option>
              </select>
            </td>
            <td width="21%">
              <div class="demo">
                <div class="ui-widget">
                  <label id='systemLabel'>系统:</label>&nbsp;&nbsp;
                  <select id="system" style='width:168px'>
                    <option value='请选择' selected='true'>---请选择---</option>
                  </select>
                </div>
              </div>						    </td>
            <td width="21%">
              <label id='doctypeLabel'>文档类型:</label>&nbsp;
              <select id="doctype" style='width:168px'>
                <option value="MAPPING" >映射文档</option>
                <option value="INTERFACE" >接口文档</option>
                <option value="SERVICE" >服务文档</option>
              </select>
            </td>
            <td width="15%">
              <input  type = "button" value="导出" id="export1" />
              <input  type = "button" value="重置" id="reset1" />
            </td>
            <td width="14%"></td>
            <td width="15%"></td>
          </tr>
        </table>
      </div>
    </div>
</body>
</html>


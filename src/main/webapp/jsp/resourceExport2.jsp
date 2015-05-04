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

<html>
<head>
  <title>资源导出</title>

  <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
  <meta http-equiv="description" content="this is my page">
  <meta http-equiv="content-type" content="text/html; charset=UTF-8">

  <link rel="stylesheet" href="<%=ctx %>/css/index.css"/>
  <script src="<%=ctx %>/js/layout.js" type="text/javascript"></script>
  <script src="<%=ctx %>/js/svcAsmRelateManager.js" type="text/javascript"></script>
  <script src="<%=ctx %>/js/configExport.js" type="text/javascript"></script>
  <script src="<%=ctx %>/js/json/json2.js" type="text/javascript"></script>

</head>
<body>
<div id="tabs" style="width: 100%">
  <ul>
    <li id='tab0'>资源导出</a>
    </li>
  </ul>
  <div id="tabs-0">
    <div class="ui-widget-header" style="margin-bottom:0.5em;padding:0.2em;">
      <input type='button' id='exportConfig' value='导出配置'></input>
      <input type='button' id='exportWSDL' value='导出WSDL'></input>
      <input type='button' id='exportMapFile' value='导出MapFile'></input>
      <input type='button' id='exportMdt' value='导出元数据'></input>
      <input type='button' id='batchExportConfig' value='批量导出配置'></input>
    </div>
    <div class="ui-widget-header" style="margin-bottom:0.5em;padding:0.2em;">
      <input type='button' id='checkAll' value='全选'></input>
      <input type='button' id='toggleAll' value='反选'></input>
    </div>
    <table cellpadding="0" cellspacing="0" border="0" class="display"
           id="configExportTable">
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
          <input type="text" name="version" id="version" value="开发版本"
                 class="search_init"/>
        </th>
        <th>
          <input type="text" name="publishVersion" id="publishVersion" value="上线版本"
                 class="search_init"/>
        </th>
      </tr>
      </tfoot>
    </table>
  </div>
</div>
</body>
</html>



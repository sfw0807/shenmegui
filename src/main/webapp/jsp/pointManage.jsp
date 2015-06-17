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
  <title>节点管理</title>

  <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
  <meta http-equiv="description" content="this is my page">
  <meta http-equiv="content-type" content="text/html; charset=UTF-8">

  <link rel="stylesheet" href="<%=ctx %>/css/index.css"/>
  <script src="<%=ctx %>/js/layout.js" type="text/javascript"></script>
  <script src="<%=ctx %>/js/conFilePathManager.js" type="text/javascript"></script>
  <script src="<%=ctx %>/js/configPointManager.js" type="text/javascript"></script>
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
      *height: 1.5em;
      *top: 0.1em;
    }

    .ui-combobox-input {
      margin: 0;
      padding: 0.3em;
      *weight: 15em
    }
  </style>

</head>
<body>
<div id="tabs" style="width: 100%">
  <ul>
    <li id='tab0'>节点管理</a>
    </li>
  </ul>

  <div id="tabs-0">
    <div class="ui-widget-header" style="margin-bottom:0.5em;padding:0.2em;">
      <input type='button' id='publish' value='发布'></input>
    </div>
    <div class="ui-widget-header" style="margin-bottom:0.5em;padding:0.2em;">
      <input type='button' id='checkAll' value='全选'></input>
      <input type='button' id='toggleAll' value='反选'></input>
    </div>
    <table cellpadding="0" cellspacing="0" border="0" class="display"
           id="configExportManagerTable">
      <tfoot>
      <tr>
        <th>
          <input type="text" name="name" id="name" value="服务器名称"
                 class="search_init"/>
        </th>
        <th>
          <input type="text" name="filePath" id="filePath" value="文件路径"
                 class="search_init"/>
        </th>
        <th>
          <input type="text" name="username" id="username" value="用户名"
                 class="search_init"/>
        </th>
        <th>
          <input type="text" name="password" id="password" value="密码"
                 class="search_init"/>
        </th>
      </tr>
      </tfoot>
    </table>
  </div>
</div>
</body>
</html>



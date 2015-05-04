<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
  String path = request.getContextPath();
  String ctx = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ include file="/jsp/includes/jquery.jsp" %>
<%@ include file="/jsp/includes/ligerUI.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <title></title>
  <%--<link href="<%=path%>/js/ligerUI/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet"--%>
        <%--type="text/css"/>--%>
  <%--<link href="<%=path%>/js/ligerUI/lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet"/>--%>
  <link href="<%=path%>/assets/scene/css/sda.css" rel="stylesheet"/>
  <%--<script src="<%=path%>/js/jquery-1.8.2.js"></script>--%>
  <%--<script src="<%=path%>/js/ligerUI/lib/ligerUI/js/core/base.js" type="text/javascript"></script>--%>
  <%--<script src="<%=path%>/js/ligerUI/lib/ligerUI/js/plugins/ligerTextBox.js" type="text/javascript"></script>--%>
  <%--<script src="<%=path%>/js/ligerUI/lib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>--%>
  <%--<script src="<%=path%>/js/ligerUI/lib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>--%>
  <%--<script src="<%=path%>/js/ligerUI/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>--%>
  <%--<script src="<%=path%>/js/ligerUI/lib/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>--%>
  <%--<script src="<%=path%>/js/ligerUI/lib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>--%>
  <script src="<%=path%>/assets/scene/js/sdaManager.js"></script>
  <script src="<%=path%>/assets/scene/js/sda.js"></script>

</head>
<body style="padding: 4px">
<div>

  <div class="l-clear">
  </div>
</div>
<div id="maingrid">
</div>
<div>
</div>
</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">

    <title>My JSP 'sdaHisPage.jsp' starting page</title>
  </head>

  <body>
  	<table title="服务场景" class="easyui-treegrid" id="tg" style=" width:auto;"
    			data-options="
    				iconCls: 'icon-ok',
    				rownumbers: true,
    				fitColumns: false,
    				animate: true,
    				collapsible: true,
    				fitColumns: true,
    				url: '/operation/getByMetadataId/${param.metadataId}',
    				method: 'get',
    				idField: 'id',
    				treeField: 'text'
    				"
                    >
    		<thead>
    			<tr>
    				<th data-options="field:'text',width:300,editor:'text'">名称</th>
    				<th data-options="field:'append1',width:250,align:'right',editor:'text'">描述</th>
    			</tr>
    		</thead>
    	</table>
  </body>
</html>

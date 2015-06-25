<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'MyJsp.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>

		<form class="formui" id="metadataForm" action="/metadata/add" method="post">
		<table border="0" cellspacing="0" cellpadding="0">
			<input type="hidden" name="status" value="${entity.status }" />  
		  <tr>
		    <th>元数据名称</th>
		    <td><input class="easyui-textbox" type="text" name="metadataId" value="${entity.metadataId }" data-options="required:true, readonly:true"></td>
		  </tr>
		  <tr>
		    <th>中文名称</th>
		    <td><input class="easyui-textbox" type="text" name="chineseName"  value="${entity.chineseName }"  ></td>
		  </tr>
		  <tr>
		    <th>英文名称</th>
		    <td><input class="easyui-textbox" type="text" name="metadataName" value="${entity.metadataName }" ></td>
		  </tr>
		  <tr>
		    <th>类别词</th>
		    <td><input type="text" name="categoryWordId" id="categoryWordId"
					class="easyui-combobox"
					 value="${entity.categoryWordId }"
					data-options="
						url:'/metadata/categoryWord',
				 		 method:'get',
				 		 valueField: 'id',
				 		 textField: 'chineseWord',
				 		 onChange:function(newValue, oldValue){
							this.value=newValue;
						}
					"
					style="width: 100px; " /></td>
		  </tr>
		  <tr>
		    <th>数据格式</th>
		    <td><input class="easyui-textbox" type="text" name="type" value="${entity.type }" ></td>
		  </tr>
		  <tr>
		    <th>业务定义</th>
		    <td><input class="easyui-textbox" type="text" name="bussDefine" value="${entity.bussDefine }" ></td>
		  </tr>
		  <tr>
		    <th>业务规则</th>
		    <td><input class="easyui-textbox" type="text" name="bussRule" value="${entity.bussRule }" ></td>
		  </tr>
		  <tr>
		    <th>数据来源</th>
		    <td><input class="easyui-textbox" type="text" name="dataSource" value="${entity.dataSource }" ></td>
		  </tr>
		  
		  <tr>
		    <td>&nbsp;</td>
		    <td class="win-bbar">
		    	<a href="#" class="easyui-linkbutton"  iconCls="icon-cancel" onClick="$('#w').window('close')">取消</a>
		    	<a href="#" onclick="save('metadataForm')" class="easyui-linkbutton"  iconCls="icon-save">保存</a>
		    </td>
		  </tr>
		</table>
		</form>
 </body>
</html>



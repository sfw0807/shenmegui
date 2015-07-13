<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%
	String mid = request.getParameter("mid");
%>
<%
	if(mid.equals("1")){
%>
<ul id="menu-tree">
  <li><a href="javascript:;" mid="1.1">版本管理</a>
    <ul>
      <li><a href="javascript:;" class="openable" mid="1.2">版本发布</a></li>
      <li><a href="javascript:;" class="openable" mid="1.3">版本历史</a></li>
      <!--
      <li><a href="javascript:;" class="openable" mid="1.4">版本公告</a></li>
       -->
      <li><a href="javascript:;" class="openable" mid="1.5">基线制作</a></li>
      <li><a href="javascript:;" class="openable" mid="1.6">基线历史</a></li>
    </ul>
  </li>
  <li><a href="javascript:;" class="openable"  mid="2.1">流程管理</a>
    <ul>
      <li><a href="javascript:;" class="openable"  mid="2.2">工作流程管理</a></li>
      <li><a href="javascript:;" class="openable"  mid="2.3">任务管理</a></li>
      <li><a href="javascript:;" class="openable"  mid="2.4">我的任务</a></li>
    </ul>
  </li><li><a href="javascript:;" class="openable"  mid="9.1">字段映射</a>
    <ul>
        <li><a href="javascript:;" class="openable"  mid="9.2">导入</a></li>
        <li><a href="javascript:;" class="openable"  mid="9.3">导出</a></li>
    </ul>
</li>
  <li><a href="javascript:;" class="openable"  mid="4.1">用户管理</a>
    <ul>
      <li><a href="javascript:;" class="openable"  mid="4.2">用户维护</a></li>
</ul>
  </li>
</ul>
<%
	}
	
	if(mid.equals("2")){
%>
<ul id="menu-tree">
  <li><a href="javascript:;" class="openable"  mid="3.1">元数据管理</a>
    <ul>
      <li><a href="javascript:;" class="openable" mid="3.2">英文单词及缩写管理</a></li>
      <li><a href="javascript:;" class="openable" mid="3.3">类别词管理</a></li>
      <li><a href="javascript:;" class="openable" mid="3.4">元数据管理</a></li>
      <li><a href="javascript:;" class="openable" mid="3.5">数据类型映射</a></li>
    </ul>
  </li>
  <li><a href="javascript:;" class="openable"  mid="3.6">公共代码管理</a></li>
</ul>
<%
	}
	
	if(mid.equals("3")){
%>
 <div class="tree-filter">
 		<input class="easyui-searchbox" id="mxsysadmintreefilter" style="width:100%">

 </div>

 <ul class="easyui-tree mxsysadmintree" data-options="url:'/interfaceHead/getAll',method:'get',animate:true"></ul>

<%
	}
	
	if(mid.equals("4")){
%>
 <div class="tree-filter">
 		<input class="easyui-searchbox" id="servicetreefilter" style="width:100%">

 </div>
  <ul class="easyui-tree mxservicetree" data-options="url:'/service/getTree',method:'get',animate:true"></ul>

<%
	}
	
	if(mid.equals("5")){
%>
 <div class="tree-filter">
 		<input class="easyui-searchbox" id="mxsysadmintreefilter" style="width:100%">

 </div>

 <ul class="easyui-tree mslinktree" data-options="url:'/system/getTree',method:'get',animate:true"></ul>
<%
	}
	if(mid.equals("6")){
%>
<div class="tree-filter">
 		<input class="easyui-searchbox" id="mxinterfacetreefilter" style="width:100%">

 </div>

 <ul class="easyui-tree msinterfacetree" data-options="url:'/interface/getLeftTree',method:'get',animate:true"></ul>
<%}%>
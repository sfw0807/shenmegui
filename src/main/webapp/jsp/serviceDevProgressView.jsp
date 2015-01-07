<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>服务开发统计信息查询</title>
	
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <link rel="stylesheet" type="text/css" href="<%= path%>/js/multi-select/css/jquery.multiselect.css" />
	<link rel="stylesheet" type="text/css" href="<%= path%>/js/multi-select/css/jquery.multiselect.filter.css" />
	<link rel="stylesheet" type="text/css" href="<%= path%>/js/jquery-ui/css/redmond/jquery.ui.theme.css" />
	<link rel="stylesheet" type="text/css" href="<%= path%>/js/jquery-ui/css/redmond/jquery-ui.css" />
	<link rel="stylesheet" href="<%= path%>/themes/smoothness/jquery-ui-1.8.4.custom.css" />
	<link rel="stylesheet" href="<%= path%>/css/index.css" />
	<link rel="stylesheet" type="text/css" href="<%= path%>/js/jquery.datatables/css/jquery.dataTables.css" />
	<script src="<%= path%>/js/jquery-ui/js/jquery-1.10.2.js"></script>
	<script src="<%= path%>/js/jquery.datatables/js/jquery.dataTables.js" type="text/javascript"></script>
	<script src="<%= path%>/js/systemManager.js" type="text/javascript"></script>
	<script src="<%= path%>/js/layout.js" type="text/javascript"></script>
	<script src="<%= path%>/js/service-dev-progress.js" type="text/javascript"></script>
    <script src="<%= path%>/js/jquery.ui.widget.js"></script>
    <script src="<%= path%>/js/jquery.multiselect.js" type="text/javascript"></script>
    <script src="<%= path%>/js/jquery-ui.min.js" type="text/javascript"></script>
	<script src="<%= path%>/js/jquery.multiselect.filter.js" type="text/javascript"></script>
	<script src="<%= path%>/js/jquery.fileDownload.js" type="text/javascript"></script>
	<script src="<%= path%>/js/json/json2.js" type="text/javascript"></script>
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
	    
  </head>
  
  <body>
  	<input type="hidden" id="selectedAB"></input>
    <div class="ui-widget-header" style="margin-bottom:0.5em;padding:0.2em;">
    	<select id='sys_select' title='Basic example' multiple='multiple' name='example-basic' size='5'>
		</select>
		<input type='button' id='query' value='查询'></input>
		<input type='button' id='reset' value='重置'></input>
		<input type='button' id='export' value='导出全部'></input>
		<input type='button' id='export1' value='导出查询结果'></input>
    </div>
    <!-- 
	<table style="width:50%">
		<tr>
			
		</tr>
		<tr>
		</tr>
		<tr>
		</tr>
	</table>
	 -->
	 
	<table cellpadding="0" cellspacing="0" border="0" class="display" id="serviceDevProgressTable">
	  	<tfoot>
             <tr>
                <th><input type="text" name="systemName" value="系统名称" class="search_init"/></th>
                <th><input type="text" name="systemAb" value="系统英文名称" class="search_init"/></th>
                <th><input type="text" name="underDefine" value="服务定义" class="search_init"/></th>
                <th><input type="text" name="dev" value="开发" class="search_init"/></th>
                <th><input type="text" name="unitTest" value="联调测试" class="search_init"/></th>
                <th><input type="text" name="sitTest" value="sit测试" class="search_init"/></th>
                <th><input type="text" name="uatTest" value="uat测试" class="search_init"/></th>
                <th><input type="text" name="productTest" value="投产验证" class="search_init"/></th>
                <th><input type="text" name="totalNum" value="总计" class="search_init"/></th>
             </tr>
          </tfoot>
	</table>
  </body>
</html>





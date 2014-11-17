<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>opertationInfoById.jsp</title>
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
		<link rel="stylesheet" href="<%=path%>/themes/smoothness/jquery-ui-1.8.4.custom.css" />
		<link rel="stylesheet" href="<%=path%>/js/jquery-ui/development-bundle/themes/base/jquery.ui.all.css" />
		<link rel="stylesheet" href="<%=path%>/css/index.css" />
		
		<script src="<%=path%>/js/jquery-ui/js/jquery-1.10.2.js"></script>
		<script src="<%=path%>/js/jquery-1.8.2.js"></script>
		<script src="<%=path%>/js/jquery-ui-tabs.js"></script>
		<script src='<%=path%>/js/jquery.datatables/js/jquery.dataTables.js' type="text/javascript"></script>
		<link rel='stylesheet' type='text/css' href='<%=path%>/js/jquery.datatables/css/jquery.dataTables.css' />
		
		<script src="<%=path%>/js/jquery.ui.core.js"></script>
		<script src="<%=path%>/js/jquery.ui.widget.js"></script>
		<script src="<%=path%>/js/jquery.ui.datepicker.js"></script>
		<script src="<%=path%>/js/operationHistoryManager.js" type="text/javascript"></script>
		<script src="<%=path%>/js/layout.js" type="text/javascript"></script>
		<script src="<%=path%>/js/operationHistory.js" type="text/javascript"></script>
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
  
  <div id="tabs" style="width:100%">
			<ul>
				<li id='tab0'><a  href='#tabs-0'>基本定义</a></li>
				<li id='tab1'><a  href='#tabs-1'>SDA</a></li>
				<li id='tab2'><a  href='#tabs-2'>SLA</a></li>
				<li id='tab3'><a  href='#tabs-3'>OLA</a></li>
			</ul>
			<div id="tabs-0"><!--
				<div class="ui-widget-header" style="margin-bottom: 0.5em;padding: 0.2em;">
					<input  type = "button" value="编辑" id="editOperationDef" />
					<input  type = "button" value="保存" id="saveOperationDef" />
				</div>
				--><table style="width:50%" id="operationHistoryTable">
					<tr>
						<td><label style="width:16%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;操作ID:</label><input type="text" id="operationId" value="" style="width:80%" disabled="true"/></td>
						<td><label style="width:19%">&nbsp;&nbsp;&nbsp;服务ID:</label><input type="text" id="serviceId" value="" style="width:80%"  disabled="true"/></td>
					</tr>	
					<tr>
						<td><label style="width:16%">&nbsp;&nbsp; 操作名称:</label><input type="text" id="operationName" value="" style="width:80%" disabled="true"/></td>
						<td>
						<label style="width:19%">&nbsp;&nbsp; 操作状态:</label>
						<!--<input type="text" id="state" value="" style="width:80%"  disabled="true"/>-->
						<select id="state" class="text ui-widget-content ui-corner-all" disabled="true">
                             <option value="服务定义">服务定义</option>
                             <option value="开发" >开发</option>
                             <option value="联调测试" >联调测试</option>
                             <option value="sit测试" >sit测试</option>
                             <option value="uat测试" >uat测试</option>
                             <option value="投产验证" >投产验证</option>
                             <option value="上线" >上线</option>
                         </select>						
						</td>
					</tr>	
					<tr>
						<td><label style="width:16%"> &nbsp;&nbsp; 操作描述:</label><input type="text" id="operationRemark" value="" style="width:80%;position:relative;z-index:10000" disabled="true"/></td>
					</tr>
					<tr>
						<td><label style="width:16%">开发版本号:</label><input type="text" id="version" value="" style="width:80%" disabled="true"/></td><!--
						<td><label style="width:19%">上线版本号:</label><input type="text" id="publishVersion" value="" style="width:80%"  disabled="true"/></td>
					--></tr><!--	
		
					<tr>
						<td><label style="width:16%"> &nbsp;&nbsp; 上线时间:</label><input type="text" id="publishDate" value="" style="width:80%;position:relative;z-index:10000" disabled="true"/></td>
					</tr>
				--></table>
			</div>
			
			<div id="tabs-1"><!--
				<div class="ui-widget-header" style="margin-bottom: 0.5em;padding: 0.2em;">
					<input  type = "button" value="编辑节点" id="editChildOperationSDA" />
					<input  type = "button" value="添加子节点" id="addChildOperationSDA" />
					<input  type = "button" value="添加兄弟节点" id="addBrotherOperationSDA" />
					<input  type = "button" value="删除节点" id="deleteOperationSDA" />
					<input  type = "button" value="保存" id="saveOperationSDA" />
				</div>			
				--><table cellpadding="0" cellspacing="0" border="0" class="display" id="operationHistorySDATable">
				   <tfoot>
		              <tr>		              	 
		             	 <th><input type="text" id="seq" name="seq" value="序号" class="search_init"/></th>
		                 <th><input type="text" id="structId" name="structId" value="英文名称" class="search_init"/></th>
		                 <th><input type="text" id="metadataId" name="metadataId" value="元数据ID" class="search_init"/></th>
		                 <th><input type="text" id="type" name="type" value="类型" class="search_init"/></th>
		                 <th><input type="text" id="required" name="required" value="是否必输" class="search_init"/></th>
		                 <th><input type="text" id="remark" name="remark" value="备注" class="search_init"/></th>
		              </tr>
		           </tfoot>
				</table>
			</div>
			
			<div id="tabs-2"><!--
				<div class="ui-widget-header" style="margin-bottom: 0.5em;padding: 0.2em;">
					<input  type = "button" value="编辑" id="editOperationSLA" />
					<input  type = "button" value="添加" id="addOperationSLA" />
					<input  type = "button" value="删除" id="deleteOperationSLA" />
					<input  type = "button" value="保存" id="saveOperationSLA" />
				</div>
				--><table cellpadding="0" cellspacing="0" border="0" class="display" id="operationHistorySlaTable">
				   <tfoot>
		              <tr>
		              <!--
		                 <th><input type="text" id="slaName" name="slaName" value="属性ID" class="search_init"/></th>
		                 <th><input type="text" id="slaValue" name="slaValue" value="属性值" class="search_init"/></th>
		                 <th><input type="text" id="slaRemark" name="slaRemark" value="备注" class="search_init"/></th>
		              -->
		              </tr>
		           </tfoot>
				</table>
			</div>
			
			<div id="tabs-3"><!--
				<div class="ui-widget-header" style="margin-bottom: 0.5em;padding: 0.2em;">
					<input  type = "button" value="编辑" id="editOperationOLA" />
					<input  type = "button" value="添加" id="addOperationOLA" />
					<input  type = "button" value="删除" id="deleteOperationOLA" />
					<input  type = "button" value="保存" id="saveOperationOLA" />
				</div>			
				--><table cellpadding="0" cellspacing="0" border="0" class="display" id="operationHistoryOlaTable">
				   <tfoot>
		              <tr>
		              <!--
		                 <th><input type="text" id="olaName" name="olaName" value="属性ID" class="search_init"/></th>
		                 <th><input type="text" id="olaValue" name="olaValue" value="属性值" class="search_init"/></th>
		                 <th><input type="text" id="olaRemark" name="olaRemark" value="备注" class="search_init"/></th>
		              -->
		              </tr>
		           </tfoot>
				</table>
			</div>
			
		</div>
	</body>
</html>


<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>接入系统服务统计表</title>

		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="this is my page">
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">

		<link rel="stylesheet"
			href="<%=path%>/themes/smoothness/jquery-ui-1.8.4.custom.css" />
		<link rel="stylesheet" href="<%=path%>/css/index.css" />
		<link rel="stylesheet" href="<%=path%>/css/demos.css" />
		<link rel="stylesheet"
			href="<%=path%>/js/jquery-ui/development-bundle/themes/base/jquery.ui.all.css" />
		<link rel="stylesheet" href="<%=path%>/css/index.css" />
		<link rel='stylesheet' type='text/css'
			href='<%=path%>/js/jquery.datatables/css/jquery.dataTables.css' />
		<script src="<%=path%>/js/jquery-ui/js/jquery-1.10.2.js"></script>
		<script src="<%=path%>/js/jquery-1.8.2.js"></script>
		<script src="<%=path%>/js/jquery.bgiframe-2.1.2.js"></script>
		<script src="<%=path%>/js/jquery.ui.core.js"></script>
		<script src="<%=path%>/js/jquery.ui.widget.js"></script>
		<script src="<%=path%>/js/jquery.ui.datepicker.js"></script>
		<script src="<%=path%>/js/jquery.ui.core.js"></script>
		<script src="<%=path%>/js/jquery.ui.widget.js"></script>
		<script src="<%=path%>/js/jquery.ui.mouse.js"></script>
		<script src="<%=path%>/js/jquery.ui.button.js"></script>
		<script src="<%=path%>/js/jquery.ui.position.js"></script>
		<script src="<%=path%>/js/jquery.ui.autocomplete.js"></script>
		<script src="<%=path%>/js/jquery.ui.draggable.js"></script>
		<script src="<%=path%>/js/jquery.ui.resizable.js"></script>
		<script src="<%=path%>/js/jquery.ui.dialog.js"></script>
		<script src="<%=path%>/js/jquery.effects.core.js"></script>
		<script src="<%=path%>/js/jquery-ui-tabs.js"></script>
		<script src="<%=path%>/js/combo-box.js"></script>
		<script src="<%=path%>/js/jquery.ui.datepicker.js"></script>
		<script src='<%=path%>/js/jquery.datatables/js/jquery.dataTables.js'
			type="text/javascript"></script>
		<script src="<%=path%>/js/jquery.fileDownload.js"
			type="text/javascript"></script>
		<script src="<%=path%>/js/layout.js" type="text/javascript"></script>
		<script src="<%=path%>/js/invokeInfoManager.js" type="text/javascript"></script>
		<script src="<%=path%>/js/sysInvokeService.js" type="text/javascript"></script>
		<script src="<%=path%>/js/json/json2.js" type="text/javascript"></script>
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
			
			.ui-draggable,.ui-droppable {
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
				/* adjust styles for IE 6/7 */ *
				height: 1.7em; *
				top: 0.1em;
			}
			
			.ui-combobox-input {
				margin: 0;
				padding: 0.3em;
			}
		</style>
	</head>
	<body>
		<div id="tabs" style="width: 100%">
			<ul>
				<li id='tab0'>
					<a href='#tabs-0'>接入系统服务统计表</a>
				</li>
			</ul>
			<div id="tabs-0">
				<div class="ui-widget-header"
					style="margin-bottom: 0.5em; padding: 0.2em;">
					系统简称:
					<select style="width: 8%; margin-right: 2em;" id="sys_select"></select>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;首次上线日期(提供方):
					<input type="text" id="datepicker_prd" value="" />
					首次上线日期(调用方):
					<input type="text" id="datepicker_csm" value="" />
					<br/>
					服务状态:
					<select style="width: 8%; margin-right: 2em;width: 141px;" id="versionSt">
						<option value=""></option>
						<option value="0">
							服务定义
						</option>
						<option value="1">
							开发
						</option>
						<option value="2">
							联调测试
						</option>
						<option value="3">
							sit测试
						</option>
						<option value="4">
							uat测试
						</option>
						<option value="5">
							投产验证
						</option>
						<option value="6">
							上线
						</option>
					</select>
					&nbsp;&nbsp;提供报文类型:
					<select style="width: 8%; margin-right: 2em; width: 136px;"
						id="provideMsgType">
						<option value=""></option>
						<option value="SOP">
							SOP
						</option>
						<option value="SOAP">
							SOAP
						</option>
						<option value="XML">
							XML
						</option>
						<option value="FIX">
							FIX
						</option>
						<option value="API">
							API
						</option>
					</select>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;调用报文类型:
					<select style="width: 8%; margin-right: 2em; width: 136px;"
						id="consumeMsgType">
						<option value=""></option>
						<option value="SOP">
							SOP
						</option>
						<option value="SOAP">
							SOAP
						</option>
						<option value="XML">
							XML
						</option>
						<option value="FIX">
							FIX
						</option>
						<option value="API">
							API
						</option>
					</select>
					<input style="width: 4%; margin-right: 2em;" type="button"
						value="查询" id="search" />
					<input style="width: 4%; margin-right: 2em;" type="button"
						value="导出" id="export" />
					<input style="width: 4%; margin-right: 2em;" type="button"
						value="重置" id="reset" />
				</div>
				<table cellpadding="0" cellspacing="0" border="0" class="display"
					id="systemInvokeServiceTable">
					<tfoot>
						<tr>
							<th>
								<input type="hidden" name="systemAB" value="系统简称"
									class="search_init" />
							</th>
							<th>
								<input type="hidden" name="systemName" value="系统名称"
									class="search_init" />
							</th>
							<th>
								<input type="hidden" name="firstPublishDate" value="首次上线日期(提供方)"
									class="search_init" />
							</th>
							<th>
								<input type="hidden" name="secondPublishDate" value="首次上线日期(调用方)"
									class="search_init" />
							</th>
							<th>
								<input type="hidden" name="provideServiceNum" value="提供服务数"
									class="search_init" />
							</th>
							<th>
								<input type="hidden" name="consumeServiceNum" value="调用服务数"
									class="search_init" />
							</th>
							<th>
								<input type="hidden" name="provideOperationNum" value="提供操作数"
									class="search_init" />
							</th>
							<th>
								<input type="hidden" name="consumeOperationNum" value="调用操作数"
									class="search_init" />
							</th>
						</tr>
					</tfoot>
				</table>
			</div>
		</div>
	</body>
</html>





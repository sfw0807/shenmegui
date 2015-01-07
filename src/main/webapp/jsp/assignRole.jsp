<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>分配角色</title>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="this is my page">
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge"/>

		<link rel="stylesheet"
			href="<%=path%>/themes/smoothness/jquery-ui-1.8.4.custom.css" />
		<link rel="stylesheet" href="<%=path%>/css/index.css" />
		<script src="<%=path%>/js/jquery-ui/js/jquery-1.10.2.js"></script>
	    <script src="<%=path%>/js/jquery.ui.core.js"></script>
	    <script src="<%=path%>/js/jquery.ui.widget.js"></script>
	    <script src="<%=path%>/js/jquery.ui.button.js"></script>
	    <script src="<%=path%>/js/jquery.ui.position.js"></script>
	    <script src="<%=path%>/js/jquery.ui.autocomplete.js"></script>
       <script src="<%=path%>/js/jquery-ui-tabs.js"></script>
       <script src="<%=path%>/js/jquery.ui.button.js"></script>
       <script src='<%=path%>/js/jquery.datatables/js/jquery.dataTables.js' type="text/javascript"></script>
		<link rel='stylesheet' type='text/css'
			href='<%=path%>/js/jquery.datatables/css/jquery.dataTables.css' />
		<script src="<%=path%>/js/jquery.fileDownload.js" type="text/javascript"></script>
		<script src="<%=path%>/js/layout.js" type="text/javascript"></script>
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
			a:link{
				text-decoration:none;
				color:blue;
			}
			a:visited{
				text-decoration:underline;
				color:blue;
			}
			a:hover{
				text-decoration:underline;
				color:blue;
			}
			a:active{
				text-decoration:underline;
				color:blue;
			}
		</style>
		<script type="text/javascript">
			var roleManager = {
			    getAll: function(callBack) {
			        $.ajax({
			            url: '../role/list',
			            type: 'GET',
			            success: function(result) {
			                callBack(result);
			            }
			        });
			    },
			    getRoleIdByUserId:function(userId,callBack){
			    	$.ajax({
			            url: '../user/getRoleIdByUserId/'+userId,
			            type: 'GET',
			            success: function(result) {
			                callBack(result);
			            }
			        });
			    },
			   	assignRole: function(param) {
			        $.ajax({
			            "type": "POST",
			            "contentType": "application/json; charset=utf-8",
			            "url": "../user/assignRole",
			            "dataType": "json",
			            "data":JSON.stringify(param),
			            "success": function(result) {
			            	if(result){
			            		alert("分配角色成功");
			            		window.location.href="userManager.jsp";
			            	}else{
			            		alert("分配角色失败");
			            	}
			            }
			        });
			    }
			}
			$(function() {
				var tables = {};
				var asInitVals = new Array();
				$('#tabs').tabs();
				$("#tab0").click(function(e) {
					if(tables["roleTable"]){
						tables["roleTable"].fnAdjustColumnSizing();
					}
				});
				/**
				 * init service interface relate table 
				 * @param {Object} result
				 * 
				 */
				// 从URL中得到userId
				var href = window.location.href;
				var userId = href.split("=")[1];
				var roleId = "";

				var initroleTable = function initroleTable(result) {
					//初始化对Grid的操作事件
					var columnClickEventInit = function columnClickEventInit() {

						$("#roleTable tbody tr").unbind("click");
						$("#roleTable tbody tr").click(function(e) {
							$(this).toggleClass("row_selected");
						});
					};
					//初始化operationTable
					tables["roleTable"] = $("#roleTable").dataTable( {
						"aaData" : result,
						"aoColumns" : roleTableLayout,
						"aoColumnDefs" : [
							{
								"sClass" : "center",
								"aTargets" : [0,1,2]
							}							
						],
						"bJQueryUI": true,
						"bAutoWidth" : true,
						"bScrollCollapse" : "full_numbers",
						"bPaginate" : true,
						"bSort" : true,
						"bFilter":true,
						"bSearchable":true,
						"oLanguage" :oLanguage,
						"fnDrawCallback" : function() {
							columnClickEventInit();
						}
					});
					tables["roleTable"].fnAdjustColumnSizing();
					var rowsAll = tables["roleTable"].$("tr");
					
					for(var i=0;i<rowsAll.length;i++){
						if(roleId==rowsAll[i].cells[0].innerText){
							$(rowsAll[i]).addClass("row_selected");
						}
					}
				};
				var initRoleId = function initRoleId(param){
					roleId = param;
					roleManager.getAll(initroleTable);
				}
				roleManager.getRoleIdByUserId(userId,initRoleId);
				
			
				//初始化操作Grid的搜索框
				var initroleTableFooter = function initroleTableFooter() {
					$("#roleTable tfoot input").keyup(
							function() {
								tables["roleTable"].fnFilter(this.value, $(
										"#roleTable tfoot input").index(this),null,null,null,false);
							});
					$("#roleTable tfoot input").each(function(i) {
						asInitVals[i] = this.value;
					});
					$("#roleTable tfoot input").focus(function() {
						if (this.className == "search_init") {
							this.className = "";
							this.value = "";
						}
					});
					$("#roleTable tfoot input")
							.blur(
									function(i) {
										if (this.value == "") {
											this.className = "search_init";
											this.value = asInitVals[$(
													"#roleTable tfoot input")
													.index(this)];
										}
									});
				};
				initroleTableFooter();
				$("#assignRole").button().click(function(){
					var table = tables["roleTable"];
					var rowsSelected = table.$("tr.row_selected");
					if(rowsSelected.length!=1){
						alert("一个用户只能有一个角色!");
					}else{
						var roleId = rowsSelected[0].cells[0].innerText;
						console.log("roleid:"+roleId+",userid:"+userId);
						var param = [userId,roleId];
						roleManager.assignRole(param);
					}
				});
			});
		</script>
	</head>
	<body>
		<div id="tabs" style="width: 100%">
			<ul>
				<li id='tab0'>
					<a href='#tabs-0'>分配角色</a>
				</li>
			</ul>
			
			<div id="tabs-0">
			<div class="ui-widget-header" style="margin-bottom: 0.5em;padding: 0.2em;">
				<input type = "button" value="保存" id="assignRole" />
			</div>
			<table cellpadding="0" cellspacing="0" border="0" class="display" id="roleTable">
					<tfoot>
						<tr>
							<th>
								<input type="text" name="operationId" id="operationId" value="角色ID"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="operationName" id="operationName" value="角色名称"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="serviceId" id="serviceId" value="备注"
									class="search_init" />
							</th>					
						</tr>
					</tfoot>
				</table>
			</div>
		</div>
	</body>
</html>


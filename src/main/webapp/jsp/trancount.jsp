<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>存量交易统计</title>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="this is my page">
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge"/>

		<link rel="stylesheet"
			href="<%=path%>/themes/smoothness/jquery-ui-1.8.4.custom.css" />
		<link rel="stylesheet" href="<%=path%>/css/index.css" />
		<script src="<%=path%>/js/jquery-ui/js/jquery-1.10.2.js"></script>
		<script src="<%=path %>/js/jquery-1.8.2.js"></script>
		<script src="<%=path %>/js/jquery.bgiframe-2.1.2.js"></script>
	    <script src="<%=path %>/js/jquery.ui.core.js"></script>
	    <script src="<%=path %>/js/jquery.ui.widget.js"></script>
	    <script src="<%=path %>/js/jquery.ui.mouse.js"></script>
	    <script src="<%=path %>/js/jquery.ui.button.js"></script>
	    <script src="<%=path %>/js/jquery.ui.position.js"></script>
	    <script src="<%=path %>/js/jquery.ui.autocomplete.js"></script>
	    <script src="<%=path %>/js/jquery.ui.draggable.js"></script>
	    <script src="<%=path %>/js/jquery.ui.resizable.js"></script>
	    <script src="<%=path %>/js/jquery.ui.dialog.js"></script>
	    <script src="<%=path %>/js/jquery.effects.core.js"></script>
        <script src="<%=path %>/js/jquery-ui-tabs.js"></script>
        <script src="<%=path %>/js/combo-box.js"></script>
       <script src='<%=path%>/js/jquery.datatables/js/jquery.dataTables.js' type="text/javascript"></script>
		<link rel='stylesheet' type='text/css'
			href='<%=path%>/js/jquery.datatables/css/jquery.dataTables.css' />
		<script src="<%=path%>/js/jquery.fileDownload.js" type="text/javascript"></script>
		<script src="<%=path%>/js/layout.js" type="text/javascript"></script>
		<script src="<%=path%>/js/tranlinkManager.js" type="text/javascript"></script>
		<script src="<%=path%>/js/tranlink.js" type="text/javascript" charset="UTF-8"></script>
		<script src="<%=path%>/js/json/json2.js" type="text/javascript"></script>
		<script type="text/javascript">
		var trancountManager = {
		    getAllTranProvider : function getAllTranProvider (callBack) {
				$.ajax({
		            url: "../tranlink/getAllTranProvider",
		            type: "GET",
		            success: function(result) {
		                callBack(result);
		            }
		        });
		    },
		    getAllTranConsumer : function getAllTranConsumer (callBack) {
				$.ajax({
		            url: "../tranlink/getAllTranConsumer",
		            type: "GET",
		            success: function(result) {
		                callBack(result);
		            }
		        });
		    }
		};
		$(function() {
			var tables = {};
			var asInitVals = new Array();
			$('#tabs').tabs();
			$("#tab1").click(function(e) {
				if(tables["AllTranProvider"]){
					tables["AllTranProvider"].fnAdjustColumnSizing();
				}
			});
	
			/**
			 * init service interface relate table 
			 * @param {Object} result
			 * 
			 */
		
			var initAllTranProviderTable = function initAllTranProviderTable(result) {
				//初始化对Grid的操作事件
				var columnClickEventInit = function columnClickEventInit() {
					$("#AllTranProvider tbody tr").unbind("click");
					$("#AllTranProvider tbody tr").click(function(e) {
						$(this).toggleClass("row_selected");
					});
				};
				//初始化slaTable
				tables["AllTranProvider"] = $("#AllTranProvider").dataTable( {
					"aaData" : result,
					"aoColumns" : AllTranProviderTableLayout,
					"aoColumnDefs" : [
						{
							"sClass" : "center",
							"aTargets" : [0,1,2,3,4,5]
						}
					],
					"bJQueryUI": true,
					"bAutoWidth" : true,
					"bScrollCollapse" : "full_numbers",
					"bPaginate" : true,
					"bSort" : true,
					"oLanguage" : oLanguage,
					"fnDrawCallback" : function() {
						columnClickEventInit();
					}
				});
				tables["AllTranProvider"].fnAdjustColumnSizing();
			};
			trancountManager.getAllTranProvider(initAllTranProviderTable);
		
			//初始化操作Grid的搜索框
			var initAllTranProviderFooter = function initAllTranProviderFooter() {
				$("#AllTranProvider tfoot input").keyup(
						function() {
							var param = this.value;
							var flag = param.indexOf(" ");
							if(flag!=-1){
								while(param.indexOf(" ")!=-1){
									param = param.replace(" ","");
								}
								tables["AllTranProvider"].fnFilter(param, $("#AllTranProvider tfoot input").index(this),null,null,null,false);
							}else{
								tables["AllTranProvider"].fnFilter(this.value, $(
									"#AllTranProvider tfoot input").index(this),null,null,null,false);
							}
		
						});
				$("#AllTranProvider tfoot input").each(function(i) {
					asInitVals[i] = this.value;
				});
				$("#AllTranProvider tfoot input").focus(function() {
					if (this.className == "search_init") {
						this.className = "";
						this.value = "";
					}
				});
				$("#AllTranProvider tfoot input")
						.blur(
								function(i) {
									if (this.value == "") {
										this.className = "search_init";
										this.value = asInitVals[$(
												"#AllTranProvider tfoot input")
												.index(this)];
									}
								});
			};
			initAllTranProviderFooter();
			
			var initAllTranConsumerTable = function initAllTranConsumerTable(result) {
				//初始化对Grid的操作事件
				var columnClickEventInit = function columnClickEventInit() {
					$("#AllTranConsumer tbody tr").unbind("click");
					$("#AllTranConsumer tbody tr").click(function(e) {
						$(this).toggleClass("row_selected");
					});
				};
				//初始化slaTable
				tables["AllTranConsumer"] = $("#AllTranConsumer").dataTable( {
					"aaData" : result,
					"aoColumns" : AllTranConsumerTableLayout,
					"aoColumnDefs" : [
						{
							"sClass" : "center",
							"aTargets" : [0,1,2,3,4,5,6,7,8]
						}
					],
					"bJQueryUI": true,
					"bAutoWidth" : true,
					"bScrollCollapse" : "full_numbers",
					"bPaginate" : true,
					"bSort" : true,
					"oLanguage" : oLanguage,
					"fnDrawCallback" : function() {
						columnClickEventInit();
					}
				});
				tables["AllTranConsumer"].fnAdjustColumnSizing();
			};
			trancountManager.getAllTranConsumer(initAllTranConsumerTable);
		
			//初始化操作Grid的搜索框
			var initAllTranConsumerFooter = function initAllTranConsumerFooter() {
				$("#AllTranConsumer tfoot input").keyup(
						function() {
							var param = this.value;
							var flag = param.indexOf(" ");
							if(flag!=-1){
								while(param.indexOf(" ")!=-1){
									param = param.replace(" ","");
								}
								tables["AllTranConsumer"].fnFilter(param, $("#AllTranConsumer tfoot input").index(this),null,null,null,false);
							}else{
								tables["AllTranConsumer"].fnFilter(this.value, $(
									"#AllTranConsumer tfoot input").index(this),null,null,null,false);
							}
		
						});
				$("#AllTranConsumer tfoot input").each(function(i) {
					asInitVals[i] = this.value;
				});
				$("#AllTranConsumer tfoot input").focus(function() {
					if (this.className == "search_init") {
						this.className = "";
						this.value = "";
					}
				});
				$("#AllTranConsumer tfoot input")
						.blur(
								function(i) {
									if (this.value == "") {
										this.className = "search_init";
										this.value = asInitVals[$(
												"#AllTranConsumer tfoot input")
												.index(this)];
									}
								});
			};
			initAllTranConsumerFooter();
			$("#exportTranProvider").button().click(function(){
				var trancode = ($('#ptrancode').val() == '交易码')?"":$('#ptrancode').val();		 
				var tranname = ($('#ptranname').val() == '交易名称')?"":$('#ptranname').val();
				var provider = ($('#pprovider').val() == '提供方')?"":$('#pprovider').val();
				var providerMsgType = ($('#pproviderMsgType').val() == '提供方报文格式')?"":$('#pproviderMsgType').val();
				var params ={
					trancode : trancode,
					tranname: tranname,
					provider: provider,
					providerMsgType: providerMsgType,
				};
				$.fileDownload(encodeURI(encodeURI("../tranlink/exportTranProvider/"+JSON.stringify(params))), {});
			});
			$("#exportTranConsumer").button().click(function(){
				var trancode = ($('#ctrancode').val() == '交易码')?"":$('#ctrancode').val();		 
				var tranname = ($('#ctranname').val() == '交易名称')?"":$('#ctranname').val();
				var consumer = ($('#cconsumer').val() == '调用方')?"":$('#cconsumer').val();
				var passedsys = ($('#cpassedsys').val() == '经由系统')?"":$('#cpassedsys').val();
				var provider = ($('#cprovider').val() == '提供方')?"":$('#cprovider').val();
				var consumerMsgType = ($('#cconsumerMsgType').val() == '调用方报文格式')?"":$('#cconsumerMsgType').val();
				var frontcode = ($('#cfrontcode').val() == '前台交易码')?"":$('#cfrontcode').val();
				var params ={
					trancode : trancode,
					tranname: tranname,
					consumer: consumer,
					passedsys: passedsys,
					provider: provider,
					consumerMsgType: consumerMsgType,
					frontcode: frontcode
				};
				$.fileDownload(encodeURI(encodeURI("../tranlink/exportTranConsumer/"+JSON.stringify(params))), {});
			});
		});
		</script>
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
		</style>

	</head>
	<body>
		<div id="tabs" style="width: 100%">
			<ul>
				<li id='tab0'>
					<a href='#tabs-0'>提供方交易统计</a>
				</li>
				<li id='tab1'>
					<a href='#tabs-1'>调用方交易统计</a>
				</li>				
			</ul>
			
			<div id="tabs-0">
			<div class="ui-widget-header" style="margin-bottom: 0.5em;padding: 0.2em;">
				<input  type = "button" value="导出" id="exportTranProvider" />
			</div>
			<table cellpadding="0" cellspacing="0" border="0" class="display" id="AllTranProvider">
					<tfoot>
						<tr>
							<th>
								<input type="text" name="trancode" id="ptrancode" value="交易码"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="tranname" id="ptranname" value="交易名称"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="provider" id="pprovider" value="提供方"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="providerMsgType" id="pproviderMsgType" value="提供方报文格式"
									class="search_init" />
							</th>	
							<th>
								<input type="hidden" name="charger" id="pcharger" value="负责人"
									class="search_init" />
							</th>
							<th>
								<input type="hidden" name="remark" id="premark" value="备注"
									class="search_init" />
							</th>																				
						</tr>
					</tfoot>
				</table>
			</div>
			<div id="tabs-1">
			<div class="ui-widget-header" style="margin-bottom: 0.5em;padding: 0.2em;">
				<input  type = "button" value="导出" id="exportTranConsumer" />
			</div>
				<table cellpadding="0" cellspacing="0" border="0" class="display" id="AllTranConsumer">
						<tfoot>
							<tr>
								<th>
									<input type="text" name="trancode" id="ctrancode" value="交易码"
										class="search_init" />
								</th>
								<th>
									<input type="text" name="tranname" id="ctranname" value="交易名称"
										class="search_init" />
								</th>
								<th>
									<input type="text" name="consumer" id="cconsumer" value="调用方"
										class="search_init" />
								</th>
								<th>
									<input type="text" name="passedsys" id="cpassedsys" value="经由系统"
										class="search_init" />
								</th>
								<th>
									<input type="text" name="provider" id="cprovider" value="提供方"
										class="search_init" />
								</th>
								<th>
									<input type="text" name="consumerMsgType" id="cconsumerMsgType" value="调用方报文格式"
										class="search_init" />
								</th>
								<th>
									<input type="text" name="frontcode" id="cfrontcode" value="前台交易码"
										class="search_init" />
								</th>	
								<th>
									<input type="hidden" name="charger" id="ccharger" value="负责人"
										class="search_init" />
								</th>
								<th>
									<input type="hidden" name="remark" id="cremark" value="备注"
										class="search_init" />
								</th>																																								
							</tr>
						</tfoot>
				</table>			
			</div>
		</div>
	</body>
</html>


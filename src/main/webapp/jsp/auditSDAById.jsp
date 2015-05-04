<%@ page language="java" pageEncoding="UTF-8" %>
<%
	String path = request.getContextPath();
	String ctx = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ include file="/jsp/includes/header.jsp" %>
<%@ include file="/jsp/includes/jquery.jsp" %>
<%@ include file="/jsp/includes/jquery-ui.jsp" %>
<%@ include file="/jsp/includes/jquery-plugins.jsp" %>
<%@ include file="/jsp/includes/datatable.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>服务元数据</title>

		<meta http-equiv="description" content="this is my page">
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">

		<link rel="stylesheet" type="text/css"
			href="<%=ctx%>/js/multi-select/css/jquery.multiselect.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=ctx%>/js/multi-select/css/jquery.multiselect.filter.css" />
		<link rel="stylesheet" href="<%=ctx%>/css/index.css" />
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
	height: 1.5em; *
	top: 0.1em;
}

.ui-combobox-input {
	margin: 0;
	padding: 0.3em; *
	weight: 15em
}
</style>
		<script type="text/javascript">
	     var operationManager = {
	         getSDAInfoByOperationId: function(operationId,serviceId,callBack) {
		        $.ajax({
		            url: '../operationInfo/getSDAInfoByOperationId/'+serviceId+operationId,
		            type: 'GET',
		            success: function(result) {
		                callBack(result);
		            }
		        });
		    }
	     };
	     var href = window.location.href;
		 var hefparam = href.split('?')[1];
	     $(function(){
	        var asInitVals_1 = new Array();
	        var asInitVals_2 = new Array();
	        var tables = {};
	        var operationId = hefparam.split('&')[0].split('=')[1];
		    var serviceId = hefparam.split('&')[1].split('=')[1];
	   //初始化SDA表格的方法
	   function initChildTable(result) {
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#operationSDATable tbody tr").unbind("click");
			$("#operationSDATable tbody tr").click(function(e) {
				$(this).toggleClass("row_selected");			
			});
		};
		//创建Grid
		tables["operationSDATable"] = $("#operationSDATable").dataTable( {
			"aaData" : result,
			"aoColumns" : operationSDATableLayout,
			"aoColumnDefs" : [ 
				{
					"sClass" : "center",
					"aTargets" : [0,2,3,4,5]
				},
				{
					"bSortable":false,
					"aTargets" : [1,2,3,4,5]
				},
				{
					"mRender" : function ( data, type, row ) {
						return "<nobr>" + data + "</nobr>";
					},
					"aTargets" : [ 1,2,3]
				},
				{
					"bVisible":false,
					"aTargets":[6,7]
				}
			],
			"fnRowCallback" : function(nRow, aData, iDisplayIndex) {
			
				//console.log(aData);
				if (aData["structId"].indexOf("|--") == 0) {
					$(nRow).css("background-color", "chocolate");
				}
				if (aData["structId"].indexOf("|--request") > 0) {
					$(nRow).css("background-color", "darkkhaki");
				}
				if (aData["structId"].indexOf("|--response") > 0) {
					$(nRow).css("background-color", "darkkhaki");
				}
				if (aData["structId"].indexOf("|--SvcBody") > 0) {
					$(nRow).css("background-color", "burlywood");
				}
				if (aData["type"] == "array") {
					$(nRow).css("background-color", "gold");
				}
				
			},
			"bJQueryUI": true,
			"bAutoWidth" : true,
			"bScrollCollapse" : true,
			"bPaginate" : false,
			"bSort" : false,
			"oLanguage" : oLanguage,
			"bFilter": false,
			"fnDrawCallback" : function() {
				columnClickEventInit();
			}
		});
		tables["operationSDATable"].fnAdjustColumnSizing(false);

	};
	operationManager.getSDAInfoByOperationId(operationId, serviceId, initChildTable);

	//初始化操作Grid的搜索框
	function initoperationSDATableFooter() {
		$("#operationSDATable tfoot input").keyup(
				function() {
					tables["operationSDATable"].fnFilter(this.value, $(
							"#operationSDATable tfoot input").index(this));
				});
		$("#operationSDATable tfoot input").each(function(i) {
			asInitVals_2[i] = this.value;
		});
		$("#operationSDATable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#operationSDATable tfoot input").blur(
				function(i) {
					if (this.value == "") {
						this.className = "search_init";
						this.value = asInitVals_2[$(
								"#operationSDATable tfoot input").index(this)];
					}
				});
	   };
	  initoperationSDATableFooter();
	  $('#tab').html("<p style='font-size:16px;'>服务操作： "+serviceId + operationId+"<p>");
      })
      
	</script>
	</head>
	<div id='tab'></div>
	<table cellpadding="0" cellspacing="0" border="0" class="display"
		id="operationSDATable">
		<tfoot>
			<tr>
				<th>
					<input type="text" id="seq" name="seq" value="序号"
						class="search_init" />
				</th>
				<th>
					<input type="text" id="structId" name="structId" value="英文名称"
						class="search_init" />
				</th>
				<th>
					<input type="text" id="metadataId" name="metadataId" value="元数据ID"
						class="search_init" />
				</th>
				<th>
					<input type="text" id="type" name="type" value="类型"
						class="search_init" />
				</th>
				<th>
					<input type="text" id="required" name="required" value="是否必输"
						class="search_init" />
				</th>
				<th>
					<input type="text" id="remark" name="remark" value="备注"
						class="search_init" />
				</th>
			</tr>
		</tfoot>
	</table>
</html>

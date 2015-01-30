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
		<title>接口元数据</title>

		<meta http-equiv="description" content="this is my page">
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">

		<!--<link rel="stylesheet" type="text/css" href="./styles.css">-->

		<link rel="stylesheet" type="text/css"
			href="<%=path%>/js/multi-select/css/jquery.multiselect.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=path%>/js/multi-select/css/jquery.multiselect.filter.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=path%>/js/jquery-ui/css/redmond/jquery.ui.theme.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=path%>/js/jquery-ui/css/redmond/jquery-ui.css" />
		<link rel="stylesheet"
			href="<%=path%>/themes/smoothness/jquery-ui-1.8.4.custom.css" />
		<link rel="stylesheet" href="<%=path%>/css/index.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=path%>/js/jquery.datatables/css/jquery.dataTables.css" />

		<script src="<%=path%>/js/jquery-ui/js/jquery-1.10.2.js"></script>
		<script src="<%=path%>/js/jquery.datatables/js/jquery.dataTables.js"
			type="text/javascript"></script>
		<script src="<%=path%>/js/layout.js" type="text/javascript"></script>
		<script src="<%=path%>/js/jquery-ui.min.js" type="text/javascript"></script>
		<script src="<%=path%>/js/combo-box.js" type="text/javascript"></script>
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
	     var interfaceManager = {
	         getIDAs: function(ecode, callBack) {
		        $.ajax({
		            url: '../interfaceManagement/getIDAs/' + ecode,
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
	        var asInitVals = new Array();
	        var tables = {};
	        var ecode = hefparam.split('&')[0].split('=')[1];
		     //初始化SDA表格的方法
		    function initChildTable(result) {
				//初始化对Grid的操作事件
				var columnClickEventInit = function columnClickEventInit() {
					$("#idaTable tbody tr").unbind("click");
					$("#idaTable tbody tr").click(function(e) {
						$(this).toggleClass("row_selected");			
					});
			    };
				//创建Grid
				tables["idaTable"] = $("#idaTable").dataTable( {
					"aaData" : result,
					"aoColumns" : auditIdaTableLayout,
					"aoColumnDefs" : [ 
						{
							"sClass" : "center",
							"aTargets" : [0,2,3,4,5]
						},
						{
							"bVisible" : false,
							"aTargets" : [5, 6]
						},
						{
							"mRender" : function ( data, type, row ) {
								return "<nobr>" + data + "</nobr>";
							},
							"aTargets" : [ 1,2,3]
						},
						{
							"mRender" : function ( data, type, row ) {
							    var type = row["type"];
								if(row["length"] != "" && row["length"] != null){
								    type += "(" + row["length"];
								}
								if(row["scale"] != "" && row["scale"] != null){
								    type += "," + row["scale"];
								}
								if(type != "" && type != null){
									type += ")";
								}
								return type;
							},
							"aTargets" : [4]
						}
					],
					"fnRowCallback" : function(nRow, aData, iDisplayIndex) {
					
						if (aData["structName"].indexOf("|--") == 0) {
							$(nRow).css("background-color", "chocolate");
						}
						if (aData["structName"].indexOf("|--request") > 0) {
							$(nRow).css("background-color", "darkkhaki");
						}
						if (aData["structName"].indexOf("|--response") > 0) {
							$(nRow).css("background-color", "darkkhaki");
						}
						if (aData["structName"].indexOf("|--SvcBody") > 0) {
							$(nRow).css("background-color", "burlywood");
						}
						if (aData["type"] == "array") {
							$(nRow).css("background-color", "gold");
						}
						
					},
					"bJQueryUI": true,
					"bAutoWidth" : true,
					"bScrollCollapse" : "full_numbers",
					"bPaginate" : false,
					"bSort" : false,
					"bFilter" : false,
					"oLanguage" : oLanguage,
					"fnDrawCallback" : function() {
						columnClickEventInit();
					}
				});
				tables["idaTable"].fnAdjustColumnSizing(false);
		};
		interfaceManager.getIDAs(ecode, initChildTable);
		//初始化操作Grid的搜索框
		function initIdaTableFooter() {
		   $("#idaTable tfoot input").keyup(
				function() {
					tables["idaTable"].fnFilter(this.value, $(
							"#idaTable tfoot input").index(this));
				});
		   $("#idaTable tfoot input").each(function(i) {
			asInitVals[i] = this.value;
		   });
		   $("#idaTable tfoot input").focus(function() {
				if (this.className == "search_init") {
					this.className = "";
					this.value = "";
				}
		   });
		   $("#idaTable tfoot input").blur(
				function(i) {
					if (this.value == "") {
						this.className = "search_init";
						this.value = asInitVals[$(
								"#idaTable tfoot input").index(this)];
					}
		  });
	   };
	   initIdaTableFooter();
	   $('#tab').html("<p style='font-size:16px;'>接口： "+ ecode +"<p>");
      })
      
	</script>
	</head>
	<div id='tab'></div>
	<table cellpadding="0" cellspacing="0" border="0" class="display"
		id="idaTable">
		<tfoot>
			<tr>
				<th>
					<input type="hidden" id="seq" name="seq" value="序号"
						class="search_init" />
				</th>
				<th>
					<input type="hidden" id="structName" name="structName" value="英文名称"
						class="search_init" />
				</th>
				<th>
				<input type="hidden" id="metadataId" name="metadataId" value="元数据ID"
						class="search_init" />
				</th>
				<th>
					<input type="hidden" id="structName" name="structAlias" value="中文名称"
						class="search_init" />
				</th>
				<th>
					<input type="hidden" id="type" name="type" value="类型"
						class="search_init" />
				</th>
				<th>
					<input type="hidden" id="length" name=""length"" value="长度"
						class="search_init" />
				</th>
				<th>
					<input type="hidden" id="scale" name="scale" value="规模"
						class="search_init" />
				</th>
				<th>
					<input type="hidden" id="required" name="required" value="是否必输"
						class="search_init" />
				</th>
				<th>
					<input type="hidden" id="remark" name="remark" value="备注"
						class="search_init" />
				</th>
			</tr>
		</tfoot>
	</table>
</html>

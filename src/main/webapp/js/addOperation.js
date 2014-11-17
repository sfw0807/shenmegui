$(function() {
	var tables = {};
	var asInitVals = new Array();
	var operationId = "";
	var serviceId = "";
	var version = "";
	$('#tabs').tabs();
	//初始化SDA表格的方法
	var initChildTable = function initChildTable(result) {
		sdaNode = result;
		for(var i=0;i<sdaNode.length;i++){
			console.log("id:"+sdaNode[i]["id"]+" seq:"+sdaNode[i]["seq"]+" parentId:"+sdaNode[i]["parentId"]);
		}
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
			"bSort" : true,
			"oLanguage" : oLanguage,
			"fnDrawCallback" : function() {
				columnClickEventInit();
			}
		});
		tables["operationSDATable"].fnAdjustColumnSizing(true);

	};
	
	$("#tab1").click(function(e) {
		console.log("tab1 click");
		
		if(tables["operationSDATable"]){
			tables["operationSDATable"].fnAdjustColumnSizing();
		}
	});
	$("#tab2").click(function(e) {
		if(tables["operationSlaTable"]){
			tables["operationSlaTable"].fnAdjustColumnSizing();
		}
		
	});
	$("#tab3").click(function(e) {
		if(tables["operationOlaTable"]){
			tables["operationOlaTable"].fnAdjustColumnSizing();
		}
	});
	var initOperationInfo = function initOperationInfo(result) {
		$('#operationId').val(result.operationId);
		$('#serviceId').val(serviceId);
		$('#operationName').val(result.operationName);
		$('#operationRemark').val(result.remark);
		$('#state').val(result.state);
		$('#version').val(version);
//		$('#publishVersion').val(publishVersion);
//		$('#publishDate').val(publishDate);
	};
	//初始化SLA表格的方法
	//初始化OLA表格的方法

	//编辑基本服务定义
	$("#editOperationDef").click(function() {
		$('#operationId').attr("disabled",false);
		$('#operationName').attr("disabled",false);
		$('#operationRemark').attr("disabled",false);
		$('#state').attr("disabled",false);
	});
	//时间控件  
//	$("#publishDate").datepicker({
//		"changeMonth":true,
//		"changeYear":true,
//		"dateFormat":"yymmdd"
//		});
	
	//保存基本服务定义
	$("#saveOperationDef").click(function() {
		var params = {
			operationId:$("#operationId").val(),
			serviceId:$("#serviceId").val(),
			operationName:$("#operationName").val(),
			remark:$("#operationRemark").val(),
			state:$("#state").val(),
			version:$("#version").val()
        };
		operationManager.saveOperationDef(params);
		operationId = $("#operationId").val();
		serviceId = $("#serviceId").val();
		version = $("#version").val();
	});
});


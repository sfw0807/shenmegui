$(function() {
	var tables = {};
    var isChrome = (navigator.appVersion.indexOf("Chrome") != -1) ? true : false;
	var asInitVals = [];
	$('#tabs').tabs();
	$("#tab1").click(function(e) {
		if(tables["operationHistorySDATable"]){
			tables["operationHistorySDATable"].fnAdjustColumnSizing();
		}
	});
	$("#tab2").click(function(e) {
		if(tables["operationHistorySlaTable"]){
			tables["operationHistorySlaTable"].fnAdjustColumnSizing();
		}
		
	});
	$("#tab3").click(function(e) {
		if(tables["operationHistoryOlaTable"]){
			tables["operationHistoryOlaTable"].fnAdjustColumnSizing();
		}
	});

	// 从URL中得到operationId
	var href = window.location.href;
	var operationId = href.split("&")[0].split("=")[1];
	var serviceId = href.split("&")[1].split("=")[1];
	var version = href.split("&")[2].split("=")[1];
	var publishVersion = "";
	var publishDate = "";	
	if(href.split("&").length>=4){
		publishVersion = href.split("&")[3].split("=")[1];
		publishDate = href.split("&")[4].split("=")[1];
	}
	if(operationId.split("%27").length>=2){
		operationId = operationId.split("%27")[1];
	}
	if(serviceId.split("%27").length>=2){
		serviceId = serviceId.split("%27")[1];
	}
	if(version.split("%27").length>=2){
		version = version.split("%27")[1];
	}
	if(publishVersion.split("%27").length>=2){
		publishVersion = publishVersion.split("%27")[1];
	}
	if(publishDate.split("%27").length>=2){
		publishDate = publishDate.split("%27")[1];
	}
	var initOperationInfo = function initOperationInfo(result) {
		$('#operationId').val(result.operationId);
		$('#serviceId').val(result.serviceId);
		$('#operationName').val(result.operationName);
		$('#operationRemark').val(result.remark);
		$('#state').val(result.operationState);
		$('#version').val(result.operationVersion);
	};
	var params=[];
	params.push(operationId);
	params.push(version);
	params.push(serviceId);
	operationHistoryManager.getOperation(params,initOperationInfo);

	//初始化SDA表格的方法
	var initChildTable = function initChildTable(result) {
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#operationHistorySDATable tbody tr").unbind("click");
			$("#operationHistorySDATable tbody tr").click(function(e) {
				$(this).toggleClass("row_selected");			
			});
		};
		//创建Grid
		tables["operationHistorySDATable"] = $("#operationHistorySDATable").dataTable( {
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
			"bSort" : true,
			"oLanguage" : oLanguage,
			"fnDrawCallback" : function() {
				columnClickEventInit();
			}
		});
		tables["operationHistorySDATable"].fnAdjustColumnSizing(true);

	};
	//operationHistoryManager.getSDA(params,initChildTable);

	//初始化操作Grid的搜索框
	var initoperationSDATableFooter = function initoperationSDATableFooter() {
		$("#operationHistorySDATable tfoot input").keyup(
				function() {
					tables["operationHistorySDATable"].fnFilter(this.value, $(
							"#operationHistorySDATable tfoot input").index(this));
				});
		$("#operationHistorySDATable tfoot input").each(function(i) {
			asInitVals[i] = this.value;
		});
		$("#operationHistorySDATable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#operationHistorySDATable tfoot input").blur(
				function(i) {
					if (this.value == "") {
						this.className = "search_init";
						this.value = asInitVals[$(
								"#operationHistorySDATable tfoot input").index(this)];
					}
				});
	};
	//initoperationSDATableFooter();

	//初始化SLA表格的方法
	var initoperationSlaTable = function initoperationSlaTable(result) {
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#operationHistorySlaTable tbody tr").unbind("click");
			$("#operationHistorySlaTable tbody tr").click(function(e) {
				$(this).toggleClass("row_selected");
			});
		};
		//创建Grid
		tables["operationHistorySlaTable"] = $("#operationHistorySlaTable").dataTable( {
			"aaData" : result,
			"aoColumns" : operationSlaTableLayout,
			"aoColumnDefs" : [
					{
						"sClass" : "center",
						"aTargets" : [ 0,1, 2]
					},
				],
			"bJQueryUI": true,
			"bAutoWidth" : true,
			"bScrollCollapse" : true,
			"bPaginate" : false,
			"bSort" : false,
			"oLanguage" : oLanguage,
			"fnDrawCallback" : function() {
				columnClickEventInit();
			}
		});
		tables["operationHistorySlaTable"].fnAdjustColumnSizing();
	};
	operationHistoryManager.getSLA(params,initoperationSlaTable);

	//初始化操作Grid的搜索框
	var initoperationSlaTableFooter = function initoperationSlaTableFooter() {
		$("#operationHistorySlaTable tfoot input").keyup(
				function() {
					tables["operationHistorySlaTable"].fnFilter(this.value, $(
							"#operationHistorySlaTable tfoot input").index(this));
				});
		$("#operationHistorySlaTable tfoot input").each(function(i) {
			asInitVals[i] = this.value;
		});
		$("#operationHistorySlaTable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#operationHistorySlaTable tfoot input").blur(
				function(i) {
					if (this.value == "") {
						this.className = "search_init";
						this.value = asInitVals[$(
								"#operationHistorySlaTable tfoot input").index(this)];
					}
				});
	};
	initoperationSlaTableFooter();

	//初始化OLA表格的方法
	var initoperationOlaTable = function initoperationOlaTable(result) {
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#operationHistoryOlaTable tbody tr").unbind("click");
			$("#operationHistoryOlaTable tbody tr").click(function(e) {
				$(this).toggleClass("row_selected");
			});
		};
		//创建Grid
		tables["operationHistoryOlaTable"] = $("#operationHistoryOlaTable").dataTable( {
			"aaData" : result,
			"aoColumns" : operationOlaTableLayout,
			"aoColumnDefs" : [
					{
						"sClass" : "center",
						"aTargets" : [0,1,2]
					}
				],
			"bAutoWidth" : true,
			"bJQueryUI": true,
			"bScrollCollapse" : true,
			"bPaginate" : false,
			"bSort" : false,
			"oLanguage" : oLanguage,
			"fnDrawCallback" : function() {
				columnClickEventInit();
			}
		});
		tables["operationHistoryOlaTable"].fnAdjustColumnSizing();
	};
	operationHistoryManager.getOLA(params,initoperationOlaTable);

	//初始化操作Grid的搜索框
	var initoperationOlaTableFooter = function initoperationOlaTableFooter() {
		$("#operationHistoryOlaTable tfoot input").keyup(
				function() {
					tables["operationHistoryOlaTable"].fnFilter(this.value, $(
							"#operationHistoryOlaTable tfoot input").index(this));
				});
		$("#operationHistoryOlaTable tfoot input").each(function(i) {
			asInitVals[i] = this.value;
		});
		$("#operationHistoryOlaTable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#operationHistoryOlaTable tfoot input").blur(
				function(i) {
					if (this.value == "") {
						this.className = "search_init";
						this.value = asInitVals[$(
								"#operationHistoryOlaTable tfoot input").index(this)];
					}
				});
	};
	initoperationOlaTableFooter();

	$("#seesda").click(function(){
        if(isChrome){
            var winOption = "height=800px,width=1200px,top=50,scrollbars=yes,resizable=yes,fullscreen=0";
            return  window.open("../jsp/sdaHistory.jsp?operationId="+operationId+"&serviceId="+serviceId+"&version="+version, window, winOption);
        }else{
            window.showModalDialog("../jsp/sdaHistory.jsp",operationId+"|"+serviceId+"|"+version,"dialogWidth:1200px;dialogHeight:800px;resizable=yes");
        }
	});
});
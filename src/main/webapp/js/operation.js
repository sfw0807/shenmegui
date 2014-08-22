$(function() {
	var tables = {};
	var asInitVals = new Array();
	$('#tabs').tabs();
	$("#tab0").click(function(e) {
		if(tables["serviceExtendsTable"]){
			tables["serviceExtendsTable"].fnAdjustColumnSizing();
		}
	});
	$("#tab1").click(function(e) {
		if(tables["serviceExtendsTable"]){
			tables["serviceChildTable"].fnAdjustColumnSizing();
		}
	});
	$("#tab2").click(function(e) {
		if(tables["serviceSlaTable"]){
			tables["serviceSlaTable"].fnAdjustColumnSizing();
		}
		
	});
	$("#tab3").click(function(e) {
		if(tables["serviceOlaTable"]){
			tables["serviceOlaTable"].fnAdjustColumnSizing();
		}
	});
	var initServiceInfo = function initServiceInfo(result) {
		$('#serviceId').val(result.serviceId);
		$('#serviceName').val(result.serviceName);
		$('#serviceRemark').val(result.serviceRemark);
		$('#state').val(result.state);
	};

	// 从URL中得到resourceID
	var href = window.location.href;
	var resourceId = href.split("=")[1];
	serviceManager.getServiceByResourceId(resourceId, initServiceInfo);

	var initExtendTable = function initExtendTable(result) {
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#serviceExtendsTable tbody tr").unbind("click");
			$("#serviceExtendsTable tbody tr").click(function(e) {
				$(this).toggleClass("row_selected");
			});
		};
		//初始化ExtendsTable
		tables["serviceExtendsTable"] = $("#serviceExtendsTable").dataTable( {
			"aaData" : result,
			"aoColumns" : serviceExtendsTableLayout,
			"aoColumnDefs" : [
				{
					"sClass" : "center",
					"aTargets" : [ 0, 1, 2 ]
				}
			],
			"bJQueryUI": true,
			"bAutoWidth" : true,
			"sScrollY" : "500px",
			"bScrollCollapse" : true,
			"bPaginate" : false,
			"bSort" : false,
			"oLanguage" : oLanguage,
			"fnDrawCallback" : function() {
				columnClickEventInit();
			}
		});
		tables["serviceExtendsTable"].fnAdjustColumnSizing();
	};
	serviceManager.getServiceExtendInfo(resourceId, initExtendTable);

	//初始化操作Grid的搜索框
	var initserviceExtendsTableFooter = function initserviceExtendsTableFooter() {
		$("#serviceExtendsTable tfoot input").keyup(
				function() {
					tables["serviceExtendsTable"].fnFilter(this.value, $(
							"#serviceExtendsTable tfoot input").index(this));
				});
		$("#serviceExtendsTable tfoot input").each(function(i) {
			asInitVals[i] = this.value;
		});
		$("#serviceExtendsTable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#serviceExtendsTable tfoot input")
				.blur(
						function(i) {
							if (this.value == "") {
								this.className = "search_init";
								this.value = asInitVals[$(
										"#serviceExtendsTable tfoot input")
										.index(this)];
							}
						});
	};
	initserviceExtendsTableFooter();

	//初始化SDA表格的方法
	var initChildTable = function initChildTable(result) {
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#serviceChildTable tbody tr").unbind("click");
			$("#serviceChildTable tbody tr").click(function(e) {
				$(this).toggleClass("row_selected");
			});
		};
		//创建Grid
		tables["serviceChildTable"] = $("#serviceChildTable").dataTable( {
			"aaData" : result,
			"aoColumns" : serviceChildTableLayout,
			"aoColumnDefs" : [ 
				{
					"sClass" : "center",
					"aTargets" : [ 0, 2, 3, 4, 5 ]
				},
				{
					"mRender" : function ( data, type, row ) {
						return "<nobr>" + data + "</nobr>";
					},
					"aTargets" : [ 1,2,3,4 ]
				},
				{
					"mRender" : function ( data, type, row ) {
						if("Y" == data){
							return "是";
						}else{
							return "否";
						}
					},
					"aTargets" : [ 5 ]
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
			"sScrollY" : "500px",
			"bScrollCollapse" : true,
			"bPaginate" : false,
			"bSort" : false,
			"oLanguage" : oLanguage,
			"fnDrawCallback" : function() {
				columnClickEventInit();
			}
		});
		tables["serviceChildTable"].fnAdjustColumnSizing();
	};
	serviceManager.getChildSdaByResourceId(resourceId, initChildTable);

	//初始化操作Grid的搜索框
	var initServiceChildTableFooter = function initServiceChildTableFooter() {
		$("#serviceChildTable tfoot input").keyup(
				function() {
					tables["serviceChildTable"].fnFilter(this.value, $(
							"#serviceChildTable tfoot input").index(this));
				});
		$("#serviceChildTable tfoot input").each(function(i) {
			asInitVals[i] = this.value;
		});
		$("#serviceChildTable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#serviceChildTable tfoot input").blur(
				function(i) {
					if (this.value == "") {
						this.className = "search_init";
						this.value = asInitVals[$(
								"#serviceChildTable tfoot input").index(this)];
					}
				});
	};
	initServiceChildTableFooter();

	//初始化SLA表格的方法
	var initSlaTable = function initSlaTable(result) {
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#serviceSlaTable tbody tr").unbind("click");
			$("#serviceSlaTable tbody tr").click(function(e) {
				$(this).toggleClass("row_selected");
			});
		};
		//创建Grid
		tables["serviceSlaTable"] = $("#serviceSlaTable").dataTable( {
			"aaData" : result,
			"aoColumns" : serviceSlaTableLayout,
			"aoColumnDefs" : [],
			"bJQueryUI": true,
			"bAutoWidth" : true,
			"sScrollY" : "500px",
			"bScrollCollapse" : true,
			"bPaginate" : false,
			"bSort" : false,
			"oLanguage" : oLanguage,
			"fnDrawCallback" : function() {
				columnClickEventInit();
			}
		});
		tables["serviceSlaTable"].fnAdjustColumnSizing();
	};
	serviceManager.getServiceSlaById(resourceId, initSlaTable);

	//初始化操作Grid的搜索框
	var initSlaTableFooter = function initSlaTableFooter() {
		$("#serviceSlaTable tfoot input").keyup(
				function() {
					tables["serviceSlaTable"].fnFilter(this.value, $(
							"#serviceSlaTable tfoot input").index(this));
				});
		$("#serviceSlaTable tfoot input").each(function(i) {
			asInitVals[i] = this.value;
		});
		$("#serviceSlaTable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#serviceSlaTable tfoot input").blur(
				function(i) {
					if (this.value == "") {
						this.className = "search_init";
						this.value = asInitVals[$(
								"#serviceSlaTable tfoot input").index(this)];
					}
				});
	};
	initSlaTableFooter();

	//初始化ODA表格的方法
	var initOlaTable = function initOlaTable(result) {
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#serviceOlaTable tbody tr").unbind("click");
			$("#serviceOlaTable tbody tr").click(function(e) {
				$(this).toggleClass("row_selected");
			});
		};
		//创建Grid
		tables["serviceOlaTable"] = $("#serviceOlaTable").dataTable( {
			"aaData" : result,
			"aoColumns" : serviceSlaTableLayout,
			"aoColumnDefs" : [],
			"bAutoWidth" : true,
			"bJQueryUI": true,
			"sScrollY" : "500px",
			"bScrollCollapse" : true,
			"bPaginate" : false,
			"bSort" : false,
			"oLanguage" : oLanguage,
			"fnDrawCallback" : function() {
				columnClickEventInit();
			}
		});
		tables["serviceOlaTable"].fnAdjustColumnSizing();
	};
	serviceManager.getServiceOlaById(resourceId, initOlaTable);

	//初始化操作Grid的搜索框
	var initOlaTableFooter = function initOlaTableFooter() {
		$("#serviceOlaTable tfoot input").keyup(
				function() {
					tables["serviceOlaTable"].fnFilter(this.value, $(
							"#serviceOlaTable tfoot input").index(this));
				});
		$("#serviceOlaTable tfoot input").each(function(i) {
			asInitVals[i] = this.value;
		});
		$("#serviceOlaTable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#serviceOlaTable tfoot input").blur(
				function(i) {
					if (this.value == "") {
						this.className = "search_init";
						this.value = asInitVals[$(
								"#serviceOlaTable tfoot input").index(this)];
					}
				});
	};
	initOlaTableFooter();

});

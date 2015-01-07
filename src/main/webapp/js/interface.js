$(function() {

	var tables = {};

	var asInitVals = new Array();
	var asInitVals1 = new Array();
	
	$('#tabs').tabs();
	$("#tab0").click(function(e) {
		tables["childTable"].fnAdjustColumnSizing();
	});
	$("#tab1").click(function(e) {
		tables["extendsTable"].fnAdjustColumnSizing();
	});

	//初始化服务Grid的方法
	var initExtendTable = function initExtendTable(result) {
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#extendsTable tbody tr").unbind("click");
			$("#extendsTable tbody tr").click(function(e) {
				$(this).toggleClass("row_selected");
			});
		};
		//创建Grid
		tables["extendsTable"] = $("#extendsTable").dataTable( {
			"aaData" : result,
			"aoColumns" : extendsTableLayout,
//			"aoColumnDefs" : [ {
//				"sClass" : "center",
//				"aTargets" : [ 0, 1, 2 ]
//			} ],
			"bJQueryUI": true,
			"bAutoWidth" : true,
			"sPaginationType" : "full_numbers",
			"oLanguage" : oLanguage,
			"fnDrawCallback" : function() {
				columnClickEventInit();
			}
		});
	};

	var initText = function initText(result) {
		$('#interfaceId').val(result.interfaceId);
		$('#interfaceType').val(result.interfaceType);
		$('#interfaceRemark').val(result.interfaceRemark);
		$('#serviceId').val(result.serviceId);
		$('#interfaceMsgType').val(result.interfaceMsgType);
		$('#interfaceName').val(result.interfaceName);
		$('#providerSys').val(result.providerSys);
		$('#consumerSys').val(result.consumerSys);

	};

	// 从URL中得到交易码
	var href = window.location.href;
	var ecode = href.split("=")[1];

	//请求服务数据，回调实例化方法
	var result = interfaceManager.getInterfaceInfo(ecode, initText);

	result = interfaceManager.getInterfaceExtendInfo(ecode, initExtendTable);

	//初始化服务Grid的方法
	var initChildTable = function initChildTable(result) {
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#childTable tbody tr").unbind("click");
			$("#childTable tbody tr").click(function(e) {
				$(this).toggleClass("row_selected");
			});
		};
		//创建Grid

		tables["childTable"] = $("#childTable").dataTable( {
			"aaData" : result,
			"aoColumns" : interfaceChildTableLayout,
			"aoColumnDefs" : [ 
				{
					"sClass" : "center",
					"aTargets" : [ 0, 2, 3, 4, 5 ,6 ,7 ]
				},
				{
					"mRender" : function ( data, type, row ) {
						return "<nobr>" + data + "</nobr>";
					},
					"aTargets" : [ 1,2,3 ]
				},
				{
					"mRender" : function ( data, type, row ) {
						var typeDtl = data;
						if(row["length"]){
							typeDtl = typeDtl + "(" +row["length"];
						}
						if(row["scale"]){
							typeDtl = typeDtl + "," +row["scale"];
						}
						if(row["length"]){
							typeDtl = typeDtl + ")";
						}
						return typeDtl;
					},
					"aTargets" : [ 4 ]
				},
				{
					"mRender" : function ( data, type, row ) {
						if("Y" == data){
							return "是";
						}else{
							return "否";
						}
					},
					"aTargets" : [ 7 ]
				},
				{
					"bVisible" : false, "aTargets" : [ 5, 6 ]
				}
			],
			"fnRowCallback" : function(nRow, aData, iDisplayIndex) {
				if (aData["structName"] == "|--root") {
					$(nRow).css("background-color", "chocolate");
				}
				if (aData["structName"] == "&nbsp;&nbsp;&nbsp;&nbsp;|--request") {
					$(nRow).css("background-color", "darkkhaki");
				}
				if (aData["structName"] == "&nbsp;&nbsp;&nbsp;&nbsp;|--response") {
					$(nRow).css("background-color", "darkkhaki");
				}
				if (aData["type"] == "array") {
					$(nRow).css("background-color", "gold");
				}
			},
			"bJQueryUI": true,
			"bAutoWidth" : true,
			"sScrollY" : "500px",
			"bScrollCollapse" : true,
//			"sPaginationType" : "full_numbers",
			"bPaginate" : false,
			"bSort" : false,
			"oLanguage" : oLanguage,
			"fnDrawCallback" : function() {
				columnClickEventInit();
			}
		});
		
//		tables["childTable"].fnAdjustColumnSizing();
	};
	result = interfaceManager.getChildSDA4IInfo(ecode, initChildTable);

	//初始化操作Grid的搜索框
	var initExtendsTableFooter = function initExtendsTableFooter() {
		$("#extendsTable tfoot input").keyup(
				function() {
					tables["extendsTable"].fnFilter(this.value, $(
							"#extendsTable tfoot input").index(this));
				});
		$("#extendsTable tfoot input").each(function(i) {
			asInitVals1[i] = this.value;
		});
		$("#extendsTable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#extendsTable tfoot input").blur(
				function(i) {
					if (this.value == "") {
						this.className = "search_init";
						this.value = asInitVals1[$("#extendsTable tfoot input")
								.index(this)];
					}
				});
	};
	initExtendsTableFooter();

	//初始化操作Grid的搜索框
	var initChildTableFooter = function initChildTableFooter() {
		$("#childTable tfoot input").keyup(
				function() {
					tables["childTable"].fnFilter(this.value, $(
							"#childTable tfoot input").index(this));
				});
		$("#childTable tfoot input").each(function(i) {
			asInitVals[i] = this.value;
		});
		$("#childTable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#childTable tfoot input").blur(
				function(i) {
					if (this.value == "") {
						this.className = "search_init";
						this.value = asInitVals[$("#childTable tfoot input")
								.index(this)];
					}
				});
	};
	initChildTableFooter();

});
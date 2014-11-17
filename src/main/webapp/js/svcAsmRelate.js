$(function() {
	var tables = {};
	var asInitVals = new Array();
	$('#tabs').tabs();
	$("#tab0").click(function(e) {
		if(tables["svcAsmRelateTable"]){
			tables["svcAsmRelateTable"].fnAdjustColumnSizing();
		}
	});
	/**
	 * init service interface relate table 
	 * @param {Object} result
	 * 
	 */
	var initSvcAsmRelateTable = function initSvcAsmRelateTable(result) {
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#svcAsmRelateTable tbody tr").unbind("click");
			$("#svcAsmRelateTable tbody tr").click(function(e) {
				$(this).toggleClass("row_selected");
			});
		};
		//初始化svcAsmRelateTable
		tables["svcAsmRelateTable"] = $("#svcAsmRelateTable").dataTable( {
			"aaData" : result,
			"aoColumns" : svcAsmTableLayout,
			"aoColumnDefs" : [
				{
					"sClass" : "center",
					"aTargets" : [ 0, 1, 2, 3, 4, 5 , 6, 7, 8, 9, 10, 11, 12, 13]
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
		tables["svcAsmRelateTable"].fnAdjustColumnSizing();
	};
	svcAsmRelateManager.getAllSvcAsmRelateInfo(initSvcAsmRelateTable);

	//初始化操作Grid的搜索框
	var initSvcAsmRelateTableFooter = function initSvcAsmRelateTableFooter() {
		$("#svcAsmRelateTable tfoot input").keyup(
				function() {
					tables["svcAsmRelateTable"].fnFilter(this.value, $(
							"#svcAsmRelateTable tfoot input").index(this),null,null,null,false);
				});
		$("#svcAsmRelateTable tfoot input").each(function(i) {
			asInitVals[i] = this.value;
		});
		$("#svcAsmRelateTable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#svcAsmRelateTable tfoot input")
				.blur(
						function(i) {
							if (this.value == "") {
								this.className = "search_init";
								this.value = asInitVals[$(
										"#svcAsmRelateTable tfoot input")
										.index(this)];
							}
						});
	};
	initSvcAsmRelateTableFooter();
	
	$('#export').button().click(function () {
				var service = ($('#serviceInfo').val() == '服务ID/名称')?"":$('#serviceInfo').val();
				var operation = ($('#operationInfo').val() == '操作ID/名称')?"":$('#operationInfo').val();
				var interfaceInfo = ($('#interfaceInfo').val() == '交易代码/名称')?"":$('#interfaceInfo').val();
				var consumeSys = ($('#consumeSysInfo').val() == '源系统简称/名称')?"":$('#consumeSysInfo').val();
				var passbySys = ($('#passbySysInfo').val() == '调用方系统简称/名称')?"":$('#passbySysInfo').val();
				var provideSys = ($('#provideSysInfo').val() == '提供方系统简称/名称')?"":$('#provideSysInfo').val();
				var prdMsgType = ($('#provideMsgType').val() == '提供报文类型')?"":$('#provideMsgType').val();
				var csmMsgType = ($('#consumeMsgType').val() == '调用报文类型')?"":$('#consumeMsgType').val();
				var through = ($('#through').val() == '是否穿透')?"":$('#through').val();
				var state = ($('#state').val() == '交易状态')?"":$('#state').val();
				var onlineDate = ($('#onlineDate').val() == '上线日期')?"":$('#onlineDate').val();
				var onlineVersion = ($('#onlineVersion').val() == '上线版本')?"":$('#onlineVersion').val();
				var params ={
				service : service,
				operation: operation,
				interfaceInfo: interfaceInfo,
				consumeSys: consumeSys,
				passbySys: passbySys,
				provideSys: provideSys,
				prdMsgType: prdMsgType,
				csmMsgType: csmMsgType,
				through: through,
				state: state,
				onlineDate: onlineDate,
				onlineVersion: onlineVersion
				}
			    $.fileDownload("../relateView/export/"+JSON.stringify(params), {
            	});
			    
			});

});

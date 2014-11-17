$(function() {
	var tables = {};
	var asInitVals = new Array();
	$('#tabs').tabs();
	$("#tab0").click(function(e) {
		if(tables["serviceDetailTable"]){
			tables["serviceDetailTable"].fnAdjustColumnSizing();
		}
	});
	/**
	 * init service interface relate table 
	 * @param {Object} result
	 * 
	 */
	var initserviceDetailTable = function initserviceDetailTable(result) {
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#serviceDetailTable tbody tr").unbind("click");
			$("#serviceDetailTable tbody tr").click(function(e) {
				$(this).toggleClass("row_selected");
			});
		};
		//初始化serviceDetailTable
		tables["serviceDetailTable"] = $("#serviceDetailTable").dataTable( {
			"aaData" : result,
			"aoColumns" : serviceDetailTableLayout,
			"aoColumnDefs" : [
				{
					"sClass" : "center",
					"aTargets" : [ 0, 1, 2, 3, 4, 5 , 6, 7, 8, 9, 10, 11]
				}
			],
			"bJQueryUI": "true",
			"bAutoWidth" : "true",
			//"sScrollY" : "500px",
			//"sScrollX" : "500px",
			"bScrollCollapse" : "full_numbers",
			"bPaginate" : "true",
			"bSort" : "true",
			"oLanguage" : oLanguage,
			"fnDrawCallback" : function() {
				columnClickEventInit();
			}
		});
		tables["serviceDetailTable"].fnAdjustColumnSizing();
	};
	serviceDetailManager.getAllServiceDetailsInfo(initserviceDetailTable);

	//初始化操作Grid的搜索框
	var initserviceDetailTableFooter = function initserviceDetailTableFooter() {
		$("#serviceDetailTable tfoot input").keyup(
				function() {
					tables["serviceDetailTable"].fnFilter(this.value, $(
							"#serviceDetailTable tfoot input").index(this),null,null,null,false);
				});
		$("#serviceDetailTable tfoot input").each(function(i) {
			asInitVals[i] = this.value;
		});
		$("#serviceDetailTable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#serviceDetailTable tfoot input")
				.blur(
						function(i) {
							if (this.value == "") {
								this.className = "search_init";
								this.value = asInitVals[$(
										"#serviceDetailTable tfoot input")
										.index(this)];
							}
						});
	};
	initserviceDetailTableFooter();
	
	$('#export').button().click(function () {
				var service = ($('#serviceInfo').val() == '服务ID/名称')?"":$('#serviceInfo').val();
				var operation = ($('#operationInfo').val() == '操作ID/名称')?"":$('#operationInfo').val();
				var interfaceInfo = ($('#interfaceInfo').val() == '交易代码/名称')?"":$('#interfaceInfo').val();
				var passbySys = ($('#passbySysInfo').val() == '经由系统简称/名称')?"":$('#passbySysInfo').val();
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
				passbySys: passbySys,
				provideSys: provideSys,
				prdMsgType: prdMsgType,
				csmMsgType: csmMsgType,
				through: through,
				state: state,
				onlineDate: onlineDate,
				onlineVersion: onlineVersion
				}
			    $.fileDownload("../relateView/svcDetailExport/"+JSON.stringify(params), {
            	});
			    
			});

});

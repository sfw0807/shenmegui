$(function() {
	var tables = {};
	var asInitVals = new Array();
	$('#tabs').tabs();
	$("#tab0").click(function(e) {
		if(tables["slaTable"]){
			tables["slaTable"].fnAdjustColumnSizing();
		}
	});
	/**
	 * init service interface relate table 
	 * @param {Object} result
	 * 
	 */
	var isIE  = (navigator.appVersion.indexOf("MSIE") != -1) ? true : false;
	if(isIE){
		$("#slaTable").attr("style","width:1360px");
	}
	var initslaTable = function initslaTable(result) {
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#slaTable tbody tr").unbind("click");
			$("#slaTable tbody tr").click(function(e) {
				$(this).toggleClass("row_selected");
			});
		};
		//初始化slaTable
		tables["slaTable"] = $("#slaTable").dataTable( {
			"aaData" : result,
			"aoColumns" : slaTableLayout,
			"aoColumnDefs" : [
				{
					"sClass" : "center",
					"aTargets" : [ 0, 1, 2, 3, 4, 5 , 6, 7,8]
				}
			],
			"bJQueryUI": true,
			"bAutoWidth" : true,
			//"sScrollY" : "500px",
			//"sScrollX" : "500px",
			"bScrollCollapse" : "full_numbers",
			"bPaginate" : true,
			"bSort" : true,
			"oLanguage" : oLanguage,
			"fnDrawCallback" : function() {
				columnClickEventInit();
			}
		});
		tables["slaTable"].fnAdjustColumnSizing();
	};
	slaManager.getAllSLAInfo(initslaTable);

	//初始化操作Grid的搜索框
	var initslaTableFooter = function initslaTableFooter() {
		$("#slaTable tfoot input").keyup(
				function() {
					var param = this.value;
					var flag = param.indexOf(" ");
					if(flag!=-1){
						while(param.indexOf(" ")!=-1){
							param = param.replace(" ","");
						}
						tables["slaTable"].fnFilter(param, $("#slaTable tfoot input").index(this),null,null,null,false);
					}else{
						tables["slaTable"].fnFilter(this.value, $(
							"#slaTable tfoot input").index(this),null,null,null,false);
					}

				});
		$("#slaTable tfoot input").each(function(i) {
			asInitVals[i] = this.value;
		});
		$("#slaTable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#slaTable tfoot input")
				.blur(
						function(i) {
							if (this.value == "") {
								this.className = "search_init";
								this.value = asInitVals[$(
										"#slaTable tfoot input")
										.index(this)];
							}
						});
	};
	initslaTableFooter();
	
	$('#export').button().click(function () {
				var service = ($('#serviceInfo').val() == '服务ID/名称')?"":$('#serviceInfo').val();		 
				var operation = ($('#operationInfo').val() == '操作ID/名称')?"":$('#operationInfo').val();
				var interfaces = ($('#interfaceInfo').val() == '交易代码/名称')?"":$('#interfaceInfo').val();
				var provideSys = ($('#provideSysInfo').val() == '提供方系统简称/名称')?"":$('#provideSysInfo').val();
				var successRate = ($('#successRate').val() == '业务成功率')?"":$('#successRate').val();
				var timeOut = ($('#timeOut').val() == '超时时间')?"":$('#timeOut').val();
				var currentCount = ($('#currentCount').val() == '并发数')?"":$('#currentCount').val();
				var averageTime = ($('#averageTime').val() == '平均响应时间')?"":$('#averageTime').val();		
				var params ={
					service : service,
					operation: operation,
					interfaces: interfaces,
					provideSys: provideSys,
					successRate: successRate,
					timeOut: timeOut,
					currentCount: currentCount,
					averageTime:averageTime
				};
				$.fileDownload(encodeURI(encodeURI("../slaview/export/"+JSON.stringify(params))), {});
			});
});

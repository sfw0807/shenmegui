$(function() {
	var tables = {};
	var asInitVals = new Array();
	$('#tabs').tabs();
	$("#tab0").click(function(e) {
		if(tables["consumerTable"]){
			tables["consumerTable"].fnAdjustColumnSizing();
		}
	});
	/**
	 * init service interface relate table 
	 * @param {Object} result
	 * 
	 */

	// 从URL中得到invokeInfo
	var href = window.location.href;
	var hrefparam = href.split("?")[1];	
	var operationInfo = hrefparam.split("&")[0];
	if(operationInfo.indexOf("%27")>0){
		
	}else{
		
	}
	var operationId = operationInfo.split("=")[1];	
	var serviceInfo = hrefparam.split("&")[1];
	var serviceId = serviceInfo.split("=")[1];	
	var interfaceInfo = hrefparam.split("&")[2];
	var interfaceId = interfaceInfo.split("=")[1];	
	var provideMsgInfo = hrefparam.split("&")[3];
	var provideMsgType = provideMsgInfo.split("=")[1];	
	var consumeMsgInfo = hrefparam.split("&")[4];
	var consumeMsgType = consumeMsgInfo.split("=")[1];	
	var throughInfo = hrefparam.split("&")[5];
	var through = throughInfo.split("=")[1];	
	if(through=="Y"){
		through="是";
	}else if(through=="N"){
		through="否";
	}
	var stateInfo = hrefparam.split("&")[6];
	var state = stateInfo.split("=")[1];	
	if(state=="def"){
		state="服务定义"
	}else if(state=="dev"){
		state="开发"
	}else if(state=="union"){
		state="联调测试"
	}else if(state=="sit"){
		state="sit测试"
	}else if(state=="uat"){
		state="uat测试"
	}else if(state=="valid"){
		state="投产验证"
	}else if(state=="online"){
		state="上线"
	}
	var onlineVersionInfo = hrefparam.split("&")[7];
	var onlineVersion = onlineVersionInfo.split("=")[1];	
	var onlineDateInfo = hrefparam.split("&")[8];
	var onlineDate = onlineDateInfo.split("=")[1];
	var privodeSysInfo = hrefparam.split("&")[9];
	var privodeSysAb = privodeSysInfo.split("=")[1];
//	var params = {
//		operationId:operationId,
//		serviceId:serviceId,
//		interfaceId:interfaceId,
//		provideMsgType:provideMsgType,
//		consumeMsgType:consumeMsgType
//    };	
	var params=[operationId,serviceId,interfaceId,provideMsgType,consumeMsgType];
	var initconsumerTable = function initconsumerTable(result) {
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#consumerTable tbody tr").unbind("click");
			$("#consumerTable tbody tr").click(function(e) {
				$(this).toggleClass("row_selected");
			});
		};
		//初始化invokeTable
		tables["consumerTable"] = $("#consumerTable").dataTable( {
			"aaData" : result,
			"aoColumns" : consumerTableLayout,
			"aoColumnDefs" : [
				{
					"sClass" : "center",
					"aTargets" : [0,1,2,3]
				}		
			],
			"bJQueryUI": true,
			"bAutoWidth" : true,
			"bScrollCollapse" : "full_numbers",
			"bPaginate" : true,
			"bSort" : true,
			"bFilter":true,
			"bSearchable":true,
			"oLanguage" :oLanguage,
			"fnDrawCallback" : function() {
				columnClickEventInit();
			}
		});
		tables["consumerTable"].fnAdjustColumnSizing();
	};
	consumerManager.getConsumer(params,initconsumerTable);

	//初始化操作Grid的搜索框
	var initconsumerTableFooter = function initconsumerTableFooter() {
		$("#consumerTable tfoot input").keyup(
				function() {
					tables["consumerTable"].fnFilter(this.value, $(
							"#consumerTable tfoot input").index(this),null,null,null,false);
				});
		$("#consumerTable tfoot input").each(function(i) {
			asInitVals[i] = this.value;
		});
		$("#consumerTable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#consumerTable tfoot input")
				.blur(
						function(i) {
							if (this.value == "") {
								this.className = "search_init";
								this.value = asInitVals[$(
										"#consumerTable tfoot input")
										.index(this)];
							}
						});
	};
	initconsumerTableFooter();
	//所有系统信息的下拉框
	function initSystemInfo() {
          $.ajax({
            url: '../serviceDevInfo/getAllSystem',
            type: 'GET',
            success: function(result) {
                initSelect(result);
            }
          });
    }
    function initSelect(result) {
		for (var i=0;i<result.length;i++)
			$('#system').append("<option value='"+result[i].systemId+"'>"+result[i].systemAb+":"+result[i].systemName+"</option>");
	}
	initSystemInfo();
        
	$("#addConsumer").click(function (){
		var sysId = $("#system").val();
		var params=[operationId,serviceId,interfaceId,provideMsgType,
			consumeMsgType,through,state,onlineVersion,onlineDate,sysId,privodeSysAb];
		consumerManager.addConsumer(params);
	});
});
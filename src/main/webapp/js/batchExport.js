$(function() {
	var tables = {};
	var asInitVals = new Array();
	$('#tabs').tabs();
	$("#tab0").click(function(e) {
		if(tables["batchExportTable"]){
			tables["batchExportTable"].fnAdjustColumnSizing();
		}
	});
	/**
	 * init service interface relate table 
	 * @param {Object} result
	 * 
	 */
	var initbatchExportTable = function initbatchExportTable(result) {
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#batchExportTable tbody tr").unbind("click");
			$("#batchExportTable tbody tr").click(function(e) {
				if($(this).find("td").hasClass("dataTables_empty")){
			    }
			    else{
				$(this).toggleClass("row_selected");
				}
			});
		};
		//初始化batchExportTable
		tables["batchExportTable"] = $("#batchExportTable").dataTable( {
			"aaData" : result,
			"aoColumns" : batchExportTableLayout,
			"aoColumnDefs" : [
				{
					"sClass" : "center",
					"aTargets" : [ 0, 1, 2, 3, 4, 5, 6]
				}
			],
			"bJQueryUI": true,
			"bAutoWidth" : true,
			"bScrollCollapse" : "full_numbers",
			"bPaginate" : true,
			"bSort" : false,
			"oLanguage" : oLanguage,
			"bFilter" : true,
			"fnDrawCallback" : function() {
				columnClickEventInit();
			}
		});
		tables["batchExportTable"].fnAdjustColumnSizing();
	};
	batchExportManager.getBatchDuplicateInvokeInfo("@@@@@",initbatchExportTable);

	//初始化操作Grid的搜索框
	var initbatchExportTableFooter = function initbatchExportTableFooter() {
		$("#batchExportTable tfoot input").keyup(
				function() {
					var param = this.value;
					var flag = param.indexOf(" ");
					if(flag!=-1){
						while(param.indexOf(" ")!=-1){
							param = param.replace(" ","");
						}
						tables["batchExportTable"].fnFilter(param, $("#batchExportTable tfoot input").index(this),null,null,null,false);
					}else{
						tables["batchExportTable"].fnFilter(this.value, $(
							"#batchExportTable tfoot input").index(this),null,null,null,false);
					}

				});
		$("#batchExportTable tfoot input").each(function(i) {
			asInitVals[i] = this.value;
		});
		$("#batchExportTable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#batchExportTable tfoot input")
				.blur(
						function(i) {
							if (this.value == "") {
								this.className = "search_init";
								this.value = asInitVals[$(
										"#batchExportTable tfoot input")
										.index(this)];
							}
						});
	};
	initbatchExportTableFooter();
	$('#search').button().click(function(){
	             var params = $('#area').val();
                 if(params == null || params == ''){
                   alert('请输入交易码!');
                   return false;
                 }
                 if(!checkRegexp(params)){
                   alert('交易码只能是数字或者英文字母!');
                   return false;
                 }
                 // 检查是否存在多条配置导出的记录
				 if (tables["batchExportTable"]) {
					 tables["batchExportTable"].fnDestroy();
				 }
                 batchExportManager.getBatchDuplicateInvokeInfo(params,initbatchExportTable);
			     initbatchExportTableFooter();
	});
	
	$('#export').button().click(function(){
                 var params = $('#area').val();
                 if(params == null || params == ''){
                   alert('请输入交易码!');
                   return false;
                 }
                 if(!checkRegexp(params)){
                   alert('交易码只能是数字或者英文字母!');
                   return false;
                 }
                 function checkTips(result){
	                 if(result != null && result != ""){
	                     alert('接口 [' + result + ']不存在,无法导出!');
	                     return false;
	                 }
	                 else{
	                     function callBack(result){
			                if(result == null || result == ''){
			                   var table = tables["batchExportTable"];
			                   var rowsSelected = table.$("tr.row_selected");
			                   if(rowsSelected.length >= 1){
			                       var selectedDatas = [];
							       for (var i = 0; i < rowsSelected.length; i++) {
							           selectedDatas[i] = table.fnGetData(table.$("tr.row_selected")[i]);
							           var serviceId = selectedDatas[i]["serviceId"];
							           var operationId = selectedDatas[i]["operationId"];
							           var interfaceId = selectedDatas[i]["interfaceId"];
							           var provideMsgType = selectedDatas[i]["provideMsgType"];
							           var consumeMsgType = selectedDatas[i]["consumeMsgType"];
							           var tmp = serviceId+ ":"+operationId+ ":"+interfaceId+ ":"+provideMsgType+ ":"+consumeMsgType;
							           params = params.replace(interfaceId,"");
							           params = params.replace(",,",",");
							           params += "," + tmp;
							       }
							      $.fileDownload("../export/batchConfig/" + params, {});
			                   }
			                   else{
			                      $.fileDownload("../export/batchConfig/" + params, {});
			                   }
			                   
			                }
			                else{
			                   alert('服务或操作 [' + result + "] 未审核通过，请先审核通过!");
			                }
			             }
			             batchExportManager.checkEcodeAudit(params, callBack);
	                 }
                 };
                 // 检查接口是否存在
                 batchExportManager.checkExitsInterface(params, checkTips);
              });
	
});
 // 校验正则表达式
     	function checkRegexp(value) {
			if ( /^([0-9a-zA-Z.,])+$/.test(value)) {
				return true;
			}
			else{
			    return false;
			}
	    }

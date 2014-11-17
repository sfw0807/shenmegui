$(function() {
	var tables = {};
	var asInitVals = new Array();
    var params = {
	 prdMsgType : "",
	 csmMsgType : "",
	 onlineDate : ""
	 };
	$('#tabs').tabs();
	$("#tab0").click(function(e) {
		if(tables["publishInfoTable"]){
			tables["publishInfoTable"].fnAdjustColumnSizing();
		}
	});
	/**
	 * init service interface relate table 
	 * @param {Object} result
	 * 
	 */
	var initpublishInfoTable = function initpublishInfoTable(result) {

		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#publishInfoTable tbody tr").unbind("click");
			$("#publishInfoTable tbody tr").click(function(e) {
				$(this).toggleClass("row_selected");
			});
		};
		//初始化publishInfoTable
	 	if (tables["publishInfoTable"]) {
            tables["publishInfoTable"].fnDestroy();
        }
		tables["publishInfoTable"] = $("#publishInfoTable").dataTable( {
			"aaData" : result,
			"aoColumns" : publishInfoTableLayout,
			"aoColumnDefs" : [
				{
					"sClass" : "center",
					"aTargets" : [ 0, 1, 2, 3, 4, 5 , 6, 7]
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
		tables["publishInfoTable"].fnAdjustColumnSizing();
	};
	publishInfoManager.getAllPublishTotalInfos(JSON.stringify(params),initpublishInfoTable);

	//初始化操作Grid的搜索框
	var initpublishInfoTableFooter = function initpublishInfoTableFooter() {
		$("#publishInfoTable tfoot input").keyup(
				function() {
					tables["publishInfoTable"].fnFilter(this.value, $(
							"#publishInfoTable tfoot input").index(this),null,null,null,false);
				});
		$("#publishInfoTable tfoot input").each(function(i) {
			asInitVals[i] = this.value;
		});
		$("#publishInfoTable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#publishInfoTable tfoot input")
				.blur(
						function(i) {
							if (this.value == "") {
								this.className = "search_init";
								this.value = asInitVals[$(
										"#publishInfoTable tfoot input")
										.index(this)];
							}
						});
	};
	initpublishInfoTableFooter();
	
	$('#export').button().click(function () {
	
				var prdMsgType = $('#provideMsgType').val();
				var csmMsgType = $('#consumeMsgType').val();
				var onlineDate = ($('#onlinedate').val() == '上线日期')?"":$('#onlinedate').val();
				params.prdMsgType = prdMsgType;
                params.csmMsgType = csmMsgType;
                params.onlineDate = onlineDate;
			    $.fileDownload("../publish/export/"+JSON.stringify(params), {
            	});
			});
			
	$('#search').button().click(function () {
        var prdMsgType = $('#provideMsgType').val();
        var csmMsgType = $('#consumeMsgType').val();
        var onlineDate = ($('#onlinedate').val() == '上线日期')?"":$('#onlinedate').val();
        params.prdMsgType = prdMsgType;
        params.csmMsgType = csmMsgType;
        params.onlineDate = onlineDate;
        publishInfoManager.getAllPublishTotalInfos(JSON.stringify(params),initpublishInfoTable);
	});

});

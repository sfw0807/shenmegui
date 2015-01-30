$(function() {
    // 获取元数据ID参数
    var isChrome = (navigator.appVersion.indexOf("Chrome") != -1) ? true : false;
    var id;
    if(isChrome){
        var href = window.location.href;
        id = href.split("=")[1];
    }else{
        id = window.dialogArguments;
    }

	var tables = {};
	var asInitVals = new Array();
	$('#tabs').tabs();
	$("#tab0").click(function(e) {
		if(tables["operationsByServiceIdTable"]){
			tables["operationsByServiceIdTable"].fnAdjustColumnSizing();
		}
	});
	/**
	 * init service interface relate table 
	 * @param {Object} result
	 * 
	 */
	var initoperationsByServiceIdTable = function initoperationsByServiceIdTable(result) {
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#operationsByServiceIdTable tbody tr").unbind("click");
			$("#operationsByServiceIdTable tbody tr").click(function(e) {
				$(this).toggleClass("row_selected");
			});
		};
		//初始化operationsByServiceIdTable
		tables["operationsByServiceIdTable"] = $("#operationsByServiceIdTable").dataTable( {
			"aaData" : result,
			"aoColumns" : operationsByServiceIdTableLayout,
			"aoColumnDefs" : [
				{
					"sClass" : "center",
					"aTargets" : [ 0, 1, 2, 3, 4, 5, 6]
				}
			],
			"bJQueryUI": "true",
			"bAutoWidth" : "true",
			"bScrollCollapse" : "full_numbers",
			"bPaginate" : "true",
			"bSort" : "true",
			"oLanguage" : oLanguage,
			"fnDrawCallback" : function() {
				columnClickEventInit();
			}
		});
		tables["operationsByServiceIdTable"].fnAdjustColumnSizing();
	};
	serviceInfoManager.getOperationsById(id,initoperationsByServiceIdTable);

	//初始化操作Grid的搜索框
	var initoperationsByServiceIdTableFooter = function initoperationsByServiceIdTableFooter() {
		$("#operationsByServiceIdTable tfoot input").keyup(
				function() {
					tables["operationsByServiceIdTable"].fnFilter(this.value, $(
							"#operationsByServiceIdTable tfoot input").index(this),null,null,null,false);
				});
		$("#operationsByServiceIdTable tfoot input").each(function(i) {
			asInitVals[i] = this.value;
		});
		$("#operationsByServiceIdTable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#operationsByServiceIdTable tfoot input")
				.blur(
						function(i) {
							if (this.value == "") {
								this.className = "search_init";
								this.value = asInitVals[$(
										"#operationsByServiceIdTable tfoot input")
										.index(this)];
							}
						});
	};
	initoperationsByServiceIdTableFooter();
});

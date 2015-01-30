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
	var asInitVals = [];
	$('#tabs').tabs();
	$("#tab0").click(function(e) {
		if(tables["mdtUsedTable"]){
			tables["mdtUsedTable"].fnAdjustColumnSizing();
		}
	});
	/**
	 * init service interface relate table 
	 * @param {Object} result
	 * 
	 */
	var initmdtUsedTable = function initmdtUsedTable(result) {
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#mdtUsedTable tbody tr").unbind("click");
			$("#mdtUsedTable tbody tr").click(function(e) {
				$(this).toggleClass("row_selected");
			});
		};
		//初始化mdtUsedTable
		tables["mdtUsedTable"] = $("#mdtUsedTable").dataTable( {
			"aaData" : result,
			"aoColumns" : mdtUsedTableLayout,
			"aoColumnDefs" : [
				{
					"sClass" : "center",
					"aTargets" : [ 0, 1, 2, 3, 4, 5, 6]
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
		tables["mdtUsedTable"].fnAdjustColumnSizing();
	};
	metadataManager.getMdtUsedById(id,initmdtUsedTable);

	//初始化操作Grid的搜索框
	var initmdtUsedTableFooter = function initmdtUsedTableFooter() {
		$("#mdtUsedTable tfoot input").keyup(
				function() {
					tables["mdtUsedTable"].fnFilter(this.value, $(
							"#mdtUsedTable tfoot input").index(this),null,null,null,false);
				});
		$("#mdtUsedTable tfoot input").each(function(i) {
			asInitVals[i] = this.value;
		});
		$("#mdtUsedTable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#mdtUsedTable tfoot input")
				.blur(
						function(i) {
							if (this.value == "") {
								this.className = "search_init";
								this.value = asInitVals[$(
										"#mdtUsedTable tfoot input")
										.index(this)];
							}
						});
	};
	initmdtUsedTableFooter();

});

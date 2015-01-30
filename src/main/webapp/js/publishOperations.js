$(function() {
	var tables = {};
	var asInitVals = new Array();
	/**
	 * init serviceInfo table 
	 * @param {Object} result
	 * 
	 */
	var initpublishOperationsTable = function initpublishOperationsTable(result) {
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#publishOperationsTable tbody tr").unbind("click");
			$("#publishOperationsTable tbody tr").click(function(e) {
				if($(this).find("td").hasClass("dataTables_empty")){
			    }
			    else{
				$(this).toggleClass("row_selected");
				}
			});
		};
		//初始化publishOperationsTable
	 	if (tables["publishOperationsTable"]) {
            tables["publishOperationsTable"].fnDestroy();
        }
		tables["publishOperationsTable"] = $("#publishOperationsTable").dataTable( {
			"aaData" : result,
			"aoColumns" : publishOperationsTableLayout,
			"aoColumnDefs" : [
				{
					"sClass" : "center",
					"aTargets" : [ 0, 1, 2, 3]
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
		tables["publishOperationsTable"].fnAdjustColumnSizing();
	};
	var href = window.location.href;
	var phref = href.split('?')[1];
	var onlineDate = phref.split('&')[0].split('=')[1];
	var prdMsgType = phref.split('&')[1].split('=')[1];
	var csmMsgType = phref.split('&')[2].split('=')[1];
	var param = onlineDate + "," + prdMsgType + "," + csmMsgType;
	var flag = phref.split('&')[3].split('=')[1];
	if(flag == '0'){
	   publishInfoManager.getPublishOperations(param, initpublishOperationsTable);
	}
	else if (flag == '1'){
	   publishInfoManager.getPublishAddOperations(param, initpublishOperationsTable);
	}
	else{
	   publishInfoManager.getPublishModifyOperations(param, initpublishOperationsTable);
	}

	//初始化操作Grid的搜索框
	var initpublishOperationsTableFooter = function initpublishOperationsTableFooter() {
		$("#publishOperationsTable tfoot input").keyup(
				function() {
					tables["publishOperationsTable"].fnFilter(this.value, $(
							"#publishOperationsTable tfoot input").index(this),null,null,null,false);
				});
		$("#publishOperationsTable tfoot input").each(function(i) {
			asInitVals[i] = this.value;
		});
		$("#publishOperationsTable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#publishOperationsTable tfoot input")
				.blur(
						function(i) {
							if (this.value == "") {
								this.className = "search_init";
								this.value = asInitVals[$(
										"#publishOperationsTable tfoot input")
										.index(this)];
							}
						});
	};
	initpublishOperationsTableFooter();
});

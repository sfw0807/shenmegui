$(function() {
	var tables = {};
	var asInitVals = new Array();

	/**
	 * init serviceInfo table 
	 * @param {Object} result
	 * 
	 */
	var initpublishServicesTable = function initpublishServicesTable(result) {
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#publishServicesTable tbody tr").unbind("click");
			$("#publishServicesTable tbody tr").click(function(e) {
				if($(this).find("td").hasClass("dataTables_empty")){
			    }
			    else{
				$(this).toggleClass("row_selected");
				}
			});
		};
		//初始化publishServicesTable
	 	if (tables["publishServicesTable"]) {
            tables["publishServicesTable"].fnDestroy();
        }
		tables["publishServicesTable"] = $("#publishServicesTable").dataTable( {
			"aaData" : result,
			"aoColumns" : publishServicesTableLayout,
			"aoColumnDefs" : [
				{
					"sClass" : "center",
					"aTargets" : [ 0, 1]
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
		tables["publishServicesTable"].fnAdjustColumnSizing();
	};
	var href = window.location.href;
	var phref = href.split('?')[1];
	var onlineDate = phref.split('&')[0].split('=')[1];
	var prdMsgType = phref.split('&')[1].split('=')[1];
	var csmMsgType = phref.split('&')[2].split('=')[1];
	var param = onlineDate + "," + prdMsgType + "," + csmMsgType;
	var flag = phref.split('&')[3].split('=')[1];
	if(flag == '0'){
	   publishInfoManager.getPublishServices(param, initpublishServicesTable);
	}
	else if (flag == '1'){
	   publishInfoManager.getPublishAddServices(param, initpublishServicesTable);
	}
	else{
	   publishInfoManager.getPublishModifyServices(param, initpublishServicesTable);
	}
	

	//初始化操作Grid的搜索框
	var initpublishServicesTableFooter = function initpublishServicesTableFooter() {
		$("#publishServicesTable tfoot input").keyup(
				function() {
					tables["publishServicesTable"].fnFilter(this.value, $(
							"#publishServicesTable tfoot input").index(this),null,null,null,false);
				});
		$("#publishServicesTable tfoot input").each(function(i) {
			asInitVals[i] = this.value;
		});
		$("#publishServicesTable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#publishServicesTable tfoot input")
				.blur(
						function(i) {
							if (this.value == "") {
								this.className = "search_init";
								this.value = asInitVals[$(
										"#publishServicesTable tfoot input")
										.index(this)];
							}
						});
	};
	initpublishServicesTableFooter();
});

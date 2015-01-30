$(function() {
	
	var tables = {};
	var asInitVals = new Array();
	var reg = '';
	//初始化服务Grid的方法
	var initTable = function initTable(result) {
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#operation-log-table tbody tr").unbind("click");
			$("#operation-log-table tbody tr").click(function() {
				 $(this).toggleClass("row_selected");
			});
		};
		//创建Grid
		tables["operation-log-table"] = $("#operation-log-table").dataTable( {
			"aaData" : result,
			"aoColumns" : logTableLayout,
			"aoColumnDefs" : [
				{
					"sClass" : "center",
					"aTargets" : [ 0, 1, 2, 3, 4]
				}
			],
			"bJQueryUI": true,
			"bAutoWidth" : true,
			"sPaginationType" : "full_numbers",
			"oLanguage" : oLanguage,
			"fnDrawCallback" : function() {
				columnClickEventInit();
			}
		});
	};
	
	$.ajax({
        url: '../logquery/getLogList',
        type: 'GET',
        success: function(result) {
            initTable(result);
        }
    });
	
	//tables["operation-log-table"].fnAdjustColumnSizing();

	//初始化操作Grid的搜索框
	var initTableFooter = function initTableFooter() {
		$("#operation-log-table tfoot input").keyup(
				function() {
					tables["operation-log-table"].fnFilter(this.value, $(
							"#operation-log-table tfoot input").index(this));
				});
		$("#operation-log-table tfoot input").each(function(i) {
			asInitVals[i] = this.value;
		});
		$("#operation-log-table tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#operation-log-table tfoot input").blur(
				function(i) {
					if (this.value == "") {
						this.className = "search_init";
						this.value = asInitVals[$("#operation-log-table tfoot input")
								.index(this)];
					}
				});
	};
	
	initTableFooter();
	
});


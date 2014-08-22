/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-6-4
 * Time: 上午10:15
 * To change this template use File | Settings | File Templates.
 */
$(function () {

    var tables = {};
    
    var getSelectedFromTable = function getSelectedFromTable(table) {
        var rowsSelected = table.$("tr.row_selected");
        var selectedDatas = [];
        for (var i = 0; i < rowsSelected.length; i++) {
            selectedDatas[i] = table.fnGetData(table.$("tr.row_selected")[i]);
        }
        return selectedDatas;
    };

    var initSIRCheckTable = function initSIRCheckTable(result) {
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#sirCheckTable tbody tr").unbind("click");
			$("#sirCheckTable tbody tr").click(function(e) {
				$(this).toggleClass("row_selected");
			});
		};
		//创建Grid

		tables["sirCheckTable"] = $("#sirCheckTable").dataTable( {
			"aaData" : result,
			"aoColumns" : sirTableLayout,
			"aoColumnDefs" : [ 
			],
			"bJQueryUI": true,
			"bAutoWidth" : true,
			"sScrollY" : "500px",
			"bScrollCollapse" : true,
			"sPaginationType" : "full_numbers",
//			"bPaginate" : false,
//			"bSort" : false,
			"oLanguage" : oLanguage,
			"fnDrawCallback" : function() {
				columnClickEventInit();
			}
		});
		
//		tables["childTable"].fnAdjustColumnSizing();
	};
	result = sirManager.getDupSIR(initSIRCheckTable);
});

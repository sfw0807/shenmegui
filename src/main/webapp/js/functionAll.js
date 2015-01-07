$(function() {
	var tables = {};
	var asInitVals = new Array();
	$('#tabs').tabs();
	$("#tab0").click(function(e) {
		if(tables["functionTable"]){
			tables["functionTable"].fnAdjustColumnSizing();
		}
	});
	/**
	 * init service interface relate table 
	 * @param {Object} result
	 * 
	 */
	var initfunctionTable = function initfunctionTable(result) {
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#functionTable tbody tr").unbind("click");
			$("#functionTable tbody tr").click(function(e) {
				$(this).toggleClass("row_selected");
			});
		};
		//初始化operationTable
		tables["functionTable"] = $("#functionTable").dataTable( {
			"aaData" : result,
			"aoColumns" : functionTableLayout,
			"aoColumnDefs" : [
				{
					"sClass" : "center",
					"aTargets" : [0,1,2,3,4]
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
		tables["functionTable"].fnAdjustColumnSizing();
	};
	functionManager.getAll(initfunctionTable);

	//初始化操作Grid的搜索框
	var initfunctionTableFooter = function initfunctionTableFooter() {
		$("#functionTable tfoot input").keyup(
				function() {
					tables["functionTable"].fnFilter(this.value, $(
							"#functionTable tfoot input").index(this),null,null,null,false);
				});
		$("#functionTable tfoot input").each(function(i) {
			asInitVals[i] = this.value;
		});
		$("#functionTable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#functionTable tfoot input")
				.blur(
						function(i) {
							if (this.value == "") {
								this.className = "search_init";
								this.value = asInitVals[$(
										"#functionTable tfoot input")
										.index(this)];
							}
						});
	};
	initfunctionTableFooter();
	$("#deleteFunction").button().click(function(){
		var table = tables["functionTable"];
		var rowsSelected = table.$("tr.row_selected");
		if(rowsSelected.length!=1){
			alert("每次只能删除一个权限!");
		}else{
			var functionId = rowsSelected[0].cells[0].innerText;
			functionManager.deleteFunction(functionId);
		}
	});
	$("#addFunction").button().click(function(){
		window.location="addFunction.jsp";
	});
});
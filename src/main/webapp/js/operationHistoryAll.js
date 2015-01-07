$(function() {
	var tables = {};
	var asInitVals = new Array();
	$('#tabs').tabs();
	$("#tab0").click(function(e) {
		if(tables["operationHistoryTable"]){
			tables["operationHistoryTable"].fnAdjustColumnSizing();
		}
	});
	/**
	 * init service interface relate table 
	 * @param {Object} result
	 * 
	 */
	var initoperationTable = function initoperationTable(result) {
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#operationHistoryTable tbody tr").unbind("click");
			$("#operationHistoryTable tbody tr").click(function(e) {
				$(this).toggleClass("row_selected");
			});
		};
		//初始化operationTable
		tables["operationHistoryTable"] = $("#operationHistoryTable").dataTable( {
			"aaData" : result,
			"aoColumns" : operationHistoryTableLayout,
			"aoColumnDefs" : [
				{
					"sClass" : "center",
					"aTargets" : [0,1,2,3,4,5,6,7,8,9]
				},
				{
					"mRender" : function ( data, type, row ) {
						return '<a href="operationHistoryInfoById.jsp?operationId='+row["operationId"]
							+'&serviceId='+row["serviceId"]
							+'&version='+row["operationVersion"]
							+'">' + '查看' + '</a>';
					},
					"aTargets" : [7]
				}
			],
			"bJQueryUI": true,
			"bAutoWidth" : true,
			"bScrollCollapse" : "full_numbers",
			"bPaginate" : true,
			"bSort" : true,			
			"oLanguage" :oLanguage,
			"fnDrawCallback" : function() {
				columnClickEventInit();
			}
		});
		tables["operationHistoryTable"].fnAdjustColumnSizing();
	};
	// 从URL中得到operationId
	var href = window.location.href;
	var operationId = href.split("&")[0].split("=")[1];
	var serviceId = href.split("&")[1].split("=")[1];
	var version = href.split("&")[2].split("=")[1];
	if(href.split("&").length>=4){
		publishVersion = href.split("&")[3].split("=")[1];
		publishDate = href.split("&")[4].split("=")[1];
	}
	if(operationId.split("%27").length>=2){
		operationId = operationId.split("%27")[1];
	}
	if(serviceId.split("%27").length>=2){
		serviceId = serviceId.split("%27")[1];
	}
	if(version.split("%27").length>=2){
		version = version.split("%27")[1];
	}
	operationHistoryManager.getAllHistory(operationId,serviceId,initoperationTable);

	//初始化操作Grid的搜索框
	var initoperationTableFooter = function initoperationTableFooter() {
		$("#operationHistoryTable tfoot input").keyup(
				function() {
					tables["operationHistoryTable"].fnFilter(this.value, $(
							"#operationHistoryTable tfoot input").index(this),null,null,null,false);
				});
		$("#operationHistoryTable tfoot input").each(function(i) {
			asInitVals[i] = this.value;
		});
		$("#operationHistoryTable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#operationHistoryTable tfoot input")
				.blur(
						function(i) {
							if (this.value == "") {
								this.className = "search_init";
								this.value = asInitVals[$(
										"#operationHistoryTable tfoot input")
										.index(this)];
							}
						});
	};
	initoperationTableFooter();
	$("#backOperation").button().click(function(){
		var table = tables["operationHistoryTable"];
		var rowsSelected = table.$("tr.row_selected");
		if(rowsSelected.length!=1){
			alert("只能选中一条记录");
		}else{
			var operationId = rowsSelected[0].cells[0].innerText;
			var operationVersion = rowsSelected[0].cells[2].innerText;
			var serviceId = rowsSelected[0].cells[4].innerText;
			var params = [operationId,operationVersion,serviceId];
			operationHistoryManager.backOperation(params);
		}
	});
});
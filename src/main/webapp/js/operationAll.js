$(function() {
	var tables = {};
	var asInitVals = new Array();
	$('#tabs').tabs();
	$("#tab0").click(function(e) {
		if(tables["operationTable"]){
			tables["operationTable"].fnAdjustColumnSizing();
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
			$("#operationTable tbody tr").unbind("click");
			$("#operationTable tbody tr").click(function(e) {
				$(this).toggleClass("row_selected");
			});
		};
		//初始化operationTable
		tables["operationTable"] = $("#operationTable").dataTable( {
			"aaData" : result,
			"aoColumns" : operationTableLayout,
			"aoColumnDefs" : [
				{
					"sClass" : "center",
					"aTargets" : [0,1,2,3,4,5,6,7,8,9,10]
				},
				{
					"bVisible":false,
					"aTargets" : [6,7,10]
				},
				{
					"mRender" : function ( data, type, row ) {
						return '<a href="operationInfoById.jsp?operationId='+row["operationId"]
							+'&serviceId='+row["serviceId"]
							+'&version='+row["version"]
							+'&publishVersion='+row["publishVersion"]
							+'&publishDate='+row["publishDate"]
							+'">' + '修改' + '</a>';
					},
					"aTargets" : [8]
				},
				{
					"mRender" : function ( data, type, row ) {
						return '<a href="operationHistory.jsp?operationId='+row["operationId"]
							+'&serviceId='+row["serviceId"]
							+'&version='+row["version"]
							+'">' + '查看' + '</a>';
					},
					"aTargets" : [9]
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
		tables["operationTable"].fnAdjustColumnSizing();
	};
	operationManager.getAll(initoperationTable);

	//初始化操作Grid的搜索框
	var initoperationTableFooter = function initoperationTableFooter() {
		$("#operationTable tfoot input").keyup(
				function() {
					tables["operationTable"].fnFilter(this.value, $(
							"#operationTable tfoot input").index(this),null,null,null,false);
				});
		$("#operationTable tfoot input").each(function(i) {
			asInitVals[i] = this.value;
		});
		$("#operationTable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#operationTable tfoot input")
				.blur(
						function(i) {
							if (this.value == "") {
								this.className = "search_init";
								this.value = asInitVals[$(
										"#operationTable tfoot input")
										.index(this)];
							}
						});
	};
	initoperationTableFooter();
	$("#deleteOperation").button().click(function(){
		var table = tables["operationTable"];
		var rowsSelected = table.$("tr.row_selected");
		if(rowsSelected.length!=1){
			alert("每次只能删除一个操作!");
		}else{
			var serviceId = rowsSelected[0].cells[2].innerText;
			var operationId = rowsSelected[0].cells[0].innerText;
			operationManager.deleteOperation(operationId,serviceId);
		}
	});
	$("#addOperation").button().click(function(){
		window.location="addOperation.jsp";
	});
	$("#deployOperation").button().click(function(){
		var table = tables["operationTable"];
		var rowsSelected = table.$("tr.row_selected");
		if(rowsSelected.length!=1){
			alert("每次只能发布一个操作!");
		}else{
			var serviceId = rowsSelected[0].cells[2].innerText;
			var operationId = rowsSelected[0].cells[0].innerText;
			var state = rowsSelected[0].cells[4].innerText;
			if(state=="服务定义"){
				operationManager.deployOperation(operationId,serviceId);
			}else{
				alert("该操作状态为["+state+"],已发布");
			}
		}
	});
	$("#redefOperation").button().click(function(){
		var table = tables["operationTable"];
		var rowsSelected = table.$("tr.row_selected");
		if(rowsSelected.length!=1){
			alert("每次只能重定义一个操作!");
		}else{
			var serviceId = rowsSelected[0].cells[2].innerText;
			var operationId = rowsSelected[0].cells[0].innerText;
			var state = rowsSelected[0].cells[4].innerText;
			if(state=="服务定义"){
				alert("该操作状态为[服务定义],不能进行重定义");
			}else{
				operationManager.redefOperation(operationId,serviceId);
			}
		}
	});
	$("#publishOperation").button().click(function(){
		var table = tables["operationTable"];
		var rowsSelected = table.$("tr.row_selected");
		if(rowsSelected.length!=1){
			alert("每次只能上线一个操作!");
		}else{
			var serviceId = rowsSelected[0].cells[2].innerText;
			var operationId = rowsSelected[0].cells[0].innerText;
			var state = rowsSelected[0].cells[4].innerText;
			if(state=="投产验证"){
				operationManager.publishOperation(operationId,serviceId);
			}else if(state=="上线"){
				alert("该操作已经上线");
			}else{
				alert("该操作未经过投产验证,不能上线");
			}
		}
	});
});
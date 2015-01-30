$(function() {
	var tables = {};
	var asInitVals = new Array();
	$('#tabs').tabs();
	$("#tab0").click(function(e) {
		if(tables["tranLinkTable"]){
			tables["tranLinkTable"].fnAdjustColumnSizing();
		}
	});
	/**
	 * init service interface relate table 
	 * @param {Object} result
	 * 
	 */

	var inittranLinkTable = function initslaTable(result) {
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#tranLinkTable tbody tr").unbind("click");
			$("#tranLinkTable tbody tr").click(function(e) {
				$(this).toggleClass("row_selected");
			});
		};
		//初始化slaTable
		tables["tranLinkTable"] = $("#tranLinkTable").dataTable( {
			"aaData" : result,
			"aoColumns" : tranLinkTableLayout,
			"aoColumnDefs" : [
				{
					"sClass" : "center",
					"aTargets" : [ 0, 1, 2, 3, 4, 5]
				},
				{
					"bVisible":false,
					"aTargets" : [5]
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
		tables["tranLinkTable"].fnAdjustColumnSizing();
	};
	tranlinkManager.getAllTranlinkInfo(inittranLinkTable);

	//初始化操作Grid的搜索框
	var inittranLinkTableFooter = function initslaTableFooter() {
		$("#tranLinkTable tfoot input").keyup(
				function() {
					var param = this.value;
					var flag = param.indexOf(" ");
					if(flag!=-1){
						while(param.indexOf(" ")!=-1){
							param = param.replace(" ","");
						}
						tables["tranLinkTable"].fnFilter(param, $("#tranLinkTable tfoot input").index(this),null,null,null,false);
					}else{
						tables["tranLinkTable"].fnFilter(this.value, $(
							"#tranLinkTable tfoot input").index(this),null,null,null,false);
					}

				});
		$("#tranLinkTable tfoot input").each(function(i) {
			asInitVals[i] = this.value;
		});
		$("#tranLinkTable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#tranLinkTable tfoot input")
				.blur(
						function(i) {
							if (this.value == "") {
								this.className = "search_init";
								this.value = asInitVals[$(
										"#tranLinkTable tfoot input")
										.index(this)];
							}
						});
	};
	inittranLinkTableFooter();
	$("#addTranProvider").button().click(function(){
		window.location.href="addTranProvider.jsp";
	})
	$("#addTranConsumer").button().click(function(){
		window.location.href="addTranConsumer.jsp";
	})
});

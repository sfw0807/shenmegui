$(function() {
	var tables = {};
	var asInitVals = new Array();
	$('#tabs').tabs();
	$("#tab0").click(function(e) {
		if(tables["OrgTable"]){
			tables["OrgTable"].fnAdjustColumnSizing();
		}
	});
	/**
	 * init service interface relate table 
	 * @param {Object} result
	 * 
	 */
	var initOrgTable = function initOrgTable(result) {
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#OrgTable tbody tr").unbind("click");
			$("#OrgTable tbody tr").click(function(e) {
				$(this).toggleClass("row_selected");
			});
		};
		//初始化operationTable
		tables["OrgTable"] = $("#OrgTable").dataTable( {
			"aaData" : result,
			"aoColumns" : OrgTableLayout,
			"aoColumnDefs" : [
				{
					"sClass" : "center",
					"aTargets" : [0,1,2,3]
				},
				{
					"mRender" : function(data, type, row){
						if(data=="1"){
							return "正常";
						}else{
							return "无效";
						}
					},
					"aTargets" : [3]
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
		tables["OrgTable"].fnAdjustColumnSizing();
	};
	orgManager.getAll(initOrgTable);

	//初始化操作Grid的搜索框
	var initOrgTableFooter = function initOrgTableFooter() {
		$("#OrgTable tfoot input").keyup(
				function() {
					tables["OrgTable"].fnFilter(this.value, $(
							"#OrgTable tfoot input").index(this),null,null,null,false);
				});
		$("#OrgTable tfoot input").each(function(i) {
			asInitVals[i] = this.value;
		});
		$("#OrgTable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#OrgTable tfoot input")
				.blur(
						function(i) {
							if (this.value == "") {
								this.className = "search_init";
								this.value = asInitVals[$(
										"#OrgTable tfoot input")
										.index(this)];
							}
						});
	};
	initOrgTableFooter();
	$("#deleteOrg").button().click(function(){
		var table = tables["OrgTable"];
		var rowsSelected = table.$("tr.row_selected");
		if(rowsSelected.length!=1){
			alert("每次只能删除一个操作!");
		}else{
			var orgId = rowsSelected[0].cells[0].innerText;
			orgManager.deleteOrg(orgId);
		}
	});
	$("#addOrg").button().click(function(){
		window.location="addOrg.jsp";
	});
});
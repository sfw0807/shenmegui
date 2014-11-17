$(function() {
	var tables = {};
	var asInitVals = new Array();
	$('#tabs').tabs();
	$("#tab0").click(function(e) {
		if(tables["roleTable"]){
			tables["roleTable"].fnAdjustColumnSizing();
		}
	});
	/**
	 * init service interface relate table 
	 * @param {Object} result
	 * 
	 */
	var initroleTable = function initroleTable(result) {
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#roleTable tbody tr").unbind("click");
			$("#roleTable tbody tr").click(function(e) {
				$(this).toggleClass("row_selected");
			});
		};
		//初始化operationTable
		tables["roleTable"] = $("#roleTable").dataTable( {
			"aaData" : result,
			"aoColumns" : roleTableLayout1,
			"aoColumnDefs" : [
				{
					"sClass" : "center",
					"aTargets" : [0,1,2,3]
				},
				{
					"mRender":function(data,type,row){
						return "<a href='assignFunction.jsp?roleId="+data+"'>分配权限</a>";
					},
					"aTargets":[3]
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
		tables["roleTable"].fnAdjustColumnSizing();
	};
	roleManager.getAll(initroleTable);

	//初始化操作Grid的搜索框
	var initroleTableFooter = function initroleTableFooter() {
		$("#roleTable tfoot input").keyup(
				function() {
					tables["roleTable"].fnFilter(this.value, $(
							"#roleTable tfoot input").index(this),null,null,null,false);
				});
		$("#roleTable tfoot input").each(function(i) {
			asInitVals[i] = this.value;
		});
		$("#roleTable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#roleTable tfoot input")
				.blur(
						function(i) {
							if (this.value == "") {
								this.className = "search_init";
								this.value = asInitVals[$(
										"#roleTable tfoot input")
										.index(this)];
							}
						});
	};
	initroleTableFooter();
	$("#deleteRole").button().click(function(){
		var table = tables["roleTable"];
		var rowsSelected = table.$("tr.row_selected");
		if(rowsSelected.length!=1){
			alert("每次只能删除一个角色!");
		}else{
			var roleId = rowsSelected[0].cells[0].innerText;
			roleManager.deleteRole(roleId);
		}
	});
	$("#addRole").button().click(function(){
		window.location="addRole.jsp";
	});
});
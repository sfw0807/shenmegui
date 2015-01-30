$(function() {
	var tables = {};
	var asInitVals = new Array();
	$('#tabs').tabs();
	$("#tab0").click(function(e) {
		if(tables["userTable"]){
			tables["userTable"].fnAdjustColumnSizing();
		}
	});
	/**
	 * init service interface relate table 
	 * @param {Object} result
	 * 
	 */
	var inituserTable = function inituserTable(result) {
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#userTable tbody tr").unbind("click");
			$("#userTable tbody tr").click(function(e) {
				$(this).toggleClass("row_selected");
			});
		};
		//初始化operationTable
		tables["userTable"] = $("#userTable").dataTable( {
			"aaData" : result,
			"aoColumns" : userTableLayout,
			"aoColumnDefs" : [
				{
					"sClass" : "center",
					"aTargets" : [0,1,2,3,4,5,6,7]
				},
				{
					"mRender" : function ( data, type, row ) {
						if("1"==data){
							return "正常";
						}else{
							return "无效";
						}
					},
					"aTargets" : [5]
				},
				{
					"mRender" : function ( data, type, row ) {
						return "<a href='assignRole.jsp?userId="+data+"'>分配角色</a>";
					},
					"aTargets" : [7]
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
		tables["userTable"].fnAdjustColumnSizing();
	};
	userManager.getAll(inituserTable);

	//初始化操作Grid的搜索框
	var inituserTableFooter = function inituserTableFooter() {
		$("#userTable tfoot input").keyup(
				function() {
					tables["userTable"].fnFilter(this.value, $(
							"#userTable tfoot input").index(this),null,null,null,false);
				});
		$("#userTable tfoot input").each(function(i) {
			asInitVals[i] = this.value;
		});
		$("#userTable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#userTable tfoot input")
				.blur(
						function(i) {
							if (this.value == "") {
								this.className = "search_init";
								this.value = asInitVals[$(
										"#userTable tfoot input")
										.index(this)];
							}
						});
	};
	inituserTableFooter();
	$("#deleteUser").button().click(function(){
		var table = tables["userTable"];
		var rowsSelected = table.$("tr.row_selected");
		if(rowsSelected.length!=1){
			alert("每次只能删除一个用户!");
		}else{
			var userId = rowsSelected[0].cells[0].innerText;
			var param = [userId];
			userManager.deleteUser(param);
		}
	});
	$("#addUser").button().click(function(){
		window.location="addUser.jsp";
	});
	
	$('#modify').button().click(function() {
		var table = tables["userTable"];
		var rowsSelected = table.$("tr.row_selected");
		if(rowsSelected.length!=1) {
			alert("请选择要修改的用户!（限1个）");
		} else {
			var datas = table.fnGetData(rowsSelected[0]);
			var user = [];
			user.push(datas.id);
			user.push(datas.password);
			window.location="../html/user-modify.html?userId="+datas.id;
		}
	});
});
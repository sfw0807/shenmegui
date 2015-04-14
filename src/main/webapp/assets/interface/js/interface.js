$(function() {
	
	var tables = {};
	var asInitVals = new Array();
	var reg = '';
	//初始化服务Grid的方法
	var initTable = function initTable(result) {
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#interfaceManagementTable tbody tr").unbind("click");
			$("#interfaceManagementTable tbody tr").click(function() {
				 $(this).toggleClass("row_selected");
			});
		};
		//创建Grid
		tables["interfaceManagementTable"] = $("#interfaceManagementTable").dataTable( {
			"aaData" : result,
			"aoColumns" : interfaceManagementTableLayout,
			"aoColumnDefs" : [
				{
					"sClass" : "center",
					"aTargets" : [ 0, 1, 2, 3, 4, 5, 6, 7]
				},
				{
					"bVisible" : false,
					"aTargets" : [ 4, 5]
				}
			],
			"bJQueryUI": true,
			"bAutoWidth" : true,
			"oLanguage" : oLanguage,
			"fnDrawCallback" : function() {
				columnClickEventInit();
			}
		});
	};
	
	interfaceManager.getInterfaceManagementList(initTable);
	
	//tables["interfaceManagementTable"].fnAdjustColumnSizing();

	//初始化操作Grid的搜索框
	var initTableFooter = function initTableFooter() {
		$("#interfaceManagementTable tfoot input").keyup(
				function() {
					tables["interfaceManagementTable"].fnFilter(this.value, $(
							"#interfaceManagementTable tfoot input").index(this));
				});
		$("#interfaceManagementTable tfoot input").each(function(i) {
			asInitVals[i] = this.value;
		});
		$("#interfaceManagementTable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#interfaceManagementTable tfoot input").blur(
				function(i) {
					if (this.value == "") {
						this.className = "search_init";
						this.value = asInitVals[$("#interfaceManagementTable tfoot input")
								.index(this)];
					}
				});
	};
	
	initTableFooter();
	
	$('#reset').button().click(function(){
	     var table = tables["interfaceManagementTable"];
             // 选择的行数
         var rowsSelected = table.$("tr.row_selected");
         rowsSelected.toggleClass("row_selected");
	});
	
	$('#edit').button().click(function(){
		var table = tables["interfaceManagementTable"];
		var rowsSelected = table.$("tr.row_selected");	
		if(rowsSelected.length == 1){
			var datas = table.fnGetData(rowsSelected[0]);
			window.location.href = 'interfaceEdit.html?ecode='+datas.ecode;
		} else {
			alert("请选择一条记录!");
		}
	});
	
	$('#add').button().click(function(){
		window.location.href = 'interfaceEdit.html?operation=add';
	});
	
	var reloadCurPage = function(){
	    alert('删除成功!');
		window.location.reload();
	}
	
	$('#delete').button().click(function(){
		var delIds = '';
		var table = tables["interfaceManagementTable"];
		var rowsSelected = table.$("tr.row_selected");	
		if(rowsSelected.length>0){
			for(var i=0;i<rowsSelected.length;i++){
				var datas = table.fnGetData(rowsSelected[i]);
				delIds += datas.ecode;
				if (i != rowsSelected.length - 1) {
					delIds += ',';
				}
			}
			//sendDelReq(delIds);
			if (confirm('确定要删除接口'+delIds+'?')) {
				interfaceManager.deleteInterfaceInfos(delIds, reloadCurPage);
			}
		}else{
			alert("请选中至少一个接口!");
		}	
		
	});
	
	
});
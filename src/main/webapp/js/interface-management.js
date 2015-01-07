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
					"aTargets" : [ 0, 2, 4, 5, 6, 7, 8]
				},
				{
					"mRender" : function ( data, type, row ) {
						return '<a href="interfaceEdit.html?ecode='+row['ecode']+ '" target=_blank>' + '修改' + '</a>';
					},
					"aTargets" : [8]
				},
				
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
	
	$('#query').button().click(function(){
		var oTable = $("#interfaceManagementTable").dataTable();
		var str = $('span').text();
		var selected = str.split("123")[0];
		var options = selected.split(",");
		//reg = $("#sys_select").multiselect("SelectedValues");
		reg = $('system').val();
		console.log(reg);
		oTable.fnFilter(reg,3,true);
		
	});
	
	$('#reset').button().click(function(){
		$('#through').val('全部');
		$('#system').val('全部');
		$('.ui-combobox:eq(0) input').val('--全部--');
		oTable = $("#interfaceManagementTable").dataTable();
		oTable.fnFilter('', 3, true);
		oTable.fnFilter('', 7, true);
	});
	
	$('#interfaceManagementTable tbody td').button().click(function(){
		
		var pos = oTable.fnGetPosition(this);
		var data = oTable.fnGetData(pos[0]);
		data[pos[1]] = 'clicked';
		this.innerHTML = 'clicked';
		oTable = $("#interfaceManagementTable").dataTable();
	});
	
	var result = 
    $.ajax({
        url: '/serviceDevInfo/getAllSystem',
        type: 'GET',
        success: function(result) {
            initSelect(result);
        }
	});
	
	var initSelect = function initSelect(result) {
		for (var i=0;i<result.length;i++)
				$('#system').append("<option value='"+result[i].systemAb+"'>"+result[i].systemAb+":"+result[i].systemName+"</option>");
	};
	
	$('#222').button().click(function(){
		$("input[type='checkbox']:checked").each(function(){
			alert($(this).val());
		});
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
				table.fnDeleteRow(rowsSelected[i]);
			}
		}else{
			alert("请选中至少一个节点!");
		}	
		
	});
	
	$('#state').change(function(){
		var state = $('#state').val();
		var oTable = $("#interfaceManagementTable").dataTable();
		if (state == '全部') {
			oTable.fnFilter('', 7,true);
		} else {
			oTable.fnFilter(state, 7, true);
		}
	});
	
	$('#through').change(function(){
		var penerate = $('#through').val();
		var oTable = $("#interfaceManagementTable").dataTable();
		if (penerate == '全部') {
			oTable.fnFilter('', 7, true);
		} else {
			oTable.fnFilter(penerate, 7, true);
		}
	});
	
	var box = $("#system" ).combobox({
	});
	
});

	function selectEventLater() {
		var oTable = $("#interfaceManagementTable").dataTable();
		var system = $('#system').val();
		if (system == '全部') {
			oTable.fnFilter('', 3, true);
		} else {
			oTable.fnFilter(system, 3, true);
		}
	}

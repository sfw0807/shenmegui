$(function() {
	
	var tables = {};
	var asInitVals = new Array();
	var reg = '';
	//初始化服务Grid的方法
	var initTable = function initTable(result) {
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#serviceDevProgressTable tbody tr").unbind("click");
			$("#serviceDevProgressTable tbody tr").click(function(e) {
				$(this).toggleClass("row_selected");
			});
		};
		//创建Grid
		tables["serviceDevProgressTable"] = $("#serviceDevProgressTable").dataTable( {
			"aaData" : result,
			"aoColumns" : serviceDevProgressTableLayout,
			"aoColumnDefs" : [
				{
					"sClass" : "center",
					"aTargets" : [ 2, 3, 4, 5, 6, 7, 8]
				}
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
	
	result = systemManager.getServiceDevProgress(initTable);

	//初始化操作Grid的搜索框
	var initTableFooter = function initTableFooter() {
		$("#serviceDevProgressTable tfoot input").keyup(
				function() {
					tables["serviceDevProgressTable"].fnFilter(this.value, $(
							"#serviceDevProgressTable tfoot input").index(this));
				});
		$("#serviceDevProgressTable tfoot input").each(function(i) {
			asInitVals[i] = this.value;
		});
		$("#serviceDevProgressTable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#serviceDevProgressTable tfoot input").blur(
				function(i) {
					if (this.value == "") {
						this.className = "search_init";
						this.value = asInitVals[$("#serviceDevProgressTable tfoot input")
								.index(this)];
					}
				});
	};
	
	initTableFooter();
	
	$('#query').button().click(function(){
		var oTable = $("#serviceDevProgressTable").dataTable();
		var str = $('span').text();
		var selected = str.split("123")[0];
		var options = selected.split(",");
		reg = $("#sys_select").multiselect("SelectedValues");
		oTable.fnFilter(reg,1,true);
	});
	
	$('#reset').button().click(function(){
		var oTable = $("#serviceDevProgressTable").dataTable();
		oTable.fnFilter('',1);
		$('#ui-multiselect-close').click();
	});
	
	$('#serviceDevProgressTable tbody td').click(function(){
		
		var pos = oTable.fnGetPosition(this);
		var data = oTable.fnGetData(pos[0]);
		data[pos[1]] = 'clicked';
		this.innerHTML = 'clicked';
		oTable = $("#serviceDevProgressTable").dataTable();
	});
	
	var result = 
    $.ajax({
        url: '../serviceDevInfo/getAllSystem',
        type: 'GET',
        success: function(result) {
            initSelect(result);
        }
	});
	
	var initSelect = function initSelect(result) {
		for (var i=0;i<result.length;i++) {
			$('#sys_select').append("<option value='"+result[i].systemAb+"'>"+result[i].systemAb+":"+result[i].systemName+"</option>");
		}
		$("#sys_select").multiselect({
			noneSelectedText:"全部系统",
			checkAllText:"全选",
			uncheckAllText:"全不选",
			selectedList:6
		});
	};
	
	$('#export, #export1').button().click(function(){
        var params = reg;
        if (params=='') {
        	params = 'null'
        }
        var td = $("#serviceDevProgressTable tbody tr td:first");
        if( td.hasClass("dataTables_empty")){
            alert('没有数据要导出！');
            return false;
        }
        $.fileDownload("../serviceDevInfo/export/" + params, {
       	});
	});
	
});

	


	
$(function() {
	
	var tables = {};
	var asInitVals = new Array();
	var reg = '';
	//初始化服务Grid的方法
	var initTable = function initTable(result) {
		if (tables["serviceDevProgressTable"]) {
            tables["serviceDevProgressTable"].fnDestroy();
        }
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
			"oLanguage" : oLanguage,
			"bPaginate" : false,
			"bFilter" : true,
			"fnDrawCallback" : function() {
				columnClickEventInit();
			}
		});
		calculateSum();
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
				
		$("#serviceDevProgressTable tfoot input").attr("display","none");
	};
	
	initTableFooter();
	
	$('#query').button().click(function() {
		/*
		var oTable = $("#serviceDevProgressTable").dataTable();
		reg = $("#sys_select").multiselect("SelectedValues");
		reg += '| 总计';
		console.log(reg);
		oTable.fnFilter(reg,1,true,true);
		*/
		reg = $("#sys_select").multiselect("SelectedValues");
		
		if (reg == '') {
			reg = 'all';
		}
		
		$.ajax({
	        url: '../serviceDevInfo/query/' + reg,
	        type: 'GET',
	        success: function(result) {
	        	initTable(result);
	        	//calculateSum();
	        }
	    });
		
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
	
	
	// 用多选下拉框的参数作为导出条件
	
	$('#export, #export1').button().click(function(){
        var params = reg;
        if (params=='' || params=='all') {
        	params = 'null'
        }
        var td = $("#serviceDevProgressTable tbody tr td:first");
        
        //if( td.hasClass("dataTables_empty")){
        if (td[0].innerHTML.indexOf('总计')>-1 == true) {
        	alert('没可导出数据！');
            return false;
        }
        
        if (this.id == 'export') {
        	params = 'null';
        }
        $.fileDownload("../serviceDevInfo/export/" + params, {
       	});
	});
	
	
	function calculateSum() {
	
		var totalUnderDefine=0;
		var totalDev = 0;
		var totalUnit = 0;
		var totalSit = 0;
		var totalUat = 0;
		var totalProduct = 0;
		var toto = 0; 
		
		var oTable = $("#serviceDevProgressTable").dataTable();
		
		oTable.$('tr').each(function (){
			//console.log(oTable.fnGetData(this));
			totalUnderDefine += oTable.fnGetData(this).underDefine;
			totalDev += oTable.fnGetData(this).dev;
			totalUnit += oTable.fnGetData(this).unitTest;
			totalSit += oTable.fnGetData(this).sitTest;
			totalUat += oTable.fnGetData(this).uatTest;
			totalProduct += oTable.fnGetData(this).productTest;
			toto += oTable.fnGetData(this).totalNum;
		});
		
		var line = oTable.fnAddData({'systemName':'', 'systemAb':'总计', 'underDefine':totalUnderDefine, 'dev':totalDev, 
			'unitTest':totalUnit, 'sitTest':totalSit, 'uatTest':totalUat, 'productTest':totalProduct, 'totalNum':toto}, true);
	}
	
	/*
		从table列表获取参数
	
	$('#export, #export1').button().click(function(){
		var oTable = $("#serviceDevProgressTable").dataTable();
		console.log(oTable);
	});
	*/
});

	


	
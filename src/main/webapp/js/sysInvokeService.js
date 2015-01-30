$(function() {
	var tables = {};
	var asInitVals = new Array();
	var params = {
	 prdMsgType : "",
	 csmMsgType : "",
	 versionSt : "",
	 sysAb: "",
     publishDate: ""
	};
	$('#tabs').tabs();
	$("#tab0").click(function(e) {
		if(tables["systemInvokeServiceTable"]){
			tables["systemInvokeServiceTable"].fnAdjustColumnSizing();
		}
	});
	/**
	 * init system invoke relate table 
	 * @param {Object} result
	 * 
	 */
	var initsystemInvokeServiceTable = function initsystemInvokeServiceTable(result) {
	    if (tables["systemInvokeServiceTable"]) {
            tables["systemInvokeServiceTable"].fnDestroy();
        }
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#systemInvokeServiceTable tbody tr").unbind("click");
			$("#systemInvokeServiceTable tbody tr").click(function(e) {
				$(this).toggleClass("row_selected");
			});
		};
		//初始化systemInvokeServiceTable
		tables["systemInvokeServiceTable"] = $("#systemInvokeServiceTable").dataTable( {
			"aaData" : result,
			"aoColumns" : sisTableLayout,
			"aoColumnDefs" : [
				{
					"sClass" : "center",
					"aTargets" : [ 0, 1, 2, 3, 4, 5, 6, 7]
				}
			],
			"bJQueryUI": true,
			"bAutoWidth" : true,
			"bScrollCollapse" : "full_numbers",
			"bPaginate" : false,
			"bSort" : true,
			"oLanguage" : oLanguage,
			"fnDrawCallback" : function() {
				columnClickEventInit();
			}
		});
		tables["systemInvokeServiceTable"].fnAdjustColumnSizing();
		var table = document.getElementById("systemInvokeServiceTable");
		if($("#systemInvokeServiceTable tfoot tr:first td:first").text() == '总计'){
		    table.deleteRow(table.rows.length - 2);
		}
		if(!$("#systemInvokeServiceTable tbody tr td").hasClass("dataTables_empty")){
		var ps=0;
	    var cs=0;
	    var po=0;
	    var co=0;
		for(var i=1;i<table.rows.length-1;i++){
			ps = ps+parseInt(table.rows[i].cells[4].innerText);
			cs+=parseInt(table.rows[i].cells[5].innerText);
			po+=parseInt(table.rows[i].cells[6].innerText);
			co+=parseInt(table.rows[i].cells[7].innerText);
		}
		// total
		var tbody = document.getElementsByTagName("tbody")[0];
		var div = document.createElement('div');
		div.innerHTML = "<table><tr><td  class='center' colspan='4'>总计</td><td class='center'>"+ps+"</td><td class='center'>"+cs+"</td><td class='center'>"+po+"</td><td class='center'>"+co+"</td></tr></table>";
		tbody.appendChild(div.firstChild.firstChild.firstChild);

        /*
        var row = table.insertRow(table.rows.length-1);
		row.innerHTML="<tr><td  class='center' colspan='3'>总计</td><td class='center'>"+ps+"</td><td class='center'>"+cs+"</td><td class='center'>"+po+"</td><td class='center'>"+co+"</td></tr>";
		*/
		}
	};
	invokeInfoManager.getSystemInvokeServiceInfo(JSON.stringify(params),initsystemInvokeServiceTable);

	//初始化操作Grid的搜索框
	var initsystemInvokeServiceTableFooter = function initsystemInvokeServiceTableFooter() {
		$("#systemInvokeServiceTable tfoot input").keyup(
				function() {
					tables["systemInvokeServiceTable"].fnFilter(this.value, $(
							"#systemInvokeServiceTable tfoot input").index(this),null,null,null,false);
				});
		$("#systemInvokeServiceTable tfoot input").each(function(i) {
			asInitVals[i] = this.value;
		});
		$("#systemInvokeServiceTable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#systemInvokeServiceTable tfoot input")
				.blur(
						function(i) {
							if (this.value == "") {
								this.className = "search_init";
								this.value = asInitVals[$(
										"#systemInvokeServiceTable tfoot input")
										.index(this)];
							}
						});
	};
	initsystemInvokeServiceTableFooter();	
	
	var result = 
    $.ajax({
        url: '../serviceDevInfo/getAllSystem',
        type: 'GET',
        success: function(result) {
            initSelect(result);
        }
	});
	
	function initSelect(result) {
		for (var i=0;i<result.length;i++) {
			$('#sys_select').append("<option value='"+result[i].systemAb+"'>"+result[i].systemAb+":"+result[i].systemName+"</option>");
		}
	};
	$('#sys_select').combobox();
	
	$('#search').button().click(function(){
	    var prdMsgType = $('#provideMsgType').val();
        var csmMsgType = $('#consumeMsgType').val();
        var versionSt = $('#versionSt').val();
        var sysAb = $('.ui-combobox:eq(0) input').val().split(':')[0];
        var publishDate_prd = $('#datepicker_prd').val();
        var publishDate_csm = $('#datepicker_csm').val();
        params.prdMsgType = prdMsgType;
        params.csmMsgType = csmMsgType;
        params.versionSt = versionSt;
        params.sysAb = sysAb;
        params.publishDate_prd = publishDate_prd;
        params.publishDate_csm = publishDate_csm;
        invokeInfoManager.getSystemInvokeServiceInfo(JSON.stringify(params),initsystemInvokeServiceTable);
	});
	
	$('#export').button().click(function(){
	    var prdMsgType = $('#provideMsgType').val();
        var csmMsgType = $('#consumeMsgType').val();
        var versionSt = $('#versionSt').val();
        var sysAb = $('.ui-combobox:eq(0) input').val().split(':')[0];
        var publishDate_prd = $('#datepicker_prd').val();
        var publishDate_csm = $('#datepicker_csm').val();
        params.prdMsgType = prdMsgType;
        params.csmMsgType = csmMsgType;
        params.versionSt = versionSt;
        params.sysAb = sysAb;
        params.publishDate_prd = publishDate_prd;
        params.publishDate_csm = publishDate_csm;
        $.fileDownload(encodeURI(encodeURI("../invokeInfo/export/"+JSON.stringify(params))), {});
	});
	
	$('#reset').button().click(function(){
	    $('#provideMsgType').val('');
        $('#consumeMsgType').val('');
        $('#versionSt').val('');
        $('.ui-combobox:eq(0) input').val('');
        $('#datepicker_prd').val('');
        $('#datepicker_csm').val('');
    });
    
    $('#datepicker_prd').datepicker({
	    changeMonth: true,
        changeYear: true,
	    dateFormat: "yymmdd"
	});
	
	$('#datepicker_csm').datepicker({
	    changeMonth: true,
        changeYear: true,
	    dateFormat: "yymmdd"
	});
});

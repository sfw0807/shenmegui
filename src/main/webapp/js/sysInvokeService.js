$(function() {
	var tables = {};
	var asInitVals = new Array();
	var params = {
	 prdMsgType : "",
	 csmMsgType : "",
	 versionSt : ""
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
					"aTargets" : [ 0, 1, 2, 3, 4, 5]
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
		var ps=0;
	    var cs=0;
	    var po=0;
	    var co=0;
		for(var i=1;i<table.rows.length-1;i++){
			ps = ps+parseInt(table.rows[i].cells[3].innerText);
			cs+=parseInt(table.rows[i].cells[4].innerText);
			po+=parseInt(table.rows[i].cells[5].innerText);
			co+=parseInt(table.rows[i].cells[6].innerText);
		}
		// 克隆节点
		/*
        var cloneNode = table.rows[1].cloneNode();
        table.lastChild.previousSibling.appendChild(cloneNode);
        var row = table.rows[table.rows.length - 1];
		row.cells[0].colSpan = 3;
		row.childNodes[0].innerText = '总计';
        row.childNodes[1].innerText = ps;
        row.childNodes[2].innerText = cs;
        row.childNodes[3].innerText = po;
        row.childNodes[4].innerText = co;
        */
        var row = table.insertRow(table.rows.length-1);
		row.innerHTML="<tr><td  class='center' colspan='3'>总计</td><td class='center'>"+ps+"</td><td class='center'>"+cs+"</td><td class='center'>"+po+"</td><td class='center'>"+co+"</td></tr>";
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
	
	$('#search').button().click(function(){
	    var prdMsgType = $('#provideMsgType').val();
        var csmMsgType = $('#consumeMsgType').val();
        var versionSt = $('#versionSt').val();
        params.prdMsgType = prdMsgType;
        params.csmMsgType = csmMsgType;
        params.versionSt = versionSt;
        invokeInfoManager.getSystemInvokeServiceInfo(JSON.stringify(params),initsystemInvokeServiceTable);
	});
	
	$('#export').button().click(function(){
	    var prdMsgType = $('#provideMsgType').val();
        var csmMsgType = $('#consumeMsgType').val();
        var versionSt = $('#versionSt').val();
        params.prdMsgType = prdMsgType;
        params.csmMsgType = csmMsgType;
        params.versionSt = versionSt;
        $.fileDownload("../invokeInfo/export/"+JSON.stringify(params), {
            	});
	});

});

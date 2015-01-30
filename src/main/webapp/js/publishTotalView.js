$(function() {
	var tables = {};
	var asInitVals1 = new Array();
	var asInitVals2 = new Array();
	var asInitVals3 = new Array();
	$('#tabs').tabs();
	$("#tab0").click(function(e) {
		if(tables["publishTotalViewTable"]){
			tables["publishTotalViewTable"].fnAdjustColumnSizing();
		}
	});
	$("#tab1").click(function(e) {
		if(tables["systemInvokeServiceTable"]){
			tables["systemInvokeServiceTable"].fnAdjustColumnSizing();
		}

	});
	$("#tab2").click(function(e) {
		if(tables["publishInfoTable"]){
			tables["publishInfoTable"].fnAdjustColumnSizing();
		}
	});
	    var isIE  = (navigator.appVersion.indexOf("MSIE") != -1) ? true : false;
		if(isIE){
		    $("#publishTotalViewTable").attr("style","width:1360px");
			$("#systemInvokeServiceTable").attr("style","width:1360px");
			$("#publishInfoTable").attr("style","width:1360px");
		}
	/**
	 * init service interface relate table 
	 * @param {Object} result
	 * 
	 */
	var initpublishTotalViewTable = function initpublishTotalViewTable(result) {

        //初始化publishTotalViewTable
	 	if (tables["publishTotalViewTable"]) {
            tables["publishTotalViewTable"].fnDestroy();
        }
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#publishTotalViewTable tbody tr").unbind("click");
			$("#publishTotalViewTable tbody tr").click(function(e) {
				$(this).toggleClass("row_selected");
			});
		};
		tables["publishTotalViewTable"] = $("#publishTotalViewTable").dataTable( {
			"aaData" : result,
			"aoColumns" : publishTotalViewTableLayout,
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
			"bSort" : false,
			"oLanguage" : oLanguage,
			"bFilter" : false,
			"fnDrawCallback" : function() {
				columnClickEventInit();
			}
		});
		tables["publishTotalViewTable"].fnAdjustColumnSizing();
	};
	publishInfoManager.getTotalCountView("all",initpublishTotalViewTable);
	//初始化操作Grid的搜索框
	var initpublishTotalViewTableFooter = function initpublishTotalViewTableFooter() {
		$("#publishTotalViewTable tfoot input").keyup(
				function() {
					tables["publishTotalViewTable"].fnFilter(this.value, $(
							"#publishTotalViewTable tfoot input").index(this),null,null,null,false);
				});
		$("#publishTotalViewTable tfoot input").each(function(i) {
			asInitVals1[i] = this.value;
		});
		$("#publishTotalViewTable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#publishTotalViewTable tfoot input")
				.blur(
						function(i) {
							if (this.value == "") {
								this.className = "search_init";
								this.value = asInitVals1[$(
										"#publishTotalViewTable tfoot input")
										.index(this)];
							}
						});
	};
	initpublishTotalViewTableFooter();
			
	$('#search1').button().click(function () {
        var onlineDate = $('#datepicker1').val();
        if(onlineDate == ""){
            onlineDate = "all";
        }
        publishInfoManager.getTotalCountView(onlineDate,initpublishTotalViewTable);
	});

    $('#datepicker1').datepicker({
	    changeMonth: true,
        changeYear: true,
	    dateFormat: "yymmdd"
	});
	
	var params = {
	 prdMsgType : "",
	 csmMsgType : "",
	 // 上线统计
	 versionSt : "6",
	 sysAb: "",
     publishDate: ""
	};

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
			"bFilter" : false,
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
		var tbody = document.getElementsByTagName("tbody")[1];
		var div = document.createElement('div');
		//div.innerHTML = "<table><tr><td  class='center' colspan='3'>总计</td><td class='center'>"+ps+"</td><td class='center'>"+cs+"</td><td class='center'>"+po+"</td><td class='center'>"+co+"</td></tr></table>";
		div.innerHTML = "<table><tr><td  class='center' colspan='4'>总计</td><td class='center'></td><td class='center'></td><td class='center'>"+po+"</td><td class='center'></td></tr></table>";
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
			asInitVals2[i] = this.value;
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
								this.value = asInitVals2[$(
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
	
	$('#search_sysInvoke').button().click(function(){
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
        $('#systemInvokeServiceTable').attr("width","1360px");
	});
	
	$('#export_sysInvoke').button().click(function(){
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
	
	$('#reset_sysInvoke').button().click(function(){
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
	
    var params2 = {
	 prdMsgType : "",
	 csmMsgType : "",
	 onlineDate : ""
	};
	/**
	 * init service interface relate table 
	 * @param {Object} result
	 * 
	 */
	var initpublishInfoTable = function initpublishInfoTable(result) {

        //初始化publishInfoTable
	 	if (tables["publishInfoTable"]) {
            tables["publishInfoTable"].fnDestroy();
        }
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#publishInfoTable tbody tr").unbind("click");
			$("#publishInfoTable tbody tr").click(function(e) {
				$(this).toggleClass("row_selected");
			});
		};
		tables["publishInfoTable"] = $("#publishInfoTable").dataTable( {
			"aaData" : result,
			"aoColumns" : publishInfoTableLayout,
			"aoColumnDefs" : [
				{
					"sClass" : "center",
					"aTargets" : [ 0, 1, 2, 3, 4, 5, 6]
				},
				{
					"mRender" : function ( data, type, row ) {
						return '<a href="publishServices.jsp?onlineDate='+row["onlinedate"]
							+'&prdMsgType='+$('#provideMsgType2').val()
							+'&csmMsgType='+$('#consumeMsgType2').val()
							+'&flag=0'
							+'" target="_blank"  style="text-decoration:none;color:blue;">' + row["countofservices"] + '</a>';
					},
					"aTargets" : [1]
				},
				{
					"mRender" : function ( data, type, row ) {
					    if(row["countofaddservices"] == '0'){
					        return row["countofaddservices"];
					    }
					    else{
						return '<a href="publishServices.jsp?onlineDate='+row["onlinedate"]
							+'&prdMsgType='+$('#provideMsgType2').val()
							+'&csmMsgType='+$('#consumeMsgType2').val()
							+'&flag=1'
							+'" target="_blank"  style="text-decoration:none;color:blue;">' + row["countofaddservices"] + '</a>';
						}	
					},
					"aTargets" : [2]
				},
				{
					"mRender" : function ( data, type, row ) {
					    if(row["counofmodifyservices"] == '0'){
					        return row["counofmodifyservices"];
					    }
					    else{
						return '<a href="publishServices.jsp?onlineDate='+row["onlinedate"]
							+'&prdMsgType='+$('#provideMsgType2').val()
							+'&csmMsgType='+$('#consumeMsgType2').val()
							+'&flag=2'
							+'" target="_blank"  style="text-decoration:none;color:blue;">' + row["counofmodifyservices"] + '</a>';
						}
					},
					"aTargets" : [3]
				},
				{
					"mRender" : function ( data, type, row ) {
						return '<a href="publishOperations.jsp?onlineDate='+row["onlinedate"]
							+'&prdMsgType='+$('#provideMsgType2').val()
							+'&csmMsgType='+$('#consumeMsgType2').val()
							+'&flag=0'
							+'" target="_blank"  style="text-decoration:none;color:blue;">' + row["countofoperations"] + '</a>';
					},
					"aTargets" : [4]
				},
				{
					"mRender" : function ( data, type, row ) {
					    if(row["countofaddoperations"] == '0'){
					        return row["countofaddoperations"];
					    }
					    else{
						return '<a href="publishOperations.jsp?onlineDate='+row["onlinedate"]
							+'&prdMsgType='+$('#provideMsgType2').val()
							+'&csmMsgType='+$('#consumeMsgType2').val()
							+'&flag=1'
							+'" target="_blank"  style="text-decoration:none;color:blue;">' + row["countofaddoperations"] + '</a>';
						}
					},
					"aTargets" : [5]
				},
				{
					"mRender" : function ( data, type, row ) {
					    if(row["countofmodifyoperations"] == '0'){
					        return row["countofmodifyoperations"];
					    }
					    else{
						return '<a href="publishOperations.jsp?onlineDate='+row["onlinedate"]
							+'&prdMsgType='+$('#provideMsgType2').val()
							+'&csmMsgType='+$('#consumeMsgType2').val()
							+'&flag=2'
							+'" target="_blank"  style="text-decoration:none;color:blue;">' + row["countofmodifyoperations"] + '</a>';
					    }
					},
					"aTargets" : [6]
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
		tables["publishInfoTable"].fnAdjustColumnSizing();
	};
	publishInfoManager.getAllPublishTotalInfos(JSON.stringify(params2),initpublishInfoTable);

	//初始化操作Grid的搜索框
	var initpublishInfoTableFooter = function initpublishInfoTableFooter() {
		$("#publishInfoTable tfoot input").keyup(
				function() {
					tables["publishInfoTable"].fnFilter(this.value, $(
							"#publishInfoTable tfoot input").index(this),null,null,null,false);
				});
		$("#publishInfoTable tfoot input").each(function(i) {
			asInitVals3[i] = this.value;
		});
		$("#publishInfoTable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#publishInfoTable tfoot input")
				.blur(
						function(i) {
							if (this.value == "") {
								this.className = "search_init";
								this.value = asInitVals3[$(
										"#publishInfoTable tfoot input")
										.index(this)];
							}
						});
	};
	initpublishInfoTableFooter();
	
	$('#export2').button().click(function () {
				var prdMsgType = $('#provideMsgType2').val();
				var csmMsgType = $('#consumeMsgType2').val();
				var onlineDate = ($('#onlinedate').val() == '上线日期')?"":$('#onlinedate').val();
				params2.prdMsgType = prdMsgType;
                params2.csmMsgType = csmMsgType;
                params2.onlineDate = onlineDate;
			    $.fileDownload("../publish/export/"+JSON.stringify(params2), {
            	});
			});
			
	$('#search3').button().click(function () {
        var prdMsgType = $('#provideMsgType2').val();
        var csmMsgType = $('#consumeMsgType2').val();
        var onlineDate = ($('#onlinedate').val() == '上线日期')?"":$('#onlinedate').val();
        params2.prdMsgType = prdMsgType;
        params2.csmMsgType = csmMsgType;
        params2.onlineDate = onlineDate;
        publishInfoManager.getAllPublishTotalInfos(JSON.stringify(params2),initpublishInfoTable);
        $('#publishInfoTable').attr("width","1360px");
	});
});

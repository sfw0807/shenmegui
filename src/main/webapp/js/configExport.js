$(function() {
	var tables = {};
	var asInitVals = new Array();
	$('#tabs').tabs();
	$("#tab0").click(function(e) {
		if(tables["configExportTable"]){
			tables["configExportTable"].fnAdjustColumnSizing();
		}
	});
	/**
	 * init service interface relate table 
	 * @param {Object} result
	 * 
	 */
	var initconfigExportTable = function initconfigExportTable(result) {
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#configExportTable tbody tr").unbind("click");
			$("#configExportTable tbody tr").click(function(e) {
				$(this).toggleClass("row_selected");
			});
		};
		//初始化configExportTable
		tables["configExportTable"] = $("#configExportTable").dataTable( {
			"aaData" : result,
			"aoColumns" : configExportTableLayout,
			"aoColumnDefs" : [
				{
					"sClass" : "center",
					"aTargets" : [ 0, 1, 2, 3, 4, 5 , 6, 7, 8, 9, 10, 11, 12, 13, 14]
				},
				{
					"mRender" : function ( data, type, row ) {
					    if(row["direction"] == "1"){
					       return "提供方";
					    } else if(row["direction"] == "0")
					       return "调用方";
					    }
					    ,"aTargets" : [ 6 ]
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
		tables["configExportTable"].fnAdjustColumnSizing();
	};
	svcAsmRelateManager.getAllExportInvokeInfos(initconfigExportTable);

	//初始化操作Grid的搜索框
	var initconfigExportTableFooter = function initconfigExportTableFooter() {
		$("#configExportTable tfoot input").keyup(
				function() {
					tables["configExportTable"].fnFilter(this.value, $(
							"#configExportTable tfoot input").index(this),null,null,null,false);
				});
		$("#configExportTable tfoot input").each(function(i) {
			asInitVals[i] = this.value;
		});
		$("#configExportTable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#configExportTable tfoot input")
				.blur(
						function(i) {
							if (this.value == "") {
								this.className = "search_init";
								this.value = asInitVals[$(
										"#configExportTable tfoot input")
										.index(this)];
							}
						});
	};
	initconfigExportTableFooter();
	
	$('#exportConfig').button().click(function () {
	       var table = tables["configExportTable"];
             // 选择的行数
            var rowsSelected = table.$("tr.row_selected");
            if(rowsSelected.length == 0){
                alert('请选择要导出配置的接口!');
                return false;
            }
            var params = '';
            var ecodeArr = '';
            for(var i=0;i<rowsSelected.length;i++){
                var selectedDatas = table.fnGetData(table.$("tr.row_selected")[i]);
                var ecode = selectedDatas["interfaceInfo"];
                ecode = ecode.substring(0,ecode.indexOf("/"));
                var serviceId = selectedDatas["serviceInfo"];
                serviceId = serviceId.substring(0,serviceId.indexOf("/"));
                var operationId = selectedDatas["operationInfo"];
                operationId = operationId.substring(0,operationId.indexOf("/"));
                var consumeMsgType = selectedDatas["consumeMsgType"];
                var provideMsgType = selectedDatas["provideMsgType"];
                var through = selectedDatas["through"];
                var prdSysId = selectedDatas["provideSysInfo"];
				var consumeSysId = selectedDatas["consumeSysInfo"];
                var direction = selectedDatas["direction"];
                if(direction == '提供方'){
                   direction = '1';
                }
                else if(direction == '调用方'){
                   direction = '0';
                }
                if(through == '是'){
                   through = '0';
                }
                else{
                   through = '1';
                }
                prdSysId = prdSysId.substring(0,prdSysId.indexOf("/"));
				consumeSysId = consumeSysId.substring(0,consumeSysId.indexOf("/"));
                var info = ecode+","+consumeMsgType+","+provideMsgType+","+through+","+serviceId+","+operationId+","+prdSysId+","+direction+","+consumeSysId;
				console.log(info);
                params = params + info + ":";
                ecodeArr += ecode + ",";
            }
            params = params.substring(0,params.length-1);
            ecodeArr = ecodeArr.substring(0,ecodeArr.length-1);
            function callBack(result){
                if(result == null || result == ''){
                   $.fileDownload("../export/config/" + params, {});
                }
                else{
                   alert('服务或操作 [' + result + "] 未审核通过，请先审核通过!");
                }
                rowsSelected.toggleClass("row_selected");
            }
            svcAsmRelateManager.checkEcodeAudit(ecodeArr, callBack);		    
	});
	$('#exportWSDL').button().click(function () {
	       var table = tables["configExportTable"];
             // 选择的行数
            var rowsSelected = table.$("tr.row_selected");
            if(rowsSelected.length == 0){
                alert('请选择要导出wsdl的接口!');
                return false;
            }else if(rowsSelected.length == 1){
            	var selectedDatas = table.fnGetData(table.$("tr.row_selected")[0]);
                var serviceId = selectedDatas["serviceInfo"];
                serviceId = serviceId.substring(0,serviceId.indexOf("/"));
			    $.fileDownload("../wsdl/byService/" + serviceId, {});
            }else{
            	var serviceIds=[];
	            for(var i=0;i<rowsSelected.length;i++){
	                var selectedDatas = table.fnGetData(table.$("tr.row_selected")[i]);
	                var serviceId = selectedDatas["serviceInfo"];
	                serviceId = serviceId.substring(0,serviceId.indexOf("/"));
				    serviceIds.push(serviceId);
	            }
	            var obj={},distinct=[];
	            for(var i=0;i<serviceIds.length;i++){
	            	if(!obj[serviceIds[i]]){
	            		distinct.push(serviceIds[i]);
	            		obj[serviceIds[i]]=true;
	            	}
	            }
	            $.fileDownload("../wsdl/byServiceList/" + distinct, {});
            }
          rowsSelected.toggleClass("row_selected");
	});
	
	$('#exportMapFile').button().click(function () {
		 var table = tables["configExportTable"];
             // 选择的行数
            var rowsSelected = table.$("tr.row_selected");
            if(rowsSelected.length == 0){
                alert('请选择要导出配置的接口!');
                return false;
            }
            var params = '';
            for(var i=0;i<rowsSelected.length;i++){
            	var selectedDatas = table.fnGetData(table.$("tr.row_selected")[i]);
                var ecode = selectedDatas["interfaceInfo"];
                ecode = ecode.substring(0,ecode.indexOf("/"));
                params += ecode + ",";
            }
            $.fileDownload("../export/mapfile/" + params, {});
            rowsSelected.toggleClass("row_selected");
	});
	
	$('#publish').button().click(function () {
		var table = tables["configExportTable"];
		// 选择的行数
		var rowsSelected = table.$("tr.row_selected");
		if(rowsSelected.length == 0){
			alert('请选择要发布配置的接口!');
			return false;
		}
		var params = '';
		var ecodeArr = '';
		for(var i=0;i<rowsSelected.length;i++){
			var selectedDatas = table.fnGetData(table.$("tr.row_selected")[i]);
			var ecode = selectedDatas["interfaceInfo"];
			ecode = ecode.substring(0,ecode.indexOf("/"));
			var serviceId = selectedDatas["serviceInfo"];
			serviceId = serviceId.substring(0,serviceId.indexOf("/"));
			var operationId = selectedDatas["operationInfo"];
			operationId = operationId.substring(0,operationId.indexOf("/"));
			var consumeMsgType = selectedDatas["consumeMsgType"];
			var provideMsgType = selectedDatas["provideMsgType"];
			var through = selectedDatas["through"];
			var prdSysId = selectedDatas["provideSysInfo"];
			var consumeSysId = selectedDatas["consumeSysInfo"];
			var direction = selectedDatas["direction"];
			if(direction == '提供方'){
				direction = '1';
			}
			else if(direction == '调用方'){
				direction = '0';
			}
			if(through == '是'){
				through = '0';
			}
			else{
				through = '1';
			}
			prdSysId = prdSysId.substring(0,prdSysId.indexOf("/"));
			consumeSysId = consumeSysId.substring(0,consumeSysId.indexOf("/"));
			var info = ecode+","+consumeMsgType+","+provideMsgType+","+through+","+serviceId+","+operationId+","+prdSysId+","+direction+","+consumeSysId;
			console.log(info);
			params = params + info + ":";
			ecodeArr += ecode + ",";
		}
		params = params.substring(0,params.length-1);
		ecodeArr = ecodeArr.substring(0,ecodeArr.length-1);
		window.open("../jsp/pointManage.jsp?params="+params+"","Sample","fullscreen=no,toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no, copyhistory=no,width=700,height=440,left=200,top=200");

	});

	$('#exportMdt').button().click(function () {
         $.fileDownload("../export/exportMetadata", {});
	});
	
	$('#checkAll').button().click(function () {
		$("#configExportTable tbody tr").addClass("row_selected");
	});
	$('#toggleAll').button().click(function () {
		$("#configExportTable tbody tr").toggleClass("row_selected");
	});
	
	$('#batchExportConfig').button().click(function () {
	    window.showModalDialog('../jsp/batchConfigExport.jsp',"","dialogWidth=920px;dialogHeight=500px;resizable=no");
	});
});

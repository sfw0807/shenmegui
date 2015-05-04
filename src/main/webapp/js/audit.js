$(function(){
  var tables = {};
  $('#tabs').tabs(); 
  
  $("#tab0").click(function(e) {
		if(tables["serviceInfoTable"]){
			tables["serviceInfoTable"].fnAdjustColumnSizing();
		}
  });
  $("#tab1").click(function(e) {
		if(tables["operationTable"]){
			tables["operationTable"].fnAdjustColumnSizing();
		}
  });
  
  var isIE  = (navigator.appVersion.indexOf("MSIE") != -1) ? true : false;
  if(isIE){
		    $("#serviceInfoTable").attr("style","width:1360px");
			$("#operationTable").attr("style","width:1360px");
  }
	/**
	 * init serviceInfo table 
	 * @param {Object} result
	 * 
	 */
	var initserviceInfoTable = function initserviceInfoTable(result) {
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#serviceInfoTable tbody tr").unbind("click");
			$("#serviceInfoTable tbody tr").click(function(e) {
				if(!$(this).find("td").hasClass("dataTables_empty")){
					$(this).toggleClass("row_selected");
				}
			});
		};
		//初始化serviceInfoTable
	 	if (tables["serviceInfoTable"]) {
            tables["serviceInfoTable"].fnDestroy();
        }
		tables["serviceInfoTable"] = $("#serviceInfoTable").dataTable( {
			"aaData" : result,
			"aoColumns" : auditServiceInfoTableLayout,
			"aoColumnDefs" : [
				{
					"sClass" : "center",
					"aTargets" : [ 0, 1, 2, 3, 4]
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
		tables["serviceInfoTable"].fnAdjustColumnSizing();
	};
	auditManager.getAuditService(initserviceInfoTable);

    var asInitVals1 = new Array();
	//初始化操作Grid的搜索框
	var initserviceInfoTableFooter = function initserviceInfoTableFooter() {
		$("#serviceInfoTable tfoot input").keyup(
				function() {
					tables["serviceInfoTable"].fnFilter(this.value, $(
							"#serviceInfoTable tfoot input").index(this),null,null,null,false);
				});
		$("#serviceInfoTable tfoot input").each(function(i) {
			asInitVals1[i] = this.value;
		});
		$("#serviceInfoTable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#serviceInfoTable tfoot input")
				.blur(
						function(i) {
							if (this.value == "") {
								this.className = "search_init";
								this.value = asInitVals1[$(
										"#serviceInfoTable tfoot input")
										.index(this)];
							}
						});
	};
	initserviceInfoTableFooter();
	
	/**
	 * init service interface relate table 
	 * @param {Object} result
	 * 
	 */
	var initoperationTable = function initoperationTable(result) {
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#operationTable tbody tr").unbind("click");
			$("#operationTable tbody tr").click(function(e) {
				if(!$(this).find("td").hasClass("dataTables_empty")){
					$(this).toggleClass("row_selected");
				}
			});
		};
		//初始化operationTable
		tables["operationTable"] = $("#operationTable").dataTable( {
			"aaData" : result,
			"aoColumns" : auditOperationTableLayout,
			"aoColumnDefs" : [
				{
					"sClass" : "center",
					"aTargets" : [0,1,2,3,4,5,6]
				},
				{
					"mRender" : function ( data, type, row ) {
						return '<a href="operationInfoByIdView.jsp?operationId='+row["operationId"]
							+'&serviceId='+row["serviceId"]
							+'&version='+row["version"]
							+'" target="_blank">' + '查看' + '</a>';

					},
					"aTargets" : [6]
				}
			],
			"bJQueryUI": true,
			"bAutoWidth" : true,
			"bScrollCollapse" : "full_numbers",
			"bPaginate" : true,
			"bSort" : true,
			"oLanguage" :oLanguage,
			"fnDrawCallback" : function() {
				columnClickEventInit();
			}
		});
		tables["operationTable"].fnAdjustColumnSizing();
	};
	auditManager.getAuditOperation(initoperationTable);

    var asInitVals2 = new Array();
	//初始化操作Grid的搜索框
	var initoperationTableFooter = function initoperationTableFooter() {
		$("#operationTable tfoot input").keyup(
				function() {
					tables["operationTable"].fnFilter(this.value, $(
							"#operationTable tfoot input").index(this),null,null,null,false);
				});
		$("#operationTable tfoot input").each(function(i) {
			asInitVals2[i] = this.value;
		});
		$("#operationTable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#operationTable tfoot input")
				.blur(
						function(i) {
							if (this.value == "") {
								this.className = "search_init";
								this.value = asInitVals2[$(
										"#operationTable tfoot input")
										.index(this)];
							}
						});
	};
	initoperationTableFooter();
	
	$('#servicePassed').button().click(function(){
	    var params = [];
	    var id = '';
        $("#serviceInfoTable tbody tr").each(function(){
             if($(this).hasClass("row_selected")){
                id = $(this).find("td").eq(0).text() +"," + "2";
                params.push(id);
             }
        }); 
        if(params.length == 0){
           alert('请选择审核的服务!');
           return false;
        }	
        function auditSer(result){
              if(result){
                 alert('服务审核通过成功!');
              }
              else{
                 alert('服务审核通过失败!');
              }
              auditManager.getAuditService(initserviceInfoTable);
              initserviceInfoTableFooter();
        };
        auditManager.auditService(auditSer, params);
	});
	$('#serviceRefused').button().click(function(){
	    var params = [];
	    var id = '';
        $("#serviceInfoTable tbody tr").each(function(){
             if($(this).hasClass("row_selected")){
                id = $(this).find("td").eq(0).text() +"," + "3";
                params.push(id);
             }
        }); 
        if(params.length == 0){
           alert('请选择审核的服务!');
           return false;
        }	
        function auditSer(result){
              if(result){
                 alert('服务审核拒绝成功!');
              }
              else{
                 alert('服务审核拒绝失败!');
              }
              auditManager.getAuditService(initserviceInfoTable);
              initserviceInfoTableFooter();
        };
        auditManager.auditService(auditSer, params);
	});
	$('#operationPassed').button().click(function(){
	    var params = [];
	    var id = '';
        $("#operationTable tbody tr").each(function(){
             if($(this).hasClass("row_selected")){
                id = $(this).find("td").eq(0).text() +"," + $(this).find("td").eq(2).text() + "," + "2";
                params.push(id);
             }
        }); 
        if(params.length == 0){
           alert('请选择审核的操作!');
           return false;
        }	
        function auditOpe(result){
              if(result){
                 alert('操作审核通过成功!');
              }
              else{
                 alert('操作审核通过失败!');
              }
              window.location.reload();
        };
        auditManager.auditOperation(auditOpe, params);
	});
	$('#operationRefused').button().click(function(){
	    var params = [];
	    var id = '';
        $("#operationTable tbody tr").each(function(){
             if($(this).hasClass("row_selected")){
                id = $(this).find("td").eq(0).text() +"," + $(this).find("td").eq(2).text() + "," + "3";
                params.push(id);
             }
        }); 
        if(params.length == 0){
           alert('请选择审核的操作!');
           return false;
        }	
        function auditOpe(result){
              if(result){
                 alert('操作审核拒绝成功!');
              }
              else{
                 alert('操作审核拒绝失败!');
              }
              window.location.reload();
        };
        auditManager.auditOperation(auditOpe, params);
	});
	
	$('#checkAllService').button().click(function () {
		$("#serviceInfoTable tbody tr").addClass("row_selected");
	});
	$('#toggleAllService').button().click(function () {
		$("#serviceInfoTable tbody tr").toggleClass("row_selected");
	});
	$('#checkAllOperation').button().click(function () {
		$("#operationTable tbody tr").addClass("row_selected");
	});
	$('#toggleAllOperation').button().click(function () {
		$("#operationTable tbody tr").toggleClass("row_selected");
	});
});
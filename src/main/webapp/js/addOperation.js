$(function() {
	var tables = {};
	var asInitVals = [];
	var operationId = "";
	var serviceId = "";
	var version = "";
	$('#tabs').tabs();

	
	$("#tab1").click(function(e) {

	});
	$("#tab2").click(function(e) {
		if(tables["operationSlaTable"]){
			tables["operationSlaTable"].fnAdjustColumnSizing();
		}
		
	});
	$("#tab3").click(function(e) {
		if(tables["operationOlaTable"]){
			tables["operationOlaTable"].fnAdjustColumnSizing();
		}
	});

    $("#version").val("1.0.0");
	//初始化SLA表格的方法
	//初始化OLA表格的方法
	
	//保存基本服务定义
    $("#saveOperationDef").button().click(function() {
        var params = {
            operationId:$("#operationId").val(),
            serviceId:$("#serviceId").val(),
            operationName:$("#operationName").val(),
            remark:$("#operationRemark").val(),
            state:$("#state").val(),
            version:$("#version").val()
        };
        operationManager.saveOperationDef(params);
    });
});


var invokeByIdManager = {
    getOperation: function(param) {
        $.ajax({
            url: '../invokeInfo/getOperation/'+param,
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    saveInvokeInfo: function(param) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "../invokeInfo/saveInvokeInfo",
            "data": JSON.stringify(param),
            "dataType": "json",
            "success": function(result) {
                if(result){
                  alert('保存成功!');
                  window.location.href="invokeManager.jsp";
                }else{
                  alert('保存失败!');
                }
            }
        });
    }
}
$(function() {
	var tables = {};
	var asInitVals = new Array();
	$('#tabs').tabs();
	var initInvokeInfo = function initInvokeInfo(params) {
		$('#operationId').val(params.operationId);
		$('#serviceId').val(params.serviceId);
		$('#interfaceId').val(params.interfaceId);
		$('#provideMsgType').val(params.provideMsgType);
		$('#consumeMsgType').val(params.consumeMsgType);
		$('#through').val(params.through);
		$('#state').val(params.state);
		$('#onlineVersion').val(params.onlineVersion);
		$('#onlineDate').val(params.onlineDate);
		$('#prdSysAb').val(params.prdSysAb);
		$('#csmSysAb').val(params.csmSysAb);
		$('#passbySysAb').val(params.passbySysAb);
	};
	// 从URL中得到invokeInfo
	var href = window.location.href;
	var hrefparam = href.split("?")[1];	
	var operationInfo = hrefparam.split("&")[0];
	var operationId = operationInfo.split("=")[1];	
	var serviceInfo = hrefparam.split("&")[1];
	var serviceId = serviceInfo.split("=")[1];	
	var interfaceInfo = hrefparam.split("&")[2];
	var interfaceId = interfaceInfo.split("=")[1];	
	var provideMsgInfo = hrefparam.split("&")[3];
	var provideMsgType = provideMsgInfo.split("=")[1];	
	var consumeMsgInfo = hrefparam.split("&")[4];
	var consumeMsgType = consumeMsgInfo.split("=")[1];	
	var throughInfo = hrefparam.split("&")[5];
	var through = throughInfo.split("=")[1];	
	var prdSysAbInfo = hrefparam.split("&")[9];
	var prdSysAb = prdSysAbInfo.split("=")[1];
	var passbySysAbInfo = hrefparam.split("&")[10];
	var passbySysAb = passbySysAbInfo.split("=")[1];
	var csmSysAbInfo = hrefparam.split("&")[11];
	var csmSysAb = csmSysAbInfo.split("=")[1];
	
	if(through=="Y"){
		through="是";
	}else if(through=="N"){
		through="否";
	}
	var stateInfo = hrefparam.split("&")[6];
	var state = stateInfo.split("=")[1];	
	if(state=="def"){
		state="服务定义"
	}else if(state=="dev"){
		state="开发"
	}else if(state=="union"){
		state="联调测试"
	}else if(state=="sit"){
		state="sit测试"
	}else if(state=="uat"){
		state="uat测试"
	}else if(state=="valid"){
		state="投产验证"
	}else if(state=="online"){
		state="上线"
	}
	var onlineVersionInfo = hrefparam.split("&")[7];
	var onlineVersion = onlineVersionInfo.split("=")[1];	
	var onlineDateInfo = hrefparam.split("&")[8];
	var onlineDate = onlineDateInfo.split("=")[1];	
	var params = {
		operationId:operationId,
		serviceId:serviceId,
		interfaceId:interfaceId,
		provideMsgType:provideMsgType,
		consumeMsgType:consumeMsgType,
		through:through,
		state:state,
		onlineVersion:onlineVersion,
		onlineDate:onlineDate,
		prdSysAb:prdSysAb,
		passbySysAb:passbySysAb,
		csmSysAb:csmSysAb
    };
	initInvokeInfo(params);
	$("#onlineDate").datepicker({
		"changeMonth":true,
		"changeYear":true,
		"dateFormat":"yymmdd"
	});
	$("#saveInvokeInfo").click(function(){
		var operationId = $('#operationId').val();
		var serviceId = $('#serviceId').val();
		var interfaceId = $('#interfaceId').val();
		var provideMsgType = $('#provideMsgType').val();
		var consumeMsgType = $('#consumeMsgType').val();
		var prdSysAb = $('#prdSysAb').val();
		var passbySysAb = $('#passbySysAb').val();
		var csmSysAb = $('#csmSysAb').val();
		var state = $('#state').val();
//		var through = $('#through').val();		
//		var onlineVersion = $('#onlineVersion').val();
//		var onlineDate = $('#onlineDate').val();
		var param = [operationId,serviceId,interfaceId,provideMsgType,consumeMsgType,state,prdSysAb,passbySysAb,csmSysAb];
		invokeByIdManager.saveInvokeInfo(param);
	});
});


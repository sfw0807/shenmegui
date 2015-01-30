
var batchExportManager = {checkExitsInterface:function checkExitsInterface(id, callBack) {
	$.ajax({url:"../invokeInfo/checkExistsByInterfaceId/" + id, type:"GET", dataType:"Text", success:function (result) {
		callBack(result);
	}});
}, checkEcodeAudit:function checkEcodeAudit(params, callBack) {
	$.ajax({"type":"GET", "url":"../export/auditEcode/" + params, "dataType":"Text", success:function (result) {
		callBack(result);
	}});
}, getBatchDuplicateInvokeInfo:function getBatchDuplicateInvokeInfo(params, callBack) {
	$.ajax({"type":"GET", "url":"../export/getBatchDuplicateInvoke/" + params,"dataType": "json", success:function (result) {
		callBack(result);
	}});
}};

var invokeInfoManager = {
    getSystemInvokeServiceInfo : function getSystemInvokeServiceInfo (params,callBack) {
		$.ajax({
            url: "../invokeInfo/syssvc/" + params,
            type: "GET",
            success: function(result) {
                callBack(result);
            }
        });
    }
};
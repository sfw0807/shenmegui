var invokeInfoManager = {
    getSystemInvokeServiceInfo : function getSystemInvokeServiceInfo (callBack) {
		$.ajax({
            url: "/invokeInfo/syssvc",
            type: "GET",
            success: function(result) {
                callBack(result);
            }
        });
    }
};
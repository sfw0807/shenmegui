var invokeManager = {
    getAll : function getAll (callBack) {
		$.ajax({
            url: "../invokeInfo/AllInvokeInfo",
            type: "GET",
            success: function(result) {
                callBack(result);
            }
        });
    }
};
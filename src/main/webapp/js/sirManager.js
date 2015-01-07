var sirManager = {
    getDupSIR : function getDupSIR (callBack) {
		$.ajax({
            url: "/serviceInvokeRelation/listDup",
            type: "GET",
            success: function(result) {
                callBack(result);
            }
        });
    }
};
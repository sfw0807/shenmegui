var publishInfoManager = {
    getAllPublishTotalInfos : function (params,callBack) {
		$.ajax({
            url: "../publish/total/" + params,
            type: "GET",
            success: function(result) {
                callBack(result);
            }
        });
    }
};
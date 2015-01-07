var serviceDetailManager = {
    getAllServiceDetailsInfo : function getAllServiceDetailsInfo (callBack) {
		$.ajax({
            url: "../relateView/svcDetails",
            type: "GET",
            success: function(result) {
                callBack(result);
            }
        });
    }
};
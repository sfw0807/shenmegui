var publishInfoManager = {
    getAllPublishTotalInfos : function (params,callBack) {
		$.ajax({
            url: "../publish/total/" + params,
            type: "GET",
            success: function(result) {
                callBack(result);
            }
        });
    },
    getTotalCountView : function (param,callBack) {
		$.ajax({
            url: "../publishView/total/" + param,
            type: "GET",
            success: function(result) {
                callBack(result);
            }
        });
    },
    getPublishServices : function (param,callBack) {
		$.ajax({
            url: "../publish/totalServiceInfo/" + param,
            type: "GET",
            success: function(result) {
                callBack(result);
            }
        });
    },
    getPublishAddServices : function (param,callBack) {
		$.ajax({
            url: "../publish/totalServiceAddInfo/" + param,
            type: "GET",
            success: function(result) {
                callBack(result);
            }
        });
    },
    getPublishModifyServices : function (param,callBack) {
		$.ajax({
            url: "../publish/totalServiceModifyInfo/" + param,
            type: "GET",
            success: function(result) {
                callBack(result);
            }
        });
    },
    getPublishOperations : function (param,callBack) {
		$.ajax({
            url: "../publish/totalOperationInfo/" + param,
            type: "GET",
            success: function(result) {
                callBack(result);
            }
        });
    },
    getPublishAddOperations : function (param,callBack) {
		$.ajax({
            url: "../publish/totalOperationAddInfo/" + param,
            type: "GET",
            success: function(result) {
                callBack(result);
            }
        });
    },
    getPublishModifyOperations : function (param,callBack) {
		$.ajax({
            url: "../publish/totalOperationModifyInfo/" + param,
            type: "GET",
            success: function(result) {
                callBack(result);
            }
        });
    }
};
var invokeManager = {
    getAll : function getAll (callBack) {
		$.ajax({
            url: "../relateView/svcasm",
            type: "GET",
            success: function(result) {
                callBack(result);
            }
        });
    },
    delInvoke: function(params, callBack) {
        $.ajax({
            url: '../invokeInfo/delInvoke/' + params,
            type: 'GET',
            dataType: 'Text',
            success: function(result) {
                callBack(result);
            }
        });
    }
};
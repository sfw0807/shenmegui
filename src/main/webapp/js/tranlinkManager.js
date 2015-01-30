var tranlinkManager = {
    getAllTranlinkInfo : function getAllTranlinkInfo (callBack) {
		$.ajax({
            url: "../tranlink/getAll",
            type: "GET",
            success: function(result) {
                callBack(result);
            }
        });
    }
};
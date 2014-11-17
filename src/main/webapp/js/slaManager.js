var slaManager = {
    getAllSLAInfo : function getAllSLAInfo (callBack) {
		$.ajax({
            url: "../slaview/allslaview",
            type: "GET",
            success: function(result) {
                callBack(result);
            }
        });
    }
};
var svcAsmRelateManager = {
    getAllSvcAsmRelateInfo : function getAllSvcAsmRelateInfo (callBack) {
		$.ajax({
            url: "../relateView/svcasm",
            type: "GET",
            success: function(result) {
                callBack(result);
            }
        });
    },
    getAllExportInvokeInfos : function getAllExportInvokeInfos (callBack) {
		$.ajax({
            url: "../relateView/configExport",
            type: "GET",
            success: function(result) {
                callBack(result);
            }
        });
    },
    exportConfig : function exportConfig (params) {
		$.ajax({
		    "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "../export/config/",
            "dataType": "json",
            "data":JSON.stringify(params),
            success: function(result) {
            }
        });
    }
};
var auditManager = {
    getAuditOperation: function(callBack) {
        $.ajax({
            url: '../operationInfo/auditList',
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    auditOperation: function(callBack, params) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "../operationInfo/audit",
            "dataType": "json",
            "data":JSON.stringify(params),
            "success": function(result) {
                callBack(result);
            }
        });
    },
    getAuditService: function(callBack) {
        $.ajax({
            url: '../serviceInfo/auditList',
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    auditService: function(callBack, params) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "../serviceInfo/audit",
            "dataType": "json",
            "data":JSON.stringify(params),
            "success": function(result) {
                callBack(result);
            }
        });
    }
}
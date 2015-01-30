var systemInfoManager = {

    getAll: function(callBack) {
        $.ajax({
            "url": '/systemInfo/list',
            "type": 'GET',
            "success": function(result) {
                callBack(result);
            }
        });
    },
    deleteById: function(id, callBack) {
        $.ajax({
            "url": '/systemInfo/delete/' + id,
            "type": 'GET',
            "success": function(result) {
                callBack(result);
            }
        });
    },
    getSystemById: function(id,callBack) {
        $.ajax({
            "url": '/systemInfo/getSystemById/' + id,
            "type": 'GET',
            "success": function(result) {
                 callBack(result);
            }
        });
    },
    
    checkSystemUsed: function(id,callBack) {
        $.ajax({
            "url": '/systemInfo/checkSystemUsed/' + id,
            "type": 'GET',
            "dataType": 'Text',
            "success": function(result) {
                 callBack(result);
            } 
        });
    },
    
    insert: function(system) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "/systemInfo/insert",
            "dataType": "json",
            "data":JSON.stringify(system),
            "success": function(result) {
            }
        });
    },
    update: function(system) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "/systemInfo/update",
            "dataType": "json",
            "data":JSON.stringify(system),
            "success": function(result) {
            }
        });
    },
    getProtocolInfosById: function(id,callBack) {
        $.ajax({
            "url": '/systemInfo/getProtocolInfosBySysId/' + id,
            "type": 'GET',
            "success": function(result) {
                 callBack(result);
            }
        });
    },
    saveProtocolInfos: function(params, callBack) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "/systemInfo/saveProtocolInfos",
            "dataType": "json",
            "data":JSON.stringify(params),
            "success": function(result) {
                callBack(result);
            }
        });
    }
}

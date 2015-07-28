/**
 * Created by vincentfxz on 15/7/8.
 */
var serviceLinkManager = {
    "getAll" : function(callBack){
        $.ajax({
            "type": "GET",
            "contentType": "application/json; charset=utf-8",
            "url": "/serviceLink/list",
            "dataType": "json",
            "success": function (result) {
                callBack(result);
            }
        });
    },
    "getConnectionsBySourceId" : function getConnectionsBySourceId (sourceId, callBack){
        $.ajax({
            "type": "GET",
            "contentType": "application/json; charset=utf-8",
            "url": "/serviceLink/invokeConnections/sourceId/" + sourceId,
            "dataType": "json",
            "success": function (result) {
                callBack(result);
            }
        });
    },
    "getParentConnectionsBySourceId" : function getParentConnectionsBySourceId(sourceId, callBack){
        $.ajax({
            "type": "GET",
            "contentType": "application/json; charset=utf-8",
            "url": "/serviceLink/parentInvokeConnections/sourceId/" + sourceId,
            "dataType": "json",
            "success": function (result) {
                callBack(result);
            }
        });
    },
    "saveConnections" : function saveConnections (connections, callBack){
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "/serviceLink/save",
            "data": JSON.stringify(connections),
            "dataType": "json",
            "success": function (result) {
                callBack(result);
            }
        });
    },
    "delConnections" : function delConnections (connections, callBack){
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "/serviceLink/delete",
            "data": JSON.stringify(connections),
            "dataType": "json",
            "success": function (result) {
                callBack(result);
            }
        });
    }
};
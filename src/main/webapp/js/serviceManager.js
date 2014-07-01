/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-6-4
 * Time: 上午10:29
 * To change this template use File | Settings | File Templates.
 */
var serviceManager = {
    getAll: function(callBack) {
        $.ajax({
            url: '/service/list',
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    getById: function(id, callBack) {
        $.ajax({
            url: '/service/list/' + id,
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    deleteById: function(id, callBack) {
        $.ajax({
            url: '/service/delete/' + id,
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    modify: function(service, callBack) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "/service/list",
            "data": JSON.stringify(service),
            "dataType": "json",
            "success": function(result) {
                callBack(result);
            }
        });
    },
    add: function (service, callBack) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "/service/list",
            "data": JSON.stringify(service),
            "dataType": "json",
            "success": function(result) {
                callBack(result);
            }
        });
    },
    getInvokeInfoById: function(id, callBack) {
        $.ajax({
            url: '/service/getInvokeRelation/' + id,
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    getByOperationId : function (id, callBack){
        $.ajax({
            url: '/service/getByOperationId/' + id,
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    exportWSDL : function (id, callBack){
        $.ajax({
            url: '/wsdl/byService/' + id,
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    }
}

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-6-4
 * Time: 上午10:29
 * To change this template use File | Settings | File Templates.
 */
var interfaceManager = {
    getAll: function(callBack) {
        $.ajax({
            url: '/interface/list',
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    getByOperationId: function(id, callBack) {
        $.ajax({
            url: '/interface/byOperationId/' + id,
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    getById: function(id, callBack) {
        $.ajax({
            url: '/interface/list/' + id,
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    deleteById: function(id, callBack) {
        $.ajax({
            url: '/interface/delete/' + id,
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
            "url": "/interface/list",
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
            "url": "/interface/list",
            "data": JSON.stringify(service),
            "dataType": "json",
            "success": function(result) {
                callBack(result);
            }
        });
    }
}
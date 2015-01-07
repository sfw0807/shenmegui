/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-6-4
 * Time: 上午10:29
 * To change this template use File | Settings | File Templates.
 */
var systemManager = {
    getAll: function(callBack) {
        $.ajax({
            url: '../system/list',
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    getById: function(id, callBack) {
        $.ajax({
            url: '../system/list/' + id,
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    deleteById: function(id, callBack) {
        $.ajax({
            url: '../system/delete/' + id,
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
            "url": "../system/list",
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
            "url": "../system/list",
            "data": JSON.stringify(service),
            "dataType": "json",
            "success": function(result) {
                callBack(result);
            }
        });
    },
    getInvokeInfoById: function(id, callBack) {
        $.ajax({
            url: '../system/getInvokeRelation/' + id,
            type: 'GET',
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    getServiceDevProgress: function(callBack) {
        $.ajax({
            url: '../serviceDevInfo/serviceDevProgress/',
            type: 'GET',
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    }
}

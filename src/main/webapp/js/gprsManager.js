/**
 * Created with IntelliJ IDEA.
 * User: Vincent Fan
 * Date: 14-2-23
 * Time: 下午4:27
 * To change this template use File | Settings | File Templates.
 */

var gprsManager = {
    getAll: function(callBack) {
        $.ajax({
            url: '/gprs/list',
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    getById: function(id, callBack) {
        $.ajax({
            url: '/gprs/list/' + id,
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    deleteById: function(id, callBack) {
        $.ajax({
            url: '/gprs/delete/' + id,
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    modify: function(gprs, callBack) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "/gprs/list",
            "data": JSON.stringify(gprs),
            "dataType": "json",
            "success": function(result) {
                callBack(result);
            }
        });
    },
    add: function(gprs, callBack) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "/gprs/list",
            "data": JSON.stringify(gprs),
            "dataType": "json",
            "success": function(result) {
                callBack(result);
            }
        });
    }
}

/**
 * Created with IntelliJ IDEA.
 * User: Vincent Fan
 * Date: 14-2-23
 * Time: 下午4:29
 * To change this template use File | Settings | File Templates.
 */

var ammeterManager = {
    deleteById: function(id, callBack) {
        if (id) {
            $.ajax({
                url: '/ammeter/delete/' + id,
                type: 'GET',
                success: function(result) {
                    callBack(result);
                }
            });
        }
    },
    getById: function(id, callBack) {
        if (id) {
            $.ajax({
                url: '/ammeter/list/' + id,
                type: 'GET',
                success: function(result) {
                    callBack(result);
                }
            });

        }
    },
    getAll: function(callBack) {
        $.ajax({
            url: '/ammeter/list',
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    modify: function(ammeter, callBack) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "/ammeter/list",
            "data": JSON.stringify(ammeter),
            "dataType": "json",
            "success": function(result) {
                callBack(result);
            }
        });
    },
    add: function(ammeter, callBack) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "/ammeter/list",
            "data": JSON.stringify(ammeter),
            "dataType": "json",
            "success": function(result) {
                callBack(result);
            }
        });
    }
};
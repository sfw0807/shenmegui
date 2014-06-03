/**
 * Created with IntelliJ IDEA.
 * User: Vincent Fan
 * Date: 14-2-23
 * Time: 下午3:54
 * To change this template use File | Settings | File Templates.
 */

var companyManager = {
    getAll: function(callBack) {
        $.ajax({
            url: '/company/list',
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    getById: function(id, callBack) {
        $.ajax({
            url: '/company/list/' + id,
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    deleteById: function(id, callBack) {
        $.ajax({
            url: '/company/delete/' + id,
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    modify: function(company, callBack) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "/company/list",
            "data": JSON.stringify(company),
            "dataType": "json",
            "success": function(result) {
                callBack(result);
            }
        });
    },
    add: function (company, callBack) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "/company/list",
            "data": JSON.stringify(company),
            "dataType": "json",
            "success": function(result) {
                callBack(result);
            }
        });
    }
}
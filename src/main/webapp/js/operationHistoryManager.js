/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-6-4
 * Time: 上午10:29
 * To change this template use File | Settings | File Templates.
 */
var operationHistoryManager = {
    getOperation: function(params,callBack) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "/operationHistory/getOperation",
            "data": JSON.stringify(params),
            "dataType": "json",
            "success": function(result) {
        		callBack(result);
            }
        });
    },
    getSDA: function(params,callBack) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "/operationHistory/getSDA",
            "data": JSON.stringify(params),
            "dataType": "json",
            "success": function(result) {
        		callBack(result);
            }
        });
    },
    getSLA: function(params,callBack) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "/operationHistory/getSLA",
            "data": JSON.stringify(params),
            "dataType": "json",
            "success": function(result) {
        		callBack(result);
            }
        });
    },
    getOLA: function(params,callBack) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "/operationHistory/getOLA",
            "data": JSON.stringify(params),
            "dataType": "json",
            "success": function(result) {
        		callBack(result);
            }
        });
    }
}

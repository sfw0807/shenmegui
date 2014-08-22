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
    getServiceById : function (id, callBack){
        $.ajax({
            url: '/service/getServiceById/' + id,
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    getServiceByResourceId : function (resourceId, callBack){
        $.ajax({
            url: '/service/getServiceByResourceId/' + resourceId,
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    getServiceSlaById : function (id, callBack){
        $.ajax({
            url: '/service/getServiceSlaById/' + id,
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    getServiceOlaById : function (id, callBack){
        $.ajax({
            url: '/service/getServiceOlaById/' + id,
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
    },
    getServiceExtendInfo : function (id, callBack){
        $.ajax({
            url: '/service/getServiceExtendInfoByOperationId/' + id,
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    getChildSDA : function (id, callBack){
        $.ajax({
            url: '/service/getServiceChild/' + id,
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    getChildSdaByResourceId : function (id, callBack){
        $.ajax({
            url: '/service/getServiceChildByResourceId/' + id,
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    }
}

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-6-4
 * Time: 上午10:29
 * To change this template use File | Settings | File Templates.
 */
var interfaceManager = {
 	getInterfaceManagementList: function(callBack) {
        $.ajax({
            url: '../interfaceManagement/getAllList',
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    getAllServices: function(callBack) {
        $.ajax({
            url: '../interfaceManagement/getAllServices',
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    getOperationOfService: function(serviceId, callBack) {
        $.ajax({
            url: '../interfaceManagement/getOperationOfService/' + serviceId,
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    getVO: function(ecode, callBack) {
        $.ajax({
            url: '../interfaceManagement/getVO/' + ecode,
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    deleteInterfaceInfos: function(params, callBack) {
        $.ajax({
            url: '../interfaceManagement/deleteInterfaceInfos/' + params,
            type: 'GET',
            success: function(){
            	callBack();
            }
        });
    },
    getIDAs: function(ecode, callBack) {
        $.ajax({
            url: '../interfaceManagement/getIDAs/' + ecode,
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    update: function(params, callBack) {
        $.ajax({
         	"contentType": "application/json; charset=utf-8",
            url: '../interfaceManagement/update/' + params,
            type: 'POST',
            success: function(result) {
                callBack(result);
            }
        });
    },
     insert: function(params, callBack) {
        $.ajax({
            "contentType": "application/json; charset=utf-8",
            "url": '../interfaceManagement/insert',
            "type": 'POST',
            "data": params,
            "dataType": "json",
            success: function(result) {
                callBack(result);
            }
        });
    },
    saveIDA: function(params, callBack) {
        $.ajax({
            url: '../interfaceManagement/saveIDA/' + params,
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    deleteIDAs: function(params, callBack) {
        $.ajax({
            url: '../interfaceManagement/deleteIDAs/' + params,
            type: 'GET',
            "dataType": "Text",
            success: function(result) {
            	console.log(result);
                callBack(result);
            }
        });
    },
     saveIDA1: function(params, callBack) {
        $.ajax({
        	"contentType": "application/json; charset=utf-8",
            url: '../interfaceManagement/saveIDA/',
            type: 'POST',
            data: params,
            dataType: "json",
            success: function(result) {
                callBack(result);
            }
        });
    },
    getAll: function(callBack) {
        $.ajax({
            url: '../interface/list',
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    getViewList_old: function(callBack) {
        $.ajax({
            url: '../excel/list',
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    getViewList: function(callBack) {
        $.ajax({
            url: '../exportExcel/list',
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    isSysLinked_old: function(id, callBack) {
        $.ajax({
            url: '../excel/isSysLinked/' + id,
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    isSysLinked: function(id, callBack) {
        $.ajax({
            url: '../exportExcel/isSysLinked/' + id,
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    getByOperationId: function(id, callBack) {
        $.ajax({
            url: '../interface/byOperationId/' + id,
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    getById: function(id, callBack) {
        $.ajax({
            url: '../interface/list/' + id,
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    deleteById: function(id, callBack) {
        $.ajax({
            url: '../interface/delete/' + id,
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
    },
    getInterfaceInfo: function(id, callBack) {
        $.ajax({
            "url": '../interface/getInterfaceInfo/' + id,
            "type": 'GET',
            "success": function(result) {
                callBack(result);
            }
   	 	});
    },
    getInterfaceExtendInfo: function(id, callBack) {
	    $.ajax({
	        url: '../interface/getInterfaceExtendInfo/' + id,
	        type: 'GET',
	        success: function(result) {
	            callBack(result);
	        }
	    });
    },
    getChildSDA4IInfo: function(id, callBack) {
	    $.ajax({
	        url: '../interface/getInterfaceChildInfo/' + id,
	        type: 'GET',
	        success: function(result) {
	            callBack(result);
	        }
	    });
    }
}

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-6-4
 * Time: 上午10:29
 * To change this template use File | Settings | File Templates.
 */
var operationManager = {
    getOperation: function(operationId,serviceId,callBack) {
        $.ajax({
            url: '../operationInfo/getOperation/'+serviceId+operationId,
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    getSDAInfoByOperationId: function(operationId,serviceId,callBack) {
        $.ajax({
            url: '../operationInfo/getSDAInfoByOperationId/'+serviceId+operationId,
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    getSLAByOperationId: function(operationId,serviceId, callBack) {
        $.ajax({
            url: '../operationInfo/getSLAByOperationId/' +serviceId+operationId,
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    getOLAByOperationId: function(operationId,serviceId, callBack) {
        $.ajax({
            url: '../operationInfo/getOLAByOperationId/' +serviceId+operationId,
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    saveOperationDef: function(operation) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "../operationInfo/addOperation",
            "data": JSON.stringify(operation),
            "dataType": "json",
            "success": function(result) {
                if(result){
                  alert('保存成功!');
                  window.location.href="operationInfoById.jsp?operationId='"+operation.operationId+"'&serviceId='"+operation.serviceId+"'&version='"+operation.version+"'&publishVersion=''&publishDate=''";
                }else{
                  alert('保存失败!');
                }
            }
        });
    },
    saveOperationSDA: function (sda) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "../operationInfo/addSDA",
            "data": JSON.stringify(sda),
            "dataType": "json",
            "success": function(result) {
        		if(result){
        			alert("保存成功");
        			window.location.reload();
        		}else{
        			alert("保存失败");
        		}
            }
        });
    },
    saveOperationSLA: function (sla) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "../operationInfo/addSLA",
            "data": JSON.stringify(sla),
            "dataType": "json",
            "success": function(result) {
        		if(result){
        			alert("保存成功");
        			window.location.reload();
        		}else{
        			alert("保存失败");
        		}
            }
        });
    },
    saveOperationOLA: function(ola) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "../operationInfo/addOLA",
            "data": JSON.stringify(ola),
            "dataType": "json",
            "success": function(result) {
        		if(result){
        			alert("保存成功");
        			window.location.reload();
        		}else{
        			alert("保存失败");
        		}
            }
        });
    }
}

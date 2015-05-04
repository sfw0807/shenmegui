/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-6-4
 * Time: 上午10:29
 * To change this template use File | Settings | File Templates.
 */
var operationManager = {
    getAll: function(callBack) {
        $.ajax({
            url: '../operationInfo/alloperations',
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    deleteOperation: function(operationId,serviceId) {
        $.ajax({
            url: '../operationInfo/deleteOperation/'+serviceId+"/"+operationId,
            type: 'GET',
            success: function(result) {
               if(result){
            	   alert("删除成功");
            	   window.location.reload();
               }else{
            	   alert("删除失败");
               }
            }
        });
    },
    deployOperation: function(operationId,serviceId) {
        $.ajax({
            url: '../operationInfo/deployOperation/'+serviceId+"/"+operationId,
            type: 'GET',
            success: function(result) {
               if(result){
            	   alert("发布成功");
            	   window.location.reload();
               }else{
            	   alert("发布失败");
               }
            }
        });
    },
    redefOperation: function(operationId,serviceId) {
        $.ajax({
            url: '../operationInfo/redefOperation/'+serviceId+operationId,
            type: 'GET',
            success: function(result) {
               if(result){
            	   alert("重定义成功");
            	   window.location.reload();	
               }else{
            	   alert("重定义失败");
               }
            }
        });
    },
    publishOperation: function(operationId,serviceId) {
        $.ajax({
            url: '../operationInfo/publishOperation/'+serviceId+operationId,
            type: 'GET',
            success: function(result) {
               if(result){
            	   alert("上线成功");
            	   window.location.reload();
               }else{
            	   alert("上线失败");
               }
            }
        });
    },
    submitOperation: function(callBack, params) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "../operationInfo/submit",
            "dataType": "json",
            "data":JSON.stringify(params),
            "success": function(result) {
                callBack(result);
            }
        });
    }
}

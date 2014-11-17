/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-6-4
 * Time: 上午10:29
 * To change this template use File | Settings | File Templates.
 */
var invokeByIdManager = {
    getOperation: function(param) {
        $.ajax({
            url: '../invokeInfo/getOperation/'+param,
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    saveInvokeInfo: function(param) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "../invokeInfo/saveInvokeInfo",
            "data": JSON.stringify(param),
            "dataType": "json",
            "success": function(result) {
                if(result){
                  alert('保存成功!');
                  window.location.href="invokeManager.jsp?operationId='"+operation.operationId+"'&serviceId='"+operation.serviceId+"'&version='"+operation.version+"'&publishVersion=''&publishDate=''";
                }else{
                  alert('保存失败!');
                }
            }
        });
    }
}

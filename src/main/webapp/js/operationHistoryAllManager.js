/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-6-4
 * Time: 上午10:29
 * To change this template use File | Settings | File Templates.
 */
var operationHistoryManager = {
    getAllHistory: function(operationId,serviceId,callback) {
        $.ajax({
            url: '../operationHistory/allHistory/'+serviceId+operationId,
            type: 'GET',
            success: function(result) {
               callback(result);
            }
        });
    },
    backOperation: function(params){
    	$.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "../operationHistory/backOperation",
            "data": JSON.stringify(params),
            "dataType": "json",
            "success": function(result) {
        		if(result){
        			alert("还原成功");
        			window.location.href="operationManager.jsp";
        		}else{
        			alert("还原失败");
        		}
            }
		});
    }
}

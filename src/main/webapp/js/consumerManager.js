var consumerManager = {
    getConsumer : function getConsumer(param,callBack) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "../invokeInfo/consumer",
            "data": JSON.stringify(param),
            "dataType": "json",
            "success": function(result) {
        		callBack(result);
            }
        });
   },
   addConsumer : function addConsumer(param) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "../invokeInfo/addconsumer",
            "data": JSON.stringify(param),
            "dataType": "json",
            "success": function(result) {
        		if(result){
        			alert("新增成功");
        			window.location.href="consumer.jsp?operationId="+param[0]
        			+"&serviceId="+param[1]
        			+"&interfaceId="+param[2]
        			+"&provideMsgType="+param[3]
        			+"&consumeMsgType="+param[4]
        			+"&through="+param[5]
        			+"&state="+param[6]
        			+"&onlineVersion="+param[7]
        			+"&onlineDate="+param[8]
        			+"&provideSysAb="+param[9];
        		}else{
        			alert("新增失败");
        		}
            }
        });
    }
};
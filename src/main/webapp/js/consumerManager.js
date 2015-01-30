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
   addConsumer : function addConsumer(param, callBack) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "../invokeInfo/addconsumer",
            "data": JSON.stringify(param),
            "dataType": "json",
            "success": function(result) {
                  callBack(result);
            }
        });
    }
};
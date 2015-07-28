/**
 * Created by vincentfxz on 15/7/15.
 */
var metadataManager ={
    "query" : function(data,callBack){
        $.ajax({
            "type" : "POST",
            "contentType" : "application/json;charset=utf-8",
            "url" : "/metadata/query",
            "data": JSON.stringify(data),
            "dataType": "json",
            "success": function(result) {
                callBack(result);
            }
        });
    }
};
/**
 * Created by vincentfxz on 15/7/8.
 */
var serviceLinkManager = {
    "getAll" : function(callBack){
        $.ajax({
            "type": "GET",
            "contentType": "application/json; charset=utf-8",
            "url": "/serviceLink/list",
            "dataType": "json",
            "success": function (result) {
                callBack(result);
            }
        });
    }
};
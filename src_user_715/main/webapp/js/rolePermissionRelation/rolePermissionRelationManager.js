/**
 * Created by lenovo on 2015/7/15.
 */

var rolePermissionRelationManager= {
    "save" : function(param , callBack) {
        var url = "/rprelation/save";
        $.ajax({
            "type" : "POST",
            "contentType" : "application/json; charset=utf-8",
            data: JSON.stringify(param),
            "url" : url,
            "dataType" : "json",
            "success" : function(result) {
                callBack(result);
            }
        });
    },
    "getById": function(data, callBack){
        $.ajax({
            type: "GET",
            contentType: "application/json; charset=utf-8",
            url: "/rprelation/getById/"+data,
            dataType: "json",
            success: function(result) {
                callBack(result);
            }
        });
    },
    "modify" : function(param , callBack) {
        var url = "/rprelation/modify";
        $.ajax({
            "type" : "POST",
            "contentType" : "application/json; charset=utf-8",
            data: JSON.stringify(param),
            "url" : url,
            "dataType" : "json",
            "success" : function(result) {
                callBack(result);
            }
        });
    },
    "delet" : function(param,callBack) {
        $.ajax({
            "type" : "DELETE",
            "contentType" : "application/json; charset=utf-8",
            "url" : "/rprelation/delete/"+param,
            "dataType" : "json",
            "success" : function(result) {
                callBack(result);
            }
        });
    }
};


/**
用户管理
**/
var userManager = {
    "add" : function(data, callBack){
        $.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8",
            url: "/user/add",
            data: JSON.stringify(data),
            dataType: "json",
            success: function(result) {
                callBack(result);
            }
        });
    },
    "getAll" : function(callBack){
        $.ajax({
            type: "GET",
            contentType: "application/json; charset=utf-8",
            url: "/user/getAll",
            data: JSON.stringify(),
            dataType: "json",
            success: function(result) {
                callBack(result);
            }
        });
    },
    "getByParams" : function(param, callBack) {
        var url = "/user/get";
        url += "/id/" +param.id;
        url += "/name/" +param.name;
        url += "/orgId/" +param.orgId;
        url += "/startdate/" +param.startdate;
        url += "/lastdate/" +param.lastdate;
        $.ajax({
            "type" : "GET",
            "contentType" : "application/json; charset=utf-8",
            "url" : url,
            "dataType" : "json",
            "success" : function(result) {
                callBack(result);
            }
        });
    },
    "deleteById" : function(id,callBack) {
        $.ajax({
            "type" : "DELETE",
            "contentType" : "application/json; charset=utf-8",
            "url" : "/user/delete/"+id,
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
            url: "/user/getById/"+data,
            data: JSON.stringify(data),
            dataType: "json",
            success: function(result) {
                callBack(result);
            }
        });
    },
    "modify": function(data,callBack){
        $.ajax({
            type: "post",
            contentType: "application/json; charset=utf-8",
            url: "/user/modify",
            data: JSON.stringify(data),
            dataType: "json",
            success: function(result) {
                callBack(result);
            }
        });
    },
    "passWord": function(data,callBack){
        $.ajax({
            type: "post",
            contentType: "application/json; charset=utf-8",
            url: "/user/passWord",
            data: JSON.stringify(data),
            dataType: "json",
            success: function(result) {
                callBack(result);
            }
        });
    }
};
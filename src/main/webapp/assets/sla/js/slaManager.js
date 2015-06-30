
var slaManager = {
    "add" : function(data, callBack) {
        $.ajax({
            "type" : "POST",
            "contentType" : "application/json; charset=utf-8",
            "url" : "/sla/add",
            "data" : JSON.stringify(data),
            "dataType" : "json",
            "success" : function(result) {
                callBack(result);
            }
        });
    },
    "modify" : function(data, callBack) {
        $.ajax({
            "type" : "POST",
            "contentType" : "application/json; charset=utf-8",
            "url" : "/sla/modify",
            "data" : JSON.stringify(data),
            "dataType" : "json",
            "success" : function(result) {
                callBack(result);
            }
        });
    },
    "deleteById" : function(id, callBack) {
        $.ajax({
            "type" : "DELETE",
            "contentType" : "application/json; charset=utf-8",
            "url" : "/sla/delete/" + id,
            "dataType" : "json",
            "success" : function(result) {
                callBack(result);
            }
        });
    },
    "getByParams" : function(param, callBack) {
        var url = "/slaTemplate/getSLA/"+param;
        $.ajax({
            "type" : "GET",
            "contentType" : "application/json; charset=utf-8",
            "url" : url,
            "dataType" : "json",
            "success" : function(result) {
                callBack(result);
            }
        });
    }
};


var slaTemplateManager = {
    "add" : function(data, callBack) {
        $.ajax({
            "type" : "POST",
            "contentType" : "application/json; charset=utf-8",
            "url" : "/slaTemplate/add",
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
            "url" : "/slaTemplate/modify",
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
            "url" : "/slaTemplate/delete/" + id,
            "dataType" : "json",
            "success" : function(result) {
                callBack(result);
            }
        });
    }
};

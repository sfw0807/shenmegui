var categoryWordManager = {
    "add": function (data, callBack) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "/categoryWord/add",
            "data": JSON.stringify(data),
            "dataType": "json",
            "success": function (result) {
                callBack(result);
            }
        });
    },
    "modify": function (data, callBack) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "/categoryWord/modify",
            "data": JSON.stringify(data),
            "dataType": "json",
            "success": function (result) {
                callBack(result);
            }
        });
    },
    "deleteRow": function (id, callBack) {
        $.ajax({
            "type": "DELETE",
            "contentType": "application/json; charset=utf-8",
            "url": "/categoryWord/delete/" + id,
            "data": JSON.stringify(id),
            "dataType": "json",
            "success": function (result) {
                callBack(result);
            }
        });
    },
    "getByParams": function (param, callBack) {
        var url = "/categoryWord/get";
        url += "/EnglishWord/" + param.englishWord;
        url += "/ChineseWord/" + param.chineseWord;
        url += "/EsglisgAb/" + param.esglisgAb;
        url += "/Remark/" + param.remark;
        $.ajax({
            "type": "GET",
            "contentType": "application/json; charset=utf-8",
            "url": url,
            "dataType": "json",
            "success": function (result) {
                callBack(result);
            }
        });
    },
    "saveCategoryWord": function (data, callBack) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "/categoryWord/saveCategoryWord",
            "dataType": "json",
            "data": JSON.stringify(data),
            "success": function (result) {
                callBack(result);
            }
        });
    },
    "deleteCategoryWord": function (data, callBack) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "/categoryWord/deleteCategoryWord",
            "dataType": "json",
            "data": JSON.stringify(data),
            "success": function (result) {
                callBack(result);
            }
        });
    },
    "query" : function(data,callBack){
        $.ajax({
            "type" : "POST",
            "contentType" : "application/json;charset=utf-8",
            "url" : "/categoryWord/query",
            "data": JSON.stringify(data),
            "dataType": "json",
            "success": function(result) {
                callBack(result);
            }
        });
    }
};
/**
 * Created by lenovo on 2015/7/14.
 */
var permissionManager = {
    "getAll": function (callBack) {
        $.ajax({
            type: "GET",
            contentType: "application/json; charset=utf-8",
            url: "/permission/getAll",
            data: JSON.stringify(),
            dataType: "json",
            success: function (result) {
                callBack(result);
            }
        });
    }

};
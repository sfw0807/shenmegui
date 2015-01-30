var passwordManager = {

    getAll: function(callBack) {
        $.ajax({
            url: '../user/list',
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    update: function(params) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "../user/updatePwd",
            "dataType": "json",
            "data":JSON.stringify(params),
            "success": function(result) {
                 if(result){
                    alert('修改成功!');
                 }
                 else{
                    alert('修改失败!');
                 }
            }
        });
    },
    checkOriginalPwd: function(params, callBack) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "../user/checkOldPwd",
            "dataType": "json",
            "data":JSON.stringify(params),
            "success": function(result) {
                 callBack(result);
            }
        });
    },
    reset: function(params) {
        $.ajax({
            url: '../user/reset/' + params,
            type: 'GET',
            success: function(result) {
               if(result){
                  alert('重置密码成功!');
               }
               else{
                  alert('重置密码失败!');
               }
            } 
        });
    }
}

var serviceInfoManager = {

    getAll: function(callBack) {
        $.ajax({
            url: '../serviceInfo/list',
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    getServiceById: function(id,callBack) {
        $.ajax({
            url: '../serviceInfo/getServiceById/' + id,
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    deleteById: function(id) {
        $.ajax({
            url: '../serviceInfo/delByServiceId/' + id,
            type: 'GET',
            success: function(result) {
                if(result){
                  alert('删除成功!');
                  window.location.reload();
                }
                else{
                  alert('删除失败!');
                }
            }
        });
    },
    insert: function(service) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "../serviceInfo/insertOrUpdate",
            "dataType": "json",
            "data":JSON.stringify(service),
            "success": function(result) {
                if(result){
                  alert('插入数据成功!');
                  window.location.reload();
                }
                else{
                  alert('插入数据失败!');
                }
            }
        });
    },
    deploy: function(id) {
        $.ajax({
            url: '../serviceInfo/deploy/' + id,
            type: 'GET',
            success: function(result) {
                if(result){
                  alert('发布成功!');
                  window.location.reload();
                }
                else{
                  alert('发布失败!');
                }
            }
        });
    },
    redef: function(id) {
        $.ajax({
            url: '../serviceInfo/redef/' + id,
            type: 'GET',
            success: function(result) {
                if(result){
                  alert('重定义成功!');
                  window.location.reload();
                }
                else{
                  alert('重定义失败!');
                }
            }
        });
    },
    publish: function(id) {
        $.ajax({
            url: '../serviceInfo/publish/' + id,
            type: 'GET',
            success: function(result) {
                if(result){
                  alert('上线成功!');
                  window.location.reload();
                }
                else{
                  alert('上线失败!');
                }
            }
        });
    },
    update: function(service) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "../serviceInfo/insertOrUpdate",
            "dataType": "json",
            "data":JSON.stringify(service),
            "success": function(result) {
                if(result){
                  alert('修改成功!');
                  window.location.reload();
                }
                else{
                  alert('修改失败!');
                }
            }
        });
    },
    getOperationsById: function(id, callBack) {
        $.ajax({
            url: '../serviceInfo/getOperationsById/' + id,
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    checkExistOperation: function(id, callBack) {
        $.ajax({
            url: '../serviceInfo/checkExistOperation/' + id,
            type: 'GET',
            dataType: 'Text',
            success: function(result) {
                callBack(result);
            }
        });
    },
    submitService: function(callBack, params) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "../serviceInfo/submit",
            "dataType": "json",
            "data":JSON.stringify(params),
            "success": function(result) {
                callBack(result);
            }
        });
    }
}

var serviceCategoryManager = {

    getAll: function(callBack) {
        $.ajax({
            "url": '../serviceCategory/list',
            "type": 'GET',
            "success": function(result) {
                callBack(result);
            }
        });
    },
    getSecondLevelInfo: function(callBack) {
        $.ajax({
            "url": '../serviceCategory/second',
            "type": 'GET',
            "success": function(result) {
                callBack(result);
            }
        });
    },
    deleteById: function(id) {
        $.ajax({
            "url": '../serviceCategory/delete/' + id,
            "type": 'GET',
            "success": function(result) {
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
    getServiceCategoryById: function(id,callBack) {
        $.ajax({
            "url": '../serviceCategory/getServiceCategoryById/' + id,
            "type": 'GET',
            "success": function(result) {
                 callBack(result);
            }
        });
    },
    checkIsFather: function(id,callBack) {
        $.ajax({
            "url": '../serviceCategory/checkIsFather/' + id,
            "type": 'GET',
            "dataType": 'Text',
            "success": function(result) {
                 callBack(result);
            } 
        });
    },
    checkIsUsed: function(id,callBack) {
        $.ajax({
            "url": '../serviceCategory/checkIsUsed/' + id,
            "type": 'GET',
            "success": function(result) {
                 callBack(result);
            } 
        });
    },
    insert: function(serviceCategory) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "../serviceCategory/insertOrUpdate",
            "dataType": "json",
            "data":JSON.stringify(serviceCategory),
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
    update: function(serviceCategory) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "../serviceCategory/insertOrUpdate",
            "dataType": "json",
            "data":JSON.stringify(serviceCategory),
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
    }
}

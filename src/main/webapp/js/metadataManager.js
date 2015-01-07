var metadataManager = {
    getAllMetadataList : function (callBack) {
		$.ajax({
            url: "../metadata/list/",
            type: "GET",
            success: function(result) {
                callBack(result);
            }
        });
    },
    getMetadataById : function (id,callBack) {
		$.ajax({
            url: "../metadata/exist/"+id,
            type: "GET",
            success: function(result) {
                callBack(result);
            }
        });
    },
    getMdtUsedById : function (id,callBack) {
		$.ajax({
            url: "../metadata/mdtused/"+id,
            type: "GET",
            success: function(result) {
                callBack(result);
            }
        });
    },
    checkIsUsed: function(id,callBack) {
        $.ajax({
            "url": '../metadata/checkIsUsed/' + id,
            "type": 'GET',
            "dataType": 'Text',
            "success": function(result) {
                 callBack(result);
            } 
        });
    },
    insertByMetadata : function (params) {
		$.ajax({
		    "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "../metadata/insert",
            "data": JSON.stringify(params),
            "dataType": "json",
            success: function(result) {
                if(result){
                  alert('插入数据成功 ！');
                  window.location.reload();}
                else{
                  alert('插入数据失败！');}
            }
        });
    },  
    updateByMetadata : function (params) {
		$.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "../metadata/update",
            "data": JSON.stringify(params),
            "dataType": "json",
            success: function(result) {
                if(result){
                  alert('修改成功！');
                  window.location.reload();}
                else{
                  alert('修改失败！');}
            }
        });
    },    
    deleteByMetadataIds : function (params) {
		$.ajax({
            url: "../metadata/delete/" + params,
            type: "GET",
            success: function(result) {
                 if(result){
                  alert('删除成功 ！')
                  window.location.reload();;
                  }
                else{
                  alert('删除失败！');}
            }
        });
    }
};
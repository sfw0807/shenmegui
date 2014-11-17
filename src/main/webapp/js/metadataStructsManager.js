var metadataStructsManager = {

    // get all metadataStructs Info
    getAll: function(callBack) {
        $.ajax({
            "url": '../mdtStructs/list',
            "type": 'GET',
            "success": function(result) {
                callBack(result);
            }
        });
    },
    
    // delete metadataStructs by id
    deleteById: function(id) {
        $.ajax({
            "url": '../mdtStructs/delete/' + id,
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
    
    // get metadataStruct by id
    getmdtStructsById: function(id,callBack) {
        $.ajax({
            "url": '../mdtStructs/getMetadataStructsById/' + id,
            "type": 'GET',
            "success": function(result) {
                 callBack(result);
            }
        });
    },
    
    // get metadataStrucstsAttr by structId
    getMdtStructsAttrByStructId: function(structId,callBack) {
        $.ajax({
            "url": '../mdtStructs/getMdtStructsAttrByStructId/' + structId,
            "type": 'GET',
            "success": function(result) {
                 callBack(result);
            }
        });
    },
    
    // check metadataStruct is or not used
    checkIsUsed: function(id,callBack) {
        $.ajax({
            "url": '../mdtStructs/checkIsUsed/' + id,
            "type": 'GET',
            "dataType": 'Text',
            "success": function(result) {
                 callBack(result);
            } 
        });
    },
    // insert metadataStruct
    insertMdtStructs: function(mdtStructs) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "../mdtStructs/insert",
            "dataType": "json",
            "data":JSON.stringify(mdtStructs),
            "success": function(result) {
                if(result){
                }
                else{
                  alert('插入数据失败!');
                  return false;
                }
            }
        });
    },
    // update metadataStruct
    updateMdtStructs: function(mdtStructs) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "../mdtStructs/update",
            "dataType": "json",
            "data":JSON.stringify(mdtStructs),
            "success": function(result) {
                if(result){
                }
                else{
                  alert('修改失败!');
                  return false;
                }
            }
        });
    },
    
    // 元数据属性处理
    // insert metadataStructAttr
    insertMdtStructsAttr: function(mdtStructsAttr) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "../mdtStructsAttr/insert",
            "dataType": "json",
            "data":JSON.stringify(mdtStructsAttr),
            "success": function(result) {
                if(result){
                  alert('插入数据成功!');
                }
                else{
                  alert('插入数据失败!');
                  return false;
                }
            }
        });
    },
    // update metadataStructAttr
    updateMdtStructsAttr: function(mdtStructsAttr) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "../mdtStructsAttr/update",
            "dataType": "json",
            "data":JSON.stringify(mdtStructsAttr),
            "success": function(result) {
                if(result){
                  alert('修改成功!');
                }
                else{
                  alert('修改失败!');
                  return false;
                }
            }
        });
    }
}

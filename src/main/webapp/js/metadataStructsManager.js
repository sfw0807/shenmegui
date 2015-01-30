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
    deleteById: function(id, callBack) {
        $.ajax({
            "url": '../mdtStructs/delete/' + id,
            "type": 'GET',
            "success": function(result) {
                 callBack(result);
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
            }
        });
    },
    
    // 元数据属性处理
    // insert metadataStructAttr
    insertMdtStructsAttr: function(mdtStructsAttr, callBack) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "../mdtStructsAttr/insert",
            "dataType": "json",
            "data":JSON.stringify(mdtStructsAttr),
            "success": function(result) {
                  callBack(result);
            }
        });
    },
    // update metadataStructAttr
    updateMdtStructsAttr: function(mdtStructsAttr, callBack) {
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "../mdtStructsAttr/update",
            "dataType": "json",
            "data":JSON.stringify(mdtStructsAttr),
            "success": function(result) {
                 callBack(result);
            }
        });
    }
}

var idaManager = {
    manager: {},
    getIDAByInterfaceId: function (treeData, interfaceId, callBack) {
        $.ajax({
            url: '/interfaceManagement/getIDAs/' + interfaceId,
            type: 'GET',
            dataType: "json",
            success: function (result) {
                console.log(result);
                for (var i = 0; i < result.length; i++) {
                    treeData.Rows.push({
                        id: result[i].id,
                        pid: result[i].parentId,
                        structName: result[i].structName,
                        metadataId: result[i].metadataId,
                        length: result[i].length == null ? "" : result[i].length,
                        scale: result[i].scale == null ? "" : result[i].scale,
                        remark: result[i].remark,
                        type: result[i].type,
                        required: result[i].required,
                        seq: result[i].seq
                    });
                }
                var manager = $("#maingrid").ligerGrid({
                        columns: [
                            {display: '序号', name: 'seq', id: 'seq', align: 'left', width: '10%'},
                            {display: '英文名称', name: 'structName', id: 'structName', align: 'left', width: '20%'},
                            {display: '元数据ID', name: 'metadataId', id: 'metadataId', align: 'left', width: '10%'},
                            {display: '长度', name: 'length', id: 'length', align: 'left', width: '10%'},
                            {display: '精度', name: 'scale', id: 'scale', align: 'left', width: '10%'},
                            {display: '类型', name: 'type', id: 'type', align: 'left', width: "10%"},
                            {display: '是否必输', name: 'required', id: 'required', align: 'left', width: "10%"},
                            {display: '备注', name: 'remark', id: 'remark', align: 'left', width: "20%"},
                            {display: 'id', name: 'id', id: 'id', align: 'left', hide: true},
                            {display: 'pid', name: 'pid', id: 'pid', align: 'left', hide: true}
                        ], width: '100%', usePager: false, height: '97%',
                        data: treeData, alternatingRow: true, tree: {
                            columnId: 'structName',
                            //columnName: 'name',
                            idField: 'id',
                            parentIDField: 'pid'
                        }
                    }
                );
                callBack(manager);
            }
        });
    },
    saveIDAs: function (idas, callBack) {
        console.log(idas);
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "/interfaceManagement/saveIDAs",
            "data": JSON.stringify(idas),
            "dataType": "json",
            "success": function (result) {
                if (result) {
                    alert("保存成功");
                    window.location.reload();
                } else {
                    alert("保存失败");
                }
            }
        });
    }
};

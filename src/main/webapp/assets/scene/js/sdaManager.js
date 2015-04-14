var sdaManager = {
    manager: {},
    getSDAInfoByOperationId: function(operationId,serviceId,callBack) {
        $.ajax({
            url: '../operationInfo/getSDAByOperationService/'+serviceId+"/"+operationId,
            type: 'GET',
            dataType: "json",
            success: function(result) {
                for(var i=0;i < result.length;i++){
                    TreeSDAData.Rows.push({
                        id:result[i].id,
                        pid:result[i].parentId,
                        structId:result[i].structId,
                        metadataId:result[i].metadataId,
                        remark:result[i].remark,
                        type:result[i].type,
                        required:result[i].required,
                        seq:result[i].seq
                    });
                }
                var manager = $("#maingrid").ligerGrid({
                        columns: [
                            { display: '序号', name: 'seq', id: 'seq', width: 50, align: 'left' },
                            { display: '英文名称', name: 'structId', id: 'structId', width: 250, align: 'left' },
                            { display: '元数据ID', name: 'metadataId', id: 'metadataId', width: 250, align: 'left' },
                            { display: '类型', name: 'type', id: 'type', width: 50, align: 'left' },
                            { display: '是否必输', name: 'required', id:'required',width: 50, align: 'left' },
                            { display: '备注', name: 'remark', id:'remark',width: 250, align: 'left' },
                            { display: 'id', name: 'id', id:'id',width: 250, align: 'left' },
                            { display: 'pid', name: 'pid', id:'pid',width: 250, align: 'left' }
                        ], width: '100%',usePager:false, height: '97%',
                        data: TreeSDAData, alternatingRow: false, tree: {
                            columnId: 'structId',
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
    "getManager": function () {
        console.log(sdaManager.manager);
        return sdaManager.manager;
    }
};

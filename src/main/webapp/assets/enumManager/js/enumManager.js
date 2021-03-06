var enumManager ={
	"addEnum" : function(data,callBack){
		$.ajax({
			"type" : "POST",
			"contentType" : "application/json;charset=utf-8",
			"url" : "/enum/addEnum",
			"data": JSON.stringify(data),
			"dataType": "json",
			"success": function(result) {
				callBack(result);
			}
		});
	},
	"deleteEnum" : function(id, callBack){
		$.ajax({
            "type": "DELETE",
            "contentType": "application/json; charset=utf-8",
            "url": "/enum/deleteEnum/"+id,
            "data": JSON.stringify(id),
            "dataType": "json",
            "success": function(result) {
                if(result){
                	$('#dg').datagrid('reload');
                }
                callBack(result);
            }
        });
	},
	"saveEnum" : function(anEnum,callBack){
		$.ajax({
			"type" : "POST",
			"contentType" : "application/json;charset=utf-8",
			"url" : "/enum/saveEnum",
			"data": JSON.stringify(anEnum),
			"dataType": "json",
			"success": function(result) {
				callBack(result);
			}
		});
	},
	"searchEnum" : function(anEnum,callBack){
		$.ajax({
			"type" : "POST",
			"contentType" : "application/json;charset=utf-8",
			"url" : "/enum/searchEnum",
			"data": JSON.stringify(anEnum),
			"dataType": "json",
			"success": function(result) {
				callBack(result);
			}
		});
	},
	"addElement" : function(data){
		$.ajax({
			"type" : "POST",
			"contentType" : "application/json;charset=utf-8",
			"url" : "/enum/addElement",
			"data": JSON.stringify(data),
			"dataType": "json",
			"success": function(result) {
				if(result){
					$('#elementdatagrid').datagrid('reload');
					$('#w').window('close');
				}
			}
		});
	},
	"addSlaveEnum" : function(data,masterId,callBack){
		$.ajax({
			"type" : "POST",
			"contentType" : "application/json;charset=utf-8",
			"url" : "/enum/addSlaveEnum/"+masterId,
			"data": JSON.stringify(data),
			"dataType": "json",
			"success": function(result) {
				callBack(result);
			}
		});
	},
	"setElementMapping" : function(data,callBack){
		$.ajax({
			"type" : "POST",
			"contentType" : "application/json;charset=utf-8",
			"url" : "/enum/setElementMapping",
			"data": JSON.stringify(data),
			"dataType": "json",
			"success": function(result) {
				callBack(result);
			}
		});
	},
	"saveElementMapping" : function(data,callBack){
		$.ajax({
			"type" : "POST",
			"contentType" : "application/json;charset=utf-8",
			"url" : "/enum/saveElementMapping",
			"data": JSON.stringify(data),
			"dataType": "json",
			"success": function(result) {
				callBack(result);
			}
		});
	},
	"deleteEnumElements" : function(data,callBack){
		$.ajax({
			"type" : "DELETE",
			"contentType" : "application/json;charset=utf-8",
			"url" : "/enum/deleteEnumElements",
			"data": JSON.stringify(data),
			"dataType": "json",
			"success": function(result) {
				callBack(result);
			}
		});
	},
	"deleteElementsMapping" : function(data,callBack){
		$.ajax({
			"type" : "DELETE",
			"contentType" : "application/json;charset=utf-8",
			"url" : "/enum/deleteElementsMapping",
			"data": JSON.stringify(data),
			"dataType": "json",
			"success": function(result) {
				callBack(result);
			}
		});
	}
};
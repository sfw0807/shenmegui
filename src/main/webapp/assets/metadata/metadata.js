$(function(){
	var queryMetadata = function queryMetadata() {
		var queryMetadataCallBack = function queryMetadataCallBack(data){
			$('#metadataList').datagrid('loadData', data);
		};
		var params = {
			"metadataId" : $("#metadataId").textbox("getValue"),
			"metadataName" : $("#metadataName").textbox("getValue"),
			"chineseName" : $("#chineseName").textbox("getValue"),
			"metadataAlias" : $("#metadataAlias").textbox("getValue"),
			"status" : $("#status").combobox("getValue"),
			"categoryWordId" : $("#categoryWordId").combobox("getValue"),
			"version" : $("#version").textbox("getValue"),
			"startDate" : $("#startDate").datebox("getValue"),
			"endDate" : $("#endDate").datebox("getValue")
		};
		metadataManager.query(params,queryMetadataCallBack);
	};
	$("#queryMetadataBtn").click(function(){
		queryMetadata();
	});

});

function save(formId){
	if(!$("#"+formId).form('validate')){
		return false;
	}
	var params = $("#"+formId).serialize();
	params = decodeURIComponent(params, true);
	 $.ajax({
         type: "post",
         async: false,
         url: "/metadata/add",
         dataType: "json",
         data: params,
         success: function(data){
        	 //关闭窗口
        	 $("#w").window("close");
        	 $('#metadataList').datagrid('reload');
        	 //刷新查询列表 
            }
	 	});
}

function deleteObj(){
	var checkedItems = $('#metadataList').datagrid('getChecked');
	if(checkedItems != null && checkedItems.length > 0){
		if(confirm("确定要删除已选中的"+checkedItems.length+"项吗？一旦删除无法恢复！")){
			var ids = [];
			$.each(checkedItems, function(index, item) {
				ids.push(item.metadataId);
			});
			 $.ajax({
		         type: "post",
		         async: false,
		         url: "/metadata/deletes",
		         dataType: "json",
		         data: {"metadataIds":ids.join(",")},
		         success: function(data){
		        	 alert("操作成功");
		        	 $('#metadataList').datagrid('reload');
		            }
			 	});
		}
	}else{
		alert("没有选中项！");
	}
}
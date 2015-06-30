function appendServiceForm(){
	var node = $('.mxservicetree').tree('getSelected');
	if(node.type=="service"){
		uiinit.win({
			w:500,
			iconCls:'icon-add',
			title:"新增服务",
			url : "/dataTemplate/formTemplate/serviceForm/serviceAppandForm.jsp"
		});		
	}else if(node.type=="serviceCategory"){
		uiinit.win({
			w:500,
			iconCls:'icon-add',
			title:"新增服务类",
			url : "/dataTemplate/formTemplate/serviceCategoryForm/serviceCategoryAppandForm.jsp"
		});
	}
}

function editServiceForm(){
	var node = $('.mxservicetree').tree('getSelected');
	if(node.type=="service"){
		uiinit.win({
			w:500,
			iconCls:'icon-add',
			title:"编辑服务",
			url : "/dataTemplate/formTemplate/serviceForm/serviceEditForm.jsp"
		});		
	}else if(node.type=="serviceCategory"){
		uiinit.win({
			w:500,
			iconCls:'icon-add',
			title:"编辑服务类",
			url : "/dataTemplate/formTemplate/serviceCategoryForm/serviceCategoryEditForm.jsp"
		});	
	}
}

function removeService(){
	var node = $('.mxservicetree').tree('getSelected');
	var id = node.id;
	if(node.type=="service"){
		$('.mxservicetree').tree('remove', node.target);
		$.ajax({
			"type" : "DELETE",
			"contentType" : "application/json;charset=utf-8",
			"url": "/service/deleteService/"+id,
			"data": JSON.stringify(id),
			"dataType": "json",
			"success": function(result) {
			}
		});
	}else if(node.type=="serviceCategory"){
		$('.mxservicetree').tree('remove', node.target);
		$.ajax({
			"type" : "DELETE",
			"contentType" : "application/json;charset=utf-8",
			"url": "/service/deleteServiceCategory/"+id,
			"data": JSON.stringify(id),
			"dataType": "json",
			"success": function(result) {
			}
		});
	}
	
}

//
function addService(data,callBack){
	$.ajax({
		"type" : "POST",
		"contentType" : "application/json;charset=utf-8",
		"url": "/service/addService",
        "data": JSON.stringify(data),
        "dataType": "json",
        "success": function(result) {
        	callBack(result);
        }
		
	});
}

function editService(data,callBack){
	$.ajax({
		"type" : "POST",
		"contentType" : "application/json;charset=utf-8",
		"url": "/service/editService",
        "data": JSON.stringify(data),
        "dataType": "json",
        "success": function(result) {
        	callBack(result);
        }
		
	});
}

function addServiceCategory(data,callBack){
	$.ajax({
		"type" : "POST",
		"contentType" : "application/json;charset=utf-8",
		"url": "/service/addServiceCategory",
        "data": JSON.stringify(data),
        "dataType": "json",
        "success": function(result) {
        	callBack(result);
        }
		
	});
}

function editServiceCategory(data,callBack){
	$.ajax({
		"type" : "POST",
		"contentType" : "application/json;charset=utf-8",
		"url": "/service/editServiceCategory",
        "data": JSON.stringify(data),
        "dataType": "json",
        "success": function(result) {
        	callBack(result);
        }
		
	});
}

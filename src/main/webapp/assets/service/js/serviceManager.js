var serviceManager ={

	"deleteById" : function deleteById(id){
		$.ajax({
			"type" : "DELETE",
			"contentType" : "application/json;charset=utf-8",
			"url": "/service/deleteService/"+id,
			"data": JSON.stringify(id),
			"dataType": "json",
			"success": function(result) {
			}
		});
	},

	"deleteCategoryById" : function deleteCategoryById(id){
		$.ajax({
			"type" : "DELETE",
			"contentType" : "application/json;charset=utf-8",
			"url": "/service/deleteServiceCategory/"+id,
			"data": JSON.stringify(id),
			"dataType": "json",
			"success": function(result) {
			}
		});
	},
	"add" : function add(service,callBack){
		$.ajax({
			"type" : "POST",
			"contentType" : "application/json;charset=utf-8",
			"url": "/service/addService",
			"data": JSON.stringify(service),
			"dataType": "json",
			"success": function(result) {
				callBack(result);
			}
		
		});
	},
	"update" : function update(data,callBack){
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
	},
	
	"addServiceCategory" : function addServiceCategory(data,callBack){
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
	},
	
	"updateServiceCategory" : function updateServiceCategory(data,callBack){
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
	},
	

};

var enumManager ={
	"addEnum" : function(data){
		$.ajax({
			"type" : "POST",
			"contentType" : "application/json;charset=utf-8",
			"url" : "/enum/addEnum",
			"data": JSON.stringify(data),
			"dataType": "json",
			"success": function(result) {
				if(result){
					$('#w').window('close');
				}
			}
		});
	}
};
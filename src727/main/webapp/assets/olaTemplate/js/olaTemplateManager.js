
var olaTemplateManager = {
		"add" : function(data, callBack) {
		$.ajax({
			"type" : "POST",
			"contentType" : "application/json; charset=utf-8",
			"url" : "/olaTemplate/add/",
			"data" : JSON.stringify(data),
			"dataType" : "json",
			"success" : function(result) {
				callBack(result);
			}
		});
	},
	"deleteByEntity" : function(data, callBack) {
        $.ajax({
			"type" : "DELETE",
			"contentType" : "application/json; charset=utf-8",
			"url" : "/olaTemplate/delete/",
			"dataType" : "json",
			"data": JSON.stringify(data),
			"success" : function(result) {
				callBack(result);
			}
		});
	},
	"addTemplate" : function(data,sId,oId,olaTemplateId, callBack) {
	        var url="/olaTemplate/addOla/"+sId+"/"+oId+"/"+olaTemplateId;
		$.ajax({
			"type" : "POST",
			"contentType" : "application/json; charset=utf-8",
			"url" : url,
			"data" : JSON.stringify(data),
			"dataType" : "json",
			"success" : function(result) {
				callBack(result);
			}
		});
	}
};

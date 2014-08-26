var ServerSide = {
	"buildServerDataProcess" : function (url){
		var fnServerData = function fnServerData (sSource, aoData, fnCallback, oSettings){
			oSettings.jqXHR = $.ajax( {
				"dataType" : 'json',
				"type" : "GET",
				"url" : url,
				"data" : aoData,
				"success" : function(json) {
					if (json.sError) {
						oSettings.oApi._fnLog(oSettings, 0, json.sError);
					}
					$(oSettings.oInstance).trigger('xhr',
							[ oSettings, json ]);
					fnCallback(json);
				},
				"dataType" : "json",
				"cache" : false,
				"type" : oSettings.sServerMethod,
				"error" : function(xhr, error, thrown) {
					if (error == "parsererror") {
						oSettings.oApi._fnLog(
							oSettings,
							0,
							"DataTables warning: JSON data from "
									+ "server could not be parsed. This is caused by a JSON formatting error.");
					}
				}
			});
		};
		return fnServerData;
	}
};
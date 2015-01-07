var metadataManager = {
    getAllMetadataList : function (fileName,callBack) {
		$.ajax({
            url: "../import/mapping/" + fileName,
            type: "GET",
            success: function(result) {
                callBack(result);
            }
        });
    }
};
$(function(){
  $('#tabs').tabs(); 
});
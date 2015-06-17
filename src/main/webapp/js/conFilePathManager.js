/**
 * Created by Administrator on 2015/5/13.
 */

var conFilePathRelateManager = {
    getAllConFilePathRelateInfo : function getAllConFilePathInfo (callBack) {
        $.ajax({
            url: "../relateVieww/configExport",
            type: "GET",
            success: function(result) {
                callBack(result);
            }
        });
    },


    publishConFilePathRelateInfo : function publishlConFilePathInfo (params,metadatas) {
        $.ajax({
            type: "GET",
            url: "../relateVieww/publish/" +metadatas +"/" +params,
            "dataType": "Text"
        });
    }
};
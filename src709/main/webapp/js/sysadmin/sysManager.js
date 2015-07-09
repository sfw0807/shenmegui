
/**
报文头管理
**/
var sysManager = {
    add : function(data, callBack){
        $.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8",
            url: "/interfaceHead/add",
            data: JSON.stringify(data),
            dataType: "json",
            success: function(result) {
                callBack(result);
            }
        });
    },
    modify : function(){
    },
    deleteEntity : function(){

    },
    get : function(){

    },
    
    addIDA : function(data, callBack){
        $.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8",
            url: "/ida/add",
            data: JSON.stringify(data),
            dataType: "json",
            success: function(result) {
                callBack(result);
            }
        });
    },
    removeIDA : function(data, callBack){
        $.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8",
            url: "/ida/delete",
            data: JSON.stringify(data),
            dataType: "json",
            success: function(result) {
                callBack(result);
            }
        });
    },
    append:function(){
    	uiinit.win({
			w:500,
			iconCls:'icon-add',
			title:"新增报文",
			url : "/jsp/sysadmin/header_add.jsp"
		});	
    },
    
    edit:function(){
    	
    	var node = $('.mxsysadmintree').tree("getSelected");
    	uiinit.win({
			w:500,
			iconCls:'icon-add',
			title:"编辑报文",
			url : "/interfaceHead/edit/"+node.id
		});
    
    },
    
    remove:function(){
    	
    	var node = $('.mxsysadmintree').tree("getSelected");
    	$.ajax({
            type: "get",
            contentType: "application/json; charset=utf-8",
            url: "/interfaceHead/delete/"+node.id,
            dataType: "json",
            success: function(result) {
                if(result){
                	$('.mxsysadmintree').tree("reload");
                	var title = node.text;
                	$('#mainContentTabs').tabs("close",title);
                	
                }
            }
        });
    
    }
 
}

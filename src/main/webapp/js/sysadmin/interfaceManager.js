
/**
接口管理
**/
var interfaceManager = {
    add : function(data,type, callBack){
        $.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8",
            url: "/interface/add?type="+type,
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
    modifySEQ : function(id,seq,id2,seq2){
        $.ajax({
            type: "get",
            async: false,
            contentType: "application/json; charset=utf-8",
            url: "/ida/modifySEQ/"+id+"/"+seq+"/"+id2+"/"+seq2,
            dataType: "json",
            success: function(result) {
            }
        });
    },
     removeIDA : function(data, callBack){
        $.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8",
            url: "/ida/delete/"+data,
            dataType: "json",
            success: function(result) {
                callBack(result);
            }
        });
    },
    append:function(systemId){

    	uiinit.win({
			w:500,
			iconCls:'icon-add',
			title:"新增接口",
			url : "/jsp/interface/interface_add.jsp?systemId="+systemId
		});	
    },
    
    edit:function(interfaceId,systemId){
    	
    	var node = $('.msinterfacetree').tree("getSelected");
    	
    	var sId = interfaceId;
    	try{
    		sId=node.id
    	}catch(e){
    		sId = interfaceId;
    	}
    	uiinit.win({
			w:500,
			iconCls:'icon-add',
			title:"编辑报文",
			url : "/interface/edit/"+sId+"?systemId="+systemId
		});
    
    },
    
    remove:function(interfaceId,title){
    	
    	var node = $('.msinterfacetree').tree("getSelected");
    	
    	var sId = interfaceId;
    	var tit = title;
    	var treeObj = $('.msinterfacetree');
    	var tabObj = $('#mainContentTabs');
    	try{
    		sId= node.id
    		tit = node.text;
    	}catch(e){
    		sId = interfaceId;
    		tit = title;
    		treeObj = parent.$('.msinterfacetree');
    		tabObj = parent.$('#mainContentTabs');
    	}
    	
    	$.ajax({
            type: "get",
            contentType: "application/json; charset=utf-8",
            url: "/interface/delete/"+sId,
            dataType: "json",
            success: function(result) {
                if(result){
                	treeObj.tree("reload");
                	tabObj.tabs("close",tit);
                }
            }
        });
    
    }
 
}

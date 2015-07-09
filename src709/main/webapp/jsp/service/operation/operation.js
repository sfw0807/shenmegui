
 function save(formId){
	if(!$("#"+formId).form('validate')){
		return false;
	}
	var params = $("#"+formId).serialize();
	params = decodeURIComponent(params, true);
	 $.ajax({
         type: "post",
         async: false,
         url: "/operation/add",
         dataType: "json",
         data: params,
         success: function(data){
         	if(data == true){
         		alert("操作成功 ！");
         		//清空页面数据
         		clean();
        	 	//刷新查询列表 
     		 	parent.serviceInfo.reloadData();
        	 
         	}else{
         		alert("保存出现异常 ，操作失败！");
         	}
        	 
            }
	 	});
}	

function clean(){
	$("#operationId").textbox("setValue","");
	$("#operationName").textbox("setValue","");
	$("#operationDesc").textbox("setValue","");
	$("#operationRemark").textbox("setValue","");
	$("#state").combobox("setValue","");
	
	//标签切换
 	parent.$('#subtab').tabs('select', '服务基本信息');
} 
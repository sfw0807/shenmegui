$(function() {
	var tables = {};
	var asInitVals = new Array();
	$('#tabs').tabs();
	$("#tab0").click(function(e) {
		if(tables["invokeTable"]){
			tables["invokeTable"].fnAdjustColumnSizing();
		}
	});
	/**
	 * init service interface relate table 
	 * @param {Object} result
	 * 
	 */
	var initinvokeTable = function initinvokeTable(result) {
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#invokeTable tbody tr").unbind("click");
			$("#invokeTable tbody tr").click(function(e) {
				$(this).toggleClass("row_selected");
			});
		};
		//初始化invokeTable
		tables["invokeTable"] = $("#invokeTable").dataTable( {
			"aaData" : result,
			"aoColumns" : invokeTableLayout,
			"aoColumnDefs" : [
				{
					"sClass" : "center",
					"aTargets" : [0,1,2,3,4,5,6,7,8,9,10,11,12,13]
				},
				{
					"mRender" : function ( data, type, row ) {
						if(data == "1"){
						   return "Provider";
						}
						else{
						   return "Consumer";
						}
					},
					"aTargets" : [8]
				}
			],
			"bJQueryUI": true,
			"bAutoWidth" : true,
			"bScrollCollapse" : "full_numbers",
			"bPaginate" : true,
			"bSort" : true,
			"oLanguage" :oLanguage,
			"fnDrawCallback" : function() {
				columnClickEventInit();
			}
		});
		tables["invokeTable"].fnAdjustColumnSizing();
	};
	invokeManager.getAll(initinvokeTable);

	//初始化操作Grid的搜索框
	var initinvokeTableFooter = function initinvokeTableFooter() {
		$("#invokeTable tfoot input").keyup(
				function() {
					tables["invokeTable"].fnFilter(this.value, $(
							"#invokeTable tfoot input").index(this),null,null,null,false);
				});
		$("#invokeTable tfoot input").each(function(i) {
			asInitVals[i] = this.value;
		});
		$("#invokeTable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#invokeTable tfoot input")
				.blur(
						function(i) {
							if (this.value == "") {
								this.className = "search_init";
								this.value = asInitVals[$(
										"#invokeTable tfoot input")
										.index(this)];
							}
						});
	};
	initinvokeTableFooter();
	
	$('#del').button().click(function(){
	    var table = tables["invokeTable"];
	    var params = "";
	    var rowsSelected = table.$("tr.row_selected");
        var selectedDatas = [];
        if(rowsSelected.length <= 0){
           alert('请选择删除的记录!');
           return false;
        } 
        else{
          for (var i = 0; i < rowsSelected.length; i++) {
              selectedDatas[i] = table.fnGetData(table.$("tr.row_selected")[i]);
	          var serviceId = selectedDatas[i]["serviceInfo"].split("/")[0];
	          var operationId = selectedDatas[i]["operationInfo"].split("/")[0];
	          var interfaceId = selectedDatas[i]["interfaceInfo"].split("/")[0];
	          var prdSysAb = selectedDatas[i]["provideSysInfo"].split("/")[0];
			  var passbySysAb = (selectedDatas[i]["passbySysInfo"] == null?"":selectedDatas[i]["passbySysInfo"]).split("/")[0];
			  var csmSysAb = (selectedDatas[i]["consumeSysInfo"]== null?"":selectedDatas[i]["consumeSysInfo"]).split("/")[0];
			  var provideMsgType = selectedDatas[i]["provideMsgType"];
			  var consumeMsgType = selectedDatas[i]["consumeMsgType"];
			  var param = serviceId + ':' + operationId + ':' + interfaceId + ':' + prdSysAb + ':' + passbySysAb + ':' + csmSysAb
			                  + ":" + provideMsgType + ":" + consumeMsgType;
			  params += param + ",";
          }
          params = params.substring(0,params.length-1);
		  function delResult(result){
		     if(result){
		        alert('删除成功!');
		        window.location.reload();
		     }
		     else{
		        alert('删除失败!');
		     }
		  };
		  if(confirm('确定删除选择的记录吗?')){
		     invokeManager.delInvoke(params, delResult);
		  }
        }
	});
	
	$('#addConsumer').button().click(function(){
	     var table = tables["invokeTable"];
	     var rowsSelected = table.$("tr.row_selected");
         if(rowsSelected.length <= 0){
           alert('请选择记录!');
           return false;
         } 
         else if(rowsSelected.length > 1){
           alert('一次只能选择一条记录!');
           return false;
         }
         else{
           var selectedDatas = table.fnGetData(table.$("tr.row_selected")[0]);
	          var serviceId = selectedDatas["serviceInfo"].split("/")[0];
	          var operationId = selectedDatas["operationInfo"].split("/")[0];
	          var interfaceId = selectedDatas["interfaceInfo"].split("/")[0];
	          var prdSysAb = selectedDatas["provideSysInfo"].split("/")[0];
			  var provideMsgType = selectedDatas["provideMsgType"];
			  var consumeMsgType = selectedDatas["consumeMsgType"];
			  var through = selectedDatas["through"];
						if(through=="否"){
							through = "N";
						}else{
							through = "Y";
						}
						var state = selectedDatas["state"];
						if(state=="服务定义"){
							state="def";
						}else if(state=="开发"){
							state="dev";
						}else if(state=="联调测试"){
							state="union";
						}else if(state=="sit测试"){
							state="sit";
						}else if(state=="uat测试"){
							state="uat";
						}else if(state=="投产验证"){
							state="valid";
						}else if(state=="上线"){
							state="online";
						}else{
						    state="offline";
						}
						var direction = selectedDatas["direction"];
			  window.location.href = 'consumer.jsp?operationId='+operationId
							+'&serviceId='+serviceId
							+'&interfaceId='+interfaceId
							+'&provideMsgType='+provideMsgType
							+'&consumeMsgType='+consumeMsgType
							+'&through='+through
							+'&state='+state
							+'&onlineVersion='+selectedDatas["onlineVersion"]
							+'&onlineDate='+selectedDatas["onlineDate"]
							+'&provideSysAb='+prdSysAb
							+'&direction='+direction;
         }
	});
	
	$('#modifyState').button().click(function(){
	     var table = tables["invokeTable"];
	     var rowsSelected = table.$("tr.row_selected");
         var selectedDatas = [];
         if(rowsSelected.length <= 0){
           alert('请选择记录!');
           return false;
         } 
         else if(rowsSelected.length > 1){
           alert('一次只能选择一条记录!');
           return false;
         }
         else{
           var selectedDatas = table.fnGetData(table.$("tr.row_selected")[0]);
	          var serviceId = selectedDatas["serviceInfo"].split("/")[0];
	          var operationId = selectedDatas["operationInfo"].split("/")[0];
	          var interfaceId = selectedDatas["interfaceInfo"].split("/")[0];
	          var prdSysAb = selectedDatas["provideSysInfo"].split("/")[0];
			  var passbySysAb = (selectedDatas["passbySysInfo"] == null?"":selectedDatas["passbySysInfo"]).split("/")[0];
			  var csmSysAb = (selectedDatas["consumeSysInfo"]== null?"":selectedDatas["consumeSysInfo"]).split("/")[0];
			  var provideMsgType = selectedDatas["provideMsgType"];
			  var consumeMsgType = selectedDatas["consumeMsgType"];
			  var through = selectedDatas["through"];
						if(through=="否"){
							through = "N";
						}else{
							through = "Y";
						}
						var state = selectedDatas["state"];
						if(state=="服务定义"){
							state="def";
						}else if(state=="开发"){
							state="dev";
						}else if(state=="联调测试"){
							state="union";
						}else if(state=="sit测试"){
							state="sit";
						}else if(state=="uat测试"){
							state="uat";
						}else if(state=="投产验证"){
							state="valid";
						}else if(state=="上线"){
							state="online";
						}else{
						    state="offline";
						}
						
			  window.location.href = 'invokeInfoById.jsp?operationId='+operationId
							+'&serviceId='+serviceId
							+'&interfaceId='+interfaceId
							+'&provideMsgType='+provideMsgType
							+'&consumeMsgType='+consumeMsgType
							+'&through='+through
							+'&state='+state
							+'&onlineVersion='+selectedDatas["onlineVersion"]
							+'&onlineDate='+selectedDatas["onlineDate"]
							+'&prdSysAb='+prdSysAb
							+'&passbySysAb='+passbySysAb
							+'&csmSysAb='+csmSysAb;
         }
	});
});
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
					"aTargets" : [0,1,2,3,4,5,6,7,8,9,10,11,12]
				},
//				{
//					"mRender" : function ( data, type, row ) {
//						return '<a href="xxx.jsp?operationId='+row["operationInfo"]
//							+'&serviceId='+row["serviceId"]
//							+'&version='+row["version"]
//							+'&publishVersion='+row["publishVersion"]
//							+'&publishDate='+row["publishDate"]
//							+'">' + '查看' + '</a>';
//					},
//					"aTargets" : [10]
//				},
				{
					"mRender" : function ( data, type, row ) {
						var operationId = row["operationInfo"].split("/")[0];
						var serviceId = row["serviceInfo"].split("/")[0];
						var interfaceId = row["interfaceInfo"].split("/")[0];
						var through = row["through"];
						if(through=="否"){
							through = "N";
						}else{
							through = "Y";
						}
						var state = row["state"];
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
						}
						var provideSysInfo = row["provideSysInfo"];
						var provideSysAb = row["provideSysInfo"].split("/")[0];
						return '<a href="consumer.jsp?operationId='+operationId
							+'&serviceId='+serviceId
							+'&interfaceId='+interfaceId
							+'&provideMsgType='+row["provideMsgType"]
							+'&consumeMsgType='+row["consumeMsgType"]
							+'&through='+through
							+'&state='+state
							+'&onlineVersion='+row["onlineVersion"]
							+'&onlineDate='+row["onlineDate"]
							+'&provideSysAb='+provideSysAb
							+'">' + '查看' + '</a>';
					},
					"aTargets" : [11]
				},
				{
					"mRender" : function ( data, type, row ) {
						var operationId = row["operationInfo"].split("/")[0];
						var serviceId = row["serviceInfo"].split("/")[0];
						var interfaceId = row["interfaceInfo"].split("/")[0];
						var through = row["through"];
						if(through=="否"){
							through = "N";
						}else{
							through = "Y";
						}
						var state = row["state"];
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
						}
						return '<a href="invokeInfoById.jsp?operationId='+operationId
							+'&serviceId='+serviceId
							+'&interfaceId='+interfaceId
							+'&provideMsgType='+row["provideMsgType"]
							+'&consumeMsgType='+row["consumeMsgType"]
							+'&through='+through
							+'&state='+state
							+'&onlineVersion='+row["onlineVersion"]
							+'&onlineDate='+row["onlineDate"]
							+'">' + '修改' + '</a>';
					},
					"aTargets" : [12]
				}
			],
			"bJQueryUI": true,
			"bAutoWidth" : true,
			"bScrollCollapse" : "full_numbers",
			"bPaginate" : true,
			"bSort" : true,
			"bFilter":true,
			"bSearchable":true,
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
});
$(function(){
	
	var asInitVals = new Array();
	var tables = {};
	$('#tabs').tabs();
	var ecode = window.location.href.split("=")[1];
	
	var initOperationSelect = function initOperationSelect(result){
		for (var i=0;i<result.length;i++) {
			$('#operationIds').append("<option value='"+result[i].operationId+"'>"+result[i].operationId+"</option>");
		}
	}
	
	var initInputs = function initInputs(result){
		//serviceIds
		$('.ui-combobox:eq(0) input').val(result.service_ID);
		$('.ui-combobox:eq(1) input').val(result.consumer_SYSAB);
		$('.ui-combobox:eq(2) input').val(result.provider_SYSAB);
		$('.ui-combobox:eq(3) input').val(result.passby_SYSAB);
		$('#ecode').val(result.ecode);
		$('#interfaceId').val(result.interface_ID);
		$('#interfaceName').val(result.interface_NAME);
		$('#operationIds').val(result.operation_ID);
		$('#remark').val(result.remark);
		$('#consumerMsgType').val(result.consume_MSG_TYPE);
		$('#providerMsgType').val(result.provide_MSG_TYPE);
		$('#through').val(result.through);
		
		interfaceManager.getOperationOfService($('.ui-combobox:eq(0) input').val()
		, initOperationSelect);
	};
	if (ecode.indexOf('add') != 0) {
		interfaceManager.getVO(ecode, initInputs);
	}
	$('#goBack').click(function(){
		window.location.href = 'interfaceManagement.html';
	});
	
	var initServices = function(result){
		for (var i=0;i<result.length;i++) {
			$('#serviceIds').append("<option value='"+result[i].serviceId+"'>"+result[i].serviceId+"</option>");
		}
	};
	
	interfaceManager.getAllServices(initServices);
	
	var initOperations = function(){
		for (var i=0;i<result.length;i++)
			$('#operationIds').append("<option value='"+result[i].operationId+"'>"+result[i].operationId+"</option>");
	};
	
	$('#services').change(function(){
		var serviceId = $('#services').val();
		interfaceManager.getOperationOfService(initOperations);
	});
	
	$('#save').click(function(){
		var params = {
			serviceId : $('#serviceIds').val(),
			operationId : $('#operationIds').val(),
			consumerSysId : $('#consumerSysId').val(),
			providerSysId : $('#providerSysId').val(),
			consumerMsgType : $('#consumerMsgType').val(),
			providerMsgType : $('#providerMsgType').val(),
			passBySys : $('#passBySys').val(),
			versionSt : $('#versionSt').val(),
			interfaceId : $('#interfaceId').val(),
			interfaceName : $('#interfaceName').val(),
			ecode : $('#ecode').val(),
			through : $('#through').val(),
			remark : $('#remark').val()
		}
		
		var updateCallBack = function (){
			alert('保存成功');
			window.location.href = 'interfaceManagement.html';
		};
		
		interfaceManager.insert(JSON.stringify(params), null);
	});
	
	var initSelect = function initSelect(result) {
		for (var i=0;i<result.length;i++) {
			$('#consumerSysId').append("<option value='"+result[i].systemId+"'>"+result[i].systemAb+":"+result[i].systemName+"</option>");
			$('#providerSysId').append("<option value='"+result[i].systemId+"'>"+result[i].systemAb+":"+result[i].systemName+"</option>");
			$('#passBySys').append("<option value='"+result[i].systemId+"'>"+result[i].systemAb+":"+result[i].systemName+"</option>");
		}
	};
	
	var result = 
    $.ajax({
        url: '/serviceDevInfo/getAllSystem',
        type: 'GET',
        success: function(result) {
            initSelect(result);
        }
	});
	
	$('#serviceIds').combobox();
	$('#consumerSysId').combobox();
	$('#providerSysId').combobox();
	$('#passBySys').combobox();
	
	
		//初始化SDA表格的方法
	var initChildTable = function initChildTable(result) {
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#idaTable tbody tr").unbind("click");
			$("#idaTable tbody tr").click(function(e) {
				$(this).toggleClass("row_selected");			
			});
		};
		//创建Grid
		tables["idaTable"] = $("#idaTable").dataTable( {
			"aaData" : result,
			"aoColumns" : idaTableLayout,
			"aoColumnDefs" : [ 
				{
					"sClass" : "center",
					"aTargets" : [0,2,3,4,5]
				},
				{
					"bSortable":false,
					"aTargets" : [1,2,3,4,5]
				},
				{
					"bVisible" : false,
					"aTargets" : [7, 8]
				},
				{
					"mRender" : function ( data, type, row ) {
						return "<nobr>" + data + "</nobr>";
					},
					"aTargets" : [ 1,2,3]
				}
			],
			"fnRowCallback" : function(nRow, aData, iDisplayIndex) {
			
				//console.log(aData);
				if (aData["structName"].indexOf("|--") == 0) {
					$(nRow).css("background-color", "chocolate");
				}
				if (aData["structName"].indexOf("|--request") > 0) {
					$(nRow).css("background-color", "darkkhaki");
				}
				if (aData["structName"].indexOf("|--response") > 0) {
					$(nRow).css("background-color", "darkkhaki");
				}
				if (aData["structName"].indexOf("|--SvcBody") > 0) {
					$(nRow).css("background-color", "burlywood");
				}
				if (aData["type"] == "array") {
					$(nRow).css("background-color", "gold");
				}
				
			},
			"bJQueryUI": true,
			"bAutoWidth" : true,
			"bScrollCollapse" : true,
			"bPaginate" : false,
			"bSort" : true,
			"oLanguage" : oLanguage,
			"fnDrawCallback" : function() {
				columnClickEventInit();
			}
		});
		tables["idaTable"].fnAdjustColumnSizing(false);

	};
	if (ecode.indexOf('add') != 0) {
		interfaceManager.getIDAs(ecode, initChildTable);
	}
	//初始化操作Grid的搜索框
	var initIdaTableFooter = function initIdaTableFooter() {
		$("#idaTable tfoot input").keyup(
				function() {
					tables["idaTable"].fnFilter(this.value, $(
							"#idaTable tfoot input").index(this));
				});
		$("#idaTable tfoot input").each(function(i) {
			asInitVals[i] = this.value;
		});
		$("#idaTable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#idaTable tfoot input").blur(
				function(i) {
					if (this.value == "") {
						this.className = "search_init";
						this.value = asInitVals[$(
								"#idaTable tfoot input").index(this)];
					}
				});
	};
	initIdaTableFooter();
	
	var operation = '';
	var id = '';
	var parentId;
	
	var row = Object;
	//编辑SDA节点
	$("#editIDA").click(function() {
		var table = tables["idaTable"];
		var rowsSelected = table.$("tr.row_selected");	
		operation = 'edit';
		var selectedDatas = table.fnGetData(table.$("tr.row_selected")[0]);
		var structName = selectedDatas["structName"];
		if (structName.indexOf('root')>0 || structName.indexOf('request')>0
			 || structName.indexOf('response')>0) {
			alert('此节点不能编辑!');
			return ;
		} 
		id = selectedDatas["id"];
		parentId = selectedDatas["parentId"];
		row = table.$("tr.row_selected");	
		if(rowsSelected.length==1){	
			var seq = rowsSelected[0].cells[0].innerText;
			var structName = rowsSelected[0].cells[1].innerText;
			var metadataId = rowsSelected[0].cells[2].innerText;
			var structAlias = rowsSelected[0].cells[3].innerText;
			var type = rowsSelected[0].cells[4].innerText;
			var required = rowsSelected[0].cells[5].innerText;
			var remark = rowsSelected[0].cells[6].innerText;
			rowsSelected[0].cells[1].innerHTML="<nobr><input type='text' onclick='window.event.stopPropagation();'"
			+ " value='"+structName+"' onblur='this.parentNode.innerHTML=this.value'/></nobr>";	
			rowsSelected[0].cells[2].innerHTML="<nobr><input type='text' onclick='window.event.stopPropagation();' value='"+metadataId+"' onblur='if(\"\"===this.value || null===this.value){alert(\"请输入元数据ID!\");}else{this.parentNode.innerHTML=this.value;}'/></nobr>";
			rowsSelected[0].cells[3].innerHTML="<nobr><input type='text' onclick='window.event.stopPropagation();' value='"+structAlias+"' onblur='if(\"\"===this.value){alert(\"请输入节点中文名\");}else{this.parentNode.innerHTML=this.value;}'/></nobr>";
			rowsSelected[0].cells[4].innerHTML="<nobr><input type='text' onclick='window.event.stopPropagation();' value='"+type+"' onblur='if(\"string\"===this.value || \"number\"===this.value || \"array\"===this.value){this.parentNode.innerHTML=this.value;}else{alert(\"只能输入string|number|array中的一种!\")}'/></nobr>";	
			rowsSelected[0].cells[5].innerHTML="<input type='text' onclick='window.event.stopPropagation();' value='"+required+"' onblur='if(\"Y\"===this.value || \"N\"===this.value ){this.parentNode.innerHTML=this.value;}else{alert(\"只能输入Y|N中的一种!\")}'/>";	
			rowsSelected[0].cells[6].innerHTML="<input type='text' onclick='window.event.stopPropagation();' value='"+remark+"' onblur='this.parentNode.innerHTML=this.value;'>";
		} else if (rowsSelected.length > 1) {
			alert("只能选中一行SDA数据!");
		} else {
			alert('请选中要编辑的行!');
		}
	});
	
	//添加SDA子节点
	$("#addChildIDA").click(function() {
		var table = tables["idaTable"];
		var rowsSelected = table.$("tr.row_selected");	
		if(rowsSelected.length!=1){
			alert("只能选中一个父节点!");
		}else{
			selectedDatas = table.fnGetData(rowsSelected[0]);
			var index = selectedDatas["seq"];
			var structName = selectedDatas["structName"];
			operation = 'add';
			parentId = selectedDatas["id"];
			content = structName.substr(0,structName.lastIndexOf("-")+1);
			row = table.dataTable().fnAddData({
				"seq" : index,
				"structName" : "<input type='text' onclick='window.event.stopPropagation();' onblur='if(\"\"===this.value){alert(\"请输入节点ID\");}else{this.parentNode.innerHTML=\"&nbsp;&nbsp;&nbsp;&nbsp;\"+content+this.value;}'}/>",
				"metadataId" : "<input type='text' onclick='window.event.stopPropagation();' onblur='if(\"\"===this.value || null===this.value){alert(\"请输入元数据ID!\");}else{this.parentNode.innerHTML=this.value;}'/>",
				"structAlias" : "<input type='text' onclick='window.event.stopPropagation();' onblur='if(\"\"===this.value){alert(\"请输入节点名称\");}else{this.parentNode.innerHTML=this.value;}'}/>",
				"type" : "<input type='text' onclick='window.event.stopPropagation();' onblur='if(\"string\"===this.value || \"number\"===this.value || \"array\"===this.value){this.parentNode.innerHTML=this.value;}else{alert(\"只能输入string|number|array中的一种!\")}'/>",
				"required" : "<input type='text' onclick='window.event.stopPropagation();' onblur='if(\"Y\"===this.value || \"N\"===this.value ){this.parentNode.innerHTML=this.value;}else{alert(\"只能输入Y|N中的一种!\")}'/>",
				"remark" : "<input type='text' onclick='window.event.stopPropagation();' onblur='this.parentNode.innerHTML='"+content+"';'>"
			});
			var tableObj = document.getElementById("idaTable");
			for(var i=2;i<tableObj.rows.length-1;i++){
				tableObj.rows[i].cells[0].innerText=i;
			}
		}		
	});
	
	//添加SDA兄弟节点
	$("#addBroIDA").click(function() {
		var table = tables["idaTable"];
		var rowsSelected = table.$("tr.row_selected");	
		if(rowsSelected.length!=1){
			alert("只能选中一个兄弟节点!");
		}else{
			selectedDatas = table.fnGetData(rowsSelected[0]);
			var index = selectedDatas["seq"];
			var structName = selectedDatas["structName"];
			operation = 'add';
			parentId = selectedDatas["parentId"];
			content = structName.substr(0,structName.lastIndexOf("-")+1);
			row = table.dataTable().fnAddData({
				"seq" : index,
				"structName" : "<input type='text' onclick='window.event.stopPropagation();' onblur='if(\"\"===this.value){alert(\"请输入节点ID\");}else{this.parentNode.innerHTML=content+this.value;}'}/>",
				"metadataId" : "<input type='text' onclick='window.event.stopPropagation();' onblur='if(\"\"===this.value || null===this.value){alert(\"请输入元数据ID!\");}else{this.parentNode.innerHTML=this.value;}'/>",
				"structAlias" : "<input type='text' onclick='window.event.stopPropagation();' onblur='if(\"\"===this.value){alert(\"请输入节点名称\");}else{this.parentNode.innerHTML=this.value;}'}/>",
				"type" : "<input type='text' onclick='window.event.stopPropagation();' onblur='if(\"string\"===this.value || \"number\"===this.value || \"array\"===this.value){this.parentNode.innerHTML=this.value;}else{alert(\"只能输入string|number|array中的一种!\")}'/>",
				"required" : "<input type='text' onclick='window.event.stopPropagation();' onblur='if(\"Y\"===this.value || \"N\"===this.value ){this.parentNode.innerHTML=this.value;}else{alert(\"只能输入Y|N中的一种!\")}'/>",
				"remark" : "<input type='text' onclick='window.event.stopPropagation();' onblur='this.parentNode.innerHTML=this.value;'>"
			});
			var tableObj = document.getElementById("idaTable");
			for(var i=2;i<tableObj.rows.length-1;i++){
				tableObj.rows[i].cells[0].innerText=i;
			}
		}
	});
	
	var delIDACallBack = function(result){
		if (result == 'success') {
			alert('删除成功!')
		} else {
			alert('删除失败!')
		}
	}
	
	//删除SDA节点
	$("#deleteIDA").click(function() {
		var delIds = '';
		var table = tables["idaTable"];
		var rowsSelected = table.$("tr.row_selected");	
		if(rowsSelected.length>0){
			for(var i=0;i<rowsSelected.length;i++){
				var datas = table.fnGetData(rowsSelected[i]);
				delIds += datas.id;
				if (i != rowsSelected.length - 1) {
					delIds += ',';
				}
				table.fnDeleteRow(rowsSelected[i]);
			}
			//sendDelReq(delIds);
			var tableObj = document.getElementById("idaTable");
			for(var i=2;i<tableObj.rows.length-1;i++){
				tableObj.rows[i].cells[0].innerText=i;
			}
			interfaceManager.deleteIDAs(delIds, delIDACallBack);
		}else{
			alert("请选中至少一个节点!");
		}				
	});
	
	$('#back').click(function(){
		window.location.href='interfaceManagement.html';
	});
	
	$('#saveIDA').button().click(function(){
	var table = tables["idaTable"];
		var params = {};
		if (operation == 'add') {
			var rowData = table.fnGetNodes(row);
			params = {
				id : id,
				parentId : parentId,
				operation : operation,
				seq : rowData.cells[0].innerText,
				structName : (rowData.cells[1].innerText).split('--')[1],
				metadataId : rowData.cells[2].innerText,
				structAlias : rowData.cells[3].innerText,
				type : rowData.cells[4].innerText,
				required : rowData.cells[5].innerText,
				remark : rowData.cells[6].innerText
			} 
		} else {
			params = {
				id : id,
				parentId : parentId,
				operation : operation,
				seq : row[0].cells[0].innerText,
				structName : (row[0].cells[1].innerText).split('--')[1],
				metadataId : row[0].cells[2].innerText,
				structAlias : row[0].cells[3].innerText,
				type : row[0].cells[4].innerText,
				required : row[0].cells[5].innerText,
				remark : row[0].cells[6].innerText
			} 
		}
		
		var updateCallBack = function (){
			alert('保存成功');
			window.location.href = 'interfaceManagement.html';
		};
		
		interfaceManager.saveIDA(JSON.stringify(params), null);
	});
})

	function linkedChangeOperation(){
		//alert($('.ui-combobox:eq(0) input').val());
	}
	
	
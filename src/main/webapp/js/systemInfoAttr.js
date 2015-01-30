$(function() {
    var isChrome = (navigator.appVersion.indexOf("Chrome") != -1) ? true : false;
    var params = {};
    if(isChrome){
        var href = window.location.href;
        params.type = href.split("&")[0].split("=")[1];
        params.sysId = href.split("&")[1].split("=")[1];
    }else{
        params = window.dialogArguments;
    }
	var tables = {};
	var asInitVals = [];
	//var href = window.location.href;
	//var structId = href.substring(href.indexOf("=")+1);
	if(params.type == 'modify'){
	  $('#sysId').attr("disabled",true);
	  $('#sysId').val(params.sysId);
	  $('#sysAb').val(params.sysAb);
	  $('#sysName').val(params.sysName);
	  $('#remark').val(params.remark);
	  $('#firstTime').val(params.firstTime);
	  $('#secondTime').val(params.secondTime);
	  $('#maxCon').val('null'== params.maxCon?'':params.maxCon);
	  $('#outMaxCon').val('null'== params.outMaxCon?'':params.outMaxCon);
	  $('#avgTime').val(params.avgTime);
	  $('#tmOut').val(params.tmOut);
	  $('#sucRate').val(params.sucRate);
	}
	else{
	  $('#sysId').removeAttr("disabled");
	}
	// 新增判断系统ID是否存在
	if(params.type == 'add'){
        function checkIsExist(result){
            if(result.systemId !=null && result.systemId !=""){
                 alert('系统ID已经存在!');
                 $('#sysId').focus();;
                 return false;
            }
        }
        // 判断系统Id是否已经存在
        $('#sysId').blur(function(){
            var sysId = $('#sysId').val();
            if(!checkRegexp( sysId, /^([0-9])+$/)){
                 alert('系统Id只能是数字!');
                 $('#sysId').focus();
                 return false;
            }
            if(sysId != null && sysId !=""){
                 systemInfoManager.getSystemById(sysId,checkIsExist);
            }
        });
    }
	
	$('#tabs').tabs();
	$("#tab0").click(function(e) {
		if(tables["protocolInfoTable"]){
			tables["protocolInfoTable"].fnAdjustColumnSizing();
		}
	});
	/**
	 * init protocolInfo table 
	 * @param {Object} result
	 * 
	 */
	var initprotocolInfoTable = function initprotocolInfoTable(result) {
	           
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#protocolInfoTable tbody tr").unbind("click");
			$("#protocolInfoTable tbody tr").click(function(e) {
			    if($(this).find("td").hasClass("dataTables_empty")){
			    }
			    else{
				$(this).toggleClass("row_selected");
				}
			});
		};
		tables["protocolInfoTable"] = $("#protocolInfoTable").dataTable( {
			"aaData" : result,
			"aoColumns" : protocolInfoTableLayout,
			"aoColumnDefs" : [
				{
					"sClass" : "center",
					"aTargets" : [ 0, 1, 2, 3, 4, 5, 6, 7]
				}/*,
				{
					"mRender" : function ( data, type, row ) {
						if("MSMGroup" == data){
						   return "";
						}
						else{
						   return data;
						}
					},
					"aTargets" : [ 2 ]
				}
				*/
			],
			"bJQueryUI": true,
			"bAutoWidth" : true,
			"bScrollCollapse" : "full_numbers",
			"bPaginate" : false,
			"bSort" : false,
			"bFilter" : false,
			"oLanguage" : oLanguage,
			"fnDrawCallback" : function() {
				columnClickEventInit();
			}
		});
		tables["protocolInfoTable"].fnAdjustColumnSizing();
	}; 
	systemInfoManager.getProtocolInfosById(params.sysId,initprotocolInfoTable);

	//初始化操作Grid的搜索框
	var initprotocolInfoTableFooter = function initprotocolInfoTableFooter() {
		$("#protocolInfoTable tfoot input").keyup(
				function() {
					tables["protocolInfoTable"].fnFilter(this.value, $(
							"#protocolInfoTable tfoot input").index(this),null,null,null,false);
				});
		$("#protocolInfoTable tfoot input").each(function(i) {
			asInitVals[i] = this.value;
		});
		$("#protocolInfoTable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#protocolInfoTable tfoot input")
				.blur(
						function(i) {
							if (this.value == "") {
								this.className = "search_init";
								this.value = asInitVals[$(
										"#protocolInfoTable tfoot input")
										.index(this)];
							}
						});
	};
	initprotocolInfoTableFooter();
    
	 // 新增行
     $( "#add" ).button().click(function(){

        var oTable = tables["protocolInfoTable"];
        
        // 新增元数据的处理
        if(params.type == 'add'){
          var tempId = $("#sysId").val();
          if(tempId == null || tempId == ""){
             alert('请先输入系统Id');
             return false;
          }else{
             // 元数据Id为只读
             $("#sysId").attr("disabled",true);
          }
        }
        oTable.dataTable().fnAddData({
             "sysId": $("#sysId").val(),
             "connectMode":"<nobr><input type='text' id='connectMode'  onblur='checkDomInvalidChar(this);' style = 'width:90px;'/></nobr>",
             "sysAddr":"<nobr><input type='text' id='sysAddr'  onblur='checkDomInvalidChar(this);' style = 'width:90px;'/></nobr>",
             "appScene":"<nobr><input type='text' id='appScene'  onblur='checkDomInvalidChar(this);'  style = 'width:90px;'/></nobr>",
             "msgType":"<nobr><input type='text' id='msgType'  onblur='checkDomInvalidChar(this);'  style = 'width:90px;'/></nobr>",
             
             "sysType":"<select id='sysType'  style = 'width:90px;height:20px;'/><option value='提供方'>提供方</option><option value='调用方' selected>调用方</option></select>",
             "codeType":"<nobr><input type='text' id='codeType'  onblur='checkDomInvalidChar(this);'  style = 'width:90px;'/></nobr>",
             "macFlag":"<nobr><input type='text' id='macFlag'  onblur='checkDomInvalidChar(this);'  style = 'width:90px;'/></nobr>"
             
             
             
        });
     });
     // 删除行
     $( "#del" ).button().click(function(){
        var oTable = tables["protocolInfoTable"];
        var rowSelected = oTable.$("tr.row_selected");
        if(rowSelected.length > 0){
             for(var i=0;i<rowSelected.length;i++){
				oTable.fnDeleteRow(rowSelected[i]);

			}
			// delete datas
        }
        else{
           alert('请选择删除的行!');
       } 
       // 新增 如果表格无数据，则元数据id为只读
       if(params.type == 'add'){
           if(oTable.find("td").hasClass("dataTables_empty")){
              $("#sysId").removeAttr("disabled");
           }
       }
     });
     
     // 编辑行
	$("#edit").button().click(function() {
		var table = tables["protocolInfoTable"];
		var rowsSelected = table.$("tr.row_selected");	
		var selectedDatas = table.fnGetData(table.$("tr.row_selected")[0]);
		// 选中一行并且 行处于非编辑状态
		if(rowsSelected.length==1){
		    if(rowsSelected[0].cells[1].children[0] == undefined){	
			    var connectMode = selectedDatas["connectMode"]
			    var sysAddr = selectedDatas["sysAddr"];
			    var appScene = selectedDatas["appScene"];
			    var msgType = selectedDatas["msgType"];
			    
			    var sysType = selectedDatas["sysType"];
			    var codeType = selectedDatas["codeType"];
			    var macFlag = selectedDatas["macFlag"];
			    
			    
			    	
				rowsSelected[0].cells[1].innerHTML="<nobr><input type='text' value='"+connectMode+"' id='connectMode'  onblur='checkDomInvalidChar(this);' style = 'width:90px;'/></nobr>";
				rowsSelected[0].cells[2].innerHTML="<nobr><input type='text' value='"+sysAddr+"' id='sysAddr' onblur='checkDomInvalidChar(this);' style = 'width:90px;'/></nobr>";
				rowsSelected[0].cells[3].innerHTML="<nobr><input type='text' value='"+appScene+"' id='appScene' onblur='checkDomInvalidChar(this);' style = 'width:90px;'/></nobr>";	
				rowsSelected[0].cells[4].innerHTML="<nobr><input type='text' value='"+msgType+"' id='msgType'  onblur='checkDomInvalidChar(this);' style = 'width:90px;'/></nobr>";	
				
				if("提供方" == sysType){
				  rowsSelected[0].cells[5].innerHTML="<select id='sysType'  style = 'width:90px;height:20px;'/><option value='提供方' selected>提供方</option><option value='调用方'>调用方</option></select>";
				}
				else{
				  rowsSelected[0].cells[5].innerHTML="<select id='sysType'  style = 'width:90px;height:20px;'/><option value='提供方'>提供方</option><option value='调用方' selected>调用方</option></select>";
				}
				rowsSelected[0].cells[6].innerHTML="<nobr><input type='text' value='"+codeType+"' id='codeType' onblur='checkDomInvalidChar(this);' style = 'width:90px;'/></nobr>";
				rowsSelected[0].cells[7].innerHTML="<nobr><input type='text' value='"+macFlag+"' id='macFlag' onblur='checkDomInvalidChar(this);' style = 'width:90px;'/></nobr>";
				
				
				
			}
			$('#sysType').val(sysType);
		} else if (rowsSelected.length > 1) {
			alert("只能选中一行数据修改!");
			return false;
		} else {
			alert('请选中要编辑的行!');
			return false;
		}
	});
     // 保存信息  保存struct信息 structAttr信息
     $('#save').button().click(function(){
           var system = {
              systemId : $('#sysId').val(),
              systemAb : $('#sysAb').val(),
              systemName : $('#sysName').val(),
              remark : $('#remark').val(),
              modifyUser: '',
              updateTime: '',
              firstPublishDate : $('#firstTime').val(),
              secondPublishDate : $('#secondTime').val(),
              avgResTime: $('#avgTime').val(),
              maxConNum: $('#maxCon').val(),
              outMaxConNum: $('#outMaxCon').val(),
              timeOut: $('#tmOut').val(),
              successRate: $('#sucRate').val()
           };
           if(system.systemId == null || system.systemId == ""){
                alert('系统Id不能为空!');
                return;
           }
           if(system.systemAb == null || system.systemAb == ""){
                alert('系统简称不能为空!');
                return;
           }
           // 存储所有属性信息列表
		   var array = [];
		   var rowsAll = tables["protocolInfoTable"].$("tr");
		   if(rowsAll.length <= 0){
		        alert('系统协议信息不能为空!');
		        return;
		   }	
		   for(var i=0;i< rowsAll.length;i++){
			   var tr = rowsAll[i];
			   var mdtAttrInfo = {
					sysId: $('#sysId').val(),
					connectMode:(tr.cells[1].children[0]!=undefined)?(tr.cells[1].children[0].children[0].value):(tr.cells[1].innerText),
					sysAddr:(tr.cells[2].children[0]!=undefined)?(tr.cells[2].children[0].children[0].value):(tr.cells[2].innerText),
					appScene:(tr.cells[3].children[0]!=undefined)?(tr.cells[3].children[0].children[0].value):(tr.cells[3].innerText),
					msgType:(tr.cells[4].children[0]!=undefined)?(tr.cells[4].children[0].children[0].value):(tr.cells[4].innerText),
					
					sysType:(tr.cells[5].children[0]!=undefined)?(tr.cells[5].children[0].value):(tr.cells[5].innerText),
					codeType:(tr.cells[6].children[0]!=undefined)?(tr.cells[6].children[0].children[0].value):(tr.cells[6].innerText),
					macFlag:(tr.cells[7].children[0]!=undefined)?(tr.cells[7].children[0].children[0].value):(tr.cells[7].innerText),
					
					
					
		       };
		       if(mdtAttrInfo.msgType == null || mdtAttrInfo.msgType == ""){
		           alert('报文类型不能为空!');
		           return;
		       }
		       if(mdtAttrInfo.sysType == null || mdtAttrInfo.sysType == ""){
		           alert('系统类型不能为空!');
		           return;
		       }
		       if(mdtAttrInfo.codeType == null || mdtAttrInfo.codeType == ""){
		           alert('编码格式不能为空!');
		           return;
		       }
			   array.push(mdtAttrInfo);	
		   }
		   // check primary key constrains
		   for(var i=0;i<array.length; i++){
		       var source = array[i];
		       for( var j= i+1; j<array.length; j++){
		           var target = array[j];
		           if(source.msgType == target.msgType && source.sysType == target.sysType){
		                  alert('系统Id、报文类型和系统类型存在重复记录!');
		                  return false;
		           }
		       }
		   }
		   function saveProtocol(result){
		        if(result){
		              alert('保存成功!');
		              //初始化protocolInfoTable
					  if(tables["protocolInfoTable"]) {
						  tables["protocolInfoTable"].fnDestroy();
					  }
					  systemInfoManager.getProtocolInfosById($('#sysId').val(),initprotocolInfoTable);
		        }
		        else{
		           alert('保存失败!');
		        }
		   }
		   if(params.type == 'add'){
              systemInfoManager.insert(system);
              systemInfoManager.saveProtocolInfos(array, saveProtocol);
              $('#sysId').attr("disabled",true);
           }
           else{
              systemInfoManager.update(system);
              systemInfoManager.saveProtocolInfos(array, saveProtocol);
           }
     });
    
});
// 校验正则表达式
function checkRegexp( o, regexp) {
	if ( !(regexp.test( o ) ) ) {
		return false;
	  } 
	  else {
		return true;
	 }
}
// 校验特殊字符
function checkInvalidChar(o){
	if (/[@#$%^&*()=\/\.]/.test(o)) {
	    return false;
	    } 
	    else{
		return true;
	}
}
// 校验DOM正则表达式
function checkDomRegexp( o, regexp) {
      if(o.value != ""){
	    if ( !(/^([a-zA-Z])+$/.test( o.value) ) ) {
	      alert("只能输入英文字母!");
	      o.focus();
		  return false;
	    } 
	  }
}
// 校验DOM特殊字符
function checkDomInvalidChar(o){
    if(o.value != ""){
	   if (/[@#$%^&*?()=\/\.]/.test(o.value)) {
	     alert('不能包含特殊字符!');
	     o.focus();
	     return false;
	   } 
	}
}
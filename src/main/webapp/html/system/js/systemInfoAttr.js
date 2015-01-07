$(function() {
	var tables = {};
	var asInitVals = new Array();
	//var href = window.location.href;
	//var structId = href.substring(href.indexOf("=")+1);
	
	var params = window.dialogArguments;
	if(params.type == 'modify'){
	  $('#sysId').attr("readonly","true");
	  $('#sysId').val(params.sysId);
	  $('#sysAb').val(params.sysAb);
	  $('#sysName').val(params.sysName);
	  $('#remark').val(params.remark);
	  $('#firstTime').val(params.firstTime);
	}
	else{
	  $('#sysId').removeAttr("readonly");
	}
	
	// 新增判断元数据结构ID是否存在
	if(params.type == 'add'){
        function checkIsExist(result){
            if(result.systemId !=null && result.systemId !=""){
                 alert('系统ID已经存在!');
                 $('#sysId').focus();;
                 return false;
            }
        }
        // 判断元数据结构是否已经存在
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
					"aTargets" : [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]
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
             $("#sysId").attr("readonly","true");
          }
        }
        oTable.dataTable().fnAddData({
             "sysId": $("#sysId").val(),
             "connectMode":"<nobr><input type='text' id='connectMode' onclick='window.event.stopPropagation();' onblur='checkDomInvalidChar(this);' style = 'width:50px;'/></nobr>",
             "sysAddr":"<nobr><input type='text' id='sysAddr' onclick='window.event.stopPropagation();' onblur='checkDomInvalidChar(this);' style = 'width:50px;'/></nobr>",
             "appScene":"<nobr><input type='text' id='appScene' onclick='window.event.stopPropagation();' onblur='checkDomInvalidChar(this);'  style = 'width:50px;'/></nobr>",
             "msgType":"<nobr><input type='text' id='msgType' onclick='window.event.stopPropagation();' onblur='checkDomInvalidChar(this);'  style = 'width:50px;'/></nobr>",
             "timeout":"<nobr><input type='text' id='timeout' onclick='window.event.stopPropagation();' onblur='checkDomInvalidChar(this);'  style = 'width:50px;'/></nobr>",
             "sysType":"<select id='sysType' onclick='window.event.stopPropagation();' style = 'width:50px;height:20px;'/><option value=''></option><option value='提供方'>提供方</option><option value='调用方'>调用方</option></select>",
             "codeType":"<nobr><input type='text' id='codeType' onclick='window.event.stopPropagation();' onblur='checkDomInvalidChar(this);'  style = 'width:50px;'/></nobr>",
             "macFlag":"<nobr><input type='text' id='macFlag' onclick='window.event.stopPropagation();' onblur='checkDomInvalidChar(this);'  style = 'width:50px;'/></nobr>",
             "currentTimes":"<nobr><input type='text' id='currentTimes' onclick='window.event.stopPropagation();' onblur='checkDomInvalidChar(this);'  style = 'width:50px;'/></nobr>",
             "avgResTime":"<nobr><input type='text' id='avgResTime' onclick='window.event.stopPropagation();' onblur='checkDomInvalidChar(this);'  style = 'width:50px;'/></nobr>",
             "successRate":"<nobr><input type='text' id='successRate' onclick='window.event.stopPropagation();' onblur='checkDomInvalidChar(this);'  style = 'width:50px;'/></nobr>"
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
              $("#sysId").removeAttr("readonly");
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
			    var timeout = selectedDatas["timeout"];
			    var sysType = selectedDatas["sysType"];
			    var codeType = selectedDatas["codeType"];
			    var macFlag = selectedDatas["macFlag"];
			    var currentTimes = selectedDatas["currentTimes"];
			    var avgResTime = selectedDatas["avgResTime"];
			    var successRate = selectedDatas["successRate"];	
				rowsSelected[0].cells[1].innerHTML="<nobr><input type='text' value='"+connectMode+"' id='connectMode' onclick='window.event.stopPropagation();' onblur='checkDomInvalidChar(this);' style = 'width:50px;'/></nobr>";
				rowsSelected[0].cells[2].innerHTML="<nobr><input type='text' value='"+sysAddr+"' id='sysAddr' onclick='window.event.stopPropagation();' onblur='checkDomInvalidChar(this);' style = 'width:50px;'/></nobr>";
				rowsSelected[0].cells[3].innerHTML="<nobr><input type='text' value='"+appScene+"' id='sysAddr' onclick='window.event.stopPropagation();' onblur='checkDomInvalidChar(this);' style = 'width:50px;'/></nobr>";	
				rowsSelected[0].cells[4].innerHTML="<nobr><input type='text' value='"+msgType+"' id='sysAddr' onclick='window.event.stopPropagation();' onblur='checkDomInvalidChar(this);' style = 'width:50px;'/></nobr>";	
				rowsSelected[0].cells[5].innerHTML="<nobr><input type='text' value='"+timeout+"' id='timeout' onclick='window.event.stopPropagation();' onblur='checkDomInvalidChar(this);' style = 'width:50px;'/></nobr>";
				rowsSelected[0].cells[6].innerHTML="<select id='sysType' onclick='window.event.stopPropagation();'  style = 'width:50px;height:20px;'/><option value=''></option><option value='提供方'>提供方</option><option value='调用方'>调用方</option></select>";
				rowsSelected[0].cells[7].innerHTML="<nobr><input type='text' value='"+codeType+"' id='codeType' onclick='window.event.stopPropagation();' onblur='checkDomInvalidChar(this);' style = 'width:50px;'/></nobr>";
				rowsSelected[0].cells[8].innerHTML="<nobr><input type='text' value='"+macFlag+"' id='macFlag' onclick='window.event.stopPropagation();' onblur='checkDomInvalidChar(this);' style = 'width:50px;'/></nobr>";
				rowsSelected[0].cells[9].innerHTML="<nobr><input type='text' value='"+currentTimes+"' id='currentTimes' onclick='window.event.stopPropagation();' onblur='checkDomInvalidChar(this);' style = 'width:50px;'/></nobr>";
				rowsSelected[0].cells[10].innerHTML="<nobr><input type='text' value='"+avgResTime+"' id='avgResTime' onclick='window.event.stopPropagation();' onblur='checkDomInvalidChar(this);' style = 'width:50px;'/></nobr>";
				rowsSelected[0].cells[11].innerHTML="<nobr><input type='text' value='"+successRate+"' id='successRate' onclick='window.event.stopPropagation();' onblur='checkDomInvalidChar(this);' style = 'width:50px;'/></nobr>";
			}
			else{
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
              firstPublishDate : $('#firstTime').val()
           };
           console.log(system);
           // 存储所有属性信息列表
		   var array = [];
		   var rowsAll = tables["protocolInfoTable"].$("tr");		
		   for(var i=0;i<rowsAll.length;i++){
			   var tr = rowsAll[i];
			   var mdtAttrInfo = {
					sysId: $('#sysId').val(),
					connectMode:(tr.cells[1].children[0]!=undefined)?(tr.cells[1].children[0].value):(tr.cells[1].innerText),
					sysAddr:(tr.cells[2].children[0]!=undefined)?(tr.cells[2].children[0].value):(tr.cells[2].innerText),
					appScene:(tr.cells[3].children[0]!=undefined)?(tr.cells[3].children[0].value):(tr.cells[3].innerText),
					msgType:(tr.cells[4].children[0]!=undefined)?(tr.cells[4].children[0].value):(tr.cells[4].innerText),
					timeout:(tr.cells[5].children[0]!=undefined)?(tr.cells[5].children[0].value):(tr.cells[5].innerText),
					sysType:(tr.cells[6].children[0]!=undefined)?(tr.cells[6].children[0].value):(tr.cells[6].innerText),
					codeType:(tr.cells[7].children[0]!=undefined)?(tr.cells[7].children[0].value):(tr.cells[7].innerText),
					macFlag:(tr.cells[8].children[0]!=undefined)?(tr.cells[8].children[0].value):(tr.cells[8].innerText),
					currentTimes:(tr.cells[9].children[0]!=undefined)?(tr.cells[9].children[0].value):(tr.cells[9].innerText),
					avgResTime:(tr.cells[10].children[0]!=undefined)?(tr.cells[10].children[0].value):(tr.cells[10].innerText),
					successRate:(tr.cells[11].children[0]!=undefined)?(tr.cells[11].children[0].value):(tr.cells[11].innerText)
		       };
			   array.push(mdtAttrInfo);	
		   }
		   console.log(array);
		   if(params.type == 'add'){
              systemInfoManager.insert(system);
              systemInfoManager.saveProtocolInfos(array);
              $('#sysId').attr("readonly","true");
           }
           else{
              systemInfoManager.update(system);
              systemInfoManager.saveProtocolInfos(array);
           }	
           
		   //初始化protocolInfoTable
	 	   if (tables["protocolInfoTable"]) {
              tables["protocolInfoTable"].fnDestroy();
           }
           systemInfoManager.getProtocolInfosById($('#sysId').val(),initprotocolInfoTable);
       
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
	   if (/[@#$%^&*()=\/\.]/.test(o.value)) {
	     alert('不能包含特殊字符!');
	     o.focus();
	     return false;
	   } 
	}
}
$(function() {
	var tables = {};
	var asInitVals = new Array();
	var addCount = -1;
	// 编辑行ID开始
	var editCount= 1000;
	//var href = window.location.href;
	//var structId = href.substring(href.indexOf("=")+1);
	
	var params = window.dialogArguments;
	if(params.type == 'modify'){
	  $('#sId').attr("readonly","true");
	  $('#sId').val(params.sId);
	  $('#sName').val(params.sName);
	  $('#sRemark').val(params.sRemark);
	}
	else{
	  $('#sId').removeAttr("readonly");
	}
	
	// 新增判断元数据结构ID是否存在
	if(params.type == 'add'){
        function checkIsExist(result){
            if(result.structId !=null && result.structId !=""){
                 alert('元数据结构ID已经存在!');
                 $('#sId').focus();;
                 return false;
            }
        }
        // 判断元数据结构是否已经存在
        $('#sId').blur(function(){
            var structId = $('#sId').val();
            if(!checkRegexp( structId, /^([a-zA-Z])+$/)){
                 alert('元数据结构Id只能是英文字母!');
                 $('#sId').focus();
                 return false;
            }
            if(structId != null && structId !=""){
                 metadataStructsManager.getmdtStructsById(structId,checkIsExist);
            }
        });
    }
    // 添加校验
    $('#sName').blur(function(){
        if(!checkInvalidChar($('#sName').val())){
           alert('元数据结构名称不能包含特殊字符!');
           $('#sName').focus();
        }
    });
    $('#sRemark').blur(function(){
        if(!checkInvalidChar($('#sRemark').val())){
           alert('描述不能包含特殊字符!');
           $('#sRemark').focus();
        }
    });
	
	$('#tabs').tabs();
	$("#tab0").click(function(e) {
		if(tables["metadataStructsAttrTable"]){
			tables["metadataStructsAttrTable"].fnAdjustColumnSizing();
		}
	});
	/**
	 * init metadataStructsAttr table 
	 * @param {Object} result
	 * 
	 */
	var initmetadataStructsAttrTable = function initmetadataStructsAttrTable(result) {
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#metadataStructsAttrTable tbody tr").unbind("click");
			$("#metadataStructsAttrTable tbody tr").click(function(e) {
			    if($(this).find("td").hasClass("dataTables_empty")){
			    }
			    else{
				$(this).toggleClass("row_selected");
				/*$(this).editable('',{
				"callback":function(sValue,y){
				   var aPos = tables["metadataStructsAttrTable"].fnGetPosition(this);
				   alert(aPos[0]);
				},
				"submitdata":function(value,setting){
				},
				"height" : "14px",
				"width" : "100%"
				});*/
				}
			});
		};
		tables["metadataStructsAttrTable"] = $("#metadataStructsAttrTable").dataTable( {
			"aaData" : result,
			"aoColumns" : metadataStructsAttrTableLayout,
			"aoColumnDefs" : [
				{
					"sClass" : "center",
					"aTargets" : [ 0, 1, 2, 3, 4, 5]
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
		tables["metadataStructsAttrTable"].fnAdjustColumnSizing();
	};
	metadataStructsManager.getMdtStructsAttrByStructId(params.sId,initmetadataStructsAttrTable);

	//初始化操作Grid的搜索框
	var initmetadataStructsAttrTableFooter = function initmetadataStructsAttrTableFooter() {
		$("#metadataStructsAttrTable tfoot input").keyup(
				function() {
					tables["metadataStructsAttrTable"].fnFilter(this.value, $(
							"#metadataStructsAttrTable tfoot input").index(this),null,null,null,false);
				});
		$("#metadataStructsAttrTable tfoot input").each(function(i) {
			asInitVals[i] = this.value;
		});
		$("#metadataStructsAttrTable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#metadataStructsAttrTable tfoot input")
				.blur(
						function(i) {
							if (this.value == "") {
								this.className = "search_init";
								this.value = asInitVals[$(
										"#metadataStructsAttrTable tfoot input")
										.index(this)];
							}
						});
	};
	initmetadataStructsAttrTableFooter();
    
	 // 新增行
     $( "#add" ).button().click(function(){

        addCount++;
        var oTable = tables["metadataStructsAttrTable"];
        // 元数据下拉框ID
        var idMdt = 'add_metadataId_'+addCount;
        // 新增元数据的处理
        if(params.type == 'add'){
          var structId = $("#sId").val();
          if(structId == null || structId == ""){
             alert('请先输入structId');
             addCount--;
             return false;
          }else{
             // 元数据Id为只读
             $("#sId").attr("readonly","true");
          }
        }
        oTable.dataTable().fnAddData({
             "structId": $("#sId").val(),
             "elementId":"<nobr><input type='text' id='add_elementId' onclick='window.event.stopPropagation();' onblur='checkDomRegexp(this);' style = 'width:120px;'/></nobr>",
             "elementName":"<nobr><input type='text' id='add_elementName' onclick='window.event.stopPropagation();' onblur='checkDomInvalidChar(this);' style = 'width:120px;'/></nobr>",
             "remark":"<nobr><input type='text' id='add_remark' onclick='window.event.stopPropagation();' onblur='checkDomInvalidChar(this);'  style = 'width:120px;'/></nobr>",
             "isRequired":"<select id='add_isRequired' onclick='window.event.stopPropagation();' style = 'width:120px;height:20px;'/><option value=''></option><option value='Y'>Y</option><option value='N'>N</option></select>",
             "metadataId":"<select id='"+idMdt+"' onclick='window.event.stopPropagation();'  style = 'width:120px;'/><option value=''></option></select>"
        });
        // 初始化元数据下拉框
	    function initMetadataInfo() {
          $.ajax({
            url: '../metadata/list',
            type: 'GET',
            success: function(result) {
                initSelect(result);
            }
          });
        }
        var addMdt = $("#"+idMdt);
        function initSelect(result) {
		   for (var i=0;i<result.length;i++)
		     $("#"+idMdt).append("<option value='"+result[i].metadataId+"'>"+result[i].metadataId+"</option>");
	    }
	    initMetadataInfo();
        addMdt.combobox();
        
        $('#metadataStructsAttrTable tbody tr:last td:last .ui-combobox a').hide();
        //$('.ui-combobox:eq('+addCount+') span').hide();
        //$('.ui-combobox:eq('+addCount+') a').hide();
        
        // combobox下拉框赋值 innerHtml
        $('#metadataStructsAttrTable tbody tr:last td:last .ui-combobox input').blur(function(){
           if(""==this.value || null == this.value){
               alert('请输入元数据ID!');
               this.focus();
               return false;
           }
        });
     });
     // 删除行
     $( "#del" ).button().click(function(){
        var oTable = tables["metadataStructsAttrTable"];
        var rowSelected = oTable.$("tr.row_selected");
        if(rowSelected.length > 0){
             for(var i=0;i<rowSelected.length;i++){
                /*
				var datas = oTable.fnGetData(rowSelected[i]);
				var delids = {
                        structId: '',
                        metadataId: ''
                };
				delids.structId = datas["structId"];
				delids.metadataId = datas["metadataId"];
				delArr.push(delids);
				*/
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
              $("#sId").removeAttr("readonly");
           }
       }
     });
     
     // 编辑行
	$("#edit").button().click(function() {
		var table = tables["metadataStructsAttrTable"];
		// 元数据下拉框ID
        var idMdt = 'add_metadataId_'+editCount;
		var rowsSelected = table.$("tr.row_selected");	
		var selectedDatas = table.fnGetData(table.$("tr.row_selected")[0]);
		// 选中一行并且 行处于非编辑状态
		if(rowsSelected.length==1){
		    if(rowsSelected[0].cells[1].children[0] == undefined){	
			    var elementId = selectedDatas["elementId"];
			    var elementName = selectedDatas["elementName"]
			    var remark = selectedDatas["remark"];
			    var isRequired = selectedDatas["isRequired"];
			    var metadataId = selectedDatas["metadataId"];
				rowsSelected[0].cells[1].innerHTML="<nobr><input type='text' value='"+elementId+"' id='add_elementId' onclick='window.event.stopPropagation();' onblur='checkDomRegexp(this);' style = 'width:120px;'/></nobr>";	
				rowsSelected[0].cells[2].innerHTML="<nobr><input type='text' value='"+elementName+"' id='add_elementName' onclick='window.event.stopPropagation();' onblur='checkDomInvalidChar(this);' style = 'width:120px;'/></nobr>";
				rowsSelected[0].cells[3].innerHTML="<nobr><input type='text' value='"+remark+"' id='add_remark' onclick='window.event.stopPropagation();' onblur='checkDomInvalidChar(this);' style = 'width:120px;'/></nobr>";
				rowsSelected[0].cells[4].innerHTML="<select id='add_isRequired' onclick='window.event.stopPropagation();'  style = 'width:120px;height:20px;'/><option value=''></option><option value='Y'>Y</option><option value='N'>N</option></select>";	
				rowsSelected[0].cells[5].innerHTML="<select id='"+idMdt+"' onclick='window.event.stopPropagation();'  style = 'width:120px;'/><option value=''></option></select>";	
				$('#add_isRequired').val(isRequired);
				// 初始化元数据下拉框
		        function initMetadataInfo() {
	             $.ajax({
	                url: '../metadata/list',
	                type: 'GET',
	                success: function(result) {
	                initSelect(result);
	               }
	             });
	            }
	            var editMdt = $("#"+idMdt);
	            function initSelect(result) {
			          for (var i=0;i<result.length;i++)
			             editMdt.append("<option value='"+result[i].metadataId+"'>"+result[i].metadataId+"</option>");
		        }
		        initMetadataInfo();
	            editMdt.combobox();
	            
	            $('tr.row_selected:last .ui-combobox span').hide();
	            $('tr.row_selected:last .ui-combobox a').hide();
	        
	            // combobox下拉框赋值 innerHtml
	            $('tr.row_selected:last .ui-combobox input').val(metadataId);
	           
	            $('tr.row_selected:last .ui-combobox input').blur(function(){
	              if(""==this.value || null == this.value){
	                 alert('请输入元数据ID!');
	                 this.focus();
	               }
	            });
	            editCount++;
            }
            else{
              return;
            }
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
           var mdtStructs = {
              structId : $('#sId').val(),
              structName : $('#sName').val(),
              remark : $('#sRemark').val()
           };
        
           // 存储所有属性信息列表
		   var array = [];
		   var rowsAll = tables["metadataStructsAttrTable"].$("tr");		
		   for(var i=0;i<rowsAll.length;i++){
			   var tr = rowsAll[i];
			   var mdtAttrInfo = {
					structId: $('#sId').val(),
					elementId:(tr.cells[1].children[0]!=undefined)?(tr.cells[1].children[0].children[0].value):(tr.cells[1].innerText),
					elementName:(tr.cells[2].children[0]!=undefined)?(tr.cells[2].children[0].children[0].value):(tr.cells[2].innerText),
					remark:(tr.cells[3].children[0]!=undefined)?(tr.cells[3].children[0].children[0].value):(tr.cells[3].innerText),
					isRequired:(tr.cells[4].children[0]!=undefined)?(tr.cells[4].children[0].value):(tr.cells[4].innerText),
					metadataId:(tr.cells[5].children[0]!=undefined)?(tr.cells[5].children[1].children[0].value):(tr.cells[5].innerText)
		       };
			   array.push(mdtAttrInfo);	
		   }
		   // 元数据ID不能重复
		   if(checkMdtDif(rowsAll)){
		      alert("元数据Id不能重复!");
		      return false;
		   }
		   if(params.type == 'add'){
              metadataStructsManager.insertMdtStructs(mdtStructs);
              metadataStructsManager.insertMdtStructsAttr(array);
              $('#sId').attr("readonly","true");
           }
           else{
              metadataStructsManager.updateMdtStructs(mdtStructs);
              metadataStructsManager.updateMdtStructsAttr(array);
           }	
           
		   //初始化metadataStructsAttrTable
	 	   if (tables["metadataStructsAttrTable"]) {
              tables["metadataStructsAttrTable"].fnDestroy();
           }
           metadataStructsManager.getMdtStructsAttrByStructId($('#sId').val(),initmetadataStructsAttrTable);
       
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
// 新增或修改的元数据ID不能重复
function checkMdtDif(rowsAll){
    for(var i=0;i<rowsAll.length;i++){
       var tr1 = rowsAll[i];
       var value1 = (tr1.cells[5].children[0]!=undefined)?(tr1.cells[5].children[1].children[0].value):(tr1.cells[5].innerText);
       for(var j=i+1;j<rowsAll.length;j++){
           var tr2 = rowsAll[j];
           var value2 = (tr2.cells[5].children[0]!=undefined)?(tr2.cells[5].children[1].children[0].value):(tr2.cells[5].innerText);
           if(value1 == value2){
             return true;
           }
      }
   }
   return false;
}
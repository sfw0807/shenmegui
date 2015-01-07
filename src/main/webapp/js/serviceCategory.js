$(function() {
	var tables = {};
	var asInitVals = new Array();
	$('#tabs').tabs();
	$("#tab0").click(function(e) {
		if(tables["serviceCategoryTable"]){
			tables["serviceCategoryTable"].fnAdjustColumnSizing();
		}
	});
	/**
	 * init serviceCategory table 
	 * @param {Object} result
	 * 
	 */
	var initserviceCategoryTable = function initserviceCategoryTable(result) {
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#serviceCategoryTable tbody tr").unbind("click");
			$("#serviceCategoryTable tbody tr").click(function(e) {
			    if($(this).find("td").hasClass("dataTables_empty")){
			    }
			    else{
				$(this).toggleClass("row_selected");
				}
			});
		};
		//初始化serviceCategoryTable
	 	if (tables["serviceCategoryTable"]) {
            tables["serviceCategoryTable"].fnDestroy();
        }
		tables["serviceCategoryTable"] = $("#serviceCategoryTable").dataTable( {
			"aaData" : result,
			"aoColumns" : serviceCategoryTableLayout,
			"aoColumnDefs" : [
				{
					"sClass" : "center",
					"aTargets" : [ 0, 1, 2]
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
			"bJQueryUI": "true",
			"bAutoWidth" : "true",
			"bScrollCollapse" : "full_numbers",
			"bPaginate" : "true",
			"bSort" : "true",
			"oLanguage" : oLanguage,
			"fnDrawCallback" : function() {
				columnClickEventInit();
			}
		});
		tables["serviceCategoryTable"].fnAdjustColumnSizing();
	};
	serviceCategoryManager.getAll(initserviceCategoryTable);

	//初始化操作Grid的搜索框
	var initserviceCategoryTableFooter = function initserviceCategoryTableFooter() {
		$("#serviceCategoryTable tfoot input").keyup(
				function() {
					tables["serviceCategoryTable"].fnFilter(this.value, $(
							"#serviceCategoryTable tfoot input").index(this),null,null,null,false);
				});
		$("#serviceCategoryTable tfoot input").each(function(i) {
			asInitVals[i] = this.value;
		});
		$("#serviceCategoryTable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#serviceCategoryTable tfoot input")
				.blur(
						function(i) {
							if (this.value == "") {
								this.className = "search_init";
								this.value = asInitVals[$(
										"#serviceCategoryTable tfoot input")
										.index(this)];
							}
						});
	};
	initserviceCategoryTableFooter();
    
    
    // 删除服务分组
    $('#del').button().click(function(){
        var delids = '';
        $("#serviceCategoryTable tbody tr").each(function(){
             if($(this).hasClass("row_selected")){
                delids += $(this).find("td").eq(0).text() +",";
             }
        }); 
        if(delids == ""){
           alert('请选择删除的记录!');
           return false;
        }	
        delids = delids.substring(0,delids.length-1);
        
        function checkIsFather(result){
              if(result != null && result != ""){
                   alert('服务'+result+'下含有子类分组，请先删除!');
                   return false;
              }
              else{
                   if(confirm('确定删除服务分组'+delids+'?')){
                      function checkIsUsed(result){
                           if(result){
                              alert('分组已被服务使用，不能删除!');
                           }
                           else{
                              serviceCategoryManager.deleteById(delids);
                           }
                      }
                      serviceCategoryManager.checkIsUsed(delids,checkIsUsed);
                   }
             }
        };
        serviceCategoryManager.checkIsFather(delids,checkIsFather);
     
    });
     
     var categoryId = $( "#form_categoryId" ),
     categoryName = $( "#form_categoryName" ),
	 parentId = $( "#form_parentId" ),
     allFields = $([]).add(categoryId).add(categoryName).add(parentId),
	 tips = $( ".validateTips" );
	
	 // 初始化服务分组下拉框
	 function initCategoryInfo() {
        $.ajax({
            url: '../serviceCategory/first',
            type: 'GET',
            success: function(result) {
                initSelect(result);
            }
        });
     }
     function initSelect(result) {
		for (var i=0;i<result.length;i++)
		 parentId.append("<option value='"+result[i].categoryId+"'>"+result[i].categoryId+":"+result[i].categoryName+"</option>");
	 }
     initCategoryInfo();
     parentId.combobox();
	 // 校验提示
	 function updateTips( t ) {
			tips
				.text( t )
				.addClass( "ui-state-highlight" );
			setTimeout(function() {
				tips.removeClass( "ui-state-highlight", 1500 );
			}, 500 );
		}
     // 校验长度
     function checkLength( o, n, min, max ) {
            if(o.val().length == 0){
                o.addClass( "ui-state-error" );
                updateTips( n + " can not be null!");
				return false; 
            }
			if ( o.val().length > max || o.val().length < min ) {
				o.addClass( "ui-state-error" );
				updateTips( "Length of " + n + " must be between " +
					min + " and " + max + "." );
				return false;
			} else {
				return true;
			}
		}
     // 校验正则表达式
     function checkRegexp( o, regexp, n ) {
			if ( !(regexp.test( o.val() ) ) ) {
				o.addClass( "ui-state-error" );
				updateTips( n );
				return false;
			} else {
				return true;
			}
		}
	 // 校验特殊字符
	 function checkInvalidChar(o,n){
	     if (/[@#$%^&*()=\/\.]/.test(o.val())) {
		      o.addClass( "ui-state-error" );
			  updateTips(n);
			  return false;
			} else {
			  return true;
			}
	 }
	 // 新增服务分组
     $( "#add" ).button().click(function(){
        tips.text('');
        $('.ui-combobox input').each(function(){
            this.value = '';
        });
        categoryId.removeAttr("readonly");
        $( "#dialog-form" ).dialog("destroy");
        $( "#dialog-form" ).dialog({
        	title: "新增服务分组",
			autoOpen: false,
			height: 300,
			width: 300,
			modal: true,
			bgiframe: true,
			buttons: {
				"新增": function() {
					var bValid = true;
					allFields.removeClass( "ui-state-error" );
                    
                    
                    bValid = bValid && checkLength( categoryId, "分组Id", 4, 6 );
					bValid = bValid && checkLength( categoryName, "分组名称", 0, 50);
					
					bValid = bValid && checkRegexp( categoryId, /^([0-9])+$/, "分组Id只能是数字" );
					bValid = bValid && checkInvalidChar( categoryName,  "分组名称包含特殊字符" );
					

					if ( bValid ) {
                     // 判断系统是否已经存在
                     var parentIdValue = $('.ui-combobox:eq(0) input').val();
                     if(parentIdValue == null || parentIdValue == ""){
                         alert('请选择上级分组!');
                         return false;
                     }
                     parentIdValue = parentIdValue.substring(0,parentIdValue.indexOf(":"));
                     var params = {
                          categoryId : categoryId.val(),
                          categoryName : categoryName.val(),
                          parentId : parentIdValue
                       };
                     var serviceCategoryIsExists = function serviceCategoryIsExists(result){
                        if(result.categoryId != null && result.categoryId !=""){
                            alert('服务分组已经存在!');}
                         else{
                            serviceCategoryManager.insert(params);
                          }
                        };
                        serviceCategoryManager.getServiceCategoryById(categoryId.val(),serviceCategoryIsExists);
					  $( this ).dialog( "close" );
					}
				},
				"取消": function() {
					$( this ).dialog( "close" );
				}
			},
			close: function() {
				allFields.val( "" ).removeClass( "ui-state-error" );
			}
		});
	    $( "#dialog-form" ).dialog( "open" );
     });
     
     // 修改服务分组
     $('#modify').button().click(function(){
            tips.text('');
            var table = tables["serviceCategoryTable"];
             // 选择的行数
            var rowsSelected = table.$("tr.row_selected");
            if(rowsSelected.length == 0){
                alert('请选择修改的服务分组!');
                return false;
               }
            if(rowsSelected.length > 1){
                alert('请只选择一条记录修改!');
                return false;
               }
            var selectedDatas;
            selectedDatas = table.fnGetData(table.$("tr.row_selected")[0]);
            
            categoryId.val(selectedDatas["categoryId"]);
            categoryName.val(selectedDatas["categoryName"]);
            $('.ui-combobox:eq(0) input').val(selectedDatas["parentId"]);
            // 系统ID不能修改
            categoryId.attr("readonly","true");
            
            $( "#dialog-form" ).dialog("destroy");
            $( "#dialog-form" ).dialog({
            "title" : "修改服务分组",
			autoOpen: false,
			height: 300,
			width: 300,
			modal: true,
			bgiframe: true,
			buttons: {
				"修改": function() {
					var bValid = true;
					allFields.removeClass( "ui-state-error" );
					
					bValid = bValid && checkLength( categoryId, "分组Id", 4, 6 );
					bValid = bValid && checkLength( categoryName, "分组名称", 0, 50);
					
					bValid = bValid && checkRegexp( categoryId, /^([0-9])+$/, "分组Id只能是数字" );
					bValid = bValid && checkInvalidChar( categoryName,  "分组名称包含特殊字符" );
					
					if ( bValid ) {
					   var parentIdValue = $('.ui-combobox:eq(0) input').val();
                       if(parentIdValue == null || parentIdValue == ""){
                         alert('请选择上级分组!');
                         return false;
                       }
                       if(parentIdValue.indexOf(":")>0){
                       parentIdValue = parentIdValue.substring(0,parentIdValue.indexOf(":"));
                       }
					   var params = {
                          categoryId : categoryId.val(),
                          categoryName : categoryName.val(),
                          parentId : parentIdValue
                       };
                       serviceCategoryManager.update(params);
					   $( this ).dialog( "close" );
					}
				},
				"取消": function() {
					$( this ).dialog( "close" );
				}
			},
			close: function() {
				allFields.val( "" ).removeClass( "ui-state-error" );
			}
		});
		$( "#dialog-form" ).dialog( "open" );
     });
    
});

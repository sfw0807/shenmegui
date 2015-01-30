$(function() {
    var isChrome = (navigator.appVersion.indexOf("Chrome") != -1) ? true : false;
	var tables = {};
	var asInitVals = [];
	$('#tabs').tabs();
	$("#tab0").click(function(e) {
		if(tables["metadataTable"]){
			tables["metadataTable"].fnAdjustColumnSizing();
		}
	});
	/**
	 * init metadata table 
	 * @param {Object} result
	 * 
	 */
	var initmetadataTable = function initmetadataTable(result) {

		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#metadataTable tbody tr").unbind("click");
			$("#metadataTable tbody tr").click(function(e) {
				if($(this).find("td").hasClass("dataTables_empty")){
			    }
			    else{
				$(this).toggleClass("row_selected");
				}
			});
		};
		//初始化metadataTable
	 	if (tables["metadataTable"]) {
            tables["metadataTable"].fnDestroy();
        }
		tables["metadataTable"] = $("#metadataTable").dataTable( {
			"aaData" : result,
			"aoColumns" : metadataTableLayout,
			"aoColumnDefs" : [
				{
					"sClass" : "center",
					"aTargets" : [ 0, 1, 2, 3, 4, 5 , 6, 7, 8]
				}
			],
			"bJQueryUI": "true",
			"bAutoWidth" : "true",
			//"sScrollY" : "500px",
			//"sScrollX" : "500px",
			"bScrollCollapse" : "full_numbers",
			"bPaginate" : "true",
			"bSort" : "true",
			"oLanguage" : oLanguage,
			"fnDrawCallback" : function() {
				columnClickEventInit();
			}
		});
		tables["metadataTable"].fnAdjustColumnSizing();
	};
	metadataManager.getAllMetadataList(initmetadataTable);

	//初始化操作Grid的搜索框
	var initmetadataTableFooter = function initmetadataTableFooter() {
		$("#metadataTable tfoot input").keyup(
				function() {
					tables["metadataTable"].fnFilter(this.value, $(
							"#metadataTable tfoot input").index(this),null,null,null,false);
				});
		$("#metadataTable tfoot input").each(function(i) {
			asInitVals[i] = this.value;
		});
		$("#metadataTable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#metadataTable tfoot input")
				.blur(
						function(i) {
							if (this.value == "") {
								this.className = "search_init";
								this.value = asInitVals[$(
										"#metadataTable tfoot input")
										.index(this)];
							}
						});
	};
	initmetadataTableFooter();

    // 删除元数据
    $('#del').button().click(function(){
        var delids = '';
        $("#metadataTable tbody tr").each(function(){
             if($(this).hasClass("row_selected")){
                delids += $(this).find("td").eq(0).text() +",";
             }
        }); 
        if(delids == ""){
           alert('请选择删除的记录!');
           return false;
        }	
        delids = delids.substring(0,delids.length-1);
        function checkIsUsed(result){
           if(result != null && result != ""){
              alert('元数据'+result+'已被服务使用，不能删除!');
              return false;
           }
           else{
              if(confirm('确定删除元数据'+delids+'?')){
                 metadataManager.deleteByMetadataIds(delids);
              }
           }
        }
        metadataManager.checkIsUsed(delids,checkIsUsed);
    });
    
    // 查看元数据服务调用情况
    $('#serviceCallback').button().click(function(){
        var count = 0;
        var id;
        $("#metadataTable tbody tr").each(function(){
             if($(this).hasClass("row_selected")){
             count++;
             id = $(this).find("td").eq(0).text();
             }
        }); 	
        if(count == 0){
            alert('请选择记录!');
            return false;}
        if(count > 1){
            alert('只能选择一条记录查看!');
            return false;}

        if(isChrome){
            var winOption = "height=800PX,width=1200px,top=50,scrollbars=yes,resizable=yes,fullscreen=0";
            return  window.open("../jsp/mdt_used.jsp?metadataId="+id,window, winOption);
        }else{
            window.showModalDialog('../jsp/mdt_used.jsp',id,'dialogWidth:900px;dialogHeight:400px');

        }
    });
     
     var metadataId = $( "#form_metadataId" ),
     name = $( "#form_name" ),
	 remark = $( "#form_remark" ),
	 type = $( "#form_type" ),
	 length = $( "#form_length" ),
	 scale = $( "#form_scale" ),
     allFields = $( [] ).add( metadataId ).add( name ).add( remark ).add( type ).add( length ).add( scale ),
	 tips = $( ".validateTips" );
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
	     if (/[@#$%^&*().\=]/.test(o.val())) {
		      o.addClass( "ui-state-error" );
			  updateTips(n);
			  return false;
			} else {
			  return true;
			}
	 }
	 // 新增元数据
     $('#add').button().click(function(){
     	tips.text('');
     	metadataId.removeAttr("readonly");
     	$( "#dialog-form" ).dialog("destroy");
        $( "#dialog-form" ).dialog({
        	"title": "新增元数据",
			autoOpen: false,
			height: 400,
			width: 350,
			modal: true,
			bgiframe: true,
			buttons: {
				"新增": function() {
					var bValid = true;
					allFields.removeClass( "ui-state-error" );

                    bValid = bValid && checkLength( metadataId, "元数据Id", 2, 25 );
					bValid = bValid && checkLength( name, "元数据名称", 2, 500 );
					bValid = bValid && checkLength( type, "类型", 3, 16 );
					bValid = bValid && checkLength( length, "长度", 0, 8 );
					
					bValid = bValid && checkInvalidChar( metadataId,  "元数据ID包含特殊字符" );
					bValid = bValid && checkInvalidChar( name,  "元数据名称包含特殊字符" );
					bValid = bValid && checkRegexp( type, /^([a-zA-Z])+$/, "类型只能是英文字母" );
					bValid = bValid && checkRegexp( length, /^([0-9])+$/, "长度只能是数字" );

					if ( bValid ) {
                     // 判断元数据是否已经存在,不存在做插入操作
                     var params = {
                          metadataId : metadataId.val(),
                          name : name.val(),
                          remark : remark.val(),
                          type : type.val(),
                          length : length.val(),
                          scale : scale.val()
                       };
                     var metadataIsExists = function metadataIsExists(result){
                        if(result){
                            alert('元数据已经存在!');}
                           else{
                             metadataManager.insertByMetadata(params);
                            }
                         };
                      metadataManager.getMetadataById(metadataId.val(),metadataIsExists);
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
     
     // 修改元数据
     $('#modify').button().click(function(){
            tips.text('');
            var table = tables["metadataTable"];
             // 选择的行数
            var rowsSelected = table.$("tr.row_selected");
            if(rowsSelected.length == 0){
                alert('请选择修改的记录!');
                return false;
               }
            if(rowsSelected.length > 1){
                alert('请只选择一条记录修改!');
                return false;
               }
            var selectedDatas;
            selectedDatas = table.fnGetData(table.$("tr.row_selected")[0]);
            
            // 赋值修改的元数据记录
            metadataId.val(selectedDatas["metadataId"]);
            name.val(selectedDatas["name"]);
            remark.val(selectedDatas["remark"]);
            type.val(selectedDatas["type"]);
            length.val(selectedDatas["length"]);
            scale.val(selectedDatas["scale"]);
            // 元数据ID不能被修改
            metadataId.attr("readonly","true");
            
            $( "#dialog-form" ).dialog("destroy");
            $( "#dialog-form" ).dialog({
            "title" : "修改元数据",
			autoOpen: false,
			height: 400,
			width: 350,
			modal: true,
			bgiframe: true,
			buttons: {
				"修改": function() {
					var bValid = true;
					allFields.removeClass( "ui-state-error" );
					
                    bValid = bValid && checkLength( metadataId, "元数据Id", 2, 25 );
					bValid = bValid && checkLength( name, "元数据名称", 2, 500 );
					bValid = bValid && checkLength( type, "类型", 3, 16 );
					bValid = bValid && checkLength( length, "长度", 0, 8 );
					
					bValid = bValid && checkInvalidChar( metadataId,  "元数据ID包含特殊字符" );
					bValid = bValid && checkInvalidChar( name,  "元数据名称包含特殊字符" );
					bValid = bValid && checkRegexp( type, /^([a-zA-Z])+$/, "类型只能是英文字母" );
					bValid = bValid && checkRegexp( length, /^([0-9])+$/, "长度只能是数字" );

					if ( bValid ) {
					    var params = {
                          metadataId : metadataId.val(),
                          name : name.val(),
                          remark : remark.val(),
                          type : type.val(),
                          length : length.val(),
                          scale : scale.val()
                       };
                       metadataManager.updateByMetadata(params);
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

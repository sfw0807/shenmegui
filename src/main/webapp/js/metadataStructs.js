$(function() {
	var tables = {};
	var asInitVals = new Array();
	var params = {
        sId : '',
        sName : '',
        sRemark : '',
        type: ''
    };
	$('#tabs').tabs();
	$("#tab0").click(function(e) {
		if(tables["metadataStructsTable"]){
			tables["metadataStructsTable"].fnAdjustColumnSizing();
		}
	});
	/**
	 * init metadataStructs table 
	 * @param {Object} result
	 * 
	 */
	var initmetadataStructsTable = function initmetadataStructsTable(result) {
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#metadataStructsTable tbody tr").unbind("click");
			$("#metadataStructsTable tbody tr").click(function(e) {
			    if($(this).find("td").hasClass("dataTables_empty")){
			    }
			    else{
				$(this).toggleClass("row_selected");
				}
			});
		};
		//初始化metadataStructsTable
	 	if (tables["metadataStructsTable"]) {
            tables["metadataStructsTable"].fnDestroy();
        }
		tables["metadataStructsTable"] = $("#metadataStructsTable").dataTable( {
			"aaData" : result,
			"aoColumns" : metadataStructsTableLayout,
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
		tables["metadataStructsTable"].fnAdjustColumnSizing();
	};
	metadataStructsManager.getAll(initmetadataStructsTable);

	//初始化操作Grid的搜索框
	var initmetadataStructsTableFooter = function initmetadataStructsTableFooter() {
		$("#metadataStructsTable tfoot input").keyup(
				function() {
					tables["metadataStructsTable"].fnFilter(this.value, $(
							"#metadataStructsTable tfoot input").index(this),null,null,null,false);
				});
		$("#metadataStructsTable tfoot input").each(function(i) {
			asInitVals[i] = this.value;
		});
		$("#metadataStructsTable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#metadataStructsTable tfoot input")
				.blur(
						function(i) {
							if (this.value == "") {
								this.className = "search_init";
								this.value = asInitVals[$(
										"#metadataStructsTable tfoot input")
										.index(this)];
							}
						});
	};
	initmetadataStructsTableFooter();
    
    
    // 删除元数据结构
    $('#del').button().click(function(){
        var delids = '';
        $("#metadataStructsTable tbody tr").each(function(){
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
                   alert('元数据结构['+result+']已被使用,不能删除!');
                   return false;
              }
              else{
                   if(confirm('确定删除元数据结构['+delids+']?')){
                      metadataStructsManager.deleteById(delids);
                   }
             }
        };
        metadataStructsManager.checkIsUsed(delids,checkIsUsed);
     
    });
	
	/*
	 // 初始化服务分组下拉框
	 function initCategoryInfo() {
        $.ajax({
            url: '/metadataStructs/first',
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
	 */
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
	 // 新增元数据结构
     $( "#add" ).button().click(function(){
        params.sId = '@#',
        params.sName = '',
        params.sRemark = '',
        params.type = 'add';
        window.showModalDialog('../jsp/metadataStructsAttr.jsp',params,'dialogWidth=950px;dialogHeight=500px');
     });
     
     // 修改元数据结构
     $('#modify').button().click(function(){
        var count = 0;
        params.type = 'modify';
        $("#metadataStructsTable tbody tr").each(function(){
             if($(this).hasClass("row_selected")){
             count++;
             params.sId = $(this).find("td").eq(0).text();
             params.sName = $(this).find("td").eq(1).text();
             params.sRemark = $(this).find("td").eq(2).text();
             }
        }); 	
        if(count == 0){
            alert('请选择记录!');
            return false;}
        if(count > 1){
            alert('请只选择一条记录修改!');
            return false;}
         window.showModalDialog('../jsp/metadataStructsAttr.jsp',params,'dialogWidth=950px;dialogHeight=500px'); 
        // window.open('/html/metadataStructs/metadataStructsAttr.html?id='+id,'','dialogWidth:600px;dialogHeight=200px');  
     });
    
});

$(function() {
	var tables = {};
	var asInitVals = new Array();
	$('#tabs').tabs();
	$("#tab0").click(function(e) {
		if(tables["passwordTable"]){
			tables["passwordTable"].fnAdjustColumnSizing();
		}
	});
	/**
	 * init password table 
	 * @param {Object} result
	 * 
	 */
	var initpasswordTable = function initpasswordTable(result) {
		//初始化对Grid的操作事件
		var columnClickEventInit = function columnClickEventInit() {
			$("#passwordTable tbody tr").unbind("click");
			$("#passwordTable tbody tr").click(function(e) {
				if($(this).find("td").hasClass("dataTables_empty")){
			    }
			    else{
				$(this).toggleClass("row_selected");
				}
			});
		};
		//初始化passwordTable
	 	if (tables["passwordTable"]) {
            tables["passwordTable"].fnDestroy();
        }
		tables["passwordTable"] = $("#passwordTable").dataTable( {
			"aaData" : result,
			"aoColumns" : passwordTableLayout,
			"aoColumnDefs" : [
				{
					"sClass" : "center",
					"aTargets" : [ 0, 1, 2, 3, 4]
				},
				{
                    "mRender": function (data, type, row) {
                         if(data == '1'){
                            return '有效';
                         }
                         else{
                            return '无效';
                         }
                    },
                    "aTargets": [ 4 ]
                }
			],
			"bJQueryUI": true,
			"bAutoWidth" : true,
			"bScrollCollapse" : "full_numbers",
			"bPaginate" : true,
			"bSort" : true,
			"oLanguage" : oLanguage,
			"fnDrawCallback" : function() {
				columnClickEventInit();
			}
		});
		tables["passwordTable"].fnAdjustColumnSizing();
	};
	passwordManager.getAll(initpasswordTable);

	//初始化操作Grid的搜索框
	var initpasswordTableFooter = function initpasswordTableFooter() {
		$("#passwordTable tfoot input").keyup(
				function() {
					tables["passwordTable"].fnFilter(this.value, $(
							"#passwordTable tfoot input").index(this),null,null,null,false);
				});
		$("#passwordTable tfoot input").each(function(i) {
			asInitVals[i] = this.value;
		});
		$("#passwordTable tfoot input").focus(function() {
			if (this.className == "search_init") {
				this.className = "";
				this.value = "";
			}
		});
		$("#passwordTable tfoot input")
				.blur(
						function(i) {
							if (this.value == "") {
								this.className = "search_init";
								this.value = asInitVals[$(
										"#passwordTable tfoot input")
										.index(this)];
							}
						});
	};
	initpasswordTableFooter();
    
   
     var userName = $( "#form_userName" ),
     oldPwd = $( "#form_oldPwd" ),
	 newPwd_first = $( "#form_newPwd_first" ),
	 newPwd_second = $( "#form_newPwd_second" ),
     allFields = $( [] ).add( userName ).add( oldPwd ).add( newPwd_first ).add( newPwd_second ),
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
	     if (/[@#$%^&*()=]/.test(o.val())) {
		      o.addClass( "ui-state-error" );
			  updateTips(n);
			  return false;
			} else {
			  return true;
			}
	 }
     
     // 修改服务
     $('#modify').button().click(function(){
            tips.text('');
            var table = tables["passwordTable"];
             // 选择的行数
            var rowsSelected = table.$("tr.row_selected");
            if(rowsSelected.length == 0){
                alert('请选择修改密码的用户!');
                return false;
               }
            if(rowsSelected.length > 1){
                alert('请只选择一个用户修改!');
                return false;
               }
            var selectedDatas;
            selectedDatas = table.fnGetData(table.$("tr.row_selected")[0]);
            
            // 赋值修改的记录
            userName.val(selectedDatas["name"]);
           
            // 用户Id不能被修改
            userName.attr('disabled',true);
            //$( "#dialog-form" ).dialog("destroy");
            $( "#dialog:ui-dialog" ).dialog( "destroy" );
            $( "#dialog-form" ).dialog({
            "title" : "修改密码",
			autoOpen: false,
			height: 400,
			width: 350,
			modal: true,
			bgiframe: true,
			buttons: {
				"修改": function() {
					var bValid = true;
					allFields.removeClass( "ui-state-error" );
					var userId = userName.val();
					var oldPassword = oldPwd.val();
					var newPassword_1 = newPwd_first.val();
					var newPassword_2 = newPwd_second.val();
					// 新密码两次输入校验
					if(oldPassword ==''){
					    alert('请输入旧密码!');
					    return false;
					}
					if(newPassword_1 == '' || newPassword_2== ''){
					    alert('请输入新密码!');
					    return false;
					}
					if(newPassword_1 != newPassword_2){
					    alert('两次输入的新密码不相同!');
					    return false;
					}
					if(oldPassword == newPassword_1){
					     alert('新旧密码不能相同!');
					    return false;
					}
					/*
					bValid = bValid && checkLength( oldPwd, "密码", 6, 10);
					bValid = bValid && checkLength( newPwd_first, "密码", 6, 10);
					*/


					if ( bValid ) {
					   function updatePassword(result){
					     if(result){
						     var params = [userId, newPassword_1];
	                         passwordManager.update(params);
	                     }
	                     else{
	                         alert('旧密码不正确!');
	                         return false;
	                     }
                       };
                       var oldParams = [userId, oldPassword];
                       passwordManager.checkOriginalPwd(oldParams, updatePassword);
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
   
     // 密码重置
    $('#reset').button().click(function(){
        var userIds = '';
        $("#passwordTable tbody tr").each(function(){
             if($(this).hasClass("row_selected")){
                userIds += $(this).find("td").eq(0).text() +",";
             }
        }); 
        if(userIds == ""){
           alert('请选择记录!');
           return false;
        }	
        userIds = userIds.substring(0,userIds.length-1);
        if(confirm('确定重置用户['+userIds+']的密码?')){
           passwordManager.reset(userIds);
        }
    });
    
    $('#save').button().click(function(){
        var userId = $('#userId').val();
        var oldPassword = $('#oldPwd').val();
		var newPassword_1 = $('#newPwd_1').val();
		var newPassword_2 = $('#newPwd_2').val();
		// 新密码两次输入校验
		if(oldPassword ==''){
				alert('请输入旧密码!');
				return false;
		}
		if(newPassword_1 == '' || newPassword_2== ''){
				alert('请输入新密码!');
				return false;
		}
		if(newPassword_1 != newPassword_2){
				alert('两次输入的新密码不相同!');
				return false;
		}
		if(oldPassword == newPassword_1){
				alert('新旧密码不能相同!');
				return false;
		}
		function updatePassword(result){
			if(result){
				var params = [userId, newPassword_1];
	            passwordManager.update(params);
	        }
	        else{
	            alert('旧密码不正确!');
	            return false;
	        }
        };
        var oldParams = [userId, oldPassword];
        passwordManager.checkOriginalPwd(oldParams, updatePassword);
    });
});

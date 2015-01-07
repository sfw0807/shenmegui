
		var tables = new Array();
		var asInitVals = new Array();
		$(function () {
		$('#tabs').tabs();
			$('#operateId').val('');
			$('#ecode').val('');
			$('#operateId').attr("disabled", true);
			$('#ecode').attr("disabled", true);
			//初始化服务Grid的方法
		    var initInterfaceTable = function initInterfaceTable(result) {
		        //初始化对Grid的操作事件
		        var columnClickEventInit = function columnClickEventInit() {
		            $("#interfaceTable tbody tr").unbind("click");
		            $("#interfaceTable tbody tr").click(function (e) {
		                if ($(this).hasClass("row_selected")) {
		                	var value = $('#ecode').val();
		                	var str = $(this).find("td").eq(0).text() + ',';
		                	var value = value.replace(str, '');
		                	$('#ecode').val(value);
		                	
		                	value = $('#operateId').val();
		                	str = $(this).find("td").eq(2).text() + ',';
		                	value = value.replace(str, '');
		                	$('#operateId').val(value);
		                	
		                	$(this).toggleClass("row_selected");
		                } else {
			                $(this).toggleClass("row_selected");
			                // 追加 ecode
			                var temp = $('#ecode').val() + $(this).find("td").eq(0).text() + ',';
			                $('#ecode').val(temp);
			                temp = $('#operateId').val() + $(this).find("td").eq(2).text() + ',';
			                $('#operateId').val(temp);
			            }
		            });
		        };
		        //创建Grid
		        tables["interfaceTable"] = $("#interfaceTable").dataTable({
		            "aaData": result,
		            "aoColumns": exportExcelLayout,
		            "aoColumnDefs": [
		            
		                { "sClass": "center", "aTargets": [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11] }
		                
		            ],
		            "bJQueryUI": true,
					"bAutoWidth" : true,
					"bScrollCollapse" : "full_numbers",
					"bPaginate" : true,
					"bSort" : true,
					"oLanguage" : oLanguage,
		            "fnDrawCallback": function () {
		                columnClickEventInit();
		            }
		        });
		        tables["interfaceTable"].fnAdjustColumnSizing();
		    };
			
			 //初始化操作Grid的搜索框
		    var initInterfaceTableFooter = function initInterfaceTableFooter() {
		        $("#interfaceTable tfoot input").keyup(function () {
		            tables["interfaceTable"].fnFilter(this.value, $("#interfaceTable tfoot input").index(this));
		        });
		        $("#interfaceTable tfoot input").each(function (i) {
		            asInitVals[i] = this.value;
		        });
		        $("#interfaceTable tfoot input").focus(function () {
		            if (this.className == "search_init") {
		                this.className = "";
		                this.value = "";
		            }
		        });
		        $("#interfaceTable tfoot input").blur(function (i) {
		            if (this.value == "") {
		                this.className = "search_init";
		                this.value = asInitVals[$("#interfaceTable tfoot input").index(this)];
		            }
		        });
		    };
		    initInterfaceTableFooter();
			
			interfaceManager.getViewList(initInterfaceTable);
			
			
			$('#exportType').change(function (){
				if($(this).val() == 'multi') {
					$('.ui-combobox:eq(0) input').val('---请选择---');
					$('#systd').find('*').each(
						function(){
							$(this).attr("disabled",true);
						}
					);
					$('#system').hide();
					return ;
				}
				// 清空input
				$('#system').attr("disabled", false);
				$('#ecode').val('');
				$('#operateId').val('');
				$('#systd').find('*').each(
						function(){
							$(this).attr("disabled",false);
						}
					);
				$("#interfaceTable tbody").find('tr').each(function(){
					if ($(this).hasClass("row_selected")) {
						$(this).toggleClass("row_selected");
					}
				});
			});
			
			$('#reset').button().click(function () {
				//$('.ui-combobox:eq(0) input').val('---请选择---');
				//$('#system').val('');
				$('#ecode').val('');
				$('#operateId').val('');
				$("#interfaceTable tbody").find('tr').each(function(){
					if ($(this).hasClass("row_selected")) {
						$(this).toggleClass("row_selected");
					}
				});
			});
				
			$('#reset1').button().click(function () {
				$('.ui-combobox:eq(0) input').val('---请选择---');
				$('#system').val('');
			});
			
			$('#export').button().click(function () {
				if ($('#ecode').val() == '') {
					alert('请在列表中点击选中要导出的接口！');
					return ;
				}
				var params = 'multi' + "," + $('#ecode').val() 
						 + $('#doctype').val() ;
						
			    $.fileDownload("../exportExcel/byInterfaceId/" + params, {
            	});
			    
			});
			
			$('#export1').button().click(function () {
				var params = 'system' + "," + $('#system :selected').val() 
						+ "," +  $('#systemType').val() + "," + $('#doctype').val() ;
				// 系统导出验证系统关联
				interfaceManager.isSysLinked(params, function(result){
					if (result == false) {
						alert("系统未关联. 不能按照系统导出!")
						return ;
					}
				});
				
			    $.fileDownload("../exportExcel/byInterfaceId/" + params, {
            	});
			    
			});
			
			var result = 
			    $.ajax({
			        url: '../exportExcel/getAllSystem',
			        type: 'GET',
			        success: function(result) {
			            initSelect(result);
			        }
	    		});
	    		
	    	$( "#system" ).combobox();
		});
		
		function initSelect(result) {
			for (var i=0;i<result.length;i++)
				$('#system').append("<option value='"+result[i].systemId+"'>"+result[i].systemId+":"+result[i].systemName+"</option>");
		}
		
		(function( $ ) {
		$.widget( "ui.combobox", {
			_create: function() {
				var input,
					self = this,
					select = this.element.hide(),
					selected = select.children( ":selected" ),
					value = selected.val() ? selected.text() : "",
					wrapper = this.wrapper = $( "<span>" )
						.addClass( "ui-combobox" )
						.insertAfter( select );

				input = $( "<input>" )
					.appendTo( wrapper )
					.val( value )
					.addClass( "ui-state-default ui-combobox-input" )
					.autocomplete({
						delay: 0,
						minLength: 0,
						source: function( request, response ) {
							var matcher = new RegExp( $.ui.autocomplete.escapeRegex(request.term), "i" );
							response( select.children( "option" ).map(function() {
								var text = $( this ).text();
								if ( this.value && ( !request.term || matcher.test(text) ) )
									return {
										label: text.replace(
											new RegExp(
												"(?![^&;]+;)(?!<[^<>]*)(" +
												$.ui.autocomplete.escapeRegex(request.term) +
												")(?![^<>]*>)(?![^&;]+;)", "gi"
											), "<strong>$1</strong>" ),
										value: text,
										option: this
									};
							}) );
						},
						select: function( event, ui ) {
							ui.item.option.selected = true;
							self._trigger( "selected", event, {
								item: ui.item.option
							});
						},
						change: function( event, ui ) {
							if ( !ui.item ) {
								var matcher = new RegExp( "^" + $.ui.autocomplete.escapeRegex( $(this).val() ) + "$", "i" ),
									valid = false;
								select.children( "option" ).each(function() {
									if ( $( this ).text().match( matcher ) ) {
										this.selected = valid = true;
										return false;
									}
								});
								if ( !valid ) {
									// remove invalid value, as it didn't match anything
									$( this ).val( "" );
									select.val( "" );
									input.data( "autocomplete" ).term = "";
									return false;
								}
							}
						}
					})
					.addClass( "ui-widget ui-widget-content ui-corner-left" )
					.css("background","white");

				input.data( "autocomplete" )._renderItem = function( ul, item ) {
					return $( "<li></li>" )
						.data( "item.autocomplete", item )
						.append( "<a>" + item.label + "</a>" )
						.appendTo( ul );
				};

				$( "<a>" )
					.attr( "tabIndex", -1 )
					.attr( "title", "Show All Items" )
					.appendTo( wrapper )
					.button({
						icons: {
							primary: "ui-icon-triangle-1-s"
						},
						text: false
					})
					.removeClass( "ui-corner-all" )
					.addClass( "ui-corner-right ui-combobox-toggle" )
					.click(function() {
						// close if already visible
						if ( input.autocomplete( "widget" ).is( ":visible" ) ) {
							input.autocomplete( "close" );
							return;
						}

						// work around a bug (likely same cause as #5265)
						$( this ).blur();

						// pass empty string as value to search for, displaying all results
						input.autocomplete( "search", "" );
						input.focus();
					});
			},

			destroy: function() {
				this.wrapper.remove();
				this.element.show();
				$.Widget.prototype.destroy.call( this );
			}
		});
	})( jQuery );
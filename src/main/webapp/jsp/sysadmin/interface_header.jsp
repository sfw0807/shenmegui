<%@ page contentType="text/html; charset=utf-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css"
			href="/resources/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css"
			href="/resources/themes/icon.css">
		<link href="/resources/css/ui.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="/resources/js/jquery.min.js"></script>
		<script type="text/javascript"
			src="/resources/js/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="/resources/js/ui.js"></script>
		<script type="text/javascript" src="/resources/js/treegrid-dnd.js"></script>
		<script type="text/javascript" src="/js/sysadmin/sysManager.js"></script>
		<script type="text/javascript">
	var editingId;
	var parentId;
	var isupdate;
	var toolbar = [{
			text:'保存',
			iconCls:'icon-save',
			handler:function(){
				 $('#tg').treegrid('endEdit', editingId);
				 var row = $('#tg').treegrid('find',editingId);
	             if (row){
	             	var structName = row.structName;
	             	var structAlias = row.structAlias;
	                var type = row.type;
	                var length = row.length;
	                var metadataId = row.metadataId;
	                var scale = row.scale;
	                var required = row.required;
	                var data = {};
					data.structName = structName;
					data.structAlias = structAlias;
					data.type = type;
					data.length = length;
					data.metadataId = metadataId;
					data.scale = scale;
					data._parentId = parentId;
					data.required = required;
					data.headId = "${param.headId}";
					if(isupdate){
						data.id = row.id;					
					}
					isupdate = false;
					sysManager.addIDA(data,function(result){
						if(result){
							$('#tg').treegrid('reload');	
						}else{
							alert("保存失败");
						}

                    });
	                
	             }
			}
		}]
		function onContextMenu(e,row){
		
			e.preventDefault();
			$(this).treegrid('select', row.id);
			$('#mm').menu('show',{
				left: e.pageX,
				top: e.pageY
			});
		}
		
		function removeIt(){
			var node = $('#tg').treegrid('getSelected');
			if (node){
				$('#tg').treegrid('remove', node.id);
				sysManager.removeIDA(node.id,function(result){
					if(result){
						$('#tg').treegrid('reload');	
					}else{
						alert("删除失败");
					}
	
	             });
			}
		}
		function editIt(){
			
				
				
				var row = $('#tg').treegrid('getSelected');
				
				if (row){
					editingId = row.id
					$('#tg').treegrid('beginEdit', editingId);
					isupdate = true;
					/*$("#upbtn"+editingId).hide();
					$("#downbtn"+editingId).hide();
					
					$("#cancelbtn"+editingId).show();
					$("#okbtn"+editingId).show();*/
				}
		}
		var idIndex=999;
		function append(){
		
			idIndex++;
			
			var node = $('#tg').treegrid('getSelected');
			$('#tg').treegrid('append',{
				parent: node.id,
				data:[{id:idIndex,structName:""}]
				
			})
			editingId = idIndex;
			parentId = node.id;
			
			$('#tg').treegrid('reloadFooter');
			$('#tg').treegrid('beginEdit', idIndex);
		}
		function save(id){
			
			if (editingId != undefined&&editingId==id){
				var t = $('#tg');
				t.treegrid('endEdit', editingId);
				editingId = undefined;
				var persons = 0;
				var rows = t.treegrid('getChildren');
				for(var i=0; i<rows.length; i++){
					var p = parseInt(rows[i].headId);
					alert(p);
				}
				var frow = t.treegrid('getFooterRows')[0];
				frow.persons = persons;
				t.treegrid('reloadFooter');
			}
		}
		function cancel(){
			if (editingId != undefined){
				$('#tg').treegrid('cancelEdit', editingId);
				editingId = undefined;
			}
		}
		function formatConsole(value){
	    	
		    	
				var s = '<a iconcls="icon-up" style="margin-top:5px;margin-bottom:5px;margin-left:5px;" class="easyui-linkbutton l-btn l-btn-small" href="#" group="" id="upbtn'+value+'"><span class="l-btn-left l-btn-icon-left"><span class="l-btn-text">上移</span><span class="l-btn-icon icon-up">&nbsp;</span></span></a>';
				s += '<a iconcls="icon-down" style="margin-top:5px;margin-bottom:5px;margin-left:5px;" class="easyui-linkbutton l-btn l-btn-small" href="#" group=""  id="downbtn'+value+'"><span class="l-btn-left l-btn-icon-left"><span class="l-btn-text">下移</span><span class="l-btn-icon icon-down">&nbsp;</span></span></a>';
				 s += '<a iconcls="icon-close" onclick="cancel()" style="display:none;margin-top:5px;margin-bottom:5px;margin-left:5px;" class="easyui-linkbutton l-btn l-btn-small" href="#" group="" id="cancelbtn'+value+'"><span class="l-btn-left l-btn-icon-left"><span class="l-btn-text">取消</span><span class="l-btn-icon icon-cancel">&nbsp;</span></span></a>';
				 s += '<a iconcls="icon-ok" onclick="save('+value+')" style="display:none;margin-top:5px;margin-bottom:5px;margin-left:5px;" class="easyui-linkbutton l-btn l-btn-small" href="#" group="" id="okbtn'+value+'"><span class="l-btn-left l-btn-icon-left"><span class="l-btn-text">确认</span><span class="l-btn-icon icon-ok">&nbsp;</span></span></a>';
		    	return s;
	    	
		}
		
		$(function(){
			
		}) 
		
		function loadData(){
			var headId = "${param.headId}"
			alert(headId);
			$('#tg').treegrid({
				url:'/ida/getHeads/'+headId,
				onAfterEdit:function(row,changes){
					//alert(row.name);
				}
			});
		}
</script>
	</head>

	<body onload="loadData();">
		<div id="mm" class="easyui-menu" style="width: 120px;">
			<div onclick="append()" data-options="iconCls:'icon-add'">
				新增
			</div>
			<div onclick="editIt()" data-options="iconCls:'icon-edit'">
				编辑
			</div>
			<div onclick="removeIt()" data-options="iconCls:'icon-remove'">
				删除
			</div>

		</div>
		<table title="报文头管理" class="easyui-treegrid" id="tg"
			style="height: 440px; width: auto;"
			data-options="
				iconCls: 'icon-folder',
				rownumbers: false,
				animate: true,
				collapsible: true,
				fitColumns: true,
				method: 'get',
				idField: 'id',
				treeField: 'structName',
                toolbar:toolbar,
                onContextMenu:onContextMenu
               
			">
			<thead>
				<tr>
					<th
						data-options="field:'structName',width:160,align:'left',editor:'text'">
						字段名称
					</th>
					<th
						data-options="field:'structAlias',width:90,align:'left',editor:'text'">
						字段别名
					</th>
					<th data-options="field:'type',width:80,editor:'text'">
						类型
					</th>
					<th data-options="field:'length',width:80,editor:'text'">
						长度
					</th>
					<th data-options="field:'metadataId',width:100,editor:'text'">
						元数据ID
					</th>
					<th data-options="field:'scale',width:100,editor:'text'">
						精度
					</th>
					<th data-options="field:'required',width:100,editor:'text'">
						是否必须
					</th>
					
				</tr>

			</thead>
		</table>
	</body>
</html>
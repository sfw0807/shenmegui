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
		<script type="text/javascript" src="/js/sysadmin/interfaceManager.js"></script>
		<script type="text/javascript">
	var editingId;
	var parentId;
	var isupdate;
	var addArray = new Array();
	var editArray = new Array();
	var parentIdAry = new Array();
	var toolbar = [{
			text:'刪除',
			iconCls:'icon-remove',
			handler:function(){
				if(!confirm("确定要删除选中的记录吗？")){
					return;
				}
				var nodes = $('#tg').treegrid('getSelections');
				if (nodes){
					var delAry = new Array();
					for(var i=0;i<nodes.length;i++){
						if(nodes[i].structName!='request' && nodes[i].structName!='response'){
							delAry.push(nodes[i].id);
						}
					}
					sysManager.removeIDA(delAry,function(result){
						if(result){
							//array.
							//$('#tg').treegrid('reload');	
							for(var j=0;j<delAry.length;j++){
								$('#tg').treegrid('remove', delAry[j]);
							}
						}else{
							alert("删除失败");
						}
		
		             });
				}
				
			}
		},{
			text:'保存',
			iconCls:'icon-save',
			handler:function(){
				 $('#tg').treegrid('endEdit', editingId);
				 var row = $('#tg').treegrid('find',editingId);
				 
				 var reqAry = [];
				 for(var i=0;i<addArray.length;i++){
				 	
				 	$('#tg').treegrid('endEdit', addArray[i]);
				 	var row = $('#tg').treegrid('find',addArray[i]);
				 	if(row){
				 		var structName = row.structName;
		             	var structAlias = row.structAlias;
		                var type = row.type;
		                var length = row.length;
		                var metadataId = row.metadataId;
		                var scale = row.scale;
		                var required = row.required;
		                var seq = row.seq;
		                var data = {};
						data.structName = structName;
						data.structAlias = structAlias;
						data.type = type;
						data.length = length;
						data.metadataId = metadataId;
						data.scale = scale;
						data._parentId = parentIdAry[i];
						data.required = required;
						data.headId = "${param.headId}";
						data.seq = seq;
						reqAry.push(data);
				 	}
				 }
				 
				 for(var i=0;i<editArray.length;i++){
				 	
				 	$('#tg').treegrid('endEdit', editArray[i]);
				 	var row = $('#tg').treegrid('find',editArray[i]);
				 	if(row){
				 		var structName = row.structName;
		             	var structAlias = row.structAlias;
		                var type = row.type;
		                var length = row.length;
		                var metadataId = row.metadataId;
		                var scale = row.scale;
		                var required = row.required;
		                var seq = row.seq;
		                var data = {};
						data.structName = structName;
						data.structAlias = structAlias;
						data.type = type;
						data.length = length;
						data.metadataId = metadataId;
						data.scale = scale;
						data.required = required;
						data.headId = "${param.headId}";
						data.seq = seq;
						data.id = row.id;
						reqAry.push(data);
				 	}
				 }
				 
				 sysManager.addIDA(reqAry,function(result){
						if(result){
							$('#tg').treegrid('reload');	
						}else{
							alert("保存失败");
						}

                 });
	           /*  if (row){
	             	var structName = row.structName;
	             	var structAlias = row.structAlias;
	                var type = row.type;
	                var length = row.length;
	                var metadataId = row.metadataId;
	                var scale = row.scale;
	                var required = row.required;
	                var seq = row.seq;
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
					data.seq = seq;
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
	                
	             }*/
			}
		}]
		function onContextMenu(e,row){
		
			e.preventDefault();
			
			$(this).treegrid('unselectAll');
			
			$(this).treegrid('select', row.id);
			
			if(row.structName=='root'){
   				$('#tg').treegrid('unselect',row.id);
   				return;
   			}
   			
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
						//$('#tg').treegrid('reload');	
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
					$("#upbtn"+editingId).hide();
					$("#downbtn"+editingId).hide();
					editArray.push(editingId);
					/*$("#cancelbtn"+editingId).show();
					$("#okbtn"+editingId).show();*/
				}
		}
		var idIndex=999;
		function append(){
		
			idIndex++;
			
			var node = $('#tg').treegrid('getSelected');
			var nodes = $('#tg').treegrid("getChildren",node.id);
			var seq = 0;
			if(nodes.length>0){
				seq = nodes[nodes.length-1].seq +1;
			}
			$('#tg').treegrid('append',{
				parent: node.id,
				data:[{id:idIndex,structName:"",seq:seq}]
				
			})
			editingId = idIndex;
			parentId = node.id;
			
			$('#tg').treegrid('reloadFooter');
			$('#tg').treegrid('beginEdit', idIndex);
			addArray.push(idIndex);
			parentIdAry.push(parentId);
			
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
				if(value){
	    			var row = $('#tg').treegrid("find",value);
	    			if(row.structName=='root' || row.structName=='request' || row.structName=='response'){
	    				return;
	    			}
	    		}
	    		
				var s = '<a iconcls="icon-up" onclick="moveUp(this,'+"'"+value+"'"+')" style="margin-top:5px;margin-bottom:5px;margin-left:5px;" class="easyui-linkbutton l-btn l-btn-small" href="#" group="" id="upbtn'+value+'"><span class="l-btn-left l-btn-icon-left"><span class="l-btn-text">上移</span><span class="l-btn-icon icon-up">&nbsp;</span></span></a>';
				s += '<a iconcls="icon-down" onclick="moveDown(this,'+"'"+value+"'"+')" style="margin-top:5px;margin-bottom:5px;margin-left:5px;" class="easyui-linkbutton l-btn l-btn-small" href="#" group=""  id="downbtn'+value+'"><span class="l-btn-left l-btn-icon-left"><span class="l-btn-text">下移</span><span class="l-btn-icon icon-down">&nbsp;</span></span></a>';
		    	
		    	return s;
		}
		
		function moveUp(obj,value) {
			
			var objParentTR = $(obj).parent().parent().parent(); 
			var prevTR = objParentTR.prev(); 
			if (prevTR.length > 0) { 
				
				
				var prevRowId = prevTR.attr("node-id");
				var curRow = $('#tg').treegrid('find',value);
				var prevRow = $('#tg').treegrid('find',prevRowId);
				
				interfaceManager.modifySEQ(value,prevRow.seq,prevRowId,curRow.seq);
				loadData();//$('#tg').treegrid('reload');	
			} 
		} 
		function moveDown(obj,value) { 
			var objParentTR = $(obj).parent().parent().parent(); 
			var nextTR = objParentTR.next(); 
			
			//alert(value+"==============="+cell);
			if (nextTR.length > 0) { 
				
			
				var nextRowId = nextTR.attr("node-id");
				
				var curRow = $('#tg').treegrid('find',value);
				var nextRow = $('#tg').treegrid('find',nextRowId);
			
				interfaceManager.modifySEQ(value,nextRow.seq,nextRowId,curRow.seq);
				loadData();//$('#tg').treegrid('reload');	
			} 
			
			
		} 
		
		
		function loadData(){
			var headId = "${param.headId}"
			$('#tg').treegrid({
				url:'/ida/getHeads/'+headId,
				onAfterEdit:function(row,changes){
					//alert(row.name);
				}
			});
		}
		
		function onClickRow(row){
			if(row.structName=='root' || row.structName=='request' || row.structName=='response'){
   				$('#tg').treegrid('unselect',row.id);
   			}
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
                onContextMenu:onContextMenu,
                singleSelect:false,
                onClickRow:onClickRow
               
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
					<th data-options="field:'seq',width:50,editor:'text',hidden:true">
						排序
					</th>
					<th data-options="field:'id',width:140,formatter:formatConsole">
						操作
					</th>
					
				</tr>

			</thead>
		</table>
	</body>
</html>
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
						data.interfaceId = "${param.interfaceId}";
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
						//data._parentId = parentId;
						data.required = required;
						data.headId = "${param.headId}";
						data.interfaceId = "${param.interfaceId}";
						data.seq = seq;
						data.id = row.id;
						reqAry.push(data);
				 	}
				 }
				 
				 sysManager.addIDA(reqAry,function(result){
						if(result){
							alert("保存成功");
							$('#tg').treegrid('reload');	
						}else{
							alert("保存失败");
						}

                 });
			}
		},{
				text:'上移',
				iconCls:'icon-up',
				handler:function(){
					var row = $('#tg').treegrid("getSelected");
					if(!row){
						alert("请选择要移动的行");
					}
					//获取选中行的父节点
					var parent = $('#tg').treegrid("getParent",row.id);
					//再获取父节点下的子节点，然后循环，查找出选中行的上一行
					var childs = $('#tg').treegrid("getChildren",parent.id);

					if(childs){
						if(childs.length==1){
							return;
						}
						var prevRowId = 0;
						for(var i=0;i<childs.length;i++){

							if(childs[i].id == row.id){
								if(i==0){
									return
								}
								prevRowId = childs[i-1].id;
								break;
							}
						}

						var prevrow = $('#tg').treegrid("find",prevRowId);
						$('#tg').treegrid('remove',row.id);

						$('#tg').treegrid('insert', {
							before: prevRowId,
							data: {
								structName:row.structName,
								structAlias:row.structAlias,
								type:row.type,
								length:row.length,
								metadataId:row.metadataId,
								scale:row.scale,
								required:row.required,
								seq:prevrow.seq,
								id:row.id
							}
						});

						$('#tg').treegrid('remove',prevRowId);
						$('#tg').treegrid('insert', {
							after: row.id,
							data: {
								structName:prevrow.structName,
								structAlias:prevrow.structAlias,
								type:prevrow.type,
								length:prevrow.length,
								metadataId:prevrow.metadataId,
								scale:prevrow.scale,
								required:prevrow.required,
								seq:row.seq,
								id:prevrow.id
							}
						});

						if(jQuery.inArray(row.id, editArray)==-1){
							editArray.push(row.id);
						}
						if(jQuery.inArray(prevRowId, editArray)==-1){
							editArray.push(prevRowId);
						}

					}
				}

  		 },{
				text:'下移',
				iconCls:'icon-down',
				handler:function(){
					var row = $('#tg').treegrid("getSelected");
					if(!row){
						alert("请选择要移动的行");
					}
					//获取选中行的父节点
					var parent = $('#tg').treegrid("getParent",row.id);
					//再获取父节点下的子节点，然后循环，查找出选中行的上一行
					var childs = $('#tg').treegrid("getChildren",parent.id);

					if(childs){
						if(childs.length==1){
							return;
						}
						var nextRowId = 0;
						for(var i=0;i<childs.length;i++){
							if(childs[i].id == row.id){
								if(childs.length == i+1){
									return;
								}
								nextRowId = childs[i+1].id;
								break;
							}
						}

						var nextrow = $('#tg').treegrid("find",nextRowId);
						$('#tg').treegrid('remove',row.id);

						$('#tg').treegrid('insert', {
							after: nextRowId,
							data: {
								structName:row.structName,
								structAlias:row.structAlias,
								type:row.type,
								length:row.length,
								metadataId:row.metadataId,
								scale:row.scale,
								required:row.required,
								seq:nextrow.seq,
								id:row.id
							}
						});

						$('#tg').treegrid('remove',nextRowId);
						$('#tg').treegrid('insert', {
							before: row.id,
							data: {
								structName:nextrow.structName,
								structAlias:nextrow.structAlias,
								type:nextrow.type,
								length:nextrow.length,
								metadataId:nextrow.metadataId,
								scale:nextrow.scale,
								required:nextrow.required,
								seq:row.seq,
								id:nextrow.id
							}
						});

						if(jQuery.inArray(row.id, editArray)==-1){
							editArray.push(row.id);
						}
						if(jQuery.inArray(nextRowId, editArray)==-1){
							editArray.push(nextRowId);
						}

					}
				}

      	  }
        ]
		
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
						//array.
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
					if(jQuery.inArray(editingId, editArray)==-1){
						editArray.push(editingId);
					}
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
		
		function loadData(){
			var interfaceId = "${param.interfaceId}"
			$('#tg').treegrid({
				url:'/ida/getInterfaces/'+interfaceId,
				singleSelect:true,//是否单选
				onAfterEdit:function(row,changes){
					//alert(row.name);
				}
			});

			  $('#interfacetg').datagrid({

					iconCls:'icon-edit',//图标
					width: 'auto',
					height: '80px',
					collapsible: false,
					fitColumns:true,
					method:'get',
					url:'/interface/getInterById/'+interfaceId,
					singleSelect:false,//是否单选

					rownumbers:false

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
		<table id="interfacetg" style="height: 370px; width: auto;">
        			<thead>
        				<tr>
        					<th data-options="field:'ecode',width:'10%'">
        						交易码
        					</th>
        					<th data-options="field:'interfaceName',width:'18%'">
        						交易名称
        					</th>

        					<th data-options="field:'status',width:'8%',align:'right'">
        						交易状态
        					</th>
        					<th data-options="field:'headName',width:'15%'">
								报文头
							</th>
        					<th data-options="field:'version',width:'10%'">
        						版本号
        					</th>
        					<th data-options="field:'desc',align:'right',width:'20%'">
								功能描述
							</th>
        					<th data-options="field:'optDate',width:'10%',align:'center'">
        						更新时间
        					</th>
        					<th data-options="field:'optUser',width:'10%'">
        						更新用户
        					</th>

        				</tr>
        			</thead>
        		</table>
        		<div id="w" class="easyui-window" title=""
        			data-options="modal:true,closed:true,iconCls:'icon-add'"
        			style="width: 500px; height: 200px; padding: 10px;">

        		</div>

		<div id="mm" class="easyui-menu" style="width: 120px;">
			<div onclick="append()" data-options="iconCls:'icon-add'">
				新增
			</div>
			<div onclick="editIt()" data-options="iconCls:'icon-edit'">
				编辑
			</div>

		</div>
		<table title="接口定义信息" id="tg"
			style="height: 440px; width: auto;"
			data-options="
				iconCls:'icon-edit',
				rownumbers: false,
				animate: true,
				collapsible: true,
				fitColumns: true,
				method: 'get',
				idField: 'id',
				treeField: 'structName',
                toolbar:toolbar,
                onContextMenu:onContextMenu,
                singleSelect:true,
                onClickRow:onClickRow
               
			">
			<thead>
				<tr>
					<th
						data-options="field:'structName',width:120,align:'left',editor:'text'">
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
					<th data-options="field:'required',width:50,editor:'text'">
						是否必须
					</th>
					<th data-options="field:'seq',width:50">
						排序
					</th>

				</tr>

			</thead>
		</table>
	</body>
</html>
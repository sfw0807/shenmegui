<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>列表页</title>
		<link rel="stylesheet" type="text/css"
			href="/resources/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css"
			href="/resources/themes/icon.css">
		<link href="/resources/css/ui.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="/resources/js/jquery.min.js"></script>
		<script type="text/javascript"
			src="/resources/js/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="/resources/js/ui.js"></script>
		<script type="text/javascript" src="/js/sysadmin/interfaceManager.js"></script>
	</head>

	<body>
		<fieldset>
			<legend>
				条件搜索
			</legend>
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th>
						交易码
					</th>
					<td>
						<input class="easyui-textbox" type="text" id="ecode">
					</td>

					<th>
						交易名称
					</th>
					<td>
						<input class="easyui-textbox" type="text" id="interfaceName">
					</td>
					<th>
						接口功能描述
					</th>
					<td>
						<input class="easyui-textbox" type="text" name="name">
					</td>
				</tr>
				<tr>
					<th>
						交易状态
					</th>
					<td>
						<select class="easyui-combobox" panelHeight="auto"
							style="width: 155px">
							<option value="java">
								节点1
							</option>
							<option value="c">
								节点2
							</option>
							<option value="basic">
								节点3
							</option>
							<option value="perl">
								节点4
							</option>
						</select>
					</td>
					<th>
						报文头
					</th>
					<td>
						<select class="easyui-combobox" panelHeight="auto"
							style="width: 155px">
							<option value="java">
								节点1
							</option>
							<option value="c">
								节点2
							</option>
							<option value="basic">
								节点3
							</option>
							<option value="perl">
								节点4
							</option>
						</select>
					</td>
					<th>
						通讯协议
					</th>
					<td>
						<select class="easyui-combobox" panelHeight="auto"
							style="width: 155px">
							<option value="java">
								节点1
							</option>
							<option value="c">
								节点2
							</option>
							<option value="basic">
								节点3
							</option>
							<option value="perl">
								节点4
							</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
					<td align="right">
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchData();">搜索</a>
					</td>
				</tr>
			</table>


		</fieldset>
		<table class="easyui-datagrid" id="tg" style="height: 370px; width: auto;" data-options="pageSize:2">
			<thead>
				<tr>
					<th data-options="field:'ecode',width:'15%'">
						交易码
					</th>
					<th data-options="field:'interfaceName',width:'15%'">
						交易名称
					</th>
					<th data-options="field:'desc',align:'right',width:'20%'">
						功能描述
					</th>
					<th data-options="field:'status',width:'12%',align:'right'">
						交易状态
					</th>
					<th data-options="field:'version',width:'12%'">
						版本号
					</th>
					<th data-options="field:'optDate',width:'15%',align:'center'">
						更新时间
					</th>
					<th data-options="field:'optUser'">
						更新用户
					</th>
					<th data-options="field:'interfaceId',hidden:true">
						
					</th>
				</tr>
			</thead>
		</table>
		<div id="w" class="easyui-window" title=""
			data-options="modal:true,closed:true,iconCls:'icon-add'"
			style="width: 500px; height: 200px; padding: 10px;">

		</div>
		<script type="text/javascript">
		 $(document).ready(function(){ 
			  $('#tg').datagrid({ 
	        title:'基本信息维护', 
	        iconCls:'icon-edit',//图标 
	        width: 'auto', 
	        height: '380px', 
	        method:'post',
	        url:'/interface/getInterface/${param.systemId }',
	        singleSelect:true,//是否单选 
	        pagination:true,//分页控件 
	        pageSize: 2,//每页显示的记录条数，默认为10 
		    pageList: [2,5,10,15,20],//可以设置每页记录条数的列表 
	        rownumbers:false,//行号 
	        toolbar: [{
					text:'新增',
					iconCls:'icon-add',
					handler:function(){
					    
							interfaceManager.append("${param.systemId}");
						}
					},{
						text:'修改',
						iconCls:'icon-edit',
						handler:function(){
							var row = $("#tg").treegrid("getSelected");
							if(row){
								interfaceManager.edit(row.interfaceId,"${param.systemId}");
							}else{
								alert("请选择要修改的行");
							}
						}
					},{
						text:'删除',
						iconCls:'icon-remove',
						handler:function(){
							var row = $("#tg").treegrid("getSelected");
							if(row){
								interfaceManager.remove(row.interfaceId,row.interfaceName);
							}else{
								alert("请选择要删除的行");
							}
						}
					},'-',
					{
						text:'导入',
						iconCls:'icon-cfp',
						handler:function(){alert('导入')}
					},
					{
						text:'导出',
						iconCls:'icon-save',
						handler:function(){alert('导出')}
					},
					{
						text:'检出',
						iconCls:'icon-qxfp',
						handler:function(){
							
						}
					},
					{
						text:'提交任务',
						iconCls:'icon-qxfp',
						handler:function(){
							
					}
				}]
	   		});  
			
			var p = $('#tg').treegrid('getPager');  
			
			$(p).pagination({ 
	       		
		        beforePageText: '第',//页数文本框前显示的汉字 
		        afterPageText: '页    共 {pages} 页', 
		        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	    	});  
		 }) 
		 
		 function searchData(){
		 
		 	var ecode = $("#ecode").val();
		 	var interfaceName = $("#interfaceName").val();
		 	
		 	 var queryParams = $('#tg').datagrid('options').queryParams;  
             queryParams.ecode = ecode;
             queryParams.interfaceName = interfaceName; 
             $('#tg').datagrid('options').queryParams = queryParams;//传递值  
             $("#tg").datagrid('reload');//重新加载table  
		 }
		</script>

	</body>
</html>
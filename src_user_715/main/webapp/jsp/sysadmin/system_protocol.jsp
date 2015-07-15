<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>系統管理</title>
		<link rel="stylesheet" type="text/css"
			href="/resources/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css"
			href="/resources/themes/icon.css">
		<link href="/resources/css/ui.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="/resources/js/jquery.min.js"></script>
		<script type="text/javascript"
			src="/resources/js/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="/resources/js/ui.js"></script>
		<script type="text/javascript" src="/js/sysadmin/sysManager.js"></script>
	</head>

	<body>
		<fieldset>
			<legend>
				条件搜索
			</legend>
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th>
						协议名称
					</th>
					<td>
						<input class="easyui-textbox" type="text" id="protocolName">
					</td>

					<th>
						消息类型
					</th>
					<td>
						<input class="easyui-textbox" type="text" id="msgType">
					</td>
					<th>
						协议编码
					</th>
					<td>
						<input class="easyui-textbox" type="text" id="encoding">
					</td>
					<th>
						备注
					</th>
					<td>
						<input class="easyui-textbox" type="text" id="remark">
					</td>
					<td align="right">
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchData();">搜索</a>
					</td>
				</tr>
			</table>


		</fieldset>
		<table id="tg" style="height: 300px; width: auto;" data-options="pageSize:5">
			<thead>
				<tr>
					<th data-options="field:'protocolName',width:'14%'">
						协议名称
					</th>
					<th data-options="field:'msgType',width:'12%'">
						消息类型
					</th>
					<th data-options="field:'encoding',width:'12%'">
						协议编码
					</th>
					<th data-options="field:'timeout',width:'8%',align:'center'">
                    	超时时间
                    </th>
                    <th data-options="field:'errorCode',width:'10%'">
                    	错误代码
                    </th>
					<th data-options="field:'succCode',align:'right',width:'10%'">
						成功代码
					</th>
					<th data-options="field:'remark',align:'right',width:'15%'">
						备注
					</th>
					<th data-options="field:'generatorId',width:'20%',align:'right'">
						生成类
					</th>
					<th data-options="field:'protocolId',align:'right',hidden:true">
						protocolId
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
	        title:'协议基本信息维护',
	        iconCls:'icon-edit',//图标 
	        width: 'auto', 
	        height: '320px',
	        collapsible: true,
	        method:'post',
            url:'/protocol/getAll',
	        singleSelect:true,//是否单选 
	        pagination:true,//分页控件 
	        pageSize: 5,//每页显示的记录条数，默认为10
		    pageList: [5,10,15,20],//可以设置每页记录条数的列表
	        rownumbers:false,//行号 
	        toolbar: [{
					text:'新增',
					iconCls:'icon-add',
					handler:function(){
					    	sysManager.addProtocolPage();
						}
					},{
						text:'修改',
						iconCls:'icon-edit',
						handler:function(){
							var node = $('#tg').datagrid("getSelected");
							if(node){
								uiinit.win({
									w:500,
									iconCls:'icon-add',
									title:"编辑协议",
									url : "/protocol/edit/"+node.protocolId
								});
							}else{
								alert("请选择要修改的行");
							}
						}
					},{
						text:'删除',
						iconCls:'icon-remove',
						handler:function(){
							var node = $('#tg').datagrid("getSelected");
							if(node){
								if(!confirm("确定要删除选中的记录吗？")){
									return;
								}
								 $.ajax({
										type: "GET",
										contentType: "application/json; charset=utf-8",
										url: "/protocol/delete/"+node.protocolId,
										dataType: "json",
										success: function(result) {
											$('#tg').datagrid("reload");
										}
									});
							}else{
								alert("请选择要删除的行");
							}
						}

				}]
	   		});  
			
			var p = $('#tg').datagrid('getPager');
			
			$(p).pagination({ 
	       		
		        beforePageText: '第',//页数文本框前显示的汉字 
		        afterPageText: '页    共 {pages} 页', 
		        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	    	});  
		 }) 
		 
		 function searchData(){
		 
		 	var protocolName = $("#protocolName").val();
		 	var encoding = $("#encoding").val();
		 	var msgType = $("#msgType").val();
		 	var remark = $("#remark").val();
		 	
		 	 var queryParams = $('#tg').datagrid('options').queryParams;  
             queryParams.protocolName = protocolName;
             queryParams.encoding = encoding;
             queryParams.msgType = msgType;
             queryParams.remark = remark;
             $('#tg').datagrid('options').queryParams = queryParams;//传递值  
             $("#tg").datagrid('reload');//重新加载table  
		 }
		</script>

	</body>
</html>
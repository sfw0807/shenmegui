<%@ page contentType="text/html; charset=utf-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>列表页</title>
<link rel="stylesheet" type="text/css" href="/resources/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/resources/themes/icon.css">
<link href="/resources/css/css.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="/resources/js/jquery.min.js"></script> 
<script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/jsp/version/version.js"></script>
<script type="text/javascript">
		var toolbar = [
		{
			text:'撤销',
			iconCls:'icon-cancel',
			handler:function(){alert('移出')}
		}];
		
		//按照关键字查询版本历史
		function verionHisSearch(){
			var data = $("#keyValue").textbox("getValue");
			$.ajax({
				type : "get",
				async : false,
				url : "/version/hisVersionList",
				dataType : "json",
				data : {"keyValue" : data},
				success : function(data) {
					$("#operationList").datagrid("loadData", data.rows);
				}
			});
		}
		
		//操作按钮
		function formatConsole(value){
				var s = '<a iconcls="icon-search" onclick="detailPage()" style="margin-top:1px;margin-bottom:1px;margin-left:5px;" class="easyui-linkbutton l-btn l-btn-small" href="javascript:void(0)" group="" id="cancelbtn'+value+'"><span class="l-btn-left l-btn-icon-left"><span class="l-btn-text">数据详情</span><span class="l-btn-icon icon-search">&nbsp;</span></span></a>';
		    	return s;
	    	
		}
		
			function detailPage() {
					 var row = $("#operationList").datagrid("getSelected");
					//获取行数据中的targetType和targetId
					if(row){
						if(row.targetType == "1"){//对应场景类型
							//查询operationHis表
							$.ajax({
								type : "get",
								async : false,
								url : "/operationHis/getByAutoId",
								dataType : "json",
								data : {"autoId" : row.targetId},
								success : function(data) {
									var urlPath = "/operation/detailPage?serviceId="+data.serviceId+"&operationId="+data.operationId;
									$('#versionDlg').dialog({
										title : 'SDAHis',
										width : 700,
										closed : false,
										cache : false,
										href : urlPath,
										modal : true
									});
								}
							});
						}
					}

				}
		
	</script> 
</head>

<body>
<fieldset>
 <legend>条件过滤</legend>
 <table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th>关键字</th>
    <td>
      <input id="keyValue" class="easyui-textbox" type="text" name="name" ></td>
   
    <td><a href="#" onclick="verionHisSearch()" class="easyui-linkbutton" plain="true" iconcls="icon-search">过滤</a></td>

  </tr>
</table>

</fieldset>
<table id="operationList" class="easyui-datagrid" title="版本历史" 
			data-options="
				rownumbers:true,
				singleSelect:true,
				url:'/version/hisVersionList',
				method:'get',
				toolbar:toolbar,
				pagination:true,
				pageSize:10" style="height:365px; width:auto;">
  <thead>
    <tr>
          <th data-options="field:'autoId',checkbox:true"> </th>
      <th data-options="field:'code'">版本号 </th>
      <th data-options="field:'type'" formatter="versionHis.type">是否基线版本 </th>
      <th data-options="field:'baseLineNum'">基线版本号 </th>
      <th data-options="field:'optType'" formatter="versionHis.optType0">修订类型 </th>
      <th data-options="field:'versionDesc'">发布说明 </th>
      <th data-options="field:'optDate'">发布时间 </th>
      <th data-options="field:'optUser'">发布人 </th>
      <th data-options="field:'id',width:120,formatter:formatConsole">操作</th>
    </tr>
  </thead>
</table>
 <div id="versionDlg" class="easyui-dialog"
		style="width:400px;height:auto;padding:10px 20px" closed="true"
		resizable="true"></div>
</body>
</html>
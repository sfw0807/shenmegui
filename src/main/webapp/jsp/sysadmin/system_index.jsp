<%@ page contentType="text/html; charset=utf-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">


<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" type="text/css" href="/resources/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="/resources/themes/icon.css">
    <link href="/resources/css/ui.css" rel="stylesheet" type="text/css">

</head>
<body class="easyui-layout" onload="load()">
<div data-options="region:'center',collapsible:true,border:false,tabHeight:39"
     class="easyui-tabs " id="sysContentTabs">
      <div title="系统管理" style="padding:0px">
      		<iframe scrolling="auto" frameborder="0"  src="system_manager.jsp" style="width:100%;height:100%;"></iframe>
      </div>
</div>
</body>
<script type="text/javascript" src="/resources/js/jquery.min.js"></script>
<script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/resources/js/ui.js"></script>

<script type="text/javascript">
	 function load(){
		/* if ($('#sysContentTabs').tabs('exists', "协议管理")){
			$('#sysContentTabs').tabs('select', "协议管理");
		 } else {
			var content = '<iframe scrolling="auto" frameborder="0"  src="system_protocol.jsp" style="width:100%;height:100%;"></iframe>';
			$('#sysContentTabs').tabs('add',{
				title:"协议管理",
				content:content,
				closable:true
			});
		 }
		 $('#sysContentTabs').tabs({
         	tools:[{
         		iconCls:'icon-add',
         		handler:function(){
         			alert('add')
         		}
         	},{
         		iconCls:'icon-save',
         		handler:function(){
         			alert('save')
         		}
         	}]
         });
			*/
	 }

</script>
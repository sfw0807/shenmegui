<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>服务页面</title>
    
<link rel="stylesheet" type="text/css" href="/resources/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/resources/themes/icon.css">
<link href="/resources/css/ui.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="/resources/js/jquery.min.js"></script> 
<script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/resources/js/ui.js"></script>
  </head>
  
 <body  style="margin:0px;">

<div class="easyui-tabs" id="subtab" name="subtab" data-options="tools:'#tab-tools'"  >
<div title="服务基本信息" style="padding:0px;">
    <iframe id="serviceInfo" name="serviceInfo" scrolling="auto" frameborder="0"  src="/service/serviceGrid?serviceId=<%=request.getParameter("serviceId") %>"  style="width:100%;height:100%;"></iframe>
</div>
<div title="服务场景" style="padding:0px;">
</div>
<div title="服务接口SDO" style="padding:0px;">
</div>
<div title="服务SLA" style="padding:0px;">
</div>
<div title="服务OLA" style="padding:0px;">
</div>
<div title="服务接口隐射" style="padding:0px;">
</div>
</div>

<script type="text/javascript">
function closeTab(title){
	 $('#subtab').tabs('close', title);
}
	var k = 0;
	var j = 0;
	var m =0;
	var n =0;
	var q = 0;
	$('#subtab').tabs({
    border:false,
	border:false,
	width:"auto",
	height:$("body").height(),
    onSelect:function(title,index){
    	 if(index==1 && k==0 ){
    	 	k++;
    	 	var urlPath = "/operation/addPage/<%=request.getParameter("serviceId") %>";
			var currTab =	$('#subtab').tabs('getSelected');
			$('#subtab').tabs('update', {
						  tab : currTab,
						  options : {
						   content : ' <iframe scrolling="auto" frameborder="0"  src="' + urlPath +'"  style="width:100%;height:100%;"></iframe>'
						  }
						 });
    	 }
		 if(index==2){
		 	var opId = serviceInfo.getSelected();
			if(opId != null){
				 		var urlPath = "/sda/sdaPage?serviceId=<%=request.getParameter("serviceId") %>&operationId="+opId;
				 		var currTab =	$('#subtab').tabs('getSelected');
						$('#subtab').tabs('update', {
						  tab : currTab,
						  options : {
						   content : ' <iframe scrolling="auto" frameborder="0"  src="' + urlPath +'"  style="width:100%;height:100%;"></iframe>'
						  }
						 });	
				 	}else{
				 		alert("请选则一个场景！");
				 		$('#subtab').tabs('select','服务基本信息');
				 	}
		}
		
		 if(index==3){
			var opId = serviceInfo.getSelected();
			if(opId != null){
				 		var urlPath = "/sla/slaPage?serviceId=<%=request.getParameter("serviceId") %>&operationId="+opId;
				 		var currTab =	$('#subtab').tabs('getSelected');
						$('#subtab').tabs('update', {
						  tab : currTab,
						  options : {
						   content : ' <iframe scrolling="auto" frameborder="0"  src="' + urlPath +'"  style="width:100%;height:100%;"></iframe>'
						  }
						 });	
				 	}else{
				 		alert("请选则一个场景！");
				 		$('#subtab').tabs('select','服务基本信息');
				 	}
		}
		 if(index==4){
			var opId = serviceInfo.getSelected();
			if(opId != null){
				 		var urlPath = "/ola/olaPage?serviceId=<%=request.getParameter("serviceId") %>&operationId="+opId;
				 		var currTab =	$('#subtab').tabs('getSelected');
						$('#subtab').tabs('update', {
						  tab : currTab,
						  options : {
						   content : ' <iframe scrolling="auto" frameborder="0"  src="' + urlPath +'"  style="width:100%;height:100%;"></iframe>'
						  }
						 });	
				 	}else{
				 		alert("请选则一个场景！");
				 		$('#subtab').tabs('select','服务基本信息');
				 	}		
		}
		 if(index==5&&q==0){
			q++;
			var currTab =	$('#subtab').tabs('getSelected');
			$('#subtab').tabs('update', {
			  tab : currTab,
			  
			  options : {
			   content : ' <iframe scrolling="auto" frameborder="0"  src="/dataTemplate/serviceadmin/grid5.html"  style="width:100%;height:100%;"></iframe>'
			  }
			 });	
		}
		//if(title+' is selected');
    
    }
});
</script>
</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String ctx = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>服务库查询</title>
    <link href="<%=path%>/js/ligerUI/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/js/ligerUI/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
    <script src="<%=path%>/js/ligerUI/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
    <script src="<%=path%>/js/ligerUI/lib/ligerUI/js/plugins/ligerTree.js"></script>
    <script type="text/javascript">
    var manager;
    $(function ()
    {   
            var data = [];
            var serviceManager = {
			    getAllService : function getAllService () {
					$.ajax({
			            url: "<%=path%>/serviceInfo/AllService",
			           	type: "GET",
            			dataType: "json",
			            success: function(result) {
			                for(var i=0;i<result.length;i++){
			            			data.push({ id: result[i].nodeId, pid: result[i].parentNodeId, text: result[i].nodeName+":"+result[i].nodeValue });
			            	}
			               	$("#tree1").ligerTree({  
						            data:data, 
						            delay:[2,3],
						            idFieldName :'id',
						            parentIDFieldName :'pid',
						            onclick : function itemclick(node){
			               				var serviceText = node.data.text;
			               				var serviceId = serviceText.split(":")[0];
			               				if(serviceId.indexOf("S")>=0){
			               					window.showModalDialog("<%=path%>/jsp/operationsByServiceId.jsp",serviceId,"dialogWidth:900px;dialogHeight:600px;resizable:no");
			               				}
						            }
						    });
			                manager = $("#tree1").ligerGetTreeManager();
			                $("#loading").hide();
			            }
			        });
			    }
			};
            serviceManager.getAllService();
	}); 
        function collapseAll()
        {
            manager.collapseAll();
        }
        function expandAll()
        {
            manager.expandAll();
        }
    </script>
</head>
	<a class="l-button" style="width:120px;float:left; margin-left:10px;" onclick="collapseAll();">全部收起</a>
   	<a class="l-button" style="width:120px;float:left; margin-left:10px;" onclick="expandAll();">全部展开</a>
    <div id="loading"><img src="<%=path%>/js/ligerUI/lib/images/loading.gif"></img></div> 
    <ul id="tree1"></ul>
    </div>
</body>
</html>

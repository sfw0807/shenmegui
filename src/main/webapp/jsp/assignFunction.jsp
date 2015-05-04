<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>分配权限</title>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="this is my page">
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />

		<link
			href="<%=path%>/plugins/ligerUI/lib/ligerUI/skins/Aqua/css/ligerui-all.css"
			rel="stylesheet" type="text/css" />
		<link href="<%=path%>/plugins/ligerUI/lib/ligerUI/skins/Gray/css/all.css"
			rel="stylesheet" />		
		<script src="<%=path%>/plugins/ligerUI/lib/jquery/jquery-1.3.2.min.js"
			type="text/javascript">
		</script>
		<script src="<%=path%>/plugins/ligerUI/lib/ligerUI/js/core/base.js"
			type="text/javascript">
		</script>
		<script src="<%=path%>/plugins/ligerUI/lib/ligerUI/js/plugins/ligerTree.js"
			type="text/javascript">
		</script>
<style type="text/css">
.box {
	float: left;
}

.tree {
	width: 230px;
	height: 200px;
	margin: 10px;
	border: 1px solid #ccc;
	overflow: auto;
}

h4 {
	margin: 10px;
}
</style>
<script type="text/javascript">
var href = window.location.href;
var roleId = href.split("=")[1];
var data = [];
var functionManager = {
	getAll: function(param,callback) {
        $.ajax({
            url: '../function/listtreevo/'+param,
            type: 'GET',
            dataType: "json",
            success: function(result) {
                for(var i=0;i<result.length;i++){
                	data.push({ id: result[i].id, pid: result[i].parentId, text: result[i].name,ischecked:result[i].isChecked });
                }
                callback(data);
            }
        });
	},
	assignFunction: function(param){
		$.ajax({
			"type": "POST",
			"contentType": "application/json; charset=utf-8",
			"url": "../role/assign",
			"dataType": "json",
			"data":JSON.stringify(param),
			"success": function(result) {
				if(result){
					alert("分配权限成功");
					window.location.href="roleManager.jsp";
				}else{
					alert("分配权限失败");
				}
			}
		});
	}
}
var treeManager = null;
$(function() {
	var initTree = function initTree(param){
		var tree = $("#tree1").ligerTree( {
			data : param,
			idFieldName : 'id',
			slide : false,
			parentIDFieldName : 'pid'
		});
		treeManager = $("#tree1").ligerGetTreeManager();
		treeManager.collapseAll();
	}
	functionManager.getAll(roleId,initTree);
});
function getChecked(){
	var notes = treeManager.getChecked();
	var text = "";
	var id = [];
	id.push(roleId);
	for (var i = 0; i < notes.length; i++)
	{     
		id.push(notes[i].data.id);
		id.push(notes[i].data.pid);
		//text += notes[i].data.id + ",";
	}
	//console.log("***********"+id);
	functionManager.assignFunction(id);
}
</script>
	</head>
	<body>
		<div id="tabs-0">
			<div class="ui-widget-header"
				style="margin-bottom: 0.5em; padding: 0.2em;">
				<input type="button" value="保存" id="assignFunction"
					style="margin-top: 4px;" onclick="getChecked()"/>
			</div>
		</div>
		<ul id="tree1"></ul>
	</body>
</html>


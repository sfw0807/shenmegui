<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title></title>
    <link href="<%=path%>/js/ligerUI/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/js/ligerUI/lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" />
    <script src="<%=path%>/js/ligerUI/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
    <script src="<%=path%>/js/ligerUI/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
    <script src="<%=path%>/js/ligerUI/lib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
    <script src="<%=path%>/js/ligerUI/lib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script> 
    <script src="<%=path%>/js/ligerUI/lib/ligerUI/js/plugins/ligerDrag.js"></script>
    <script src="<%=path%>/js/ligerUI/lib/ligerUI/js/plugins/ligerDialog.js"></script>
    <style>
        .message {
            width: 99%;
            height: 100px;
            overflow:auto;
        }
        .l-dialog-win .l-dialog-content { 
            overflow: hidden;
        }
    </style>
    <script type="text/javascript">
        var manager;

        var TreeServiceData = {
            Rows: [
            ]
        };


        $(function ()
        {
        	var serviceManager = {
		    	getAllService : function getAllService () {
					$.ajax({
		            	url: "<%=path%>/serviceInfo/AllService",
		            	type: "GET",
            			dataType: "json",
		            	success: function(result) {
			            		//TreeServiceData.Rows = result;
			            		for(var i=0;i<result.length;i++){
			            			var obj ={
			            				 id: result[i].nodeId,
			            				 pid: result[i].parentNodeId,
			            				 name: result[i].nodeName, 
			            				 remark: result[i].nodeValue
			            			};
			            			TreeServiceData.Rows.push(obj);
			            		}
			            		//console.log(TreeServiceData.Rows);
			            		window.dialog = $.ligerDialog.open({
				                	isResize: true,
				                	isHidden: true,
				                	target: $("<div id='message' class='message'></div>"),
				                	buttons: [
				                    	{ text: '关闭', onclick: function (i, d) { d.hide(); } }
				                	]
				            	});
					            dialog.hide();
					            window.alert = function (message) {
					                $("#message").html(message.toString());
					                dialog.show();
					            }
					            manager = $("#maingrid").ligerGrid({
					                columns: [
					                { display: '节点', name: 'remark', id: 'serviceNodeId', width: 250, align: 'left' },
					                { display: '节点ID', name: 'name', id: 'serviceId', width: 250, align: 'left' },
					                { display: '节点名称', name: 'remark', id:'serviceName',width: 250, type: 'int', align: 'left' }, 
					                { display: '节点描述', name: 'remark', width: 250, align: 'left' }
					                ], width: '70%', usePager: false,  height: '97%',
					                data: TreeServiceData, alternatingRow: true, tree: {
					                    columnId: 'serviceNodeId',
					                    idField: 'id',
					                    parentIDField: 'pid'
					                }
					            });
					            $tree.co
		            	}
		        	});
		    	}
			};
        	serviceManager.getAllService();	
        });
       
        function getParent()
        {
            var row = manager.getParent(manager.getSelectedRow());
            console.log("rowsssssss:"+row);
            alert(JSON.stringify(row));
        }
        function getSelected()
        {
            var row = manager.getSelectedRow();
            if (!row) { alert('请选择行'); return; }
            alert(JSON.stringify(row));
        }
        function getData()
        {
            var data = manager.getData();
            alert(JSON.stringify(data));
        }
        function hasChildren()
        {
            var row = manager.getSelectedRowObj();
            alert(manager.hasChildren(row));
        }
        function isLeaf()
        {
            var row = manager.getSelectedRowObj();
            alert(manager.isLeaf(row));
        }
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
<body  style="padding:4px"> 
<div> 
       <a class="l-button" style="width:120px;float:left; margin-left:10px;" onclick="collapseAll();">全部收起</a>
       <a class="l-button" style="width:120px;float:left; margin-left:10px;" onclick="expandAll();">全部展开</a>

<%--
     <a class="l-button" style="width:120px;float:left; margin-left:10px;" onclick="hasChildren()">是否有子节点</a>
       
        <a class="l-button" style="width:120px;float:left; margin-left:10px;" onclick="isLeaf()">是否叶节点节点</a>
          
   <a class="l-button" style="width:120px;float:left; margin-left:10px;" onclick="getSelected()">获取选中的值(选择行)</a>
  
   <a class="l-button" style="width:120px;float:left; margin-left:10px;" onclick="getData()">获取当前的值</a>

   --%><div class="l-clear"></div>
 
</div>

    <div id="maingrid"></div> 
<div>


</div>

</body>
</html>


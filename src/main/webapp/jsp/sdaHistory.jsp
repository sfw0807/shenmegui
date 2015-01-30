<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <link href="<%=path%>/js/ligerUI/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet"
        type="text/css" />
    <link href="<%=path%>/js/ligerUI/lib/ligerUI/skins/Gray/css/all.css" rel="stylesheet" />
    <script src="<%=path%>/js/ligerUI/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
    <script src="<%=path%>/js/ligerUI/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
    <script src="<%=path%>/js/ligerUI/lib/ligerUI/js/plugins/ligerTextBox.js" type="text/javascript"></script>
    <script src="<%=path%>/js/ligerUI/lib/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
    <script src="<%=path%>/js/ligerUI/lib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
    <script src="<%=path%>/js/ligerUI/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
    <script src="<%=path%>/js/ligerUI/lib/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>
    <script src="<%=path%>/js/ligerUI/lib/ligerUI/js/plugins/ligerResizable.js" type="text/javascript"></script>
    <script type="text/javascript">

        var operationId,serviceId,version;

        var isChrome = (navigator.appVersion.indexOf("Chrome") != -1) ? true : false;
        if(isChrome){
            // 从URL中得到operationId
            var href = window.location.href;
            operationId = href.split("&")[0].split("=")[1];
            serviceId = href.split("&")[1].split("=")[1];
            version = href.split("&")[2].split("=")[1];
        }else{
            param = window.dialogArguments;
            operationId = param.split("|")[0];
            serviceId = param.split("|")[1];
            version = param.split("|")[2];
        }

    	var TreeSDAData = { Rows : [] };
        function alert(message)
        {
            $.ligerDialog.alert(message.toString(), '提示信息');
        }
        function tip(message)
        {
            $.ligerDialog.tip({ title: '提示信息', content: message.toString() });
        }
        var manager;
        $(function ()
        {
        	var sdaManager = {
        		getSDAHistoryInfoByOperationId: function(param) {
			        $.ajax({
			            "type": "POST",
			            "contentType": "application/json; charset=utf-8",
			            "url": "../operationHistory/getSDANew",
			            "data": JSON.stringify(param),
			            "dataType": "json",
			            "success": function(result) {
			        		for(var i=0;i<result.length;i++){
			        			TreeSDAData.Rows.push({
			        				id:result[i].id,
			        				pid:result[i].parentId,
			        				structId:result[i].structId,
			        				metadataId:result[i].metadataId,
			        				remark:result[i].remark,
			        				type:result[i].type,
			        				required:result[i].required,
			        				seq:result[i].seq
			        				}); 
			        		}
				            manager = $("#maingrid").ligerGrid({
			                columns: [
			                { display: '序号', name: 'seq', id: 'seq', width: 50, align: 'left' },
			                { display: '英文名称', name: 'structId', id: 'structId', width: 250, align: 'left' },
			                { display: '元数据ID', name: 'metadataId', id: 'metadataId', width: 250, align: 'left' },
			                { display: '类型', name: 'type', id: 'type', width: 50, align: 'left' },
			                { display: '是否必输', name: 'required', id:'required',width: 50, align: 'left' }, 
			                { display: '备注', name: 'remark', id:'remark',width: 250, align: 'left' },
			                { display: 'id', name: 'id', id:'id',width: 250, align: 'left' },
			                { display: 'pid', name: 'pid', id:'pid',width: 250, align: 'left' }
			                ], width: '100%',usePager:false, height: '97%',
			                data: TreeSDAData, alternatingRow: false, tree: {
			                    columnId: 'structId',
			                    //columnName: 'name',
			                    idField: 'id',
			                    parentIDField: 'pid'
			                }
			            }
			            );
			            }
			        });
			  	}
        	}
        	var param = [operationId,serviceId,version];
        	sdaManager.getSDAHistoryInfoByOperationId(param);
        });
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
            var row = manager.getSelected();
            alert(manager.hasChildren(row));
        }
        function upgrade()
        {
            var row = manager.getSelected();
            manager.upgrade(row);
        }
        function demotion()
        {
            var row = manager.getSelected();
            manager.demotion(row);
        }
        function toggle()
        {
            var row = manager.getSelected();
            manager.toggle(row);
        }
        function expand()
        {
            var row = manager.getSelected();
            manager.expand(row);
        }
    </script>
    <style type="text/css">
    .l-button{width: 120px; float: left; margin-left: 10px; margin-bottom:2px; margin-top:2px;}
    .2-button{height: 23px;overflow: hidden;line-height: 23px;cursor: pointer;position: relative;text-align:center;border:1px solid #D3D3D3; color:#333333;background:url('<%=path%>/js/ligerUI/lib/ligerUI/skins/Gray/images/ui/button.gif') repeat-x center center;};
    </style>
</head>
<body style="padding: 4px">
    <div>
    <a class="l-button" onclick="toggle()" style="width: 100px;">展开/收缩节点</a>
        <div class="l-clear">
        </div>
    </div>
    <div id="maingrid">
    </div>
    <div>
    </div>
</body>
</html>

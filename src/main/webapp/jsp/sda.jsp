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
        var isChrome = (navigator.appVersion.indexOf("Chrome") != -1) ? true : false;
        var param;
        if(isChrome){
            var href = window.location.href;
            param = href.split("=")[1];
        }else{
            param = window.dialogArguments;
        }
		var operationId = param.substr(0,param.length-10);
		var serviceId = param.substr(param.length-10,param.length-1);
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
        		getSDAInfoByOperationId: function(operationId,serviceId) {
			        $.ajax({
			            url: '../operationInfo/getSDAByOperationService/'+serviceId+operationId,
			            type: 'GET',
            			dataType: "json",
			            success: function(result) {
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
        	var operationId = param.substr(0,param.length-10);
        	var serviceId = param.substr(param.length-10,param.length-1);
        	sdaManager.getSDAInfoByOperationId(operationId,serviceId);
        	$("#randomUUID").click(function(){
        		var operationManager = {
				    randomUUID: function() {
				        $.ajax({
				            url: '../operationInfo/randomUUID',
				            type: 'GET',
				            success: function(result) {
				                $("#sdaid").val(result);
				            }
				        });
				    }
				}
        		operationManager.randomUUID();
        	});
        });
        function refreshSeq()
        {
        	var table = document.getElementById("gridId");
        	for(var i=table.rows.length-1;i>=0;i--){
        		if(table.rows[i].cells[0].innerText==""){
        			for(var j=i;j<table.rows.length;j++){
        				table.rows[j].cells[0].innerHTML='<div class="l-grid-row-cell-inner" style="width:42px;height:28px;min-height:28px; text-align:left;">'+(j+1)+'</div>';
        			}
        		}
        	}
        	for(var i=table.rows.length-1;i>=0;i--){
        		table.rows[i].cells[0].innerHTML='<div class="l-grid-row-cell-inner" style="width:42px;height:28px;min-height:28px; text-align:left;">'+(i+1)+'</div>';
        	}
        }
        function deleteRow()
        {
            var row = manager.getSelectedRow();
            var structId = row["structId"];
            if(structId==operationId ||structId=="request" ||structId=="response" ||structId=="SvcBody"){
            	alert("该节点不能删除");
            	 return ;
            }
            manager.deleteRow(row);
            refreshSeq();
        }
        var i = -1;
        function getNewData(pid,withchildren)
        {
            i++;
            var id=$("#sdaid").val();
            var structId = $("#structId").val();
            var metadataId = $("#metadataId").val();
            var type = $("#type").val();
            var required = $("#required").val();
            var remark = $("#remark").val();        
            var data = {
            	id:id,
            	pid:pid,
                structId: structId,
                metadataId: metadataId,
                remark: remark,
                type:type,
                required:required
            };
            if (withchildren)
            {
                data.children = [];
                data.children.push(getNewData());
                data.children.push(getNewData());
                data.children.push(getNewData());
            }
            return data;
        }
        //parent:是否增加到当前节点下面
        function addRow(withchildren)
        {
        	var selectRow = manager.getSelectedRow();
        	var pid = selectRow["id"];
            var data = getNewData(pid,withchildren);
            if(data.id=="" || data.structId=="" || data.metadataId=="" || type=="" || pid=="" ){
            	alert("节点数据不完整");
            	return ;
            }
            
            var structId = selectRow["structId"];
            if(structId==operationId ||structId=="request" ||structId=="response"){
            	alert("该节点不能增加子节点");
            	 return ;
            }
            var parentRow = selectRow;

            if (manager.isLeaf(parentRow))
            {
                alert('叶节点不能增加子节点');
                return;
            }
            manager.add(data, null, true, parentRow);
            refreshSeq();
        }
        function appendToCurrentNodeUp()
        {
            var selectRow = manager.getSelectedRow();
            if (!selectRow) return;
            var pid = selectRow["pid"];
            var selectRowParnet = manager.getParent(selectRow);
            var data = getNewData(pid,false);
            if(data.id=="" || data.structId=="" || data.metadataId=="" || type=="" ){
            	alert("节点数据不完整");
            	return ;
            }
            manager.add(data, selectRow, true, selectRowParnet);
            refreshSeq();
        }
        function appendToCurrentNodeDown()
        {
            var selectRow = manager.getSelectedRow();
            if (!selectRow) return;
            var pid = selectRow["pid"];
            var selectRowParnet = manager.getParent(selectRow);
            var data = getNewData(pid,false);
            if(data.id=="" || data.structId=="" || data.metadataId=="" || type=="" ){
            	alert("节点数据不完整");
            	return ;
            }
            manager.add(data, selectRow, false, selectRowParnet);
            refreshSeq();
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
        function isLeaf()
        {
            var row = manager.getSelected();
            alert(manager.isLeaf(row));
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
        function up()
        {
            var row = manager.getSelected();
            manager.up(row);
            refreshSeq();
        }
        function down()
        {
            var row = manager.getSelected();
            manager.down(row);
            refreshSeq();
        }
        function saveSDA()
        {
        	var sdaManager = {
        		saveSDA: function(sda) {
			        $.ajax({
			            "type": "POST",
			            "contentType": "application/json; charset=utf-8",
			            "url": "../operationInfo/addSDANew",
			            "data": JSON.stringify(sda),
			            "dataType": "json",
			            "success": function(result) {
			        		if(result){
			        			alert("保存成功");
			        			window.location.reload();
			        		}else{
			        			alert("保存失败");
			        		}
			            }
			        });
			  	}
        	};
			var sdaArray = [];
        	var table = document.getElementById("gridId");
        	for(var i=table.rows.length-1;i>=0;i--){
        		var seq = table.rows[i].cells[0].innerText;
        		var id = table.rows[i].cells[6].innerText;
        		var structId = table.rows[i].cells[1].innerText;
        		var metadataId = table.rows[i].cells[2].innerText;
        		var type = table.rows[i].cells[3].innerText;
        		var required = table.rows[i].cells[4].innerText;
        		var remark = table.rows[i].cells[5].innerText;
        		var parentId = table.rows[i].cells[7].innerText;
 				var params = {
					id:id,
					structId:structId,
					metadataId:metadataId,
					type:type,
					seq:seq,
					required:required,
					remark:remark,
					serviceId:serviceId,
					operationId:operationId,
					parentId:parentId		
		        };
				sdaArray.push(params);
        	}	
			sdaManager.saveSDA(sdaArray);
        }
    </script>
    <style type="text/css">
    .l-button{width: 120px; float: left; margin-left: 10px; margin-bottom:2px; margin-top:2px;}
    .2-button{height: 23px;overflow: hidden;line-height: 23px;cursor: pointer;position: relative;text-align:center;border:1px solid #D3D3D3; color:#333333;background:url('<%=path%>/js/ligerUI/lib/ligerUI/skins/Gray/images/ui/button.gif') repeat-x center center;};
    </style>
</head>
<body style="padding: 4px">
    <div>
    <div style="margin: 2px;">
          【节点数据】节点ID: <input type="text" id="sdaid" disabled="true" />
           <input type="button" value="生成ID" id="randomUUID" 
           style="width: 50px;
           height: 23px;
           overflow: hidden;
           line-height: 23px;
           cursor: pointer;
           position: relative;
           text-align:center;
           border:1px solid #D3D3D3; 
           color:#333333;background:url('<%=path%>/js/ligerUI/lib/ligerUI/skins/Gray/images/ui/button.gif') repeat-x center center;"/>
             	英文名称：<input type="text"  id="structId" />
             	元数据ID：<input type="text"  id="metadataId" />
             	类型：<select id="type">
             			<option value="string">string</option>
             			<option value="char">char</option>
             			<option value="double">double</option>
             			<option value="int">int</option>
             			<option value="number">number</option>
             			<option value="struct">struct</option>
             			<option value="array">array</option>
             			<option value="">不填</option>
             		</select>
             	是否必输：<select id="required">
             			<option value="Y">Y</option>
             			<option value="N">N</option>
             			</select>
             	备注：<input type="text"  id="remark" />         	             	
    </div>
    <a class="l-button" onclick="toggle()" style="width: 100px;">展开/收缩节点</a>
    <a class="l-button"  onclick="deleteRow()" style="width: 100px;"> 删除选择行</a> 
    <a class="l-button" onclick="addRow()" style="width: 100px;">增加子节点</a><%-- 
    <a class="l-button" onclick="addRow(true)" style="width: 100px;">增加子树节点</a>
    --%><a class="l-button" onclick="appendToCurrentNodeUp()" style="width: 100px;">增加兄弟节点(上)</a>
    <a class="l-button" onclick="appendToCurrentNodeDown()" style="width: 100px;">增加兄弟节点(下)</a>  
    <a class="l-button" onclick="up()" style="width: 100px;">节点上移</a> 
    <a class="l-button" onclick="down()" style="width: 100px;">节点下移</a>
    <a class="l-button" onclick="saveSDA()" style="width: 100px;">保存</a> 
        <div class="l-clear">
        </div>
    </div>
    <div id="maingrid">
    </div>
    <div>
    </div>
</body>
</html>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>列表页</title>
    <link rel="stylesheet" type="text/css" href="/resources/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="/resources/themes/icon.css">
    <link href="/resources/css/ui.css" rel="stylesheet" type="text/css">
    <script type="text/javascript">

        var taskFormatter = {
            "formatStatus": function (data, row) {
                if (data) {
                    if (data == "Reserved") {
                        return "待分派";
                    }
                    if (data == "Ready") {
                        return "已分派";
                    }
                    if (data == "InProgress"){
                        return "处理中";
                    }
                }
            },
            "formatPriority": function (data, row) {
                if (data != undefined) {
                    if ("0" == data) {
                        return "低";
                    }
                }
            },
            "formatCreateBy": function (data, row) {
                if (row) {
                    if (row.createdBy)
                        return row.createdBy.id;
                }
            },
            "formatActualOwner": function(data,row){
                if(data){
                    return data.id;
                }
            }
        }
    </script>
</head>

<body>

<div class="easyui-tabs" style="width:100%;height:auto">
    <div title="未完成任务" style="padding:0px">
        <table class="easyui-datagrid" id="taskTable"
               data-options="rownumbers:true,border:false,toolbar:toolbar,singleSelect:false,url:'/process/admin/list',method:'get',pagination:true,
				pageSize:10" style="height:370px; width:auto;">
            <thead>
            <tr>
                <th data-options="field:'productid',checkbox:true"></th>
                <th data-options="field:'id'">任务ID</th>
                <th data-options="field:'name'">任务名称</th>
                <th data-options="field:'subject'">主题</th>
                <th data-options="field:'description'">描述</th>
                <th data-options="field:'status'" formatter="taskFormatter.formatStatus">状态</th>
                <th data-options="field:'priority'" formatter="taskFormatter.formatPriority">优先级</th>
                <th data-options="field:'createBy'" formatter="taskFormatter.formatCreateBy">创建人</th>
                <th data-options="field:'actualOwner'" formatter="taskFormatter.formatActualOwner">受理人</th>
            </tr>
            </thead>
        </table>
    </div>
    <div title="已完成任务" style="padding:10px">
        <fieldset>
            <legend>条件搜索</legend>
            <%--<table border="0" cellspacing="0" cellpadding="0">--%>
            <%--<tr>--%>
            <%--<th>工作流程</th>--%>
            <%--<td>--%>
            <%--<select class="easyui-combobox" panelHeight="auto" style="width:155px">--%>
            <%--<option value="java">流程1</option>--%>
            <%--<option value="c">流程2</option>--%>
            <%--<option value="basic">流程3</option>--%>
            <%--<option value="perl">流程4</option>--%>
            <%--</select></td>--%>

            <%--<th>任务节点</th>--%>
            <%--<td>--%>
            <%--<select class="easyui-combobox" panelHeight="auto" style="width:155px">--%>
            <%--<option value="java">节点1</option>--%>
            <%--<option value="c">节点2</option>--%>
            <%--<option value="basic">节点3</option>--%>
            <%--<option value="perl">节点4</option>--%>
            <%--</select></td>--%>
            <%--<th>紧急程度</th>--%>
            <%--<td><input class="easyui-textbox" type="text" name="name"></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
            <%--<th>计划起始日期</th>--%>
            <%--<td><input class="easyui-datebox" type="text" name="name"></td>--%>
            <%--<th>计划结束日期</th>--%>
            <%--<td><input class="easyui-datebox" type="text" name="name"></td>--%>
            <%--<th>责任人</th>--%>
            <%--<td><input class="easyui-textbox" type="text" name="name"></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
            <%--<td>&nbsp;</td>--%>
            <%--<td>&nbsp;</td>--%>
            <%--<td>&nbsp;</td>--%>
            <%--<td>&nbsp;</td>--%>
            <%--<td>&nbsp;</td>--%>
            <%--<td align="right"><a href="#" class="easyui-linkbutton" iconCls="icon-search">搜索任务</a></td>--%>
            <%--</tr>--%>
            <%--</table>--%>


        </fieldset>
        <%--<table class="easyui-datagrid" title="已完成任务"--%>
        <%--data-options="rownumbers:true,singleSelect:false,url:'../datagrid_data1.json',method:'get',pagination:true,--%>
        <%--pageSize:10" style="height:370px; width:auto;">--%>
        <%--<thead>--%>
        <%--<tr>--%>
        <%--<th data-options="field:'productid',checkbox:true"></th>--%>


        <%--<th data-options="field:'itemid'">字段1</th>--%>
        <%--<th data-options="field:'status'">字段2</th>--%>
        <%--<th data-options="field:'listprice',align:'right'">字段3</th>--%>
        <%--<th data-options="field:'unitcost',width:80,align:'right'">字段4</th>--%>
        <%--<th data-options="field:'attr1'">消费方</th>--%>
        <%--<th data-options="field:'status',width:60,align:'center'">字段5</th>--%>
        <%--<th data-options="field:'attr1'"> 字段6</th>--%>
        <%--<th data-options="field:'attr1'"> 字段7</th>--%>
        <%--<th data-options="field:'attr1'">字段8</th>--%>
        <%--<th data-options="field:'attr1'">字段9</th>--%>
        <%--</tr>--%>
        <%--</thead>--%>
        <%--</table>--%>
    </div>
    <script type="text/javascript">
        var Global = {};
        var toolbar = [
            {
                text: '创建',
                iconCls: 'icon-edit',
                handler: function () {
                    uiinit.win({
                        w: 500,
                        iconCls: 'icon-add',
                        title: "创建任务",
                        url: "/jsp/task/addTask.jsp"
                    });
                }
            },
            {
                text: '分配',
                iconCls: 'icon-edit',
                handler: function () {
                    var checkedItems = $('#taskTable').datagrid('getChecked');
                    var checkedItem;
                    if (checkedItems != null && checkedItems.length > 0) {
                        if (checkedItems.length > 1) {
                            alert("请选择一个任务进行分配！");
                            return false;
                        }
                        else {
                            checkedItem = checkedItems[0];
                            Global.taskId = checkedItem.id;
                            uiinit.win({
                                w: 500,
                                iconCls: 'icon-edit',
                                title: "任务分配",
                                url: "/jsp/task/assignTask.jsp"
                            });
                        }
                    }
                    else {
                        alert("请选中要修改的数据！");
                    }
                }
            },
            {
                text: '处理',
                iconCls: 'icon-edit',
                handler: function () {
                    var checkedItems = $('#taskTable').datagrid('getChecked');
                    var checkedItem;
                    if (checkedItems != null && checkedItems.length > 0) {
                        if (checkedItems.length > 1) {
                            alert("请选择一个任务进行分配！");
                            return false;
                        }
                        else {
                            checkedItem = checkedItems[0];
                            Global.taskId = checkedItem.id;
                            parent.PROCESS_INFO.processId = checkedItem.id;
                            Global.processInstanceId = checkedItem.processInstanceId;
                            Global.taskName = checkedItem.name;
                            var task = {};
                            task.processInstanceId = Global.processInstanceId;
                            task.taskId = Global.taskId;
                            task.userId = "admin";
                            console.log(Global);
                            task.name = Global.taskName;
                            taskManager.processTask(task,function(){
                                console.log(task.name);
                                if(task.name=='创建元数据'){
                                    $("#w").window("close");
                                    $('#taskTable').datagrid('reload');
                                    var content = '<iframe scrolling="auto" frameborder="0"  src="/process/metadataByTask/process/'+task.processInstanceId+'/task/'+task.taskId+'" style="width:100%;height:100%;"></iframe>';
                                    parent.addTab("创建元数据", content);
                                }
                                if(task.name=="元数据审核"){
                                    $("#w").window("close");
                                    $('#taskTable').datagrid('reload');
                                    var content = '<iframe scrolling="auto" frameborder="0"  src="/process/metadataAuditByTask/process/'+task.processInstanceId+'/task/'+task.taskId+'" style="width:100%;height:100%;"></iframe>';
                                    parent.addTab("创建元数据", content);
                                }
                                if(task.name=="服务定义"){
                                    $("#w").window("close");
                                    $('#taskTable').datagrid('reload');
                                    parent.SYSMENU.changeLeftMenu(4);
                                }
                                if(task.name=="创建公共代码"){
                                    var content = '<iframe scrolling="auto" frameborder="0"  src="/jsp/SGEnum/task/common.jsp?processId='+task.processInstanceId+'&taskId='+task.taskId+'" style="width:100%;height:100%;"></iframe>';
                                    parent.addTab("创建公共代码", content);
                                }
                                if(task.name=="公共代码审核"){
                                    $("#w").window("close");
                                    $('#taskTable').datagrid('reload');
                                    var content = '<iframe scrolling="auto" frameborder="0"  src="/process/sgenum/sgenumAuditByTask/process/'+task.processInstanceId+'/task/'+task.taskId+'" style="width:100%;height:100%;"></iframe>';
                                    parent.addTab("创建公共代码", content);
                                }

                            });
                            /*uiinit.win({
                                w: 500,
                                iconCls: 'icon-edit',
                                title: "任务分配",
                                url: "/jsp/task/processTask.jsp"
                            });*/
                        }
                    }
                    else {
                        alert("请选中要修改的数据！");
                    }
                }
            },
            {
                text: '挂起',
                iconCls: 'icon-guaqi',
                handler: function () {
                    alert('挂起')
                }
            },{
                text: '完成',
                iconCls: 'icon-guaqi',
                handler: function () {
                    alert('挂起')
                }
            }
        ];
    </script>
    <script type="text/javascript" src="/resources/js/jquery.min.js"></script>
    <script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/resources/js/ui.js"></script>
    <script type="text/javascript" src="/assets/task/taskManager.js"></script>

</body>
</html>
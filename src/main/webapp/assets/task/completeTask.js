/**
 * Created by vincentfxz on 15/7/7.
 */
$(function () {
    $("#completeTaskBtn").click(function () {
        var task = {};
        task.taskId = taskId;
        var nextUser = $("#nextUser").val();
        task.userId = "admin";
        var params = {};
        params.userId = nextUser;
        params.approved = true;
        taskManager.completeTask(task,params,function(){
            alert("任务已经处理！");
            $("#w").window("close");
            $('#taskTable').datagrid('reload');
        });
    });
});
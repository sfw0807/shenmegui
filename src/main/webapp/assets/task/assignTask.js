/**
 * Created by vincentfxz on 15/7/6.
 */
var href = window.location.href;
var params = href.split("&");

$(function () {
    $("#assignTaskBtn").click(function () {
        var task = {};
        task.taskId = Global.taskId;
        task.targetUserId = $("#targetUserId").val();
        task.userId = "admin";
        taskManager.assignTask(task, function () {
            alert("任务已经分派！");
            $("#w").window("close");
            $('#taskTable').datagrid('reload');
        });
    });
});




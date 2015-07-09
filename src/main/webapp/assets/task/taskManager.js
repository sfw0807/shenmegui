/**
 * Created by vincentfxz on 15/7/6.
 */
var taskManager = {
    "createTask": function createTask(task, params, callBack) {
        //{user}/create/{type}
        var url = "/process/" + task.userId + "/create/" + task.taskType;
        console.log(task.userId);
        console.log(url);
        $.ajax({
            "type": "POST",
            "contentType": "application/json;charset=utf-8",
            "url": url,
            "data": JSON.stringify(params),
            "dataType": "json",
            "success": function (result) {
                callBack(result);
            }
        });
    },
    "assignTask": function assignTask(task, callBack) {
        //{user}/delegate/{targetUser}/task/{taskId}
        var url = "/process/" + task.userId + "/delegate/" + task.targetUserId + "/task/" + task.taskId;
        $.ajax({
            "type": "GET",
            "contentType": "application/json; charset=utf-8",
            "url": url,
            "dataType": "json",
            "success": function (result) {
                callBack(result);
            }
        });
    },
    "processTask" : function(task, callBack){
        //{user}/work/{task}/taskName/{taskName}
        var url = "/process/" + task.userId + "/work/" + task.taskId ;
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": url,
            "data": JSON.stringify(task),
            "dataType": "json",
            "success": function (result) {
                callBack(result);
            }
        });

    },
    "completeTask" : function (task, params, callBack){
        //{user}/work/{task}
        var url = "/process/" + task.userId + "/complete/" + task.taskId;
        $.ajax({
            "type": "POST",
            "contentType": "application/json;charset=utf-8",
            "url": url,
            "data": JSON.stringify(params),
            "dataType": "json",
            "success": function (result) {
                callBack(result);
            }
        });
    },
    "auditMetadata" : function (task,callBack){
        //"/audit/process/{processId}"
        var url = "/metadata/audit/process/" + task.processId;
        console.log(url);
        $.ajax({
            "type": "GET",
            "contentType": "application/json;charset=utf-8",
            "url": url,
            "dataType": "json",
            "success": function (result) {
                callBack(result);
            }
        });
    }
};
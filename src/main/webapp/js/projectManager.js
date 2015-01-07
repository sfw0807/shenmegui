/**
 * Created with IntelliJ IDEA.
 * User: Vincent Fan
 * Date: 14-2-23
 * Time: 下午4:19
 * To change this template use File | Settings | File Templates.
 */
var projectManager = {
    deleteById: function(id, callBack) {
        if (id) {
            $.ajax({
                url: '/project/delete/' + id,
                type: 'GET',
                success: function(result) {
                    callBack(result);
                }
            });
        }
    },
    getById: function(id, callBack) {
        if (id) {
            $.ajax({
                url: '/project/list/' + id,
                type: 'GET',
                success: function(result) {
                    callBack(result);
                }
            });

        }
    },
    getAll: function(callBack) {
        $.ajax({
            url: '/project/list',
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    modify: function(project, callBack) {
        console.log(project);
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "/project/list",
            "data": JSON.stringify(project),
            "dataType": "json",
            "success": function(result) {
                callBack(result);
            }
        });
    },
    add: function(project, callBack) {
        console.log(project);
        $.ajax({
            "type": "POST",
            "contentType": "application/json; charset=utf-8",
            "url": "/project/list",
            "data": JSON.stringify(project),
            "dataType": "json",
            "success": function(result) {
                callBack(result);
            }
        });
    },
    getByCompanyId: function(companyId, callBack) {
        console.log(id);
        if (id) {
            $.ajax({
                url: '/project/companyId/' + companyId,
                type: 'GET',
                success: function(result) {
                    callBack(result);
                }
            });

        }
    }

};
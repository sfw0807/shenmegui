/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-6-4
 * Time: 上午10:29
 * To change this template use File | Settings | File Templates.
 */
var userManager = {
    getAll: function(callBack) {
        $.ajax({
            url: '../user/list',
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    deleteUser : function(param) {
		$.ajax( {
			"type" : "POST",
			"contentType" : "application/json; charset=utf-8",
			"url" : "../user/delete",
			"dataType" : "json",
			"data" : JSON.stringify(param),
			"success" : function(result) {
				if (result) {
					alert("删除成功");
					window.location.href="userManager.jsp";
				} else {
					alert("删除失败");
				}
			}
		});
	}
}

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-6-4
 * Time: 上午10:29
 * To change this template use File | Settings | File Templates.
 */
var orgManager = {
    getAll: function(callBack) {
        $.ajax({
            url: '../org/list',
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    },
    deleteOrg: function(param){
        $.ajax({
            url: '../org/delete/'+param,
            type: 'GET',
            success: function(result) {
                if(result){
                	alert("删除成功");
                	window.location.href="orgManager.jsp";
                }else{
                	alert("删除失败");
                }
            }
        });
    }
}

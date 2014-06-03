/**
 * Created with IntelliJ IDEA.
 * User: Vincent Fan
 * Date: 14-2-23
 * Time: 下午4:48
 * To change this template use File | Settings | File Templates.
 */


var ammeterRecordManager = {

    get: function(id, callBack) {
        if (id) {
            $.ajax({
                url: '/ammeter_record/list/' + id,
                type: 'GET',
                success: function(result) {
                    callBack(result);
                }
            });

        }
    },
    getAll: function(callBack) {
        $.ajax({
            url: '/ammeter_record/list/',
            type: 'GET',
            success: function(result) {
                callBack(result);
            }
        });
    }

};

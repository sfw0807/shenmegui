/**
 * Created by vincentfxz on 15/6/30.
 */
var serviceUIHelper = {
    "appendServiceForm": function appendServiceForm() {
        var node = $('.mxservicetree').tree('getSelected');
        if (node.type == "service") {
            uiinit.win({
                w: 500,
                iconCls: 'icon-add',
                title: "新增服务",
                url: "/jsp/service/serviceAppandForm.jsp"
            });
        } else if (node.type == "root" || node.serviceCategory.parentId == null) {//根节点 或 父节点
            uiinit.win({
                w: 500,
                iconCls: 'icon-add',
                title: "新增服务类",
                url: "/dataTemplate/formTemplate/serviceCategoryForm/serviceCategoryAppandForm.jsp"
            });
        } else if (node.type == "serviceCategory" && node.serviceCategory.parentId != null) {//服务类子节点
            uiinit.win({
                w: 500,
                iconCls: 'icon-add',
                title: "新增服务",
                url: "/jsp/service/serviceAppandForm.jsp"
            });
        }
    },
    "editServiceForm": function editServiceForm() {
        var node = $('.mxservicetree').tree('getSelected');
        if (node.type == "service") {
            uiinit.win({
                w: 500,
                iconCls: 'icon-add',
                title: "编辑服务",
                url: "/pages/service/form/serviceEditForm.jsp"
            });
        } else if (node.type == "serviceCategory") {
            uiinit.win({
                w: 500,
                iconCls: 'icon-add',
                title: "编辑服务类",
                url: "/dataTemplate/formTemplate/serviceCategoryForm/serviceCategoryEditForm.jsp"
            });
        }
    },
    "deleteServiceFormTree" : function removeService(){
        var node = $('.mxservicetree').tree('getSelected');
        var id = node.id;
        if(node.type=="service"){
            $('.mxservicetree').tree('remove', node.target);
            serviceManager.deleteById(id);
        }else if(node.type=="serviceCategory"){
            $('.mxservicetree').tree('remove', node.target);
            serviceManager.deleteCategoryById(id);
        }
    },
    "searchService" : function searchService(){
        var serviceName = $('#servicetreefilter').val();
        if(serviceName==""){
            $('.mxservicetree').tree({
                url:"/service/getTree"
            });
        }else{
            $('.mxservicetree').tree({
                url:"/service/searchService/"+serviceName
            });
        }
    }

};

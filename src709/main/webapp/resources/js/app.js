var LOAD_URL = {
    LEFTMENU: '/dataTemplate/leftMenu.jsp',
    CHECKRULE: '/dataTemplate/checkRule.jsp',
    SYSADMINUI: '/dataTemplate/sysadmin/sla.html',
    SYSADMINUIEDIT: '/dataTemplate/sysadmin/grid2.html',
    PUBLICHEADER: '/jsp/sysadmin/interface_header.jsp',
    INTERFACELIST: '/jsp/interface/interface_list.jsp',
    INTERFACEDEFINE: '/jsp/interface/interface_define.jsp',
    SERVICEUI: '/dataTemplate/serviceadmin/index.html',
    SERVICEUI2: '/dataTemplate/serviceadmin/fwcjmx.html',
    SERVICEUI_LW: '/jsp/service/servicePage.jsp',
    USERADD:'/jsp/user/useradd.jsp'

}
var SYSMENU = {
    init: function () {
        SYSMENU.initHeaderMenu();
    },
    initHeaderMenu: function () {//初始化头部菜单
        $("#nav").find("a").click(function () {//点击更新左侧菜单
            $("#nav").find("a").removeClass("current");
            $(this).addClass("current");
            var mid = $(this).attr("mid");
            $("#west-menu").load(LOAD_URL.LEFTMENU, 'mid=' + mid, function () {
                $('#mxsysadmintreefilter').searchbox({
                    searcher: function (value, name) {
                        alert(value + "," + name);
                    },
                    prompt: '请输入关键词'
                });
                $('#servicetreefilter').searchbox({
                    "searcher": serviceManager.searchService,
                    prompt: '请输入服务名'
                });
                $('#mxinterfacetreefilter').searchbox({
                    searcher: function (value, name) {
                        $('.msinterfacetree').tree('doFilter', value);
                    },
                    prompt: '请输入关键词'
                });
                $('#mxinterfaceheadtreefilter').searchbox({
                    searcher: function (value, name) {
                        $('.mxsysadmintree').tree('doFilter', value);
                    },
                    prompt: '请输入关键词'
                });

                //报文管理
                $('.mxsysadmintree').tree({
                    onContextMenu: function (e, node) {
                        /*if(node.id=='root'){
                         return;
                         }*/
                        e.preventDefault();
                        $(this).tree('select', node.target);
                        if (typeof(node.children) != 'undefined') {//编辑接口
                            $('#mm-mxsysadmintree').menu('show', {
                                left: e.pageX,
                                top: e.pageY
                            });
                        }
                    },
                    onClick: function (node) {
                        if (node.id == 'root') {
                            return;
                        }
                        if (typeof(node.children) == 'undefined') {//编辑接口
                            var url = LOAD_URL.SYSADMINUIEDIT;
                            var mid = node.id;
                            var title = node.text;
                            //公共报文头信息管理
                            if (mid == 1) {
                                url = LOAD_URL.PUBLICHEADER;
                            }

                            if ($('#mainContentTabs').tabs('exists', title)) {
                                $('#mainContentTabs').tabs('select', title);
                            } else {
                                var content = '<iframe scrolling="auto" frameborder="0"  src="' + url + '" style="width:100%;height:100%;"></iframe>';
                                $('#mainContentTabs').tabs('add', {
                                    title: title,
                                    content: content,
                                    closable: true
                                });
                            }
                        }

                        else {//基本信息
                            var mid = node.id;
                            var title = node.text;

                            var node = $('.mxsysadmintree').tree("getSelected");
                            if ($('#mainContentTabs').tabs('exists', title)) {
                                $('#mainContentTabs').tabs('select', title);
                            } else {//LOAD_URL.SYSADMINUI+
                                var content = '<iframe scrolling="auto" frameborder="0"  src="' + LOAD_URL.PUBLICHEADER + '?headId=' + node.id + '" style="width:100%;height:100%;"></iframe>';
                                $('#mainContentTabs').tabs('add', {
                                    title: title,
                                    content: content,
                                    closable: true
                                });
                            }
                        }
                    }
                });

                //接口管理
                $('.msinterfacetree').tree({
                    onContextMenu: function (e, node) {
                        e.preventDefault();
                        $(this).tree('select', node.target);
                        if (typeof(node.children) != 'undefined') {//编辑接口

                            if (node.click == 'disable') {
                                $('#mm-mxinterfacetree').menu('show', {
                                    left: e.pageX,
                                    top: e.pageY
                                });
                            } else {
                                $('#mm-mxinterfacedefinetree').menu('show', {
                                    left: e.pageX,
                                    top: e.pageY
                                });
                            }

                        }

                    },
                    onClick: function (node) {
                        if (node.click == 'disable') {
                            var mid = node.id;
                            var title = node.text;
                            if ($('#mainContentTabs').tabs('exists', title)) {
                                $('#mainContentTabs').tabs('select', title);
                            } else {//SYSADMINUIEDIT
                                var content = '<iframe scrolling="auto" frameborder="0"  src="' + LOAD_URL.INTERFACELIST + '?systemId=' + mid + '" style="width:100%;height:100%;"></iframe>';
                                $('#mainContentTabs').tabs('add', {
                                    title: title,
                                    content: content,
                                    closable: true
                                });
                            }
                        } else {

                            //alert(typeof(node.children));
                            //if(typeof(node.children)=='undefined'){//编辑接口

                            var mid = node.id;
                            var title = node.text;
                            if ($('#mainContentTabs').tabs('exists', title)) {
                                $('#mainContentTabs').tabs('select', title);
                            } else {//SYSADMINUIEDIT
                                var content = '<iframe scrolling="auto" frameborder="0"  src="' + LOAD_URL.INTERFACEDEFINE + '?interfaceId=' + mid + '" style="width:100%;height:100%;"></iframe>';
                                $('#mainContentTabs').tabs('add', {
                                    title: title,
                                    content: content,
                                    closable: true
                                });
                            }
                        }
                        /*}else{//基本信息
                         var mid = node.id;
                         var title = node.text;
                         if ($('#mainContentTabs').tabs('exists', title)){
                         $('#mainContentTabs').tabs('select', title);
                         } else {
                         var content = '<iframe scrolling="auto" frameborder="0"  src="'+LOAD_URL.SYSADMINUI+'" style="width:100%;height:100%;"></iframe>';
                         $('#mainContentTabs').tabs('add',{
                         title:title,
                         content:content,
                         closable:true
                         });
                         }
                         }  */
                    }
                });

                $('.mslinktree').tree({
                    onContextMenu: function (e, node) {
                        e.preventDefault();
                        $(this).tree('select', node.target);
                        if (typeof(node.children) != 'undefined') {//编辑接口
                            $('#mm-mslinktree').menu('show', {
                                left: e.pageX,
                                top: e.pageY
                            });

                        }

                    },
                    onClick: function (node) {

                        if (typeof(node.children) == 'undefined') {//编辑接口

                            var mid = node.id;
                            var title = node.text;
                            if ($('#mainContentTabs').tabs('exists', title)) {
                                $('#mainContentTabs').tabs('select', title);
                            } else {
                                var content = '<iframe scrolling="auto" frameborder="0"  src="/dataTemplate/syslink/index2.html" style="width:100%;height:100%;"></iframe>';
                                $('#mainContentTabs').tabs('add', {
                                    title: title,
                                    content: content,
                                    closable: true
                                });
                            }
                        } else {
                            var mid = node.id;
                            var title = node.text;
                            if ($('#mainContentTabs').tabs('exists', title)) {
                                $('#mainContentTabs').tabs('select', title);
                            } else {
                                var content = '<iframe scrolling="auto" frameborder="0"  src="/dataTemplate/syslink/index.html" style="width:100%;height:100%;"></iframe>';
                                $('#mainContentTabs').tabs('add', {
                                    title: title,
                                    content: content,
                                    closable: true
                                });
                            }

                        }
                    }
                });

                $('.mxservicetree').tree({
                    onContextMenu: function (e, node) {
                        e.preventDefault();
                        $(this).tree('select', node.target);
                        if (typeof(node.children) != 'undefined') {//编辑接口
                            $('#mm-mxservicetree').menu('show', {
                                left: e.pageX,
                                top: e.pageY
                            });

                        }

                    },
                    onClick: function (node) {
                        console.log(node);

                        if (node.type == 'service') {//打开服务场景
                            if ($("#serviceFrame" + node.id).size() == 0) {//如果没有打开基本信息，则新创建基本信息
                                var mid = node.id;
                                var title = node.text;
                                if ($('#mainContentTabs').tabs('exists', title)) {
                                    $('#mainContentTabs').tabs('select', title);
                                } else {
                                    var content = '<iframe scrolling="auto"  name="serviceFrame' + node.id + '" id="serviceFrame' + node.id + '" frameborder="0"  src="' + LOAD_URL.SERVICEUI_LW + "?serviceId=" + node.id + '" style="width:100%;height:100%;"></iframe>';
                                    $('#mainContentTabs').tabs('add', {
                                        title: title,
                                        content: content,
                                        closable: true
                                    });
                                }
                            }

                        }
                    }
                });


                SYSTABMENU.init();
            })
        });
    }
}
SYSMENU.init();
$("#nav").find("a").get(0).click();

var SYSTABMENU = {
    init: function () {
        $("#west-menu").find(".openable").click(function () {
            var mid = $(this).attr("mid");
            var title = $(this).text();
            if ($('#mainContentTabs').tabs('exists', title)) {
                $('#mainContentTabs').tabs('select', title);
            } else {
                SYSTABMENU.checkRule(mid);
            }
        })
    },
    checkRule: function (mid) {
        $.post(LOAD_URL.CHECKRULE, 'mid=' + mid + '&t=' + new Date().getTime(), function (result) {


            if (result.success) {
                var content = '<iframe scrolling="auto" frameborder="0"  src="' + result.url + '" style="width:100%;height:100%;"></iframe>';
                $('#mainContentTabs').tabs('add', {
                    title: result.title,
                    content: content,
                    closable: true
                });
            } else {
                alert("对不起您没有权限.");
            }
        }, "json");

    }
}

function appendSysapi() {
    uiinit.win({
        w: 500,
        iconCls: 'icon-add',
        title: "新增接口",
        url: "/dataTemplate/formTemplate/form.html"
    });
}

function editSysapi() {
    uiinit.win({
        w: 500,
        iconCls: 'icon-add',
        title: "编辑接口",
        url: "/dataTemplate/formTemplate/form.html"
    });
}
function removeSysapi() {
    var node = $('.mxsysadmintree').tree('getSelected');
    $('.mxsysadmintree').tree('remove', node.target);
}

function addTab(title, content) {
    $('#mainContentTabs').tabs('add', {
        title: title,
        content: content,
        closable: true
    });
}
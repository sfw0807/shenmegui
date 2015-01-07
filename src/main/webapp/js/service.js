/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-6-4
 * Time: 上午10:15
 * To change this template use File | Settings | File Templates.
 */
$(function () {

    var tables = {};
    var asInitVals = [];

    var panels = [$("#service_main"), $("#operation_main"), $("#interface_main"), $("#service_invoke_main")];
    var hideOthers = function hiderOthers(self) {

        for (var i = 0; i < panels.length; i++) {
            if (!panels[i].hasClass("hidden")) {
                panels[i].addClass("hidden");
            }
        }
        if (self.hasClass("hidden")) {
            self.removeClass("hidden");
        }
    };

    var getSelectedFromTable = function getSelectedFromTable(table) {
        var rowsSelected = table.$("tr.row_selected");
        var selectedDatas = [];
        for (var i = 0; i < rowsSelected.length; i++) {
            selectedDatas[i] = table.fnGetData(table.$("tr.row_selected")[i]);
        }
        return selectedDatas;
    };


    //初始化服务Grid的方法
    var initServiceTable = function initServiceTable(result) {
        //初始化对Grid的操作事件
        var columnClickEventInit = function columnClickEventInit() {
            $("#serviceTable tbody tr").unbind("click");
            $("#serviceTable tbody tr").click(function (e) {
                $(this).toggleClass("row_selected");
            });
        };
        if (tables["serviceTable"]) {
            tables["serviceTable"].fnDestroy();
        }
        //创建Grid
        tables["serviceTable"] = $("#serviceTable").dataTable({
            "aaData": result,
            "aoColumns": serviceTableLayout,
            "aoColumnDefs": [
                {
                    "mRender": function (data, type, row) {
                        if (data == "passed") {
                            return "正式";
                        }
                        if (data == "history") {
                            return "过时";
                        }
                        if (data == "submit") {
                            return "等待审核";
                        }
                        if (data == "temp") {
                            return "新增";
                        }
                    },
                    "aTargets": [ 2 ]
                },
                { "sClass": "center", "aTargets": [ 0, 1, 2, 3 ] }
            ],
            "bJQueryUI": true,
            "bAutoWidth": true,
            "sPaginationType": "full_numbers",
            "oLanguage": oLanguage,
            "fnDrawCallback": function () {
                columnClickEventInit();
            }
        });
    };
    //请求服务数据，回调实例化方法
    var result = serviceManager.getAll(initServiceTable);
    //实例化tfoot查询框
    var initServiceTableFooter = function initServiceTableFooter() {
        $("#serviceTable tfoot input").keyup(function () {
            tables["serviceTable"].fnFilter(this.value, $("#serviceTable tfoot input").index(this));
        });
        $("#serviceTable tfoot input").each(function (i) {
            asInitVals[i] = this.value;
        });
        $("#serviceTable tfoot input").focus(function () {
            if (this.className == "search_init") {
                this.className = "";
                this.value = "";
            }
        });
        $("#serviceTable tfoot input").blur(function (i) {
            if (this.value == "") {
                this.className = "search_init";
                this.value = asInitVals[$("#serviceTable tfoot input").index(this)];
            }
        });
    };
    initServiceTableFooter();

    //查询操作
    var queryOperation = function queryOperation() {
        hideOthers($("#operation_main"));
        //获取选中的服务
        var services = getSelectedFromTable(tables["serviceTable"]);
        if (!services || services.length == 0) {
            alert("请选择服务查询!");
            $("#operation_main").addClass("hidden");
            $("#service_main").removeClass("hidden");
        } else if (services.length > 1) {
            var serviceNames = "";
            for (var i = 0; i < services.length; i++) {
                serviceNames += "[";
                serviceNames += services[i]["serviceName"];
                serviceNames += "]";
            }
            alert("您选择了多个服务：" + serviceNames + "进行查询，请选择一个服务进行查询！");
            $("#operation_main").addClass("hidden");
            $("#service_main").removeClass("hidden");
        } else {
            var serviceId = services[0]["serviceId"];
            //获取操作的数据，回调操作Grid构建函数
            operationManager.getByServiceId(serviceId, initOperationTable);
        }
    };
    //初始化操作Grid的方法
    var initOperationTable = function initOperationTable(result) {
        //初始化对Grid的操作事件
        var columnClickEventInit = function columnClickEventInit() {
            $("#operationTable tbody tr").unbind("click");
            $("#operationTable tbody tr").click(function (e) {
                $(this).toggleClass("row_selected");
            });
        };
        if (tables["operationTable"]) {
            tables["operationTable"].fnDestroy();
        }
        //创建Grid
        tables["operationTable"] = $("#operationTable").dataTable({
            "aaData": result,
            "aoColumns": operationTableLayout,
            "aoColumnDefs": [
                {
                    "mRender": function (data, type, row) {
                        if (data == "passed") {
                            return "正式";
                        }
                        if (data == "history") {
                            return "过时";
                        }
                        if (data == "submit") {
                            return "等待审核";
                        }
                        if (data == "temp") {
                            return "新增";
                        }
                    },
                    "aTargets": [ 2 ]
                },
                { "sClass": "center", "aTargets": [ 0, 1, 2, 3 ] }
            ],
            "bJQueryUI": true,
            "bAutoWidth": true,
            "sPaginationType": "full_numbers",
            "oLanguage": oLanguage,
            "fnDrawCallback": function () {
                columnClickEventInit();
            }
        });
    };
    //初始化操作Grid的搜索框
    var initOperationTableFooter = function initOperationTableFooter() {
        $("#operationTable tfoot input").keyup(function () {
            tables["operationTable"].fnFilter(this.value, $("#operationTable tfoot input").index(this));
        });
        $("#operationTable tfoot input").each(function (i) {
            asInitVals[i] = this.value;
        });
        $("#operationTable tfoot input").focus(function () {
            if (this.className == "search_init") {
                this.className = "";
                this.value = "";
            }
        });
        $("#operationTable tfoot input").blur(function (i) {
            if (this.value == "") {
                this.className = "search_init";
                this.value = asInitVals[$("#operationTable tfoot input").index(this)];
            }
        });
    };
    initOperationTableFooter();

    //操作页面按照操作查询服务
    $("#queryOpServiceBtn").button().click(function () {
        var operations = getSelectedFromTable(tables["operationTable"]);
        if (operations) {
            console.log(operations);
            if (operations.length == 0) {
                alert("请选择操作！");
            } else if (operations.length > 1) {
                var operationNames = "";
                for (var i = 0; i < operations.length; i++) {
                    operationNames += "[";
                    operationNames += operations[0]["serviceName"];
                    operationNames += "]";
                }
                alert("您选择了多个操作" + operationNames + "请选择一个操作进行查询！请选择一个操作进行查询！");
            } else {
                hideOthers($("#service_main"));
                console.log(operations[0]);
                serviceManager.getByOperationId(operations[0]["serviceId"], initServiceTable);
            }
        } else {
            alert("请选择操作！");
        }
    });

    //按服务查询操作
    $("#querySvrOperationBtn").button().click(function (event) {
        queryOperation();
    });
    //查询所有的操作
    $("#queryAllOperationeBtn").button().click(function () {
        hideOthers($("#operation_main"));
        operationManager.getAll(initOperationTable);
    });
    //查询所有的系统
    $("#queryAllSysBtn").button().click(function () {
        hideOthers($("#system_main"));
        systemManager.getAll(initSystemTable);
    });
    //导出WSDL
    $('#exportWSDLBtn').button().click(function () {
        var services = getSelectedFromTable(tables["serviceTable"]);
        if (!services || services.length == 0) {
            alert("请选择服务查询!");
        } else if (services.length > 1) {
            var serviceNames = "";
            for (var i = 0; i < services.length; i++) {
                serviceNames += "[";
                serviceNames += services[i].serviceName;
                serviceNames += "]";
            }
            alert("您选择了多个服务：" + serviceNames + "进行查询，请选择一个服务进行查询！");
        } else {
            var serviceId = services[0].serviceId;
            $.fileDownload("/wsdl/byService/" + serviceId, {
                successCallback : function (url) {
                    alert("下载成功！");
                },
                failCallback : function (url) {
                    alert("fail to download wsdl");
                }
            });
        }
    });


    //查询服务调用关系
    var querySvrInvokeInfo = function querySvrInvokeInfo() {
        hideOthers($("#service_invoke_main"));
        //获取选中的服务
        var services = getSelectedFromTable(tables["serviceTable"]);
        if (!services || services.length == 0) {
            alert("请选择服务查询!");
            $("#service_invoke_main").addClass("hidden");
            $("#service_main").removeClass("hidden");
        } else if (services.length > 1) {
            var serviceNames = "";
            for (var i = 0; i < services.length; i++) {
                serviceNames += "[";
                serviceNames += services[i].serviceName;
                serviceNames += "]";
            }
            alert("您选择了多个服务：" + serviceNames + "进行查询，请选择一个服务进行查询！");
            $("#service_invoke_main").addClass("hidden");
            $("#service_main").removeClass("hidden");
        } else {
            var serviceId = services[0].serviceId;
            //获取操作的数据，回调操作Grid构建函数
            serviceManager.getInvokeInfoById(serviceId, initServiceInvokeTable);
        }
    };
    //展示服务调用关系页面按钮事件
    $("#querySvrInvokeBtn").button().click(function (event) {
        querySvrInvokeInfo();
    });
    //初始化服务调用关系Grid
    var initServiceInvokeTable = function initServiceInvokeTable(result) {
        //初始化对Grid的操作事件
        var columnClickEventInit = function columnClickEventInit() {
            $("#serviceInvokeTable tbody tr").unbind("click");
            $("#serviceInvokeTable tbody tr").click(function (e) {
                $(this).toggleClass("row_selected");
            });
        };
        if (tables["serviceInvokeTable"]) {
            tables["serviceInvokeTable"].fnDestroy();
        }
        //创建Grid
        tables["serviceInvokeTable"] = $("#serviceInvokeTable").dataTable({
            "aaData": result,
            "aoColumns": serviceInvokeTableLayout,
            "aoColumnDefs": [
                { "sClass": "center", "aTargets": [0, 1, 2, 3, 4, 5, 6 ] }
            ],
            "bJQueryUI": true,
            "bAutoWidth": true,
            "sPaginationType": "full_numbers",
            "oLanguage": oLanguage,
            "fnDrawCallback": function () {
                columnClickEventInit();
            }
        });
    };

    //查询接口
    var queryInterface = function queryInterface() {
        hideOthers($("#interface_main"));
        //获取选中的操作
        var operations = getSelectedFromTable(tables["operationTable"]);
        if (!operations || operations.length == 0) {
            alert("请选择操作查询!");
            $("#interface_main").addClass("hidden");
            $("#operation_main").removeClass("hidden");
        } else if (operations.length > 1) {
            var operationNames = "";
            for (var i = 0; i < operations.length; i++) {
                operationNames += "[";
                operationNames += operations[i].serviceName;
                operationNames += "]";
            }
            alert("您选择了多个服务：" + operationNames + "进行查询，请选择一个服务进行查询！");
            $("#interface_main").addClass("hidden");
            $("#operation_main").removeClass("hidden");
        } else {
            var operationId = operations[0].serviceId;
            //获取操作的数据，回调操作Grid构建函数
            interfaceManager.getByOperationId(operationId, initInterfaceTable);
        }
    };
    //初始化场景Grid的方法
    var initInterfaceTable = function initInterfaceTable(result) {
        //初始化对Grid的操作事件
        var columnClickEventInit = function columnClickEventInit() {
            $("#interfaceTable tbody tr").unbind("click");
            $("#interfaceTable tbody tr").click(function (e) {
                $(this).toggleClass("row_selected");
            });
        };
        if (tables["interfaceTable"]) {
            tables["interfaceTable"].fnDestroy();
        }
        //创建Grid
        tables["interfaceTable"] = $("#interfaceTable").dataTable({
            "aaData": result,
            "aoColumns": interfaceTableLayout,
            "aoColumnDefs": [
                {
                    "mRender": function (data, type, row) {
                        if (data == "0") {
                            return "消费方";
                        }
                        if (data == "1") {
                            return "提供方";
                        }
                    },
                    "aTargets": [ 5 ]
                },
                { "sClass": "center", "aTargets": [0, 1, 2, 3, 4 ] }
            ],
            "bJQueryUI": true,
            "bAutoWidth": true,
            "sPaginationType": "full_numbers",
            "oLanguage": oLanguage,
            "fnDrawCallback": function () {
                columnClickEventInit();
            }
        });
    };
    //初始化接口Grid的搜索框
    var initInterfaceTableFooter = function initInterfaceTableFooter() {
        $("#interfaceTable tfoot input").keyup(function () {
            tables["interfaceTable"].fnFilter(this.value, $("#interfaceTable tfoot input").index(this));
        });
        $("#interfaceTable tfoot input").each(function (i) {
            asInitVals[i] = this.value;
        });
        $("#interfaceTable tfoot input").focus(function () {
            if (this.className == "search_init") {
                this.className = "";
                this.value = "";
            }
        });
        $("#interfaceTable tfoot input").blur(function (i) {
            if (this.value == "") {
                this.className = "search_init";
                this.value = asInitVals[$("#interfaceTable tfoot input").index(this)];
            }
        });
    };
    initInterfaceTableFooter();
    //操作页面，查询接口
    $("#queryOpInterfaceBtn").button().click(function (e) {
        queryInterface();
    });
    //操作页面，返回服务页面
    $("#backToServiceBtn").button().click(function (e) {
        hideOthers($("#service_main"));
    });
    //服务调用关系页面，返回服务页面
    $("#sibackToServiceBtn").button().click(function () {
        hideOthers($("#service_main"));
    });

    //查询系统
    //初始化系统Grid的方法
    var initSystemTable = function initSystemTable(result) {
        //初始化对Grid的操作事件
        var columnClickEventInit = function columnClickEventInit() {
            $("#systemTable tbody tr").unbind("click");
            $("#systemTable tbody tr").click(function (e) {
                $(this).toggleClass("row_selected");
            });
        };
        if (tables["systemTable"]) {
            tables["systemTable"].fnDestroy();
        }
        //创建Grid
        tables["systemTable"] = $("#systemTable").dataTable({
            "aaData": result,
            "aoColumns": systemTableLayout,
            "aoColumnDefs": [
                { "sClass": "center", "aTargets": [0, 1, 2, 3 ] }
            ],
            "bJQueryUI": true,
            "bAutoWidth": true,
            "sPaginationType": "full_numbers",
            "oLanguage": oLanguage,
            "fnDrawCallback": function () {
                columnClickEventInit();
            }
        });
    };

    //初始化操作Grid的搜索框
    var initSystemTableFooter = function initSystemTableFooter() {
        $("#systemTable tfoot input").keyup(function () {
            tables["systemTable"].fnFilter(this.value, $("#systemTable tfoot input").index(this));
        });
        $("#systemTable tfoot input").each(function (i) {
            asInitVals[i] = this.value;
        });
        $("#systemTable tfoot input").focus(function () {
            if (this.className == "search_init") {
                this.className = "";
                this.value = "";
            }
        });
        $("#systemTable tfoot input").blur(function (i) {
            if (this.value == "") {
                this.className = "search_init";
                this.value = asInitVals[$("#systemTable tfoot input").index(this)];
            }
        });
    };
    initSystemTableFooter();

    //操作页面，查询接口
    $("#queryOpInterfaceBtn").button().click(function (e) {
        queryInterface();
    });
});

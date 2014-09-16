$(function () {
    var tables = {};
    var asInitVals = [];
    var searchBarInitVals = [];
    $('#tabs').tabs();
    $("#tab0").click(function (e) {
        if (tables.systemInvokeServiceTable) {
            tables.systemInvokeServiceTable.fnAdjustColumnSizing();
        }
    });
    /**
     * init system invoke service table
     * @param {Object} result
     * @memberOf {TypeName}
     */
    var initSISTable = function initSISTable(result) {

        var initSearchField = function initSearchField(id, callback) {
            var initValues = [];
            $("#" + id).each(function (i) {
                initValues[id] = this.value;
            });
            $("#" + id).keyup(
                function () {
                    var that = this;
                    callback(that);
                });
            $("#" + id).focus(function () {
                if (this.className === "search_init") {
                    this.className = "";
                    this.value = "";
                }
            });
            $("#" + id)
                .blur(
                function (i) {
                    if (this.value === "") {
                        this.className = "search_init";
                        this.value = initValues[id];
                    }
                });
        };
        //初始化对Grid的操作事件
        var columnClickEventInit = function columnClickEventInit() {
            $("#systemInvokeServiceTable tbody tr").unbind("click");
            $("#systemInvokeServiceTable tbody tr").click(function (e) {
                $(this).toggleClass("row_selected");
            });
        };
        //初始化ExtendsTable
        tables.systemInvokeServiceTable = $("#systemInvokeServiceTable").dataTable({
            "aaData": result,
            "aoColumns": sisTableLayout,
            "aoColumnDefs": [
                {
                    "sClass": "center",
                    "aTargets": [ 0, 1, 2, 3, 4, 5 ]
                }
            ],
            "bJQueryUI": true,
            "bAutoWidth": true,
            "sScrollY": "500px",
            "bScrollCollapse": true,
            "bPaginate": true,
            "sPaginationType": "full_numbers",
            "bSort": true,
            "oLanguage": oLanguage,
            "fnDrawCallback": function () {
                columnClickEventInit();
            }
        });
//		tables["systemInvokeServiceTable"].fnAdjustColumnSizing();
        //初始化操作Grid的搜索框
        var initSystemInvokeServiceTableFooter = function initSystemInvokeServiceTableFooter() {
            $("tfoot input").each(function (i) {
                asInitVals[i] = this.value;
            });
            $("tfoot input").keyup(
                function () {
                    tables.systemInvokeServiceTable.fnFilter(this.value, $("tfoot input").index(this));
                });

            $("tfoot input").focus(function () {
                console.log("focus");
                if (this.className === "search_init") {
                    this.className = "";
                    this.value = "";
                }
            });
            $("tfoot input")
                .blur(
                function (i) {
                    if (this.value === "") {
                        this.className = "search_init";
                        this.value = asInitVals[$("tfoot input").index(this)];
                    }
                });
        };
        initSearchField("searchProvideMsgInput");
        initSearchField("searchConsumeMsgInput");
        initSystemInvokeServiceTableFooter();
        var initSearchBar = function initSearchBar() {

        };

    };



    invokeInfoManager.getSystemInvokeServiceInfo(initSISTable);

    //需要分页处理的服务端Table,暂时没有使用
//    var initServerSideSISTable = function initSISTable(result) {
//        //初始化对Grid的操作事件
//        var columnClickEventInit = function columnClickEventInit() {
//            $("#systemInvokeServiceTable tbody tr").unbind("click");
//            $("#systemInvokeServiceTable tbody tr").click(function (e) {
//                $(this).toggleClass("row_selected");
//            });
//        };
//        //初始化ExtendsTable
//        tables["systemInvokeServiceTable"] = $("#systemInvokeServiceTable").dataTable({
//            "bProcessing": true,
//            "bServerSide": true,
//            "fnServerData": ServerSide.buildServerDataProcess("/invokeInfo/syssvc"),
//            "aoColumns": sisTableLayout,
//            "aoColumnDefs": [
//                {
//                    "sClass": "center",
//                    "aTargets": [ 0, 1, 2, 3, 4, 5 ]
//                }
//            ],
//            "bJQueryUI": true,
//            "bAutoWidth": true,
//            "sScrollY": "500px",
//            "bScrollCollapse": true,
//            "bPaginate": true,
//            "sPaginationType": "full_numbers",
//            "bSort": true,
//            "oLanguage": oLanguage,
//            "fnDrawCallback": function () {
//                columnClickEventInit();
//            }
//        });
////		tables["systemInvokeServiceTable"].fnAdjustColumnSizing();
//        //初始化操作Grid的搜索框
//        var initSystemInvokeServiceTableFooter = function initSystemInvokeServiceTableFooter() {
//            $("#systemInvokeServiceTable tfoot input").keyup(
//                function () {
//                    tables["systemInvokeServiceTable"].fnFilter(this.value, $(
//                        "#systemInvokeServiceTable tfoot input").index(this));
//                });
//            $("#systemInvokeServiceTable tfoot input").each(function (i) {
//                asInitVals[i] = this.value;
//            });
//            $("#systemInvokeServiceTable tfoot input").focus(function () {
//                if (this.className === "search_init") {
//                    this.className = "";
//                    this.value = "";
//                }
//            });
//            $("#systemInvokeServiceTable tfoot input")
//                .blur(
//                function (i) {
//                    if (this.value === "") {
//                        this.className = "search_init";
//                        this.value = asInitVals[$(
//                            "#systemInvokeServiceTable tfoot input")
//                            .index(this)];
//                    }
//                });
//        };
//        initSystemInvokeServiceTableFooter();
//    };
});

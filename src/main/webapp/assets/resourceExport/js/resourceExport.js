$(function () {
    var tables = [];
    var initConfigExportTable = function initConfigExportTable(result) {
        //初始化对Grid的操作事件
        var columnClickEventInit = function columnClickEventInit() {
            $("#configExportTable tbody tr").unbind("click");
            $("#configExportTable tbody tr").click(function (e) {
                $(this).toggleClass("row_selected");
            });
        };
        //初始化configExportTable
        tables["configExportTable"] = $("#configExportTable").dataTable({
            "aaData": result,
            "aoColumns": ,
            "aoColumnDefs": [
                {
                    "sClass": "center",
                    "aTargets": [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]
                },
                {
                    "bVisible": false,
                    "aTargets": [7, 8, 11]
                },
                {
                    "mRender": function (data, type, row) {
                        return '<a href="operationInfoById.jsp?operationId=' + row["operationId"]
                            + '&serviceId=' + row["serviceId"]
                            + '&version=' + row["version"]
                            + '&publishVersion=' + row["publishVersion"]
                            + '&publishDate=' + row["publishDate"]
                            + '">' + '修改' + '</a>';
                    },
                    "aTargets": [9]
                },
                {
                    "mRender": function (data, type, row) {
                        return '<a href="operationHistory.jsp?operationId=' + row["operationId"]
                            + '&serviceId=' + row["serviceId"]
                            + '&version=' + row["version"]
                            + '">' + '查看' + '</a>';
                    },
                    "aTargets": [10]
                }
            ],
            "bJQueryUI": true,
            "bAutoWidth": true,
            "bScrollCollapse": "full_numbers",
            "bPaginate": true,
            "bSort": true,
            "bFilter": true,
            "bSearchable": true,
            "oLanguage": oLanguage,
            "fnDrawCallback": function () {
                columnClickEventInit();
            }
        });
        tables["configExportTable"].fnAdjustColumnSizing();
    };
    operationManager.getAll(initConfigExportTable);

});
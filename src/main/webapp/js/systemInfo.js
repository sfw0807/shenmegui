$(function () {
    var tables = {};
    var isChrome = (navigator.appVersion.indexOf("Chrome") != -1) ? true : false;
    var asInitVals = [];
    var params = {
        sysId: '',
        sysAb: '',
        sysName: '',
        remark: '',
        firstTime: '',
        secondTime: '',
        maxCon: '',
        avgTime: '',
        tmOut: '',
        sucRate: '',
        outMaxCon: '',
        type: ''
    };
    $('#tabs').tabs();
    $("#tab0").click(function (e) {
        if (tables["systemInfoTable"]) {
            tables["systemInfoTable"].fnAdjustColumnSizing();
        }
    });
    /**
     * init systemInfo table
     * @param {Object} result
     *
     */
    var initsystemInfoTable = function initsystemInfoTable(result) {
        //初始化systemInfoTable
        if (tables["systemInfoTable"]) {
            tables["systemInfoTable"].fnDestroy();
        }
        //初始化对Grid的操作事件
        var columnClickEventInit = function columnClickEventInit() {
            $("#systemInfoTable tbody tr").unbind("click");
            $("#systemInfoTable tbody tr").click(function (e) {
                if ($(this).find("td").hasClass("dataTables_empty")) {
                }
                else {
                    $(this).toggleClass("row_selected");
                }
            });
        };
        tables["systemInfoTable"] = $("#systemInfoTable").dataTable({
            "aaData": result,
            "aoColumns": systemInfoTableLayout,
            "aoColumnDefs": [
                {
                    "sClass": "center",
                    "aTargets": [ 0, 1, 2, 3, 4, 5]
                }
            ],
            "bJQueryUI": "true",
            "bAutoWidth": "true",
            "bScrollCollapse": "full_numbers",
            "bPaginate": "true",
            "bSort": "true",
            "oLanguage": oLanguage,
            "fnDrawCallback": function () {
                columnClickEventInit();
            }
        });
        tables["systemInfoTable"].fnAdjustColumnSizing();
    };
    systemInfoManager.getAll(initsystemInfoTable);

    //初始化操作Grid的搜索框
    var initsystemInfoTableFooter = function initsystemInfoTableFooter() {
        $("#systemInfoTable tfoot input").keyup(
            function () {
                tables["systemInfoTable"].fnFilter(this.value, $(
                    "#systemInfoTable tfoot input").index(this), null, null, null, false);
            });
        $("#systemInfoTable tfoot input").each(function (i) {
            asInitVals[i] = this.value;
        });
        $("#systemInfoTable tfoot input").focus(function () {
            if (this.className == "search_init") {
                this.className = "";
                this.value = "";
            }
        });
        $("#systemInfoTable tfoot input")
            .blur(
            function (i) {
                if (this.value == "") {
                    this.className = "search_init";
                    this.value = asInitVals[$(
                        "#systemInfoTable tfoot input")
                        .index(this)];
                }
            });
    };
    initsystemInfoTableFooter();

    // 删除系统
    $('#del').button().click(function () {
        var delids = '';
        $("#systemInfoTable tbody tr").each(function () {
            if ($(this).hasClass("row_selected")) {
                delids += $(this).find("td").eq(0).text() + ",";
            }
        });
        if (delids == "") {
            alert('请选择删除的记录!');
            return false;
        }
        delids = delids.substring(0, delids.length - 1);
        function delResult(result) {
            if (result) {
                alert('删除成功!');
                systemInfoManager.getAll(initsystemInfoTable);
                initsystemInfoTableFooter();
            }
            else {
                alert('删除失败!');
            }
        }

        if (confirm('确定删除系统' + delids + '?')) {
            function checkIsExists(result) {
                if (result != null && result != "") {
                    alert('系统' + result + '已被使用，不能删除!');
                }
                else {
                    systemInfoManager.deleteById(delids, delResult);
                }
            }

            systemInfoManager.checkSystemUsed(delids, checkIsExists);
        }
    });

    // 新增系统
    $("#add").button().click(function () {
        params.sysId = '@$';
        params.type = 'add';
        // 打开窗口。 具有returnValue传参功能， 置于showModalDialog后面的代码，在关闭打开的窗口后执行
        if (isChrome) {
            var winOption = "height=800px,width=1200px,top=50,scrollbars=yes,resizable=yes,fullscreen=0";
            return  window.open("../jsp/systemInfoAttr.jsp?type=" + params.type + "&sysId="+ params.sysId, window, winOption);
        } else {
            window.showModalDialog('../jsp/systemInfoAttr.jsp', params, 'dialogWidth:1050px;dialogHeight:500px');
        }

        systemInfoManager.getAll(initsystemInfoTable);
        initsystemInfoTableFooter();
    });

    // 修改系统
    $('#modify').button().click(function () {
        var table = tables["systemInfoTable"];
        // 选择的行数
        var rowsSelected = table.$("tr.row_selected");
        if (rowsSelected.length == 0) {
            alert('请选择修改的系统!');
            return false;
        }
        if (rowsSelected.length > 1) {
            alert('请只选择一条记录修改!');
            return false;
        }
        var selectedDatas;
        selectedDatas = table.fnGetData(table.$("tr.row_selected")[0]);
        params.type = 'modify';
        params.sysId = selectedDatas["systemId"];
        params.sysAb = selectedDatas["systemAb"];
        params.sysName = selectedDatas["systemName"];
        params.remark = selectedDatas["remark"];
        params.firstTime = selectedDatas["firstPublishDate"];
        params.secondTime = selectedDatas["secondPublishDate"];
        params.maxCon = selectedDatas["maxConNum"];
        params.avgTime = selectedDatas["avgResTime"];
        params.tmOut = selectedDatas["timeOut"];
        params.sucRate = selectedDatas["successRate"];
        params.outMaxCon = selectedDatas["outMaxConNum"];
        // 打开窗口。 具有returnValue传参功能， 置于showModalDialog后面的代码，在关闭打开的窗口后执行
        if (isChrome) {
            var winOption = "height=800px,width=1200px,top=50,scrollbars=yes,resizable=yes,fullscreen=0";
            return  window.open("../jsp/systemInfoAttr.jsp?type=" + params.type + "&sysId="+ params.sysId, window, winOption);
        } else {
            window.showModalDialog('../jsp/systemInfoAttr.jsp', params, 'dialogWidth:1050px;dialogHeight:500px');
        }

        systemInfoManager.getAll(initsystemInfoTable);
        initsystemInfoTableFooter();
    });

});

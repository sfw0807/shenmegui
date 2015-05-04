$(function () {
    var tables = {};
    var isChrome = (navigator.appVersion.indexOf("Chrome") != -1) ? true : false;
    var asInitVals = [];
    $('#tabs').tabs();
    $("#tab1").click(function (e) {
        if (tables["operationSDATable"]) {
            tables["operationSDATable"].fnAdjustColumnSizing();
        }
    });
    $("#tab2").click(function (e) {
        if (tables["operationSlaTable"]) {
            tables["operationSlaTable"].fnAdjustColumnSizing();
        }

    });
    $("#tab3").click(function (e) {
        if (tables["operationOlaTable"]) {
            tables["operationOlaTable"].fnAdjustColumnSizing();
        }
    });
    var initOperationInfo = function initOperationInfo(result) {
        $('#operationId').val(result.operationId);
        $('#serviceId').val(serviceId);
        $('#operationName').val(result.operationName);
        $('#operationRemark').val(result.remark);
        $('#state').val(result.state);
        $('#version').val(result.version);
    };

    var slaList = [];

    //是否有子节点
    var hasChild = function hasChild(Node) {
        var flag = false;
        for (var i = 0; i < sdaNode.length; i++) {
            if (sdaNode[i]["parentId"] == Node["id"]) {
                flag = true;
                break;
            }
        }
        return flag;
    };
    var maxSeqOfBrotherNode = 0;
    var getMaxBrotherSeq = function getMaxBrotherSeq(Node) {
        for (var i = 0; i < sdaNode.length; i++) {
            if (sdaNode[i]["parentId"] == Node["parentId"]) {
                if (maxSeqOfBrotherNode < sdaNode[i]["seq"]) {
                    maxSeqOfBrotherNode = sdaNode[i]["seq"];
                }
            }
        }
//		console.log("最大兄弟节点的seq:"+maxSeqOfBrotherNode);
        return maxSeqOfBrotherNode;
    };
    //插入位置计数器
    var count = 0;
    //迭代方法：添加子节点时数目
    var getChildCount = function getChildCount(Node) {
        if (hasChild(Node)) {
            for (var i = 0; i < sdaNode.length; i++) {
                if (sdaNode[i]["parentId"] == Node["id"]) {
                    count++;
                    if (hasChild(sdaNode[i])) {
                        count = count + getChildCount(sdaNode[i]) - 1;
                    }
                }
            }
        }
        return count;
    };
    var getBrotherCount = function getBrotherCount(Node) {
        if (hasChild(Node)) {
            for (var i = 0; i < sdaNode.length; i++) {
                if (sdaNode[i]["parentId"] == Node["id"]) {
                    count++;
                    if (hasChild(sdaNode[i])) {
                        count = count + getChildCount(sdaNode[i]) - 1;
                    }
                }
            }
        }
        return count;
    };

    // 从URL中得到operationId
    var href = window.location.href;
    var operationId = href.split("&")[0].split("=")[1];
    var serviceId = href.split("&")[1].split("=")[1];
    var version = href.split("&")[2].split("=")[1];
    var publishVersion = "";
    var publishDate = "";
    if (href.split("&").length >= 4) {
        publishVersion = href.split("&")[3].split("=")[1];
        publishDate = href.split("&")[4].split("=")[1];
    }
    if (operationId.split("%27").length >= 2) {
        operationId = operationId.split("%27")[1];
    }
    if (serviceId.split("%27").length >= 2) {
        serviceId = serviceId.split("%27")[1];
    }
    if (version.split("%27").length >= 2) {
        version = version.split("%27")[1];
    }
    if (publishVersion.split("%27").length >= 2) {
        publishVersion = publishVersion.split("%27")[1];
    }
    if (publishDate.split("%27").length >= 2) {
        publishDate = publishDate.split("%27")[1];
    }
    operationManager.getOperation(operationId, serviceId, initOperationInfo);

    //初始化SDA表格的方法
    var initChildTable = function initChildTable(result) {
        sdaNode = result;
        //初始化对Grid的操作事件
        var columnClickEventInit = function columnClickEventInit() {
            $("#operationSDATable tbody tr").unbind("click");
            $("#operationSDATable tbody tr").click(function (e) {
                $(this).toggleClass("row_selected");
            });
        };
        //创建Grid
        tables["operationSDATable"] = $("#operationSDATable").dataTable({
            "aaData": result,
            "aoColumns": operationSDATableLayout,
            "aoColumnDefs": [
                {
                    "sClass": "center",
                    "aTargets": [0, 2, 3, 4, 5]
                },
                {
                    "bSortable": false,
                    "aTargets": [1, 2, 3, 4, 5]
                },
                {
                    "mRender": function (data, type, row) {
                        return "<nobr>" + data + "</nobr>";
                    },
                    "aTargets": [ 1, 2, 3]
                },
                {
                    "bVisible": false,
                    "aTargets": [6, 7]
                }
            ],
            "fnRowCallback": function (nRow, aData, iDisplayIndex) {

                //console.log(aData);
                if (aData["structId"].indexOf("|--") == 0) {
                    $(nRow).css("background-color", "chocolate");
                }
                if (aData["structId"].indexOf("|--request") > 0) {
                    $(nRow).css("background-color", "darkkhaki");
                }
                if (aData["structId"].indexOf("|--response") > 0) {
                    $(nRow).css("background-color", "darkkhaki");
                }
                if (aData["structId"].indexOf("|--SvcBody") > 0) {
                    $(nRow).css("background-color", "burlywood");
                }
                if (aData["type"] == "array") {
                    $(nRow).css("background-color", "gold");
                }

            },
            "bJQueryUI": true,
            "bAutoWidth": true,
            "bScrollCollapse": true,
            "bPaginate": false,
            "bSort": true,
            "oLanguage": oLanguage,
            "fnDrawCallback": function () {
                columnClickEventInit();
            }
        });
        tables["operationSDATable"].fnAdjustColumnSizing(true);

    };
    //operationManager.getSDAInfoByOperationId(operationId,serviceId, initChildTable);

    //初始化操作Grid的搜索框
    var initoperationSDATableFooter = function initoperationSDATableFooter() {
        $("#operationSDATable tfoot input").keyup(
            function () {
                tables["operationSDATable"].fnFilter(this.value, $(
                    "#operationSDATable tfoot input").index(this));
            });
        $("#operationSDATable tfoot input").each(function (i) {
            asInitVals[i] = this.value;
        });
        $("#operationSDATable tfoot input").focus(function () {
            if (this.className == "search_init") {
                this.className = "";
                this.value = "";
            }
        });
        $("#operationSDATable tfoot input").blur(
            function (i) {
                if (this.value == "") {
                    this.className = "search_init";
                    this.value = asInitVals[$(
                        "#operationSDATable tfoot input").index(this)];
                }
            });
    };
    //initoperationSDATableFooter();

    //初始化SLA表格的方法
    var initoperationSlaTable = function initoperationSlaTable(result) {
        //初始化对Grid的操作事件
        var columnClickEventInit = function columnClickEventInit() {
            $("#operationSlaTable tbody tr").unbind("click");
            $("#operationSlaTable tbody tr").click(function (e) {
                $(this).toggleClass("row_selected");
            });
        };
        //创建Grid
        tables["operationSlaTable"] = $("#operationSlaTable").dataTable({
            "aaData": result,
            "aoColumns": operationSlaTableLayout,
            "aoColumnDefs": [
                {
                    "sClass": "center",
                    "aTargets": [0, 1, 2]
                }
            ],
            "bJQueryUI": true,
            "bAutoWidth": true,
            "bScrollCollapse": true,
            "bPaginate": false,
            "bSort": false,
            "oLanguage": oLanguage,
            "fnDrawCallback": function () {
                columnClickEventInit();
            }
        });
        tables["operationSlaTable"].fnAdjustColumnSizing();
    };
    operationManager.getSLAByOperationId(operationId, serviceId, initoperationSlaTable);

    //初始化操作Grid的搜索框
    var initoperationSlaTableFooter = function initoperationSlaTableFooter() {
        $("#operationSlaTable tfoot input").keyup(
            function () {
                tables["operationSlaTable"].fnFilter(this.value, $(
                    "#operationSlaTable tfoot input").index(this));
            });
        $("#operationSlaTable tfoot input").each(function (i) {
            asInitVals[i] = this.value;
        });
        $("#operationSlaTable tfoot input").focus(function () {
            if (this.className == "search_init") {
                this.className = "";
                this.value = "";
            }
        });
        $("#operationSlaTable tfoot input").blur(
            function (i) {
                if (this.value == "") {
                    this.className = "search_init";
                    this.value = asInitVals[$(
                        "#operationSlaTable tfoot input").index(this)];
                }
            });
    };
    initoperationSlaTableFooter();

    //初始化OLA表格的方法
    var initoperationOlaTable = function initoperationOlaTable(result) {
        //初始化对Grid的操作事件
        var columnClickEventInit = function columnClickEventInit() {
            $("#operationOlaTable tbody tr").unbind("click");
            $("#operationOlaTable tbody tr").click(function (e) {
                $(this).toggleClass("row_selected");
            });
        };
        //创建Grid
        tables["operationOlaTable"] = $("#operationOlaTable").dataTable({
            "aaData": result,
            "aoColumns": operationOlaTableLayout,
            "aoColumnDefs": [
                {
                    "sClass": "center",
                    "aTargets": [0, 1, 2]
                }
            ],
            "bAutoWidth": true,
            "bJQueryUI": true,
            "bScrollCollapse": true,
            "bPaginate": false,
            "bSort": false,
            "oLanguage": oLanguage,
            "fnDrawCallback": function () {
                columnClickEventInit();
            }
        });
        tables["operationOlaTable"].fnAdjustColumnSizing();
    };
    operationManager.getOLAByOperationId(operationId, serviceId, initoperationOlaTable);

    //初始化操作Grid的搜索框
    var initoperationOlaTableFooter = function initoperationOlaTableFooter() {
        $("#operationOlaTable tfoot input").keyup(
            function () {
                tables["operationOlaTable"].fnFilter(this.value, $(
                    "#operationOlaTable tfoot input").index(this));
            });
        $("#operationOlaTable tfoot input").each(function (i) {
            asInitVals[i] = this.value;
        });
        $("#operationOlaTable tfoot input").focus(function () {
            if (this.className == "search_init") {
                this.className = "";
                this.value = "";
            }
        });
        $("#operationOlaTable tfoot input").blur(
            function (i) {
                if (this.value == "") {
                    this.className = "search_init";
                    this.value = asInitVals[$(
                        "#operationOlaTable tfoot input").index(this)];
                }
            });
    };
    initoperationOlaTableFooter();

    //保存基本服务定义
    $("#saveOperationDef").click(function () {
        var params = {
            operationId: $("#operationId").val(),
            serviceId: serviceId,
            operationName: $("#operationName").val(),
            remark: $("#operationRemark").val(),
            state: $("#state").val(),
            version: $("#version").val()
        };
        operationManager.saveOperationDef(params);
    });
    //添加SDA根节点
    $("#addRootOperationSDA").click(function () {
        var table = tables["operationSDATable"];
        var row = table.dataTable().fnAddData({
            "seq": "<input type='text' onclick='window.event.stopPropagation();' />",
            "structId": "<input type='text' onclick='window.event.stopPropagation();' />",
            "metadataId": "<input type='text' onclick='window.event.stopPropagation();' />",
            "type": "<input type='text' onclick='window.event.stopPropagation();' />",
            "required": "<input type='text' onclick='window.event.stopPropagation();' />",
            "remark": "<input type='text' onclick='window.event.stopPropagation();' />"
        });
        onModifyOLARow.push(row);
        for (var i = 0; i < onModifyOLARow.length; i++) {
            var tr = table.fnGetNodes(onModifyOLARow[i]);
            $(tr).click(function (e) {
                e.stopPropagation();
            });
        }
    });
    var onModifySDAChildrenRow = [];
    var onModifySDABrotherRow = [];
    //编辑SDA节点
    $("#editChildOperationSDA").click(function () {
        onModifySDAChildrenRow = [];
        onModifySDABrotherRow = [];
        var table = tables["operationSDATable"];
        var rowsSelected = table.$("tr.row_selected");
        if (rowsSelected.length == 1) {
            var seq = rowsSelected[0].cells[0].innerText;
            var structId = rowsSelected[0].cells[1].innerText;
            var metadataId = rowsSelected[0].cells[2].innerText;
            var type = rowsSelected[0].cells[3].innerText;
            var required = rowsSelected[0].cells[4].innerText;
            var remark = rowsSelected[0].cells[5].innerText;
            rowsSelected[0].cells[1].innerHTML = "<nobr><input type='text' onclick='window.event.stopPropagation();' value='" + structId + "' /></nobr>";
            rowsSelected[0].cells[2].innerHTML = "<nobr><input type='text' onclick='window.event.stopPropagation();' value='" + metadataId + "' /></nobr>";
            rowsSelected[0].cells[3].innerHTML = "<nobr><input type='text' onclick='window.event.stopPropagation();' value='" + type + "' /></nobr>";
            rowsSelected[0].cells[4].innerHTML = "<input type='text' onclick='window.event.stopPropagation();' value='" + required + "' />";
            rowsSelected[0].cells[5].innerHTML = "<input type='text' onclick='window.event.stopPropagation();' value='" + remark + "' >";
        } else {
            alert("只能选中一行SDA数据!");
        }
    });

    //添加SDA子节点
    $("#addChildOperationSDA").click(function () {
        var table = tables["operationSDATable"];
        var rowsSelected = table.$("tr.row_selected");
        if (rowsSelected.length != 1) {
            alert("只能选中一个父节点!");
        } else if (rowsSelected[0].cells[3].innerText == "array" || rowsSelected[0].cells[1].innerText.indexOf("SvcBody") > 0) {
            console.log("sda length before:" + sdaNode.length);
            selectedDatas = table.fnGetData(rowsSelected[0]);
            var structId = rowsSelected[0].cells[1].innerText;
            var index = rowsSelected[0].cells[0].innerText;
            var id = Math.random() * 100000000000000000 + ''
                + Math.random() * 100000000000000000;
            id = id.substr(0, 32);
            var parentId = "";
            var childCount = 0;
            var content = "&nbsp;&nbsp;&nbsp;&nbsp;" + structId.substr(0, structId.lastIndexOf("-") + 1);
            //获取父节点
            for (var i = 0; i < sdaNode.length; i++) {
                if (sdaNode[i]["seq"] == index) {
                    parentId = sdaNode[i]["id"];
                    break;
                }
            }
            //获取插入位置与父节点的间隔
            for (var j = 0; j < sdaNode.length; j++) {
                if (sdaNode[j]["parentId"] == parentId) {
                    childCount++;
                    childCount = childCount + getChildCount(sdaNode[j]);
                }
            }
            var insert = parseInt(index) + parseInt(childCount);
            //存储新增节点数据
            sdaNode.push({
                id: id,
                structId: "<input type='text' onclick='window.event.stopPropagation();' value='" + content + "'/>",
                metadataId: "<input type='text' onclick='window.event.stopPropagation();'/>",
                type: "<input type='text' onclick='window.event.stopPropagation();'/>",
                seq: insert,
                required: "<input type='text' onclick='window.event.stopPropagation();'/>",
                remark: "<input type='text' onclick='window.event.stopPropagation();'>",
                serviceId: serviceId,
                operationId: operationId,
                parentId: parentId
            });
            console.log("sda length after:" + sdaNode.length);
            //对sdaNode按照seq进行排序
            for (var i = 0; i < sdaNode.length; i++) {
                for (var j = i; j < sdaNode.length; j++) {
                    if (sdaNode[i]["seq"] > sdaNode[j]["seq"]) {
                        var temp = sdaNode[i];
                        sdaNode[i] = sdaNode[j];
                        sdaNode[j] = temp;
                    }
                }
            }
            //从新插入位置起之后seq各加一
            for (var i = insert; i < sdaNode.length; i++) {
                sdaNode[i]["seq"] = sdaNode[i]["seq"] + 1;
            }
            //重新初始化表格
            table.fnDestroy();
            initChildTable(sdaNode);
            $("#operationSDATable").removeAttr("style");
            //刷新表格顺序一列数据
            var tableObj = document.getElementById("operationSDATable");
            //屏蔽父容器的click事件
            if (null != onModifySDAChildrenRow) {
                onModifySDAChildrenRow.push(tableObj.rows[insert + 1]);
                for (var i = 0; i < onModifySDAChildrenRow.length; i++) {
                    var tr = table.fnGetNodes(onModifySDAChildrenRow[i]);
                    $(tr).click(function (e) {
                        e.stopPropagation();
                    });
                }
            }
            //初始化计数变量
            childCount = 0;
            count = 0;
        } else {
            alert("此节点不能作为父节点");
        }
    });

    //添加SDA兄弟节点
    $("#addBrotherOperationSDA").click(function () {
        var table = tables["operationSDATable"];
        var rowsSelected = table.$("tr.row_selected");
        if (rowsSelected.length != 1) {
            alert("只能选中一个兄弟节点!");
        } else if (rowsSelected[0].cells[1].innerText.indexOf(operationId) < 0
            && rowsSelected[0].cells[1].innerText.indexOf("request") < 0
            && rowsSelected[0].cells[1].innerText.indexOf("response") < 0
            && rowsSelected[0].cells[1].innerText.indexOf("SvcBody") < 0) {
            selectedDatas = table.fnGetData(rowsSelected[0]);
            var structId = rowsSelected[0].cells[1].innerText;
            var index = rowsSelected[0].cells[0].innerText;
            var id = Math.random() * 100000000000000000 + ''
                + Math.random() * 100000000000000000;
            id = id.substr(0, 32);
            var parentId = "";
            var childCount = 0;
            var insert = 0;
            var content = structId.substr(0, structId.lastIndexOf("-") + 1);
            //获取父节点
            for (var i = 0; i < sdaNode.length; i++) {
                if (sdaNode[i]["seq"] == index) {
                    parentId = sdaNode[i]["parentId"];
                    break;
                }
            }
            var maxBrotherSeq = 0;
            //获取最大兄弟节点seq
            for (var i = 0; i < sdaNode.length; i++) {
                if (sdaNode[i]["seq"] == index) {
                    maxBrotherSeq = getMaxBrotherSeq(sdaNode[i]);
                    break;
                }
            }
            //根据最大兄弟节点获取插入位置
            if (hasChild(sdaNode[maxBrotherSeq - 1])) {
                childCount = getBrotherCount(sdaNode[maxBrotherSeq - 1]);
                insert = maxBrotherSeq + childCount;
            } else {
                insert = maxBrotherSeq;
            }
            //存储新增的sda节点
            sdaNode.push({
                id: id,
                structId: "<input type='text' onclick='window.event.stopPropagation();' value='" + content + "'/>",
                metadataId: "<input type='text' onclick='window.event.stopPropagation();'/>",
                type: "<input type='text' onclick='window.event.stopPropagation();'/>",
                seq: insert,
                required: "<input type='text' onclick='window.event.stopPropagation();'/>",
                remark: "<input type='text' onclick='window.event.stopPropagation();'>",
                serviceId: serviceId,
                operationId: operationId,
                parentId: parentId
            });
            //对存储的sda数据按照seq进行排序
            for (var i = 0; i < sdaNode.length; i++) {
                for (var j = i; j < sdaNode.length; j++) {
                    if (sdaNode[i]["seq"] > sdaNode[j]["seq"]) {
                        var temp = sdaNode[i];
                        sdaNode[i] = sdaNode[j];
                        sdaNode[j] = temp;
                    }
                }
            }
            //从插入位置起seq各加一
            for (var i = insert; i < sdaNode.length; i++) {
                sdaNode[i]["seq"] = sdaNode[i]["seq"] + 1;
            }
            //重新加载表格
            table.fnDestroy();
            initChildTable(sdaNode);
            $("#operationSDATable").removeAttr("style");
            var tableObj = document.getElementById("operationSDATable");
            //屏蔽父容器的click事件
            if (null != onModifySDAChildrenRow) {
                onModifySDAChildrenRow.push(tableObj[insert + 1]);
                for (var i = 0; i < onModifySDAChildrenRow.length; i++) {
                    var tr = table.fnGetNodes(onModifySDAChildrenRow[i]);
                    $(tr).click(function (e) {
                        e.stopPropagation();
                    });
                }
            }
            //初始化变量
            childCount = 0;
            count = 0;
            insert = 0;
            maxBrotherSeq = 0;
            maxSeqOfBrotherNode = 0;
        } else {
            alert("此节点不能作为兄弟节点");
        }
    });
    //删除SDA节点
    $("#deleteOperationSDA").click(function () {
        var table = tables["operationSDATable"];
        var rowsSelected = table.$("tr.row_selected");
        if (rowsSelected.length > 0) {
            for (var i = 0; i < rowsSelected.length; i++) {
                table.fnDeleteRow(rowsSelected[i]);
            }
        } else {
            alert("请选中至少一个节点!");
        }
        var rowsAll = tables["operationSDATable"].$("tr");
        for (var i = 0; i < rowsAll.length; i++) {
            var sdaArray = [];
            var params = {
                id: table.fnGetData(rowsAll[i])["id"],
                structId: rowsAll[i].cells[1].innerText,
                metadataId: rowsAll[i].cells[2].innerText,
                type: rowsAll[i].cells[3].innerText,
                seq: rowsAll[i].cells[0].innerText,
                required: rowsAll[i].cells[4].innerText,
                remark: rowsAll[i].cells[5].innerText,
                serviceId: serviceId,
                operationId: operationId,
                parentId: table.fnGetData(rowsAll[i])["parentId"]
            };
            sdaArray.push(params);
        }
        sdaNode = sdaArray;
    });
    //保存全部SDA信息
    $("#saveOperationSDA").click(function () {
        var sdaArray = [];
        var rowsAll = tables["operationSDATable"].$("tr");
        var table = tables["operationSDATable"];
        for (var i = 0; i < rowsAll.length; i++) {
            var params = {
                id: table.fnGetData(rowsAll[i])["id"],
                structId: rowsAll[i].cells[1].innerText,
                metadataId: rowsAll[i].cells[2].innerText,
                type: rowsAll[i].cells[3].innerText,
                seq: rowsAll[i].cells[0].innerText,
                required: rowsAll[i].cells[4].innerText,
                remark: rowsAll[i].cells[5].innerText,
                serviceId: serviceId,
                operationId: operationId,
                parentId: table.fnGetData(rowsAll[i])["parentId"]
            };
            sdaArray.push(params);
        }
        operationManager.saveOperationSDA(sdaArray);
    });
    //完成编辑：遍历表格所有cell,如有编辑框则取出其值赋给cell
    $("#operationSDATable").click(function () {
        var rowsAll = tables["operationSDATable"].$("tr");
        for (var i = 0; i < rowsAll.length; i++) {
            for (var j = 0; j < rowsAll[i].cells.length; j++) {
                if (j > 0 && j < 4) {
                    if (rowsAll[i].cells[j].hasChildNodes()) {
                        if (rowsAll[i].cells[j].children[0] != undefined) {
                            if (!rowsAll[i].cells[j].children[0].hasChildNodes() && rowsAll[i].cells[j].children[0] == "INPUT") {
                                rowsAll[i].cells[j].innerText = rowsAll[i].cells[j].children[0].children[0].value;
                            } else {
                                if (rowsAll[i].cells[j].children[0].children[0] != undefined) {
                                    rowsAll[i].cells[j].innerText = rowsAll[i].cells[j].children[0].children[0].value;
                                }
                            }

                        }
                    }
                } else {
                    if (rowsAll[i].cells[j].hasChildNodes()) {
                        if (rowsAll[i].cells[j].children[0] != undefined) {
                            rowsAll[i].cells[j].innerText = rowsAll[i].cells[j].children[0].value;
                        }
                    }
                }

            }
        }
        onModifySDAChildrenRow = [];
        onModifySDABrotherRow = [];
    });
    //编辑SLA信息
    $("#editOperationSLA").click(function () {
        var table = tables["operationSlaTable"];
        var rowsSelected = table.$("tr.row_selected");
        if (rowsSelected.length == 1) {
            var slaName = rowsSelected[0].cells[0].innerText;
            var slaValue = rowsSelected[0].cells[1].innerText;
            var slaRemark = rowsSelected[0].cells[2].innerText;
            rowsSelected[0].cells[0].innerHTML = "<input type='text' value='" + slaName + "' onblur='this.parentNode.innerHTML=this.value;'>";
            rowsSelected[0].cells[1].innerHTML = "<input type='text' value='" + slaValue + "' onblur='this.parentNode.innerHTML=this.value;'>";
            rowsSelected[0].cells[2].innerHTML = "<input type='text' value='" + slaRemark + "' onblur='this.parentNode.innerHTML=this.value;'>";
        } else {
            alert("只能选中一行SLA数据!");
        }
    });
    var onModifySLARow = [];
    //添加SLA信息
    $("#addOperationSLA").click(function () {
        var table = tables["operationSlaTable"];
        var row = table.dataTable().fnAddData({
            "slaName": "<input type='text'  onclick='window.event.stopPropagation();' onblur='this.parentNode.innerHTML=this.value;'/>",
            "slaValue": "<input type='text'  onclick='window.event.stopPropagation();' onblur='this.parentNode.innerHTML=this.value;'/>",
            "slaRemark": "<input type='text'  onclick='window.event.stopPropagation();' onblur='this.parentNode.innerHTML=this.value;'/>"
        });
        if (null != onModifySLARow) {
            onModifySLARow.push(row);
            for (var i = 0; i < onModifySLARow.length; i++) {
                var tr = table.fnGetNodes(onModifySLARow[i]);
                $(tr).click(function (e) {
                    e.stopPropagation();
                });
            }
        }
    });
    //删除SLA信息
    $("#deleteOperationSLA").click(function () {
        var table = tables["operationSlaTable"];
        var rowsSelected = table.$("tr.row_selected");
        if (rowsSelected.length > 0) {
            for (var i = 0; i < rowsSelected.length; i++) {
                table.fnDeleteRow(rowsSelected[i]);
            }
        } else {
            alert("请选中至少一行SLA数据!");
        }
    });
    //保存SLA信息
    $("#saveOperationSLA").click(function () {
        var array = [];
        var rowsAll = tables["operationSlaTable"].$("tr");
        for (var i = 0; i < rowsAll.length; i++) {
            var params = {
                serviceId: serviceId,
                operationId: operationId,
                slaName: rowsAll[i].cells[0].innerText,
                slaValue: rowsAll[i].cells[1].innerText,
                slaRemark: rowsAll[i].cells[2].innerText
            };
            array.push(params);
        }
        //如果SLA信息为空，则删除所有的SLA
        if(array.length == 0){
            var deleteSLACallBack = function () {
                alert("删除完成");
            };
            operationManager.deleteOperationAllSLA(serviceId, operationId, deleteSLACallBack);
        }else{
            operationManager.saveOperationSLA(array);
        }
    });
    //完成编辑：遍历表格所有cell,如有编辑框则取出其值赋给cell
    $("#operationSlaTable").click(function () {
        var target = window.event.srcElement;
        var rowsAll = tables["operationSlaTable"].$("tr");
        for (var i = 0; i < rowsAll.length; i++) {
            for (var j = 0; j < rowsAll[i].cells.length; j++) {
                if (rowsAll[i].cells[j].hasChildNodes()) {
                    if (rowsAll[i].cells[j].children[0] != undefined) {
                        rowsAll[i].cells[j].width = "532px";
                        rowsAll[i].cells[j].height = "20px";
                        rowsAll[i].cells[j].innerText = rowsAll[i].cells[j].children[0].value;
                    }
                }
            }
        }
    });
    //编辑OLA信息
    $("#editOperationOLA").click(function () {
        var table = tables["operationOlaTable"];
        var rowsSelected = table.$("tr.row_selected");
        if (rowsSelected.length == 1) {
            var olaName = rowsSelected[0].cells[0].innerText;
            var olaValue = rowsSelected[0].cells[1].innerText;
            var olaRemark = rowsSelected[0].cells[2].innerText;
            rowsSelected[0].cells[0].innerHTML = "<input type='text' value='" + olaName + "'  onclick='' onblur='this.parentNode.innerHTML=this.value;'>";
            rowsSelected[0].cells[1].innerHTML = "<input type='text' value='" + olaValue + "' onclick='' onblur='this.parentNode.innerHTML=this.value;'>";
            rowsSelected[0].cells[2].innerHTML = "<input type='text' value='" + olaRemark + "'onclick='' onblur='this.parentNode.innerHTML=this.value;'>";
        } else {
            alert("只能选中一行SLA数据!");
        }
    });
    var onModifyOLARow = [];
    //添加OLA信息
    $("#addOperationOLA").click(function () {
        var table = tables["operationOlaTable"];
        var row = table.dataTable().fnAddData({
            "olaName": "<input type='text' onclick='window.event.stopPropagation();' onblur='this.parentNode.innerHTML=this.value;'/>",
            "olaValue": "<input type='text' onclick='window.event.stopPropagation();' onblur='this.parentNode.innerHTML=this.value;'/>",
            "olaRemark": "<input type='text' onclick='window.event.stopPropagation();' onblur='this.parentNode.innerHTML=this.value;'/>"
        });
        if (null != onModifyOLARow) {
            onModifyOLARow.push(row);
            for (var i = 0; i < onModifyOLARow.length; i++) {
                var tr = table.fnGetNodes(onModifyOLARow[i]);
                $(tr).click(function (e) {
                    e.stopPropagation();
                });
            }
        }
    });


    //删除OLA信息
    $("#deleteOperationOLA").click(function () {
        var table = tables["operationOlaTable"];
        var rowsSelected = table.$("tr.row_selected");
        if (rowsSelected.length > 0) {
            for (var i = 0; i < rowsSelected.length; i++) {
                table.fnDeleteRow(rowsSelected[i]);
            }
        } else {
            alert("请选中至少一行SLA数据!");
        }
    });
    //保存OLA信息
    $("#saveOperationOLA").click(function () {
        var array = [];
        var rowsAll = tables["operationOlaTable"].$("tr");
        for (var i = 0; i < rowsAll.length; i++) {
            var params = {
                serviceId: serviceId,
                operationId: operationId,
                olaName: rowsAll[i].cells[0].innerText,
                olaValue: rowsAll[i].cells[1].innerText,
                olaRemark: rowsAll[i].cells[2].innerText
            };
            array.push(params);
        }
        if(array.length > 0){
            operationManager.saveOperationOLA(array);
        }else{
            var deleteOLACallBack = function (result) {
                alert("删除完成");
            }
            operationManager.deleteOperationAllOLA(serviceId, operationId,deleteOLACallBack);
        }

    });
    //完成编辑：遍历表格所有cell,如有编辑框则取出其值赋给cell
    $("#operationOlaTable").click(function () {
        var rowsAll = tables["operationOlaTable"].$("tr");
        for (var i = 0; i < rowsAll.length; i++) {
            for (var j = 0; j < rowsAll[i].cells.length; j++) {
                if (rowsAll[i].cells[j].hasChildNodes()) {
                    if (rowsAll[i].cells[j].children[0] != undefined) {
                        rowsAll[i].cells[j].width = "532px";
                        rowsAll[i].cells[j].height = "20px";
                        rowsAll[i].cells[j].innerText = rowsAll[i].cells[j].children[0].value;
                    }
                }
            }
        }
    });
    var isIE = (navigator.appVersion.indexOf("MSIE") != -1) ? true : false;
    if (isIE) {
        $("#operationOlaTable").attr("style", "width:1180px");
        $("#operationSlaTable").attr("style", "width:1180px");
    }
	    $("#seesda").click(function () {
	        if (isChrome) {
	            var winOption = "height=800px,width=1200px,top=50,scrollbars=yes,resizable=yes,fullscreen=0";
	            return  window.open("../jsp/sda.jsp?serviceId=" + serviceId + "&operationId=" + operationId, window, winOption);
	        } else {
	            window.showModalDialog("../jsp/sda.jsp?serviceId=" + serviceId + "&operationId=" + operationId, "", "dialogWidth:1200px;dialogHeight:800px;resizable=yes");
	        }

	    });


        $("#seesdaView").click(function () {
            if (isChrome) {
                var winOption = "height=800px,width=1200px,top=50,scrollbars=yes,resizable=yes,fullscreen=0";
                return  window.open("../jsp/sdaView.jsp?serviceId=" + serviceId + "&operationId=" + $("#operationId").val(), window, winOption);
            } else {
                window.showModalDialog("../jsp/sdaView.jsp?serviceId=" + serviceId + "&operationId=" + operationId, "", "dialogWidth:1200px;dialogHeight:800px;resizable=yes");
            }

        });
});
var editStructId = function editStructId(input, content, id, sdaNode) {
    if ("" === input.value || null === input.value) {
        alert("请输入节点ID!");
    } else {
        input.parentNode.innerHTML = content + input.value;
    }
    for (var i = 0; i < sdaNode.length; i++) {
        if (sdaNode[i]["id"] == id) {
            sdaNode[i]["structId"] = input.value;
        }
    }
};
var editMetadataId = function editMetadataId(input, id, sdaNode) {
    if ("" === input.value || null === input.value) {
        alert("请输入元数据ID!");
    } else {
        input.parentNode.innerHTML = input.value;
    }
    for (var i = 0; i < sdaNode.length; i++) {
        if (sdaNode[i]["id"] == id) {
            sdaNode[i]["metadataId"] = input.value;
        }
    }
};
var editType = function editType(input, id, sdaNode) {
    if ("string" === input.value || "number" === input.value || "array" === input.value) {
        this.parentNode.innerHTML = input.value;
    } else {
        alert("只能输入string|number|array中的一种!");
    }
    for (var i = 0; i < sdaNode.length; i++) {
        if (sdaNode[i]["id"] == id) {
            sdaNode[i]["type"] = input.value;
        }
    }
};
var editRequired = function editRequired(input, id, sdaNode) {
    if ("Y" === input.value || "N" === input.value) {
        input.parentNode.innerHTML = input.value;
    } else {
        alert("只能输入Y|N中的一种!");
    }
    for (var i = 0; i < sdaNode.length; i++) {
        if (sdaNode[i]["id"] == id) {
            sdaNode[i]["required"] = input.value;
        }
    }
}
var editRemark = function editRemark(input, id, sdaNode) {

    input.parentNode.innerHTML = input.value;
    for (var i = 0; i < sdaNode.length; i++) {
        if (sdaNode[i]["id"] == id) {
            sdaNode[i]["remark"] = input.value;
        }
    }
}
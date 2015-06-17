$(function () {
    var tables = {};
    var asInitVals = [];
    $('#tabs').tabs();
    $("#tab0").click(function (e) {
        if (tables["serviceInfoTable"]) {
            tables["serviceInfoTable"].fnAdjustColumnSizing();
        }
    });
    /**
     * init serviceInfo table
     * @param {Object} result
     *
     */
    var initserviceInfoTable = function initserviceInfoTable(result) {
        //初始化对Grid的操作事件
        var columnClickEventInit = function columnClickEventInit() {
            $("#serviceInfoTable tbody tr").unbind("click");
            $("#serviceInfoTable tbody tr").click(function (e) {
                if ($(this).find("td").hasClass("dataTables_empty")) {
                }
                else {
                    $(this).toggleClass("row_selected");
                }
            });
        };
        //初始化serviceInfoTable
        if (tables["serviceInfoTable"]) {
            tables["serviceInfoTable"].fnDestroy();
        }
        tables["serviceInfoTable"] = $("#serviceInfoTable").dataTable({
            "aaData": result,
            "aoColumns": serviceInfoTableLayout,
            "aoColumnDefs": [
                {
                    "sClass": "center",
                    "aTargets": [ 0, 1, 2, 3, 4, 5, 6, 7, 8]
                }
            ],
            "bJQueryUI": true,
            "bAutoWidth": true,
            "bScrollCollapse": "full_numbers",
            "bPaginate": true,
            "bSort": true,
            "oLanguage": oLanguage,
            "fnDrawCallback": function () {
                columnClickEventInit();
            }
        });
        tables["serviceInfoTable"].fnAdjustColumnSizing();
    };
    serviceInfoManager.getAll(initserviceInfoTable);

    //初始化操作Grid的搜索框
    var initserviceInfoTableFooter = function initserviceInfoTableFooter() {
        $("#serviceInfoTable tfoot input").keyup(
            function () {
                tables["serviceInfoTable"].fnFilter(this.value, $(
                    "#serviceInfoTable tfoot input").index(this), null, null, null, false);
            });
        $("#serviceInfoTable tfoot input").each(function (i) {
            asInitVals[i] = this.value;
        });
        $("#serviceInfoTable tfoot input").focus(function () {
            if (this.className == "search_init") {
                this.className = "";
                this.value = "";
            }
        });
        $("#serviceInfoTable tfoot input")
            .blur(
            function (i) {
                if (this.value == "") {
                    this.className = "search_init";
                    this.value = asInitVals[$(
                        "#serviceInfoTable tfoot input")
                        .index(this)];
                }
            });
    };
    initserviceInfoTableFooter();

    // 删除服务
    $('#del').button().click(function () {
        var delids = '';
        $("#serviceInfoTable tbody tr").each(function () {
            if ($(this).hasClass("row_selected")) {
                delids += $(this).find("td").eq(0).text() + ",";
            }
        });
        if (delids == "") {
            alert('请选择删除的记录!');
            return false;
        }
        delids = delids.substring(0, delids.length - 1);
        function checkExistOperation(result) {
            if (result != null && result != "") {
                alert('请先删除服务下的所有操作!');
            }
            else {
                if (confirm('确定删除服务' + delids + '吗?')) {
                    serviceInfoManager.deleteById(delids, result);
                }
            }
        }
        serviceInfoManager.checkExistOperation(delids, checkExistOperation);
    });

    // 查看服务下的所有操作
    $('#operationsInfo').button().click(function () {
        var count = 0;
        var id;
        $("#serviceInfoTable tbody tr").each(function () {
            if ($(this).hasClass("row_selected")) {
                count++;
                id = $(this).find("td").eq(0).text();
            }
        });
        if (count == 0) {
            alert('请选择记录!');
            return false;
        }
        if (count > 1) {
            alert('只能选择一条记录查看!');
            return false;
        }
        var isChrome = (navigator.appVersion.indexOf("Chrome") != -1) ? true : false;
        if (isChrome) {
            var winOption = "height=600px,width=900px,top=50,scrollbars=yes,resizable=yes,fullscreen=0";
            return  window.open("../jsp/operationsByServiceId.jsp?serviceId=" + id, window, winOption);
        } else {
            window.showModalDialog("../jsp/operationsByServiceId.jsp", id, "dialogWidth:900px;dialogHeight:400px;resizable=no");

        }


    });

    var serviceId = $("#form_serviceId"),
        serviceName = $("#form_serviceName"),
        serviceRemark = $("#form_serviceRemark"),
        categoryId = $("#form_categoryId"),
        version = $("#form_version"),
        state = $("#form_state"),
        allFields = $([]).add(serviceId).add(serviceName).add(serviceRemark).add(categoryId).add(version).add(state),
        tips = $(".validateTips");

    // 初始化服务分组下拉框
    function initCategoryInfo() {
        $.ajax({
            url: '../serviceCategory/second',
            type: 'GET',
            success: function (result) {
                initSelect(result);
            }
        });
    };
    function initSelect(result) {
        for (var i = 0; i < result.length; i++)
            categoryId.append("<option value='" + result[i].categoryId + "'>" + result[i].categoryId + ":" + result[i].categoryName + "</option>");
    };
    initCategoryInfo();
    //categoryId.combobox();
    //state.combobox();


    // 校验提示
    function updateTips(t) {
        tips
            .text(t)
            .addClass("ui-state-highlight");
        setTimeout(function () {
            tips.removeClass("ui-state-highlight", 1500);
        }, 500);
    }

    // 校验长度
    function checkLength(o, n, min, max) {
        if (o.val().length == 0) {
            o.addClass("ui-state-error");
            updateTips(n + " can not be null!");
            return false;
        }
        if (o.val().length > max || o.val().length < min) {
            o.addClass("ui-state-error");
            updateTips( n + " 的长度必须在 " +
                min + " 和 " + max + "之间");
            return false;
        } else {
            return true;
        }
    }

    // 校验正则表达式
    function checkRegexp(o, regexp, n) {
        if (!(regexp.test(o.val()) )) {
            o.addClass("ui-state-error");
            updateTips(n);
            return false;
        } else {
            return true;
        }
    }

    // 校验特殊字符
    function checkInvalidChar(o, n) {
        if (/[@#$%^&*()=]/.test(o.val())) {
            o.addClass("ui-state-error");
            updateTips(n);
            return false;
        } else {
            return true;
        }
    }

    // 新增服务
    $("#add").button().click(function () {

        tips.text('');
        version.val("1.0.0");
        $('.ui-combobox input').each(function () {
            this.value = '';
        });
        serviceId.removeAttr("readonly");
        $('.ui-combobox:eq(0) input').removeAttr("disabled", "true");
        $('.ui-combobox:eq(0) a').show();
        $('.ui-combobox:eq(0) a').removeAttr("style");

        $("#dialog:ui-dialog").dialog("destroy");
        $("#dialog-form").dialog({
            title: "新增服务",
            autoOpen: false,
            height: 400,
            width: 350,
            modal: true,
            bgiframe: true,
            buttons: {
                "新增": function () {
                    var bValid = true;
                    allFields.removeClass("ui-state-error");
                    bValid = bValid && checkLength(serviceId, "服务Id", 11, 11);
                    bValid = bValid && checkLength(serviceName, "元数据名称", 0, 500);
                    bValid = bValid && checkInvalidChar(serviceId, "服务ID包含特殊字符");
                    bValid = bValid && checkInvalidChar(serviceName, "服务名称包含特殊字符");
                    bValid = bValid && checkInvalidChar(serviceRemark, "服务描述包含特殊字符");
                    bValid = bValid && checkRegexp(version, /^([0-9.])+$/, "版本号只能是数字或.号");
                    // 判断服务是否已经存在,不存在做插入操作
                    if (bValid) {
                        // 获取combobox下拉框的值
                        var categoryValue = $("#form_categoryId option:selected").text();
                        if (categoryValue == null || categoryValue == "") {
                            alert('请选择服务分组!');
                            return false;
                        }
                        categoryValue = categoryValue.substring(0, categoryValue.indexOf(":"));
                        console.log(categoryValue);
                        // 服务ID的第1位到第5位，必须与分组ID相同
                        var tempValue = serviceId.val().substring(0, 5);
                        if (categoryValue != tempValue) {
                            alert('服务ID的第1位到第5位，必须与分组ID相同!');
                            return false;
                        }
                        var stateValue = $('#form_state option:selected').text();
                        var params = {
                            serviceId: serviceId.val(),
                            serviceName: serviceName.val(),
                            serviceRemark: serviceRemark.val(),
                            categoryId: categoryValue,
                            version: version.val(),
                            state: stateValue
                        };
                        var serviceInfoIsExists = function serviceInfoIsExists(result) {
                            if (result.serviceId != null && result.serviceId != "") {
                                alert('服务已经存在!');
                            }
                            else {
                                serviceInfoManager.insert(params);
                            }
                        };
                        serviceInfoManager.getServiceById(serviceId.val(), serviceInfoIsExists);
                        $(this).dialog("close");
                    }
                },
                "取消": function () {
                    $(this).dialog("close");
                }
            },
            close: function () {
                allFields.val("").removeClass("ui-state-error");
            }
        });
        $("#dialog-form").dialog("open");
    });

    // 修改服务
    $('#modify').button().click(function () {
        tips.text('');
        var table = tables["serviceInfoTable"];
        // 选择的行数
        var rowsSelected = table.$("tr.row_selected");
        if (rowsSelected.length == 0) {
            alert('请选择修改的服务!');
            return false;
        }
        if (rowsSelected.length > 1) {
            alert('请只选择一个服务修改!');
            return false;
        }
        var selectedDatas;
        selectedDatas = table.fnGetData(table.$("tr.row_selected")[0]);

        // 赋值修改的服务记录
        serviceId.val(selectedDatas["serviceId"]);
        serviceName.val(selectedDatas["serviceName"]);
        serviceRemark.val(selectedDatas["serviceRemark"]);
        categoryId.val(selectedDatas["categoryId"]);
        $('.ui-combobox:eq(0) input').val(selectedDatas["categoryId"]);
        $('.ui-combobox:eq(1) input').val(selectedDatas["state"]);
        version.val(selectedDatas["version"]);

        // 服务ID不能被修改
        serviceId.attr("readonly", "true");
        $('.ui-combobox:eq(0) input').attr("disabled", "true");
        $('.ui-combobox:eq(0) a').hide();

        //$( "#dialog-form" ).dialog("destroy");
        $("#dialog:ui-dialog").dialog("destroy");
        $("#dialog-form").dialog({
            "title": "修改服务",
            autoOpen: false,
            height: 400,
            width: 350,
            modal: true,
            bgiframe: true,
            buttons: {
                "修改": function () {
                    var bValid = true;
                    allFields.removeClass("ui-state-error");

                    bValid = bValid && checkLength(serviceId, "服务Id", 11, 11);
                    bValid = bValid && checkLength(serviceName, "元数据名称", 0, 500);

                    bValid = bValid && checkInvalidChar(serviceId, "服务ID包含特殊字符");
                    bValid = bValid && checkInvalidChar(serviceName, "服务名称包含特殊字符");
                    bValid = bValid && checkInvalidChar(serviceRemark, "服务描述包含特殊字符");
                    bValid = bValid && checkRegexp(version, /^([0-9.])+$/, "版本号只能是数字或.号");

                    if (bValid) {
                        // 获取combobox下拉框的值
                        var categoryValue = $("#form_categoryId option:selected").text();
                        categoryValue = categoryValue.substring(0, categoryValue.indexOf(":"));
                        var stateValue = $("#form_state option:selected").text();
                        var params = {
                            serviceId: serviceId.val(),
                            serviceName: serviceName.val(),
                            serviceRemark: serviceRemark.val(),
                            categoryId: categoryValue,
                            version: version.val(),
                            state: stateValue
                        };
                        serviceInfoManager.update(params);
                        $(this).dialog("close");
                    }
                },
                "取消": function () {
                    $(this).dialog("close");
                }
            },
            close: function () {
                allFields.val("").removeClass("ui-state-error");
            }
        });
        $("#dialog-form").dialog("open");
    });

    // 发布服务
    $('#deploy').button().click(function () {
        var deployids = '';
        var invalidId = '';
        $("#serviceInfoTable tbody tr").each(function () {
            if ($(this).hasClass("row_selected")) {
                deployids += $(this).find("td").eq(0).text() + ",";
                if ($(this).find("td").eq(6).text() != '服务定义') {
                    invalidId = $(this).find("td").eq(0).text();
                    return;
                }
            }
        });
        if (invalidId != '') {
            alert('服务[' + invalidId + ']不能发布!');
            return false;
        }
        if (deployids == "") {
            alert('请选择要发布的服务!');
            return false;
        }
        deployids = deployids.substring(0, deployids.length - 1);
        if (confirm('确定发布服务' + deployids + '及其下的所有操作吗?')) {
            serviceInfoManager.deploy(deployids);
        }
    });

    // 重定义服务
    $('#redef').button().click(function () {
        var redefids = '';
        var invalidId = '';
        $("#serviceInfoTable tbody tr").each(function () {
            if ($(this).hasClass("row_selected")) {
                redefids += $(this).find("td").eq(0).text() + ",";
                if ($(this).find("td").eq(6).text() == '上线') {
                    invalidId = $(this).find("td").eq(0).text();
                    return;
                }
            }
        });
        if (invalidId != '') {
            alert('服务[' + invalidId + ']不能重定义!');
            return false;
        }
        if (redefids == "") {
            alert('请选择重定义的记录!');
            return false;
        }
        redefids = redefids.substring(0, redefids.length - 1);
        if (confirm('确定重定义服务' + redefids + '及其下的所有操作吗??')) {
            serviceInfoManager.redef(redefids);
        }
    });

    // 服务上线
    $('#publish').button().click(function () {
        var publishids = '';
        var invalidId = '';
        $("#serviceInfoTable tbody tr").each(function () {
            if ($(this).hasClass("row_selected")) {
                publishids += $(this).find("td").eq(0).text() + ",";
                if ($(this).find("td").eq(6).text() == '上线') {
                    invalidId = $(this).find("td").eq(0).text();
                    return;
                }
            }
        });
        if (invalidId != '') {
            alert('服务[' + invalidId + ']已经上线!');
            return false;
        }
        if (publishids == "") {
            alert('请选择上线的记录!');
            return false;
        }
        publishids = publishids.substring(0, publishids.length - 1);
        if (confirm('确定上线服务' + publishids + '及其下的所有操作吗??')) {
            serviceInfoManager.publish(publishids);
        }
    });

    $('#submit').button().click(function () {
        var params = [];
        var id = '';
        var flag = false;
        $("#serviceInfoTable tbody tr").each(function () {
            if ($(this).hasClass("row_selected")) {
                id = $(this).find("td").eq(0).text();
                params.push(id);
                var state = $(this).find("td").eq(7).text();
                if (state == '通过' || state == '待审核') {
                    flag = true;
                }
            }
        });
        if (flag) {
            alert('服务已经是通过或者待审核，不能提交!');
            return false;
        }
        if (params.length == 0) {
            alert('请选择服务!');
            return false;
        }
        function submitOpe(result) {
            if (result) {
                alert('服务提交审核成功!');
                window.location.reload();
            }
            else {
                alert('服务提交审核失败!');
            }
        };
        serviceInfoManager.submitService(submitOpe, params);
    });

    $('#checkAll').button().click(function () {
        $("#serviceInfoTable tbody tr").addClass("row_selected");
    });
    $('#toggleAll').button().click(function () {
        $("#serviceInfoTable tbody tr").toggleClass("row_selected");
    });
});

var pageCompany;
var pageProject;
var pageGPRS;
var pageAmmeter;

$(function () {

    $("#accordion").accordion({
        heightStyle: "fill"
    });
    $("#accordion-resizer").resizable({
        minHeight: 140,
        minWidth: 200,
        resize: function () {
            $("#accordion").accordion("refresh");
        }
    })

    //init the nav tree in the left
    var initTree = function () {
        $.ajax({
            url: '/menu/list',
            type: 'GET',
            success: function (result) {
                $("#tree").dynatree({
                    onActivate: function (node) {
                        if (node.data.type == "company") {
                            var id =  node.data.id;
                            var title = node.data.titley;
                            var tableStr = '<table cellpadding="0" cellspacing="0" border="0" class="display" id="projectTable' + id + '"></table>';
                            addTab(title, tableStr);
                            initProjectTable(id);
                        }
//                        alert("You activated " + node.data.title);
                    },
                    children: result
                });
            }
        });
    };

    initTree();

    var tabTitle = $("#tab_title"),
        tabContent = $("#tab_content"),
        tabTemplate = "<li><a href=''></a> <span class='ui-icon ui-icon-close' role='presentation'>Remove Tab</span></li>",
        tabCounter = 2;

    var tabs = $("#tabs").tabs();

    // actual addTab function: adds new tab using the input from the form above
    var addTab = function addTab(name, context) {
        var li = '<li><a href="#' + name + '">' + name + '</a> <span class="ui-icon ui-icon-close" role="presentation">Remove Tab</span></li>';
        tabs.find(".ui-tabs-nav").append(li);
        tabs.append('<div id="' + name + '">' + context + '</div>');
        tabs.tabs("refresh");
    }

    // close icon: removing the tab on click
    tabs.delegate("span.ui-icon-close", "click", function () {
        var panelId = $(this).closest("li").remove().attr("aria-controls");
        $("#" + panelId).remove();
        tabs.tabs("refresh");
    });

    tabs.bind("keyup", function (event) {
        if (event.altKey && event.keyCode === $.ui.keyCode.BACKSPACE) {
            var panelId = tabs.find(".ui-tabs-active").remove().attr("aria-controls");
            $("#" + panelId).remove();
            tabs.tabs("refresh");
        }
    });

    var fnGetSelected = function fnGetSelected(oTableLocal) {
        return oTableLocal.$('tr.row_selected');
    };

    /**
     * company module
     */
    var initCompanyTable = function (id) {

        var init = function init(result) {
            id = id || "";
            var companyTable = $("#companyTable" + id).dataTable({
                "aaData": result,
                "aoColumns": companyTableLayout,
                "bJQueryUI": true,
                "oLanguage": {
                    "sLengthMenu": "显示 _MENU_ 条记录每页"
                }
            });

            $("#companyTable tbody tr").click(function (e) {
                if ($(this).hasClass('row_selected')) {
                    $(this).removeClass('row_selected');
                } else {
                    companyTable.$('tr.row_selected').removeClass('row_selected');
                    $(this).addClass('row_selected');
                }
            });

            $("#createCompanyDialog").dialog({
                autoOpen: false,
                height: 300,
                width: 350,
                modal: true,
                buttons: {
                    "保存": function () {
                        var company = pageCompany || {};
                        company["companyName"] = $("#companyNameInput").val();
                        companyManager.add(company, function (result) {
                            $('#companyTable').dataTable().fnDestroy();
                            initCompanyTable();
                        });
                        $(this).dialog("close");
                    },
                    "取消": function () {
                        $(this).dialog("close");
                    }
                },
                close: function () {
                    pageCompany = {};
                }
            });

            $('#addCompanyBtn').button().click(function (event) {
                $("#createCompanyDialog").dialog("open");
            });

            $("#modifyCompanyBtn").button().click(function (event) {
                var anSelected = fnGetSelected(companyTable);
                if (anSelected.length !== 0) {
                    var id = $(anSelected[0]).children().first().text();
                    var modifyCallBack = function modifyCallBack(result) {
                        pageCompany = result;
                        $("#companyNameInput").val(pageCompany["companyName"]);
                    };
                    companyManager.getById(id, modifyCallBack);
                    $("#createCompanyDialog").dialog("open");
                }
                event.preventDefault();
            });

            $("#deleteCompanyBtn").button().click(function (event) {
                var anSelected = fnGetSelected(companyTable);
                var deleteCallBack = function deleteCallBack(result) {
                    companyTable.fnDeleteRow(anSelected[0]);
                };
                if (anSelected.length !== 0) {
                    var id = $(anSelected[0]).children().first().text();
                    $("#deleteCompanyConfirm").dialog({
                        resizable: false,
                        height: 140,
                        modal: true,
                        buttons: {
                            "确定": function () {
                                companyManager.deleteById(id, deleteCallBack);
                                $(this).dialog("close");
                            },
                            "取消": function () {
                                $(this).dialog("close");
                            }
                        }
                    });
                    $("#deleteCompanyConfirm").dialog("open");
                }
                event.preventDefault();
            });
        }
        if (id) {
            companyManager.getById(id, function (result) {
                init(result);
            });
        } else {
            companyManager.getAll(function (result) {
                init(result);
            });
        }

    };

    initCompanyTable();

    /**
     * end company
     */


    /**
     * project module
     */
    var initProjectTable = function (id) {
        var init = function init (result) {
            id = id || "";
            var projectTable = $("#projectTable" + id).dataTable({
                "aaData": result,
                "aoColumns": projectTableLayout,
                "bJQueryUI": true,
                "oLanguage": {
                    "sLengthMenu": "显示 _MENU_ 条记录每页"
                },
                "aoColumnDefs": [
                    {
                        "aTargets": [4],
                        "bSearchable": false,
                        "sType": 'date',
                        "fnRender": function (oObj) {
                            var startDate = new Date(oObj.aData["startDate"]);
                            startDate = startDate.getDate() + "/" + (startDate.getMonth() + 1) + "/" + startDate.getFullYear();
                            return "<div class='date'>" + startDate + "<div>";
                        }
                    },
                    {
                        "aTargets": [5],
                        "bSearchable": false,
                        "sType": 'date',
                        "fnRender": function (oObj) {
                            var endDate = new Date(oObj.aData["endDate"]);
                            endDate = endDate.getDate() + "/" + (endDate.getMonth() + 1) + "/" + endDate.getFullYear();
                            return "<div class='date'>" + endDate + "<div>";
                        }
                    }
                ]
            });

            $("#projectTable tbody tr").click(function (e) {
                if ($(this).hasClass('row_selected')) {
                    $(this).removeClass('row_selected');
                } else {
                    projectTable.$('tr.row_selected').removeClass('row_selected');
                    $(this).addClass('row_selected');
                }
            });

            $("#createProjectDialog").dialog({
                autoOpen: false,
                height: 550,
                width: 500,
                modal: true,
                buttons: {
                    "保存": function () {
                        var project = pageProject || {};
                        project["projectName"] = $("#projectNameInput").val();
                        project["startDate"] = $("#startDateInput").datepicker("getDate");
                        project["endDate"] = $("#endDateInput").datepicker("getDate");
                        project["electricityCharge"] = $("#electricityChargeInput").val();
                        project["partsRatio"] = $("#partsRatioInput").val();
                        projectManager.add(project, function (result) {
                            $('#projectTable').dataTable().fnDestroy();
                            initProjectTable();
                            $('#projectTable').css("width", "100%");
                        });
                        $(this).dialog("close");
                    },
                    "取消": function () {
                        $(this).dialog("close");
                    }
                },
                close: function () {
                    pageProject = {};
                }
            });

            $('#addProjectBtn').button().click(function (event) {
                $("#createProjectDialog").dialog("open");
            });

            $("#modifyProjectBtn").button().click(function (event) {
                var anSelected = fnGetSelected(projectTable);
                if (anSelected.length !== 0) {
                    var id = $(anSelected[0]).children().first().text();
                    var modifyCallBack = function modifyCallBack(result) {
                        pageProject = result;
                        $("#projectNameInput").val(pageProject["projectName"]);
                    };
                    projectManager.getById(id, modifyCallBack);
                    $("#createProjectDialog").dialog("open");
                }
                event.preventDefault();
            });

            $("#deleteProjectBtn").button().click(function (event) {
                var anSelected = fnGetSelected(projectTable);
                var deleteCallBack = function deleteCallBack(result) {
                    projectTable.fnDeleteRow(anSelected[0]);
                };
                if (anSelected.length !== 0) {
                    var id = $(anSelected[0]).children().first().text();
                    $("#deleteProjectConfirm").dialog({
                        resizable: false,
                        height: 140,
                        modal: true,
                        buttons: {
                            "确定": function () {
                                projectManager.deleteById(id, deleteCallBack);
                                $(this).dialog("close");
                            },
                            "取消": function () {
                                $(this).dialog("close");
                            }
                        }
                    });
                    $("#deleteProjectConfirm").dialog("open");
                }
                event.preventDefault();
            });

            var projectStartDate = $("#startDateInput").datepicker();
            var projectEndDate = $("#endDateInput").datepicker();

        };
        if(id){
            projectManager.getByCompanyId(id, function (result) {
                init(result);
            });
        } else {
            projectManager.getAll(function (result) {
               init(result);
            });
        }

    };

    initProjectTable();

    /**
     * project module end
     */

    /**
     * gprs module
     */
    var initGPRSTable = function () {
        gprsManager.getAll(function (result) {
            var gprsTable = $("#gprsTable").dataTable({
                "aaData": result,
                "aoColumns": gprsTableLayout,
                "bJQueryUI": true,
                "oLanguage": {
                    "sLengthMenu": "显示 _MENU_ 条记录每页"
                }
            });

            $("#gprsTable tbody tr").click(function (e) {
                if ($(this).hasClass('row_selected')) {
                    $(this).removeClass('row_selected');
                } else {
                    gprsTable.$('tr.row_selected').removeClass('row_selected');
                    $(this).addClass('row_selected');
                }
            });

            $("#createGPRSDialog").dialog({
                autoOpen: false,
                height: 350,
                width: 350,
                modal: true,
                buttons: {
                    "保存": function () {
                        var gprs = pageGPRS || {};
                        gprs["name"] = $("#gprsNameInput").val();
                        gprs["identifier"] = $("#identifierInput").val();
                        gprsManager.add(gprs, function (result) {
                            $('#gprsTable').dataTable().fnDestroy();
                            initGPRSTable();
                            $('#gprsTable').css("width", "100%");
                        });
                        $(this).dialog("close");
                    },
                    "取消": function () {
                        $(this).dialog("close");
                    }
                },
                close: function () {
                    pageGPRS = {};

                }
            });

            $('#addGPRSBtn').button().click(function (event) {
                $("#createGPRSDialog").dialog("open");
            });

            $("#modifyGPRSBtn").button().click(function (event) {
                var anSelected = fnGetSelected(gprsTable);
                if (anSelected.length !== 0) {
                    var id = $(anSelected[0]).children().first().text();
                    var modifyCallBack = function modifyCallBack(result) {
                        pageGPRS = result;
                        $("#gprsNameInput").val(pageGPRS["name"]);
                        $("#identifierInput").val(pageGPRS["identifier"]);
                    };
                    gprsManager.getById(id, modifyCallBack);
                    $("#createGPRSDialog").dialog("open");
                }
                event.preventDefault();
            });

            $("#deleteGPRSBtn").button().click(function (event) {
                var anSelected = fnGetSelected(gprsTable);
                var deleteCallBack = function deleteCallBack(result) {
                    gprsTable.fnDeleteRow(anSelected[0]);
                };
                if (anSelected.length !== 0) {
                    var id = $(anSelected[0]).children().first().text();
                    $("#deleteGPRSConfirm").dialog({
                        resizable: false,
                        height: 140,
                        modal: true,
                        buttons: {
                            "确定": function () {
                                gprsManager.deleteById(id, deleteCallBack);
                                $(this).dialog("close");
                            },
                            "取消": function () {
                                $(this).dialog("close");
                            }
                        }
                    });
                    $("#deleteGPRSConfirm").dialog("open");
                }
                event.preventDefault();
            });
        });
    };

    initGPRSTable();
    /**
     * gprs module end
     */

    /**
     * ammeter module
     */
    var initAmmeterTable = function () {
        ammeterManager.getAll(function (result) {
            var ammeterTable = $("#ammeterTable").dataTable({
                "aaData": result,
                "aoColumns": ammeterTableLayout,
                "bJQueryUI": true,
                "oLanguage": {
                    "sLengthMenu": "显示 _MENU_ 条记录每页"
                }
            });

            $("#ammeterTable tbody tr").click(function (e) {
                if ($(this).hasClass('row_selected')) {
                    $(this).removeClass('row_selected');
                } else {
                    ammeterTable.$('tr.row_selected').removeClass('row_selected');
                    $(this).addClass('row_selected');
                }
            });

            $("#createAmmeterDialog").dialog({
                autoOpen: false,
                height: 650,
                width: 500,
                modal: true,
                buttons: {
                    "保存": function () {
                        var ammeter = pageAmmeter || {};
                        ammeter["name"] = $("#ammeterNameInput").val();
                        ammeter["pumpName"] = $("#pumpNameInput").val();
                        ammeter["sensorRate"] = $("#sensorRateInput").val();
                        ammeter["formerCost"] = $("#formerCostInput").val();
                        ammeter["upperLimit"] = $("#upperLimitInput").val();
                        ammeter["lowerLimit"] = $("#lowerLimitInput").val();
                        ammeterManager.add(ammeter, function (result) {
                            $('#ammeterTable').dataTable().fnDestroy();
                            initAmmeterTable();
                            $('#ammeterTable').css("width", "100%");
                        });
                        $(this).dialog("close");
                    },
                    "取消": function () {
                        $(this).dialog("close");
                    }
                },
                close: function () {
                    pageAmmeter = {};

                }
            });

            $('#addAmmeterBtn').button().click(function (event) {
                $("#createAmmeterDialog").dialog("open");
            });

            $("#modifyAmmeterBtn").button().click(function (event) {
                var anSelected = fnGetSelected(ammeterTable);
                if (anSelected.length !== 0) {
                    var id = $(anSelected[0]).children().first().text();
                    var modifyCallBack = function modifyCallBack(result) {
                        pageAmmeter = result;
                        $("#ammeterNameInput").val(pageAmmeter["name"]);
                        $("#pumpNameInput").val(pageAmmeter["pumpName"]);
                        $("#sensorRateInput").val(pageAmmeter["sensorRate"]);
                        $("#formerCostInput").val(pageAmmeter["formerCost"]);
                        $("#upperLimitInput").val(pageAmmeter["upperLimit"]);
                        $("#lowerLimitInput").val(pageAmmeter["lowerLimit"]);
                    };
                    ammeterManager.getById(id, modifyCallBack);
                    $("#createAmmeterDialog").dialog("open");
                }
                event.preventDefault();
            });

            $("#deleteAmmeterBtn").button().click(function (event) {
                var anSelected = fnGetSelected(ammeterTable);
                var deleteCallBack = function deleteCallBack(result) {
                    ammeterTable.fnDeleteRow(anSelected[0]);
                };
                if (anSelected.length !== 0) {
                    var id = $(anSelected[0]).children().first().text();
                    $("#deleteAmmeterConfirm").dialog({
                        resizable: false,
                        height: 140,
                        modal: true,
                        buttons: {
                            "确定": function () {
                                ammeterManager.deleteById(id, deleteCallBack);
                                $(this).dialog("close");
                            },
                            "取消": function () {
                                $(this).dialog("close");
                            }
                        }
                    });
                    $("#deleteAmmeterConfirm").dialog("open");
                }
                event.preventDefault();
            });
        });
    };

    initAmmeterTable();
    /**
     * end ammeter module
     */

    /**
     * ammeter record module
     */
    var initAmmeterRecordTable = function () {
        ammeterRecordManager.getAll(function (result) {
            var ammeterRecordTable = $("#ammeterRecordTable").dataTable({
                "aaData": result,
                "aoColumns": ammeterRecordTableLayout,
                "bJQueryUI": true,
                "oLanguage": {
                    "sLengthMenu": "显示 _MENU_ 条记录每页"
                }
            });

            $("#ammeterRecordTable tbody tr").click(function (e) {
                if ($(this).hasClass('row_selected')) {
                    $(this).removeClass('row_selected');
                } else {
                    ammeterRecordTable.$('tr.row_selected').removeClass('row_selected');
                    $(this).addClass('row_selected');
                }
            });

        });
    };

    initAmmeterRecordTable();

    /**
     * end ammeter record module
     */

    function updateTips(t) {
        tips
            .text(t)
            .addClass("ui-state-highlight");
        setTimeout(function () {
            tips.removeClass("ui-state-highlight", 1500);
        }, 500);
    }

    function checkLength(o, n, min, max) {
        if (o.val().length > max || o.val().length < min) {
            o.addClass("ui-state-error");
            updateTips("Length of " + n + " must be between " +
                min + " and " + max + ".");
            return false;
        } else {
            return true;
        }
    }

    function checkRegexp(o, regexp, n) {
        if (!( regexp.test(o.val()) )) {
            o.addClass("ui-state-error");
            updateTips(n);
            return false;
        } else {
            return true;
        }
    }
});
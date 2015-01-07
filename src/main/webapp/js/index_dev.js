// alert(new Date(parseInt("1380302750394")));

var userPane;
var construtUserPane;
var userPaneConstruted = false;

var ammeterPane;
var construtAmmeterPane;
var ammeterPaneConstruted = false;

var ammeterRecordPane;
var construtAmmeterRecordPane;
var ammeterRecordPaneConstruted = false;

var companyPane;
var companyPaneConstruted = false;

var projectPane;
var projectPaneConstruted = false;

var cpPane;
var construtCPPane;
var cpPaneConstruted = false;

var paPane;
var construtPAPane;
var paPaneConstruted = false;

var upPane;
var constructUPPane;
var upPaneConstruted = false;

var agPane;
var agPaneConstruted = false;

var constructNewPane;

var saveComputationPane;
var constructSaveComputationPane;
var saveComputationPaneConstructed = false;

var lastAmmeterStatusPane;
var constructLastAmmeterStatusPane;
var lastAmmeterStatusPaneConstructed = false;

var saveComputationChartPane;
var constructSaveComputationChartPane;
var saveComputationChartPaneConstructed = false;

var ammeterMonitorPane;
var ammeterMonitorPaneConstructed = false;

var activedMenuItem;

var request = {};

var stores = {};


require([
    "dijit/registry",
    "dojo/on",
    "dojo/topic",
    "dojox/data/JsonRestStore",
    "dojo/store/JsonRest",
    "dojo/store/Memory",
    "dojo/store/Cache",
    "dojox/grid/EnhancedGrid",
    "dojo/data/ObjectStore",
    "dojo/query",
    "dojo/ready",
    "dijit/form/RadioButton",
    "dijit/form/MultiSelect",
    "dijit/form/TextBox",
    "dijit/form/Button",
    "dijit/Menu",
    "dijit/MenuItem",
    "dijit/form/ComboButton",
    "dijit/form/ComboBox",
    "dijit/form/FilteringSelect",
    "dojo/dom-style",
    "dojo/dom-class",
    "dojo/dom-construct",
    "dojo/dom",
    "dojo/_base/xhr",
    "dijit/layout/ContentPane",
    "dijit/Dialog",
    "dojox/charting/themes/Claro",
    "dojox/charting/widget/Legend",
    "dijit/form/VerticalRuleLabels",
    "dijit/form/VerticalRule",
    "dijit/form/VerticalSlider",
    "dojox/charting/widget/SelectableLegend",
    "dojox/charting/action2d/Magnify",
    "dojox/charting/plot2d/Markers",
    "dojox/charting/Chart",
    "dojox/charting/StoreSeries",
    "dojox/charting/Theme",
    "dojox/charting/action2d/PlotAction",
    "dojox/charting/axis2d/Default",
    "dojox/charting/plot2d/Columns",
    "dojox/charting/plot2d/Lines",
    "dojox/charting/plot2d/Pie",
    "dojox/charting/plot2d/Grid",
    "dojox/charting/action2d/Tooltip",
    "dojox/charting/action2d/Highlight",
    "dojo/i18n!/dojo-release-1.7.2/dojox/grid/enhanced/nls/zh/Filter.js",
    "dojox/grid/enhanced/plugins/Search",
    "dojox/grid/enhanced/plugins/Filter",
    "dojo/parser",
    "dijit/layout/TabContainer",
    "dijit/form/DropDownButton",
    "dijit/TooltipDialog",
    "dijit/form/TextBox",
    "dojox/grid/enhanced/plugins/IndirectSelection",
    "dojox/grid/enhanced/plugins/Printer",
    "dojo/domReady!"], function (registry, on, topic, JsonRestStore, JsonRest, Memory, Cache, EnhancedGrid, ObjectStore, query, ready, RadioButton, MultiSelect, TextBox, Button, Menu, MenuItem, ComboButton, ComboBox, FilteringSelect, domStyle, domClass, domConstruct, dom, xhr, ContentPane, Dialog, Claro, Legend, VerticalRuleLabels, VerticalRule, VerticalSlider, SelectableLegend, Magnify, Markers, Chart, StoreSeries, Theme, PlotAction, Default, Columns, Lines, Pie, Grid, Tooltip, Highlight, i18nChart) {

    ready(function () {
        dojox.grid.DataGrid.prototype.setQueryAfterLoading = function (query) {
            if (this._isLoading === true) {
                if (this._queryAfterLoadingHandle !== undefined) {
                    dojo.disconnect(this, '_onFetchComplete', this._queryAfterLoadingHandle);
                }
                this._queryAfterLoadingHandle = dojo.connect(this, '_onFetchComplete', function () {
                    if (this._queryAfterLoadingHandle !== undefined) {
                        dojo.disconnect(this._queryAfterLoadingHandle);
                        delete this._queryAfterLoadingHandle;
                    }
                    this.setQuery(query);
                });
            }
            else {
                this.setQuery(query);
            }
        }

        xhr.get({
                    url: "/gprsserver/start",
                    timeout: 3000,
                    // give up after 3 seconds
                    handleAs: "json",
                    load: function () {
                        alert("抄表开始");
                    }
                });

        //hidden the user ContentPane
        var tabContainer = dijit.byId("tab_container");

        userPane = dijit.byId("userPane");
        // ammeterPane = dijit.byId("ammeterPane");
        ammeterRecordPane = dijit.byId("ammeterRecordPane");
        companyPane = dijit.byId("companyPane");
        projectPane = dijit.byId("projectPane");
        saveComputationPane = registry.byId("saveComputationPane");
        lastAmmeterStatusPane = registry.byId("lastAmmeterStatusPane");
        cpPane = dijit.byId("cpPane");
        paPane = dijit.byId("paPane");
        upPane = dijit.byId("upPane");
        saveComputationChartPane = registry.byId("saveComputationChartPane");
        agPane = dijit.byId("agPane");
        ammeterMonitorPane = registry.byId("ammeterMonitorPane");

        tabContainer.removeChild(ammeterMonitorPane);
        tabContainer.removeChild(userPane);
        // tabContainer.removeChild(ammeterPane);
        tabContainer.removeChild(ammeterRecordPane);
        tabContainer.removeChild(companyPane);
        tabContainer.removeChild(projectPane);
        tabContainer.removeChild(saveComputationPane);
        tabContainer.removeChild(lastAmmeterStatusPane);
        tabContainer.removeChild(cpPane);
        tabContainer.removeChild(paPane);
        tabContainer.removeChild(upPane);
        tabContainer.removeChild(agPane);
        tabContainer.removeChild(saveComputationChartPane);

        var main_container_width = dojo.style("main_container", "width");
        var ammeter_cell_width = main_container_width / 6;
        var ammeter_record_width = main_container_width * 0.24;
        var user_cell_width = main_container_width * 0.17;
        var company_cell_width = main_container_width / 3;
        var project_cell_width = "10em";
        var gprs_cell_width = "20em";

        var cp_cell_width = main_container_width * 0.20;
        var pa_cell_width = main_container_width * 0.20;
        var up_cell_width = main_container_width * 0.20;

        var ammeterManager = {
            store: new JsonRestStore({
                target: "/ammeter/list/"
            }),
            deleteAmmeter: function (ammeter, callBack, errorCallBack) {
                this.store.deleteItem(ammeter);
                this.store.save({
                    onComplete: function () {
                        callBack();
                        topic.publish("updateAmmeter", "delete ammeter");
                    },
                    onError: function () {
                        errorCallBack();
                    }
                });
            },
            addAmmeter: function (ammeter, callBack, errorCallBack) {
                this.store.newItem(ammeter);
                this.store.save({
                    onComplete: function () {
                        callBack();
                        topic.publish("updateAmmeter", ammeter.name);
                    },
                    onError: function () {
                        errorCallBack();
                    }
                });
            },
            saveAmmeter: function (callBack, errorCallBack) {
                this.store.save({
                    onComplete: function () {
                        callBack();
                        topic.publish("updateAmmeter");
                    },
                    onError: function () {
                        errorCallBack();
                    }
                })
            },
            getStore: function () {
                return this.store;
            }
        };

        var ammeterRecordManager = {
             store: new JsonRestStore({
                target: "/ammeter_record/list"
            })
        }



        var companyManager = {

            store: new JsonRestStore({
                target: "/company/list"
            }),
            getCompanyName: function (companyId, callBack) {

                this.store.fetch({
                    "query": {
                        "companyId": companyId
                    },
                    onComplete: function (company) {
                        callBack(company.companyName);
                    }
                });
            },
            addCompany: function (company, callBack, errorCallBack) {
                console.log("start add company");
                console.log("add" + company);
                this.store.newItem(company);
                this.store.save({
                    onComplete: function (company) {
                        callBack(company);
                        topic.publish("updateCompany", "add company");
                    },
                    onError: function (error) {
                        errorCallBack(error);
                    }
                });
            },
            deleteCompany: function (company, callBack, errorCallBack) {
                this.store.deleteItem(company);
                this.store.save({
                    onComplete: function () {
                        console.log("company delete");
                        callBack();
                        topic.publish("updateCompany", "delete company");
                    },
                    onError: function () {
                        errorCallBack();
                    }
                });
            },
            getStore: function () {
                return this.store;
            },
            saveCompany: function (callBack, errorCallBack) {
                this.store.save({
                    onComplete: function () {
                        callBack();
                        topic.publish("updateCompany", "delete company");
                    },
                    onError: function () {
                        errorCallBack();
                    }
                });
            }
        };

        var companyProjectManager = {
            store: new JsonRestStore({
                target: "/cp/list"
            }),
            addCompanyProject: function (companyProject, callBack, errorCallBack) {
                this.store.newItem(companyProject);
                this.store.save({
                    onComplete: function () {
                        callBack();
                        topic.publish("updateCompanyProject", "add company project");
                    },
                    onError: function () {
                        errorCallBack();
                    }
                });
            }
        };

        var projectUserManager = {
            store: new JsonRestStore({
                target: "/up/list"
            }),

            getStore: function () {
                return this.store;
            },

            addProjectUser: function (projectUser, callBack, errorCallBack) {
                this.store.newItem(projectUser);
                this.store.save({
                    onComplete: function () {
                        callBack();
                        topic.publish("updateProjectUser", "add project user");
                    },
                    onError: function () {
                        errorCallBack();
                    }
                });
            },

            deleteProjectUser: function (projectUser, callBack, errorCallBack) {
                this.store.deleteItem(projectUser);
                this.store.save({
                    onComplete: function () {
                        callBack();
                        topic.publish("updateProjectUser", "delete Project user");
                    },
                    onError: function () {
                        errorCallBack();
                    }
                });
            }
        }

        var projectAmmeterManager = {
            store: new JsonRestStore({
                target: "/pa/list"
            }),
            addProjectAmmeter: function (projectAmmeter, callBack, errorCallBack) {
                this.store.newItem(projectAmmeter);
                this.store.save({
                    onComplete: function () {
                        callBack();
                        topic.publish("updateProjectAmmeter", "add project ammeter");
                    },
                    onError: function () {
                        errorCallBack();
                    }
                });
            }
        };

        var projectManager = {
            store: new JsonRestStore({
                target: "/project/list/"
            }),
            getStore: function () {
                return this.store;
            },
            getProjectName: function (projectId, callBack) {

                var projectName;

                this.store.fetch({
                    "query": {
                        "projectId": projectId
                    },
                    onComplete: function (project) {
                        callBack(project.projectName);
                    }
                });
            },
            saveProject: function (callBack, errorCallBack) {
                this.store.save({
                    onComplete: function () {
                        callBack();
                        topic.publish("updateProject", "projectUpdated");
                    },
                    onError: function () {
                        errorCallBack();
                    }
                });
            },
            deleteProject: function (project, callBack, errorCallBack) {
                this.store.deleteItem(project);
                this.store.save({
                    onComplete: function () {
                        callBack();
                        topic.publish("updateProject", "projectUpdated");
                    },
                    onError: function () {
                        errorCallBack();
                    }
                });
            },
            addProject: function (project, callBack, errorCallBack) {
                this.store.newItem(project);
                this.store.save({
                    onComplete: function () {
                        callBack();
                        topic.publish("updateProject", "projectUpdated");
                    },
                    onError: function () {
                        errorCallBack();
                    }
                });
            }
        };

        var userManager = {
            store: new JsonRestStore({
                target: "/user/list"
            }),
            getStore: function () {
                return this.store;
            },
            saveUser: function (callBack, errorCallBack) {
                this.store.save({
                    onComplete: function () {
                        callBack();
                        topic.publish("updateUser", "save User");
                    },
                    onError: function () {
                        errorCallBack();
                    }
                });
            },
            addUser: function (user, callBack, errorCallBack) {
                this.store.newItem(user);
                this.store.save({
                    onComplete: function () {
                        callBack();
                        topic.publish("updateUser", user.username);
                    },
                    onError: function () {
                        errorCallBack();
                    }
                });
            },
            deleteUser: function (user, callBack, errorCallBack) {
                this.store.deleteItem(user);
                this.store.save({
                    onComplete: function () {
                        callBack();
                        topic.publish("updateUser", "delete user");
                    },
                    onError: function () {
                        errorCallBack();
                    }
                });
            }
        };

        var saveComputationManager = {
            store: new JsonRestStore({
                target: "/saveComputation/list"
            }),
            getStore: function () {
                return this.store;
            },
            getSaveComputationByDate: function (startDate, endDate, ammeterName, callBack, errorCallBack) {
                this.store.fetch({
                    "query": {
                        "startDate": startDate,
                        "endDate": endDate,
                        "ammeterName": ammeterName
                    },
                    onComplete: function (saveComputation) {
                        callBack(saveComputation);
                    }
                });
            }
        };

        var saveComputationRecordManager = {
            store: new JsonRestStore({
                target: "/scr/list"
            }),
            jsonRest: new JsonRest({
                target: "scr/list"
            }),
            getStore: function () {
                return this.store;
            },
            getJsonRest: function () {
                return this.jsonRest;
            },
            addSaveComputationRecord: function (saveComputation, callBack, errorCallBack) {
                this.store.newItem(saveComputation);
                this.store.save({
                    onComplete: function () {
                        callBack();
                        topic.publish("updateSaveComputationRecord");
                    },
                    onError: function () {
                        errorCallBack();
                    }
                });
            },
            getSaveComputationRecord: function (callBack, errorCallBack) {
                this.store.fetch({
                    "query": {},
                    onComplete: function (records) {
                        console.log("ok");
                        callBack(records);
                    }
                });
            }
        };

        var gprsManager = {
            store: new JsonRestStore({
                target: "/gprs/list"
            }),
            getStore: function () {
                return this.store;
            },
            addGPRS: function (gprs, callBack, errorCallBack) {
                this.store.newItem(gprs);
                this.store.save({
                    onComplete: function () {
                        callBack();
                        topic.publish("updateGPRS", "update");
                    },
                    onError: function () {
                        errorCallBack();
                    }
                });
            },
            getGPRS: function (callBack, errorCallBack) {
                this.store.fetch({
                    "query": {},
                    onComplete: function (gprsModules) {
                        callBack(gprsModules);
                    },
                    onError: function () {
                        errorCallBack();
                    }
                });
            },
            saveGPRS: function (callBack, errorCallBack) {
                this.store.save({
                    onComplete: function () {
                        callBack();
                        topic.publish("updateGPRS", "gprsUpdated");
                    },
                    onError: function () {
                        errorCallBack();
                    }
                });
            },
            deleteGPRS: function (gprs, callBack, errorCallBack) {
                this.store.deleteItem(gprs);
                this.store.save({
                    onComplete: function () {
                        callBack();
                        topic.publish("updateGPRS", "gprsUpdated");
                    },
                    onError: function () {
                        errorCallBack();
                    }
                });
            }
        };

        var ammeterGPRSManager = {
            store: new JsonRestStore({
                target: "/ammetergprs/list"
            }),
            getStore: function () {
                return this.store;
            },
            addAmmeterGPRS: function (gprsAmmeterLink, callBack, errorCallBack) {
                this.store.newItem(gprsAmmeterLink);
                this.store.save({
                    onComplete: function () {
                        callBack;
                        topic.publish("ammetergprs", "update");
                    },
                    onError: function () {
                        errorCallBack()
                    }
                });
            },

            getAmmeterGPRS: function (callBack, errorCallBack) {
                this.store.fetch({
                    "query": {},
                    onComplete: function (ammeterGPRSLinks) {
                        callBack(ammeterGPRSLinks);
                    },
                    onError: function () {
                        errorCallBack();
                    }
                });
            },

            saveAmmeterGPRS: function (callBack, errorCallBack) {
                this.store.save({
                    onComplete: function () {
                        callBack();
                        topic.publish("ammetergprs", "gprsUpdated");
                    },
                    onError: function () {
                        errorCallBack();
                    }
                });
            },
            deleteAmmeterGPRS: function (ammetergprs, callBack, errorCallBack) {
                this.store.deleteItem(ammetergprs);
                this.store.save({
                    onComplete: function () {
                        callBack();
                        topic.publish("ammetergprs", "gprsUpdated");
                    },
                    onError: function () {
                        errorCallBack();
                    }
                });
            }
        };

        var constructors = {
            ammeterMonitorPaneConstructor: function () {
//                var ammeterMonitorGrid =
            },

            CompanyPaneConstructor: function () {
                var construtCompanyGrid = function construtCompanyGrid() {
                    companyGrid = new EnhancedGrid({
                        store: companyManager.getStore(),
                        autoWidth: true,
                        structure: layouts.companyGridLayout,
                        plugins: {
                            search: true,
                            filter: true,
                            printer: true,
                            indirectSelection: {
                                headerSelector: true,
                                width: "40px",
                                styles: "text-align: center;"
                            }
                        }
                    }, "company_grid");
                    companyGrid.startup();
                    topic.subscribe("updateCompany", function () {
                        companyGrid.setQueryAfterLoading({
                            "id": "*"
                        });
                    });
                }
                if (!companyPaneConstruted) {
                    if (typeof companyPane != "undefined") {
                        tabContainer.addChild(companyPane, 0);
                        tabContainer.selectChild(companyPane);
                        construtCompanyGrid();
                        companyPaneConstruted = true;
                    }
                } else {
                    tabContainer.selectChild(companyPane);
                }
            },
            CreateProjectDialogConstructor: function (companyName) {
                if (request) {
                    request.ammetersForProject = [];
                    request.usersForProject = [];
                }
                var projectCompanyCombo = registry.byId("projectCompany");
                var clearComponents = function () {
                    var projectCompanyCombo = registry.byId("projectCompanyCombo");
                    var projectCompanyTextBox = registry.byId("projectCompanyTextBox");
                    if (projectCompanyCombo) {
                        registry.remove("projectCompanyCombo");
                        domConstruct.destroy("widget_projectCompanyCombo");
                    }
                    if (projectCompanyTextBox) {
                        registry.remove("projectCompanyTextBox");
                        domConstruct.destroy("widget_projectCompanyTextBox");
                    }
                };
                if (registry.byId("createNewCompanyRadio").checked) {
                    clearComponents();
                    var projectCompanyTextBox = document.getElementById("projectCompanyTextBox") || document.createElement("input");
                    projectCompanyTextBox.setAttribute("id", "projectCompanyTextBox");
                    document.getElementById("projectCompanyLi").appendChild(projectCompanyTextBox);
                    var companyForProjectTextBox = new TextBox({
                        placeHolder: "输入公司名称"
                    }, "projectCompanyTextBox");
                }
                if (registry.byId("selectExistingCompanyradio").checked) {
                    clearComponents();
                    var projectCompanyCombo = document.getElementById("projectCompanyCombo") || document.createElement("input");
                    projectCompanyCombo.setAttribute("id", "projectCompanyCombo");
                    document.getElementById("projectCompanyLi").appendChild(projectCompanyCombo);
                    var projectCompanyCombo = new ComboBox({
                        id: "projectCompanyCombo",
                        name: "company",
                        value: companyName,
                        store: companyManager.getStore(),
                        searchAttr: "companyName"
                    }, "projectCompanyCombo");
                }
                var createProjectForCompanyDialog = registry.byId("createProjectForCompanyDialog");
                if (createProjectForCompanyDialog) {
                    dojo.byId("createProjectDialogHiddenBtn").style.display = "inline";
                    createProjectForCompanyDialog.show();
                }
            },
            ProjectPaneConstructor: function (projectPaneName, companyId) {
                projectPaneName = projectPaneName || "项目管理";
                var paneNode = dijit.byId(projectPaneName);
                if (paneNode) {
                    var paneGrid = dijit.byId(projectPaneName + "Grid");
                    if (paneGrid) {
                        paneGrid.setQueryAfterLoading({"id": "*"});
                        //tabContainer.addChild(paneNode);
                        tabContainer.selectChild(dijit.byId(paneNode));
                        return;
                    }
                } else {
                    paneNode = constructNewPane(projectPaneName, projectPaneName, "width: 100%;", tabContainer);
                    //create button node
                    var addButtonNodeId = paneNode + "AddButton";
                    var newAddButtonNode = document.createElement("div");
                    newAddButtonNode.setAttribute("id", addButtonNodeId);
                    document.getElementById(paneNode).appendChild(newAddButtonNode);
                    //construt add button
                    var add_project_btn = new Button({
                        label: "新建",
                        onClick: function () {
                            constructors.CreateProjectDialogConstructor(companyId);
                        }
                    }, addButtonNodeId);
                    add_project_btn.startup();
                    //create close tab button
                    constructCloseTabBtn(paneNode);
                    //create grid node
                    var gridNodeId = paneNode + "Grid";
                    var newGridNode = document.createElement("div");
                    document.getElementById(paneNode).appendChild(newGridNode);
                    newGridNode.setAttribute("id", gridNodeId);
                    newGridNode.setAttribute("style", "height:400px;");
                    var newGridLayout = layouts.projectGridLayout;
                    var newGrid = constructNewGridForPane(gridNodeId, newGridLayout, projectManager.getStore());
                    if (companyId) {
                        newGrid.setQueryAfterLoading({
                            "companyId": companyId
                        });
                        topic.subscribe("updateProject", function (text) {
                            newGrid.setQueryAfterLoading({"companyId": companyId});
                        });
                    } else {
                        topic.subscribe("updateProject", function (text) {
                            newGrid.setQueryAfterLoading({"id": "*"});
                        });
                    }
                    //save btn
                    var saveButtonNodeId = paneNode + "SaveButton";
                    var newSaveButtonNode = document.createElement("div");
                    newSaveButtonNode.setAttribute("id", saveButtonNodeId);
                    document.getElementById(paneNode).appendChild(newSaveButtonNode);
                    var saveProjectBtn = new Button({
                        label: "保存",
                        onClick: function () {
                            var saveProjectSuccessCallBack = function () {
                            };
                            var saveProjectErrorCallBack = function () {
                            };
                            projectManager.saveProject(saveProjectSuccessCallBack, saveProjectErrorCallBack);
                        }
                    }, saveButtonNodeId);
                    saveProjectBtn.startup();
                    //delete btn
                    var deleteButtonNodeId = paneNode + "DeleteButton";
                    var newDeleteButtonNode = document.createElement("div");
                    newDeleteButtonNode.setAttribute("id", deleteButtonNodeId);
                    document.getElementById(paneNode).appendChild(newDeleteButtonNode);
                    var deleteProjectBtn = new Button({
                        label: "删除",
                        onClick: function () {
                            var deleteProjectSuccessCallBack = function () {
                            };
                            var deleteProjectErrorCallBack = function () {
                            };
                            var projectGrid = registry.byId(gridNodeId);
                            var projectSelected = projectGrid.selection.getSelected();
                            if (projectSelected.length) {
                                for (key in projectSelected) {
                                    projectManager.deleteProject(projectSelected[key], deleteProjectSuccessCallBack, deleteProjectErrorCallBack);
                                }
                            }
                            ;
                        }
                    }, deleteButtonNodeId);
                    saveProjectBtn.startup();

                }
            },
            GPRSPaneConstructor: function (gprsName) {
                var gprsName = gprsName || "GPRS模块管理";
                var paneNode = dijit.byId(gprsName);
                if (paneNode) {
                    var paneGrid = dijit.byId(gprsName + "Grid");
                    if (paneGrid) {
                        paneGrid.setQueryAfterLoading({"id": "*"});
                        tabContainer.selectChild(dijit.byId(paneNode));
                        return;
                    }
                } else {
                    paneNode = constructNewPane(gprsName, gprsName, "width: 100%;", tabContainer);
                    //create button node
                    var addButtonNodeId = paneNode + "AddButton";
                    var newAddButtonNode = document.createElement("div");
                    newAddButtonNode.setAttribute("id", addButtonNodeId);
                    document.getElementById(paneNode).appendChild(newAddButtonNode);
                    //construt add button
                    var addGPRSBtn = new Button({
                        label: "新建",
                        onClick: function () {
                            constructors.CreateGPRSDialogConstructor();
                        }
                    }, addButtonNodeId);
                    addGPRSBtn.startup();
                    //create close tab button
                    constructCloseTabBtn(paneNode);
                    //create grid node
                    var gridNodeId = paneNode + "Grid";
                    var newGridNode = document.createElement("div");
                    document.getElementById(paneNode).appendChild(newGridNode);
                    newGridNode.setAttribute("id", gridNodeId);
                    newGridNode.setAttribute("style", "height:400px;");
                    var newGridLayout = layouts.gprsGridLayout;
                    var newGrid = constructNewGridForPane(gridNodeId, newGridLayout, gprsManager.getStore());
                    //save btn
                    var saveButtonNodeId = paneNode + "SaveButton";
                    var newSaveButtonNode = document.createElement("div");
                    newSaveButtonNode.setAttribute("id", saveButtonNodeId);
                    document.getElementById(paneNode).appendChild(newSaveButtonNode);
                    var saveGPRSBtn = new Button({
                        label: "保存",
                        onClick: function () {
                            var saveGPRSSuccessCallBack = function () {
                            };
                            var saveGPRSErrorCallBack = function () {
                            };
                            gprsManager.saveGPRS(saveGPRSSuccessCallBack, saveGPRSErrorCallBack);
                        }
                    }, saveButtonNodeId);
                    saveGPRSBtn.startup();
                    //delete btn
                    var deleteButtonNodeId = paneNode + "DeleteButton";
                    var newDeleteButtonNode = document.createElement("div");
                    newDeleteButtonNode.setAttribute("id", deleteButtonNodeId);
                    document.getElementById(paneNode).appendChild(newDeleteButtonNode);
                    var deleteGPRSBtn = new Button({
                        label: "删除",
                        onClick: function () {
                            var deleteGPRSSuccessCallBack = function () {
                            };
                            var deleteGPRSErrorCallBack = function () {
                            };
                            var gprsGrid = registry.byId(gridNodeId);
                            var gprsSeleted = gprsGrid.selection.getSelected();
                            if (gprsSeleted.length) {
                                for (var key in gprsSeleted) {
                                    gprsManager.deleteGPRS(gprsSeleted[key], deleteGPRSSuccessCallBack, deleteGPRSErrorCallBack);
                                }
                            }
                        }
                    }, deleteButtonNodeId);
                    deleteGPRSBtn.startup();
                    topic.subscribe("updateGPRS", function (text) {
                        newGrid.setQueryAfterLoading({"id": "*"});
                    });
                }
            },
            CreateGPRSDialogConstructor: function () {
                registry.byId("createGPRSDialog").show();
            },
            AmmeterPaneConstructor: function (ammeterPaneName, projectId, gprsId) {
                console.log("projectId" + projectId);
                console.log("gprsId" + gprsId);
                ammeterPaneName = ammeterPaneName || "电表管理";
                var paneNode = dijit.byId(ammeterPaneName);
                if (paneNode) {
                    var paneGrid = dijit.byId(ammeterPaneName + "Grid");
                    paneGrid.setQueryAfterLoading({"id": "*"});
                    //tabContainer.addChild(paneNode);
                    tabContainer.selectChild(dijit.byId(paneNode));
                    return;
                } else {
                    paneNode = constructNewPane(ammeterPaneName, ammeterPaneName, "width: 100%;", tabContainer);
                    //create button node
                    var addButtonNodeId = paneNode + "AddButton";
                    var newAddButtonNode = document.createElement("div");
                    newAddButtonNode.setAttribute("id", addButtonNodeId);
                    document.getElementById(paneNode).appendChild(newAddButtonNode);
                    //construt add button
                    var add_ammeter_btn = new Button({
                        label: "新建",
                        onClick: function () {
                            constructors.CreateAmmeterDialogConstructor(projectId);
                        }
                    }, addButtonNodeId);
                    add_ammeter_btn.startup();
                    //create close tab button
                    constructCloseTabBtn(paneNode);
                    //create grid node
                    var gridNodeId = paneNode + "Grid";
                    var newGridNode = document.createElement("div");
                    document.getElementById(paneNode).appendChild(newGridNode);
                    newGridNode.setAttribute("id", gridNodeId);
                    newGridNode.setAttribute("style", "height:400px;");
                    var newGridLayout = layouts.ammeterGridLayout;
                    var newGrid = constructNewGridForPane(gridNodeId, newGridLayout, ammeterManager.getStore());
                    if (projectId) {
                        newGrid.setQueryAfterLoading({
                            "projectId": projectId
                        });
                        topic.subscribe("updateAmmeter", function (text) {
                            newGrid.setQueryAfterLoading({"projectId": projectId});
                        });
                    } else if (gprsId) {
                        newGrid.setQueryAfterLoading({
                            "gprsId": gprsId
                        });
                        topic.subscribe("updateAmmeter", function (text) {
                            newGrid.setQueryAfterLoading({"gprsId": gprsId});
                        });
                    } else {
                        topic.subscribe("updateAmmeter", function (text) {
                            newGrid.setQueryAfterLoading({"id": "*"});
                        });
                    }

                    //save btn
                    var saveButtonNodeId = paneNode + "SaveButton";
                    var newSaveButtonNode = document.createElement("div");
                    newSaveButtonNode.setAttribute("id", saveButtonNodeId);
                    document.getElementById(paneNode).appendChild(newSaveButtonNode);
                    var saveAmmeterBtn = new Button({
                        label: "保存",
                        onClick: function () {
                            var saveAmmeterSuccessCallBack = function () {
                            };
                            var saveAmmeterErrorCallBack = function () {
                            };
                            ammeterManager.saveAmmeter(saveAmmeterSuccessCallBack, saveAmmeterErrorCallBack);
                        }
                    }, saveButtonNodeId);
                    saveAmmeterBtn.startup();
                    //delete btn
                    var deleteButtonNodeId = paneNode + "DeleteButton";
                    var newDeleteButtonNode = document.createElement("div");
                    newDeleteButtonNode.setAttribute("id", deleteButtonNodeId);
                    document.getElementById(paneNode).appendChild(newDeleteButtonNode);
                    var deleteAmmeterBtn = new Button({
                        label: "删除",
                        onClick: function () {
                            var deleteAmmeterSuccessCallBack = function () {
                            };
                            var deleteAmmeterErrorCallBack = function () {
                            };
                            var ammeterGrid = registry.byId(gridNodeId);
                            var ammeterSelected = ammeterGrid.selection.getSelected();
                            if (ammeterSelected.length) {
                                for (key in ammeterSelected) {
                                    ammeterManager.deleteAmmeter(ammeterSelected[key], deleteAmmeterSuccessCallBack, deleteAmmeterErrorCallBack);
                                }
                            }
                            ;
                        }
                    }, deleteButtonNodeId);
                }
            },

            CreateAmmeterDialogConstructor: function (projectId) {
                if (projectId) {
                    var projectForAmmeterCombo = registry.byId("ammeterProject");
                    var getProjectNameCallBack;
                    if (!projectForAmmeterCombo) {
                        getProjectNameCallBack = function (projectName) {
                            var projectForAmmeterCombo = new ComboBox({
                                id: "ammeterProject",
                                name: "project",
                                value: projectName,
                                store: projectManager.getStore(),
                                searchAttr: "projectName"
                            }, "ammeterProject");
                            registry.byId("createAmmeterDialog").show();
                        };
                    } else {
                        getProjectNameCallBack = function (projectName) {
                            projectForAmmeterCombo.set("value", projectName);
                            registry.byId("createAmmeterDialog").show();
                        };
                    }
                    projectManager.getProjectName(projectId, getProjectNameCallBack);
                } else {
                    var projectForAmmeterCombo = registry.byId("ammeterProject");
                    if (!projectForAmmeterCombo) {
                        var projectForAmmeterCombo = new ComboBox({
                            id: "ammeterProject",
                            name: "project",
                            // value: projectName,
                            store: projectManager.getStore(),
                            searchAttr: "projectName"
                        }, "ammeterProject");
                    }
                }
                var clearComponents = function () {
                    var ammeterGPRSCombo = registry.byId("ammeterGPRSCombo");
                    var ammeterGPRSNameTextBox = registry.byId("ammeterGPRSNameTextBox");
                    var ammeterGPRSIdentifierTextBox = registry.byId("ammeterGPRSIdentifierTextBox");
                    if (ammeterGPRSCombo) {
                        registry.remove("ammeterGPRSCombo");
                        domConstruct.destroy("widget_ammeterGPRSCombo");
                    }
                    if (ammeterGPRSNameTextBox) {
                        registry.remove("ammeterGPRSNameTextBox");
                        domConstruct.destroy("widget_ammeterGPRSNameTextBox");
                    }
                    if (ammeterGPRSIdentifierTextBox) {
                        registry.remove("ammeterGPRSIdentifierTextBox");
                        domConstruct.destroy("widget_ammeterGPRSIdentifierTextBox");
                    }
                };
                if (registry.byId("createNewGPRSRadio").checked) {
                    clearComponents();
                    var ammeterGPRSNameTextBox = document.getElementById("ammeterGPRSNameTextBox") || document.createElement("input");
                    ammeterGPRSNameTextBox.setAttribute("id", "ammeterGPRSNameTextBox");
                    document.getElementById("ammeterGPRSLi").appendChild(ammeterGPRSNameTextBox);
                    var ammeterGPRSNameTextBox = new TextBox({
                        placeHolder: "输入GPRS名称"
                    }, "ammeterGPRSNameTextBox");
                    var ammeterGPRSIdentifierTextBox = document.getElementById("ammeterGPRSIdentifierTextBox") || document.createElement("input");
                    ammeterGPRSIdentifierTextBox.setAttribute("id", "ammeterGPRSIdentifierTextBox");
                    document.getElementById("ammeterGPRSLi").appendChild(ammeterGPRSIdentifierTextBox);
                    var ammeterGPRSIdentifierTextBox = new TextBox({
                        placeHolder: "输入GPRS识别码"
                    }, "ammeterGPRSIdentifierTextBox");
                    document.getElementById("widget_ammeterGPRSIdentifierTextBox").setAttribute("style", "margin-left:2em;")
                }
                if (registry.byId("selectExistingGPRSRadio").checked) {
                    clearComponents();
                    var ammeterGPRSCombo = document.getElementById("ammeterGPRSCombo") || document.createElement("input");
                    ammeterGPRSCombo.setAttribute("id", "ammeterGPRSCombo");
                    document.getElementById("ammeterGPRSLi").appendChild(ammeterGPRSCombo);
                    var ammeterGPRSCombo = new ComboBox({
                        id: "ammeterGPRSCombo",
                        name: "name",
                        value: "",
                        store: gprsManager.getStore(),
                        searchAttr: "name"
                    }, "ammeterGPRSCombo");
                }
                registry.byId("createAmmeterDialog").show();
            },

            UserPaneConstructor: function () {
                var construtUserGrid = function construtUserGrid() {
                    userStore = new JsonRestStore({
                        target: "/user/list/"
                    });
                    userGrid = new EnhancedGrid({
                        store: userManager.getStore(),
                        structure: layouts.userLayout,
                        plugins: {
                            search: true,
                            filter: true,
                            printer: true,
                            indirectSelection: {
                                headerSelector: true,
                                width: "40px",
                                styles: "text-align: center;"
                            }
                        }
                    }, "user_grid");
                    userGrid.startup();
                    topic.subscribe("updateUser", function (text) {
                        userGrid.setQueryAfterLoading({
                            "id": "*"
                        });
                    });


                    var save_button = new Button({
                        label: "保存",
                        onClick: function () {
                            userManager.saveUser();
                            // userDataStore.save();

                        }
                    }, "user_save_button");
                    save_button.startup();

                    //construt delete button
                    var delete_button = new Button({
                        label: "删除",
                        onClick: function () {
                            var user_selected = userGrid.selection.getSelected();
                            if (user_selected.length) {
                                for (var i = 0; i < user_selected.length; i++) {

                                    var deleteUserSuccCallBack = function () {

                                    };

                                    var deleteUserErrorCallBack = function () {

                                    };
                                    userManager.deleteUser(user_selected[i], deleteUserSuccCallBack, deleteUserErrorCallBack);
                                }
                            }
                        }
                    }, "user_delete_button");
                    delete_button.startup();
                }

                var constructProjectCompanyForUserCombo = function constructProjectCompanyForUserCombo() {

                    companyForUserStore = new JsonRestStore({
                        target: "/company/list/"
                    });

                    projectForUserStore = new JsonRestStore({
                        target: "/project/list/"
                    });

                    var projectForUserCombo = new ComboBox({
                        id: "projectForUser",
                        name: "project",
                        value: "",
                        store: projectForUserStore,
                        searchAttr: "projectName"
                    }, "projectForUser");

                    var companyForAmmeterCombo = new ComboBox({
                        id: "companyForUser",
                        name: "company",
                        value: "",
                        store: companyForUserStore,
                        searchAttr: "companyName"
                    }, "companyForUser");
                };

                construtUserGrid();
                constructProjectCompanyForUserCombo();
            },

            ProjectUserPaneConstructor: function () {

                var upGrid;

                var construtUPGrid = function construtUPGrid() {
                    upGrid = constructNewGridForPane("up_grid", layouts.upGridLayout, projectUserManager.getStore());
                };

                var constructUPBtns = function constructUPBtns() {
                    //construt add button
                    var add_up_btn = new Button({

                        label: "新建",
                        onClick: function () {
                            var userProject = {
                                userName: dijit.byId("userForUPCombo").get("value"),
                                projectName: dijit.byId("projectForUPCombo").get("value")
                            };

                            var addUserProjectSuccCallBack = function () {

                            };
                            var addUserProjectErrorCallBack = function () {

                            };
                            projectUserManager.addProjectUser(userProject, addUserProjectSuccCallBack, addUserProjectErrorCallBack);

                        }
                    }, "add_up_btn");
                    add_up_btn.startup();
                    //construt delete button
                    var up_delete_button = new Button({
                        label: "删除",
                        onClick: function () {
                            var up_selected = upGrid.selection.getSelected();
                            if (up_selected.length) {
                                for (var i = 0; i < up_selected.length; i++) {
                                    var deleteUserProjectSuccCallBack = function () {

                                    };
                                    var deleteUserProjectErrorCallBack = function () {

                                    };
                                    projectUserManager.deleteProjectUser(up_selected[i], deleteUserProjectSuccCallBack, deleteUserProjectErrorCallBack);
                                }
                            }
                        }
                    }, "up_delete_button");
                    up_delete_button.startup();
                };

                var constructUserProjectForUPCombo = function constructUserProjectForUPCombo() {

                    var projectForUPCombo = new ComboBox({
                        id: "projectForUPCombo",
                        name: "project",
                        value: "",
                        store: projectManager.getStore(),
                        searchAttr: "projectName"
                    }, "projectForUPCombo");

                    var userForUPCombo = new ComboBox({
                        id: "userForUPCombo",
                        name: "user",
                        value: "",
                        store: userManager.getStore(),
                        searchAttr: "username"
                    }, "userForUPCombo");
                };

                if (!upPaneConstruted) {
                    if (typeof upPane != "undefined") {
                        tabContainer.addChild(upPane, 0);
                        tabContainer.selectChild(upPane);
                        construtUPGrid();
                        constructUPBtns();
                        constructUserProjectForUPCombo();
                        upPaneConstruted = true;
                    }
                } else {
                    tabContainer.selectChild(upPane);
                }
            },

            AmmeterGPRSPaneConstructor: function () {

                var agGrid;

                var construtAGGrid = function construtagGrid() {
                    agGrid = constructNewGridForPane("ag_grid", layouts.agGridLayout, ammeterGPRSManager.getStore());
                    topic.subscribe("ammetergprs", function (text) {
                        agGrid.setQueryAfterLoading({
                            "id": "*"
                        });
                    });
                };

                var constructAGBtns = function constructAGBtns() {
                    //construt add button
                    var add_ag_btn = new Button({

                        label: "新建",
                        onClick: function () {
                            console.log(dijit.byId("ammeterForAGCombo").get("value"));
                            var ammeterGPRS = {
                                ammeterName: dijit.byId("ammeterForAGCombo").get("value"),
                                gprsName: dijit.byId("gprsForAGCombo").get("value")
                            };

                            var addAmmeterGPRSSuccCallBack = function () {

                            };
                            var addAmmeterGPRSErrorCallBack = function () {

                            };
                            ammeterGPRSManager.addAmmeterGPRS(ammeterGPRS, addAmmeterGPRSSuccCallBack, addAmmeterGPRSErrorCallBack);

                        }
                    }, "add_ag_btn");
                    add_ag_btn.startup();
                    //construt delete button
                    var ag_delete_button = new Button({
                        label: "删除",
                        onClick: function () {
                            var ag_selected = agGrid.selection.getSelected();
                            if (ag_selected.length) {
                                for (var i = 0; i < ag_selected.length; i++) {
                                    var deleteAmmeterGPRSSuccCallBack = function () {

                                    };
                                    var deleteAmmeterGPRSErrorCallBack = function () {

                                    };
                                    ammeterGPRSManager.deleteAmmeterGPRS(ag_selected[i], deleteAmmeterGPRSSuccCallBack, deleteAmmeterGPRSErrorCallBack);
                                }
                            }
                        }
                    }, "ag_delete_button");
                    ag_delete_button.startup();
                };

                var constructAmmeterGPRSForAGCombo = function constructAmmeterGPRSForagCombo() {

                    var ammeterForAGCombo = new ComboBox({
                        id: "ammeterForAGCombo",
                        name: "ammeter",
                        value: "",
                        store: ammeterManager.getStore(),
                        searchAttr: "name"
                    }, "ammeterForAGCombo");

                    var gprsForAGCombo = new ComboBox({
                        id: "gprsForAGCombo",
                        name: "gprs",
                        value: "",
                        store: gprsManager.getStore(),
                        searchAttr: "name"
                    }, "gprsForAGCombo");
                };

                if (!agPaneConstruted) {
                    if (typeof agPane != "undefined") {
                        tabContainer.addChild(agPane, 0);
                        tabContainer.selectChild(agPane);
                        construtAGGrid();
                        constructAGBtns();
                        constructAmmeterGPRSForAGCombo();
                        agPaneConstruted = true;
                    }
                } else {
                    tabContainer.selectChild(agPane);
                }
            },

            saveComputationConstructor: function () {
                var constructSaveComputationGrid = function constructSaveComputationGrid() {

                    var saveComputationAmmeterCombo = new ComboBox({
                        id: "saveComputationAmmeter",
                        name: "name",
                        value: "",
                        store: ammeterManager.getStore(),
                        searchAttr: "name"
                    }, "saveComputationAmmeter");

                    saveComputationAmmeterCombo.startup();

                    var targetUrl = "/saveComputation/list";

                    stores.constructSaveComputationStore = new JsonRestStore({
                        target: targetUrl

                    });

                    saveComputationGrid = new EnhancedGrid({
                        store: saveComputationRecordManager.getStore(),
                        autoWidth: true,
                        structure: layouts.saveComputationGridLayout,
                        // structure: layouts.lalalayout,
                        plugins: {
                            search: true,
                            filter: true,
                            printer: true,
                            indirectSelection: {
                                headerSelector: true,
                                width: "40px",
                                styles: "text-align: center;"
                            }
                        },
                        onStyleRow: function (e) {
                            dojo.style(e.node.children[0].children[0].rows[1], 'display', 'none');
                        }
                    }, "saveComputationGrid");
                    saveComputationGrid.startup();
                };

                if (!saveComputationPaneConstructed) {
                    if (typeof saveComputationPane != "undefined") {
                        tabContainer.addChild(saveComputationPane, 0);
                        tabContainer.selectChild(saveComputationPane);
                        constructSaveComputationGrid();
                        saveComputationPaneConstructed = true;
                    }
                } else {
                    tabContainer.selectChild(saveComputationPane);
                }
            },

            saveComputationChartPaneConstructor: function () {


                var constructSaveComputationChart = function constructSaveComputationChart() {
                    var getSaveComputationRecordSucc = function getSaveComputationRecordSucc(saveComputationRecords) {

                        var realCosts = [];
                        var coalSaves = [];
                        var electricSaves = [];
                        var thePartyBonuses = [];
                        var axisXLayout = [];
                        var Y;
                        var getMaxY = function (items) {

                            var maxY = 0;

                            for (var j = 0; j < items.length; j++) {
                                if (items[j] > maxY) {
                                    maxY = items[j];
                                }
                            }
                            return maxY;
                        }

                        for (var i = 0; i < saveComputationRecords.length; i++) {
                            if (saveComputationRecords[i].realCost != undefined) {
                                realCosts.push(saveComputationRecords[i].realCost);
                            } else {
                                realCosts.push(0);
                            }

                            if (saveComputationRecords[i].coalSave != undefined) {
                                coalSaves.push(saveComputationRecords[i].coalSave);
                            } else {
                                coalSaves.push(0);
                            }

                            if (saveComputationRecords[i].eletricSave != undefined) {
                                electricSaves.push(saveComputationRecords[i].eletricSave);
                            } else {
                                electricSaves.push(0);
                            }

                            if (saveComputationRecords[i].thePartyBonus != undefined) {
                                thePartyBonuses.push(saveComputationRecords[i].thePartyBonus);
                            } else {
                                thePartyBonuses.push(0);
                            }

                            var axisXItem = {};
                            axisXItem.value = i + 1;
                            var date = new Date();
                            date.setTime(saveComputationRecords[i].endDate);
                            axisXItem.text = date.getFullYear() + "年" + date.getMonth() + "月" + date.getDate() + "日" + date.getHours() + "时";
                            axisXLayout.push(axisXItem);
                        }

                        if (realCosts && electricSaves && coalSaves && thePartyBonuses) {
                            var maxCandidate = [];
                            maxCandidate.push(getMaxY(realCosts));
                            maxCandidate.push(getMaxY(electricSaves));
                            maxCandidate.push(getMaxY(coalSaves));
                            maxCandidate.push(getMaxY(thePartyBonuses));
                            console.log(maxCandidate);
                            Y = getMaxY(maxCandidate);
                        }

                        // Create the chart within it's "holding" node
                        var chart = new Chart("saveComputationChart");

                        // Set the theme
                        chart.setTheme(Claro);

                        // Add the only/default plot
                        chart.addPlot("default", {
                            type: Lines,
                            markers: true
                        });

                        var maxY = 0;

                        // Add axes
                        chart.addAxis("x", {
                            title: "结算日期",
                            labels: axisXLayout
                        });
                        console.log(Y);

                        chart.addAxis("y", { min: 0, max: Y * 1.5, vertical: true, fixLower: "major", fixUpper: "major" });
                        // Add the series of data
                        chart.addSeries("节约煤", coalSaves);
                        chart.addSeries("节约电量", electricSaves);
                        chart.addSeries("技改后能耗", realCosts);
                        chart.addSeries("用能方收益", thePartyBonuses);
                        // Create the tooltip
                        var tip = new Tooltip(chart, "default");

                        // Create the magnifier
                        var mag = new Magnify(chart, "default");

                        // Render the chart!
                        chart.render();

                        // Create the legend
                        var legend = new SelectableLegend({ chart: chart }, "legend");

                        var vertical = dojo.byId("vertical");
                        var rulesNode = document.createElement('div');
                        vertical.appendChild(rulesNode);
                        var sliderRules = new dijit.form.VerticalRule({
                            count: 11,
                            style: "width:5px;"
                        }, rulesNode);
                        var slider = new dijit.form.VerticalSlider({
                            name: "vertical",
                            value: 10,
                            minimum: 0,
                            maximum: 10,
                            intermediateChanges: true,
                            style: "height:400px;"
                        }, vertical);

                        var zoomY = 1;

                        on(slider, "change", function (value) {
                            zoomY = value / 10 * Y * 1.5;
                            chart.zoomIn("y", [0, zoomY]);
                        });
                    };

                    var getSaveComputationRecordErr = function getSaveComputationRecordErr() {

                    }

                    saveComputationRecordManager.getSaveComputationRecord(getSaveComputationRecordSucc, getSaveComputationRecordErr);


                };


                if (!saveComputationChartPaneConstructed) {
                    if (typeof saveComputationChartPane != "undefined") {
                        tabContainer.addChild(saveComputationChartPane, 0);
                        tabContainer.selectChild(saveComputationChartPane);
                        // constructSaveComputationGrid();
                        constructSaveComputationChart();
                        saveComputationChartPaneConstructed = true;
                    }
                } else {
                    tabContainer.selectChild(saveComputationChartPane);
                }
            }
        };

        var formatters = {
            dateFormatter: function (inDatum) {
                console.log(inDatum);
                console.log(new Date(inDatum));
                return dojo.date.locale.format(new Date(inDatum), this.constraint);
            },

            ammeterGridOptFormatter: function (id) {
                return new Button({
                    label: "查看记录",
                    onClick: function () {
                        construtAmmeterRecordPane(id);
                    }
                });
            },

            projectGridOptFormatter: function (projectId) {
                return new Button({
                    label: "查看电表",
                    onClick: function () {

                        var callBack = function (projectName) {
                            var ammeterPaneName = "项目" + projectName + "的电表";
                            constructors.AmmeterPaneConstructor(ammeterPaneName, projectId);
                        };
                        projectManager.getProjectName(projectId, callBack);

                    }
                });
            },

            gprsGridOptFormatter: function (gprsId) {
                return new Button({
                    label: "查看电表",
                    onClick: function () {

                        var ammeterPaneName = "GPRS编号" + gprsId + "的电表";
                        constructors.AmmeterPaneConstructor(ammeterPaneName, null, gprsId);

                    }
                });
            },

            companyGridOptFormatter: function (companyId) {
                return new Button({
                    label: "查看项目",
                    onClick: function () {
                        var callBack = function (companyName) {
                            var projectPaneName = "公司" + companyName + "的项目";
                            constructors.ProjectPaneConstructor(projectPaneName, companyId);
                        };
                        companyManager.getCompanyName(companyId, callBack);

                    }
                });
            },

            saveComputationGridOptFormatter: function (saveComputationRecordId) {
                return new Button({
                    label: "核算保存",
                    onClick: function () {

                        var saveComputationGrid = registry.byId("saveComputationGrid");
                        saveComputationGrid.setQuery({
                            "id": "*"
                        });

                    }
                });
            },

            userGridOptFormatter: function (id) {
                return new Button({
                    label: "修改密码",
                    onClick: function () {
                        registry.byId("modifyPasswordDialog").show();
                        var modifyPasswordFun = function modifyPasswordFun() {
                            xhr.get({
                                url: "/user/list/" + id + "/" + registry.byId("newPassword").value,
                                timeout: 3000,
                                // give up after 3 seconds
                                handleAs: "json",
                                load: function () {
                                    alert("修改成功！");
                                }
                            });
                        };

                        on(registry.byId("modifyPasswordBtn"), "click", function () {
                            modifyPasswordFun();
                            registry.byId("modifyPasswordDialog").hide();
                        });
                    }
                });
            }
        };

        var layouts = {

            userLayout: [
                {
                    name: "用户编号",
                    field: "id",
                    height: "24px",
                    width: user_cell_width * 0.5 + "px",
                    canSort: true
                },
                {
                    name: "用户名称",
                    field: "username",
                    width: user_cell_width * 0.5 + "px",
                    editable: true
                },
                {
                    name: "用户邮箱",
                    field: "email",
                    width: user_cell_width + "px",
                    editable: true
                },
                {
                    name: "用户角色",
                    field: "roles",
                    width: user_cell_width + "px",
                    editable: true
                },
                {
                    name: "用户公司",
                    field: "company",
                    width: user_cell_width + "px",
                    editable: false
                },
                {
                    name: "用户项目",
                    field: "project",
                    width: user_cell_width + "px",
                    editable: false
                },
                {
                    name: "操作",
                    field: "id",
                    width: project_cell_width,
                    type: dojox.grid.cells._Widget,
                    editable: false,
                    formatter: formatters.userGridOptFormatter
                }

            ],

            lastAmmeterStatusGridLayout: [
                {
                    name: "电表名",
                    field: "ammeterName",
                    width: "15em",
                    canSort: true
                },
                {
                    name: "电表示数(Kwh)",
                    field: "ammeterValue",
                    width: "15em",
                    canSort: true
                },
                {
                    name: "累时器读数",
                    field: "timeSum",
                    width: "15em",
                    editable: true
                },
                {
                    name: "单位小时能耗",
                    field: "costPerHour",
                    width: "15em",
                    canSort: true
                },
                {
                    name: "报警类型",
                    field: "warningStatus",
                    width: "15em",
                    canSort: true
                }
            ],


            saveComputationGridLayout: [
                {
                    cells: [
                        [
                            {
                                name: "电表名", field: "ammeterName"
                            },
                            {
                                name: "项目名", field: "projectName"
                            },
                            {
                                name: "日期", field: "startDate", formatter: formatters.dateFormatter
                            },
                            {
                                name: "累时器值", field: "startTimeSum"
                            },
                            {
                                name: "电表度数(Kwh)", field: "startValue"
                            },
                            {
                                name: "日期", field: "endDate", formatter: formatters.dateFormatter
                            },
                            {
                                name: "累时器值", field: "endTimeSum"
                            },
                            {
                                name: "电表示数", field: "endValue"
                            },
                            {
                                name: "互感器倍率", field: "sensorRate"
                            },
                            {
                                name: "技改后能耗", field: "realCost"
                            },
                            {
                                name: "技改前能耗(可修改)", field: "formerCost", editable: true
                            },
                            {
                                name: "节电量", field: "eletricSave"
                            },
                            {
                                name: "节电费", field: "eletricChargeSave"
                            },
                            {
                                name: "折算煤系数(可修改)", field: "standardCoalRatio", editable: true
                            },
                            {
                                name: "节约标煤", field: "coalSave"
                            },
                            {
                                name: "标准电费(可修改)", field: "eletricCharge", editable: true
                            },
                            {
                                name: "成分比率(可修改)", field: "partsRatio", editable: true
                            },
                            {
                                name: "节能公司收益", field: "theOtherPartyBouns"
                            },
                            {
                                name: "用能公司收益", field: "thePartyBonus"
                            }
                        ],
                        [
                            {
                                name: "电表信息", colSpan: 2
                            },
                            {
                                name: "抄表起始示数", colSpan: 3
                            },
                            {
                                name: "抄表结束示数", colSpan: 3
                            },
                            {
                                name: "节能计算", colSpan: 11
                            }
                        ]
                    ],
                    onBeforeRow: function (inDataIndex, inSubRows) {
                        if (inDataIndex >= 0) {
                            inSubRows[1].invisible = true;
                        } else {
                            inSubRows[1].invisible = false;
                        }
                    }
                }
            ],

            upGridLayout: [
                {
                    name: "编号",
                    field: "id",
                    width: up_cell_width * 0.2 + "px",
                    canSort: true
                },
                {
                    name: "项目编号",
                    field: "projectId",
                    width: up_cell_width + "px",
                    canSort: true
                },
                {
                    name: "项目名称",
                    field: "projectName",
                    width: up_cell_width + "px",
                    editable: true
                },
                {
                    name: "用户编号",
                    field: "userId",
                    width: up_cell_width + "px",
                    canSort: true
                },
                {
                    name: "用户名称",
                    field: "userName",
                    width: up_cell_width + "px",
                    canSort: true
                }
            ],

            agGridLayout: [
                {
                    name: "编号",
                    field: "id",
                    width: up_cell_width * 0.2 + "px",
                    canSort: true
                },
                {
                    name: "电表编号",
                    field: "ammeterId",
                    width: up_cell_width + "px",
                    canSort: true
                },
                {
                    name: "电表标识",
                    field: "ammeterName",
                    width: up_cell_width + "px",
                    canSort: true
                },
                {
                    name: "GPRS编号",
                    field: "gprsId",
                    width: up_cell_width + "px",
                    canSort: true
                },
                {
                    name: "GPRS名称",
                    field: "gprsName",
                    width: up_cell_width + "px",
                    canSort: true
                }
            ],

            ammeterGridLayout: [
                {
                    name: "电表编号",
                    field: "id",
                    width: ammeter_cell_width * 0.5 + "px",
                    canSort: true
                },
                {
                    name: "电表标识",
                    field: "name",
                    width: ammeter_cell_width + "px",
                    editable: true
                },
                {
                    name: "电表名称",
                    field: "pumpName",
                    width: ammeter_cell_width + "px",
                    editable: true
                },
                {
                    name: "互感器倍率",
                    field: "sensorRate",
                    width: ammeter_cell_width * 0.5 + "px",
                    canSort: true
                },
                {
                    name: "技改前能耗",
                    field: "formerCost",
                    width: ammeter_cell_width * 0.5 + "px",
                    canSort: true
                },
                {
                    name: "报警上限",
                    field: "upperLimit",
                    width: ammeter_cell_width * 0.5 + "px",
                    canSort: true
                },
                {
                    name: "报警下限",
                    field: "lowerLimit",
                    width: ammeter_cell_width * 0.5 + "px",
                    canSort: true
                },
                {
                    name: "操作",
                    field: "id",
                    type: dojox.grid.cells._Widget,
                    editable: false,
                    formatter: formatters.ammeterGridOptFormatter
                }
            ],

            projectGridLayout: [
                {
                    name: "项目编号",
                    field: "id",
                    width: project_cell_width,
                    canSort: true
                },
                {
                    name: "项目名称",
                    field: "projectName",
                    width: project_cell_width,
                    editable: true
                },
                {
                    name: "项目开始日期",
                    field: "startDate",
                    width: project_cell_width,
                    formatter: formatters.dateFormatter,
                    canSort: true
                },
                {
                    name: "项目结束日期",
                    field: "endDate",
                    width: project_cell_width,
                    formatter: formatters.dateFormatter,
                    canSort: true
                },
                {
                    name: "当前电费",
                    field: "electricityCharge",
                    width: project_cell_width,
                    canSort: true
                },
                {
                    name: "分成比率",
                    field: "partsRatio",
                    width: project_cell_width,
                    canSort: true
                },
                {
                    name: "操作",
                    field: "id",
                    width: project_cell_width,
                    type: dojox.grid.cells._Widget,
                    editable: false,
                    formatter: formatters.projectGridOptFormatter
                }
            ],

            companyGridLayout: [
                {
                    name: "公司编号",
                    field: "id",
                    width: company_cell_width + "px",
                    canSort: true
                },
                {
                    name: "公司名称",
                    field: "companyName",
                    width: company_cell_width + "px",
                    editable: true
                },
                {
                    name: "操作",
                    field: "id",
                    width: company_cell_width / 2 + "px",
                    type: dojox.grid.cells._Widget,
                    editable: false,
                    formatter: formatters.companyGridOptFormatter
                }
            ],

            gprsGridLayout: [
                {
                    name: "GPRS编号",
                    field: "id",
                    width: gprs_cell_width,
                    canSort: true
                },
                {
                    name: "GPRS名称",
                    field: "name",
                    width: gprs_cell_width,
                    canSort: true
                },
                {
                    name: "GPRS识别码",
                    field: "identifier",
                    width: gprs_cell_width,
                    canSort: true
                },
                {
                    name: "操作",
                    field: "id",
                    width: project_cell_width,
                    type: dojox.grid.cells._Widget,
                    editable: false,
                    formatter: formatters.gprsGridOptFormatter
                }
            ]
        };
        //project ammeter pane
        construtPAPane = function construtPAPane() {
            var construtPAGrid = function construtPAGrid() {
                paStore = new JsonRestStore({
                    target: "/pa/list/"
                });
                paGrid = new EnhancedGrid({
                    store: paDataStore = paStore,
                    autoWidth: true,
                    structure: [
                        {
                            name: "编号",
                            field: "id",
                            width: pa_cell_width * 0.2 + "px",
                            canSort: true
                        },
                        {
                            name: "项目编号",
                            field: "projectId",
                            width: pa_cell_width + "px",
                            canSort: true
                        },
                        {
                            name: "项目名称",
                            field: "projectName",
                            width: pa_cell_width + "px",
                            editable: true
                        },
                        {
                            name: "电表编号",
                            field: "ammeterId",
                            width: pa_cell_width + "px",
                            canSort: true
                        },
                        {
                            name: "电表标识",
                            field: "ammeterName",
                            width: pa_cell_width + "px",
                            canSort: true
                        }
                    ],
                    plugins: {
                        search: true,
                        filter: true,
                        printer: true,
                        indirectSelection: {
                            headerSelector: true,
                            width: "40px",
                            styles: "text-align: center;"
                        }
                    }
                }, "pa_grid");
                paGrid.startup();

                //construt add button
                var add_pa_btn = new Button({

                    label: "新建",
                    onClick: function () {
                        var form_content = {
                            ammeterName: dijit.byId("ammeterForPACombo").get("value"),
                            projectName: dijit.byId("projectForPACombo").get("value")
                        };
                        xhr.post({
                            form: "add_pa_form",
                            // read the url: from the action="" of the <form>
                            timeout: 3000,
                            // give up after 3 seconds
                            content: form_content,
                            handleAs: "json",
                            load: function (new_pa) {
                                paDataStore.newItem(new_pa);
                                padataStore.save();
                            }
                        });
                    }
                }, "add_pa_btn");
                add_pa_btn.startup();

                //construt save button
                var pa_save_button = new Button({
                    label: "保存",
                    onClick: function () {
                        paDataStore.save();

                    }
                }, "pa_save_button");
                pa_save_button.startup();

                //construt delete button
                var pa_delete_button = new Button({
                    label: "删除",
                    onClick: function () {
                        var pa_selected = paGrid.selection.getSelected();
                        if (pa_selected.length) {
                            for (key in pa_selected) {
                                paDataStore.deleteItem(pa_selected[key]);
                                paDataStore.save();
                            }
                        }
                    }
                }, "pa_delete_button");
                pa_delete_button.startup();
            }

            var constructProjectAmmeterForPACombo = function constructProjectAmmeterForPACombo() {

                projectForPAStore = new JsonRestStore({
                    target: "/project/list/"
                });

                ammeterForPAStore = new JsonRestStore({
                    target: "/ammeter/list/"
                });

                var projectForAmmeterCombo = new ComboBox({
                    id: "projectForPACombo",
                    name: "project",
                    value: "",
                    store: projectForPAStore,
                    searchAttr: "projectName"
                }, "projectForPACombo");

                var companyForAmmeterCombo = new ComboBox({
                    id: "ammeterForPACombo",
                    name: "company",
                    value: "",
                    store: ammeterForPAStore,
                    searchAttr: "name"
                }, "ammeterForPACombo");
            };

            if (!paPaneConstruted) {
                if (typeof paPane != "undefined") {
                    tabContainer.addChild(paPane, 0);
                    tabContainer.selectChild(paPane);
                    construtPAGrid();
                    constructProjectAmmeterForPACombo();
                    paPaneConstruted = true;
                }
            } else {
                tabContainer.selectChild(paPane);
            }
        };

        //company project pane

        construtCPPane = function construtCPPane() {
            var construtCPGrid = function construtCPGrid() {
                cpStore = new JsonRestStore({
                    target: "/cp/list/"
                });
                cpGrid = new EnhancedGrid({
                    store: cpDataStore = cpStore,
                    autoWidth: true,
                    structure: [
                        {
                            name: "编号",
                            field: "id",
                            width: cp_cell_width * 0.2 + "px",
                            canSort: true
                        },
                        {
                            name: "项目编号",
                            field: "projectId",
                            width: cp_cell_width + "px",
                            canSort: true
                        },
                        {
                            name: "项目名称",
                            field: "projectName",
                            width: cp_cell_width + "px",
                            editable: true
                        },
                        {
                            name: "公司编号",
                            field: "companyId",
                            width: cp_cell_width + "px",
                            canSort: true
                        },
                        {
                            name: "公司名称",
                            field: "companyName",
                            width: cp_cell_width + "px",
                            canSort: true
                        }
                    ],
                    plugins: {
                        search: true,
                        filter: true,
                        printer: true,
                        indirectSelection: {
                            headerSelector: true,
                            width: "40px",
                            styles: "text-align: center;"
                        }
                    }
                }, "cp_grid");
                cpGrid.startup();

                //construt add button
                var add_cp_btn = new Button({

                    label: "新建",
                    onClick: function () {
                        var form_content = {
                            companyName: dijit.byId("companyForCP").get("value"),
                            projectName: dijit.byId("projectForCP").get("value")
                        };
                        xhr.post({
                            form: "add_cp_form",
                            // read the url: from the action="" of the <form>
                            timeout: 3000,
                            // give up after 3 seconds
                            content: form_content,
                            handleAs: "json",
                            load: function (new_cp) {
                                cpDataStore.newItem(new_cp);
                                cpdataStore.save();
                            }
                        });
                    }
                }, "add_cp_btn");
                add_cp_btn.startup();

                //construt save button
                var cp_save_button = new Button({
                    label: "保存",
                    onClick: function () {
                        cpDataStore.save();

                    }
                }, "cp_save_button");
                cp_save_button.startup();

                //construt delete button
                var cp_delete_button = new Button({
                    label: "删除",
                    onClick: function () {
                        var cp_selected = cpGrid.selection.getSelected();
                        if (cp_selected.length) {
                            for (key in cp_selected) {
                                cpDataStore.deleteItem(cp_selected[key]);
                                cpDataStore.save();
                            }
                        }
                    }
                }, "cp_delete_button");
                cp_delete_button.startup();
            };

            var constructProjectCompanyForCPCombo = function constructProjectCompanyForCPCombo() {

                companyForCPStoreForCP = new JsonRestStore({
                    target: "/company/list/"
                });

                projectForCPStoreForCP = new JsonRestStore({
                    target: "/project/list/"
                });

                var projectForCPCombo = new ComboBox({
                    id: "projectForCP",
                    name: "project",
                    value: "",
                    store: projectForCPStoreForCP,
                    searchAttr: "projectName"
                }, "projectForCP");

                var companyForCPCombo = new ComboBox({
                    id: "companyForCP",
                    name: "company",
                    value: "",
                    store: companyForCPStoreForCP,
                    searchAttr: "companyName"
                }, "companyForCP");
            };

            if (!cpPaneConstruted) {
                if (typeof cpPane != "undefined") {
                    tabContainer.addChild(cpPane, 0);
                    tabContainer.selectChild(cpPane);
                    construtCPGrid();
                    constructProjectCompanyForCPCombo();
                    cpPaneConstruted = true;
                }
            } else {
                tabContainer.selectChild(cpPane);
            }
        };

        //save computation pane
        constructSaveComputationPane = function constructSaveComputationPane(id) {
            constructors.saveComputationConstructor();

        };//end save computation pane

        //to add
        constructLastAmmeterStatusPane = function constructLastAmmeterStatusPane(id) {

            var constructLastAmmeterStatusGrid = function constructLastAmmeterStatusGrid() {

                var targetUrl = "/lastAmmeterStatus/list";

                stores.lastAmmeterStatusStore = new JsonRestStore({
                    target: targetUrl
                });
                lastAmmeterStatusGrid = new EnhancedGrid({
                    store: stores.lastAmmeterStatusStore,
                    autoWidth: true,
                    structure: layouts.lastAmmeterStatusGridLayout,
                    // structure: layouts.lalalayout,
                    plugins: {
                        search: true,
                        filter: true,
                        printer: true,
                        indirectSelection: {
                            headerSelector: true,
                            width: "40px",
                            styles: "text-align: center;"
                        }
                    },
                }, "lastAmmeterStatusGrid");
                lastAmmeterStatusGrid.startup();
            };

            if (!lastAmmeterStatusPaneConstructed) {
                if (typeof lastAmmeterStatusPane != "undefined") {
                    tabContainer.addChild(lastAmmeterStatusPane, 0);
                    tabContainer.selectChild(lastAmmeterStatusPane);
                    constructLastAmmeterStatusGrid();
                    lastAmmeterStatusPaneConstructed = true;
                }
            } else {
                tabContainer.selectChild(lastAmmeterStatusPane);
            }
        };


        //ammeter record pane
        construtAmmeterRecordPane = function construtAmmeterRecordPane(id) {

            var construtAmmeterRecordGrid = function construtAmmeterRecordGrid() {

                var restUrl = "/ammeter_record/list";

                if (id) {
                    restUrl = restUrl + "/" + id;
                }

                ammeterRecordStore = new JsonRestStore({
                    target: restUrl
                });
                ammeterRecordGrid = new EnhancedGrid({
                    autoWidth: true,
                    store: recordDataStore = ammeterRecordStore,
                    structure: [
                        {
                            name: "编号",
                            field: "id",
                            width: "5em",
                            canSort: true
                        },
                        {
                            name: "电表标识",
                            field: "ammeterName",
                            width: "15em",
                            canSort: true
                        },
                        {
                            name: "电表数值",
                            field: "ammeterValue",
                            width: "15em",
                            canSort: true
                        },
                        {
                            name: "累时器",
                            field: "timeSum",
                            width: "10em",
                            canSort: true
                        },
                        {
                            name: "电表记录日期",
                            field: "recordDate",
                            width: "15em",
                            formatter: formatters.dateFormatter,
                            canSort: true
                        },
                    ],
                    plugins: {
                        search: true,
                        filter: true,
                        printer: true,
                        indirectSelection: {
                            headerSelector: true,
                            width: "40px",
                            styles: "text-align: center;"
                        }
                    }
                }, "ammeter_record_grid");
                ammeterRecordGrid.startup();
            }

            var refreshRecord = registry.byId("refreshRecord");
            on(refreshRecord, "click", function () {
                ammeterRecordGrid.setQueryAfterLoading({
                    "id": "*"
                });
            });

            var startMoniter = registry.byId("startMoniter");
            on(startMoniter, "click", function () {
                
                alert("开始抄表");
            });

            if (!ammeterRecordPaneConstruted) {
                if (typeof ammeterRecordPane != "undefined") {
                    tabContainer.addChild(ammeterRecordPane, 0);
                    tabContainer.selectChild(ammeterRecordPane);
                    construtAmmeterRecordGrid();
                    ammeterRecordPaneConstruted = true;
                }
            } else {
                tabContainer.selectChild(ammeterRecordPane);
            }
        };


        //ammeter pane
        construtAmmeterPane = function construtAmmeterPane(id, title) {

            var paneNode = registry.byId(title);

            if (paneNode) {
                paneGrid = dijit.byId(title + "Grid")
                paneGrid.setQueryAfterLoading({"id": "*"});
                //tabContainer.addChild(paneNode);
                tabContainer.selectChild(dijit.byId(paneNode));
                return;
            } else {
                constructors.AmmeterPaneConstructor();
            }
        };

        //user pane
        construtUserPane = function construtUserPane() {


            if (!userPaneConstruted) {
                if (typeof userPane != "undefined") {
                    tabContainer.addChild(userPane, 0);
                    tabContainer.selectChild(userPane);
                    constructors.UserPaneConstructor();
                    userPaneConstruted = true;
                }
            } else {
                tabContainer.selectChild(userPane);
            }
        };

        //construtUserPane(); 

        //constuctNewPane and return the content node id
        constructNewPane = function constructNewPane(id, title, styles, container) {
            console.log(title);
            var newPane = new ContentPane({
                id: title,
                title: title,
                content: "<div id = " + title + "></div>",
                style: styles
            });
            console.log(newPane);
            container.addChild(newPane, 0);
            container.selectChild(newPane);
            return title;
        };

        //create close tab button
        var constructCloseTabBtn = function constructCloseTabBtn(paneNode) {
            var closeTabButtonNodeId = paneNode + "CloseTabBtn";
            var closeTabButtonNode = document.createElement("div");
            closeTabButtonNode.setAttribute("id", closeTabButtonNodeId);
            document.getElementById(paneNode).appendChild(closeTabButtonNode);
            var addProjectFromExistBtn = new Button({
                label: "关闭选项卡",
                onClick: function () {
                    // registry.byId(paneNode).domNode.style.visibility = 'hidden';
                    tabContainer.removeChild(dijit.byId(paneNode));
                }
            }, closeTabButtonNodeId);
        };

        //constuctNewGrid
        constructNewGridForPane = function constructNewGridForPane(contentNode, layout, store) {

            var newGrid = new EnhancedGrid({
                store: store,
                autoWidth: true,
                structure: layout,
                plugins: {
                    search: true,
                    filter: true,
                    printer: true,
                    indirectSelection: {
                        headerSelector: true,
                        width: "40px",
                        styles: "text-align: center;"
                    }
                }
            }, contentNode);
            newGrid.startup();
            return newGrid;
        };
        //Company Pane Events
        var saveCompanyBtn = registry.byId("saveCompanyBtn");
        if (saveCompanyBtn) {
            on(saveCompanyBtn, "click", function () {
                var saveSuccessCallBack = function () {

                };

                var errorCallBack = function () {

                };
                companyManager.saveCompany(saveSuccessCallBack, errorCallBack);
            });
        }

        var deleteCompanyBtn = registry.byId("deleteCompanyBtn");
        if (deleteCompanyBtn) {
            on(deleteCompanyBtn, "click", function () {
                var deleteCOmpanySuccessCallBack = function () {

                };

                var deleteCompanyErrorCallBack = function () {

                };

                var companyGrid = registry.byId("company_grid");
                var companySelected = companyGrid.selection.getSelected();
                if (companySelected.length) {
                    for (key in companySelected) {
                        companyManager.deleteCompany(companySelected[key], deleteCOmpanySuccessCallBack, deleteCompanyErrorCallBack);
                    }
                }
                ;
            });
        }

        //Company Creation Dialog Events
        var showCreateCompanyDialogBtn = registry.byId("showCreateCompanyDialogBtn");
        if (showCreateCompanyDialogBtn) {
            on(showCreateCompanyDialogBtn, "click", function () {
                var createCompanyDialog = registry.byId("createCompanyDialog");
                if (createCompanyDialog) {
                    createCompanyDialog.show();
                }
            });
        }

        var createCompanyDialogAddBtn = registry.byId("createCompanyDialogAddBtn");
        if (createCompanyDialogAddBtn) {
            on(createCompanyDialogAddBtn, "click", function () {
                var companyName = registry.byId("companyName").value;
                var company = {
                    companyName: companyName
                };
                var addCompanySuccessCallBack = function (company) {
                    topic.publish("updateCompany", company.companyName);
                    var createCompanyDialog = registry.byId("createCompanyDialog");
                    createCompanyDialog.hide();
                }
                var addCompanyErrorCallBack = function (error) {
                    console.log(error);
                }
                companyManager.addCompany(company, addCompanySuccessCallBack, addCompanyErrorCallBack);
            });

        }

        //Projects Creation Dialog Events
        var selectExistingCompanyradio = dijit.byId("selectExistingCompanyradio");
        if (selectExistingCompanyradio) {
            on(selectExistingCompanyradio, "click", function () {
                var projectCompanyCombo = registry.byId("projectCompanyCombo")
                var projectCompanyTextBox = registry.byId("projectCompanyTextBox");
                if (projectCompanyTextBox) {
                    registry.remove("projectCompanyTextBox");
                    domConstruct.destroy("widget_projectCompanyTextBox");
                }

                if (projectCompanyCombo) {
                    registry.remove("projectCompanyCombo");
                    domConstruct.destroy("widget_projectCompanyCombo");
                }

                var projectCompanyCombo = document.getElementById("projectCompanyCombo") || document.createElement("input");
                projectCompanyCombo.setAttribute("id", "projectCompanyCombo");
                document.getElementById("projectCompanyLi").appendChild(projectCompanyCombo);

                if (stores && (!stores.companyStore)) {
                    stores.companyStore = new JsonRestStore({
                        target: "/company/list/"
                    });
                }
                var projectCompanyCombo = new ComboBox({
                    id: "projectCompanyCombo",
                    name: "company",
                    value: "",
                    store: stores.companyStore,
                    searchAttr: "companyName"
                }, "projectCompanyCombo");
            });
        }

        var createNewCompanyRadio = dijit.byId("createNewCompanyRadio");
        if (createNewCompanyRadio) {
            on(createNewCompanyRadio, "click", function () {
                var projectCompanyCombo = registry.byId("projectCompanyCombo");
                var projectCompanyTextBox = registry.byId("projectCompanyTextBox");

                if (projectCompanyCombo) {
                    registry.remove("projectCompanyCombo");
                    domConstruct.destroy("widget_projectCompanyCombo");
                }

                if (projectCompanyTextBox) {
                    registry.remove("projectCompanyTextBox");
                    domConstruct.destroy("widget_projectCompanyTextBox");
                }

                var projectCompanyTextBox = document.getElementById("projectCompanyTextBox") || document.createElement("input");
                projectCompanyTextBox.setAttribute("id", "projectCompanyTextBox");
                document.getElementById("projectCompanyLi").appendChild(projectCompanyTextBox);


                var companyForProjectTextBox = new TextBox({
                    placeHolder: "输入公司名称"
                }, "projectCompanyTextBox");

            });
        }

        var newProjectDialogAddBtn = dijit.byId("newProjectDialogAddBtn");
        if (newProjectDialogAddBtn) {
            on(newProjectDialogAddBtn, "click", function () {

                if (registry.byId("selectExistingCompanyradio").checked) {
                    var companyName = registry.byId("projectCompanyCombo").value;
                }

                var projectName = dom.byId("forCompanyProjectName").value;
                var addProject = function () {
                    var addProjectSuccessCallBack = function () {
                        addCompanyProject();
                        addProjectAmmeter();
                        addProjectUser();
                    };
                    var addProjectErrorCallBack = function () {

                    };
                    var project = {
                        projectName: projectName,
                        startDate: dom.byId("projectStartDate").value,
                        endDate: dom.byId("projectEndDate").value,
                        electricityCharge: dom.byId("electricityCharge").value,
                        partsRatio: dom.byId("partsRatio").value
                    };
                    projectManager.addProject(project, addProjectSuccessCallBack, addProjectErrorCallBack);
                }
                var addCompanyProjectSuccCallBack = function () {

                };
                var addCompanyProjectErrorCallBack = function () {

                };
                var addCompanyProject = function () {
                    var companyProject = {
                        "companyName": companyName,
                        "projectName": projectName
                    };
                    companyProjectManager.addCompanyProject(companyProject, addCompanyProjectSuccCallBack, addCompanyProjectErrorCallBack);
                }
                //add ammeter if there is any ammeter to add

                var addProjectAmmeter = function () {
                    if (request && request.ammetersForProject) {
                        for (var i = 0; i < request.ammetersForProject.length; i++) {

                            var ammeterProject = {
                                "projectName": projectName,
                                "ammeterName": request.ammetersForProject[i]
                            }
                            var addProjectAmmeterSuccCallBack = function () {

                            };
                            var addProjectAmmeterErrorCallBack = function () {

                            };
                            projectAmmeterManager.addProjectAmmeter(ammeterProject, addProjectAmmeterSuccCallBack, addProjectAmmeterErrorCallBack);
                        }
                        request.ammetersForProject = [];
                    }
                };

                //add usersif thereis any ammeter to add

                var addProjectUser = function () {
                    if (request && request.usersForProject) {
                        for (var i = 0; i < request.usersForProject.length; i++) {

                            var userProject = {
                                "projectName": projectName,
                                "userName": request.usersForProject[i]
                            }
                            var addProjectUserSuccCallBack = function () {

                            };
                            var addProjectUserErrorCallBack = function () {

                            };
                            projectUserManager.addProjectUser(userProject, addProjectUserSuccCallBack, addProjectUserErrorCallBack);
                        }
                        request.usersForProject = [];
                    }
                };

                if (registry.byId("createNewCompanyRadio").checked) {
                    var companyName = registry.byId(projectCompanyTextBox).value;
                    var company = {"companyName": companyName};
                    var addCompanySuccessCallBack = function (company) {
                        addProject();
                    };
                    var addCompanyErrorCallBack = function (error) {

                    };
                    companyManager.addCompany(company, addCompanySuccessCallBack, addCompanyErrorCallBack);
                } else {
                    addProject();
                }
                registry.byId("createProjectForCompanyDialog").hide();
            });
        }

        var addAmmeterForProjectCancelBtn = registry.byId("addAmmeterForProjectCancelBtn");
        if (addAmmeterForProjectCancelBtn) {
            on(addAmmeterForProjectCancelBtn, "click", function () {
                registry.byId("AddExistingAmmeterToProjectDialog").hide();
            });
        }

        var createAmmeterDialogCancelBtn = registry.byId("createAmmeterDialogCancelBtn");
        if (createAmmeterDialogCancelBtn) {
            on(createAmmeterDialogCancelBtn, "click", function () {
                registry.byId("createAmmeterDialog").hide();
            });
        }

        var newProjectDialogCancelBtn = dijit.byId("newProjectDialogCancelBtn");
        if (newProjectDialogCancelBtn) {
            on(newProjectDialogCancelBtn, "click", function () {
                registry.byId("createProjectForCompanyDialog").hide();
            });
        }

        var addNewAmmeterForNewCreatingProjectBtn = registry.byId("addNewAmmeterForNewCreatingProjectBtn");
        if (addNewAmmeterForNewCreatingProjectBtn) {
            on(addNewAmmeterForNewCreatingProjectBtn, "click", function () {
                dojo.byId("ammeterProjectLi").style.display = "none";
                registry.byId("createAmmeterDialog").show();
            })
        }

        var addExistingAmmeterForProjectBtn = registry.byId("addExistingAmmeterForProjectBtn");
        if (addExistingAmmeterForProjectBtn) {
            on(addExistingAmmeterForProjectBtn, "click", function () {
                xhr.get({
                    url: "/ammeter/list",
                    timeout: 3000,
                    // give up after 3 seconds
                    handleAs: "json",
                    load: function (ammeterList) {
                        domConstruct.empty("ExstingAmmeterMultiSelect");
                        for (var ammeterKey in ammeterList) {

                            var option = domConstruct.create("option", {
                                innerHTML: ammeterList[ammeterKey].name,
                                className: "seven",
                                style: {fontWeight: "bold"}
                            });

                            dom.byId("ExstingAmmeterMultiSelect").appendChild(option);
                        }
                        var AddExistingAmmeterToProjectDialog = registry.byId("AddExistingAmmeterToProjectDialog");
                        AddExistingAmmeterToProjectDialog.show();
                    }
                });

            });
        }

        var AmmeterMultiSelectRightBtn = registry.byId("AmmeterMultiSelectRightBtn");
        if (AmmeterMultiSelectRightBtn) {
            on(AmmeterMultiSelectRightBtn, "click", function () {
                registry.byId("AddedAmmeterMultiSelect").addSelected(registry.byId("ExstingAmmeterMultiSelect"));
            });
        }

        var AmmeterMultiSelectLeftBtn = registry.byId("AmmeterMultiSelectLeftBtn");
        if (AmmeterMultiSelectLeftBtn) {
            on(AmmeterMultiSelectLeftBtn, "click", function () {
                registry.byId("ExstingAmmeterMultiSelect").addSelected(registry.byId("AddedAmmeterMultiSelect"));
            });
        }

        var addAmmeterForProjectSubmitBtn = registry.byId("addAmmeterForProjectSubmitBtn");
        if (addAmmeterForProjectSubmitBtn) {
            on(addAmmeterForProjectSubmitBtn, "click", function () {
                request.ammetersForProject = [];
                var ammetersToAddOptions = dom.byId("AddedAmmeterMultiSelect").childNodes;
                if (ammetersToAddOptions && (ammetersToAddOptions.length > 0)) {
                    for (var i = 0; i < ammetersToAddOptions.length; i++) {
                        request = request || {};
                        request.ammetersForProject = request.ammetersForProject || [];
                        if (ammetersToAddOptions[i].innerHTML) {
                            request.ammetersForProject.push(ammetersToAddOptions[i].innerHTML);
                        }
                    }
                }

                topic.publish("updateProjectAmmeter", "update");
                registry.byId("AddExistingAmmeterToProjectDialog").hide();
            });
        }

        var addUsersForProjectBtn = registry.byId("addUsersForProjectBtn");
        if (addUsersForProjectBtn) {
            on(addUsersForProjectBtn, "click", function () {
                registry.byId("addUsersToProjectDialog").show();
            });
        }

        var addNewUserForNewCreatingProjectBtn = registry.byId("addNewUserForNewCreatingProjectBtn");
        if (addNewUserForNewCreatingProjectBtn) {
            on(addNewUserForNewCreatingProjectBtn, "click", function () {
                registry.byId("createUserDialog").show();
            });
        }

        var showCreateUserDialogBtn = registry.byId("showCreateUserDialogBtn");
        if (showCreateUserDialogBtn) {
            on(showCreateUserDialogBtn, "click", function () {
                registry.byId("createUserDialog").show();
            });
        }


        var userMultiSelectRightBtn = registry.byId("AmmeterMultiSelectRightBtn");
        if (AmmeterMultiSelectRightBtn) {
            on(AmmeterMultiSelectRightBtn, "click", function () {
                registry.byId("addedUsersMultiSelect").addSelected(registry.byId("exstingAUsersMultiSelect"));
            });
        }

        var userMultiSelectLeftBtn = registry.byId("usersMultiSelectLeftBtn");
        if (usersMultiSelectLeftBtn) {
            on(usersMultiSelectLeftBtn, "click", function () {
                registry.byId("exstingAUsersMultiSelect").addSelected(registry.byId("addedUsersMultiSelect"));
            });
        }

        var addUsersForProjectSubmitBtn = registry.byId("addUsersForProjectSubmitBtn");
        if ("addUsersForProjectSubmitBtn") {
            on(addUsersForProjectSubmitBtn, "click", function () {
                request.usersForProject = [];
                var usersToAddOptions = dom.byId("addedUsersMultiSelect").childNodes;
                if (usersToAddOptions && (usersToAddOptions.length > 0)) {
                    for (var i = 0; i < usersToAddOptions.length; i++) {
                        request.usersForProject = request.usersForProject || [];
                        if (usersToAddOptions[i].innerHTML) {
                            request.usersForProject.push(usersToAddOptions[i].innerHTML);
                        }
                    }
                }
                topic.publish("updateProjectUser", "update");
                registry.byId("addUsersToProjectDialog").hide();
            });
        }

        //Ammeter Creation Dialog Events
        var createAmmeterDialogAddBtn = registry.byId("createAmmeterDialogAddBtn");
        if (createAmmeterDialogAddBtn) {
            on(createAmmeterDialogAddBtn, "click", function () {


                if (registry.byId("createNewGPRSRadio").checked) {
                    var gprsName = registry.byId("ammeterGPRSNameTextBox").value;
                    var gprsIdentifer = registry.byId("ammeterGPRSIdentifierTextBox").value;

                    var gprsModule = {
                        name: gprsName,
                        identifier: gprsIdentifer
                    };

                    var addGprsSuccCallBack = function () {
                        var ammeter = {
                            name: dom.byId("ammeterName").value,
                            pumpName: dom.byId("pumpName").value,
                            projectName: dom.byId("ammeterProject").value,
                            sensorRate: dom.byId("sensorRate").value,
                            formerCost: dom.byId("formerCost").value,
                            upperLimit: dom.byId("upperLimit").value,
                            lowerLimit: dom.byId("lowerLimit").value
                        };
                        var addAmmeterSuccCallBack = function () {
                            var ammeterGPRS = {
                                ammeterName: ammeter.name,
                                gprsName: gprsModule.name,
                            };

                            var addAmmeterGPRSSuccCallBack = function () {

                            };
                            var addAmmeterGPRSErrorCallBack = function () {

                            };
                            ammeterGPRSManager.addAmmeterGPRS(ammeterGPRS, addAmmeterGPRSSuccCallBack, addAmmeterGPRSErrorCallBack);
                            registry.byId("createAmmeterDialog").hide();
                        };
                        var addAmmeterErrorCallBack = function () {

                        }
                        ammeterManager.addAmmeter(ammeter, addAmmeterSuccCallBack, addAmmeterErrorCallBack);
                    };

                    var addGprsErrorCallBack = function () {

                    };
                    gprsManager.addGPRS(gprsModule, addGprsSuccCallBack, addGprsErrorCallBack);
                }
            });
        }

        var createNewGPRSRadio = registry.byId("createNewGPRSRadio");
        if (createNewGPRSRadio) {
            on(createNewGPRSRadio, "click", function () {
                var ammeterGPRSCombo = registry.byId("ammeterGPRSCombo");
                var ammeterGPRSNameTextBox = registry.byId("ammeterGPRSNameTextBox");
                var ammeterGPRSIdentifierTextBox = registry.byId("ammeterGPRSIdentifierTextBox");
                if (ammeterGPRSCombo) {
                    registry.remove("ammeterGPRSCombo");
                    domConstruct.destroy("widget_ammeterGPRSCombo");
                }
                if (ammeterGPRSNameTextBox) {
                    registry.remove("ammeterGPRSNameTextBox");
                    domConstruct.destroy("widget_ammeterGPRSNameTextBox");
                }
                if (ammeterGPRSIdentifierTextBox) {
                    registry.remove("ammeterGPRSIdentifierTextBox");
                    domConstruct.destroy("widget_ammeterGPRSIdentifierTextBox");
                }
                var ammeterGPRSNameTextBox = document.getElementById("ammeterGPRSNameTextBox") || document.createElement("input");
                ammeterGPRSNameTextBox.setAttribute("id", "ammeterGPRSNameTextBox");
                document.getElementById("ammeterGPRSLi").appendChild(ammeterGPRSNameTextBox);
                var ammeterGPRSNameTextBox = new TextBox({
                    placeHolder: "输入GPRS名称"
                }, "ammeterGPRSNameTextBox");
                var ammeterGPRSIdentifierTextBox = document.getElementById("ammeterGPRSIdentifierTextBox") || document.createElement("input");
                ammeterGPRSIdentifierTextBox.setAttribute("id", "ammeterGPRSIdentifierTextBox");
                document.getElementById("ammeterGPRSLi").appendChild(ammeterGPRSIdentifierTextBox);
                var ammeterGPRSIdentifierTextBox = new TextBox({
                    placeHolder: "输入GPRS识别码"
                }, "ammeterGPRSIdentifierTextBox");
                document.getElementById("widget_ammeterGPRSIdentifierTextBox").setAttribute("style", "margin-left:2em;");
            });
        }

        var selectExistingGPRSRadio = registry.byId("selectExistingGPRSRadio");
        if (selectExistingGPRSRadio) {
            on(selectExistingGPRSRadio, "click", function () {
                var ammeterGPRSCombo = registry.byId("ammeterGPRSCombo");
                var ammeterGPRSNameTextBox = registry.byId("ammeterGPRSNameTextBox");
                var ammeterGPRSIdentifierTextBox = registry.byId("ammeterGPRSIdentifierTextBox");
                if (ammeterGPRSCombo) {
                    registry.remove("ammeterGPRSCombo");
                    domConstruct.destroy("widget_ammeterGPRSCombo");
                }
                if (ammeterGPRSNameTextBox) {
                    registry.remove("ammeterGPRSNameTextBox");
                    domConstruct.destroy("widget_ammeterGPRSNameTextBox");
                }
                if (ammeterGPRSIdentifierTextBox) {
                    registry.remove("ammeterGPRSIdentifierTextBox");
                    domConstruct.destroy("widget_ammeterGPRSIdentifierTextBox");
                }
                var ammeterGPRSCombo = document.getElementById("ammeterGPRSCombo") || document.createElement("input");
                ammeterGPRSCombo.setAttribute("id", "ammeterGPRSCombo");
                document.getElementById("ammeterGPRSLi").appendChild(ammeterGPRSCombo);
                var ammeterGPRSCombo = new ComboBox({
                    id: "ammeterGPRSCombo",
                    name: "name",
                    // value: companyName,
                    store: gprsManager.getStore(),
                    searchAttr: "name"
                }, "ammeterGPRSCombo");
            })
        }

        var createGPRSDialogCancelBtn = registry.byId("createGPRSDialogCancelBtn");
        if (createGPRSDialogCancelBtn) {
            on(createGPRSDialogCancelBtn, "click", function () {
                registry.byId("createGPRSDialog").hide();
            });
        }
        //User Creation Dialog Event
        var createUserDialogAddBtn = registry.byId("createUserDialogAddBtn");
        if (createUserDialogAddBtn) {
            on(createUserDialogAddBtn, "click", function () {
                console.log("click");
                var user = {
                    username: dom.byId("userName").value,
                    email: dom.byId("userEmail").value,
                    password: dom.byId("userPassword").value
                };
                var addUserSuccCallBack = function () {
                    registry.byId("createUserDialog").hide();
                };
                var addUserErrorCallBack = function () {
                };
                userManager.addUser(user, addUserSuccCallBack, addUserErrorCallBack);
            });
        }
        //save computation events
        var saveComputationBtn = registry.byId("saveComputationBtn");
        if (saveComputationBtn) {
            on(saveComputationBtn, "click", function () {
                var saveComputationDialog = registry.byId("saveComputationDialog");
                var saveComputationStartDate = dom.byId("saveComputationStartDate").value;
                var saveComputationEndDate = dom.byId("saveComputationEndDate").value;
                var saveComputationAmmeter = registry.byId("saveComputationAmmeter").value;
                var getSaveComputationByDateSucc = function getSaveComputationByDateSucc(saveComputation) {
                    registry.byId("saveComputationDialogAmmeterName").set("value", saveComputation.ammeterName);
                    registry.byId("saveComputationDialogProjectName").set("value", saveComputation.projectName);
                    registry.byId("saveComputationDialogStartDate").attr("value", new Date(parseInt(saveComputation.startDate)));
                    registry.byId("saveComputationDialogStartTimeSum").set("value", saveComputation.startTimeSum);
                    registry.byId("saveComputationDialogStartValue").set("value", saveComputation.startValue);
                    registry.byId("saveComputationDialogEndDate").attr("value", new Date(parseInt(saveComputation.endDate)));
                    registry.byId("saveComputationDialogEndTimeSum").set("value", saveComputation.endTimeSum);
                    registry.byId("saveComputationDialogEndValue").set("value", saveComputation.endValue);
                    registry.byId("saveComputationDialogSensorRate").set("value", saveComputation.sensorRate);
                    registry.byId("saveComputationDialogRealCost").set("value", saveComputation.realCost);
                    registry.byId("saveComputationDialogFormerCost").set("value", saveComputation.formerCost);
                    registry.byId("saveComputationDialogEletricSave").set("value", saveComputation.eletricSave);
                    registry.byId("saveComputationDialogEletricChargeSave").set("value", saveComputation.eletricChargeSave);
                    registry.byId("saveComputationDialogStandardCoalRatio").set("value", saveComputation.standardCoalRatio);
                    registry.byId("saveComputationDialogCoalSave").set("value", saveComputation.coalSave);
                    registry.byId("saveComputationDialogEletricCharge").set("value", saveComputation.eletricCharge);
                    registry.byId("saveComputationDialogPartsRatio").set("value", saveComputation.partsRatio);
                    registry.byId("saveComputationDialogTheOtherPartyBouns").set("value", saveComputation.theOtherPartyBouns);
                    registry.byId("saveComputationDialogThePartyBonus").set("value", saveComputation.thePartyBonus);
                    console.log(registry.byId("saveComputationDialogAmmeterName").value);
                    saveComputationDialog.show();
                };
                var getSaveComputationByDateErr = function getSaveComputationByDateErr() {
                };
                saveComputationManager.getSaveComputationByDate(saveComputationStartDate, saveComputationEndDate, saveComputationAmmeter, getSaveComputationByDateSucc, getSaveComputationByDateErr);
                saveComputationDialog.show();

            });
        }

        var saveComputationDialogAddBtn = registry.byId("saveComputationDialogAddBtn");
        if (saveComputationDialogAddBtn) {
            on(saveComputationDialogAddBtn, "click", function () {
                var saveComputationRecord = {
                    ammeterName: registry.byId("saveComputationDialogAmmeterName").value,
                    projectName: registry.byId("saveComputationDialogProjectName").value,
                    startDate: registry.byId("saveComputationDialogStartDate").value,
                    startTimeSum: registry.byId("saveComputationDialogStartTimeSum").value,
                    startValue: registry.byId("saveComputationDialogStartValue").value,
                    endDate: registry.byId("saveComputationDialogEndDate").value,
                    endTimeSum: registry.byId("saveComputationDialogEndTimeSum").value,
                    endValue: registry.byId("saveComputationDialogEndValue").value,
                    sensorRate: registry.byId("saveComputationDialogSensorRate").value,
                    realCost: registry.byId("saveComputationDialogRealCost").value,
                    formerCost: registry.byId("saveComputationDialogFormerCost").value,
                    eletricSave: registry.byId("saveComputationDialogEletricSave").value,
                    eletricChargeSave: registry.byId("saveComputationDialogEletricChargeSave").value,
                    standardCoalRatio: registry.byId("saveComputationDialogStandardCoalRatio").value,
                    coalSave: registry.byId("saveComputationDialogCoalSave").value,
                    eletricCharge: registry.byId("saveComputationDialogEletricCharge").value,
                    partsRatio: registry.byId("saveComputationDialogPartsRatio").value,
                    theOtherPartyBouns: registry.byId("saveComputationDialogTheOtherPartyBouns").value,
                    thePartyBonus: registry.byId("saveComputationDialogThePartyBonus").value,
                };
                var addSaveComputationRecordSucc = function () {
                    registry.byId("saveComputationDialog").hide();
                };
                var addSaveComputationRecordErr = function () {
                    registry.byId("saveComputationDialog").hide();
                };
                saveComputationRecordManager.addSaveComputationRecord(saveComputationRecord, addSaveComputationRecordSucc, addSaveComputationRecordErr);
                // console.log(registry.byId("saveComputationDialogAmmeterName").value);
            });
        }

        //create gprs module dialog events
        var createGPRSDialogAddBtn = registry.byId("createGPRSDialogAddBtn");
        if (createGPRSDialogAddBtn) {
            on(createGPRSDialogAddBtn, "click", function () {
                var gprsModule = {
                    name: dom.byId("gprsName").value,
                    identifier: dom.byId("gprsIdentifer").value
                };

                var addGprsSuccCallBack = function () {
                    registry.byId("createGPRSDialog").hide();
                };

                var addGprsErrorCallBack = function () {

                };
                gprsManager.addGPRS(gprsModule, addGprsSuccCallBack, addGprsErrorCallBack);
            });


        }
        //save computation record chart event
        //deal with Menu active
        var activeMenuItem = function activeMenuItem(menuItem) {
            if (activedMenuItem) {
                domClass.remove(activedMenuItem, "active");
            }
            activedMenuItem = menuItem;
            domClass.add(activedMenuItem, "active");
        };

        var ammeterMenuItem = dojo.byId("ammeterMenuItem");
        if (ammeterMenuItem) {
            on(ammeterMenuItem, "click", function () {
                var ammeterMenuItemBtn = dojo.byId("ammeterMenuItemBtn");
                activeMenuItem(ammeterMenuItemBtn);
                construtAmmeterPane("ammeterPane", "电表管理");
            });
        }


        var upMenuItem = dojo.byId("upMenuItem");
        if (upMenuItem) {
            on(upMenuItem, "click", function () {
                var upMenuItemBtn = dojo.byId("upMenuItemBtn");
                activeMenuItem(upMenuItemBtn);
                constructors.ProjectPaneConstructor();
            });
        }


        var uaMenuItem = dojo.byId("uaMenuItem");
        if (uaMenuItem) {
            on(uaMenuItem, "click", function () {
                var uaMenuItemBtn = dojo.byId("uaMenuItemBtn");
                activeMenuItem(uaMenuItemBtn);
                construtAmmeterPane("ammeterPane", "电表管理");
            });
        }

        var ammeterRecordMenuItem = dojo.byId("ammeterRecordMenuItem");
        if (ammeterRecordMenuItem) {
            on(ammeterRecordMenuItem, "click", function () {
                var ammeterRecordMenuItemBtn = dojo.byId("ammeterRecordMenuItemBtn");
                activeMenuItem(ammeterRecordMenuItemBtn);
                construtAmmeterRecordPane();
            });
        }

        var projectMenuItem = dojo.byId("projectMenuItem");
        if (projectMenuItem) {
            on(projectMenuItem, "click", function () {
                var projectMenuItemBtn = dojo.byId("projectMenuItemBtn");
                activeMenuItem(projectMenuItemBtn);
                constructors.ProjectPaneConstructor();
            });

        }

        var createProjectGuideMenuItem = dojo.byId("createProjectGuideMenuItem");
        if (createProjectGuideMenuItem) {
            on(createProjectGuideMenuItem, "click", function () {
                var createProjectGuideBtn = dojo.byId("createProjectGuideMenuItem");
                activeMenuItem(createProjectGuideBtn);
                constructors.CreateProjectDialogConstructor();
            });
        }

        var paMenuItem = dojo.byId("paMenuItem")
        if (paMenuItem) {
            on(paMenuItem, "click", function () {
                var paMenuItemBtn = dojo.byId("paMenuItemBtn")
                activeMenuItem(paMenuItemBtn);
                construtPAPane();
            });
        }

        var puMenuItem = dojo.byId("puMenuItem");
        if (puMenuItem) {
            on(puMenuItem, "click", function () {
                var puMenuItemBtn = dojo.byId("puMenuItemBtn");
                activeMenuItem(puMenuItemBtn);
                constructors.ProjectUserPaneConstructor();
            });
        }

        var gprsMenuItem = dojo.byId("gprsMenuItem");
        if (gprsMenuItem) {
            on(gprsMenuItem, "click", function () {
                var gprsMenuItemBtn = dojo.byId("gprsMenuItemBtn");
                activeMenuItem(gprsMenuItemBtn);
                constructors.GPRSPaneConstructor();
            });
        }

        var ammeterGprsMenuItem = dojo.byId("ammeterGprsMenuItem");
        if (ammeterGprsMenuItem) {
            on(ammeterGprsMenuItem, "click", function () {
                var ammeterGprsMenuItemBtn = dojo.byId("ammeterGprsMenuItemBtn");
                activeMenuItem(ammeterGprsMenuItemBtn);
                constructors.AmmeterGPRSPaneConstructor();
            });
        }

        var saveComputationMenuItem = dojo.byId("saveComputationMenuItem");
        if (saveComputationMenuItem) {
            on(saveComputationMenuItem, "click", function () {
                var saveComputationMenuItemBtn = dojo.byId("saveComputationMenuItemBtn");
                activeMenuItem(saveComputationMenuItemBtn);
                constructSaveComputationPane();
            });
        }

        var companyMenuItem = dojo.byId("companyMenuItem");
        if (companyMenuItem) {
            on(companyMenuItem, "click", function () {
                var companyMenuItemBtn = dojo.byId("companyMenuItemBtn");
                activeMenuItem(companyMenuItemBtn);
                constructors.CompanyPaneConstructor();
            });
        }

        var cpMenuItem = dojo.byId("cpMenuItem");
        if (cpMenuItem) {
            on(cpMenuItem, "click", function () {
                var cpMenuItemBtn = dojo.byId("cpMenuItemBtn");
                activeMenuItem(cpMenuItemBtn);
                construtCPPane();
            });
        }

        var userMenuItem = dojo.byId("userMenuItem");
        if (userMenuItem) {
            on(userMenuItem, "click", function () {
                var userMenuItemBtn = dojo.byId("userMenuItemBtn");
                activeMenuItem(userMenuItemBtn);
                construtUserPane();
            });
        }

        var saveComputationChartMenuItem = dojo.byId("saveComputationChartMenuItem");
        if (saveComputationChartMenuItem) {
            console.log("clicked");
            on(saveComputationChartMenuItem, "click", function () {
                var saveComputationChartMenuItemBtn = dojo.byId("saveComputationChartMenuItemBtn");
                activeMenuItem(saveComputationChartMenuItemBtn);
                constructors.saveComputationChartPaneConstructor();
            });
        }

        var LastAmmeterMenuItem = dojo.byId("LastAmmeterMenuItemBtn");
        if (LastAmmeterMenuItem) {
            on(LastAmmeterMenuItem, "click", function () {
                var lastAmmeterMenuItemBtn = dojo.byId("LastAmmeterMenuItemBtn");
                activeMenuItem(lastAmmeterMenuItemBtn);
                constructLastAmmeterStatusPane();
            });
        }

        var ammeterMonitorItem = dojo.byId("ammeterMonitorItem");
        if (ammeterMonitorItem) {
            on(ammeterMonitorItem, "click", function () {
                var ammeterMonitorItemBtn = dojo.byId("ammeterMonitorItemBtn");
                activeMenuItem(ammeterMonitorItemBtn);
                constructors.ammeterMonitorPaneConstructor();
                // constructLastAmmeterStatusPane();
            });
        }

        topic.subscribe("updateAmmeter", function (text) {
            console.log(text);
            if (registry.byId("AddExistingAmmeterToProjectDialog").open) {
                var option = domConstruct.create("option", {
                    innerHTML: text,
                    className: "seven",
                    style: {fontWeight: "bold"}
                });
                dom.byId("AddedAmmeterMultiSelect").appendChild(option);
            }
        });

        topic.subscribe("updateUser", function (text) {
            console.log(text);
            if (registry.byId("addUsersToProjectDialog").open) {
                var option = domConstruct.create("option", {
                    innerHTML: text,
                    className: "seven",
                    style: {fontWeight: "bold"}
                });
                dom.byId("addedUsersMultiSelect").appendChild(option);
            }
        });

        topic.subscribe("updateProjectAmmeter", function (text) {
            console.log("start updateProjectAmmeter");
            var ammeterAddedMultiSelect = dom.byId("ammeterAddedMultiSelect");
            domConstruct.empty(ammeterAddedMultiSelect);
            console.log(request.ammetersForProject);
            for (var i = 0; i < request.ammetersForProject.length; i++) {
                console.log(request.ammetersForProject[i]);
                var option = domConstruct.create("option", {
                    innerHTML: request.ammetersForProject[i],
                    className: "seven",
                    style: {fontWeight: "bold"}
                });

                ammeterAddedMultiSelect.appendChild(option);
            }
        });

        topic.subscribe("updateProjectUser", function (text) {
            var userAddedMultiSelect = dom.byId("userAddedMultiSelect");
            domConstruct.empty(userAddedMultiSelect);
            for (var i = 0; i < request.usersForProject.length; i++) {
                var option = domConstruct.create("option", {
                    innerHTML: request.usersForProject[i],
                    className: "seven",
                    style: {fontWeight: "bold"}
                });
                userAddedMultiSelect.appendChild(option);
            }

        });
        constructLastAmmeterStatusPane();
    });
});


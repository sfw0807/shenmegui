/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-22
 * Time: 下午4:03
 * To change this template use File | Settings | File Templates.
 */

/**
 * this is grid attrs of grid utils
 * @type {*}
 */


/**
 * this is grid plugins of grid utils
 * @type {{nestedSorting: {}, indirectSelection: {headerSelector: boolean}, menus: {headerMenu: "headerMenu", rowMenu: "rowMenu", cellMenu: "cellMenu", selectedRegionMenu: "selectedRegionMenu"}, exporter: {}, printer: {}, filter: boolean, selector: {}, dnd: {copyOnly: boolean}, cellMerge: {mergedCells: Array}, search: {}, pagination: {pageSizes: Array, description: boolean, sizeSwitch: boolean, pageStepper: boolean, gotoButton: boolean, maxPageStep: number, position: "bottom"}}}
 */
var plugins = {
//    "nestedSorting": {},
////    "indirectSelection": {
////        headerSelector: true
////    },
//    "menus": {
//        headerMenu: "headerMenu",
//        rowMenu: "rowMenu",
//        cellMenu: "cellMenu",
//        selectedRegionMenu: "selectedRegionMenu"
//    },
//    "exporter": {},
//    "printer": {},
    "filter": true
//    "selector": {},
//    "dnd": {
//        copyOnly: true
//    },
//    "cellMerge": {
//        "mergedCells": [
//            {row: "3", start: 1, end: 10, major: 3}
//        ]
//    },
//    "search": {}
//    "pagination": {
//        pageSizes: ["5", "10", "20", "50", "All"],	// Array, custom the items per page button
//        description: true,	// boolean, custom weather or not the discription will be displayed
//        sizeSwitch: true,	// boolean, custom weather or not the page size switch will be displayed
//        pageStepper: true,	// boolean, custom weather or not the page step will be displayed
//        gotoButton: true,
//        maxPageStep: 10,		// Integer, custom how many page step will be displayed
//        position: "bottom"		// String, custom the position of the paginator bar
//        // there're three options: top, bottom, both
//    }
};

var gridAttrs = {
//    rowsPerPage: 5,
//    keepSelection: true,
    plugins: plugins
};

/**
 * this is grid features of grid utils
 * @type {{canSort: {label: string, value: Function}, rowSelector: {value: string}, autoWidth: {value: boolean}, singleClickEdit: {value: boolean}, selectionMode: {label: string, value: string}}}
 */
var gridFeatures = {
    "canSort": {
        label: "disable canSort",
        value: function (colIndex) {
            return false;
        }
    },
    "rowSelector": {
        value: "20px"
    },
    "autoWidth": {
        value: true
    },
    "singleClickEdit": {
        value: true
    },
    "selectionMode": {
        label: "single selectionMode",
        value: "single"
    }
};

require([
    "require",
    "dojo/aspect",
    "dojo/on",
    "dojo/_base/array",
    "dojo/_base/config",
    "dojo/dom",
    "dojo/dom-class",
    "dojo/dom-construct",
    "dojo/_base/kernel",
    "dojo/query",
    "dojo/ready",
    "dojo/_base/window",
    "dojo/_base/fx",
    "dijit/registry",
    "dijit/MenuItem",
    "dijit/Menu",
    "dojo/date/locale",
    "dojo/parser",
    "dojo/data/ItemFileReadStore",
    "dijit/tree/ForestStoreModel",
    "dojox/data/JsonRestStore",
    "dojo/store/JsonRest",
    "dijit/form/Button",
    "dijit/layout/ContentPane",
    "dojo/topic",
    "dojo/store/Observable",
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
    "dojox/charting/themes/Claro",
    "dojox/charting/plot2d/Grid",
    "dojox/charting/action2d/Tooltip",
    "dojox/charting/action2d/Highlight",
    "dijit/Tree",
    "dojo/store/Memory",
    "dijit/tree/ObjectStoreModel",
    "dojox/grid/EnhancedGrid",
    "dojox/grid/enhanced/plugins/Filter",
    "dojox/grid/enhanced/plugins/exporter/CSVWriter",
    "dojox/grid/enhanced/plugins/Printer",
    "dojox/grid/enhanced/plugins/Cookie",
    "dojox/grid/enhanced/plugins/IndirectSelection",
    "dojox/grid/enhanced/plugins/NestedSorting",
    "dojox/grid/enhanced/plugins/Selector",
    "dojox/grid/enhanced/plugins/Menu",
    "dojox/grid/enhanced/plugins/DnD",
    "dojox/grid/enhanced/plugins/Search",
    "dojox/grid/enhanced/plugins/CellMerge",
    "dojox/grid/enhanced/plugins/Pagination",
    "dojo/number", // dojo.number.format
    "dojo/dnd/Source", // dojo.dnd.Source
    "dojo/_base/json", // dojo.toJson
    "dijit/dijit-all", // dijit.*
    "dojo/domReady!"
], function (require, aspect, on, array, config, dom, domClass, domConstruct, kernel, query, ready, win, fx, registry, MenuItem, Menu, locale, parser, ItemFileReadStore, ForestStoreModel, JsonRestStore, JsonRest, Button, ContentPane, topic, Observable, Legend, VerticalRuleLabels, VerticalRule, VerticalSlider, SelectableLegend, Magnify, Markers, Chart, StoreSeries, Theme, PlotAction, Default, Columns, Lines, Pie,Claro, Grid, Tooltip, Highlight) {

    var openTreeNode = function openTreeNode() {
        var tabHead = $('div[widgetid="topTabs_tablist_templateTab"]');
        var tabBody = $('div[]')
    };
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
    };

    /**
     * this is grid formatter plugins for grids
     * @type {{dateFormatter: Function, ammeterGridOptFormatter: Function, projectGridOptFormatter: Function, gprsGridOptFormatter: Function, companyGridOptFormatter: Function, saveComputationGridOptFormatter: Function, userGridOptFormatter: Function}}
     */
    var formatters = {
        dateFormatter: function (inDatum) {
            return dojo.date.locale.format(new Date(inDatum), this.constraint);
        },

        ammeterGridOptFormatter: function (id) {
            return new Button({
                label: "保存修改",
                onClick: function () {
                    var saveAmmeterSuccessCallBack = function () {
                        alert("修改成功！");
                    };
                    var saveAmmeterErrorCallBack = function () {
                        alert("修改失败！");
                    };
                    ammeterManager.saveAmmeter(saveAmmeterSuccessCallBack, saveAmmeterErrorCallBack);
                }
            });
        },

        projectGridOptFormatter: function (projectId) {
            return new Button({
                label: "保存修改",
                onClick: function () {
                    var saveProjectSuccessCallBack = function () {
                        topic.publish("updateTree");
                        alert("保存成功！")
                    };
                    var saveProjectErrorCallBack = function () {
                        alert("保存失败！")
                    };
                    projectManager.saveProject(saveProjectSuccessCallBack, saveProjectErrorCallBack);
                }
            });
        },

        gprsGridOptFormatter: function (gprsId) {
            return new Button({
                label: "保存修改",
                onClick: function () {
                    var saveGPRSSuccessCallBack = function () {
                        alert("修改成功！");
                    };
                    var saveGPRSErrorCallBack = function () {
                        alert("修改失败！");
                    };
                    gprsManager.saveGPRS(saveGPRSSuccessCallBack, saveGPRSErrorCallBack);
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
        }),
        getStore: function () {
            return this.store;
        }
    };
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

    var governmentTree;
    var governmentStore
    var treeNode;

    var buildTree = function buildTree() {
        var menuTree = $.get('/menu/list', {}, function (response) {
            var menuData = [
                {
                    "$Ref": null,
                    "children": response,
                    "id": "menu",
                    "name": "能源管理平台"
                }
            ];

            governmentStore = new dojo.store.Memory({
                data: menuData,
                getChildren: function (object) {
                    return object.children || [];
                }
            });
            governmentStore = new Observable(governmentStore);
            var governmentModel = new dijit.tree.ObjectStoreModel({
                store: governmentStore,
                mayHaveChildren: function (item) {
                    item.children = item.children || [];
                    return item.children.length > 0;
                }
            });

            governmentTree = new dijit.Tree({
                id: "myTree",
                model: governmentModel,
                onOpenClick: true,
                onLoad: function () {
                },
                onClick: function (item) {
                    var topTabs = registry.byId("topTabs");
                    if (item["type"] == "company") {
                        var projectPane = dom.byId(item["name"]+"的项目");
                        if(projectPane){
                            alert(item["name"]+"的项目的TAB已经打开");
                        } else {
                            var projectPane = constructNewPane(item["name"] + "的项目", topTabs, item["name"]+"的项目");
                            var grid = createGrid(item["name"], projectManager.getStore(), layouts.projectGridLayout,
                                item["name"]+"的项目" );
                            var companyId =  item["id"].split("_")[1] || "" ;
                            grid.setQueryAfterLoading({
                                "companyId": companyId
                            });
                        }
                    }
                    if (item["type"] == "project") {
                        var gprsPane = constructNewPane(item["name"] + "的GPRS模块", topTabs, item["name"] );
                        var grid = createGrid(item["name"], gprsManager.getStore(), layouts.gprsGridLayout, item["name"]);
                        grid.setQueryAfterLoading({
                            "projectName" : item["id"]
                        })
                    }
                    if (item["type"] == "gprs") {
                        var ammeterPane = constructNewPane(item["name"] + "的电表", topTabs, item["name"] );
                        var grid = createGrid(item["name"], ammeterManager.getStore(), layouts.ammeterGridLayout, item["name"] );
                        console.log(item["name"]);
                        grid.setQueryAfterLoading({
                            "gprsName" : item["id"]
                        })
                    }
                    if (item["type"] == "ammeter") {
                        var typeWithId = item["id"];
                        var typeWithIdArr = typeWithId.split("_");
                        var id = typeWithIdArr[1];
                        var ammeterRecordPane = constructNewPane(item["name"] + "的电表记录", topTabs, item["name"] );
                        var grid =createGrid(item["name"], ammeterRecordManager.getStore(), layouts.ammeterRecordLayout, item["name"] );
//                        grid.setQueryAfterLoading({
//                            "ammeterId" : id
//                        })
                    }

                },
                onMouseDown: function (ev, node) {
                    if (ev.button == 2) { // right-click
                        var here = dijit.getEnclosingWidget(ev.target);
                        console.log(here);
                        this.set('selectedNode', here);
                    }
                },
                persist: false
            }, "divTree");
            governmentTree.startup();

            var addMenuItem = new MenuItem({
                label: "增加",
                onClick: function () {
                    var selected = governmentTree.selectedItem;
                    console.log(selected["name"]);
                    if(selected["id"] == "menu"){
                        var  createCompanyDialog = registry.byId("createCompanyDialog");
                        createCompanyDialog.show();
                    }
                    if (selected["type"] == "company") {
                        var addProjectDialog = registry.byId("createProjectForCompanyDialog");
                        addProjectDialog.show();
                    }
                    if(selected["type"] == "project") {
                        var createGPRSDialog = registry.byId("createGPRSDialog");
                        createGPRSDialog.show();
                    }
                    if(selected["type"]=="gprs"){
                        var createAmmeterDialog = registry.byId("createAmmeterDialog");
                        createAmmeterDialog.show();
                    }
                    treeNode = selected;

                }
            });
            var deleteMenuItem = new MenuItem({
                label: "删除",
                onClick: function () {
                    var selected = governmentTree.selectedItem;
                    if (selected["type"] == "company") {
                        var typeWithId = selected["id"];
                        var typeWithIdArr = typeWithId.split("_");
                        var id = typeWithIdArr[1];
                        $.ajax({
                            type:"DELETE",
                            url:'/company/list/'+id,
                            success:function(msg){
                                reBuildTree();
                               alert("删除成功");
                            }
                        });
                    }
                    if(selected["type"] == "project") {
                        var typeWithId = selected["id"];
                        var typeWithIdArr = typeWithId.split("_");
                        var id = typeWithIdArr[1];
                        $.ajax({
                            type:"DELETE",
                            url:'/project/list/'+id,
                            success:function(msg){
                                reBuildTree();
                                alert("删除成功");
                            }
                        });
                    }
                    if(selected["type"]=="gprs"){
                        var typeWithId = selected["id"];
                        var typeWithIdArr = typeWithId.split("_");
                        var id = typeWithIdArr[1];
                        $.ajax({
                            type:"DELETE",
                            url:'/gprs/list/'+id,
                            success:function(msg){
                                reBuildTree();
                                alert("删除成功");
                            }
                        });
                    }
                    if(selected["type"]=="ammeter"){
                        var typeWithId = selected["id"];
                        var typeWithIdArr = typeWithId.split("_");
                        var id = typeWithIdArr[1];
                        $.ajax({
                            type:"DELETE",
                            url:'/ammeter/list/'+id,
                            success:function(msg){
                                reBuildTree();
                                alert("删除成功");
                            }
                        });
                    }
                    treeNode = selected;

                }
            });
            var myMenu = new dijit.Menu({
                onFocus: function () {
                    var selected = governmentTree.selectedItem;
                }
            });
            myMenu.addChild(addMenuItem);
            myMenu.addChild(deleteMenuItem);
            myMenu.bindDomNode(governmentTree.domNode);
            myMenu.startup();

        });
    };

    buildTree();

    var reBuildTree = function reBuildTree() {
        $("#myTree").remove();
        $("#dijit_layout_ContentPane_0").html('<div id="divTree"></div>');
        registry.remove("myTree");
        buildTree();
    };


    var constructNewPane = function constructNewPane(title, container, contentId) {
        var newPane = new ContentPane({
            title: title,
            closable: true,
            content: "<div id = " + contentId + '  class="gridContainer"></div>'
        });
        container.addChild(newPane, 0);
        container.selectChild(newPane);
        return title;
    };

    /**
     * this is layouts of all the grids
     * @type {{userLayout: Array, lastAmmeterStatusGridLayout: Array, saveComputationGridLayout: Array, upGridLayout: Array, agGridLayout: Array, ammeterGridLayout: Array, projectGridLayout: Array, companyGridLayout: Array, gprsGridLayout: Array}}
     */

    var layouts = {
        userLayout: [
            {
                name: "用户编号",
                field: "id",
                height: "24px",
                width: "10em",
                canSort: true
            },
            {
                name: "用户名称",
                field: "username",
                width: "10em",
                editable: true
            },
            {
                name: "用户邮箱",
                field: "email",
                width: "10em",
                editable: true
            },
            {
                name: "用户角色",
                field: "roles",
                width: "10em",
                editable: true
            },
            {
                name: "用户公司",
                field: "company",
                width: "10em",
                editable: false
            },
            {
                name: "用户项目",
                field: "project",
                width: "10em",
                editable: false
            },
            {
                name: "操作",
                field: "id",
                width: "10em",
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
                width: "10em",
                canSort: true
            },
            {
                name: "项目编号",
                field: "projectId",
                width: "10em",
                canSort: true
            },
            {
                name: "项目名称",
                field: "projectName",
                width: "10em",
                editable: true
            },
            {
                name: "用户编号",
                field: "userId",
                width: "10em",
                canSort: true
            },
            {
                name: "用户名称",
                field: "userName",
                width: "10em",
                canSort: true
            }
        ],

        agGridLayout: [
            {
                name: "编号",
                field: "id",
                width: "10em",
                canSort: true
            },
            {
                name: "电表编号",
                field: "ammeterId",
                width: "10em",
                canSort: true
            },
            {
                name: "电表标识",
                field: "ammeterName",
                width: "10em",
                canSort: true
            },
            {
                name: "GPRS编号",
                field: "gprsId",
                width: "10em",
                canSort: true
            },
            {
                name: "GPRS名称",
                field: "gprsName",
                width: "10em",
                canSort: true
            }
        ],

        ammeterGridLayout: [
//            {
//                name: "电表编号",
//                field: "id",
//                width: "10em",
//                canSort: true
//            },
            {
                name: "操作",
                field: "id",
                type: dojox.grid.cells._Widget,
                editable: false,
                formatter: formatters.ammeterGridOptFormatter
            },
            {
                name: "电表名称",
                field: "pumpName",
                width: "10em",
                editable: true
            },
            {
                name: "电表标识",
                field: "name",
                width: "15em",
                editable: true
            },
            {
                name: "互感器倍率",
                field: "sensorRate",
                width: "10em",
                canSort: true,
                editable: true
            },
            {
                name: "技改前能耗",
                field: "formerCost",
                width: "10em",
                canSort: true,
                editable: true
            },
            {
                name: "报警上限",
                field: "upperLimit",
                width: "10em",
                canSort: true,
                editable: true
            },
            {
                name: "报警下限",
                field: "lowerLimit",
                width: "10em",
                canSort: true,
                editable: true
            }
        ],

        projectGridLayout: [
            {
                name: "保存修改",
                field: "id",
                width: "10em",
                type: dojox.grid.cells._Widget,
                editable: false,
                formatter: formatters.projectGridOptFormatter
            },
            {
                name: "项目名称",
                field: "projectName",
                width: "10em",
                editable: true
            },
            {
                name: "当前电费(元/度)",
                field: "electricityCharge",
                width: "10em",
                editable: true,
                canSort: true
            },
            {
                name: "节能分享比例(%)",
                field: "partsRatio",
                width: "10em",
                editable: true,
                canSort: true
            },
            {
                name: "项目开始日期",
                field: "startDate",
                width: "10em",
                formatter: formatters.dateFormatter,
                canSort: true
            },
            {
                name: "项目结束日期",
                field: "endDate",
                width: "10em",
                formatter: formatters.dateFormatter,
                canSort: true
            }
        ],

        companyGridLayout: [
            {
                name: "公司编号",
                field: "id",
                width: "10em" + "px",
                canSort: true
            },
            {
                name: "公司名称",
                field: "companyName",
                width: "10em" + "px",
                editable: true
            },
            {
                name: "操作",
                field: "id",
                width: "10em" / 2 + "px",
                type: dojox.grid.cells._Widget,
                editable: false,
                formatter: formatters.companyGridOptFormatter
            }
        ],

        gprsGridLayout: [
//            {
//                name: "GPRS编号",
//                field: "id",
//                width: "10em",
//                canSort: true
//            },
            {
                name: "操作",
                field: "id",
                width: "10em",
                type: dojox.grid.cells._Widget,
                editable: false,
                formatter: formatters.gprsGridOptFormatter
            },
            {
                name: "GPRS识别码",
                field: "identifier",
                width: "20em",
                editable: true,
                canSort: true
            },
            {
                name: "GPRS名称",
                field: "name",
                width: "20em",
                editable: true,
                canSort: true
            }
        ],
        ammeterRecordLayout: [
//            {
//                name: "编号",
//                field: "id",
//                width: "5em",
//                canSort: true
//            },
            {
                name: "电表标识",
                field: "ammeterName",
                width: "20em",
                canSort: true
            },
            {
                name: "电表电量读数（kWh）",
                field: "ammeterValue",
                width: "20em",
                canSort: true
            },
            {
                name: "累时器读数（h）",
                field: "timeSum",
                width: "20em",
                canSort: true
            },
            {
                name: "抄表时间",
                field: "recordDate",
                width: "20em",
                formatter: formatters.dateFormatter,
                canSort: true
            },
            {
                name: "瞬时功率（h）",
                field: "ammeterValue",
                width: "20em",
                canSort: true
            }
        ]
    };

    var constructors = {
        ammeterPaneConstructor: function () {
        },
        projectPaneConstructor: function () {
        },
        companyPaneConstructor: function () {
        },
        ammeterRecordConstructor: function () {
        },
        userPaneConstructor: function () {
        },
        lastAmmeterStatusPaneConstructor: function () {
        },
        saveComputationPaneConstructor: function () {
        },
        upPaneConstructor: function () {
        },
        agPaneConstructor: function () {
        },
        gprsPaneConstructor: function () {
        }

    };

    var createGrid = function createGrid(id, store, layout, container) {
        try {
            var g = dijit.byId(id);
            g && g.destroyRecursive();
            g = new dojox.grid.EnhancedGrid(dojo.mixin({
                "id": id,
                "store": store,
                "structure": layout
            }, gridAttrs || {}));
            g.placeAt(dojo.byId(container));
            g.startup();
            return g;
        } catch (e) {
        }
    }

    showDialog = function () {
        var dlg = registry.byId('dialog1');
        dlg.show();
        // avoid (trying to) restore focus to a closed menu, go to MenuBar instead
        dlg._savedFocus = dom.byId("header");
    };

    showDialogAb = function () {
        var dlg = registry.byId('dialogAB');
        dlg.show();
        // avoid (trying to) restore focus to a closed menu, go to MenuBar instead
        dlg._savedFocus = dom.byId("header");
    };

// current setting (if there is one) to override theme default padding on TextBox based widgets
    var currentInputPadding = "";

    setTextBoxPadding = function () {
        // summary:
        //		Handler for when a MenuItem is clicked to set non-default padding for
        //		TextBox widgets

        // Effectively ignore clicks on the	 currently checked MenuItem
        if (!this.get("checked")) {
            this.set("checked", true);
        }

        // val will be "theme default", "0px", "1px", ..., "5px"
        var val = this.get("label");

        // Set class on body to get requested padding, and remove any previously set class
        if (currentInputPadding) {
            domClass.remove(win.body(), currentInputPadding);
            currentInputPadding = "";
        }
        if (val != "theme default") {
            currentInputPadding = "inputPadding" + val.replace("px", "");
            domClass.add(win.body(), currentInputPadding);
        }

        // Clear previously checked MenuItem (radio-button effect).
        array.forEach(this.getParent().getChildren(), function (mi) {
            if (mi != this) {
                mi.set("checked", false);
            }
        }, this);
    };

    ready(function () {
        // Delay parsing until the dynamically injected theme <link>'s have had time to finish loading
        setTimeout(function () {
            parser.parse();
            dom.byId('loaderInner').innerHTML += " done.";
            setTimeout(function hideLoader() {
                fx.fadeOut({
                    node: 'loader',
                    duration: 500,
                    onEnd: function (n) {
                        n.style.display = "none";
                    }
                }).play();
            }, 250);
        }, 320);
        //project Dialog event handler
        var newProjectDialogAddBtn = registry.byId("newProjectDialogAddBtn");
        if (newProjectDialogAddBtn) {
            on(newProjectDialogAddBtn, "click", function () {
                var projectName = dom.byId("forCompanyProjectName").value;
                var addProject = function () {
                    var addProjectSuccessCallBack = function () {
                        addCompanyProject();
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
                    reBuildTree();
                };
                var addCompanyProjectErrorCallBack = function () {

                };
                var addCompanyProject = function () {
                    var companyProject = {
                        "companyName": treeNode["name"],
                        "projectName": projectName
                    };
                    companyProjectManager.addCompanyProject(companyProject, addCompanyProjectSuccCallBack, addCompanyProjectErrorCallBack);
                }
                addProject();
                registry.byId("createProjectForCompanyDialog").hide();
                var childItem = {
                    name: projectName,
                    id: projectName
                };
            });
        }
        //gprs add event handler
        var createGPRSDialogAddBtn = registry.byId("createGPRSDialogAddBtn");
        if (createGPRSDialogAddBtn) {
            on(createGPRSDialogAddBtn, "click", function () {
                var gprsModule = {
                    name: dom.byId("gprsName").value,
                    identifier: dom.byId("gprsIdentifer").value,
                    projectName : treeNode["id"]
                };

                var addGprsSuccCallBack = function () {
                    registry.byId("createGPRSDialog").hide();
                    reBuildTree();
                };
                var addGprsErrorCallBack = function () {
                };
                gprsManager.addGPRS(gprsModule, addGprsSuccCallBack, addGprsErrorCallBack);
            });
        }

        //ammeter add event handler
        var createAmmeterDialogAddBtn = registry.byId("createAmmeterDialogAddBtn");
        if (createAmmeterDialogAddBtn) {
            on(createAmmeterDialogAddBtn, "click", function () {
                console.log(treeNode["name"]);
                var ammeter = {
                    name: dom.byId("ammeterName").value,
                    pumpName: dom.byId("pumpName").value,
                    sensorRate: dom.byId("sensorRate").value,
                    formerCost: dom.byId("formerCost").value,
                    upperLimit: dom.byId("upperLimit").value,
                    lowerLimit: dom.byId("lowerLimit").value,
                    gprsName:treeNode["id"]
                };
                var addAmmeterSuccCallBack = function () {
                    reBuildTree();
                    registry.byId("createAmmeterDialog").hide();
                };
                var addAmmeterErrorCallBack = function () {

                }
                ammeterManager.addAmmeter(ammeter, addAmmeterSuccCallBack, addAmmeterErrorCallBack);
            });
        }
        //company add event handler
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
                    reBuildTree();
                }
                var addCompanyErrorCallBack = function (error) {
                }
                companyManager.addCompany(company, addCompanySuccessCallBack, addCompanyErrorCallBack);
            });

        }
        //chart
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
        constructSaveComputationChart();
    });
})
;


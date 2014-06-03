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
var construtCompanyPane;
var companyPaneConstruted = false;

var projectPane;
var construtProjectPane;
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

var constructNewPane;

var activedMenuItem;


require(["dojo/on",
         "dojox/data/JsonRestStore", 
         "dojo/store/Memory", 
         "dojo/store/Cache", 
         "dojox/grid/EnhancedGrid", 
         "dojo/data/ObjectStore", 
         "dojo/query", 
         "dojo/ready", 
         "dijit/form/TextBox", 
         "dijit/form/Button", 
         "dijit/Menu", 
         "dijit/MenuItem", 
         "dijit/form/ComboButton", 
         "dijit/form/ComboBox", 
         "dojo/dom-style",
         "dojo/dom-class",
         "dojo/dom-construct",
         "dojo/dom", 
         "dojo/_base/xhr", 
         "dijit/layout/ContentPane", 
         "dijit/Dialog", 
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
         "dojo/domReady!"], function(on,JsonRestStore, Memory, Cache, EnhancedGrid, ObjectStore, query, ready, TextBox, Button, Menu, MenuItem, ComboButton, ComboBox, domStyle, domClass, domConstruct, dom, xhr, ContentPane, Dialog, i18n) {

    ready(function() {
    	
        //hidden the user ContentPane
        var tabContainer = dijit.byId("tab_container");

        userPane = dijit.byId("userPane");
        // ammeterPane = dijit.byId("ammeterPane");
        ammeterRecordPane = dijit.byId("ammeterRecordPane");
        companyPane = dijit.byId("companyPane");
        projectPane = dijit.byId("projectPane");
        cpPane = dijit.byId("cpPane");
        paPane = dijit.byId("paPane");
        upPane = dijit.byId("upPane");

        tabContainer.removeChild(userPane);
        // tabContainer.removeChild(ammeterPane);
        tabContainer.removeChild(ammeterRecordPane);
        tabContainer.removeChild(companyPane);
        tabContainer.removeChild(projectPane);
        tabContainer.removeChild(cpPane);
        tabContainer.removeChild(paPane);
        tabContainer.removeChild(upPane);


        var main_container_width = dojo.style("main_container", "width");
        var ammeter_cell_width = main_container_width / 6;
        var ammeter_record_width = main_container_width * 0.24;
        var user_cell_width = main_container_width * 0.17;
        var company_cell_width = main_container_width / 3;
        var project_cell_width = (main_container_width - 50) / 5;
        var cp_cell_width = main_container_width * 0.20;
        var pa_cell_width = main_container_width * 0.20;
        var up_cell_width = main_container_width * 0.20;

        var upGridLayout = [{
            name: "编号",
            field: "id",
            width: up_cell_width * 0.2 + "px",
            canSort: true
        }, {
            name: "项目编号",
            field: "projectId",
            width: up_cell_width + "px",
            canSort: true
        }, {
            name: "项目名称",
            field: "projectName",
            width: up_cell_width + "px",
            editable: true
        }, {
            name: "用户编号",
            field: "userId",
            width: up_cell_width + "px",
            canSort: true
        }, {
            name: "用户名称",
            field: "userName",
            width: up_cell_width + "px",
            canSort: true
        }];

        var ammeterGridLayout = [{
            name: "电表编号",
            field: "id",
            width: ammeter_cell_width * 0.5 + "px",
            canSort: true
        }, {
            name: "电表名称",
            field: "name",
            width: ammeter_cell_width + "px",
            editable: true
        }, {
            name: "泵名称",
            field: "pumpName",
            width: ammeter_cell_width + "px",
            editable: true
        }, {
            name: "项目名称",
            field: "projectName",
            width: ammeter_cell_width + "px",
            editable: true
        }, {
            name: " 公司名称",
            field: "companyName",
            width: ammeter_cell_width + "px",
            editable: true
        },{
            name: "操作",
            field: "id",
            type: dojox.grid.cells._Widget,
            editable: false,
            formatter: function(id){
                return new Button({
                    label:"查看记录",
                    onClick: function() {
                        construtAmmeterRecordPane(id);
                        
                    }
                });
            }
        }];

        //user project pane
        construtUPPane = function construtUPPane() {
            var construtUPGrid = function construtUPGrid() {
                constructNewGridForPane("up_grid", upGridLayout, "/up/list/")
            };

            var constructUPBtns = function constructUPBtns(){
                //construt add button
                var add_up_btn = new Button({

                    label: "新建",
                    onClick: function() {
                        var form_content = {
                            userName: dijit.byId("userForUPCombo").get("value"),
                            projectName: dijit.byId("projectForUPCombo").get("value")
                        };
                        xhr.post({
                            form: "add_up_form",
                            // read the url: from the action="" of the <form>
                            timeout: 3000,
                            // give up after 3 seconds
                            content: form_content,
                            handleAs: "json",
                            load: function(new_up) {
                                upDataStore.newItem(new_up);
                                updataStore.save();
                            }
                        });
                    }
                }, "add_up_btn");
                add_up_btn.startup();

                //construt save button
                var up_save_button = new Button({
                    label: "保存",
                    onClick: function() {
                        upDataStore.save();

                    }
                }, "up_save_button");
                up_save_button.startup();

                //construt delete button
                var up_delete_button = new Button({
                    label: "删除",
                    onClick: function() {
                        var up_selected = upGrid.selection.getSelected();
                        if (up_selected.length) {
                            for (key in up_selected) {
                                upDataStore.deleteItem(up_selected[key]);
                                upDataStore.save();
                            }
                        }
                    }
                }, "up_delete_button");
                up_delete_button.startup();
            };
            
            var constructUserProjectForUPCombo = function constructUserProjectForUPCombo() {

                    projectForUPStore = new JsonRestStore({
                        target: "/project/list/"
                    });

                    userForUPStore = new JsonRestStore({
                        target: "/user/list/"
                    });
                    
                    var projectForUPCombo = new ComboBox({
                        id: "projectForUPCombo",
                        name: "project",
                        value: "",
                        store: projectForUPStore,
                        searchAttr: "projectName"
                    }, "projectForUPCombo");

                    var userForUPCombo = new ComboBox({
                        id: "userForUPCombo",
                        name: "user",
                        value: "",
                        store: userForUPStore,
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
                        structure: [{
                            name: "编号",
                            field: "id",
                            width: pa_cell_width * 0.2 + "px",
                            canSort: true
                        }, {
                            name: "项目编号",
                            field: "projectId",
                            width: pa_cell_width + "px",
                            canSort: true
                        }, {
                            name: "项目名称",
                            field: "projectName",
                            width: pa_cell_width + "px",
                            editable: true
                        }, {
                            name: "电表编号",
                            field: "ammeterId",
                            width: pa_cell_width + "px",
                            canSort: true
                        }, {
                            name: "电表名称",
                            field: "ammeterName",
                            width: pa_cell_width + "px",
                            canSort: true
                        }],
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
                        onClick: function() {
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
                                load: function(new_pa) {
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
                        onClick: function() {
                            paDataStore.save();

                        }
                    }, "pa_save_button");
                    pa_save_button.startup();

                    //construt delete button
                    var pa_delete_button = new Button({
                        label: "删除",
                        onClick: function() {
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
                        structure: [{
                            name: "编号",
                            field: "id",
                            width: cp_cell_width * 0.2 + "px",
                            canSort: true
                        }, {
                            name: "项目编号",
                            field: "projectId",
                            width: cp_cell_width + "px",
                            canSort: true
                        }, {
                            name: "项目名称",
                            field: "projectName",
                            width: cp_cell_width + "px",
                            editable: true
                        }, {
                            name: "公司编号",
                            field: "companyId",
                            width: cp_cell_width + "px",
                            canSort: true
                        }, {
                            name: "公司名称",
                            field: "companyName",
                            width: cp_cell_width + "px",
                            canSort: true
                        }],
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
                        onClick: function() {
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
                                load: function(new_cp) {
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
                        onClick: function() {
                            cpDataStore.save();

                        }
                    }, "cp_save_button");
                    cp_save_button.startup();

                    //construt delete button
                    var cp_delete_button = new Button({
                        label: "删除",
                        onClick: function() {
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

        //project pane
        construtProjectPane = function construtProjectPane(id) {

            var construtProjectGrid = function construtProjectGrid() {
            		
            		var targetUrl = "/project/list/";
            		
            		if(id){
            			targetUrl = targetUrl + id;
            		}
            		
                    projectStore = new JsonRestStore({
                        target: targetUrl
                    });
                    projectGrid = new EnhancedGrid({
                        store: projectDataStore = projectStore,
                        autoWidth: true,
                        structure: [{
                            name: "项目编号",
                            field: "id",
                            width: project_cell_width + "px",
                            canSort: true
                        }, {
                            name: "项目名称",
                            field: "projectName",
                            width: project_cell_width + "px",
                            editable: true
                        }, {
                            name: "项目开始日期",
                            field: "startDate",
                            width: project_cell_width + "px",
                            canSort: true
                        }, {
                            name: "项目结束日期",
                            field: "endDate",
                            width: project_cell_width + "px",
                            canSort: true
                        },{
                        	name: "操作",
							field: "id",
							 width: project_cell_width / 2 + "px",
							type: dojox.grid.cells._Widget,
							editable: false,
							formatter: function(id){
								return new Button({
									label:"查看电表",
									onClick: function() {
										var paneName = "项目" + id + "的电表";
										var paneNode = dijit.byId(paneName);
										if(paneNode){
											tabContainer.addChild(paneNode);
											tabContainer.selectChild(dijit.byId(paneNode));
											return ;
										}
                                        paneNode = constructNewPane(paneName, paneName,"width: 100%;",tabContainer);
                                        //create button node
                                        var addButtonNodeId = paneNode + "AddButton";
                                        var newAddButtonNode = document.createElement("div");
                                        newAddButtonNode.setAttribute("id", addButtonNodeId);
                                        document.getElementById(paneNode).appendChild(newAddButtonNode);
                                        //construt add button
                                        var add_ammeter_btn = new Button({

                                            label: "新建",
                                            onClick: function() {
                                            	
                                            	dijit.byId("createProjectForCompanyDialog").show();
                                            	
                                            	// var newProjectDialogNameText = new TextBox({
                                            	// 	placeHolder: "输入项目名称"
                                            	// },"forCompanyProjectName");
                                            	// newProjectDialogNameText.startup();
                                            	
                                            	// var newProjectDialogStartText = new TextBox({
                                            	// 	placeHolder: "输入项目开始时间"
                                            	// },"forCompanyProjectStart");
                                            	// newProjectDialogStartText.startup();
                                            	
                                            	// var newProjectDialogEndText = new TextBox({
                                            	// 	placeHolder: "输入项目结束时间"
                                            	// },"forCompanyProjectEnd");
                                            	// newProjectDialogEndText.startup();
                                            	
                                            	var newProjectDialogAddBtn = new Button({
                                            		label: "添加",
                                            		onClick: function(){
                                            			var form_content = {
                                                              projectName: dom.byId("forCompanyProjectName").value
                                                          };
                                                          xhr.post({
                                                              form: "newProjectDialogForm",
                                                              // read the url: from the action="" of the <form>
                                                              timeout: 3000,
                                                              // give up after 3 seconds
                                                              content: form_content,
                                                              handleAs: "json",
                                                              load: function(new_project) {
                                                                  projectDataStore.newItem(new_project);
                                                                  projectdataStore.save();
                                                                  
                                                              }
                                                          });
                                            		}
                                            	},"newProjectDialogAddBtn");
                                            	newProjectDialogAddBtn.startup();
                                            	
                                            	var newProjectDialogCancelBtn = new Button({
                                            		label: "取消",
                                            		onClick: function(){
                                            			dijit.byId("createProjectForCompanyDialog").hide();
                                            		}
                                            	}, "newProjectDialogCancelBtn");
                                            	newProjectDialogCancelBtn.startup();
//                                                var form_content = {
//                                                    projectName: dom.byId("addProjectName").value,
//                                                };
//                                                xhr.post({
//                                                    form: "add_project_form",
//                                                    // read the url: from the action="" of the <form>
//                                                    timeout: 3000,
//                                                    // give up after 3 seconds
//                                                    content: form_content,
//                                                    handleAs: "json",
//                                                    load: function(new_project) {
//                                                        projectDataStore.newItem(new_project);
//                                                        projectdataStore.save();
//                                                    }
//                                                });
                                            }
                                        }, addButtonNodeId);
                                        add_project_btn.startup();
                                        
                                        //create addFromExistButton
                                        var addFromExistButtonNodeId = paneNode + "AddFromExsitButton";
                                        var addFromExitAddButtonNode = document.createElement("div");
                                        addFromExitAddButtonNode.setAttribute("id", addFromExistButtonNodeId);
                                        document.getElementById(paneNode).appendChild(addFromExitAddButtonNode);
                                        var addProjectFromExistBtn = new Button({
                                        	label: "从已存在的项目中添加",
                                        	onClick: function(){
                                        		
                                        	}
                                        },addFromExistButtonNodeId);
                                        
                                        //create close tab button
                                        constructCloseTabBtn(paneNode);

                                        //create grid node
                                        var gridNodeId = paneNode + "Grid";
                                        var newGridNode = document.createElement("div");
                                        document.getElementById(paneNode).appendChild(newGridNode);
                                        newGridNode.setAttribute("id", gridNodeId);
                                        newGridNode.setAttribute("style", "height:400px;");
                                        var newGridLayout = [{
                                            name: "电表编号",
                                            field: "id",
                                            width: ammeter_cell_width * 0.5 + "px",
                                            canSort: true
                                        }, {
                                            name: "电表名称",
                                            field: "name",
                                            width: ammeter_cell_width + "px",
                                            editable: true
                                        }, {
                                            name: "泵名称",
                                            field: "pumpName",
                                            width: ammeter_cell_width + "px",
                                            editable: true
                                        }, {
                                            name: "项目名称",
                                            field: "projectName",
                                            width: ammeter_cell_width + "px",
                                            editable: true
                                        }, {
                                            name: " 公司名称",
                                            field: "companyName",
                                            width: ammeter_cell_width + "px",
                                            editable: true
                                        },{
                                        	name: "操作",
                							field: "id",
                							type: dojox.grid.cells._Widget,
                							editable: false,
                							formatter: function(id){
                								return new Button({
                									label:"查看记录",
                									onClick: function() {
                										construtAmmeterRecordPane(id);
                										
                									}
                								});
                							}
                                    	}];
                                        constructNewGridForPane(gridNodeId, newGridLayout, "/ammeter/list/");
										
									}
								});
							}
                    	}],
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
                    }, "project_grid");
                    projectGrid.startup();

                    //construt add button
                    var add_project_btn = new Button({

                        label: "新建",
                        onClick: function() {
                            var form_content = {
                                projectName: dom.byId("addProjectName").value,
                            };
                            xhr.post({
                                form: "add_project_form",
                                // read the url: from the action="" of the <form>
                                timeout: 3000,
                                // give up after 3 seconds
                                content: form_content,
                                handleAs: "json",
                                load: function(new_project) {
                                    projectDataStore.newItem(new_project);
                                    projectdataStore.save();
                                }
                            });
                        }
                    }, "add_project_btn");
                    add_project_btn.startup();

                    //construt save button
                    var save_button = new Button({
                        label: "保存",
                        onClick: function() {
                            projectDataStore.save();

                        }
                    }, "project_save_button");
                    save_button.startup();

                    //construt delete button
                    var delete_button = new Button({
                        label: "删除",
                        onClick: function() {
                            var project_selected = projectGrid.selection.getSelected();
                            if (project_selected.length) {
                                for (key in project_selected) {
                                    projectDataStore.deleteItem(project_selected[key]);
                                    projectDataStore.save();
                                }
                            }
                        }
                    }, "project_delete_button");
                    delete_button.startup();
                }

            if (!projectPaneConstruted) {
                if (typeof projectPane != "undefined") {
                    tabContainer.addChild(projectPane, 0);
                    tabContainer.selectChild(projectPane);
                    construtProjectGrid();
                    projectPaneConstruted = true;
                }
            } else {
                tabContainer.selectChild(projectPane);
            }
        };

        //company pane
        construtCompanyPane = function construtCompanyPane() {
            var construtCompanyGrid = function construtCompanyGrid() {
                    companyStore = new JsonRestStore({
                        target: "/company/list/"
                    });
                    companyGrid = new EnhancedGrid({
                        store: companyDataStore = companyStore,
                        autoWidth: true,
                        structure: [{
                            name: "公司编号",
                            field: "id",
                            width: company_cell_width + "px",
                            canSort: true
                        }, {
                            name: "公司名称",
                            field: "companyName",
                            width: company_cell_width + "px",
                            editable: true
                        },{
                        	name: "操作",
							field: "id",
							 width: company_cell_width / 2 + "px",
							type: dojox.grid.cells._Widget,
							editable: false,
							formatter: function(id){
								return new Button({
									label:"查看项目",
									onClick: function() {
										var paneNode = dijit.byId("公司" + id + "的项目");
										if(paneNode){
											paneGrid = dijit.byId("公司" + id + "的项目Grid")
											paneGrid.setQuery({"id" : "*"}); 
											tabContainer.addChild(paneNode);
											tabContainer.selectChild(dijit.byId(paneNode));
											return ;
										}
                                        paneNode = constructNewPane("公司" + id + "的项目", "公司" + id + "的项目","width: 100%;",tabContainer);
                                        //create button node
                                        var addButtonNodeId = paneNode + "AddButton";
                                        var newAddButtonNode = document.createElement("div");
                                        newAddButtonNode.setAttribute("id", addButtonNodeId);
                                        document.getElementById(paneNode).appendChild(newAddButtonNode);
                                        //construt add button
                                        var add_project_btn = new Button({

                                            label: "新建",
                                            onClick: function() {
                                            	
                                                console.log("hello");
                                            	dijit.byId("createProjectForCompanyDialog").show();
                                            	
                                            	// var newProjectDialogNameText = new TextBox({
                                            	// 	placeHolder: "输入项目名称"
                                            	// },"forCompanyProjectNameforCompanyProjectName");
                                            	// newProjectDialogNameText.startup();
                                            	
                                            	// var newProjectDialogStartText = new TextBox({
                                            	// 	placeHolder: "输入项目开始时间"
                                            	// },"forCompanyProjectStart");
                                            	// newProjectDialogStartText.startup();
                                            	
                                            	// var newProjectDialogEndText = new TextBox({
                                            	// 	placeHolder: "输入项目结束时间"
                                            	// },"forCompanyProjectEnd");
                                            	// newProjectDialogEndText.startup();
                                            	
                                            	var newProjectDialogAddBtn = new Button({
                                            		label: "添加",
                                            		onClick: function(){
                                            			var form_content = {
                                                              projectName: dom.byId("forCompanyProjectName").value
                                                          };
                                                          xhr.post({
                                                              form: "newProjectDialogForm",
                                                              // read the url: from the action="" of the <form>
                                                              timeout: 3000,
                                                              // give up after 3 seconds
                                                              content: form_content,
                                                              handleAs: "json",
                                                              load: function(new_project) {
                                                                  projectDataStore.newItem(new_project);
                                                                  projectdataStore.save();
                                                                  
                                                              }
                                                          });
                                            		}
                                            	},"newProjectDialogAddBtn");
                                            	newProjectDialogAddBtn.startup();
                                            	
                                            	var newProjectDialogCancelBtn = new Button({
                                            		label: "取消",
                                            		onClick: function(){
                                            			dijit.byId("createProjectForCompanyDialog").hide();
                                            		}
                                            	}, "newProjectDialogCancelBtn");
                                            	newProjectDialogCancelBtn.startup();
//                                                var form_content = {
//                                                    projectName: dom.byId("addProjectName").value,
//                                                };
//                                                xhr.post({
//                                                    form: "add_project_form",
//                                                    // read the url: from the action="" of the <form>
//                                                    timeout: 3000,
//                                                    // give up after 3 seconds
//                                                    content: form_content,
//                                                    handleAs: "json",
//                                                    load: function(new_project) {
//                                                        projectDataStore.newItem(new_project);
//                                                        projectdataStore.save();
//                                                    }
//                                                });
                                            }
                                        }, addButtonNodeId);
                                        add_project_btn.startup();
                                        
                                        //create addFromExistButton
                                        var addFromExistButtonNodeId = paneNode + "AddFromExsitButton";
                                        var addFromExitAddButtonNode = document.createElement("div");
                                        addFromExitAddButtonNode.setAttribute("id", addFromExistButtonNodeId);
                                        document.getElementById(paneNode).appendChild(addFromExitAddButtonNode);
                                        var addProjectFromExistBtn = new Button({
                                        	label: "从已存在的项目中添加",
                                        	onClick: function(){
                                        		
                                        	}
                                        },addFromExistButtonNodeId);
                                        
                                        //create close tab button
                                        constructCloseTabBtn(paneNode);

                                        //create grid node
                                        var gridNodeId = paneNode + "Grid";
                                        var newGridNode = document.createElement("div");
                                        document.getElementById(paneNode).appendChild(newGridNode);
                                        newGridNode.setAttribute("id", gridNodeId);
                                        newGridNode.setAttribute("style", "height:400px;");
                                        var newGridLayout = [{
                                            name: "项目编号",
                                            field: "id",
                                            width: project_cell_width + "px",
                                            canSort: true
                                        }, {
                                            name: "项目名称",
                                            field: "projectName",
                                            width: project_cell_width + "px",
                                            editable: true
                                        }, {
                                            name: "项目开始日期",
                                            field: "startDate",
                                            width: project_cell_width + "px",
                                            canSort: true
                                        }, {
                                            name: "项目结束日期",
                                            field: "endDate",
                                            width: project_cell_width + "px",
                                            canSort: true
                                        }]
                                        constructNewGridForPane(gridNodeId, newGridLayout, "/project/list/" + id);
										
									}
								});
							}
                    	}],
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

                    //construt add button
                    var add_company_btn = new Button({

                        label: "新建",
                        onClick: function() {
                            var form_content = {
                                companyName: dom.byId("addCompanyName").value,
                            };
                            xhr.post({
                                form: "add_company_form",
                                // read the url: from the action="" of the <form>
                                timeout: 3000,
                                // give up after 3 seconds
                                content: form_content,
                                handleAs: "json",
                                load: function(new_company) {
                                    companyDataStore.newItem(new_company);
                                    companydataStore.save();
                                }
                            });
                        }
                    }, "add_company_btn");
                    add_company_btn.startup();

                    //construt save button
                    var save_button = new Button({
                        label: "保存",
                        onClick: function() {
                            companyDataStore.save();

                        }
                    }, "company_save_button");
                    save_button.startup();

                    //construt delete button
                    var delete_button = new Button({
                        label: "删除",
                        onClick: function() {
                            var company_selected = companyGrid.selection.getSelected();
                            if (company_selected.length) {
                                for (key in company_selected) {
                                    companyDataStore.deleteItem(company_selected[key]);
                                    companyDataStore.save();
                                }
                            }
                        }
                    }, "company_delete_button");
                    delete_button.startup();
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
        };
        

        //ammeter record pane
        construtAmmeterRecordPane = function construtAmmeterRecordPane(id) {

            var construtAmmeterRecordGrid = function construtAmmeterRecordGrid() {
            		
            		var restUrl = "/ammeter_record/list";
            		
            		if(id){
            			restUrl = restUrl + "/" + id;
            		}
            		
                    ammeterRecordStore = new JsonRestStore({
                        target: restUrl
                    });
                    ammeterRecordGrid = new EnhancedGrid({
                        autoHeight: 15,
                        store: recordDataStore = ammeterRecordStore,
                        structure: [{
                            name: "电表记录编号",
                            field: "id",
                            width: "25%",
                            canSort: true
                        }, {
                            name: "电表名称",
                            field: "ammeterName",
                            width: "25%",
                            canSort: true
                        }, {
                            name: "电表数值",
                            field: "ammeterValue",
                            width: "25%",
                            canSort: true
                        }, {
                            name: "电表记录日期",
                            field: "recordDate",
                            width: "25%",
                            canSort: true
                        }, ],
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
            
            var paneNode = dijit.byId(title);

            if(paneNode){
                paneGrid = dijit.byId(title + "Grid")
                paneGrid.setQuery({"id" : "*"}); 
                tabContainer.addChild(paneNode);
                tabContainer.selectChild(dijit.byId(paneNode));
                return ;
            }else{
                paneNode = constructNewPane(id, title, "width: 100%;", tabContainer);
                //create button node
                var addButtonNodeId = paneNode + "AddButton";
                domConstruct.place('<div id="' + addButtonNodeId + '">', paneNode, "first");

                var ammeterAddBtn = new Button({
                    label: "添加",
                    onClick: function(){
                        var form_content = {
                            projectName: dom.byId("forCompanyProjectName").value
                        };
                        xhr.post({
                            form: "newProjectDialogForm",
                              // read the url: from the action="" of the <form>
                              timeout: 3000,
                              // give up after 3 seconds
                              content: form_content,
                              handleAs: "json",
                              load: function(new_project) {
                                  projectDataStore.newItem(new_project);
                                  projectdataStore.save();
                                  
                              }
                          });
                    }
                },addButtonNodeId);
                newProjectDialogAddBtn.startup();


                var ammeterGridId = paneNode + "Grid";
                domConstruct.place('<div id="' + ammeterGridId + '">', paneNode, "last");
                domStyle.set(dojo.byId(ammeterGridId), "height", "400px");
                constructNewGridForPane(ammeterGridId, ammeterGridLayout, "/ammeter/list/");

            }

            // var constructAmmeterOperBtns = function constructAmmeterOperBtns(){
            //     //construt add button
            //     var add_ammeter_btn = new Button({
            //         label: "新建",
            //         onClick: function() {
            //             var form_content = {
            //                 name: dom.byId("name").value,
            //                 companyName: dijit.byId("companyForAmmeter").get("value"),
            //                 projectName: dijit.byId("projectForAmmeter").get("value"),
            //                 pumpName: dom.byId("pumpName").value,
            //             };
            //             xhr.post({
            //                 form: "add_ammeter_form",
            //                 // read the url: from the action="" of the <form>
            //                 timeout: 3000,
            //                 // give up after 3 seconds
            //                 content: form_content,
            //                 handleAs: "json",
            //                 load: function(new_ammeter) {
            //                     dataStore.newItem(new_ammeter);
            //                     dataStore.save();
            //                 }
            //             });
            //         }
            //     }, "add_ammeter_btn");
            //     add_ammeter_btn.startup();

            //     //construt save button
            //     var save_button = new Button({
            //         label: "保存",
            //         onClick: function() {
            //             dataStore.save();
            //         }
            //     }, "save_button");
            //     save_button.startup();

            //     //construt delete button
            //     var delete_button = new Button({
            //         label: "删除",
            //         onClick: function() {
            //             var user_selected = ammeterGrid.selection.getSelected();
            //             if (user_selected.length) {
            //                 for (key in user_selected) {
            //                     dataStore.deleteItem(user_selected[key]);
            //                     dataStore.save();
            //                 }
            //             }
            //         }
            //     }, "delete_button");
            //     delete_button.startup();
            // };


            // var constructProjectCompanyForAmmeterCombo = function constructProjectCompanyForAmmeterCombo() {

            //     companyForAmmeterStore = new JsonRestStore({
            //         target: "/company/list/"
            //     });

            //     projectForAmmeterStore = new JsonRestStore({
            //         target: "/project/list/"
            //     });
                
            //     var projectForAmmeterCombo = new ComboBox({
            //         id: "projectForAmmeter",
            //         name: "project",
            //         value: "",
            //         store: projectForAmmeterStore,
            //         searchAttr: "projectName"
            //     }, "projectForAmmeter");

            //     var companyForAmmeterCombo = new ComboBox({
            //         id: "companyForAmmeter",
            //         name: "company",
            //         value: "",
            //         store: companyForAmmeterStore,
            //         searchAttr: "companyName"
            //     }, "companyForAmmeter");
            // };    
        };
        
        //user pane
        construtUserPane = function construtUserPane() {

            //show the user Pane
            var construtUserGrid = function construtUserGrid() {
                    userStore = new JsonRestStore({
                        target: "/user/list/"
                    });
                    userGrid = new EnhancedGrid({
                        store: userDataStore = userStore,
                        structure: [{
                            name: "用户编号",
                            field: "id",
                            height: "24px",
                            width: user_cell_width * 0.5 + "px",
                            canSort: true
                        }, {
                            name: "用户名称",
                            field: "username",
                            width: user_cell_width * 0.5 + "px",
                            editable: true
                        }, {
                            name: "用户邮箱",
                            field: "email",
                            width: user_cell_width + "px",
                            editable: true
                        }, {
                            name: "用户角色",
                            field: "roles",
                            width: user_cell_width + "px",
                            editable: true
                        }, {
                            name: "用户公司",
                            field: "company",
                            width: user_cell_width + "px",
                            editable: false
                        }, {
                            name: "用户项目",
                            field: "project",
                            width: user_cell_width + "px",
                            editable: false
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
                    }, "user_grid");
                    userGrid.startup();

                    //construt add button
                    var add_user_btn = new Button({
                        label: "新建",
                        onClick: function() {
                            var form_content = {
                                username: dom.byId("username").value,
                                email: dom.byId("userEmail").value,
                                password: dom.byId("password").value,
                            };
                            xhr.post({
                                form: "add_user_form",
                                // read the url: from the action="" of the <form>
                                timeout: 3000,
                                // give up after 3 seconds
                                content: form_content,
                                handleAs: "json",
                                load: function(new_user) {
                                    userDataStore.newItem(new_user);
                                    userDataStore.save();
                                }
                            });
                        }
                    }, "add_user_btn");
                    add_user_btn.startup();

                    //construt save button
                    var save_button = new Button({
                        label: "保存",
                        onClick: function() {
                            userDataStore.save();

                        }
                    }, "user_save_button");
                    save_button.startup();

                    //construt delete button
                    var delete_button = new Button({
                        label: "删除",
                        onClick: function() {
                            var user_selected = userGrid.selection.getSelected();
                            if (user_selected.length) {
                                for (key in user_selected) {
                                    userDataStore.deleteItem(user_selected[key]);
                                    userDataStore.save();
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

            if (!userPaneConstruted) {
                if (typeof userPane != "undefined") {
                    tabContainer.addChild(userPane, 0);
                    tabContainer.selectChild(userPane);
                    construtUserGrid();
                    constructProjectCompanyForUserCombo();
                    userPaneConstruted = true;
                }
            } else {
                tabContainer.selectChild(userPane);
            }
        };


        //construtUserPane(); 

        //constuctNewPane and return the content node id
        constructNewPane = function  constructNewPane(id, title, styles, container){
            console.log(title);
            var newPane = new ContentPane({
            	id: title,
                title:title,
                content:"<div id = "+title+"></div>",
                style: styles
            });
            console.log(newPane);
            container.addChild(newPane, 0);
            container.selectChild(newPane);
            return title;
        };
        
        //create close tab button
        var constructCloseTabBtn = function constructCloseTabBtn(paneNode){
        	 var closeTabButtonNodeId = paneNode + "CloseTabBtn";
             var closeTabButtonNode = document.createElement("div");
             closeTabButtonNode.setAttribute("id", closeTabButtonNodeId);
             document.getElementById(paneNode).appendChild(closeTabButtonNode);
             var addProjectFromExistBtn = new Button({
             	label: "关闭选项卡",
             	onClick: function(){
             		tabContainer.removeChild(dijit.byId(paneNode));
             	}
             },closeTabButtonNodeId);
        };
        
        //constuctNewGrid
        constructNewGridForPane = function constructNewGridForPane(contentNode, layout, targetUrl){
        	
        	 var newStore = new JsonRestStore({
                 target: targetUrl
             });
        	 
             var newGrid = new EnhancedGrid({
                 store: newStore,
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
        };


        var activeMenuItem = function activeMenuItem(menuItem){
        	if(activedMenuItem){
        		domClass.remove(activedMenuItem, "active");
        	}
        	activedMenuItem = menuItem;
        	domClass.add(activedMenuItem,"active");
        };
        
        var ammeterMenuItem = dojo.byId("ammeterMenuItem");
        on(ammeterMenuItem, "click", function(){
        	var ammeterMenuItemBtn = dojo.byId("ammeterMenuItemBtn");
        	activeMenuItem(ammeterMenuItemBtn);
            construtAmmeterPane("ammeterPane", "电表管理");	
        });
        
        var upMenuItem = dojo.byId("upMenuItem");
        on(upMenuItem, "click", function(){
        	var upMenuItemBtn = dojo.byId("upMenuItemBtn");
        	activeMenuItem(upMenuItemBtn);
        	construtProjectPane();
        });
        
        var uaMenuItem = dojo.byId("uaMenuItem");
        on(uaMenuItem, "click", function(){
        	var uaMenuItemBtn = dojo.byId("uaMenuItemBtn");
        	activeMenuItem(uaMenuItemBtn);
        	construtAmmeterPane();
        });
        
        var ammeterRecordMenuItem = dojo.byId("ammeterRecordMenuItem");
        on(ammeterRecordMenuItem, "click", function(){
        	var ammeterRecordMenuItemBtn = dojo.byId("ammeterRecordMenuItemBtn");
        	activeMenuItem(ammeterRecordMenuItemBtn);
        	construtAmmeterRecordPane();
        });
        
        var projectMenuItem = dojo.byId("projectMenuItem");
        on(projectMenuItem, "click", function(){
        	var projectMenuItemBtn = dojo.byId("projectMenuItemBtn");
        	activeMenuItem(projectMenuItemBtn);
        	construtProjectPane();
        });
        
        var paMenuItem = dojo.byId("paMenuItem")
        on(paMenuItem, "click", function(){
        	var paMenuItemBtn = dojo.byId("paMenuItemBtn")
        	activeMenuItem(paMenuItemBtn);
        	construtPAPane();
        });
        
        var puMenuItem = dojo.byId("puMenuItem");
        on(puMenuItem, "click", function(){
        	var puMenuItemBtn = dojo.byId("puMenuItemBtn");
        	activeMenuItem(puMenuItemBtn);
        	construtUPPane();
        });
        
        var companyMenuItem = dojo.byId("companyMenuItem");
        on(companyMenuItem, "click", function(){
        	var companyMenuItemBtn = dojo.byId("companyMenuItemBtn");
        	activeMenuItem(companyMenuItemBtn);
        	construtCompanyPane();
        });
        
        var cpMenuItem = dojo.byId("cpMenuItem");
        on(cpMenuItem, "click", function(){
        	var cpMenuItemBtn = dojo.byId("cpMenuItemBtn");
        	activeMenuItem(cpMenuItemBtn);
        	construtCPPane();
        });
        
        var userMenuItem = dojo.byId("userMenuItem");
        on(userMenuItem, "click", function(){
        	var userMenuItemBtn = dojo.byId("userMenuItemBtn");
        	activeMenuItem(userMenuItemBtn);
        	construtUserPane();
        });
    });
});


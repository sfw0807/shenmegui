<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">

    <title></title>
    <link rel="stylesheet" href="/js/jquery-ui/css/redmond/jquery-ui-1.10.4.custom.css"/>
    <link rel="stylesheet" href="/css/index.css"/>
    <script src="/js/jquery-ui/js/jquery-1.10.2.js"></script>
    <script src="/js/jquery-ui/js/jquery-ui-1.10.4.custom.js"></script>
    <link rel='stylesheet' type='text/css' href='/js/jquery.dynatree/skin/ui.dynatree.css'/>
    <script src='/js/jquery.dynatree/jquery.dynatree.js' type="text/javascript"></script>
    <script src='/js/jquery.datatables/js/jquery.dataTables.js' type="text/javascript"></script>
    <link rel='stylesheet' type='text/css' href='/js/jquery.datatables/css/jquery.dataTables.css'/>
    <script src="/js/layout.js" type="text/javascript"></script>
    <script src="/js/companyManager.js" type="text/javascript"></script>
    <script src="/js/projectManager.js" type="text/javascript"></script>
    <script src="/js/ammeterManager.js" type="text/javascript"></script>
    <script src="/js/ammeterRecordManager.js" type="text/javascript"></script>
    <script src="/js/gprsManager.js" type="text/javascript"></script>
    <script src="/js/home.js" type="text/javascript"></script>
</head>
<body>
<!--start left bar-->
<div id="accordion-resizer" class="ui-widget-content">
    <div id="accordion">
        <h3>平台管理</h3>

        <div>
            <div id="tree"></div>
        </div>
        <h3>用户管理</h3>

        <div>
            <p>正在努力完成中 </p>
        </div>
        <h3>用户管理</h3>

        <div>
            <p>正在努力完成中</p>
        </div>
    </div>
</div>
<!--end left bar-->
<!--start main container-->
<div id="tabs">
    <ul>
        <li><a href="#tabs-1">公司管理</a> <span class="ui-icon ui-icon-close" role="presentation">Remove Tab</span></li>
        <li><a href="#tabs-2">项目管理</a> <span class="ui-icon ui-icon-close" role="presentation">Remove Tab</span></li>
        <li><a href="#tabs-3">GPRS管理</a> <span class="ui-icon ui-icon-close" role="presentation">Remove Tab</span></li>
        <li><a href="#tabs-4">电表管理</a> <span class="ui-icon ui-icon-close" role="presentation">Remove Tab</span></li>
        <li><a href="#tabs-5">电表记录管理</a> <span class="ui-icon ui-icon-close" role="presentation">Remove Tab</span></li>
    </ul>
    <div id="tabs-1">
        <div id="companyToolbar" class="ui-widget-header ui-corner-all toolbar">
            <button id="addCompanyBtn">新增公司</button>
            <button id="modifyCompanyBtn">修改公司</button>
            <button id="deleteCompanyBtn">删除公司</button>
        </div>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="companyTable"></table>
    </div>
    <div id="tabs-2">
        <div id="projectToolbar" class="ui-widget-header ui-corner-all toolbar">
            <button id="addProjectBtn">新增项目</button>
            <button id="modifyProjectBtn">修改项目</button>
            <button id="deleteProjectBtn">删除项目</button>
        </div>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="projectTable"></table>
    </div>
    <div id="tabs-3">
        <div id="gprsToolbar" class="ui-widget-header ui-corner-all toolbar">
            <button id="addGPRSBtn">新增GPRS</button>
            <button id="modifyGPRSBtn">修改GPRS</button>
            <button id="deleteGPRSBtn">删除GPRS</button>
        </div>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="gprsTable"></table>
    </div>
    <div id="tabs-4">
        <div id="ammeterToolbar" class="ui-widget-header ui-corner-all toolbar">
            <button id="addAmmeterBtn">新增电表</button>
            <button id="modifyAmmeterBtn">修改电表</button>
            <button id="deleteAmmeterBtn">删除电表</button>
        </div>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="ammeterTable"></table>
    </div>
    <div id="tabs-5">
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="ammeterRecordTable"></table>
    </div>
</div>

<%--company dialog--%>
<div id="deleteCompanyConfirm" class="hidden" title="删除公司?">
    <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>确定删除公司?</p>
</div>

<div id="createCompanyDialog" class="dialog hidden" title="创建公司">
    <p class="validateTips">所有项都为必填！</p>

    <form>
        <fieldset>
            <label class="dialogLabel" for="companyNameInput">公司名:</label>
            <input type="text" name="companyNameInput" id="companyNameInput"
                   class="text ui-widget-content ui-corner-all"/>
        </fieldset>
    </form>
</div>
<%--end company dialog--%>
<%--project dialog--%>
<div id="deleteProjectConfirm" class="hidden" title="删除项目?">
    <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>确定删除项目?</p>
</div>

<div id="createProjectDialog" class="dialog hidden" title="创建项目">
    <p class="validateTips">所有项都为必填！</p>

    <form>
        <fieldset>
            <label class="dialogLabel" for="projectNameInput">项目名称:</label>
            <input type="text" name="projectNameInput" id="projectNameInput"
                   class="text ui-widget-content ui-corner-all"/>
            <label class="dialogLabel" for="startDateInput">项目开始时间:</label>
            <input type="text" name="startDateInput" id="startDateInput" class="text ui-widget-content ui-corner-all"/>
            <label class="dialogLabel" for="endDateInput">项目结束时间:</label>
            <input type="text" name="endDateInput" id="endDateInput" class="text ui-widget-content ui-corner-all"/>
            <label class="dialogLabel" for="electricityChargeInput">电费:</label>
            <input type="text" name="electricityChargeInput" id="electricityChargeInput"
                   class="text ui-widget-content ui-corner-all"/>
            <label class="dialogLabel" for="partsRatioInput">分成比例:</label>
            <input type="text" name="partsRatioInput" id="partsRatioInput"
                   class="text ui-widget-content ui-corner-all"/>
        </fieldset>
    </form>
</div>
<%--end project dialog--%>
<%--gprs dialog--%>
<div id="deleteGPRSConfirm" class="hidden" title="删除GPRS?">
    <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>确定删除GPRS?</p>
</div>
<div id="createGPRSDialog" class="dialog hidden" title="创建GPRS">
    <p class="validateTips">所有项都为必填！</p>

    <form>
        <fieldset>
            <label class="dialogLabel" for="identifierInput">GPRS识别码:</label>
            <input type="text" name="identifierInput" id="identifierInput"
                   class="text ui-widget-content ui-corner-all"/>
            <label class="dialogLabel" for="gprsNameInput">GPRS名称:</label>
            <input type="text" name="gprsNameInput" id="gprsNameInput" class="text ui-widget-content ui-corner-all"/>
        </fieldset>
    </form>
</div>
<%--end gprs dialog--%>
<%--ammeter dialog--%>
<div id="deleteAmmeterConfirm" class="hidden" title="删除电表?">
    <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>确定删除GPRS?</p>
</div>
<div id="createAmmeterDialog" class="dialog hidden" title="创建电表">
    <p class="validateTips">所有项都为必填！</p>

    <form>
        <fieldset>
            <label class="dialogLabel" for="ammeterNameInput">电表标识:</label>
            <input type="text" name="ammeterNameInput" id="ammeterNameInput"
                   class="text ui-widget-content ui-corner-all"/>
            <label class="dialogLabel" for="pumpNameInput">电表名称:</label>
            <input type="text" name="pumpNameInput" id="pumpNameInput" class="text ui-widget-content ui-corner-all"/>
            <label class="dialogLabel" for="sensorRateInput">互感器倍率:</label>
            <input type="text" name="sensorRateInput" id="sensorRateInput"
                   class="text ui-widget-content ui-corner-all"/>
            <label class="dialogLabel" for="formerCostInput">技改前能耗:</label>
            <input type="text" name="formerCostInput" id="formerCostInput"
                   class="text ui-widget-content ui-corner-all"/>
            <label class="dialogLabel" for="upperLimitInput">警报上限:</label>
            <input type="text" name="upperLimitInput" id="upperLimitInput"
                   class="text ui-widget-content ui-corner-all"/>
            <label class="dialogLabel" for="lowerLimitInput">警报下限:</label>
            <input type="text" name="lowerLimitInput" id="lowerLimitInput"
                   class="text ui-widget-content ui-corner-all"/>
        </fieldset>
    </form>
</div>
<%--end ammeter dialog--%>


<!--end main containers-->
</body>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

<%
    String path = request.getContextPath();
    String ctx = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ include file="/jsp/includes/header.jsp" %>
<%@ include file="/jsp/includes/jquery.jsp" %>
<%@ include file="/jsp/includes/jquery-ui.jsp" %>
<%@ include file="/jsp/includes/jquery-plugins.jsp" %>
<%@ include file="/jsp/includes/datatable.jsp" %>

<!DOCTYPE html >
<html>
<head>
    <title>服务分类管理</title>

    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="<%=path %>/css/index.css"/>
    <script src="<%=path %>/js/layout.js" type="text/javascript"></script>
    <script src="<%=path %>/js/serviceCategoryManager.js" type="text/javascript"></script>
    <script src="<%=path %>/js/serviceCategory.js" type="text/javascript"></script>
    <script src="<%=path %>/js/json/json2.js" type="text/javascript"></script>
    <style>
        .ui-combobox {
            position: relative;
            display: inline-block;
        }

        .ui-combobox-toggle {
            position: absolute;
            top: 0;
            bottom: 0;
            margin-left: -1px;
            padding: 0;
            /* adjust styles for IE 6/7 */
            *height: 1.7em;
            *top: 0.1em;
        }

        .ui-combobox-input {
            margin: 0;
            padding: 0.3em;
        }

        .ui-menu {
            position: absolute;
            width: 100px;
        }

        .demo-description {
            clear: both;
            padding: 12px;
            font-size: 1.3em;
            line-height: 1.4em;
        }

        .ui-draggable, .ui-droppable {
            background-position: top;
        }

        #dialog-form label input select {
            display: block;
        }

        #dialog-form select {
            margin-bottom: 12px;
            width: 95%;
            padding: .4em;
        }

        input.text {
            margin-bottom: 12px;
            width: 95%;
            padding: .4em;
        }

        fieldset {
            padding: 0;
            border: 0;
            margin-top: 25px;
        }

        .ui-dialog .ui-state-error {
            padding: .3em;
        }

        .validateTips {
            border: 0px solid transparent;
            padding: 0.3em;
        }
    </style>

</head>
<body>

<div id="tabs" style="width: 100%;font-size:66%;">
    <ul>
        <li id='tab0'>
            <a href='#tabs-0'>服务分类管理</a>
        </li>
    </ul>

    <div id="tabs-0">
        <div class="ui-widget-header" style="margin-bottom: 0.5em;padding:0.5em;">
            <button id="add">新增</button>
            <button id="del">删除</button>
            <button id="modify">修改</button>
        </div>
        <div id="dialog-form" style="display:none;">
            <p class="validateTips"></p>

            <form>
                <fieldset>
                    <label>分组Id</label>
                    <input type="text" id="form_categoryId" class="text ui-widget-content ui-corner-all"/>
                    <label>分组名称</label>
                    <input type="text" id="form_categoryName" class="text ui-widget-content ui-corner-all"/>
                    <label>上级分组</label>
                    <br>
                    <select id="form_parentId" class="text ui-widget-content ui-corner-all">
                    </select>
                    <br>
                </fieldset>
            </form>
        </div>
        <table cellpadding="0" cellspacing="0" border="0" class="display"
               id="serviceCategoryTable">
            <tfoot>
            <tr>
                <th>
                    <input type="text" name="categoryId" id="categoryId" value="分组Id"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="categoryName" id="categoryName" value="分组名称"
                           class="search_init"/>
                </th>
                <th>
                    <input type="text" name="parentId" id="parentId" value="上级分组"
                           class="search_init"/>
                </th>
            </tr>
            </tfoot>
        </table>
    </div>
</div>
</body>
</html>




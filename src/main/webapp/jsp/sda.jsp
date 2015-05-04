<%@ page language="java" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String ctx = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ include file="/jsp/includes/jquery.jsp" %>
<%@ include file="/jsp/includes/ligerUI.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>SDA查看</title>
    <link href="<%=path%>/assets/scene/css/sda.css" rel="stylesheet"/>
    <script src="<%=path%>/assets/scene/js/sdaManager.js"></script>
    <script src="<%=path%>/assets/scene/js/sda.js"></script>
</head>
<body style="padding: 4px">
<div>
    <div style="margin: 2px;">
        【节点数据】节点ID: <input type="text" id="sdaid" disabled="true"/>
        <input type="button" value="生成ID" id="randomUUID"
               style="width: 50px;
                       height: 23px;
                       overflow: hidden;
                       line-height: 23px;
                       cursor: pointer;
                       position: relative;
                       text-align:center;
                       border:1px solid #D3D3D3;
                       color:#333333;background:url('<%=path%>/js/ligerUI/lib/ligerUI/skins/Gray/images/ui/button.gif') repeat-x center center;"/>
        英文名称：<input type="text" id="structId"/>
        元数据ID：<input type="text" id="metadataId"/>
        类型：<select id="type">
        <option value="string">string</option>
        <option value="char">char</option>
        <option value="double">double</option>
        <option value="int">int</option>
        <option value="number">number</option>
        <option value="struct">struct</option>
        <option value="array">array</option>
        <option value="">不填</option>
    </select>
        是否必输：<select id="required">
        <option value="Y">Y</option>
        <option value="N">N</option>
    </select>
        备注：<input type="text" id="remark"/>
    </div>
    <a class="l-button" onclick="toggle()" style="width: 100px;">展开/收缩节点</a>
    <a class="l-button" onclick="deleteRow()" style="width: 100px;"> 删除选择行</a>
    <a class="l-button" onclick="addRow()" style="width: 100px;">增加子节点</a>
    <a class="l-button" onclick="appendToCurrentNodeUp()" style="width: 100px;">增加兄弟节点(上)</a>
    <a class="l-button" onclick="appendToCurrentNodeDown()" style="width: 100px;">增加兄弟节点(下)</a>
    <a class="l-button" onclick="up()" style="width: 100px;">节点上移</a>
    <a class="l-button" onclick="down()" style="width: 100px;">节点下移</a>
    <a class="l-button" onclick="saveSDA()" style="width: 100px;">保存</a>

    <div class="l-clear">
    </div>
</div>
<div id="maingrid">
</div>
<div>
</div>
</body>
</html>

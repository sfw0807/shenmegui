<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>列表页</title>
    <link rel="stylesheet" type="text/css" href="/resources/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="/resources/themes/icon.css">
    <link href="/resources/css/ui.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/resources/js/jquery.min.js"></script>
    <script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/resources/js/ui.js"></script>

    <script type="text/javascript" src="/jsp/service/operation/operation.js"></script>
    <script type="text/javascript">
        $(function () {
            var href = window.location.href;
            var params = href.split("&");
            var serviceId = ${service.serviceId } + "";
            if(serviceId == "undefine"){
                serviceId = params[0].split("=")[1];
            }
            console.log(href);
            console.log(serviceId);
            $.extend($.fn.validatebox.defaults.rules, {
                unique: {
                    validator: function (value, param) {
                        var result;
                        $.ajax({
                            type: "get",
                            async: false,
                            url: "/operation/uniqueValid",
                            dataType: "json",
                            data: {"operationId": value, "serviceId": serviceId},
                            success: function (data) {
                                result = data;
                            }
                        });
                        return result;
                    },
                    message: '已存在相同场景编号'
                }
            });
        });

    </script>

</head>

<body>
<form class="formui" id="operationForm">
    <div class="easyui-panel" title="基本信息" style="width:100%;height:auto;padding:10px;">
        <input type="hidden" name="serviceId" value="${service.serviceId }"/>
        <table border="0" cellspacing="0" cellpadding="0">
            <tr>
                <th>服务代码</th>
                <td><input class="easyui-textbox" disabled="disabled" type="text" value="${service.serviceId }"></td>

                <th>服务名称</th>
                <td><input class="easyui-textbox" disabled="disabled" type="text" name="serviceName"
                           value="${service.serviceName }">
                </td>
            </tr>
            <tr>
                <th>场景号</th>
                <td><input id="operationId" name="operationId" class="easyui-textbox" type="text"
                           data-options="required:true, validType:'unique'"></td>
                <th>场景名称</th>
                <td><input id="operationName" name="operationName" class="easyui-textbox" type="text"></td>
            </tr>
            <tr>
                <th>功能描述</th>
                <td colspan="3"><input id="operationDesc" name="operationDesc" class="easyui-textbox" style="width:100%"
                                       type="text"></td>
            </tr>
            <tr>
                <th>场景关键词</th>
                <td><input class="easyui-textbox" disabled="disabled" type="text" name=""></td>

                <th>状态</th>
                <td><select id="state" name="state" class="easyui-combobox" panelHeight="auto" style="width:155px">
                    <option value="java">节点1</option>
                    <option value="c">节点2</option>
                    <option value="basic">节点3</option>
                    <option value="perl">节点4</option>
                </select>
                </td>
            </tr>
            <tr>
                <th>使用范围</th>
                <td><input class="easyui-textbox" disabled="disabled" type="text" name=""></td>

                <th>备注</th>
                <td><input id="operationRemark" name="operationRemark" class="easyui-textbox" type="text">
                </td>
            </tr>
        </table>


    </div>
    <div style="margin-top:10px;"></div>

    <div id="p" class="easyui-panel" title="服务消费方应用管理" style="width:100%;height:auto;padding:10px;">
        <table border="0" cellspacing="0" cellpadding="0" style="width:auto;">
            <tr>
                <th align="center">已经应用</th>
                <td width="50" align="center">&nbsp;</td>
                <th align="center">应用系统列表</th>
            </tr>

            <tr>
                <th align="center"><select name="select2" id="olddataui" size="10" multiple
                                           style="width:155px;height:160px" panelHeight="auto">
                    <option value="java">节点1</option>
                    <option value="c">节点2</option>
                    <option value="basic">节点3</option>
                    <option value="perl">节点4</option>
                </select></th>
                <td align="center" valign="middle"><a href="#" class="easyui-linkbutton" iconCls="icon-select-add"
                                                      onClick="uiinit.selectex('olddataui','newdataui')"></a><br>
                    <br>
                    <br>
                    <a href="#" class="easyui-linkbutton" iconCls="icon-select-remove"
                       onClick="uiinit.selectex('newdataui','olddataui')"></a></td>
                <td align="center"><select name="select" id="newdataui" size="10" multiple
                                           style="width:155px;height:160px" panelHeight="auto">

                </select></td>
            </tr>
        </table>
    </div>
    <div style="margin-top:10px;"></div>
    <div class="easyui-panel" title="服务提供方应用管理" style="width:100%;height:auto;padding:10px;">
        <table border="0" cellspacing="0" cellpadding="0" style="width:auto;">
            <tr>
                <th align="center">已经应用</th>
                <td width="50" align="center">&nbsp;</td>
                <th align="center">应用系统列表</th>
            </tr>

            <tr>
                <th align="center"><select name="select2" id="olddataui" size="10" multiple
                                           style="width:155px;height:160px" panelHeight="auto">
                    <option value="java">节点1</option>
                    <option value="c">节点2</option>
                    <option value="basic">节点3</option>
                    <option value="perl">节点4</option>
                </select></th>
                <td align="center" valign="middle"><a href="#" class="easyui-linkbutton" iconCls="icon-select-add"
                                                      onClick="uiinit.selectex('olddataui','newdataui')"></a><br>
                    <br>
                    <br>
                    <a href="#" class="easyui-linkbutton" iconCls="icon-select-remove"
                       onClick="uiinit.selectex('newdataui','olddataui')"></a></td>
                <td align="center"><select name="select" id="newdataui" size="10" multiple
                                           style="width:155px;height:160px" panelHeight="auto">

                </select></td>
            </tr>
        </table>
    </div>
    <div style="margin-top:10px;"></div>

    <div class="win-bbar" style="text-align:center"><a href="#" class="easyui-linkbutton" iconCls="icon-cancel"
                                                       onClick="clean()">取消</a><a href="#"
                                                                                  onclick="save('operationForm')"
                                                                                  class="easyui-linkbutton"
                                                                                  iconCls="icon-save">保存</a></div>
</form>
</body>
</html>

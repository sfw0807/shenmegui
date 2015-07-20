<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>列表页</title>
<link rel="stylesheet" type="text/css"
	href="/resources/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/resources/themes/icon.css">
<link href="/resources/css/css.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="/resources/js/jquery.min.js"></script>
	<script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="/jsp/service/operation/operation.js"></script>
	<script type="text/javascript">
	    //根据已选中的接口关系在“接口映射”区域更新接口信息
        function relateInterface(){
                    var item = $('#invokeList').treegrid('getSelected');
                    if(item != null){
                        if(item.interfaceId == null || item.interfaceId=='' ){
                            alert("该系统没有关联接口！");
                            return false;
                        }
                        else{
                            $('#ida').treegrid({
                                url:'/ida/getInterfaces/'+item.interfaceId
                                });
                        }
                    }
         }
         //根据已选中的sda的元数据替换“接口映射”区域中对应选中接口的元数据。
         function replaceMetadataId(){
            var sda = $("#sda").treegrid("getSelected");
            console.log(sda);
            if(sda != null){
                if(sda.text == "root" || sda.text == "request"|| sda.text == "response"){
                    alert("请选择其他节点!");
                    return false
                }
                var ida = $("#ida").treegrid("getSelected");
                if(ida != null){
                     if(ida.structName == "root" || ida.structName == "request"|| ida.structName == "response"){
                          alert("请选择其他节点!");
                          return false
                     }
                     if(sda.append4 != null){
                         $.ajax({
                                    type: "post",
                                    async: false,
                                    url: "/ida/updateMetadataId",
                                    dataType: "json",
                                    data: {"metadataId": sda.append4, "id":ida.id},
                                    success: function (data) {
                                        if(data){
                                            $('#ida').treegrid("reload");
                                        }
                                    }
                         });
                     }
                }
            }
         }
	</script>

</head>

<body>
	<fieldset>
		<div>
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th>服务代码</th>
					<td><input class="easyui-textbox" type="text" name="1" id="1"
						value="${service.serviceId }" disabled="disabled">
					</td>
					<th>服务名称</th>
					<td><input class="easyui-textbox" type="text" name="2" id="2"
						value="${service.serviceName }" disabled="disabled">
					</td>
					<th>场景号</th>
					<td><input class="easyui-textbox" type="text" name="3" id="3"
						value="${operation.operationId }" disabled="disabled">
					</td>
					<th>场景名称</th>
					<td><input class="easyui-textbox" type="text" name="4" id="4"
						value="${operation.operationName }" disabled="disabled">
					</td>
				</tr>
				<tr>
					<th>映射关系列表</th>
					<td></td>
				</tr>
			</table>
		</div>
		<div>
			<table id="invokeList" class="easyui-datagrid"
				data-options="	rownumbers:true,
								singleSelect:true,
								url:'/serviceLink/getInterfaceByOSS?operationId=${operation.operationId }&serviceId=${service.serviceId }',
								method:'get',
								pagination:true,
								toolbar: '#tb',
								pageSize:10"
				style="height:200px; width:100%;">
				<thead>
					<tr>
						<th data-options="field:'invokeId',checkbox:true"></th>
						<th data-options="field:'systemId', width:50">系统id</th>
						<th data-options="field:'systemChineseName', width:150">系统名称</th>
						<th data-options="field:'isStandard', width:50"
							formatter='ff.isStandardText'>标准</th>
						<th data-options="field:'interfaceId', width:50">接口id</th>
						<th data-options="field:'interfaceName', width:150">接口名称</th>
						<th data-options="field:'type', width:50"
							formatter='ff.typeText'>类型</th>
						<th data-options="field:'desc', width:100">描述</th>
						<th data-options="field:'remark', width:100">备注</th>
					</tr>
				</thead>
			</table>

		</div>
			<div id="tb" style="padding:5px;height:auto">
        			<a href="javascript:void(0);" onclick="relateInterface()" class="easyui-linkbutton" iconCls="icon-save" plain="true">关联映射结果</a>
        	</div>
	</fieldset>
	<fieldset>
	    <div>
            <table width="100%">
                <tr>
                    <td width="50%">
                        接口需求
                    </td>
                    <td width="50%">
                        映射结果
                    </td>
                </tr>
                <tr>
                    <td colspan=2 width="100%">
                        <div style="width:45%;padding:1px; margin-top:0; float:left">
                            <table id="sda" title="sda" class="easyui-treegrid" id="tg" style=" width:auto;"
                                 data-options="
                                iconCls: 'icon-ok',
                                rownumbers: true,
                                animate: true,
                                fitColumns: true,
                                url: '/sda/sdaTree?serviceId=${service.serviceId }&operationId='+encodeURI(encodeURI('${operation.operationId }')),
                                method: 'get',
                                idField: 'id',
                                treeField: 'text'
                                "
                                >
                                    <thead>
                                        <tr>
                                            <th data-options="field:'id',checkbox:true"></th>
                                            <th data-options="field:'text',width:180,editor:'text'">字段名</th>
                                            <th data-options="field:'append1',width:60,align:'right',editor:'text'">字段别名</th>
                                            <th data-options="field:'append2',width:80,editor:'text'">类型</th>
                                            <th data-options="field:'append3',width:80,editor:'text'">长度</th>
                                            <th data-options="field:'append4',width:80,editor:'text'">元数据</th>
                                        </tr>
                                    </thead>
                            </table>
                        </div>
                        <div style="width:15px; float:left;text-align:center;">
                            <table style="margin:auto; margin-top:20px">
                             <tr><td> <a href="#" title="元数据关联" class="easyui-linkbutton"  iconCls="icon-select-add" onClick="replaceMetadataId()"></a></td></tr>
                            </table>
                        </div>
                        <div style="width:50%;padding:1px; margin-top:0; float:right">
                                                    <table title="接口定义信息" id="ida"
                                                            class="easyui-treegrid"
                                                            data-options="
                                                                iconCls:'icon-edit',
                                                                rownumbers: false,
                                                                animate: true,
                                                                fitColumns: true,
                                                                method: 'get',
                                                                idField: 'id',
                                                                treeField: 'structName',
                                                                singleSelect:true,
                                                            ">
                                                            <thead>
                                                                <tr>
                                                                    <th data-options="field:'id',checkbox:true"></th>
                                                                    <th
                                                                        data-options="field:'structName',width:150,align:'left',editor:'text'">
                                                                        字段名称
                                                                    </th>
                                                                    <th
                                                                        data-options="field:'structAlias',width:100,align:'left',editor:'text'">
                                                                        字段别名
                                                                    </th>
                                                                    <th data-options="field:'type',width:50,editor:'text'">
                                                                        类型
                                                                    </th>
                                                                    <th data-options="field:'length',width:50,editor:'text'">
                                                                        长度
                                                                    </th>
                                                                    <th data-options="field:'metadataId',width:50,editor:'text'">
                                                                        元数据ID
                                                                    </th>
                                                                    <th data-options="field:'scale',width:50,editor:'text'">
                                                                        精度
                                                                    </th>
                                                                    <th data-options="field:'required',width:50,editor:'text'">
                                                                        是否必须
                                                                    </th>

                                                                </tr>

                                                            </thead>
                                                        </table>
                        </div>
				    </td>
			    </tr>
		    </table>
	</fieldset>

</body>
</html>

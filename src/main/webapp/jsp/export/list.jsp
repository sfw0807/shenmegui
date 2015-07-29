<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>列表页</title>
<link rel="stylesheet" type="text/css"
	href="/resources/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css"
		href="/resources/themes/icon.css">
		<link href="/resources/css/css.css" rel="stylesheet" type="text/css">
			<script type="text/javascript" src="/resources/js/jquery.min.js"></script>
	<script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
	<script type="text/javascript">
            function exportServiceView(){
            	var value = $("#categoryId").combobox("getValue");
            	if(value == null || value == ''){
            		alert("请选择要导出的大类！");
            		return false;
            	}
                                                var form=$("<form>");//定义一个form表单
                                                form.attr("style","display:none");
                                                form.attr("target","");
                                                form.attr("method","post");
                                                form.attr("action","/excelExporter/exportServiceView");
                                                var input1=$("<input>");
                                                input1.attr("type","hidden");
                                                input1.attr("name","categoryId");
                                                input1.attr("value",$("#categoryId").combobox("getValue"));

                                                $("body").append(form);//将表单放置在web中
                                                form.append(input1);

                                                form.submit();//表单提交

            }
	</script>
</head>

<body>
	<fieldset>
		<legend>服务视图导出</legend>
		<form id="baseForm">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th>大类：</th>
					<td>
                        <input type="text" name="categoryId" id="categoryId"
                                               class="easyui-combobox"
                                               data-options="
                                               panelHeight:'auto',
                        						url:'/service/serviceCategorys',
                        				 		 method:'get',
                        				 		 valueField: 'categoryId',
                        				 		 textField: 'categoryName',
                        				 		 onChange:function(newValue, oldValue){
                        							this.value=newValue;
                        						}
                        					"
                                            />
					</td>
					<td><a href="javascript:void(0)" onclick="exportServiceView()" class="easyui-linkbutton" plain="true"
						iconCls="icon-save">导出</a>
					</td>
				</tr>
		</table>
		</form>
	</fieldset>
</body>
</html>
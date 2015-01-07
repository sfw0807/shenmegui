<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<html>
<head>
<meta charset="utf-8">
<title>智能电表管理系统</title>
<style type="text/css">
	@import "/dojo-release-1.7.2/dojo/resources/dojo.css";
	@import "/dojo-release-1.7.2/dijit/themes/dijit.css";
	@import "/dojo-release-1.7.2/dijit/themes/claro/claro.css";
	@import "/dojo-release-1.7.2/dojox/grid/resources/claroGrid.css";
	@import "/dojo-release-1.7.2/dojox/grid/enhanced/resources/claro/EnhancedGrid.css";
	@import "/dojo-release-1.7.2/dojox/grid/enhanced/resources/tundra/EnhancedGrid.css";
	@import "/dojo-release-1.7.2/dojox/grid/enhanced/resources/EnhancedGrid_rtl.css";
	@import "/dojo-release-1.7.2/dojox/grid/enhanced/resources/EnhancedGrid_rtl.css";
</style>
<link rel="stylesheet" href="/css/index.css" type="text/css" />

<script src="/dojo-release-1.7.2/dojo/dojo.js" 
	data-dojo-config="
		async:true,
		parseOnLoad:true,
		locale:'zh-cn'">
</script>
<script language="javascript" src="/js/index2.js">	</script>

</head>
<body class="claro">
<br />
<div id="main_container">
	<!-- This is the Tab Container -->
    <div id="tab_container" data-dojo-type="dijit.layout.TabContainer" doLayout="false" >
        <!-- The First Tab -->
        
        <!-- The Secord Tab -->
        <div data-dojo-type="dijit.layout.ContentPane" title="电表纪录管理" >   
        </div>
        
        <div data-dojo-type="dijit.layout.ContentPane" title="电表管理" style="width:98%;height:85%;">
			<div id="search_var">
				<div data-dojo-type="dijit.form.DropDownButton" style="display:float;">
				<span>新建</span>
				<div data-dojo-type="dijit.TooltipDialog" id="new_ammeter_dialog">
					<form id="add_ammeter_form" method="post" action="/ammeter/list">
					<div>
						<strong><label class="add_form_label" for="name">电表名称</label></strong>
						<div data-dojo-type="dijit.form.TextBox" id="name"></div>
					</div>
					<div>
						<strong><label class="add_form_label" for="pumpName">泵名称</label></strong>
						<div data-dojo-type="dijit.form.TextBox" id="pumpName"></div>
					</div>
					<div>
						<strong><label class="add_form_label" for="companyName">公司名称</label></strong>
						<div data-dojo-type="dijit.form.TextBox" id="companyName"></div>
					</div>
					<div>
						<strong><label class="add_form_label" for="projectName">项目名称</label></strong>
						<div data-dojo-type="dijit.form.TextBox" id="projectName"></div>
					</div>
					<div>
						<button id="add_ammeter_btn"></button>
					</div>
					</form>
				</div>
			</div>
			</div>
			<!-- The Data Grid -->
			<div id="grid" class="claro" style="width:98%;height:85%;"></div>
			<!-- The Action Bar -->
			<div id="modify_bar" class="claro">
				<button id="save_button"></button>
				<button id="delete_button"></button>
			</div>
        </div>
    </div>
</div>
<form id="search_ammeter_form" action="ammeter/search"></form>
<form id="deleget_form" action="/ammeter/list/update"></form>
</body>
</html>

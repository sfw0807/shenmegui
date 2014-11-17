<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link rel="stylesheet" href="<%=path%>/css/index.css" />
<script src="<%=path%>/jsp/system.js"language="javascript"></script>
<script src="<%=path%>/js/jquery-ui/js/jquery-1.10.2.js"></script>
<script src="<%=path%>/js/json/json2.js" type="text/javascript"></script>
<script language="JavaScript" type="text/javascript">
	$(function() {
		var systemManager = {
		   getTopology : function getTopology (param) {
	       $.ajax({
	            "type": "POST",
	            "contentType": "application/json; charset=utf-8",
	            "url": "<%=path%>/systemInfo/getTopology",
	            "data": JSON.stringify(param),
	            "dataType": "json",
	            "success": function(result) {
	            		var requiredMajorVersion = 9;
						var requiredMinorVersion = 0;
						var requiredRevision = 28;
	            		var hasProductInstall = DetectFlashVer(6, 0, 65);
						var hasRequestedVersion = DetectFlashVer(requiredMajorVersion, requiredMinorVersion, requiredRevision);
						if ( hasProductInstall && !hasRequestedVersion ) {
							var MMPlayerType = (isIE == true) ? "ActiveX" : "PlugIn";
							var MMredirectURL = window.location;
						    document.title = document.title.slice(0, 47) + " - Flash Player Installation";
						    var MMdoctitle = document.title;
							AC_FL_RunContent(
								"src", "playerProductInstall",
								"FlashVars", "MMredirectURL="+MMredirectURL+'&MMplayerType='+MMPlayerType+'&MMdoctitle='+MMdoctitle+"",
								"width", "100%",
								"height", "100%",
								"align", "middle",
								"id", "System",
								"quality", "high",
								"name", "System",
								"allowScriptAccess","sameDomain",
								"type", "application/x-shockwave-flash",
								"pluginspage", "http://www.adobe.com/go/getflashplayer"
							);
						} else if (hasRequestedVersion) {
							AC_FL_RunContent(
									"src", "System",
									"width", "100%",
									"height", "100%",
									"align", "middle",
									"id", "System",
									"quality", "high",
									"name", "System",
									"allowScriptAccess","sameDomain",
									"type", "application/x-shockwave-flash",
									"pluginspage", "http://www.adobe.com/go/getflashplayer"
							);
						} else {  
						    var alternateContent = 'Alternate HTML content should be placed here. '
						  	+ 'This content requires the Adobe Flash Player. '
						   	+ '<a href=http://www.adobe.com/go/getflash/>Get Flash</a>';
						    document.write(alternateContent);  
						}
						window.location.reload();
	            }
	        });
		}};
		
		//所有系统信息的下拉框
		function initSystemInfo() {
	          $.ajax({
	            url: '<%=path%>/serviceDevInfo/getAllSystem',
	            type: 'GET',
	            success: function(result) {
	                initSelect(result);
	            }
	          });
	    }
	    function initSelect(result) {
	    	$('#sys').append("<option value='0' selected='true'>请选择系统</option>");
			for (var i=0;i<result.length;i++)
				$('#sys').append("<option value='"+result[i].systemId+"'>"+result[i].systemAb+":"+result[i].systemName+"</option>");
		}
		initSystemInfo();
		$("#sysTopo").click(function(){
			var sysId = $("#sys").val();
			var sysType = $("input:radio:checked").val();
			if(sysId=="0"){
				alert("请选择系统");
			}else{
				var param = [sysId,sysType];
				systemManager.getTopology(param);
			}
		});
	});
</script>
</head>
<body scroll="no">
系统名称：<select id="sys"></select>
<input type="radio" value="1" name="sysType" checked/>提供方
<input type="radio" value="0" name="sysType" />调用方
<input type = "button" value="查看拓扑图" id="sysTopo" />
<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
			id="System" width="100%" height="100%"
			codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">
			<param name="movie" value="System.swf?nocache=<%=Math.random() %>" />
			<param name="quality" value="high" />
			<param name="allowScriptAccess" value="sameDomain" />
			<param name="wmode" value="opaque" />
			<embed src="System.swf?nocache=<%=Math.random() %>" quality="high" 
				width="100%" height="100%" name="System" align="middle"
				play="true"
				loop="false"
				quality="high"
				allowScriptAccess="sameDomain"
				type="application/x-shockwave-flash"
				wmode="transparent"
				pluginspage="http://www.adobe.com/go/getflashplayer">
			</embed>
</object>
</body>
</html>

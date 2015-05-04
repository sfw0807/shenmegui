<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.dc.esb.servicegov.entity.User"%>
<%
String path = request.getContextPath();
String ctx = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ include file="/jsp/includes/header.jsp" %>
<%@ include file="/jsp/includes/jquery.jsp" %>
<%@ include file="/jsp/includes/jquery-ui.jsp" %>
<%@ include file="/jsp/includes/jquery-plugins.jsp" %>
<%@ include file="/jsp/includes/datatable.jsp" %>

<!DOCTYPE html >
<html >
	<head>
		<title>密码修改</title>

		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="this is my page">
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">

		<%--<link rel="stylesheet" href="<%=path %>/themes/smoothness/jquery-ui-1.8.4.custom.css" />--%>
		<link rel="stylesheet" href="<%=path %>/css/index.css" />
		<link rel="stylesheet" href="<%=path %>/css/demos.css" />
		<%--<script src="<%=path %>/js/jquery-ui/js/jquery-1.10.2.js"></script>--%>
		<%--<script src="<%=path %>/js/jquery-1.8.2.js"></script>--%>
		<%--<script src="<%=path %>/js/jquery.bgiframe-2.1.2.js"></script>--%>
	    <%--<script src="<%=path %>/js/jquery.ui.core.js"></script>--%>
	    <%--<script src="<%=path %>/js/jquery.ui.widget.js"></script>--%>
	    <%--<script src="<%=path %>/js/jquery.ui.mouse.js"></script>--%>
	    <%--<script src="<%=path %>/js/jquery.ui.button.js"></script>--%>
	    <%--<script src="<%=path %>/js/jquery.ui.position.js"></script>--%>
	    <%--<script src="<%=path %>/js/jquery.ui.autocomplete.js"></script>--%>
	    <%--<script src="<%=path %>/js/jquery.ui.draggable.js"></script>--%>
	    <%--<script src="<%=path %>/js/jquery.ui.resizable.js"></script>--%>
	    <%--<script src="<%=path %>/js/jquery.ui.dialog.js"></script>--%>
	    <%--<script src="<%=path %>/js/jquery.effects.core.js"></script>--%>
        <%--<script src="<%=path %>/js/jquery-ui-tabs.js"></script>--%>
        <%--<script src="<%=path %>/js/combo-box.js"></script>--%>
        <%--<script src='<%=path %>/js/jquery.datatables/js/jquery.dataTables.js' type="text/javascript"></script>--%>
		<%--<link rel='stylesheet' type='text/css' href='<%=path %>/js/jquery.datatables/css/jquery.dataTables.css' />--%>
		<script src="<%=path %>/js/layout.js" type="text/javascript"></script>
		<script src="<%=path %>/js/passwordManager.js" type="text/javascript"></script>
		<script src="<%=path %>/js/password.js" type="text/javascript"></script>
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
		   .ui-draggable,.ui-droppable {
				background-position: top;
			  }
			#dialog-form label input select{ display:block; }
			#dialog-form select{ margin-bottom:12px; width:95%; padding: .4em; }
		    input.text { margin-bottom:12px; width:95%; padding: .4em; }
		    fieldset { padding:0; border:0; margin-top:25px; }
		    .ui-dialog .ui-state-error { padding: .3em; }
		    .validateTips { border: 0px solid transparent; padding: 0.3em; }
	  </style>
        
        
	</head>
	<body>
		<div id="tabs" style="width: 100%">
			<ul>
				<li id='tab0'>
					<a href='#tabs-0'>密码修改</a>
				</li>
			</ul>
			<%
			  User user = (User)request.getSession().getAttribute("user");
			  if(user != null && !"admin".equals(user.getName()))
			  { 
			%>
			<br/>
			<div id="form_u">
			     <input type="hidden"  id="userId" value="<%=user.getName() %>"/>
		           &nbsp; &nbsp;旧密码:
		         <input type="password"  id="oldPwd" value="" class="text ui-widget-content ui-corner-all" style="width:140px;"/>
		         <br/>
		           &nbsp; &nbsp;新密码:
		         <input type="password"  id="newPwd_1" value="" class="text ui-widget-content ui-corner-all" style="width:140px;"/>
		         <br/>  
		           &nbsp; &nbsp;新密码:
		         <input type="password"  id="newPwd_2" value="" class="text ui-widget-content ui-corner-all" style="width:140px;"/>
            </div>
            <div class="ui-widget-header" style="margin-bottom: 0.5em;padding:0.5em;">
			    <button id="save">保存</button>
			</div>
			<%
			  }
			  else{
			 %>
			<div id="tabs-0">
			<div class="ui-widget-header" style="margin-bottom: 0.5em;padding:0.5em;">
			    <button id="modify">修改</button>
			    <button id="reset">重置密码</button>
			</div>
			<div id="dialog-form" style="display:none;">
	               <p class="validateTips"></p>
	            <form>
	              <fieldset>
	              <label for="form_userName">用户名</label>
		          <input type="text"  id="form_userName"  class="text ui-widget-content ui-corner-all" />
		          <label for="form_oldPwd">旧密码</label>
		          <input type="password"  id="form_oldPwd" value="" class="text ui-widget-content ui-corner-all" />
		          <label for="form_newPwd_first">新密码</label>
		          <input type="password"  id="form_newPwd_first" value="" class="text ui-widget-content ui-corner-all" />
		          <label for="form_newPwd_second">新密码</label>
		          <input type="password"  id="form_newPwd_second" value="" class="text ui-widget-content ui-corner-all" />
	              </fieldset>
	           </form>
            </div>
	        <table cellpadding="0" cellspacing="0" border="0" class="display"
					id="passwordTable">
					<tfoot>
						<tr>
							<th>
								<input type="text" name="userName" id="userName" value="用户名"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="role" id="role" value="所属角色"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="orgName" id="orgName" value="所属机构"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="updateTime" id=updateTime value="最后更新时间"
									class="search_init" />
							</th>
							<th>
								<input type="text" name="status" id="status" value="用户状态"
									class="search_init" />
							</th>
						</tr>
					</tfoot>
				</table>
			</div>
			<%
			} 
			%>
		</div>
	</body>
</html>




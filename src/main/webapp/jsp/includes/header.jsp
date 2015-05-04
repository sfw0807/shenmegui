<%@ page language="java" import="com.dc.esb.servicegov.entity.Function" pageEncoding="UTF-8" %>
<%@ page language="java" import="java.util.List" %>
<%@ page language="java" %>
<%@include file="/jsp/includes/jquery.jsp" %>
<%@include file="/jsp/includes/bootstrap.jsp" %>
<%
    String path1 = request.getContextPath();
%>
<script src="<%=path1%>/js/json/json2.js" type="text/javascript"></script>
<body>
<div class="navbar navbar-inverse" style="font-size: 100%">
    <div class="navbar-inner">
        <!-- Responsive Navbar Part 1: Button for triggering responsive navbar (not covered in tutorial). Include responsive CSS to utilize. -->
        <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <%
            List<Function> functionList = (List<Function>) request.getSession().getAttribute("function_list");
            if (functionList != null) {
                for (int i = 0; i < functionList.size(); i++) {

                }
        %>
        <a class="brand">服务治理平台</a>
        <!-- Responsive Navbar Part 2: Place all navbar contents you want collapsed withing .navbar-collapse.collapse. -->
        <div class="nav-collapse collapse">
            <ul class="nav">
                <%
                    for (int i = 0; i < functionList.size(); i++) {
                        Function function = functionList.get(i);
                        if ("0".equals(function.getParentId())) {
                %>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><%=function.getName()%><b
                            class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <%
                            for (int j = 0; j < functionList.size(); j++) {
                                Function subfunction = functionList.get(j);
                                if ((function.getId() + "").equals(subfunction.getParentId())) {
                        %>
                        <li><a href="<%=path1%><%=subfunction.getUrl()%>" target="_blank"><%=subfunction.getName()%>
                        </a></li>
                        <%
                                }
                            }
                        %>
                    </ul>
                </li>
                <%
                        }
                    }
                %>
                <li>
                    <a href="<%=path1%>/user/logOut">退出</a>
                </li>
            </ul>
        </div>
        <%
        } else {
        %>
        <a href="<%=path1%>/jsp/login.jsp">登录</a>
        <%
            }
        %>
        <!--/.nav-collapse -->
    </div>
    <!-- /.navbar-inner -->
</div>



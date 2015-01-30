<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.dc.esb.servicegov.refactoring.entity.User"%>
<%@ page language="java" import="com.dc.esb.servicegov.refactoring.entity.Function"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <!--<meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport'/>-->
    <!--<meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport'/>-->
    <!--<meta name="viewport" content="width=device-width"/>-->
    <link href="/assets/favicon-160d25a7a41b36769a195df3acef4f7b.ico" rel="shortcut icon"
          type="image/vnd.microsoft.icon"/>

    <title>神州数码 | 服务治理平台</title>
    <link data-turbolinks-track="true" href="<%=path%>/jsp/loginPage/assets/sgHome.css" media="all"
          rel="stylesheet"/>

    <link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">

    <script type="text/javascript" src="https://gumroad.com/js/gumroad.js"></script>
    <script data-turbolinks-track="true" src="<%=path%>/jsp/loginPage/assets/sgHome.js"></script>
</head>
<body>
<nav class="navbar filter-bar fixed-absolute" role="navigation">
    <div class="container">
        <div class="notification">
            <div id="notif-message" class="notif-message" style="display: none;" notice-type=success>
            </div>
        </div>
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>

            <a class="navbar-brand" href="/">
                <div class="logo">
                    <img src="<%=path%>/jsp/loginPage/thumb.png"/>
                </div>
                <p>神州 <br>数码
                </p>
            </a>
        </div>
        <div class="collapse navbar-collapse navbar-ex1-collapse">
            <ul class="nav navbar-nav">
                <li><a href="#"></a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <!--
          <li>
                  <a href="javascript:void(0)" onclick="showSearchForm(this)">
                      <i class="icon-search icon-2x"></i>
                      <p>Search</p>
                  </a>
                </li>
          -->

                <li>
                    <a href="<%=path %>/jsp/serviceInfoManager.jsp">
                        <i class="icon-home icon-2x"></i>

                        <p>管理首页</p>
                    </a></li>
                <%
                    User user = (User)request.getSession().getAttribute("user");
                    if(user == null)
                    {
                %>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown">
                        <i class="icon-male-sign-alt icon-2x"></i>

                        <p>账户</p>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="javascript:void(0)" onclick="openLoginModal();" role="button" data-toggle="modal">登录</a>
                        </li>
                        <!--<li class="divider"></li>-->
                        <!--<li><a data-toggle="modal" href="javascript:void(0)" onclick="openRegisterModal();"></a>-->
                        <!--</li>-->
                    </ul>
                </li>
                <%
                    }else{
                %>
                <li>
                    <a href="/">
                        <i class="icon-home icon-2x"></i>

                        <p><%=user.getName() %></p>
                    </a>
                </li>
                <%
                    }
                %>
            </ul>

            <form accept-charset="UTF-8" action="/search" class="navbar-form navbar-right" method="get" role="search"
                  style="display:none">
                <div style="margin:0;padding:0;display:inline"><input name="utf8" type="hidden" value="&#x2713;"/></div>
                <div class="input-group">

                    <input class="form-control search" id="search" name="search"
                           placeholder="Search themes and freebies..." type="text"/>
                </div>
            </form>

        </div>
    </div>

</nav>


<div class="modal fade login" id="loginModal">
    <div class="modal-dialog login animated">
        <div class="modal-content">

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">登录:</h4>
            </div>
            <div class="modal-body">
                <div class="box">
                    <div class="content">
                        <div class="error inside-alert"></div>
                        <div class="form loginBox">
                            <!--
                            -->
                            <form action="<%=path %>/user/login"
                                  method="post" enctype="multipart/form-data">
                                <div style="margin:0;padding:0;display:inline"><input name="utf8" type="hidden"
                                                                                      value="&#x2713;"/></div>
                                <input class="form-control" id="userName" name="userName" placeholder="账号" type="text"/>
                                <input class="form-control" id="password" name="password" placeholder="密码"
                                       type="password"/>
                                <input class="btn btn-default btn-login" name="commit" type="submit" value="登录"/>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="box">
                    <div class="content registerBox" style="display:none;">
                        <div class="form">
                            <form accept-charset="UTF-8" action="/register" data-remote="true"
                                  html="{:multipart=&gt;true}" method="post">
                                <div style="margin:0;padding:0;display:inline"><input name="utf8" type="hidden"
                                                                                      value="&#x2713;"/></div>
                                <input class="form-control" id="email" name="email" placeholder="Email" type="text"/>
                                <input class="form-control" id="password" name="password" placeholder="Password"
                                       type="password"/>
                                <input class="form-control" id="password_confirmation" name="password_confirmation"
                                       placeholder="Repeat Password" type="password"/>
                                <input class="btn btn-default btn-register" name="commit" type="submit"
                                       value="Create account"/>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <div class="forgot login-footer">
            <span>Looking to 
                 <a href="javascript: showRegisterForm();">create an account</a>
            ?</span>
                </div>
                <div class="forgot register-footer" style="display:none">
                    <span>Already have an account?</span>
                    <a href="javascript: showLoginForm();">Login</a>
                </div>
            </div>

        </div>
    </div>
</div>


<div class="parallax parallax-small">
    <div class="over-gradient"></div>
    <div class="container">
        <div class="row">
            <div class="col-md-6">
                <h1 class="hello">
                    <a href="http://get-shit-done-pro.herokuapp.com">服务治理平台</a>
                    <small>让服务治理,配置开发更加轻松!</small>
                </h1>
            </div>
            <%
                if(user == null)
                {
            %>
            <div class="col-md-6">
                <div class="actions pull-right">
                    <a class="btn btn-lg btn-fill btn-warning" onclick="openLoginModal();">立即登录</a>
                </div>
                <div class="actions pull-right">
                    <a class="btn btn-lg btn-default" onclick="openClientMode();">游客模式</a>
                </div>
            </div>
            <%
                }else{
            %>
            <div class="col-md-6">
                <div class="actions pull-right">
                    <a class="btn btn-lg btn-default btn-warning">
                        欢迎，<%=user.getName()%>
                    </a>
                </div>
            </div>
            <% } %>
        </div>

    </div>
</div>


<div class="main">

<div class="page-title">
    <div class="container">
        <h1 class="title">最新功能</h1>
    </div>
</div>

<div class="container">
<div class="row">
<div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
    <div class="card">
        <div class="thumbnail">
            <img alt="服务管理"
                 src="<%=path%>/jsp/loginPage/assets/service.png"/>
            <a href="<%=path%>/jsp/serviceInfoManager.jsp">
                <div class="thumb-cover">
                </div>
            </a>

            <div class="details">
                <div class="user hidden">
                    <div class="user-photo">
                        <img alt="Thumb"
                             src="http://s3.amazonaws.com/creativetim_bucket/photos/2/thumb.png?1394973934"/>
                    </div>
                    <div class="name">Tim</div>
                </div>
                <div class="clearfix"></div>
            </div>
        </div>
        <div class="card-info">
            <div class="moving">
                <a href="<%=path%>/jsp/serviceInfoManager.jsp">
                    <h3>服务管理系统</h3>
                    <p>管理服务</p>
                </a> <b class="actions">
                <a href="<%=path%>/jsp/serviceInfoManager.jsp">查看</a>
                <b class="separator">|</b>
                <a class="blue-text" href="<%=path%>/jsp/serviceInfoManager.jsp" target="_blank">进入</a>
            </b>
            </div>
        </div>
    </div>

</div>


<div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
    <div class="card">
        <div class="thumbnail">
            <img alt="Opt gsdp thumbnail"
                 src="<%=path%>/jsp/loginPage/assets/service.png"/>
            <a href="<%=path%>/jsp/serviceInfoManager.jsp">
                <div class="thumb-cover">
                </div>
            </a>

            <div class="details">
                <div class="user hidden">
                    <div class="user-photo">
                        <img alt="Thumb"
                             src="http://s3.amazonaws.com/creativetim_bucket/photos/2/thumb.png?1394973934"/>
                    </div>
                    <div class="name">Tim</div>
                </div>
                <div class="clearfix"></div>
            </div>
        </div>
        <div class="card-info">
            <div class="moving">
                <a href="<%=path%>/jsp/serviceInfoManager.jsp">
                    <h3>配置管理系统</h3>
                    <p>配置导入导出</p>
                </a> <b class="actions">
                <a href="/product/get-shit-done-pro">查看</a>
                <b class="separator">|</b>
                <a class="blue-text" href="<%=path%>/jsp/serviceInfoManager.jsp" target="_blank">进入</a>
            </b>
            </div>
        </div>
    </div>

</div>


<div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
    <div class="card">
        <div class="thumbnail">
            <img alt="Opt wizard thumbnail"
                 src="<%=path%>/jsp/loginPage/assets/service.png"/>
            <a href="<%=path%>/jsp/serviceInfoManager.jsp">
                <div class="thumb-cover">
                </div>
            </a>

            <div class="details">
                <div class="user hidden">
                    <div class="user-photo">
                        <img alt="Thumb"
                             src="http://s3.amazonaws.com/creativetim_bucket/photos/2/thumb.png?1394973934"/>
                    </div>
                    <div class="name">Tim</div>
                </div>
                <div class="clearfix"></div>
            </div>
        </div>
        <div class="card-info">
            <div class="moving">
                <a href="<%=path%>/jsp/serviceInfoManager.jsp">
                    <h3>用户管理</h3>
                    <p>管理用户权限等</p>
                </a> <b class="actions">
                <a href="<%=path%>/jsp/serviceInfoManager.jsp">查看</a>
                <b class="separator">|</b>
                <a class="blue-text" href="<%=path%>/jsp/serviceInfoManager.jsp" target="_blank">进入</a>
            </b>
            </div>
        </div>
    </div>

</div>
</div>
</div>
</div>


<div class="footer-title ">
    <div class="container">
        <div class="row">
            <div class="col-md-3">
                <h1 class="title">联系我们: </h1>
            </div>
            <div class="col-md-2">
                <a class="social dribbble" rel="publisher"><i
                        class="fa fa-dribbble"></i> 邮箱</a>
            </div>
            <div class="col-md-2">
                <a class="social"><i
                        class="fa fa-qq"></i> 扣扣</a>

            </div>
        </div>
    </div>
</div>

<div class="footer">
    <div class="overlayer">
        <div class="container">
            <div class="row support">
                <div class="col-sm-3">
                    <h4>支持</h4>
                    <ul class="list-unstyled">
                        <li><a>神州数码</a></li>
                        <li><a>Vincent Fan</a></li>
                    </ul>
                </div>
                <div class="col-sm-2">
                    <h4>神州数码</h4>
                    <ul class="list-unstyled">
                        <li><a>Terms &amp; Conditions</a></li>
                        <li><a>Privacy Policy</a></li>
                        <li><a>Licenses</a></li>
                        <li>all rights reserved.</li>
                    </ul>
                </div>
            </div>
            <div class="row">
                <div class="credits">
                    &copy; 2015 Vincent Fan, 现在是北京时间00:05我的颈椎好痛
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="feedbackModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-account">
        <div class="modal-content">
            <form accept-charset="UTF-8" action="/give_feedback" data-remote="true" enctype="multipart/form-data"
                  method="post">
                <div style="margin:0;padding:0;display:inline"><input name="utf8" type="hidden" value="&#x2713;"/></div>
                <div class="modal-body">
                    <textarea class="form-control" id="text_content" name="text[content]"
                              placeholder="Leave a comment for other creative minds..." rows="7">
                    </textarea>
                    <input class="btn btn-block btn-info btn-fill" name="commit" type="submit" value="Give feedback"/>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal fade" id="requestInvitationModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-account">
        <div class="modal-content">
            <form accept-charset="UTF-8" action="/request_invitation" data-remote="true" enctype="multipart/form-data"
                  method="post">
                <div style="margin:0;padding:0;display:inline"><input name="utf8" type="hidden" value="&#x2713;"/></div>

                <div class="modal-body">

                    <textarea class="form-control" id="text_content" name="text[content]"
                              placeholder="Show us your portfolio and we will come back with an invitation as soon as possible."
                              rows="7">
                    </textarea>
                    <input class="btn btn-block btn-info btn-fill" name="commit" type="submit"
                           value="Request Invitation"/>
                </div>
            </form>
        </div>
    </div>
</div>


<script>
    (function (i, s, o, g, r, a, m) {
        i['GoogleAnalyticsObject'] = r;
        i[r] = i[r] || function () {
            (i[r].q = i[r].q || []).push(arguments)
        }, i[r].l = 1 * new Date();
        a = s.createElement(o),
                m = s.getElementsByTagName(o)[0];
        a.async = 1;
        a.src = g;
        m.parentNode.insertBefore(a, m)
    })(window, document, 'script', '//www.google-analytics.com/analytics.js', 'ga');

    ga('create', 'UA-46172202-1', 'creative-tim.com');
    ga('send', 'pageview');

</script>

</body>


</html>

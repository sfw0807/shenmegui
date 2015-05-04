<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.dc.esb.servicegov.entity.User"%>
<%@ page language="java" import="com.dc.esb.servicegov.entity.Function"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<base href="<%=path%>">
<title>服务治理平台</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<!-- Le styles -->
<link href="<%=path%>/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="<%=path%>/bootstrap/css/bootstrap-responsive.css" rel="stylesheet">
<style>
#wrapper{
width:100px!important;
width:80px;
}

    /* GLOBAL STYLES
    -------------------------------------------------- */
    /* Padding below the footer and lighter body text */

body {
    padding-bottom: 40px;
    color: #5a5a5a;
}

    /* CUSTOMIZE THE NAVBAR
    -------------------------------------------------- */

    /* Special class on .container surrounding .navbar, used for positioning it into place. */
.navbar-wrapper {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    z-index: 11;
    margin-top: 20px;
    margin-bottom: -90px; /* Negative margin to pull up carousel. 90px is roughly margins and height of navbar. */
}

.navbar-wrapper .navbar {

}

    /* Remove border and change up box shadow for more contrast */
.navbar .navbar-inner {
    border: 0;
    -webkit-box-shadow: 0 2px 10px rgba(0, 0, 0, .25);
    -moz-box-shadow: 0 2px 10px rgba(0, 0, 0, .25);
    box-shadow: 0 2px 10px rgba(0, 0, 0, .25);
}

    /* Downsize the brand/project name a bit */
.navbar .brand {
    padding: 14px 20px 16px; /* Increase vertical padding to match navbar links */
    font-size: 16px;
    font-weight: bold;
    text-shadow: 0 -1px 0 rgba(0, 0, 0, .5);
}

    /* Navbar links: increase padding for taller navbar */
.navbar .nav > li > a {
    padding: 15px 20px;
}

    /* Offset the responsive button for proper vertical alignment */
.navbar .btn-navbar {
    margin-top: 10px;
}

    /* CUSTOMIZE THE CAROUSEL
    -------------------------------------------------- */

    /* Carousel base class */
.carousel {
    margin-bottom: 60px;
}

.carousel .container {
    position: relative;
    z-index: 9;
}

.carousel-control {
    height: 80px;
    margin-top: 0;
    font-size: 120px;
    text-shadow: 0 1px 1px rgba(0, 0, 0, .4);
    background-color: transparent;
    border: 0;
    z-index: 10;
}

.carousel .item {
    height: 500px;
}

.carousel img {
    position: absolute;
    top: 0;
    left: 0;
    min-width: 100%;
    height: 500px;
}

.carousel-caption {
    background-color: transparent;
    position: static;
    max-width: 550px;
    padding: 0 20px;
    margin-top: 200px;
}

.carousel-caption h1,
.carousel-caption .lead {
    margin: 0;
    line-height: 1.25;
    color: #fff;
    text-shadow: 0 1px 1px rgba(0, 0, 0, .4);
}

.carousel-caption .btn {
    margin-top: 10px;
}

    /* MARKETING CONTENT
    -------------------------------------------------- */

    /* Center align the text within the three columns below the carousel */
.marketing .span4 {
    text-align: center;
}

.marketing h2 {
    font-weight: normal;
}

.marketing .span4 p {
    margin-left: 10px;
    margin-right: 10px;
}

    /* Featurettes
    ------------------------- */

.featurette-divider {
    margin: 80px 0; /* Space out the Bootstrap <hr> more */
}

.featurette {
    padding-top: 120px; /* Vertically center images part 1: add padding above and below text. */
    overflow: hidden; /* Vertically center images part 2: clear their floats. */
}

.featurette-image {
    margin-top: -120px; /* Vertically center images part 3: negative margin up the image the same amount of the padding to center it. */
}

    /* Give some space on the sides of the floated elements so text doesn't run right into it. */
.featurette-image.pull-left {
    margin-right: 40px;
}

.featurette-image.pull-right {
    margin-left: 40px;
}

    /* Thin out the marketing headings */
.featurette-heading {
    font-size: 50px;
    font-weight: 300;
    line-height: 1;
    letter-spacing: -1px;
}

    /* RESPONSIVE CSS
    -------------------------------------------------- */

@media (max-width: 979px) {

    .container.navbar-wrapper {
        margin-bottom: 0;
        width: auto;
    }

    .navbar-inner {
        border-radius: 0;
        margin: -20px 0;
    }

    .carousel .item {
        height: 500px;
    }

    .carousel img {
        width: auto;
        height: 500px;
    }

    .featurette {
        height: auto;
        padding: 0;
    }

    .featurette-image.pull-left,
    .featurette-image.pull-right {
        display: block;
        float: none;
        max-width: 40%;
        margin: 0 auto 20px;
    }
}

@media (max-width: 767px) {

    .navbar-inner {
        margin: -20px;
    }

    .carousel {
        margin-left: -20px;
        margin-right: -20px;
    }

    .carousel .container {

    }

    .carousel .item {
        height: 300px;
    }

    .carousel img {
        height: 300px;
    }

    .carousel-caption {
        width: 65%;
        padding: 0 70px;
        margin-top: 100px;
    }

    .carousel-caption h1 {
        font-size: 30px;
    }

    .carousel-caption .lead,
    .carousel-caption .btn {
        font-size: 18px;
    }

    .marketing .span4 + .span4 {
        margin-top: 40px;
    }

    .featurette-heading {
        font-size: 30px;
    }

    .featurette .lead {
        font-size: 18px;
        line-height: 1.5;
    }

}
</style>

<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
<script src="../js/html5shiv.js"></script>
<![endif]-->

</head>

<body>


<!-- NAVBAR
================================================== -->
<div class="navbar-wrapper">
    <!-- Wrap the .navbar in .container to center it within the absolutely positioned parent. -->
    <div class="container">

        <div class="navbar navbar-inverse">
            <div class="navbar-inner">
                <!-- Responsive Navbar Part 1: Button for triggering responsive navbar (not covered in tutorial). Include responsive CSS to utilize. -->
                <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <% 
                List<Function> functionList = (List<Function>)request.getSession().getAttribute("function_list");
		        if(functionList != null)
		        {
		        	for(int i=0;i<functionList.size();i++){
		        		
		        	}
		        %>
                <a class="brand">浦发银行服务治理平台</a>
                <!-- Responsive Navbar Part 2: Place all navbar contents you want collapsed withing .navbar-collapse.collapse. -->
                <div class="nav-collapse collapse">
                    <ul class="nav">
                    	<%
                    	for(int i=0;i<functionList.size();i++){
                    		Function function = functionList.get(i);
                    		if("0".equals(function.getParentId())){  		
                    	%>
                        <li class="dropdown">
                        	<a href="#" class="dropdown-toggle" data-toggle="dropdown"><%=function.getName()%><b class="caret"></b></a>
                        	<ul class="dropdown-menu">
                        	<%
                        	for(int j=0;j<functionList.size();j++){
                        		Function subfunction = functionList.get(j);
                        		if((function.getId()+"").equals(subfunction.getParentId())){
                        	%> 
                            	<li><a href="<%=path%><%=subfunction.getUrl()%>" target ="_blank"><%=subfunction.getName()%></a></li>                                           	
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
                        <li >
                            <a href="<%=path%>/user/logOut">退出</a>
                        </li>
                    </ul>
                </div>
		 		<%
		 		}
		 		else
		 		{
		 		 %>
		 		    <a href="<%=path%>/jsp/login.jsp">登录</a>
		 		<%
		 		}
		 		 %>

                <!--/.nav-collapse -->
            </div>
            <!-- /.navbar-inner -->
        </div>
        <!-- /.navbar -->
    </div>
    <!-- /.container -->
</div>
<!-- /.navbar-wrapper -->


<!-- Carousel
================================================== -->
<div id="myCarousel" class="carousel slide">
    <div class="carousel-inner">
        <div class="item active">
            <img src="<%=path%>/image/slide-01.jpg" alt="">

            <div class="container">
                <div class="carousel-caption">
                    <h1>服务管理子系统</h1>
                    <p class="lead">轻松管理服务、接口、元数据、代码、系统！</p>
                </div>
            </div>
        </div>
        <div class="item">
            <img src="<%=path%>/image/slide-02.jpg" alt="">

            <div class="container">
                <div class="carousel-caption">
                    <h1>视图查询系统</h1>
                    <p class="lead">轻松查询任意服务、接口、元数据、系统之间的调用关系！</p>
                </div>
            </div>
        </div>
        <div class="item">
            <img src="<%=path%>/image/slide-03.jpg" alt="">

            <div class="container">
                <div class="carousel-caption">
                    <h1>ESB配置生成</h1>
                    <p class="lead">轻松生成ESB运行平台需要的拆组包配置、WSDL、以及脚本！</p>
                </div>
            </div>
        </div>
    </div>
    <a class="left carousel-control" href="#myCarousel" data-slide="prev">&lsaquo;</a>
    <a class="right carousel-control" href="#myCarousel" data-slide="next">&rsaquo;</a>
</div>
<!-- /.carousel -->


<!-- Marketing messaging and featurettes
================================================== -->
<!-- Wrap the rest of the page in another container to center all the content. -->
<div class="container marketing">
        <div style="float:left">
            <img src="<%=path%>/image/login_06.png">          
        </div>
        <div style="float:left;margin-left:200px;margin-top:100px;font-family: '宋体'; font-size: 14pt; font-weight: bold; font-style: normal;text-align:center;">
        <% 
        User user = (User)request.getSession().getAttribute("user");
        if(user != null)
        {
        %>
 		    <p><%=user.getName() %>,您好!</p>
 		<%
 		}
 		else
 		{
 		 %>
 		    <a href="<%=path%>/jsp/login.jsp">登录</a>
 		<%
 		}
 		 %>
 			<p>欢迎使用服务治理平台!</p>
        </div>
</div>

    <footer>
         <p style="margin-left:450px;font-size: 12px;">&nbsp;&nbsp;&nbsp;&nbsp;Copyright © 2007-2014 Digital China</p>
    </footer>

</div>
<!-- /.container -->


<!-- Le javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="<%=path%>/js/jquery.js"></script>
<script src="<%=path%>/plugins/bootstrap/js/bootstrap-transition.js"></script>
<script src="<%=path%>/plugins/bootstrap/js/bootstrap-alert.js"></script>
<script src="<%=path%>/plugins/bootstrap/js/bootstrap-modal.js"></script>
<script src="<%=path%>/plugins/bootstrap/js/bootstrap-dropdown.js"></script>
<script src="<%=path%>/plugins/bootstrap/js/bootstrap-scrollspy.js"></script>
<script src="<%=path%>/plugins/bootstrap/js/bootstrap-tab.js"></script>
<script src="<%=path%>/plugins/bootstrap/js/bootstrap-tooltip.js"></script>
<script src="<%=path%>/plugins/bootstrap/js/bootstrap-popover.js"></script>
<script src="<%=path%>/plugins/bootstrap/js/bootstrap-button.js"></script>
<script src="<%=path%>/plugins/bootstrap/js/bootstrap-collapse.js"></script>
<script src="<%=path%>/plugins/bootstrap/js/bootstrap-carousel.js"></script>
<script src="<%=path%>/plugins/bootstrap/js/bootstrap-typeahead.js"></script>
<script src="<%=path%>/plugins/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=path%>/plugins/bootstrap/js/bootstrap.js"></script>
<script>
    !function ($) {
        $(function () {
            // carousel demo
            $('#myCarousel').carousel()
        })
    }(window.jQuery)
</script>
<script src="<%=path%>/js/holder.js"></script>
</body>
</html>


<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
                <a class="brand" href="#">浦发银行服务治理平台</a>
                <!-- Responsive Navbar Part 2: Place all navbar contents you want collapsed withing .navbar-collapse.collapse. -->
                <div class="nav-collapse collapse">
                    <ul class="nav">
                        <li class="dropdown">
                        	<a href="#" class="dropdown-toggle" data-toggle="dropdown">服务管理<b class="caret"></b></a>
                            <ul class="dropdown-menu">
                            	<li><a href="<%=path%>/jsp/operationManager.jsp" target ="_blank">操作管理</a></li>
                                <li><a href="<%=path%>/jsp/serviceInfoManager.jsp" target ="_blank">服务管理</a></li>
                                <li><a href="<%=path%>/html/interfaceManagement.html" target ="_blank">接口管理</a></li>
                                <li><a href="#" target ="_blank">服务审核</a></li>
                                <li><a href="<%=path%>/jsp/serviceCategoryManager.jsp" target ="_blank">服务分类管理</a></li>
                                <li><a href="<%=path%>/jsp/metadataManager.jsp" target ="_blank">元数据管理</a></li>
                                <li><a href="<%=path%>/jsp/metadataStructsManager.jsp" target ="_blank">元数据结构管理</a></li>   
                                <li><a href="<%=path%>/jsp/systemInfoManager.jsp" target ="_blank">接入系统管理</a></li>         
                                <li><a href="<%=path%>/jsp/invokeManager.jsp" target ="_blank">调用关系管理</a></li>                      
                            </ul>
                        </li>
                        <li class="dropdown">
                        	<a href="#" class="dropdown-toggle" data-toggle="dropdown">资源管理<b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li><a href="<%=path%>/jsp/resourceImport.jsp">资源导入</a></li>
                                <li class="divider"></li>
                                <li><a href="#" target ="_blank">资源导出</a></li>
                                <li><a href="#" target ="_blank">修订记录导出</a></li> 
                                <li><a href="#" target ="_blank">上线清单导出</a></li> 
                                <li><a href="#" target ="_blank">系统级别SQL导出</a></li>
                                <li><a href="#" target ="_blank">服务依赖关系表导出</a></li> 
                                <li><a href="#" target ="_blank">服务调用申请单导出</a></li> 
                            </ul>
                        </li>                  
                        <li class="dropdown">
                        	<a href="#" class="dropdown-toggle" data-toggle="dropdown">视图查询<b class="caret"></b></a>
                            <ul class="dropdown-menu">                              
                                <li><a href="<%=path%>/jsp/svcAsmRelateView.jsp" target ="_blank">服务调用关系查询</a></li>
                                <li><a href="<%=path%>/jsp/serviceDetailView.jsp" target ="_blank">服务详细信息查询</a></li> 
                                <li><a href="<%=path%>/jsp/serviceStore.jsp" target ="_blank">服务库查询</a></li> 
                                <li><a href="<%=path%>/jsp/serviceTotalView.jsp" target ="_blank">服务统计信息</a></li> 
                                <li><a href="<%=path%>/jsp/serviceDevProgressView.jsp" target ="_blank">服务开发统计信息查询</a></li>
                                <li><a href="<%=path%>/jsp/sla.jsp" target ="_blank">服务SLA信息查询</a></li> 
                                <li><a href="<%=path%>/jsp/sysInvokeServiceView.jsp" target ="_blank">接入系统服务统计表</a></li>   
                                <li><a href="<%=path%>/jsp/sysTopology.jsp" target ="_blank">接入系统拓扑图</a></li>
                                <li><a href="<%=path%>/jsp/publishInfoView.jsp" target ="_blank">上线统计表(按日期)</a></li>                        
                            </ul>
                        </li>  
                        <li class="dropdown">
                        	<a href="#" class="dropdown-toggle" data-toggle="dropdown">系统管理<b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li><a href="#" target ="_blank">用户管理</a></li>                                
                                <li><a href="#" target ="_blank">角色管理</a></li>
                                <li><a href="#" target ="_blank">权限管理</a></li>
                                <li><a href="#" target ="_blank">用户机构管理</a></li>  
                                <li><a href="#" target ="_blank">密码修改</a></li> 
                                <li><a href="#" target ="_blank">日志查询</a></li> 
                                <li><a href="#" target ="_blank">公告管理</a></li>                                                      
                            </ul>
                        </li> 
                        <!-- Read about Bootstrap dropdowns at http://twitter.github.com/bootstrap/javascript.html#dropdowns -->
                        <li >
                            <a href="#">退出</a>
                        </li>
                    </ul>
                </div>
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
 			<p>admin,您好!</p>
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
<script src="<%=path%>/js/bootstrap-transition.js"></script>
<script src="<%=path%>/js/bootstrap-alert.js"></script>
<script src="<%=path%>/js/bootstrap-modal.js"></script>
<script src="<%=path%>/js/bootstrap-dropdown.js"></script>
<script src="<%=path%>/js/bootstrap-scrollspy.js"></script>
<script src="<%=path%>/js/bootstrap-tab.js"></script>
<script src="<%=path%>/js/bootstrap-tooltip.js"></script>
<script src="<%=path%>/js/bootstrap-popover.js"></script>
<script src="<%=path%>/js/bootstrap-button.js"></script>
<script src="<%=path%>/js/bootstrap-collapse.js"></script>
<script src="<%=path%>/js/bootstrap-carousel.js"></script>
<script src="<%=path%>/js/bootstrap-typeahead.js"></script>
<script src="<%=path%>/js/bootstrap.min.js"></script>
<script src="<%=path%>/js/bootstrap.js"></script>
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


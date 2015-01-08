<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="./favicon.ico">

    <title>Dashboard Template for Bootstrap</title>

    <!-- Bootstrap core CSS -->
    <%--<link href="../bootstrap-3.3.1/dist/css/bootstrap.min.css" rel="stylesheet">--%>
    <%--<link rel="stylesheet" href="./../jstree/vakata-jstree-841eee8/dist/themes/default/style.min.css" />--%>
    <link href="<%=path%>/bootstrap-3.3.1/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom styles for this template -->
    <link href="<%=path%>/css/dashboard.css" rel="stylesheet">
    <%--<link href="./dashboard.css" rel="stylesheet">--%>

    <%--<!-- Just for debugging purposes. Don't actually copy these 2 lines! -->--%>
    <%--<!--[if lt IE 9]>--%>
    <%--<script src="./assets/js/ie8-responsive-file-warning.js"></script><![endif]-->--%>
    <%--<script src="./assets/js/ie-emulation-modes-warning.js"></script>--%>

    <%--<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->--%>
    <%--<!--[if lt IE 9]>--%>
    <%--<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>--%>
    <%--<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>--%>
    <%--<![endif]-->--%>

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="<%=path%>/js/html5shiv.js"></script>
    <![endif]-->
</head>
<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">服务治理平台</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">Dashboard</a></li>
                <li><a href="#">Settings</a></li>
                <li><a href="#">Profile</a></li>
                <li><a href="#">Help</a></li>
            </ul>
            <form class="navbar-form navbar-right">
                <input type="text" class="form-control" placeholder="Search...">
            </form>
        </div>
    </div>
</nav>


<div>

    <div class="row">
        <%--<div class="col-sm-3 col-md-2 sidebar">--%>
            <%--<div id="data" class="demo"></div>--%>
        <%--</div>--%>
        <%--<div class="col-sm-10 col-sm-offset-3 col-md-20 col-md-offset-2 main">--%>
        <div class="col-sm-12 col-md-20 main">
            <%--<h2 class="sub-header">Section title</h2>--%>

            <div class="table-responsive">
                <ul class="nav nav-tabs">
                    <li role="presentation" class="active"><a href="#">Home</a></li>
                    <li role="presentation"><a href="#">Profile</a></li>
                    <li role="presentation"><a href="#">Messages</a></li>
                </ul>
                <%--<table class="table table-striped">--%>

                <%--</table>--%>
                <table cellpadding="0" cellspacing="0" border="0" class="display" id="operationTable">
                    <tfoot>
                    <tr>
                        <th>
                            <input type="text" name="operationId" id="operationId" value="操作ID"
                                   class="search_init" />
                        </th>
                        <th>
                            <input type="text" name="operationName" id="operationName" value="操作名称"
                                   class="search_init" />
                        </th>
                        <th>
                            <input type="text" name="serviceId" id="serviceId" value="服务ID"
                                   class="search_init" />
                        </th>
                        <th>
                            <input type="text" name="serviceName" id="serviceName" value="服务名称"
                                   class="search_init" />
                        </th>
                        <th>
                            <input type="text" name="state" id="state" value="操作状态"
                                   class="search_init" />
                        </th>
                        <th>
                            <input type="text" name="version" id="version" value="开发版本"
                                   class="search_init" />
                        </th>
                        <th>
                            <input type="text" name="publishVersion" id="publishVersion" value="上线版本"
                                   class="search_init" />
                        </th>
                        <th>
                            <input type="text" name="publishDate" id="publishDate" value="上线日期"
                                   class="search_init" />
                        </th>
                        <th>
                            <input type="hidden" name="action" id="action" value="动作"
                                   class="search_init" />
                        </th>
                        <th>
                            <input type="hidden" name="history" id="history" value="历史"
                                   class="search_init" />
                        </th>
                        <th>
                            <input type="hidden" name="invokeInfo" id="invokeInfo" value="调用情况"
                                   class="search_init" />
                        </th>
                    </tr>
                    </tfoot>
                </table>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="<%=path%>/js/jquery.js"></script>
<link rel="stylesheet"
      href="<%=path%>/themes/smoothness/jquery-ui-1.8.4.custom.css" />
<link rel="stylesheet" href="<%=path%>/css/index.css" />
<script src="<%=path%>/js/jquery-ui/js/jquery-1.10.2.js"></script>
<script src="<%=path%>/js/jquery.ui.core.js"></script>
<script src="<%=path%>/js/jquery.ui.widget.js"></script>
<script src="<%=path%>/js/jquery.ui.button.js"></script>
<script src="<%=path%>/js/jquery.ui.position.js"></script>
<script src="<%=path%>/js/jquery.ui.autocomplete.js"></script>
<script src="<%=path%>/js/jquery-ui-tabs.js"></script>
<script src="<%=path%>/js/jquery.ui.button.js"></script>
<script src='<%=path%>/DataTables-1.10.4/media/js/jquery.dataTables.js' type="text/javascript"></script>
<link rel='stylesheet' type='text/css'
      href='<%=path%>/DataTables-1.10.4/media/css/jquery.dataTables.css' />
<script src="<%=path%>/js/jquery.fileDownload.js" type="text/javascript"></script>
<script src="<%=path%>/js/layout.js" type="text/javascript"></script>
<script src="<%=path%>/js/operationAllManager.js" type="text/javascript"></script>
<script src="<%=path%>/js/operationAll.js" type="text/javascript"></script>
<script src="<%=path%>/js/json/json2.js" type="text/javascript"></script>
<%--<script src="../jquery/jquery-1.11.1.js"></script>--%>
<script src="<%=path%>/bootstrap-3.3.1/js/bootstrap.min.js"></script>
<%--<script src="./assets/js/docs.min.js"></script>--%>
<%--<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->--%>
<%--<script src="./assets/js/ie10-viewport-bug-workaround.js"></script>--%>
<%--<script>--%>
    <%--$('#data').jstree({--%>
        <%--'core' : {--%>
            <%--'data' : [--%>
                <%--{ "text" : "Root node", "children" : [--%>
                    <%--{ "text" : "Child node 1" },--%>
                    <%--{ "text" : "Child node 2" }--%>
                <%--]}--%>
            <%--]--%>
        <%--}--%>
    <%--});--%>
<%--</script>--%>
</body>
</html>
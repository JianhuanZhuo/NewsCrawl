<%--
  Created by IntelliJ IDEA.
  User: tom
  Date: 2017/9/20
  Time: 22:55
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>信息检索大作业</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/css/theme.css"/>">
</head>
<body>
<div class="container-fluid page-full-height">
    <div class="center-pane">
        <div class="info-pane flex-center flex-col">
            <img width="80%" src="<c:url value="/resources/images/title.png"/>">
            <form method="get" action="<c:url value="/search"/> ">
                <div class="input-group">
                    <%--<div class="input-group-btn search-panel">--%>
                        <%--<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">--%>
                            <%--<span id="search_concept">搜索方式</span> <span class="caret"></span>--%>
                        <%--</button>--%>
                        <%--<ul id="search_list" class="dropdown-menu" role="menu">--%>
                            <%--<li><a href="#" data-type="kw">关键字查询</a></li>--%>
                            <%--<li class="divider"></li>--%>
                            <%--<li><a href="#" data-type="bl">布尔查询</a></li>--%>
                            <%--<li><a href="#" data-type="ph">短语查询</a></li>--%>
                            <%--<li><a href="#" data-type="wf">通配符查询</a></li>--%>
                        <%--</ul>--%>
                    <%--</div>--%>
                    <input type="hidden" name="search_type" value="all" id="search_type">
                    <input type="text" class="form-control" name="content" placeholder="请输入搜索关键词...">
                    <div class="input-group-btn">
                        <button class="btn btn-default" type="submit">
                            <span class="glyphicon glyphicon-search"></span>
                        </button>
                    </div>
                </div>
            </form>
            <p>小组组员：卓建欢、李怡乐、彭萍、何敏、靳晓晗</p>
        </div>
    </div>
</div>
<script src="<c:url value="/resources/js/tether.js"/>"></script>
<script src="<c:url value="/resources/js/jquery.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.js"/>"></script>
<%--<script type="application/javascript">--%>
    <%--$("#search_list").find("a").click(function () {--%>
        <%--$("#search_concept").html($(this).html());--%>
        <%--$("#search_type").val($(this).data("type"));--%>
    <%--})--%>
<%--</script>--%>
</body>
</html>
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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
<div class="container-fluid head-container">
    <div class="container">
        <div class="visible-xs">
            <div class="jh-center jh-padding">
                <a href="<c:url value="/"/> ">
                    <img height="80px" src="<c:url value="/resources/images/title.png"/>">
                </a>
            </div>
        </div>
        <div class="row head-line">
            <div class="col-sm-2 hidden-xs">
                <a href="<c:url value="/"/> ">
                    <img width="100%" src="<c:url value="/resources/images/title.png"/>">
                </a>
            </div>
            <div class="col-md-6 col-sm-9">
                <div class="input-group">
                    <input type="text" value="${model.content}" id="search_content" class="form-control" name="content"
                           placeholder="请输入搜索关键词...">
                    <div class="input-group-btn">
                        <button id="search_btn" class="btn btn-default" type="submit">
                            <span class="glyphicon glyphicon-search"></span>
                        </button>
                        <button class="btn btn-default" type="button" data-toggle="collapse"
                                data-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample">
                            <span class="glyphicon glyphicon-filter"></span>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="container param-container collapse" id="collapseExample">
        <form action="<c:url value="/search" /> " method="get" class="form-horizontal">
            <div class="form-group">
                <label for="method" class="col-sm-2 control-label">指定搜索方式：</label>
                <div class="col-sm-4">
                    <select name="method" class="selectpicker form-control" id="method">
                        <option
                                <c:if test="${model.method=='kw'}">selected</c:if> value="kw">关键字查询
                        </option>
                        <%--<option--%>
                        <%--<c:if test="${model.method=='bl'}">selected</c:if> value="bl">布尔查询--%>
                        <%--</option>--%>
                        <%--<option--%>
                        <%--<c:if test="${model.method=='ph'}">selected</c:if> value="ph">短语查询--%>
                        <%--</option>--%>
                        <option
                                <c:if test="${model.method=='wf'}">selected</c:if> value="wf">通配符查询
                        </option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="time" class="col-sm-2 control-label">指定时间范围：</label>
                <div class="col-sm-4">
                    <select name="time" class="selectpicker form-control" id="time">
                        <option
                                <c:if test="${model.time=='week'}">selected</c:if> value="week">最近一周
                        </option>
                        <option
                                <c:if test="${model.time=='month'}">selected</c:if> value="month">最近一月
                        </option>
                        <option
                                <c:if test="${model.time=='year'}">selected</c:if> value="year">最近一年
                        </option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="sort" class="col-sm-2 control-label">指定排序方式：</label>
                <div class="col-sm-4">
                    <select name="sort" class="selectpicker form-control" id="sort">
                        <option
                                <c:if test="${model.sort=='relative'}">selected</c:if> value="relative">按相关度
                        </option>
                        <option
                                <c:if test="${model.sort=='time'}">selected</c:if> value="time">按时间
                        </option>
                        <option
                                <c:if test="${model.sort=='hot'}">selected</c:if> value="hot">按热度
                        </option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="match" class="col-sm-2 control-label">指定匹配域：</label>
                <div class="col-sm-4">
                    <select name="match" class="selectpicker form-control" id="match">
                        <option
                                <c:if test="${model.match=='all'}">selected</c:if> value="all">全部域
                        </option>
                        <option
                                <c:if test="${model.match=='title'}">selected</c:if> value="title">标题域
                        </option>
                        <option
                                <c:if test="${model.match=='content'}">selected</c:if> value="content">内容域
                        </option>
                        <option
                                <c:if test="${model.match=='cmt'}">selected</c:if> value="cmt">评论域
                        </option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <div class="checkbox">
                        <label>
                            <input name="duplicate"
                                   <c:if test="${model.duplicate=='on'}">checked</c:if> type="checkbox"> 不去除相似新闻
                        </label>
                    </div>
                </div>
            </div>
            <div class="form-group hidden">
                <input id="content" name="content" type="text" title="content" value="${model.content}"/>
                <input id="pageCapacity" name="pageCapacity" type="text" title="pageCapacity"
                       value="${model.pageCapacity}"/>
                <input id="currentPage" name="currentPage" type="text" title="currentPage"
                       value="${model.currentPage}"/>
                <div class="col-sm-offset-2 col-sm-10">
                    <button id="sub_btn" type="submit" class="btn btn-default">submit</button>
                </div>
            </div>
        </form>
    </div>
</div>

<c:if test="${resPage.total!=0}">
    <div class="container">
        <p>找到约 ${resPage.total} 条结果 （用时 ${cost} 毫秒）</p>
    </div>
</c:if>
<div class="container">
    <div class="row">
        <div class="col-md-9">
            <c:if test="${resPage.total==0}">
                <div class="jh-center jh-padding">
                    <a href="<c:url value="/"/> ">
                        <img height="200px" src="<c:url value="/resources/images/tan90.jpg"/>">
                    </a>
                </div>
            </c:if>
            <c:forEach var="news" items="${resPage.items}" varStatus="status">
                <div class="new_item">
                    <div class="new_item_img">
                        <c:choose>
                            <c:when test="${not empty news.pic}">
                                <img class="thumbnail" src="<c:url value="${news.pic}"/>">
                            </c:when>
                            <c:otherwise>
                                <img class="thumbnail" src="<c:url value="/resources/images/none.jpg"/>">
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="new_item_content">
                        <a class="title-link" href="${news.url}">${news.title}</a>
                        <button data-url="${news.url}"
                                class="preview"
                                data-content='<iframe style="zoom: 0.6;" height="1000" width="1000" frameBorder="0" src="${news.url}"></iframe>'>
                            预览
                        </button>
                        <p><fmt:formatDate value="${news.pubtime}" pattern="yyyy年MM月dd日"/><a href="#"><span
                                class="caret"></span>相似新闻</a></p>
                        <p>${news.abs}</p>
                    </div>
                </div>
            </c:forEach>

            <ul class="pagination">
                <li <c:if test="${empty prev}">class="disabled"</c:if>>
                    <a href="${prev}">«</a>
                </li>
                <c:forEach var="pair" items="${urls}">
                    <li
                            <c:if test="${pair.key == resPage.currentPage}">class="active"</c:if> >
                        <a href="${pair.value}">${pair.key}</a>
                    </li>
                </c:forEach>
                <li <c:if test="${empty next}">class="disabled"</c:if>>
                    <a href="${next}">»</a>
                </li>
            </ul>
        </div>
        <div class="col-md-3 hidden-xs hidden-sm">
            <div class="jh-margin jh-padding">
                <ul class="list-group">
                    <li class="list-group-item active">热门新闻推荐</li>
                    <c:forEach varStatus="status" items="${hots}" var="hot">
                        <li class="list-group-item">
                            <span class="badge">${status.count}</span>
                            <a href="${hot.url}" title="${hot.title}">${fn:substring(hot.title,0, 10)}...</a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
</div>

<div class="container-fluid foot jh-padding jh-center">
    <p>信息检索大作业小组组员：卓建欢、李怡乐、彭萍、何敏、靳晓晗</p>
</div>
<script src="<c:url value="/resources/js/tether.js"/>"></script>
<script src="<c:url value="/resources/js/jquery.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.js"/>"></script>
<script type="application/javascript">
    $("#search_btn").click(function () {
        $("#content").val($("#search_content").val());
        $("#currentPage").val("1");
        $("#sub_btn").click()
    });
    $("#search_content").on('keyup', function (e) {
        if (e.keyCode == 13) {
            $("#content").val($("#search_content").val());
            $("#currentPage").val("1");
            $("#sub_btn").click()
        }
    });

    $(".preview").popover({
        html: true,
        title: "网页预览",
        trigger: "focus"
    })
</script>
</body>
</html>
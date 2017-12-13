<%--
  User: tom
  Date: 2017/9/21
  Time: 10:47
--%>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
            <img src="<c:url value="/resources/images/404.png"/>">
            <p class="text-center">页面没找到，点击<a href="<c:url value="/"/> ">网站主页</a></p>
        </div>
    </div>
</div>
</body>
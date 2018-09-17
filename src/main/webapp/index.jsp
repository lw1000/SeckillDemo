<%--<?xml version="1.0" encoding="utf-8"?>--%>

<%--<taglib xmlns="http://java.sun.com/xml/ns/javaee"--%>
        <%--xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"--%>
        <%--xsi:schemaLocation="http://java.sun.com/xml/ns/javaee--%>
        <%--http://java.sun.com/xml/ns/javaee/web-jsptaglibrary_2_1.xsd"--%>
        <%--version="2.1">--%>

    <%--&lt;%&ndash;<tlib-version>1.0</tlib-version>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<short-name>myshortname</short-name>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<uri>http://mycompany.com</uri>&ndash;%&gt;--%>
        <%--<h1 class="text text-center">    <a class="btn btn-info" href="/SeckillDemo/seckill/list" target="_blank">link</a>--%>
        <%--</h1>--%>

    <%--<!-- Invoke 'Generate' action to add tags or functions -->--%>

<%--</taglib>--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/9/10 0010
  Time: 下午 10:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!--viewport 用来检测设备的宽度高度，用来适配-->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>初始页</title>
    <%@include file="WEB-INF/jsp/common/header.jsp"%>

</head>
<body>
<h1 class="text text-center">
    <a class="btn btn-info" href="/SeckillDemo/seckill/list" target="_parent">link</a>
</h1>

<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>





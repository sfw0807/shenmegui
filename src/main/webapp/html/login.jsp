<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>治理平台</title>
    <link rel="stylesheet" type="text/css" href="/css/login.css"/>
</head>
<body>
<div id="box">
    <div class="title">Login</div>
    <div class="content">
        <form:form modelAttribute="loginCommand">
            <form:errors path="*" element="div" cssClass="errors"/>
            <div>
                <div class="form-label">Username:</div>
                <form:input path="username"/></div>
            <div>
                <div class="form-label">Password:</div>
                <form:password path="password"/></div>
            <div><form:checkbox path="rememberMe"/> Remember Me</div>
            <div><input type="submit" value="Login"/></div>
        </form:form>
    </div>
</div>
<p>
    Users created through the signup form have the role "user". You can also log in as admin/admin, which has the
    "admin" role.
</p>
</body>
</html>

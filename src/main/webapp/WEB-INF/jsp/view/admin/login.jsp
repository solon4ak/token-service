<%--@elvariable id="adminLoginForm" type="ru.tokens.site.controller.admin.AdminController.AdminLoginForm"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:loggedOut htmlTitle="Administrator authentication" 
                bodyTitle="Вход на страницу администрирования">  
        
    You must log in to access admin panel.<br /><br />
    <c:if test="${loginFailed}">
        <b>User credentials doesn't correspond.</b><br /><br />
    </c:if>
    <form:form method="post" modelAttribute="adminLoginForm">
        <fieldset>
            <legend>Admin authentication form</legend>
            <form:label path="email">E-mail:</form:label>
            <form:input path="email" /><br />
            <form:label path="password">Password</form:label>
            <form:password path="password" /><br /><br />
        </fieldset>
        <input type="submit" value="Log In" />
    </form:form>    
</template:loggedOut>>        
<%--@elvariable id="uuidString" type="java.lang.String"--%>
<%--@elvariable id="message" type="java.lang.String"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic htmlTitle="Error for token: ${uuidString}"
                bodyTitle="Error for token: '${uuidString}'">
    <hr />
    <c:if test="${message != null}">
        <b>Message:&nbsp;&nbsp;<c:out value="${message}"/></b>
    </c:if>
        <hr />
        <a href="<c:url value="/token/register"/>">Регистрация жетона</a><br /><br />
        <a href="<c:url value="/"/>">На главную</a>
</template:basic>
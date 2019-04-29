<%--@elvariable id="message" type="java.lang.String"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic htmlTitle="User doesn't exist!"
                bodyTitle="User doesn't exist!">
    <hr />
    <c:if test="${message != null}">
        <b>Message:&nbsp;&nbsp;<c:out value="${message}"/></b>
    </c:if>
        <hr />
        <a href="<c:url value="/"/>">На главную</a>
</template:basic>
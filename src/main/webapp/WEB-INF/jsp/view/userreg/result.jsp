<%--@elvariable id="message" type="java.lang.String"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic htmlTitle="E-mail confirmation"
                bodyTitle="User e-mail confirmation.">
    <b>Message:&nbsp;&nbsp;<c:out value="${message}"/></b>
    <hr />
    <a href="<c:url value="/"/>">На главную</a>
</template:basic>
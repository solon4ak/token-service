<%--@elvariable id="categoryForm" type="ru.tokens.site.controller.CategoryController.CategoryForm"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:admin htmlTitle="Admin panel" bodyTitle="Admin panel">  
        
    <c:if test="${message != null}">
        <c:out value="${message}" />
        <hr />
    </c:if>
    Admin content  
</template:admin>
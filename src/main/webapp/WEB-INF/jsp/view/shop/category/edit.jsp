<%--@elvariable id="categoryForm" type="ru.tokens.site.controller.CategoryController.CategoryForm"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:admin htmlTitle="Editing store category" bodyTitle="Изменить категорию товара">  
    <c:if test="${message != null}">
        <c:out value="${message}" />
        <hr />
    </c:if>
    <form:form method="post" modelAttribute="categoryForm">
        <fieldset>
            <legend>Edit category</legend>
            <form:label path="name">Category name</form:label>
            <form:input path="name"/>           
            <hr />
            <input type="submit" value="Submit" />
        </fieldset>                
    </form:form>    
</template:admin>
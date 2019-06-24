<%--@elvariable id="categories" type="java.util.List"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:admin htmlTitle="List categories" bodyTitle="список категорий">
    <hr />    
    <c:if test="${message != null}">
        <c:out value="${message}" />
        <hr />
    </c:if>
    <c:choose>
        <c:when test="${fn:length(categories) > 0}">
            <table class="data_table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Category</th>
                        <th>Products</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${categories}" var="category">
                        <tr>
                            <td><c:url value="${category.categoryId}" /></td>
                            <td><c:url value="${category.categoryName}" /></td>
                            <td><c:url value="${category.itemsNumber}" /></td>
                            <td>
                                <a href="<c:url value="/admin/shop/category/view/${category.categoryId}" />">View</a> /                                                                
                                <a href="<c:url value="/admin/shop/category/edit/${category.categoryId}" />">Edit</a> / 
                                <a href="<c:url value="/admin/shop/category/delete/${category.categoryId}" />">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>            
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            Список категорий пуст
        </c:otherwise>            
    </c:choose>
    <br /><br />
    <a href="<c:url value="/admin/shop/category/add" />">Add category</a>
</template:admin>
<%--@elvariable id="products" type="java.util.List"--%>
<%--@elvariable id="category" type="ru.tokens.site.entities.shop.Category"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:admin htmlTitle="List products for category: ${category.categoryName}" 
                bodyTitle="Products list for category: ${category.categoryName}">
    <hr />    
    <c:if test="${message != null}">
        <c:out value="${message}" />
        <hr />
    </c:if>
    <c:choose>
        <c:when test="${fn:length(products) > 0}">
            <table class="data_table">
                <thead>
                    <tr>
                        <th>Product</th>
                        <th>Last updated</th>
                        <th>Price</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${products}" var="product">
                        <tr>
                            <td><c:url value="${product.productName}" /></td>
                            <td><tags:localDate date="${product.lastUpdated}" /></td>
                            <td><c:url value="${product.initialPrice}" /> руб.</td> 
                            <td>
                                <a href="<c:url value="/admin/shop/product/${product.productId}/view" />">View</a> /                                                                
                                <a href="<c:url value="/admin/shop/product/${product.productId}/edit" />">Edit</a> / 
                                <a href="<c:url value="/admin/shop/product/${product.productId}/delete" />">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>            
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            В категории товаров пока нет.
        </c:otherwise>            
    </c:choose>
    <br /><br />
    <a href="<c:url value="/admin/shop/product/add" />">Add product</a>
</template:admin>
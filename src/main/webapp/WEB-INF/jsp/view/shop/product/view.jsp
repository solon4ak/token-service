<%--@elvariable id="product" type="ru.tokens.site.entities.shop.Product"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:admin htmlTitle="Product: ${product.productName}" 
                bodyTitle="Просмотр товара: ${product.productName}">
    <hr />    
    <c:if test="${message != null}">
        <c:out value="${message}" />
        <hr />
    </c:if>
    <b>Created:</b> <tags:localDate date="${product.createdDate}" /><br /><br />
    <b>Last updated:</b> <tags:localDate date="${product.lastUpdated}" />
    <hr />
    <b>Price:</b> <c:out value="${product.initialPrice}" /> руб.<br /><br />    
    <b>Description:</b> <br /><br />
    <c:out escapeXml="false" value="${product.description}" />
    <hr />
    <b>Categories:</b><br /><br />
    <ul>
        <c:forEach items="${product.categories}" var="cat">
            <li>
                <a href="<c:url value="/admin/shop/category/view/${cat.categoryId}" />">
                    <c:out value="${cat.categoryName}" />
                </a>
            </li>
        </c:forEach>
    </ul>
    <c:if test="${fn:length(product.pictures) > 0}">
        <hr />
        <b>Product images:</b><br /><br />
        <c:forEach items="${product.pictures}" var="img">
            <a href="<c:url value="/admin/shop/product/image/${img.id}/view" />" target="_blank">
                <img src="<c:url value="/admin/shop/product/image/${img.id}/icon" />" 
                     alt="${img.name}"/>
            </a>&nbsp;
        </c:forEach>
    </c:if>
    <br /><br />
    <a href="<c:url value="/admin/shop/product/${product.productId}/edit" />">Edit</a> / 
    <a href="<c:url value="/admin/shop/product/${product.productId}/delete" />">Delete</a>
</template:admin>
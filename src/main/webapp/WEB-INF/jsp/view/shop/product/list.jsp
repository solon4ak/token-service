<%--@elvariable id="products" type="java.util.List"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:admin htmlTitle="Product list" 
                bodyTitle="Список товаров">
    <hr />    
    <c:if test="${message != null}">
        <c:out value="${message}" />
        <hr />
    </c:if>        
    <a href="<c:url value="/admin/shop/product/add" />">Add product</a>
    <hr />
    <c:choose>
        <c:when test="${fn:length(products) > 0}">
            <table class="data_table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Product</th>
                        <th>Price</th>
                        <th>Pictures</th>
                        <th>Categories</th>
                        <th>Last updated</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${products}" var="product">
                        <tr>
                            <td><c:out value="${product.productId}" /></td>
                            <td>
                                <a href="<c:url value="/admin/shop/product/${product.productId}/view" />">
                                    ${product.productName}
                                </a>
                            </td>
                            <td>
                                <c:out value="${product.initialPrice}" /> руб.
                            </td>
                            <td>
                                <c:out value="${fn:length(product.pictures)}" />
                            </td>
                            <td>
                                <ul>
                                    <c:forEach items="${product.categories}" var="category">
                                        <li>
                                            <a href="<c:url value="/admin/shop/category/view/${category.categoryId}" />">
                                                <c:out value="${category.categoryName}" />
                                            </a>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </td>
                            <td>
                                <tags:localDate date="${product.lastUpdated}" />
                            </td>
                            <td>                        
                                <a href="<c:url value="/admin/shop/product/${product.productId}/edit" />">Edit</a> / 
                                <a href="<c:url value="/admin/shop/product/${product.productId}/delete" />">Delete</a>
                            </td>
                        </tr>
                    </c:forEach> 
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            Products list is empty!
        </c:otherwise>
    </c:choose>
    
</template:admin>
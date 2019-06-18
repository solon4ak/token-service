<%--@elvariable id="categories" type="java.util.List"--%>
<%--@elvariable id="products" type="java.util.List"--%>
<%--@elvariable id="category" type="ru.tokens.site.entities.shop.Category"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_shop htmlTitle="Shop :: ${category.categoryName}"
                        bodyTitle="${category.categoryName}">

    <jsp:attribute name="leftColumnContent">
        <nav class="nav flex-column">
            <c:forEach items="${categories}" var="category">
                <a class="nav-link" href="<c:url value="/shop/category/${category.categoryId}" />">
                    <c:out value="${category.categoryName}" />
                </a>
            </c:forEach>            
        </nav>
    </jsp:attribute>

    <jsp:body>
        <div class="container">    
            <c:if test="${sessionScope['ru.tkn.user.principal'] eq null}">
                <div class="alert alert-info alert-dismissible fade show" role="alert">
                    <p>Для совершения покупок необходимо 
                        <a href="<c:url value="/login" />">
                            авторизоваться или зарегистрироваться.</a><br />
                        <u>Не забудьте указать свой почтовый адрес!</u></p>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:if>            

            <c:choose>
                <c:when test="${fn:length(products) > 0}">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Picture</th>
                                <th>Product</th>
                                <th>Price</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${products}" var="product">
                                <tr>
                                    <td>
                                        <c:if test="${product.mainPicture ne null}">
                                            <img class="img-thumbnail" 
                                                 src="<c:url value="/shop/product/image/${product.mainPicture}/thumbnail" />" 
                                                 alt="${product.productName}" width="150" />
                                        </c:if>                                        
                                    </td>
                                    <td class="align-middle">
                                        <a href="<c:url value="/shop/category/${category.categoryId}/product/${product.productId}" />">
                                            <c:out value="${product.productName}" />
                                        </a>
                                    </td>
                                    <td class="align-middle">
                                        <fmt:formatNumber type="currency" currencySymbol="&#x20bd; " value="${product.initialPrice}"/>
                                    </td>
                                    <td class="align-middle">
                                        <a class="btn btn-primary btn-sm" 
                                           href="
                                           <c:url value="/shop/cart/add/category/${category.categoryId}/product/${product.productId}">
                                               <c:param name="src" value="cat" />
                                           </c:url>
                                           ">
                                            В корзину
                                        </a>                              
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>                
                    </table>
                </c:when>
                <c:otherwise>
                    В данной категории товаров пока нет.
                </c:otherwise>
            </c:choose>
        </div>
    </jsp:body>
</template:basic_bs_shop>

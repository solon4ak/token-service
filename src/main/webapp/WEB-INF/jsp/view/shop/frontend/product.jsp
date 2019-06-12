<%--@elvariable id="product" type="ru.tokens.site.entities.shop.Product"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_shop_one_col htmlTitle="Shop :: ${category.categoryName} :: ${product.productName}"
                                bodyTitle="${product.productName}">    

    <jsp:body>
        <div class="container"> 
            <div class="row justify-content-between">
                <div class="col-5">
                    <c:if test="${product.pictures ne null and fn:length(product.pictures) > 0}">
                        <p class="imglist" style="max-width: 520px;">
                            <a data-fancybox-trigger="preview" href="javascript:;">
                                <img class="img-thumbnail m-1" src="<c:url value="/shop/product/image/${product.mainPicture}/thumbnail" />" 
                                     alt="<c:out value="${product.productName}" />" />
                            </a>
                            <c:forEach items="${product.pictures}" var="pic">
                                <a href="<c:url value="/shop/product/image/${pic.id}/view" />" 
                                   data-fancybox="preview" data-width="1500" data-height="1000">
                                    <img class="img-thumbnail m-1" src="<c:url value="/shop/product/image/${pic.id}/icon" />" 
                                         alt="<c:out value="${product.productName}" />" />
                                </a> 
                            </c:forEach>                                       
                        </p>
                    </c:if>                    
                </div>
                <div class="col-7">
                    <h4><c:out value="${product.productName}" /></h4>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item">
                            <span>Category</span> :  
                            <a class="active" href="<c:url value="/shop/category/${category.categoryId}" />">
                                <c:out value="${category.categoryName}" />
                            </a>
                        </li>                        
                        <li class="list-group-item">
                            <span>Price</span> :
                            <fmt:formatNumber type="currency" currencySymbol="&#x20bd; " value="${product.initialPrice}"/>
                        </li>
                    </ul>
                    <p>
                        <c:out value="${product.description}" escapeXml="false" />
                    </p>
                    <hr />
                    <a class="btn btn-primary" 
                       href="
                       <c:url value="/shop/cart/add/category/${category.categoryId}/product/${product.productId}">
                           <c:param name="src" value="prod" />
                       </c:url>
                       ">
                        В корзину
                    </a>
                </div>
            </div>            
        </div>
    </jsp:body>
</template:basic_bs_shop_one_col>

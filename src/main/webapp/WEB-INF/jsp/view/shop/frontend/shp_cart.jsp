<%--@elvariable id="cartItems" type="java.util.List"--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_shop_one_col htmlTitle="Shop :: Shopping cart"
                                bodyTitle="Корзина">   

    <jsp:body>
        <div class="container">    
            <c:choose>
                <c:when test="${cart != null && fn:length(cartItems) > 0}">                    
                    <table class="table justify-content-lg-center">
                        <thead>
                            <tr>
                                <th>Товар</th>
                                <th>Название</th>
                                <th>Сумма</th>
                                <th>Количество</th>
                            </tr>
                        </thead>
                        <tbody>                            
                            <c:forEach items="${cartItems}" var="item">
                                <tr>
                                    <td>
                                        <img class="img-thumbnail" 
                                             src="<c:url value="/shop/product/image/${item.product.mainPicture}/thumbnail" />" 
                                             alt="${product.productName}" width="150" />
                                    </td>
                                    <td class="align-middle">
                                        <c:out value="${item.product.productName}" />
                                    </td>
                                    <td class="align-middle">
                                        <div>
                                            <fmt:formatNumber type="currency" currencySymbol="&#x20bd; " value="${item.total}"/>
                                        </div> 
                                        <small>
                                            (<fmt:formatNumber type="currency" currencySymbol="&#x20bd; " value="${item.product.initialPrice}" /> / шт.)
                                        </small>                                        
                                    </td>
                                    <td class="align-middle">  
                                        <form action="<c:url value='/shop/cart/update'/>" method="post">
                                            <input type="hidden"
                                                   name="productId"
                                                   value="${item.product.productId}">
                                            <input type="text"
                                                   maxlength="2"
                                                   size="2"
                                                   value="${item.quantity}"
                                                   name="quantity"
                                                   style="margin:5px">
                                            <input type="submit"
                                                   class="btn btn-outline-info btn-sm align-baseline"
                                                   name="submit"
                                                   value="Изменить">
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                            <tr>
                                <td>&nbsp;</td>
                                <td>&nbsp;</td>
                                <td><strong>Предварительный итог</strong></td>
                                <td>
                                    <strong>
                                        <fmt:formatNumber type="currency" currencySymbol="&#x20bd; " value="${cart.subtotal}"/>
                                    </strong>
                                </td>
                            </tr>
                        </tbody>                
                    </table>
                    <a class="btn btn-primary btn-sm" href="<c:url value="/shop/cart/clear" />">Очистить корзину</a>
                    <a class="btn btn-primary btn-sm" href="<c:url value="/shop/main" />">Продолжить покупки</a>
                    <a class="btn btn-primary btn-sm" href="<c:url value="/shop/checkout" />">Оформить заказ</a>
                </c:when>
                <c:otherwise>
                    <p class="m-2">Корзина пуста</p>
                    <a class="btn btn-primary btn-sm" href="<c:url value="/shop/main" />">Продолжить покупки</a>
                </c:otherwise>
            </c:choose>
        </div>
    </jsp:body>
</template:basic_bs_shop_one_col>

<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:basic_bs_three_col htmlTitle="User :: User orders :: Order #${orderRecord.orderId}"
                             bodyTitle="Заказ #${orderRecord.orderId}">   

    <jsp:attribute name="leftColumnContent">
        <nav class="nav flex-column">  
            <a class="nav-link disabled" href="<c:url value="/user/view" />">
                Пользователь
            </a>
            <a class="nav-link" href="<c:url value="/token/user/postaddress/view" />">
                Почтовый адрес
            </a>
            <a class="nav-link" href="<c:url value="/user/order/list" />">
                Заказы
            </a>
            <a class="nav-link" href="<c:url value="/token/user/view" />">
                Жетон
            </a>            
        </nav>
    </jsp:attribute>

    <jsp:attribute name="rightColumnContent">
        <nav class="nav flex-column">
<!--            <a class="nav-link" href="<c:url value="/user/edit" />">
                Edit
            </a>-->
        </nav>        
    </jsp:attribute>

    <jsp:body>
        <div class="container">    
            <table class="table">
                <thead>
                    <tr>
                        <th colspan="3" class="lead text-uppercase">заказ #${orderRecord.orderId}</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><strong>товар</strong></td>
                        <td><strong>количество</strong></td>
                        <td><strong>сумма</strong></td>
                    </tr>

                    <c:forEach items="${orderedProducts}" var="op">
                        <tr>
                            <td>
                                <a href="<c:url value="/shop/category/${op.product.categories[0].categoryId}/product/${op.product.productId}" />">
                                    <c:out value="${op.product.productName}" />
                                </a>                                
                            </td>
                            <td>
                                <c:url value="${op.quantity}" />
                                &nbsp;(
                                <small>
                                    x&nbsp;<fmt:formatNumber type="currency" currencySymbol="&#x20bd; "
                                                      value="${op.product.initialPrice}"/>
                                </small>                                        
                                )
                            </td>
                            <td>
                                <fmt:formatNumber type="currency" currencySymbol="&#x20bd; "
                                                  value="${op.product.initialPrice * op.quantity}"/>
                            </td>
                        </tr>
                    </c:forEach>

                    <tr>
                        <td colspan="3" style="padding: 0 20px"></td>
                    </tr>

                    <tr>
                        <td colspan="2"><strong>доставка:</strong></td>
                        <td>
                            <fmt:formatNumber type="currency"
                                              currencySymbol="&#x20bd; "
                                              value="${surcharge}"/></td>
                    </tr>

                    <tr>
                        <td colspan="2"><strong>итог:</strong></td>
                        <td>
                            <fmt:formatNumber type="currency"
                                              currencySymbol="&#x20bd; "
                                              value="${orderRecord.amount}"/></td>
                    </tr>

                    <tr>
                        <td colspan="3" style="padding: 0 20px"></td>
                    </tr>

                    <tr>
                        <td colspan="3"><strong>заказ принят:</strong>
                            <fmt:formatDate value="${orderRecord.created}"
                                            type="both"
                                            dateStyle="short"
                                            timeStyle="short"/>
                        </td>
                    </tr>
                </tbody>
            </table> 
        </div>
    </jsp:body>
</template:basic_bs_three_col>
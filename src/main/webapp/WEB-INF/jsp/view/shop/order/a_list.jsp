<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<template:admin htmlTitle="List orders" bodyTitle="Список заказов">
    <hr />    
    <c:if test="${message != null}">
        <c:out value="${message}" />
        <hr />
    </c:if>
    <c:choose>
        <c:when test="${orders != null && fn:length(orders) > 0}">                    
            <table class="data_table">
                <thead>
                    <tr>
                        <th>ID заказа</th>
                        <th>ID пользователя</th>
                        <th>Дата размещения</th>
                        <th>Сумма</th>
                        <th>Статус</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${orders}" var="order">
                        <tr>
                            <td>
                                <a href="<c:url value='/admin/shop/order/view/${order.orderId}' />">
                                    <c:out value="${order.orderId}" />
                                </a>
                            </td>
                            <td>
                                <a href="#">
                                    <c:out value="${order.user.userId}" />
                                </a>
                            </td>
                            <td>
                                <fmt:formatDate value="${order.created}"
                                                type="both"
                                                dateStyle="short"
                                                timeStyle="short"/>
                            </td>
                            <td>
                                <fmt:formatNumber type="currency"
                                                  currencySymbol="&#x20bd; "
                                                  value="${order.amount}"/>
                            </td>
                            <td><c:out value="${order.orderStatus}" /></td>
                        </tr>
                    </c:forEach>                            
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p class="m-2">Список заказов пуст</p>
        </c:otherwise>
    </c:choose>
</template:admin>